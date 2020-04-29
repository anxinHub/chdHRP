package com.chd.hrp.htc.controller.income.plan.set;

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
import com.chd.base.SessionManager;
import com.chd.hrp.htc.service.income.plan.set.HtcIncomePlanDeptService;

@Controller
public class HtcIncomePlanDeptController {
	
	private static Logger logger = Logger.getLogger(HtcIncomePlanDeptController.class);

	@Resource(name = "htcIncomePlanDeptService")
	private final HtcIncomePlanDeptService htcIncomePlanDeptService = null;
	
	
	// 查询方案中已经存在的核算科室
	@RequestMapping(value = "/hrp/htc/income/plan/set/queryHtcIncomePlanDeptByPlanSetOk", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcIncomePlanDeptByPlanSetOk(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String depts = htcIncomePlanDeptService.queryHtcIncomePlanDeptByPlanSetOk(mapVo);
		return depts;
	}

	// 查询方案中不存在的核算科室
	@RequestMapping(value = "/hrp/htc/income/plan/set/queryHtcIncomePlanDeptByPlanSetNO", method = RequestMethod.POST)
	@ResponseBody
	public String queryHtcIncomePlanDeptByPlanSetNO(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String depts = htcIncomePlanDeptService.queryHtcIncomePlanDeptByPlanSetNO(mapVo);
		return depts;
	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/income/plan/set/addHtcIncomePlanDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIncomePlanDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		String planDeptJson = "";
		
		try {
			
			planDeptJson = htcIncomePlanDeptService.addHtcIncomePlanDept(mapVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(planDeptJson);

	}
}
