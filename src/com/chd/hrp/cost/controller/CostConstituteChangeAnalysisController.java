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
import com.chd.hrp.cost.service.CostConstituteChangeAnalysisService;

@Controller
public class CostConstituteChangeAnalysisController extends com.chd.base.BaseController {
	
	private static Logger logger = Logger.getLogger(CostConstituteChangeAnalysisController.class);

	@Resource(name = "costConstituteChangeAnalysisService")
	private final CostConstituteChangeAnalysisService costConstituteChangeAnalysisService = null;
	
	/**
	*成本构成变化趋势分析<BR>
	*分析显示页面
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costconstitutechangealysis/costConstituteChangeAnalysisMainPage", method = RequestMethod.GET)
	public String costConstituteChangeAnalysisMainPage(Model mode) throws Exception {

		return "hrp/cost/costanalysis/costconstitutechangealysis/costConstituteChangeAnalysisMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costconstitutechangealysis/queryCostConstituteChangeAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostConstituteChangeAnalysis(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

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
			 
		 }
		
		String costExecIncomeTrend = costConstituteChangeAnalysisService.queryCostConstituteChange(getPage(mapVo));

		return JSONObject.parseObject(costExecIncomeTrend);
	}

}
