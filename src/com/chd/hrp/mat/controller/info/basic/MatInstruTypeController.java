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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.mat.entity.MatInstruType;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.info.basic.MatInstruTypeService;

/**
 * @Description: 医疗器械分类字典
 * @Table: MAT_INSTRU_TYPE
 * @Author: weixiaofeng
 * @Version: 1.0
 */

@Controller
public class MatInstruTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(MatInstruTypeController.class);

	// 引入Service服务
	@Resource(name = "matInstruTypeService")
	private final MatInstruTypeService matInstruTypeService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/matInstruTypeMainPage", method = RequestMethod.GET)
	public String matInstruTypeMainPage(Model mode) throws Exception {
	
		return "hrp/mat/info/basic/instrutype/matInstruTypeMain";
	}
	
	/**
	 * @Description 编码规则
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/getRules", method = RequestMethod.POST)
	@ResponseBody
	public String getRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("proj_code", "MAT_INSTRU_TYPE");
	    mapVo.put("mod_code", "04");
	    String rules = matCommonService.getMatHosRules(mapVo);//获取编码规则2-2-2....
	    String[] ruless  = rules.split("-");
	    
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i = 0; i <= ruless.length; i++){
	    	
	    	if(ruless[i].equals("0")){
	    		break;
	    	}
	    	if(i > 0){
	    		sb.append("-");
	    	}
	    	sb.append(ruless[i]);
	    }
		return "{\"ruless\":\""+sb.toString()+"\"}";
	}
	
	/**
	 * @Description 树形结构
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/queryMatInstruTypeByTree", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInstruTypeByTree(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInstruTypeJson = matInstruTypeService.queryMatInstruTypeByTree(mapVo);

		return matInstruTypeJson;
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/queryMatInstruTypeById", method = RequestMethod.POST)
	@ResponseBody
	public MatInstruType queryMatInstruTypeById(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		return matInstruTypeService.queryMatInstruTypeById(mapVo);
	}

	/**
	 * @Description 添加数据医疗器械分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/addMatInstruType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInstruType(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}


		mapVo.put("spell_code",
				StringTool.toPinyinShouZiMu(mapVo.get("instru_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("instru_type_name").toString()));

		String matInstruTypeJson;
		try {
			matInstruTypeJson = matInstruTypeService.addMatInstruType(mapVo);
		} catch (Exception e) {
			matInstruTypeJson = e.getMessage();
		}

		return JSONObject.parseObject(matInstruTypeJson);
	}

	/**
	 * @Description 更新数据医疗器械分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/saveMatInstruType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatInstruType(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("change_user", SessionManager.getUserId());
		mapVo.put("change_date",new Date());
		mapVo.put("change_note", "变更");
		if (mapVo.get("instru_type_id") == null) {
			mapVo.put("instru_type_id", "");
		}
	
		String MatInstruTypeJson;
		try {
			MatInstruTypeJson = matInstruTypeService.saveMatInstruType(mapVo);
		} catch (Exception e) {
			MatInstruTypeJson = e.getMessage();
		}
	
		return JSONObject.parseObject(MatInstruTypeJson);
	}

	/**
	 * @Description 删除数据医疗器械分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/deleteMatInstruType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInstruType(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matInstruTypeJson;
		try {
			matInstruTypeJson = matInstruTypeService.deleteMatInstruType(mapVo);
		} catch (Exception e) {
			matInstruTypeJson = e.getMessage();
		}

		return JSONObject.parseObject(matInstruTypeJson);

	}

	/**
	 * @Description 查询数据医疗器械分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/queryMatInstruType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInstruType(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInstruTypeJson = matInstruTypeService.queryMatInstruType(mapVo);
		
		return JSONObject.parseObject(matInstruTypeJson);
	}
	
	/**
	 * @Description 跳转页面医疗器械分类变更
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/matInstruTypeDictPage", method = RequestMethod.GET)
	public String matInstruTypeDictPage(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mode.addAttribute("matInstruType", matInstruTypeService.queryMatInstruTypeById(mapVo));

		return "hrp/mat/info/basic/instrutype/matInstruTypeDict";
	}

	/**
	 * @Description 下载导入模版医疗器械分类字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/info/basic/instrutype/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate","04102物资分类字典.xls");

		return null;
	}

	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/instrutype/matInstruTypeImportPage", method = RequestMethod.GET)
	public String matInstruTypeImportNewPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/instrutype/matInstruTypeImport";
	}
	
	/**
	 * @Description 
	 * 导入数据 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/hrp/mat/info/basic/instrutype/import",method = RequestMethod.POST)  
	@ResponseBody
	public Map<String, Object> importData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		try {
			retMap = matInstruTypeService.importData(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
}
