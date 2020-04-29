package com.chd.hrp.budg.controller.business.report.budgmedcost;

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
import com.chd.hrp.budg.service.business.med.BudgMedCostService;


@Controller
public class BudgMedCostReportController extends BaseController{
	
	@Resource(name = "budgMedCostService")
	private final BudgMedCostService budgMedCostService = null;

	private static Logger logger = Logger.getLogger(BudgMedCostReportController.class);
	
	@RequestMapping(value = "/hrp/budg/business/report/budgmedcost/budgMedDeptCostQueryMainPage", method = RequestMethod.GET)
	public String budgMedDeptMainPage(Model mode) throws Exception {

		return "hrp/budg/business/report/budgmedcost/budgMedDeptCostQueryMain";
	}
	
	@RequestMapping(value = "/hrp/budg/business/report/budgmedcost/budgMedHosCostQueryMainPage", method = RequestMethod.GET)
	public String budgMedHosCostQueryMain(Model mode) throws Exception {

		return "hrp/budg/business/report/budgmedcost/budgMedHosCostQueryMain";
	}
	
	@RequestMapping(value = "/hrp/budg/business/report/budgmedcost/budgMedAllCostQueryMainPage", method = RequestMethod.GET)
	public String budgMedAllMainPage(Model mode) throws Exception {
		
		return "hrp/budg/business/report/budgmedcost/budgMedAllCostQueryMain";
	}
	
	@RequestMapping(value = "/hrp/budg/business/report/budgmedcost/queryReportMedHosCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportMedHosCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String budgMedCost = budgMedCostService.queryReportMedHosCost(getPage(mapVo));
		
		return JSONObject.parseObject(budgMedCost);
		
	}
	
	@RequestMapping(value = "/hrp/budg/business/report/budgmedcost/queryReportMedDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportMedDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgMedCost = budgMedCostService.queryReportMedDeptCost(getPage(mapVo));

		return JSONObject.parseObject(budgMedCost);
		
	}
	
	@RequestMapping(value = "/hrp/budg/business/report/budgmedcost/queryMedAllCostReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAllCostReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgMedCost = budgMedCostService.queryMedAllCostReport(getPage(mapVo));

		return JSONObject.parseObject(budgMedCost);
	}
}
