package com.chd.hrp.pac.controller.fkht.guarantee.performance;

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
import com.chd.hrp.pac.entity.fkht.guarantee.PactDepRetFKHTEntity;
import com.chd.hrp.pac.service.fkht.guarantee.performance.PactDepRetFKHTService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

/**
 * 保证金退款
 * 
 * @author haotong
 * @date 2018年9月12日 下午4:10:24
 */
@Controller
@RequestMapping(value = "/hrp/pac/fkht/guarantee/performance/refund")
public class PactDepRetFKHTController extends BaseController {

	@Resource(name = "pactDepRetFKHTService")
	private PactDepRetFKHTService pactDepRetFKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@RequestMapping(value = "/pactDepRetFKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/fkht/guarantee/performance/refund/pactDepRetFKHTMain";
	}

	@RequestMapping(value = "/toPactDepRetFKHTAddPage", method = RequestMethod.GET)
	public String toPactDepRetFKHTAddPage() {
		return "hrp/pac/fkht/guarantee/performance/refund/pactDepRetFKHTAdd";
	}

	@RequestMapping(value = "/toPactDepRetFKHTEditPage", method = RequestMethod.GET)
	public String toPactDepRetFKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactDepRetFKHTEntity entity = pactDepRetFKHTService.queryPactDepRetByRetCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("date", format.format(entity.getDate()));
			mode.addAttribute("make_date", format.format(entity.getMake_date()));
		}
		return "hrp/pac/fkht/guarantee/performance/refund/pactDepRetFKHTEdit";
	}

	/**
	 * 主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactDepRetFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactDepRetFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDepRetFKHTService.queryPactDepRetFKHT(getPage(mapVo));
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
	@RequestMapping(value = "/addPactDepRetFKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactDepRetFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "01");
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));
			mapVo.put("ret_code", pactNoRuleService.getNo("PACT_DEP_RET_FKHT", mapVo));

			String query = pactDepRetFKHTService.addPactDepRetFKHT(mapVo);
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
	@RequestMapping(value = "/updatePactDepRetFKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactDepRetFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactDepRetFKHTService.updatePactDepRetFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactFKHTForRet", method = RequestMethod.POST)
	public Map<String, Object> queryPactFKHTForRet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactDepRetFKHTService.queryPactFKHTForRet(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactFKHTSelectForRet", method = RequestMethod.POST)
	public String queryPactFKHTSelectForRet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state_code", "12");
			mapVo.put("deposit_type", "01");
			return pactDepRetFKHTService.queryPactFKHTSelectForRet(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
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
	@RequestMapping(value = "/deletePactDepRetFKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactDepRetFKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDepRetFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactDepRetFKHTEntity.class);

			String query = pactDepRetFKHTService.deletePactDepRetFKHT(listVo);
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
	@RequestMapping(value = "/checkPactDepRetFKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactDepRetFKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<String> listVo = JSONArray.parseArray(mapVo, String.class);

			String query = pactDepRetFKHTService.checkPactDepRetFKHT(listVo, state);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
