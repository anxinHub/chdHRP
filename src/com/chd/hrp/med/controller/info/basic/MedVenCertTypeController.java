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
import com.chd.hrp.med.entity.MedVenCertType;
import com.chd.hrp.med.serviceImpl.info.basic.MedVenCertTypeServiceImpl;
/**
 * 
 * @Description:
 * 08604 供应商证件类型
 * @Table:
 * MED_VEN_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedVenCertTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedVenCertTypeController.class);
	
	//引入Service服务
	@Resource(name = "medVenCertTypeService")
	private final MedVenCertTypeServiceImpl medVenCertTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/medVenCertTypeMainPage", method = RequestMethod.GET)
	public String medVenCertTypeMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/vencerttype/medVenCertTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/medVenCertTypeAddPage", method = RequestMethod.GET)
	public String medVenCertTypeAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/vencerttype/medVenCertTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/addMedVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String medVenCertTypeJson = medVenCertTypeService.addMedVenCertType(mapVo);

		return JSONObject.parseObject(medVenCertTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 08604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/medVenCertTypeUpdatePage", method = RequestMethod.GET)
	public String medVenCertTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MedVenCertType medVenCertType = new MedVenCertType();
    
		medVenCertType = medVenCertTypeService.queryMedVenCertTypeByCode(mapVo);
		
		mode.addAttribute("group_id", medVenCertType.getGroup_id());
		mode.addAttribute("hos_id", medVenCertType.getHos_id());
		mode.addAttribute("copy_code", medVenCertType.getCopy_code());
		mode.addAttribute("type_id", medVenCertType.getType_id());
		mode.addAttribute("type_code", medVenCertType.getType_code());
		mode.addAttribute("type_name", medVenCertType.getType_name());
		mode.addAttribute("spell_code", medVenCertType.getSpell_code());
		mode.addAttribute("wbx_code", medVenCertType.getWbx_code());
		mode.addAttribute("is_alarm", medVenCertType.getIs_alarm());
		mode.addAttribute("war_days", medVenCertType.getWar_days());
		
		return "hrp/med/info/basic/cert/vencerttype/medVenCertTypeUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 08604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/updateMedVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

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
	  
		String medVenCertTypeJson = medVenCertTypeService.updateMedVenCertType(mapVo);
		
		return JSONObject.parseObject(medVenCertTypeJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 08604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/addOrUpdateMedVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medVenCertTypeJson ="";
		
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
	  
		medVenCertTypeJson = medVenCertTypeService.addOrUpdateMedVenCertType(detailVo);
		
		}
		return JSONObject.parseObject(medVenCertTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/deleteMedVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedVenCertType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
	    
		String medVenCertTypeJson = medVenCertTypeService.deleteBatchMedVenCertType(paramVo);
			
	  return JSONObject.parseObject(medVenCertTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 08604 供应商证件类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/queryMedVenCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedVenCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String medVenCertType = medVenCertTypeService.queryMedVenCertType(mapVo);

		return JSONObject.parseObject(medVenCertType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 08604 供应商证件类型
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/medVenCertTypeImportPage", method = RequestMethod.GET)
	public String medVenCertTypeImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/cert/vencerttype/medVenCertTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08604 供应商证件类型
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/cert/vencerttype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08604 供应商证件类型.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 08604 供应商证件类型
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/info/basic/cert/vencerttype/readMedVenCertTypeFiles",method = RequestMethod.POST)  
    public String readMedVenCertTypeFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedVenCertType> list_err = new ArrayList<MedVenCertType>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedVenCertType medVenCertType = new MedVenCertType();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
					 
					if (StringTool.isNotBlank(temp[0])) {
						
					medVenCertType.setType_code(String.valueOf(temp[0]));
		    		mapVo.put("type_code", temp[0]);
					
					} else {
						
						err_sb.append("证件类型编码为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[1])) {
						
					medVenCertType.setType_name(String.valueOf(temp[1]));
		    		mapVo.put("type_name", temp[1]);
					
					} else {
						
						err_sb.append("证件类型名称为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					medVenCertType.setIs_alarm(Integer.valueOf(temp[2]));
		    		mapVo.put("is_alarm", temp[2]);
					
					} else {
						
						err_sb.append("是否预警为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medVenCertType.setWar_days(Integer.valueOf(temp[3]));
		    		mapVo.put("war_days", temp[3]);
					
					} else {
						
						err_sb.append("预警天数为空  ");
						
					}
					 
					List<MedVenCertType> extisList = medVenCertTypeService.queryMedVenCertTypeByID(mapVo);

					if (extisList.size()>0) {
						for(MedVenCertType item: extisList){
							if(item.getType_code().equals(mapVo.get("type_code"))){
								err_sb.append("证件分类编码:"+mapVo.get("type_code")+"已存在");
							}
							if(item.getType_name().equals(mapVo.get("type_name"))){
								err_sb.append("证件分类名称:"+mapVo.get("type_name")+"已存在");
							}
						}
					}
				if (err_sb.toString().length() > 0) {
					
					medVenCertType.setError_type(err_sb.toString());
					
					list_err.add(medVenCertType);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			  
					String dataJson = medVenCertTypeService.addMedVenCertType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedVenCertType data_exc = new MedVenCertType();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 08604 供应商证件类型
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/info/basic/cert/vencerttype/addBatchMedVenCertType", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedVenCertType(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedVenCertType> list_err = new ArrayList<MedVenCertType>();
		
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
			
			MedVenCertType medVenCertType = new MedVenCertType();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					medVenCertType.setType_code(String.valueOf(jsonObj.get("type_code")));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("证件类型编码为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
						
					medVenCertType.setType_name(String.valueOf(jsonObj.get("type_name")));
		    		mapVo.put("type_name", jsonObj.get("type_name"));
		    		} else {
						
						err_sb.append("证件类型名称为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_alarm"))) {
						
					medVenCertType.setIs_alarm(Integer.valueOf(String.valueOf(jsonObj.get("is_alarm"))));
		    		mapVo.put("is_alarm", jsonObj.get("is_alarm"));
		    		} else {
						
						err_sb.append("是否预警为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("war_days"))) {
						
					medVenCertType.setWar_days(Integer.valueOf(String.valueOf(jsonObj.get("war_days"))));
		    		mapVo.put("war_days", jsonObj.get("war_days"));
		    		} else {
						
						err_sb.append("预警天数为空  ");
						
					}
					List<MedVenCertType> extisList = medVenCertTypeService.queryMedVenCertTypeByID(mapVo);
					if (extisList.size()>0) {
						for(MedVenCertType item: extisList){
							if(item.getType_code().equals(mapVo.get("type_code"))){
								err_sb.append("证件分类编码:"+mapVo.get("type_code")+"已存在");
							}
							if(item.getType_name().equals(mapVo.get("type_name"))){
								err_sb.append("证件分类名称:"+mapVo.get("type_name")+"已存在");
							}
						}
					}
				if (err_sb.toString().length() > 0) {
					
					medVenCertType.setError_type(err_sb.toString());
					
					list_err.add(medVenCertType);
					
				} else {
				  mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
				  mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			  
					String dataJson = medVenCertTypeService.addMedVenCertType(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedVenCertType data_exc = new MedVenCertType();
			
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

