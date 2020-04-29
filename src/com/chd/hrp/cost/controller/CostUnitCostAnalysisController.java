/**
* @Copyright: Copyright (c) 2015-3-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
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
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.cost.serviceImpl.CostDeptDriDataServiceImpl;
/**
* @Title. @Description.
* 单位成本分析
* @Author: XueWanLi
* @email: bell@s-chd.com
* @Version: 1.0
*/
@Controller
public class CostUnitCostAnalysisController extends BaseController{
	private static Logger logger = Logger.getLogger(CostUnitCostAnalysisController.class);
	
	@Resource(name = "costDeptDriDataService")
	private final CostDeptDriDataServiceImpl costDeptDriDataService = null;
	
	
	/**
	*诊次成本分析<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costunitcostanalysis/visitsCostAnalysisMainPage", method = RequestMethod.GET)
	public String visitsCostAnalysisMainPage(Model mode) throws Exception {
		return "hrp/cost/costanalysis/costunitcostanalysis/visitsCostAnalysisMain";
	}
	
	/**
	*床日成本分析<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costunitcostanalysis/onTheBedCostAnalysisMainPage", method = RequestMethod.GET)
	public String onTheBedCostAnalysisMainPage(Model mode) throws Exception {
		return "hrp/cost/costanalysis/costunitcostanalysis/onTheBedCostAnalysisMain";
	}
	
	/**
	*诊次成本分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costunitcostanalysis/queryVisitsCostAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryVisitsCostAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
		String costBonusDetailMap = costDeptDriDataService.queryVisitsCostAnalysis(getPage(mapVo));

		return JSONObject.parseObject(costBonusDetailMap);
	}
	
	
	/**
	*床日成本分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costunitcostanalysis/queryOnTheBedCostAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOnTheBedCostAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
		String costBonusDetailMap = costDeptDriDataService.queryOnTheBedCostAnalysis(getPage(mapVo));

		return JSONObject.parseObject(costBonusDetailMap);
	}
}
