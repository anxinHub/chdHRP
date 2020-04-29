package com.chd.hrp.pac.controller.fkht.pactInfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;

/**
 * 合同签订
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/fkht/pactinfo/pactsign")
public class PacSignFKHTController extends BaseController {

	@RequestMapping(value = "/pactSignFKHTMainPage", method = RequestMethod.GET)
	public String toPactNoRuleMain() {
		return "hrp/pac/fkht/pactinfo/pactsign/pactSignFKHTMain";
	}

}
