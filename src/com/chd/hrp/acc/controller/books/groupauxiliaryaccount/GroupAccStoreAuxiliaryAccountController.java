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
import com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount.GroupAccStoreAuxiliaryAccountServiceImpl;
@Controller
public class GroupAccStoreAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccStoreAuxiliaryAccountController.class);
	
	@Resource(name = "groupAccStoreAuxiliaryAccountService")
	private final GroupAccStoreAuxiliaryAccountServiceImpl groupAccStoreAuxiliaryAccountService = null;
	
	/**
	*项目科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccStoreGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccStoreGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccStoreGeneralLedgerMain";
	}
	
	/**
	*项目科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccStoreDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccStoreDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccStoreDetailLedgerMain";
	}
	
	/**
	*科目项目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccSubjStoreGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjStoreGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccSubjStoreGeneralLedgerMain";
	}
	
	/**
	*科目项目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccSubjStoreDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjStoreDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccSubjStoreDetailLedgerMain";
	}
	
	/**
	*库房余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccStoreEndOsMainPage", method = RequestMethod.GET)
	public String groupAccStoreEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/groupAccStoreEndOsMain";
	}
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/queryGroupAccStoreSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccStoreSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 4);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccStoreAuxiliaryAccountService.collectGroupAccStoreSubjGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/queryGroupAccStoreSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccStoreSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 4);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccStoreAuxiliaryAccountService.collectGroupAccStoreSubjDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/queryGroupAccSubjStoreGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjStoreGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 4);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccStoreAuxiliaryAccountService.collectGroupAccSubjStoreGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/queryGroupAccSubjStoreDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjStoreDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 4);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccStoreAuxiliaryAccountService.collectGroupAccSubjStoreDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccstoreauxiliaryaccount/queryGroupAccStoreEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccStoreEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String results = groupAccStoreAuxiliaryAccountService.collectGroupAccStoreEndOs(mapVo);

		return JSONObject.parseObject(results);
	}
}
