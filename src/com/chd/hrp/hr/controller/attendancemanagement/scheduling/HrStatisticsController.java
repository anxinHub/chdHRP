package com.chd.hrp.hr.controller.attendancemanagement.scheduling;

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
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSchedulingService;

/**
 * 【考勤管理-排班管理-排班统计】
 * @author yang
 * @date 2018-12-21
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/scheduling")
public class HrStatisticsController extends BaseController {

	private static Logger logger = Logger.getLogger(HrStatisticsController.class);
	
	@Resource(name = "hrSchedulingService")
	private final HrSchedulingService hrSchedulingService = null;
	
	/**
	 * 排班统计主页面
	 */
	@RequestMapping(value = "/hrStatisticsMainPage", method = RequestMethod.GET)
	public String mainPage() throws Exception {
		return "hrp/hr/attendancemanagement/scheduling/statistics/main";
	}
	
	/**
	 * 排班统计主页面-主查询
	 */
	@RequestMapping(value = "/queryStatistics", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStatistics(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String json = hrSchedulingService.queryPBStatistics(getPage(mapVo));
		return JSONObject.parseObject(json);
	}
}
