
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
import com.chd.hrp.prm.entity.PrmEmpScheme;
import com.chd.hrp.prm.service.PrmEmpKpiAdService;
import com.chd.hrp.prm.service.PrmEmpKpiScoreService;
import com.chd.hrp.prm.service.PrmEmpKpiSectionService;
import com.chd.hrp.prm.service.PrmEmpSchemeService;
import com.chd.hrp.prm.service.PrmFormulaStackService;

/**
 * 
 * @Description:
 * 0409 职工KPI指标考评计算表
 * @Table:
 * PRM_EMP_KPI_SCORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmEmpKpiScoreController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmEmpKpiScoreController.class);

	// 引入Service服务
	@Resource(name = "prmEmpKpiScoreService")
	private final PrmEmpKpiScoreService prmEmpKpiScoreService = null;

	// 引入Service服务
	@Resource(name = "prmEmpKpiSectionService")
	private final PrmEmpKpiSectionService prmEmpKpiSectionService = null;

	// 引入Service服务
	@Resource(name = "prmEmpKpiAdService")
	private final PrmEmpKpiAdService prmEmpKpiAdService = null;

	@Resource(name = "prmEmpSchemeService")
	private final PrmEmpSchemeService prmEmpSchemeService = null;

	@Resource(name = "prmFormulaStackService")
	private final PrmFormulaStackService prmFormulaStackService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpiscore/prmEmpKpiScoreMainPage", method = RequestMethod.GET)
	public String prmEmpKpiScoreMainPage(Model mode) throws Exception {

		return "hrp/prm/prmempkpiscore/prmEmpKpiScoreMain";
	}

	@RequestMapping(value = "/hrp/prm/prmempkpiscore/prmEmpKpiScoreSchemeMainPage", method = RequestMethod.GET)
	public String prmEmpKpiScoreSchemeMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("emp_no", mapVo.get("emp_no"));

		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		return "hrp/prm/prmempkpiscore/prmEmpKpiScoreSchemeMain";
	}

	@RequestMapping(value = "/hrp/prm/prmempkpiscore/prmEmpKpiScoreAdMain", method = RequestMethod.GET)
	public String prmEmpKpiScoreAdMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		PrmEmpScheme prmEmpScheme = prmEmpSchemeService.queryPrmEmpSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("hos_code", prmEmpScheme.getHos_code());

		mode.addAttribute("hos_name", prmEmpScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name", mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("emp_no", mapVo.get("emp_no"));

		mode.addAttribute("emp_id", mapVo.get("emp_id"));

		mode.addAttribute("emp_code", prmEmpScheme.getEmp_code());

		mode.addAttribute("emp_name", prmEmpScheme.getEmp_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmempkpiscore/prmEmpKpiScoreAdMain";

	}

	@RequestMapping(value = "/hrp/prm/prmempkpiscore/prmEmpKpiScoreSectionMain", method = RequestMethod.GET)
	public String prmEmpKpiScoreSectionMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		PrmEmpScheme prmEmpScheme = prmEmpSchemeService.queryPrmEmpSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("hos_code", prmEmpScheme.getHos_code());

		mode.addAttribute("hos_name", prmEmpScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", prmEmpScheme.getAcc_year());

		mode.addAttribute("acc_month", prmEmpScheme.getAcc_month());

		mode.addAttribute("goal_code", prmEmpScheme.getGoal_code());

		mode.addAttribute("nature_name", prmEmpScheme.getNature_name());

		mode.addAttribute("kpi_code", prmEmpScheme.getKpi_code());

		mode.addAttribute("kpi_name", prmEmpScheme.getKpi_name());

		mode.addAttribute("emp_no", prmEmpScheme.getEmp_no());

		mode.addAttribute("emp_id", prmEmpScheme.getEmp_id());

		mode.addAttribute("emp_code", prmEmpScheme.getEmp_code());

		mode.addAttribute("emp_name", prmEmpScheme.getEmp_name());

		mode.addAttribute("ratio", prmEmpScheme.getRatio());

		mode.addAttribute("goal_value", prmEmpScheme.getGoal_value());

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmempkpiscore/prmEmpKpiScoreSectionMain";

	}
	
	
	@RequestMapping(value = "/hrp/prm/prmempkpiscore/prmEmpKpiScoreFormulaMain", method = RequestMethod.GET)
	public String prmEmpKpiScoreFormulaMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("kpi_value", mapVo.get("kpi_value"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("emp_id", mapVo.get("emp_id"));

		mode.addAttribute("emp_no", mapVo.get("emp_no"));

		mode.addAttribute("formula_method_chs", mapVo.get("formula_method_chs"));

		mode.addAttribute("formula_code", mapVo.get("formula_code"));

		return "hrp/prm/prmempkpiscore/prmEmpKpiScoreFormulaMain";

	}
	
	@RequestMapping(value = "/hrp/prm/prmempkpiscore/queryPrmEmpScoreFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpScoreFormula(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String EmpKpiSection = prmFormulaStackService.queryPrmEmpFormulaStackByScore(getPage(mapVo));

		return JSONObject.parseObject(EmpKpiSection);
	}
	
	

	/**
	 * @Description 查询数据 评分方法区间法(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpiscore/queryPrmEmpSectionScoreByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpSectionScoreByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String EmpKpiSection = prmEmpKpiSectionService.queryPrmEmpKpiSection(getPage(mapVo));

		return JSONObject.parseObject(EmpKpiSection);
	}

	/**
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpiscore/queryPrmEmpKpiScoreAdByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiScoreAdByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmEmpKpiAd = prmEmpKpiAdService.queryPrmEmpKpiAd(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiAd);
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
	@RequestMapping(value = "/hrp/prm/prmempkpiscore/queryPrmEmpKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmEmpKpiScore = prmEmpKpiScoreService.queryPrmEmpKpiScore(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiScore);

	}

	/**
	 * @Description 查询数据 0309 科室KPI指标考评计算表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpiscore/queryPrmEmpKpiScoreEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiScoreEmp(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String prmEmpKpiScore = prmEmpKpiScoreService.queryPrmEmpKpiScoreEmp(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiScore);

	}

	@RequestMapping(value = "/hrp/prm/prmempkpiscore/queryPrmKpiScoreEmpByScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiScoreEmpByScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmEmpKpiScore = prmEmpKpiScoreService.queryPrmEmpKpiScoreByScheme(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiScore);

	}

	@RequestMapping(value = "/hrp/prm/prmempkpiscore/queryPrmKpiScoreEmpBySchemeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmKpiScoreEmpBySchemeTree(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmEmpKpiScore = prmEmpKpiScoreService.queryPrmEmpKpiScoreBySchemeTree(mapVo);

		return JSONObject.parseObject(prmEmpKpiScore);

	}

	@RequestMapping(value = "/hrp/prm/prmempkpiscore/auditPrmEmpKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmEmpKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String auditPrmEmpKpiScore = prmEmpKpiScoreService.auditPrmEmpKpiScore(mapVo);

		return JSONObject.parseObject(auditPrmEmpKpiScore);
	}

	@RequestMapping(value = "/hrp/prm/prmempkpiscore/reAuditPrmEmpKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ReAuditPrmEmpKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String ReAuditPrmEmpKpiScore = prmEmpKpiScoreService.reAuditPrmEmpKpiScore(mapVo);

		return JSONObject.parseObject(ReAuditPrmEmpKpiScore);
	}

	@RequestMapping(value = "/hrp/prm/prmempkpiscore/collectEmpKpiScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectEmpKpiScore(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String ReAuditPrmEmpKpiScore = prmEmpKpiScoreService.collectEmpKpiScore(mapVo);

		return JSONObject.parseObject(ReAuditPrmEmpKpiScore);
	}
}

