package com.chd.hrp.htc.controller.income.readydata.deptincome;

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
import com.chd.hrp.htc.service.income.readydata.deptincome.HtcIncomeProjectCostDeptIncomeService;
@Controller
public class HtcIncomeProjectCostDeptIncomeController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcIncomeProjectCostDeptIncomeController.class);
	
	@Resource(name = "htcIncomeProjectCostDeptIncomeService")
	private final HtcIncomeProjectCostDeptIncomeService htcIncomeProjectCostDeptIncomeService = null;
	
	 // 维护页面跳转         
	@RequestMapping(value = "/hrp/htc/income/readydata/deptincome/htcIncomeProjectCostDeptIncomeMainPage", method = RequestMethod.GET)
	public String htcIncomeProjectCostDeptIncomeMainPage(Model mode) throws Exception {

		return "hrp/htc/income/readydata/deptincome/htcIncomeProjectCostDeptIncomeMain";

	}
	
	@RequestMapping(value = "/hrp/htc/income/readydata/deptincome/disposeHtcIncomeProjectCostDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disposeHtcIncomeProjectCostDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String projectCostDeptIncome = "";
		
		try {
			
			projectCostDeptIncome = htcIncomeProjectCostDeptIncomeService.disposeHtcIncomeProjectCostDeptIncome(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			projectCostDeptIncome = e.getMessage();
		}

		return JSONObject.parseObject(projectCostDeptIncome);

	}
		
	// 查询
	@RequestMapping(value = "/hrp/htc/income/readydata/deptincome/queryHtcIncomeProjectCostDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeProjectCostDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String projectCostDeptIncome = htcIncomeProjectCostDeptIncomeService.queryHtcIncomeProjectCostDeptIncome(getPage(mapVo));

		return JSONObject.parseObject(projectCostDeptIncome);

	}
}
