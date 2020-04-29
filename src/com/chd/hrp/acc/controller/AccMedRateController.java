package com.chd.hrp.acc.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.AccMedRateService;

/**
 * 财务----账簿-----科室占药比
 * 河北四院使用。
 * 
 * @author haotong
 *
 */
@Controller
public class AccMedRateController extends BaseController{
	
	@Resource(name = "accMedRateService")
	private final AccMedRateService accMedRateService = null;
	
	@RequestMapping(value = "/hrp/acc/medRate/medRateMainPage", method = RequestMethod.GET)
	public String accBadDebtsMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/acc/medrate/medRateMain";
	}
	
	//查询
	@RequestMapping(value = "/hrp/acc/medRate/queryMedRate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedRate(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		return JSON.parseObject(accMedRateService.queryMedRate(getPage(mapVo)));
	}
	

	
}
