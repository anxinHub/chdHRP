package com.chd.hrp.htcg.controller.analysis;
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
import com.chd.hrp.htcg.service.analysis.PatientDrgsAnalysisService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PatientDrgsAnalysisController extends BaseController{
	private static Logger logger = Logger.getLogger(PatientDrgsAnalysisController.class);
	
	
	@Resource(name = "patientDrgsAnalysisService")
	private final PatientDrgsAnalysisService patientDrgsAnalysisService = null;

	
	/*
	 * 单病种成本明细表(页面跳转)
	 */
	@RequestMapping(value = "/hrp/htcg/analysis/patientDrgsAnalysisCostDetailMainPage", method = RequestMethod.GET)
	public String patientDrgsAnalysisCostDetailMainPage(Model mode) throws Exception {
		return "hrp/htcg/analysis/patientDrgsAnalysisCostDetailMain";

	}
	/*
	 * 单病种成本明细表(查询)
	 */
	@RequestMapping(value = "hrp/htcg/analysis/queryPatientDrgsAnalysisCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPatientDrgsAnalysisCostDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
	  		 mapVo.put("copy_code", SessionManager.getCopyCode());
			 mapVo.put("acc_year", SessionManager.getAcctYear());
		String patientDrgsAnalysisJson = patientDrgsAnalysisService.queryPatientDrgsAnalysisCostDetail(getPage(mapVo));
		return JSONObject.parseObject(patientDrgsAnalysisJson);

	}

	/*
	 * 单病种成本表(页面跳转)
	 */
	@RequestMapping(value = "hrp/htcg/analysis/patientDrgsAnalysisCostMainPage", method = RequestMethod.GET)
	public String patientDrgsAnalysisCostMainPage(Model mode) throws Exception {
		return "hrp/htcg/analysis/patientDrgsAnalysisCostMain";

	}
	
	/*
	 * 单病种成本表(查询)
	 */
	@RequestMapping(value = "hrp/htcg/analysis/queryPatientDrgsAnalysisCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPatientDrgsAnalysisCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
		 mapVo.put("hos_id", SessionManager.getHosId());
 		 mapVo.put("copy_code", SessionManager.getCopyCode());
		 mapVo.put("acc_year", SessionManager.getAcctYear());
		String patientDrgsAnalysisJson = patientDrgsAnalysisService.queryPatientDrgsAnalysisCost(getPage(mapVo));
		return JSONObject.parseObject(patientDrgsAnalysisJson);

	}

}

