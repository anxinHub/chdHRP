package com.chd.hrp.acc.controller.autovouch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.chd.hrp.acc.service.autovouch.AccAutoVouchMaintainService;

@Controller
public class AccAutoVouchMaintainConttroller extends BaseController {
	private static Logger logger = Logger.getLogger(AccAutoVouchMaintainConttroller.class);

	@Resource(name = "accAutoVouchMaintainService")
	private final AccAutoVouchMaintainService accAutoVouchMaintainService = null;

	/**
	 * 自动凭证维护<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/maintain/accAutoVouchMaintainMainPage", method = RequestMethod.GET)
	public String accAutoVouchMaintainMainPage(Model mode) throws Exception {
		mode.addAttribute("a005", MyConfig.getSysPara("005"));
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		return "hrp/acc/autovouch/maintain/accAutoVouchMaintainMain";
	}

	/**
	 * <BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/maintain/queryAccAutoVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccAutoVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}
		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accBankCheckTell = accAutoVouchMaintainService.queryAccAutoVouch(getPage(mapVo));

		return JSONObject.parseObject(accBankCheckTell);

	}

	/**
	 * <BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/maintain/deleteAccAutoVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccAutoVouch(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assApplyDeptJson;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("user_id", ids[3]);

			mapVo.put("log_table", ids[4]);
			
			mapVo.put("vouch_id", ids[5]);
			listVo.add(mapVo);
		}

		try{
			assApplyDeptJson = accAutoVouchMaintainService.deleteAccAutoVouch(listVo);
		}catch(Exception e){
			assApplyDeptJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		

		return JSONObject.parseObject(assApplyDeptJson);

	}
}
