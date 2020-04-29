package com.chd.hrp.cost.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CostExecIncomeConstituteAnalysisController extends com.chd.base.BaseController {
	
	/**
	*执行收入构成趋势分析<BR>
	*分析显示页面
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costexecincomeconstitute/costExecIncomeConstituteMainPage", method = RequestMethod.GET)
	public String costExecIncomeConstituteMainPage(Model mode) throws Exception {

		return "hrp/cost/costanalysis/costexecincomeconstitute/costExecIncomeConstituteMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costexecincomeconstitute/queryCostExecIncomeConstitute", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostExecIncomeConstitute(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "";

	}

}
