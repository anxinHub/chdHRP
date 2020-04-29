/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.serviceImpl.AccBalanceAdjustmentServiceImpl;

/**
* @Title. @Description.
* 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccBalanceAdjustmentController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBalanceAdjustmentController.class);
   
	@Resource(name = "accBalanceAdjustmentService")
	private final AccBalanceAdjustmentServiceImpl accBalanceAdjustmentService = null;
	
    
	/**
	*<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accbanktell/accBalanceAdjustmentMainPage", method = RequestMethod.GET)
	public String accBalanceAdjustmentMainPage(Model mode) throws Exception {
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		//String yearMonth=SessionManager.getSysYearMonth("acc_flag");
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		
		return "hrp/acc/accbanktell/accBalanceAdjustmentMain";

	}
	/**
	*<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbanktell/accBalanceAdjustmentAddPage", method = RequestMethod.GET)
	public String accBalanceAdjustmentAddPage(Model mode) throws Exception {

		return "hrp/acc/accbanktell/accBalanceAdjustmentAdd";

	}
	
	/**
	*<BR>
	*初始余额页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbanktell/accBalanceAdjustmentInstallPage", method = RequestMethod.GET)
	public String accBalanceAdjustmentInstallPage(Model mode) throws Exception {

		return "hrp/acc/accbanktell/accBalanceAdjustmentInstall";

	}
	
	
	/**
	*<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/balanceadjustment/accBalanceAdjustmentByDayMainPage", method = RequestMethod.GET)
	public String accBalanceAdjustmentByDayMainPage(Model mode) throws Exception {
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		entityMap.put("cash_flag", "0");
		
		String tell_day =accBalanceAdjustmentService.queryAccBalanceAdjustment(entityMap);
		
		mode.addAttribute("acc_time",tell_day);
		
		entityMap.remove("cash_flag");
		
		entityMap.put("cash_flag", "1");
		
		String account_day = accBalanceAdjustmentService.queryAccBalanceAdjustment(entityMap);
		
		mode.addAttribute("acc_year",account_day);
		
		return "hrp/acc/balanceadjustment/accBalanceAdjustmentByDayMain";

	}
	
	
	/**
	*<BR>
	*日结：出纳日结
	*/
	@RequestMapping(value = "/hrp/acc/balanceadjustment/dailyAccBalanceAdjustment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dailyAccBalanceAdjustment(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String daily =accBalanceAdjustmentService.queryDailyAccBalanceAdjustment(mapVo);
		
		return JSONObject.parseObject(daily);

	}
	
	/**
	*<BR>
	*日结：出纳反结
	*/
	@RequestMapping(value = "/hrp/acc/balanceadjustment/undailyAccBalanceAdjustment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> undailyAccBalanceAdjustment(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String daily =accBalanceAdjustmentService.queryUndailyAccBalanceAdjustment(mapVo);
		
		return JSONObject.parseObject(daily);

	}
	
	
	/**
	 * 月结：出纳月结
	 **/
	//维护页面跳转
	@RequestMapping(value = "/hrp/acc/balanceadjustment/accBalanceAdjustmentByMonthMainPage", method = RequestMethod.GET)
	public String accBalanceAdjustmentByMonthMainPage(Model mode) throws Exception {

		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		entityMap.put("cash_flag", "0");
		//当前出纳月
		String now_time =accBalanceAdjustmentService.queryAccBalanceAdjustmentByMonth(entityMap);
		
		Map<String, Object> preMap = new HashMap<String, Object>();
		
		preMap.putAll(entityMap);
		
		preMap.put("cash_flag", "1");
		//上次结账月
		String pre_time =accBalanceAdjustmentService.queryAccBalanceAdjustmentByMonth(preMap);
		
		mode.addAttribute("now_time",now_time);
		
		mode.addAttribute("pre_time",pre_time);
		
		
		return "hrp/acc/balanceadjustment/accBalanceAdjustmentByMonthMain";

	}
	
	
	/**
	*<BR>
	*月结：出纳月结
	*/
	@RequestMapping(value = "/hrp/acc/balanceadjustment/dailyAccBalanceAdjustmentByMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dailyAccBalanceAdjustmentByMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String daily =accBalanceAdjustmentService.queryDailyAccBalanceAdjustmentByMonth(mapVo);
		
		return JSONObject.parseObject(daily);

	}
	
	/**
	*<BR>
	*月结：出纳反结
	*/
	@RequestMapping(value = "/hrp/acc/balanceadjustment/undailyAccBalanceAdjustmentByMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> undailyAccBalanceAdjustmentByMonth(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		String daily =accBalanceAdjustmentService.queryUndailyAccBalanceAdjustmentByMonth(mapVo);
		
		return JSONObject.parseObject(daily);

	}
	

}

