package com.chd.hrp.pac.controller.skxy.pactinfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;

@Controller
@RequestMapping(value = "/hrp/pac/skxy/pactinfo/pactsign")
public class PactMainSKXYSignController extends BaseController{

	@RequestMapping(value = "/pactSignSKXYMainPage", method = RequestMethod.GET)
	public String toPactInitSKXYMainPage() {
		return "hrp/pac/skxy/pactinfo/pactsign/pactSignSKXYMain";
	}

}
