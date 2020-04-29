package com.chd.hrp.acc.controller.verification;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.chd.hrp.acc.serviceImpl.AccLederCheckServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccLederServiceImpl;
import com.chd.hrp.acc.serviceImpl.verification.AccBudgRangeServiceImpl;
import com.chd.hrp.acc.serviceImpl.verification.AccVerificationServiceImpl;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchCheckServiceImpl;

@Controller
public class AccNostroAgeAnalysisController extends BaseController {
	private static Logger logger = Logger.getLogger(AccBadDebtsPreparaController.class);
	
	@Resource(name = "accVouchCheckService")
	private final AccVouchCheckServiceImpl accVouchCheckService = null;
	
	@Resource(name = "accBudgRangeService")
	private final AccBudgRangeServiceImpl accBudgRangeService = null;
	
	
	@Resource(name = "accVerificationService")
	private final AccVerificationServiceImpl accVerificationService = null;
	
	@Resource(name = "accLederService")
	private final AccLederServiceImpl accLederService = null;
	
	//应收账龄分析跳转
	@RequestMapping(value = "/hrp/acc/accnostroageanalysis/receivableAccountAnalysisMainPage", method = RequestMethod.GET)
	public String receivableAccountAnalysisMainPage(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String rs = accBudgRangeService.queryAccBugRangeTop(mapVo);
		
		mode.addAttribute("rangeIsExits", rs);
		
		return "hrp/acc/accnostroageanalysis/receivableAccountAnalysisMain";
	}
	
	//应付账龄分析跳转
	@RequestMapping(value = "/hrp/acc/accnostroageanalysis/payableAccountAnalysisMainPage", method = RequestMethod.GET)
	public String payableAccountAnalysisMainPage(Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String rs = accBudgRangeService.queryAccBugRangeTop(mapVo);
		
		mode.addAttribute("rangeIsExits", rs);
		
		return "hrp/acc/accnostroageanalysis/payableAccountAnalysisMain";
	}
	
	//应付账龄分析查询
	@RequestMapping(value = "/hrp/acc/accnostroageanalysis/queryReceivableAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReceivableAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		//mapVo.put("objdata", new ArrayList<Map<String, Object>>());
	    String result = accVerificationService.collectReceivableAccount(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	
	//应付账龄分析查询
	@RequestMapping(value = "/hrp/acc/accnostroageanalysis/queryPayAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPayAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
	    String result  = accVerificationService.collectReceivableAccount(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	/**
	 * 单位字典
	 */
	@RequestMapping(value = "/hrp/acc/accnostroageanalysis/queryVHosDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryVHosDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String subj = accLederService.queryHosInfo(mapVo);
		return subj;
		
	}
}
