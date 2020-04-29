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
import org.nutz.lang.Strings;
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
import com.chd.hrp.mat.entity.MatInvCertType;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.info.basic.MatInvCertTypeService;
import com.chd.hrp.mat.serviceImpl.info.basic.MatInvCertTypeServiceImpl;
/**
 * 
 * @Description:
 * 04601 材料证件类型字典
 * @Table:
 * MAT_INV_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatInvCertTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatInvCertTypeController.class);
	
	//引入Service服务
	@Resource(name = "matInvCertTypeService")
	private final MatInvCertTypeServiceImpl matInvCertTypeService = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcerttype/matInvCertTypeMainPage", method = RequestMethod.GET)
	public String matInvCertTypeMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcerttype/matInvCertTypeMain"; 

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcerttype/matInvCertTypeAddPage", method = RequestMethod.GET)
	public String matInvCertTypeAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcerttype/matInvCertTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 04601 材料证件类型字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcerttype/addMatInvCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String matInvCertTypeJson = matInvCertTypeService.addMatInvCertType(mapVo);

		return JSONObject.parseObject(matInvCertTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 04601 材料证件类型字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcerttype/matInvCertTypeUpdatePage", method = RequestMethod.GET)
	public String matInvCertTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    MatInvCertType matInvCertType = new MatInvCertType();
    
		matInvCertType = matInvCertTypeService.queryMatInvCertTypeByCode(mapVo);
		
		mode.addAttribute("group_id", matInvCertType.getGroup_id());
		mode.addAttribute("hos_id", matInvCertType.getHos_id());
		mode.addAttribute("copy_code", matInvCertType.getCopy_code());
		mode.addAttribute("type_id", matInvCertType.getType_id());
		mode.addAttribute("type_code", matInvCertType.getType_code());
		mode.addAttribute("type_name", matInvCertType.getType_name());
		mode.addAttribute("validity_type", matInvCertType.getValidity_type());
		mode.addAttribute("spell_code", matInvCertType.getSpell_code());
		mode.addAttribute("wbx_code", matInvCertType.getWbx_code());
		mode.addAttribute("is_alarm", matInvCertType.getIs_alarm());
		mode.addAttribute("war_days", matInvCertType.getWar_days());
		
		return "hrp/mat/info/basic/cert/invcerttype/matInvCertTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 04601 材料证件类型字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcerttype/updateMatInvCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInvCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String matInvCertTypeJson = matInvCertTypeService.updateMatInvCertType(mapVo);
		
		return JSONObject.parseObject(matInvCertTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 04601 材料证件类型字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcerttype/deleteMatInvCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvCertType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String useStr = "";
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("type_id", ids[3]);
				mapVo.put("type_code", ids[4]);
				int isUse = matInvCertTypeService.queryDelDate(mapVo);
				if(isUse >0){
					useStr += mapVo.get("type_code") +",";
				}
	      listVo.add(mapVo);
	      
	    }
			
		if(useStr != null && !"".equals(useStr)){
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的数据：【" + useStr.substring(0, useStr.length() - 1) + "】已被使用,不允许删除。\",\"state\":\"false\"}");
		}
		String matInvCertTypeJson = matInvCertTypeService.deleteBatchMatInvCertType(listVo);
		
		return JSONObject.parseObject(matInvCertTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 04601 材料证件类型字典
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcerttype/queryMatInvCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matInvCertType = matInvCertTypeService.queryMatInvCertType(getPage(mapVo));

		return JSONObject.parseObject(matInvCertType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 04601 材料证件类型字典
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcerttype/matInvCertTypeImportPage", method = RequestMethod.GET)
	public String matInvCertTypeImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/cert/invcerttype/matInvCertTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 04601 材料证件类型字典
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/mat/info/basic/cert/invcerttype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"mat\\downTemplate","04601 材料证件类型字典.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 04601 材料证件类型字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/mat/info/basic/cert/invcerttype/readMatInvCertTypeFiles",method = RequestMethod.POST)  
    public String readMatInvCertTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MatInvCertType> list_err = new ArrayList<MatInvCertType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatInvCertType matInvCertType = new MatInvCertType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					matInvCertType.setType_code(String.valueOf(temp[0]));
		    		mapVo.put("type_code", temp[0]);
					
					} else {
						
						err_sb.append("证件类型编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					matInvCertType.setType_name(String.valueOf(temp[1]));
		    		mapVo.put("type_name", temp[1]);
					
					} else {
						
						err_sb.append("证件类型名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					matInvCertType.setValidity_type(String.valueOf(temp[2]));
		    		mapVo.put("validity_type", temp[2]);
					
					} else {
						
						err_sb.append("效期类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					matInvCertType.setIs_alarm(Integer.valueOf(temp[3]));
		    		mapVo.put("is_alarm", temp[3]);
					
					} else {
						
						err_sb.append("是否预警为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					matInvCertType.setWar_days(	Integer.valueOf(temp[4]));
		    		mapVo.put("war_days", temp[4]);
					
					} else {
						
						err_sb.append("预警天数为空  ");
						
					}
					
					if(temp[3].equals("1") && temp[4].equals("0")){
						err_sb.append("预警天数不能小于0 ");
					}
					if(temp[3].equals("0") && !temp[4].equals("0")){
						err_sb.append("预警天数不能大于0 ");
					}
					 
				List<MatInvCertType> existList = matInvCertTypeService.queryMatInvCertTypeById(mapVo);
				
				if (existList.size() > 0) {
					for(MatInvCertType item : existList){
						if(item.getType_code().equals(mapVo.get("type_code"))){
							err_sb.append("证件类型编码重复 ");
						}
						if(item.getType_name().equals(mapVo.get("type_name"))){
							err_sb.append("证件类型名称重复 ");
						}
					}
				}
				if (err_sb.toString().length() > 0) {
					
					matInvCertType.setError_type(err_sb.toString());
					
					list_err.add(matInvCertType);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			  
					String dataJson = matInvCertTypeService.addMatInvCertType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatInvCertType data_exc = new MatInvCertType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 04601 材料证件类型字典
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/cert/invcerttype/addBatchMatInvCertType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMatInvCertType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MatInvCertType> list_err = new ArrayList<MatInvCertType>();
		
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
			
			MatInvCertType matInvCertType = new MatInvCertType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					matInvCertType.setType_code(String.valueOf(jsonObj.get("type_code")));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("证件类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
						
					matInvCertType.setType_name(String.valueOf(jsonObj.get("type_name")));
		    		mapVo.put("type_name", jsonObj.get("type_name"));
		    		} else {
						
						err_sb.append("证件类型名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("validity_type"))) {
						
					matInvCertType.setValidity_type(String.valueOf(jsonObj.get("validity_type")));
		    		mapVo.put("validity_type", jsonObj.get("validity_type"));
		    		} else {
						
						err_sb.append("效期类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_alarm"))) {
						
					matInvCertType.setIs_alarm(Integer.valueOf(String.valueOf(jsonObj.get("is_alarm"))));
		    		mapVo.put("is_alarm", jsonObj.get("is_alarm"));
		    		} else {
						
						err_sb.append("是否预警为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("war_days"))) {
						
					matInvCertType.setWar_days(Integer.valueOf(String.valueOf(jsonObj.get("war_days"))));
		    		mapVo.put("war_days", jsonObj.get("war_days"));
		    		} else {
						
						err_sb.append("预警天数为空  ");
						
					}
					
					
					List<MatInvCertType> existList = matInvCertTypeService.queryMatInvCertTypeById(mapVo);
					
					if (existList.size() > 0) {
						for(MatInvCertType item : existList){
							if(item.getType_code().equals(mapVo.get("type_code"))){
								err_sb.append("证件类型编码重复");
							}
							if(item.getType_name().equals(mapVo.get("type_name"))){
								err_sb.append("证件类型名称重复");
							}
						}
					}
				if (err_sb.toString().length() > 0) {
					
					matInvCertType.setError_type(err_sb.toString());
					
					list_err.add(matInvCertType);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			  
					String dataJson = matInvCertTypeService.addMatInvCertType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MatInvCertType data_exc = new MatInvCertType();
			
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

