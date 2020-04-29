package com.chd.hrp.htc.controller.relative.readydata.deptcost;

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
import com.chd.hrp.htc.service.relative.readydata.depcost.HtcRelativeProjectCostDeptCostService;
@Controller
public class HtcRelativeProjectCostDeptCostController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcRelativeProjectCostDeptCostController.class);
	
	@Resource(name = "htcRelativeProjectCostDeptCostService")
	private final HtcRelativeProjectCostDeptCostService htcRelativeProjectCostDeptCostService = null;
	
	 // 维护页面跳转         
	@RequestMapping(value = "/hrp/htc/relative/readydata/deptcost/htcRelativeProjectCostDeptCostMainPage", method = RequestMethod.GET)
	public String htcRelativeProjectCostDeptRelativeMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/readydata/deptcost/htcRelativeProjectCostDeptCostMain";

	}
	
	@RequestMapping(value = "/hrp/htc/relative/readydata/deptcost/disposeHtcRelativeProjectCostDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disposeHtcRelativeProjectCostDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String projectCostDeptRelative = "";
		
		try {
			
			projectCostDeptRelative = htcRelativeProjectCostDeptCostService.disposeHtcRelativeProjectCostDeptCost(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			projectCostDeptRelative = e.getMessage();
		}

		return JSONObject.parseObject(projectCostDeptRelative);

	}
		
	// 查询                                     
	@RequestMapping(value = "/hrp/htc/relative/readydata/deptcost/queryHtcRelativeProjectCostDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeProjectCostDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String projectCostDeptRelative = htcRelativeProjectCostDeptCostService.queryHtcRelativeProjectCostDeptCost(getPage(mapVo));

		return JSONObject.parseObject(projectCostDeptRelative);

	}
}
