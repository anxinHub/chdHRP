package com.chd.hrp.htc.controller.task.business;

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
import com.chd.hrp.htc.service.task.business.HtcSrouceTestService;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcSrouceTestController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcSrouceTestController.class);

	@Resource(name = "htcSrouceTestService")
	private final HtcSrouceTestService htcSrouceTestService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/business/sroucetest/htcSrouceTestMainPage", method = RequestMethod.GET)
	public String htcSrouceTestMainPage(Model mode) throws Exception {
		return "hrp/htc/task/business/sroucetest/htcSrouceTestMain";
	}
	
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/business/sroucetest/queryHtcSrouceTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcSrouceTest(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcSrouceTestJson = htcSrouceTestService.queryHtcSrouceTest(getPage(mapVo));
		
		return JSONObject.parseObject(htcSrouceTestJson);

	}
	
	// 校验
	@RequestMapping(value = "/hrp/htc/task/business/sroucetest/checkHtcSrouceTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkHtcSrouceTest(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcSrouceTestJson = "";
		try {
			htcSrouceTestJson = htcSrouceTestService.checkHtcSrouceTest(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcSrouceTestJson = e.getMessage();
		}
		return JSONObject.parseObject(htcSrouceTestJson);
	}
		
	@RequestMapping(value = "/hrp/htc/task/business/sroucetest/htcSrouceTestCostItemMainPage", method = RequestMethod.GET)
	public String htcSrouceTestCostItemMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("plan_code", mapVo.get("plan_code"));
		mode.addAttribute("proj_dept_id", mapVo.get("proj_dept_id"));
		return "hrp/htc/task/business/sroucetest/htcSrouceTestCostItemMain";

	}	
    
	// 查询
	@RequestMapping(value = "/hrp/htc/task/business/sroucetest/queryHtcSrouceTestCostItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcSrouceTestCostItem (@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String htcSrouceTestJson = htcSrouceTestService.queryHtcSrouceTestCostItem(getPage(mapVo));

		return JSONObject.parseObject(htcSrouceTestJson);

	}
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/business/sroucetest/htcSrouceTestCostItemSourceMainPage", method = RequestMethod.GET)
	public String htcSrouceTestCostItemSourceMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("plan_code", mapVo.get("plan_code"));
		mode.addAttribute("proj_dept_id", mapVo.get("proj_dept_id"));
		mode.addAttribute("cost_item_id", mapVo.get("cost_item_id"));
		return "hrp/htc/task/business/sroucetest/htcSrouceTestCostItemSourceMain";
	}
		
		
	@RequestMapping(value = "/hrp/htc/task/business/sroucetest/queryHtcSrouceTestCostItemSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcSrouceTestCostItemSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		String htcSrouceTestJson = htcSrouceTestService.queryHtcSrouceTestCostItemSource(getPage(mapVo));

		return JSONObject.parseObject(htcSrouceTestJson);

	}
	
}
