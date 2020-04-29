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
import com.chd.hrp.hpm.entity.AphiDeptKindBonusData;
import com.chd.hrp.hpm.service.AphiDeptKindBonusDataService;

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
public class AphiDeptKindBonusDataController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiDeptKindBonusDataController.class);

	@Resource(name = "aphiDeptKindBonusDataService")
	private final AphiDeptKindBonusDataService aphiDeptKindBonusDataService = null;

	//@Resource(name = "deptBonusDataService1_2")
	//private final AphiDeptBonusDataService1_2 deptBonusDataService1_2 = null;

	@RequestMapping(value = "/hpm/deptkindbonusdata/deptKindBonusDataMainPage", method = RequestMethod.GET)
	public String deptKindBonusDataMainPage(Model mode) throws Exception {

		return "hpm/deptkindbonusdata/deptKindBonusDataMain";

	}

	@RequestMapping(value = "/hpm/deptkindbonusdata/queryDeptKindBonusDataMainPage", method = RequestMethod.GET)
	public String queryDeptKindBonusDataMainPage(Model mode) throws Exception {

		return "hpm/deptkindbonusdata/queryDeptKindBonusDataMain";

	}

	// 查询
	@RequestMapping(value = "/hpm/deptkindbonusdata/queryDeptKindBonusData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptKindBonusData(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (mapVo.containsKey("acct_year_month")) {

			if (mapVo.get("acct_year_month") != null&& !"".equals(mapVo.get("acct_year_month"))) {
				
				mapVo.put("acct_year", mapVo.get("acct_year_month").toString().substring(0, 4));

				mapVo.put("acct_month",mapVo.get("acct_year_month").toString().substring(4,mapVo.get("acct_year_month").toString().length()));

			}
			
		}
		
		String deptKindBonusData = aphiDeptKindBonusDataService.queryDeptKindBonusData(getPage(mapVo));

		return JSONObject.parseObject(deptKindBonusData);

	}

	@RequestMapping(value = "/hpm/deptkindbonusdata/initDeptBonusData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initDeptBonusData(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String companyBonusData = aphiDeptKindBonusDataService.initDeptKindBonusData(mapVo);

		return JSONObject.parseObject(companyBonusData);
		
	}

	@RequestMapping(value = "/hpm/deptkindbonusdata/queryDeptKindBonusDataGrid", method = RequestMethod.GET)
	@ResponseBody
	public String queryDeptKindBonusDataGrid(
			@RequestParam Map<String, Object> mapVo, Model model)
			throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiDeptKindBonusDataService.queryDeptKindBonusDataGrid(mapVo);

		return resultJson;
		
	}

	@RequestMapping(value = "/hpm/deptkindbonusdata/deptKindBonusDataUpdatePage", method = RequestMethod.GET)
	public String deptKindBonusDataUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		AphiDeptKindBonusData deptKindBonusData = aphiDeptKindBonusDataService.queryDeptKindBonusDataByCode(mapVo);

		mode.addAttribute("item_code", deptKindBonusData.getItem_code());

		mode.addAttribute("item_name", deptKindBonusData.getItem_name());

		mode.addAttribute("dept_kind_code",deptKindBonusData.getDept_kind_code());

		mode.addAttribute("dept_kind_name",deptKindBonusData.getDept_kind_name());

		mode.addAttribute("formula_code", deptKindBonusData.getFormula_code());

		mode.addAttribute("formula_method_chs",deptKindBonusData.getFormula_method_chs());

		mode.addAttribute("bonus_money", deptKindBonusData.getBonus_money());

		mode.addAttribute("acct_year", deptKindBonusData.getAcct_year());

		mode.addAttribute("acct_month", deptKindBonusData.getAcct_month());

		mode.addAttribute("nature_code", deptKindBonusData.getNature_code());

		return "/hpm/deptkindbonusdata/deptKindBonusDataUpdatePage";
	}

	@RequestMapping(value = "/hpm/deptkindbonusdata/queryTargetGrid", method = RequestMethod.GET)
	@ResponseBody
	public String queryTargetGrid(@RequestParam Map<String, Object> mapVo,
			Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiDeptKindBonusDataService.queryTargetGrid(mapVo);

		return resultJson;
	}

	@RequestMapping(value = "/hpm/deptkindbonusdata/queryTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTarget(
			@RequestParam(value = "formula_code", required = true) String formula_code,
			@RequestParam Map<String, Object> mapVo, Model model)
			throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		model.addAttribute("formula_code", formula_code);

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		//String resultJson = deptBonusDataService1_2.queryTargetByCode(mapVo);

		return JSONObject.parseObject("");

	}
}
