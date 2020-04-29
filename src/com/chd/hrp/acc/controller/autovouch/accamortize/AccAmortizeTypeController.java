package com.chd.hrp.acc.controller.autovouch.accamortize;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.autovouch.accamortize.AccAmortizeTypeService;

@Controller
@RequestMapping(value = "/hrp/acc/autovouch/amortizetype")
public class AccAmortizeTypeController extends BaseController {

	@Resource(name = "accAmortizeTypeService")
	private AccAmortizeTypeService accAmortizeTypeService;

	@RequestMapping(value = "/amortizeTypeMainPage", method = RequestMethod.GET)
	public String amortizeCardMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/autovouch/amortizetype/amortizeTypeMain";
	}

	@RequestMapping(value = "/amortizeTypeAddPage", method = RequestMethod.GET)
	public String amortizeCardAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/acc/autovouch/amortizetype/amortizeTypeAdd";
	}

	@RequestMapping(value = "/amortizeTypeUpdatePage", method = RequestMethod.GET)
	public String amortizeTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> reJson = accAmortizeTypeService.queryAmortizeTypeByCode(mapVo);
		mode.addAllAttributes(reJson);

		return "hrp/acc/autovouch/amortizetype/amortizeTypeUpdate";
	}

	@ResponseBody
	@RequestMapping(value = "/queryAmortizeType", method = RequestMethod.POST)
	public Map<String, Object> queryAmortizeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accAmortizeTypeService.queryAmortizeType(getPage(mapVo));
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}

	}

	@ResponseBody
	@RequestMapping(value = "/saveAmortizeType", method = RequestMethod.POST)
	public Map<String, Object> saveAmortizeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accAmortizeTypeService.saveAmortizeType(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}

	}

	@ResponseBody
	@RequestMapping(value = "/updateAmortizeType", method = RequestMethod.POST)
	public Map<String, Object> updateAmortizeType(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accAmortizeTypeService.updateAmortizeType(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}

	}

	@ResponseBody
	@RequestMapping(value = "/deleteAmortizeType", method = RequestMethod.POST)
	public Map<String, Object> deleteAmortizeType(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			String reJson = accAmortizeTypeService.deleteAmortizeType(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/amortizeTypeSelect", method = RequestMethod.POST)
	public String amortizeTypeSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<Map<String, Object>> reJson = accAmortizeTypeService.amortizeTypeSelect(mapVo);
		return JSON.toJSONString(reJson);

	}
}
