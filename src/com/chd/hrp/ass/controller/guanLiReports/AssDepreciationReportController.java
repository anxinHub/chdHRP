package com.chd.hrp.ass.controller.guanLiReports;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.guanLiReports.AssDepreciationReportService;
/**
 * 资产折旧报表
 * @author 
 *
 */
@Controller
public class AssDepreciationReportController extends BaseController {
	
	private static Logger logger = Logger.getLogger(AssDepreciationReportController.class);
	
	//引入Service服务
	@Resource(name = "assDepreciationReportService")
	private final AssDepreciationReportService assDepreciationReportService = null;
	
	/**
	 * @Description 
	 * 资产折旧分析页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/assDepreciationAnalysePage", method = RequestMethod.GET)
	public String assDepreciationAnalysePage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assDepreciationAnalyseMain";

	}
	
	/**
	 * @Description 
	 * 资产折旧汇总页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/assDepreciationSummaryPage", method = RequestMethod.GET)
	public String assDepreciationSummaryPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assDepreciationSummaryMain";

	}
	
	/**
	 * @Description 
	 * 资产折旧信息页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/assDepreciationInfoPage", method = RequestMethod.GET)
	public String assDepreciationInfoPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assDepreciationInfoMain";

	}
	
	/**
	 * @Description 
	 * 查询数据 资产折旧分析 资产分类表头
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception 
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssTypeHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTypeHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson = assDepreciationReportService.queryAssTypeHead(mapVo);
		return JSONObject.parseObject(reJson);
		
	}
	

	/**
	 * @Description 
	 * 查询数据 资产折旧分析
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception 
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssDepreciationAnalyse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreciationAnalyse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson = assDepreciationReportService.queryAssDepreciationAnalyse(mapVo);
		return JSONObject.parseObject(reJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 资产折旧汇总
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception 
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssDepreciationSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreciationSummary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson = assDepreciationReportService.queryAssDepreciationSummary(getPage(mapVo));
		return JSONObject.parseObject(reJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 资产折旧信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception 
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssDepreciationInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreciationInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson = assDepreciationReportService.queryAssDepreciationInfo(getPage(mapVo));
		return JSONObject.parseObject(reJson);
		
	}
	
}
