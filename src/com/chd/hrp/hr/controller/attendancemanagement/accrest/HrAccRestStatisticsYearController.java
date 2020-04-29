package com.chd.hrp.hr.controller.attendancemanagement.accrest;

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
import com.chd.hrp.hr.service.attendancemanagement.accrest.HrAccRestStatisticsYearService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 积休统计
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrAccRestStatisticsYearController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAccRestStatisticsYearController.class);

	// 引入Service服务 
	@Resource(name = "hrAccRestStatisticsYearService")
	private final HrAccRestStatisticsYearService hrAccRestStatisticsYearService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAccRestStatisticsNianMainPage", method = RequestMethod.GET)
	public String stationYearMainPage(Model mode) throws Exception {
	return "hrp/hr/attendancemanagement/accrest/accreststatisticsyear/accRestStatisticsYearMainPage";

	}
	
	/**
	 * 增加跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrOvertimeYearPage", method = RequestMethod.GET)
	public String hrOvertimeYearPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		return "hrp/hr/attendancemanagement/accrest/accreststatisticsyear/hrOvertimeYearPage";
	}
	
	/**
	 * 减少跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/applyLeaveYearPage", method = RequestMethod.GET)
	public String applyLeaveYearPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		return "hrp/hr/attendancemanagement/accrest/accreststatisticsyear/applyLeaveYearPage";
	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAccRestStatisticsYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccRestStatisticsYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String stationTransef = hrAccRestStatisticsYearService.queryAccRestStatisticsYear(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询增加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/overtimeYearQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> overtimeYearQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String stationTransef = hrAccRestStatisticsYearService.overtimeYearQuery(getPage(mapVo));
		return JSONObject.parseObject(stationTransef);
	}
	/**
	 * @Description 查询减少数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/applyingLeavesYearQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> applyingLeavesYearQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String stationTransef = hrAccRestStatisticsYearService.applyingLeavesYearQuery(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * 更新
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAccRestStatisticsYear", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccRestStatisticsYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String hosEmpKindJson = hrAccRestStatisticsYearService.updateAccRestStatisticsYear(mapVo);
		return JSONObject.parseObject(hosEmpKindJson);

	}
}
