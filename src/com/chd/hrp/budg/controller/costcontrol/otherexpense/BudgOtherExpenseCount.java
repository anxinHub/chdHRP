package com.chd.hrp.budg.controller.costcontrol.otherexpense;

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
import com.chd.hrp.budg.service.business.budgotherexpense.BudgExpenseService;

/**
 * 资金支出控制-其他费用预算执行统计
 * BUDG_EXPENSE
 * @author zhaon
 *
 */
@Controller
public class BudgOtherExpenseCount extends BaseController {
	private static Logger logger = Logger.getLogger(BudgOtherExpenseCount.class);
	
	//引入Service服务
	@Resource(name = "budgExpenseService")
	private final BudgExpenseService budgExpenseService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/costcontrol/otherexpense/budgOtherExpenseCountMainPage", method = RequestMethod.GET)
	public String budgOtherExpenseCountMainPage(Model mode) throws Exception {
		return "hrp/budg/costcontrol/otherexpense/budgOtherExpenseCountMain";
	}
	
	/**
	 * @Description 
	 * 查询数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/costcontrol/otherexpense/queryBudgOtherExpenseCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgOtherExpenseCount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String comType = budgExpenseService.queryBudgOtherExpenseCount(getPage(mapVo));
		return JSONObject.parseObject(comType);
		
	}
	
	/**
	 * 计算功能
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/costcontrol/otherexpense/countBudgOtherExpense", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> countBudgOtherExpense(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String comType = budgExpenseService.countBudgOtherExpense(mapVo);
		return JSONObject.parseObject(comType);
	}
}
