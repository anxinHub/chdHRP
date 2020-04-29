package com.chd.hrp.med.controller.info.filetype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.med.controller.info.basic.MedStoreController;
import com.chd.hrp.med.entity.MedFileType;
import com.chd.hrp.med.serviceImpl.info.filetype.MedFileTypeServiceImpl;

/**
 * 
 * @Description: 08103 文档信息
 * @Table: MED_FILE_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedFileTypeController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedFileTypeController.class);
	
	@Resource(name = "medFileTypeService")
	private final MedFileTypeServiceImpl medFileTypeService = null ;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/filetype/medFileTypeMainPage", method = RequestMethod.GET)
	public String medFileTypeMainPage(Model mode) throws Exception {

		return "hrp/med/info/filetype/medFileTypeMain";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//添加页面
	@RequestMapping(value = "/hrp/med/info/filetype/addMedFileTypePage", method = RequestMethod.GET)
	public String addMedFileTypePage(Model mode) throws Exception {
		
		

		return "hrp/med/info/filetype/medFileTypeAdd";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//修改页面
	@RequestMapping(value = "/hrp/med/info/filetype/updateMedFileTypePage", method = RequestMethod.GET)
	public String updateMedFileTypePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		MedFileType medFileType = medFileTypeService.queryByCode(mapVo);
		
		mode.addAttribute("type_code", medFileType.getType_code());
		
		mode.addAttribute("type_name", medFileType.getType_name());
		
		mode.addAttribute("is_stop",medFileType.getIs_stop());
		
		mode.addAttribute("note", medFileType.getNote());
		
		return "hrp/med/info/filetype/medFileTypeUpdate";

	}
	
	/**
	 * @Description 
	 * 文档信息 查询
	 * @param  mode
	 * @return Map<String,Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/filetype/queryMedFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medFileTypeJson = medFileTypeService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medFileTypeJson);
		
	}
	
	/**
	 * @Description 
	 * 文档信息 添加
	 * @param  mode
	 * @return Map<String,Object>
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/med/info/filetype/addMedFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String fileTypeJson = medFileTypeService.add(mapVo);
		
		return JSONObject.parseObject(fileTypeJson);
		
	}
	
	/**
	 * @Description 
	 * 文档信息 修改
	 * @param  mode
	 * @return Map<String,Object>
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/med/info/filetype/updateMedFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matFileTypeJson = medFileTypeService.update(mapVo);
		
		return JSONObject.parseObject(matFileTypeJson);
		
	}
	
	
	/**
	 * @Description 
	 * 文档信息 删除
	 * @param  mode
	 * @return Map<String,Object>
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/med/info/filetype/deleteMedFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String paramVo = mapVo.get("paramVo").toString();
		
			for ( String type_code: paramVo.split(",")) {
				
				Map<String, Object> map=new HashMap<String, Object>();
				
				if(mapVo.get("group_id") == null){
					
					map.put("group_id", SessionManager.getGroupId());
					
				}
					
				if(mapVo.get("hos_id") == null){
					
					map.put("hos_id", SessionManager.getHosId());
				}
					
				if(mapVo.get("copy_code") == null){
			    	
					map.put("copy_code", SessionManager.getCopyCode());
				}
				
				map.put("type_code", type_code)   ;
				
				listVo.add(map);
	      
			}
	    
		String medFileTypeJson = medFileTypeService.deleteBatch(listVo);
		
		return JSONObject.parseObject(medFileTypeJson);
		
	}
	
}
