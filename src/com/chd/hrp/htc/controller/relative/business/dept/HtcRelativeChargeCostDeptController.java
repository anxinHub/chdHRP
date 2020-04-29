package com.chd.hrp.htc.controller.relative.business.dept;

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
import com.chd.hrp.htc.service.relative.business.dept.HtcRelativeChargeCostDeptService;

@Controller
public class HtcRelativeChargeCostDeptController extends BaseController{

	private static Logger logger = Logger.getLogger(HtcRelativeChargeCostDeptController.class);
	
	@Resource(name = "htcRelativeChargeCostDeptService")
	private final HtcRelativeChargeCostDeptService htcRelativeChargeCostDeptService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/relative/business/dept/htcRelativeChargeCostDeptMainPage", method = RequestMethod.GET)
	public String htcRelativeChargeCostDeptMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/business/dept/htcRelativeChargeCostDeptMain";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/relative/business/dept/queryHtcRelativeChargeCostDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeChargeCostDept(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
	    mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String chargeCostDept = htcRelativeChargeCostDeptService.queryHtcRelativeChargeCostDept(getPage(mapVo));
	
		return JSONObject.parseObject(chargeCostDept);
	
	}
	
	//核算
	@RequestMapping(value = "/hrp/htc/relative/business/dept/collectHtcRelativeChargeCostDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHtcRelativeChargeCostDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String chargeCostDept = "";
		
		try {
	  		
			chargeCostDept = htcRelativeChargeCostDeptService.collectHtcRelativeChargeCostDept(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			chargeCostDept = e.getMessage();
		}
		
		return JSONObject.parseObject(chargeCostDept);

	}
	
}
