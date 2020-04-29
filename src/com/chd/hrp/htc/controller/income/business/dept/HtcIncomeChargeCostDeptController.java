package com.chd.hrp.htc.controller.income.business.dept;

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
import com.chd.hrp.htc.service.income.business.dept.HtcIncomeChargeCostDeptService;

@Controller 
public class HtcIncomeChargeCostDeptController extends BaseController{

	private static Logger logger = Logger.getLogger(HtcIncomeChargeCostDeptController.class);
	
	@Resource(name = "htcIncomeChargeCostDeptService")
	private final HtcIncomeChargeCostDeptService htcIncomeChargeCostDeptService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/income/business/dept/htcIncomeChargeCostDeptMainPage", method = RequestMethod.GET)
	public String htcIncomeChargeCostDeptMainPage(Model mode) throws Exception {

		return "hrp/htc/income/business/dept/htcIncomeChargeCostDeptMain";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/income/business/dept/queryHtcIncomeChargeCostDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeChargeCostDept(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
	    mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String chargeCostDept = htcIncomeChargeCostDeptService.queryHtcIncomeChargeCostDept(getPage(mapVo));
	
		return JSONObject.parseObject(chargeCostDept);
	
	}
	
	//核算
	@RequestMapping(value = "/hrp/htc/income/business/dept/collectHtcIncomeChargeCostDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHtcIncomeChargeCostDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String chargeCostDept = "";
		
		try {
	  		
			chargeCostDept = htcIncomeChargeCostDeptService.collectHtcIncomeChargeCostDept(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			chargeCostDept = e.getMessage();
		}
		
		return JSONObject.parseObject(chargeCostDept);

	}
	
}
