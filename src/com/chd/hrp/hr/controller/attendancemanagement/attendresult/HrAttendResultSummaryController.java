package com.chd.hrp.hr.controller.attendancemanagement.attendresult;

import java.util.HashMap;
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
import com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultSummaryService;

/**
 * 考勤汇总
 * @table HR_ATTEND_RESULT_MANAGE
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attendsummary")
public class HrAttendResultSummaryController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HrAttendResultSummaryController.class);
	
	@Resource(name = "hrAttendResultSummaryService")
	private final HrAttendResultSummaryService hrAttendResultSummaryService = null;
	
	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendSummaryMainPage", method = RequestMethod.GET)
	public String hrAttendResultSummaryMainPage(Model mode) throws Exception {
		
		return "hrp/hr/attendancemanagement/resultsummary/hrAttendResultSummaryMain";

	}
	
	

	/**
	 * 主页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAttendResultSummary(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String resultJson = null;
		
		try {
			mapVo.remove("changepage");
			
			resultJson = hrAttendResultSummaryService.queryAttendResultSummary(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			resultJson = "{\"error\":\"操作失败\"}";
		}
		
		return JSONObject.parseObject(resultJson);
	}
	

	
	
	
	
}
