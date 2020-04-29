/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c07;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.analysis.c07.C07Service;

/**
 * @Title. @Description. 单元分析请求类
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C07Controller extends BaseController {

	@Resource(name = "c07Service")
	private final C07Service c07Service = null;

	/**
	 * 结余分析主页面<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/analysis/c07/c0701MainPage", method = RequestMethod.GET)
	public String c0701MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c07/c0701Main";

	}

	/**
	 * 结余分析查询<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c07/queryAnalysisC0701", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0701(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String analysis = c07Service.queryAnalysisC0701(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}

	/**
	 * 结余分析主页面<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/analysis/c07/c0702MainPage", method = RequestMethod.GET)
	public String c0702MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c07/c0702Main";

	}

	/**
	 * 结余分析查询<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c07/queryAnalysisC0702", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0702(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String analysis = c07Service.queryAnalysisC0702(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}

	/**
	 * 结余分析主页面<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/analysis/c07/c0703MainPage", method = RequestMethod.GET)
	public String c0703MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c07/c0703Main";

	}

	/**
	 * 结余分析查询<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c07/queryAnalysisC0703", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0703(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String analysis = c07Service.queryAnalysisC0703(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}

	/**
	 * 结余分析主页面<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/analysis/c07/c0704MainPage", method = RequestMethod.GET)
	public String c0704MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c07/c0704Main";

	}

	/**
	 * 结余分析查询<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c07/queryAnalysisC0704", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0704(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String analysis = c07Service.queryAnalysisC0704(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
}
