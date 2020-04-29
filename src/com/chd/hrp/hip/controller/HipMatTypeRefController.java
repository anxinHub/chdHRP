/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.hip.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.Plupload;
import com.chd.hrp.hip.entity.HipMatTypeRef;
import com.chd.hrp.hip.service.HipMatTypeRefService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HipMatTypeRefController extends BaseController {
	private static Logger logger = Logger.getLogger(HipMatTypeRefController.class);

	@Resource(name = "hipMatTypeRefService")
	private final HipMatTypeRefService hipMatTypeRefService = null;

	@RequestMapping(value = "/hrp/hip/hipmattyperef/hipMatTypeRefMainPage", method = RequestMethod.GET)
	public String hipMatTypeRefMainPage(Model mode) throws Exception {
		
		if(SessionManager.getCopyCode() == null || "".equals(SessionManager.getCopyCode())){
			
			mode.addAttribute("copy_code", "copy_code");

		}
		
		return "hrp/hip/hipmattyperef/hipMatTypeRefMain";
	}

	@RequestMapping(value = "/hrp/hip/hipmattyperef/queryHipMatTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipMatTypeRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String str = hipMatTypeRefService.queryHipMatTypeRef(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipmattyperef/addHipMatTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipMatTypeRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String str = hipMatTypeRefService.addHipMatTypeRef(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hipmattyperef/updateHipMatTypeRefPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHipMatTypeRefPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		HipMatTypeRef hdr = hipMatTypeRefService.queryHipMatTypeRefByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		sb.append("\"ds_code\":\"" + hdr.getDs_code() + "\",");

		sb.append("\"hip_mat_type_code\":\"" + hdr.getHip_mat_type_code() + "\",");

		sb.append("\"hrp_mat_type_code\":\"" + hdr.getHrp_mat_type_code() + "\",");
		
		sb.append("\"hip_mat_type_name\":\"" + hdr.getHip_mat_type_name() + "\",");

		sb.append("\"hrp_mat_type_name\":\"" + hdr.getHrp_mat_type_name() + "\",");

		sb.append("\"ds_name\":\"" + hdr.getDs_name() + "\",");

		sb.append("}");

		return JSONObject.parseObject(sb.toString());

	}

	@RequestMapping(value = "/hrp/hip/hipmattyperef/deleteHipMatTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipMatTypeRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String apt = hipMatTypeRefService.delHipMatTypeRef(mapVo);

		return JSONObject.parseObject(apt);

	}

	@RequestMapping(value = "/hrp/hip/hipmattyperef/delCheckHipMatTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delCheckHipMatTypeRef(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);

			mapVo.put("ds_code", ids[3]);

			mapVo.put("hip_mat_type_code", ids[4]);

			mapVo.put("hrp_mat_type_code", ids[5]);

			listVo.add(mapVo);

		}

		String apt = hipMatTypeRefService.deleteBatchHipMatTypeRef(listVo);

		return JSONObject.parseObject(apt);

	}

	/**
	 * @Description 下载导入模版 0308 职工表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hip/hipmattyperef/downTemplateHipMatTypeRef", method = RequestMethod.GET)
	public String downTemplateHipMatTypeRef(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hip\\映射关系", "平台材料分类映射关系.xls");

		return null;
	}

	/**
	 * @Description 导入跳转页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/hip/hipmattyperef/hipMatTypeRefImportPage", method = RequestMethod.GET)
	public String hipMatTypeRefImportPage(Model mode) throws Exception {

		return "hrp/hip/hipmattyperef/HipMatTypeRefImport";

	}

}
