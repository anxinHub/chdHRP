package com.chd.hrp.budg.controller.business.report.budgelsecost;

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
import com.chd.hrp.budg.service.budgcost.eslecost.BudgElseCostService;


@Controller
public class BudgElseCostReportController extends BaseController{
	
	@Resource(name = "budgElseCostService")
	private final BudgElseCostService budgElseCostService = null;

	private static Logger logger = Logger.getLogger(BudgElseCostReportController.class);
	
	@RequestMapping(value = "/hrp/budg/business/report/budgelsecost/budgElseCostQueryMainPage", method = RequestMethod.GET)
	public String budgElseCostQueryMainPage(Model mode) throws Exception {

		return "hrp/budg/business/report/budgelsecost/budgElseCostQueryMain";
	}
	
	
	@RequestMapping(value = "/hrp/budg/business/report/budgelsecost/queryElseCostReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryElseCostReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String budgMedCost = budgElseCostService.queryElseCostReport(getPage(mapVo));
		
		return JSONObject.parseObject(budgMedCost);
		
	}
}
