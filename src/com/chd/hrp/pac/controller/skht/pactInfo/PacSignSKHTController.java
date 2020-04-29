package com.chd.hrp.pac.controller.skht.pactInfo;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chd.base.BaseController;

/**
 * 合同签订
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/skht/pactinfo/pactsign")
public class PacSignSKHTController extends BaseController {

	@RequestMapping(value = "/pactSignSKHTMainPage", method = RequestMethod.GET)
	public String toPactNoRuleMain() {
		return "hrp/pac/skht/pactinfo/pactsign/pactSignSKHTMain";
	}
}

