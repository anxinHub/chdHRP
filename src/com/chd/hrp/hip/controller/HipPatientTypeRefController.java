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
import com.chd.hrp.hip.entity.HipPatientTypeRef;
import com.chd.hrp.hip.service.HipPatientTypeRefService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HipPatientTypeRefController extends BaseController {
	private static Logger logger = Logger.getLogger(HipPatientTypeRefController.class);

	@Resource(name = "hipPatientTypeRefService")
	private final HipPatientTypeRefService hipPatientTypeRefService = null;

	@RequestMapping(value = "/hrp/hip/hippatienttyperef/hipPatientTypeRefMainPage", method = RequestMethod.GET)
	public String hipPatientTypeRefMainPage(Model mode) throws Exception {
		
		if(SessionManager.getCopyCode() == null || "".equals(SessionManager.getCopyCode())){
			
			mode.addAttribute("copy_code", "copy_code");

		}
		
		return "hrp/hip/hippatienttyperef/hipPatientTypeRefMain";
	}

	@RequestMapping(value = "/hrp/hip/hippatienttyperef/queryHipPatientTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipPatientTypeRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String str = hipPatientTypeRefService.queryHipPatientTypeRef(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hippatienttyperef/addHipPatientTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipPatientTypeRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String str = hipPatientTypeRefService.addHipPatientTypeRef(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/hip/hippatienttyperef/updateHipPatientTypeRefPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHipPatientTypeRefPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		HipPatientTypeRef hdr = hipPatientTypeRefService.queryHipPatientTypeRefByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		sb.append("\"ds_code\":\"" + hdr.getDs_code() + "\",");

		sb.append("\"hip_patient_type_code\":\"" + hdr.getHip_patient_type_code() + "\",");

		sb.append("\"hrp_patient_type_code\":\"" + hdr.getHrp_patient_type_code() + "\",");
		
		sb.append("\"hip_patient_type_name\":\"" + hdr.getHip_patient_type_name() + "\",");

		sb.append("\"hrp_patient_type_name\":\"" + hdr.getHrp_patient_type_name() + "\",");

		sb.append("\"ds_name\":\"" + hdr.getDs_name() + "\",");

		sb.append("}");

		return JSONObject.parseObject(sb.toString());

	}

	@RequestMapping(value = "/hrp/hip/hippatienttyperef/deleteHipPatientTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipPatientTypeRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String apt = hipPatientTypeRefService.delHipPatientTypeRef(mapVo);

		return JSONObject.parseObject(apt);

	}

	@RequestMapping(value = "/hrp/hip/hippatienttyperef/delCheckHipPatientTypeRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delCheckHipPatientTypeRef(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("ds_code", ids[2]);

			mapVo.put("hip_patient_type_code", ids[3]);

			mapVo.put("hrp_patient_type_code", ids[4]);

			listVo.add(mapVo);

		}

		String apt = hipPatientTypeRefService.deleteBatchHipPatientTypeRef(listVo);

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
	@RequestMapping(value = "hrp/hip/hippatienttyperef/downTemplateHipPatientTypeRef", method = RequestMethod.GET)
	public String downTemplateHipPatientTypeRef(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hip\\映射关系", "平台患者类别映射关系.xls");

		return null;
	}

	/**
	 * @Description 导入跳转页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/hip/hippatienttyperef/hipPatientTypeRefImportPage", method = RequestMethod.GET)
	public String hipPatientTypeRefImportPage(Model mode) throws Exception {

		return "hrp/hip/hippatienttyperef/hipPatientTypeRefImport";

	}

}
