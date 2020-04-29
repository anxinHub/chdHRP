package com.chd.hrp.hr.controller.attendancemanagement.scheduling;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.scientificresearch.HrResearchTotSet;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSchedulingInquireService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 排班查询
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
@SuppressWarnings("unused")
public class HrSchedulingInquireController extends BaseController {

	
	private static Logger logger = Logger.getLogger(HrSchedulingInquireController.class);

	// 引入Service服务
	@Resource(name = "hrSchedulingInquireService")
	private final HrSchedulingInquireService hrSchedulingInquireService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrSchedulingInquireMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/scheduling/schedulinginquire/main";
	}
	
	
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAllPB", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPB(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrSchedulingInquireService.queryAllPB(mapVo);

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEmpPb", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpPb(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		String stationTransef = hrSchedulingInquireService.queryEmpPb(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * 动态表头
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value = "/queryMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryDynamicTabHead(@RequestParam Map<String, Object> mapVo){
		
		Map<String,Object> reMap = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> tabHead = hrSchedulingInquireService.queryMonth(mapVo);
		reMap.put("columns", tabHead);
		
		
		return reMap;
		
	}

	
	
}
