/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
import java.io.IOException;
import java.text.ParseException;
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
import com.chd.hrp.med.entity.MedStockType;
import com.chd.hrp.med.serviceImpl.info.basic.MedStockTypeServiceImpl;
/**
 * 
 * @Description:
 * 08118 采购类型
 * @Table:
 * MED_STOCK_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedStockTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedStockTypeController.class);
	
	//引入Service服务
	@Resource(name = "medStockTypeService")
	private final MedStockTypeServiceImpl medStockTypeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medstocktype/medStockTypeMainPage", method = RequestMethod.GET)
	public String medStockTypeMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/medstocktype/medStockTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medstocktype/medStockTypeAddPage", method = RequestMethod.GET)
	public String medStockTypeAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/medstocktype/medStockTypeAdd";

	}

	/**
	 * @Description 
	 * 添加数据 08118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medstocktype/addMedStockType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String medStockTypeJson = medStockTypeService.add(mapVo);

		return JSONObject.parseObject(medStockTypeJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 08118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medstocktype/medStockTypeUpdatePage", method = RequestMethod.GET)
	public String medStockTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	  if(mapVo.get("group_id") == null){	
		  mapVo.put("group_id", SessionManager.getGroupId());	
	  }
		
	  if(mapVo.get("hos_id") == null){	
		mapVo.put("hos_id", SessionManager.getHosId());	
	   }
		
	if(mapVo.get("copy_code") == null){	
		mapVo.put("copy_code", SessionManager.getCopyCode());      
	}
		
    MedStockType medStockType = new MedStockType();
    
	medStockType = medStockTypeService.queryByCode(mapVo);
	
	mode.addAttribute("group_id", medStockType.getGroup_id());
	mode.addAttribute("hos_id", medStockType.getHos_id());
	mode.addAttribute("copy_code", medStockType.getCopy_code());
	mode.addAttribute("stock_type_code", medStockType.getStock_type_code());
	mode.addAttribute("stock_type_name", medStockType.getStock_type_name());
	mode.addAttribute("wbx_code", medStockType.getWbx_code());
	mode.addAttribute("spell_code", medStockType.getSpell_code());
	mode.addAttribute("is_stop", medStockType.getIs_stop());
	mode.addAttribute("note", medStockType.getNote());
		
	return "hrp/med/info/basic/medstocktype/medStockTypeUpdate";
}
		
	/**
	 * @Description 
	 * 更新数据 08118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medstocktype/updateMedStockType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String medStockTypeJson = medStockTypeService.update(mapVo);
		
		return JSONObject.parseObject(medStockTypeJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 08118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medstocktype/deleteMedStockType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedStockType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
			String medStockTypeJson = medStockTypeService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(medStockTypeJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 08118 采购类型
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medstocktype/queryMedStockType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){			
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){			
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());       
		}
		String medStockType = medStockTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(medStockType);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 08118 采购类型
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/medstocktype/medStockTypeImportPage", method = RequestMethod.GET)
	public String medStockTypeImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/medstocktype/medStockTypeImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 08118 采购类型
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/info/basic/medstocktype/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","08118 采购类型.xls");
	    
	    return null; 
	 }
	
	 
	 /**
	 * @Description 
	 * 导入
	 * @param  mapVo
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value="hrp/med/info/basic/medstocktype/importMedStockType",method = RequestMethod.POST)  
	@ResponseBody
	public Map<String, Object> importMedStockType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			if(mapVo.get("group_id") == null){			
		    	mapVo.put("group_id", SessionManager.getGroupId());		
			}
			
			if(mapVo.get("hos_id") == null){			
				mapVo.put("hos_id", SessionManager.getHosId());		
			}
			
			if(mapVo.get("copy_code") == null){			
				mapVo.put("copy_code", SessionManager.getCopyCode());       
			}
			
			String reJson=medStockTypeService.importMedStockType(mapVo);
			
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	 
	 
}

