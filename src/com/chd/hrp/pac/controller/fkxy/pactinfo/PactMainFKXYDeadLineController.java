package com.chd.hrp.pac.controller.fkxy.pactinfo;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.fkxy.pactinfo.PactMainFKXYService;

@Controller
@RequestMapping(value = "/hrp/pac/fkxy/pactinfo/deadline")
public class PactMainFKXYDeadLineController extends BaseController {

	@Resource(name = "pactMainFKXYService")
	private PactMainFKXYService pactMainFKXYService;

	@RequestMapping(value = "/pactDeadLineFKXYMainPage", method = RequestMethod.GET)
	public String toPactMainFKXYDeadLinePage() {
		return "hrp/pac/fkxy/pactinfo/deadline/pactFKXYDeadlineMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactFKXYDeadLine", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("state", "3");
			mapVo.put("state_code", "12");

			String query = pactMainFKXYService.queryPactFKXYForDeadline(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
