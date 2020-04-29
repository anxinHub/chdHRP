package com.chd.hrp.hpm.controller.report;

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
import com.chd.hrp.hpm.service.report.AphiDeptBonusCalculationService;

@Controller
public class AphiDeptBonusCalculationController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptBonusCalculationController.class);

	@Resource(name = "aphiDeptBonusCalculationService")
	private AphiDeptBonusCalculationService aphiDeptBonusCalculationService = null;

	@RequestMapping(value = "/hrp/hpm/report/hpmDeptBonusCalculationMainPage", method = RequestMethod.GET)
	public String queryDeptBonusCalculationMainPage(Model model) throws Exception {
		return "hrp/hpm/report/hpmDeptBonusCalculationMain";
	}

	@RequestMapping(value = "/hrp/hpm/report/queryHpmDeptBonusCalculationByRepot", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptBonusCalculationByRepot(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		mapVo.put("acct_year_start", mapVo.get("year_month_start").toString().substring(0, 4));

		mapVo.put("acct_month_start", mapVo.get("year_month_start").toString().substring(4, 6));

		mapVo.put("acct_year_end", mapVo.get("year_month_end").toString().substring(0, 4));

		mapVo.put("acct_month_end", mapVo.get("year_month_end").toString().substring(4, 6));

		String deptBoundsCalculation = aphiDeptBonusCalculationService.queryDeptBonusCalculation(getPage(mapVo));

		return JSONObject.parseObject(deptBoundsCalculation);
	}

	@RequestMapping(value = "/hrp/hpm/report/queryHpmDeptBonusCalculationGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptBonusCalculationGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String resultJson = aphiDeptBonusCalculationService.queryDeptBonusCalculationGrid(mapVo);

		return resultJson;
	}

}
