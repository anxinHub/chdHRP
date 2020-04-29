/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.project.carry;
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
import com.chd.hrp.budg.service.project.carry.BudgProjCarryService;
/**
 * 
 * @Description:
 * 项目预算年末结转
 * @Table:
 * BUDG_PROJ_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgProjCarryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgProjCarryController.class);
	
	//引入Service服务
	@Resource(name = "budgProjCarryService")
	private final BudgProjCarryService budgProjCarryService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/carry/budgProjCarryMainPage", method = RequestMethod.GET)
	public String budgProjCarryMainPage(Model mode) throws Exception {

		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		entityMap.put("group_id", SessionManager.getGroupId());
		
		entityMap.put("hos_id", SessionManager.getHosId());
		
		entityMap.put("copy_code", SessionManager.getCopyCode());
		String tell_day =budgProjCarryService.queryCarryYear(entityMap);
		if(tell_day==null){
			mode.addAttribute("year", Calendar.getInstance().get(Calendar.YEAR));
		}else{
		mode.addAttribute("year", Integer.parseInt(tell_day)+1);
		mode.addAttribute("yet_year", tell_day);
		}
		return "hrp/budg/project/carry/budgProjCarryMain";

	}
	/**
	 * @Description 
	 * 结转
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/carry/carryBudgProjCarry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> carryBudgProjCarry(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carryBudgProjCarry =budgProjCarryService.carryBudgProjCarry(mapVo);
		
		
		return JSONObject.parseObject(carryBudgProjCarry);
		
	}
	/**
	 * @Description 
	 * 结转
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/project/carry/reCarryBudgProjCarry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reCarryBudgProjCarry(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carryBudgProjCarry =budgProjCarryService.reCarryBudgProjCarry(mapVo);
		
		
		return JSONObject.parseObject(carryBudgProjCarry);
		
	}
    
}

