package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiAppMod;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.AphiAppModService;
import com.chd.hrp.hpm.service.AphiItemService;

@Controller
public class AphiItemController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiItemController.class);

	@Resource(name = "aphiItemService")
	private AphiItemService aphiItemService = null;

	@Resource(name = "aphiAppModService")
	private AphiAppModService aphiAppModService = null;

	// 奖金项目页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmitem/hpmItemMainPage", method = RequestMethod.GET)
	public String hpmItemMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmitem/hpmItemMain";

	}

	@RequestMapping(value = "/hrp/hpm/hpmitem/hpmItemImportPage", method = RequestMethod.GET)
	public String hpmItemImportPage(Model mode) throws Exception {
		return "hrp/hpm/hpmitem/hpmItemImport";
	}

	// 奖金项目查询
	@RequestMapping(value = "/hrp/hpm/hpmitem/queryHpmItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmItem(@RequestParam Map<String, Object> mapVo, Model mode, Object ItemService) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String item = aphiItemService.queryItem(getPage(mapVo));

		return JSONObject.parseObject(item);
	}

	@RequestMapping(value = "/hrp/hpm/hpmitem/addItemPage", method = RequestMethod.GET)
	public String addItemPage(Model mode) throws Exception {

		return "/hrp/hpm/hpmitem/hpmItemAdd";

	}

	// 保存增加的奖金项目
	@RequestMapping(value = "/hrp/hpm/hpmitem/saveHpmItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHpmItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		// for (Map.Entry<String, Object> entry : mapVo.entrySet()) {
		//
		// System.out.println("key= " + entry.getKey() + " and value= " +
		// entry.getValue());
		//
		// }

		String itemJson = aphiItemService.addItem(mapVo);

		return JSONObject.parseObject(itemJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmitem/updateHpmItemPage", method = RequestMethod.GET)
	public String updateHpmItemPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AphiItem item = aphiItemService.queryItemByCode(mapVo);

		mode.addAttribute("item_code", item.getItem_code());

		mode.addAttribute("item_name", item.getItem_name());

		mode.addAttribute("item_note", item.getItem_note());

		mode.addAttribute("app_mod_code", item.getApp_mod_code());

		mode.addAttribute("app_mod_name", item.getApp_mod_name());

		mode.addAttribute("is_avg", item.getIs_avg());

		mode.addAttribute("is_stop", item.getIs_stop());
		
		mode.addAttribute("is_sum", item.getIs_sum());
		
		mode.addAttribute("is_two_audit", item.getIs_two_audit());

		return "/hrp/hpm/hpmitem/hpmItemUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmitem/updateHpmItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String itemJson = aphiItemService.updateItem(mapVo);

		return JSONObject.parseObject(itemJson);
	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmitem/deleteHpmItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmItem(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		Map map = new HashMap();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		String itemJson = aphiItemService.deleteItem(map, checkIds);

		return JSONObject.parseObject(itemJson);
	}

}
