/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.business.compilationplan.uptodown.budg;
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
import com.chd.hrp.budg.service.business.compilationplan.budg.BudgWorkHosYearService;
/**
 * 
 * @Description:
 * 医院年度业务预算
 * @Table:
 * BUDG_WORK_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgWorkHosYearController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgWorkHosYearController.class);
	
	//引入Service服务
	@Resource(name = "budgWorkHosYearService")
	private final BudgWorkHosYearService budgWorkHosYearService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/budg/business/compilationplan/uptodown/hosyearbudg/workhosyear/workHosYearMainPage", method = RequestMethod.GET)
	public String budgWorkHosYearMainPage(Model mode) throws Exception {

		return "hrp/budg/business/compilationplan/uptodown/hosyearbudg/workhosyear/budgWorkHosYearMain";

	}
	
	/**
	 * @Description 
	 * 查询数据 医院年度业务预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/workhosyear/queryBudgWorkHosYearUp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkHosYearUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String budgWorkHosYear = budgWorkHosYearService.queryBudgWorkHosYear(getPage(mapVo));

		return JSONObject.parseObject(budgWorkHosYear);
		
	}
	
	/**
	 * @Description 
	 * 科室意见汇总
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/business/compilationplan/uptodown/hosyearbudg/workhosyear/sumDeptSuggest", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sumDeptSuggest(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
    	mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String budgWorkHosYear = budgWorkHosYearService.sumDeptSuggest(mapVo);

		return JSONObject.parseObject(budgWorkHosYear);
		
	}
	
    
}

