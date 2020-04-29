package com.chd.hrp.acc.controller.books.groupauxiliaryaccount;

import java.util.ArrayList;
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
import com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount.GroupAccProjAuxiliaryAccountServiceImpl;

@Controller
public class GroupAccProjAuxiliaryAccountController extends BaseController {
	private static Logger logger = Logger.getLogger(GroupAccProjAuxiliaryAccountController.class);

	@Resource(name = "groupAccProjAuxiliaryAccountService")
	private final GroupAccProjAuxiliaryAccountServiceImpl groupAccProjAuxiliaryAccountService = null;

	/**
	 * 项目科目总账<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccProjGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accProjGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccProjGeneralLedgerMain";
	}

	/**
	 * 项目科目明细账<BR>
	 * 维护页面跳转
	 */ 
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccProjDetailLedgerMainPage", method = RequestMethod.GET)
	public String accProjDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccProjDetailLedgerMain";
	}

	/**
	 * 科目项目总账<BR>
	 * 维护页面跳转
	 */ 
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccSubjProjGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjProjGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccSubjProjGeneralLedgerMain";
	}

     /**
	 * 科目项目明细账<BR>
	 * 维护页面跳转
	 */ 
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccSubjProjDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjProjDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccSubjProjDetailLedgerMain";
	}

	/**
	 * 项目余额表<BR>
	 * 维护页面跳转
	 */ 
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccProjEndOsMainPage", method = RequestMethod.GET)
	public String accProjEndOsMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4) + "." + yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/groupAccProjEndOsMain";
	}

	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/queryGroupAccProjSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccProjSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("table_flag", 3);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		// mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccProjAuxiliaryAccountService.collectGroupAccProjSubjGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}

	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/queryGroupAccProjSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccProjSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("table_flag", 3);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		// mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccProjAuxiliaryAccountService.collectGroupAccProjSubjDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}

	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/queryGroupAccSubjProjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjProjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("table_flag", 3);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		// mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccProjAuxiliaryAccountService.collectGroupAccSubjProjGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}

	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/queryGroupAccSubjProjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjProjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("table_flag", 3);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		// mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccProjAuxiliaryAccountService.collectGroupAccSubjProjDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}

	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccprojauxiliaryaccount/queryGroupAccProjEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccProjEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		String results = groupAccProjAuxiliaryAccountService.collectGroupAccProjEndOs(mapVo);

		return JSONObject.parseObject(results);
	} 
}
