package com.chd.hrp.pac.controller.skht.pactInfo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.BaseController;

@Controller
@RequestMapping(value="/hrp/pac/skht/pactinfo/pactAnalysis")
public class PactAnalysisSKHTController extends BaseController{

	@RequestMapping(value = "/pactAnalysisSKHTMainPage", method = RequestMethod.GET)
	public String toPactAnalysisSKHTMainPage() {
		return "hrp/pac/skht/pactinfo/pactanalysis/pactAnalysisSKHTMain";
	}
	
	@RequestMapping(value = "/pactAnalysisSKHTDetailPage", method = RequestMethod.GET)
	public String pactAnalysisSKHTDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		return "hrp/pac/skht/pactinfo/pactanalysis/pactAnalysisSKHTDetail";
	}
}
