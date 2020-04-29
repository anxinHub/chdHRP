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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.basicset.common.PactSelectService;
import com.chd.hrp.pac.service.fkht.payment.PactCreatePayMentFKHTService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

@Controller
@RequestMapping(value = "/hrp/pac/fkht/payment/create")
public class PactCreatPayMainFKHTController extends BaseController {

	@Resource(name = "pactCreatePayMentFKHTService")
	private PactCreatePayMentFKHTService pactCreatePayMentFKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@Resource(name = "pactSelectService")
	private PactSelectService pactSelectService;

	@RequestMapping(value = "/pactCreatPayMainFKHTMainPage")
	public String toPactPayMainInitFKHTMainPage() {
		return "hrp/pac/fkht/payment/create/pactCreatPayMainFKHTMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryCreatPactPayMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryCreatPactPayMainFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String query = pactCreatePayMentFKHTService.queryCreatPactPayMainFKHT(getPage(mapVo));
		return JSONObject.parseObject(query);
	}

	@ResponseBody
	@RequestMapping(value = "/addCreatPactPayMainFKHT", method = RequestMethod.POST)
	public Map<String, Object> addCreatPactPayMainFKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {});
			for (Map<String, Object> map : listVo) {
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("state", "01");
				map.put("sup_id", map.get("sup_no"));
				map.put("pay_money", map.get("plan_money"));
				map.put("pay_date", map.get("plan_date"));
				map.put("maker", SessionManager.getUserId());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				map.put("make_date", format.format(new Date()));
				String pay_code = pactNoRuleService.getNo("PACT_PAY_MAIN_FKHT", map);
				map.put("pay_code", pay_code);
				map.put("detail_id", map.get("plan_det_id"));

				List<Map<String, Object>> list2 = pactSelectService.queryPayTypeDictBySource(map);
				map.put("pay_way", list2.get(0).get("id"));

				// 设置为null或null，避免在sql中遍历时无法找到此字段报错
				map.put("checker", null);
				map.put("check_date", null);
				map.put("confirmer", null);
				map.put("confirm_date", null);
				map.put("note", null);
				map.put("cheq_no", null);
			}
			String add = pactCreatePayMentFKHTService.addCreatPactPayMainFKHT(listVo);
			return JSONObject.parseObject(add);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
