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
import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.service.AphiDeptBonusDataService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiDeptBonusDataController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiDeptBonusDataController.class);

	@Resource(name = "aphiDeptBonusDataService")
	private final AphiDeptBonusDataService aphiDeptBonusDataService = null;

	// 医疗核算
	@RequestMapping(value = { "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0101", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0102",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0103", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0104",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0105", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0106",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0107", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0201",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0202", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0203",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0204", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0205",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0206", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0207",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0301", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0302",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0303", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0304",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0305", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0306",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0307", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0401",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0402", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0403",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0404", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0405",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0406", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMainPage0407"

	}, method = RequestMethod.GET)
	public String hpmDeptBonusDataMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("nature_code", mapVo.get("nature_code"));

		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));

		return "hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataMain";
	}

	// 医疗查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForFormulaMainPage", method = RequestMethod.GET)
	public String hpmDeptBonusDataForFormulaMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		
		
		if("1".equals(mapVo.get("viewFlag"))){
			
			AphiDeptBonusData deptBonusData = aphiDeptBonusDataService.queryHpmDeptBonusDataByCode(mapVo);
			
			if(deptBonusData !=null){
				
				mode.addAttribute("acct_yearm", mapVo.get("acct_yearm"));
				
				mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));

				mode.addAttribute("nature_code", mapVo.get("nature_code"));

				mode.addAttribute("dept_kind_name", deptBonusData.getDept_kind_name());

				mode.addAttribute("dept_kind_code", deptBonusData.getDept_kind_code());

				mode.addAttribute("dept_id", deptBonusData.getDept_id());
				
				mode.addAttribute("dept_no", deptBonusData.getDept_no());
				
				mode.addAttribute("dept_name", deptBonusData.getDept_name());

				mode.addAttribute("dept_code", deptBonusData.getDept_code());

				mode.addAttribute("item_code", deptBonusData.getItem_code());
				
				mode.addAttribute("item_name", deptBonusData.getItem_name());
				if(deptBonusData.getFormula_method_chs() == null){
					mode.addAttribute("formula_method_chs", "");

				}else{
					
					mode.addAttribute("formula_method_chs", deptBonusData.getFormula_method_chs());
				}

				mode.addAttribute("bonus_money", deptBonusData.getBonus_money());

				mode.addAttribute("formula_code", deptBonusData.getFormula_code());
				
			}else{
				
				mode.addAttribute("errorMsg", "当前科室没有配置改核算项目");
				
			}
			
			
		}else{
			
			mode.addAttribute("acct_yearm", mapVo.get("acct_yearm"));
			
			mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));

			mode.addAttribute("nature_code", mapVo.get("nature_code"));

			mode.addAttribute("dept_kind_name", mapVo.get("dept_kind_name"));

			mode.addAttribute("dept_kind_code", mapVo.get("dept_kind_code"));

			mode.addAttribute("dept_id", mapVo.get("dept_id"));
			
			mode.addAttribute("dept_no", mapVo.get("dept_no"));
			
			mode.addAttribute("dept_name", mapVo.get("dept_name"));

			mode.addAttribute("dept_code", mapVo.get("dept_code"));

			mode.addAttribute("item_code", mapVo.get("item_code"));
			
			mode.addAttribute("item_name", mapVo.get("item_name"));

			mode.addAttribute("formula_method_chs", mapVo.get("formula_method_chs"));

			mode.addAttribute("bonus_money", mapVo.get("bonus_money"));

			mode.addAttribute("formula_code", mapVo.get("formula_code"));
		}
		
		

		return "hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForFormulaMain";

	}

	// 医疗查询
	@RequestMapping(value = { "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0101",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0102", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0103",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0104", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0105",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0106", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0107",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0201", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0202",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0203", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0204",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0205", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0206",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0207", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0301",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0302", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0303",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0304", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0305",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0306", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0307",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0401", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0402",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0403", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0404",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0405", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0406",
			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMainPage0407" }, method = RequestMethod.GET)
	public String hpmDeptBonusDataForTargetMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));

		mode.addAttribute("nature_code", mapVo.get("nature_code"));

		return "hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataForTargetMain";

	}

	// 查询
	@RequestMapping(value = { "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0101", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0102",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0103", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0104",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0105", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0106",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0107", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0201",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0202", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0203",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0204", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0205",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0206", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0207",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0301", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0302",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0303", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0304",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0305", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0306",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0307", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0401",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0402", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0403",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0404", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0405",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0406", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusData0407" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptBonusData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (mapVo.containsKey("acct_yearm")) {

			if (mapVo.get("acct_yearm") != null && !"".equals(mapVo.get("acct_yearm"))) {

				mapVo.put("acct_year", mapVo.get("acct_yearm").toString().substring(0, 4));

				mapVo.put("acct_month", mapVo.get("acct_yearm").toString().substring(4, mapVo.get("acct_yearm").toString().length()));

			}
		}

		String deptBonusData = aphiDeptBonusDataService.queryHpmDeptBonusData(getPage(mapVo));

		return JSONObject.parseObject(deptBonusData);
	}

	@RequestMapping(value = { "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0101", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0102",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0103", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0104",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0105", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0106",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0107", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0201",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0202", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0203",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0204", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0205",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0206", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0207",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0301", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0302",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0303", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0304",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0305", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0306",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0307", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0401",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0402", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0403",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0404", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0405",
			"/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0406", "/hrp/hpm/hpmdeptbonusdata/initHpmDeptBonusData0407" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmDeptBonusData(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiDeptBonusDataService.initHpmDeptBonusData(mapVo);

		return JSONObject.parseObject(resultJson);

	}

	@RequestMapping(value = { "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0101", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0102",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0103", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0104",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0105", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0106",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0107", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0201",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0202", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0203",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0204", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0205",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0206", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0207",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0301", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0302",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0303", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0304",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0305", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0306",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0307", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0401",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0402", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0403",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0404", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0405",
			"/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0406", "/hrp/hpm/hpmdeptbonusdata/collectHpmDeptBonusData0407" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHpmDeptBonusData(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String COPY_CODE = SessionManager.getCopyCode();

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = "";
		
		try{
			
			resultJson = aphiDeptBonusDataService.collectHpmDeptBonusData(mapVo);
			
		}catch(Exception e){
			
			resultJson = e.getMessage();
		}
		

		return JSONObject.parseObject(resultJson);

	}

	@RequestMapping(value = { "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0101", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0102",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0103", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0104",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0105", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0106",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0107", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0201",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0202", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0203",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0204", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0205",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0206", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0207",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0301", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0302",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0303", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0304",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0305", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0306",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0307", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0401",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0402", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0403",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0404", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0405",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0406", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataGrid0407" }, method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmDeptBonusDataGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String COPY_CODE = SessionManager.getCopyCode();

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiDeptBonusDataService.queryHpmDeptBonusDataGrid(mapVo);

		return resultJson;
	}

//	@RequestMapping(value = { "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0101", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0102",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0103", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0104",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0105", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0106",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0107", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0201",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0202", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0203",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0204", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0205",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0206", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0207",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0301", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0302",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0303", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0304",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0305", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0306",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0307", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0401",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0402", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0403",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0404", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0405",
//			"/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0406", "/hrp/hpm/hpmdeptbonusdata/hpmDeptBonusDataUpdatePage0407" }, method = RequestMethod.GET)
//	public String hpmDeptBonusDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {
//
//		if (mapVo.get("group_id") == null) {
//
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//
//		if (mapVo.get("hos_id") == null) {
//
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		mapVo.put("copy_code", COPY_CODE);
//
//		AphiDeptBonusData deptBonusData = aphiDeptBonusDataService.queryHpmDeptBonusDataByCode(mapVo);
//
//		model.addAttribute("dept_kind_name", deptBonusData.getDept_kind_name());
//
//		model.addAttribute("dept_kind_code", deptBonusData.getDept_kind_code());
//
//		model.addAttribute("dept_name", deptBonusData.getDept_name());
//
//		model.addAttribute("dept_code", deptBonusData.getDept_code());
//
//		model.addAttribute("item_name", deptBonusData.getItem_name());
//
//		model.addAttribute("formula_method_chs", deptBonusData.getFormula_method_chs());
//
//		model.addAttribute("bonus_money", deptBonusData.getBonus_money());
//
//		model.addAttribute("formula_code", deptBonusData.getFormula_code());
//
//		model.addAttribute("method_code", deptBonusData.getMethod_code());
//
//		model.addAttribute("nature_code", deptBonusData.getNature_code());
//
//		model.addAttribute("acct_year", deptBonusData.getAcct_year());
//
//		model.addAttribute("acct_month", deptBonusData.getAcct_month());
//
//		return "/hrp/hpm/hpmdeptbonusdata/deptBonusDataUpdatePage";
//	}

	@RequestMapping(value = { "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0101",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0102", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0103",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0104", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0105",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0106", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0107",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0201", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0202",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0203", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0204",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0205", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0206",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0207", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0301",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0302", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0303",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0304", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0305",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0306", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0307",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0401", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0402",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0403", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0404",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0405", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0406",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTargetGrid0407"

	}, method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmDeptBonusDataForTargetGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String COPY_CODE = SessionManager.getCopyCode();

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiDeptBonusDataService.queryHpmDeptBonusDataForTargetGrid(mapVo);

		return resultJson;

	}

	@RequestMapping(value = { "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0101", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0102",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0103", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0104",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0105", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0106",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0107", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0201",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0202", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0203",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0204", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0205",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0206", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0207",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0301", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0302",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0303", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0304",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0305", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0306",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0307", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0401",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0402", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0403",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0404", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0405",
			"/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0406", "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForTarget0407" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptBonusDataForTarget(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String resultJson = aphiDeptBonusDataService.queryHpmDeptBonusDataForTarget(getPage(mapVo));

		return JSONObject.parseObject(resultJson);

	}
	
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusdata/queryHpmDeptBonusDataForFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptBonusDataForFormula(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String resultJson = aphiDeptBonusDataService.queryHpmDeptBonusDataForFormula(mapVo);

		return JSONObject.parseObject(resultJson);

	}

}
