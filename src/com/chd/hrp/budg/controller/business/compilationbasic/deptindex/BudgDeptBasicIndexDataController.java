/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.deptindex;
import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.hrp.budg.entity.BudgDeptBasicIndexData;
import com.chd.hrp.budg.entity.BudgHosBasicIndexData;
import com.chd.hrp.budg.service.business.compilationbasic.deptindex.BudgDeptBasicIndexDataService;
import com.chd.hrp.budg.service.business.compilationbasic.hosindex.BudgHosBasicIndexDataService;
/**
 * 
 * @Description:
 * 科室基本指标数据维护
 * @Table:
 * BUDG_DEPT_BASIC_INDEX_DATA
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgDeptBasicIndexDataController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgDeptBasicIndexDataController.class);
	
	//引入Service服务
	@Resource(name = "budgDeptBasicIndexDataService")
	private final BudgDeptBasicIndexDataService budgDeptBasicIndexDataService = null;
	
	@Resource(name = "budgHosBasicIndexDataService")
	private final BudgHosBasicIndexDataService budgHosBasicIndexDataService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexDataMainPage", method = RequestMethod.GET)
	public String budgDeptBasicIndexDataMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexDataMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexDataAddPage", method = RequestMethod.GET)
	public String budgDeptBasicIndexDataAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexDataAdd";

	}
	
	/**
	 * @Description 
	 * 保存数据 科室基本指标数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/saveBudgDeptBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgDeptBasicIndexData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("dept_id", ids[2]);
			mapVo.put("budg_value", ids[3]);
			if("-1".equals(ids[4])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[4]);
			}
			
			mapVo.put("rowNo", ids[5]);
			mapVo.put("flag", ids[6]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgDeptBasicIndexJson = null ;
		
		try {
			
			budgDeptBasicIndexJson = budgDeptBasicIndexDataService.save(listVo);
			
		} catch (Exception e) {
			
			budgDeptBasicIndexJson = e.getMessage() ;
		}
				
		
		return JSONObject.parseObject(budgDeptBasicIndexJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 科室基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/addBudgDeptBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDeptBasicIndexData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0]);
			mapVo.put("index_code", ids[1]);
			mapVo.put("index_name", ids[2]);
			mapVo.put("budg_value", ids[3]);
			mapVo.put("dept_id", ids[4]);
			mapVo.put("dept_name", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[6]);
			}
			
	      listVo.add(mapVo);
	      
	    }       
		String budgDeptBasicIndexJson = budgDeptBasicIndexDataService.addBatch(listVo);

		return JSONObject.parseObject(budgDeptBasicIndexJson);		
	
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 科室基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexDataUpdatePage", method = RequestMethod.GET)
	public String budgDeptBasicIndexDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgDeptBasicIndexData budgDeptBasicIndexData = new BudgDeptBasicIndexData();
    
		budgDeptBasicIndexData = budgDeptBasicIndexDataService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgDeptBasicIndexData.getGroup_id());
		mode.addAttribute("hos_id", budgDeptBasicIndexData.getHos_id());
		mode.addAttribute("copy_code", budgDeptBasicIndexData.getCopy_code());
		mode.addAttribute("year", budgDeptBasicIndexData.getYear());
		mode.addAttribute("dept_id", budgDeptBasicIndexData.getDept_id());
		mode.addAttribute("index_code", budgDeptBasicIndexData.getIndex_code());
		mode.addAttribute("budg_value", budgDeptBasicIndexData.getBudg_value());
		mode.addAttribute("remark", budgDeptBasicIndexData.getRemark());
		
		return "hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexDataUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 科室基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/updateBudgDeptBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDeptBasicIndexData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0]);
			mapVo.put("index_code", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("budg_value", ids[3]);
			if("-1".equals(ids[4])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[4]);
			}
			
	      listVo.add(mapVo);
	      
	    }       
	  
		String budgDeptBasicIndexDataJson = budgDeptBasicIndexDataService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgDeptBasicIndexDataJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 科室基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/addOrUpdateBudgDeptBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDeptBasicIndexData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgDeptBasicIndexDataJson ="";
		

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
	  
		budgDeptBasicIndexDataJson = budgDeptBasicIndexDataService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgDeptBasicIndexDataJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 科室基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/deleteBudgDeptBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDeptBasicIndexData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgDeptBasicIndexDataJson = budgDeptBasicIndexDataService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgDeptBasicIndexDataJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 科室基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/queryBudgDeptBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDeptBasicIndexData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgDeptBasicIndexData = budgDeptBasicIndexDataService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDeptBasicIndexData);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 科室基本指标数据维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexDataImportPage", method = RequestMethod.GET)
	public String budgDeptBasicIndexDataImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexDataImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 科室基本指标数据维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/deptindex/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","科室基本指标数据维护模板.xls");
	    
	    return null; 
	 }
	 
	 /**
	 * @Description 
	 * 导入数据 科室基本指标数据维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/deptindex/readBudgDeptBasicIndexDataFiles",method = RequestMethod.POST)  
    public String readBudgDeptBasicIndexDataFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 		 
		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();	
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> budgWorkHosBasicIndexData = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
	    		    mapVo.put("hos_id", SessionManager.getHosId());  						 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])){
							err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
						}
					}
		
					if (StringTool.isNotBlank(temp[0])) {					
						budgWorkHosBasicIndexData.put("year",temp[0]);
						
			    		mapVo.put("year", temp[0]);
					} else {
						
						err_sb.append("年度为空;");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
							
						budgWorkHosBasicIndexData.put("index_code", temp[1]);
						
			    		mapVo.put("index_code", temp[1]);
			    		
			    		mapVo.put("index_nature", "02"); 
			    		
			    		int count = budgHosBasicIndexDataService.queryBasicIndexExist(mapVo);
			    		
			    		if(count == 0 ){
			    			err_sb.append("填写基本指标编码不存在或其指标性质不是科室;");
			    		}
					
					} else {
						
						err_sb.append("指标编码为空;");
						
					}
					if (StringTool.isNotBlank(temp[2])) {	
						
						budgWorkHosBasicIndexData.put("dept_code", temp[2]);
						
			    		mapVo.put("dept_code", temp[2]);
			    		
			    		//根据科室编码 查询  预算科室ID
			    		Map<String,Object> map = budgDeptBasicIndexDataService.queryDeptID(mapVo);
			    		
			    		if(map != null ){
			    			
			    			mapVo.put("dept_id", map.get("dept_id"));
			    			
			    		}else{
			    			
			    			err_sb.append("填写指标编码与科室编码不存对应关系;");
			    		}
			    		
					} else {
						
						err_sb.append("科室编码为空;");
						
					}
					if (StringTool.isNotBlank(temp[3])) {
						budgWorkHosBasicIndexData.put("budg_value", temp[3]);;	
			    		mapVo.put("budg_value", temp[3]);
						
					}else{
						err_sb.append("数据为空;");
					}
					if (ExcelReader.validExceLColunm(temp, 4)) {
						
						budgWorkHosBasicIndexData.put("remark", temp[4]);	
						
			    		mapVo.put("remark", temp[4]);
						
					}else{
						
						mapVo.put("remark", "");
						
					}
					
					if(mapVo.get("dept_id") != null){
						//查询数据是否存在
						int count = budgDeptBasicIndexDataService.queryDataExist(mapVo);
						
						if(count > 0 ){
							
							err_sb.append("数据已存在;");
						}
					}
					
					
					if(err_sb.length() >  0){
						
						budgWorkHosBasicIndexData.put("error_type",err_sb.toString()); 
						
						list_err.add(budgWorkHosBasicIndexData);
						
					}else{
						
						addList.add(mapVo);
					}
			}
			
			if(list_err.size() == 0){
				String dataJson = budgDeptBasicIndexDataService.addBatch(addList);
			}
			
		} catch (DataAccessException e) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("error_type","系统导入出错");
			list_err.add( map);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 科室基本指标数据维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/addBatchBudgDeptBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgDeptBasicIndexData(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgDeptBasicIndexData> list_err = new ArrayList<BudgDeptBasicIndexData>();
		
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
			
			BudgDeptBasicIndexData budgDeptBasicIndexData = new BudgDeptBasicIndexData();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgDeptBasicIndexData.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					budgDeptBasicIndexData.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("部门ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgDeptBasicIndexData.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgDeptBasicIndexData.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgDeptBasicIndexData.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgDeptBasicIndexData data_exc_extis = budgDeptBasicIndexDataService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgDeptBasicIndexData.setError_type(err_sb.toString());
					
					list_err.add(budgDeptBasicIndexData);
					
				} else {
			  
					String dataJson = budgDeptBasicIndexDataService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgDeptBasicIndexData data_exc = new BudgDeptBasicIndexData();
			
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
	 * 科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/queryBudgDept", method = RequestMethod.POST)
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
		
		String nature = budgDeptBasicIndexDataService.queryBudgDept(mapVo);
		return nature;

	}
	/**
	 * @Description 
	 * 增量生成 科室基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		  
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("index_nature", "02");
			//查询 科室基本指标生成数据
			List<Map<String,Object>> list=budgDeptBasicIndexDataService.queryBudgDeptIndexData(mapVo);
			
			for( Map<String,Object> item :list){
				
				item.put("year", mapVo.get("year")) ;
				
				//判断 科室基本指标数据 是否已存在
				int count = budgDeptBasicIndexDataService.queryDataExist(item) ;
			   
				if(count == 0){
					
					//不存在  组装 生成科室基本指标数据维护
					item.put("budg_value","")   ;
				   
					item.put("remark","")   ;
					   
					listVo.add(item);
				}
			}
			
			String budgDeptBasicIndexJson = null ;
			
			if(listVo.size() > 0 ){
				
				budgDeptBasicIndexJson = budgDeptBasicIndexDataService.addBatch(listVo);
				
			    
			}else{
				
				budgDeptBasicIndexJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
			}
		    
			return JSONObject.parseObject(budgDeptBasicIndexJson);	
	}
	
	
	
	/**
	 * @Description 
	 * 导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexImportPage", method = RequestMethod.GET)
	public String budgDeptBasicIndexImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/deptindex/budgDeptBasicIndexImport";

	}
	
	
	/**
	 * 最新导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/importBudgBasicDeptIndex", method = RequestMethod.POST)
	@ResponseBody
	public String importBudgBasicDeptIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=budgDeptBasicIndexDataService.importBudgBasicDeptIndex(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/deptindex/collectBudgDeptBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public String collectBudgDeptBasicIndexData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String reJson = budgDeptBasicIndexDataService.collectBudgDeptBasicIndexData(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\"计算失败.\",\"state\":\"false\"}";
			
		}
	}
	
}

