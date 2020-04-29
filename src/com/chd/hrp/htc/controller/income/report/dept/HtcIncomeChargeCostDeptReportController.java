package com.chd.hrp.htc.controller.income.report.dept;

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
import com.chd.hrp.htc.service.income.report.dept.HtcIncomeChargeCostDeptReportService;
 
@Controller
public class HtcIncomeChargeCostDeptReportController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcIncomeChargeCostDeptReportController.class);
	
	@Resource(name = "htcIncomeChargeCostDeptReportService")
	private final HtcIncomeChargeCostDeptReportService htcIncomeChargeCostDeptReportService = null;
	
	 // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/income/report/dept/htcIncomeChargeCostDeptReportMainPage", method = RequestMethod.GET)
	public String htcIncomeChargeCostDeptReportMainPage(Model mode) throws Exception {

		return "hrp/htc/income/report/dept/htcIncomeChargeCostDeptReportMain";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/income/report/dept/queryHtcIncomeChargeCostDeptReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeChargeCostDeptReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = htcIncomeChargeCostDeptReportService.queryHtcIncomeChargeCostDeptReport(getPage(mapVo));

		return JSONObject.parseObject(json);

	}
	
	
		
}
