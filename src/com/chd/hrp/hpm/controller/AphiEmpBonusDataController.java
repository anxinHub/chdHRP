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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.service.AphiEmpBonusDataService;
import com.chd.hrp.hpm.service.AphiEmpService;

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
public class AphiEmpBonusDataController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiEmpBonusDataController.class);

	@Resource(name = "aphiEmpBonusDataService")
	private final AphiEmpBonusDataService aphiEmpBonusDataService = null;

	@Resource(name = "aphiEmpService")
	private final AphiEmpService aphiEmpService = null;

	// 核算页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/hpmEmpBonusDataForCollectMainPage", method = RequestMethod.GET)
	public String hpmEmpBonusDataForCollectMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempbonusdata/hpmEmpBonusDataForCollectMain";

	}
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/hpmEmpBonusDataForReportImportPage", method = RequestMethod.GET)
	public String hpmEmpBonusDataForReportImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempbonusdata/hpmEmpBonusDataForReportImport";

	}
	

	// 上报页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/hpmEmpBonusDataForReportMainPage", method = RequestMethod.GET)
	public String hpmEmpBonusDataForReportMainPage(Model mode) throws Exception {

		if(!SessionManager.existsUserPerm("grantHpmEmpBonusDataForReport")){
			
			mode.addAttribute("is_grant_show", true);
			
		}else{
			mode.addAttribute("is_grant_show", false);
		}
		
		if(!SessionManager.existsUserPerm("auditHpmEmpBonusDataForReport")){
			
			mode.addAttribute("is_audit_show", true);
			
		}else{
			
			mode.addAttribute("is_audit_show", false);
		}
		
		if(!SessionManager.existsUserPerm("twoAuditHpmEmpBonusDataForReport")){
			
			mode.addAttribute("is_two_audit_show", true);
			
		}else{
			
			mode.addAttribute("is_two_audit_show", false);
		}
		
		if(!SessionManager.existsUserPerm("addHpmEmpBonusDataForReport")){
			
			mode.addAttribute("is_add_show", true);
			
		}else{
			
			mode.addAttribute("is_add_show", false);
		}

		if(!SessionManager.existsUserPerm("deleteHpmEmpBonusDataForReport")){
			
			mode.addAttribute("is_del_show", true);
			
		}else{
			
			mode.addAttribute("is_del_show", false);
		}
		
		if(!SessionManager.existsUserPerm("createHpmEmpBonusDataForReport")){
			
			mode.addAttribute("is_create_show", true);
			
		}else{
			
			mode.addAttribute("is_create_show", false);
		}
		
		if(!SessionManager.existsUserPerm("importHpmEmpBonusDataForReport")){
			
			mode.addAttribute("is_import_show", true);
			
		}else{
			
			mode.addAttribute("is_import_show", false);
		}

		return "hrp/hpm/hpmempbonusdata/hpmEmpBonusDataForReportMain";

	}

	// 二次分配查询页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/hpmEmpBonusDataMainPage", method = RequestMethod.GET)
	public String hpmEmpBonusDataForTargetMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempbonusdata/hpmEmpBonusDataMain";

	}
	

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/hpmEmpBonusDataAddPage", method = RequestMethod.GET)
	public String hpmEmpBonusDataAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("acct_yearm", mapVo.get("acct_yearm"));

		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		
		mode.addAttribute("item_code", mapVo.get("item_code"));

		return "hrp/hpm/hpmempbonusdata/hpmEmpBonusDataAddPage";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/addHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		String str = aphiEmpBonusDataService.addEmpBonusData(mapVo);

		return JSONObject.parseObject(str);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/queryHpmEmpBonusDataForCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpBonusDataForCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		String empBonusData = aphiEmpBonusDataService.queryEmpBonusData(getPage(mapVo));

		return JSONObject.parseObject(empBonusData);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/queryHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		String empBonusData = aphiEmpBonusDataService.queryHpmEmpBonusDataForReport(getPage(mapVo));

		return JSONObject.parseObject(empBonusData);

	}

	// 二次分配核算生成
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/createHpmEmpBonusDataForCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmEmpBonusDataForCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		String createEmpBonusDataJson = aphiEmpBonusDataService.createEmpBonusData(mapVo);

		return JSONObject.parseObject(createEmpBonusDataJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/initEmpBonusData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initEmpBonusData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		String createEmpBonusDataJson = aphiEmpBonusDataService.initEmpBonusData(mapVo);

		return JSONObject.parseObject(createEmpBonusDataJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/createHpmEmpBonusDataForReportPage", method = RequestMethod.GET)
	public String createHpmEmpBonusDataForReportPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		String[] paraArray = paras.split("@");

		mode.addAttribute("acct_yearm", paraArray[0]);

//		if (!"null".equals(paraArray[1])) {
//
//			mode.addAttribute("dept_id", paraArray[1]);
//
//		}
		
		mode.addAttribute("dept_id_no", paraArray[1]);
		
		//选择项目时才走此方法
		if(paraArray.length > 2){
			
			mode.addAttribute("item_code", paraArray[2]);
			
		}
		
		
		return "hrp/hpm/hpmempbonusdata/hpmEmpBonusDataForReportByCreate";

	}

	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/createHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001")); 
		}
		
		String createEmpBonusDataJson = aphiEmpBonusDataService.createHpmEmpBonusDataForReport(mapVo);

		return JSONObject.parseObject(createEmpBonusDataJson);

	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/collectHpmEmpBonusDataForCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHpmEmpBonusDataForCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();
		String USER_CODE = SessionManager.getUserCode();

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
		mapVo.put("user_code", USER_CODE);

		String calculationEmpBonusData = aphiEmpBonusDataService.calculationEmpBonusData(mapVo);

		return JSONObject.parseObject(calculationEmpBonusData);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/hpmEmpBonusDataUpdatePage", method = RequestMethod.GET)
	public String hpmEmpBonusDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("acct_year", mapVo.get("acct_year"));

		mode.addAttribute("acct_month", mapVo.get("acct_month"));

		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		mode.addAttribute("emp_no", mapVo.get("emp_no"));
		mode.addAttribute("emp_name", mapVo.get("emp_name"));

		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_name", mapVo.get("dept_name"));

		mode.addAttribute("bonus_money", mapVo.get("bonus_money"));

		return "hrp/hpm/hpmempbonusdata/hpmEmpBonusDataUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/updateHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		String empBonusDataJson = aphiEmpBonusDataService.updateEmpBonusData(mapVo);

		return JSONObject.parseObject(empBonusDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/getEmpBonusDataValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getEmpBonusDataValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		String targetValueJson = aphiEmpBonusDataService.getEmpBonusDataValue(mapVo);

		return JSONObject.parseObject(targetValueJson);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/deleteHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmEmpBonusDataForReport(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("acct_year", id.split(";")[0]);
			mapVo.put("acct_month", id.split(";")[1]);
			mapVo.put("emp_id", id.split(";")[2]);
			mapVo.put("emp_no", id.split(";")[3]);
			mapVo.put("dept_id", id.split(";")[4]);
			mapVo.put("dept_no", id.split(";")[5]);
				if(!"0".equals(id.split(";")[6])){
					
					mapVo.put("item_code", id.split(";")[6]);
					
				}
				
		
			listVo.add(mapVo);

		}

		String empSchemeJson = aphiEmpBonusDataService.deleteHpmEmpBonusDataForReport(listVo);

		return JSONObject.parseObject(empSchemeJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/grantHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> grantHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}
		

		String targetValueJson = aphiEmpBonusDataService.grantHpmEmpBonusDataForReport(mapVo);

		return JSONObject.parseObject(targetValueJson);

	}

	/**
	 * @Description 导入跳转页面 8801
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/hpmEmpBonusDataImportPage", method = RequestMethod.GET)
	public String hpmEmpBonusDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempbonusdata/hpmEmpBonusDataImport";

	}
	
	//审核
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/auditHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		String aphiEmpBonusDataJson = aphiEmpBonusDataService.auditHpmEmpBonusDataForReport(mapVo);

		return JSONObject.parseObject(aphiEmpBonusDataJson);
	}
	
	//消审
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/reAuditHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String COPY_CODE = SessionManager.getCopyCode();
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		mapVo.put("copy_code", COPY_CODE);
		
		String aphiEmpBonusDataJson = aphiEmpBonusDataService.auditHpmEmpBonusDataForReport(mapVo);
		
		return JSONObject.parseObject(aphiEmpBonusDataJson);
	}
	
	// 审核
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/twoAuditHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> twoAuditHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		mapVo.put("copy_code", COPY_CODE);
		
		String aphiEmpBonusDataJson = aphiEmpBonusDataService.twoAuditHpmEmpBonusDataForReport(mapVo);
		
		return JSONObject.parseObject(aphiEmpBonusDataJson);
	}
	
	// 消审
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/reTwoAuditHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reTwoAuditHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String COPY_CODE = SessionManager.getCopyCode();
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		mapVo.put("copy_code", COPY_CODE);
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		String aphiEmpBonusDataJson = aphiEmpBonusDataService.twoAuditHpmEmpBonusDataForReport(mapVo);
		
		return JSONObject.parseObject(aphiEmpBonusDataJson);
	}

	// 二次分配查询
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/queryHpmEmpBonusData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpBonusData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}


		String empBonusData = aphiEmpBonusDataService.queryEmpBonusData(getPage(mapVo));

		return JSONObject.parseObject(empBonusData);

	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/downEmpBonusDataTemplateForReport", method = RequestMethod.GET)
	public String downEmpBonusDataTemplateForReport(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "二次分配上报.xlsx");
		return null;
	}

	/**
	 * @Description 导入数据 8801 职工字典表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/importHpmEmpBonusDataForReport", method = RequestMethod.POST)
	@ResponseBody
	public String importHpmEmpBonusDataForReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String reJson = null;

		try {
			
			reJson = aphiEmpBonusDataService.importHpmEmpBonusDataForReport(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			reJson = e.getMessage();
			
		}
		return reJson;
	}
	
	
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/queryHpmEmpBonusDataForReportGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmEmpBonusDataForReportGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

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

		String resultJson = aphiEmpBonusDataService.queryHpmEmpBonusDataForReportGrid(mapVo);

		return resultJson;
	}
	
	//二次分配查询:显示当前科室发放总金额
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/queryHpmEmpBonusDataDeptGrantSumMoney", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmEmpBonusDataDeptGrantSumMoney(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

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


		String resultJson = aphiEmpBonusDataService.queryHpmEmpBonusDataDeptGrantSumMoney(mapVo);

		return resultJson;
	}
	
	// 二次分配查询:科室状态查看 页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/hpmEmpBonusDataDeptStatePage", method = RequestMethod.GET)
	public String hpmEmpBonusDataDeptStatePage(Model mode) throws Exception {

		return "hrp/hpm/hpmempbonusdata/hpmEmpBonusDataDeptState";

	}
		
	// 二次分配查询:科室状态查看 查询
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/queryHpmEmpBonusDataDeptState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpBonusDataDeptState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


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
		
		if(mapVo.get("acct_yearm") != null){
			String acct_yearm = String.valueOf(mapVo.get("acct_yearm"));
			mapVo.put("acct_year", acct_yearm.substring(0, 4));
			mapVo.put("acct_month", acct_yearm.substring(4, 6));
		}


		String empBonusData = aphiEmpBonusDataService.queryAphiEmpBonusDataDeptState(getPage(mapVo));

		return JSONObject.parseObject(empBonusData);

	}	
	
	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmempbonusdata/querydataAuditaBonus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querydataAuditaBonus(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String deptBonusAuditJson = aphiEmpBonusDataService.querydataAuditaBonus(mapVo);

		return JSONObject.parseObject(deptBonusAuditJson);
	}
	
}
