package com.chd.hrp.pac.controller.basicset.warning;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.basicset.warning.PactWarnSetFKXYService;

/**
 * 付款协议预警
 * 
 * @author haotong
 * @date 2018年8月23日 下午3:10:29
 */
@Controller
@RequestMapping(value = "/hrp/pac/basicset/warning")
public class PactWarnSetFKXYController extends BaseController {

	@Resource(name = "pactWarnSetFKXYService")
	private PactWarnSetFKXYService pactWarnSetFKXYService;

	@RequestMapping(value = "/pactWarnSetFKXYMainPage")
	public String toWarnSetFKHTMainPage() {
		return "hrp/pac/basicset/warning/pactWarnSetFKXYMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactWarnSetFKXY")
	public Map<String, Object> queryPactWarnSetFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());

			String query = pactWarnSetFKXYService.queryPactWarnSetFKXY(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatePactWarnSetFKXY")
	public Map<String, Object> updatePactWarnSetFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String query = pactWarnSetFKXYService.updatePactWarnSetFKXY(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
