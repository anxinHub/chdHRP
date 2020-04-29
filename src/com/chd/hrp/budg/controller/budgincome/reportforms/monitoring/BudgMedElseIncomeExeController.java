/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.reportforms.monitoring;
import java.util.*;

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
import com.chd.hrp.budg.service.budgincome.reportforms.monitoring.BudgMedElseIncomeExeService;
/**
 * 
 * @Description:
 * 医院医疗其他收入预算执行监控
 * @Table:
 * BUDG_ELSE_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgMedElseIncomeExeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgMedElseIncomeExeController.class);
	
	//引入Service服务
	@Resource(name = "budgMedElseIncomeExeService")
	private final BudgMedElseIncomeExeService budgMedElseIncomeExeService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/budgMedElseIncomeExeMainPage", method = RequestMethod.GET)
	public String budgMedInHosYearMonMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/reportforms/monitoring/budgMedElseIncomeExeMain";

	}

	/**
	 * @Description 
	 * 查询数据 医疗收入执行数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/monitoring/queryBudgMedElseIncomExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgMedElseIncomExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgMedInHosYearMon = budgMedElseIncomeExeService.query(getPage(mapVo));

		return JSONObject.parseObject(budgMedInHosYearMon);
		
	}
	
}

