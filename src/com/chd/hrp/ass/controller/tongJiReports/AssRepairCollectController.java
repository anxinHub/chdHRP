package com.chd.hrp.ass.controller.tongJiReports;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.tongJiReports.AssRepairCollectService;

@Controller
public class AssRepairCollectController {
	
	@Resource(name="assRepairCollectService")
	private final AssRepairCollectService assRepairCollectService = null;
	
	
	
	@RequestMapping(value = "/hrp/ass/tongJiReports/AssRepairCollect/assRepairCollectPage", method = RequestMethod.GET)
	public String assAgeAnalyzePage(Model mode) throws Exception {

		return "hrp/ass/tongJiReports/assRepairCollectMain";

	}
	@RequestMapping(value = "/hrp/ass/tongJiReports/AssRepairCollect/queryAssRepairCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assAgeAnalyze = assRepairCollectService.queryAssRepairCollect(mapVo);

		return JSONObject.parseObject(assAgeAnalyze);
		
	}
}
