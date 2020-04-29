/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationbasic.hosindex;
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
import com.chd.hrp.budg.entity.BudgHosBasicIndexData;
import com.chd.hrp.budg.entity.BudgWorkHosExecute;
import com.chd.hrp.budg.service.business.compilationbasic.hosindex.BudgHosBasicIndexDataService;
import com.chd.hrp.budg.service.common.BudgCountProcessService;
/**
 * 
 * @Description:
 * 医院基本指标数据维护
 * @Table:
 * BUDG_HOS_BASIC_INDEX_DATA
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgHosBasicIndexDataController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgHosBasicIndexDataController.class);
	
	//引入Service服务
	@Resource(name = "budgHosBasicIndexDataService")
	private final BudgHosBasicIndexDataService budgHosBasicIndexDataService = null;
	
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexDataMainPage", method = RequestMethod.GET)
	public String budgHosBasicIndexDataMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexDataMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexDataAddPage", method = RequestMethod.GET)
	public String budgHosBasicIndexDataAddPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexDataAdd";

	}
	
	/**
	 * @Description 
	 * 导入 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexImportPage", method = RequestMethod.GET)
	public String budgHosBasicIndexImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexImport";

	}
	
	/**
	 * @Description 
	 * 保存数据 医院基本指标数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/saveBudgHosBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgHosBasicIndexData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			mapVo.put("budg_value", ids[2]);
			if("-1".equals(ids[3])){
				mapVo.put("remark", "");
			}else{
				mapVo.put("remark", ids[3]);
			}
			
			mapVo.put("rowNo", ids[4]);
			mapVo.put("flag", ids[5]);
			
			listVo.add(mapVo);
		}
		
	  
		String budgHosBasicIndexDataJson = null ;
		
		try {
			
			budgHosBasicIndexDataJson = budgHosBasicIndexDataService.save(listVo);
			
		} catch (Exception e) {
			String s="{\"error\":\"保存失败.\",\"state\":\"false\"}";
			if(e.getMessage().equals(s)){
				System.out.println("equals");
				budgHosBasicIndexDataJson = e.getMessage() ;
			}else{
				budgHosBasicIndexDataJson=s;
			}
			
		}
				
		
		return JSONObject.parseObject(budgHosBasicIndexDataJson);
	}
	
	/**
	 * @Description 
	 * 添加数据 医院基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/addBudgHosBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgHosBasicIndexData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String budgHosBasicIndexDataJson = budgHosBasicIndexDataService.add(mapVo);

		return JSONObject.parseObject(budgHosBasicIndexDataJson);		
	}
	
	/**
	 * @Description 
	 * 更新跳转页面 医院基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexDataUpdatePage", method = RequestMethod.GET)
	public String budgHosBasicIndexDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		BudgHosBasicIndexData budgHosBasicIndexData = new BudgHosBasicIndexData();
    
		budgHosBasicIndexData = budgHosBasicIndexDataService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgHosBasicIndexData.getGroup_id());
		mode.addAttribute("hos_id", budgHosBasicIndexData.getHos_id());
		mode.addAttribute("copy_code", budgHosBasicIndexData.getCopy_code());
		mode.addAttribute("year", budgHosBasicIndexData.getYear());
		mode.addAttribute("index_code", budgHosBasicIndexData.getIndex_code());
		mode.addAttribute("budg_value", budgHosBasicIndexData.getBudg_value());
		mode.addAttribute("remark", budgHosBasicIndexData.getRemark());
		
		return "hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexDataUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 医院基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/updateBudgHosBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgHosBasicIndexData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("index_code", ids[1])   ;
			mapVo.put("budg_value", ids[2])   ;
			if("-1".equals(ids[3])){
				
				mapVo.put("remark", "")   ;
				
			}else{
				
				mapVo.put("remark", ids[3])   ;
			}
			
	      listVo.add(mapVo);
	      
	    }       
	  
		String budgHosBasicIndexDataJson = budgHosBasicIndexDataService.updateBatch(listVo);
		
		return JSONObject.parseObject(budgHosBasicIndexDataJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 医院基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/addOrUpdateBudgHosBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgHosBasicIndexData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String budgHosBasicIndexDataJson ="";
		

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
	  
		budgHosBasicIndexDataJson = budgHosBasicIndexDataService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(budgHosBasicIndexDataJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 医院基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/deleteBudgHosBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgHosBasicIndexData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String budgHosBasicIndexDataJson = budgHosBasicIndexDataService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(budgHosBasicIndexDataJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 医院基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/queryBudgHosBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgHosBasicIndexData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgHosBasicIndexData = budgHosBasicIndexDataService.query(getPage(mapVo));

		return JSONObject.parseObject(budgHosBasicIndexData);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 医院基本指标数据维护
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexDataImportPage", method = RequestMethod.GET)
	public String budgHosBasicIndexDataImportPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationbasic/hosindex/budgHosBasicIndexDataImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 医院基本指标数据维护
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/budg/business/compilationbasic/hosindex/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\business","医院基本指标数据维护模板.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 医院基本指标数据维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/budg/business/compilationbasic/hosindex/readBudgHosBasicIndexDataFiles",method = RequestMethod.POST)  
    public String readBudgHosBasicIndexDataFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<BudgHosBasicIndexData> list_err = new ArrayList<BudgHosBasicIndexData>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				BudgHosBasicIndexData budgWorkHosBasicIndexData = new BudgHosBasicIndexData();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
	    		    mapVo.put("hos_id", SessionManager.getHosId());  						 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
		    		for(int j = i + 1 ; j < list.size(); j++){
						String error[] = list.get(j);
						if(temp[0].equals(error[0]) && temp[1].equals(error[1]) ){
							err_sb.append("第"+i+"行与第 "+j+"行数据重复;");
						}
					}
		
					if (StringTool.isNotBlank(temp[0])) {					
						budgWorkHosBasicIndexData.setYear(temp[0]);
			    		mapVo.put("year", temp[0]);
					} else {
						
						err_sb.append("年度为空;");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
							
						budgWorkHosBasicIndexData.setIndex_code(temp[1]);
						
			    		mapVo.put("index_code", temp[1]);
			    		
			    		mapVo.put("index_nature", "01"); 
			    		
			    		//查询指标编码是否存在
			    		int count = budgHosBasicIndexDataService.queryBasicIndexExist(mapVo);
			    		
			    		if(count == 0 ){
			    			
			    			err_sb.append("填写基本指标编码不存在或其指标性质不是医院;");
			    		}
					
					} else {
						
						err_sb.append("基本指标编码为空;");
						
					}
					
					if (ExcelReader.validExceLColunm(temp,2)) {
						
						budgWorkHosBasicIndexData.setBudg_value(Double.parseDouble(temp[2]));
						
			    		mapVo.put("budg_value", temp[2]);
						
					}else{
						err_sb.append("指标值为空;");
					}
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						budgWorkHosBasicIndexData.setRemark(temp[3]);	
						
			    		mapVo.put("remark", temp[3]);
						
					}else{
						mapVo.put("remark", "");
					}
					
					//查询数据是否存在
					int count = budgHosBasicIndexDataService.queryDataExist(mapVo);
					
					if(count > 0 ){
						
						err_sb.append("数据已存在;");
					}
					
					if(err_sb.length() >  0){
						
						budgWorkHosBasicIndexData.setError_type(err_sb.toString()); 
						
						list_err.add(budgWorkHosBasicIndexData);
						
					}else{
						
						addList.add(mapVo);
					}
			}
			
			if(list_err.size() == 0){
				
				String dataJson = budgHosBasicIndexDataService.addBatch(addList);
			}
		} catch (DataAccessException e) {
			
			BudgHosBasicIndexData budgWorkHosBasicIndexData = new BudgHosBasicIndexData();
			
			budgWorkHosBasicIndexData.setError_type("导入系统出错");
			
			list_err.add( budgWorkHosBasicIndexData);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 医院基本指标数据维护
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/addBatchBudgHosBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchBudgHosBasicIndexData(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<BudgHosBasicIndexData> list_err = new ArrayList<BudgHosBasicIndexData>();
		
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
			
			BudgHosBasicIndexData budgHosBasicIndexData = new BudgHosBasicIndexData();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());	
					if (StringTool.isNotBlank(jsonObj.get("year"))) {
						
					budgHosBasicIndexData.setYear((String)jsonObj.get("year"));
		    		mapVo.put("year", jsonObj.get("year"));
		    		} else {
						
						err_sb.append("年度为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("index_code"))) {
						
					budgHosBasicIndexData.setIndex_code((String)jsonObj.get("index_code"));
		    		mapVo.put("index_code", jsonObj.get("index_code"));
		    		} else {
						
						err_sb.append("指标编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("budg_value"))) {
						
					budgHosBasicIndexData.setBudg_value(Double.valueOf((String)jsonObj.get("budg_value")));
		    		mapVo.put("budg_value", jsonObj.get("budg_value"));
		    		} else {
						
						err_sb.append("预算值为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("remark"))) {
						
					budgHosBasicIndexData.setRemark((String)jsonObj.get("remark"));
		    		mapVo.put("remark", jsonObj.get("remark"));
		    		} else {
						
						err_sb.append("说明为空  ");
						
					}
					
					
				BudgHosBasicIndexData data_exc_extis = budgHosBasicIndexDataService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					budgHosBasicIndexData.setError_type(err_sb.toString());
					
					list_err.add(budgHosBasicIndexData);
					
				} else {
			  
					String dataJson = budgHosBasicIndexDataService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			BudgHosBasicIndexData data_exc = new BudgHosBasicIndexData();
			
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
	 * 增量生成  医院基本指标数据维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/generate", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		//构建 查询参数Map
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
		
		mapVo.put("index_nature", "01")   ;//指标性质 01：医院    02：科室
		//查询 基本指标字典 budg_basic_index_dic 中指标性质 为 01 医院的 指标数据
		List<Map<String,Object>> list = budgHosBasicIndexDataService.queryBudgBasicIndexData(mapVo);
		
		
		//循环遍历list,用以增量生成的
		for(Map<String,Object> map :list){				
			
			map.put("year", mapVo.get("year")) ;
			//判断 医院基本指标数据 是否已存在
			int count = budgHosBasicIndexDataService.queryDataExist(map) ;
		   
			if(count == 0){
				
				//不存在  组装 生成医院基本指标数据维护
				map.put("budg_value","")   ;
			   
				map.put("remark","")   ;
				   
				listVo.add(map);
			}
		}
		String budgWorkHosExecuteJson="";
		if(listVo.size() > 0){
			
			 budgWorkHosExecuteJson = budgHosBasicIndexDataService.addBatch(listVo);
			 
		}else{
			
			budgWorkHosExecuteJson = "{\"error\":\"数据已全部生成,无需再次生成.\"}";
		}
		  
		return JSONObject.parseObject(budgWorkHosExecuteJson);	
	}
	
	/**
	 * 指标名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/queryIndex", method = RequestMethod.POST)
	@ResponseBody
	public String queryIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String typeNature = budgHosBasicIndexDataService.queryIndex(mapVo);
		return typeNature;

	}
	
	
	/**
	 * 最新导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/importBudgBasicIndex", method = RequestMethod.POST)
	@ResponseBody
	public String importBudgBasicIndex(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String reJson=budgHosBasicIndexDataService.importBudgBasicIndex(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\"导入失败.\",\"state\":\"false\"}";
			
		}
	}
	
	
	/**
	 * 计算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/compilationbasic/hosindex/collectBudgHosBasicIndexData", method = RequestMethod.POST)
	@ResponseBody
	public String collectBudgHosBasicIndexData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String reJson = budgHosBasicIndexDataService.collectBudgHosBasicIndexData(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\"计算失败.\",\"state\":\"false\"}";
			
		}
	}
	
	
}

