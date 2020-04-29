/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.downtotop.deptyearbudg.resbudg;
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
import com.chd.hrp.budg.entity.BudgMedIncomeDeptYear;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg.BudgDeptIndependentSubjService;
import com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg.BudgMedIncomeDeptYearService;

/**
 * 
 * @Description:
 * 科室年度医疗预算分解预算 
 * @Table:
 * BUDG_HOS_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class resolveDYInbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(resolveDYInbudgController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptIndependentSubjService")
	private final BudgDeptIndependentSubjService budgDeptIndependentSubjService = null;
	
	@Resource(name = "budgMedIncomeDeptYearService")
	private final BudgMedIncomeDeptYearService budgMedIncomeDeptYearService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
    
	/*@Resource(name="budgDeptResolveRateService")
	private final  BudgDeptResolveRateService budgDeptResolveRateService=null;*/
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resDYInBudgPage", method = RequestMethod.GET)
	public String resolveDYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveDYInBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveDYInBudgAddPage", method = RequestMethod.GET)
	public String resolveDYInBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveDYInBudgAdd";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveRateDYInBudgMainPage", method = RequestMethod.GET)
	public String resolveRateDYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveRateDYInBudgMain";

	}

	/**
	 * @Description 
	 * 保存数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/saveResolveDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveResolveDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("dept_id", ids[2]);
			mapVo.put("budg_value", ids[3]);
			mapVo.put("count_value", ids[4]);
			mapVo.put("last_year_income", ids[5]);
			mapVo.put("remark", ids[6]);
			
			mapVo.put("rowNo", ids[7]);
			mapVo.put("flag", ids[8]);
			
			mapVo.put("state", "");
			mapVo.put("reason", "");
			mapVo.put("refer_value", "");
			
			//添加BUDG_HOS_RESOLVE_RATE表中字段GROW_RATE(增长比例),RESOLVE_RATE(分解比例)暂设为空
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
       
		String budgMedIncomeDeptYearJson = null;
		try {
			budgMedIncomeDeptYearJson = budgMedIncomeDeptYearService.save(listVo);
		} catch (Exception e) {
			budgMedIncomeDeptYearJson = e.getMessage();
		}

		return JSONObject.parseObject(budgMedIncomeDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 科室年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/addResolveDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResolveDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("dept_id", ids[2]);//科室ID
			mapVo.put("budg_value", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("last_year_income", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("hos_suggest_resolve", "");
			}else{
				mapVo.put("hos_suggest_resolve", ids[6]);
			}
			
			mapVo.put("count_value", "");
			
			mapVo.put("dept_suggest", "");
			
			//添加BUDG_HOS_RESOLVE_RATE表中字段GROW_RATE(增长比例),RESOLVE_RATE(分解比例)暂设为空
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
		
       
		String budgMedIncomeDeptYearJson = budgMedIncomeDeptYearService.addBatch(listVo);

		return JSONObject.parseObject(budgMedIncomeDeptYearJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 科室年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/			
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveDYInBudgUpdatePage", method = RequestMethod.GET)
	public String resolveDYInBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
    
		budgMedIncomeDeptYear = budgMedIncomeDeptYearService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedIncomeDeptYear.getGroup_id());
		mode.addAttribute("hos_id", budgMedIncomeDeptYear.getDept_id());
		mode.addAttribute("copy_code", budgMedIncomeDeptYear.getCopy_code());
		mode.addAttribute("year", budgMedIncomeDeptYear.getYear());
		mode.addAttribute("subj_code", budgMedIncomeDeptYear.getSubj_code());
		mode.addAttribute("dept_code", budgMedIncomeDeptYear.getDept_code());
		mode.addAttribute("count_value", budgMedIncomeDeptYear.getCount_value());
		mode.addAttribute("budg_value", budgMedIncomeDeptYear.getBudg_value());
		mode.addAttribute("remark", budgMedIncomeDeptYear.getRemark());
		mode.addAttribute("last_year_income", budgMedIncomeDeptYear.getLast_year_income());
		mode.addAttribute("hos_suggest_resolve", budgMedIncomeDeptYear.getHos_suggest_resolve());
		mode.addAttribute("dept_suggest", budgMedIncomeDeptYear.getDept_suggest());
		
		return "hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveDYInBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/updateResolveDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateResolveDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
			mapVo.put("dept_id", ids[2]);//科室ID
			mapVo.put("budg_value", ids[3]);
			mapVo.put("remark", ids[4]);
			mapVo.put("last_year_income", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("hos_suggest_resolve", "");
			}else{
				mapVo.put("hos_suggest_resolve", ids[6]);
			}
			
			mapVo.put("count_value", "");
			
			mapVo.put("dept_suggest", "");
			
			//添加BUDG_HOS_RESOLVE_RATE表中字段GROW_RATE(增长比例),RESOLVE_RATE(分解比例)暂设为空
			mapVo.put("grow_rate", "");
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
	  
		String budgMedIncomeDeptYearJson = budgMedIncomeDeptYearService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgMedIncomeDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/addOrUpdateResolveDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateResolveDYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	  
		budgMedIncomeDeptYearJson = budgMedIncomeDeptYearService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedIncomeDeptYearJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/deleteResolveDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteResolveDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMedIncomeDeptYearJson = budgMedIncomeDeptYearService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedIncomeDeptYearJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室年度医疗预算分解预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/queryResolveDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryResolveDYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMedIncomeDeptYear = budgMedIncomeDeptYearService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeDeptYear);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室年度医疗预算分解预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveDYInBudgImportPage", method = RequestMethod.GET)
	public String resolveDYInbudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/resolveDYInBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室年度医疗预算分解预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\downtotop","科室年度医疗预算分解预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室年度医疗预算分解预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/readResolveDYInBudgFiles",method = RequestMethod.POST)  
    public String readResolveDYInbudgFiles(Plupload plupload,HttpServletRequest request,
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
		    		
					if (ExcelReader.validExceLColunm(temp,0)) {
						
						errMap.put("year", temp[0]);
						
			    		mapVo.put("year", temp[0]);
			    		
					} else {
						
						err_sb.append("预算年度为空 ;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,1)) {
							
						errMap.put("subj_code", temp[1]);
			    		mapVo.put("subj_code", temp[1]);
			    		
			    		BudgIncomeSubj income = budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
						if(income == null ){
							err_sb.append("该年度收入预算科目编码不存在;");
						}
					
					} else {
						
						err_sb.append("收入预算科目编码为空 ;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						errMap.put("dept_id", temp[2]);
			    		mapVo.put("dept_id", temp[2]);
						
					} else {
						
						err_sb.append("部门ID为空;");
						
					}
					
					if(ExcelReader.validExceLColunm(temp, 3)){
						
						errMap.put("last_year_income", temp[3]);
						mapVo.put("last_year_income", temp[3]);
						
					}else{
						
						err_sb.append("上年收入为空");
					}
					
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						errMap.put("budg_value", temp[4]);
			    		mapVo.put("budg_value", temp[4]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					
					if(ExcelReader.validExceLColunm(temp, 5)){
						errMap.put("grow_rate", temp[5]);
						mapVo.put("grow_rate", temp[5]);
					}else{
						err_sb.append("增长比例为空");
						
					}
					if (ExcelReader.validExceLColunm(temp,6)) {
						
						errMap.put("remark", temp[6]);
			    		mapVo.put("remark", temp[6]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					if (ExcelReader.validExceLColunm(temp,7)) {
						
						errMap.put("hos_suggest_resolve", temp[7]);
			    		mapVo.put("hos_suggest_resolve", temp[7]);
					
					}else{
						mapVo.put("hos_suggest_resolve", "");
					}
					 
					
				BudgMedIncomeDeptYear data_exc_extis = budgMedIncomeDeptYearService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					errMap.put("error_type",err_sb.toString());
					
					list_err.add(errMap);
					
				} else {
					
					mapVo.put("count_value", "");
					mapVo.put("resolve_rate", "");
					mapVo.put("state", "");
					mapVo.put("reason", "");
					mapVo.put("refer_value", "");
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0 ){
				
				String dataJson = budgMedIncomeDeptYearService.addBatch(addList);
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
	 * 批量添加数据 科室年度医疗预算分解预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/addBatchResolveDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchResolveDYInbudgUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
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
					
					budgMedIncomeDeptYear.setSubj_code((String)jsonObj.get("dept_id"));
					mapVo.put("dept_id", jsonObj.get("dept_id"));
				} else {
					
					err_sb.append("科室编码为空  ");
					
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
					
					err_sb.append("增加额为空  ");
					
				}
				
				if (StringTool.isNotBlank(jsonObj.get("last_year_income"))) {
					
				budgMedIncomeDeptYear.setLast_year_income(Double.valueOf((String)jsonObj.get("last_year_income")));
	    		mapVo.put("last_year_income", jsonObj.get("last_year_income"));
	    		} else {
					
					err_sb.append("上年收入为空  ");
					
				}
				
				mapVo.put("state", "");
				mapVo.put("reason", "");
				mapVo.put("refer_value", "");
				
				BudgMedIncomeDeptYear data_exc_extis = budgMedIncomeDeptYearService.queryByCode(mapVo);
			
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedIncomeDeptYear.setError_type(err_sb.toString());
					
					list_err.add(budgMedIncomeDeptYear);
					
				} else {
			  
					String dataJson = budgMedIncomeDeptYearService.add(mapVo);
					
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
	 * 根据科目查询上年收入
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/queryLastYearIncome", method = RequestMethod.POST)
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
		
		String str  = budgMedIncomeDeptYearService.queryLastYearIncome(mapVo) ;
		
		return str;

	}
	/**
	 * 查询科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/queryBudgDept", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("is_single_count", "0");//是否独立核算 0 否 1 是

			// 查询要生成的数据
			List<Map<String,Object>> list = budgMedIncomeDeptYearService.queryData(mapVo);
			
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
				
				budgMedIncomeDeptYearService.addGenerateData(listVo) ;
				
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
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/generateResolveRateDept", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateResolveRateDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("is_single_count", "0");//是否独立核算 0 否 1 是

			// 查询要生成的数据
			List<Map<String,Object>> list = budgMedIncomeDeptYearService.queryDataDept(mapVo);
			
			//查询要生成的部门
			List<Map<String,Object>>  deptList = budgDeptIndependentSubjService.queryDataDeptList(mapVo);
			if(list.size() == 0){
				return JSONObject.parseObject("{\"error\":\"未查询到生成数据,请核对所选年度的【医院年度业务预算编制方案】数据 或数据已全部生成.\"}");
			}
		
			for(Map<String, Object> item : list ){
				Map<String, Object>  item1 = new HashMap<String, Object>();
				item1.put("count_value", "");
				item1.put("budg_value", "");
				item1.put("remark", "");
				item1.put("grow_rate", "");
				item1.put("resolve_rate", "");
				item1.put("grow_value", "");
				item1.put("dept_id", item.get("dept_id"));
				item1.put("state", "");
				item1.put("reason", "");
				item1.put("refer_value", "");
				item1.put("grow_rate", "");
				item1.put("resolve_rate", "");
				
				item1.put("copy_code",item.get("copy_code"));
				item1.put("year",item.get("year"));
				item1.put("hos_id",item.get("hos_id"));
				item1.put("group_id",item.get("group_id"));
				item1.put("subj_code",item.get("subj_code"));
				item1.put("last_year_income",item.get("last_year_income"));
						
				        listVo.add(item1); 
					}
				
			
				
			
			
			
			String budgWorkHosYearJson = null ;
			
			if(listVo.size() > 0 ){
				
				budgMedIncomeDeptYearService.generateResolveRateDept(listVo) ;
				
				budgWorkHosYearJson =  "{\"msg\":\"操作成功.\",\"state\":\"true\"}" ;
		         
			}else{
				
				budgWorkHosYearJson = "{\"error\":\"数据已全部生成,无需再次生成.\",\"state\":\"false\"}";
			}
			
			return JSONObject.parseObject(budgWorkHosYearJson);	
	}
	/**
	 * 03科室年度  分解计算 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/resbudg/collect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBudgWorkDeptYearUp(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("is_single_count", "0");//是否独立核算（IS_SINGLE_COUNT）：0否，1是
	
		String collect  = null ;
		
		try {
			
			collect = budgMedIncomeDeptYearService.collect(mapVo) ;
			
		} catch (Exception e) {
			
			collect = e.getMessage() ;
		}
		

		return JSONObject.parseObject(collect);

	}
}

