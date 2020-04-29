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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.attendancemanagement.accrest.HrAccRestStatisticsKQService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 积休统计
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrAccRestStatisticsKQController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAccRestStatisticsKQController.class);

	// 引入Service服务 
	@Resource(name = "hrAccRestStatisticsKQService")
	private final HrAccRestStatisticsKQService hrAccRestStatisticsKQService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAccRestStatisticsKQMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
	return "hrp/hr/attendancemanagement/accrest/accreststatistics/accRestStatisticsKQMainPage";

	}
	
	/**
	 * 增加跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/hrOvertimePage", method = RequestMethod.GET)
	public String hrOvertimePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		return "hrp/hr/attendancemanagement/accrest/accreststatistics/overTimePage";
	}*/
	
	/**
	 * 减少跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/applyLeavePage", method = RequestMethod.GET)
	public String applyLeavePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		return "hrp/hr/attendancemanagement/accrest/accreststatistics/applyingLeavePage";
	}*/
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAccRestStatisticsKQ", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccRestStatisticsKQ(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String stationTransef = hrAccRestStatisticsKQService.queryAccRestStatisticsKQ(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询增加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/overtimeQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> overtimeQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String stationTransef = hrAccRestStatisticsKQService.overtimeQuery(getPage(mapVo));
		return JSONObject.parseObject(stationTransef);
	}*/
	/**
	 * @Description 查询减少数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/applyingLeavesQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> applyingLeavesQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String stationTransef = hrAccRestStatisticsKQService.applyingLeavesQuery(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}*/
	/**
	 * 更新
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAccRestStatisticsKQ", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccRestStatisticsKQ(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String hosEmpKindJson = hrAccRestStatisticsKQService.updateAccRestStatisticsKQ(mapVo);
		return JSONObject.parseObject(hosEmpKindJson);

	}
}
