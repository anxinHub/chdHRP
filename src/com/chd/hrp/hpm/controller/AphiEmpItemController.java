package com.chd.hrp.hpm.controller;

import java.util.HashMap;
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
import com.chd.hrp.hpm.entity.AphiEmpItem;
import com.chd.hrp.hpm.service.AphiEmpItemService;

@Controller
public class AphiEmpItemController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiEmpItemController.class);

	@Resource(name = "aphiEmpItemService")
	private AphiEmpItemService aphiEmpItemService = null;

	// 奖金项目页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempitem/hpmEmpItemMainPage", method = RequestMethod.GET)
	public String hpmEmpItemMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempitem/hpmEmpItemMain";

	}

	// 奖金项目查询
	@RequestMapping(value = "/hrp/hpm/hpmempitem/queryHpmEmpItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpItem(@RequestParam Map<String, Object> mapVo, Model mode, Object ItemService) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String item = aphiEmpItemService.queryEmpItem(getPage(mapVo));

		return JSONObject.parseObject(item);
	}

	@RequestMapping(value = "/hrp/hpm/hpmempitem/addEmpItemPage", method = RequestMethod.GET)
	public String addEmpItemPage(Model mode) throws Exception {

		return "/hrp/hpm/hpmempitem/hpmEmpItemAdd";

	}

	// 保存增加的奖金项目
	@RequestMapping(value = "/hrp/hpm/hpmempitem/saveHpmEmpItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHpmEmpItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String itemJson = aphiEmpItemService.addEmpItem(mapVo);

		return JSONObject.parseObject(itemJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempitem/updateHpmEmpItemPage", method = RequestMethod.GET)
	public String updateHpmEmpItemPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AphiEmpItem item = aphiEmpItemService.queryEmpItemByCode(mapVo);

		mode.addAttribute("item_code", item.getItem_code());

		mode.addAttribute("item_name", item.getItem_name());

		mode.addAttribute("item_note", item.getItem_note());

		mode.addAttribute("is_avg", item.getIs_avg());

		mode.addAttribute("is_stop", item.getIs_stop());

		return "/hrp/hpm/hpmempitem/hpmEmpItemUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmempitem/updateHpmEmpItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmEmpItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String itemJson = aphiEmpItemService.updateEmpItem(mapVo);

		return JSONObject.parseObject(itemJson);
	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmempitem/deleteHpmEmpItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmEmpItem(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

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

		String itemJson = aphiEmpItemService.deleteEmpItem(map, checkIds);

		return JSONObject.parseObject(itemJson);
	}
}
