
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
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.service.PrmDeptKpiAdService;
import com.chd.hrp.prm.service.PrmDeptKpiScoreService;
import com.chd.hrp.prm.service.PrmDeptKpiSectionService;
import com.chd.hrp.prm.service.PrmDeptSchemeService;
import com.chd.hrp.prm.service.PrmFormulaStackService;

/**
 * 
 * @Description: 0309 科室KPI指标考评计算表
 * @Table: PRM_DEPT_KPI_SCORE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptKpiScoreController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptKpiScoreController.class);

	// 引入Service服务
	@Resource(name = "prmDeptKpiScoreService")
	private final PrmDeptKpiScoreService prmDeptKpiScoreService = null;

	// 引入Service服务
	@Resource(name = "prmDeptKpiSectionService")
	private final PrmDeptKpiSectionService prmDeptKpiSectionService = null;

	// 引入Service服务
	@Resource(name = "prmDeptKpiAdService")
	private final PrmDeptKpiAdService prmDeptKpiAdService = null;

	@Resource(name = "prmDeptSchemeService")
	private final PrmDeptSchemeService prmDeptSchemeService = null;

	@Resource(name = "prmFormulaStackService")
	private final PrmFormulaStackService prmFormulaStackService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/prmDeptKpiScoreMainPage", method = RequestMethod.GET)
	public String prmDeptKpiScoreMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkpiscore/prmDeptKpiScoreMain";
	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/prmDeptKpiScoreSchemeMainPage", method = RequestMethod.GET)
	public String prmDeptKpiScoreSchemeMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("dept_no", mapVo.get("dept_no"));

		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		return "hrp/prm/prmdeptkpiscore/prmDeptKpiScoreSchemeMain";
	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/prmDeptKpiScoreAdMain", method = RequestMethod.GET)
	public String prmDeptKpiScoreAdMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		PrmDeptScheme prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("hos_code", prmDeptScheme.getHos_code());

		mode.addAttribute("hos_name", prmDeptScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name", mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("dept_no", mapVo.get("dept_no"));

		mode.addAttribute("dept_id", mapVo.get("dept_id"));

		mode.addAttribute("dept_code", prmDeptScheme.getDept_code());

		mode.addAttribute("dept_name", prmDeptScheme.getDept_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmdeptkpiscore/prmDeptKpiScoreAdMain";

	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/prmDeptKpiScoreSectionMain", method = RequestMethod.GET)
	public String prmDeptKpiScoreSectionMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		PrmDeptScheme prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("hos_code", prmDeptScheme.getHos_code());

		mode.addAttribute("hos_name", prmDeptScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", prmDeptScheme.getAcc_year());

		mode.addAttribute("acc_month", prmDeptScheme.getAcc_month());

		mode.addAttribute("goal_code", prmDeptScheme.getGoal_code());

		mode.addAttribute("nature_name", prmDeptScheme.getNature_name());

		mode.addAttribute("kpi_code", prmDeptScheme.getKpi_code());

		mode.addAttribute("kpi_name", prmDeptScheme.getKpi_name());

		mode.addAttribute("dept_no", prmDeptScheme.getDept_no());

		mode.addAttribute("dept_id", prmDeptScheme.getDept_id());

		mode.addAttribute("dept_code", prmDeptScheme.getDept_code());

		mode.addAttribute("dept_name", prmDeptScheme.getDept_name());

		mode.addAttribute("ratio", prmDeptScheme.getRatio());

		mode.addAttribute("goal_value", prmDeptScheme.getGoal_value());

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmdeptkpiscore/prmDeptKpiScoreSectionMain";

	}
	
	
	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/prmDeptKpiScoreFormulaMain", method = RequestMethod.GET)
	public String prmDeptKpiScoreFormulaMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("kpi_value", mapVo.get("kpi_value"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("dept_id", mapVo.get("dept_id"));

		mode.addAttribute("dept_no", mapVo.get("dept_no"));

		mode.addAttribute("formula_method_chs", mapVo.get("formula_method_chs"));

		mode.addAttribute("formula_code", mapVo.get("formula_code"));

		return "hrp/prm/prmdeptkpiscore/prmDeptKpiScoreFormulaMain";

	}
	
	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/queryPrmDeptScoreFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptScoreFormula(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String deptKpiSection = prmFormulaStackService.queryPrmFormulaStackByScore(getPage(mapVo));

		return JSONObject.parseObject(deptKpiSection);
	}
	
	

	/**
	 * @Description 查询数据 评分方法区间法(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/queryPrmDeptSectionScoreByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptSectionScoreByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String deptKpiSection = prmDeptKpiSectionService.queryPrmDeptKpiSection(getPage(mapVo));

		return JSONObject.parseObject(deptKpiSection);
	}

	/**
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/queryPrmDeptKpiScoreAdByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiScoreAdByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmDeptKpiAd = prmDeptKpiAdService.queryPrmDeptKpiAd(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiAd);
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
	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/queryPrmDeptKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}
		String prmDeptKpiScore = prmDeptKpiScoreService.queryPrmDeptKpiScore(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiScore);

	}

	/**
	 * @Description 查询数据 0309 科室KPI指标考评计算表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/queryPrmDeptKpiScoreDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiScoreDept(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String prmDeptKpiScore = prmDeptKpiScoreService.queryPrmDeptKpiScoreDept(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiScore);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/queryPrmKpiScoreDeptByScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiScoreDeptByScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmDeptKpiScore = prmDeptKpiScoreService.queryPrmDeptKpiScoreByScheme(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiScore);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/queryPrmKpiScoreDeptBySchemeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiScoreDeptBySchemeTree(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmDeptKpiScore = prmDeptKpiScoreService.queryPrmDeptKpiScoreBySchemeTree(mapVo);

		return JSONObject.parseObject(prmDeptKpiScore);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/auditPrmDeptKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmDeptKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String auditPrmDeptKpiScore = prmDeptKpiScoreService.auditPrmDeptKpiScore(mapVo);

		return JSONObject.parseObject(auditPrmDeptKpiScore);
	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/reAuditPrmDeptKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReAuditPrmDeptKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String ReAuditPrmDeptKpiScore = prmDeptKpiScoreService.reAuditPrmDeptKpiScore(mapVo);

		return JSONObject.parseObject(ReAuditPrmDeptKpiScore);
	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpiscore/collectDeptKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectDeptKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String ReAuditPrmDeptKpiScore = prmDeptKpiScoreService.collectDeptKpiScore(mapVo);

		return JSONObject.parseObject(ReAuditPrmDeptKpiScore);
	}

}
