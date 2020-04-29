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
import com.chd.hrp.budg.service.project.query.BudgProjYearQueryService;
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
public class BudgProjYearQueryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgProjYearQueryController.class);
	
	//引入Service服务
	@Resource(name = "budgProjYearQueryService")
	private final BudgProjYearQueryService budgProjYearQueryService = null;
   
	/**
	 * @Description 
	 * 项目预算查询  报表(一) 主页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/budgProjYearQueryMainPage", method = RequestMethod.GET)
	public String budgProjYearQueryMainPage(Model mode) throws Exception {

		return "hrp/budg/project/query/budgProjYearQueryMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 项目预算查询  报表(一)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/queryBudgProjYearQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjYearQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgProjYearQueryService.queryBudgProjYearQuery(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
    
	
	/**
	 * @Description 
	 * 项目预算查询  报表(二) 主页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/budgProjYearQueryTMainPage", method = RequestMethod.GET)
	public String budgProjYearQueryTMainPage(Model mode) throws Exception {

		return "hrp/budg/project/query/budgProjYearQueryTMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 项目预算查询  报表(二)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/queryBudgProjYearQueryT", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjYearQueryT(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgProjYearQueryService.queryBudgProjYearQueryT(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
	
	/**
	 * @Description 
	 * 项目预算查询  (一)(集团) 主页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/budgProjYearGroupQueryMainPage", method = RequestMethod.GET)
	public String budgProjYearGroupQueryMainPage(Model mode) throws Exception {

		return "hrp/budg/project/query/budgProjYearGroupQueryMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 项目预算查询  (一)(集团)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/queryBudgProjYearGroupQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjYearGroupQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
	    mapVo.put("group_id", SessionManager.getGroupId());
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgProjYearQueryService.queryBudgProjYearGroupQuery(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
	
    
	
	/**
	 * @Description 
	 * 项目预算查询  报表(二)(集团) 主页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/budgProjYearGroupQueryTMainPage", method = RequestMethod.GET)
	public String budgProjYearGroupQueryTMainPage(Model mode) throws Exception {

		return "hrp/budg/project/query/budgProjYearGroupQueryTMain";

	}

	
	/**
	 * @Description 
	 * 查询数据 项目预算查询  报表(二)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/query/queryBudgProjYearGroupQueryT", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgProjYearGroupQueryT(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
	    mapVo.put("group_id", SessionManager.getGroupId());
		
		
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String budgProjYear = budgProjYearQueryService.queryBudgProjYearGroupQueryT(getPage(mapVo));

		return JSONObject.parseObject(budgProjYear);
		
	}
}

