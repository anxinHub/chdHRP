
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
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.tongJiReports.AssAgeAnalyzeService;
 

/**
 * @Description: 050701 
 * @Table: ASS_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssAgeAnalyzeController extends BaseController {
	
	@Resource(name = "assAgeAnalyzeService")
	private final AssAgeAnalyzeService assAgeAnalyzeService = null;
	
	
	@RequestMapping(value = "/hrp/ass/tongJiReports/assAgeAnalyzePage", method = RequestMethod.GET)
	public String assAgeAnalyzePage(Model mode) throws Exception {

		return "hrp/ass/tongJiReports/assAgeAnalyze";

	}
	
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssAgeAnalyze", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAgeAnalyze(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assAgeAnalyze = assAgeAnalyzeService.queryAssCardUseYearOrLifeYear(getPage(mapVo));

		return JSONObject.parseObject(assAgeAnalyze);
		
	}
	  
}
