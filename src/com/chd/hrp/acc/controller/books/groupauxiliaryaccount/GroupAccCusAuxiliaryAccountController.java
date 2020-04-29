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
import com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount.GroupAccCusAuxiliaryAccountServiceImpl;
@Controller
public class GroupAccCusAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccCusAuxiliaryAccountController.class);
	@Resource(name = "groupAccCusAuxiliaryAccountService")
	private final GroupAccCusAuxiliaryAccountServiceImpl groupAccCusAuxiliaryAccountService = null;
	/**
	*客户科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccCusGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccCusGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccCusGeneralLedgerMain";
	}
	
	/**
	*客户科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccCusDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccCusDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccCusDetailLedgerMain";
	}
	
	/**
	*科目客户总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccSubjCusGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjCusGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccSubjCusGeneralLedgerMain";
	}
	
	/**
	*科目客户明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccSubjCusDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjCusDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccSubjCusDetailLedgerMain";
	}
	
	/**
	*客户余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccCusEndOsMainPage", method = RequestMethod.GET)
	public String groupAccCusEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/groupAccCusEndOsMain";
	}
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/queryGroupAccCusSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccCusSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 5);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		//mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccCusAuxiliaryAccountService.collectGroupAccCusSubjGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/queryGroupAccCusSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccCusSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 5);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		//mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccCusAuxiliaryAccountService.collectGroupAccCusSubjDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/queryGroupAccSubjCusGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjCusGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 5);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		//mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccCusAuxiliaryAccountService.collectGroupAccSubjCusGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/queryGroupAccSubjCusDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjCusDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 5);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		//mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccCusAuxiliaryAccountService.collectGroupAccSubjCusDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupacccusauxiliaryaccount/queryGroupAccCusEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccCusEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String results = groupAccCusAuxiliaryAccountService.collectGroupAccCusEndOs(mapVo);

		return JSONObject.parseObject(results);
	}
}
