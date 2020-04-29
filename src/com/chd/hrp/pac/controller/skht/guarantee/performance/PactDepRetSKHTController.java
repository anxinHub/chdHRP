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
import com.chd.hrp.pac.entity.skht.guarantee.PactDepRetSKHTEntity;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;
import com.chd.hrp.pac.service.skht.guarantee.performance.PactDepRetSKHTService;

/**
 * 保证金退款
 * 
 * @author haotong
 * @date 2018年9月12日 下午4:10:24
 */
@Controller
@RequestMapping(value = "/hrp/pac/skht/guarantee/performance/refund")
public class PactDepRetSKHTController extends BaseController {

	@Resource(name = "pactDepRetSKHTService")
	private PactDepRetSKHTService pactDepRetSKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@RequestMapping(value = "/pactDepRetSKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/skht/guarantee/performance/refund/pactDepRetSKHTMain";
	}

	@RequestMapping(value = "/toPactDepRetSKHTAddPage", method = RequestMethod.GET)
	public String toPactDepRetSKHTAddPage() {
		return "hrp/pac/skht/guarantee/performance/refund/pactDepRetSKHTAdd";
	}

	@RequestMapping(value = "/toPactDepRetSKHTEditPage", method = RequestMethod.GET)
	public String toPactDepRetSKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactDepRetSKHTEntity entity = pactDepRetSKHTService.queryPactDepRetByRetCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("date", format.format(entity.getDate()));
		}
		return "hrp/pac/skht/guarantee/performance/refund/pactDepRetSKHTEdit";
	}

	/**
	 * 主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactDepRetSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactDepRetSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDepRetSKHTService.queryPactDepRetSKHT(getPage(mapVo));
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
	@RequestMapping(value = "/addPactDepRetSKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactDepRetSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "01");
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));
			mapVo.put("ret_code", pactNoRuleService.getNo("PACT_DEP_RET_SKHT", mapVo));

			String query = pactDepRetSKHTService.addPactDepRetSKHT(mapVo);
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
	@RequestMapping(value = "/queryPactSKHTForRet", method = RequestMethod.POST)
	public Map<String, Object> queryPactSKHTForRet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDepRetSKHTService.queryPactSKHTForRet(mapVo);
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
	@RequestMapping(value = "/queryPactSKHTSelectForRet", method = RequestMethod.POST)
	public String queryPactSKHTSelectForRet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state_code", "12");
			mapVo.put("deposit_type", "01");
			return pactDepRetSKHTService.queryPactSKHTSelectForRet(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
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
	@RequestMapping(value = "/updatePactDepRetSKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactDepRetSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDepRetSKHTService.updatePactDepRetSKHT(mapVo);
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
	@RequestMapping(value = "/deletePactDepRetSKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactDepRetSKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDepRetSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactDepRetSKHTEntity.class);

			String query = pactDepRetSKHTService.deletePactDepRetSKHT(listVo);
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
	@RequestMapping(value = "/checkPactDepRetSKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactDepRetSKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<String> listVo = JSONArray.parseArray(mapVo, String.class);

			String query = pactDepRetSKHTService.checkPactDepRetSKHT(listVo, state);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
