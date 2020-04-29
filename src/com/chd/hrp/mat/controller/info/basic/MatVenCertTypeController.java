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
import com.chd.hrp.mat.entity.MatVenCertType;
import com.chd.hrp.mat.serviceImpl.info.basic.MatVenCertTypeServiceImpl;
/**
 * 
 * @Description:
 * 04604 供应商证件类型
 * @Table:
 * MAT_VEN_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatVenCertTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatVenCertTypeController.class);
	
	//引入Service服务
	@Resource(name = "matVenCertTypeService")
	private final MatVenCertTypeServiceImpl matVenCertTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/matVenCertTypeMainPage", method = RequestMethod.GET)
	public String matVenCertTypeMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/vencerttype/matVenCertTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/matVenCertTypeAddPage", method = RequestMethod.GET)
	public String matVenCertTypeAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/vencerttype/matVenCertTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/addMatVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
       
		String matVenCertTypeJson = matVenCertTypeService.addMatVenCertType(mapVo);

		return JSONObject.parseObject(matVenCertTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/matVenCertTypeUpdatePage", method = RequestMethod.GET)
	public String matVenCertTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MatVenCertType matVenCertType = new MatVenCertType();
    
		matVenCertType = matVenCertTypeService.queryMatVenCertTypeByCode(mapVo);
		
		mode.addAttribute("group_id", matVenCertType.getGroup_id());
		mode.addAttribute("hos_id", matVenCertType.getHos_id());
		mode.addAttribute("copy_code", matVenCertType.getCopy_code());
		mode.addAttribute("type_id", matVenCertType.getType_id());
		mode.addAttribute("type_code", matVenCertType.getType_code());
		mode.addAttribute("type_name", matVenCertType.getType_name());
		mode.addAttribute("spell_code", matVenCertType.getSpell_code());
		mode.addAttribute("wbx_code", matVenCertType.getWbx_code());
		mode.addAttribute("is_alarm", matVenCertType.getIs_alarm());
		mode.addAttribute("war_days", matVenCertType.getWar_days());
		
		return "hrp/mat/info/basic/cert/vencerttype/matVenCertTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/updateMatVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		  
		  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		
		  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
	  
		String matVenCertTypeJson = matVenCertTypeService.updateMatVenCertType(mapVo);
		
		return JSONObject.parseObject(matVenCertTypeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 04604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/addOrUpdateMatVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matVenCertTypeJson ="";
		
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
		  
		detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("type_name").toString()));
		
		detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("type_name").toString()));
	  
		matVenCertTypeJson = matVenCertTypeService.addOrUpdateMatVenCertType(detailVo);
		
		}
		return JSONObject.parseObject(matVenCertTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/deleteMatVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatVenCertType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("type_id", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String matVenCertTypeJson = matVenCertTypeService.deleteBatchMatVenCertType(listVo);
			
	  return JSONObject.parseObject(matVenCertTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 04604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/queryMatVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matVenCertType = matVenCertTypeService.queryMatVenCertType(getPage(mapVo));

		return JSONObject.parseObject(matVenCertType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04604 供应商证件类型
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/matVenCertTypeImportPage", method = RequestMethod.GET)
	public String matVenCertTypeImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/vencerttype/matVenCertTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04604 供应商证件类型
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/info/basic/cert/vencerttype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04604 供应商证件类型.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04604 供应商证件类型
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/basic/cert/vencerttype/readMatVenCertTypeFiles",method = RequestMethod.POST)  
    public String readMatVenCertTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatVenCertType> list_err = new ArrayList<MatVenCertType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatVenCertType matVenCertType = new MatVenCertType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					matVenCertType.setType_code(String.valueOf(temp[0]));
		    		mapVo.put("type_code", temp[0]);
					
					} else {
						
						err_sb.append("证件类型编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					matVenCertType.setType_name(String.valueOf(temp[1]));
		    		mapVo.put("type_name", temp[1]);
					
					} else {
						
						err_sb.append("证件类型名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					matVenCertType.setIs_alarm(Integer.valueOf(temp[2]));
		    		mapVo.put("is_alarm", temp[2]);
					
					} else {
						
						err_sb.append("是否预警为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matVenCertType.setWar_days(Integer.valueOf(temp[3]));
		    		mapVo.put("war_days", temp[3]);
					
					} else {
						
						err_sb.append("预警天数为空  ");
						
					}
					 
					List<MatVenCertType> extisList = matVenCertTypeService.queryMatVenCertTypeByID(mapVo);

					if (extisList.size()>0) {
						for(MatVenCertType item: extisList){
							if(item.getType_code().equals(mapVo.get("type_code"))){
								err_sb.append("证件分类编码:"+mapVo.get("type_code")+"已存在");
							}
							if(item.getType_name().equals(mapVo.get("type_name"))){
								err_sb.append("证件分类名称:"+mapVo.get("type_name")+"已存在");
							}
						}
					}
				if (err_sb.toString().length() > 0) {
					
					matVenCertType.setError_type(err_sb.toString());
					
					list_err.add(matVenCertType);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			  
					String dataJson = matVenCertTypeService.addMatVenCertType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatVenCertType data_exc = new MatVenCertType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04604 供应商证件类型
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/vencerttype/addBatchMatVenCertType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatVenCertType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatVenCertType> list_err = new ArrayList<MatVenCertType>();
		
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
			
			MatVenCertType matVenCertType = new MatVenCertType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					matVenCertType.setType_code(String.valueOf(jsonObj.get("type_code")));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("证件类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
						
					matVenCertType.setType_name(String.valueOf(jsonObj.get("type_name")));
		    		mapVo.put("type_name", jsonObj.get("type_name"));
		    		} else {
						
						err_sb.append("证件类型名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_alarm"))) {
						
					matVenCertType.setIs_alarm(Integer.valueOf(String.valueOf(jsonObj.get("is_alarm"))));
		    		mapVo.put("is_alarm", jsonObj.get("is_alarm"));
		    		} else {
						
						err_sb.append("是否预警为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("war_days"))) {
						
					matVenCertType.setWar_days(Integer.valueOf(String.valueOf(jsonObj.get("war_days"))));
		    		mapVo.put("war_days", jsonObj.get("war_days"));
		    		} else {
						
						err_sb.append("预警天数为空  ");
						
					}
					List<MatVenCertType> extisList = matVenCertTypeService.queryMatVenCertTypeByID(mapVo);
					if (extisList.size()>0) {
						for(MatVenCertType item: extisList){
							if(item.getType_code().equals(mapVo.get("type_code"))){
								err_sb.append("证件分类编码:"+mapVo.get("type_code")+"已存在");
							}
							if(item.getType_name().equals(mapVo.get("type_name"))){
								err_sb.append("证件分类名称:"+mapVo.get("type_name")+"已存在");
							}
						}
					}
				if (err_sb.toString().length() > 0) {
					
					matVenCertType.setError_type(err_sb.toString());
					
					list_err.add(matVenCertType);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			  
					String dataJson = matVenCertTypeService.addMatVenCertType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatVenCertType data_exc = new MatVenCertType();
			
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

