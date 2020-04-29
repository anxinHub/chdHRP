/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.toptodown.deptmonthinbudg;
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
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.entity.BudgMedIncomeDeptMonth;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.toptodown.deptmonthinbudg.MedInDMBudgService;
import com.chd.hrp.budg.service.budgincome.toptodown.deptyearinbudg.MedInDYBudgService;
/**
 * 
 * @Description:
 * 科室月份医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class MedInDMBudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedInDMBudgController.class);
	
	//引入Service服务
	@Resource(name = "medInDMBudgService")
	private final MedInDMBudgService medInDMBudgService = null;
	
	@Resource(name = "medInDYBudgService")
	private final MedInDYBudgService medInDYBudgService = null;
   
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/medInDMBudgMainPage", method = RequestMethod.GET)
	public String budgMedIncomeDeptMonthMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/deptmonthinbudg/medInDMBudgMain";

	}

	/**
	 * @Description 
	 * 预算分解维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/medInDMBudgResolveMainPage", method = RequestMethod.GET)
	public String medInDYBudgResolveMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("year", mapVo.get("year"));
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		return "hrp/budg/budgincome/toptodown/deptmonthinbudg/medInDMBudgResolveMain";

	}

	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/medInDMBudgAddPage", method = RequestMethod.GET)
	public String budgMedIncomeDeptMonthAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/deptmonthinbudg/medInDMBudgAdd";

	}

	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/saveMedInDMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedInDMBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("dept_id", ids[2]);
			mapVo.put("dept_name", ids[3]);
			mapVo.put("subj_code", ids[4])   ;
			mapVo.put("subj_name", ids[5])   ;
			
			if("-1".equals(ids[6])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[6]);
			}
			
			mapVo.put("rowNo", ids[7]);
			mapVo.put("flag", ids[8]);
			
			//构建 查询上年业务量  参数 Map
			Map<String,Object> paraMap =  new HashMap<String,Object>();
			
			paraMap.put("group_id", SessionManager.getGroupId())   ;
			paraMap.put("hos_id", SessionManager.getHosId())   ;
			paraMap.put("copy_code", SessionManager.getCopyCode())   ;
			paraMap.put("subj_code", ids[2]);
			paraMap.put("year", Integer.parseInt(String.valueOf(mapVo.get("year")))-1);
			mapVo.put("month", ids[1])   ;

			
			String last_year_income = medInDMBudgService.queryDMLastYearIncome(mapVo);
			
			
			if(last_year_income == null || "".equals(last_year_income) ){
				
				mapVo.put("last_year_income", "");
				
			}else{
				
				mapVo.put("last_year_income", last_year_income);
			}
			
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			mapVo.put("count_value", "");
			mapVo.put("remark", "");
			mapVo.put("last_month_carried", "");
			mapVo.put("carried_next_month", "");
			mapVo.put("hos_suggest_resolve", "");
			
			listVo.add(mapVo);
		}
											
		String budgMedIncomeDeptMonthJson = medInDMBudgService.save(listVo);
		
		return JSONObject.parseObject(budgMedIncomeDeptMonthJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 科室月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/addMedInDMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInDMBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("dept_id", ids[2]);
			mapVo.put("dept_name", ids[3]);
			mapVo.put("subj_code", ids[4])   ;
			mapVo.put("subj_name", ids[5])   ;
			if("-1".equals(ids[6])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[6]);
			}
			
			mapVo.put("remark", "");
			mapVo.put("last_month_carried", "");
			mapVo.put("carried_next_month", "");
			
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			
			listVo.add(mapVo);
		}
		
		String budgMedIncomeDeptMonthJson = medInDMBudgService.addBatch(listVo);

		return JSONObject.parseObject(budgMedIncomeDeptMonthJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/medInDMBudgUpdatePage", method = RequestMethod.GET)
	public String budgMedIncomeDeptMonthUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgMedIncomeDeptMonth budgMedIncomeDeptMonth = new BudgMedIncomeDeptMonth();
    
		budgMedIncomeDeptMonth = medInDMBudgService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedIncomeDeptMonth.getGroup_id());
		mode.addAttribute("hos_id", budgMedIncomeDeptMonth.getHos_id());
		mode.addAttribute("copy_code", budgMedIncomeDeptMonth.getCopy_code());
		mode.addAttribute("year", budgMedIncomeDeptMonth.getYear());
		mode.addAttribute("subj_code", budgMedIncomeDeptMonth.getSubj_code());
		mode.addAttribute("dept_id", budgMedIncomeDeptMonth.getDept_id());
		mode.addAttribute("month", budgMedIncomeDeptMonth.getMonth());
		mode.addAttribute("count_value", budgMedIncomeDeptMonth.getCount_value());
		mode.addAttribute("budg_value", budgMedIncomeDeptMonth.getBudg_value());
		mode.addAttribute("remark", budgMedIncomeDeptMonth.getRemark());
		mode.addAttribute("grow_rate", budgMedIncomeDeptMonth.getGrow_rate());
		mode.addAttribute("resolve_rate", budgMedIncomeDeptMonth.getResolve_rate());
		mode.addAttribute("last_year_income", budgMedIncomeDeptMonth.getLast_year_income());
		mode.addAttribute("last_month_carried", budgMedIncomeDeptMonth.getLast_month_carried());
		mode.addAttribute("carried_next_month", budgMedIncomeDeptMonth.getCarried_next_month());
		
		return "hrp/budgbudgincome/toptodown/deptmonthinbudg/medInDMBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/																		
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/updateMedInDMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInDMBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("dept_id", ids[2]);
			mapVo.put("subj_code", ids[3])   ;
			
			if("-1".equals(ids[4])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[4]);
			}
			
			listVo.add(mapVo);
		}
											
		String budgMedIncomeDeptMonthJson = medInDMBudgService.updateBatchBudgValue(listVo);
		
		return JSONObject.parseObject(budgMedIncomeDeptMonthJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/																		
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/updateMedInDMBudgResolveUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInDMBudgResolveUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("dept_id", ids[2]);
			mapVo.put("subj_code", ids[3])   ;
			
			if("-1".equals(ids[4])){
				mapVo.put("budg_value", "");
			}else{
				mapVo.put("budg_value", ids[4]);
			}
			if("-1".equals(ids[5])){
				mapVo.put("grow_rate", "");
			}else{
				mapVo.put("grow_rate", ids[5]);
			}
			if("-1".equals(ids[6])){
				mapVo.put("resolve_rate", "");
			}else{
				mapVo.put("resolve_rate", ids[6]);
			}
			if("-1".equals(ids[7])){
				mapVo.put("count_value", "");
			}else{
				mapVo.put("count_value", ids[7]);
			}
			if("-1".equals(ids[8])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[8]);
			}
			if("-1".equals(ids[9])){
				mapVo.put("last_year_income", "");
			}else{
				mapVo.put("last_year_income", ids[9]);
			}
			
			
			mapVo.put("last_month_carried", "");
			mapVo.put("carried_next_month", "");
			
			listVo.add(mapVo);
		}
											
		String budgMedIncomeDeptMonthJson = medInDMBudgService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgMedIncomeDeptMonthJson);
	}
	
	
	/**
	 * @Description 
	 * 更新数据 科室月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/addOrUpdateMedInDMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedInDMBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMedIncomeDeptMonthJson ="";
		

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
	  
		budgMedIncomeDeptMonthJson = medInDMBudgService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedIncomeDeptMonthJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/deleteMedInDMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInDMBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("subj_code", ids[4])   ;
				mapVo.put("dept_id", ids[5])   ;
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMedIncomeDeptMonthJson = medInDMBudgService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedIncomeDeptMonthJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室月份医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/queryMedInDMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInDMBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgMedIncomeDeptMonth = medInDMBudgService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeDeptMonth);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 科室月份医疗收入预算分解维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/queryMedInDMBudgResolveUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInDMBudgResolveUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgMedIncomeDeptMonth = medInDMBudgService.queryResolve(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeDeptMonth);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室月份医疗收入预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/medInDMBudgImportPage", method = RequestMethod.GET)
	public String budgMedIncomeDeptMonthImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/deptmonthinbudg/medInDMBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室月份医疗收入预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/deptmonthinbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown","科室月份医疗收入预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室月份医疗收入预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/deptmonthinbudg/readMedInDMBudgFiles",method = RequestMethod.POST)  
    public String readMedInDMBudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> errMap = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
						errMap.put("year", temp[0]);
						
						mapVo.put("year", temp[0]);
						
						mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						errMap.put("dept_code",temp[1]);
						
			    		mapVo.put("dept_code", temp[1]);
			    		
			    		Map<String,Object> dept = medInDYBudgService.queryDeptExist(mapVo);
			    		
			    		if(dept != null ){
			    			
			    			mapVo.put("dept_id", dept.get("dept_id"));
			    			
			    		}else{
			    			
			    			err_sb.append("预算科室编码不存在;");
			    		}
						
					} else {
						
						err_sb.append("预算科室编码为空 ;");
						
					}
					
					if (StringTool.isNotBlank(temp[2])) {
						
						errMap.put("subj_code", temp[2]);
						
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		BudgIncomeSubj income = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
			    		
						if(income == null ){
							err_sb.append("该年度收入预算科目编码不存在;");
						}
					
					} else {
						
						err_sb.append("科目编码为空 ;");
						
					}
					 
					mapVo.put("last_year_income", "");
					mapVo.put("remark", "");
					mapVo.put("last_month_carried", "");
					mapVo.put("carried_next_month", "");
					
					mapVo.put("count_value", "");
					mapVo.put("grow_rate", "");
					mapVo.put("resolve_rate", "");
					mapVo.put("hos_suggest_resolve", "");
					
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "01");
						
						map.put("budg_value", temp[3]);
						
						errMap.put("m01", temp[3]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "01");
						
						map.put("budg_value", "");
						
						errMap.put("m01", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "02");
						
						map.put("budg_value", temp[4]);
						
						errMap.put("m02", temp[4]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "02");
						
						map.put("budg_value", "");
						
						errMap.put("m02", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "03");
						
						map.put("budg_value", temp[5]);
						
						errMap.put("m03", temp[5]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "03");
						
						map.put("budg_value", "");
						
						errMap.put("m03", "");
						
						addList.add(map);
						
					}

					if (ExcelReader.validExceLColunm(temp,6)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "04");
						
						map.put("budg_value", temp[6]);
						
						errMap.put("m04", temp[6]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "04");
						
						map.put("budg_value", "");
						
						errMap.put("m04", "");
						
						addList.add(map);
						
					}
					if (ExcelReader.validExceLColunm(temp,7)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "05");
						
						map.put("budg_value", temp[7]);
						
						errMap.put("m05", temp[7]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "05");
						
						map.put("budg_value", "");
						
						errMap.put("m05", "");
						
						addList.add(map);
						
					}
					if (ExcelReader.validExceLColunm(temp,8)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "06");
						
						map.put("budg_value", temp[8]);
						
						errMap.put("m06", temp[8]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "06");
						
						map.put("budg_value", "");
						
						errMap.put("m06", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,9)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "07");
						
						map.put("budg_value", temp[9]);
						
						errMap.put("m07", temp[9]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "07");
						
						map.put("budg_value", "");
						
						errMap.put("m07", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,10)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "08");
						
						map.put("budg_value", temp[10]);
						
						errMap.put("m08", temp[10]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "08");
						
						map.put("budg_value", "");
						
						errMap.put("m08", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,11)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "09");
						
						map.put("budg_value", temp[11]);
						
						errMap.put("m09", temp[11]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "09");
						
						map.put("budg_value", "");
						
						errMap.put("m09", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,12)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "10");
						
						map.put("budg_value", temp[12]);
						
						errMap.put("m10", temp[12]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "10");
						
						map.put("budg_value", "");
						
						errMap.put("m10", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,13)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "11");
						
						map.put("budg_value", temp[13]);
						
						errMap.put("m11", temp[13]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "11");
						
						map.put("budg_value", "");
						
						errMap.put("m11", "");
						
						addList.add(map);
						
					}
					
					if (ExcelReader.validExceLColunm(temp,14)) {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "12");
						
						map.put("budg_value", temp[14]);
						
						errMap.put("m12", temp[14]);
						
						addList.add(map);
						
					} else {
						
						Map<String,Object> map = new HashMap<String,Object>();
						
						map.putAll(mapVo);
						
						map.put("month", "12");
						
						map.put("budg_value", "");
						
						errMap.put("m12", "");
						
						addList.add(map);
						
					}

				if(mapVo.get("dept_id") != null){
					
					int count  = medInDMBudgService.queryDataExist(mapVo);
					
					if (count > 0) {
						
						err_sb.append("数据已经存在！ ");
						
					}
				}
				
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				}
				
			}
			
			if(list_err.size()==0){
				
				String dataJson = medInDMBudgService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			Map<String,Object> data_exc = new HashMap<String,Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室月份医疗收入预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/addBatchMedInDMBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedInDMBudg(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeDeptMonth> list_err = new ArrayList<BudgMedIncomeDeptMonth>();
		
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
			
			BudgMedIncomeDeptMonth budgMedIncomeDeptMonth = new BudgMedIncomeDeptMonth();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgMedIncomeDeptMonth.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedIncomeDeptMonth.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgMedIncomeDeptMonth.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgMedIncomeDeptMonth.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgMedIncomeDeptMonth.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgMedIncomeDeptMonth.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgMedIncomeDeptMonth.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgMedIncomeDeptMonth.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_rate"))) {
						
					budgMedIncomeDeptMonth.setResolve_rate(Double.valueOf((String)jsonObj.get("resolve_rate")));
		    		mapVo.put("resolve_rate", jsonObj.get("resolve_rate"));
		    		} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
						
					budgMedIncomeDeptMonth.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
		    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_month_carried"))) {
						
					budgMedIncomeDeptMonth.setLast_month_carried(Double.valueOf((String)jsonObj.get("last_month_carried")));
		    		mapVo.put("last_month_carried", jsonObj.get("last_month_carried"));
		    		} else {
						
						err_sb.append("上月结转为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("carried_next_month"))) {
						
					budgMedIncomeDeptMonth.setCarried_next_month(Double.valueOf((String)jsonObj.get("carried_next_month")));
		    		mapVo.put("carried_next_month", jsonObj.get("carried_next_month"));
		    		} else {
						
						err_sb.append("结转下月为空  ");
						
					}
					
					
				BudgMedIncomeDeptMonth data_exc_extis = medInDMBudgService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeDeptMonth.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeDeptMonth);
					
				} else {
			  
					String dataJson = medInDMBudgService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeDeptMonth data_exc = new BudgMedIncomeDeptMonth();
			
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
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/collectMedInDMBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectMedInDMBudgUp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
	
			mapVo.put("hos_id", SessionManager.getHosId());
		
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
			String collect  = medInDMBudgService.collectMedInDMBudgUp(mapVo) ;

			return JSONObject.parseObject(collect);
	}
	
	/**
	 * 查询 所传 科室 的 上年业务量  
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/queryDeptyearValue", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptYearLastYearWork(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String str  = medInDMBudgService.queryDeptyearValue(mapVo) ;

		return str;

	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptmonthinbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			// 查询要生成的数据
			List<Map<String,Object>> list = medInDMBudgService.queryData(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【历史执行数据】、科室年度至科室月份医疗收入预算分解方案数据.\"}");
			}
			
			for(Map<String, Object> item : list ){
				
				item.put("count_value", "");
				item.put("budg_value", "");
				item.put("remark", "");
				item.put("grow_rate", "");
				item.put("resolve_rate", "");
				item.put("last_month_carried", "");
				item.put("carried_next_month", "");
				listVo.add(item) ;
			}
			
			String budgMedIncomeDeptMonth = null ;
			
			if(listVo.size() > 0 ){
				
				// 批量添加 生成数据
				budgMedIncomeDeptMonth =medInDMBudgService.addGenerateBatch(listVo);
		         
			}else{
				budgMedIncomeDeptMonth = "{\"error\":\"科目对应科室数据未维护或数据已全部生成,无法生成.\"}";
			}
			return JSONObject.parseObject(budgMedIncomeDeptMonth);	
	} 	
}

