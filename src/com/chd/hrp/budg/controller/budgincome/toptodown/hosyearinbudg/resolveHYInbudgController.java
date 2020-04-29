/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.toptodown.hosyearinbudg;
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
import com.chd.hrp.budg.entity.BudgMedIncomeHosYear;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.budghosresolverate.BudgHosResolveRateService;
import com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg.BudgMedIncomeHosYearService;

/**
 * 
 * @Description:
 * 医院年度医疗预算分解预算 
 * @Table:
 * BUDG_HOS_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class resolveHYInbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(resolveHYInbudgController.class);
	
	//引入Service服务
	
	@Resource(name = "budgMedIncomeHosYearService")
	private final BudgMedIncomeHosYearService budgMedIncomeHosYearService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
    
	/*@Resource(name="budgHosResolveRateService")
	private final  BudgHosResolveRateService budgHosResolveRateService=null;*/
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveHYInBudgMainPage", method = RequestMethod.GET)
	public String resolveHYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveHYInBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveHYInBudgAddPage", method = RequestMethod.GET)
	public String resolveHYInBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveHYInBudgAdd";

	}
	
	/**
	 * @Description 
	 * 分解比例维护页面跳转 
	 * @param mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveRateHYInBudgMainPage", method = RequestMethod.GET)
	public String resolveRateHYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveRateHYInBudgMain";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院年度医疗收入预算 分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/saveResolveHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveResolveHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("last_year_income", ids[2].equals("null")||ids[2].equals("undefined")?0:ids[2]);
			mapVo.put("grow_rate", ids[3]);
			mapVo.put("grow_value", ids[4]);
			mapVo.put("count_value", ids[5]);
			mapVo.put("budg_value", ids[6]);
			mapVo.put("remark", ids[7]);
			
			mapVo.put("rowNo", ids[8]) ;
			mapVo.put("flag", ids[9]) ;
			listVo.add(mapVo);
		}
		
       
		String budgHosIndependentSubjJson = null ;
		
		try {
			
			budgHosIndependentSubjJson = budgMedIncomeHosYearService.save(listVo);

		} catch (Exception e) {
			
			budgHosIndependentSubjJson = e.getMessage() ;
		}
		
		return JSONObject.parseObject(budgHosIndependentSubjJson);
		
	}
	
	/**
	 * @Description 
	 * 添加数据 医院年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/addResolveHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResolveHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("budg_value", ids[2].equals("null")?0:ids[2]);
			mapVo.put("remark", ids[3].equals("null")?0:ids[3]);
			mapVo.put("last_year_income", ids[4]);
			if("-1".equals(ids[5])){
				mapVo.put("dept_suggest_sum", "");
			}else{
				mapVo.put("dept_suggest_sum", ids[5]);
			}
			
			mapVo.put("count_value", "");
			
			mapVo.put("hos_suggest", "");
			
			//添加BUDG_HOS_RESOLVE_RATE表中字段GROW_RATE(增长比例),RESOLVE_RATE(分解比例)暂设为空
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
		
       
		String budgMedIncomeHosYearJson = budgMedIncomeHosYearService.addBatch(listVo);

		return JSONObject.parseObject(budgMedIncomeHosYearJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 医院年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/			
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveHYInBudgUpdatePage", method = RequestMethod.GET)
	public String resolveHYInBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgMedIncomeHosYear budgMedIncomeHosYear = new BudgMedIncomeHosYear();
    
		budgMedIncomeHosYear = budgMedIncomeHosYearService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedIncomeHosYear.getGroup_id());
		mode.addAttribute("hos_id", budgMedIncomeHosYear.getHos_id());
		mode.addAttribute("copy_code", budgMedIncomeHosYear.getCopy_code());
		mode.addAttribute("year", budgMedIncomeHosYear.getYear());
		mode.addAttribute("subj_code", budgMedIncomeHosYear.getSubj_code());
		mode.addAttribute("count_value", budgMedIncomeHosYear.getCount_value());
		mode.addAttribute("budg_value", budgMedIncomeHosYear.getBudg_value());
		mode.addAttribute("remark", budgMedIncomeHosYear.getRemark());
		mode.addAttribute("last_year_income", budgMedIncomeHosYear.getLast_year_income());
		mode.addAttribute("hos_suggest", budgMedIncomeHosYear.getHos_suggest());
		mode.addAttribute("dept_suggest_sum", budgMedIncomeHosYear.getDept_suggest_sum());
		
		return "hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveHYInBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/updateResolveHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateResolveHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("budg_value", ids[2]);
			mapVo.put("remark", ids[3]);
			mapVo.put("last_year_income", ids[4]);
			if("-1".equals(ids[5])){
				mapVo.put("dept_suggest_sum", "");
			}else{
				mapVo.put("dept_suggest_sum", ids[5]);
			}
			
			mapVo.put("count_value", "");
			
			mapVo.put("hos_suggest", "");
			
			//添加BUDG_HOS_RESOLVE_RATE表中字段GROW_RATE(增长比例),RESOLVE_RATE(分解比例)暂设为空
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
	  
		String budgMedIncomeHosYearJson = budgMedIncomeHosYearService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgMedIncomeHosYearJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/addOrUpdateResolveHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateResolveHYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMedIncomeHosYearJson ="";
		

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
	  
		budgMedIncomeHosYearJson = budgMedIncomeHosYearService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedIncomeHosYearJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/deleteResolveHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteResolveHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("subj_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMedIncomeHosYearJson = budgMedIncomeHosYearService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedIncomeHosYearJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/queryResolveHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResolveHYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMedIncomeHosYear = budgMedIncomeHosYearService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeHosYear);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院年度医疗预算分解预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveHYInBudgImportPage", method = RequestMethod.GET)
	public String resolveHYInbudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/resolveHYInBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院年度医疗预算分解预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown","医院年度医疗预算分解预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院年度医疗预算分解预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/readResolveHYInBudgFiles",method = RequestMethod.POST)  
    public String readResolveHYInbudgFiles(Plupload plupload,HttpServletRequest request,
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
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
						errMap.put("year", temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						errMap.put("subj_code", temp[1]);
			    		mapVo.put("subj_code", temp[1]);
			    		
			    		BudgIncomeSubj income = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
						if(income == null ){
							err_sb.append("该年度收入预算科目编码不存在;");
						}
					
					} else {
						
						err_sb.append("收入预算科目编码为空 ;");
						
					}
					
					if(ExcelReader.validExceLColunm(temp, 2)){
						
						errMap.put("last_year_income", temp[2]);
						mapVo.put("last_year_income", temp[2]);
						
					}else{
						
						err_sb.append("上年收入为空");
					}
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						errMap.put("budg_value", temp[3]);
			    		mapVo.put("budg_value", temp[3]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					
					if(ExcelReader.validExceLColunm(temp, 4)){
						errMap.put("grow_rate", temp[4]);
						mapVo.put("grow_rate", temp[4]);
					}else{
						err_sb.append("增长比例为空");
						
					}
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						errMap.put("remark", temp[5]);
			    		mapVo.put("remark", temp[5]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						errMap.put("dept_suggest_sum", temp[6]);
			    		mapVo.put("dept_suggest_sum", temp[6]);
					
					}else{
						mapVo.put("dept_suggest_sum", "");
					}
					 
					
				BudgMedIncomeHosYear data_exc_extis = budgMedIncomeHosYearService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				} else {
					
					mapVo.put("count_value", "");
					mapVo.put("resolve_rate", "");
					mapVo.put("hos_suggest", "");
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgMedIncomeHosYearService.addBatch(addList);
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
	 * 批量添加数据 医院年度医疗预算分解预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/addBatchResolveHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchResolveHYInbudgUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeHosYear> list_err = new ArrayList<BudgMedIncomeHosYear>();
		
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
			
			BudgMedIncomeHosYear budgMedIncomeHosYear = new BudgMedIncomeHosYear();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgMedIncomeHosYear.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedIncomeHosYear.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgMedIncomeHosYear.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgMedIncomeHosYear.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgMedIncomeHosYear.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					/*if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgMedIncomeHosYear.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_value"))) {
						
					budgMedIncomeHosYear.setGrow_value(Double.valueOf((String)jsonObj.get("grow_value")));
		    		mapVo.put("grow_value", jsonObj.get("grow_value"));
		    		} else {
						
						err_sb.append("增加额为空  ");
						
					}*/
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
						
					budgMedIncomeHosYear.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
		    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("hos_suggest"))) {
						
					budgMedIncomeHosYear.setHos_suggest(Double.valueOf((String)jsonObj.get("hos_suggest")));
		    		mapVo.put("hos_suggest", jsonObj.get("hos_suggest"));
		    		} else {
						
						err_sb.append("医院意见为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_suggest_sum"))) {
						
					budgMedIncomeHosYear.setDept_suggest_sum(Double.valueOf((String)jsonObj.get("dept_suggest_sum")));
		    		mapVo.put("dept_suggest_sum", jsonObj.get("dept_suggest_sum"));
		    		} else {
						
						err_sb.append("科室意见汇总为空  ");
						
					}
					
					
					BudgMedIncomeHosYear data_exc_extis = budgMedIncomeHosYearService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeHosYear.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeHosYear);
					
				} else {
			  
					String dataJson = budgMedIncomeHosYearService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeHosYear data_exc = new BudgMedIncomeHosYear();
			
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
	 * 根据科目查询上年收入
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/queryLastYearIncome", method = RequestMethod.POST)
	@ResponseBody
	public String queryLastYearIncome(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
	
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))- 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgMedIncomeHosYearService.queryLastYearIncome(mapVo) ;

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
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("is_single_count", "0");//是否独立核算 0 否 1 是

			// 查询要生成的数据
			List<Map<String,Object>> list = budgMedIncomeHosYearService.queryData(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【医院年度业务预算编制方案】数据 或数据已全部生成.\"}");
			}
			
			for(Map<String, Object> item : list ){
					
				//构建 查询上年业务量  参数 Map
				Map<String,Object> paraMap =  new HashMap<String,Object>();
				
				paraMap.put("group_id", SessionManager.getGroupId())   ;
				paraMap.put("hos_id", SessionManager.getHosId())   ;
				paraMap.put("copy_code", SessionManager.getCopyCode())   ;
				paraMap.put("subj_code", item.get("subj_code"));
				paraMap.put("year", Integer.parseInt(String.valueOf(item.get("year")))-1);
				
				//查询上年业务量
			
					
					item.put("last_year_income", "");
				
				
				item.put("count_value", "");
				item.put("budg_value", "");
				item.put("remark", "");
				item.put("hos_suggest", "");
				item.put("dept_suggest_sum", "");
		        listVo.add(item); 
			}
			
			String budgWorkHosYearJson = null ;
			
			if(listVo.size() > 0 ){
				
				budgMedIncomeHosYearService.addGenerateData(listVo) ;
				
				budgWorkHosYearJson =  "{\"msg\":\"操作成功.\",\"state\":\"true\"}" ;
		         
			}else{
				
				budgWorkHosYearJson = "{\"error\":\"数据已全部生成,无需再次生成.\",\"state\":\"false\"}";
			}
			
			return JSONObject.parseObject(budgWorkHosYearJson);	
	}
	/**
	 * @Description 
	 * 生成 分解比例维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/generateResolveRate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateResolveRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("is_single_count", "0");//是否独立核算 0 否 1 是

			// 查询要生成的数据
			List<Map<String,Object>> list = budgMedIncomeHosYearService.queryIncome(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【医院年度业务预算编制方案】数据 或数据已全部生成.\"}");
			}
			
			for(Map<String, Object> item : list ){
					
				//构建 查询上年业务量  参数 Map
				Map<String,Object> paraMap =  new HashMap<String,Object>();
				
				paraMap.put("group_id", SessionManager.getGroupId())   ;
				paraMap.put("hos_id", SessionManager.getHosId())   ;
				paraMap.put("copy_code", SessionManager.getCopyCode())   ;
				paraMap.put("subj_code", item.get("subj_code"));
				paraMap.put("year", Integer.parseInt(String.valueOf(item.get("year")))-1);
				
				//查询上年业务量
				//item.put("last_year_income", "");
				item.put("count_value", "");
				item.put("budg_value", "");
				item.put("remark", "");
				item.put("hos_suggest", "");
				item.put("dept_suggest_sum", "");
				
				item.put("resolve_rate", "");
				item.put("grow_rate", "");
		        listVo.add(item); 
			}
			
			String budgWorkHosYearJson = null ;
			
			if(listVo.size() > 0 ){
				
				budgMedIncomeHosYearService.generateResolveRate(listVo) ;
				
				budgWorkHosYearJson =  "{\"msg\":\"操作成功.\",\"state\":\"true\"}" ;
		         
			}else{
				
				budgWorkHosYearJson = "{\"error\":\"数据已全部生成,无需再次生成.\",\"state\":\"false\"}";
			}
			
			return JSONObject.parseObject(budgWorkHosYearJson);	
	}
	/**
	 * 医院年度  计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/resolvebudg/collect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgWorkDeptYearUp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("is_single_count", "0");//是否独立核算（IS_SINGLE_COUNT）：0否，1是
	
		String collect  = null ;
		
		try {
			
			collect = budgMedIncomeHosYearService.collect(mapVo) ;
			
		} catch (Exception e) {
			
			collect = e.getMessage() ;
		}
		

		return JSONObject.parseObject(collect);

	}
}

