/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.proj;
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
import com.chd.hrp.budg.service.budgincome.proj.BudgResearchProjService;
/**
 * 
 * @Description:
 * 科研项目收入预算 / 科研项目收入预算执行
 * @Table:
 * BUDG_PROJ_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgResearchProjController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgResearchProjController.class);
	
	//引入Service服务
	@Resource(name = "budgResearchProjService")
	private final BudgResearchProjService budgResearchProjService = null;
   
	/**
	 * @Description 
	 * 科研项目收入预算 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/proj/budgResearchProjIncomeMainPage", method = RequestMethod.GET)
	public String budgResearchProjIncomeExeMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/proj/budgResearchProjIncomeMain";

	}

	
	/**
	 * @Description 
	 *  科研项目收入预算 查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/proj/queryBudgResearchProjIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgResearchProjIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
    	mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
    
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgResearchProjService.queryBudgResearchProjIncome(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
	/**
	 * @Description 
	 * 科研项目收入预算执行 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/proj/budgResearchProjIncomeExeMainPage", method = RequestMethod.GET)
	public String budgResearchProjIncomeMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/proj/budgResearchProjIncomeExeMain";

	}

	
	/**
	 * @Description 
	 *  科研项目收入预算执行 查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/proj/queryBudgResearchProjIncomeExe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgResearchProjIncomeExe(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
    	mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
    
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgResearchProjService.queryBudgResearchProjIncomeExe(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
}

