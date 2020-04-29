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
import com.chd.hrp.hip.entity.HipInitView;
import com.chd.hrp.hip.service.HipInitViewService;

/**
 * @Title. @Description. 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HipInitViewController extends BaseController {
	private static Logger logger = Logger.getLogger(HipInitViewController.class);

	@Resource(name = "hipInitViewService")
	private final HipInitViewService hipInitViewService = null;

	@RequestMapping(value = "/hrp/hip/hipinitview/hipInitViewMainPage", method = RequestMethod.GET)
	public String hipInitViewMainPage(Model mode) throws Exception {
		
		if(SessionManager.getCopyCode() == null || "".equals(SessionManager.getCopyCode())){
			
			mode.addAttribute("copy_code", "copy_code");

		}
		
		return "hrp/hip/hipinitview/hipInitViewMain";
	}

	@RequestMapping(value = "/hrp/hip/hipinitview/queryHipInitView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipInitView(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str = hipInitViewService.queryHipInitView(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipinitview/addHipInitView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipInitView(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str = hipInitViewService.addHipInitView(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipinitview/updateHipInitViewPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHipInitViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		HipInitView hiv = hipInitViewService.queryHipInitViewByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		sb.append("\"mod_code\":\"" + hiv.getMod_code() + "\",");
		
		sb.append("\"mod_name\":\"" + hiv.getMod_name() + "\",");
		
		sb.append("\"hip_view_code\":\"" + hiv.getHip_view_code() + "\",");
		
		sb.append("\"hip_view_name\":\"" + hiv.getHip_view_name() + "\",");
		
		sb.append("\"ref_tab_name\":\"" + hiv.getRef_tab_name() + "\",");
		
		sb.append("\"view_level\":\"" + hiv.getView_level() + "\"");

		sb.append("}");

		return JSONObject.parseObject(sb.toString());

	}
	
	@RequestMapping(value = "/hrp/hip/hipinitview/deleteHipInitView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipInitView(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


		String apt = hipInitViewService.delHipInitView(mapVo);


		return JSONObject.parseObject(apt);

	}

}
