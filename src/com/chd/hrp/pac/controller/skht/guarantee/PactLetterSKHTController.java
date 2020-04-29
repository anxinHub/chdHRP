package com.chd.hrp.pac.controller.skht.guarantee;

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
import com.chd.hrp.pac.entity.skht.guarantee.PactLetterSKHTEntity;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.chd.hrp.pac.service.skht.guarantee.PactLetterSKHTService;

@Controller
@RequestMapping(value = "/hrp/pac/skht/guarantee/letter")
public class PactLetterSKHTController extends BaseController {

	@Resource(name = "pactLetterSKHTService")
	private PactLetterSKHTService pactLetterSKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@RequestMapping(value = "/pactLetterSKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/skht/guarantee/letter/pactLetterSKHTMain";
	}

	@RequestMapping(value = "/toPactLetterSKHTAddPage", method = RequestMethod.GET)
	public String toPactLetterSKHTAddPage() {
		return "hrp/pac/skht/guarantee/letter/pactLetterSKHTAdd";
	}

	@RequestMapping(value = "/toPactLetterSKHTEditPage", method = RequestMethod.GET)
	public String toPactLetterSKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactLetterSKHTEntity entity = pactLetterSKHTService.queryPactLetterByLetterCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
		}
		return "hrp/pac/skht/guarantee/letter/pactLetterSKHTEdit";
	}

	/**
	 * 主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactLetterSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactLetterSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactLetterSKHTService.queryPactLetterSKHT(getPage(mapVo));
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
	@RequestMapping(value = "/addPactLetterSKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactLetterSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("letter_state", "01");
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));

			String query = pactLetterSKHTService.addPactLetterSKHT(mapVo);
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
	@RequestMapping(value = "/updatePactLetterSKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactLetterSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactLetterSKHTService.updatePactLetterSKHT(mapVo);
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
	@RequestMapping(value = "/deletePactLetterSKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactLetterSKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactLetterSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactLetterSKHTEntity.class);

			String query = pactLetterSKHTService.deletePactLetterSKHT(listVo);
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
	@RequestMapping(value = "/checkPactLetterSKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactLetterSKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<PactLetterSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactLetterSKHTEntity.class);

			String query = pactLetterSKHTService.checkPactLetterSKHT(listVo, state);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询银行信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactBankDetailInfo", method = RequestMethod.POST)
	public Map<String, Object> queryPactBankDetailInfo(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String query = pactLetterSKHTService.queryPactBankDetailInfo(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询银行信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactSKHTSelectForLetter", method = RequestMethod.POST)
	public String queryPactSKHTSelectForLetter(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state_code", "12");
			mapVo.put("deposit_type", "02");
			mapVo.put("state", "3");

			return pactLetterSKHTService.queryPactSKHTSelectForLetter(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

}
