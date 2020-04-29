package com.chd.hrp.htc.controller.income.report.hos;

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
import com.chd.hrp.htc.service.income.report.hos.HtcIncomeChargeCostHosReportService;
 
@Controller
public class HtcIncomeChargeCostHosReportController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcIncomeChargeCostHosReportController.class);
	
	@Resource(name = "htcIncomeChargeCostHosReportService")
	private final HtcIncomeChargeCostHosReportService htcIncomeChargeCostHosReportService = null;
	
	 // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/income/report/hos/htcIncomeChargeCostHosReportMainPage", method = RequestMethod.GET)
	public String htcIncomeChargeCostHosReportMainPage(Model mode) throws Exception {

		return "hrp/htc/income/report/hos/htcIncomeChargeCostHosReportMain";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/income/report/hos/queryHtcIncomeChargeCostHosReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeChargeCostHosReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = htcIncomeChargeCostHosReportService.queryHtcIncomeChargeCostHosReport(getPage(mapVo));

		return JSONObject.parseObject(json);

	}
	
	
		
}
