package com.chd.hrp.pac.controller.skht.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chd.base.BaseController;

@Controller
@RequestMapping(value = "/hrp/pac/skht/payment/payment")
public class PactRecMainInitSKHTController extends BaseController {

	@RequestMapping(value = "/pactRecMainInitSKHTMainPage")
	public String toPactRecMainInitFKHTMainPage() {
		return "hrp/pac/skht/payment/init/pactRecMainInitSKHTMain";
	}
}
