/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.execute;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgChargeStandardDict;
import com.chd.hrp.budg.entity.BudgIncomeSubj;
import com.chd.hrp.budg.entity.BudgWorkHosExecute;
import com.chd.hrp.budg.entity.BudgWorkHosMonth;
import com.chd.hrp.budg.service.business.compilationbasic.hosexecute.BudgWorkHosExecuteService;

/**
 * 
 * @Description:
 * 医院业务执行数据
 * @Table:
 * BUDG_WORK_HOS_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class WorkHosExecuteController extends BaseController{
	
	private static Logger logger = Logger.getLogger(WorkHosExecuteController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkHosExecuteService")
	private final BudgWorkHosExecuteService budgWorkHosExecuteService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/workHosExecuteMainPage", method = RequestMethod.GET)
	public String workHosExecuteMainPage(Model mode) throws Exception {

		return "hrp/budg/business/execute/hosexecute/workHosExecuteMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/workHosExecuteAddPage", method = RequestMethod.GET)
	public String workHosExecuteAddPage(Model mode) throws Exception {

		return "hrp/budg/business/execute/hosexecute/workHosExecuteAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/addWorkHosExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addWorkHosExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			
	       
		String budgWorkHosExecuteJson = budgWorkHosExecuteService.add(mapVo);

		return JSONObject.parseObject(budgWorkHosExecuteJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/workHosExecuteUpdatePage", method = RequestMethod.GET)
	public String workHosExecuteUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		BudgWorkHosExecute budgWorkHosExecute = new BudgWorkHosExecute();
    
		budgWorkHosExecute = budgWorkHosExecuteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkHosExecute.getGroup_id());
		mode.addAttribute("hos_id", budgWorkHosExecute.getHos_id());
		mode.addAttribute("copy_code", budgWorkHosExecute.getCopy_code());
		mode.addAttribute("year", budgWorkHosExecute.getYear());
		mode.addAttribute("month", budgWorkHosExecute.getMonth());
		mode.addAttribute("index_code", budgWorkHosExecute.getIndex_code());
		mode.addAttribute("execute_value", budgWorkHosExecute.getExecute_value());
		mode.addAttribute("remark", budgWorkHosExecute.getRemark());
		
		return "hrp/budg/business/execute/hosexecute/workHosExecuteUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/updateWorkHosExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWorkHosExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键			
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1]);
			mapVo.put("month", ids[2])   ;
			mapVo.put("execute_value", ids[3]);
			if("-1".equals(ids[4])){
				mapVo.put("remark","");
			}else{
				mapVo.put("remark",ids[4]);
			}
			
	      listVo.add(mapVo);
	    }
	  
		String budgWorkHosExecuteJson = budgWorkHosExecuteService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWorkHosExecuteJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/addOrUpdateWorkHosExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateWorkHosExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkHosExecuteJson ="";
		

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
	  
		budgWorkHosExecuteJson = budgWorkHosExecuteService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkHosExecuteJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/deleteWorkHosExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteWorkHosExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("month", ids[4])   ;
				mapVo.put("index_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkHosExecuteJson = budgWorkHosExecuteService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkHosExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/queryWorkHosExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWorkHosExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String budgWorkHosExecute = budgWorkHosExecuteService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosExecute);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 医院业务执行数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/workHosExecuteImportPage", method = RequestMethod.GET)
	public String workHosExecuteImportPage(Model mode) throws Exception {

		return "hrp/budg/business/execute/hosexecute/workHosExecuteImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院业务执行数据
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/execute/hosexecute/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business\\execute","医院月份业务预算执行模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院业务执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/execute/hosexecute/readWorkHosExecuteFiles",method = RequestMethod.POST)  
    public String readWorkHosExecuteFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 			 
		 
			List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
			
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"};
			
			try {
				for (int i = 1; i < list.size(); i++) {
					
					StringBuffer err_sb = new StringBuffer();
					
					Map<String,Object> errMap = new HashMap<String,Object> ();
					
					String temp[] = list.get(i);// 行
					Map<String, Object> mapVo = new HashMap<String, Object>();
					
			    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		    mapVo.put("hos_id", SessionManager.getHosId());  						 
			    		mapVo.put("copy_code", SessionManager.getCopyCode());   
			    		         
			    		for(int j = i + 1 ; j < list.size(); j++){
							String error[] = list.get(j);
							if(temp[0].equals(error[0]) && temp[1].equals(error[1])&& temp[2].equals(error[2])){
								err_sb.append("第"+i+"行数据与第 "+j+"行【年度、指标编码】重复;");
							}
						}
			
						if (StringTool.isNotBlank(temp[0])) {					
							errMap.put("year",temp[0]);
				    		mapVo.put("year", temp[0]);
						} else {
							
							err_sb.append("年度为空;");
							
						}
						 
						if (StringTool.isNotBlank(temp[1])) {
								
							errMap.put("index_code",temp[1]);
				    		mapVo.put("index_code", temp[1]);
			    		
				    		int count = budgWorkHosExecuteService.queryIndexCode(mapVo);
				    		
				    		if(count == 0 ){
				    			err_sb.append("基本指标编码不存在;");
				    		}
						
						} else {
							
							err_sb.append("基本指标编码为空;");
							
						}
						
						if (ExcelReader.validExceLColunm(temp,2)) {
							
							if(Arrays.asList(monthData).contains(temp[2])){
								
								errMap.put("month", temp[2]);
								
								mapVo.put("month", temp[2]);
								
							}else{
								err_sb.append("月份填写不符合规则(必须两位数值：如1月,填写01);");
							}
							
						} else {
							
							err_sb.append("月份为空;");
							
						}
						
						if (ExcelReader.validExceLColunm(temp,3)){
							
							errMap.put("execute_value", Double.parseDouble(temp[3]));
							
							mapVo.put("execute_value", Double.parseDouble(temp[3]));
							
						}else{
							err_sb.append("指标值为空;");
						}
						
						if (ExcelReader.validExceLColunm(temp,4)){
							
							errMap.put("remark", temp[4]);
							
							mapVo.put("remark", temp[4]);
							
						}else{
							
							errMap.put("remark", "");
							
							mapVo.put("remark", "");
						}
							
							
						Map<String,Object> data_exc_extis = budgWorkHosExecuteService.queryByCode(mapVo);
						
						if (data_exc_extis != null) {
							
							err_sb.append("数据已经存在！ ");
							
						}		
					if(err_sb.length() >  0){
						
						errMap.put("error_type",err_sb.toString()); 
						
						list_err.add(errMap);
						
					}else{
						addList.add(mapVo);
					}
				}
				
				if(list_err.size() == 0){
					String dataJson = budgWorkHosExecuteService.addBatch(addList);
				}
			} catch (DataAccessException e) {
				
				Map<String,Object> budgWorkHosExecute = new HashMap<String,Object>();
				budgWorkHosExecute.put("error_type","导入系统出错");
                list_err.add(budgWorkHosExecute);
			}
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
			
	    }  
    
   /**
	 * @Description 
	 * 批量添加数据 医院业务执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/hosexecute/addBatchWorkHosExecute", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchWorkHosExecute(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkHosExecute> list_err = new ArrayList<BudgWorkHosExecute>();
		
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
			
			BudgWorkHosExecute budgWorkHosExecute = new BudgWorkHosExecute();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgWorkHosExecute.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgWorkHosExecute.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgWorkHosExecute.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("execute_value"))) {
						
					budgWorkHosExecute.setExecute_value(Double.valueOf((String)jsonObj.get("execute_value")));
		    		mapVo.put("execute_value", jsonObj.get("execute_value"));
		    		} else {
						
						err_sb.append("执行数据为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgWorkHosExecute.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgWorkHosExecute data_exc_extis = budgWorkHosExecuteService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosExecute.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosExecute);
					
				} else {
			  
					String dataJson = budgWorkHosExecuteService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkHosExecute data_exc = new BudgWorkHosExecute();
			
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

