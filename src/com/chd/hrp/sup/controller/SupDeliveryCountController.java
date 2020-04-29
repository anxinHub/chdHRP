/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.controller;

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
import com.chd.hrp.sup.service.SupDeliveryCountService;

/**
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class SupDeliveryCountController extends BaseController {

	private static Logger logger = Logger.getLogger(SupDeliveryCountController.class);

	// 引入Service服务
	@Resource(name = "supDeliveryCountService")
	private final SupDeliveryCountService supDeliveryCountService = null;

	/**
	 * @Description 订单执行查询--主页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/supDeliveryCountMainPage", method = RequestMethod.GET)
	public String supDeliveryCountMainPage(Model mode) throws Exception {
		// 限制 只能查询当前用户所属供应商数据
		mode.addAttribute("sup_id", SessionManager.getSupId());
		mode.addAttribute("sup_no", SessionManager.getSupNo());
		return "hrp/sup/supdeliverymain/supDeliveryCountMain";

	}

	/**
	 * 订单执行查询--主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/querySupDeliveryCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupDeliveryCount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		// 限制 只能查询当前用户所属供应商数据
		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());
		String supDelivery = supDeliveryCountService.querySupDeliveryCount(getPage(mapVo));

		return JSONObject.parseObject(supDelivery);
	}
}
