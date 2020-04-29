package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.dao.AphiDeptBonusAuditMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusAudit;
import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.AphiDeptBonusAuditService;

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
public class AphiDeptBonusAuditController extends BaseController {
 
	private static Logger logger = Logger.getLogger(AphiDeptBonusAuditController.class);

	@Resource(name = "aphiDeptBonusAuditService")
	private final AphiDeptBonusAuditService aphiDeptBonusAuditService = null;

	@Resource(name = "aphiDeptBonusAuditMapper")
	private final AphiDeptBonusAuditMapper aphiDeptBonusAuditMapper = null;
	
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/hpmDeptBonusAuditMainPage", method = RequestMethod.GET)
	public String deptBonusAuditMainPage(Model mode) throws Exception {
		
		if(!SessionManager.existsUserPerm("importHpmDeptBonusAudit")){
			mode.addAttribute("import_hide", true);
		}else{
			mode.addAttribute("import_hide", false);
		}
		
		return "hrp/hpm/hpmdeptbonusaudit/hpmDeptBonusAuditMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/hpmDeptBonusauditAddPage", method = RequestMethod.GET)
	public String hpmDeptBonusauditAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptbonusaudit/hpmDeptBonusauditAdd";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/queryHpmDeptBonusAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptBonusAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();
		
		String USER_ID = SessionManager.getUserId(); 

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		mapVo.put("user_id", USER_ID);

		if (mapVo.containsKey("acct_yearm")) {

			if (mapVo.get("acct_yearm") != null && !"".equals(mapVo.get("acct_yearm"))) {

				mapVo.put("acct_year", mapVo.get("acct_yearm").toString().substring(0, 4));

				mapVo.put("acct_month", mapVo.get("acct_yearm").toString().substring(4, 6));
			}
		}

		String deptBonusAudit = aphiDeptBonusAuditService.queryHpmDeptBonusAudit(getPage(mapVo));

		return JSONObject.parseObject(deptBonusAudit);

	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/updateHpmDeptBonusAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmDeptBonusAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		mapVo.put("user_id", SessionManager.getUserId());
		

		String deptBonusAuditJson = aphiDeptBonusAuditService.updateHpmDeptBonusAudit(mapVo);

		return JSONObject.parseObject(deptBonusAuditJson);
	}
	
	
	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/querydataAudita", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querydataAudita(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String deptBonusAuditJson = aphiDeptBonusAuditService.querydataAudita(mapVo);

		return JSONObject.parseObject(deptBonusAuditJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/shenhe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> shenhe(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		//String res = aphiDeptBonusAuditService.shenhe(mapVo);

		return JSONObject.parseObject("");
	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/queryHpmDeptBonusAuditGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmDeptBonusAuditGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();
		
		String USER_ID = SessionManager.getUserId(); 

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		mapVo.put("user_id", USER_ID);

		String resultJson = aphiDeptBonusAuditService.queryDeptBounsAuditGrid(mapVo);

		return resultJson;
	}
	
	// 导入页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/hpmDeptBonusAuditImportPage", method = RequestMethod.GET)
	public String hpmDeptBonusAuditImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("dept_kind_code") != null && !"".equals(mapVo.get("dept_kind_code"))){
			mode.addAttribute("dept_kind_code", mapVo.get("dept_kind_code"));
		}
		return "hrp/hpm/hpmdeptbonusaudit/hpmDeptBonusAuditImport";
	}
	
	//导入
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/importHpmDeptBonusAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> importHpmDeptBonusAudit(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {


		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String importJson = null;

		try {
			
			importJson = aphiDeptBonusAuditService.importHpmDeptBonusAudit(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			importJson = e.getMessage();
			
		}

		return JSONObject.parseObject(importJson);
	}
	
	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/downHpmDeptBonusAudit", method = RequestMethod.GET)
	public String downHpmDeptBonusAudit(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "绩效工资审核.xlsx");
		return null;
	}
	
	
	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/saveDeptBonusauditAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savedeptbonusauditAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.containsKey("acct_yearm")) {

			if (mapVo.get("acct_yearm") != null && !"".equals(mapVo.get("acct_yearm"))) {

				mapVo.put("acct_year", mapVo.get("acct_yearm").toString().substring(0, 4));

				mapVo.put("acct_month", mapVo.get("acct_yearm").toString().substring(4, 6));
			}
		}
		
		mapVo.put("is_audit", 0);
		
		String resultJson = aphiDeptBonusAuditService.savedeptbonusauditAdd(mapVo);

		return JSONObject.parseObject(resultJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/deleteHpmDeptItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDeptItem(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : checkIds.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			String[] ids = id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("dept_id", ids[0]);
			mapVo.put("dept_no", ids[1]);
			mapVo.put("acct_year", ids[2]);
			mapVo.put("acct_month", ids[3]);
			if(ids.length != 4){
				mapVo.put("item_code",ids[4] );
			}	
			listVo.add(mapVo);
		
		}

		String itemJson = aphiDeptBonusAuditService.deleteHpmDeptItem(listVo);

		return JSONObject.parseObject(itemJson);
	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbonusaudit/updateHpmDeptItemPage", method = RequestMethod.GET)
	public String updateHpmDeptItemPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String[] dept_ids = mapVo.get("dept_id").toString().split("@");
	
		String dept_id = dept_ids[1];
		
		String acct_year =dept_ids[0].substring(0,4); 
		
		String acct_month = dept_ids[0].substring(4,6);
		
		String item_code = mapVo.get("item_code").toString();
		
		mapVo.put("dept_id", dept_id);
		mapVo.put("acct_year", acct_year);
		mapVo.put("acct_month", acct_month);
		mapVo.put("item_code", item_code);

		AphiDeptBonusData aphiDeptBonusAudit= aphiDeptBonusAuditService.queryDeptName(mapVo);
		
		if (aphiDeptBonusAudit != null) {
			
			mode.addAttribute("group_id",aphiDeptBonusAudit.getGroup_id());
			mode.addAttribute("hos_id",aphiDeptBonusAudit.getHos_id());
			mode.addAttribute("copy_code",aphiDeptBonusAudit.getCopy_code());
			mode.addAttribute("acct_yearm",dept_ids[0]);
			mode.addAttribute("bonus_money",aphiDeptBonusAudit.getBonus_money());
			mode.addAttribute("dept_code",aphiDeptBonusAudit.getDept_code());
			mode.addAttribute("dept_name",aphiDeptBonusAudit.getDept_name());
			mode.addAttribute("dept_id",aphiDeptBonusAudit.getDept_id());
			mode.addAttribute("dept_no",aphiDeptBonusAudit.getDept_no());
			mode.addAttribute("item_code",aphiDeptBonusAudit.getItem_code());
			mode.addAttribute("item_name",aphiDeptBonusAudit.getItem_name());
			mode.addAttribute("note",aphiDeptBonusAudit.getNote());

		}
		return "/hrp/hpm/hpmdeptbonusaudit/updateHpmDeptItem";
	}
}
