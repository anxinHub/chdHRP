package com.chd.hrp.budg.controller.budgsysset;

import java.util.Calendar;
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
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.budg.entity.BudgSysSet;
import com.chd.hrp.budg.service.budgsysset.BudgSysSetService;


/**
 *  @Description:
 * 添加预算系统设置
 * @Table:
 * WORK_BUDG_ORGANIZE_PLAN
 * @author lenovo
 *
 */
@Controller
public class BudgSysSetController extends BaseController {
	private static Logger logger = Logger.getLogger(BudgSysSetController.class);
	
	@Resource(name = "budgSysSetService")
	private final BudgSysSetService budgSysSetService = null; 
	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/budgSysSetMainPage", method = RequestMethod.GET)
	public String workBudgOrganizePlanMainPage(Model mode) throws Exception {
		
		//获取当前年度
		int Year=Calendar.getInstance().get(Calendar.YEAR);
		 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		
		map.put("hos_id", SessionManager.getHosId());
	
		map.put("copy_code", SessionManager.getCopyCode());
		
		map.put("budg_year",String.valueOf(Year));
		
		//根据主键查询出对象后  由mode携带传回页面
		BudgSysSet budgSysSet = budgSysSetService.queryBudgSysSetByCode(map);
		
		if(budgSysSet != null){
		
			mode.addAttribute("group_id", budgSysSet.getGroup_id());
			
			mode.addAttribute("hos_id", budgSysSet.getHos_id());
			
			mode.addAttribute("budg_year", budgSysSet.getBudg_year());
			
			mode.addAttribute("copy_code", budgSysSet.getCopy_code());
			
			mode.addAttribute("work_budg_mode", budgSysSet.getWork_budg_mode());
			
			mode.addAttribute("income_budg_mode", budgSysSet.getIncome_budg_mode());
			
			mode.addAttribute("workload_index_out", budgSysSet.getWorkload_index_out());
			
			mode.addAttribute("workload_index_in", budgSysSet.getWorkload_index_in());
			
			mode.addAttribute("workload_index_check", budgSysSet.getWorkload_index_check());
			
			mode.addAttribute("risk_fund_subj", budgSysSet.getRisk_fund_subj());
			
			mode.addAttribute("risk_fund_rate", budgSysSet.getRisk_fund_rate());
			
		}else{
			mode.addAttribute("budg_year", String.valueOf(Year));
		}
		
		return "hrp/budg/budgsysset/budgsysset/BudgSysSetMain";

	}
	/**
	 * 
	 * 添加预算系统设置
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/addBudgSysSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgSysSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgIndexDictJson = budgSysSetService.addBudgSysSet(mapVo);

		return JSONObject.parseObject(budgIndexDictJson);
		
	}
	
	/**
	 * 
	 * 查询预算系统设置
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/queryBudgSysSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgSysSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgIndexDictJson = budgSysSetService.queryBudgSysSet(mapVo);
		
		return JSONObject.parseObject(budgIndexDictJson);
		
	}

}
