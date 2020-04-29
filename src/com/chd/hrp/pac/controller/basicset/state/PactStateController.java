package com.chd.hrp.pac.controller.basicset.state;

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
import com.chd.hrp.pac.entity.basicset.state.PactStateEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.state.PactStateService;

/**
 * 合同状态
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:05:35
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/state")
public class PactStateController extends BaseController {

	@Resource(name = "pactStateService")
	private PactStateService pactStateService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/PactStateMainPage")
	public String toPactStateMainPage() {
		return "hrp/pac/basicset/state/pactStateMain";
	}

	@RequestMapping(value = "/pactStateAdd")
	public String toPactNatureAdd() {
		return "hrp/pac/basicset/state/pactStateAdd";
	}

	@RequestMapping(value = "/queryPactState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactState(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactStateService.queryPactState(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/pactStateEdit", method = RequestMethod.GET)
	public String toPactNatureEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactStateEntity entity = pactStateService.queryPactStateByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("state_code", entity.getState_code());
			mode.addAttribute("state_name", entity.getState_name());
			mode.addAttribute("is_stop", entity.getIs_stop());
			mode.addAttribute("note", entity.getNote());
		}
		return "hrp/pac/basicset/state/pactStateEdit";
	}

	@RequestMapping(value = "/addPactState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactState(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_init", 0);

			String query = pactStateService.addPactState(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/updatePactState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePactState(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String object = (String) mapVo.get("check");
			if ("false".equals(object)) {
				String a = pactDeleteService.isExistsDataByTable("PACT_STATE", mapVo.get("state_code"));
				if (a != null) {
					return JSONObject.parseObject("{\"error\":\"该数据已在" + a + "中使用，不可更改\"}");
				}
				return JSONObject.parseObject("{\"msg\":\"\"}");
			}
			String query = pactStateService.updatePactState(mapVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/deletePactState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactState(@RequestParam String paramVo, Model mode) {
		try {
			List<PactStateEntity> listVo = JSONArray.parseArray(paramVo, PactStateEntity.class);

			StringBuffer buffer = new StringBuffer();
			for (PactStateEntity entity : listVo) {
				buffer.append(entity.getState_code()).append("','");
			}
			buffer.delete(buffer.length()-3, buffer.length());
			String exists = pactDeleteService.isExistsDataByTable("PACT_STATE", buffer);
			if (exists != null) {
				exists = exists.substring(0, exists.length() - 1);
				return JSONObject.parseObject("{\"error\":\"该数据已在" + exists + "中使用，不可删除\"}");
			}

			String query = pactStateService.deletePactState(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
