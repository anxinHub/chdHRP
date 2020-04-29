/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.disposal;

import java.util.*;

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

import com.chd.hrp.ass.service.disposal.AssDisposalMainService;

/**
 * 
 * @Description: 051001资产处置主表
 * @Table: ASS_DISPOSAL_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDisposalRegistration extends BaseController {

	private static Logger logger = Logger.getLogger(AssDisposalRegistration.class);

	// 引入Service服务
	@Resource(name = "assDisposalMainService")
	private final AssDisposalMainService assDisposalMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/assDisposalRegistrationPage", method = RequestMethod.GET)
	public String assDisposalRegistrationPage(Model mode) throws Exception {

		return "hrp/ass/assdisposalmain/assDisposalRegistration";

	}

	/**
	 * @Description 051001资产处置主表 (登记数据)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdisposalmain/registrationAssDisposal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registrationAssDisposal(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("dis_id", ids[3]);

			mapVo.put("state", "3");

			listVo.add(mapVo);

		}
		try {
			String assDisposalMainJson = assDisposalMainService.updateBatchAssDisposalMain(listVo);

			return JSONObject.parseObject(assDisposalMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

}
