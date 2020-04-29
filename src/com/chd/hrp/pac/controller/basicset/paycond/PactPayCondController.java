package com.chd.hrp.pac.controller.basicset.paycond;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
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
import com.chd.hrp.pac.entity.basicset.paycond.PactPayCondEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.paycond.PactPayCondService;

/**
 * 收付款条件
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:05:01
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/pacPayCond")
public class PactPayCondController extends BaseController {

	@Resource(name = "pactPayCondService")
	private PactPayCondService pactPayCondService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/PactPayCondMainPage")
	public String toPactPayCondMainPage() {
		return "hrp/pac/basicset/paycond/pactPayCondMain";
	}

	@RequestMapping(value = "/pactPayCondAdd")
	public String toPactNatureAdd() {
		return "hrp/pac/basicset/paycond/pactPayCondAdd";
	}

	@RequestMapping(value = "/queryPactPayCond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactPayCond(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPayCondService.queryPactPayCond(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/pactPayCondEdit", method = RequestMethod.GET)
	public String toPactNatureEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactPayCondEntity entity = pactPayCondService.queryPactPayCondByCode(mapVo);

		if (entity != null) {
			mode.addAttribute("cond_code", entity.getCond_code());
			mode.addAttribute("cond_name", entity.getCond_name());
			mode.addAttribute("spell_code", entity.getSpell_code());
			mode.addAttribute("wbx_code", entity.getWbx_code());
			mode.addAttribute("is_stop", entity.getIs_stop());
			mode.addAttribute("note", entity.getNote());
		}
		return "hrp/pac/basicset/paycond/pactPayCondEdit";
	}

	@RequestMapping(value = "/addPactPayCond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactPayCond(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactPayCondService.addPactPayCond(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/updatePactPayCond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePactPayCond(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactPayCondService.updatePactPayCond(mapVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/deletePactPayCond", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactPayCond(@RequestParam String paramVo, Model mode) {
		try {
			List<PactPayCondEntity> listVo = JSONArray.parseArray(paramVo, PactPayCondEntity.class);
			String query = pactPayCondService.deletePactPayCond(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
