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
import com.chd.hrp.hpm.entity.AphiDeptScheme;
import com.chd.hrp.hpm.service.AphiDeptSchemeService;
import com.chd.hrp.hpm.service.AphiItemService;

;

/**
 * alfred
 */

@Controller
public class AphiDeptSchemeController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptSchemeController.class);

	@Resource(name = "aphiDeptSchemeService")
	private final AphiDeptSchemeService aphiDeptSchemeService = null;

	@Resource(name = "aphiItemService")
	private final AphiItemService aphiItemService = null;

//	@Resource(name = "aphiHpmFormulaService")
//	private final AphiHpmFormulaService aphiHpmFormulaService = null;

	// 医疗单元核算方案配置
	@RequestMapping(value = {
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0101",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0102",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0103",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0104",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0105",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0106",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0107",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0201",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0202",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0203",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0204",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0205",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0206",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0207",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0301",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0302",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0303",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0304",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0305",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0306",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0307",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0401",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0402",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0403",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0404",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0405",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0406",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0407",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeMainPage0056"
							}, method = RequestMethod.GET)
	public String hpmDeptSchemeMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("nature_code", mapVo.get("nature_code"));
		
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		
		return "hrp/hpm/hpmdeptscheme/hpmDeptSchemeMain";

	}

	// 添加页面
	@RequestMapping(value = {
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0101",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0102",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0103",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0104",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0105",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0106",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0107",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0201",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0202",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0203",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0204",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0205",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0206",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0207",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0301",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0302",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0303",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0304",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0305",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0306",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0307",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0401",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0402",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0403",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0404",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0405",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0406",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0407",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptSchemePage0056",
							}, method = RequestMethod.GET)
	public String addHpmDeptSchemePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("nature_code", mapVo.get("nature_code"));
		
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		
		return "hrp/hpm/hpmdeptscheme/hpmDeptSchemeAdd";

	}

	@RequestMapping(value = {
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0101",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0102",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0103",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0104",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0105",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0106",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0107",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0201",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0202",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0203",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0204",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0205",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0206",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0207",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0301",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0302",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0303",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0304",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0305",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0306",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0307",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0401",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0402",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0403",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0404",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0405",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0406",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0407",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeImportPage0056",
							}, method = RequestMethod.GET)
	public String hpmDeptSchemeImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("nature_code", mapVo.get("nature_code"));
		
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		
		return "hpm/income/dept/deptSchemeImport";

	}

	// 保存
	@RequestMapping(value = {
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0101",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0102",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0103",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0104",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0105",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0106",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0107",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0201",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0202",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0203",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0204",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0205",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0206",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0207",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0301",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0302",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0303",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0304",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0305",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0306",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0307",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0401",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0402",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0403",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0404",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0405",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0406",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0407",
							"/hrp/hpm/hpmdeptscheme/addHpmDeptScheme0056",
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("fun_code", "");

		String deptSchemeJson = aphiDeptSchemeService.addDeptScheme(mapVo);

		return JSONObject.parseObject(deptSchemeJson);

	}

	// 查询
	@RequestMapping(value = {
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0101",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0102",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0103",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0104",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0105",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0106",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0107",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0201",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0202",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0203",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0204",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0205",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0206",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0207",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0301",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0302",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0303",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0304",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0305",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0306",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0307",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0401",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0402",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0403",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0404",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0405",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0406",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0407",
			"/hrp/hpm/hpmdeptscheme/queryHpmDeptScheme0056",
			}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String deptScheme = aphiDeptSchemeService.queryDeptScheme(getPage(mapVo));

		return JSONObject.parseObject(deptScheme);

	}

	// 删除
	@RequestMapping(value = {
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0101",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0102",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0103",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0104",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0105",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0106",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0107",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0201",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0202",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0203",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0204",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0205",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0206",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0207",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0301",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0302",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0303",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0304",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0305",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0306",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0307",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0401",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0402",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0403",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0404",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0405",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0406",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0407",
			"/hrp/hpm/hpmdeptscheme/deleteHpmDeptScheme0056",
			}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDeptScheme(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

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

		String deptSchemeJson = aphiDeptSchemeService.deleteDeptScheme(map, checkIds);

		return JSONObject.parseObject(deptSchemeJson);

	}

	// 修改页面跳转
	@RequestMapping(value = {
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0101",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0102",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0103",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0104",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0105",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0106",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0107",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0201",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0202",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0203",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0204",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0205",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0206",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0207",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0301",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0302",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0303",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0304",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0305",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0306",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0307",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0401",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0402",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0403",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0404",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0405",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0406",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0407",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdatePage0056",
							}, method = RequestMethod.GET)
	public String hpmDeptSchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AphiDeptScheme deptScheme = aphiDeptSchemeService.queryDeptSchemeByCode(mapVo);

		mode.addAttribute("item_code", deptScheme.getItem_code());

		mode.addAttribute("item_name", deptScheme.getItem_name());

		mode.addAttribute("item_note", deptScheme.getItem_note());

		mode.addAttribute("dept_no", deptScheme.getDept_no());
		
		mode.addAttribute("dept_id", deptScheme.getDept_id());

		mode.addAttribute("dept_name", deptScheme.getDept_name());

		mode.addAttribute("method_code", deptScheme.getMethod_code());

		mode.addAttribute("method_name", deptScheme.getMethod_name());

		mode.addAttribute("formula_code", deptScheme.getFormula_code());

		mode.addAttribute("formula_name", deptScheme.getFormula_name());

		mode.addAttribute("fun_code", deptScheme.getFun_code());

		mode.addAttribute("fun_name", deptScheme.getFun_name());
		mode.addAttribute("formula_method_chs", deptScheme.getFormula_method_chs());
		mode.addAttribute("nature_code", mapVo.get("nature_code"));
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmdeptscheme/hpmDeptSchemeUpdate";
	}

	// 修改保存
	@RequestMapping(value = {
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0101",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0102",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0103",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0104",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0105",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0106",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0107",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0201",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0202",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0203",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0204",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0205",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0206",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0207",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0301",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0302",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0303",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0304",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0305",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0306",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0307",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0401",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0402",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0403",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0404",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0405",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0406",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0407",
							"/hrp/hpm/hpmdeptscheme/updateHpmDeptScheme0056",
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String deptSchemeJson = aphiDeptSchemeService.updateDeptScheme(mapVo);

		return JSONObject.parseObject(deptSchemeJson);
	}

	// 快速添加跳转
	@RequestMapping(value = {
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0101",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0102",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0103",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0104",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0105",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0106",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0107",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0201",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0202",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0203",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0204",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0205",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0206",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0207",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0301",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0302",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0303",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0304",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0305",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0306",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0307",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0401",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0402",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0403",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0404",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0405",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0406",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0407",
							"/hrp/hpm/hpmdeptscheme/hpmDeptSchemeFastPage0056",
							}, method = RequestMethod.GET)
	public String hpmDeptSchemeFastPage(
			@RequestParam(value = "checkIds", required = true) String checkIds,
			@RequestParam(value = "codes") String codes, Model mode) throws Exception {

		mode.addAttribute("checkIds", checkIds);
		mode.addAttribute("app_mod_code", codes.split(",")[0]);
		mode.addAttribute("nature_code", codes.split(",")[1]);
		return "hrp/hpm/hpmdeptscheme/hpmDeptSchemeFast";

	}

	// 快速填充
	@RequestMapping(value = {
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0101",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0102",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0103",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0104",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0105",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0106",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0107",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0201",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0202",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0203",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0204",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0205",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0206",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0207",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0301",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0302",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0303",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0304",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0305",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0306",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0307",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0401",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0402",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0403",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0404",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0405",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0406",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0407",
							"/hrp/hpm/hpmdeptscheme/fastHpmDeptScheme0056",
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fastHpmDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String deptBalancePercConfJson = aphiDeptSchemeService.fastDeptScheme(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);

	}

//	/** 上传处理方法 */
//	@RequestMapping(value = "/hpm/income/dept/readDeptSchemeFiles", method = RequestMethod.POST)
//	public String readDeptSchemeFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		List<String[]> list = UploadUtil.readFile(plupload, request, response);
//
//		List<AphiDeptScheme> errorList = new ArrayList<AphiDeptScheme>();
//
//		if (mapVo.get("group_id") == null) {
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//		if (mapVo.get("hos_id") == null) {
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//		if (mapVo.get("copy_code") == null) {
//			mapVo.put("copy_code", SessionManager.getCopyCode());
//		}
//
//		try {
//
//			for (int i = 1; i < list.size(); i++) {
//
//				AphiDeptScheme deptScheme = new AphiDeptScheme();
//
//				StringBuffer err_sb = new StringBuffer();
//
//				String temp[] = list.get(i);
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					deptScheme.setDept_code(temp[0]);
//
//					mapVo.put("dept_code", temp[0]);
//
//				} else {
//
//					err_sb.append("医疗单位名称编码不能为空！");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[1])) {
//
//					deptScheme.setItem_code(temp[1]);
//
//					mapVo.put("item_code", temp[1]);
//
//				} else {
//
//					err_sb.append("奖金项目编码不能为空！");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[2])) {
//
//					deptScheme.setFormula_code(temp[2]);
//
//					mapVo.put("formula_code", temp[2]);
//
//				} else {
//
//					err_sb.append("取值公式编码不能为空");
//
//				}
//
//				// 计算公式编码默认为02
//				deptScheme.setMethod_code("02");
//				mapVo.put("method_code", "02");
//
////				AphiHpmFormula HpmFormula = aphiHpmFormulaService.queryFormulaByCode(mapVo);
////
////				if (HpmFormula == null) {
////
////					err_sb.append("取值公式编码不存在");
////
////				}
//
//				AphiItem item = aphiItemService.queryItemByCode(mapVo);
//
//				if (item == null) {
//
//					err_sb.append("奖金项目编码不存在！");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					deptScheme.setError_type(err_sb.toString());
//
//					errorList.add(deptScheme);
//
//				} else {
//
//					aphiDeptSchemeService.addDeptScheme(mapVo);
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			AphiDeptScheme deptScheme = new AphiDeptScheme();
//
//			deptScheme.setError_type("系统导入错误！");
//
//			errorList.add(deptScheme);
//
//			response.getWriter().print(JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//			return null;
//		}
//
//		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//		return "/hpm/income/dept/deptSchemeImportMessage";
//
//	}
//
//	@RequestMapping(value = "/hpm/income/dept/addBatchDeptScheme", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchDeptScheme(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		List<AphiDeptScheme> errorList = new ArrayList<AphiDeptScheme>();
//
//		if (mapVo.get("group_id") == null) {
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//		if (mapVo.get("hos_id") == null) {
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//		if (mapVo.get("copy_code") == null) {
//			mapVo.put("copy_code", SessionManager.getCopyCode());
//		}
//
//		JSONArray json = JSONArray.parseArray(paramVo);
//
//		Iterator it = json.iterator();
//
//		try {
//
//			AphiDeptScheme deptScheme = new AphiDeptScheme();
//
//			StringBuffer err_sb = new StringBuffer();
//
//			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//			mapVo.put("dept_code", jsonObj.get("dept_code"));
//
//			mapVo.put("item_code", jsonObj.get("item_code"));
//
//			mapVo.put("method_code", jsonObj.get("method_code"));
//
//			mapVo.put("formula_code", jsonObj.get("formula_code"));
//
////			AphiHpmFormula HpmFormula = aphiHpmFormulaService.queryFormulaByCode(mapVo);
////
////			if (HpmFormula == null) {
////
////				err_sb.append("取值公式编码不存在");
////
////			}
//
//			AphiItem item = aphiItemService.queryItemByCode(mapVo);
//
//			if (item == null) {
//
//				err_sb.append("奖金项目编码不存在！");
//
//			}
//
//			if (err_sb.toString().length() > 0) {
//
//				deptScheme.setDept_code(jsonObj.get("dept_code").toString());
//
//				deptScheme.setItem_code(jsonObj.get("item_code").toString());
//
//				deptScheme.setMethod_code(jsonObj.get("method_code").toString());
//
//				deptScheme.setFormula_code(jsonObj.get("formula_code").toString());
//
//				deptScheme.setError_type(err_sb.toString());
//
//				errorList.add(deptScheme);
//
//			} else {
//
//				aphiDeptSchemeService.addDeptScheme(mapVo);
//
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
//		}
//		if (errorList.size() > 0) {
//
//			return JSONObject.parseObject(JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//		} else {
//
//			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
//
//		}
//	}

	// 下载导入模板
//	@RequestMapping(value = "/hpm/income/dept/downTemplateDeptScheme", method = RequestMethod.GET)
//	public String downTemplateDeptScheme(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//		printTemplate(request, response, "hpm\\方案制定\\收支结余计将模式\\核算方案配置", "医疗单元核算方案配置.xlsx");
//		return null;
//	}

}
