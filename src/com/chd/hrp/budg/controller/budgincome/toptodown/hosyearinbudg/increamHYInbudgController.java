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
import com.chd.hrp.budg.entity.BudgHosIndependentSubj;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.entity.BudgMedIncomeEditPlan;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg.BudgHosIndependentSubjService;
/**
 * 
 * @Description:
 * 医院年度医疗预算增量预算 
 * @Table:
 * BUDG_HOS_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class increamHYInbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(increamHYInbudgController.class);
	
	//引入Service服务
	@Resource(name = "budgHosIndependentSubjService")
	private final BudgHosIndependentSubjService budgHosIndependentSubjService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
   
	
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/increamHYInBudgMainPage", method = RequestMethod.GET)
	public String increamHYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/increamHYInBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/increamHYInBudgAddPage", method = RequestMethod.GET)
	public String increamHYInBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/increamHYInBudgAdd";

	}
	
	/**
	 * @Description 
	 * 增长比例获取 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/getGrowRatePage", method = RequestMethod.GET)
	public String getGrowRatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("year")));
		
		mode.addAttribute("year", year);
		mode.addAttribute("start_year", year-2);
		mode.addAttribute("end_year", year-1);
		
		return "hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/getGrowRate";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/saveIncreamHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveIncreamHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			if("null".equals(ids[2])){
				mapVo.put("last_year_income", "");
			}else{
				mapVo.put("last_year_income", ids[2]);
			}
			
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
			
			budgHosIndependentSubjJson = budgHosIndependentSubjService.save(listVo);

		} catch (Exception e) {
			
			budgHosIndependentSubjJson = e.getMessage() ;
		}
		
		return JSONObject.parseObject(budgHosIndependentSubjJson);
		
	}
	
	/**
	 * @Description 
	 * 添加数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/addIncreamHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addIncreamHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("last_year_income", ids[2]);
			mapVo.put("grow_rate", ids[3]);
			mapVo.put("grow_value", ids[4]);
			mapVo.put("count_value", ids[5]);
			mapVo.put("budg_value", ids[6]);
			mapVo.put("remark", ids[7]);
			mapVo.put("dept_suggest_sum", ids[8]);
			mapVo.put("hos_suggest", ids[9]);
			
			mapVo.put("rowNo", ids[10]) ;
			mapVo.put("flag", ids[11]) ;
			listVo.add(mapVo);
		}
		
       
		String budgHosIndependentSubjJson = null ;
		try {
			
			budgHosIndependentSubjJson = budgHosIndependentSubjService.addBatch(listVo);
			
		} catch (Exception e) {
			
			budgHosIndependentSubjJson = e.getMessage() ;
		}
		

		return JSONObject.parseObject(budgHosIndependentSubjJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/			
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/increamHYInBudgUpdatePage", method = RequestMethod.GET)
	public String increamHYInBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgHosIndependentSubj budgHosIndependentSubj = new BudgHosIndependentSubj();
    
		budgHosIndependentSubj = budgHosIndependentSubjService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgHosIndependentSubj.getGroup_id());
		mode.addAttribute("hos_id", budgHosIndependentSubj.getHos_id());
		mode.addAttribute("copy_code", budgHosIndependentSubj.getCopy_code());
		mode.addAttribute("year", budgHosIndependentSubj.getYear());
		mode.addAttribute("subj_code", budgHosIndependentSubj.getSubj_code());
		mode.addAttribute("count_value", budgHosIndependentSubj.getCount_value());
		mode.addAttribute("budg_value", budgHosIndependentSubj.getBudg_value());
		mode.addAttribute("remark", budgHosIndependentSubj.getRemark());
		mode.addAttribute("grow_rate", budgHosIndependentSubj.getGrow_rate());
		mode.addAttribute("grow_value", budgHosIndependentSubj.getGrow_value());
		mode.addAttribute("last_year_income", budgHosIndependentSubj.getLast_year_income());
		mode.addAttribute("hos_suggest", budgHosIndependentSubj.getHos_suggest());
		mode.addAttribute("dept_suggest_sum", budgHosIndependentSubj.getDept_suggest_sum());
		
		return "hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/increamHYInBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/updateIncreamHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIncreamHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
			mapVo.put("count_value", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("grow_rate", ids[5]);
			mapVo.put("grow_value", ids[6]);
			mapVo.put("last_year_income", ids[7]);
			mapVo.put("hos_suggest", ids[8]);
			if("-1".equals(ids[9])){
				mapVo.put("dept_suggest_sum", "");
			}else{
				mapVo.put("dept_suggest_sum", ids[9]);
			}
			
			listVo.add(mapVo);
		}
	  
		String budgHosIndependentSubjJson =  null ;
		try {
			
			budgHosIndependentSubjJson =  budgHosIndependentSubjService.updateBatch(listVo);
			
		} catch (Exception e) {
			
			budgHosIndependentSubjJson = e.getMessage() ;
		}
		
		
		return JSONObject.parseObject(budgHosIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/addOrUpdateIncreamHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateIncreamHYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgHosIndependentSubjJson ="";
		

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
	  
		budgHosIndependentSubjJson = budgHosIndependentSubjService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgHosIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/deleteIncreamHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIncreamHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgHosIndependentSubjJson = null ;
		
		try {
			
			budgHosIndependentSubjJson = budgHosIndependentSubjService.deleteBatch(listVo);
			
		} catch (Exception e) {

			budgHosIndependentSubjJson = e.getMessage();
		}
		
			
	   return JSONObject.parseObject(budgHosIndependentSubjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/queryIncreamHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIncreamHYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgHosIndependentSubj = budgHosIndependentSubjService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosIndependentSubj);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院年度医疗预算增量预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/increamHYInBudgImportPage", method = RequestMethod.GET)
	public String increamHYInbudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/increamHYInBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院年度医疗预算增量预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown","医院年度医疗预算增量预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院年度医疗预算增量预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/readIncreamHYInBudgFiles",method = RequestMethod.POST)  
    public String readIncreamHYInbudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgHosIndependentSubj> list_err = new ArrayList<BudgHosIndependentSubj>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgHosIndependentSubj budgHosIndependentSubj = new BudgHosIndependentSubj();
				
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
						
						budgHosIndependentSubj.setYear(temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgHosIndependentSubj.setSubj_code(temp[1]);
			    		mapVo.put("subj_code", temp[1]);
			    		mapVo.put("edit_method","02");
			    		
			    		int index=budgHosIndependentSubjService.queryBudgMedIncomeEditPlanByCode(mapVo);
						if(index == 0 ){
							err_sb.append("该年度收入预算科目编码的编制方法不是增量预算或数据不存在;");
						}
					
					} else {
						
						err_sb.append("收入预算科目编码为空 ;");
						
					}
					 
					if(ExcelReader.validExceLColunm(temp, 2)){
						budgHosIndependentSubj.setLast_year_income(Double.valueOf(temp[2]));
						mapVo.put("last_year_income", temp[2]);
					}else{
						err_sb.append("上年收入为空;");
					}
					
					
					if(!ExcelReader.validExceLColunm(temp,3) && !ExcelReader.validExceLColunm(temp,4)){
						
						err_sb.append("增长比例和增长额两项不能同时为空;");
						
					}else if (ExcelReader.validExceLColunm(temp,3) && ExcelReader.validExceLColunm(temp,4)){
						err_sb.append("增长比例和增长额两项只能填写其中一项;");
					}
					
					
					//根据数据编码查询取值方法并判断
					String getway=budgHosIndependentSubjService.queryGetWayFromBudgMedIncomeEditPlan(mapVo);
					if(getway!=null&&getway!=""){
						//04 增长比例  05 增长额
						if("04".equals(getway)){
							if(ExcelReader.validExceLColunm(temp, 3)){
								budgHosIndependentSubj.setGrow_rate(Double.valueOf(temp[3]));
								
								mapVo.put("grow_rate", temp[3]);
								if(ExcelReader.validExceLColunm(temp, 4)){
									budgHosIndependentSubj.setGrow_value(Double.valueOf(temp[3]));
									err_sb.append("该指标取值方法不是历史数据+增长额,增长额不得填写;");
								}else{
									budgHosIndependentSubj.setGrow_value(null);
									mapVo.put("grow_value", "");
								}
								
							}else{
								err_sb.append("该指标取值方法为历史数据*增长比例,增长比例不得为空;");
							}
						}
							
						if("05".equals(getway)){
							if(ExcelReader.validExceLColunm(temp, 3)){
								
								budgHosIndependentSubj.setGrow_rate(Double.valueOf(temp[3]));
								err_sb.append("该指标取值方法不是历史数据*增长比例,增长比例不得填写;");
							}else{
								budgHosIndependentSubj.setGrow_rate(null);
								mapVo.put("grow_rate", "");
							}
							if(ExcelReader.validExceLColunm(temp, 4)){
								budgHosIndependentSubj.setGrow_value(Double.valueOf(temp[4]));
								mapVo.put("grow_rate", "");
								mapVo.put("grow_value", temp[4]);
								
							}else{
								err_sb.append("该指标取值方法为历史数据+增长额,增长额不得为空;");
							}
						}		
							
					}else{
						//如果取值方法为空   则页面显示数据完整信息   并添加错误说明
						if(ExcelReader.validExceLColunm(temp, 3)){
							budgHosIndependentSubj.setGrow_rate(Double.valueOf(temp[3]));
						}else{
							budgHosIndependentSubj.setGrow_rate(null);
						}
						
						if(ExcelReader.validExceLColunm(temp, 4)){
							budgHosIndependentSubj.setGrow_value(Double.valueOf(temp[4]));
						}else{
							budgHosIndependentSubj.setGrow_value(null);
						}
						
						err_sb.append("该指标填写年度编制方案没有维护或编制方案中编制方法不是增量预算;");
					}
					
					
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						budgHosIndependentSubj.setBudg_value(Double.valueOf(temp[5]));
			    		mapVo.put("budg_value", temp[5]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					
					
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						budgHosIndependentSubj.setRemark(temp[6]);
			    		mapVo.put("remark", temp[6]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					if (ExcelReader.validExceLColunm(temp,7)) {
						
						budgHosIndependentSubj.setDept_suggest_sum(Double.valueOf(temp[7]));
			    		mapVo.put("dept_suggest_sum", temp[7]);
					
					}else{
						mapVo.put("dept_suggest_sum", "");
					}
					 
					
				BudgHosIndependentSubj data_exc_extis = budgHosIndependentSubjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosIndependentSubj.setError_type(err_sb.toString());
					
					list_err.add(budgHosIndependentSubj);
					
				} else {
					
					mapVo.put("count_value", "");
					
					mapVo.put("hos_suggest", "");
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgHosIndependentSubjService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			BudgHosIndependentSubj data_exc = new BudgHosIndependentSubj();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院年度医疗预算增量预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/addBatchIncreamHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchIncreamHYInbudgUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgHosIndependentSubj> list_err = new ArrayList<BudgHosIndependentSubj>();
		
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
			
			BudgHosIndependentSubj budgHosIndependentSubj = new BudgHosIndependentSubj();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
						budgHosIndependentSubj.setYear((String)jsonObj.get("year"));
						
			    		mapVo.put("year", jsonObj.get("year"));
		    		
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgHosIndependentSubj.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
						
					budgHosIndependentSubj.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
		    		mapVo.put("count_value", jsonObj.get("count_value"));
		    		} else {
						
						err_sb.append("计算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgHosIndependentSubj.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgHosIndependentSubj.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
						
					budgHosIndependentSubj.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
		    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
		    		} else {
						
						err_sb.append("增长比例为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("grow_value"))) {
						
					budgHosIndependentSubj.setGrow_value(Double.valueOf((String)jsonObj.get("grow_value")));
		    		mapVo.put("grow_value", jsonObj.get("grow_value"));
		    		} else {
						
						err_sb.append("增加额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
						
					budgHosIndependentSubj.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
		    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
		    		} else {
						
						err_sb.append("上年收入为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("hos_suggest"))) {
						
					budgHosIndependentSubj.setHos_suggest(Double.valueOf((String)jsonObj.get("hos_suggest")));
		    		mapVo.put("hos_suggest", jsonObj.get("hos_suggest"));
		    		} else {
						
						err_sb.append("医院意见为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_suggest_sum"))) {
						
					budgHosIndependentSubj.setDept_suggest_sum(Double.valueOf((String)jsonObj.get("dept_suggest_sum")));
		    		mapVo.put("dept_suggest_sum", jsonObj.get("dept_suggest_sum"));
		    		} else {
						
						err_sb.append("科室意见汇总为空  ");
						
					}
					
					
				BudgHosIndependentSubj data_exc_extis = budgHosIndependentSubjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosIndependentSubj.setError_type(err_sb.toString());
					
					list_err.add(budgHosIndependentSubj);
					
				} else {
			  
					String dataJson = budgHosIndependentSubjService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgHosIndependentSubj data_exc = new BudgHosIndependentSubj();
			
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
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/queryLastYearIncome", method = RequestMethod.POST)
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
		
		mapVo.put("edit_method", "02") ;
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))- 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgHosIndependentSubjService.queryLastYearIncome(mapVo) ;

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
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("edit_method", "02");//编制方法（EDIT_METHOD）01零基预算  02增量预算 03确定预算 	04概率预算

			// 查询要生成的数据
			List<Map<String,Object>> list = budgHosIndependentSubjService.queryData(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【医院年度业务预算编制方案】数据 或数据已全部生成.\"}");
			}
			
			for(Map<String, Object> item : list ){
					
				//构建 查询上年业务量  参数 Map
				Map<String,Object> paraMap =  new HashMap<String,Object>();
				
				paraMap.put("group_id", SessionManager.getGroupId())   ;
				paraMap.put("hos_id", SessionManager.getHosId())   ;
				paraMap.put("copy_code", SessionManager.getCopyCode())   ;
				paraMap.put("budg_year", item.get("year"));
				paraMap.put("subj_code", item.get("subj_code"));
				paraMap.put("year", Integer.parseInt(String.valueOf(item.get("year")))-1);
				
				paraMap.put("budg_level", "01");
				paraMap.put("edit_method", "02");
				item.put("last_year_income", "");
				item.put("count_value", "");
				item.put("budg_value", "");
				item.put("remark", "");
				item.put("grow_rate", "");
				item.put("grow_value", "");
				item.put("hos_suggest", "");
				item.put("dept_suggest_sum", "");
		        listVo.add(item); 
			}
			
			String budgWorkHosYearJson = null ;
			
				
			if(listVo.size() > 0 ){
				
				budgHosIndependentSubjService.addBatch(listVo) ;
				
				budgWorkHosYearJson =  "{\"msg\":\"操作成功.\",\"state\":\"true\"}" ;
		         
			}else{
				
				budgWorkHosYearJson = "{\"error\":\"数据已全部生成,无需再次生成.\",\"state\":\"false\"}";
			}
			
			return JSONObject.parseObject(budgWorkHosYearJson);	
	} 	
	/**
	 * 增长比例获取
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/getGrowRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getGrowRate(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String str  = budgHosIndependentSubjService.getGrowRate(mapVo) ;

		return JSONObject.parseObject(str);

	}
	/**
	 * @Description 
	 * 医院年度医疗收入预算增量预算  更新 增长比例 及相关数据数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/increambudg/updateGrowRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGrowRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
			mapVo.put("grow_rate", ids[2]);
			
			listVo.add(mapVo);
		}
	  
		String budgWorkHosYearJson = null ;
		try {
			budgWorkHosYearJson = budgHosIndependentSubjService.updateGrowRate(listVo);
		} catch (Exception e) {
			budgWorkHosYearJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgWorkHosYearJson);
	}
	
}

