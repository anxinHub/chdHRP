package com.chd.hrp.pac.controller.fkht.pactInfo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.BaseController;

/**
 * 追踪分析
 * 
 * @author haotong
 *
 */
@Controller 
@RequestMapping(value = "/hrp/pac/fkht/pactinfo/pactAnalysis")
public class PactAnalysisFKHTController extends BaseController {

	@RequestMapping(value = "/pactAnalysisFKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/fkht/pactinfo/pactanalysis/pactAnalysisFKHTMain";
	}

	@RequestMapping(value = "/pactAnalysisFKHTDetailPage", method = RequestMethod.GET)
	public String toPactMainFKHTAdd(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		return "hrp/pac/fkht/pactinfo/pactanalysis/pactAnalysisFKHTDetail";
	}
}
