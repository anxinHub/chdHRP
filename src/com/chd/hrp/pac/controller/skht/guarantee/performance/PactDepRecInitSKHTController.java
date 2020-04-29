package com.chd.hrp.pac.controller.skht.guarantee.performance;

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
import com.chd.hrp.pac.entity.skht.guarantee.PactDepRecSKHTEntity;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.chd.hrp.pac.service.skht.guarantee.performance.PactDepRecSKHTService;

/**
 * 期初保证金收款
 * 
 * @author haotong
 * @date 2018年9月12日 下午4:10:24
 */
@Controller
@RequestMapping(value = "/hrp/pac/skht/guarantee/performance/init")
public class PactDepRecInitSKHTController extends BaseController {

	@Resource(name = "pactDepRecSKHTService")
	private PactDepRecSKHTService pactDepRecSKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@RequestMapping(value = "/pactDepRecInitSKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/skht/guarantee/performance/init/PactDepRecInitSKHTMain";
	}

	@RequestMapping(value = "/toPactDepRecSKHTAddPage", method = RequestMethod.GET)
	public String toPactDepRecSKHTAddPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("is_init", mapVo.get("is_init"));
		return "hrp/pac/skht/guarantee/performance/init/pactDepRecSKHTAdd";
	}

	@RequestMapping(value = "/toPactDepRecSKHTEditPage", method = RequestMethod.GET)
	public String toPactDepRecSKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactDepRecSKHTEntity entity = pactDepRecSKHTService.queryPactDepRecByRecCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("ret_plan_date", format.format(entity.getRet_plan_date()));
			mode.addAttribute("date", format.format(entity.getPay_date()));
			mode.addAttribute("make_date", format.format(entity.getMake_date()));
			if (entity.getConfirm_date() != null) {
				mode.addAttribute("confirm_date", format.format(entity.getConfirm_date()));
			}
		}
		return "hrp/pac/skht/guarantee/performance/init/pactDepRecSKHTEdit";
	}

	/**
	 * 主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactDepRecSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactDepRecSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDepRecSKHTService.queryPactDepRecSKHT(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 添加
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addPactDepRecSKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactDepRecSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "01");
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));

			mapVo.put("pay_code", pactNoRuleService.getNo("PACT_DEP_REC_SKHT", mapVo));

			String query = pactDepRecSKHTService.addPactDepRecSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 更新
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePactDepRecSKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactDepRecSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDepRecSKHTService.updatePactDepRecSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePactDepRecSKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactDepRecSKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDepRecSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactDepRecSKHTEntity.class);

			String query = pactDepRecSKHTService.deletePactDepRecSKHT(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 审核
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkPactDepRecSKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactDepRecSKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<String> listVo = JSONArray.parseArray(mapVo, String.class);
			String query = pactDepRecSKHTService.checkPactDepRecSKHT(listVo, state);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询付款合同
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDepRecSKHTService.queryPactSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
