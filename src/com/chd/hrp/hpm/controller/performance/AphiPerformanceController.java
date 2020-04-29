package com.chd.hrp.hpm.controller.performance;

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
import com.chd.hrp.hpm.service.AphiEmpBonusDataService;
import com.chd.hrp.hpm.service.performance.AphiPerformanceService;

@Controller
public class AphiPerformanceController extends BaseController {
	
	
	private static final Logger logger = Logger.getLogger(AphiPerformanceController.class);  
	
	
	@Resource(name = "aphiPerformanceService")
	private final AphiPerformanceService aphiPerformanceService = null;

	
	@Resource(name = "aphiEmpBonusDataService")
	private final AphiEmpBonusDataService aphiEmpBonusDataService = null;
	
	//主页面跳转
	@RequestMapping(value = "/hrp/hpm/performance/hpmPerformanceMainPage", method = RequestMethod.GET)
	public String hpmPerformanceMainPage(Model model) throws Exception {
		
		return "hrp/hpm/performance/hpmPerformanceMain";
	}
	
	
	@RequestMapping(value = "/hrp/hpm/performance/queryHpmEmpBonusDataForReportGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmEmpBonusDataForReportGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String COPY_CODE = SessionManager.getCopyCode();

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiPerformanceService.queryHpmEmpBonusDataForReportGrid(mapVo);

		return resultJson;
	}
	
	
	// 查询
	@RequestMapping(value = "/hrp/hpm/performance/queryHpmPerformance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmPerformance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		mapVo.put("copy_code", COPY_CODE);
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		String empBonusData = aphiPerformanceService.queryHpmPerformance(getPage(mapVo));

		return JSONObject.parseObject(empBonusData);

	}
	
	
	
}
