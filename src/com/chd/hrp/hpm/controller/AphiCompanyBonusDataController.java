package com.chd.hrp.hpm.controller;

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
import com.chd.hrp.hpm.entity.AphiCompanyBonusData;
import com.chd.hrp.hpm.service.AphiCompanyBonusDataService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiCompanyBonusDataController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiCompanyBonusDataController.class);

	@Resource(name = "aphiCompanyBonusDataService")
	private final AphiCompanyBonusDataService aphiCompanyBonusDataService = null;

	// @Resource(name = "deptBonusDataService1_2")
	// private final AphiDeptBonusDataServiceImpl1_2 deptBonusDataService1_2 =
	// null;

	@RequestMapping(value = "/hrp/hpm/hpmcompanybonusdata/hpmCompanyBonusDataMainPage", method = RequestMethod.GET)
	public String companyBonusDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcompanybonusdata/companyBonusDataMain";

	}

	@RequestMapping(value = "/hrp/hpm/hpmcompanybonusdata/queryCompanyBonusDataMainPage", method = RequestMethod.GET)
	public String queryCompanyBonusDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcompanybonusdata/queryCompanyBonusDataMain";

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmcompanybonusdata/queryCompanyBonusData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCompanyBonusData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		String ACCT_YEAR = SessionManager.getAcctYear();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (mapVo.containsKey("acct_year_month")) {

			if (mapVo.get("acct_year_month") != null && !"".equals(mapVo.get("acct_year_month"))) {

				mapVo.put("acct_year", mapVo.get("acct_year_month").toString().substring(0, 4));

				mapVo.put("acct_month", mapVo.get("acct_year_month").toString().substring(4, mapVo.get("acct_year_month").toString().length()));
			}

		}

		String companyBonusData = aphiCompanyBonusDataService.queryCompanyBonusData(getPage(mapVo));

		return JSONObject.parseObject(companyBonusData);

	}

	@RequestMapping(value = "/hrp/hpm/hpmcompanybonusdata/initCompanyBonusData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initCompanyBonusData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		String ACCT_YEAR = SessionManager.getAcctYear();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String companyBonusData = aphiCompanyBonusDataService.initCompanyBonusData(mapVo);

		return JSONObject.parseObject(companyBonusData);

	}

	@RequestMapping(value = "/hrp/hpm/hpmcompanybonusdata/companyBonusDataUpdatePage", method = RequestMethod.GET)
	public String companyBonusDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		AphiCompanyBonusData companyBonusData = aphiCompanyBonusDataService.queryCompanyBonusDataByCode(mapVo);

		model.addAttribute("acct_month", companyBonusData.getAcct_month());

		model.addAttribute("acct_year", companyBonusData.getAcct_year());

		model.addAttribute("item_code", companyBonusData.getItem_code());

		model.addAttribute("item_name", companyBonusData.getItem_name());

		model.addAttribute("formula_code", companyBonusData.getFormula_code());

		model.addAttribute("formula_method_chs", companyBonusData.getFormula_method_chs());

		model.addAttribute("bonus_money", companyBonusData.getBonus_money());

		model.addAttribute("nature_code", companyBonusData.getNature_code());

		return "hrp/hpm/hpmcompanybonusdata/companyBonusDataUpdate";

	}

	@RequestMapping(value = "/hrp/hpm/hpmcompanybonusdata/queryTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTarget(@RequestParam(value = "formula_code", required = true) String formula_code, @RequestParam Map<String, Object> mapVo,
			Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		model.addAttribute("formula_code", formula_code);

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		// String resultJson = deptBonusDataService1_2.queryTargetByCode(mapVo);

		return JSONObject.parseObject("");

	}
}
