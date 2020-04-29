/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationplan.downtoup.plan;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgWorkEditPlan;
import com.chd.hrp.budg.service.business.compilationplan.plan.BudgWorkEditPlanService;
import com.github.abel533.echarts.json.GsonUtil;
/**
 * 
 * @Description:
 * BUDG_WORK_EDIT_PLAN
 * @Table:
 * BUDG_WORK_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDeptMonthPlanControllerDown extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDeptMonthPlanControllerDown.class);
	
	//引入Service服务
	@Resource(name = "budgWorkEditPlanService")
	private final BudgWorkEditPlanService budgWorkEditPlanService = null;
   
	/**
	 * @Description 
	 * 根据业务预算编制模式以及预算编制层次，查询方案内置BUDG_BUILT_IN表  “是否独立核算”，“分解或汇总”，“分解层次”，“汇总层次”的值
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/queryBudgBuiltIn", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgBuiltIn(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		//查询 方案内置BUDG_BUILT_IN表
		String str  = budgWorkEditPlanService.queryBudgBuiltIn(mapVo) ;

		return str;

	}
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/budgDeptMonthPlanMainPage", method = RequestMethod.GET)
	public String budgDeptMonthPlanMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/deptmonthplan/budgDeptMonthPlanMain";

	}
	
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/budgDeptMonthPlanAddPage", method = RequestMethod.GET)
	public String budgDeptMonthPlanAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("budg_year", mapVo.get("budg_year"));
		
		return "hrp/budg/business/compilationplan/downtoup/deptmonthplan/budgDeptMonthPlanAdd";

	}

	/**
	 * @Description 
	 * 添加数据 BUDG_WORK_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/addBudgDeptMonthPlanDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDeptMonthPlanDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
       
		String budgDeptMonthPlanJson =  null ;
		
		try {
			
			budgDeptMonthPlanJson = budgWorkEditPlanService.add(mapVo);
			
		} catch (Exception e) {
			
			budgDeptMonthPlanJson = e.getMessage() ;
			
		}
		
		return JSONObject.parseObject(budgDeptMonthPlanJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 BUDG_WORK_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/budgDeptMonthPlanUpdatePage", method = RequestMethod.GET)
	public String budgDeptMonthPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		BudgWorkEditPlan budgWorkEditPlan = new BudgWorkEditPlan();
    
		budgWorkEditPlan = budgWorkEditPlanService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkEditPlan.getGroup_id());
		mode.addAttribute("hos_id", budgWorkEditPlan.getHos_id());
		mode.addAttribute("copy_code", budgWorkEditPlan.getCopy_code());
		mode.addAttribute("budg_year", budgWorkEditPlan.getBudg_year());
		mode.addAttribute("budg_level", budgWorkEditPlan.getBudg_level());
		mode.addAttribute("index_code", budgWorkEditPlan.getIndex_code());
		mode.addAttribute("is_single_count", budgWorkEditPlan.getIs_single_count());
		mode.addAttribute("edit_method", budgWorkEditPlan.getEdit_method());
		mode.addAttribute("get_way", budgWorkEditPlan.getGet_way());
		mode.addAttribute("formula_id", budgWorkEditPlan.getFormula_id());
		mode.addAttribute("fun_id", budgWorkEditPlan.getFun_id());
		mode.addAttribute("resolve_or_sum", budgWorkEditPlan.getResolve_or_sum());
		mode.addAttribute("resolve_way", budgWorkEditPlan.getResolve_way());
		mode.addAttribute("resolve_level", budgWorkEditPlan.getResolve_level());
		mode.addAttribute("sum_level", budgWorkEditPlan.getSum_level());
		mode.addAttribute("reference_years", budgWorkEditPlan.getReference_years());
		mode.addAttribute("resolve_data", budgWorkEditPlan.getResolve_data());
		
		return "hrp/budg/business/compilationplan/downtoup/deptmonthplan/budgDeptMonthPlanUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 BUDG_WORK_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/updateBudgDeptMonthPlanDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDeptMonthPlanDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
		
		String budgDeptMonthPlanJson = null ; 
		
		try {
			
			budgDeptMonthPlanJson = budgWorkEditPlanService.update(mapVo);
					
		} catch (Exception e) {
			
			budgDeptMonthPlanJson = e.getMessage() ;
			
		}
		
		return JSONObject.parseObject(budgDeptMonthPlanJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 BUDG_WORK_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/addOrUpdateBudgDeptMonthPlanDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDeptMonthPlanDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptMonthPlanJson ="";
		

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
	  
		budgDeptMonthPlanJson = budgWorkEditPlanService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptMonthPlanJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 BUDG_WORK_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/deleteBudgDeptMonthPlanDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDeptMonthPlanDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("budg_year", ids[3])   ;
				mapVo.put("budg_level", ids[4])   ;
				mapVo.put("index_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgDeptMonthPlanJson = null ; 
		
		try {
			
			budgDeptMonthPlanJson = budgWorkEditPlanService.deleteBatch(listVo);
					
		} catch (Exception e) {
			
			budgDeptMonthPlanJson = e.getMessage() ;
			
		}
		
			
	  return JSONObject.parseObject(budgDeptMonthPlanJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 BUDG_WORK_EDIT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/queryBudgDeptMonthPlanDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDeptMonthPlanDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgHosYearPlan = budgWorkEditPlanService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosYearPlan);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 BUDG_WORK_EDIT_PLAN
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/budgDeptMonthPlanImportPage", method = RequestMethod.GET)
	public String budgDeptMonthPlanImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/deptmonthplan/budgDeptMonthPlanImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 BUDG_WORK_EDIT_PLAN
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationplan/downtoup/deptmonthplan/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","BUDG_WORK_EDIT_PLAN.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 BUDG_WORK_EDIT_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationplan/downtoup/deptmonthplan/readBudgDeptMonthPlanFiles",method = RequestMethod.POST)  
    public String readBudgDeptMonthPlanFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgWorkEditPlan> list_err = new ArrayList<BudgWorkEditPlan>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgWorkEditPlan budgHosYearPlan = new BudgWorkEditPlan();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					budgHosYearPlan.setBudg_year(temp[3]);
		    		mapVo.put("budg_year", temp[3]);
					
					} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					budgHosYearPlan.setBudg_level(temp[4]);
		    		mapVo.put("budg_level", temp[4]);
					
					} else {
						
						err_sb.append("预算层次为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					budgHosYearPlan.setIndex_code(temp[5]);
		    		mapVo.put("index_code", temp[5]);
					
					} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					budgHosYearPlan.setIs_single_count(Integer.valueOf(temp[6]));
		    		mapVo.put("is_single_count", temp[6]);
					
					} else {
						
						err_sb.append("是否独立核算为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					budgHosYearPlan.setEdit_method(temp[7]);
		    		mapVo.put("edit_method", temp[7]);
					
					} else {
						
						err_sb.append("编制方法为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					budgHosYearPlan.setGet_way(temp[8]);
		    		mapVo.put("get_way", temp[8]);
					
					} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					budgHosYearPlan.setFormula_id(temp[9]);
		    		mapVo.put("formula_id", temp[9]);
					
					} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					budgHosYearPlan.setFun_id(temp[10]);
		    		mapVo.put("fun_id", temp[10]);
					
					} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					budgHosYearPlan.setResolve_or_sum(temp[11]);
		    		mapVo.put("resolve_or_sum", temp[11]);
					
					} else {
						
						err_sb.append("分解或汇总为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					budgHosYearPlan.setResolve_way(temp[12]);
		    		mapVo.put("resolve_way", temp[12]);
					
					} else {
						
						err_sb.append("分解方法为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					budgHosYearPlan.setResolve_level(temp[13]);
		    		mapVo.put("resolve_level", temp[13]);
					
					} else {
						
						err_sb.append("分解层次为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					budgHosYearPlan.setSum_level(temp[14]);
		    		mapVo.put("sum_level", temp[14]);
					
					} else {
						
						err_sb.append("汇总层次为空  ");
						
					}
					 
					
				BudgWorkEditPlan data_exc_extis = budgWorkEditPlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosYearPlan.setError_type(err_sb.toString());
					
					list_err.add(budgHosYearPlan);
					
				} else {
			  
					String dataJson = budgWorkEditPlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkEditPlan data_exc = new BudgWorkEditPlan();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 BUDG_WORK_EDIT_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptmonthplan/addBatchBudgDeptMonthPlan", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDeptMonthPlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkEditPlan> list_err = new ArrayList<BudgWorkEditPlan>();
		
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
			
			BudgWorkEditPlan budgHosYearPlan = new BudgWorkEditPlan();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgHosYearPlan.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_level"))) {
						
					budgHosYearPlan.setBudg_level((String)jsonObj.get("budg_level"));
		    		mapVo.put("budg_level", jsonObj.get("budg_level"));
		    		} else {
						
						err_sb.append("预算层次为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgHosYearPlan.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_single_count"))) {
						
					budgHosYearPlan.setIs_single_count(Integer.valueOf((String)jsonObj.get("is_single_count")));
		    		mapVo.put("is_single_count", jsonObj.get("is_single_count"));
		    		} else {
						
						err_sb.append("是否独立核算为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("edit_method"))) {
						
					budgHosYearPlan.setEdit_method((String)jsonObj.get("edit_method"));
		    		mapVo.put("edit_method", jsonObj.get("edit_method"));
		    		} else {
						
						err_sb.append("编制方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("get_way"))) {
						
					budgHosYearPlan.setGet_way((String)jsonObj.get("get_way"));
		    		mapVo.put("get_way", jsonObj.get("get_way"));
		    		} else {
						
						err_sb.append("取值方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("formula_id"))) {
						
					budgHosYearPlan.setFormula_id((String)jsonObj.get("formula_id"));
		    		mapVo.put("formula_id", jsonObj.get("formula_id"));
		    		} else {
						
						err_sb.append("计算公式ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fun_id"))) {
						
					budgHosYearPlan.setFun_id((String)jsonObj.get("fun_id"));
		    		mapVo.put("fun_id", jsonObj.get("fun_id"));
		    		} else {
						
						err_sb.append("取值函数ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_or_sum"))) {
						
					budgHosYearPlan.setResolve_or_sum((String)jsonObj.get("resolve_or_sum"));
		    		mapVo.put("resolve_or_sum", jsonObj.get("resolve_or_sum"));
		    		} else {
						
						err_sb.append("分解或汇总为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_way"))) {
						
					budgHosYearPlan.setResolve_way((String)jsonObj.get("resolve_way"));
		    		mapVo.put("resolve_way", jsonObj.get("resolve_way"));
		    		} else {
						
						err_sb.append("分解方法为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_level"))) {
						
					budgHosYearPlan.setResolve_level((String)jsonObj.get("resolve_level"));
		    		mapVo.put("resolve_level", jsonObj.get("resolve_level"));
		    		} else {
						
						err_sb.append("分解层次为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("sum_level"))) {
						
					budgHosYearPlan.setSum_level((String)jsonObj.get("sum_level"));
		    		mapVo.put("sum_level", jsonObj.get("sum_level"));
		    		} else {
						
						err_sb.append("汇总层次为空  ");
						
					}
					
					
				BudgWorkEditPlan data_exc_extis = budgWorkEditPlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosYearPlan.setError_type(err_sb.toString());
					
					list_err.add(budgHosYearPlan);
					
				} else {
			  
					String dataJson = budgWorkEditPlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkEditPlan data_exc = new BudgWorkEditPlan();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

