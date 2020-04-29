package com.chd.hrp.htc.controller.relative.report.dept;

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
import com.chd.hrp.htc.service.relative.report.dept.HtcRelativeChargeCostDeptReportService;
 
@Controller
public class HtcRelativeChargeCostDeptReportController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcRelativeChargeCostDeptReportController.class);
	
	@Resource(name = "htcRelativeChargeCostDeptReportService")
	private final HtcRelativeChargeCostDeptReportService htcRelativeChargeCostDeptReportService = null;
	
	 // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/relative/report/dept/htcRelativeChargeCostDeptReportMainPage", method = RequestMethod.GET)
	public String htcRelativeChargeCostDeptReportMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/report/dept/htcRelativeChargeCostDeptReportMain";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/relative/report/dept/queryHtcRelativeChargeCostDeptReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeChargeCostDeptReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = htcRelativeChargeCostDeptReportService.queryHtcRelativeChargeCostDeptReport(getPage(mapVo));

		return JSONObject.parseObject(json);

	}
	
	
		
}
