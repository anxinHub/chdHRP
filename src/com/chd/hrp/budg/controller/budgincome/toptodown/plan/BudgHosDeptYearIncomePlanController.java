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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgMedIncomeDeptPlan;
import com.chd.hrp.budg.entity.BudgMedIncomeHmPlan;
import com.chd.hrp.budg.service.toptodown.plan.BudgHosDeptYearIncomePlanService;
/**
 * 
 * @Description:
 * BUDG_MED_INCOME_DEPT_PLAN
 * @Table:
 * BUDG_MED_INCOME_DEPT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgHosDeptYearIncomePlanController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgHosDeptYearIncomePlanController.class);
	
	//引入Service服务
	@Resource(name = "budgHosDeptYearIncomePlanService")
	private final BudgHosDeptYearIncomePlanService budgHosDeptYearIncomePlanService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanMainPage", method = RequestMethod.GET)
	public String budgHosDeptYearIncomePlanMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanAddPage", method = RequestMethod.GET)
	public String budgHosDeptYearIncomePlanAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanAdd";

	}
	
	/**
	 * @Description 
	 * 批量设置页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanSetPage", method = RequestMethod.GET)
	public String budgHosDeptYearIncomePlanSetPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanSet";

	}

	/**
	 * @Description 
	 * 添加数据 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/addBudgHosDeptYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgHosDeptYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgHosDeptYearIncomePlanJson = budgHosDeptYearIncomePlanService.add(mapVo);

		return JSONObject.parseObject(budgHosDeptYearIncomePlanJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanUpdatePage", method = RequestMethod.GET)
	public String budgHosDeptYearIncomePlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgMedIncomeDeptPlan budgHosDeptYearIncomePlan = new BudgMedIncomeDeptPlan();
    
		budgHosDeptYearIncomePlan = budgHosDeptYearIncomePlanService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgHosDeptYearIncomePlan.getGroup_id());
		mode.addAttribute("hos_id", budgHosDeptYearIncomePlan.getHos_id());
		mode.addAttribute("copy_code", budgHosDeptYearIncomePlan.getCopy_code());
		mode.addAttribute("budg_year", budgHosDeptYearIncomePlan.getBudg_year());
		mode.addAttribute("subj_code", budgHosDeptYearIncomePlan.getSubj_code());
		mode.addAttribute("resolve_way", budgHosDeptYearIncomePlan.getResolve_way());
		mode.addAttribute("resolve_data", budgHosDeptYearIncomePlan.getResolve_data());
		mode.addAttribute("reference_years", budgHosDeptYearIncomePlan.getReference_years());
		
		return "hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/updateBudgHosDeptYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgHosDeptYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgHosDeptYearIncomePlanJson = budgHosDeptYearIncomePlanService.update(mapVo);
		
		return JSONObject.parseObject(budgHosDeptYearIncomePlanJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/addOrUpdateBudgHosDeptYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgHosDeptYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgHosDeptYearIncomePlanJson ="";
		

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
	  
		budgHosDeptYearIncomePlanJson = budgHosDeptYearIncomePlanService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgHosDeptYearIncomePlanJson);
	}
	
	/**
	 * @Description 生成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/generate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String Json = budgHosDeptYearIncomePlanService.generate(mapVo);

		return JSONObject.parseObject(Json);

	}
	
	/**
	 * @Description 
	 * 批量设置
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/settingBudgHosDeptYearIncomePlan", method = RequestMethod.POST)
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
	    
		String budgHosDeptYearIncomePlanJson = budgHosDeptYearIncomePlanService.updateBatch(listVo);
			
	  return JSONObject.parseObject(budgHosDeptYearIncomePlanJson);
			
	}
	
	/**
	 * @Description 
	 * 删除数据 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/deleteBudgHosDeptYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgHosDeptYearIncomePlan(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgHosDeptYearIncomePlanJson = budgHosDeptYearIncomePlanService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgHosDeptYearIncomePlanJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/queryBudgHosDeptYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgHosDeptYearIncomePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgHosDeptYearIncomePlan = budgHosDeptYearIncomePlanService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosDeptYearIncomePlan);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanImportPage", method = RequestMethod.GET)
	public String budgHosDeptYearIncomePlanImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/budgHosDeptYearIncomePlanImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown\\plan","医院年度至科室年度医疗收入预算分解方案.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/readBudgHosDeptYearIncomePlanFiles",method = RequestMethod.POST)  
    public String readBudgHosDeptYearIncomePlanFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
			List<BudgMedIncomeDeptPlan> list_err = new ArrayList<BudgMedIncomeDeptPlan>();
		
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			String[] resolveData = {"01","02","03","04","05"};
			
			
			
			try {
				for (int i = 1; i < list.size(); i++) {
					
					StringBuffer err_sb = new StringBuffer();
					
					BudgMedIncomeDeptPlan budgMedIncomeDeptPlan = new BudgMedIncomeDeptPlan();
					
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
							
							budgMedIncomeDeptPlan.setBudg_year(temp[0]);
				    		mapVo.put("budg_year", temp[0]);
						
						} else {
							
							err_sb.append("预算年度为空;");
							
						}
						 
						if (ExcelReader.validExceLColunm(temp,1)) {
							
							budgMedIncomeDeptPlan.setSubj_code(temp[1]);
				    		mapVo.put("subj_code", temp[1]);
				    		
				    		//校验 医疗收入科目 是否存在
				    		int count = budgHosDeptYearIncomePlanService.querySubjExist(mapVo) ;
				    		
				    		if(count == 0){
				    			err_sb.append("输入年度收入科目不存在,科目编码输入错误;");
				    		}
						
						} else {
							err_sb.append("科目编码为空;");
						}
						
						if (ExcelReader.validExceLColunm(temp,2)) {
							
							budgMedIncomeDeptPlan.setSubj_name(temp[2]);
							
				    		mapVo.put("subj_name", temp[2]);
						
						} else {
							err_sb.append("科目名称为空;");
						}
					
						if (ExcelReader.validExceLColunm(temp,3)) {
							
							budgMedIncomeDeptPlan.setResolve_way(temp[3]);
							
							if(Arrays.asList(resolveData).contains(temp[3])){
								mapVo.put("resolve_way", temp[3]);
								
							}else{
								err_sb.append("分解方法输入错误;");
							}
				    		
						
						} else {
							
							err_sb.append("分解方法为空;");
							
						}
						
						mapVo.put("resolve_data", "");
						mapVo.put("reference_years", "");
						
						int count = budgHosDeptYearIncomePlanService.queryDataExist(mapVo);
					
					if (count > 0 ) {
						
						err_sb.append("数据已经存在!");
						
					}
					if (err_sb.toString().length() > 0) {
						
						budgMedIncomeDeptPlan.setError_type(err_sb.toString());
						
						list_err.add(budgMedIncomeDeptPlan);
						
					} else {
						
						addList.add(mapVo) ;
					}
					
				}
				
				if(list_err.size() == 0){
					if(addList.size() > 0){
						budgHosDeptYearIncomePlanService.addBatch(addList);
					}else{
						BudgMedIncomeDeptPlan data_exc = new BudgMedIncomeDeptPlan();
						
						data_exc.setError_type("没有导入数据,导入失败!");
						
						list_err.add(data_exc);
					}
					
				}
				
			} catch (DataAccessException e) {
				
				BudgMedIncomeDeptPlan data_exc = new BudgMedIncomeDeptPlan();
				
				data_exc.setError_type("导入系统出错");
				
				list_err.add(data_exc);
				
			}
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 BUDG_MED_INCOME_DEPT_PLAN
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/plan/hosdeptyearincome/addBatchBudgHosDeptYearIncomePlan", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgHosDeptYearIncomePlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeDeptPlan> list_err = new ArrayList<BudgMedIncomeDeptPlan>();
		
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
			
			BudgMedIncomeDeptPlan budgHosDeptYearIncomePlan = new BudgMedIncomeDeptPlan();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {
						
					budgHosDeptYearIncomePlan.setBudg_year((String)jsonObj.get("budg_year"));
		    		mapVo.put("budg_year", jsonObj.get("budg_year"));
		    		} else {
						
						err_sb.append("预算年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgHosDeptYearIncomePlan.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_way"))) {
						
					budgHosDeptYearIncomePlan.setResolve_way((String)jsonObj.get("resolve_way"));
		    		mapVo.put("resolve_way", jsonObj.get("resolve_way"));
		    		} else {
						
						err_sb.append("分解方法为空  ");
						
					}
					
					
					BudgMedIncomeDeptPlan data_exc_extis = budgHosDeptYearIncomePlanService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosDeptYearIncomePlan.setError_type(err_sb.toString());
					
					list_err.add(budgHosDeptYearIncomePlan);
					
				} else {
			  
					String dataJson = budgHosDeptYearIncomePlanService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeDeptPlan data_exc = new BudgMedIncomeDeptPlan();
			
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

