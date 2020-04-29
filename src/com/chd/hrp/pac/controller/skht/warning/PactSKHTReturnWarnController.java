package com.chd.hrp.pac.controller.skht.warning;

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
import com.chd.hrp.pac.service.skht.pactinfo.PactMainSKHTService;

/**
 * 合同执行
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/skht/warning")
public class PactSKHTReturnWarnController extends BaseController {

	@Resource(name = "pactMainSKHTService")
	private PactMainSKHTService pactMainSKHTService;

	@RequestMapping(value = "/pactSKHTReturnWarnMainPage", method = RequestMethod.GET)
	public String toPactNoRuleMain() {
		return "hrp/pac/skht/warning/pactSKHTReturnWarnMain";
	}

	/**
	 * 查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMainSKHTForRetWarning", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainSKHTForRetWarning(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", 3);
			mapVo.put("state_code", 12);
			String query = pactMainSKHTService.queryPactMainSKHTForRetWarning(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
