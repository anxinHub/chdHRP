package com.chd.hrp.pac.controller.fkxy.pactinfo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.BaseController;

@Controller
@RequestMapping(value="/hrp/pac/fkxy/pactinfo/pactAnalysis")
public class PactAnalysisFKXYController extends BaseController{

	@RequestMapping(value = "/pactAnalysisFKXYMainPage", method = RequestMethod.GET)
	public String toPactAnalysisFKXYMainPage() {
		return "hrp/pac/fkxy/pactinfo/pactanalysis/pactAnalysisFKXYMain";
	}
	
	@RequestMapping(value = "/pactAnalysisFKXYDetailPage", method = RequestMethod.GET)
	public String pactAnalysisFKXYDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		return "hrp/pac/fkxy/pactinfo/pactanalysis/pactAnalysisFKXYDetail";
	}
}
