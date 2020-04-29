package com.chd.hrp.ass.controller.deptreport;

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
import com.chd.hrp.ass.service.deptreport.AssDeptAccountReportService;

@Controller
public class AssDeptAccountReportController extends BaseController{
	private static Logger logger = Logger.getLogger(AssDeptAccountReportController.class);
 
	// 引入Service服务
	@Resource(name = "assDeptAccountReportService")
	private final AssDeptAccountReportService assDeptAccountReportService = null;
	
	@RequestMapping(value = "/hrp/ass/assdeptreport/assDeptAccountReportPage", method = RequestMethod.GET)
	public String assDeptAccountReportPage(Model mode) throws Exception {  

		mode.addAttribute("ass_year_month",MyConfig.getCurAccYear());
		
		return "hrp/ass/assdeptreport/assDeptAccountReport";

	}
	
	@RequestMapping(value = "/hrp/ass/assdeptreport/assDeptAccountReportDetPage", method = RequestMethod.GET)
	public String assDeptAccountReportDetPage(Model mode,String ass_nature,String dept_id,String year_month) throws Exception {  
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("dept_id", dept_id);
		mode.addAttribute("year_month", year_month);
		return "hrp/ass/assdeptreport/assDeptAccountReportDet";

	}
	
	@RequestMapping(value = "/hrp/ass/assdeptreport/queryAssDeptAccountReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
 
		String assScrapBySummry = assDeptAccountReportService.query(getPage(mapVo));

		return JSONObject.parseObject(assScrapBySummry);
		
	}
	
	@RequestMapping(value = "/hrp/ass/assdeptreport/queryAssDeptAccountReportDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDeptAccountReportDet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
 
		String assScrapBySummry = assDeptAccountReportService.queryDetail(getPage(mapVo));

		return JSONObject.parseObject(assScrapBySummry);
		
	}
}
