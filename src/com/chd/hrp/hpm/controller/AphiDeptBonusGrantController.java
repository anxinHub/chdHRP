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
import com.chd.hrp.hpm.entity.AphiDeptBonusGrant;
import com.chd.hrp.hpm.service.AphiDeptBonusAuditService;
import com.chd.hrp.hpm.service.AphiDeptBonusGrantService;

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
public class AphiDeptBonusGrantController extends BaseController { 

	private static Logger logger = Logger.getLogger(AphiDeptBonusGrantController.class);

	@Resource(name = "aphiDeptBonusGrantService")
	private final AphiDeptBonusGrantService aphiDeptBonusGrantService = null;

	@Resource(name = "aphiDeptBonusAuditService")
	private final AphiDeptBonusAuditService aphiDeptBonusAuditService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/hpmDeptBonusGrantMainPage", method = RequestMethod.GET)
	public String deptBonusGrantMainPage(Model mode) throws Exception {

		if (!SessionManager.existsUserPerm("grantHpmDeptBonusGrant")) {

			mode.addAttribute("is_grant_show", true);

		} else {
			mode.addAttribute("is_grant_show", false);
		}

		if (!SessionManager.existsUserPerm("auditHpmDeptBonusGrant")) {

			mode.addAttribute("is_audit_show", true);

		} else {

			mode.addAttribute("is_audit_show", false);
		}

		if (!SessionManager.existsUserPerm("createHpmDeptBonusGrant")) {

			mode.addAttribute("is_create_show", true);

		} else {

			mode.addAttribute("is_create_show", false);
		}
		
		if (SessionManager.existsUserPerm("updateHpmDeptBonusGrant")) {

			mode.addAttribute("is_update", true);

		} else {

			mode.addAttribute("is_update", false);
		}

		return "hrp/hpm/hpmdeptbonusgrant/hpmDeptBonusGrantMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/hpmDeptBonusGrantAddPage", method = RequestMethod.GET)
	public String deptBonusGrantAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptbonusgrant/hpmDeptBonusGrantAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/addHpmDeptBonusGrant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeptBonusGrant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String deptBonusGrantJson = aphiDeptBonusGrantService.addDeptBonusGrant(mapVo);

		return JSONObject.parseObject(deptBonusGrantJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/queryHpmDeptBonusGrant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptBonusGrant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String deptBonusGrant = aphiDeptBonusGrantService.queryDeptBonusGrant(getPage(mapVo));

		return JSONObject.parseObject(deptBonusGrant);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/queryHpmDeptBonusGrantByPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptBonusGrantByPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String deptBonusGrant = aphiDeptBonusGrantService.queryDeptBonusGrant(getPage(mapVo));

		return JSONObject.parseObject(deptBonusGrant);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/deleteHpmDeptBonusGrant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptBonusGrant(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		String deptBonusGrantJson = aphiDeptBonusGrantService.deleteDeptBonusGrant(listVo);

		/*
		 * String bonusItemDictJson =
		 * bonusItemDictService.deleteBonusItemDictById(ParamVo);
		 */
		return JSONObject.parseObject(deptBonusGrantJson);

	}

	

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/updateHpmDeptBonusGrant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeptBonusGrant(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			
			mapVo.put("item_code", id.split("#")[2]);

			mapVo.put("dept_id", id.split("#")[3]);

			mapVo.put("activity_money", id.split("#")[4]);

			mapVo.put("activity_percent", id.split("#")[5]);

			mapVo.put("grant_money", id.split("#")[6]);

			mapVo.put("bonus_money", id.split("#")[7]);
			
			//mapVo.put("is_audit", id.split("#")[7]);

			listVo.add(mapVo);

		}

		String deptBonusGrantJson = aphiDeptBonusGrantService.updateDeptBonusGrant(listVo);

		return JSONObject.parseObject(deptBonusGrantJson);
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/grantHpmDeptBonusGrant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> grantHpmDeptBonusGrant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String json = aphiDeptBonusGrantService.grantHpmDeptBonusGrant(mapVo);

		return JSONObject.parseObject(json);
	}

	// 审核
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/auditHpmDeptBonusGrant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHpmDeptBonusGrant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String json = aphiDeptBonusGrantService.auditHpmDeptBonusGrant(mapVo);

		return JSONObject.parseObject(json);
	}
	
	// 消审
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/reAuditHpmDeptBonusGrant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditHpmDeptBonusGrant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String json = aphiDeptBonusGrantService.auditHpmDeptBonusGrant(mapVo);
		
		return JSONObject.parseObject(json);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusgrant/createHpmDeptBonusGrant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmDeptBonusGrant(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String json = aphiDeptBonusGrantService.createHpmDeptBonusGrant(mapVo);

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
	// String deptBonusGrantJson = aphiDeptBonusAuditService.insert(mapVo);
	//
	// return JSONObject.parseObject(deptBonusGrantJson);
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
	// String deptBonusGrantJson = aphiDeptBonusAuditService.noInsert(mapVo);
	//
	// return JSONObject.parseObject(deptBonusGrantJson);
	// }
	//

}
