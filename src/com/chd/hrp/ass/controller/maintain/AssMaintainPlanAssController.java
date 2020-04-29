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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanAss;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanAssService;

/**
 * 
 * @Description: 051202 保养计划资产明细
 * @Table: ASS_MAINTAIN_PLAN_ASS
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMaintainPlanAssController extends BaseController { 

	private static Logger logger = Logger.getLogger(AssMaintainPlanAssController.class);

	// 引入Service服务
	@Resource(name = "assMaintainPlanAssService")
	private final AssMaintainPlanAssService assMaintainPlanAssService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanass/assMaintainPlanAssMainPage", method = RequestMethod.GET)
	public String assMaintainPlanAssMainPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainplanass/assMaintainPlanAssMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanass/assMaintainPlanAssAddPage", method = RequestMethod.GET)
	public String assMaintainPlanAssAddPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainplanass/assMaintainPlanAssAdd";

	}

	/**
	 * @Description 添加数据 051202 保养计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanass/addAssMaintainPlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMaintainPlanAss(@RequestParam Map<String, Object> mapVo, Model mode)
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

		try {
			String assMaintainPlanAssJson = assMaintainPlanAssService.addAssMaintainPlanAss(mapVo);

			return JSONObject.parseObject(assMaintainPlanAssJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051202 保养计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanass/assMaintainPlanAssUpdatePage", method = RequestMethod.GET)
	public String assMaintainPlanAssUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssMaintainPlanAss assMaintainPlanAss = new AssMaintainPlanAss();

		assMaintainPlanAss = assMaintainPlanAssService.queryAssMaintainPlanAssByCode(mapVo);

		mode.addAttribute("group_id", assMaintainPlanAss.getGroup_id());
		mode.addAttribute("hos_id", assMaintainPlanAss.getHos_id());
		mode.addAttribute("copy_code", assMaintainPlanAss.getCopy_code());
		mode.addAttribute("plan_id", assMaintainPlanAss.getPlan_id());
		mode.addAttribute("ass_card_no", assMaintainPlanAss.getAss_card_no());

		return "hrp/ass/assmaintainplanass/assMaintainPlanAssUpdate";
	}

	/**
	 * @Description 更新数据 051202 保养计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanass/updateAssMaintainPlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMaintainPlanAss(@RequestParam Map<String, Object> mapVo, Model mode)
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
			String assMaintainPlanAssJson = assMaintainPlanAssService.updateAssMaintainPlanAss(mapVo);

			return JSONObject.parseObject(assMaintainPlanAssJson);

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051202 保养计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanass/addOrUpdateAssMaintainPlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMaintainPlanAss(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMaintainPlanAssJson = "";

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
			try {
				assMaintainPlanAssJson = assMaintainPlanAssService.addOrUpdateAssMaintainPlanAss(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assMaintainPlanAssJson);
	}

	/**
	 * @Description 删除数据 051202 保养计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanass/deleteAssMaintainPlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMaintainPlanAss(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			listVo.add(mapVo);

		}
		try {
			String assMaintainPlanAssJson = assMaintainPlanAssService.deleteBatchAssMaintainPlanAss(listVo);
			return JSONObject.parseObject(assMaintainPlanAssJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

	}

	/**
	 * @Description 查询数据 051202 保养计划资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplanass/queryAssMaintainPlanAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainPlanAss(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		if (mapVo.get("plan_id") == null) {

			mapVo.put("plan_id", "0");

		}
		String assMaintainPlanAss = assMaintainPlanAssService.queryAssMaintainPlanAss(getPage(mapVo));

		return JSONObject.parseObject(assMaintainPlanAss);

	}

}
