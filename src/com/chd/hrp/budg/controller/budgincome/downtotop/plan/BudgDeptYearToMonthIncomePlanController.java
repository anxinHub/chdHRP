/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.downtotop.plan;
import java.io.IOException;
import java.util.*;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgMedIncomeMonthPlan;
import com.chd.hrp.budg.service.toptodown.plan.BudgDeptYearMonthIncomePlanService;
/**
 * 
 * @Description:
 * BUDG_MED_INCOME_MONTH_PLAN
 * @Table:
 * BUDG_MED_INCOME_MONTH_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDeptYearToMonthIncomePlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDeptYearToMonthIncomePlanController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptYearMonthIncomePlanService")
	private final BudgDeptYearMonthIncomePlanService budgDeptYearMonthIncomePlanService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/deptMonthPlanPage", method = RequestMethod.GET)
	public String budgDeptYearMonthIncomePlanMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/plan/deptmonthplan/budgDeptYearToMonthIncomePlanMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/budgDeptYearToMonthIncomePlanAddPage", method = RequestMethod.GET)
	public String budgDeptYearMonthIncomePlanAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/plan/deptmonthplan/budgDeptYearToMonthIncomePlanAdd";

	}
	
	/**
	 * @Description 
	 * 批量设置 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/budgDeptYearToMonthIncomePlanSetPage", method = RequestMethod.GET)
	public String budgDeptYearToMonthIncomePlanSetPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/plan/deptmonthplan/budgDeptYearToMonthIncomePlanSet";

	}
	
	/**
	 * @Description 
	 * 添加数据 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/addBudgDeptYearToMonthIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDeptYearMonthIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
    	mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
       
		String budgDeptYearMonthIncomePlanJson = budgDeptYearMonthIncomePlanService.add(mapVo);

		return JSONObject.parseObject(budgDeptYearMonthIncomePlanJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/budgDeptYearToMonthIncomePlanUpdatePage", method = RequestMethod.GET)
	public String budgDeptYearMonthIncomePlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		BudgMedIncomeMonthPlan budgDeptYearMonthIncomePlan = new BudgMedIncomeMonthPlan();
    
		budgDeptYearMonthIncomePlan = budgDeptYearMonthIncomePlanService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptYearMonthIncomePlan.getGroup_id());
		mode.addAttribute("hos_id", budgDeptYearMonthIncomePlan.getHos_id());
		mode.addAttribute("copy_code", budgDeptYearMonthIncomePlan.getCopy_code());
		mode.addAttribute("budg_year", budgDeptYearMonthIncomePlan.getBudg_year());
		mode.addAttribute("subj_code", budgDeptYearMonthIncomePlan.getSubj_code());
		mode.addAttribute("resolve_way", budgDeptYearMonthIncomePlan.getResolve_way());
		mode.addAttribute("resolve_data", budgDeptYearMonthIncomePlan.getResolve_data());
		mode.addAttribute("reference_years", budgDeptYearMonthIncomePlan.getReference_years());
		
		return "hrp/budg/budgincome/downtotop/plan/deptmonthplan/budgDeptYearToMonthIncomePlanUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/updateBudgDeptYearToMonthIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDeptYearMonthIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgDeptYearMonthIncomePlanJson = budgDeptYearMonthIncomePlanService.update(mapVo);
		
		return JSONObject.parseObject(budgDeptYearMonthIncomePlanJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/addOrUpdateBudgDeptYearToMonthIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDeptYearMonthIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptYearMonthIncomePlanJson ="";
		

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
	  
		budgDeptYearMonthIncomePlanJson = budgDeptYearMonthIncomePlanService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptYearMonthIncomePlanJson);
	}
	
	/**
	 * @Description 
	 * 批量设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/settingBudgHosDeptYearToMonthIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> settingBudgHosDeptYearIncomePlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("resolve_way", ids[5]);
				mapVo.put("resolve_data", ids[6]);
				if("-1".equals(ids[7])){
					mapVo.put("reference_years", "");
				}else{
					mapVo.put("reference_years", ids[7]);
				}
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgHosDeptYearIncomePlanJson = budgDeptYearMonthIncomePlanService.updateBatch(listVo);
			
	  return JSONObject.parseObject(budgHosDeptYearIncomePlanJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/deleteBudgDeptYearToMonthIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDeptYearMonthIncomePlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgDeptYearMonthIncomePlanJson = budgDeptYearMonthIncomePlanService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDeptYearMonthIncomePlanJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/queryBudgDeptYearToMonthIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDeptYearMonthIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String budgDeptYearMonthIncomePlan = budgDeptYearMonthIncomePlanService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDeptYearMonthIncomePlan);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/budgDeptYearToMonthIncomePlanImportPage", method = RequestMethod.GET)
	public String budgDeptYearMonthIncomePlanImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/plan/deptmonthplan/budgDeptYearToMonthIncomePlanImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/downtotop/plan/deptmonthplan/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown\\plan","科室年度至科室月份医疗收入预算分解方案.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/downtotop/plan/deptmonthplan/readBudgDeptYearToMonthIncomePlanFiles",method = RequestMethod.POST)  
    public String readBudgDeptYearMonthIncomePlanFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedIncomeMonthPlan> list_err = new ArrayList<BudgMedIncomeMonthPlan>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] resolveData = {"01","02","03","04","05"};
		
		
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedIncomeMonthPlan budgMedIncomeMonthPlan = new BudgMedIncomeMonthPlan();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
		    		mapVo.put("hos_id", SessionManager.getHosId());   
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					}
					 
					if (ExcelReader.validExceLColunm(temp, 0)){
						
						budgMedIncomeMonthPlan.setBudg_year(temp[0]);
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
						
						budgMedIncomeMonthPlan.setSubj_code(temp[1]);
			    		mapVo.put("subj_code", temp[1]);
			    		
			    		//校验 医疗收入科目 是否存在
			    		int count = budgDeptYearMonthIncomePlanService.querySubjExist(mapVo) ;
			    		
			    		if(count == 0){
			    			err_sb.append("输入年度收入科目不存在,科目编码输入错误;");
			    		}
					
					} else {
						err_sb.append("科目编码为空;");
					}
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						budgMedIncomeMonthPlan.setSubj_name(temp[2]);
						
			    		mapVo.put("subj_name", temp[2]);
					
					} else {
						err_sb.append("科目名称为空;");
					}
				
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						budgMedIncomeMonthPlan.setResolve_way(temp[3]);
						
						if(Arrays.asList(resolveData).contains(temp[3])){
							mapVo.put("resolve_way", temp[3]);
							
						}else{
							err_sb.append("分解方法输入错误;");
						}
			    		
					
					} else {
						
						err_sb.append("分解方法为空;");
						
					}
					
					int count = budgDeptYearMonthIncomePlanService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在!");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeMonthPlan.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeMonthPlan);
					
				} else {
					
					addList.add(mapVo) ;
				}
				
			}
			
			if(list_err.size() == 0){
				if(addList.size() > 0){
					budgDeptYearMonthIncomePlanService.addBatch(addList);
				}else{
					BudgMedIncomeMonthPlan data_exc = new BudgMedIncomeMonthPlan();
					
					data_exc.setError_type("没有导入数据,导入失败!");
					
					list_err.add(data_exc);
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeMonthPlan data_exc = new BudgMedIncomeMonthPlan();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 BUDG_MED_INCOME_MONTH_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/plan/deptmonthplan/addBatchBudgDeptYearToMonthIncomePlan", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDeptYearMonthIncomePlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeMonthPlan> list_err = new ArrayList<BudgMedIncomeMonthPlan>();
		
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
			
			BudgMedIncomeMonthPlan budgDeptYearMonthIncomePlan = new BudgMedIncomeMonthPlan();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgDeptYearMonthIncomePlan.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgDeptYearMonthIncomePlan.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_way"))) {
						
					budgDeptYearMonthIncomePlan.setResolve_way((String)jsonObj.get("resolve_way"));
		    		mapVo.put("resolve_way", jsonObj.get("resolve_way"));
		    		} else {
						
						err_sb.append("分解方法为空  ");
						
					}
					
					
					BudgMedIncomeMonthPlan data_exc_extis = budgDeptYearMonthIncomePlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptYearMonthIncomePlan.setError_type(err_sb.toString());
					
					list_err.add(budgDeptYearMonthIncomePlan);
					
				} else {
			  
					String dataJson = budgDeptYearMonthIncomePlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeMonthPlan data_exc = new BudgMedIncomeMonthPlan();
			
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

