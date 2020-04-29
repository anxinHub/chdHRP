
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.controller.dict;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.dict.AssNaturs;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssNatursService;

/**
 * 
 * @Description:
 * 050103 资产性质
 * @Table:
 * ASS_NATURS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class AssNatursController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssNatursController.class);
	
	//引入Service服务
	@Resource(name = "assNatursService")
	private final AssNatursService assNatursService = null;
   
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assnaturs/assNatursMainPage", method = RequestMethod.GET)
	public String assNatursMainPage(Model mode) throws Exception {

		return "hrp/ass/assnaturs/assNatursMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assnaturs/assNatursAddPage", method = RequestMethod.GET)
	public String assNatursAddPage(Model mode) throws Exception {

		return "hrp/ass/assnaturs/assNatursAdd";

	}

	/**
	 * @Description 
	 * 添加数据 050103 资产性质
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assnaturs/addAssNaturs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssNaturs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assNatursJson = "" ;
		
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
       
		try{
		 
			assNatursJson = assNatursService.addAssNaturs(mapVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(assNatursJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 050103 资产性质
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assnaturs/assNatursUpdatePage", method = RequestMethod.GET)
	public String assNatursUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
	/*	String subjType = assNatursService.queryHosStoreDict(getPage(mapVo));
		JSONObject.parseObject(subjType);*/
    AssNaturs assNaturs = new AssNaturs();
    
		assNaturs = assNatursService.queryAssNatursByCode(mapVo);
		
		mode.addAttribute("naturs_code", assNaturs.getNaturs_code());
		mode.addAttribute("naturs_name", assNaturs.getNaturs_name());
		
		return "hrp/ass/assnaturs/assNatursUpdate";
	}
/*	*//**
	 * 仓库检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 *//*
	@RequestMapping(value = "/hrp/ass/assnaturs/queryHosStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("naturs_code", "0"+mapVo.get("naturs_code"));
		String subjType = assNatursService.queryHosStoreDictByNaturs(getPage(mapVo));
	return JSONObject.parseObject(subjType);
	}*/
	
	/**
	 * 资产仓库检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/ass/assnaturs/queryHosStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosStoreDictByNaturs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("naturs_code", "0"+mapVo.get("naturs_code"));
		mapVo.put("user_id", SessionManager.getUserId());
		String subj = assNatursService.queryHosStoreDict(getPage(mapVo));
	return JSONObject.parseObject(subj);
	}
	/**
	 * @Description 
	 * 修改资产性质对应仓库
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assnaturs/updateAssNatursStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssNatursStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String assNatursJson =  "" ;
		ArrayList<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("dataItem").toString());
		if(detail.size() == 0 || detail == null){
			Map<String, Object> mapStore= new HashMap<String, Object>();
			mapStore.put("naturs_code", mapVo.get("naturs_code"));
			mapStore.put("group_id",  SessionManager.getGroupId());
			mapStore.put("hos_id", SessionManager.getHosId());
			mapStore.put("copy_code", SessionManager.getCopyCode());
			listVo.add(mapStore);
		}
		
		for (Map<String, Object> detailVo : detail) {
			detailVo.put("naturs_code", mapVo.get("naturs_code"));
			Map<String, Object> mapStore= new HashMap<String, Object>();
			mapStore.put("group_id", SessionManager.getGroupId());

			mapStore.put("hos_id", SessionManager.getHosId());

			mapStore.put("copy_code", SessionManager.getCopyCode());

			
			mapStore.put("store_id", detailVo.get("store_id"));
			mapStore.put("store_name", detailVo.get("text"));
			mapStore.put("naturs_code", detailVo.get("naturs_code"));

			


			listVo.add(mapStore);
		}
		try{
		 
			assNatursJson = assNatursService.updateAssNatursStore(listVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(assNatursJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 050103 资产性质
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assnaturs/updateAssNaturs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssNaturs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String assNatursJson =  "" ;
		
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
		
		try{
		 
			assNatursJson = assNatursService.updateAssNaturs(mapVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(assNatursJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050103 资产性质
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assnaturs/deleteAssNaturs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssNaturs(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String assNatursJson = "" ;
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("naturs_code", ids[0]);
				
	      listVo.add(mapVo);
	      
	    }
	    try{
			 
	    	assNatursJson = assNatursService.deleteBatchAssNaturs(listVo);
		
	    }catch(Exception e){
			
	    	return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	  
	    return JSONObject.parseObject(assNatursJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 050103 资产性质
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assnaturs/queryAssNaturs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssNaturs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		mapVo.put("user_id", SessionManager.getUserId());
		String assNaturs = assNatursService.queryAssNaturs(getPage(mapVo));

		return JSONObject.parseObject(assNaturs);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 050103 资产性质
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assnaturs/assNatursImportPage", method = RequestMethod.GET)
	public String assNatursImportPage(Model mode) throws Exception {

		return "hrp/ass/assnaturs/assNatursImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 050103 资产性质
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/ass/assnaturs/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"base\\目录","模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050103 资产性质
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/cost/assnaturs/readAssNatursFiles",method = RequestMethod.POST)  
    public String readAssNatursFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssNaturs> list_err = new ArrayList<AssNaturs>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssNaturs assNaturs = new AssNaturs();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					
					if (StringTool.isNotBlank(temp[0])) {
						
					assNaturs.setNaturs_code(temp[0]);
								
		    		mapVo.put("naturs_code", temp[0]);
		    		
					} else {
						
						err_sb.append("性质编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					assNaturs.setNaturs_name(temp[1]);
								
		    		mapVo.put("naturs_name", temp[1]);
		    		
					} else {
						
						err_sb.append("性质名称为空  ");
						
					}
					
				AssNaturs data_exc_extis = assNatursService.queryAssNatursByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assNaturs.setError_type(err_sb.toString());
					
					list_err.add(assNaturs);
					
				} else {
			  
					try{
				
						String dataJson = assNatursService.addAssNaturs(mapVo);
				
					}catch(Exception e){
						
						return  "{\"error\":\""+e.getMessage()+" \"}" ;
					
					}
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssNaturs data_exc = new AssNaturs();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050103 资产性质
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/cost/assnaturs/addBatchAssNaturs", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssNaturs(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssNaturs> list_err = new ArrayList<AssNaturs>();
		
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
			
			AssNaturs assNaturs = new AssNaturs();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					if (StringTool.isNotBlank(jsonObj.get("naturs_code"))) {
						
					assNaturs.setNaturs_code((String)jsonObj.get("naturs_code"));
											
		    		mapVo.put("naturs_code", jsonObj.get("naturs_code"));
		    		
					} else {
						
						err_sb.append("性质编码为空  ");
						
					}
					if (StringTool.isNotBlank(jsonObj.get("naturs_name"))) {
						
					assNaturs.setNaturs_name((String)jsonObj.get("naturs_name"));
											
		    		mapVo.put("naturs_name", jsonObj.get("naturs_name"));
		    		
					} else {
						
						err_sb.append("性质名称为空  ");
						
					}
					
				AssNaturs data_exc_extis = assNatursService.queryAssNatursByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assNaturs.setError_type(err_sb.toString());
					
					list_err.add(assNaturs);
					
				} else {
			  
					try{
					
						String dataJson = assNatursService.addAssNaturs(mapVo);
				
					}catch(Exception e){
						
						return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
					
					}
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssNaturs data_exc = new AssNaturs();
			
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

