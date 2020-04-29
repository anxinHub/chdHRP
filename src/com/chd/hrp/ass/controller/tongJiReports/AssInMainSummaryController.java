/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.tongJiReports;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.tongJiReports.AssInMainSummaryService;

@Controller
public class AssInMainSummaryController extends BaseController {    

	private static Logger logger = Logger.getLogger(AssInMainSummaryController.class);

	// 引入Service服务
	@Resource(name = "assInMainSummaryService")
	private final AssInMainSummaryService assInMainSummaryService = null;

	// 资产入库汇总页面跳转
	@RequestMapping(value = "/hrp/ass/tongJiReports/assInMainSummaryPage", method = RequestMethod.GET)
	public String assInMainSummaryPage(Model mode) throws Exception {

		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assInMainSummary";

	}

	// 查询数据 资产入库汇总带分页
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssInMainBySummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainBySummary(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMain = assInMainSummaryService.queryAssInMainBySummary(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}

	// 资产入库分类页面跳转
	@RequestMapping(value = "/hrp/ass/tongJiReports/assInMainSummaryByTypePage", method = RequestMethod.GET)
	public String assInMainSummaryByTypePage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assInMainSummaryByType";

	}

	// 查询数据 资产入库分类带分页
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssInMainSummaryByType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainSummaryByType(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMain = assInMainSummaryService.queryAssInMainSummaryByType(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}

	// 资产入库情况页面跳转
	@RequestMapping(value = "/hrp/ass/tongJiReports/assInMainSituationPage", method = RequestMethod.GET)
	public String assInMainSituationPageassInMainSituationPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assInMainQuery";

	}

	// 查询数据 资产入库情况带分页
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryInMainSituation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInMainSituation(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMain = assInMainSummaryService.queryInMainSituation(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}

}
