package com.chd.hrp.htc.controller.task.business.dept;

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
import com.chd.hrp.htc.service.task.business.dept.HtcTaskChargeCostDeptService;

@Controller
public class HtcTaskChargeCostDeptController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcTaskChargeCostDeptController.class);

	@Resource(name = "htcTaskChargeCostDeptService")
	private final HtcTaskChargeCostDeptService htcTaskChargeCostDeptService = null;
	

	@RequestMapping(value = "/hrp/htc/task/business/dept/htcTaskChargeCostDeptMainPage", method = RequestMethod.GET)
	public String htcTaskChargeCostDeptMainPage(Model mode) throws Exception {
		return "hrp/htc/task/business/dept/htcTaskChargeCostDeptMain";
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/business/dept/queryHtcTaskChargeCostDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskChargeCostDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String htcTaskChargeCostDeptJson = htcTaskChargeCostDeptService.queryHtcTaskChargeCostDept(getPage(mapVo));

		return JSONObject.parseObject(htcTaskChargeCostDeptJson);

	}
	
	//核算
	@RequestMapping(value = "/hrp/htc/task/business/dept/collectHtcTaskChargeCostDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHtcTaskChargeCostDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcTaskChargeCostDeptJson = "";
		
		try {
	  		
			htcTaskChargeCostDeptJson = htcTaskChargeCostDeptService.collectHtcTaskChargeCostDept(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			htcTaskChargeCostDeptJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcTaskChargeCostDeptJson);

	}
}
