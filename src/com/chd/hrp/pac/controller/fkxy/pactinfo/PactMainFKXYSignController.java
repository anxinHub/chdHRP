package com.chd.hrp.pac.controller.fkxy.pactinfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;

@Controller
@RequestMapping(value = "/hrp/pac/fkxy/pactinfo/pactsign")
public class PactMainFKXYSignController extends BaseController {

	@RequestMapping(value = "/pactSignFKXYMainPage", method = RequestMethod.GET)
	public String toPactInitFKXYMainPage() {
		return "hrp/pac/fkxy/pactinfo/pactsign/pactSignFKXYMain";
	}

}
