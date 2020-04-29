/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
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
import com.chd.hrp.acc.serviceImpl.tell.AccInventoryReportServiceImpl;
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
public class AccInventoryReportController extends BaseController{
	private static Logger logger = Logger.getLogger(AccInventoryReportController.class);
   
    @Resource(name="modStartService")
    private final ModStartServiceImpl modStartService = null;
    
    @Resource(name="accInventoryReportService")
    private final AccInventoryReportServiceImpl accInventoryReportService = null;
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	//库存日报 主页跳转
	@RequestMapping(value = "/hrp/acc/report/accInventoryReportByDayMainPage", method = RequestMethod.GET)
	public String accInventoryReportByDayMainPage(Model mode) throws Exception {

		return "hrp/acc/report/accInventoryReportByDayMain";

	}
	
	/**
	*库存日报<BR>
	*	查询
	*/
	@RequestMapping(value = "/hrp/acc/report/queryAccInventoryReportByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAccInventoryReportByDay(@RequestParam Map<String,Object> mapVo) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(mapVo);
		
		mapVo.put("modStartYearMonth", modStart.getStart_year()+modStart.getStart_month());
		
		mapVo.put("acc_year", mapVo.get("end_time").toString().substring(0, 4));
		
		String accInventoryReportJson = accInventoryReportService.queryAccInventoryReportByDay(getPage(mapVo));
		
		return JSONObject.parseObject(accInventoryReportJson);
	}
	
	/**
	*库存日报<BR>
	*	查询
	*/
	/*@RequestMapping(value = "/hrp/acc/report/queryAccInventoryReportByDayPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAccInventoryReportByDayPrint(@RequestParam Map<String,Object> mapVo) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(mapVo);
		
		mapVo.put("modStartYearMonth", modStart.getStart_year()+modStart.getStart_month());
		
		mapVo.put("acc_year", mapVo.get("end_time").toString().substring(0, 4));
		
		String accInventoryReportJson = accInventoryReportService.queryAccInventoryReportByDayPrint(mapVo);
		
		return JSONObject.parseObject(accInventoryReportJson);
	}*/
	
	
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	// 库存月报 主页跳转
	@RequestMapping(value = "/hrp/acc/report/accInventoryReportByMonthMainPage", method = RequestMethod.GET)
	public String accInventoryReportByMonthMainPage(Model mode) throws Exception {
		
		return "hrp/acc/report/accInventoryReportByMonthMain";
	}
	
	
	/**
	*库存月报<BR>
	*	查询
	*/
	@RequestMapping(value = "/hrp/acc/report/queryAccInventoryReportByMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAccInventoryReportByMonth(@RequestParam Map<String,Object> mapVo) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(mapVo);
		
		mapVo.put("modStartYearMonth", modStart.getStart_year()+modStart.getStart_month());
		
		mapVo.put("acc_year", mapVo.get("end_yearMonth").toString().substring(0, 4));
		
		String accInventoryReportJson = accInventoryReportService.queryAccInventoryReportByMonth(getPage(mapVo));
		
		return JSONObject.parseObject(accInventoryReportJson);
	}
	
	/**
	*库存月报<BR>
	*	打印
	*/
	/*@RequestMapping(value = "/hrp/acc/report/queryAccInventoryReportByMonthPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAccInventoryReportByMonthPrint(@RequestParam Map<String,Object> mapVo) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("mod_code", "0101");
		
		ModStart modStart = modStartService.queryModStartByCode(mapVo);
		
		mapVo.put("modStartYearMonth", modStart.getStart_year()+modStart.getStart_month());
		
		mapVo.put("acc_year", mapVo.get("end_yearMonth").toString().substring(0, 4));
		
		String accInventoryReportJson = accInventoryReportService.queryAccInventoryReportByMonthPrint(mapVo);
		
		return JSONObject.parseObject(accInventoryReportJson);
	}*/
}

