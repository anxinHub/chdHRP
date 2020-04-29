/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.protocol;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.entity.MatProtocolType;
import com.chd.hrp.mat.serviceImpl.protocol.MatProtocolTypeServiceImpl;
/**
 * 
 * @Description:
 * 04501 协议类别
 * @Table:
 * MAT_PROTOCOL_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatProtocolTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatProtocolTypeController.class);
	
	//引入Service服务
	@Resource(name = "matProtocolTypeService")
	private final MatProtocolTypeServiceImpl matProtocolTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/matProtocolTypeMainPage", method = RequestMethod.GET)
	public String matProtocolTypeMainPage(Model mode) throws Exception {

		return "hrp/mat/protocol/matprotocoltype/matProtocolTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/matProtocolTypeAddPage", method = RequestMethod.GET)
	public String matProtocolTypeAddPage(Model mode) throws Exception {

		return "hrp/mat/protocol/matprotocoltype/matProtocolTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04501 付款协议类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/addMatProtocolType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatProtocolType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String matProtocolTypeJson = matProtocolTypeService.addMatProtocolType(mapVo);

		return JSONObject.parseObject(matProtocolTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04501 付款协议类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/matProtocolTypeUpdatePage", method = RequestMethod.GET)
	public String matProtocolTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		MatProtocolType matProtocolType = new MatProtocolType();
    
		matProtocolType = matProtocolTypeService.queryMatProtocolTypeByCode(mapVo);
		
		mode.addAttribute("group_id", matProtocolType.getGroup_id());
		mode.addAttribute("hos_id", matProtocolType.getHos_id());
		mode.addAttribute("copy_code", matProtocolType.getCopy_code());
		mode.addAttribute("type_id", matProtocolType.getType_id());
		mode.addAttribute("type_code", matProtocolType.getType_code());
		mode.addAttribute("type_name", matProtocolType.getType_name());
		mode.addAttribute("pre", matProtocolType.getPre());
		mode.addAttribute("war_days", matProtocolType.getWar_days());
		mode.addAttribute("spell_code", matProtocolType.getSpell_code());
		mode.addAttribute("wbx_code", matProtocolType.getWbx_code());
		mode.addAttribute("is_stop", matProtocolType.getIs_stop());
		
		return "hrp/mat/protocol/matprotocoltype/matProtocolTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04501 付款协议类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/updateMatProtocolType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProtocolType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
	  
		String matProtocolTypeJson = matProtocolTypeService.updateMatProtocolType(mapVo);
		
		return JSONObject.parseObject(matProtocolTypeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 04501 付款协议类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/addOrUpdateMatProtocolType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatProtocolType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String matProtocolTypeJson ="";
		
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
	  
		matProtocolTypeJson = matProtocolTypeService.addOrUpdateMatProtocolType(detailVo);
		
		}
		return JSONObject.parseObject(matProtocolTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04501 付款协议类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/deleteMatProtocolType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatProtocolType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String matProtocolTypeJson = matProtocolTypeService.deleteBatchMatProtocolType(listVo);
			
	  return JSONObject.parseObject(matProtocolTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 04501 付款协议类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/queryMatProtocolType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProtocolType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matProtocolType = matProtocolTypeService.queryMatProtocolType(getPage(mapVo));

		return JSONObject.parseObject(matProtocolType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04501 付款协议类别
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/matProtocolTypeImportPage", method = RequestMethod.GET)
	public String matProtocolTypeImportPage(Model mode) throws Exception {

		return "hrp/mat/protocol/matprotocoltype/matProtocolTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04501 付款协议类别
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/matprotocoltype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04501 协议类别.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04501 付款协议类别
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/matprotocoltype/readMatProtocolTypeFiles",method = RequestMethod.POST)  
    public String readMatProtocolTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatProtocolType> list_err = new ArrayList<MatProtocolType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatProtocolType matProtocolType = new MatProtocolType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					if (StringTool.isNotBlank(temp[0])) {
						
					matProtocolType.setType_code(String.valueOf(temp[0]));
		    		mapVo.put("type_code", temp[0]);
					
					} else {
						
						err_sb.append("类别代码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					matProtocolType.setType_name(String.valueOf(temp[1]));
		    		mapVo.put("type_name", temp[1]);
					
					} else {
						
						err_sb.append("类别名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					matProtocolType.setPre(String.valueOf(temp[2]));
		    		mapVo.put("pre", temp[2]);
					
					} else {
						
						err_sb.append("协议前缀为空  ");
						
					}
					if (StringTool.isNotBlank(temp[3])) {
						
						matProtocolType.setWar_days(Integer.valueOf(temp[3]));
			    		mapVo.put("war_days", temp[3]);
						
						} else {
							
							err_sb.append("预警天数为空  ");
							
						}
					if (StringTool.isNotBlank(temp[4])) {
						
					matProtocolType.setIs_stop(Integer.valueOf(temp[4]));
		    		mapVo.put("is_stop", temp[4]);
					
					} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					//获取当前物流管理系统的启用年月
					Date current =new Date();
					Map<String, Object> paraMap = matProtocolTypeService.queryYearMonth(mapVo);
					if(Integer.parseInt(String.valueOf(current.getYear())+String.valueOf(current.getMonth())) > 
						Integer.parseInt(String.valueOf(paraMap.get("start_year"))+String.valueOf(paraMap.get("start_month")))){
						matProtocolType.setError_type("协议类别维护日期必须在当前物流管理系统的启用年月:"+ paraMap.get("start_year") + paraMap.get("start_month") + "之前！");
					}
					List<MatProtocolType> extisList = matProtocolTypeService.queryMatProtocolTypeByID(mapVo);

					if (extisList.size()>0) {
						for(MatProtocolType item : extisList){
							if(item.getType_code().equals(mapVo.get("type_code"))){
								if(item.getType_name().equals(mapVo.get("type_name"))){
									if(item.getPre().equals(mapVo.get("pre"))){
										err_sb.append("类别代码:"+mapVo.get("type_code")+",类别名称:"+mapVo.get("type_name")+",协议前缀:"+mapVo.get("pre")+" 重复");
									}else{
										err_sb.append("类别代码:"+mapVo.get("type_code")+",类别名称:"+mapVo.get("type_name")+" 重复");
									}
								}else if(item.getPre().equals(mapVo.get("pre"))){
									err_sb.append("类别代码:"+mapVo.get("type_code")+",类别名称:"+mapVo.get("type_name")+" 重复");
								}else{
									err_sb.append("类别代码:"+mapVo.get("type_code")+" 重复");
								}
							}else{
								if(item.getType_name().equals(mapVo.get("type_name"))){
									if(item.getPre().equals(mapVo.get("pre"))){
										err_sb.append("类别名称:"+mapVo.get("type_name")+",协议前缀:"+mapVo.get("pre")+" 重复");
									}else{
										err_sb.append("类别名称:"+mapVo.get("type_name")+" 重复");
									}
								}else{
									if(item.getPre().equals(mapVo.get("pre"))){
										err_sb.append("协议前缀:"+mapVo.get("pre")+" 重复");
									}
								}
							}
						}
					}
				if (err_sb.toString().length() > 0) {
					
					matProtocolType.setError_type(err_sb.toString());
					
					list_err.add(matProtocolType);
					
				} else {
					  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
					  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
					String dataJson = matProtocolTypeService.addMatProtocolType(mapVo);
				}
			}
			
		} catch (DataAccessException e) {
			
			MatProtocolType data_exc = new MatProtocolType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04501 付款协议类别
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/matprotocoltype/addBatchMatProtocolType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatProtocolType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatProtocolType> list_err = new ArrayList<MatProtocolType>();
		
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
			
			MatProtocolType matProtocolType = new MatProtocolType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					matProtocolType.setType_code(String.valueOf(jsonObj.get("type_code")));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("类别代码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
						
					matProtocolType.setType_name(String.valueOf(jsonObj.get("type_name")));
		    		mapVo.put("type_name", jsonObj.get("type_name"));
		    		} else {
						
						err_sb.append("类别名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pre"))) {
						
						matProtocolType.setPre(String.valueOf(jsonObj.get("pre")));
			    		mapVo.put("pre", jsonObj.get("pre"));
			    		} else {
							
							err_sb.append("协议前缀为空  ");
							
						}
					if (StringTool.isNotBlank(jsonObj.get("war_days"))) {
						
						matProtocolType.setWar_days(Integer.parseInt(String.valueOf(jsonObj.get("war_days"))));
			    		mapVo.put("war_days", jsonObj.get("war_days"));
			    		} else {
							
							err_sb.append("预警天数为空  ");
							
						}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					matProtocolType.setIs_stop(Integer.valueOf(String.valueOf(jsonObj.get("is_stop"))));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
						
					}
					
					Date current =new Date();
					Map<String, Object> paraMap = matProtocolTypeService.queryYearMonth(mapVo);
					if(Integer.parseInt(String.valueOf(current.getYear())+String.valueOf(current.getMonth())) > 
						Integer.parseInt(String.valueOf(paraMap.get("start_year"))+String.valueOf(paraMap.get("start_month")))){
						matProtocolType.setError_type("协议类别维护日期必须在当前物流管理系统的启用年月:"+ paraMap.get("start_year") + paraMap.get("start_month") + "之前！");
					}
					
					List<MatProtocolType> extisList = matProtocolTypeService.queryMatProtocolTypeByID(mapVo);

					if (extisList.size()>0) {
						for(MatProtocolType item : extisList){
							if(item.getType_code().equals(mapVo.get("type_code"))){
								if(item.getType_name().equals(mapVo.get("type_name"))){
									if(item.getPre().equals(mapVo.get("pre"))){
										err_sb.append("类别代码:"+mapVo.get("type_code")+",类别名称:"+mapVo.get("type_name")+",协议前缀:"+mapVo.get("pre")+" 重复");
									}else{
										err_sb.append("类别代码:"+mapVo.get("type_code")+",类别名称:"+mapVo.get("type_name")+" 重复");
									}
								}else if(item.getPre().equals(mapVo.get("pre"))){
									err_sb.append("类别代码:"+mapVo.get("type_code")+",类别名称:"+mapVo.get("type_name")+" 重复");
								}else{
									err_sb.append("类别代码:"+mapVo.get("type_code")+" 重复");
								}
							}else{
								if(item.getType_name().equals(mapVo.get("type_name"))){
									if(item.getPre().equals(mapVo.get("pre"))){
										err_sb.append("类别名称:"+mapVo.get("type_name")+",协议前缀:"+mapVo.get("pre")+" 重复");
									}else{
										err_sb.append("类别名称:"+mapVo.get("type_name")+" 重复");
									}
								}else{
									if(item.getPre().equals(mapVo.get("pre"))){
										err_sb.append("协议前缀:"+mapVo.get("pre")+" 重复");
									}
								}
							}
						}
					}
				if (err_sb.toString().length() > 0) {
					
					matProtocolType.setError_type(err_sb.toString());
					
					list_err.add(matProtocolType);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
				  String dataJson = matProtocolTypeService.addMatProtocolType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatProtocolType data_exc = new MatProtocolType();
			
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

