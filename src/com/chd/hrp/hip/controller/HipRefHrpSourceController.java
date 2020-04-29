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
import com.chd.hrp.hip.entity.HipRefHrpSource;
import com.chd.hrp.hip.service.HipRefHrpSourceService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HipRefHrpSourceController extends BaseController {
	private static Logger logger = Logger.getLogger(HipRefHrpSourceController.class);

	@Resource(name = "hipRefHrpSourceService")
	private final HipRefHrpSourceService hipRefHrpSourceService = null;

	@RequestMapping(value = "/hrp/hip/hiprefhrpsource/hipRefHrpSourceMainPage", method = RequestMethod.GET)
	public String HipRefHrpSourceMainPage(Model mode) throws Exception {
		
		if(SessionManager.getCopyCode() == null || "".equals(SessionManager.getCopyCode())){
			
			mode.addAttribute("copy_code", "copy_code");

		}
		
		return "hrp/hip/hiprefhrpsource/hipRefHrpSourceMain";
	}

	@RequestMapping(value = "/hrp/hip/hiprefhrpsource/queryHipRefHrpSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipRefHrpSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str = hipRefHrpSourceService.queryHipRefHrpSource(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hiprefhrpsource/addHipRefHrpSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipRefHrpSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String str = hipRefHrpSourceService.addHipRefHrpSource(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hiprefhrpsource/updateHipRefHrpSourcePage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHipRefHrpSourcePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		HipRefHrpSource hrhs = hipRefHrpSourceService.queryHipRefHrpSourceByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		sb.append("\"mod_code\":\"" + hrhs.getMod_code() + "\",");
		
		sb.append("\"mod_name\":\"" + hrhs.getMod_name() + "\",");
		
		sb.append("\"hip_view_code\":\"" + hrhs.getHip_view_code() + "\",");
		
		sb.append("\"hip_view_name\":\"" + hrhs.getHip_view_name() + "\",");
		
		sb.append("\"hrp_view_code\":\"" + hrhs.getHrp_view_code() + "\",");
		
		sb.append("\"hrp_view_name\":\"" + hrhs.getHrp_view_name() + "\",");
		
		sb.append("\"dfd_flag\":\"" + hrhs.getDfd_flag() + "\"");

		sb.append("}");

		return JSONObject.parseObject(sb.toString());

	}

	@RequestMapping(value = "/hrp/hip/hiprefhrpsource/deleteHipRefHrpSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipRefHrpSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String apt = hipRefHrpSourceService.delHipRefHrpSource(mapVo);

		return JSONObject.parseObject(apt);

	}

}
