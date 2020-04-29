package com.chd.hrp.cost.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CostOrderIncomeConstituteAnalysisController extends com.chd.base.BaseController {
	
	/**
	*开单收入构成趋势分析<BR>
	*分析显示页面
	*/
	@RequestMapping(value = "/hrp/cost/costanalysis/costorderincomeconstitute/costOrderIncomeConstituteMainPage", method = RequestMethod.GET)
	public String costOrderIncomeConstituteMainPage(Model mode) throws Exception {

		return "hrp/cost/costanalysis/costorderincomeconstitute/costOrderIncomeConstituteMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costanalysis/costorderincomeconstitute/queryCostOrderIncomeConstitute", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostOrderIncomeConstitute(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return mapVo;

	}

}
