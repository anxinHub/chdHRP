package com.chd.hrp.pac.controller.basicset.elsesub;

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
import com.chd.hrp.pac.entity.basicset.elsesub.PactElseSubDictEntity;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;
import com.chd.hrp.pac.service.basicset.elsesub.PactElseSubService;

/**
 * 其他标的物
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:03:04
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/elsesub")
public class PactElseSubController extends BaseController {

	@Resource(name = "pactElseSubService")
	private PactElseSubService pactElseSubService;

	@Resource(name = "pactDeleteService")
	private PactDeleteService pactDeleteService;

	@RequestMapping(value = "/PactElseSubMainPage")
	public String toPactElseSubMainPage() {
		return "hrp/pac/basicset/elsesub/pactElseSubMain";
	}

	@RequestMapping(value = "/pactElseSubAdd")
	public String toPactNatureAdd() {
		return "hrp/pac/basicset/elsesub/pactElseSubAdd";
	}

	@RequestMapping(value = "/queryPactElseSub", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPactElseSub(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_new", 0);

			String query = pactElseSubService.queryPactElseSub(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/pactElseSubEdit", method = RequestMethod.GET)
	public String toPactNatureEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactElseSubDictEntity entity = pactElseSubService.queryPactElseSubByCode(mapVo);
		if (entity != null) {
			mode.addAttribute("sub_name", entity.getSub_name());
			mode.addAttribute("sub_code", entity.getSub_code());
			mode.addAttribute("sub_id", entity.getSub_id());
			mode.addAttribute("sub_no", entity.getSub_no());
			mode.addAttribute("is_stop", entity.getIs_stop());
			mode.addAttribute("note", entity.getNote());
		}
		return "hrp/pac/basicset/elsesub/pactElseSubEdit";
	}

	@RequestMapping(value = "/addPactElseSub", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPactElseSub(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactElseSubService.addPactElseSub(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/updatePactElseSub", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePactElseSub(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String object = (String) mapVo.get("check");
			if ("false".equals(object)) {
				String a = pactDeleteService.isExistsDataByTable("PACT_ELSE_SUB", mapVo.get("sub_code"));
				if (a != null) {
					return JSONObject.parseObject("{\"msg\":\"数据存在.\"}");
				}
				return JSONObject.parseObject("{\"msg\":\"\"}");
			}
			String query = pactElseSubService.updatePactElseSub(mapVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/deletePactElseSub", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactElseSub(@RequestParam String paramVo, Model mode) {
		try {
			List<PactElseSubDictEntity> listVo = JSONArray.parseArray(paramVo, PactElseSubDictEntity.class);
			String query = pactElseSubService.deletePactElseSub(listVo);
			return JSONObject.parseObject(query);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
