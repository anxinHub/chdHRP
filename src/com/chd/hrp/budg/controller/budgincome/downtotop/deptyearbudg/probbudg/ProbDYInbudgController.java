/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.downtotop.deptyearbudg.probbudg;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgDeptIndependentSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.budgmedincomedyrate.BudgMedIncomeDyRateService;
import com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg.BudgDeptIndependentSubjService;
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
public class ProbDYInbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(ProbDYInbudgController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptIndependentSubjService")
	private final BudgDeptIndependentSubjService budgDeptIndependentSubjService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
   
	@Resource(name="budgMedIncomeDyRateService")
	private final BudgMedIncomeDyRateService budgMedIncomeDyRateService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/probDYInBudgPage", method = RequestMethod.GET)
	public String probDYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/probDYInBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/probDYInBudgAddPage", method = RequestMethod.GET)
	public String probDYInBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/probDYInBudgAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/saveProbDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveProbDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("dept_id", ids[2]);
			mapVo.put("count_value", ids[3]);
			mapVo.put("budg_value", ids[4]);
			mapVo.put("remark", ids[5]);
			
			mapVo.put("rowNo", ids[6]);
			
			mapVo.put("flag", ids[7]);
			
			//构建 查询上年收入参数Map 
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("group_id", SessionManager.getGroupId())   ;
			map.put("hos_id", SessionManager.getHosId())   ;
			map.put("copy_code", SessionManager.getCopyCode())   ;
			map.put("year", Integer.parseInt(ids[0])-1)   ;
			map.put("subj_code", ids[1]);
			
			Map<String,Object> incomeMap = JSONObject.parseObject(budgDeptIndependentSubjService.queryLastYearIncome(map)) ;
			
			if( incomeMap != null ){
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
			mapVo.put("resolve_rate", "");
			
			mapVo.put("state", "");
			mapVo.put("reason", "");
			mapVo.put("refer_value", "");
			
			if(!"-1".equals(ids[8])){
				JSONArray json = JSONArray.parseArray(String.valueOf(ids[8]));
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
					rateMap.put("dept_id", ids[2]);//科室id
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
       
		String budgDeptIndependentSubjJson = null;
		try {
			budgDeptIndependentSubjJson = budgDeptIndependentSubjService.save(listVo);
		} catch (Exception e) {
			budgDeptIndependentSubjJson = e.getMessage();
		}

		return JSONObject.parseObject(budgDeptIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/addProbDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProbDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String budgDeptIndependentSubjJson = "";
		
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
				mapVo.put("dept_id", ids[2]);
				mapVo.put("budg_value", ids[3]);
				mapVo.put("count_value", ids[4]);
				mapVo.put("remark", ids[5]);
				mapVo.put("hos_suggest", ids[6]);
				mapVo.put("hos_suggest_resolve", ids[6]);
				if("-1".equals(ids[7])){
					mapVo.put("dept_suggest_sum", "");
					mapVo.put("dept_suggest", "");
				}else{
					mapVo.put("dept_suggest_sum", ids[7]);
					mapVo.put("dept_suggest", ids[7]);
				}
				
				mapVo.put("last_year_income","0");
				mapVo.put("grow_rate", "0");
				mapVo.put("grow_value", "0");
				mapVo.put("resolve_rate", "");
				
				BudgDeptIndependentSubj budgDeptIndependentSubj = budgDeptIndependentSubjService.queryByCode(mapVo);
				if(budgDeptIndependentSubj!=null){
					errStr += ids[0]+"年"+ids[1]+ "指标;" ;
				}
				JSONArray json = JSONArray.parseArray(String.valueOf(ids[8]));
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
					rateMap.put("dept_id", ids[2]);//科室ID
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
			budgDeptIndependentSubjJson = budgDeptIndependentSubjService.addBatch(listVo);
			

		} catch (Exception e) {
			budgDeptIndependentSubjJson = e.getMessage();
		}
       
		return JSONObject.parseObject(budgDeptIndependentSubjJson);
		
	}
	/**	
	 * @Description 
	 * 更新跳转页面 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/			
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg//probbudg/probDYInBudgUpdatePage", method = RequestMethod.GET)
	public String probDYInBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		BudgDeptIndependentSubj budgDeptIndependentSubj = new BudgDeptIndependentSubj();
    
		budgDeptIndependentSubj = budgDeptIndependentSubjService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptIndependentSubj.getGroup_id());
		mode.addAttribute("hos_id", budgDeptIndependentSubj.getHos_id());
		mode.addAttribute("copy_code", budgDeptIndependentSubj.getCopy_code());
		mode.addAttribute("year", budgDeptIndependentSubj.getYear());
		mode.addAttribute("subj_code", budgDeptIndependentSubj.getSubj_code());
		mode.addAttribute("count_value", budgDeptIndependentSubj.getCount_value());
		mode.addAttribute("budg_value", budgDeptIndependentSubj.getBudg_value());
		mode.addAttribute("remark", budgDeptIndependentSubj.getRemark());
		mode.addAttribute("grow_rate", budgDeptIndependentSubj.getGrow_rate());
		mode.addAttribute("grow_value", budgDeptIndependentSubj.getGrow_value());
		mode.addAttribute("last_year_income", budgDeptIndependentSubj.getLast_year_income());
		mode.addAttribute("hos_suggest", budgDeptIndependentSubj.getHos_suggest());
		mode.addAttribute("dept_suggest_sum", budgDeptIndependentSubj.getDept_suggest_sum());
		
		return "hrp/budg/budgincome/downtotop/deptyearbudg//probbudg/probDYInBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/updateProbDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProbDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String budgDeptIndependentSubjJson = "";
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
				mapVo.put("dept_id", ids[2]);
				mapVo.put("budg_value", ids[3]);
				mapVo.put("count_value", ids[4]);
				mapVo.put("remark", ids[5]);
				mapVo.put("hos_suggest", ids[6]);
				mapVo.put("hos_suggest_resolve", ids[6]);
				if("-1".equals(ids[7])){
					mapVo.put("dept_suggest_sum", "");
					mapVo.put("dept_suggest", "");
				}else{
					mapVo.put("dept_suggest_sum", ids[7]);
					mapVo.put("dept_suggest", ids[7]);
				}
				
				mapVo.put("last_year_income","0");
				mapVo.put("grow_rate", "0");
				mapVo.put("grow_value", "0");
				mapVo.put("resolve_rate", "");
				
				JSONArray json = JSONArray.parseArray(String.valueOf(ids[8]));
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
					rateMap.put("dept_id", ids[2]);//科室ID
					rateMap.put("measure_name", jsonObj.get("measure_name"));//运营尺度名称
					rateMap.put("measure_value",  jsonObj.get("measure_value"));//运营预期
					rateMap.put("rate",  jsonObj.get("rate"));//概率
					rateMap.put("count_value",  jsonObj.get("count_value"));//计算值
					rateList.add(rateMap);
				}
				mapVo.put("rateList", rateList);
				//根据主键编码删除概率信息
				budgMedIncomeDyRateService.delete(mapVo);
				
				listVo.add(mapVo);
			}
			//更新数据
			//System.out.println(ChdJson.toJson(listVo));
				budgDeptIndependentSubjJson = budgDeptIndependentSubjService.updateBatch(listVo);
			}catch (Exception e) {
				budgDeptIndependentSubjJson = e.getMessage();
			}
		
		return JSONObject.parseObject(budgDeptIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg//probbudg/addOrUpdateProbDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateProbDYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptIndependentSubjJson ="";
		

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
	  
		budgDeptIndependentSubjJson = budgDeptIndependentSubjService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/deleteProbDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProbDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("dept_id", ids[5]);
				mapVo.put("is_prob","1");
				
				listVo.add(mapVo);
	      
	    }
	    
		String budgDeptIndependentSubjJson = budgDeptIndependentSubjService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDeptIndependentSubjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度医疗预算概率预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/queryProbDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryProbDYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgDeptIndependentSubj = budgDeptIndependentSubjService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDeptIndependentSubj);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院年度医疗预算概率预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/probDYInBudgImportPage", method = RequestMethod.GET)
	public String probDYInbudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/probDYInBudgImport";

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
	 @RequestMapping(value="hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\downtotop","科室年度医疗预算概率预算模板.xls");
	    
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
	@RequestMapping(value="/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/readProbDYInBudgFiles",method = RequestMethod.POST)  
    public String readProbDYInbudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgDeptIndependentSubj> list_err = new ArrayList<BudgDeptIndependentSubj>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgDeptIndependentSubj budgDeptIndependentSubj = new BudgDeptIndependentSubj();
				
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
						
						budgDeptIndependentSubj.setYear(temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgDeptIndependentSubj.setSubj_code(temp[1]);
			    		mapVo.put("subj_code", temp[1]);
			    		mapVo.put("edit_method","04");
			    		
			    		int index=budgDeptIndependentSubjService.queryBudgMedIncomeEditPlanByCode(mapVo);
						if(index == 0 ){
							err_sb.append("该年度收入预算科目编码的编制方法不是零基预算或数据不存在;");
						}
					
					} else {
						
						err_sb.append("收入预算科目编码为空 ;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						budgDeptIndependentSubj.setDept_id(Long.valueOf(temp[2]));
			    		mapVo.put("dept_id", temp[2]);
						
					} else {
						
						err_sb.append("部门ID为空;");
						
					}
					
					if(ExcelReader.validExceLColunm(temp, 3)){
						budgDeptIndependentSubj.setLast_year_income(Double.valueOf(temp[3]));
						mapVo.put("last_year_income", temp[3]);
						
					}else{
						
						err_sb.append("上年收入为空;");
					}
					
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgDeptIndependentSubj.setBudg_value(Double.valueOf(temp[4]));
			    		mapVo.put("budg_value", temp[4]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						budgDeptIndependentSubj.setRemark(temp[5]);
			    		mapVo.put("remark", temp[5]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						budgDeptIndependentSubj.setDept_suggest_sum(Double.valueOf(temp[6]));
			    		mapVo.put("hos_suggest", temp[6]);
			    		mapVo.put("hos_suggest_resolve", temp[6]);
					
					}else{
						mapVo.put("hos_suggest", "");
						mapVo.put("hos_suggest_resolve", "");
					}
					 
					
				BudgDeptIndependentSubj data_exc_extis = budgDeptIndependentSubjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptIndependentSubj.setError_type(err_sb.toString());
					
					list_err.add(budgDeptIndependentSubj);
					
				} else {
					
					mapVo.put("count_value", "");
					mapVo.put("grow_value", "");
					mapVo.put("grow_rate", "");
					mapVo.put("last_year_income", "");
					
					mapVo.put("state", "");
					mapVo.put("reason", "");
					mapVo.put("refer_value", "");
					
					mapVo.put("resolve_rate", "");
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgDeptIndependentSubjService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			BudgDeptIndependentSubj data_exc = new BudgDeptIndependentSubj();
			
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
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/addBatchProbDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchProbDYInbudgUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDeptIndependentSubj> list_err = new ArrayList<BudgDeptIndependentSubj>();
		
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
				
				BudgDeptIndependentSubj budgDeptIndependentSubj = new BudgDeptIndependentSubj();
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				if (StringTool.isNotBlank(jsonObj.get("year"))) {
					
				budgDeptIndependentSubj.setYear((String)jsonObj.get("year"));
	    		mapVo.put("year", jsonObj.get("year"));
	    		} else {
					
					err_sb.append("年度为空  ");
					
				}
				
				if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
					
				budgDeptIndependentSubj.setSubj_code((String)jsonObj.get("subj_code"));
	    		mapVo.put("subj_code", jsonObj.get("subj_code"));
	    		} else {
					
					err_sb.append("科目编码为空  ");
					
				}
				
				if (StringTool.isNotBlank(jsonObj.get("count_value"))) {
					
				budgDeptIndependentSubj.setCount_value(Double.valueOf((String)jsonObj.get("count_value")));
	    		mapVo.put("count_value", jsonObj.get("count_value"));
	    		} else {
					
					err_sb.append("计算值为空  ");
					
				}
				
				if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
					
				budgDeptIndependentSubj.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
	    		mapVo.put("budg_value", jsonObj.get("budg_value"));
	    		} else {
					
					err_sb.append("预算值为空  ");
					
				}
				
				if (StringTool.isNotBlank(jsonObj.get("remark"))) {
					
				budgDeptIndependentSubj.setRemark((String)jsonObj.get("remark"));
	    		mapVo.put("remark", jsonObj.get("remark"));
	    		} else {
					
					err_sb.append("说明为空  ");
					
				}
				
				if (StringTool.isNotBlank(jsonObj.get("grow_rate"))) {
					
				budgDeptIndependentSubj.setGrow_rate(Double.valueOf((String)jsonObj.get("grow_rate")));
	    		mapVo.put("grow_rate", jsonObj.get("grow_rate"));
	    		} else {
					
					err_sb.append("增长比例为空  ");
					
				}
				
				if (StringTool.isNotBlank(jsonObj.get("grow_value"))) {
					
				budgDeptIndependentSubj.setGrow_value(Double.valueOf((String)jsonObj.get("grow_value")));
	    		mapVo.put("grow_value", jsonObj.get("grow_value"));
	    		} else {
					
					err_sb.append("增加额为空  ");
					
				}
				
				if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
					
				budgDeptIndependentSubj.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
	    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
	    		} else {
					
					err_sb.append("上年收入为空  ");
					
				}
				
				/*if (StringTool.isNotBlank(jsonObj.get("hos_suggest"))) {
					
				budgDeptIndependentSubj.setHos_suggest(Double.valueOf((String)jsonObj.get("hos_suggest")));
	    		mapVo.put("hos_suggest", jsonObj.get("hos_suggest"));
	    		} else {
					
					err_sb.append("医院意见为空  ");
					
				}
				
				if (StringTool.isNotBlank(jsonObj.get("dept_suggest_sum"))) {
					
				budgDeptIndependentSubj.setDept_suggest_sum(Double.valueOf((String)jsonObj.get("dept_suggest_sum")));
	    		mapVo.put("dept_suggest_sum", jsonObj.get("dept_suggest_sum"));
	    		} else {
					
					err_sb.append("科室意见汇总为空  ");
					
				}*/
				mapVo.put("state", "");
				mapVo.put("reason", "");
				mapVo.put("refer_value", "");
						
				BudgDeptIndependentSubj data_exc_extis = budgDeptIndependentSubjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptIndependentSubj.setError_type(err_sb.toString());
					
					list_err.add(budgDeptIndependentSubj);
					
				} else {
			  
					String dataJson = budgDeptIndependentSubjService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDeptIndependentSubj data_exc = new BudgDeptIndependentSubj();
			
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
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/queryLastYearIncome", method = RequestMethod.POST)
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
		
		mapVo.put("edit_mehtod", "04") ;
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))- 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgDeptIndependentSubjService.queryLastYearIncome(mapVo) ;

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
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/queryBudgMedIncomeDYRate", method = RequestMethod.POST)
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
		String budgMedIncomeDyRate = budgMedIncomeDyRateService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeDyRate);
		
	}
	/**
	 * 查询科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/queryBudgDept", method = RequestMethod.POST)
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
		
		String dept = budgDeptIndependentSubjService.queryBudgDept(mapVo);
		return dept;

	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/probbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("edit_method", "04");//编制方法（EDIT_METHOD）01零基预算  02增量预算 03确定预算 	04概率预算
			// 查询要生成的数据
			List<Map<String,Object>> list = budgDeptIndependentSubjService.queryData(mapVo);
			
			//查询要生成的部门
			List<Map<String,Object>>  deptList = budgDeptIndependentSubjService.queryDataDeptList(mapVo);
			if(list.size() == 0){
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【医院年度业务预算编制方案】数据 或数据已全部生成.\"}");
			}
			for (Map<String, Object> map : deptList) {
				if(map.get("dept_id") != null){
			for(Map<String, Object> item : list ){
				Map<String, Object>  item1 = new HashMap<String, Object>();
				item1.put("count_value", "");
				item1.put("budg_value", "");
				item1.put("remark", "");
				item1.put("grow_rate", "");
				item1.put("resolve_rate", "");
				item1.put("grow_value", "");
				item1.put("dept_id", map.get("dept_id"));
				item1.put("state", "");
				item1.put("reason", "");
				item1.put("refer_value", "");
				item1.put("copy_code",item.get("copy_code"));
				item1.put("year",item.get("year"));
				item1.put("hos_id",item.get("hos_id"));
				item1.put("group_id",item.get("group_id"));
				item1.put("subj_code",item.get("subj_code"));
				item1.put("last_year_income",item.get("last_year_income"));
						
				        listVo.add(item1); 
					}
				}
			
				
			}
			
			
			String budgWorkHosYearJson = null ;
				
			if(listVo.size() > 0 ){
				
				budgWorkHosYearJson = budgDeptIndependentSubjService.addBatch(listVo) ;
				if("true".equals(JSONObject.parseObject(budgWorkHosYearJson).get("state"))){
					budgWorkHosYearJson =  "{\"msg\":\"操作成功.\",\"state\":\"true\"}" ;
				}
				
			}else{
				
				budgWorkHosYearJson = "{\"error\":\"历史执行数据未维护或数据已全部生成,无需再次生成.\",\"state\":\"false\"}";
			}
			
			return JSONObject.parseObject(budgWorkHosYearJson);	
	} 	
}

