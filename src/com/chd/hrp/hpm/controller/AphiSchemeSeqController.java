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
import com.chd.hrp.hpm.entity.AphiDeptScheme;
import com.chd.hrp.hpm.service.AphiDeptSchemeService;
import com.chd.hrp.hpm.service.AphiSchemeSeqService;

@Controller
public class AphiSchemeSeqController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiSchemeSeqController.class);

	@Resource(name = "aphiSchemeSeqService")
	private AphiSchemeSeqService aphiSchemeSeqService = null;

	@Resource(name = "aphiDeptSchemeService")
	private AphiDeptSchemeService aphiDeptSchemeService = null;

	// @Resource(name = "deptBonusDataService2_1")
	// private DeptBonusDataPrefService deptBonusDataService2_1 = null;

	// @Resource(name = "hpmFormulaService")
	// private final AphiHpmFormulaService hpmFormulaService = null;

	@RequestMapping(value = "/hrp/hpm/hpmschemeseq/hpmSchemeSeqMainPage", method = RequestMethod.GET)
	public String hpmSchemeAuditMainPage(Model model) {

		return "hrp/hpm/hpmschemeseq/hpmSchemeSeqMain";

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmschemeseq/queryHpmSchemeSeq", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmSchemeSeq(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String querySchemeCheck = aphiSchemeSeqService.queryHpmSchemeSeq(getPage(mapVo));

		return JSONObject.parseObject(querySchemeCheck);

	}

	//
	@RequestMapping(value = "/hrp/hpm/hpmschemeseq/auditHpmSchemeSeq", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHpmSchemeSeq(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

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
		String JsonResult = aphiSchemeSeqService.auditHpmSchemeSeq(mapVo);

		return JSONObject.parseObject(JsonResult);
	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeseq/querySchemeSeqGrid", method = RequestMethod.GET)
	@ResponseBody
	public String querySchemeSeqGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

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


		String resultJson = aphiSchemeSeqService.querySchemeSeqGrid(mapVo);

		return resultJson;
	}

	// 方案审核
	@RequestMapping(value = "/hrp/hpm/hpmschemeseq/auditHpmSchemeSeqPage", method = RequestMethod.GET)
	public String auditHpmSchemeSeqPage(Model model) {

		return "hrp/hpm/hpmschemeseq/hpmSchemeAudit";

	}

	// 修改页面
	@RequestMapping(value = "/hrp/hpm/hpmschemeseq/hpmSchemeUpdatePage", method = RequestMethod.GET)
	public String hpmSchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model model) {

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
		AphiDeptScheme deptScheme = aphiDeptSchemeService.queryDeptSchemeByCode(mapVo);

		model.addAttribute("dept_kind_code", deptScheme.getDept_kind_name());

		model.addAttribute("dept_id", deptScheme.getDept_id());

		model.addAttribute("dept_name", deptScheme.getDept_name());

		model.addAttribute("item_code", deptScheme.getItem_code());

		model.addAttribute("item_name", deptScheme.getItem_name());

		model.addAttribute("formula_code", deptScheme.getFormula_code());

		model.addAttribute("formula_name", deptScheme.getFormula_name());

		model.addAttribute("formula_method_chs", deptScheme.getFormula_method_chs());

		return "hrp/hpm/hpmschemeseq/hpmSchemeUpdate";

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeseq/queryTarget2_1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTarget2_1(@RequestParam(value = "formula_code", required = true) String formula_code,
			@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		model.addAttribute("formula_code", formula_code);

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
		// String resultJson = deptBonusDataService2_1.queryTargetByCode(mapVo);

		return JSONObject.parseObject("");

	}

	@RequestMapping(value = "/hrp/hpm/hpmschemeseq/hpmFormulaUpdatePage", method = RequestMethod.GET)
	public String hpmFormulaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		// AphiHpmFormula formula = hpmFormulaService.queryFormulaByCode(mapVo);
		//
		// mode.addAttribute("formula_code", formula.getFormula_code());
		//
		// mode.addAttribute("formula_name", formula.getFormula_name());
		//
		// mode.addAttribute("formula_method_chs",
		// formula.getFormula_method_chs());
		//
		// mode.addAttribute("formula_method_eng",
		// formula.getFormula_method_eng());
		//
		// mode.addAttribute("is_stop", formula.getIs_stop());
		//
		// mode.addAttribute("formula_stack", formula.getFormula_stack());

		return "hpm/formula/hpmFormulaUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmschemeseq/updateHpmFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmFormula(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		// String formulaJson = hpmFormulaService.updateFormula(mapVo);

		return JSONObject.parseObject("");
	}

}
