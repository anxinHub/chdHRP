package com.chd.hrp.htc.controller.task.report.hos;

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
import com.chd.hrp.htc.service.task.report.hos.HtcTaskChargeCostHosReportService;

@Controller
public class HtcTaskChargeCostHosReportController extends BaseController{

	@Resource(name = "htcTaskChargeCostHosReportService")
	private final HtcTaskChargeCostHosReportService htcTaskChargeCostHosReportService = null;

	 // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/report/hos/htcTaskChargeCostHosReportMainPage", method = RequestMethod.GET)
	public String htcTaskChargeCostHosReportMainPage(Model mode) throws Exception {
		return "hrp/htc/task/report/hos/htcTaskChargeCostHosReportMain";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/report/hos/queryHtcTaskChargeCostHosReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskChargeCostHosReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = htcTaskChargeCostHosReportService.queryHtcTaskChargeCostHosReport(getPage(mapVo));

		return JSONObject.parseObject(json);

	}
}
