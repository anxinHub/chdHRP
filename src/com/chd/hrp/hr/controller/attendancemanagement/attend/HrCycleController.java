package com.chd.hrp.hr.controller.attendancemanagement.attend;

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
import com.chd.hrp.hr.service.attendancemanagement.attend.HrCycleService;

/**
 * 考勤周期
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/attendancemanagement/attend")
public class HrCycleController  extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrCycleController.class);

	// 引入Service服务 
	@Resource(name = "hrCycleService")
	private final HrCycleService hrCycleService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCycleMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
	return "hrp/hr/attendancemanagement/attend/cycle/cycleMainPage";

	}

	
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCycle", method = RequestMethod.POST)
	@ResponseBody
	public String addCycle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
		
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
		
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
		
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hosEmpKindJson = hrCycleService.addCycle(mapVo);
		
			return hosEmpKindJson;
		
		
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	/**
	 * 生成
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createCycle", method = RequestMethod.POST)
	@ResponseBody
	public String createCycle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
		
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
		
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
		
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String hosEmpKindJson = hrCycleService.createCycle(mapVo);
			return hosEmpKindJson;
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryCycle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCycle(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
		
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
		
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
		
			String hrCycleJson = hrCycleService.queryHrCycle(mapVo);
		
			return JSONObject.parseObject(hrCycleJson);
		
		
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/**
	 * 考勤期间查询
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrPeriod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrPeriod(@RequestParam Map<String, Object> mapVo) throws Exception {
		try {
		
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
		
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
		    if(mapVo.get("attend_year") == null){
		    	mapVo.put("attend_year", SessionManager.getAcctYear());
		    }
			String hrCycleJson = hrCycleService.queryHrPeriod(mapVo);
			return JSONObject.parseObject(hrCycleJson);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	//
	/**
	 * @Description 清除考勤期间
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCycle", method = RequestMethod.POST)
	@ResponseBody
	public String deleteCycle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		try {

          
			return hrCycleService.deleteCycle(mapVo);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
}

