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
import com.chd.hrp.hpm.service.report.AphiPostBonusRatioService;

@Controller
public class AphiPostBonusRatioController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiPostBonusRatioController.class);
	
	@Resource(name = "aphiPostBonusRatioService")
	private AphiPostBonusRatioService aphiPostBonusRatioService = null;

	@RequestMapping(value = "/hpm/report/queryPostBonusRatioMainPage", method = RequestMethod.GET)
	public String queryPostBonusRatioMainPage(Model model) throws Exception {
		return "hpm/report/postBonusRatioMain";
	}

	@RequestMapping(value = "/hpm/report/queryPostBonusRatioByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPostBonusRatioByCode(
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
		
		String PostBonusRatio = aphiPostBonusRatioService
				.queryPostBonusRatioByCode(getPage(mapVo));
		
		return JSONObject.parseObject(PostBonusRatio);
	}
}
