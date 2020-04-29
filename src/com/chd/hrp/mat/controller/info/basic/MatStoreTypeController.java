/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatStoreType;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreTypeServiceImpl;
/**
 * 
 * @Description:
 * 04111 仓库类别信息
 * @Table:
 * MAT_STORE_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatStoreTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatStoreTypeController.class);
	
	//引入Service服务
	@Resource(name = "matStoreTypeService")
	private final MatStoreTypeServiceImpl matStoreTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/matStoreTypeMainPage", method = RequestMethod.GET)
	public String matStoreTypeMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/storeinv/matstoretype/matStoreTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/matStoreTypeAddPage", method = RequestMethod.GET)
	public String matStoreTypeAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		return "hrp/mat/info/basic/storeinv/matstoretype/matStoreTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/addMatStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStoreType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> deleteList= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			Map<String, Object> deleteMap=new HashMap<String, Object>();
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("mat_type_id", ids[3]);
			mapVo.put("store_id", ids[4])   ;
			listVo.add(mapVo);
			
			deleteMap.put("group_id", ids[0])   ;
			deleteMap.put("hos_id", ids[1])   ;
			deleteMap.put("copy_code", ids[2])   ;
			deleteMap.put("store_id", ids[4])   ;
			deleteList.add(deleteMap);
		}
		String matStoreTypeJson =null;
		
		if(deleteList.size()>0){
        	matStoreTypeJson = matStoreTypeService.deleteBatchMatStoreType(deleteList);
        }
		
        if(listVo.size()>0){
        	matStoreTypeJson = matStoreTypeService.addBatchMatStoreType(listVo);
        }
		

		return JSONObject.parseObject(matStoreTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/matStoreTypeUpdatePage", method = RequestMethod.GET)
	public String matStoreTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MatStoreType matStoreType = new MatStoreType();
    
		matStoreType = matStoreTypeService.queryMatStoreTypeByCode(mapVo);
		
		mode.addAttribute("group_id", matStoreType.getGroup_id());
		mode.addAttribute("hos_id", matStoreType.getHos_id());
		mode.addAttribute("copy_code", matStoreType.getCopy_code());
		mode.addAttribute("store_id", matStoreType.getStore_id());
		mode.addAttribute("mat_type_id", matStoreType.getMat_type_id());
		
		return "hrp/mat/info/basic/storeinv/matstoretype/matStoreTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/updateMatStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String matStoreTypeJson = matStoreTypeService.updateMatStoreType(mapVo);
		
		return JSONObject.parseObject(matStoreTypeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 04111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/addOrUpdateMatStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matStoreTypeJson ="";
		
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
	  
		matStoreTypeJson = matStoreTypeService.addOrUpdateMatStoreType(detailVo);
		
		}
		return JSONObject.parseObject(matStoreTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/deleteMatStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatStoreType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("store_id", ids[3])   ;
				mapVo.put("mat_type_id", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String matStoreTypeJson = matStoreTypeService.deleteBatchMatStoreType(listVo);
			
	  return JSONObject.parseObject(matStoreTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 根据仓库ID 删除其对应的所有对应关系数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/saveMatStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatStoreType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		String[] id=paramVo.split(",");
		String[] ids = id[0].split("@");
		
		//表的主键
		mapVo.put("group_id",SessionManager.getGroupId() );
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("store_id", ids[0]);
				
	      
	    Long count = matStoreTypeService.queryExist(mapVo);
	    String matStoreTypeJson = null;
	    if(count > 0){
	    	 matStoreTypeJson = matStoreTypeService.deleteMatStoreType(mapVo);
	    }else{
	    	matStoreTypeJson = "{\"error\":\"无操作数据.\",\"state\":\"true\"}";
	    }
		
	  return JSONObject.parseObject(matStoreTypeJson);
			
	}
	/**
	 * @Description 
	 * 查询数据 04111 仓库类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/queryMatStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matStoreType = matStoreTypeService.queryMatStoreType(getPage(mapVo));

		return JSONObject.parseObject(matStoreType);
		
	}
	/**
	 * 查询出物资类别字典表 MAT_TYPE中IS_STOP=0的所有物资类别记录
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/queryStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matStoreType = matStoreTypeService.queryStoreType(getPage(mapVo));

		return JSONObject.parseObject(matStoreType);
		
	}
  /**
	 * @Description 
	 * 导入跳转页面 04111 仓库类别信息
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/matStoreTypeImportPage", method = RequestMethod.GET)
	public String matStoreTypeImportPage(Model mode) throws Exception {

		return "hrp/mat/matstoretype/matStoreTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04111 仓库类别信息
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/matstoretype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04111 仓库类别信息.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04111 仓库类别信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/basic/storeinv/matstoretype/readMatStoreTypeFiles",method = RequestMethod.POST)  
    public String readMatStoreTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatStoreType> list_err = new ArrayList<MatStoreType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatStoreType matStoreType = new MatStoreType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matStoreType.setStore_id(Long.valueOf(temp[3]));
		    		mapVo.put("store_id", temp[3]);
					
					} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matStoreType.setMat_type_id(Long.valueOf(temp[4]));
		    		mapVo.put("mat_type_id", temp[4]);
					
					} else {
						
						err_sb.append("物资类别ID为空  ");
						
					}
					 
					
				MatStoreType data_exc_extis = matStoreTypeService.queryMatStoreTypeByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStoreType.setError_type(err_sb.toString());
					
					list_err.add(matStoreType);
					
				} else {
			  
					String dataJson = matStoreTypeService.addMatStoreType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStoreType data_exc = new MatStoreType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04111 仓库类别信息
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/addBatchMatStoreType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatStoreType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatStoreType> list_err = new ArrayList<MatStoreType>();
		
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
			
			MatStoreType matStoreType = new MatStoreType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					matStoreType.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("仓库ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("mat_type_id"))) {
						
					matStoreType.setMat_type_id(Long.valueOf((String)jsonObj.get("mat_type_id")));
		    		mapVo.put("mat_type_id", jsonObj.get("mat_type_id"));
		    		} else {
						
						err_sb.append("物资类别ID为空  ");
						
					}
					
					
				MatStoreType data_exc_extis = matStoreTypeService.queryMatStoreTypeByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStoreType.setError_type(err_sb.toString());
					
					list_err.add(matStoreType);
					
				} else {
			  
					String dataJson = matStoreTypeService.addMatStoreType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStoreType data_exc = new MatStoreType();
			
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
	 * 查询出物资类别字典表去除已经做出对应关系
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/queryMatTypeByStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatTypeByStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matTypeByStore = matStoreTypeService.queryMatTypeByStore(mapVo);

		return JSONObject.parseObject(matTypeByStore);
		
	}
	
	
	/**
	 * @Description 
	 * 添加数据 04110 仓库物资类别信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/storeinv/matstoretype/addMatTypeByStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatTypeByStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String user_id = SessionManager.getUserId();
		Date date = new Date();
		//System.out.println("paramVo====="+paramVo);
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			
				Map<String, Object> mapVo=new HashMap<String, Object>();
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("mat_type_id", ids[0]);
				mapVo.put("store_id", ids[1]) ;
				listVo.add(mapVo);
		}
		
		String matStoreTypeJson = null ;
		
		try {
			
			matStoreTypeJson = matStoreTypeService.addMatTypeByStore(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			matStoreTypeJson = e.getMessage();
		}

		return JSONObject.parseObject(matStoreTypeJson);
		
	}
	
    
}

