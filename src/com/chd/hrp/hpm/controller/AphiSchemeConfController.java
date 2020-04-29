package com.chd.hrp.hpm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiSchemeConfService;

@Controller
public class AphiSchemeConfController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiSchemeConfController.class);

	@Resource(name = "aphiSchemeConfService")
	private AphiSchemeConfService aphiSchemeConfService = null;

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/hpmSchemeConfMainPage", method = RequestMethod.GET)
	public String hpmSchemeApplyMain(Model model) throws Exception {

		return "hrp/hpm/hpmschemeconf/hpmSchemeConfMain";

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/queryHpmSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmSchemeConf(@RequestParam Map<String, Object> map, Model model) {

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}
		map.put("user_id", SessionManager.getUserId());
		String schemeApplyJson = aphiSchemeConfService.queryHpmSchemeConf(getPage(map));

		return JSONObject.parseObject(schemeApplyJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/addHpmSchemeConfPage", method = RequestMethod.GET)
	public String addHpmSchemeConfPage(Model model) throws Exception {

		return "hrp/hpm/hpmschemeconf/hpmSchemeConfAdd";

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/createHpmSchemeConfPage", method = RequestMethod.GET)
	public String createHpmSchemeConfPage(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		model.addAttribute("year_month", mapVo.get("year_month"));

		model.addAttribute("scheme_seq_no", mapVo.get("scheme_seq_no"));

		return "hrp/hpm/hpmschemeconf/hpmSchemeConfCreate";

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/createHpmSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmSchemeConf(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

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
		String result = aphiSchemeConfService.createHpmSchemeConf(mapVo);

		return JSONObject.parseObject(result);

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/addHpmSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmSchemeConf(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

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
		String result = aphiSchemeConfService.addHpmSchemeConf(mapVo);

		return JSONObject.parseObject(result);

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/updateHpmSchemeConfPage", method = RequestMethod.GET)
	public String updateHpmSchemeConfPage(@RequestParam Map<String, Object> mapVo, Model model) {

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
		AphiSchemeConf schemeConf = aphiSchemeConfService.queryHpmSchemeConfByCode(mapVo);

		model.addAttribute("acct_month", schemeConf.getAcct_month());

		model.addAttribute("acct_year", schemeConf.getAcct_year());

		model.addAttribute("scheme_seq_no", schemeConf.getScheme_seq_no());

		return "hrp/hpm/hpmschemeconf/hpmSchemeConfUpdate";

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/updateHpmSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmSchemeConf(@RequestParam Map<String, Object> mapVo, Model Model) throws Exception {

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
		String JsonResult = aphiSchemeConfService.updateHpmSchemeConf(mapVo);

		return JSONObject.parseObject(JsonResult);

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/deleteHpmSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmSchemeConf(@RequestParam(value = "checkIds", required = true) String checkIds, Model model) throws Exception {

		Map mapVo = new HashMap();

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

		String JsonResult = aphiSchemeConfService.deleteHpmSchemeConf(mapVo, checkIds);

		return JSONObject.parseObject(JsonResult);

	}
	
	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/hpmSchemeConfForDeptMainPage", method = RequestMethod.GET)
	public String hpmSchemeQueryMain(Model model) throws Exception {

		return "hrp/hpm/hpmschemeconf/hpmSchemeConfForDeptMain";

	}
	
	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/queryHpmSchemeConfForDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmSchemeConfForDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		if (StringUtils.isNotEmpty((String) mapVo.get("year_month"))) {

			String year_month = (String) mapVo.get("year_month");

			mapVo.put("acct_year", year_month.substring(0, 4));

			mapVo.put("acct_month", year_month.substring(4, 6));

		}

		String dept = aphiSchemeConfService.queryHpmSchemeConfForDept(getPage(mapVo));

		return JSONObject.parseObject(dept);

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeconf/queryHpmSchemeConfGridForDept", method = RequestMethod.GET)
	@ResponseBody
	public String queryHpmSchemeConfGridForDept(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

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
		String resultJson = aphiSchemeConfService.queryHpmSchemeConfGridForDept(mapVo);

		return resultJson;
	}

}
