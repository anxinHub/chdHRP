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
import com.chd.hrp.acc.service.autovouch.AccBusiTypeService;

/**
 * @Title. @Description. 出纳账
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccBusiTypeController extends BaseController {
	private static Logger logger = Logger.getLogger(AccBusiTypeController.class);

	@Resource(name = "accBusiTypeService")
	private final AccBusiTypeService accBusiTypeService = null;

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitype/accBusiTypeMainPage", method = RequestMethod.GET)
	public String accBusiTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/accbusitype/accBusiTypeMain";
	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitype/queryAccBusiType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBusiType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String accBusiType = accBusiTypeService.queryAccBusiType(getPage(mapVo));

		return JSONObject.parseObject(accBusiType);

	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbusitype/updateAccBusiTypeForIsVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBusiTypeForIsVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String emp = accBusiTypeService.updateAccBusiTypeForIsVouch(mapVo);

		return JSONObject.parseObject(emp);

	}

}
