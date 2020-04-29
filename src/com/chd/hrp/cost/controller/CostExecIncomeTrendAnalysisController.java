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
import com.chd.hrp.cost.service.CostExecIncomeTrendAnalysisService;
import com.chd.hrp.cost.service.CostOrderIncomeTrendAnalysisService;

@Controller
public class CostExecIncomeTrendAnalysisController extends com.chd.base.BaseController {
	
	private static Logger logger = Logger.getLogger(CostExecIncomeTrendAnalysisController.class);

	@Resource(name = "costExecIncomeTrendAnalysisService")
	private final CostExecIncomeTrendAnalysisService costExecIncomeTrendAnalysisService = null;
	
	@Resource(name = "costOrderIncomeTrendAnalysisService")
	private final CostOrderIncomeTrendAnalysisService costOrderIncomeTrendAnalysisService = null;
	/**
	*执行收入趋势分析<BR>
	*分析显示页面
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costexecincometrend/costExecIncomeTrendMainPage", method = RequestMethod.GET)
	public String costExecIncomeTrendMainPage(Model mode) throws Exception {

		return "hrp/cost/costanalysis/costexecincometrend/costexecIncomeTrendMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costexecincometrend/queryCostExecIncomeTrend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostExecIncomeTrend(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		String costExecIncomeTrend = costExecIncomeTrendAnalysisService.queryCostExecIncomeTrend(getPage(mapVo));

		return JSONObject.parseObject(costExecIncomeTrend);
	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costexecincometrend/queryExecIncomeTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryExecIncomeTitle(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
