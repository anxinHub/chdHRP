package com.chd.hrp.htcg.controller.calculation;
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
import com.chd.hrp.htcg.service.calculation.HtcgDrugAdminResultService;


@Controller
public class HtcgDrugAdminResultController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgDrugAdminResultController.class);
	
	
	@Resource(name = "htcgDrugAdminResultService")
	private final HtcgDrugAdminResultService htcgDrugAdminResultService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "hrp/htcg/calculation/drugAdminResult/htcgDrugAdminResultMainPage", method = RequestMethod.GET)
	public String htcgDrugAdminResultMainPage(Model mode) throws Exception {
		return "hrp/htcg/calculation/drugAdminResult/htcgDrugAdminResultMain";
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/calculation/drugAdminResult/queryHtcgDrugAdminResult", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgDrugAdminResult(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drgs = htcgDrugAdminResultService.queryHtcgDrugAdminResult(getPage(mapVo));

		return JSONObject.parseObject(drgs);
		
	}
	
}

