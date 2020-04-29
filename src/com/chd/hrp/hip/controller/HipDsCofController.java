/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.hip.controller;

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
import com.chd.base.SessionManager;
import com.chd.hrp.hip.entity.HipDsCof;
import com.chd.hrp.hip.service.HipDsCofService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HipDsCofController extends BaseController {
	private static Logger logger = Logger.getLogger(HipDsCofController.class);

	@Resource(name = "hipDsCofService")
	private final HipDsCofService hipDsCofService = null;

	@RequestMapping(value = "/hrp/hip/hipdscof/hipDsCofMainPage", method = RequestMethod.GET)
	public String hipDsCofMainPage(Model mode) throws Exception {
		
		if(SessionManager.getCopyCode() == null || "".equals(SessionManager.getCopyCode())){
			
			mode.addAttribute("copy_code", "copy_code");

		}
		
		return "hrp/hip/hipdscof/hipDsCofMain";
	}

	@RequestMapping(value = "/hrp/hip/hipdscof/queryHipDsCof", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipDsCof(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str = hipDsCofService.queryHipDsCof(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipdscof/addHipDsCof", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipDsCof(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str = hipDsCofService.addHipDsCof(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipdscof/updateHipDsCofPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHipDsCofPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		HipDsCof hdc = hipDsCofService.queryHipDsCofByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		sb.append("\"ds_code\":\"" + hdc.getDs_code()+ "\",");

		sb.append("\"ds_name\":\"" + hdc.getDs_name() + "\",");
		
		sb.append("\"ds_note\":\"" + hdc.getDs_note() + "\",");
		
		sb.append("\"db_user\":\"" + hdc.getDb_user() + "\",");
		
		sb.append("\"url_str\":\"" + hdc.getUrl_str() + "\",");
		
		sb.append("\"db_psw\":\"" + hdc.getDb_psw() + "\"");

		sb.append("}");

		return JSONObject.parseObject(sb.toString());

	}

	@RequestMapping(value = "/hrp/hip/hipdscof/deleteHipDsCof", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipDsCof(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String apt = hipDsCofService.delHipDsCof(mapVo);

		return JSONObject.parseObject(apt);

	}

}
