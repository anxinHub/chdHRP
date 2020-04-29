package com.chd.hrp.pac.controller.fkht.breach;

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
import com.chd.hrp.pac.entity.fkht.breach.PactSPFKHTEntity;
import com.chd.hrp.pac.service.fkht.breach.PactSPFKHTService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

@Controller
@RequestMapping(value = "/hrp/pac/fkht/break/sp")
public class PactSPFKHTController extends BaseController {

	@Resource(name = "pactSPFKHTService")
	private PactSPFKHTService pactSPFKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@RequestMapping(value = "/pactSPFKHTMainPage", method = RequestMethod.GET)
	public String toPactSPFKHTMainPage() {
		return "hrp/pac/fkht/break/sp/pactSPFKHTMain";
	}

	@RequestMapping(value = "/toPactSPFKHTAddPage", method = RequestMethod.GET)
	public String toPactSPFKHTAddPage() {
		return "hrp/pac/fkht/break/sp/pactSPFKHTAdd";
	}

	@RequestMapping(value = "/toPactSPFKHTEditPage", method = RequestMethod.GET)
	public String toPactSPFKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactSPFKHTEntity entity = pactSPFKHTService.queryPactSPBySPCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
		}
		return "hrp/pac/fkht/break/sp/pactSPFKHTEdit";
	}

	/**
	 * 主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactSPFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactSPFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactSPFKHTService.queryPactSPFKHT(getPage(mapVo));
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
	@RequestMapping(value = "/addPactSPFKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactSPFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "01");
			mapVo.put("sup_id", mapVo.get("sup_no"));
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));
			mapVo.put("sp_code", pactNoRuleService.getNo("PACT_SP_FKHT", mapVo));

			String query = pactSPFKHTService.addPactSPFKHT(mapVo);
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
	@RequestMapping(value = "/updatePactSPFKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactSPFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactSPFKHTService.updatePactSPFKHT(mapVo);
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
	@RequestMapping(value = "/deletePactSPFKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactSPFKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactSPFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactSPFKHTEntity.class);

			String query = pactSPFKHTService.deletePactSPFKHT(listVo);
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
	@RequestMapping(value = "/checkPactSPFKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactSPFKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<PactSPFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactSPFKHTEntity.class);

			String query = pactSPFKHTService.checkPactSPFKHT(listVo, state);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
