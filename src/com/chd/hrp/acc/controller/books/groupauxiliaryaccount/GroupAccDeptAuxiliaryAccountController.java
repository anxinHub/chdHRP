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
import com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount.GroupAccDeptAuxiliaryAccountServiceImpl;
@Controller
public class GroupAccDeptAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccDeptAuxiliaryAccountController.class);
	@Resource(name = "groupAccDeptAuxiliaryAccountService")
	private final GroupAccDeptAuxiliaryAccountServiceImpl groupAccDeptAuxiliaryAccountService = null;
	/**
	*部门科目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccDeptGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccDeptGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));

		return "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccDeptGeneralLedgerMain";
	}
	
	/**
	*部门科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccDeptDetailLedgerMainPage", method = RequestMethod.GET)
	public String accDeptDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));

		return "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccDeptDetailLedgerMain";
	}
	
	/**
	*科目部门总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccSubjDeptGeneralLedgerMainPage", method = RequestMethod.GET)
	public String accSubjDeptGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));

		return "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccSubjDeptGeneralLedgerMain";
	}
	
	/**
	*科目部门明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccSubjDeptDetailLedgerMainPage", method = RequestMethod.GET)
	public String accSubjDeptDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));

		return "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccSubjDeptDetailLedgerMain";
	}
	
	/**
	*部门余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccDeptEndOsMainPage", method = RequestMethod.GET)
	public String accDeptEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));

		return "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/groupAccDeptEndOsMain";
	}
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/queryGroupAccDeptSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccDeptSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 1);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		//mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccDeptAuxiliaryAccountService.collectGroupAccDeptSubjGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/queryGroupAccDeptSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccDeptSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 1);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		//mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccDeptAuxiliaryAccountService.collectGroupAccDeptSubjDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/queryGroupAccSubjDeptGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjDeptGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 1);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		//mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccDeptAuxiliaryAccountService.collectGroupAccSubjDeptGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/queryGroupAccSubjDeptDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjDeptDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 1);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		//mapVo.put("objdata", OracleTypes. CURSOR);
		String accLederZcheck = groupAccDeptAuxiliaryAccountService.collectGroupAccSubjDeptDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "hrp/acc/groupaccauxiliaryaccount/groupaccdeptauxiliaryaccount/queryGroupAccDeptEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccDeptEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 1);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		
		String results = groupAccDeptAuxiliaryAccountService.collectGroupAccDeptBalance(mapVo);

		return JSONObject.parseObject(results);
	} 
}
