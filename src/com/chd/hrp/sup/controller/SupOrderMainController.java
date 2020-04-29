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
import com.chd.hrp.sup.service.SupOrderMainService;

/**
 * @Description: 供应商订单
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class SupOrderMainController extends BaseController {

	private static Logger logger = Logger.getLogger(SupOrderMainController.class);

	// 引入Service服务
	@Resource(name = "supOrderMainService")
	private final SupOrderMainService supOrderMainService = null;

	/**
	 * @Description 供应商订单--主页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/suporder/supOrderMainPage", method = RequestMethod.GET)
	public String supOrderMainPage(Model mode) throws Exception {
		// 限制 只能查询当前用户所属供应商数据
		mode.addAttribute("sup_id", SessionManager.getSupId());
		mode.addAttribute("sup_no", SessionManager.getSupNo());
		return "hrp/sup/suporder/supOrderMain";

	}

	/**
	 * 供应商订单-主查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/suporder/querySupOrderMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupOrderMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", SessionManager.getCostParaMap().get("03001"));
		}
		// 限制 只能查询当前用户所属供应商数据
		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());

		String supOrder = supOrderMainService.querySupOrderMain(getPage(mapVo));

		return JSONObject.parseObject(supOrder);
	}

	/**
	 * 供应商订单修改页面跳转
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/suporder/supOrderUpdatePage", method = RequestMethod.GET)
	public String supOrderUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("show_history") == null) {
			mapVo.put("show_history", SessionManager.getCostParaMap().get("03001"));
		}
		// 限制 只能查询当前用户所属供应商数据
		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());
		Map<String, Object> supOrderMain = supOrderMainService.queryByCode(mapVo);

		mode.addAttribute("supOrderMain", supOrderMain);
		return "hrp/sup/suporder/supOrderUpdate";

	}

	/**
	 * @Description 供应商订单审核：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/suporder/querySupOrderUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupOrderUpdate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String supJson = supOrderMainService.queryDetail(mapVo);

		return JSONObject.parseObject(supJson);
	}
	
	
	/**
	 * @Description 供应商订单：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/suporder/queryDetailWithDelivery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDetailWithDelivery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("sup_id", SessionManager.getSupId());

		mapVo.put("sup_no", SessionManager.getSupNo());

		String supJson = supOrderMainService.queryDetailWithDelivery(mapVo);

		return JSONObject.parseObject(supJson);
	}
	/**
	 * @Description 供应商订单：根据主键材料ID查询明细信息 用于修改送货单引订单的数量 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/suporder/queryDetailWithDeliveryView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDetailWithDeliveryView(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("sup_id", SessionManager.getSupId());
		
		mapVo.put("sup_no", SessionManager.getSupNo());
		
		String supJson = supOrderMainService.queryDetailWithDeliveryView(mapVo);
		
		return JSONObject.parseObject(supJson);
	}

}
