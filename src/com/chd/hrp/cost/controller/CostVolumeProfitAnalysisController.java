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
* 本量利分析
* @Author: XueWanLi
* @email: bell@s-chd.com
* @Version: 1.0
*/
@Controller
public class CostVolumeProfitAnalysisController extends BaseController{
	private static Logger logger = Logger.getLogger(CostVolumeProfitAnalysisController.class);
	
	@Resource(name = "costDeptDriDataService")
	private final CostDeptDriDataServiceImpl costDeptDriDataService = null;
	
	
	/**
	*门诊本量利分析<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costvolumeprofitanalysis/outpatientVolumeAnalysisMainPage", method = RequestMethod.GET)
	public String outpatientVolumeAnalysisMainPage(Model mode) throws Exception {
		return "hrp/cost/costanalysis/costvolumeprofitanalysis/outpatientVolumeAnalysisMain";
	}
	
	/**
	*住院本量利分析<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costvolumeprofitanalysis/inpatientVolumeAnalysisMainPage", method = RequestMethod.GET)
	public String inpatientVolumeAnalysisMainPage(Model mode) throws Exception {
		return "hrp/cost/costanalysis/costvolumeprofitanalysis/inpatientVolumeAnalysisMain";
	}
	
	/**
	*门诊量本利分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costvolumeprofitanalysis/queryOutpatientVolumeAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOutpatientVolumeAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
		String costBonusDetailMap = costDeptDriDataService.queryOutpatientVolumeAnalysis(getPage(mapVo));

		return JSONObject.parseObject(costBonusDetailMap);
	}
	
	
	/**
	*住院量本利分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costvolumeprofitanalysis/queryInpatientVolumeAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInpatientVolumeAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
		String costBonusDetailMap = costDeptDriDataService.queryInpatientVolumeAnalysis(getPage(mapVo));

		return JSONObject.parseObject(costBonusDetailMap);
	}
	
	
}
