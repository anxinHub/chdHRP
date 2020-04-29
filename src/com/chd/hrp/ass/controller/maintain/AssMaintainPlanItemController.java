/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.maintain;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlan;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanItemService;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanService;

/**
 * 
 * @Description: 051202 保养计划项目明细
 * @Table: ASS_MAINTAIN_PLAN_ITEM
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMaintainPlanItemController extends BaseController {

	private static Logger logger = Logger.getLogger(AssMaintainPlanItemController.class);

	// 引入Service服务
	@Resource(name = "assMaintainPlanItemService")
	private final AssMaintainPlanItemService assMaintainPlanItemService = null;
	
	@Resource(name = "assMaintainPlanService")
	private final AssMaintainPlanService assMaintainPlanService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/assMaintainPlanItemMainPage", method = RequestMethod.GET)
	public String assMaintainPlanItemMainPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainplanitem/assMaintainPlanItemMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/assMaintainPlanItemAddPage", method = RequestMethod.GET)
	public String assMaintainPlanItemAddPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainplanitem/assMaintainPlanItemAdd";

	}

	/**
	 * @Description 添加数据 051202 保养计划项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/addAssMaintainPlanItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMaintainPlanItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			String assMaintainPlanItemJson = assMaintainPlanItemService.addAssMaintainPlanItem(mapVo);

			return JSONObject.parseObject(assMaintainPlanItemJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051202 保养计划项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/assMaintainPlanItemUpdatePage", method = RequestMethod.GET)
	public String assMaintainPlanItemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssMaintainPlanItem assMaintainPlanItem = new AssMaintainPlanItem();

		assMaintainPlanItem = assMaintainPlanItemService.queryAssMaintainPlanItemByCode(mapVo);

		mode.addAttribute("group_id", assMaintainPlanItem.getGroup_id());

		mode.addAttribute("hos_id", assMaintainPlanItem.getHos_id());

		mode.addAttribute("copy_code", assMaintainPlanItem.getCopy_code());

		mode.addAttribute("plan_id", assMaintainPlanItem.getPlan_id());

		mode.addAttribute("ass_card_no", assMaintainPlanItem.getAss_card_no());

		mode.addAttribute("item_code", assMaintainPlanItem.getItem_code());

		mode.addAttribute("item_name", assMaintainPlanItem.getItem_name());

		return "hrp/ass/assmaintainplanitem/assMaintainPlanItemUpdate";
	}

	/**
	 * @Description 更新数据 051202 保养计划项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/updateAssMaintainPlanItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMaintainPlanItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		try {
			String assMaintainPlanItemJson = assMaintainPlanItemService.updateAssMaintainPlanItem(mapVo);

			return JSONObject.parseObject(assMaintainPlanItemJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051202 保养计划项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/addOrUpdateAssMaintainPlanItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMaintainPlanItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMaintainPlanItemJson = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {

				detailVo.put("group_id", SessionManager.getGroupId());

			}

			if (detailVo.get("hos_id") == null) {

				detailVo.put("hos_id", SessionManager.getHosId());

			}

			if (detailVo.get("copy_code") == null) {

				detailVo.put("copy_code", SessionManager.getCopyCode());

			}

			detailVo.put("item_code", detailVo.get("maintain_item_code"));

			detailVo.put("item_name", detailVo.get("maintain_item_name"));

			detailVo.put("ass_card_no", detailVo.get("ass_card_no"));

			detailVo.put("plan_id", mapVo.get("plan_id"));

			if (detailVo.get("detail_id") == null) {

				detailVo.put("detail_id", "0");

			} else {

				detailVo.put("detail_id", detailVo.get("detail_id"));

			}
			try {
				assMaintainPlanItemJson = assMaintainPlanItemService.addOrUpdateAssMaintainPlanItem(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assMaintainPlanItemJson);
	}

	/**
	 * @Description 删除数据 051202 保养计划项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/deleteAssMaintainPlanItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMaintainPlanItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_id", ids[3]);

			mapVo.put("ass_card_no", ids[4]);

			mapVo.put("detail_id", ids[5]);

			listVo.add(mapVo);

		}
		try {
			String assMaintainPlanItemJson = assMaintainPlanItemService.deleteBatchAssMaintainPlanItem(listVo);

			return JSONObject.parseObject(assMaintainPlanItemJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

	}

	/**
	 * @Description 查询数据 051202 保养计划项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/queryAssMaintainPlanItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainPlanItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		if (!mapVo.containsKey("plan_id")) {
			mapVo.put("plan_id", "0");
		}
		String assMaintainPlanItem = assMaintainPlanItemService.queryAssMaintainPlanItem(getPage(mapVo));

		return JSONObject.parseObject(assMaintainPlanItem);

	}
	
	/**
	 * @Description 查询数据 051202 保养计划项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/queryAssMaintainPlanItemByPlanId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainPlanItemByPlanId(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		AssMaintainPlan ent = assMaintainPlanService.queryAssMaintainPlanByCode(mapVo);
		List<AssMaintainPlanItem> list = assMaintainPlanItemService.queryAssMaintainPlanItemByPlans(mapVo);
		
		returnMap.put("main", ent);
		returnMap.put("detail", list);
		return returnMap;

	}
	
	/**
	 * @Description 查询数据 051202 保养计划项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/queryAssMaintainPlanItemByPlans", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssMaintainPlanItemByPlans(@RequestParam(value = "paramVo") String paramVo, Model mode)
			throws Exception {
		List<Map> paramList = JSONArray.parseArray(paramVo, Map.class);
		List<AssMaintainPlanItem> list = new ArrayList<AssMaintainPlanItem>();
		for (Map<String,Object> map : paramList) {
			list.addAll(assMaintainPlanItemService.queryAssMaintainPlanItemByPlans(map));
		}
		return ChdJson.toJson(list);

	}
	
	/**
	 * 生成固定资产卡片号 根据主表的资产性质 (弹出生成页面)
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	/**
	 * @Description 更新跳转页面 051202 保养计划项目明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanitem/createMaintainPlanItem", method = RequestMethod.GET)
	public String createMaintainPlanItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/ass/assmaintainplan/assCardNo";
	}

}
