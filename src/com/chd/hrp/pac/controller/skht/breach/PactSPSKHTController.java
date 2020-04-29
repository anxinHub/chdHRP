package com.chd.hrp.pac.controller.skht.breach;

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
import com.chd.hrp.pac.entity.skht.breach.PactSPSKHTEntity;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.chd.hrp.pac.service.skht.breach.PactSPSKHTService;

@Controller
@RequestMapping(value = "/hrp/pac/skht/break/sp")
public class PactSPSKHTController extends BaseController {

	@Resource(name = "pactSPSKHTService")
	private PactSPSKHTService pactSPSKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@RequestMapping(value = "/pactSPSKHTMainPage", method = RequestMethod.GET)
	public String toPactSPSKHTMainPage() {
		return "hrp/pac/skht/break/sp/pactSPSKHTMain";
	}

	@RequestMapping(value = "/toPactSPSKHTAddPage", method = RequestMethod.GET)
	public String toPactSPSKHTAddPage() {
		return "hrp/pac/skht/break/sp/pactSPSKHTAdd";
	}

	@RequestMapping(value = "/toPactSPSKHTEditPage", method = RequestMethod.GET)
	public String toPactSPSKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactSPSKHTEntity entity = pactSPSKHTService.queryPactSPBySPCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
		}
		return "hrp/pac/skht/break/sp/pactSPSKHTEdit";
	}

	/**
	 * 主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactSPSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactSPSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactSPSKHTService.queryPactSPSKHT(getPage(mapVo));
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
	@RequestMapping(value = "/addPactSPSKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactSPSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "01");
			mapVo.put("sup_id", mapVo.get("sup_no"));
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));
			mapVo.put("sp_code", pactNoRuleService.getNo("PACT_SP_SKHT", mapVo));

			pactSPSKHTService.addPactSPSKHT(mapVo);
			return JSONObject.parseObject("{\"msg\":\"添加成功.\",\"state\":\"true\"}");
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
	@RequestMapping(value = "/updatePactSPSKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactSPSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			pactSPSKHTService.updatePactSPSKHT(mapVo);
			return JSONObject.parseObject("{\"msg\":\"更新成功.\",\"state\":\"true\"}");
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
	@RequestMapping(value = "/deletePactSPSKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactSPSKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactSPSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactSPSKHTEntity.class);

			String query = pactSPSKHTService.deletePactSPSKHT(listVo);
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
	@RequestMapping(value = "/checkPactSPSKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactSPSKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<PactSPSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactSPSKHTEntity.class);

			String query = pactSPSKHTService.checkPactSPSKHT(listVo, state);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
