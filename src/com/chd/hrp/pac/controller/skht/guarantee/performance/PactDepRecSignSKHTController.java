package com.chd.hrp.pac.controller.skht.guarantee.performance;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;
import com.chd.hrp.pac.service.skht.guarantee.performance.PactDepRecSKHTService;

/**
 * 期初保证金收款
 * 
 * @author haotong
 * @date 2018年9月12日 下午4:10:24
 */
@Controller
@RequestMapping(value = "/hrp/pac/skht/guarantee/performance/sign")
public class PactDepRecSignSKHTController extends BaseController {

	@Resource(name = "pactDepRecSKHTService")
	private PactDepRecSKHTService pactDepRecSKHTService;

	@RequestMapping(value = "/pactDepRecSignSKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/skht/guarantee/performance/sign/PactDepRecSignSKHTMain";
	}

}
