/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgincome.reportforms.query;

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
import com.chd.hrp.budg.service.budgincome.reportforms.query.BudgMedInQueryService;
import com.chd.hrp.budg.service.budgincome.toptodown.deptmonthinbudg.MedInDMBudgService;
import com.chd.hrp.budg.service.budgincome.toptodown.hosmonthinbudg.MedInHMBudgService;
/**
 * 
 * @Description:
 * 科室月份医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class MedInQueryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedInQueryController.class);
	
	//引入Service服务
	@Resource(name = "budgMedInQueryService")
	private final BudgMedInQueryService budgmMedInQueryService = null;
	
	/**
	 * @Description 
	 * 医院医疗收入预算查询 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/budg/budgincome/reportforms/query/budgMedInHosMainPage", method = RequestMethod.GET)
	public String budgMedIncomeDeptMonthMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/reportforms/query/medInHosBudgMain";

	}
	
	/**
	 * @Description 
	 * 医院医疗收入预算查询 页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/budg/budgincome/reportforms/query/budgMedInDeptMainPage", method = RequestMethod.GET)
	public String budgMedInDeptYearMainPage(Model mode) throws Exception {

		return "hrp/budg/budgincome/reportforms/query/medInDeptBudgMain";

	}
	
	/**
	 * @Description 
	 * 查询数据 医院医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/query/queryMedInHosBudg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkHosMonthUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String budgMedIncomeHos = budgmMedInQueryService.queryMedInHosBudg(getPage(mapVo));

		return JSONObject.parseObject(budgMedIncomeHos);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 科室医疗收入预算
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgincome/reportforms/query/queryMedInDeptBudg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgWorkDeptMonthUp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}

		String budgMedInDept = budgmMedInQueryService.queryMedInDeptBudg(getPage(mapVo));

		return JSONObject.parseObject(budgMedInDept);
	}
	
	
}

