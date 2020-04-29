/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.med;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.budg.entity.BudgCostSubj;
import com.chd.hrp.budg.entity.BudgMedCostExecute;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostSubjService;
import com.chd.hrp.budg.service.budgincome.toptodown.deptyearinbudg.MedInDYBudgService;
import com.chd.hrp.budg.service.business.med.BudgMedCostExecuteService;
/**
 * 
 * @Description:
 * 医疗支出执行数据
 * @Table:
 * BUDG_MED_COST_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedCostExecuteController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedCostExecuteController.class);
	
	//引入Service服务
	@Resource(name = "budgMedCostExecuteService")
	private final BudgMedCostExecuteService budgMedCostExecuteService = null;
	
	
	@Resource(name = "medInDYBudgService")
	private final MedInDYBudgService medInDYBudgService = null;
	
	@Resource(name = "budgCostSubjService")
	private final BudgCostSubjService budgCostSubjService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/budgMedCostExecuteMainPage", method = RequestMethod.GET)
	public String budgMedCostExecuteMainPage(Model mode) throws Exception {

		return "hrp/budg/business/med/costexecute/budgMedCostExecuteMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/budgMedCostExecuteAddPage", method = RequestMethod.GET)
	public String budgMedCostExecuteAddPage(Model mode) throws Exception {

		return "hrp/budg/business/med/costexecute/budgMedCostExecuteAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医疗支出执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/addBudgMedCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgMedCostExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String budgMedCostExecuteJson = budgMedCostExecuteService.add(mapVo);

		return JSONObject.parseObject(budgMedCostExecuteJson);
		
	}
	
	/**
	 * @Description 
	 * 医疗支出执行数据 采集  (财务取数 )
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/collectData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgMedCostExecuteJson = null ;
		
		try {
			
			budgMedCostExecuteJson = budgMedCostExecuteService.collectData(mapVo);
			
		} catch (Exception e) {
			
			budgMedCostExecuteJson = e.getMessage() ;
		}
       
		return JSONObject.parseObject(budgMedCostExecuteJson);
		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 医疗支出执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/budgMedCostExecuteUpdatePage", method = RequestMethod.GET)
	public String budgMedCostExecuteUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgMedCostExecute budgMedCostExecute = new BudgMedCostExecute();
    
		budgMedCostExecute = budgMedCostExecuteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedCostExecute.getGroup_id());
		mode.addAttribute("hos_id", budgMedCostExecute.getHos_id());
		mode.addAttribute("copy_code", budgMedCostExecute.getCopy_code());
		mode.addAttribute("year", budgMedCostExecute.getYear());
		mode.addAttribute("dept_id", budgMedCostExecute.getDept_id());
		mode.addAttribute("month", budgMedCostExecute.getMonth());
		mode.addAttribute("subj_code", budgMedCostExecute.getSubj_code());
		mode.addAttribute("amount", budgMedCostExecute.getAmount());
		mode.addAttribute("remark", budgMedCostExecute.getRemark());
		
		return "hrp/budg/business/med/costexecute/budgMedCostExecuteUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医疗支出执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/updateBudgMedCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgMedCostExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String budgMedCostExecuteJson = budgMedCostExecuteService.update(mapVo);
		
		return JSONObject.parseObject(budgMedCostExecuteJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医疗支出执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/addOrUpdateBudgMedCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgMedCostExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgMedCostExecuteJson ="";
		

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
	  
		budgMedCostExecuteJson = budgMedCostExecuteService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgMedCostExecuteJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医疗支出执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/deleteBudgMedCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgMedCostExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("dept_id", ids[4])   ;
				mapVo.put("month", ids[5])   ;
				mapVo.put("subj_code", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgMedCostExecuteJson = budgMedCostExecuteService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgMedCostExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医疗支出执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/queryBudgMedCostExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMedCostExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgMedCostExecute = budgMedCostExecuteService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedCostExecute);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医疗支出执行数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/budgMedCostExecuteImportPage", method = RequestMethod.GET)
	public String budgMedCostExecuteImportPage(Model mode) throws Exception {

		return "hrp/budg/business/med/costexecute/budgMedCostExecuteImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医疗支出执行数据
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/med/costexecute/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\med","医疗支出预算执行.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医疗支出执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/med/costexecute/readBudgMedCostExecuteFiles",method = RequestMethod.POST)  
    public String readBudgMedCostExecuteFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedCostExecute> list_err = new ArrayList<BudgMedCostExecute>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthList = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedCostExecute budgMedCostExecute = new BudgMedCostExecute();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])
								&& temp[4].equals(error[4])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
		    		
					if (StringTool.isNotBlank(temp[0])) {
						
						budgMedCostExecute.setYear(temp[0]);
			    		mapVo.put("year", temp[0]);
						
					} else {
						
						err_sb.append("年度为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
						budgMedCostExecute.setDept_code(temp[1]);
						budgMedCostExecute.setDept_name(temp[2]);
						
						Map<String, Object> deptMapVo = new HashMap<String, Object>();
						
						deptMapVo.put("group_id", SessionManager.getGroupId());   
						 
						deptMapVo.put("hos_id", SessionManager.getHosId());   
						 
						deptMapVo.put("copy_code", SessionManager.getCopyCode()); 
						
						deptMapVo.put("dept_code", temp[1]);
						
						Map<String,Object> dept = medInDYBudgService.queryDeptExist(deptMapVo);
			    		
			    		if(dept != null ){
			    			
			    			mapVo.put("dept_id", dept.get("dept_id"));
			    			
			    		}else{
			    			
			    			err_sb.append("该年度预算科室编码不存在;");
			    		}
					
					} else {
						
						err_sb.append("部门编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
						budgMedCostExecute.setMonth(temp[3]);
						if(Arrays.asList(monthList).contains(temp[3])){
							mapVo.put("month", temp[3]);
						}else{
							err_sb.append("月份不符合规则(01-12);");
						}
					
					} else {
						
						err_sb.append("月份为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						budgMedCostExecute.setSubj_code(temp[4]);
						budgMedCostExecute.setSubj_name(temp[5]);
						
						Map<String, Object> subjMapVo = new HashMap<String, Object>();
						
						subjMapVo.put("group_id", SessionManager.getGroupId());   
						 
						subjMapVo.put("hos_id", SessionManager.getHosId());   
						 
						subjMapVo.put("copy_code", SessionManager.getCopyCode());   
			    			
						subjMapVo.put("budg_year", temp[0]);
						
						subjMapVo.put("subj_code", temp[4]);
		    			
						BudgCostSubj subj = budgCostSubjService.queryBudgCostSubjByCode(subjMapVo);
		    			
		    			if(subj != null){
		    				
		    				mapVo.put("subj_code", temp[4]);
		    				
		    			}else{
		    				err_sb.append("该年度科目不存在 ");
		    			}
					
					} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 6)) {
						
						budgMedCostExecute.setAmount(Double.valueOf(temp[6]));
			    		mapVo.put("amount", temp[6]);
					
					} else {
						
						err_sb.append("金额为空  ");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 7)) {
						
						budgMedCostExecute.setRemark(temp[7]);
			    		mapVo.put("remark", temp[7]);
					
					}else{
						budgMedCostExecute.setRemark("");
			    		mapVo.put("remark", "");
					}
					 
					
				BudgMedCostExecute data_exc_extis = budgMedCostExecuteService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedCostExecute.setError_type(err_sb.toString());
					
					list_err.add(budgMedCostExecute);
					
				} else {
					addList.add(mapVo);
					
				}
				
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgMedCostExecuteService.addBatch(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			BudgMedCostExecute data_exc = new BudgMedCostExecute();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医疗支出执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/med/costexecute/addBatchBudgMedCostExecute", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgMedCostExecute(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedCostExecute> list_err = new ArrayList<BudgMedCostExecute>();
		
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
			
			BudgMedCostExecute budgMedCostExecute = new BudgMedCostExecute();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgMedCostExecute.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgMedCostExecute.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgMedCostExecute.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedCostExecute.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("amount"))) {
						
					budgMedCostExecute.setAmount(Double.valueOf((String)jsonObj.get("amount")));
		    		mapVo.put("amount", jsonObj.get("amount"));
		    		} else {
						
						err_sb.append("金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgMedCostExecute.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgMedCostExecute data_exc_extis = budgMedCostExecuteService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedCostExecute.setError_type(err_sb.toString());
					
					list_err.add(budgMedCostExecute);
					
				} else {
			  
					String dataJson = budgMedCostExecuteService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedCostExecute data_exc = new BudgMedCostExecute();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

