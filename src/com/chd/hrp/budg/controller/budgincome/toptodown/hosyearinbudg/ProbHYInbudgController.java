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

import com.alibaba.fastjson.JSON;
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
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.budgmedincomehyrate.BudgMedIncomeHyRateService;
import com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg.BudgHosIndependentSubjService;
/**
 * 
 * @Description:
 * 医院年度医疗预算概率预算 
 * @Table:
 * BUDG_HOS_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class ProbHYInbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(ProbHYInbudgController.class);
	
	//引入Service服务
	@Resource(name = "budgHosIndependentSubjService")
	private final BudgHosIndependentSubjService budgHosIndependentSubjService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
   
	@Resource(name="budgMedIncomeHyRateService")
	private final BudgMedIncomeHyRateService budgMedIncomeHyRateService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/probHYInBudgMainPage", method = RequestMethod.GET)
	public String probHYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/probHYInBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/probHYInBudgAddPage", method = RequestMethod.GET)
	public String probHYInBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/probHYInBudgAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院年度医疗预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/saveProbHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveZeroHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("subj_code", ids[1]);
			mapVo.put("count_value", ids[2]);
			mapVo.put("budg_value", ids[3]);
			mapVo.put("remark", ids[4]);
			
			//构建 查询上年收入参数Map 
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("group_id", SessionManager.getGroupId())   ;
			map.put("hos_id", SessionManager.getHosId())   ;
			map.put("copy_code", SessionManager.getCopyCode())   ;
			map.put("year", Integer.parseInt(ids[0]) -1 )   ;
			map.put("subj_code", ids[1]);
			
			Map<String,Object> incomeMap = JSONObject.parseObject(budgHosIndependentSubjService.queryLastYearIncome(map)) ;
			
			if(incomeMap != null ){
				if(incomeMap.get("last_year_income") == null){
					
					mapVo.put("last_year_income", "");
					
				}else{
					
					mapVo.put("last_year_income", String.valueOf(incomeMap.get("last_year_income")));
					
				}
				
			}else{
				
				mapVo.put("last_year_income", "");
			}
			
			
			mapVo.put("grow_rate", "");
			mapVo.put("grow_value", "");
			
			mapVo.put("rowNo", ids[5]);
			
			mapVo.put("flag", ids[6]);
			
			if(!"-1".equals(ids[7])){
				JSONArray json = JSONArray.parseArray(String.valueOf(ids[7]));
				List<Map<String,Object>> rateList = new ArrayList<Map<String,Object>>();
				
				Iterator it=json.iterator();
				while(it.hasNext()){
					JSONObject jsonObj = JSON.parseObject(it.next().toString());
					Map<String,Object> rateMap = new HashMap<String,Object>();
					
					rateMap.put("group_id", SessionManager.getGroupId());
					rateMap.put("hos_id", SessionManager.getHosId());
					rateMap.put("copy_code", SessionManager.getCopyCode());
					rateMap.put("year",ids[0]);//年度
					rateMap.put("subj_code",ids[1]);//指标编码
					rateMap.put("measure_name", jsonObj.get("measure_name"));//运营尺度名称
					rateMap.put("measure_value",  Double.parseDouble(String.valueOf(jsonObj.get("measure_value"))));//运营预期
					rateMap.put("rate",  Double.parseDouble(String.valueOf(jsonObj.get("rate"))));//概率
					rateMap.put("count_value",  Double.parseDouble(String.valueOf(jsonObj.get("count_value"))));//计算值
					rateList.add(rateMap);
				}
				mapVo.put("rateList", rateList);
			}
			listVo.add(mapVo);
		}
       
		String budgHosIndependentSubjJson = null;
		
		try {
			
			budgHosIndependentSubjJson = budgHosIndependentSubjService.save(listVo);
			
		} catch (Exception e) {
			
			budgHosIndependentSubjJson = e.getMessage();
		}
				

		return JSONObject.parseObject(budgHosIndependentSubjJson);
		
	}
	
	/**
	 * @Description 
	 * 添加数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/addProbHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProbHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String budgHosIndependentSubjJson = "";
		
		String errStr ="";
		
		try {
			
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			for ( String id: paramVo.split("@,")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("year", ids[0])   ;
				mapVo.put("subj_code", ids[1]);
				mapVo.put("count_value", ids[2]);
				mapVo.put("budg_value", ids[3]);
				mapVo.put("remark", ids[4]);
				if("-1".equals(ids[5])){
					mapVo.put("dept_suggest_sum", "0");
				}else{
					mapVo.put("dept_suggest_sum", ids[5]);
				}
				
				mapVo.put("last_year_income","0");
				
				mapVo.put("grow_rate", "0");
				mapVo.put("grow_value", "0");
				mapVo.put("hos_suggest", "0");
				BudgHosIndependentSubj budgHosIndependentSubj = budgHosIndependentSubjService.queryByCode(mapVo);
				if(budgHosIndependentSubj!=null){
					errStr += ids[0]+"年"+ids[1]+ "指标;" ;
				}
				JSONArray json = JSONArray.parseArray(String.valueOf(ids[6]));
				List<Map<String,Object>> rateList = new ArrayList<Map<String,Object>>();
				
				Iterator it=json.iterator();
				while(it.hasNext()){
					JSONObject jsonObj = JSON.parseObject(it.next().toString());
					Map<String,Object> rateMap = new HashMap<String,Object>();
					
					rateMap.put("group_id", SessionManager.getGroupId());
					rateMap.put("hos_id", SessionManager.getHosId());
					rateMap.put("copy_code", SessionManager.getCopyCode());
					rateMap.put("year",ids[0]);//年度
					rateMap.put("subj_code",ids[1]);//指标编码
					rateMap.put("measure_name", jsonObj.get("measure_name"));//运营尺度名称
					rateMap.put("measure_value",  jsonObj.get("measure_value"));//运营预期
					rateMap.put("rate",  jsonObj.get("rate"));//概率
					rateMap.put("count_value",  jsonObj.get("count_value"));//计算值
					rateList.add(rateMap);
				}
				mapVo.put("rateList", rateList);
				listVo.add(mapVo);
			}
			if(errStr != ""){
				return JSONObject.parseObject("{\"error\":\"添加失败,以下数据："+errStr+"已存在\"}");
			}
			//System.out.println(ChdJson.toJson(listVo));
			budgHosIndependentSubjJson = budgHosIndependentSubjService.addBatch(listVo);
			

		} catch (Exception e) {
			budgHosIndependentSubjJson = e.getMessage();
		}
       
		return JSONObject.parseObject(budgHosIndependentSubjJson);
		
	}
	/**	
	 * @Description 
	 * 更新跳转页面 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/			
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/probHYInBudgUpdatePage", method = RequestMethod.GET)
	public String probHYInBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		return "hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/probHYInBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/updateProbHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProbHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String budgHosIndependentSubjJson = "";
		try {
			
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			for ( String id: paramVo.split("@,")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("year", ids[0])   ;
				mapVo.put("subj_code", ids[1]);
				mapVo.put("count_value", ids[2]);
				mapVo.put("budg_value", ids[3]);
				mapVo.put("remark", ids[4]);
				if("-1".equals(ids[5])){
					mapVo.put("dept_suggest_sum", "");
				}else{
					mapVo.put("dept_suggest_sum", ids[5]);
				}
				
				mapVo.put("last_year_income","");
				
				mapVo.put("grow_rate", "");
				mapVo.put("grow_value", "");
				mapVo.put("hos_suggest", "");
				
				JSONArray json = JSONArray.parseArray(String.valueOf(ids[6]));
				List<Map<String,Object>> rateList = new ArrayList<Map<String,Object>>();
				
				Iterator it=json.iterator();
				while(it.hasNext()){
					JSONObject jsonObj = JSON.parseObject(it.next().toString());
					Map<String,Object> rateMap = new HashMap<String,Object>();
					
					rateMap.put("group_id", SessionManager.getGroupId());
					rateMap.put("hos_id", SessionManager.getHosId());
					rateMap.put("copy_code", SessionManager.getCopyCode());
					rateMap.put("year",ids[0]);//年度
					rateMap.put("subj_code",ids[1]);//指标编码
					rateMap.put("measure_name", jsonObj.get("measure_name"));//运营尺度名称
					rateMap.put("measure_value",  jsonObj.get("measure_value"));//运营预期
					rateMap.put("rate",  jsonObj.get("rate"));//概率
					rateMap.put("count_value",  jsonObj.get("count_value"));//计算值
					rateList.add(rateMap);
				}
				mapVo.put("rateList", rateList);
				//根据主键编码删除概率信息
				budgMedIncomeHyRateService.delete(mapVo);
				
				listVo.add(mapVo);
			}
			//更新数据
			//System.out.println(ChdJson.toJson(listVo));
				budgHosIndependentSubjJson = budgHosIndependentSubjService.updateBatch(listVo);
			}catch (Exception e) {
				budgHosIndependentSubjJson = e.getMessage();
			}
		
		return JSONObject.parseObject(budgHosIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/addOrUpdateProbHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateProbHYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	 * 删除数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/deleteProbHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProbHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				
				mapVo.put("is_prob","1");
				
				listVo.add(mapVo);
	      
	    }
	    
		String budgHosIndependentSubjJson = budgHosIndependentSubjService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgHosIndependentSubjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/queryProbHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryProbHYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	 * 导入跳转页面 医院年度医疗预算概率预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/probHYInBudgImportPage", method = RequestMethod.GET)
	public String probHYInbudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/probHYInBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院年度医疗预算概率预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown","医院年度医疗预算概率预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院年度医疗预算概率预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/readProbHYInBudgFiles",method = RequestMethod.POST)  
    public String readProbHYInbudgFiles(Plupload plupload,HttpServletRequest request,
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
		    		
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
						budgHosIndependentSubj.setYear(temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 1)) {
							
						budgHosIndependentSubj.setSubj_code(temp[1]);
			    		mapVo.put("subj_code", temp[1]);
			    		mapVo.put("edit_method","04");
			    		
			    		int index=budgHosIndependentSubjService.queryBudgMedIncomeEditPlanByCode(mapVo);
						if(index == 0 ){
							err_sb.append("该年度收入预算科目编码的编制方法不是零基预算或数据不存在;");
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
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						budgHosIndependentSubj.setBudg_value(Double.valueOf(temp[3]));
			    		mapVo.put("budg_value", temp[3]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgHosIndependentSubj.setRemark(temp[4]);
			    		mapVo.put("remark", temp[4]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						budgHosIndependentSubj.setDept_suggest_sum(Double.valueOf(temp[5]));
			    		mapVo.put("dept_suggest_sum", temp[5]);
					
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
					mapVo.put("grow_rate", "");
					mapVo.put("grow_value", "");
					
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
	 * 批量添加数据 医院年度医疗预算概率预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/addBatchProbHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchProbHYInbudgUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
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
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/queryLastYearIncome", method = RequestMethod.POST)
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
		
		mapVo.put("edit_mehtod", "02") ;
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))- 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgHosIndependentSubjService.queryLastYearIncome(mapVo) ;

		return str;

	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度收入预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/queryBudgMedIncomeHyRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMedIncomeHyRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMedIncomeHyRate = budgMedIncomeHyRateService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeHyRate);
		
	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/probbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("edit_method", "04");//编制方法（EDIT_METHOD）01零基预算  02增量预算 03确定预算 	04概率预算

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
				paraMap.put("edit_method", "04");
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
}

