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
import com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount.GroupAccSupAuxiliaryAccountServiceImpl;
@Controller
public class GroupAccSupAuxiliaryAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccSupAuxiliaryAccountController.class);
	
	@Resource(name = "groupAccSupAuxiliaryAccountService")
	private final GroupAccSupAuxiliaryAccountServiceImpl groupAccSupAuxiliaryAccountService = null;
	
	/**
	*项目科目总账<BR>
	*维护页面跳转
	*/						 		   
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSupGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSupGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSupGeneralLedgerMain";
	}
	
	/**
	*项目科目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSupDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSupDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSupDetailLedgerMain";
	}
	
	/**
	*科目项目总账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSubjSupGeneralLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjSupGeneralLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSubjSupGeneralLedgerMain";
	}
	
	/**
	*科目项目明细账<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSubjSupDetailLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjSupDetailLedgerMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSubjSupDetailLedgerMain";
	}
	
	/**
	*供应商余额表<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSupEndOsMainPage", method = RequestMethod.GET)
	public String groupAccSupEndOsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/groupAccSupEndOsMain";
	}
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/queryGroupAccSupSubjGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSupSubjGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 6);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccSupAuxiliaryAccountService.collectGroupAccSupSubjGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/queryGroupAccSupSubjDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSupSubjDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 6);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccSupAuxiliaryAccountService.collectGroupAccSupSubjDetailLedger(mapVo);
		return JSONObject.parseObject(accLederZcheck);
	}
	
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/queryGroupAccSubjSupGeneralLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjSupGeneralLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 6);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccSupAuxiliaryAccountService.collectGroupAccSubjSupGeneralLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/queryGroupAccSubjSupDetailLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjSupDetailLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
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
		mapVo.put("table_flag", 6);
		mapVo.put("objdata", new ArrayList<Map<String, Object>>());
		String accLederZcheck = groupAccSupAuxiliaryAccountService.collectGroupAccSubjSupDetailLedger(mapVo);

		return JSONObject.parseObject(accLederZcheck);
	}
	
	@RequestMapping(value = "/hrp/acc/groupaccauxiliaryaccount/groupaccsupauxiliaryaccount/queryGroupAccSupEndOs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSupEndOs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String results = groupAccSupAuxiliaryAccountService.collectGroupAccSupEndOs(mapVo);

		return JSONObject.parseObject(results);
	}
}
