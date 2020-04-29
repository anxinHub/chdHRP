/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.cost.entity.CostMonthEnd;
import com.chd.hrp.cost.serviceImpl.CostMonthEndServiceImpl;
import com.chd.hrp.mat.dao.base.MatCommonMapper;

/**
* @Title. @Description.
* 科室成本核算月结表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostMonthEndController extends BaseController{
	private static Logger logger = Logger.getLogger(CostMonthEndController.class);
	
	
	@Resource(name = "costMonthEndService")
	private final CostMonthEndServiceImpl costMonthEndService = null;
   
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
   
	/**
	*科室成本核算月结表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costmonthend/costMonthEndMainPage", method = RequestMethod.GET)
	public String costMonthEndMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> costCur = costMonthEndService.queryCostCurYearMonth(mapVo);
		
		mode.addAttribute("year", costCur.get("acc_year"));
		
		mode.addAttribute("month", costCur.get("acc_month"));
		
		     //获取上个物流期间
				Integer acc_year=Integer.parseInt(costCur.get("acc_year").toString());
				Integer acc_month=Integer.parseInt(costCur.get("acc_month").toString());
				
				Integer last_year=acc_year;
				Integer last_month=acc_month;
				
				if(acc_month==1){
					
					last_month=12;
					
					last_year=acc_year-1;
					
				}else{
					
					last_month=acc_month-1;
					
					last_year=acc_year;
				}
				//判断下一期间是否存在
				Map<String, Object> existsMap = new HashMap<String, Object>();
				
				existsMap.put("group_id", SessionManager.getGroupId());
				
				existsMap.put("hos_id", SessionManager.getHosId());
				
				existsMap.put("copy_code", SessionManager.getCopyCode());
				
				existsMap.put("year", last_year);
				
				existsMap.put("month", (last_month.toString().length()==1)?('0'+last_month.toString()):last_month.toString());
		/* 
		 * 
		 * 引用物流 判断日期是否在会计期间
		 * 
		 * */
				int flag = matCommonMapper.existsAccYearMonthCheck(existsMap);
				
				if(flag == 0){
					mode.addAttribute("last_year", "");
					mode.addAttribute("last_month","");	
				}else{
					mode.addAttribute("last_year", last_year.toString());
					mode.addAttribute("last_month", (last_month.toString().length()==1)?('0'+last_month.toString()):last_month.toString());	
				}
				
		return "hrp/cost/costmonthend/costMonthEndMain";

	}
	/**
	*科室成本核算月结表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costmonthend/costMonthEndAddPage", method = RequestMethod.GET)
	public String costMonthEndAddPage(Model mode) throws Exception {
		
			Map<String, Object> mapVo = new HashMap<String, Object>();
		
			if(mapVo.get("group_id") == null){
				
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
			
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
	        CostMonthEnd costMonthEnd  = costMonthEndService.queryCostMonthEndByCode(mapVo);
			
			if(costMonthEnd != null){
				
				mode.addAttribute("group_id", costMonthEnd.getGroup_id());
				
				mode.addAttribute("hos_id", costMonthEnd.getHos_id());
				
				mode.addAttribute("copy_code", costMonthEnd.getCopy_code());
				
				mode.addAttribute("acc_year", costMonthEnd.getAcc_year());
				
				mode.addAttribute("acc_month", costMonthEnd.getAcc_month());
				
				mode.addAttribute("is_end", costMonthEnd.getIs_end());
				
			}

		return "hrp/cost/costmonthend/costMonthEndAdd";

	}
	/**
	*科室成本核算月结表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costmonthend/addCostMonthEnd", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostMonthEnd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costMonthEndJson = costMonthEndService.addCostMonthEnd(mapVo);

		return JSONObject.parseObject(costMonthEndJson);
		
	}
	/**
	 *生成分析报表<BR>
	 *保存
	 */
	@RequestMapping(value = "/hrp/cost/costmonthend/saveCostAnalysisProc", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> saveCostAnalysisProc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costMonthEndJson = costMonthEndService.saveCostAnalysisProc(mapVo);
		
		return JSONObject.parseObject(costMonthEndJson);
		
	}
	/**
	*科室成本核算月结表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costmonthend/queryCostMonthEnd", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostMonthEnd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costMonthEnd = costMonthEndService.queryCostMonthEnd(getPage(mapVo));

		return JSONObject.parseObject(costMonthEnd);
		
	}
	/**
	*科室成本核算月结表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costmonthend/deleteCostMonthEnd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostMonthEnd(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("acc_year",ids[3]);   
			mapVo.put("acc_month",ids[4]); 
            listVo.add(mapVo);
        }
		String costMonthEndJson = costMonthEndService.deleteBatchCostMonthEnd(listVo);
	   return JSONObject.parseObject(costMonthEndJson);
			
	}
	
	/**
	*科室成本核算月结表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costmonthend/costMonthEndUpdatePage", method = RequestMethod.GET)
	
	public String costMonthEndUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostMonthEnd costMonthEnd = new CostMonthEnd();
		costMonthEnd = costMonthEndService.queryCostMonthEndByCode(mapVo);
		mode.addAttribute("group_id", costMonthEnd.getGroup_id());
		mode.addAttribute("hos_id", costMonthEnd.getHos_id());
		mode.addAttribute("copy_code", costMonthEnd.getCopy_code());
		mode.addAttribute("acc_year", costMonthEnd.getAcc_year());
		mode.addAttribute("acc_month", costMonthEnd.getAcc_month());
		mode.addAttribute("is_end", costMonthEnd.getIs_end());
		return "hrp/cost/costmonthend/costMonthEndUpdate";
	}
	/**
	*科室成本核算月结表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costmonthend/updateCostMonthEnd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostMonthEnd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costMonthEndJson = costMonthEndService.updateCostMonthEnd(mapVo);
		
		return JSONObject.parseObject(costMonthEndJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costmonthend/costMonthEndImportPage", method = RequestMethod.GET)
	public String costMonthEndImportPage(Model mode) throws Exception {

		return "hrp/cost/costmonthend/costMonthEndImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costmonthend/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\对应目录","对应字典.xls");
	    return null; 
	 }
	 
	/**
	*科室成本核算月结表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costmonthend/readCostMonthEndFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
    	List<CostMonthEnd> list2 = new ArrayList<CostMonthEnd> ();  
    	List<String[]> list = UploadUtil.readFile(plupload, request, response);
    	List<CostMonthEnd> errorList = new ArrayList<CostMonthEnd>();
		for (int i = 1; i < list.size(); i++) {
			String temp[] = list.get(i);//行
			Map<String, Object> mapVo = new HashMap<String, Object>();
			//模版生成,根据不同字典处理业务
			if("".equals(temp[0]) || "".equals(temp[1])){
				CostMonthEnd costMonthEnd = new CostMonthEnd();
				
					costMonthEnd.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					costMonthEnd.setHos_id(Long.getLong(SessionManager.getHosId()));
					costMonthEnd.setCopy_code(SessionManager.getCopyCode());
					costMonthEnd.setAcc_year(temp[3]);
					costMonthEnd.setAcc_month(temp[4]);
					costMonthEnd.setIs_end(Integer.getInteger(temp[5]));
				costMonthEnd.setError_type("数据表列存在空的数据列");
				errorList.add(costMonthEnd);
			}else{
				//需要转换各别列 通过SessionManager 里面获取
					mapVo.put("group_id", SessionManager.getGroupId());
				
					mapVo.put("hos_id", SessionManager.getHosId());
				
					mapVo.put("copy_code", SessionManager.getCopyCode());
				
					mapVo.put("acc_year",temp[3]);
				
					mapVo.put("acc_month",temp[4]);
				
					mapVo.put("is_end",temp[5]);
				
			}
			try {
				CostMonthEnd data = new CostMonthEnd();
				data = costMonthEndService.queryCostMonthEndByCode(mapVo);
					if(data != null){
					data.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					data.setHos_id(Long.getLong(SessionManager.getHosId()));
					data.setCopy_code(SessionManager.getCopyCode());
					data.setAcc_year(temp[3]);
					data.setAcc_month(temp[4]);
					data.setIs_end(Integer.getInteger(temp[5]));
					data.setError_type("数据在数据库中已存在");
					errorList.add(data);
					}else{
							if(i==list.size()-1){
							String dataJson = costMonthEndService.addCostMonthEnd(mapVo);
							JSONObject json = JSONObject.parseObject(dataJson);
							String flag = String.valueOf(json.get("state"));
							if(!flag.equals("true")){
								CostMonthEnd data_error = new CostMonthEnd();
								data_error.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_error.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_error.setCopy_code(SessionManager.getCopyCode());
								data_error.setAcc_year(temp[3]);
								data_error.setAcc_month(temp[4]);
								data_error.setIs_end(Integer.getInteger(temp[5]));
								data_error.setError_type("导入失败");
								errorList.add(data_error);
								}
							}
					}
			} catch (DataAccessException e) {
				CostMonthEnd data_exc = new CostMonthEnd();
								data_exc.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_exc.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_exc.setCopy_code(SessionManager.getCopyCode());
								data_exc.setAcc_year(temp[3]);
								data_exc.setAcc_month(temp[4]);
								data_exc.setIs_end(Integer.getInteger(temp[5]));
				data_exc.setError_type("导入系统出错");
				errorList.add(data_exc);
			}
		}
		list2.addAll(errorList);
		mode.addAttribute("resultsJson",  ChdJson.toJson(list2, list2.size()));
	    	return "hrp/cost/costmonthend/costmonthendImportMessage";
    }  
	/**
	*科室成本核算月结表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costmonthend/addBatchCostMonthEnd", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostMonthEnd(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("acc_year",ids[3]);   
			mapVo.put("acc_month",ids[4]);   
			mapVo.put("is_end",ids[5]); 
            listVo.add(mapVo);
        }
		String costMonthEndJson = costMonthEndService.addBatchCostMonthEnd(listVo);
	    return JSONObject.parseObject(costMonthEndJson);
    }
	
	
	
	
	/**
	 * @Description 
	 * 成本月结查询
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costmonthend/queryCostYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostYearMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acc_year", SessionManager.getAcctYear());
        
		}
		String costYearMonth = costMonthEndService.queryCostYearMonth(getPage(mapVo));

		return JSONObject.parseObject(costYearMonth);
	}
	
	
	@RequestMapping(value = "/hrp/cost/costmonthend/updateCostFinalCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostFinalCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("cost_flag", "1");
		
		mapVo.put("cost_user", SessionManager.getUserId());
		
		mapVo.put("cost_date", new Date());
		
		String costFinalChargeJson=costMonthEndService.updateCostFinalCharge(mapVo);
		
		return JSONObject.parseObject(costFinalChargeJson);
	}
	
	
	@RequestMapping(value = "/hrp/cost/costmonthend/updateCostFinalUnCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostFinalUnCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String costFinalUnChargeJson=costMonthEndService.updateCostFinalUnCharge(mapVo);
		
		return JSONObject.parseObject(costFinalUnChargeJson);
	}
}

