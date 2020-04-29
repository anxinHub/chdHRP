package com.chd.hrp.flw.controller.instance;


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
import com.chd.hrp.flw.service.instance.FlowSendService;

@Controller
@RequestMapping(value="/hrp/flw/instance")
public class FlowSendController extends BaseController{
	
	private static Logger logger = Logger.getLogger(FlowSendController.class);
	@Resource(name = "flowSendService")
	private final FlowSendService flowSendService = null;

	// 新建事项页面跳转
	@RequestMapping(value = "/flowSendPage", method = RequestMethod.GET)
	public String flowSendPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/flw/instance/send/main";
	}
	
	// 选择模板
	@RequestMapping(value = "/flowSelectSendPage", method = RequestMethod.GET)
	public String flowSelectSendPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/flw/instance/send/selectFlow";
	}
	
	//查询激活状态的流程类别、部署ID
	@RequestMapping(value = "/queryFlowCategoryDeploymentId", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> queryFlowCategoryDeploymentId(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String jsonStr=flowSendService.queryFlowCategoryDeploymentId(mapVo);
		return JSONObject.parseObject(jsonStr);
	}
}
