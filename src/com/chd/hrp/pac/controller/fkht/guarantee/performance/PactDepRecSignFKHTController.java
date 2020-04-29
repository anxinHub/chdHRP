package com.chd.hrp.pac.controller.fkht.guarantee.performance;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.BaseController;

/**
 * 期初保证金收款
 * 
 * @author haotong
 * @date 2018年9月12日 下午4:10:24
 */
@Controller
@RequestMapping(value = "/hrp/pac/fkht/guarantee/performance/sign")
public class PactDepRecSignFKHTController extends BaseController {

	@RequestMapping(value = "/pactDepRecSignFKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/fkht/guarantee/performance/sign/PactDepRecSignFKHTMain";
	}

}
