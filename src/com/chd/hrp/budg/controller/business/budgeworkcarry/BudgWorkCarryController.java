/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.budgeworkcarry;
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
import com.chd.hrp.budg.service.business.budgeworkcarry.BudgWorkCarryService;
/**
 * 
 * @Description:
 * 业务预算结转
 * @Table:
 * BUDG_WORK_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWorkCarryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkCarryController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkCarryService")
	private final BudgWorkCarryService budgWorkCarryService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkcarry/budgWorkCarryMainPage", method = RequestMethod.GET)
	public String budgWorkCarryMainPage(Model mode) throws Exception {
		
		return "hrp/budg/business/budgeworkcarry/budgWorkCarryMain";

	}
	
	/**
	 * @Description 
	 * 待结转月份 、已结转月份 赋值
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/budgeworkcarry/queryYearMonth", method = RequestMethod.POST)
	@ResponseBody
	public String queryYearMonth(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String budgWorkJson = budgWorkCarryService.queryYearMonth(mapVo);
		
		return budgWorkJson;

	}
	
	/**
	 *  结转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgeworkcarry/chargeBudgWorkCarry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chargeBudgWorkCarry(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String budgJson = null ;
		
		try {
			
			 budgJson = budgWorkCarryService.chargeBudgWorkCarry(mapVo);
			 
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			budgJson = e.getMessage();
		}
				
		return JSONObject.parseObject(budgJson);
	}
	
	/**
	 * 反结
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/budgeworkcarry/reChargeBudgWorkCarry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reChargeBudgWorkCarry(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String budgJson = null ;
		
		try {
			
			budgJson = budgWorkCarryService.reChargeBudgWorkCarry(mapVo);
			
		} catch (Exception e) {
			
			budgJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgJson);
	}
	


}

