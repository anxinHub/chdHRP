package com.chd.hrp.pac.controller.basicset.warning;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.basicset.warning.PactWarnSetFKHTService;

/**
 * 付款合同预警
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:09:53
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/warning")
public class PactWarnSetFKHTController extends BaseController {

	@Resource(name = "pactWarnSetFKHTService")
	private PactWarnSetFKHTService pactWarnSetFKHTService;

	@RequestMapping(value = "/pactWarnSetFKHTMainPage")
	public String toWarnSetFKHTMainPage() {
		return "hrp/pac/basicset/warning/pactWarnSetFKHTMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactWarnSetFKHT")
	public Map<String, Object> queryPactWarnSetFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());

			String query = pactWarnSetFKHTService.queryPactWarnSetFKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatePactWarnSetFKHT")
	public Map<String, Object> updatePactWarnSetFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactWarnSetFKHTService.updatePactWarnSetFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
