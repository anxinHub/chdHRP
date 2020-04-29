package com.chd.hrp.hr.controller.attendancemanagement.overtime;

import java.util.Date;
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
import com.chd.base.MyConfig;
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.service.attendancemanagement.overtime.HrOvertimeService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 加班登记设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrOvertimeController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrOvertimeController.class);

	// 引入Service服务
	@Resource(name = "hrOvertimeService")
	private final HrOvertimeService hrOvertimeService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrOvertimeMainPage", method = RequestMethod.GET)
	public String hrOvertimeMainPage(Model mode) throws Exception {
		return "hrp/hr/attendancemanagement/overtime/overtime/overtimeMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOvertimePage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/attendancemanagement/overtime/overtime/overtimeAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addOvertime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOvertime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		
		try {

			retMap = hrOvertimeService.addOvertime(mapVo);
			
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}

		return retMap;
	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOvertimePage", method = RequestMethod.GET)
	public String updateHrDeptovertimePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("dept_source", MyConfig.getSysPara("06103"));
		
		Map<String, Object> attendOvertime = hrOvertimeService.queryByCodeOvertime(mapVo);
		
		//日期转换
		if(attendOvertime.get("overtime_date") != null && !"".equals(attendOvertime.get("overtime_date").toString())){
			attendOvertime.put("overtime_date", DateUtil.dateToString((Date)attendOvertime.get("overtime_date"), "yyyy-MM-dd"));
		}
		
		mode.addAttribute("attendOvertime", attendOvertime);
		
		return "hrp/hr/attendancemanagement/overtime/overtime/overtimeUpdate";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOvertime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOvertime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {

			retMap = hrOvertimeService.updateOvertime(mapVo);
			
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}

		return retMap;
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteOvertime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteOvertime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {

			retMap = hrOvertimeService.deleteOvertime(mapVo);
			
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}

		return retMap;
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryOvertime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOvertime(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("dept_source", MyConfig.getSysPara("06103"));
			String stationTransef = hrOvertimeService.queryOvertime(getPage(mapVo));

			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmOvertime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmOvertime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {

			retMap = hrOvertimeService.confirmOvertime(mapVo);
			
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}

		return retMap;
	}
	/**
	 * @Description 撤回
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmOvertime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmOvertime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		
		try {

			retMap = hrOvertimeService.reConfirmOvertime(mapVo);
			
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}

		return retMap;
	}
}
