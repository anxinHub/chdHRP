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
import com.chd.hrp.acc.entity.autovouch.AccChargeType;
import com.chd.hrp.acc.service.autovouch.AccChargeTypeService;

/**
 * @Title. @Description. 出纳账
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccChargeTypeController extends BaseController {
	private static Logger logger = Logger.getLogger(AccChargeTypeController.class);

	@Resource(name = "accChargeTypeService")
	private final AccChargeTypeService accChargeTypeService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/accchargetype/accChargeTypeMainPage", method = RequestMethod.GET)
	public String accChargeTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/accchargetype/accChargeTypeMain";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/accchargetype/queryAccChargeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccChargeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String str = accChargeTypeService.queryAccChargeType(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accchargetype/addAccChargeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccChargeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String str = accChargeTypeService.addAccChargeType(mapVo);

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accchargetype/updateAccChargeTypePage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccChargeTypePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		AccChargeType act = accChargeTypeService.queryAccChargeTypeByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		sb.append("{");

		sb.append("\"charge_type_code\":\"" + act.getCharge_type_code() + "\",");

		sb.append("\"charge_type_name\":\"" + act.getCharge_type_name() + "\",");

		sb.append("\"is_stop\":\"" + act.getIs_stop() + "\",");

		sb.append("\"note\":\"" + act.getNote() + "\"");

		sb.append("}");

		return JSONObject.parseObject(sb.toString());

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accchargetype/deleteAccChargeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccChargeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String apt = accChargeTypeService.delAccChargeType(mapVo);

		return JSONObject.parseObject(apt);

	}

}
