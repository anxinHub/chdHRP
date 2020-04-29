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
import com.chd.hrp.hpm.entity.AphiEmpBonusAudit;
import com.chd.hrp.hpm.service.AphiEmpBonusAuditService;

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
public class AphiEmpBonusAuditController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiEmpBonusAuditController.class);

	@Resource(name = "aphiEmpBonusAuditService")
	private final AphiEmpBonusAuditService aphiEmpBonusAuditService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hpm/empbonusaudit/empBonusAuditMainPage", method = RequestMethod.GET)
	public String empBonusAuditMainPage(Model mode) throws Exception {

		return "hpm/empbonusaudit/empBonusAuditMain";

	}

	// 添加页面
	@RequestMapping(value = "/hpm/empbonusaudit/empBonusAuditAddPage", method = RequestMethod.GET)
	public String empBonusAuditAddPage(Model mode) throws Exception {

		return "hpm/empbonusaudit/empBonusAuditAdd";

	}

	// 保存
	@RequestMapping(value = "/hpm/empbonusaudit/addEmpBonusAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addEmpBonusAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String empBonusAuditJson = aphiEmpBonusAuditService.addEmpBonusAudit(mapVo);

		return JSONObject.parseObject(empBonusAuditJson);

	}

	// 查询
	@RequestMapping(value = "/hpm/empbonusaudit/queryEmpBonusAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpBonusAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String empBonusAudit = aphiEmpBonusAuditService.queryEmpBonusAudit(getPage(mapVo));

		return JSONObject.parseObject(empBonusAudit);

	}

	// 删除
	@RequestMapping(value = "/hpm/empbonusaudit/deleteEmpBonusAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEmpBonusAudit(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		//String empBonusAuditJson = aphiEmpBonusAuditService.deleteEmpBonusAudit(listVo);

		/*
		 * String bonusItemDictJson =
		 * bonusItemDictService.deleteBonusItemDictById(ParamVo);
		 */
		return JSONObject.parseObject("");

	}

	// 修改页面跳转
	@RequestMapping(value = "/hpm/empbonusaudit/empBonusAuditUpdatePage", method = RequestMethod.GET)
	public String empBonusAuditUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AphiEmpBonusAudit empBonusAudit = new AphiEmpBonusAudit();

		empBonusAudit = aphiEmpBonusAuditService.queryEmpBonusAuditByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", empBonusAudit.getCopy_code());

		mode.addAttribute("acct_year", empBonusAudit.getAcct_year());

		mode.addAttribute("acct_month", empBonusAudit.getAcct_month());

		mode.addAttribute("is_audit", empBonusAudit.getIs_audit());

		mode.addAttribute("is_grant", empBonusAudit.getIs_grant());

		return "hpm/empbonusaudit/empBonusAuditUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hpm/empbonusaudit/updateEmpBonusAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEmpBonusAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String empBonusAuditJson = aphiEmpBonusAuditService.updateEmpBonusAudit(mapVo);

		return JSONObject.parseObject(empBonusAuditJson);
	}

}
