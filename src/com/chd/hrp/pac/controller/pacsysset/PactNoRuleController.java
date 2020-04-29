package com.chd.hrp.pac.controller.pacsysset;

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
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

/**
 * 单据编码规则
 * 
 * @author haotong
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/pacsysset")
public class PactNoRuleController extends BaseController {

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService service;

	@RequestMapping(value = "/pacNoRuleMainPage", method = RequestMethod.GET)
	public String toPactNoRuleMain() {
		return "hrp/pac/pacsysset/pacNoRuleMain";
	}

	@RequestMapping(value = "/queryPacNoRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactNoRule(@RequestParam Map<String, Object> mapVo, Model mode) {

		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String queryAllPactNoRule = service.queryAllPactNoRule(getPage(mapVo));

			return JSONObject.parseObject(queryAllPactNoRule);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
	@RequestMapping(value = "/updatePacNoRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePactNoRule(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String updatePactNoRule = service.updatePactNoRule(mapVo);
			
			return JSONObject.parseObject(updatePactNoRule);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
