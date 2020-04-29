package com.chd.hrp.acc.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.serviceImpl.AccSelectServiceImpl;
import com.chd.hrp.sys.controller.HrpSysSelectController;

@Controller
public class AccSelectController extends BaseController {

	private static Logger logger = Logger.getLogger(HrpSysSelectController.class);
            
	@Resource(name = "accSelectService")
	private final AccSelectServiceImpl accSelectService = null;

	// 医院
	@RequestMapping(value = "/hrp/acc/queryHosInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosNature = accSelectService.queryHosInfoDict(mapVo);
		return hosNature;
	}

	//账套
	@RequestMapping(value = "/hrp/acc/queryCopyDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCopyDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosNature = accSelectService.queryCopyDict(mapVo);
		return hosNature;
	}

	//合计年月
	@RequestMapping(value = "/hrp/acc/queryAcctYearDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAcctYearDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hosNature = accSelectService.queryAcctYearDict(mapVo);
		return hosNature;
	}

	// 行业性质
	@RequestMapping(value = "/hrp/acc/queryHosNatureDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosNatureDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosNature = accSelectService.queryHosNatureDict(mapVo);
		return hosNature;
	}
	/**
	 * 核算类下拉框
	 * @param mapVo 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/queryCheckTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String hosNature = accSelectService.queryCheckTypeDict(mapVo);
		return hosNature;
	}
	@RequestMapping(value = "/hrp/acc/queryWageAndNotInThisWage", method = RequestMethod.POST)
	@ResponseBody
	public String queryWageAndNotInThisWage(@RequestParam Map<String, Object> mapVo, Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
	       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String hosNature=accSelectService.queryWageAndNotInThisWage(mapVo);
		return hosNature;
	}
}
