package com.chd.hrp.htc.controller.relative.plan.set;

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
import com.chd.hrp.htc.service.relative.plan.set.HtcRelativePlanDeptService;

@Controller
public class HtcRelativePlanDeptController {
	
	private static Logger logger = Logger.getLogger(HtcRelativePlanDeptController.class);

	@Resource(name = "htcRelativePlanDeptService")
	private final HtcRelativePlanDeptService htcRelativePlanDeptService = null;
	
	
	// 查询方案中已经存在的核算科室
	@RequestMapping(value = "/hrp/htc/relative/plan/set/queryHtcRelativePlanDeptByPlanSetOk", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcRelativePlanDeptByPlanSetOk(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String depts = htcRelativePlanDeptService.queryHtcRelativePlanDeptByPlanSetOk(mapVo);
		return depts;
	}

	// 查询方案中不存在的核算科室
	@RequestMapping(value = "/hrp/htc/relative/plan/set/queryHtcRelativePlanDeptByPlanSetNO", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcRelativePlanDeptByPlanSetNO(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String depts = htcRelativePlanDeptService.queryHtcRelativePlanDeptByPlanSetNO(mapVo);
		return depts;
	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/relative/plan/set/addHtcRelativePlanDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcRelativePlanDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		String planDeptJson = "";
		
		try {
			
			planDeptJson = htcRelativePlanDeptService.addHtcRelativePlanDept(mapVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(planDeptJson);

	}
}
