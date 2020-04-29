package com.chd.hrp.htc.controller.task.readydata.deptcost;

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
import com.chd.hrp.htc.service.task.readydata.deptcost.HtcTaskProjectCostDeptCostService;
@Controller
public class HtcTaskProjectCostDeptCostController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcTaskProjectCostDeptCostController.class);
	
	@Resource(name = "htcTaskProjectCostDeptCostService")
	private final HtcTaskProjectCostDeptCostService htcTaskProjectCostDeptCostService = null;
	
	 // 维护页面跳转         
	@RequestMapping(value = "/hrp/htc/task/readydata/deptcost/htcTaskProjectCostDeptCostMainPage", method = RequestMethod.GET)
	public String htcTaskProjectCostDeptTaskMainPage(Model mode) throws Exception {

		return "hrp/htc/task/readydata/deptcost/htcTaskProjectCostDeptCostMain";

	}
	
	@RequestMapping(value = "/hrp/htc/task/readydata/deptcost/disposeHtcTaskProjectCostDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disposeHtcTaskProjectCostDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String projectCostDeptTask = "";
		
		try {
			
			projectCostDeptTask = htcTaskProjectCostDeptCostService.disposeHtcTaskProjectCostDeptCost(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			projectCostDeptTask = e.getMessage();
		}

		return JSONObject.parseObject(projectCostDeptTask);

	}
		
	// 查询                                     
	@RequestMapping(value = "/hrp/htc/task/readydata/deptcost/queryHtcTaskProjectCostDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskProjectCostDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String projectCostDeptTask = htcTaskProjectCostDeptCostService.queryHtcTaskProjectCostDeptCost(getPage(mapVo));

		return JSONObject.parseObject(projectCostDeptTask);

	}
}
