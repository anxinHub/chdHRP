package com.chd.hrp.hpm.controller.report;

import java.util.Map;

import javax.annotation.Resource;
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
import com.chd.hrp.hpm.service.report.AphiHosPerfWageRatioReportService;

@Controller
public class AphiHosPerfWageRatioReportController extends BaseController {

	private static final Logger logger = Logger.getLogger(AphiHosPerfWageRatioReportController.class);

	@Resource(name = "aphiHosPerfWageRatioReportService")
	private final AphiHosPerfWageRatioReportService aphiHosPerfWageRatioReportService = null;

	// 主页面跳转
	@RequestMapping(value = "/hrp/hpm/report/hpmHosPerfWageRatioReportMainPage", method = RequestMethod.GET)
	public String hpmHosPerfWageRatioReportMainPage(Model model) throws Exception {
		return "hrp/hpm/report/hpmHosPerfWageRatioReportMain";
	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/report/queryHpmHosPerfWageRatioReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmHosPerfWageRatioReport(@RequestParam Map<String, Object> mapVo, Model model, HttpServletResponse response)
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

		String hosPerfWageRatioTargetJson = aphiHosPerfWageRatioReportService.queryHpmHosPerfWageRatioReport(getPage(mapVo));

		return JSONObject.parseObject(hosPerfWageRatioTargetJson);
	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/report/initHpmHosPerfWageRatioReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmHosPerfWageRatioReport(@RequestParam Map<String, Object> mapVo, Model model, HttpServletResponse response)
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

		String hosPerfWageRatioTargetJson = aphiHosPerfWageRatioReportService.initHpmHosPerfWageRatioReport(mapVo);

		return JSONObject.parseObject(hosPerfWageRatioTargetJson);
	}
	
	//shenhe
	@RequestMapping(value = "/hrp/hpm/report/updateshenhe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateshenhe(@RequestParam Map<String, Object> mapVo) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String aphiHosPerfWageRatioReportJson;
		try {
			aphiHosPerfWageRatioReportJson = aphiHosPerfWageRatioReportService.shenhe(mapVo);
		} catch (Exception e) {
			aphiHosPerfWageRatioReportJson = e.getMessage();
		}
		
		return JSONObject.parseObject(aphiHosPerfWageRatioReportJson);
	}
	// 修改
	@RequestMapping(value = "/hrp/hpm/report/updateHpmHosPerfWageRatioReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmHosPerfWageRatioReport(@RequestParam Map<String, Object> mapVo, Model model, HttpServletResponse response)
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

		String hosPerfWageRatioTargetJson = aphiHosPerfWageRatioReportService.updateHpmHosPerfWageRatioReport(mapVo);

		return JSONObject.parseObject(hosPerfWageRatioTargetJson);
	}
}
