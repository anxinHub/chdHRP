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
import com.chd.hrp.cost.service.CostOrderIncomeTrendAnalysisService;

@Controller
public class CostOrderIncomeTrendAnalysisController extends com.chd.base.BaseController {
	
	private static Logger logger = Logger.getLogger(CostOrderIncomeTrendAnalysisController.class);

	@Resource(name = "costOrderIncomeTrendAnalysisService")
	private final CostOrderIncomeTrendAnalysisService costOrderIncomeTrendAnalysisService = null;
	
	/**
	*开单收入趋势分析<BR>
	*分析显示页面
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costorderincometrend/costOrderIncomeTrendMainPage", method = RequestMethod.GET)
	public String costOrderIncomeTrendMainPage(Model mode) throws Exception {

		return "hrp/cost/costanalysis/costorderincometrend/costOrderIncomeTrendMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costorderincometrend/queryCostOrderIncomeTrend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostOrderIncomeTrend(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			
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
			
			String costOrderIncomeTrend = costOrderIncomeTrendAnalysisService.queryCostOrderIncomeTrend(getPage(mapVo));

			return JSONObject.parseObject(costOrderIncomeTrend);

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costorderincometrend/queryOrderIncomeTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOrderIncomeTitle(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
