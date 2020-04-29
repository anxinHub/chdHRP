package com.chd.hrp.hr.controller.attendancemanagement.scheduling;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.net.aso.s;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrScheduling;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrCountersignService;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSchedulingService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 排班审签设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
@SuppressWarnings("unused")
public class HrCountersignController extends BaseController {

	
	private static Logger logger = Logger.getLogger(HrCountersignController.class);

	// 引入Service服务
	@Resource(name = "hrCountersignService")
	private final HrCountersignService hrCountersignService = null;

	@Resource(name = "hrSchedulingService")
	private final HrSchedulingService hrSchedulingService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCountersignMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/scheduling/countersign/countersignMainPage";
	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCountersignPage", method = RequestMethod.GET)
	public String updateSchedulingPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrScheduling hrScheduling = new HrScheduling();
		//hrScheduling = hrSchedulingService.queryByCode(mapVo);
		mode.addAttribute("attend_pbcode", hrScheduling.getAttend_pbcode());
		mode.addAttribute("attend_areacode", hrScheduling.getAttend_areacode());
		mode.addAttribute("attend_pbname", hrScheduling.getAttend_pbname());
		mode.addAttribute("attend_pbrule", hrScheduling.getAttend_pbrule());
		mode.addAttribute("attend_pbnote", hrScheduling.getAttend_pbnote());
		mode.addAttribute("attend_pbcheck_state", hrScheduling.getAttend_pbcheck_state());
		return "hrp/hr/attendancemanagement/scheduling/countersign/countersignUpate";

	}
	
	
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCountersign", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCountersign(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrCountersignService.queryCountersign(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}

}
