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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.autovouch.AccMatAutoVouchService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
@Controller
public class AccMatAutoVouchController extends BaseController{
	private static Logger logger = Logger.getLogger(AccMatAutoVouchController.class);
	
	@Resource(name = "accMatAutoVouchService")
	private final AccMatAutoVouchService accMatAutoVouchService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	/**
	 * <BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/matautovouch/accMatAutoVouchMainPage", method = RequestMethod.GET)
	public String accMatAutoVouchMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("p029", MyConfig.getSysPara("029"));
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		mode.addAttribute("vouch_date", reMaxVouchDate);
		return "hrp/acc/autovouch/matautovouch/accMatAutoVouchMain";
	}
	
	/**
	 * <BR>
	 * 查询表头
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/matautovouch/queryMatAutoVouchHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAutoVouchHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String reJson="";
		try {
			reJson=accMatAutoVouchService.queryMatAutoVouchHead(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}

		return JSONObject.parseObject(reJson);
	}
	
	
	@RequestMapping(value = "/hrp/acc/autovouch/matautovouch/queryMatAutoVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAutoVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			reJson=accMatAutoVouchService.queryMatAutoVouch(getPage(mapVo));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}

		return JSONObject.parseObject(reJson);
	}
	
	
	//查询自动凭证json
	@RequestMapping(value = "/hrp/acc/autovouch/matautovouch/queryVouchJsonByBusi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryVouchJsonByBusi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			//reJson=accMatAutoVouchService.queryVouchJsonByBusi(mapVo);
			reJson=accMatAutoVouchService.saveAutoVouch(mapVo);//用新的处理方式，直接插自动凭证表
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}	
	
	//凭证说明页面
	@RequestMapping(value = "/hrp/acc/autovouch/vouchNotePage", method = RequestMethod.GET)
	public String vouchNotePage(@RequestParam Map<String, Object> reMap,Model mode,String vouch_id) throws Exception {
		mode.addAttribute("vouch_id", vouch_id);
		return "hrp/acc/autovouch/matautovouch/vouchNote";
	}
		
	
	//检查自动凭证
	@RequestMapping(value = "/hrp/acc/autovouch/checkAutoVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAutoVouch(@RequestParam Map<String, Object> map, Model mode)throws Exception {

		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
	
		try{
			String accVouchJson = accMatAutoVouchService.checkAutoVouch(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
}
