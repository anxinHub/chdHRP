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
import com.chd.hrp.pac.entity.fkht.breach.PactBreakFKHTEntity;
import com.chd.hrp.pac.service.fkht.breach.PactBreakFKHTService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

@Controller
@RequestMapping(value = "/hrp/pac/fkht/break/break")
public class PactBreakFKHTController extends BaseController {

	@Resource(name = "pactBreakFKHTService")
	private PactBreakFKHTService pactBreakFKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@RequestMapping(value = "/pactBreakFKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/fkht/break/break/pactBreakFKHTMain";
	}

	@RequestMapping(value = "/toPactBreakFKHTAddPage", method = RequestMethod.GET)
	public String toPactBreakFKHTAddPage() {
		return "hrp/pac/fkht/break/break/pactBreakFKHTAdd";
	}

	@RequestMapping(value = "/toPactBreakFKHTEditPage", method = RequestMethod.GET)
	public String toPactBreakFKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactBreakFKHTEntity entity = pactBreakFKHTService.queryPactBreakByBreakCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
		}
		return "hrp/pac/fkht/break/break/pactBreakFKHTEdit";
	}

	/**
	 * 主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactBreakFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactBreakFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactBreakFKHTService.queryPactBreakFKHT(getPage(mapVo));
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
	@RequestMapping(value = "/addPactBreakFKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactBreakFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "01");
			mapVo.put("sup_id", mapVo.get("sup_no"));
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));
			mapVo.put("break_code", pactNoRuleService.getNo("PACT_BREAK_FKHT", mapVo));

			String query = pactBreakFKHTService.addPactBreakFKHT(mapVo);
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
	@RequestMapping(value = "/updatePactBreakFKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactBreakFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactBreakFKHTService.updatePactBreakFKHT(mapVo);
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
	@RequestMapping(value = "/deletePactBreakFKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactBreakFKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactBreakFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactBreakFKHTEntity.class);

			String query = pactBreakFKHTService.deletePactBreakFKHT(listVo);
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
	@RequestMapping(value = "/checkPactBreakFKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactBreakFKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<PactBreakFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactBreakFKHTEntity.class);

			String query = pactBreakFKHTService.checkPactBreakFKHT(listVo, state);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
