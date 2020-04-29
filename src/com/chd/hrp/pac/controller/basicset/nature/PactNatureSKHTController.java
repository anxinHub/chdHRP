package com.chd.hrp.pac.controller.basicset.nature;

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
import com.chd.hrp.pac.entity.basicset.nature.PactNatureEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.nature.PactNatureSKHTService;

/**
 * 收款合同树形
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:04:35
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/nature/SKHT/")
public class PactNatureSKHTController extends BaseController {

	@Resource(name = "pactNatureSKHTService")
	private PactNatureSKHTService pactNatureSKHTService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/PactNatureSKHTMainPage")
	public String toPactNatureSKHTMainPage() {
		return "hrp/pac/basicset/nature/SKHT/pactNatureSKHTMain";
	}

	@RequestMapping(value = "/pactNatureSKHTAddPage")
	public String toPactNatureSKHTAddPage() {
		return "hrp/pac/basicset/nature/SKHT/pactNatureSKHTAdd";
	}

	@RequestMapping(value = "/pactNatureSKHTEditPage", method = RequestMethod.GET)
	public String toPactNatureSKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		PactNatureEntity entity = pactNatureSKHTService.querySKHTNatureByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("nature_code", entity.getNature_code());
			mode.addAttribute("nature_name", entity.getNature_name());
			mode.addAttribute("is_stop", entity.getIs_stop());
			mode.addAttribute("note", entity.getNote());
		}
		return "hrp/pac/basicset/nature/SKHT/pactNatureSKHTEdit";
	}

	@RequestMapping(value = "/querySKHTNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySKHTNature(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactNatureSKHTService.querySKHTNature(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/addSKHTNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSKHTNature(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactNatureSKHTService.addSKHTNature(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/updateSKHTNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSKHTNature(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String object = (String) mapVo.get("check");
			if ("false".equals(object)) {
				String a = pactDeleteService.isExistsDataByTable("PACT_NATURE_SKHT", mapVo.get("nature_code"));
				if (a != null) {
					return JSONObject.parseObject("{\"msg\":\"数据存在.\"}");
				}
				return JSONObject.parseObject("{\"msg\":\"\"}");
			}
			String query = pactNatureSKHTService.updateSKHTNature(mapVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/deleteSKHTNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSKHTNature(@RequestParam String paramVo, Model mode) {
		try {
			List<PactNatureEntity> listVo = JSONArray.parseArray(paramVo, PactNatureEntity.class);

			StringBuffer buffer = new StringBuffer();
			for (PactNatureEntity pactNatureEntity : listVo) {
				buffer.append(pactNatureEntity.getNature_code()).append("','");
			}
			buffer.delete(buffer.length()-3, buffer.length());
			String queryNatureExists = pactDeleteService.isExistsDataByTable("PACT_NATURE_SKHT", buffer);
			if (queryNatureExists != null) {
				queryNatureExists = queryNatureExists.substring(0, queryNatureExists.length() - 1);
				return JSONObject.parseObject("{\"error\":\"该数据已在" + queryNatureExists + "中使用，不可删除\"}");
			}

			String query = pactNatureSKHTService.deleteSKHTNature(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
