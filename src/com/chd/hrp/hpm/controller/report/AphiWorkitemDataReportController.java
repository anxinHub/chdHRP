package com.chd.hrp.hpm.controller.report;

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
import com.chd.hrp.hpm.service.report.AphiWorkitemDataReportService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiWorkitemDataReportController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiWorkitemDataReportController.class);

	@Resource(name = "aphiWorkitemDataReportService")
	private final AphiWorkitemDataReportService aphiWorkitemDataReportService = null;

	
	// 主页面跳转
	@RequestMapping(value = "/hrp/hpm/report/hpmWorkItemDataReportMainPage", method = RequestMethod.GET)
	public String hpmWorkItemDataReportMainPage(Model mode) throws Exception {
		
		return "hrp/hpm/report/hpmWorkItemDataReportMain";
	}

	
	// 查询
	@RequestMapping(value = "/hrp/hpm/report/queryHpmWorkItemDataReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmWorkItemDataReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}


		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);
			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);
			mapVo.put("acct_month", acct_month);
		}

		String workitemData = aphiWorkitemDataReportService.queryWorkitemDataReport(getPage(mapVo));

		return JSONObject.parseObject(workitemData);

	}
	
}
