package com.chd.hrp.cost.controller.director;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.director.CostRevenueAnalysisService;

/*
 * 收入分析
 * */
@Controller
@RequestMapping(value="/hrp/cost/director/revenueanalysis")
public class CostRevenueAnalysisController extends BaseController{
	
	private static Logger logger = Logger.getLogger(CostRevenueAnalysisController.class);
	
	@Resource(name = "costRevenueAnalysisService")
	private final CostRevenueAnalysisService costRevenueAnalysisService = null;
	
	/**
	 * 
	* @Title: costDeptRevenueMainPage
	* @Description: 科室收入分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costDeptRevenueMainPage",method = RequestMethod.GET)
	public String costDepartmentOperationMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/revenueanalysis/deptrevenue/costDeptRevenueMain";
	}
	
	/**
	 * 
	* @Title: costDeptRevenueMainPage
	* @Description: 科室收入分析-构成分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costDeptRevenueConstituteMainPage",method = RequestMethod.GET)
	public String costDeptRevenueConstituteMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/revenueanalysis/deptrevenue/costDeptRevenueConstituteMain";
	}
	
	/**
	 * 
	* @Title: queryCostDeptRevenueConstitute
	* @Description: 科室收入分析-构成分析查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostDeptRevenueConstitute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptRevenueConstitute(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costRevenueAnalysisService.queryCostDeptRevenueConstitute(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	

	/**
	 * 
	* @Title: costDeptRevenueMainPage
	* @Description: 科室收入分析-趋势分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costDeptRevenueTrendMainPage",method = RequestMethod.GET)
	public String costDeptRevenueTrendMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/revenueanalysis/deptrevenue/costDeptRevenueTrendMain";
	}
	
	/**
	 * 
	* @Title: queryCostDeptRevenueTrend
	* @Description: 科室收入分析-构趋势分析查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostDeptRevenueTrend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptRevenueTrend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costRevenueAnalysisService.queryCostDeptRevenueTrend(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
  /**
	* 
	* @Title: queryCostDeptRevenueTrend
	* @Description: 科室收入分析-构趋势分析查询图表
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostDeptRevenueTrendEcharts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptRevenueTrendEcharts(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costRevenueAnalysisService.queryCostDeptRevenueTrendEcharts(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}

	/**
	 * 
	* @Title: costDeptRevenueCompareMainPage
	* @Description: 科室收入分析-比较分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costDeptRevenueCompareMainPage",method = RequestMethod.GET)
	public String costDeptRevenueCompareMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/revenueanalysis/deptrevenue/costDeptRevenueCompareMain";
	}
	
  /**
	* 
	* @Title: queryCostDeptRevenueCompare
	* @Description: 科室收入分析-比较分析
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostDeptRevenueCompare", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptRevenueCompare(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costRevenueAnalysisService.queryCostDeptRevenueCompare(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	/**
	 * 
	* @Title: costDeptRevenueOpeningOrderMainPage
	* @Description: 科室收入分析-开单收入分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costDeptRevenueOpeningOrderMainPage",method = RequestMethod.GET)
	public String costDeptRevenueOpeningOrderMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/revenueanalysis/deptrevenue/costDeptRevenueOpeningOrderMain";
	}
	
  /**
	* 
	* @Title: queryCostDeptRevenueOpeningOrder
	* @Description: 科室收入分析-开单收入分析查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostDeptRevenueOpeningOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptRevenueOpeningOrder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costRevenueAnalysisService.queryCostDeptRevenueOpeningOrder(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	/**
	 * 
	* @Title: costDeptRevenueImplementMainPage
	* @Description: 科室收入分析-执行收入分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costDeptRevenueImplementMainPage",method = RequestMethod.GET)
	public String costDeptRevenueImplementMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/revenueanalysis/deptrevenue/costDeptRevenueImplementMain";
	}
	
  /**
	* 
	* @Title: queryCostDeptRevenueOpeningOrder
	* @Description: 科室收入分析-执行收入分析查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostDeptRevenueImplement", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptRevenueImplement(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costRevenueAnalysisService.queryCostDeptRevenueImplement(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
}
