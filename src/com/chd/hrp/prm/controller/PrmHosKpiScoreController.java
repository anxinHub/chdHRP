
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.controller;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmFormulaStackService;
import com.chd.hrp.prm.service.PrmHosKpiAdService;
import com.chd.hrp.prm.service.PrmHosKpiScoreService;
import com.chd.hrp.prm.service.PrmHosKpiSectionService;
import com.chd.hrp.prm.service.PrmHosSchemeService;

/**
 * 
 * @Description:
 * 0209 医院KPI指标考评计算表
 * @Table:
 * PRM_HOS_KPI_SCORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmHosKpiScoreController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmHosKpiScoreController.class);

	// 引入Service服务
	@Resource(name = "prmHosKpiScoreService")
	private final PrmHosKpiScoreService prmHosKpiScoreService = null;

	// 引入Service服务
	@Resource(name = "prmHosKpiSectionService")
	private final PrmHosKpiSectionService prmHosKpiSectionService = null;

	// 引入Service服务
	@Resource(name = "prmHosKpiAdService")
	private final PrmHosKpiAdService prmHosKpiAdService = null;

	@Resource(name = "prmHosSchemeService")
	private final PrmHosSchemeService prmHosSchemeService = null;

	@Resource(name = "prmFormulaStackService")
	private final PrmFormulaStackService prmFormulaStackService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/prmHosKpiScoreMainPage", method = RequestMethod.GET)
	public String prmHosKpiScoreMainPage(Model mode) throws Exception {

		return "hrp/prm/prmhoskpiscore/prmHosKpiScoreMain";
	}

	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/prmHosKpiScoreSchemeMainPage", method = RequestMethod.GET)
	public String prmHosKpiScoreSchemeMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("check_hos_id", mapVo.get("check_hos_id"));
		return "hrp/prm/prmhoskpiscore/prmHosKpiScoreSchemeMain";
	}

	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/prmHosKpiScoreAdMain", method = RequestMethod.GET)
	public String prmHosKpiScoreAdMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		PrmHosScheme prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("hos_code", prmHosScheme.getHos_code());

		mode.addAttribute("hos_name", prmHosScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name", mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("check_hos_id", prmHosScheme.getHos_id());

		mode.addAttribute("check_hos_code", prmHosScheme.getHos_code());

		mode.addAttribute("check_hos_name", prmHosScheme.getHos_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmhoskpiscore/prmHosKpiScoreAdMain";

	}

	@RequestMapping(value = "/hrp/prm/prmHoskpiscore/prmHosKpiScoreSectionMain", method = RequestMethod.GET)
	public String prmHosKpiScoreSectionMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		PrmHosScheme prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("hos_code", prmHosScheme.getHos_code());

		mode.addAttribute("hos_name", prmHosScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", prmHosScheme.getAcc_year());

		mode.addAttribute("acc_month", prmHosScheme.getAcc_month());

		mode.addAttribute("goal_code", prmHosScheme.getGoal_code());

		mode.addAttribute("nature_name", prmHosScheme.getNature_name());

		mode.addAttribute("kpi_code", prmHosScheme.getKpi_code());

		mode.addAttribute("kpi_name", prmHosScheme.getKpi_name());

		mode.addAttribute("check_hos_id", prmHosScheme.getHos_id());

		mode.addAttribute("check_hos_code", prmHosScheme.getHos_code());

		mode.addAttribute("check_hos_name", prmHosScheme.getHos_name());

		mode.addAttribute("ratio", prmHosScheme.getRatio());

		mode.addAttribute("goal_value", prmHosScheme.getGoal_value());

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmhoskpiscore/prmHosKpiScoreSectionMain";

	}
	
	
	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/prmHosKpiScoreFormulaMain", method = RequestMethod.GET)
	public String prmHosKpiScoreFormulaMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("kpi_value", mapVo.get("kpi_value"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("check_hos_id", mapVo.get("check_hos_id"));

		mode.addAttribute("formula_method_chs", mapVo.get("formula_method_chs"));

		mode.addAttribute("formula_code", mapVo.get("formula_code"));

		return "hrp/prm/prmhoskpiscore/prmHosKpiScoreFormulaMain";

	}
	
	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/queryPrmHosScoreFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosScoreFormula(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String HosKpiSection = prmFormulaStackService.queryPrmHosFormulaStackByScore(getPage(mapVo));

		return JSONObject.parseObject(HosKpiSection);
	}
	
	

	/**
	 * @Description 查询数据 评分方法区间法(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/queryPrmHosSectionScoreByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosSectionScoreByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String HosKpiSection = prmHosKpiSectionService.queryPrmHosKpiSection(getPage(mapVo));

		return JSONObject.parseObject(HosKpiSection);
	}

	/**
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/queryPrmHosKpiScoreAdByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiScoreAdByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmHosKpiAd = prmHosKpiAdService.queryPrmHosKpiAd(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiAd);
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */

	/**
	 * @Description 查询数据 0309 科室KPI指标考评计算表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/queryPrmHosKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null  || "".equals(mapVo.get("hos_id").toString())) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String prmHosKpiScore = prmHosKpiScoreService.queryPrmHosKpiScore(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiScore);

	}

	/**
	 * @Description 查询数据 0309 科室KPI指标考评计算表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/queryPrmHosKpiScoreHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiScoreHos(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null || "".equals(mapVo.get("hos_id").toString())) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String prmHosKpiScore = prmHosKpiScoreService.queryPrmHosKpiScoreHos(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiScore);

	}

	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/queryPrmKpiScoreHosByScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiScoreHosByScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		if (mapVo.get("super_kpi_code") != null) {
			if (mapVo.get("super_kpi_code").toString().endsWith("goal")) {
				mapVo.put("super_kpi_code", mapVo.get("kpi_code"));
				mapVo.put("kpi_code", "");
			} else {
				mapVo.put("super_kpi_code", mapVo.get("super_kpi_code").toString());
			}
		}
		mapVo.put("user_id", SessionManager.getUserId());

		mapVo.put("mod_code", SessionManager.getModCode());
		String prmHosKpiScore = prmHosKpiScoreService.queryPrmHosKpiScoreByScheme(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiScore);

	}

	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/queryPrmKpiScoreHosBySchemeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiScoreHosBySchemeTree(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String prmHosKpiScore = prmHosKpiScoreService.queryPrmHosKpiScoreBySchemeTree(mapVo);

		return JSONObject.parseObject(prmHosKpiScore);

	}

	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/auditPrmHosKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmHosKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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

		mapVo.put("user_code", SessionManager.getUserCode());
		mapVo.put("is_audit", 1);
		mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

		String auditPrmHosKpiScore = prmHosKpiScoreService.auditPrmHosKpiScore(mapVo);

		return JSONObject.parseObject(auditPrmHosKpiScore);
	}

	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/reAuditPrmHosKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReAuditPrmHosKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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

		mapVo.put("user_code", "");
		mapVo.put("is_audit", 0);
		mapVo.put("audit_date", "");

		String ReAuditPrmHosKpiScore = prmHosKpiScoreService.reAuditPrmHosKpiScore(mapVo);

		return JSONObject.parseObject(ReAuditPrmHosKpiScore);
	}

	@RequestMapping(value = "/hrp/prm/prmhoskpiscore/collectHosKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHosKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String ReAuditPrmHosKpiScore = prmHosKpiScoreService.collectHosKpiScore(mapVo);

		return JSONObject.parseObject(ReAuditPrmHosKpiScore);
	}
}

