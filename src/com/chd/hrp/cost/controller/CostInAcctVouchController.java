/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostInAcctVouch;
import com.chd.hrp.cost.entity.CostIncomeItemArrt;
import com.chd.hrp.cost.serviceImpl.CostInAcctVouchServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostIncomeItemArrtServiceImpl;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;

/**
* @Title. @Description.
* 科室收入总账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostInAcctVouchController extends BaseController{
	private static Logger logger = Logger.getLogger(CostInAcctVouchController.class);
	
	
	@Resource(name = "costInAcctVouchService")
	private final CostInAcctVouchServiceImpl costInAcctVouchService = null;
	
	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;
	
	@Resource(name = "costIncomeItemArrtService")
	private final CostIncomeItemArrtServiceImpl costIncomeItemArrtService = null;
	
   
   
	/**
	*科室收入总账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costinacctvouch/costInAcctVouchMainPage", method = RequestMethod.GET)
	public String costInAcctVouchMainPage(Model mode) throws Exception {

		return "hrp/cost/costinacctvouch/costInAcctVouchMain";

	}
	/**
	*科室收入总账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costinacctvouch/costInAcctVouchAddPage", method = RequestMethod.GET)
	public String costInAcctVouchAddPage(Model mode) throws Exception {

		return "hrp/cost/costinacctvouch/costInAcctVouchAdd";

	}
	/**
	*科室收入总账<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costinacctvouch/addCostInAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostInAcctVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costInAcctVouchJson = costInAcctVouchService.addCostInAcctVouch(mapVo);

		return JSONObject.parseObject(costInAcctVouchJson);
		
	}
	/**
	*科室收入总账<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costinacctvouch/queryCostInAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostInAcctVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());

		mapVo.put("is_flag", para_value);
		
		String costInAcctVouch = costInAcctVouchService.queryCostInAcctVouch(getPage(mapVo));

		return JSONObject.parseObject(costInAcctVouch);
		
	}
	/**
	*科室收入总账<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costinacctvouch/deleteCostInAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostInAcctVouch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("year_month",ids[3]);   
			mapVo.put("dept_id",ids[4]);   
			mapVo.put("dept_no",ids[5]);   
			mapVo.put("income_item_id",ids[6]); 
            listVo.add(mapVo);
        }
		String costInAcctVouchJson = costInAcctVouchService.deleteBatchCostInAcctVouch(listVo);
	   return JSONObject.parseObject(costInAcctVouchJson);
			
	}
	
	/**
	*科室收入总账<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costinacctvouch/costInAcctVouchUpdatePage", method = RequestMethod.GET)
	
	public String costInAcctVouchUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostInAcctVouch costInAcctVouch = new CostInAcctVouch();
        
        String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
    	mapVo.put("is_flag", para_value);
        
		costInAcctVouch = costInAcctVouchService.queryCostInAcctVouchByCode(mapVo);
		
		mode.addAttribute("group_id", costInAcctVouch.getGroup_id());
		
		mode.addAttribute("hos_id", costInAcctVouch.getHos_id());
		
		mode.addAttribute("copy_code", costInAcctVouch.getCopy_code());
		
		mode.addAttribute("year_month", costInAcctVouch.getYear_month());
		
		mode.addAttribute("dept_id", costInAcctVouch.getDept_id());
		
		mode.addAttribute("dept_no", costInAcctVouch.getDept_no());
		
		mode.addAttribute("dept_name", costInAcctVouch.getDept_name());
		
		mode.addAttribute("income_item_id", costInAcctVouch.getIncome_item_id());
		
		mode.addAttribute("income_item_name", costInAcctVouch.getIncome_item_name());
		
		mode.addAttribute("amount", costInAcctVouch.getAmount());
		
		return "hrp/cost/costinacctvouch/costInAcctVouchUpdate";
	}
	/**
	*科室收入总账<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costinacctvouch/updateCostInAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostInAcctVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costInAcctVouchJson = costInAcctVouchService.updateCostInAcctVouch(mapVo);
		
		return JSONObject.parseObject(costInAcctVouchJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costinacctvouch/costInAcctVouchImportPage", method = RequestMethod.GET)
	public String costInAcctVouchImportPage(Model mode) throws Exception {

		return "hrp/cost/costinacctvouch/costInAcctVouchImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costinacctvouch/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","科室收入总账采集 .xls");
	    return null; 
	 }
	 
	/**
	*科室收入总账<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costinacctvouch/readCostInAcctVouchFiles",method = RequestMethod.POST)  
    public String readCostInAcctVouchFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<CostInAcctVouch> list_err = new ArrayList<CostInAcctVouch>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
				CostInAcctVouch costInAcctVouch = new CostInAcctVouch();
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				
						mapVo.put("group_id", SessionManager.getGroupId());
				
						mapVo.put("hos_id", SessionManager.getHosId());

						mapVo.put("copy_code", SessionManager.getCopyCode());



						
						if (StringUtils.isNotEmpty(temp[0])) {
							mapVo.put("income_item_id", temp[0]);
							CostIncomeItemArrt incomeItemArrt = costIncomeItemArrtService.queryCostIncomeItemArrtByCode(mapVo);
							if(incomeItemArrt == null){
								costInAcctVouch.setIncome_item_id(Long.valueOf((temp[0])));
								costInAcctVouch.setIncome_item_name(temp[1]);
								err_sb.append("收入项目不存在  ");
							}
						}
						if (StringUtils.isNotEmpty(temp[2])) {
							costInAcctVouch.setYear_month(temp[2]);
							mapVo.put("year_month", temp[2]);
						} else {
							err_sb.append("统计年月为空  ");
						}
						
						if (StringUtils.isNotEmpty(temp[3])) {
							mapVo.put("dept_no", temp[3]);
							DeptDict deptDict = deptDictService.queryDeptDictByCode(mapVo);
							if(deptDict != null){
								mapVo.put("dept_id", deptDict.getDept_id());
								mapVo.put("dept_no", deptDict.getDept_no());
							}else{
								costInAcctVouch.setDept_no(Long.valueOf(temp[3]));
								costInAcctVouch.setDept_name(temp[4]);
								err_sb.append("科室不存在  ");
							}
						} else {
							err_sb.append("科室编码为空  ");
						}
						if (StringUtils.isNotEmpty(temp[5])) {
							mapVo.put("amount", Double.valueOf(temp[5]));
						} else {
							costInAcctVouch.setAmount(Double.valueOf(temp[5]));
							err_sb.append("金额为空  ");
						}
					
				CostInAcctVouch data_exc_extis = costInAcctVouchService.queryCostInAcctVouchByCode(mapVo);
				if (data_exc_extis != null) {
					costInAcctVouch.setIncome_item_id(Long.valueOf((temp[0])));
					costInAcctVouch.setIncome_item_name(temp[1]);
					costInAcctVouch.setYear_month(temp[2]);
					costInAcctVouch.setDept_no(Long.valueOf(temp[3]));
					costInAcctVouch.setDept_name(temp[4]);
					costInAcctVouch.setAmount(Double.valueOf(temp[5]));
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					costInAcctVouch.setError_type(err_sb.toString());
					list_err.add(costInAcctVouch);
				} else {
					String dataJson = costInAcctVouchService.addCostInAcctVouch(mapVo);
				}
			}
		} catch (DataAccessException e) {
			CostInAcctVouch data_exc = new CostInAcctVouch();
			data_exc.setError_type("导入系统出错");
			list_err.add(data_exc);
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
    }  
	/**
	*科室收入总账<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costinacctvouch/addBatchCostInAcctVouch", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostInAcctVouch(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<CostInAcctVouch> list_err = new ArrayList<CostInAcctVouch>();
		JSONArray json = JSONArray.parseArray(paramVo);
		Map<String, Object> mapVo = new HashMap<String, Object>();
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
		String s = null;
		Iterator it = json.iterator();
		try {
			while (it.hasNext()) {
			StringBuffer err_sb = new StringBuffer();
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			mapVo.put("income_item_code", jsonObj.get("income_item_code"));
			mapVo.put("income_item_name", jsonObj.get("income_item_name"));
			CostIncomeItemArrt incomeItemArrt = costIncomeItemArrtService.queryCostIncomeItemArrtByCode(mapVo);
			if(incomeItemArrt != null){
				mapVo.put("income_item_id", incomeItemArrt.getIncome_item_id());
			}else{
				err_sb.append("收入项目存在  ");
			}
			
			
			mapVo.put("year_month", jsonObj.get("year_month"));
			mapVo.put("dept_code", jsonObj.get("dept_id"));
			mapVo.put("dept_name", jsonObj.get("dept_name"));
			DeptDict deptDict = deptDictService.queryDeptDictByCode(mapVo);
			if(deptDict != null){
				mapVo.put("dept_id", deptDict.getDept_id());
				mapVo.put("dept_no", deptDict.getDept_no());
			}else{
				err_sb.append("科室不存在  ");
			}
			mapVo.put("amount", jsonObj.get("amount"));
				CostInAcctVouch data_exc_extis = costInAcctVouchService.queryCostInAcctVouchByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				CostInAcctVouch costInAcctVouch = new CostInAcctVouch();
				if (err_sb.toString().length() > 0) {
					costInAcctVouch.setIncome_item_code(mapVo.get("income_item_code").toString());
					costInAcctVouch.setIncome_item_name(mapVo.get("income_item_name").toString());
					costInAcctVouch.setYear_month(mapVo.get("year_month").toString());
					costInAcctVouch.setDept_code(mapVo.get("dept_code").toString());
					costInAcctVouch.setDept_name(mapVo.get("dept_name").toString());
					costInAcctVouch.setAmount(Double.valueOf(mapVo.get("amount").toString()));
					costInAcctVouch.setError_type(err_sb.toString());
					list_err.add(costInAcctVouch);
				} else {
					costInAcctVouchService.addCostInAcctVouch(mapVo);
				}
			}
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
    }
}

