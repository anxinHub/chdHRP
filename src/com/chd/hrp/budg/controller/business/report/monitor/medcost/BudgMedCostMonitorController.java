package com.chd.hrp.budg.controller.business.report.monitor.medcost;

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
public class BudgMedCostMonitorController extends BaseController{
	
	@Resource(name = "budgMedCostService")
	private final BudgMedCostService budgMedCostService = null;

	private static Logger logger = Logger.getLogger(BudgMedCostMonitorController.class);
	
	@RequestMapping(value = "/hrp/budg/business/report/monitor/medcost/budgMedDeptMonitorMainPage", method = RequestMethod.GET)
	public String budgMedDeptMonitorMainPage(Model mode) throws Exception {

		return "hrp/budg/business/report/monitor/medcost/budgMedDeptCostMonitorMain";
	}
	
	@RequestMapping(value = "/hrp/budg/business/report/monitor/medcost/budgMedAllMonitorMainPage", method = RequestMethod.GET)
	public String budgMedAllMonitorMainPage(Model mode) throws Exception {

		return "hrp/budg/business/report/monitor/medcost/budgMedAllCostMonitorMain";
	}
	
	
	@RequestMapping(value = "/hrp/budg/business/report/monitor/medcost/budgMedElseCostMonitorMainPage", method = RequestMethod.GET)
	public String budgMedElseCostMonitorMainPage(Model mode) throws Exception {

		return "hrp/budg/business/report/monitor/medcost/budgMedElseCostMonitorMain";
	}
	
	
	@RequestMapping(value = "/hrp/budg/business/report/monitor/medcost/queryReportMedDeptMonitor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportMedDeptMonitor(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgMedCost = budgMedCostService.queryDeptMedCostMonitor(getPage(mapVo));

		return JSONObject.parseObject(budgMedCost);
		
	}
	
	
	@RequestMapping(value = "/hrp/budg/business/report/monitor/medcost/queryReportMedAllMonitor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportMedAllMonitor(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgMedCost = budgMedCostService.queryAllMedCostMonitor(getPage(mapVo));

		return JSONObject.parseObject(budgMedCost);
		
	}
	
	
	@RequestMapping(value = "/hrp/budg/business/report/monitor/medcost/queryReportMedElseCostMonitor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReportMedElseCostMonitor(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgMedCost = budgMedCostService.queryReportMedElseCostMonitor(getPage(mapVo));

		return JSONObject.parseObject(budgMedCost);
		
	}
	
}
