/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.hosexecute;
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
import com.chd.hrp.budg.entity.BudgWorkHosMonth;
import com.chd.hrp.budg.entity.BudgWorkHosYearExecute;
import com.chd.hrp.budg.service.business.compilationbasic.hosexecute.BudgWorkHosExecuteService;
import com.chd.hrp.budg.service.business.compilationbasic.hosexecute.BudgWorkHosYearExecuteService;

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
public class BudgWorkHosYearExecuteController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkHosYearExecuteController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkHosYearExecuteService")
	private final BudgWorkHosYearExecuteService budgWorkHosYearExecuteService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteMainPage", method = RequestMethod.GET)
	public String budgWorkHosYearExecuteMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteAddPage", method = RequestMethod.GET)
	public String budgWorkHosYearExecuteAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteAdd";

	}

	/**
	 * @Description 
	 * 添加数据 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/addBudgWorkHosYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkHosYearExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


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
		
       
		String budgWorkHosExecuteJson = budgWorkHosYearExecuteService.add(mapVo);

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
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteUpdatePage", method = RequestMethod.GET)
	public String budgWorkHosYearExecuteUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		BudgWorkHosYearExecute budgWorkHosYearExecute = budgWorkHosYearExecuteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkHosYearExecute.getGroup_id());
		mode.addAttribute("hos_id", budgWorkHosYearExecute.getHos_id());
		mode.addAttribute("copy_code", budgWorkHosYearExecute.getCopy_code());
		mode.addAttribute("year", budgWorkHosYearExecute.getYear());
		mode.addAttribute("index_code", budgWorkHosYearExecute.getIndex_code());
		mode.addAttribute("execute_value", budgWorkHosYearExecute.getExecute_value());
		mode.addAttribute("remark", budgWorkHosYearExecute.getRemark());
		
		return "hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/updateBudgWorkHosYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkHosYearExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("execute_value", ids[2]);
			if("-1".equals(ids[3])){
				mapVo.put("remark","");
			}else{
				mapVo.put("remark",ids[3]);
			}
			
	      listVo.add(mapVo);
	      
	    }
	  
		String budgWorkHosExecuteJson = budgWorkHosYearExecuteService.updateBatch(listVo);
		
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
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/addOrUpdateBudgWorkHosYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkHosYearExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkHosYearExecuteJson ="";
		

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
	  
		budgWorkHosYearExecuteJson = budgWorkHosYearExecuteService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkHosYearExecuteJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/deleteBudgWorkHosYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkHosYearExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("year", ids[3])   ;
				mapVo.put("index_code", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkHosYearExecuteJson = budgWorkHosYearExecuteService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkHosYearExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/queryBudgWorkHosYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkHosYearExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String budgWorkHosExecute = budgWorkHosYearExecuteService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosExecute);
		
	}
	
	/**
	 * @Description 
	 * 导入跳转页面 医院业务执行数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteImportPage", method = RequestMethod.GET)
	public String budgWorkHosExecuteImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteImport";

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
	 @RequestMapping(value="hrp/budg/business/compilationbasic/hosyearexecute/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","医院年度历史指标数据采集模板.xls");
	    
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
	@RequestMapping(value="/hrp/budg/business/compilationbasic/hosyearexecute/readBudgWorkHosYearExecuteFiles",method = RequestMethod.POST)  
    public String readBudgWorkHosYearExecuteFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 			 
		 
			List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
			
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
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
							if(temp[0].equals(error[0]) && temp[1].equals(error[1]) ){
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
			    		
				    		int count = budgWorkHosYearExecuteService.queryIndexCode(mapVo);
				    		
				    		if(count == 0 ){
				    			err_sb.append("基本指标编码不存在;");
				    		}
						
						} else {
							
							err_sb.append("基本指标编码为空;");
							
						}
						
						if (ExcelReader.validExceLColunm(temp,2)){
							
							errMap.put("execute_value", Double.parseDouble(temp[2]));
							
							mapVo.put("execute_value", Double.parseDouble(temp[2]));
							
						}else{
							err_sb.append("指标值为空;");
						}
						
						if (ExcelReader.validExceLColunm(temp,3)){
							
							errMap.put("remark", temp[3]);
							
							mapVo.put("remark", temp[3]);
							
						}else{
							
							errMap.put("remark", "");
							
							mapVo.put("remark", "");
						}
						
						Map<String,Object> data_exc_extis = budgWorkHosYearExecuteService.queryByCode(mapVo);
						
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
					String dataJson = budgWorkHosYearExecuteService.addBatch(addList);
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
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/addBatchBudgWorkHosYearExecute", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWorkHosYearExecute(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkHosYearExecute> list_err = new ArrayList<BudgWorkHosYearExecute>();
		
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
			
			BudgWorkHosYearExecute budgWorkHosYearExecute = new BudgWorkHosYearExecute();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
						budgWorkHosYearExecute.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
						budgWorkHosYearExecute.setIndex_code((String)jsonObj.get("index_code"));
						mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("execute_value"))) {
						
						budgWorkHosYearExecute.setExecute_value(Double.valueOf((String)jsonObj.get("execute_value")));
						mapVo.put("execute_value", jsonObj.get("execute_value"));
		    		} else {
						
						err_sb.append("执行数据为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
						budgWorkHosYearExecute.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
					BudgWorkHosYearExecute data_exc_extis = budgWorkHosYearExecuteService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkHosYearExecute.setError_type(err_sb.toString());
					
					list_err.add(budgWorkHosYearExecute);
					
				} else {
			  
					String dataJson = budgWorkHosYearExecuteService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkHosYearExecute data_exc = new BudgWorkHosYearExecute();
			
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
	 * 医院年度历史指标数据采集导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteImportNewPage", method = RequestMethod.GET)
	public String budgWorkHosYearExecuteImportPage(Model mode) throws Exception {
		return "/hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteImport";

	}
	
	
	/**
	 * @Description 
	 * 医院年度历史指标数据导入
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosyearexecute/budgWorkHosYearExecuteImport", method = RequestMethod.POST)
	@ResponseBody
	public String budgWorkHosYearExecuteImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgWorkHosYearExecuteService.budgWorkHosYearExecuteImport(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
}

