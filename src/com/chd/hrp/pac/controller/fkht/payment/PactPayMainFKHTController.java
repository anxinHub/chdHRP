package com.chd.hrp.pac.controller.fkht.payment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.entity.fkht.payment.PactPayMainFKHTEntity;
import com.chd.hrp.pac.service.basicset.type.PactTypeFKHTService;
import com.chd.hrp.pac.service.fkht.pactinfo.PactPlanFKHTService;
import com.chd.hrp.pac.service.fkht.payment.PactPayMainFKHTService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

@Controller
@RequestMapping(value = "/hrp/pac/fkht/payment/payment")
public class PactPayMainFKHTController extends BaseController {

	@Resource(name = "pactPayMainFKHTService")
	private PactPayMainFKHTService pactPayMainFKHTService;

	@Resource(name = "pactPlanFKHTService")
	private PactPlanFKHTService pactPlanFKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@Resource(name = "pactTypeFKHTService")
	private PactTypeFKHTService pactTypeFKHTService;

	@RequestMapping(value = "pactPayMainFKHTMainPage")
	public String toPactPayMainFKHTMainPage() {
		return "hrp/pac/fkht/payment/payment/pactPayMainFKHTMain";
	}

	@RequestMapping(value = "pactPayMainFKHTAddPage")
	public String toPactPayMainFKHTAddPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("is_init", mapVo.get("is_init"));
		return "hrp/pac/fkht/payment/payment/pactPayMainFKHTAdd";
	}

	@RequestMapping(value = "pactPayMainFKHTEditPage")
	public String pactPayMainFKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		PactPayMainFKHTEntity entity = pactPayMainFKHTService.queryPactPayMainFKHTByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("is_init", mapVo.get("is_init"));
			mode.addAttribute("entity", entity);
			mode.addAttribute("state", mapVo.get("state"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("pay_date", format.format(entity.getPay_date()));
		}
		String state = mapVo.get("state").toString();
		if ("03".equals(state)) {
			return "hrp/pac/fkht/payment/payment/pactPayMainFKHTEditView";
		} else {
			return "hrp/pac/fkht/payment/payment/pactPayMainFKHTEdit";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactPayMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactPayMainFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPayMainFKHTService.queryPactPayMainFKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatePactPayMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactPayMainFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPayMainFKHTService.updatePactPayMainFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addPactPayMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactPayMainFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("sup_id", mapVo.get("sup_no"));
			mapVo.put("maker", SessionManager.getUserId());
			mapVo.put("state", "01");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));
			String pay_code = pactNoRuleService.getNo("PACT_PAY_MAIN_FKHT", mapVo);
			mapVo.put("pay_code", pay_code);

			String result = pactPayMainFKHTService.addPactPayMainFKHT(mapVo);
			return JSONObject.parseObject(result);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactPlanFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactPlanFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPlanFKHTService.queryPactPlanFKHTForEdit(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletePactPayMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactPayMainFKHT(@RequestParam String mapVo, Model mode) {
		try {
			String query = pactPayMainFKHTService.deletePactPayMainFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkPactPayMainFKHTState", method = RequestMethod.POST)
	public Map<String, Object> checkPactPayMainFKHTState(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<PactPayMainFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactPayMainFKHTEntity.class);

			String query = pactPayMainFKHTService.checkPactPayMainFKHTState(listVo, state);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactPayDetFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactPayDetFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPayMainFKHTService.queryPactPayDetFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactFKHTForPayMent", method = RequestMethod.POST)
	public Map<String, Object> queryPactFKHTForPayMent(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPayMainFKHTService.queryPactFKHTForPayMent(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询修改页面中的付款计划
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactPayPlanFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactPayPlanFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPayMainFKHTService.queryPactPayPlanFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
