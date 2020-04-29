package com.chd.hrp.cost.controller;

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
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.CostExecDeptEarningsAnalysisService;
import com.chd.hrp.cost.service.CostOrderIncomeTrendAnalysisService;

@Controller
public class CostExecDeptEarningsAnalysisController extends com.chd.base.BaseController {
	
	private static Logger logger = Logger.getLogger(CostExecDeptEarningsAnalysisController.class);

	@Resource(name = "costExecDeptEarningsAnalysisService")
	private final CostExecDeptEarningsAnalysisService costExecDeptEarningsAnalysisService = null;
	
	@Resource(name = "costOrderIncomeTrendAnalysisService")
	private final CostOrderIncomeTrendAnalysisService costOrderIncomeTrendAnalysisService = null;
	
	/**
	*执行科室收益分析<BR>
	*分析显示页面
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costexecdeptearningsanalysis/costExecDeptEarningsAnalysisMainPage", method = RequestMethod.GET)
	public String costExecDeptEarningsAnalysisMainPage(Model mode) throws Exception {

		return "hrp/cost/costanalysis/costexecdeptearningsanalysis/costExecDeptEarningsAnalysisMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costexecdeptearningsanalysis/queryCostExecDeptEarningsAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostExecDeptEarningsAnalysis(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
	
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		if(!"0".equals(mapVo.get("acct_year_type"))){
			 
			 mapVo.put("acc_year", SessionManager.getAcctYear());
			 
			 mapVo.put("date_type", "1");
			 
		 }else{
			 
			 mapVo.put("date_type", "0");
			 
		 }
		
		String costExecDeptEarnings = costExecDeptEarningsAnalysisService.queryCostExecDeptEarnings(getPage(mapVo));

		return JSONObject.parseObject(costExecDeptEarnings);

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costexecdeptearningsanalysis/queryExecEarningsTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExecEarningsTitle(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(!"0".equals(mapVo.get("acct_year_type"))){
			 
			 mapVo.put("acc_year", SessionManager.getAcctYear());
			 
		 }
		
		if("".equals(mapVo.get("first_year"))||"".equals(mapVo.get("last_year"))){
			
			mapVo.put("first_year", SessionManager.getAcctYear());
			
			mapVo.put("last_year", SessionManager.getAcctYear());
			
		}
		
			String json = costOrderIncomeTrendAnalysisService.queryOrderIncomeTitle(mapVo);

			return JSONObject.parseObject(json.toString());

	}

}
