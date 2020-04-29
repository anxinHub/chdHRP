/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostIncome;
import com.chd.hrp.cost.serviceImpl.CostIncomeServiceImpl;

/**
* @Title. @Description.
* 科室收入数据总表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostIncomeController extends BaseController{
	private static Logger logger = Logger.getLogger(CostIncomeController.class);
	
	
	@Resource(name = "costIncomeService")
	private final CostIncomeServiceImpl costIncomeService = null;
   
   
	/**
	*科室收入数据总表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costincome/costIncomeMainPage", method = RequestMethod.GET)
	public String costIncomeMainPage(Model mode) throws Exception {

		return "hrp/cost/costincome/costIncomeMain";

	}
	/**
	*科室收入数据总表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costincome/costIncomeAddPage", method = RequestMethod.GET)
	public String costIncomeAddPage(Model mode) throws Exception {

		return "hrp/cost/costincome/costIncomeAdd";

	}
	/**
	*科室收入数据总表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costincome/addCostIncome", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costIncomeJson = costIncomeService.addCostIncome(mapVo);

		return JSONObject.parseObject(costIncomeJson);
		
	}
	/**
	*科室收入数据总表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costincome/queryCostIncome", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costIncome = costIncomeService.queryCostIncome(getPage(mapVo));

		return JSONObject.parseObject(costIncome);
		
	}
	/**
	*科室收入数据总表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costincome/deleteCostIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostIncome(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("year_month",ids[3]);   
			mapVo.put("appl_dept_no",ids[4]);   
			mapVo.put("exec_dept_no",ids[5]); 
            listVo.add(mapVo);
        }
		String costIncomeJson = costIncomeService.deleteBatchCostIncome(listVo);
	   return JSONObject.parseObject(costIncomeJson);
			
	}
	
	/**
	*科室收入数据总表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costincome/costIncomeUpdatePage", method = RequestMethod.GET)
	
	public String costIncomeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostIncome costIncome = new CostIncome();
		costIncome = costIncomeService.queryCostIncomeByCode(mapVo);
		mode.addAttribute("group_id", costIncome.getGroup_id());
		mode.addAttribute("hos_id", costIncome.getHos_id());
		mode.addAttribute("copy_code", costIncome.getCopy_code());
		mode.addAttribute("year_month", costIncome.getYear_month());
		mode.addAttribute("appl_dept_id", costIncome.getAppl_dept_id());
		mode.addAttribute("appl_dept_no", costIncome.getAppl_dept_no());
		mode.addAttribute("exec_dept_id", costIncome.getExec_dept_id());
		mode.addAttribute("exec_dept_no", costIncome.getExec_dept_no());
		mode.addAttribute("charge_kind_code", costIncome.getCharge_kind_code());
		mode.addAttribute("money", costIncome.getMoney());
		mode.addAttribute("create_user", costIncome.getCreate_user());
		mode.addAttribute("create_date", costIncome.getCreate_date());
		return "hrp/cost/costincome/costIncomeUpdate";
	}
	/**
	*科室收入数据总表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costincome/updateCostIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costIncomeJson = costIncomeService.updateCostIncome(mapVo);
		
		return JSONObject.parseObject(costIncomeJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costincome/costIncomeImportPage", method = RequestMethod.GET)
	public String costIncomeImportPage(Model mode) throws Exception {

		return "hrp/cost/costincome/costIncomeImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costincome/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\对应目录","对应字典.xls");
	    return null; 
	 }
	 
	/**
	*科室收入数据总表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costincome/readCostIncomeFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
    	List<CostIncome> list2 = new ArrayList<CostIncome> ();  
    	List<String[]> list = UploadUtil.readFile(plupload, request, response);
    	List<CostIncome> errorList = new ArrayList<CostIncome>();
		for (int i = 1; i < list.size(); i++) {
			String temp[] = list.get(i);//行
			Map<String, Object> mapVo = new HashMap<String, Object>();
			//模版生成,根据不同字典处理业务
			if("".equals(temp[0]) || "".equals(temp[1])){
				CostIncome costIncome = new CostIncome();
				
					costIncome.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					costIncome.setHos_id(Long.getLong(SessionManager.getHosId()));
					costIncome.setCopy_code(SessionManager.getCopyCode());
					costIncome.setYear_month(temp[3]);
					costIncome.setAppl_dept_id(Long.getLong(temp[4]));
					costIncome.setAppl_dept_no(Long.getLong(temp[5]));
					costIncome.setExec_dept_id(Long.getLong(temp[6]));
					costIncome.setExec_dept_no(Long.getLong(temp[7]));
					costIncome.setCharge_kind_code(temp[8]);
					costIncome.setMoney(Double.valueOf(temp[9]));
					costIncome.setCreate_user(Long.valueOf(temp[10]));
					costIncome.setCreate_date(DateUtil.stringToDate(temp[11]));
				costIncome.setError_type("数据表列存在空的数据列");
				errorList.add(costIncome);
			}else{
				//需要转换各别列 通过SessionManager 里面获取
					mapVo.put("group_id", SessionManager.getGroupId());
				
					mapVo.put("hos_id", SessionManager.getHosId());
				
					mapVo.put("copy_code", SessionManager.getCopyCode());
				
					mapVo.put("year_month",temp[3]);
				
					mapVo.put("appl_dept_id",temp[4]);
				
					mapVo.put("appl_dept_no",temp[5]);
				
					mapVo.put("exec_dept_id",temp[6]);
				
					mapVo.put("exec_dept_no",temp[7]);
				
					mapVo.put("charge_kind_code",temp[8]);
				
					mapVo.put("money",temp[9]);
				
					mapVo.put("create_user",temp[10]);
				
					mapVo.put("create_date",temp[11]);
				
			}
			try {
				CostIncome data = new CostIncome();
				data = costIncomeService.queryCostIncomeByCode(mapVo);
					if(data != null){
					data.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					data.setHos_id(Long.getLong(SessionManager.getHosId()));
					data.setCopy_code(SessionManager.getCopyCode());
					data.setYear_month(temp[3]);
					data.setAppl_dept_id(Long.getLong(temp[4]));
					data.setAppl_dept_no(Long.getLong(temp[5]));
					data.setExec_dept_id(Long.getLong(temp[6]));
					data.setExec_dept_no(Long.getLong(temp[7]));
					data.setCharge_kind_code(temp[8]);
					data.setMoney(Double.valueOf(temp[9]));
					data.setCreate_user(Long.valueOf(temp[10]));
					data.setCreate_date(DateUtil.stringToDate(temp[11]));
					data.setError_type("数据在数据库中已存在");
					errorList.add(data);
					}else{
							if(i==list.size()-1){
							String dataJson = costIncomeService.addCostIncome(mapVo);
							JSONObject json = JSONObject.parseObject(dataJson);
							String flag = String.valueOf(json.get("state"));
							if(!flag.equals("true")){
								CostIncome data_error = new CostIncome();
								data_error.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_error.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_error.setCopy_code(SessionManager.getCopyCode());
								data_error.setYear_month(temp[3]);
								data_error.setAppl_dept_id(Long.getLong(temp[4]));
								data_error.setAppl_dept_no(Long.getLong(temp[5]));
								data_error.setExec_dept_id(Long.getLong(temp[6]));
								data_error.setExec_dept_no(Long.getLong(temp[7]));
								data_error.setCharge_kind_code(temp[8]);
								data_error.setMoney(Double.valueOf(temp[9]));
								data_error.setCreate_user(Long.valueOf(temp[10]));
								data_error.setCreate_date(DateUtil.stringToDate(temp[11]));
								data_error.setError_type("导入失败");
								errorList.add(data_error);
								}
							}
					}
			} catch (DataAccessException e) {
				CostIncome data_exc = new CostIncome();
								data_exc.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_exc.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_exc.setCopy_code(SessionManager.getCopyCode());
								data_exc.setYear_month(temp[3]);
								data_exc.setAppl_dept_id(Long.getLong(temp[4]));
								data_exc.setAppl_dept_no(Long.getLong(temp[5]));
								data_exc.setExec_dept_id(Long.getLong(temp[6]));
								data_exc.setExec_dept_no(Long.getLong(temp[7]));
								data_exc.setCharge_kind_code(temp[8]);
								data_exc.setMoney(Double.valueOf(temp[9]));
								data_exc.setCreate_user(Long.valueOf(temp[10]));
								data_exc.setCreate_date(DateUtil.stringToDate(temp[11]));
				data_exc.setError_type("导入系统出错");
				errorList.add(data_exc);
			}
		}
		list2.addAll(errorList);
		mode.addAttribute("resultsJson",  ChdJson.toJson(list2, list2.size()));
	    	return "hrp/cost/costincome/costincomeImportMessage";
    }  
	/**
	*科室收入数据总表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costincome/addBatchCostIncome", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostIncome(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("year_month",ids[3]);   
			mapVo.put("appl_dept_id",ids[4]);   
			mapVo.put("appl_dept_no",ids[5]);   
			mapVo.put("exec_dept_id",ids[6]);   
			mapVo.put("exec_dept_no",ids[7]);   
			mapVo.put("charge_kind_code",ids[8]);   
			mapVo.put("money",ids[9]);   
			mapVo.put("create_user",ids[10]);   
			mapVo.put("create_date",ids[11]); 
            listVo.add(mapVo);
        }
		String costIncomeJson = costIncomeService.addBatchCostIncome(listVo);
	    return JSONObject.parseObject(costIncomeJson);
    }
}

