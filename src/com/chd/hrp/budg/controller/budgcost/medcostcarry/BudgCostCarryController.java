package com.chd.hrp.budg.controller.budgcost.medcostcarry;

/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 

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
import com.chd.hrp.budg.service.budgcost.carry.BudgCostCarryService;
/**
 * 
 * @Description:
 * 医疗支出预算结转
 * @Table:
 * BUDG_MED_COST_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgCostCarryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgCostCarryController.class);
	
	@Resource(name = "budgCostCarryService")
	private final BudgCostCarryService budgCostCarryService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/carry/budgCostCarryMainPage", method = RequestMethod.GET)
	public String budgCostCarryMainPage(Model mode) throws Exception {

		return "hrp/budg/budgcost/carry/budgCostCarryMain";

	}
	
	/**
	 * @Description 
	 * 待结转月份 、已结转月份 页面赋值
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcost/carry/queryYearMonth", method = RequestMethod.POST)
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
		
		return budgCostCarryService.queryYearMonth(mapVo);

	}
	
	/**
	 *  结转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcost/carry/chargeBudgCostCarry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chargeBudgCostCarry(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
	
		String budgJson = null ;
		
		try {
			
			budgJson = budgCostCarryService.chargeBudgCostCarry(mapVo);
			 
		} catch (Exception e) {
			
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
	@RequestMapping(value = "/hrp/budg/budgcost/carry/reChargeBudgCostCarry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reChargeBudgCostCarry(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String budgJson = null ;
		
		try {
			
			budgJson = budgCostCarryService.reChargeBudgCostCarry(mapVo);
			
		} catch (Exception e) {
			
			budgJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgJson);
	}

}

