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
import com.chd.hrp.hpm.entity.AphiSubSchemeConf;
import com.chd.hrp.hpm.serviceImpl.AphiSubSchemeConfServiceImpl;

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
public class AphiSubSchemeConfController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiSubSchemeConfController.class);

	@Resource(name = "aphiSubSchemeConfService")
	private final AphiSubSchemeConfServiceImpl aphiSubSchemeConfService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/hpmSubSchemeConfMainPage", method = RequestMethod.GET)
	public String hpmSubSchemeConfMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmsubschemeconf/hpmSubSchemeConfMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/hpmSubSchemeConfAddPage", method = RequestMethod.GET)
	public String hpmSubSchemeConfAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmsubschemeconf/hpmSubSchemeConfAdd";

	}

	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/hpmCreateSubSchemeConfPage", method = RequestMethod.GET)
	public String hpmCreateSubSchemeConfPage(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		model.addAttribute("year_month", mapVo.get("year_month"));

		model.addAttribute("sub_scheme_seq_no", mapVo.get("sub_scheme_seq_no"));

		return "/hrp/hpm/hpmsubschemeconf/hpmCreateSubSchemeConf";

	}

	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/createHpmSubSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmSubSchemeConf(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String result = aphiSubSchemeConfService.createSubSchemeConf(mapVo);

		return JSONObject.parseObject(result);

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/addHpmSubSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmSubSchemeConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.containsKey("acct_year_month")) {

			if (mapVo.get("acct_year_month") != null && !"".equals(mapVo.get("acct_year_month"))) {

				mapVo.put("acct_year", mapVo.get("acct_year_month").toString().substring(0, 4));

				mapVo.put("acct_month", mapVo.get("acct_year_month").toString().substring(4, mapVo.get("acct_year_month").toString().length()));

			}
		}

		String subSchemeConfJson = aphiSubSchemeConfService.addSubSchemeConf(mapVo);

		return JSONObject.parseObject(subSchemeConfJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/queryHpmSubSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmSubSchemeConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.containsKey("acct_year_month")) {

			if (mapVo.get("acct_year_month") != null && !"".equals(mapVo.get("acct_year_month"))) {

				mapVo.put("acct_year", mapVo.get("acct_year_month").toString().substring(0, 4));

				mapVo.put("acct_month", mapVo.get("acct_year_month").toString().substring(4, mapVo.get("acct_year_month").toString().length()));

			}
		}

		String subSchemeConf = aphiSubSchemeConfService.querySubSchemeConf(getPage(mapVo));

		return JSONObject.parseObject(subSchemeConf);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/deleteHpmSubSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmSubSchemeConf(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("acct_year", id.split(";")[0]);

			mapVo.put("acct_month", id.split(";")[1]);// 实际实体类变量

			listVo.add(mapVo);
		}

		String subSchemeConfJson = aphiSubSchemeConfService.deleteSubSchemeConf(listVo);
		/*
		 * String bonusItemDictJson =
		 * bonusItemDictService.deleteBonusItemDictById(ParamVo);
		 */
		return JSONObject.parseObject(subSchemeConfJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/hpmSubSchemeConfUpdatePage", method = RequestMethod.GET)
	public String hpmSubSchemeConfUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AphiSubSchemeConf subSchemeConf = new AphiSubSchemeConf();

		subSchemeConf = aphiSubSchemeConfService.querySubSchemeConfByCode(mapVo);

		//mode.addAttribute("comp_code", subSchemeConf.getComp_code());

		mode.addAttribute("copy_code", subSchemeConf.getCopy_code());

		mode.addAttribute("acct_year_month", subSchemeConf.getAcct_year() + subSchemeConf.getAcct_month());

		mode.addAttribute("sub_scheme_seq_no", subSchemeConf.getSub_scheme_seq_no());

		return "hrp/hpm/hpmsubschemeconf/hpmSubSchemeConfUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/updateHpmSubSchemeConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmSubSchemeConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.containsKey("acct_year_month")) {

			if (mapVo.get("acct_year_month") != null && !"".equals(mapVo.get("acct_year_month"))) {

				mapVo.put("acct_year", mapVo.get("acct_year_month").toString().substring(0, 4));

				mapVo.put("acct_month", mapVo.get("acct_year_month").toString().substring(4, mapVo.get("acct_year_month").toString().length()));

			}
		}

		String subSchemeConfJson = aphiSubSchemeConfService.updateSubSchemeConf(mapVo);

		return JSONObject.parseObject(subSchemeConfJson);
	}
	
	// 分配方案查询页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/hpmSubSchemeConfForEmpMainPage", method = RequestMethod.GET)
	public String hpmSubSchemeConfForEmpMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmsubschemeconf/hpmSubSchemeConfForEmpMain";

	}
	
	// 分配方案查询
	@RequestMapping(value = "/hrp/hpm/hpmsubschemeconf/queryHpmSubSchemeConfForEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmSubSchemeConfForEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		if (mapVo.get("acct_year_month") != null && !"".equals(mapVo.get("acct_year_month"))) {
			mapVo.put("acct_year", mapVo.get("acct_year_month").toString().substring(0, 4));
			mapVo.put("acct_month", mapVo.get("acct_year_month").toString().substring(4, mapVo.get("acct_year_month").toString().length()));
		}

		String subSchemeConfJson = aphiSubSchemeConfService.queryHpmSubSchemeConfForEmp(getPage(mapVo));

		return JSONObject.parseObject(subSchemeConfJson);

	}
}
