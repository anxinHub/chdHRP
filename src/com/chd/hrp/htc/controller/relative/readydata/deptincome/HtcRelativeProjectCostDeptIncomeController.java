package com.chd.hrp.htc.controller.relative.readydata.deptincome;

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
import com.chd.hrp.htc.service.relative.readydata.deptincome.HtcRelativeProjectCostDeptIncomeService;
@Controller
public class HtcRelativeProjectCostDeptIncomeController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcRelativeProjectCostDeptIncomeController.class);
	
	@Resource(name = "htcRelativeProjectCostDeptIncomeService")
	private final HtcRelativeProjectCostDeptIncomeService htcRelativeProjectCostDeptIncomeService = null;
	
	 // 维护页面跳转              
	@RequestMapping(value = "/hrp/htc/relative/readydata/deptincome/htcRelativeProjectCostDeptIncomeMainPage", method = RequestMethod.GET)
	public String htcRelativeProjectCostDeptRelativeMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/readydata/deptincome/htcRelativeProjectCostDeptIncomeMain";

	}
	
	@RequestMapping(value = "/hrp/htc/relative/readydata/deptincome/disposeHtcRelativeProjectCostDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disposeHtcRelativeProjectCostDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String projectCostDeptRelative = "";
		
		try {
			
			projectCostDeptRelative = htcRelativeProjectCostDeptIncomeService.disposeHtcRelativeProjectCostDeptIncome(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			projectCostDeptRelative = e.getMessage();
		}

		return JSONObject.parseObject(projectCostDeptRelative);

	}
		
	// 查询
	@RequestMapping(value = "/hrp/htc/relative/readydata/deptincome/queryHtcRelativeProjectCostDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeProjectCostDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String projectCostDeptRelative = htcRelativeProjectCostDeptIncomeService.queryHtcRelativeProjectCostDeptIncome(getPage(mapVo));

		return JSONObject.parseObject(projectCostDeptRelative);

	}
}
