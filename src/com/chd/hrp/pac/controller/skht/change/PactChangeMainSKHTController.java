package com.chd.hrp.pac.controller.skht.change;

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
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;

@Controller
@RequestMapping(value = "/hrp/pac/skht/change")
public class PactChangeMainSKHTController {

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;

	@RequestMapping(value = "/pactMainChangeSKHTMainPage")
	public String toPactChangeMainSKHTPage() {
		return "hrp/pac/skht/change/pactChangeSKHTMain";
	}

	@RequestMapping(value = "/pactMoneyChangeSKHTMainPage")
	public String toPactMoneyChangeSKHTMainPage() {
		return "hrp/pac/skht/change/pactChangeMoneySKHTMain";
	}

	@RequestMapping(value = "/pactMainChangeSKHTPrePage")
	public String toPactMainChangeSKHTPrePage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainSKHTEntity entity = pactChangeService.queryPrePactMainSKHTByChangeCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
			mode.addAttribute("change_code", mapVo.get("change_code"));
		}
		return "hrp/pac/skht/change/pactExecSKHTEdit";
	}

	@RequestMapping(value = "/pactMainChangeSKHTPDetailPage")
	public String toPactMainChangeSKHTPDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("change_code", mapVo.get("change_code"));
		return "hrp/pac/skht/change/pactMainChangeSKHTPDetail";
	}

	/**
	 * 查询合同变更记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMainChangeSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainChangeSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactChangeService.queryPactMainChangeSKHT(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询合同金额变更记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMainChangeMoneySKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainChangeMoneySKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactChangeService.queryPactMainChangeMoneySKHT(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询合同金额变更记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMoneyChangeSKHTDet", method = RequestMethod.POST)
	public Map<String, Object> queryPactMoneyChangeSKHTDet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			// 必填项
			mapVo.put("table_code", "PACT_MONEY_C_SKHT");
			mapVo.put("inner_table_code", "PACT_CHANGE_SKHT");
			String string = pactChangeService.queryPactMoneyChangeDet(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactPlanSKHTForPre", method = RequestMethod.POST)
	public Map<String, Object> queryPactPlanSKHTForPre(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			// 必填项
			String string = pactChangeService.queryPactPlanSKHTForPre(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
