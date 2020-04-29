package com.chd.hrp.pac.controller.skht.payment;

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
import com.chd.hrp.pac.entity.skht.payment.PactRecMainSKHTEntity;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.chd.hrp.pac.service.skht.pactinfo.PactPlanSKHTService;
import com.chd.hrp.pac.service.skht.payment.PactRecMainSKHTService;

@Controller
@RequestMapping(value = "/hrp/pac/skht/payment/payment")
public class PactRecMainSKHTController extends BaseController {

	@Resource(name = "pactRecMainSKHTService")
	private PactRecMainSKHTService pactRecMainSKHTService;

	@Resource(name = "pactPlanSKHTService")
	private PactPlanSKHTService pactPlanSKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@RequestMapping(value = "pactRecMainSKHTMainPage")
	public String toPactRecMainSKHTMainPage() {
		return "hrp/pac/skht/payment/payment/pactRecMainSKHTMain";
	}

	@RequestMapping(value = "pactRecMainSKHTAddPage")
	public String toPactRecMainSKHTAddPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("is_init", mapVo.get("is_init"));
		return "hrp/pac/skht/payment/payment/pactRecMainSKHTAdd";
	}

	@RequestMapping(value = "pactRecMainSKHTEditPage")
	public String pactRecMainSKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		PactRecMainSKHTEntity entity = pactRecMainSKHTService.queryPactRecMainSKHTByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("is_init", mapVo.get("is_init"));
			mode.addAttribute("entity", entity);
			mode.addAttribute("state", mapVo.get("state"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("rec_date", format.format(entity.getRec_date()));
		}
		String state = mapVo.get("state").toString();
		if ("03".equals(state)) {
			return "hrp/pac/skht/payment/payment/pactRecMainSKHTEditView";
		}
		return "hrp/pac/skht/payment/payment/pactRecMainSKHTEdit";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactRecMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactRecMainSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactRecMainSKHTService.queryPactRecMainSKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatePactRecMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactRecMainSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactRecMainSKHTService.updatePactRecMainSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/addPactRecMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactRecMainSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("maker", SessionManager.getUserId());
			mapVo.put("state", "01");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));
			String rec_code = pactNoRuleService.getNo("PACT_REC_MAIN_SKHT", mapVo);
			mapVo.put("rec_code", rec_code);

			pactRecMainSKHTService.addPactRecMainSKHT(mapVo);
			return JSONObject.parseObject("{\"msg\":\"添加成功.\",\"state\":\"true\",\"rec_code\":\"" + rec_code + "\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactSKHTForRecMent", method = RequestMethod.POST)
	public Map<String, Object> queryPactSKHTForRecMent(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactRecMainSKHTService.queryPactTypesSKHTByCode(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactPlanSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactPlanSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPlanSKHTService.queryPactPlanSKHTForPay(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactPlanSKHTForEdit", method = RequestMethod.POST)
	public Map<String, Object> queryPactPlanSKHTForEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPlanSKHTService.queryPactPlanSKHTForEdit(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/deletePactRecMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactRecMainSKHT(@RequestParam String mapVo, Model mode) {
		try {
			String query = pactRecMainSKHTService.deletePactRecMainSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/checkPactRecMainSKHTState", method = RequestMethod.POST)
	public Map<String, Object> checkPactRecMainSKHTState(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<PactRecMainSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactRecMainSKHTEntity.class);

			String query = pactRecMainSKHTService.checkPactRecMainSKHTState(listVo, state);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactRecDetSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactRecDetSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactRecMainSKHTService.queryPactRecDetSKHT(mapVo);
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
	@RequestMapping(value = "/queryPactRecPlanSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactRecPlanSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactRecMainSKHTService.queryPactRecPlanSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
