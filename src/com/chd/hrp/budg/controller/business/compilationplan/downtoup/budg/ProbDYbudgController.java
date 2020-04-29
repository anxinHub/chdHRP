/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationplan.downtoup.budg;
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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgIndexDict;
import com.chd.hrp.budg.entity.BudgWorkDeptYear;
import com.chd.hrp.budg.entity.BudgWorkHosYear;
import com.chd.hrp.budg.service.base.BudgSelectService;
import com.chd.hrp.budg.service.base.budgindexdict.BudgIndexDictService;
import com.chd.hrp.budg.service.business.budgeworkdeptrate.BudgWorkDeptYearRateService;
import com.chd.hrp.budg.service.business.budgeworkhosrate.BudgWorkHosYearRateService;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptYearService;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosYearService;
/**
 * 
 * @Description:
 * 科室年度业务预算概率预算 
 * @Table:
 * BUDG_WORK_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class ProbDYbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(ProbDYbudgController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkDeptYearService")
	private final BudgWorkDeptYearService budgWorkDeptYearService = null;
	
	@Resource(name = "budgSelectService")
	private final BudgSelectService budgSelectService = null;
	
	@Resource(name = "budgWorkDeptYearRateService")
	private final BudgWorkDeptYearRateService budgWorkDeptYearRateService = null;
	
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/probDYBudgMainPage", method = RequestMethod.GET)
	public String probDYBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/probDYBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/probDYBudgAddPage", method = RequestMethod.GET)
	public String probDYBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/probDYBudgAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室年度业务预算概率预算 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/addProbDYBudgDownNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProbDYBudgDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkHosYearJson = "";
		
		try {
				
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				
				//构建 查询上年业务量  参数 Map
				Map<String,Object> paraMap =  new HashMap<String,Object>();
				
				paraMap.put("group_id", SessionManager.getGroupId())   ;
				paraMap.put("hos_id", SessionManager.getHosId())   ;
				paraMap.put("copy_code", SessionManager.getCopyCode())   ;
				paraMap.put("budg_year", mapVo.get("year"));
				paraMap.put("index_code", mapVo.get("index_code"));
				paraMap.put("dept_id", mapVo.get("dept_id"));
				paraMap.put("year", Integer.parseInt(String.valueOf(mapVo.get("year")))-1);
				
				paraMap.put("budg_level", "03");
				
				paraMap.put("edit_method", "04");
				
				//查询上年业务量
				Map<String,Object> map = JSONObject.parseObject(budgWorkDeptYearService.queryDeptYearLastYearWork(paraMap));
				
				if(map == null){
					
					mapVo.put("last_year_workload", "");
					
				}else{
					
					mapVo.put("last_year_workload", map.get("last_year_workload"));
				}
				
				
				mapVo.put("grow_rate", "");
				mapVo.put("grow_value", "");
				mapVo.put("resolve_rate", "");
				
				mapVo.put("state","");
				mapVo.put("reason","");
				mapVo.put("refer_value","");
				
				List<Map<String,Object>> rateList = new ArrayList<Map<String,Object>>();
				
				if(!"-1".equals(String.valueOf(mapVo.get("detail")))){
					JSONArray json = JSONArray.parseArray(String.valueOf(mapVo.get("detail")));
					
					Iterator it = json.iterator();
					while (it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						Map<String,Object> rateMap = new HashMap<String,Object>();
						
						rateMap.put("group_id", SessionManager.getGroupId());
						rateMap.put("hos_id", SessionManager.getHosId());
						rateMap.put("copy_code", SessionManager.getCopyCode());
						rateMap.put("year",mapVo.get("year"));//年度
						rateMap.put("index_code",mapVo.get("index_code"));//指标编码
						rateMap.put("dept_id",mapVo.get("dept_id"));//科室编码
						rateMap.put("measure_name", jsonObj.get("measure_name"));//运营尺度名称
						rateMap.put("measure_value",  jsonObj.get("measure_value"));//运营预期
						rateMap.put("rate",  jsonObj.get("rate"));//概率
						rateMap.put("count_value",  jsonObj.get("count_value"));//计算值
						rateList.add(rateMap);
					}
				}
				
				if(rateList.size()>0){
					
					mapVo.put("rateList", rateList);
				}
			
				
			budgWorkHosYearJson = budgWorkDeptYearService.add(mapVo);
			
		} catch (Exception e) {

			budgWorkHosYearJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgWorkHosYearJson);
		
		
	}
	
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/saveProbDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveProbDYBudgDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("count_value", ids[4]);
			mapVo.put("budg_value", ids[5]);
			mapVo.put("remark", ids[6]);
			
			//构建 查询上年收入参数Map 
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("group_id", SessionManager.getGroupId())   ;
			map.put("hos_id", SessionManager.getHosId())   ;
			map.put("copy_code", SessionManager.getCopyCode())   ;
			map.put("year", Integer.parseInt(ids[0]) -1 )   ;
			map.put("index_code", ids[1]);
			map.put("dept_id", ids[2]);
			map.put("dept_code", ids[3]);
			
			Map<String,Object> incomeMap = JSONObject.parseObject(budgWorkDeptYearService.queryDeptYearLastYearWork(map)) ;
			
			if(incomeMap == null){
				
				mapVo.put("last_year_workload", "");
				
			}else{
				
				mapVo.put("last_year_workload",incomeMap.get("last_year_workload"));
				
			}
				
			
			mapVo.put("grow_rate", "");
			mapVo.put("grow_value", "");
			mapVo.put("resolve_rate", "");
			
			mapVo.put("state","");
			mapVo.put("reason","");
			mapVo.put("refer_value","");
			
			mapVo.put("rowNo", ids[7]);
			
			mapVo.put("flag", ids[8]);
			
			if(!"-1".equals(ids[9])){
				JSONArray json = JSONArray.parseArray(String.valueOf(ids[9]));
				List<Map<String,Object>> rateList = new ArrayList<Map<String,Object>>();
				
				Iterator it=json.iterator();
				while(it.hasNext()){
					JSONObject jsonObj = JSON.parseObject(it.next().toString());
					Map<String,Object> rateMap = new HashMap<String,Object>();
					
					rateMap.put("group_id", SessionManager.getGroupId());
					rateMap.put("hos_id", SessionManager.getHosId());
					rateMap.put("copy_code", SessionManager.getCopyCode());
					rateMap.put("year",ids[0]);//年度
					rateMap.put("index_code",ids[1]);//指标编码
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
       
		String budgWorkHosYearJson = null;
		
		try {
			
			budgWorkHosYearJson = budgWorkDeptYearService.save(listVo);
			
		} catch (Exception e) {
			
			budgWorkHosYearJson = e.getMessage();
		}
				

		return JSONObject.parseObject(budgWorkHosYearJson);
	}
	/**
	 * @Description 
	 * 更新跳转页面 科室年度业务预算概率预算 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/probDYBudgUpdatePage", method = RequestMethod.GET)
	public String probHYBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgWorkDeptYear budgWorkDeptYear = budgWorkDeptYearService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkDeptYear.getGroup_id());
		mode.addAttribute("hos_id", budgWorkDeptYear.getHos_id());
		mode.addAttribute("copy_code", budgWorkDeptYear.getCopy_code());
		mode.addAttribute("year", budgWorkDeptYear.getYear());
		mode.addAttribute("index_code", budgWorkDeptYear.getIndex_code());
		mode.addAttribute("dept_id", budgWorkDeptYear.getDept_id());
		mode.addAttribute("count_value", budgWorkDeptYear.getCount_value());
		mode.addAttribute("budg_value", budgWorkDeptYear.getBudg_value());
		mode.addAttribute("remark", budgWorkDeptYear.getRemark());
		mode.addAttribute("grow_rate", budgWorkDeptYear.getGrow_rate());
		mode.addAttribute("grow_value", budgWorkDeptYear.getGrow_value());
		mode.addAttribute("resolve_rate", budgWorkDeptYear.getResolve_rate());
		mode.addAttribute("last_year_workload", budgWorkDeptYear.getLast_year_workload());
		mode.addAttribute("hos_suggest", budgWorkDeptYear.getHos_suggest_resolve());
		mode.addAttribute("dept_suggest_sum", budgWorkDeptYear.getDept_suggest());
		
		return "hrp/budg/budgincome/toptodown/hosyearinbudg/ProbHYBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室年度业务预算概率预算 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/updateProbDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProbDYBudgDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		String budgWorkHosYearJson = "";
		
		try {
				
				List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
				
				for(String id : paramVo.split("@,")){
					
					String[] ids=id.split("@");
					
					Map<String, Object> mapVo=new HashMap<String, Object>();
					
					//表的主键
					mapVo.put("group_id", SessionManager.getGroupId())   ;
					mapVo.put("hos_id", SessionManager.getHosId())   ;
					mapVo.put("copy_code", SessionManager.getCopyCode())   ;
					mapVo.put("year", ids[0])   ;
					mapVo.put("index_code", ids[1]);
					mapVo.put("dept_id", ids[2]);
					mapVo.put("last_year_workload",ids[3]);
					mapVo.put("count_value", ids[4]);
					mapVo.put("budg_value", ids[5]);
					mapVo.put("remark", ids[6]);
					if("-1".equals(ids[7])){
						mapVo.put("hos_suggest_resolve", "");
					}else{
						mapVo.put("hos_suggest_resolve", ids[7]);
					}
					
					mapVo.put("grow_rate", "");
					mapVo.put("grow_value", "");
					mapVo.put("resolve_rate", "");
					mapVo.put("dept_suggest", "");
					
					JSONArray json = JSONArray.parseArray(String.valueOf(ids[8]));
					
					List<Map<String,Object>> rateList = new ArrayList<Map<String,Object>>();
					
					Iterator it = json.iterator();
					while (it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						Map<String,Object> rateMap = new HashMap<String,Object>();
						
						rateMap.put("group_id", SessionManager.getGroupId());
						rateMap.put("hos_id", SessionManager.getHosId());
						rateMap.put("copy_code", SessionManager.getCopyCode());
						rateMap.put("year",ids[0]);//年度
						rateMap.put("index_code",ids[1]);//指标编码
						rateMap.put("dept_id",ids[2]);//科室编码
						rateMap.put("measure_name", jsonObj.get("measure_name"));//运营尺度名称
						rateMap.put("measure_value",  jsonObj.get("measure_value"));//运营预期
						rateMap.put("rate",  jsonObj.get("rate"));//概率
						rateMap.put("count_value",  jsonObj.get("count_value"));//计算值
						rateList.add(rateMap);
					}
					
					mapVo.put("rateList", rateList);
					
					budgWorkDeptYearRateService.delete(mapVo);
					
					listVo.add(mapVo);
				}
				
				budgWorkHosYearJson = budgWorkDeptYearService.updateBatch(listVo);

				
			} catch (Exception e) {

				budgWorkHosYearJson = e.getMessage();
			}
			
			return JSONObject.parseObject(budgWorkHosYearJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室年度业务预算概率预算 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/addOrUpdateProbDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateProbDYBudgDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkHosYearJson ="";
		

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
	  
		budgWorkHosYearJson = budgWorkDeptYearService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkHosYearJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室年度业务预算概率预算 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/deleteProbDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProbDYBudgDown(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("dept_id", ids[4]);
				mapVo.put("index_code", ids[5]);
				
				mapVo.put("is_prob","1");
				
	      listVo.add(mapVo);
	      
	    }
			
		String budgWorkHosYearJson =null;
		
	    try {
			
	    	budgWorkHosYearJson = budgWorkDeptYearService.deleteBatch(listVo);
	    	
		} catch (Exception e) {
			
			budgWorkHosYearJson = e.getMessage();
		}
		 
			
	  return JSONObject.parseObject(budgWorkHosYearJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室年度业务预算概率预算 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/queryBudgWorkDeptRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkHosYear = budgWorkDeptYearRateService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosYear);
		
	}
	
	
	
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/queryProbDYBudgDown", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryProbDYBudgDown(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkHosYear = budgWorkDeptYearService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosYear);
		
	}
  /**
	 * @Description 
	 * 导入跳转页面 科室年度业务预算概率预算 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/probDYBudgImportPage", method = RequestMethod.GET)
	public String probHYBudgImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/probDYBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室年度业务预算概率预算 
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business\\downtotop","科室年度业务预算概率预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室年度业务预算概率预算 
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/readProbDYBudgFiles",method = RequestMethod.POST)  
    public String readProbHYBudgFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String, Object> errMap = new HashMap<String, Object>();
				
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
						
						errMap.put("year",temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
			    		mapVo.put("budg_year", temp[0]);
		    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						errMap.put("index_code",temp[1]);
						
			    		mapVo.put("index_code", temp[1]);
			    		
			    		mapVo.put("budg_level", "03");
			    		
			    		mapVo.put("edit_method", "04");
			    		
			    		String index = budgSelectService.qureyBudgIndexFromPlan(mapVo);
			    		
						if(index.length() == 2 ){
							
							err_sb.append("该指标编码在所填预算年度业务预算编制方案中编制方法不是概率预算;");
						}
					
					} else {
						
						err_sb.append("指标编码为空 ;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						errMap.put("dept_code",temp[2]);
						
						mapVo.put("dept_code",temp[2]);
						//根据 指标编码、 科室编码 查询  预算指标对应科室 
						Map<String,Object> dept = budgWorkDeptYearService.queryIndexDeptSet(mapVo);
						
						if(dept == null  ){
							
							err_sb.append("指标编码与科室不存在对应关系;");
							
						}else{
							
							mapVo.put("dept_id", dept.get("dept_id"));
						}
						
					} else {
						
						err_sb.append("科室编码为空;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						errMap.put("last_year_workload",Double.valueOf(temp[3]));
			    		mapVo.put("last_year_workload", temp[3]);
						
					} else {
						
						err_sb.append("上年业务量为空;");
						
					}
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						errMap.put("budg_value",Double.valueOf(temp[4]));
			    		mapVo.put("budg_value", temp[4]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,5)) {
						
						errMap.put("remark",temp[5]);
			    		mapVo.put("remark", temp[5]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					if(mapVo.get("dept_id") != null ){
						BudgWorkHosYear data_exc_extis = budgWorkDeptYearService.queryByCode(mapVo);
						
						if (data_exc_extis != null) {
							err_sb.append("数据已经存在！ ");
						}
					}
					
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				} else {
					
					mapVo.put("count_value", "");
					mapVo.put("grow_rate", "");
					mapVo.put("grow_value", "");
					mapVo.put("resolve_rate", "");
					
					mapVo.put("state","");
					mapVo.put("reason","");
					mapVo.put("refer_value","");
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgWorkDeptYearService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			Map<String, Object> data_exc = new HashMap<String, Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
    
	/**
	 * 生成运营尺度数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/setProbBudgRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setProbBudgRate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String budgWorkDeptYear = budgWorkDeptYearService.queryProbBudgRate(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptYear);
		
	}
	
	/**
	 * 查询 所传 指标的 上年业务量
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/queryDeptYearLastYearWork", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptYearLastYearWork(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("budg_level", "03");
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))  - 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgWorkDeptYearService.queryDeptYearLastYearWork(mapVo) ;

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
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("budg_level", "03");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份

			mapVo.put("edit_method", "04");// 编制方法（EDIT_METHOD）01零基预算  02增量预算 	03确定预算  04概率预算


			// 查询要生成的数据
			List<Map<String,Object>> list = budgWorkDeptYearService.queryData(mapVo);
			
			if(list.size() == 0){
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【科室年度业务预算编制方案】数据.\"}");
			}
			
			for(Map<String, Object> item : list ){
				
				if(item.get("dept_id") != null ){//指标 存在 对应科室关系时  生成相应数据  否则不生成
					
					//根据主键  查询数据是否存在
					int count  = budgWorkDeptYearService.queryDataExist(item);
					
					//不存在  组装 生成数据
					if(count == 0){
						
						//构建 查询上年业务量  参数 Map
						Map<String,Object> paraMap =  new HashMap<String,Object>();
						
						paraMap.put("group_id", SessionManager.getGroupId())   ;
						paraMap.put("hos_id", SessionManager.getHosId())   ;
						paraMap.put("copy_code", SessionManager.getCopyCode())   ;
						paraMap.put("budg_year", item.get("year"));
						paraMap.put("index_code", item.get("index_code"));
						paraMap.put("dept_id", item.get("dept_id"));
						paraMap.put("dept_code", item.get("dept_code"));
						paraMap.put("year", Integer.parseInt(String.valueOf(item.get("year")))-1);
						
						mapVo.put("budg_level", "03");//预算层次（LEVEL） 01医院年度	02医院月份 03科室年度 04科室月份

						
						//查询上年业务量
						Map<String,Object> map = JSONObject.parseObject(budgWorkDeptYearService.queryDeptYearLastYearWork(paraMap));
						
						if(map.get("last_year_workload") == null){
							
							item.put("last_year_workload", "");
							
						}else{
							
							item.put("last_year_workload", map.get("last_year_workload") == null?"":map.get("last_year_workload"));
						}	
						item.put("count_value", "");
						item.put("budg_value", "");
						item.put("remark", "");
						item.put("grow_rate", "");
						item.put("grow_value", "");
						item.put("resolve_rate", "");
						item.put("state","");
						item.put("reason","");
						item.put("refer_value","");
				        listVo.add(item); 
					}
					
				}
				
				
			}
			
			String budgWorkDeptYearJson = null ;
			
			if(listVo.size() > 0 ){
				
				budgWorkDeptYearJson =budgWorkDeptYearService.addBatch(listVo);
		         
			}else{
				budgWorkDeptYearJson = "{\"error\":\"指标对应科室数据未维护或数据已全部生成,无法生成.\"}";
			}
			
			return JSONObject.parseObject(budgWorkDeptYearJson);	
	} 
	
	/**
	 *  预算指标下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/queryBudgIndex", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null ){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String index = budgWorkDeptYearService.queryBudgIndex(mapVo);
		return index;

	}
	
	/**
	 *  根据指标 关联查询科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationplan/downtoup/deptyearbudg/prob/queryBudgIndexDeptSet", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgIndexDeptSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("budg_year") == null ){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String index = budgWorkDeptYearService.queryBudgIndexDeptSet(mapVo);
		return index;

	}
}

