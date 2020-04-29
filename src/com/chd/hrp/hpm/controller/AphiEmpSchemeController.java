package com.chd.hrp.hpm.controller;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.entity.AphiEmpScheme;
import com.chd.hrp.hpm.service.AphiEmpSchemeService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiEmpSchemeController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiEmpSchemeController.class);

	@Resource(name = "aphiEmpSchemeService")
	private final AphiEmpSchemeService aphiEmpSchemeService = null;

//	@Resource(name = "aphiDeptService")
//	private final AphiDeptService aphiDeptService = null;
//
//	@Resource(name = "aphiEmpDutyService")
//	private final AphiEmpDutyService aphiEmpDutyService = null;

//	@Resource(name = "aphiHpmFormulaService")
//	private final AphiHpmFormulaService aphiHpmFormulaService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempscheme/hpmEmpSchemeMainPage", method = RequestMethod.GET)
	public String empSchemeMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempscheme/hpmEmpSchemeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmempscheme/hpmEmpSchemeAddPage", method = RequestMethod.GET)
	public String hpmEmpSchemeAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempscheme/hpmEmpSchemeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmempscheme/addHpmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String empSchemeJson = aphiEmpSchemeService.addEmpScheme(mapVo);

		return JSONObject.parseObject(empSchemeJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmempscheme/queryHpmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		String empScheme = aphiEmpSchemeService.queryEmpScheme(getPage(mapVo));

		return JSONObject.parseObject(empScheme);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmempscheme/deleteHpmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmEmpScheme(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("copy_code", COPY_CODE);

			mapVo.put("dept_id", id.split(";")[0]);
			mapVo.put("dept_no", id.split(";")[1]);

			mapVo.put("duty_code", id.split(";")[2]);

			listVo.add(mapVo);

		}

		String empSchemeJson = aphiEmpSchemeService.deleteBatchEmpScheme(listVo);

		/*
		 * String bonusItemDictJson =
		 * bonusItemDictService.deleteBonusItemDictById(ParamVo);
		 */
		return JSONObject.parseObject(empSchemeJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempscheme/hpmEmpSchemeUpdatePage", method = RequestMethod.GET)
	public String hpmEmpSchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		AphiEmpScheme empScheme = new AphiEmpScheme();

		empScheme = aphiEmpSchemeService.queryEmpSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", empScheme.getCopy_code());
		
		mode.addAttribute("dept_id", empScheme.getDept_id());
		
		mode.addAttribute("dept_no", empScheme.getDept_no());

		//mode.addAttribute("dept_code", empScheme.getDept_code());

		mode.addAttribute("duty_code", empScheme.getDuty_code());

		mode.addAttribute("dept_name", empScheme.getDept_name());

		mode.addAttribute("duty_name", empScheme.getDuty_name());
		
		mode.addAttribute("item_code", empScheme.getItem_code());
		
		mode.addAttribute("item_name", empScheme.getItem_name());

		mode.addAttribute("method_code", empScheme.getMethod_code());

		mode.addAttribute("formula_code", empScheme.getFormula_code());

		mode.addAttribute("formula_name", empScheme.getFormula_name());

		mode.addAttribute("formula_method_chs", empScheme.getFormula_method_chs());

		return "hrp/hpm/hpmempscheme/hpmEmpSchemeUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmempscheme/updateHpmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String empSchemeJson = aphiEmpSchemeService.updateEmpScheme(mapVo);

		return JSONObject.parseObject(empSchemeJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmempscheme/empSchemePage", method = RequestMethod.GET)
	public String incomeitemPercImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempscheme/empSchemeImport";
	}

	// 快速填充跳转
	@RequestMapping(value = "/hrp/hpm/hpmempscheme/hpmEmpSchemeFastPage", method = RequestMethod.GET)
	public String hpmEmpSchemeFastPage(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		mode.addAttribute("checkIds", checkIds);

		return "hrp/hpm/hpmempscheme/hpmEmpSchemeFast";

	}

	// 快速填充保存
	@RequestMapping(value = "/hrp/hpm/hpmempscheme/fastHpmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fastHpmEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}


		String empSchemeJson = aphiEmpSchemeService.fastEmpScheme(mapVo);

		return JSONObject.parseObject(empSchemeJson);

	}

}
