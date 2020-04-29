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
import com.chd.hrp.cost.service.director.CostItemAnalysisService;
/**
 * 成本分析
 * @author lenovo
 * 
 */
@Controller
@RequestMapping(value="/hrp/cost/director/itemanalysis")
public class CostItemAnalysisController extends BaseController{

	private static Logger logger = Logger.getLogger(CostItemAnalysisController.class);
	
	@Resource(name = "costItemAnalysisService")
	private final CostItemAnalysisService costItemAnalysisService = null;
	
	/**
	 * 
	* @Title: costProjectAnalysisMainPage
	* @Description: 成本项目分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costProjectAnalysisMainPage",method = RequestMethod.GET)
	public String costProjectAnalysisMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/itemanalysis/project/costProjectAnalysisMain";
	}
	
	
	/**
	 * 
	* @Title: costDeptRevenueMainPage
	* @Description: 成本项目-趋势分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */   
	@RequestMapping(value="/costProjectTrendMainPage",method = RequestMethod.GET)
	public String costProjectTrendMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/itemanalysis/project/costProjectTrendMain";
	}
	
	/**
	 * 
	* @Title: queryCostDeptRevenueTrend
	* @Description: 成本项目-构趋势分析查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostProjectTrend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostProjectTrend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costItemAnalysisService.queryCostProjectTrend(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	/**
	* 
	* @Title: queryCostProjectTrendEcharts
	* @Description: 成本项目-构趋势分析查询图表
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostProjectTrendEcharts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostProjectTrendEcharts(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costItemAnalysisService.queryCostProjectTrendEcharts(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	/**
	 * 
	* @Title: costProjectCompareMainPage
	* @Description: 成本项目比较分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costProjectCompareMainPage",method = RequestMethod.GET)
	public String costProjectCompareMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/itemanalysis/project/costProjectCompareMain";
	}
	
  /**
	* 
	* @Title: queryCostProjectCompare
	* @Description: 成本项目-比较分析
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostProjectCompare", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostProjectCompare(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costItemAnalysisService.queryCostProjectCompare(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
}
