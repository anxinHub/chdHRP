/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.sys.controller;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.sys.service.base.SysTableStyleService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class SysTableStyleController extends BaseController {
	private static Logger logger = Logger
			.getLogger(SysTableStyleController.class);

	@Resource(name = "sysTableStyleService")
	private final SysTableStyleService sysTableStyleService = null;

	@RequestMapping(value = "hrp/sys/querySysTableStyle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysTableStyle(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("user_id", SessionManager.getUserId());
		String sysTableStyle = sysTableStyleService
				.querySysTableStyle(getPage(mapVo));

		return JSONObject.parseObject(sysTableStyle);

	}

	// 保存
	@RequestMapping(value = "hrp/sys/addSysTableStyle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSysTableStyle(
			@RequestParam Map<String, Object> mapVo, Model mode)
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
		mapVo.put("user_id", SessionManager.getUserId());
		String sysTableStyleJson = sysTableStyleService.addSysTableStyle(mapVo);

		return JSONObject.parseObject(sysTableStyleJson);

	}

	// 批量保存
	@RequestMapping(value = "hrp/sys/addBatchSysTableStyle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchSysTableStyle(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		JSONObject obj = JSONObject.parseObject(mapVo.get("param").toString());
		
		String path_l = obj.get("path_l").toString();

		JSONArray json = JSONArray.parseArray(obj.get("colList").toString());

		Iterator iterator = json.iterator();

		while (iterator.hasNext()) {
			Map<String, Object> map = new HashMap<String, Object>();
			JSONObject jsonObj = JSONObject.parseObject(iterator.next()
					.toString());
			map.put("col", jsonObj.get("col").toString());
			map.put("is_show", jsonObj.get("is_show").toString());
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("user_id", SessionManager.getUserId());
			map.put("path_l", path_l);
			listVo.add(map);
		}
		String sysTableStyleJson = "";
		try {
			//sysTableStyleService.deleteBatchSysTableStyle(listVo);
			sysTableStyleJson = sysTableStyleService
					.addBatchSysTableStyle(listVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage()
					+ " \"}");

		}
		return JSONObject.parseObject(sysTableStyleJson);
	}
	
	//保存列表打印格式
	@RequestMapping(value = "hrp/sys/saveStyleByUrl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveStyleByUrl(@RequestBody Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("user_id", SessionManager.getUserId());
		if(mapVo.get("page_url")==null ||mapVo.get("page_url").equals("")){
			return JSONObject.parseObject("{\"error\":\"页面路径为空！\"}");
		}
		
		try {
			String resJson = sysTableStyleService.saveStyleByUrl(mapVo);
			return JSONObject.parseObject(resJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage()+" \"}");

		}
	}
	
	//查询用户打印格式
	@RequestMapping(value = "hrp/sys/queryPrintByPageUrl", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryPrintByPageUrl(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("user_id", SessionManager.getUserId());
		try{
			String resultJson=sysTableStyleService.queryPrintByPageUrl(mapVo);
			return resultJson;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "";
		}
		
	}
	
	//删除列表格式
	@RequestMapping(value = "hrp/sys/deleteStyleByUrl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStyleByUrl(@RequestBody Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("user_id", SessionManager.getUserId());
		if(mapVo.get("page_url")==null ||mapVo.get("page_url").equals("")){
			return JSONObject.parseObject("{\"error\":\"页面路径为空！\"}");
		}
		
		try {
			String resJson = sysTableStyleService.deleteStyleByUrl(mapVo);
			return JSONObject.parseObject(resJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage()+" \"}");

		}
	}
}
