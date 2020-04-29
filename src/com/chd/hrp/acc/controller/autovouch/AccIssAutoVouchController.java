package com.chd.hrp.acc.controller.autovouch;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.autovouch.AccIssAutoVouchService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

@Controller
public class AccIssAutoVouchController extends BaseController {

	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;

	@Resource(name = "accIssAutoVouchService")
	private final AccIssAutoVouchService accIssAutoVouchService = null;

	/**
	 * <BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/issautovouch/accIssAutoVouchMainPage", method = RequestMethod.GET)
	public String accIssAutoVouchMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("p029", MyConfig.getSysPara("029"));
		String reMaxVouchDate = superVouchService.queryMaxVouchDate(mapVo);
		mode.addAttribute("vouch_date", reMaxVouchDate);
		return "hrp/acc/autovouch/issautovouch/accIssAutoVouchMain";
	}

	/**
	 * <BR>
	 * 查询表头
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/issautovouch/queryIssAutoVouchHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIssAutoVouchHead(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String reJson = "";
		try {
			 reJson=accIssAutoVouchService.queryIssAutoVouchHead(mapVo);
		} catch (Exception e) {
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}

		return JSONObject.parseObject(reJson);
	}

	@RequestMapping(value = "/hrp/acc/autovouch/issautovouch/queryIssAutoVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIssAutoVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String reJson = "";
		try {
			reJson=accIssAutoVouchService.queryIssAutoVouch(getPage(mapVo));
		} catch (Exception e) {
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}

		return JSONObject.parseObject(reJson);
	}




	// 查询自动凭证json
	@RequestMapping(value = "/hrp/acc/autovouch/issautovouch/queryIssVouchJsonByBusi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIssVouchJsonByBusi(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String reJson = null;
		try {
			reJson = accIssAutoVouchService.queryVouchJsonByBusi(mapVo);
		} catch (Exception e) {
			
			reJson = "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
		return JSONObject.parseObject(reJson);

	}
}
