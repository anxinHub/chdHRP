package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.HashMap;
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
import com.chd.hrp.hpm.entity.AphiWorkitemData;
import com.chd.hrp.hpm.service.AphiWorkitemDataService;

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
public class AphiWorkitemDataController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiWorkitemDataController.class);

	@Resource(name = "aphiWorkitemDataService")
	private final AphiWorkitemDataService aphiWorkitemDataService = null;

	// @Resource(name = "aphiDeptService")
	// private final AphiDeptService aphiDeptService = null;
	//
	// @Resource(name = "aphiWorkitemService")
	// private final AphiWorkitemService aphiWorkitemService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/hpmWorkitemDataMainPage", method = RequestMethod.GET)
	public String workitemDataMainPage(Model mode) throws Exception {
		
		//添加权限
		if (!SessionManager.existsUserPerm("addHpmWorkitemData")) {

			mode.addAttribute("is_add_show", true);

		} else {
			mode.addAttribute("is_add_show", false);
		}
		
		//修改权限
		if (!SessionManager.existsUserPerm("updateHpmWorkitemData")) {

			mode.addAttribute("is_update_show", true);

		} else {
			mode.addAttribute("is_update_show", false);
		}
		
		//生成权限
		if (!SessionManager.existsUserPerm("initHpmWorkitemData")) {

			mode.addAttribute("is_init_show", true);

		} else {
			mode.addAttribute("is_init_show", false);
		}
		
		//删除权限
		if (!SessionManager.existsUserPerm("deleteHpmWorkitemData")) {

			mode.addAttribute("is_delete_show", true);

		} else {
			mode.addAttribute("is_delete_show", false);
		}
		
		//导入权限
		if (!SessionManager.existsUserPerm("importHpmWorkitemData")) {

			mode.addAttribute("is_import_show", true);

		} else {
			mode.addAttribute("is_import_show", false);
		}

		return "hrp/hpm/hpmworkitemdata/hpmWorkitemDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/hpmWorkitemDataAddPage", method = RequestMethod.GET)
	public String hpmWorkitemDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmworkitemdata/hpmWorkitemDataAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/addHpmWorkitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmWorkitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);
		}

		String workitemDataJson = aphiWorkitemDataService.addWorkitemData(mapVo);

		return JSONObject.parseObject(workitemDataJson);

	}

	//工作量数据准备 生成
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/initHpmWorkitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmWorkitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


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

		String hospTargetDataJson = aphiWorkitemDataService.createWorkitemData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/workItemDataFastPage", method = RequestMethod.GET)
	public String workItemDataFastPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		String[] paraArray = paras.split("@");

		mode.addAttribute("acct_yearm", paraArray[0]);

		if (!"null".equals(paraArray[1])) {
			mode.addAttribute("dept_id", paraArray[1]);
		}else{
			mode.addAttribute("dept_id", "");
		}
		
		if (!"null".equals(paraArray[2])) {
			mode.addAttribute("dept_no", paraArray[2]);
		}else{
			mode.addAttribute("dept_no", "");
		}
		
		if (!"null".equals(paraArray[3])) {
			mode.addAttribute("work_item_code", paraArray[3]);
		}

		if (!"null".equals(paraArray[4])) {
			mode.addAttribute("dept_kind_code", paraArray[4]);
		}

		return "hrp/hpm/hpmworkitemdata/hpmWorkitemDataFast";

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/queryHpmWorkitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmWorkitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);
		}

		String workitemData = aphiWorkitemDataService.queryWorkitemData(getPage(mapVo));

		return JSONObject.parseObject(workitemData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/deleteHpmWorkitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmWorkitemData(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String workitemDataJson = aphiWorkitemDataService.deleteWorkitemData(mapVo, checkIds);

		return JSONObject.parseObject(workitemDataJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/hpmWorkitemDataUpdatePage", method = RequestMethod.GET)
	public String workitemDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		AphiWorkitemData workitemData = new AphiWorkitemData();

		workitemData = aphiWorkitemDataService.queryWorkitemDataByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", workitemData.getCopy_code());

		mode.addAttribute("acct_year", workitemData.getAcct_year());

		mode.addAttribute("acct_month", workitemData.getAcct_month());

		mode.addAttribute("dept_id", workitemData.getDept_id());
		mode.addAttribute("dept_no", workitemData.getDept_no());
		mode.addAttribute("work_item_code", workitemData.getWork_item_code());

		mode.addAttribute("dept_name", workitemData.getDept_name());

		mode.addAttribute("work_item_name", workitemData.getWork_item_name());

		mode.addAttribute("work_amount", workitemData.getWork_amount());

		mode.addAttribute("work_money", workitemData.getWork_money());

		return "hrp/hpm/hpmworkitemdata/hpmWorkitemDataUpdate";
	}

	// 修改保存
	@RequestMapping(value ="/hrp/hpm/hpmworkitemdata/updateHpmWorkitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmWorkitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		String workitemDataJson = aphiWorkitemDataService.updateWorkitemData(mapVo);

		return JSONObject.parseObject(workitemDataJson);
	}

	//导入
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/hpmWorkitemDataImportPage", method = RequestMethod.GET)
	public String workitemDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmworkitemdata/hpmWorkitemDataImport";

	}
	
	//导入跳转
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/importHpmWorkitemData", method = RequestMethod.POST)
	@ResponseBody
	public String importHpmWorkitemData(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			String impJson = aphiWorkitemDataService.importHpmWorkitemData(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmworkitemdata/downTemplateHpmWorkitemData", method = RequestMethod.GET)
	public String downTemplateWorkitemData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "工作量指标数据准备.xlsx");
		return null;
	}

}
