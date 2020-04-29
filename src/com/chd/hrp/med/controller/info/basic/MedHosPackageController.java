/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.mat.entity.HosPackage;
import com.chd.hrp.mat.serviceImpl.info.basic.HosPackageServiceImpl;
/**
 * 
 * @Description:
 * 04117 包装单位表
 * @Table:
 * HOS_PACKAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedHosPackageController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedHosPackageController.class);
	
	//引入Service服务
	@Resource(name = "hosPackageService")
	private final HosPackageServiceImpl hosPackageService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/hosPackageMainPage", method = RequestMethod.GET)
	public String hosPackageMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/hospackage/hosPackageMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/hosPackageAddPage", method = RequestMethod.GET)
	public String hosPackageAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/hospackage/hosPackageAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04117 包装单位表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/addHosPackage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHosPackage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if("系统生成".equals(mapVo.get("pack_code"))){
			mapVo.put("pack_code", null);
			}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pack_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pack_name").toString()));
       
		String hosPackageJson = hosPackageService.addHosPackage(mapVo);

		return JSONObject.parseObject(hosPackageJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04117 包装单位表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/hosPackageUpdatePage", method = RequestMethod.GET)
	public String hosPackageUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		HosPackage hosPackage = new HosPackage();
    
		hosPackage = hosPackageService.queryHosPackageByCode(mapVo);
		
		mode.addAttribute("group_id", hosPackage.getGroup_id());
		mode.addAttribute("hos_id", hosPackage.getHos_id());
		mode.addAttribute("pack_code", hosPackage.getPack_code());
		mode.addAttribute("pack_name", hosPackage.getPack_name());
		mode.addAttribute("is_stop", hosPackage.getIs_stop());
		mode.addAttribute("spell_code", hosPackage.getSpell_code());
		mode.addAttribute("wbx_code", hosPackage.getWbx_code());
		mode.addAttribute("note", hosPackage.getNote());
		
		return "hrp/mat/info/basic/hospackage/hosPackageUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04117 包装单位表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/updateHosPackage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHosPackage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pack_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pack_name").toString()));
	  
		String hosPackageJson = hosPackageService.updateHosPackage(mapVo);
		
		return JSONObject.parseObject(hosPackageJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 04117 包装单位表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/addOrUpdateHosPackage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateHosPackage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String hosPackageJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("pack_name").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("pack_name").toString()));
	  
		hosPackageJson = hosPackageService.addOrUpdateHosPackage(detailVo);
		
		}
		return JSONObject.parseObject(hosPackageJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04117 包装单位表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/deleteHosPackage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHosPackage(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("pack_code", ids[2]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String hosPackageJson = hosPackageService.deleteBatchHosPackage(listVo);
			
	  return JSONObject.parseObject(hosPackageJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 04117 包装单位表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/queryHosPackage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosPackage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String hosPackage = hosPackageService.queryHosPackage(getPage(mapVo));

		return JSONObject.parseObject(hosPackage);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04117 包装单位表
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/hosPackageImportPage", method = RequestMethod.GET)
	public String hosPackageImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/hospackage/hosPackageImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04117 包装单位表
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/hospackage/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04117 包装单位定义.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04117 包装单位表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/info/basic/hospackage/readHosPackageFiles",method = RequestMethod.POST)  
    public String readHosPackageFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
		
		
		Map<String, Object> is_Stop_Map = new HashMap<String, Object>();
		
		is_Stop_Map.put("0", "0");
		
		is_Stop_Map.put("1", "1");
		
		is_Stop_Map.put("否", "0");
		
		is_Stop_Map.put("是", "1");
		
		List<Map<String, Object>> addlistMaps = new ArrayList<Map<String,Object>>();
		
		List<HosPackage> list_err = new ArrayList<HosPackage>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				HosPackage hosPackage = new HosPackage();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		
		    		
		    		if(ExcelReader.validExceLColunm(temp, 0)){
		    			
		    			hosPackage.setPack_code(temp[0]);
		    			
		    			mapVo.put("pack_code", temp[0]);
		    			
		    		}else {
		    			
		    			err_sb.append("包装单位编码为空 ");
		    		}
		    		         
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
					  hosPackage.setPack_name(temp[1]);
					
		    		   mapVo.put("pack_name", temp[1]);
					
					} else {
						
						err_sb.append("包装单位名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
                         if(is_Stop_Map.containsKey(temp[2])){
							
							hosPackage.setIs_stop(Integer.parseInt(is_Stop_Map.get(temp[2]).toString()));
							
				    		mapVo.put("is_stop", Integer.parseInt(is_Stop_Map.get(temp[2]).toString()));
							
						}else {
							
							err_sb.append("是否停用输入错误  ");
						}

					
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						  hosPackage.setNote(temp[3]);
						
			    		   mapVo.put("note", temp[3]);
						
						} else {
							
							  hosPackage.setNote(null);
								
				    		  mapVo.put("note", null);
							
						}
					 
					HosPackage existList = hosPackageService.queryHosPackageByCode(mapVo);
					
					if (existList  != null) {
						
						err_sb.append("包装单位编码已经存在！");
						
					}
				if (err_sb.toString().length() > 0) {
					
					hosPackage.setError_type(err_sb.toString());
					
					list_err.add(hosPackage);
					
				} else {
					
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pack_name").toString()));
				  
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pack_name").toString()));
			  
				  addlistMaps.add(mapVo);
				}
				
			}
			
			if(addlistMaps.size() > 0 ){
				
				hosPackageService.addBatchHosPackage(addlistMaps);
				
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			
			HosPackage data_exc = new HosPackage();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04117 包装单位表
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/hospackage/addBatchHosPackage", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchHosPackage(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> addlistMaps = new ArrayList<Map<String,Object>>();
		
		List<HosPackage> list_err = new ArrayList<HosPackage>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
			}
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			HosPackage hosPackage = new HosPackage();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
			if (StringTool.isNotBlank(jsonObj.get("pack_code"))) {
				
			hosPackage.setPack_code(jsonObj.get("pack_code").toString());
			
    		mapVo.put("pack_code", jsonObj.get("pack_code").toString());
    		
    		} else {
				
				err_sb.append("包装单位编码为空  ");
				
			}
					
					if (StringTool.isNotBlank(jsonObj.get("pack_name"))) {
						
					hosPackage.setPack_name(jsonObj.get("pack_name").toString());
					
		    		mapVo.put("pack_name", jsonObj.get("pack_name").toString());
		    		
		    		} else {
						
						err_sb.append("包装单位名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					hosPackage.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
					
		    		mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));
		    		
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					hosPackage.setNote(jsonObj.get("note").toString());
					
		    		mapVo.put("note", jsonObj.get("note").toString());
		    		
		    		} else {
						
						hosPackage.setNote(null);
						
			    		mapVo.put("note", null);
						
					}
					
				HosPackage existList = hosPackageService.queryHosPackageByCode(mapVo);
				
				if (existList  != null) {
					
					err_sb.append("包装单位编码已经存在！");
					
				}
				if (err_sb.toString().length() > 0) {
					
					hosPackage.setError_type(err_sb.toString());
					
					list_err.add(hosPackage);
					
				} else {
					
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pack_name").toString()));
				  
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pack_name").toString()));
			  
				  addlistMaps.add(mapVo);
					
				}
				
			}
			if(addlistMaps.size() > 0 ){
				
				hosPackageService.addBatchHosPackage(addlistMaps);
				
			}
		} catch (DataAccessException e) {
			
			e.printStackTrace();
			
			HosPackage data_exc = new HosPackage();
			
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

