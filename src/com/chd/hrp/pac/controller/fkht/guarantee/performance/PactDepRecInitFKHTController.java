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
import com.chd.hrp.pac.entity.fkht.guarantee.PactDepRecFKHTEntity;
import com.chd.hrp.pac.service.fkht.guarantee.performance.PactDepRecFKHTService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

/**
 * 期初保证金收款
 * 
 * @author haotong
 * @date 2018年9月12日 下午4:10:24
 */
@Controller
@RequestMapping(value = "/hrp/pac/fkht/guarantee/performance/init")
public class PactDepRecInitFKHTController extends BaseController {

	@Resource(name = "pactDepRecFKHTService")
	private PactDepRecFKHTService pactDepRecFKHTService;

	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;

	@RequestMapping(value = "/pactDepRecInitFKHTMainPage", method = RequestMethod.GET)
	public String toPactInitBondSKMainPage() {
		return "hrp/pac/fkht/guarantee/performance/init/PactDepRecInitFKHTMain";
	}

	@RequestMapping(value = "/toPactDepRecInitFKHTAddPage", method = RequestMethod.GET)
	public String toPactDepRecFKHTAddPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("is_init", mapVo.get("is_init"));
		return "hrp/pac/fkht/guarantee/performance/init/pactDepRecInitFKHTAdd";
	}

	@RequestMapping(value = "/toPactDepRecInitFKHTEditPage", method = RequestMethod.GET)
	public String toPactDepRecFKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactDepRecFKHTEntity entity = pactDepRecFKHTService.queryPactDepRecByRecCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("ret_plan_date", format.format(entity.getRet_plan_date()));
			mode.addAttribute("date", format.format(entity.getDate()));
			mode.addAttribute("make_date", format.format(entity.getMake_date()));
			if (entity.getConfirm_date() != null) {
				mode.addAttribute("confirm_date", format.format(entity.getConfirm_date()));
			}
		}
		return "hrp/pac/fkht/guarantee/performance/init/pactDepRecInitFKHTEdit";
	}

	/**
	 * 主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactDepRecFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactDepRecFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			String query = pactDepRecFKHTService.queryPactDepRecFKHT(getPage(mapVo));
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
	@RequestMapping(value = "/addPactDepRecFKHT", method = RequestMethod.POST)
	public Map<String, Object> addPactDepRecFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "01");
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));

			String no = pactNoRuleService.getNo("PACT_DEP_REC_FKHT", mapVo);
			mapVo.put("rec_code", no);

			String query = pactDepRecFKHTService.addPactDepRecFKHT(mapVo);
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
	@RequestMapping(value = "/updatePactDepRecFKHT", method = RequestMethod.POST)
	public Map<String, Object> updatePactDepRecFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			String query = pactDepRecFKHTService.updatePactDepRecFKHT(mapVo);
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
	@RequestMapping(value = "/deletePactDepRecFKHT", method = RequestMethod.POST)
	public Map<String, Object> deletePactDepRecFKHT(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDepRecFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactDepRecFKHTEntity.class);

			String query = pactDepRecFKHTService.deletePactDepRecFKHT(listVo);
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
	@RequestMapping(value = "/checkPactDepRecFKHT", method = RequestMethod.POST)
	public Map<String, Object> checkPactDepRecFKHT(@RequestParam(name = "mapVo") String mapVo,
			@RequestParam(name = "state") String state, Model mode) {
		try {
			List<String> listVo = JSONArray.parseArray(mapVo, String.class);
			String query = pactDepRecFKHTService.checkPactDepRecFKHT(listVo, state);
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
	@RequestMapping(value = "/queryPactFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			String query = pactDepRecFKHTService.queryPactFKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
