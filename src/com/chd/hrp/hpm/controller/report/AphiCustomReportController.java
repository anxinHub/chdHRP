package com.chd.hrp.hpm.controller.report;

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
import com.chd.hrp.hpm.service.report.AphiCustomReportService;

@Controller
public class AphiCustomReportController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(AphiCustomReportController.class);
	
	@Resource(name = "aphiCustomReportService")
	private final AphiCustomReportService aphiCustomReportService = null;
	
	//主页面跳转
	@RequestMapping(value = "/hrp/hpm/report/hpmCustomReportMainPage", method = RequestMethod.GET)
	public String hpmCustomReportMainPage(Model model) throws Exception {
		
		model.addAttribute("is_show_print", "1");
		
		return "hrp/hpm/report/hpmCustomReportMain";
	}
	
	//查询表头
	@RequestMapping(value = "/hrp/hpm/report/queryHpmCustomReportHead", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmCustomReportHead(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {


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

		String resultJson = aphiCustomReportService.queryHpmCustomReportHead(mapVo);

		return resultJson;

	}

	// 主查询
	@RequestMapping(value = "/hrp/hpm/report/queryHpmCustomReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmCustomReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		/*if (!"".equals(mapVo.get("year_month")) && mapVo.get("year_month") != null) {

			String acct_year = mapVo.get("year_month").toString().substring(0, 4);

			String acct_month = mapVo.get("year_month").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}*/
		String aphiCustomReportJson = aphiCustomReportService.queryHpmCustomReport(getPage(mapVo));

		return JSONObject.parseObject(aphiCustomReportJson);

	}
	
	
	
	
	
	
	
	
	
	
	/*
	
	//选择指标页面跳转
	@RequestMapping(value = "/hpm/report/hpmCustomChoiceTargetMainPage", method = RequestMethod.GET)
	public String hpmCustomChoiceTargetMainPage(Model model) throws Exception {
		return "hpm/report/hpmCustomChoiceTargetMain";
	}
	
	//选择指标页面查询
	@RequestMapping(value = "/hpm/report/queryCustomTargetTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryCustomTargetTree(@RequestParam Map<String,Object> mapVo,Model model,HttpServletResponse response) throws Exception {
			
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String customTargetTreeJson = aphiCustomReportService.queryCustomTargetTree(getPage(mapVo));
			
		response.getWriter().print(customTargetTreeJson);
		return null;
	}
	
	//选择科室页面跳转
	@RequestMapping(value = "/hpm/report/hpmChoiceDeptMainPage", method = RequestMethod.GET)
	public String hpmChoiceDeptMainPage(Model model) throws Exception {
		return "hpm/report/hpmChoiceDeptMain";
	}
	
	//选择科室页面查询
	@RequestMapping(value = "/hpm/report/queryCustomDeptTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryCustomDeptTree(@RequestParam Map<String,Object> mapVo,Model model,HttpServletResponse response) throws Exception {
				
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
				
		String customReportJson = aphiCustomReportService.queryCustomDeptTree(getPage(mapVo));
				
		response.getWriter().print(customReportJson);
		return null;
	}
	
	//查询
	@RequestMapping(value = "/hpm/report/queryHpmCustomReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryHpmCustomReport(@RequestParam Map<String,Object> mapVo,Model model) throws Exception {
				
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
				
		String hpmCustomReportJson = aphiCustomReportService.queryHpmCustomReport(mapVo);
				
		return JSONObject.parseObject(hpmCustomReportJson);
	}*/
}
