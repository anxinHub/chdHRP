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
import com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount.GroupAccHosAuxiliaryAccountServiceImpl;
@Controller
public class GroupAccHosAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccHosAuxiliaryAccountController.class);
	
	@Resource(name = "groupAccHosAuxiliaryAccountService")
	private final GroupAccHosAuxiliaryAccountServiceImpl groupAccHosAuxiliaryAccountService = null;
	
	/**
	*单位科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccHosGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccHosGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccHosGeneralLedgerMain";
	}
	
	/**
	*单位科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccHosDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccHosDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccHosDetailLedgerMain";
	}
	
	/**
	*科目单位总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccSubjHosGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjHosGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccSubjHosGeneralLedgerMain";
	}
	
	/**
	*科目单位明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccSubjHosDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjHosDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccSubjHosDetailLedgerMain";
	}
	
	/**
	*单位余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccHosEndOsMainPage", method = RequestMethod.GET)
	public String groupAccHosEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/groupAccHosEndOsMain";
	}
	
	//单位科目总账 
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/queryGroupAccHosSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccHosSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 8);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccHosAuxiliaryAccountService.collectGroupAccHosSubjGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	//单位科目明细账  
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/queryGroupAccHosSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccHosSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 8);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccHosAuxiliaryAccountService.collectGroupAccHosSubjDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/queryGroupAccSubjHosGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjHosGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 8);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccHosAuxiliaryAccountService.collectGroupAccSubjHosGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/queryGroupAccSubjHosDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjHosDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 8);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccHosAuxiliaryAccountService.collectGroupAccSubjHosDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacchosauxiliaryaccount/queryGroupAccHosEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccHosEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String results = groupAccHosAuxiliaryAccountService.collectGroupAccHosEndOs(getPage(mapVo));

		return JSONObject.parseObject(results);
	}
}
