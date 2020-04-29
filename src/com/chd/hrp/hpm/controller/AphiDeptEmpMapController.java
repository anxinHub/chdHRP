package com.chd.hrp.hpm.controller;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.entity.AphiDeptEmpMap;
import com.chd.hrp.hpm.service.AphiDeptEmpMapService;

/**
 * 资金项目关系维护
 * @author zhaon
 *
 */

@Controller
public class AphiDeptEmpMapController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptEmpMapController.class);

	@Resource(name = "aphiDeptEmpMapService")
	private AphiDeptEmpMapService aphiDeptEmpMapService = null;

	// 主页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/hpmDeptEmpMapMainPage", method = RequestMethod.GET)
	public String hpmDeptEmpMapMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptempmap/hpmDeptEmpMapMain";

	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/hpmDeptEmpMapImportPage", method = RequestMethod.GET)
	public String hpmDeptEmpMapImportPage(Model mode) throws Exception {
		return "hrp/hpm/hpmdeptempmap/hpmDeptEmpMapImport";
	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/queryHpmDeptEmpMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptEmpMap(@RequestParam Map<String, Object> mapVo, Model mode, Object ItemService) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String item = aphiDeptEmpMapService.queryDeptEmpMap(getPage(mapVo));

		return JSONObject.parseObject(item);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/addDeptEmpMapPage", method = RequestMethod.GET)
	public String addItemPage(Model mode) throws Exception {

		return "/hrp/hpm/hpmdeptempmap/hpmDeptEmpMapAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/saveHpmDeptEmpMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHpmDeptEmpMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String itemJson = aphiDeptEmpMapService.saveDeptEmpMap(mapVo);

		return JSONObject.parseObject(itemJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/updateHpmDeptEmpMapPage", method = RequestMethod.GET)
	public String updateHpmDeptEmpMapPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AphiDeptEmpMap item = aphiDeptEmpMapService.queryDeptEmpMapByCode(mapVo);

		mode.addAttribute("dept_item_code", item.getDept_item_code());

		mode.addAttribute("dept_item_name", item.getDept_item_name());
		
		mode.addAttribute("emp_item_code", item.getEmp_item_code());

		mode.addAttribute("emp_item_name", item.getEmp_item_name());


		return "/hrp/hpm/hpmdeptempmap/hpmDeptEmpMapUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/updateHpmDeptEmpMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmDeptEmpMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String itemJson = aphiDeptEmpMapService.updateDeptEmpMap(mapVo);

		return JSONObject.parseObject(itemJson);
	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/deleteHpmDeptEmpMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDeptEmpMap(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		Map<String,Object> map = new HashMap<String,Object>();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		String itemJson = aphiDeptEmpMapService.deleteDeptEmpMap(map, checkIds);

		return JSONObject.parseObject(itemJson);
	}
	
	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/queryHpmItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String,Object> map = new HashMap<String,Object>();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		String itemJson = aphiDeptEmpMapService.queryHpmItem(map);

		return JSONObject.parseObject(itemJson);
	}
	
	@RequestMapping(value = "/hrp/hpm/hpmdeptempmap/queryHpmEmpItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String,Object> map = new HashMap<String,Object>();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		String itemJson = aphiDeptEmpMapService.queryHpmEmpItem(map);

		return JSONObject.parseObject(itemJson);
	}

}
