package com.chd.hrp.hpm.controller.report;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.service.report.AphiCustomReportService;

@Controller
public class AphiDeptTargetCkeckReportController extends BaseController {
	
	
	@Resource(name = "aphiCustomReportService")
	private final AphiCustomReportService aphiCustomReportService = null;
	
	// 查询表头 
	// 职能科室考核明细表 和 财务其它考核明细表 共用此查询
	@RequestMapping(value = "/hrp/hpm/report/queryDeptTargetCkeckReportHead", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptTargetCkeckReportHead(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {


		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
			
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
			
		if("QT".equals(mapVo.get("select_para"))){
				
			mapVo.put("where_sql", "and adtd.target_code like 'QT%' "
				+ "and adtd.target_code <> 'QT01000' ");
				
		}else if("ZN".equals(mapVo.get("select_para"))){
				
			mapVo.put("where_sql", "and adtd.target_code like 'ZN%' "
					+ "and adtd.target_code <> 'ZN01000' ");
		}
		
		if (!SessionManager.existsUserPerm("isShowZeroHpmDeptTargetCkeckReport")) {

			mapVo.put("is_containsZero", "0");

		} else {
			mapVo.put("is_containsZero", "1");
		}

		String resultJson = aphiCustomReportService.queryHpmCustomReportHead(mapVo);

		return resultJson;

	}
		
	
	// 职能科室考核明细表 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/report/hpmDeptTargetCkeckReportMainPage", method = RequestMethod.GET)
	public String hpmDeptTargetCkeckReportMainPage(Model mode) throws Exception {
			
		return "hrp/hpm/report/hpmDeptTargetCkeckReportMain";

	}
	
	
	//职能科室考核明细表  主查询
	@RequestMapping(value = "/hrp/hpm/report/queryHpmDeptTargetCkeckReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptTargetCkeckReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		if (!"".equals(mapVo.get("year_month")) && mapVo.get("year_month") != null) {

			String acct_year = mapVo.get("year_month").toString().substring(0, 4);

			String acct_month = mapVo.get("year_month").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}
		
		if("ZN".equals(mapVo.get("select_para"))){
			
			mapVo.put("where_sql", " and adtd.target_code like 'ZN%' "
					+ "and adtd.target_code <> 'ZN01000' ");
		}
		
		if (!SessionManager.existsUserPerm("isShowZeroHpmDeptTargetCkeckReport")) {

			mapVo.put("is_containsZero", "0");

		} else {
			mapVo.put("is_containsZero", "1");
		}

		
		String data = aphiCustomReportService.queryHpmCustomReport(getPage(mapVo));

		return JSONObject.parseObject(data);
	}
	
	// 财务其它考核明细表 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/report/hpmOtherTargetCkeckReportMainPage", method = RequestMethod.GET)
	public String hpmOtherTargetCkeckReportMainPage(Model mode) throws Exception {
				
		return "hrp/hpm/report/hpmOtherTargetCkeckReportMain";

	}
	
	//财务其它考核明细表   主查询
	@RequestMapping(value = "/hrp/hpm/report/queryHpmOtherTargetCkeckReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmOtherTargetCkeckReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


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

		if (!"".equals(mapVo.get("year_month")) && mapVo.get("year_month") != null) {

			String acct_year = mapVo.get("year_month").toString().substring(0, 4);

			String acct_month = mapVo.get("year_month").toString().substring(4);
			
			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}
			
		if("QT".equals(mapVo.get("select_para"))){
				
			mapVo.put("where_sql", " and adtd.target_code like 'QT%' "
				+ "and adtd.target_code <> 'QT01000' ");
		}
		
		if (!SessionManager.existsUserPerm("isShowZeroHpmOtherTargetCkeckReport")) {

			mapVo.put("is_containsZero", "0");

		} else {
			mapVo.put("is_containsZero", "1");
		}

			
		String data = aphiCustomReportService.queryHpmCustomReport(getPage(mapVo));

		return JSONObject.parseObject(data);
	}
}
