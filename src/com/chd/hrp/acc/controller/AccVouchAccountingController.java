package com.chd.hrp.acc.controller;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.serviceImpl.AccVouchAccountingServiceImpl;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccVouchAccountingController extends BaseController {
	private static Logger logger = Logger.getLogger(AccVouchAccountingController.class);

	@Resource(name = "accVouchAccountingService")
	private final AccVouchAccountingServiceImpl accVouchAccountingService = null;
	
	/**
	 * 凭证记账统计<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/accVouchAccountingCountMainPage", method = RequestMethod.GET)
	public String accVouchAccountingCountMainPage(Model mode) throws Exception {
		
		String yearMonth=MyConfig.getCurAccYearMonth();//SessionManager.getSysYearMonth("acc_flag");
		
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Integer parent_node_id =accVouchAccountingService.queryAccVouchFlow(mapVo);
		
		mode.addAttribute("parent_node_id", parent_node_id);
		
		return "hrp/acc/accvouch/accounting/accVouchAccountingCountMain";
		
	}
	/**
	 * 凭证已记账<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/accVouchAccountingMainPage", method = RequestMethod.GET)
	public String accVouchAccountingMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		
		/*String acc_year_month = String.valueOf(mapVo.get("acc_year"));
		
		String acc_year = acc_year_month.split(".")[0];
		String acc_month = acc_year_month.split(".")[1];*/
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		
		mode.addAttribute("vouch_type_code", mapVo.get("vouch_type_code"));
		
		return "hrp/acc/accvouch/accounting/accVouchAccountingMain";
		
	}
	/**
	 * 凭证未记账<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/accVouchUnAccountingMainPage", method = RequestMethod.GET)
	public String accVouchUnAccountingMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("vouch_type_code", mapVo.get("vouch_type_code"));
		
		return "hrp/acc/accvouch/accounting/accVouchUnAccountingMain";
		
	}
	
	/**
	 * 凭证记账报告<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/accVouchAccountingReportMainPage", method = RequestMethod.GET)
	public String accVouchAccountingReportMainPage(Model mode) throws Exception {
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accvouch/accounting/accVouchAccountingReportMain";
		
	}
	/**
	 * 凭证主表<BR>
	 * 查询记账未记账相关数据
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/queryAccVouchAccount", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccVouchAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String accVouch = accVouchAccountingService.queryAccVouchAccount(getPage(mapVo));

		return JSONObject.parseObject(accVouch);

	}
	
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/queryAccVouchAccounting", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccVouchAccounting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accVouch = accVouchAccountingService.queryAccVouchAccounting(getPage(mapVo));

		return JSONObject.parseObject(accVouch);

	}
	


	/**
	 * 凭证主表<BR>
	 * 查询记账未记账相关数据
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/queryAccVouchUnAccounting", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccVouchUnAccounting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accVouch = accVouchAccountingService.queryAccVouchUnAccounting(getPage(mapVo));

		return JSONObject.parseObject(accVouch);

	}

	/**
	 * 凭证主表<BR>
	 * 按照凭证类型统计凭证记账相关数据
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/queryAccVouchAccountingCount", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccVouchAccountingCount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accVouch = accVouchAccountingService.queryAccVouchAccountingCount(getPage(mapVo));

		return JSONObject.parseObject(accVouch);

	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/accountingAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> accountingAccVouch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());


		mapVo.put("acc_year", mapVo.get("acc_year").toString());

		mapVo.put("acc_month", mapVo.get("acc_month").toString());
		
		mapVo.put("parent_node_id", mapVo.get("parent_node_id").toString());

		mapVo.put("user_id", SessionManager.getUserId());
		
		String accVouchJson = accVouchAccountingService.updateBatchAccountingAccVouch(mapVo);
		
		return JSONObject.parseObject(accVouchJson);

	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/unaccountingAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unaccountingAccVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());


		mapVo.put("parent_node_id", mapVo.get("parent_node_id").toString());
		
		mapVo.put("acc_year", mapVo.get("acc_year").toString());

		mapVo.put("acc_month", mapVo.get("acc_month").toString());
		
		String accVouchJson =accVouchAccountingService.updateBatchUnAccountingAccVouch(mapVo);
		
		return JSONObject.parseObject(accVouchJson);

	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/accounting/queryAccVouchAccountingReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVouchAccountingReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String accVouchJson =accVouchAccountingService.queryAccVouchAccountingReport(mapVo);
		
		return JSONObject.parseObject(accVouchJson);

	}
}
