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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.basicset.common.PactSelectService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.chd.hrp.pac.service.skht.payment.PactCreateRecMentSKHTService;

@Controller
@RequestMapping(value = "/hrp/pac/skht/payment/create")
public class PactCreatRecMainSKHTController extends BaseController {

	@Resource(name = "pactCreateRecMentSKHTService")
	private PactCreateRecMentSKHTService pactCreateRecMentSKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@Resource(name = "pactSelectService")
	private PactSelectService pactSelectService;

	@RequestMapping(value = "/pactCreatRecMainSKHTMainPage")
	public String toPactRecMainInitSKHTMainPage() {
		return "hrp/pac/skht/payment/create/pactCreatRecMainSKHTMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryCreatPactRecMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryCreatPactRecMainSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String query = pactCreateRecMentSKHTService.queryCreatPactRecMainSKHT(getPage(mapVo));
		return JSONObject.parseObject(query);
	}

	@ResponseBody
	@RequestMapping(value = "/addCreatPactRecMainSKHT", method = RequestMethod.POST)
	public Map<String, Object> addCreatPactRecMainSKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<Map<String, Object>> listVo = JSON.parseObject(mapVo, new TypeReference<List<Map<String, Object>>>() {});
			if (listVo.isEmpty()) {
				return JSONObject.parseObject( "{\"msg\":\"添加失败.\",\"state\":\"error\"}");
			}
			for (Map<String, Object> map : listVo) {
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("state", "01");
				List<Map<String,Object>> list = pactSelectService.queryHosCusDictSelect(map);
				map.put("cus_id", list.get(0).get("cus_id"));
				map.put("rec_money", map.get("plan_money"));
				map.put("rec_date", map.get("plan_date"));
				map.put("maker", SessionManager.getUserId());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				map.put("make_date", format.format(new Date()));
				String pay_code = pactNoRuleService.getNo("PACT_REC_MAIN_SKHT", map);
				map.put("rec_code", pay_code);
				map.put("detail_id", map.get("plan_detail_id"));

				List<Map<String, Object>> list2 = pactSelectService.queryPayTypeDictBySource(map);
				map.put("pay_way", list2.get(0).get("id"));

				// 设置为null或null，避免在sql中遍历时无法找到此字段报错
				map.put("emp_id", null);
				map.put("checker", null);
				map.put("check_date", null);
				map.put("confirmer", null);
				map.put("confirm_date", null);
				map.put("note", null);
				map.put("cheq_no", null);
			}

			String add = pactCreateRecMentSKHTService.addCreatPactRecMainSKHT(listVo);
			return JSONObject.parseObject(add);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
