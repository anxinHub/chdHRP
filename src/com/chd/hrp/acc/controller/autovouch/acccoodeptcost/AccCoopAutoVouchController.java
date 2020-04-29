package com.chd.hrp.acc.controller.autovouch.acccoodeptcost;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.autovouch.acccoodeptcost.AccCoopAutoVouchService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
@Controller
public class AccCoopAutoVouchController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCoopAutoVouchController.class);
	
	@Resource(name = "accCoopAutoVouchService")
	private final AccCoopAutoVouchService accCoopAutoVouchService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	/**
	 * <BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodepcost/autoVouchPage", method = RequestMethod.GET)
	public String autoVouchPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("p029", MyConfig.getSysPara("029"));
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		mode.addAttribute("vouch_date", reMaxVouchDate);
		return "hrp/acc/autovouch/acccoodepcost/autoVouch";
	}
	
	/**
	 * <BR>
	 * 查询表头
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodepcost/queryPubAutoVouchHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPubAutoVouchHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String reJson="";
		try {
			reJson=accCoopAutoVouchService.queryCoopAutoVouchHead(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}

		return JSONObject.parseObject(reJson);
	}
	
	
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodepcost/queryPubAutoVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPubAutoVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}
		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String reJson="";
		try {
			reJson=accCoopAutoVouchService.queryCoopAutoVouch(getPage(mapVo));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}

		return JSONObject.parseObject(reJson);
	}
	
	
	//查询自动凭证json
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodepcost/queryPubVouchJsonByBusi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPubVouchJsonByBusi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=accCoopAutoVouchService.saveAutoVouch(mapVo);//用新的处理方式，直接插自动凭证表
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}	
	
}
