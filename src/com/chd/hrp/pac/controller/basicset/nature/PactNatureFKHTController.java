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
import com.chd.hrp.pac.service.basicset.nature.PactNatureFKHTService;

/**
 * 付款合同性质
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:03:39
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/nature/FKHT")
public class PactNatureFKHTController extends BaseController {

	@Resource(name = "pactNatureFKHTService")
	private PactNatureFKHTService pactNatureFKHTService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/PactNatureFKHTMainPage")
	public String toPactNatureFKHTMainPage() {
		return "hrp/pac/basicset/nature/FKHT/pactNatureFKHTMain";
	}

	@RequestMapping(value = "/pactNatureFKHTAddPage")
	public String toPactNatureFKHTAddPage() {
		return "hrp/pac/basicset/nature/FKHT/pactNatureFKHTAdd";
	}

	@RequestMapping(value = "/pactNatureFKHTEditPage", method = RequestMethod.GET)
	public String toPactNatureFKHTEditPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		PactNatureEntity entity = pactNatureFKHTService.queryFKHTNatureByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("nature_code", entity.getNature_code());
			mode.addAttribute("nature_name", entity.getNature_name());
			mode.addAttribute("is_stop", entity.getIs_stop());
			mode.addAttribute("note", entity.getNote());
		}
		return "hrp/pac/basicset/nature/FKHT/pactNatureFKHTEdit";
	}

	@RequestMapping(value = "/queryFKHTNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFKHTNature(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactNatureFKHTService.queryFKHTNature(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/addFKHTNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addFKHTNature(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactNatureFKHTService.addFKHTNature(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/updateFKHTNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateFKHTNature(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String object = (String) mapVo.get("check");
			if ("false".equals(object)) {
				String a = pactDeleteService.isExistsDataByTable("PACT_NATURE_FKHT", mapVo.get("nature_code"));
				if (a != null) {
					return JSONObject.parseObject("{\"msg\":\"数据存在.\"}");
				}
				return JSONObject.parseObject("{\"msg\":\"\"}");
			}
			String query = pactNatureFKHTService.updateFKHTNature(mapVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/deleteFKHTNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFKHTNature(@RequestParam String paramVo, Model mode) {
		try {
			List<PactNatureEntity> listVo = JSONArray.parseArray(paramVo, PactNatureEntity.class);
			StringBuffer buffer = new StringBuffer();
			for (PactNatureEntity pactNatureEntity : listVo) {
				buffer.append(pactNatureEntity.getNature_code()).append("','");
			}
			buffer.delete(buffer.length()-3, buffer.length());
			String queryNatureExists = pactDeleteService.isExistsDataByTable("PACT_NATURE_FKHT", buffer);
			if (queryNatureExists != null) {
				queryNatureExists = queryNatureExists.substring(0, queryNatureExists.length() - 1);
				return JSONObject.parseObject("{\"error\":\"该数据已在" + queryNatureExists + "中使用，不可删除\"}");
			}
			String query = pactNatureFKHTService.deleteFKHTNature(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
