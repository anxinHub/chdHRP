/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.info.basic;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatStockType;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStockTypeServiceImpl;
/**
 * 
 * @Description:
 * 04118 采购类型
 * @Table:
 * MAT_STOCK_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatStockTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatStockTypeController.class);
	
	//引入Service服务
	@Resource(name = "matStockTypeService")
	private final MatStockTypeServiceImpl matStockTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matstocktype/matStockTypeMainPage", method = RequestMethod.GET)
	public String matStockTypeMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matstocktype/matStockTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matstocktype/matStockTypeAddPage", method = RequestMethod.GET)
	public String matStockTypeAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matstocktype/matStockTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matstocktype/addMatStockType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){	
			mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());        
		}
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("stock_type_name").toString()));
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("stock_type_name").toString()));
       
		String matStockTypeJson = matStockTypeService.add(mapVo);

		return JSONObject.parseObject(matStockTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matstocktype/matStockTypeUpdatePage", method = RequestMethod.GET)
	public String matStockTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	  if(mapVo.get("group_id") == null){	
		  mapVo.put("group_id", SessionManager.getGroupId());	
	  }
		
	  if(mapVo.get("hos_id") == null){	
		mapVo.put("hos_id", SessionManager.getHosId());	
	   }
		
	if(mapVo.get("copy_code") == null){	
		mapVo.put("copy_code", SessionManager.getCopyCode());      
	}
		
    MatStockType matStockType = new MatStockType();
    
	matStockType = matStockTypeService.queryByCode(mapVo);
	
	mode.addAttribute("group_id", matStockType.getGroup_id());
	mode.addAttribute("hos_id", matStockType.getHos_id());
	mode.addAttribute("copy_code", matStockType.getCopy_code());
	mode.addAttribute("stock_type_code", matStockType.getStock_type_code());
	mode.addAttribute("stock_type_name", matStockType.getStock_type_name());
	mode.addAttribute("wbx_code", matStockType.getWbx_code());
	mode.addAttribute("spell_code", matStockType.getSpell_code());
	mode.addAttribute("is_stop", matStockType.getIs_stop());
	mode.addAttribute("note", matStockType.getNote());
		
	return "hrp/mat/info/basic/matstocktype/matStockTypeUpdate";
}
		
	/**
	 * @Description 
	 * 更新数据 04118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matstocktype/updateMatStockType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){				
			mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){			
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());        
		}
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("stock_type_name").toString()));
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("stock_type_name").toString()));
		String matStockTypeJson = matStockTypeService.update(mapVo);
		
		return JSONObject.parseObject(matStockTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matstocktype/deleteMatStockType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatStockType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				mapVo.put("copy_code", SessionManager.getCopyCode())   ;
				mapVo.put("stock_type_code", ids[3]) ; 
				
	      listVo.add(mapVo);
	      
	    }
	    
			String matStockTypeJson = matStockTypeService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(matStockTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 04118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matstocktype/queryMatStockType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){			
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){			
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());       
		}
		String matStockType = matStockTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(matStockType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04118 采购类型
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matstocktype/matStockTypeImportPage", method = RequestMethod.GET)
	public String matStockTypeImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/matstocktype/matStockTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04118 采购类型
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/info/basic/matstocktype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04118 采购类型.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04118 采购类型
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/basic/matstocktype/readMatStockTypeFiles",method = RequestMethod.POST)  
    public String readMatStockTypeFiles(Plupload plupload,HttpServletRequest request,HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatStockType> list_err = new ArrayList<MatStockType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatStockType matStockType = new MatStockType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					matStockType.setStock_type_code(temp[0]);
		    		mapVo.put("stock_type_code", temp[0]);
					
					} else {
						
						err_sb.append("采购类型编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					matStockType.setStock_type_name(temp[1]);
		    		mapVo.put("stock_type_name", temp[1]);
					
					} else {
						
						err_sb.append("采购类型名称为空  ");
						
					}
					 
				
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					matStockType.setIs_stop(Integer.parseInt(temp[2]));
					
		    		mapVo.put("is_stop", Integer.parseInt(temp[2]));
					
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					 

					 
					
				MatStockType data_exc_extis = matStockTypeService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStockType.setError_type(err_sb.toString());
					
					list_err.add(matStockType);
					
				} else {
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("stock_type_name").toString()));
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("stock_type_name").toString()));
			  
					String dataJson = matStockTypeService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStockType data_exc = new MatStockType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04118 采购类型
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/matstocktype/addBatchMatStockType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatStockType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatStockType> list_err = new ArrayList<MatStockType>();
		
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
			
			MatStockType matStockType = new MatStockType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("stock_type_code"))) {
						
					matStockType.setStock_type_code(jsonObj.get("stock_type_code").toString());
		    		mapVo.put("stock_type_code", jsonObj.get("stock_type_code"));
		    		} else {
						
						err_sb.append("采购类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("stock_type_name"))) {
						
					matStockType.setStock_type_name(jsonObj.get("stock_type_name").toString());
		    		mapVo.put("stock_type_name", jsonObj.get("stock_type_name"));
		    		} else {
						
						err_sb.append("采购类型名称为空  ");
						
					}
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					matStockType.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否为空  ");
						
					}
					
					
					
				MatStockType data_exc_extis = matStockTypeService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					matStockType.setError_type(err_sb.toString());
					
					list_err.add(matStockType);
					
				} else {
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("stock_type_name").toString()));
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("stock_type_name").toString()));
			  
					String dataJson = matStockTypeService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatStockType data_exc = new MatStockType();
			
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

