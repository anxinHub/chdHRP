package com.chd.hrp.hr.controller.attendancemanagement.termend;

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
import com.chd.hrp.hr.service.attendancemanagement.termend.HrAttendTermendService;

/**
 * 加班审核
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/termend")
public class HrAttendTermendController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAttendTermendController.class);

	// 引入Service服务
	@Resource(name = "hrAttendTermendService")
	private final HrAttendTermendService hrAttendTermendService = null;
	
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAttendTermendPage", method = RequestMethod.GET)
	public String hrOvertimeCheckMainPage(Model mode) throws Exception {
		
		return "hrp/hr/attendancemanagement/termend/hrAttendTermend";
	}
	
	/**
	 * @Description 获取当前期间
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendTermendYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendTermendYearMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendTermendService.queryAttendTermendYearMonth(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}

	/**
	 * @Description 查询清除余额
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAttendXjedDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAttendXjedDel(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String retMsg;
		try {
			
			retMsg = hrAttendTermendService.queryAttendXjedDel(getPage(mapVo));
		} catch (Exception e) {
			
			retMsg = e.getMessage();
		}

		return JSONObject.parseObject(retMsg); 
	}
	
	/**
	 * @Description 保存清除余额
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAttendXjedDel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAttendXjedDel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendTermendService.addAttendXjedDel(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	
	/**
	 * @Description 结账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmAttendTermend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmAttendTermend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendTermendService.confirmAttendTermend(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	
	/**
	 * @Description 反结账
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/unConfirmAttendTermend", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unConfirmAttendTermend(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		try {
			
			retMap = hrAttendTermendService.unConfirmAttendTermend(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
}
