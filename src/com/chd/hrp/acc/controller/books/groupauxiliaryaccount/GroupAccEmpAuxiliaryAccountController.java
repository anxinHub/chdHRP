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
import com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount.GroupAccEmpAuxiliaryAccountServiceImpl;
@Controller
public class GroupAccEmpAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccEmpAuxiliaryAccountController.class);
	
	@Resource(name = "groupAccEmpAuxiliaryAccountService")
	private final GroupAccEmpAuxiliaryAccountServiceImpl groupAccEmpAuxiliaryAccountService = null;
	
	/**
	*项目科目总账<BR>
	*维护页面跳转
	*/
	 @RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccEmpGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccEmpGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccEmpGeneralLedgerMain";
	}
	
	/**
	*项目科目明细账<BR>
	*维护页面跳转
	*/ 
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccEmpDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccEmpDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccEmpDetailLedgerMain";
	}
	
    /**
	*科目项目总账<BR>
	*维护页面跳转
	*/ 
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccSubjEmpGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjEmpGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccSubjEmpGeneralLedgerMain";
	}
	
	/**
	*科目项目明细账<BR>
	*维护页面跳转
	*/ 
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccSubjEmpDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjEmpDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccSubjEmpDetailLedgerMain";
	}
	
	/**
	*职工余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccEmpEndOsMainPage", method = RequestMethod.GET)
	public String groupAccEmpEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/groupAccEmpEndOsMain";
	}
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/queryGroupAccEmpSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccEmpSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("source_id", "0");
		mapVo.put("table_flag", 2);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccEmpAuxiliaryAccountService.collectGroupAccEmpSubjGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/queryGroupAccEmpSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccEmpSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("source_id", "0");
		mapVo.put("table_flag", 2);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccEmpAuxiliaryAccountService.collectGroupAccEmpSubjDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/queryGroupAccSubjEmpGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjEmpGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("source_id", "0");
		mapVo.put("table_flag", 2);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccEmpAuxiliaryAccountService.collectGroupAccSubjEmpGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/queryGroupAccSubjEmpDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjEmpDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("source_id", "0");
		mapVo.put("table_flag", 2);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccEmpAuxiliaryAccountService.collectGroupAccSubjEmpDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccempauxiliaryaccount/queryGroupAccEmpEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccEmpEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String results = groupAccEmpAuxiliaryAccountService.collectGroupAccEmpEndOs(getPage(mapVo));

		return JSONObject.parseObject(results);
	} 
}
