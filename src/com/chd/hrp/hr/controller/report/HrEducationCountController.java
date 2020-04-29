package com.chd.hrp.hr.controller.report;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.report.HrEducationCountService;

@Controller
@RequestMapping(value = "/hrp/hr/report")
public class HrEducationCountController {
	
	@Resource(name="hrEducationCountService")
	private final HrEducationCountService hrEducationCountService = null;
/**
 * 学历分布表
 * @param mode
 * @return
 */
	@RequestMapping(value = "HrEducationCountMainPage", method = RequestMethod.GET)
	public String HrEducationCountMainPage(Model mode) {

		return "hrp/hr/record/degree/hrEducationCountMain";
	}

	@RequestMapping(value = "queryHrEducationCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrEducationCount(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String DegreeCount = hrEducationCountService.queryHrEducationCount(mapVo);
		
		return JSONObject.parseObject(DegreeCount);
	}
	/**
	 * 年龄分布表
	 */
	@RequestMapping(value = "HrAgeCountMainPage", method = RequestMethod.GET)
	public String HrAgeCountMainPage(Model mode) {

		return "hrp/hr/record/age/hrAgeCountMain";
	}

	@RequestMapping(value = "queryHrAgeCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrAgeCount(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String age = hrEducationCountService.queryHrAgeCount(mapVo);
		
		return JSONObject.parseObject(age);
	}
	
	/**
	 * 服务年限分布表
	 */
	@RequestMapping(value = "hrWorkAgeCountMainPage", method = RequestMethod.GET)
	public String HrWorkAgeCountMainPage(Model mode) {
		
		return "hrp/hr/record/workAge/hrWorkAgeCountMain";
	}
	
	@RequestMapping(value = "queryHrWorkAgeCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrWorkAgeCount(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String workAge = hrEducationCountService.queryHrWorkAgeCount(mapVo);
		
		return JSONObject.parseObject(workAge);
	}
	/**
	 * 人员分布表
	 */
	@RequestMapping(value = "hrUserCountMainPage", method = RequestMethod.GET)
	public String hrUserCountMainPage(Model mode) {
		
		return "hrp/hr/record/usercount/hrUserCountMain";
	}
	
	@RequestMapping(value = "queryHrUserCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrUserCount(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String useAll = hrEducationCountService.queryHrUserCountMain(mapVo);
		
		return JSONObject.parseObject(useAll);
	}
	@RequestMapping(value = "queryDegreeColumns", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDegreeColumns(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> useAll = hrEducationCountService.queryDegreeColumns(mapVo);
		
		return useAll;
	}
	
	
}
