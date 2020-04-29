package com.chd.hrp.pac.controller.skht.payment;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.skht.payment.PactRecMainSKHTService;

@Controller
@RequestMapping(value = "/hrp/pac/skht/payment/detail")
public class PactRecMainSKHTDetailController extends BaseController {

	@Resource(name = "pactRecMainSKHTService")
	private PactRecMainSKHTService pactRecMainSKHTService;

	@RequestMapping(value = "/pactRecMainSKHTDetailMainPage")
	public String toPactRecMainInitSKHTMainPage() {
		return "hrp/pac/skht/payment/detail/pactRecMainSKHTDetailMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactRecMainSKHTDetail", method = RequestMethod.POST)
	public Map<String, Object> queryPactRecMainSKHTDetail(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String query = pactRecMainSKHTService.queryPactRecMainSKHTDetail(getPage(mapVo));
		return JSONObject.parseObject(query);
	}
	
}
