/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
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
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.autovouch.AccPatientType;
import com.chd.hrp.acc.service.autovouch.AccPatientTypeService;

/**
 * @Title. @Description. 出纳账
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccPatientTypeController extends BaseController {
	private static Logger logger = Logger.getLogger(AccPatientTypeController.class);

	@Resource(name = "accPatientTypeService")
	private final AccPatientTypeService accPatientTypeService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/accpatienttype/accPatientTypeMainPage", method = RequestMethod.GET)
	public String accPatientTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/accpatienttype/accPatientTypeMain";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/accpatienttype/queryAccPatientType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPatientType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = accPatientTypeService.queryAccPatientType(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accpatienttype/addAccPatientType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccPatientType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}

		String str = accPatientTypeService.addAccPatientType(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accpatienttype/updateAccPatientTypePage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPatientTypePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		AccPatientType apt = accPatientTypeService.queryAccPatientTypeByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		sb.append("\"patient_type_code\":\"" + apt.getPatient_type_code() + "\",");

		sb.append("\"patient_type_name\":\"" + apt.getPatient_type_name() + "\",");

		sb.append("\"is_stop\":\"" + apt.getIs_stop() + "\",");

		sb.append("\"note\":\"" + apt.getNote() + "\"");

		sb.append("}");

		return JSONObject.parseObject(sb.toString());

	}
	
	@RequestMapping(value = "/hrp/acc/autovouch/accpatienttype/deleteAccPatientType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccPatientType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String apt = accPatientTypeService.delAccPatientType(mapVo);


		return JSONObject.parseObject(apt);

	}

}
