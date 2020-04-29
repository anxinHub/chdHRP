/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.toptodown.deptyearinbudg;
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
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.entity.BudgMedIncomeDeptYear;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.toptodown.deptyearinbudg.MedInDYBudgService;
/**
 * 
 * @Description:
 * 科室年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class MedInDYBudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedInDYBudgController.class);
	
	//引入Service服务
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
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgMainPage", method = RequestMethod.GET)
	public String medInDYBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgMain";

	}
	
	/**
	 * @Description 
	 * 预算分解维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgResolveMainPage", method = RequestMethod.GET)
	public String medInDYBudgResolveMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("year", mapVo.get("year"));
		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		
		return "hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgResolveMain";

	}


	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgAddPage", method = RequestMethod.GET)
	public String medInDYBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgAdd";

	}
	
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/saveMedInDYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedInDYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1])   ;
			mapVo.put("dept_id", ids[2]);
			
			mapVo.put("budg_value", ids[3]);
			mapVo.put("last_year_income", ids[4]);
			
			mapVo.put("remark", ids[5]);
			mapVo.put("refer_value", ids[6]);
			mapVo.put("reason", ids[7]);
			mapVo.put("state", ids[8]);
			
			
			mapVo.put("rowNo", ids[9]);
			mapVo.put("flag", ids[10]);
			
			mapVo.put("count_value", "");
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
	  
		String budgMedIncomeDeptYearJson = null ;
		
		try {
			
			budgMedIncomeDeptYearJson = medInDYBudgService.save(listVo);
			
		} catch (Exception e) {
			
			budgMedIncomeDeptYearJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgMedIncomeDeptYearJson);
	}
	
	
	/**
	 * @Description 
	 * 添加数据 科室年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/addMedInDYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInDYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("dept_id", ids[1]);
			mapVo.put("dept_name", ids[2]);
			mapVo.put("subj_code", ids[3])   ;
			mapVo.put("subj_name", ids[4])   ;
			
			mapVo.put("last_year_income", ids[5]);
			mapVo.put("budg_value", ids[6]);
			
			mapVo.put("remark", ids[7]);
			if("-1".equals(ids[8])){
				mapVo.put("dept_suggest", "");
			}else{
				mapVo.put("dept_suggest", ids[8]);
			}
			
			
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			mapVo.put("hos_suggest_resolve", "");
			
			listVo.add(mapVo);
		}
       
		String budgMedIncomeDeptYearJson = "";
		
		try {
			
			budgMedIncomeDeptYearJson = medInDYBudgService.addBatch(listVo);
			
		} catch (Exception e) {
			
			budgMedIncomeDeptYearJson = e.getMessage() ;
		}
		

		return JSONObject.parseObject(budgMedIncomeDeptYearJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgUpdatePage", method = RequestMethod.GET)
	public String medInDYBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgMedIncomeDeptYear budgMedIncomeDeptYear = new BudgMedIncomeDeptYear();
    
		budgMedIncomeDeptYear = medInDYBudgService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedIncomeDeptYear.getGroup_id());
		mode.addAttribute("hos_id", budgMedIncomeDeptYear.getHos_id());
		mode.addAttribute("copy_code", budgMedIncomeDeptYear.getCopy_code());
		mode.addAttribute("year", budgMedIncomeDeptYear.getYear());
		mode.addAttribute("subj_code", budgMedIncomeDeptYear.getSubj_code());
		mode.addAttribute("dept_id", budgMedIncomeDeptYear.getDept_id());
		mode.addAttribute("count_value", budgMedIncomeDeptYear.getCount_value());
		mode.addAttribute("budg_value", budgMedIncomeDeptYear.getBudg_value());
		mode.addAttribute("remark", budgMedIncomeDeptYear.getRemark());
		mode.addAttribute("grow_rate", budgMedIncomeDeptYear.getGrow_rate());
		mode.addAttribute("resolve_rate", budgMedIncomeDeptYear.getResolve_rate());
		mode.addAttribute("last_year_income", budgMedIncomeDeptYear.getLast_year_income());
		mode.addAttribute("hos_suggest_resolve", budgMedIncomeDeptYear.getHos_suggest_resolve());
		mode.addAttribute("dept_suggest", budgMedIncomeDeptYear.getDept_suggest());
		
		return "hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/updateMedInDYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInDYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
			mapVo.put("dept_id", ids[5]);
			
			mapVo.put("budg_value", ids[6]);
			mapVo.put("last_year_income", ids[7]);
			mapVo.put("remark", ids[8]);
			if("-1".equals(ids[9])){
				mapVo.put("dept_suggest", "");
			}else{
				mapVo.put("dept_suggest", ids[9]);
			}
			if("-1".equals(ids[10])){
				mapVo.put("grow_rate", "");
			}else{
				mapVo.put("grow_rate", ids[10]);
			}
			if("-1".equals(ids[11])){
				mapVo.put("resolve_rate", "");
			}else{
				mapVo.put("resolve_rate", ids[11]);
			}
			if("-1".equals(ids[12])){
				mapVo.put("count_value", "");
			}else{
				mapVo.put("count_value", ids[12]);
			}
			
			mapVo.put("hos_suggest_resolve", "");
			
			listVo.add(mapVo);
	      
	    }
	  
		String budgMedIncomeDeptYearJson = medInDYBudgService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgMedIncomeDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/addOrUpdateMedInDYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedInDYBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMedIncomeDeptYearJson ="";
		

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
	  
		budgMedIncomeDeptYearJson = medInDYBudgService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedIncomeDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/deleteMedInDYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInDYBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("dept_id", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMedIncomeDeptYearJson = medInDYBudgService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedIncomeDeptYearJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室年度医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/queryMedInDYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInDYBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMedIncomeDeptYear = medInDYBudgService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeDeptYear);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室年度医疗收入预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgImportPage", method = RequestMethod.GET)
	public String medInDYBudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/deptyearinbudg/medInDYBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室年度医疗收入预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/deptyearinbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown","科室年度医疗收入预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室年度医疗收入预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/deptyearinbudg/readMedInDYBudgFiles",method = RequestMethod.POST)  
    public String readMedInDYBudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedIncomeDeptYear> list_err = new ArrayList<BudgMedIncomeDeptYear>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedIncomeDeptYear budgMedIncomeDeptYear = new BudgMedIncomeDeptYear();
				
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
						
						budgMedIncomeDeptYear.setYear(temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgMedIncomeDeptYear.setDept_code(temp[1]);
						
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
						
						budgMedIncomeDeptYear.setSubj_code(temp[2]);
						
			    		mapVo.put("subj_code", temp[2]);
			    		
			    		BudgIncomeSubj income = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
			    		
						if(income == null ){
							err_sb.append("该年度收入预算科目编码不存在;");
						}
					
					} else {
						
						err_sb.append("科目编码为空 ;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						budgMedIncomeDeptYear.setLast_year_income(Double.valueOf(temp[3]));
						mapVo.put("last_year_income", temp[3]);
					
					} else {
						
						mapVo.put("last_year_income", "");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgMedIncomeDeptYear.setBudg_value(Double.valueOf(temp[4]));
						
						mapVo.put("budg_value", temp[4]);
					
					} else {
						
						mapVo.put("budg_value", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						budgMedIncomeDeptYear.setRemark(temp[5]);
						
						mapVo.put("remark", temp[5]);
					
					} else {
						
						mapVo.put("remark", "");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						budgMedIncomeDeptYear.setDept_suggest(Double.valueOf(temp[6]));
						
						mapVo.put("dept_suggest", temp[6]);
					
					} else {
						
						mapVo.put("dept_suggest", "");
						
					}
					 
					
				BudgMedIncomeDeptYear data_exc_extis = medInDYBudgService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeDeptYear.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeDeptYear);
					
				} else {
					mapVo.put("count_value", "");
					mapVo.put("grow_rate", "");
					mapVo.put("resolve_rate", "");
					mapVo.put("hos_suggest_resolve", "");
					
					addList.add(mapVo) ;
					
				}
				
			}
			
			if(list_err.size() == 0){
				String dataJson = medInDYBudgService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeDeptYear data_exc = new BudgMedIncomeDeptYear();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室年度医疗收入预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/addBatchMedInDYBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedInDYBudg(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeDeptYear> list_err = new ArrayList<BudgMedIncomeDeptYear>();
		
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
			
			BudgMedIncomeDeptYear budgMedIncomeDeptYear = new BudgMedIncomeDeptYear();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgMedIncomeDeptYear.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedIncomeDeptYear.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgMedIncomeDeptYear.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgMedIncomeDeptYear.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgMedIncomeDeptYear.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgMedIncomeDeptYear.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgMedIncomeDeptYear.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("resolve_rate"))) {
						
					budgMedIncomeDeptYear.setResolve_rate(Double.valueOf((String)jsonObj.get("resolve_rate")));
		    		mapVo.put("resolve_rate", jsonObj.get("resolve_rate"));
		    		} else {
						
						err_sb.append("分解比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
						
					budgMedIncomeDeptYear.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
		    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("hos_suggest_resolve"))) {
						
					budgMedIncomeDeptYear.setHos_suggest_resolve(Double.valueOf((String)jsonObj.get("hos_suggest_resolve")));
		    		mapVo.put("hos_suggest_resolve", jsonObj.get("hos_suggest_resolve"));
		    		} else {
						
						err_sb.append("医院意见分解为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_suggest"))) {
						
					budgMedIncomeDeptYear.setDept_suggest(Double.valueOf((String)jsonObj.get("dept_suggest")));
		    		mapVo.put("dept_suggest", jsonObj.get("dept_suggest"));
		    		} else {
						
						err_sb.append("科室意见为空  ");
						
					}
					
					
				BudgMedIncomeDeptYear data_exc_extis = medInDYBudgService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeDeptYear.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeDeptYear);
					
				} else {
			  
					String dataJson = medInDYBudgService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeDeptYear data_exc = new BudgMedIncomeDeptYear();
			
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
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/queryBudgDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String dept = medInDYBudgService.queryBudgDept(mapVo);
		return dept;

	}
    
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/collectMedInDYBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectMedInDYBudgUp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String collect  = medInDYBudgService.collectMedInDYBudgUp(mapVo) ;

		return JSONObject.parseObject(collect);

	}
	/**
	 * 查询 所传 科室 的 上年业务量  
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/queryDeptYearLastYearWork", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptYearLastYearWork(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))  - 1 ;
		mapVo.put("year", year) ;
		String str  = medInDYBudgService.queryDeptYearLastYearWork(mapVo) ;
		return str;
	}
	
	/**
	 * @Description 
	 * 下发,撤回,取消确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/issuedOrRetract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> issuedOrRetract(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("subj_name", ids[5])   ;
			mapVo.put("dept_id", ids[6]);
			mapVo.put("budg_value", ids[7]);
			mapVo.put("dept_name", ids[8]);
			if(!"-1".equals(ids[9])){
				mapVo.put("state", ids[9]);
			}else{
				mapVo.put("state", "");
			}
			mapVo.put("flag", ids[10]);
			
			listVo.add(mapVo);
	    }
	    
		String budgIncomeDeptYearJson = medInDYBudgService.issuedOrRetract(listVo);
			
	  return JSONObject.parseObject(budgIncomeDeptYearJson);
			
	}
	
	/**
	 * @Description 
	 * 确认不通过页面跳转
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/disPassReasonPage", method = RequestMethod.GET)
	public String disPassReasonPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/deptyearinbudg/disPassReasonPage";

	}
	
	/**
	 * @Description 
	 * 确认 (通过,不通过)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/passOrDisPass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> passOrDisPass(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("subj_name", ids[5])   ;
			mapVo.put("dept_id", ids[6]);
			mapVo.put("dept_name", ids[7]);
			mapVo.put("refer_value", ids[8]);
			mapVo.put("reason", ids[9]);
			mapVo.put("state", ids[10]);
			
			listVo.add(mapVo);
	    }
	    
		String budgIncomeDeptYearJson = medInDYBudgService.passOrDisPass(listVo);
	    return JSONObject.parseObject(budgIncomeDeptYearJson);
	}
	
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/deptyearinbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("year", mapVo.get("year"));
			

			// 查询要生成的数据
			List<Map<String,Object>> list = medInDYBudgService.queryData(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【科室年度业务预算编制方案】数据.或数据已全部生成\"}");
			}
			
			for(Map<String, Object> item : list ){
				
					item.put("count_value", "");
					item.put("budg_value", "");
					item.put("remark", "");
					item.put("grow_rate", "");
					item.put("grow_value", "");
					item.put("resolve_rate", "");
					//item.put("last_year_income", "");
					item.put("state", "");
					item.put("refer_value", "");
					item.put("reason", "");
			        listVo.add(item); 
			}
			
			String budgIncomeDeptYearJson = null ;
			
			if(listVo.size() > 0 ){
				budgIncomeDeptYearJson =medInDYBudgService.addBatch(listVo);
			}else{
				budgIncomeDeptYearJson = "{\"error\":\"历史数据未维护.\"}";;
			}
			return JSONObject.parseObject(budgIncomeDeptYearJson);	
	} 
}

