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
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.entity.AphiDeptBonusGrantItem;
import com.chd.hrp.hpm.service.AphiDeptBonusAuditService;
import com.chd.hrp.hpm.service.AphiDeptBonusGrantItemService;

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
public class AphiDeptBonusGrantItemController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptBonusGrantItemController.class);

	@Resource(name = "aphiDeptBonusGrantItemService")
	private final AphiDeptBonusGrantItemService aphiDeptBonusGrantItemService = null;

	@Resource(name = "aphiDeptBonusAuditService")
	private final AphiDeptBonusAuditService aphiDeptBonusAuditService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/hpmDeptBonusGrantItemMainPage", method = RequestMethod.GET)
	public String deptBonusGrantItemMainPage(Model mode) throws Exception {

		if (!SessionManager.existsUserPerm("grantHpmDeptBonusGrantItem")) {

			mode.addAttribute("is_grant_show", true);

		} else {
			mode.addAttribute("is_grant_show", false);
		}

		if (!SessionManager.existsUserPerm("auditHpmDeptBonusGrantItem")) {

			mode.addAttribute("is_audit_show", true);

		} else {

			mode.addAttribute("is_audit_show", false);
		}

		if (!SessionManager.existsUserPerm("createHpmDeptBonusGrantItem")) {

			mode.addAttribute("is_create_show", true);

		} else {

			mode.addAttribute("is_create_show", false);
		}
		
		if (SessionManager.existsUserPerm("updateHpmDeptBonusGrantItem")) {

			mode.addAttribute("is_update", true);

		} else {

			mode.addAttribute("is_update", false);
		}

		return "hrp/hpm/hpmdeptbonusgrantitem/hpmDeptBonusGrantItemMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/hpmDeptBonusGrantItemAddPage", method = RequestMethod.GET)
	public String deptBonusGrantItemAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptbonusgrantitem/hpmDeptBonusGrantItemAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/addHpmDeptBonusGrantItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeptBonusGrantItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		String COPY_CODE = SessionManager.getCopyCode();

		String ACCT_YEAR = SessionManager.getAcctYear();

		mapVo.put("copy_code", COPY_CODE);

		mapVo.put("acct_year", ACCT_YEAR);

		String deptBonusGrantItemJson = aphiDeptBonusGrantItemService.addDeptBonusGrantItem(mapVo);

		return JSONObject.parseObject(deptBonusGrantItemJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/queryHpmDeptBonusGrantItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptBonusGrantItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String deptBonusGrantItem = aphiDeptBonusGrantItemService.queryDeptBonusGrantItem(getPage(mapVo));

		return JSONObject.parseObject(deptBonusGrantItem);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/queryHpmDeptBonusGrantItemByPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptBonusGrantItemByPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String deptBonusGrantItem = aphiDeptBonusGrantItemService.queryDeptBonusGrantItem(getPage(mapVo));

		return JSONObject.parseObject(deptBonusGrantItem);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/deleteHpmDeptBonusGrantItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptBonusGrantItem(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		String ACCT_YEAR = SessionManager.getAcctYear();

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

			mapVo.put("acct_year", ACCT_YEAR);

			mapVo.put("temp", id);// 实际实体类变量

			listVo.add(mapVo);

		}
		String deptBonusGrantItemJson = aphiDeptBonusGrantItemService.deleteDeptBonusGrantItem(listVo);

		/*
		 * String bonusItemDictJson =
		 * bonusItemDictService.deleteBonusItemDictById(ParamVo);
		 */
		return JSONObject.parseObject(deptBonusGrantItemJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/hpmDeptBonusGrantItemUpdatePage", method = RequestMethod.GET)
	public String deptBonusGrantItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		String ACCT_YEAR = SessionManager.getAcctYear();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		mapVo.put("acct_year", ACCT_YEAR);

		AphiDeptBonusGrantItem deptBonusGrantItem = new AphiDeptBonusGrantItem();

		deptBonusGrantItem = aphiDeptBonusGrantItemService.queryDeptBonusGrantItemByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", deptBonusGrantItem.getCopy_code());

		mode.addAttribute("acct_year", deptBonusGrantItem.getAcct_year());

		mode.addAttribute("acct_month", deptBonusGrantItem.getAcct_month());

		mode.addAttribute("dept_id", deptBonusGrantItem.getDept_id());

		mode.addAttribute("bonus_money", deptBonusGrantItem.getBonus_money());

		mode.addAttribute("activity_money", deptBonusGrantItem.getActivity_money());

		mode.addAttribute("activity_percent", deptBonusGrantItem.getActivity_percent());

		mode.addAttribute("grant_money", deptBonusGrantItem.getGrant_money());

		return "hrp/hpm/hpmdeptbonusgrantitem/hpmDeptBonusGrantItemUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/updateHpmDeptBonusGrantItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeptBonusGrantItem(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

			mapVo.put("acct_year", id.split("#")[0]);

			mapVo.put("acct_month", id.split("#")[1]);

			mapVo.put("dept_id", id.split("#")[2]);

			mapVo.put("activity_money", id.split("#")[3]);

			mapVo.put("activity_percent", id.split("#")[4]);

			mapVo.put("grant_money", id.split("#")[5]);

			mapVo.put("bonus_money", id.split("#")[6]);

			listVo.add(mapVo);

		}

		String deptBonusGrantItemJson = aphiDeptBonusGrantItemService.updateDeptBonusGrantItem(listVo);

		return JSONObject.parseObject(deptBonusGrantItemJson);
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/grantHpmDeptBonusGrantItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> grantHpmDeptBonusGrantItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String json = aphiDeptBonusGrantItemService.grantHpmDeptBonusGrantItem(mapVo);

		return JSONObject.parseObject(json);
	}

	// 审核
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/auditHpmDeptBonusGrantItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHpmDeptBonusGrantItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String json = aphiDeptBonusGrantItemService.auditHpmDeptBonusGrantItem(mapVo);

		return JSONObject.parseObject(json);
	}
	
	// 消审
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/reAuditHpmDeptBonusGrantItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditHpmDeptBonusGrantItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String json = aphiDeptBonusGrantItemService.auditHpmDeptBonusGrantItem(mapVo);
		
		return JSONObject.parseObject(json);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrantitem/createHpmDeptBonusGrantItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmDeptBonusGrantItem(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String json = aphiDeptBonusGrantItemService.createHpmDeptBonusGrantItem(mapVo);

		return JSONObject.parseObject(json);
	}

	// @RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/insert", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> insert(@RequestParam Map<String, Object>
	// mapVo, Model model) throws Exception {
	//
	// String COPY_CODE = SessionManager.getCopyCode();
	//
	// if (mapVo.get("group_id") == null) {
	//
	// mapVo.put("group_id", SessionManager.getGroupId());
	// }
	//
	// if (mapVo.get("hos_id") == null) {
	//
	// mapVo.put("hos_id", SessionManager.getHosId());
	// }
	//
	// mapVo.put("copy_code", COPY_CODE);
	//
	// String deptBonusGrantItemJson = aphiDeptBonusAuditService.insert(mapVo);
	//
	// return JSONObject.parseObject(deptBonusGrantItemJson);
	//
	// }

	// @RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/noInsert", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> noInsert(@RequestParam Map<String, Object>
	// mapVo, Model model) throws Exception {
	//
	// if (mapVo.get("group_id") == null) {
	//
	// mapVo.put("group_id", SessionManager.getGroupId());
	// }
	//
	// if (mapVo.get("hos_id") == null) {
	//
	// mapVo.put("hos_id", SessionManager.getHosId());
	// }
	//
	// String COPY_CODE = SessionManager.getCopyCode();
	//
	// mapVo.put("copy_code", COPY_CODE);
	//
	// String deptBonusGrantItemJson = aphiDeptBonusAuditService.noInsert(mapVo);
	//
	// return JSONObject.parseObject(deptBonusGrantItemJson);
	// }
	//

}
