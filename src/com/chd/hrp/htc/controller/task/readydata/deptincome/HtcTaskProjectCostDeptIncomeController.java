package com.chd.hrp.htc.controller.task.readydata.deptincome;

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
import com.chd.hrp.htc.service.task.readydata.deptincome.HtcTaskProjectCostDeptIncomeService;
@Controller
public class HtcTaskProjectCostDeptIncomeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcTaskProjectCostDeptIncomeController.class);
	
	@Resource(name = "htcTaskProjectCostDeptIncomeService")
	private final HtcTaskProjectCostDeptIncomeService htcTaskProjectCostDeptIncomeService = null;
	
	 // 维护页面跳转                                                                                                                                                                        
	@RequestMapping(value = "/hrp/htc/task/readydata/deptincome/htcTaskProjectCostDeptIncomeMainPage", method = RequestMethod.GET)
	public String htcTaskProjectCostDeptTaskMainPage(Model mode) throws Exception {

		return "hrp/htc/task/readydata/deptincome/htcTaskProjectCostDeptIncomeMain";

	}
	
	@RequestMapping(value = "/hrp/htc/task/readydata/deptincome/disposeHtcTaskProjectCostDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disposeHtcTaskProjectCostDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String projectCostDeptTask = "";
		
		try {
			
			projectCostDeptTask = htcTaskProjectCostDeptIncomeService.disposeHtcTaskProjectCostDeptIncome(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			projectCostDeptTask = e.getMessage();
		}

		return JSONObject.parseObject(projectCostDeptTask);

	}
		
	// 查询
	@RequestMapping(value = "/hrp/htc/task/readydata/deptincome/queryHtcTaskProjectCostDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskProjectCostDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String projectCostDeptTask = htcTaskProjectCostDeptIncomeService.queryHtcTaskProjectCostDeptIncome(getPage(mapVo));

		return JSONObject.parseObject(projectCostDeptTask);

	}
}
