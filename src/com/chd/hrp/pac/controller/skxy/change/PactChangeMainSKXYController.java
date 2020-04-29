package com.chd.hrp.pac.controller.skxy.change;

import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;

@Controller
@RequestMapping(value = "/hrp/pac/skxy/change")
public class PactChangeMainSKXYController {

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;

	@RequestMapping(value = "/pactMainChangeSKXYMainPage")
	public String toPactChangeMainSKXYPage() {
		return "hrp/pac/skxy/change/pactChangeSKXYMain";
	}

	@RequestMapping(value = "/pactMainChangeSKXYPrePage")
	public String toPactMainChangeSKXYPrePage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainSKXYEntity entity = pactChangeService.queryPrePactMainSKXYByChangeCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
			mode.addAttribute("change_code", mapVo.get("change_code"));
		}
		return "hrp/pac/skxy/change/pactExecSKXYEdit";
	}

	@RequestMapping(value = "/pactMainChangeSKXYPDetailPage")
	public String toPactMainChangeSKXYPDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("change_code", mapVo.get("change_code"));
		return "hrp/pac/skxy/change/pactMainChangeSKXYPDetail";
	}

	/**
	 * 查询合同变更记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMainChangeSKXY", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainChangeSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_exe", "0");
			String string = pactChangeService.queryPactMainChangeSKXY(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
