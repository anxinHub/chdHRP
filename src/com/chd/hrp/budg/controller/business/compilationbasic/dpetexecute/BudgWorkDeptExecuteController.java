/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.dpetexecute;
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
import com.chd.hrp.budg.entity.BudgWorkDeptExecute;
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
public class BudgWorkDeptExecuteController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkDeptExecuteController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkDeptExecuteService")
	private final BudgWorkDeptExecuteService budgWorkDeptExecuteService = null;
	
	//引入Service服务
	@Resource(name = "budgWorkDeptYearExecuteService")
	private final BudgWorkDeptYearExecuteService budgWorkDeptYearExecuteService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/budgWorkDeptExecuteMainPage", method = RequestMethod.GET)
	public String budgWorkDeptExecuteMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/dpetexecute/budgWorkDeptExecuteMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/budgWorkDeptExecuteAddPage", method = RequestMethod.GET)
	public String budgWorkDeptExecuteAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/dpetexecute/budgWorkDeptExecuteAdd";

	}

	/**
	 * @Description 
	 * 添加数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/addBudgWorkDeptExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgWorkDeptExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			
	       
		String budgWorkDeptExecuteJson = budgWorkDeptExecuteService.add(mapVo);

		return JSONObject.parseObject(budgWorkDeptExecuteJson);
	}
	/**
	 * @Description 
	 * 更新跳转页面 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/budgWorkDeptExecuteUpdatePage", method = RequestMethod.GET)
	public String budgWorkDeptExecuteUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgWorkDeptExecute budgWorkDeptExecute = budgWorkDeptExecuteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgWorkDeptExecute.getGroup_id());
		mode.addAttribute("hos_id", budgWorkDeptExecute.getHos_id());
		mode.addAttribute("copy_code", budgWorkDeptExecute.getCopy_code());
		mode.addAttribute("year", budgWorkDeptExecute.getYear());
		mode.addAttribute("dept_id", budgWorkDeptExecute.getDept_id());
		mode.addAttribute("month", budgWorkDeptExecute.getMonth());
		mode.addAttribute("index_code", budgWorkDeptExecute.getIndex_code());
		mode.addAttribute("execute_value", budgWorkDeptExecute.getExecute_value());
		mode.addAttribute("remark", budgWorkDeptExecute.getRemark());
		
		return "hrp/budg/business/compilationbasic/dpetexecute/budgWorkDeptExecuteUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/updateBudgWorkDeptExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgWorkDeptExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("month", ids[3])   ;
			mapVo.put("execute_value", ids[4]);
			if("-1".equals(ids[5])){
				mapVo.put("remark","");
			}else{
				mapVo.put("remark",ids[5]);
			}
			
	      listVo.add(mapVo);
	    }
	  
	  
		String budgWorkDeptExecuteJson = budgWorkDeptExecuteService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgWorkDeptExecuteJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/addOrUpdateBudgWorkDeptExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgWorkDeptExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgWorkDeptExecuteJson ="";
		

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
	  
		budgWorkDeptExecuteJson = budgWorkDeptExecuteService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgWorkDeptExecuteJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/deleteBudgWorkDeptExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgWorkDeptExecute(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				mapVo.put("index_code", ids[6]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String budgWorkDeptExecuteJson = budgWorkDeptExecuteService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgWorkDeptExecuteJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室业务执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/queryBudgWorkDeptExecute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptExecute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkDeptExecute = budgWorkDeptExecuteService.query(getPage(mapVo));

		return JSONObject.parseObject(budgWorkDeptExecute);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室业务执行数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/budgWorkDeptExecuteImportPage", method = RequestMethod.GET)
	public String budgWorkDeptExecuteImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/dpetexecute/budgWorkDeptExecuteImport";

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
	 @RequestMapping(value="/hrp/budg/business/compilationbasic/dpetexecute/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","科室月份历史指标数据采集模板.xls");
	    
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
	
	@RequestMapping(value="/hrp/budg/business/compilationbasic/dpetexecute/readBudgWorkDeptExecuteFiles",method = RequestMethod.POST)  
    public String readBudgWorkDeptExecuteFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		
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
						if(temp[0].equals(error[0])&&temp[1].equals(error[1])&&temp[2].equals(error[2])&&temp[3].equals(error[3])){
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
						
						budgWorkDeptExecute.put("month",temp[3]);
						
						if(Arrays.asList(monthData).contains(temp[3])){
								
				    		mapVo.put("month", temp[3]);
						}else{
							err_sb.append("月份填写不符合规则(必须两位数字,如:1月填写01);");
						}
						
						
					}else{
						err_sb.append("月份为空;");
					}
					
					if (StringTool.isNotBlank(temp[4])) {
						budgWorkDeptExecute.put("execute_value",Double.parseDouble(temp[4]));	
			    		mapVo.put("execute_value", temp[4]);
						
					}else{
						err_sb.append("执行数据为空;");
					}
					if (ExcelReader.validExceLColunm(temp, 5)) {
						
						budgWorkDeptExecute.put("remark",temp[5]);	
						
			    		mapVo.put("remark", temp[5]);
						
					}else{
						mapVo.put("remark", "");
					}
					
					if(mapVo.get("dept_id") != null ){
						Map<String,Object> data_exc_extis = budgWorkDeptExecuteService.queryByCode(mapVo);
						
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
				
				if(addList.size() > 0 ){
					
					String dataJson = budgWorkDeptExecuteService.addBatch(addList);
					
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
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/addBatchBudgWorkDeptExecute", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgWorkDeptExecute(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgWorkDeptExecute> list_err = new ArrayList<BudgWorkDeptExecute>();
		
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
			
			BudgWorkDeptExecute budgWorkDeptExecute = new BudgWorkDeptExecute();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgWorkDeptExecute.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgWorkDeptExecute.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("month"))) {
						
					budgWorkDeptExecute.setMonth((String)jsonObj.get("month"));
		    		mapVo.put("month", jsonObj.get("month"));
		    		} else {
						
						err_sb.append("月份为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgWorkDeptExecute.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("execute_value"))) {
						
					budgWorkDeptExecute.setExecute_value(Double.valueOf((String)jsonObj.get("execute_value")));
		    		mapVo.put("execute_value", jsonObj.get("execute_value"));
		    		} else {
						
						err_sb.append("执行数据为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgWorkDeptExecute.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
				
					BudgWorkDeptExecute data_exc_extis = budgWorkDeptExecuteService.queryByCode(mapVo);
					
					if (data_exc_extis != null) {
						
						err_sb.append("编码已经存在！ ");
						
					}
				
				if (err_sb.toString().length() > 0) {
					
					budgWorkDeptExecute.setError_type(err_sb.toString());
					
					list_err.add(budgWorkDeptExecute);
					
				} else {
			  
					String dataJson = budgWorkDeptExecuteService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgWorkDeptExecute data_exc = new BudgWorkDeptExecute();
			
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
	 * 指标名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/queryBudgDeptindex_code_name1", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptindex_code_name(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String deptType = budgWorkDeptExecuteService.queryBudgDeptindex_code_name1(mapVo);
		return deptType;

	}
	
	/**
	 * 科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/queryBudgDeptId", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgDeptId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String deptType = budgWorkDeptExecuteService.queryBudgDeptId(mapVo);
		return deptType;

	}
	
	

	/**
	 * @Description 
	 * 科室月份历史指标数据采集导入页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/budgWorkMonthDeptExecuteImportNewPage", method = RequestMethod.GET)
	public String budgWorkMonthDeptExecuteImportNewPage(Model mode) throws Exception {
		return "/hrp/budg/business/compilationbasic/dpetexecute/budgWorkMonthDeptExecuteImportNewPage";

	}
	/**
	 * @Description 
	 * 科室月份历史指标数据采集导入
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/dpetexecute/budgWorkDeptMonthExecuteImport", method = RequestMethod.POST)
	@ResponseBody
	public String budgWorkDeptMonthExecuteImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgWorkDeptExecuteService.budgWorkDeptMonthExecuteImport(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	
	
	
	
	
}

