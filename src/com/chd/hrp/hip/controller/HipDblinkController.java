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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hip.entity.HipDblink;
import com.chd.hrp.hip.service.HipDblinkService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HipDblinkController extends BaseController {
	private static Logger logger = Logger.getLogger(HipDblinkController.class);

	@Resource(name = "HipDblinkService")
	private final HipDblinkService HipDblinkService = null;

	@RequestMapping(value = "/hrp/hip/hipdblink/hipDblinkMainPage", method = RequestMethod.GET)
	public String HipDblinkMainPage(Model mode) throws Exception {
		
		if(SessionManager.getCopyCode() == null || "".equals(SessionManager.getCopyCode())){
			
			mode.addAttribute("copy_code", "copy_code");
		}
		
		return "hrp/hip/hipdblink/hipDblinkMain";
	}

	@RequestMapping(value = "/hrp/hip/hipdblink/queryHipDblink", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipDblink(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str = HipDblinkService.queryHipDblink(getPage(mapVo));

		return JSONObject.parseObject(str);
	}

	@RequestMapping(value = "/hrp/hip/hipdblink/addHipDblink", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipDblink(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str;
		try {
			
			str = HipDblinkService.addHipDblink(mapVo);
		} catch (Exception e) {
			
			str = e.getMessage();
		}

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipdblink/updateHipDblinkPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHipDblinkPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		HipDblink hdk = HipDblinkService.queryHipDblinkByCode(mapVo);

		return JSONObject.parseObject(JsonListBeanUtil.beanToJson(hdk));

	}

	@RequestMapping(value = "/hrp/hip/hipdblink/deleteHipDblink", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipDblink(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String apt;
		try {
			
			apt = HipDblinkService.delHipDblink(mapVo);
		} catch (Exception e) {

			apt = e.getMessage();
		}

		return JSONObject.parseObject(apt);

	}

}
