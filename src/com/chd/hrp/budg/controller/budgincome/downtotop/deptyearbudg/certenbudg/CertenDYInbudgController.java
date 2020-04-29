/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.downtotop.deptyearbudg.certenbudg;
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
import com.chd.hrp.budg.entity.BudgDeptIndependentSubj;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg.BudgDeptIndependentSubjService;
/**
 * 
 * @Description:
 * 科室年度医疗预算确定预算 
 * @Table:
 * BUDG_HOS_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class CertenDYInbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(CertenDYInbudgController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptIndependentSubjService")
	private final BudgDeptIndependentSubjService budgDeptIndependentSubjService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/certenDYInBudgPage", method = RequestMethod.GET)
	public String certenDYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/certenDYInBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/certenDYInBudgAddPage", method = RequestMethod.GET)
	public String certenDYInBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/certenDYInBudgAdd";

	}

	/**
	 * @Description 
	 * 保存数据 医院年度医疗预算增量预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/saveCertenDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCertenDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("remark", ids[5]);
			
			mapVo.put("rowNo", ids[6]);
			
			mapVo.put("flag", ids[7]);
			
			//构建 查询上年收入参数Map 
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("group_id", SessionManager.getGroupId())   ;
			map.put("hos_id", SessionManager.getHosId())   ;
			map.put("copy_code", SessionManager.getCopyCode())   ;
			map.put("year", Integer.parseInt(ids[0]) - 1)   ;
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
			mapVo.put("grow_value","");
			mapVo.put("resolve_rate", "");
			mapVo.put("state", "");
			mapVo.put("reason", "");
			mapVo.put("refer_value", "");
			
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
	 * 添加数据 科室年度医疗预算确定预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/addCertenDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCertenDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("count_value", ids[3]);
			mapVo.put("remark", ids[5]);
			mapVo.put("grow_rate", ids[6]);
			mapVo.put("grow_value", ids[7]);
			mapVo.put("last_year_income", ids[8]);
			mapVo.put("hos_suggest", ids[9]);
			mapVo.put("hos_suggest_resolve", ids[9]);
			if("-1".equals(ids[10])){
				mapVo.put("dept_suggest_sum", "");
				mapVo.put("dept_suggest", "");
			}else{
				mapVo.put("dept_suggest_sum", ids[10]);
				mapVo.put("dept_suggest", ids[10]);
			}
			
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
		
       
		String budgDeptIndependentSubjJson = budgDeptIndependentSubjService.addBatch(listVo);

		return JSONObject.parseObject(budgDeptIndependentSubjJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 科室年度医疗预算确定预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/			
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/certenDYInBudgUpdatePage", method = RequestMethod.GET)
	public String certenDYInBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgDeptIndependentSubj budgDeptIndependentSubj = new BudgDeptIndependentSubj();
    
		budgDeptIndependentSubj = budgDeptIndependentSubjService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptIndependentSubj.getGroup_id());
		mode.addAttribute("hos_id", budgDeptIndependentSubj.getDept_id());
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
		
		return "hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/certenDYInBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室年度医疗预算确定预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/updateCertenDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCertenDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
			mapVo.put("remark", ids[5]);
			mapVo.put("grow_rate", ids[6]);
			mapVo.put("grow_value", ids[7]);
			mapVo.put("last_year_income", ids[8]);
			mapVo.put("hos_suggest", ids[9]);
			mapVo.put("hos_suggest_resolve", ids[9]);
			if("-1".equals(ids[10])){
				mapVo.put("dept_suggest_sum", "");
				mapVo.put("dept_suggest", "");
			}else{
				mapVo.put("dept_suggest_sum", ids[10]);
				mapVo.put("dept_suggest", ids[10]);
			}
			
			mapVo.put("resolve_rate", "");
			
			listVo.add(mapVo);
		}
	  
		String budgDeptIndependentSubjJson = budgDeptIndependentSubjService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgDeptIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室年度医疗预算确定预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/addOrUpdateCertenDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateCertenDYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	 * 删除数据 科室年度医疗预算确定预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/deleteCertenDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCertenDYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgDeptIndependentSubjJson = budgDeptIndependentSubjService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDeptIndependentSubjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室年度医疗预算确定预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/queryCertenDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCertenDYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	 * 导入跳转页面 科室年度医疗预算确定预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/certenDYInBudgImportPage", method = RequestMethod.GET)
	public String certenDYInbudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/certenDYInBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室年度医疗预算确定预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\downtotop","科室年度医疗预算确定预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室年度医疗预算确定预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	 */																			   
	@RequestMapping(value="/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/readCertenDYInBudgFiles",method = RequestMethod.POST)  
    public String readCertenDYInbudgFiles(Plupload plupload,HttpServletRequest request,
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
		    		mapVo.put("edit_method","03");
		    		
		    		int index=budgDeptIndependentSubjService.queryBudgMedIncomeEditPlanByCode(mapVo);
					if(index == 0 ){
						err_sb.append("该年度收入预算科目编码的编制方法不是确定预算或数据不存在;");
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
					err_sb.append("上年收入为空");
				}
				
				if (ExcelReader.validExceLColunm(temp,4)) {
					
					budgDeptIndependentSubj.setBudg_value(Double.valueOf(temp[4]));
		    		mapVo.put("budg_value", temp[4]);
		    		mapVo.put("count_value",temp[4]);
				} else {
					mapVo.put("count_value","");
					err_sb.append("预算值为空;");
					
				}
				
				/*if(ExcelReader.validExceLColunm(temp, 5)){
					budgDeptIndependentSubj.setGrow_rate(Double.valueOf(temp[5]));
					mapVo.put("grow_rate", temp[5]);
				}else{
					err_sb.append("增长比例为空;");
				}
				*/
				if (ExcelReader.validExceLColunm(temp,5)) {
					
					budgDeptIndependentSubj.setRemark(temp[5]);
		    		mapVo.put("remark", temp[5]);
				
				}else{
					mapVo.put("remark", "");
				}
				 
				/*if (ExcelReader.validExceLColunm(temp,6)) {
					
					budgDeptIndependentSubj.setDept_suggest_sum(Double.valueOf(temp[6]));
		    		mapVo.put("hos_suggest", temp[6]);
		    		mapVo.put("hos_suggest_resolve", temp[6]);
				
				}else{
					mapVo.put("hos_suggest", "");
					mapVo.put("hos_suggest_resolve", "");
				}*/
				 
				BudgDeptIndependentSubj data_exc_extis = budgDeptIndependentSubjService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptIndependentSubj.setError_type(err_sb.toString());
					
					list_err.add(budgDeptIndependentSubj);
					
				} else {
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
	 * 批量添加数据 科室年度医疗预算确定预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/addBatchCertenDYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCertenDYInbudgUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
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
					
					if (StringTool.isNotBlank(jsonObj.get("hos_suggest"))) {
						
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
						
					}
					
					
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
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/queryLastYearIncome", method = RequestMethod.POST)
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
		
		mapVo.put("edit_mehtod", "03") ;
		
		int year = Integer.parseInt(String.valueOf(mapVo.get("budg_year")))- 1 ;
		
		mapVo.put("year", year) ;
		
		String str  = budgDeptIndependentSubjService.queryLastYearIncome(mapVo) ;

		return str;

	}
	
	/**
	 * 查询科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/queryBudgDept", method = RequestMethod.POST)
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
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/collectCertenDYBudgData", method = RequestMethod.POST)
	@ResponseBody
	public String collectCertenDYBudgData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String reJson = budgDeptIndependentSubjService.collectCertenDYBudgData(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\"计算失败.\",\"state\":\"false\"}";
			
		}
	}
	
	/**
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/downtotop/deptyearbudg/certenbudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("edit_method", "03");//编制方法（EDIT_METHOD）01零基预算  02增量预算 03确定预算 	04概率预算
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

