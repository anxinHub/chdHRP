package com.chd.hrp.acc.controller.autovouch;

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
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.autovouch.AccMedAutoVouchService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
@Controller
public class AccMedAutoVouchController extends BaseController{
	private static Logger logger = Logger.getLogger(AccMedAutoVouchController.class);
	
	@Resource(name = "accMedAutoVouchService")
	private final AccMedAutoVouchService accMedAutoVouchService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	/**
	 * 【自动凭证-自动凭证生成-药品管理凭证】：主页面<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/medautovouch/accMedAutoVouchMainPage", method = RequestMethod.GET)
	public String accMedAutoVouchMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("p029", MyConfig.getSysPara("029"));
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		mode.addAttribute("vouch_date", reMaxVouchDate);
		
		
		return "hrp/acc/autovouch/medautovouch/accMedAutoVouchMain";
	}
	
	/**
	 * <BR>
	 * 查询表头
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/medautovouch/queryMedAutoVouchHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAutoVouchHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accBankCheckTell = accMedAutoVouchService.queryMedAutoVouchHead(mapVo);

		return JSONObject.parseObject(accBankCheckTell);
	}
	
	
	@RequestMapping(value = "/hrp/acc/autovouch/medautovouch/queryMedAutoVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAutoVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}
		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		String reJson=null;
		try {
			reJson=accMedAutoVouchService.queryMedAutoVouch(getPage(mapVo));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}

		return JSONObject.parseObject(reJson);

	}
	
	
	//查询自动凭证json
	@RequestMapping(value = "/hrp/acc/autovouch/medautovouch/queryVouchJsonByMed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryVouchJsonByMed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			//reJson=accMedAutoVouchService.queryVouchJsonByMed(mapVo);
			reJson=accMedAutoVouchService.saveAutoVouch(mapVo);//用新的处理方式，直接插自动凭证表
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}	
	
	//查询药品入库退货明细数据页面
	@RequestMapping(value = "/hrp/acc/autovouch/medautovouch/medInBackPage", method = RequestMethod.GET)
	public String medInBackPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/autovouch/medautovouch/medInBack";
	}
	
	//查询药品入库退货明细数据
	@RequestMapping(value = "/hrp/acc/autovouch/medautovouch/queryMedInBackDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInBackDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=accMedAutoVouchService.queryMedInBackDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}	
}
