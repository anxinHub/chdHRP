package com.chd.hrp.mat.controller.info.filetype;

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
import com.chd.hrp.mat.controller.info.basic.MatStoreController;
import com.chd.hrp.mat.entity.MatFileType;
import com.chd.hrp.mat.serviceImpl.info.filetype.MatFileTypeServiceImpl;

/**
 * 
 * @Description: 04103 文档信息
 * @Table: MAT_FILE_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatFileTypeController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatFileTypeController.class);
	
	@Resource(name = "matFileTypeService")
	private final MatFileTypeServiceImpl matFileTypeService = null ;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/filetype/matFileTypeMainPage", method = RequestMethod.GET)
	public String matFileTypeMainPage(Model mode) throws Exception {

		return "hrp/mat/info/filetype/matFileTypeMain";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//添加页面
	@RequestMapping(value = "/hrp/mat/info/filetype/addMatFileTypePage", method = RequestMethod.GET)
	public String addMatFileTypePage(Model mode) throws Exception {
		
		

		return "hrp/mat/info/filetype/matFileTypeAdd";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//修改页面
	@RequestMapping(value = "/hrp/mat/info/filetype/updateMatFileTypePage", method = RequestMethod.GET)
	public String updateMatFileTypePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		MatFileType matFileType = matFileTypeService.queryByCode(mapVo);
		
		mode.addAttribute("type_code", matFileType.getType_code());
		
		mode.addAttribute("type_name", matFileType.getType_name());
		
		mode.addAttribute("is_stop",matFileType.getIs_stop());
		
		mode.addAttribute("note", matFileType.getNote());
		
		return "hrp/mat/info/filetype/matFileTypeUpdate";

	}
	
	/**
	 * @Description 
	 * 文档信息 查询
	 * @param  mode
	 * @return Map<String,Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/filetype/queryMatFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matFileTypeJson = matFileTypeService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matFileTypeJson);
		
	}
	
	/**
	 * @Description 
	 * 文档信息 添加
	 * @param  mode
	 * @return Map<String,Object>
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/mat/info/filetype/addMatFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String fileTypeJson = matFileTypeService.add(mapVo);
		
		return JSONObject.parseObject(fileTypeJson);
		
	}
	
	/**
	 * @Description 
	 * 文档信息 修改
	 * @param  mode
	 * @return Map<String,Object>
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/mat/info/filetype/updateMatFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matFileTypeJson = matFileTypeService.update(mapVo);
		
		return JSONObject.parseObject(matFileTypeJson);
		
	}
	
	
	/**
	 * @Description 
	 * 文档信息 删除
	 * @param  mode
	 * @return Map<String,Object>
	 * @throws Exception
	*/
	
	@RequestMapping(value = "/hrp/mat/info/filetype/deleteMatFileType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatFileType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
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
	    
		String matFileTypeJson = matFileTypeService.deleteBatch(listVo);
		
		return JSONObject.parseObject(matFileTypeJson);
		
	}
	
}
