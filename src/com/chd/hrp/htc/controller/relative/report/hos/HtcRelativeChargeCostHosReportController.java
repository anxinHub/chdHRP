package com.chd.hrp.htc.controller.relative.report.hos;

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
import com.chd.hrp.htc.service.relative.report.hos.HtcRelativeChargeCostHosReportService;
 
@Controller
public class HtcRelativeChargeCostHosReportController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcRelativeChargeCostHosReportController.class);
	
	@Resource(name = "htcRelativeChargeCostHosReportService")
	private final HtcRelativeChargeCostHosReportService htcRelativeChargeCostHosReportService = null;
	
	 // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/relative/report/hos/htcRelativeChargeCostHosReportMainPage", method = RequestMethod.GET)
	public String htcRelativeChargeCostHosReportMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/report/hos/htcRelativeChargeCostHosReportMain";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/relative/report/hos/queryHtcRelativeChargeCostHosReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeChargeCostHosReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = htcRelativeChargeCostHosReportService.queryHtcRelativeChargeCostHosReport(getPage(mapVo));

		return JSONObject.parseObject(json);

	}
	
	
		
}
