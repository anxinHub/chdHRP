package com.chd.hrp.htc.controller.task.business;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.hrp.htc.entity.task.business.HtcWorkTest;
import com.chd.hrp.htc.service.task.business.HtcWorkTestService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcWorkTestController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcWorkTestController.class);
	
	
	@Resource(name = "htcWorkTestService")
	private final HtcWorkTestService htcWorkTestService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/business/worktest/htcWorkTestMainPage", method = RequestMethod.GET)
	public String htcWorkTestMainPage(Model mode) throws Exception {
		return "/hrp/htc/task/business/worktest/htcWorkTestMain";

	}
	
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/business/worktest/queryHtcWorkTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcWorkTest(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcWorkTestJson = htcWorkTestService.queryHtcWorkTest(getPage(mapVo));
		
		return JSONObject.parseObject(htcWorkTestJson);		
	}
	
	// 校验
	@RequestMapping(value = "/hrp/htc/task/business/worktest/checkHtcWorkTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkHtcWorkTest(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcWorkTestJson = "";
		try {
			htcWorkTestJson = htcWorkTestService.checkHtcWorkTest(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcWorkTestJson = e.getMessage();
		}
		return JSONObject.parseObject(htcWorkTestJson);
	}
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/business/worktest/htcWorkTestDetailMainPage", method = RequestMethod.GET)
	public String workTestDetailMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("plan_code", mapVo.get("plan_code"));
		mode.addAttribute("proj_dept_id", mapVo.get("proj_dept_id"));
		return "hrp/htc/task/business/worktest/htcWorkTestDetailMain";
		
	}
		
	// 查询 作业项目信息
	@RequestMapping(value = "/hrp/htc/task/business/worktest/queryHtcWorkDetailTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWorkDetailTest(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcWorkTestJson = htcWorkTestService.queryHtcWorkDetailTest(getPage(mapVo));
		return JSONObject.parseObject(htcWorkTestJson);
		
	}
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/business/worktest/htcWorkTestItemDetailMainPage", method = RequestMethod.GET)
	public String workTestItemDetailMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("plan_code", mapVo.get("plan_code"));
		mode.addAttribute("proj_dept_id", mapVo.get("proj_dept_id"));
		mode.addAttribute("work_code", mapVo.get("work_code"));
		return "hrp/htc/task/business/worktest/htcWorkTestItemDetailMain";
	}
	
	
	// 查询 作业对应收费项目信息
	@RequestMapping(value = "/hrp/htc/task/business/worktest/queryHtcWorkItemDetailTest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWorkItemDetailTest(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		System.out.println(mapVo);
		String htcWorkTestJson = htcWorkTestService.queryHtcWorkItemDetailTest(getPage(mapVo));
		return JSONObject.parseObject(htcWorkTestJson);	
	}
	
}

