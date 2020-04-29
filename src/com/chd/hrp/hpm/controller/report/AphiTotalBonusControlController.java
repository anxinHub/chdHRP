package com.chd.hrp.hpm.controller.report;

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
import com.chd.hrp.hpm.service.report.AphiTotalBonusControlService;

@Controller
public class AphiTotalBonusControlController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiTotalBonusControlController.class);
	
	@Resource(name = "aphiTotalBonusControlService")
	private final AphiTotalBonusControlService aphiTotalBonusControlService = null;

	@RequestMapping(value = "/hrp/hpm/hpmreport/hpmTotalBonusControlMainPage", method = RequestMethod.GET)
	public String hpmTotalBonusControlMainPage(Model model) throws Exception {
		return "hrp/hpm/hpmreport/hpmTotalBonusControlMain";
	}

	@RequestMapping(value = "/hrp/hpm/hpmreport/queryHpmTotalBonusControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmTotalBonusControl(
			@RequestParam Map<String, Object> mapVo, Model model)
			throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String totalBoundsCalculation = aphiTotalBonusControlService.queryTotalBonusControlMainPage(getPage(mapVo));
		
		return JSONObject.parseObject(totalBoundsCalculation);
	}
}
