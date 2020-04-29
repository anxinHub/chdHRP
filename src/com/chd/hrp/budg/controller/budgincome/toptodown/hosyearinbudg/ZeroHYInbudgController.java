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
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg.BudgHosIndependentSubjService;
/**
 * 
 * @Description:
 * 医院年度医疗预算零基预算 
 * @Table:
 * BUDG_HOS_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class ZeroHYInbudgController extends BaseController{
	
	private static Logger logger = Logger.getLogger(ZeroHYInbudgController.class);
	
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
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/zeroHYInBudgMainPage", method = RequestMethod.GET)
	public String zeroHYInBudgMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/zeroHYInBudgMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/zeroHYInBudgAddPage", method = RequestMethod.GET)
	public String zeroHYInBudgAddPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/zeroHYInBudgAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院年度医疗预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/saveZeroHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveZeroHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			
			mapVo.put("rowNo", ids[4]);
			
			mapVo.put("flag", ids[5]);
			
			//构建 查询上年收入参数Map 
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put("group_id", SessionManager.getGroupId())   ;
			map.put("hos_id", SessionManager.getHosId())   ;
			map.put("copy_code", SessionManager.getCopyCode())   ;
			map.put("year", Integer.parseInt(ids[0]) - 1 )   ;
			map.put("budg_year", ids[0])   ;
			map.put("subj_code", ids[1]);
			
	/*		Map<String,Object> incomeMap = JSONObject.parseObject(budgHosIndependentSubjService.queryLastYearIncome(map)) ;
			
			if( incomeMap != null ){
				if(incomeMap.get("last_year_income") == null){
					
					mapVo.put("last_year_income", "");
					
				}else{
					
					mapVo.put("last_year_income", String.valueOf(incomeMap.get("last_year_income")));
					
				}
			}else{*/
				mapVo.put("last_year_income", "");
			/*}*/
			
			
			mapVo.put("count_value", "");
			mapVo.put("grow_rate", "");
			mapVo.put("grow_value", "");
			
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
	 * 添加数据 医院年度医疗预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/addZeroHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addZeroHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
		
       
		String budgHosIndependentSubjJson = budgHosIndependentSubjService.addBatch(listVo);

		return JSONObject.parseObject(budgHosIndependentSubjJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医院年度医疗预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/			
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/zeroHYInBudgUpdatePage", method = RequestMethod.GET)
	public String zeroHYInBudgUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		return "hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/zeroHYInBudgUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/updateZeroHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateZeroHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

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
	  
		String budgHosIndependentSubjJson = budgHosIndependentSubjService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgHosIndependentSubjJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院年度医疗预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/addOrUpdateZeroHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateZeroHYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	 * 删除数据 医院年度医疗预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/deleteZeroHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteZeroHYInBudgUp(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgHosIndependentSubjJson = budgHosIndependentSubjService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgHosIndependentSubjJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度医疗预算零基预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/queryZeroHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryZeroHYInBudgUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	 * 导入跳转页面 医院年度医疗预算零基预算
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/zeroHYInBudgImportPage", method = RequestMethod.GET)
	public String zeroHYInbudgImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/zeroHYInBudgImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院年度医疗预算零基预算
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income\\toptodown","医院年度医疗预算零基预算模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院年度医疗预算零基预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/readZeroHYInBudgFiles",method = RequestMethod.POST)  
    public String readZeroHYInbudgFiles(Plupload plupload,HttpServletRequest request,
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
			    		mapVo.put("edit_method","01");
			    		
			    		int index=budgHosIndependentSubjService.queryBudgMedIncomeEditPlanByCode(mapVo);
						if(index == 0 ){
							err_sb.append("该年度收入预算科目编码的编制方法不是零基预算或数据不存在;");
						}
					
					} else {
						
						err_sb.append("收入预算科目编码为空 ;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						budgHosIndependentSubj.setBudg_value(Double.valueOf(temp[2]));
			    		mapVo.put("budg_value", temp[2]);
						
					} else {
						
						err_sb.append("预算值为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp,3)) {
						
						budgHosIndependentSubj.setRemark(temp[3]);
			    		mapVo.put("remark", temp[3]);
					
					}else{
						mapVo.put("remark", "");
					}
					 
					if (ExcelReader.validExceLColunm(temp,4)) {
						
						budgHosIndependentSubj.setDept_suggest_sum(Double.valueOf(temp[4]));
			    		mapVo.put("dept_suggest_sum", temp[4]);
					
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
					mapVo.put("last_year_income", "");
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
	 * 批量添加数据 医院年度医疗预算零基预算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/addBatchZeroHYInBudgUp", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchZeroHYInbudgUp(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
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
	 * @Description 
	 * 增量生成 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/toptodown/hosyearinbudg/zerobudg/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			mapVo.put("budg_year", mapVo.get("year"));
			
			mapVo.put("edit_method", "01");//编制方法（EDIT_METHOD）01零基预算  02增量预算 03确定预算 	04概率预算

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
				paraMap.put("edit_method", "01");
		/*		
				//查询上年业务量
				Map<String,Object> map = JSONObject.parseObject(budgHosIndependentSubjService.queryLastYearIncome(paraMap));
				
				if(map != null ){
					if(map.get("last_year_income") == null){
						
						item.put("last_year_income", "");
						
					}else{
						
						item.put("last_year_income", map.get("last_year_income"));
					}
					
				}else{*/
					
					item.put("last_year_income", "");
			/*	}*/
				
				
				
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

