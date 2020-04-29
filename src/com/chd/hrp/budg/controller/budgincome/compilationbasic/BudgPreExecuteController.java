/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.compilationbasic;


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
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.entity.BudgMedIncomeExecute;
import com.chd.hrp.budg.service.base.budgsubj.BudgIncomeSubjService;
import com.chd.hrp.budg.service.budgincome.execute.BudgMedInExecuteService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
/**
 * 
 * @Description:
 * 医疗收入执行数据
 * @Table:
 * BUDG_MED_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgPreExecuteController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgPreExecuteController.class);
	
	//引入Service服务
	@Resource(name = "budgMedInExecuteService")
	private final BudgMedInExecuteService budgMedInExecuteService = null;
	
	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;
	
	@Resource(name = "budgIncomeSubjService")
	private final BudgIncomeSubjService budgIncomeSubjService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/budgPreExecuteMainPage", method = RequestMethod.GET)
	public String budgPreExcuteMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/compilationbasic/budgPreExecuteMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/budgPreExecuteAddPage", method = RequestMethod.GET)
	public String budgPreExecuteAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("year", mapVo.get("year"));
		return "hrp/budg/budgincome/compilationbasic/budgPreExecuteAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/addBudgPreExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgPreExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("year") == null){
			mapVo.put("year", SessionManager.getAcctYear());
		}
		
       
		String budgPreExecuteJson = budgMedInExecuteService.add(mapVo);

		return JSONObject.parseObject(budgPreExecuteJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/budgPreExecuteUpdatePage", method = RequestMethod.GET)
	public String budgPreExecuteUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("year") == null){
			
			mapVo.put("year", SessionManager.getAcctYear());
		}
		
		BudgMedIncomeExecute budgMedInExecute = new BudgMedIncomeExecute();
    
		budgMedInExecute = budgMedInExecuteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgMedInExecute.getGroup_id());
		mode.addAttribute("hos_id", budgMedInExecute.getHos_id());
		mode.addAttribute("copy_code", budgMedInExecute.getCopy_code());
		mode.addAttribute("year", budgMedInExecute.getYear());
		mode.addAttribute("dept_id", budgMedInExecute.getDept_id());
		mode.addAttribute("month", budgMedInExecute.getMonth());
		mode.addAttribute("subj_code", budgMedInExecute.getSubj_code());
		mode.addAttribute("amount", budgMedInExecute.getAmount());
		mode.addAttribute("remark", budgMedInExecute.getRemark());
		
		return "hrp/budg/budgincome/compilationbasic/budgPreExecuteUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/updateBudgPreExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgPreExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());   

		mapVo.put("hos_id", SessionManager.getHosId());   

		mapVo.put("copy_code", SessionManager.getCopyCode());   
	  
		String budgPreExecuteJson = budgMedInExecuteService.update(mapVo);
		
		return JSONObject.parseObject(budgPreExecuteJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/addOrUpdateBudgPreExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgPreExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgPreExecuteJson ="";
		

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
	  
		budgPreExecuteJson = budgMedInExecuteService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgPreExecuteJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/deleteBudgPreExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgPreExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("year", ids[0])   ;
				mapVo.put("dept_id", ids[1])   ;
				mapVo.put("month", ids[2])   ;
				mapVo.put("subj_code", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgPreExecuteJson = budgMedInExecuteService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgPreExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/queryBudgPreExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPreExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgPreExecute = budgMedInExecuteService.query(getPage(mapVo));

		return JSONObject.parseObject(budgPreExecute);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医疗收入执行数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/budgPreExecuteImportPage", method = RequestMethod.GET)
	public String budgPreExecuteImportPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/compilationbasic/budgPreExecuteImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医疗收入执行数据
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/budgincome/compilationbasic/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\income","医疗收入执行数据模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医疗收入执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/budgincome/compilationbasic/readBudgPreExecuteFiles",method = RequestMethod.POST)  
    public String readBudgPreExecuteFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgMedIncomeExecute> list_err = new ArrayList<BudgMedIncomeExecute>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> listmap=new ArrayList<Map<String,Object>>();
		
		String[] month ={"01","02","03","04","05","06","07","08","09","10","11","12"};
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgMedIncomeExecute budgMedInExecute = new BudgMedIncomeExecute();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])&& temp[3].equals(error[3])
								&& temp[4].equals(error[4])&& temp[5].equals(error[5])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
							
					}
		    		         
					 
					if (temp.length>=1&&StringTool.isNotBlank(temp[0])) {
						
						budgMedInExecute.setYear(temp[0]);
			    		
						mapVo.put("year", temp[0]);
						
						mapVo.put("budg_year", temp[0]);
					
					} else {
						
						err_sb.append("年度为空;");
						
					}
					 
					if (temp.length>=2&&StringTool.isNotBlank(temp[1])) {
						
			    		mapVo.put("dept_code", temp[1]);
			    		
			    		DeptDict  deptDict = deptDictService.queryDeptDictByDeptCodeMap(mapVo);
			    		
			    		if(deptDict!=null){
			    			
			    			mapVo.put("dept_id", deptDict.getDept_id());
			    			
			    		}else{
			    			
			    			err_sb.append("部门ID不存在;");
			    		}
			    		
			    		budgMedInExecute.setDept_code(temp[1]);
			    		
			    		budgMedInExecute.setDept_name(temp[2]);
					
					} else {
						
						err_sb.append("部门ID为空;");
						
					}
	           if (temp.length>=3&&StringTool.isNotBlank(temp[2])) {
						
			    		mapVo.put("dept_code", temp[1]);
			    		
			    		DeptDict  deptDict = deptDictService.queryDeptDictByDeptCodeMap(mapVo);
			    		
			    		if(deptDict!=null){
			    			
			    			mapVo.put("dept_id", deptDict.getDept_id());
			    			
			    		}else{
			    			
			    			err_sb.append("部门ID不存在;");
			    		}
			    		
			    	
			    		
			    		budgMedInExecute.setDept_name(temp[2]);
					
					} else {
						
						err_sb.append("部门为空;");
						
					}
					if (temp.length>=4&&StringTool.isNotBlank(temp[3])) {
						
						budgMedInExecute.setMonth(temp[3]);
						
						if(Arrays.asList(month).contains(temp[3])){
							
							mapVo.put("month", temp[3]);
							
						}else{
							
							err_sb.append("月份不符合规则(01-12);");
						}
					
					} else {
						
						err_sb.append("月份为空;");
						
					}
					 
					if (temp.length>=5&&StringTool.isNotBlank(temp[4])) {
						
			    		mapVo.put("subj_code", temp[4]);
			    		
			    		BudgIncomeSubj budgIncomeSubj=budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
			    		
			    		if(budgIncomeSubj ==null){
			    		
			    			err_sb.append("科目编码不存在;");
			    		}
			    		budgMedInExecute.setSubj_code(temp[4]);
					
					} else {
						
						err_sb.append("科目编码为空;");
						
					}	
					if (temp.length>=6&&StringTool.isNotBlank(temp[5])) {
						
			    		mapVo.put("subj_code", temp[4]);
			    		
			    		BudgIncomeSubj budgIncomeSubj=budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
			    		
			    		if(budgIncomeSubj ==null){
			    		
			    			err_sb.append("科目编码不存在;");
			    		}
			    	
			    		budgMedInExecute.setSubj_name(temp[5]);
					
					} else {
						
						err_sb.append("科目编码为空;");
						
					}
					 
					if (temp.length>=7&&StringTool.isNotBlank(temp[6])) {
						
						budgMedInExecute.setAmount(Double.valueOf(temp[6]));
						
			    		mapVo.put("amount", temp[6]);
					
					} else {
						
						err_sb.append("金额为空;");
						
					}
					 
					if (ExcelReader.validExceLColunm(temp, 7)) {
						
						budgMedInExecute.setRemark(temp[7]);
						
			    		mapVo.put("remark", temp[7]);
					
					} else {
						
						budgMedInExecute.setRemark("");
						
			    		mapVo.put("remark", "");
						
					}
					 
					
					BudgMedIncomeExecute data_exc_extis = budgMedInExecuteService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedInExecute.setError_type(err_sb.toString());
					
					list_err.add(budgMedInExecute);
					
				} else {
					listmap.add(mapVo);
					
					
				}
				
			}
			if(list_err.size()==0){
				
				String dataJson = budgMedInExecuteService.addBatch(listmap);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			BudgMedIncomeExecute data_exc = new BudgMedIncomeExecute();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医疗收入执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/addBatchBudgPreExecute", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgPreExecute(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgMedIncomeExecute> list_err = new ArrayList<BudgMedIncomeExecute>();
		
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
			
			BudgMedIncomeExecute budgMedInExecute = new BudgMedIncomeExecute();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgMedInExecute.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgMedInExecute.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		DeptDict deptDict = deptDictService.queryDeptDictByCode(mapVo);
		    		if(deptDict !=null){
		    			mapVo.put("dept_id", deptDict.getDept_id());
		    		}else{
		    			err_sb.append("部门ID不存在  ");
		    		}
		    		
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgMedInExecute.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {
						
					budgMedInExecute.setSubj_code((String)jsonObj.get("subj_code"));
		    		mapVo.put("subj_code", jsonObj.get("subj_code"));
		    		BudgIncomeSubj budgIncomeSubj=budgIncomeSubjService.queryBudgIncomeSubjByCode(mapVo);
		    		if(budgIncomeSubj !=null){
		    			mapVo.put("subj_code", budgIncomeSubj.getSubj_code());
		    		}else{
		    			err_sb.append("科目编码不存在  ");
		    		}
		    		
		    		} else {
						
						err_sb.append("科目编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("amount"))) {
						
					budgMedInExecute.setAmount(Double.valueOf((String)jsonObj.get("amount")));
		    		mapVo.put("amount", jsonObj.get("amount"));
		    		} else {
						
						err_sb.append("金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgMedInExecute.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
					BudgMedIncomeExecute data_exc_extis = budgMedInExecuteService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgMedInExecute.setError_type(err_sb.toString());
					
					list_err.add(budgMedInExecute);
					
				} else {
			  
					String dataJson = budgMedInExecuteService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgMedIncomeExecute data_exc = new BudgMedIncomeExecute();
			
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
	 * 财务取数页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/getDatafromAccPage", method = RequestMethod.GET)
	public String getDatafromAccPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/budg/budgincome/compilationbasic/getDatafromAcc";
		
	}
    
	/**
	 * @Description 
	 * 财务取数
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/compilationbasic/getDatafromAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDatafromAcc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		//mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//String dataJson = budgMedInExecuteService.getDatafromAcc(mapVo);
		String dataJson = budgMedInExecuteService.getDatafromAcc2(mapVo);

		return JSONObject.parseObject(dataJson);
		
	}
	
}

