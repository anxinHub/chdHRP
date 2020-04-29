package com.chd.hrp.pac.controller.fkht.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chd.base.BaseController;

@Controller
@RequestMapping(value = "/hrp/pac/fkht/payment/payment")
public class PactPayMainInitFKHTController extends BaseController {

	@RequestMapping(value = "/pactPayMainInitFKHTMainPage")
	public String toPactPayMainInitFKHTMainPage() {
		return "hrp/pac/fkht/payment/init/pactPayMainInitFKHTMain";
	}
}
