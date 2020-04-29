/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.util.HashMap;
import java.util.List;
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
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.acc.serviceImpl.AccYearMonthServiceImpl;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchCheckServiceImpl;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;

/**
* @Title. @Description.
* 出纳账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccBankJournalController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBankJournalController.class);
	
	@Resource(name = "accYearMonthService")
	private final AccYearMonthServiceImpl accYearMonthService = null;
	
	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;
	
	@Resource(name = "accVouchCheckService")
	private final AccVouchCheckServiceImpl accVouchCheckService = null;
   
    
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accdiary/accBankJournalMainPage", method = RequestMethod.GET)
	public String accBankJournalMainPage(Model mode) throws Exception {
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		entityMap.put("cash_flag", "0");
		
		List<AccYearMonth> accYearMonthList = accYearMonthService.queryAccTellPeriod(entityMap);
		
		if(accYearMonthList.size() > 0 ){
			
			mode.addAttribute("begin_date", accYearMonthList.get(0).getBegin_date());
			
			mode.addAttribute("end_date", accYearMonthList.get(accYearMonthList.size()-1).getEnd_date());
		}
		entityMap.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(entityMap);
		
		if(modStart != null){
			
			mode.addAttribute("modStartTime", modStart.getStart_year()+"-"+modStart.getStart_month()+"-01");
		}
		return "hrp/acc/accdiary/accBankJournalMain";

	}
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accdiary/accBankJournalAddPage", method = RequestMethod.GET)
	public String accBankJournalAddPage(Model mode) throws Exception {

		return "hrp/acc/accdiary/accBankJournalAdd";

	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accdiary/accBankJournalInstallPage", method = RequestMethod.GET)
	public String accBankJournalInstallPage(Model mode) throws Exception {

		return "hrp/acc/accdiary/accBankJournalInstall";

	}
	
	/**
	*出纳账<BR>
	*查询 银行日记账
	*2016-06-06
	*/
	@RequestMapping(value = "/hrp/acc/accdiary/queryAccBankJournal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankJournal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date"))){
				
				mapVo.put("begin_date", mapVo.get("begin_date").toString().replace("-", "/"));
				
				mapVo.put("acc_year", mapVo.get("begin_date").toString().substring(0, 4));
			}
			
			if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date"))){
				
				mapVo.put("end_date", mapVo.get("end_date").toString().replace("-", "/"));
			}
			
			String accBnakJournal = accVouchCheckService.queryAccBankJournal(getPage(mapVo));
			
			return JSONObject.parseObject(accBnakJournal);
		
	}
	
	/**
	*出纳账<BR>
	*查询 银行日记账
	*2016-06-06
	*/
	/*@RequestMapping(value = "/hrp/acc/accdiary/queryAccBankJournalPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankJournalPrint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
			
			if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date"))){
				
				mapVo.put("begin_date", mapVo.get("begin_date").toString().replace("-", "/"));
				
				mapVo.put("acc_year", mapVo.get("begin_date").toString().substring(0, 4));
			}
			
			if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date"))){
				
				mapVo.put("end_date", mapVo.get("end_date").toString().replace("-", "/"));
			}
			
			String accBnakJournal = accVouchCheckService.queryAccBankJournalPrint(mapVo);
			
			return JSONObject.parseObject(accBnakJournal);
		
	}*/
}

