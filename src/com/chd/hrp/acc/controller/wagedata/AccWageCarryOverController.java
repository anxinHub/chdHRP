/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wagedata;
import java.util.ArrayList;
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
import com.chd.hrp.acc.entity.AccWageVouch;
import com.chd.hrp.acc.service.wagedata.AccWageCarryOverService;

/**
* @Title. @Description.
* 结转工资
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccWageCarryOverController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageCarryOverController.class);
	
	@Resource(name = "accWageCarryOverService")
	private final AccWageCarryOverService accWageCarryOverService = null;
	
    
	/**
	*结转工资<BR>
	*维护页面跳转accWageCarryOverAddPage
	*/
	
	@RequestMapping(value = "hrp/acc/accwagecarryover/accWageCarryOverMainPage", method = RequestMethod.GET)
	public String accWageCarryOverMainPage(Model mode) throws Exception {

		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		entityMap.put("wage_flag", "0");
		
		String tell_day =accWageCarryOverService.queryAccYearMonthNow(entityMap);
		
		mode.addAttribute("acc_time",tell_day);
		
		entityMap.remove("wage_flag");
		
		entityMap.put("wage_flag", "1");
		
		String account_day = accWageCarryOverService.queryAccYearMonthLast(entityMap);
		
		mode.addAttribute("acc_year",account_day);
		
		return "hrp/acc/accwagecarryover/accWageCarryOverMain";

	}
	
	/**
	*结转工资<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "hrp/acc/accwagecarryover/updateAccWageCarryOver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageCarryOver(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
				
	        mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
	        
	        mapVo.put("acc_month", mapVo.get("year_month").toString().substring(5, 7));
	        
	        mapVo.put("wage_user", SessionManager.getUserCode());
		        
		String accWageCarryOverJson = accWageCarryOverService.updateAccYearMonth(mapVo);
		
		return JSONObject.parseObject(accWageCarryOverJson);
	}
	
	@RequestMapping(value = "hrp/acc/accwagecarryover/upAccWageCarryOver", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upAccWageCarryOver(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			}
				
	        mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
	        
	        mapVo.put("acc_month", mapVo.get("year_month").toString().substring(5, 7));
	        
	        mapVo.put("wage_user", SessionManager.getUserCode());
		        
		String accWageCarryOverJson = accWageCarryOverService.updateAccYearMonth(mapVo);
		
		return JSONObject.parseObject(accWageCarryOverJson);
	}
	
	
}

