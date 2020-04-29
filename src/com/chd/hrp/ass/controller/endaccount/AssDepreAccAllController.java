package com.chd.hrp.ass.controller.endaccount;

import java.util.HashMap;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.depre.acc.AssDepreAccAllService;

@Controller
public class AssDepreAccAllController extends BaseController {

	@Resource(name = "assDepreAccAllService")
	private final AssDepreAccAllService assDepreAccAllService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assendaccount/assDepreAccMainPage", method = RequestMethod.GET)
	public String assDepreAccMainPage(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mode.addAttribute("ass_05005", MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assendaccount/assDepreAccMain";
	}

	// 查询计提折旧
	@RequestMapping(value = "/hrp/ass/assendaccount/queryAssDepreAccInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreAccInfo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assDepreAccInfo = assDepreAccAllService.queryAssDepreAccInfo(getPage(mapVo));

		return JSONObject.parseObject(assDepreAccInfo);
	}

}
