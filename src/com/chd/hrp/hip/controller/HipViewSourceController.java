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
import com.chd.hrp.hip.entity.HipViewSource;
import com.chd.hrp.hip.service.HipViewSourceService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HipViewSourceController extends BaseController {
	private static Logger logger = Logger.getLogger(HipViewSourceController.class);

	@Resource(name = "hipViewSourceService")
	private final HipViewSourceService hipViewSourceService = null;

	@RequestMapping(value = "/hrp/hip/hipviewsource/hipViewSourceMainPage", method = RequestMethod.GET)
	public String HipViewSourceMainPage(Model mode) throws Exception {
		
		if(SessionManager.getCopyCode() == null || "".equals(SessionManager.getCopyCode())){
			
			mode.addAttribute("copy_code", "copy_code");

		}
		
		return "hrp/hip/hipviewsource/hipViewSourceMain";
	}

	@RequestMapping(value = "/hrp/hip/hipviewsource/queryHipViewSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipViewSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String str = hipViewSourceService.queryHipViewSource(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipviewsource/addHipViewSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipViewSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", "0");}

		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}

		String str = hipViewSourceService.addHipViewSource(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipviewsource/updateHipViewSourcePage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHipViewSourcePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		HipViewSource hvs = hipViewSourceService.queryHipViewSourceByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		sb.append("\"group_id\":\"" + hvs.getGroup_id() + "\",");
		
		sb.append("\"hos_id\":\"" + hvs.getHos_id() + "\",");
		
		sb.append("\"copy_code\":\"" + hvs.getCopy_code() + "\",");
		
		sb.append("\"ds_code\":\"" + hvs.getDs_code() + "\",");

		sb.append("\"mod_code\":\"" + hvs.getMod_code() + "\",");
		
		sb.append("\"his_view_code\":\"" + hvs.getHis_view_code() + "\",");
		
		sb.append("\"his_view_name\":\"" + hvs.getHis_view_name() + "\",");
		
		sb.append("\"is_ref\":\"" + hvs.getIs_ref() + "\",");
		
		sb.append("\"mod_name\":\"" + hvs.getMod_name() + "\",");
		
		sb.append("\"hip_view_name\":\"" + hvs.getHip_view_name() + "\",");
		
		sb.append("\"ds_name\":\"" + hvs.getDs_name() + "\",");

		sb.append("\"hip_view_code\":\"" + hvs.getHip_view_code() + "\"");

		sb.append("}");

		return JSONObject.parseObject(sb.toString());

	}

	@RequestMapping(value = "/hrp/hip/hipviewsource/deleteHipViewSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipViewSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String apt = hipViewSourceService.delHipViewSource(mapVo);

		return JSONObject.parseObject(apt);

	}

}
