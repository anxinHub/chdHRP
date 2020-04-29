/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.toptodown.plan;
import java.io.IOException;
import java.util.*;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgMedIncomeEditPlan;
import com.chd.hrp.budg.service.toptodown.plan.BudgHosYearIncomePlanService;
/**
 * 
 * @Description:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Table:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgHosYearIncomePlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgHosYearIncomePlanController.class);
	
	//引入Service服务
	@Resource(name = "budgHosYearIncomePlanService")
	private final BudgHosYearIncomePlanService budgHosYearIncomePlanService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanMainPage", method = RequestMethod.GET)
	public String budgHosYearIncomePlanMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanAddPage", method = RequestMethod.GET)
	public String budgHosYearIncomePlanAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("budg_year", mapVo.get("budg_year")) ;
		
		return "hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanAdd";

	}

	/**
	 * @Description 
	 * 添加数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/addBudgHosYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgHosYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String budgHosYearIncomePlanJson = null;
		
		try{
			
			budgHosYearIncomePlanJson = budgHosYearIncomePlanService.add(mapVo);
			
		} catch (Exception e) {

			budgHosYearIncomePlanJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgHosYearIncomePlanJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanUpdatePage", method = RequestMethod.GET)
	public String budgHosYearIncomePlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		BudgMedIncomeEditPlan budgHosYearIncomePlan = new BudgMedIncomeEditPlan();
    
		budgHosYearIncomePlan = budgHosYearIncomePlanService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgHosYearIncomePlan.getGroup_id());
		mode.addAttribute("hos_id", budgHosYearIncomePlan.getHos_id());
		mode.addAttribute("copy_code", budgHosYearIncomePlan.getCopy_code());
		mode.addAttribute("budg_year", budgHosYearIncomePlan.getBudg_year());
		mode.addAttribute("subj_code", budgHosYearIncomePlan.getSubj_code());
		mode.addAttribute("is_single_count", budgHosYearIncomePlan.getIs_single_count());
		mode.addAttribute("edit_method", budgHosYearIncomePlan.getEdit_method());
		mode.addAttribute("get_way", budgHosYearIncomePlan.getGet_way());
		mode.addAttribute("formula_id", budgHosYearIncomePlan.getFormula_id());
		mode.addAttribute("fun_id", budgHosYearIncomePlan.getFun_id());
		mode.addAttribute("resolve_or_sum", budgHosYearIncomePlan.getResolve_or_sum());
		mode.addAttribute("resolve_way", budgHosYearIncomePlan.getResolve_way());
		
		return "hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/updateBudgHosYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgHosYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
       
		String budgHosYearIncomePlanJson =  null ;
		
		try {
			
			budgHosYearIncomePlanJson = budgHosYearIncomePlanService.update(mapVo);
			
		} catch (Exception e) {

			budgHosYearIncomePlanJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(budgHosYearIncomePlanJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/addOrUpdateBudgHosYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgHosYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgHosYearIncomePlanJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		budgHosYearIncomePlanJson = budgHosYearIncomePlanService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgHosYearIncomePlanJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/deleteBudgHosYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgHosYearIncomePlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("subj_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgHosYearIncomePlanJson = null ;
		
		try {
			
			budgHosYearIncomePlanJson = budgHosYearIncomePlanService.deleteBatch(listVo);
			
		} catch (Exception e) {

			budgHosYearIncomePlanJson = e.getMessage() ;
		}
		
			
	  return JSONObject.parseObject(budgHosYearIncomePlanJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/queryBudgHosYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgHosYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgHosYearIncomePlan = budgHosYearIncomePlanService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosYearIncomePlan);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanImportPage", method = RequestMethod.GET)
	public String budgHosYearIncomePlanImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/plan/hosyearincome/budgHosYearIncomePlanImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/plan/hosyearincome/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","BUDG_MED_INCOME_EDIT_PLAN.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/plan/hosyearincome/readBudgHosYearIncomePlanFiles",method = RequestMethod.POST)  
    public String readBudgHosYearIncomePlanFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedIncomeEditPlan> list_err = new ArrayList<BudgMedIncomeEditPlan>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedIncomeEditPlan budgHosYearIncomePlan = new BudgMedIncomeEditPlan();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgHosYearIncomePlan.setBudg_year(temp[3]);
		    		mapVo.put("budg_year", temp[3]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgHosYearIncomePlan.setSubj_code(temp[4]);
		    		mapVo.put("subj_code", temp[4]);
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgHosYearIncomePlan.setIs_single_count(Integer.valueOf(temp[5]));
		    		mapVo.put("is_single_count", temp[5]);
					
					} else {
						
						err_sb.append("是否独立核算为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgHosYearIncomePlan.setEdit_method(temp[6]);
		    		mapVo.put("edit_method", temp[6]);
					
					} else {
						
						err_sb.append("编制方法为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgHosYearIncomePlan.setGet_way(temp[7]);
		    		mapVo.put("get_way", temp[7]);
					
					} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgHosYearIncomePlan.setFormula_id(temp[8]);
		    		mapVo.put("formula_id", temp[8]);
					
					} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgHosYearIncomePlan.setFun_id(temp[9]);
		    		mapVo.put("fun_id", temp[9]);
					
					} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgHosYearIncomePlan.setResolve_or_sum(temp[10]);
		    		mapVo.put("resolve_or_sum", temp[10]);
					
					} else {
						
						err_sb.append("分解或汇总为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgHosYearIncomePlan.setResolve_way(temp[11]);
		    		mapVo.put("resolve_way", temp[11]);
					
					} else {
						
						err_sb.append("分解方法为空  ");
						
					}
					 
					
					BudgMedIncomeEditPlan data_exc_extis = budgHosYearIncomePlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosYearIncomePlan.setError_type(err_sb.toString());
					
					list_err.add(budgHosYearIncomePlan);
					
				} else {
			  
					String dataJson = budgHosYearIncomePlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeEditPlan data_exc = new BudgMedIncomeEditPlan();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/addBatchBudgHosYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgHosYearIncomePlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeEditPlan> list_err = new ArrayList<BudgMedIncomeEditPlan>();
		
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
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			BudgMedIncomeEditPlan budgHosYearIncomePlan = new BudgMedIncomeEditPlan();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgHosYearIncomePlan.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgHosYearIncomePlan.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_single_count"))) {
						
					budgHosYearIncomePlan.setIs_single_count(Integer.valueOf((String)jsonObj.get("is_single_count")));
		    		mapVo.put("is_single_count", jsonObj.get("is_single_count"));
		    		} else {
						
						err_sb.append("是否独立核算为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("edit_method"))) {
						
					budgHosYearIncomePlan.setEdit_method((String)jsonObj.get("edit_method"));
		    		mapVo.put("edit_method", jsonObj.get("edit_method"));
		    		} else {
						
						err_sb.append("编制方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("get_way"))) {
						
					budgHosYearIncomePlan.setGet_way((String)jsonObj.get("get_way"));
		    		mapVo.put("get_way", jsonObj.get("get_way"));
		    		} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("formula_id"))) {
						
					budgHosYearIncomePlan.setFormula_id((String)jsonObj.get("formula_id"));
		    		mapVo.put("formula_id", jsonObj.get("formula_id"));
		    		} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_id"))) {
						
					budgHosYearIncomePlan.setFun_id((String)jsonObj.get("fun_id"));
		    		mapVo.put("fun_id", jsonObj.get("fun_id"));
		    		} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_or_sum"))) {
						
					budgHosYearIncomePlan.setResolve_or_sum((String)jsonObj.get("resolve_or_sum"));
		    		mapVo.put("resolve_or_sum", jsonObj.get("resolve_or_sum"));
		    		} else {
						
						err_sb.append("分解或汇总为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_way"))) {
						
					budgHosYearIncomePlan.setResolve_way((String)jsonObj.get("resolve_way"));
		    		mapVo.put("resolve_way", jsonObj.get("resolve_way"));
		    		} else {
						
						err_sb.append("分解方法为空  ");
						
					}
					
					
					BudgMedIncomeEditPlan data_exc_extis = budgHosYearIncomePlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosYearIncomePlan.setError_type(err_sb.toString());
					
					list_err.add(budgHosYearIncomePlan);
					
				} else {
			  
					String dataJson = budgHosYearIncomePlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeEditPlan data_exc = new BudgMedIncomeEditPlan();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
	/**
	 * @Description 
	 * 生成数据 BUDG_MED_INCOME_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosyearincome/generateBudgHosYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateBudgHosYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgHosYearIncomePlan = budgHosYearIncomePlanService.generateBudgHosYearIncomePlan(mapVo);

		return JSONObject.parseObject(budgHosYearIncomePlan);
		
	}
	
}

