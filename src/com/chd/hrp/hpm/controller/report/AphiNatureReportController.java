package com.chd.hrp.hpm.controller.report;

import java.util.Map;

import javax.annotation.Resource;
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
import com.chd.hrp.hpm.service.report.AphiNatureReportService;

@Controller
public class AphiNatureReportController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiNatureReportController.class);
	
	@Resource(name = "aphiNatureReportService")
	private final AphiNatureReportService aphiNatureReportService = null;

	@RequestMapping(value = "/hrp/hpm/report/hpmNatureReportMainPage", method = RequestMethod.GET)
	public String hpmNatureReportMainPage(Model model) throws Exception {
		return "hrp/hpm/report/hpmNatureReportMain";
	}
	
	//选择指标页面跳转
	@RequestMapping(value = "/hrp/hpm/report/hpmChoiceTargetMainPage", method = RequestMethod.GET)
	public String hpmChoiceTargetMainPage(Model model) throws Exception {
		return "hrp/hpm/report/hpmChoiceTargetMain";
	}
	
	//选择指标页面跳转
	@RequestMapping(value = "/hrp/hpm/report/queryTargetTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryTargetTree(@RequestParam Map<String,Object> mapVo,Model model,HttpServletResponse response) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String natureReportJson = aphiNatureReportService.queryTargetTree(getPage(mapVo));
		
		response.getWriter().print(natureReportJson);
		return null;
	}
	
	//查询
	@RequestMapping(value = "/hrp/hpm/report/queryHpmNatureReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryHpmNatureReport(@RequestParam Map<String,Object> mapVo,Model model) throws Exception {
			
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String natureReportJson = aphiNatureReportService.queryHpmNatureReport(mapVo);
			
		return JSONObject.parseObject(natureReportJson);
	}
}
