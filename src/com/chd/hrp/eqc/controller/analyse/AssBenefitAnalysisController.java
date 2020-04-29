
/*
 *
 */
 package com.chd.hrp.eqc.controller.analyse;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.eqc.service.analyse.AssBenefitAnalysisService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
*  Controller实现类
*/
@Controller
public class AssBenefitAnalysisController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssBenefitAnalysisController.class);
	
	//引入Service服务
	@Resource(name = "assBenefitAnalysisService")
	private final AssBenefitAnalysisService assBenefitAnalysisService = null;
   
	/**
	 * @Description 
	 * 资产收益分析报表 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/assProfitAnalysisMainPage", method = RequestMethod.GET)
	public String assBenefitAnalysisMainPage(Model mode) throws Exception {

		return "hrp/eqc/analyse/assProfitAnalysisMain";

	}
	/**
	 * @Description 
	 * 资产收益分析报表 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/queryAssProfitAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBenefitAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBenefitAnalysis = assBenefitAnalysisService.queryAssProfitAnalysis(getPage(mapVo));

		return JSONObject.parseObject(assBenefitAnalysis);
		
	}
	
	/**
	 * @Description 
	 * 投资收益报表 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/assInvestmentBenefitAnalysMainPage.do", method = RequestMethod.GET)
	public String assInvestmentBenefitAnalysMainPage(Model mode) throws Exception {

		return "hrp/eqc/analyse/assInvestmentBenefitAnalysMain";

	}
	/**
	 * @Description 
	 * 投资收益报表 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/queryInvestmentBenefitAnalys", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInvestmentBenefitAnalys(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBenefitAnalysis = assBenefitAnalysisService.queryInvestmentBenefitAnalys(getPage(mapVo));

		return JSONObject.parseObject(assBenefitAnalysis);
		
	}
	
	/**
	 * @Description 
	 * 本量利分析报表 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/assCvpAnalysisMainPage.do", method = RequestMethod.GET)
	public String assCvpAnalysisMainPage(Model mode) throws Exception {

		return "hrp/eqc/analyse/assCvpAnalysisMain";
		//return "hrp/eqc/analyse/assInvestmentBenefitAnalysMain";

	}
	/**
	 * @Description 
	 * 本量利分析报表 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/queryCvpAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCvpAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBenefitAnalysis = assBenefitAnalysisService.queryCvpAnalysis(getPage(mapVo));

		return JSONObject.parseObject(assBenefitAnalysis);
		
	}
	
	
	/**
	 * @Description 
	 * 设备收支明细 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/assIncomeAndCostMainPage", method = RequestMethod.GET)
	public String assIncomeAndCostMainPage(Model mode) throws Exception {

		return "hrp/eqc/analyse/assIncomeAndCostMain";

	}
	/**
	 * @Description 
	 * 设备收支明细 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/queryAssIncomAndCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssIncomAndCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBenefitAnalysis = assBenefitAnalysisService.queryAssIncomAndCost(getPage(mapVo));

		return JSONObject.parseObject(assBenefitAnalysis);
		
	}
	
	/**
	 * @Description 
	 * 贵重医疗设备使用效益分析表 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/assUseBenefitAnalysMainPage", method = RequestMethod.GET)
	public String assUseBenefitAnalysMainPage(Model mode) throws Exception {

		return "hrp/eqc/analyse/assUseBenefitAnalysMain";

	}
	/**
	 * @Description 
	 * 贵重医疗设备使用效益分析表 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/analyse/queryAssUseBenefitAnalys", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssUseBenefitAnalys(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(!StringTool.isNotBlank(mapVo.get("year"))){
			mapVo.put("year", SessionManager.getAcctYear());
		}
        
		String assBenefitAnalysis = assBenefitAnalysisService.queryAssUseBenefitAnalys(getPage(mapVo));

		return JSONObject.parseObject(assBenefitAnalysis);
		
	}
    
}

