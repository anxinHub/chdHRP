package com.chd.hrp.pac.controller.fkht.payment;

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
import com.chd.hrp.pac.service.fkht.payment.PactPayMainFKHTService;

@Controller
@RequestMapping(value = "/hrp/pac/fkht/payment/detail")
public class PactPayMainFKHTDetailController extends BaseController {

	@Resource(name = "pactPayMainFKHTService")
	private PactPayMainFKHTService pactPayMainFKHTService;

	@RequestMapping(value = "/pactPayMainFKHTDetailMainPage")
	public String toPactPayMainInitFKHTMainPage() {
		return "hrp/pac/fkht/payment/detail/pactPayMainFKHTDetailMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactPayMainFKHTDetail", method = RequestMethod.POST)
	public Map<String, Object> queryPactPayMainFKHTDetail(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String query = pactPayMainFKHTService.queryPactPayMainFKHTDetail(getPage(mapVo));
		return JSONObject.parseObject(query);
	}
	
}
