/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.chd.hrp.acc.serviceImpl.tell.AccUnitBankTellServiceImpl;

/**
* @Title. @Description.
* 出纳账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccUnitBankTellController extends BaseController{
	private static Logger logger = Logger.getLogger(AccUnitBankTellController.class);
	
	@Resource(name = "accUnitBankTellService")
	private AccUnitBankTellServiceImpl accUnitBankTellService = null;
    
	@Resource(name = "accYearMonthService")
	private final AccYearMonthServiceImpl accYearMonthService = null;
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accbanktell/accUnitBankTellMainPage", method = RequestMethod.GET)
	public String accUnitBankTellMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("acc_year", sdf.format(new Date()));
		
		mapVo.put("cash_flag", "0");
		
		List<AccYearMonth> accYearMonthList = accYearMonthService.queryAccTellPeriod(mapVo);
		
		if(accYearMonthList.size() > 0){
			
			mode.addAttribute("begin_date", accYearMonthList.get(0).getAcc_year()+"."+accYearMonthList.get(0).getAcc_month());
			
			mode.addAttribute("end_date",accYearMonthList.get(accYearMonthList.size()-1).
					getAcc_year()+"."+accYearMonthList.get(accYearMonthList.size()-1).getAcc_month());
		}

		return "hrp/acc/accbanktell/accUnitBankTellMain";

	}
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbanktell/accUnitBankTellAddPage", method = RequestMethod.GET)
	public String accUnitBankTellAddPage(Model mode) throws Exception {

		return "hrp/acc/accbanktell/accUnitBankTellAdd";

	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbanktell/accUnitBankTellInstallPage", method = RequestMethod.GET)
	public String accUnitBankTellInstallPage(Model mode) throws Exception {

		return "hrp/acc/accbanktell/accUnitBankTellInstall";

	}
	
	/**
	*出纳账<BR>
	*查询单位银行账
	*/
	@RequestMapping(value = "/hrp/acc/accbanktell/queryAccUnitBankTell", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccUnitBankTell(Model mode,@RequestParam Map<String,Object> mapVo) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("acc_year", mapVo.get("begin_date").toString().substring(0, 4));
		
		mapVo.put("acc_month", mapVo.get("begin_date").toString().substring(5));
		
		String accUnitBankTellJson =  accUnitBankTellService.queryAccUnitBankTell(getPage(mapVo));

		return JSONObject.parseObject(accUnitBankTellJson);
	}
	
	/**
	*出纳账<BR>
	*查询单位银行账
	*/
	/*@RequestMapping(value = "/hrp/acc/accbanktell/queryAccUnitBankTellPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccUnitBankTellPrint(Model mode,@RequestParam Map<String,Object> mapVo) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("acc_year", mapVo.get("begin_date").toString().substring(0, 4));
		
		mapVo.put("acc_month", mapVo.get("begin_date").toString().substring(5));
		
		String accUnitBankTellJson =  accUnitBankTellService.queryAccUnitBankTellPrint(mapVo);

		return JSONObject.parseObject(accUnitBankTellJson);
	}*/
	
}

