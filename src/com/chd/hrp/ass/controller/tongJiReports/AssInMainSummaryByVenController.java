
package com.chd.hrp.ass.controller.tongJiReports;
 

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.tongJiReports.AssInMainSummaryByVenService;
 

@Controller
public class AssInMainSummaryByVenController extends BaseController {
	
	private static Logger logger = Logger.getLogger(AssInMainSummaryByVenController.class);

	// 引入Service服务
	@Resource(name = "assInMainSummaryByVenService")
	private final AssInMainSummaryByVenService assInMainSummaryByVenService = null;

	//主页面跳转
	@RequestMapping(value = "/hrp/ass/tongJiReports/assInMainSummaryByVenPage", method = RequestMethod.GET)
	public String assInMainSummaryByVenPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assInMainSummaryByVen";

	}
	
	//查询数据 带分页
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssInMainSummaryByVen", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainSummaryByVen(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMain = assInMainSummaryByVenService.queryAssInMainSummaryByVen(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}

	 

 
}
