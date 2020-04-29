/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.execute;
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
import com.chd.hrp.budg.entity.BudgWorkDeptYearExecute;
import com.chd.hrp.budg.entity.BudgWorkHosExecute;
import com.chd.hrp.budg.service.business.compilationbasic.dpetexecute.BudgWorkDeptExecuteService;
import com.chd.hrp.budg.service.business.compilationbasic.dpetexecute.BudgWorkDeptYearExecuteService;
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkDeptYearService;
/**
 * 
 * @Description:
 * 科室业务执行数据
 * @Table:
 * BUDG_WORK_DEPT_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class WorkDeptYearExecuteController extends BaseController{
	
	private static Logger logger = Logger.getLogger(WorkDeptYearExecuteController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkDeptYearExecuteService")
	private final BudgWorkDeptYearExecuteService budgWorkDeptYearExecuteService = null;
	
	@Resource(name = "budgWorkDeptExecuteService")
	private final BudgWorkDeptExecuteService budgWorkDeptExecuteService = null;
	
	@Resource(name = "budgWorkDeptYearService")
	private final BudgWorkDeptYearService budgWorkDeptYearService = null;
	
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/workDeptYearExecuteMainPage", method = RequestMethod.GET)
	public String workDeptYearExecuteMainPage(Model mode) throws Exception {

		return "hrp/budg/business/execute/dpetyearexecute/workDeptYearExecuteMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/workDeptYearExecuteAddPage", method = RequestMethod.GET)
	public String workDeptYearExecuteAddPage(Model mode) throws Exception {

		return "hrp/budg/business/execute/dpetyearexecute/workDeptYearExecuteAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/addWorkDeptYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addWorkDeptYearExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String budgWorkHosExecuteJson = budgWorkDeptYearExecuteService.add(mapVo);

		return JSONObject.parseObject(budgWorkHosExecuteJson);		
	}
	/**
	 * @Description 
	 * 更新跳转页面 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/workDeptYearExecuteUpdatePage", method = RequestMethod.GET)
	public String workDeptYearExecuteUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgWorkDeptYearExecute budgWorkDeptYearExecute = budgWorkDeptYearExecuteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkDeptYearExecute.getGroup_id());
		mode.addAttribute("hos_id", budgWorkDeptYearExecute.getHos_id());
		mode.addAttribute("copy_code", budgWorkDeptYearExecute.getCopy_code());
		mode.addAttribute("year", budgWorkDeptYearExecute.getYear());
		mode.addAttribute("dept_id", budgWorkDeptYearExecute.getDept_id());
		mode.addAttribute("index_code", budgWorkDeptYearExecute.getIndex_code());
		mode.addAttribute("execute_value", budgWorkDeptYearExecute.getExecute_value());
		mode.addAttribute("remark", budgWorkDeptYearExecute.getRemark());
		
		return "hrp/budg/business/execute/dpetyearexecute/workDeptYearExecuteUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/updateWorkDeptYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWorkDeptYearExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键		
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("dept_id", ids[1]);
			mapVo.put("index_code", ids[2])   ;
			mapVo.put("execute_value", ids[3]);
			if("-1".equals(ids[4])){
				mapVo.put("remark","");
			}else{
				mapVo.put("remark",ids[4]);
			}
			
	      listVo.add(mapVo);
	    }
	  
		String budgWorkDeptYearExecuteJson = budgWorkDeptYearExecuteService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWorkDeptYearExecuteJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/addOrUpdateWorkDeptYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateWorkDeptYearExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkDeptYearExecuteJson ="";
		

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
	  
		budgWorkDeptYearExecuteJson = budgWorkDeptYearExecuteService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkDeptYearExecuteJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/deleteWorkDeptYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteWorkDeptYearExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("index_code", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkDeptYearExecuteJson = budgWorkDeptYearExecuteService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptYearExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/queryWorkDeptYearExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWorkDeptYearExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkDeptYearExecute = budgWorkDeptYearExecuteService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptYearExecute);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室业务执行数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/workDeptYearExecuteImportPage", method = RequestMethod.GET)
	public String workDeptYearExecuteImportPage(Model mode) throws Exception {

		return "hrp/budg/business/execute/dpetyearexecute/workDeptYearExecuteImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室业务执行数据
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/budg/business/execute/dpetyearexecute/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business\\execute","科室年度业务预算执行模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 科室业务执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	
	@RequestMapping(value="/hrp/budg/business/execute/dpetyearexecute/readWorkDeptYearExecuteFiles",method = RequestMethod.POST)  
    public String readWorkDeptYearExecuteFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> paraMap = new  HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		paraMap.put("hos_id", SessionManager.getHosId());  						 
		paraMap.put("copy_code", SessionManager.getCopyCode());  
		
		// 末级科室 基本信息（根据code 匹配ID）
		List<Map<String,Object>> deptData = budgWorkDeptYearExecuteService.queryDeptData(paraMap);
		
		//指标信息(校验数据是否存在)
		List<Map<String,Object>> indexData = budgWorkDeptYearExecuteService.queryIndexData(paraMap);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> budgWorkDeptExecute = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
	    		    mapVo.put("hos_id", SessionManager.getHosId());  						 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0])&&temp[1].equals(error[1])&&temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					}
		
					if (StringTool.isNotBlank(temp[0])) {					
						budgWorkDeptExecute.put("year",temp[0]);
			    		mapVo.put("year", temp[0]);
					} else {
						
						err_sb.append("年度为空;");
						
					}
					if (StringTool.isNotBlank(temp[1])) {
						
						int flag = 0 ;
						
						for(Map<String,Object> item : indexData){
							
							if(temp[1].equals(item.get("index_code"))){
								
								mapVo.put("index_code", temp[1]);
								
								flag = 1 ;
								break ;
								
							}
						}
						budgWorkDeptExecute.put("index_code",temp[1]);
						
						if(flag == 0){
							
							err_sb.append("指标编码不存在或已停用;");
						}
						
					} else {
						
						err_sb.append("指标编码为空;");
						
					}
					if (StringTool.isNotBlank(temp[2])) {
						
						int flag = 0 ;
						
						for(Map<String,Object> item : deptData){
							
							if(temp[2].equals(item.get("dept_code"))){
								
								mapVo.put("dept_code", temp[2]);
								mapVo.put("dept_id", item.get("dept_id"));
								
								flag = 1;
								
								break ;
								
							}
						}
						
						budgWorkDeptExecute.put("dept_code",temp[2]);
						
						if(flag == 0 ){
							err_sb.append("科室不是末级科目或已停用;");
						}
			    		
					}else{
						
						err_sb.append("科室编码为空;");
					}
					
					if (StringTool.isNotBlank(temp[3])) {
						budgWorkDeptExecute.put("execute_value",Double.parseDouble(temp[3]));	
			    		mapVo.put("execute_value", temp[3]);
						
					}else{
						err_sb.append("执行数据为空;");
					}
					if (ExcelReader.validExceLColunm(temp, 4)) {
						
						budgWorkDeptExecute.put("remark",temp[4]);	
						
			    		mapVo.put("remark", temp[4]);
						
					}else{
						mapVo.put("remark", "");
					}
					
					if(mapVo.get("dept_id") != null ){
						BudgWorkDeptYearExecute data_exc_extis = budgWorkDeptYearExecuteService.queryByCode(mapVo);
						if (data_exc_extis != null) {
							
							err_sb.append("数据已经存在！ ");
							
						}
					}
					
					
					if(err_sb.length() >  0){
						
						budgWorkDeptExecute.put("error_type",err_sb.toString()); 
						
						list_err.add(budgWorkDeptExecute);
						
					}else{
						addList.add(mapVo);
					}
			
			}
			if(list_err.size() == 0){
				
				if(addList.size()>0){
					
					String dataJson = budgWorkDeptYearExecuteService.addBatch(addList);
					
				}else{
					Map<String,Object> budgWorkHosExecute = new HashMap<String,Object>();
					
					budgWorkHosExecute.put("error_type","没有导入数据,导入失败!");
					
		            list_err.add(budgWorkHosExecute);
				}
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
	 * 批量添加数据 科室业务执行数据
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/execute/dpetyearexecute/addBatchWorkDeptYearExecute", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchWorkDeptYearExecute(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkDeptYearExecute> list_err = new ArrayList<BudgWorkDeptYearExecute>();
		
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
			
			BudgWorkDeptYearExecute budgWorkDeptYearExecute = new BudgWorkDeptYearExecute();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgWorkDeptYearExecute.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgWorkDeptYearExecute.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgWorkDeptYearExecute.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("execute_value"))) {
						
					budgWorkDeptYearExecute.setExecute_value(Double.valueOf((String)jsonObj.get("execute_value")));
		    		mapVo.put("execute_value", jsonObj.get("execute_value"));
		    		} else {
						
						err_sb.append("执行数据为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgWorkDeptYearExecute.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgWorkDeptYearExecute data_exc_extis = budgWorkDeptYearExecuteService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgWorkDeptYearExecute.setError_type(err_sb.toString());
					
					list_err.add(budgWorkDeptYearExecute);
					
				} else {
			  
					String dataJson = budgWorkDeptYearExecuteService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkDeptYearExecute data_exc = new BudgWorkDeptYearExecute();
			
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

