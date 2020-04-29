package com.chd.hrp.pac.controller.skxy.pactinfo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.BaseController;

@Controller
@RequestMapping(value="/hrp/pac/skxy/pactinfo/pactAnalysis")
public class PactAnalysisSKXYController extends BaseController{

	@RequestMapping(value = "/pactAnalysisSKXYMainPage", method = RequestMethod.GET)
	public String toPactAnalysisSKXYMainPage() {
		return "hrp/pac/skxy/pactinfo/pactanalysis/pactAnalysisSKXYMain";
	}
	
	@RequestMapping(value = "/pactAnalysisSKXYDetailPage", method = RequestMethod.GET)
	public String pactAnalysisSKXYDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		return "hrp/pac/skxy/pactinfo/pactanalysis/pactAnalysisSKXYDetail";
	}
}
