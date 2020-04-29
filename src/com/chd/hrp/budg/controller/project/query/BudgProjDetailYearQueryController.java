/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.project.query;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.budg.service.project.query.BudgProjDetailYearQueryService;
/**
 * 
 * @Description:
 * 年度项目预算
 * @Table:
 * BUDG_PROJ_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgProjDetailYearQueryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgProjDetailYearQueryController.class);
	
	//引入Service服务
	@Resource(name = "budgProjDetailYearQueryService")
	private final BudgProjDetailYearQueryService budgProjDetailYearQueryService = null;
   
	/**
	 * @Description 
	 * 项目预算明细查询(一) 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/budgProjDetailYearQueryMainPage", method = RequestMethod.GET)
	public String budgProjDetailYearQueryMainPage(Model mode) throws Exception {

		return "hrp/budg/project/query/budgProjDetailYearQueryMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 项目预算明细查询(一) 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/queryBudgProjDetailYearQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjDetailYearQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
    	mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
    
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgProjDetailYearQueryService.queryBudgProjDetailYearQuery(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
    
	/**
	 * @Description 
	 * 项目预算明细查询(二) 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/budgProjDetailYearQueryTMainPage", method = RequestMethod.GET)
	public String budgProjDetailYearQueryTMainPage(Model mode) throws Exception {

		return "hrp/budg/project/query/budgProjDetailYearQueryTMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 项目预算明细查询(二) 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/queryBudgProjDetailYearQueryT", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjDetailYearQueryT(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
    	mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
    
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgProjDetailYearQueryService.queryBudgProjDetailYearQueryT(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
	/**
	 * @Description 
	 * 项目预算明细查询(一)(集团) 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/budgProjDetailYearGroupQueryMainPage", method = RequestMethod.GET)
	public String budgProjDetailYearGroupQueryMainPage(Model mode) throws Exception {

		return "hrp/budg/project/query/budgProjDetailYearGroupQueryMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 项目预算明细查询(一)(集团)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/queryBudgProjDetailYearGroupQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjDetailYearGroupQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
    	mapVo.put("group_id", SessionManager.getGroupId());
	
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgProjDetailYearQueryService.queryBudgProjDetailYearGroupQuery(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
    
	/**
	 * @Description 
	 * 项目预算明细查询(二)(集团) 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/budgProjDetailYearGroupQueryTMainPage", method = RequestMethod.GET)
	public String budgProjDetailYearGroupQueryTMainPage(Model mode) throws Exception {

		return "hrp/budg/project/query/budgProjDetailYearGroupQueryTMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 项目预算明细查询(二) 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/queryBudgProjDetailYearGroupQueryT", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjDetailYearGroupQueryT(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
    	mapVo.put("group_id", SessionManager.getGroupId());
	
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgProjDetailYearQueryService.queryBudgProjDetailYearGroupQueryT(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
}

