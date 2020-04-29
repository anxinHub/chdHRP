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
import com.chd.hrp.cost.service.CostMedDeptContrastAnalysisService;

@Controller
public class CostMedDeptContrastAnalysisController extends com.chd.base.BaseController {
	
	private static Logger logger = Logger.getLogger(CostMedDeptContrastAnalysisController.class);

	@Resource(name = "costMedDeptContrastAnalysisService")
	private final CostMedDeptContrastAnalysisService costMedDeptContrastAnalysisService = null;
	
	/**
	*医技科室对比分析<BR>
	*分析显示页面
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costmeddeptcontrastanalysis/costMedDeptContrastAnalysisMainPage", method = RequestMethod.GET)
	public String costMedDeptContrastAnalysisMainPage(Model mode) throws Exception {

		return "hrp/cost/costanalysis/costmeddeptcontrastanalysis/costMedDeptContrastAnalysisMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costmeddeptcontrastanalysis/queryCostMedDeptContrastAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostMedDeptContrastAnalysis(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

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
		
		String costMedDeptContrast = costMedDeptContrastAnalysisService.queryCostMedDeptContrast(getPage(mapVo));

		return JSONObject.parseObject(costMedDeptContrast);

	}

}
