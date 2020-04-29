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
import com.chd.hrp.hpm.service.report.AphiDeptTargetDataReportService;

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
public class AphiDeptTargetDataReportController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptTargetDataReportController.class);

	@Resource(name = "aphiDeptTargetDataReportService")
	private final AphiDeptTargetDataReportService aphiDeptTargetDataReportService = null;



	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/report/hpmDeptTargetDataReportMainPage", method = RequestMethod.GET)
	public String hpmDeptTargetDataReportMainPage(Model mode) throws Exception {
		
		if (SessionManager.existsUserPerm("isShowZeroHpmDeptTargetDataReport")) {

			mode.addAttribute("is_show_zero", true);

		} else {
			mode.addAttribute("is_show_zero", false);
		}
		
		
		return "hrp/hpm/report/hpmDeptTargetDataReportMain";

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/report/queryHpmDeptTargetDataReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptTargetDataReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

			
		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}
			
		if("false".equals(mapVo.get("is_show_zero"))){//不显示指标值为0 的数据
				
			mapVo.put("is_show_zero", "0");
		}
			
		String deptTargetData = aphiDeptTargetDataReportService.queryDeptTargetDataReport(getPage(mapVo));

		return JSONObject.parseObject(deptTargetData);

	}
}
