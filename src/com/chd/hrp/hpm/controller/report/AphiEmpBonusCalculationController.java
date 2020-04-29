package com.chd.hrp.hpm.controller.report;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.service.report.AphiEmpBonusCalculationService;

/**
 * @Title.
 * @Description.
 * 职工绩效工资核算计算表
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiEmpBonusCalculationController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiEmpBonusCalculationController.class);
	@Resource(name = "aphiEmpBonusCalculationService")
	private final AphiEmpBonusCalculationService aphiEmpBonusCalculationService = null;

	@RequestMapping(value = "/hrp/hpm/report/hpmEmpBonusCalculationMainPage", method = RequestMethod.GET)
	public String hpmEmpBonusCalculationMainPage(Model model) throws Exception {
		return "hrp/hpm/report/hpmEmpBonusCalculationMain";
	}
	
	//职工绩效工资核算计算表 查询
	@RequestMapping(value = "/hrp/hpm/report/queryHpmEmpBonusCalculation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpBonusCalculation(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("acct_year_start", mapVo.get("year_month_start").toString().substring(0, 4));

		mapVo.put("acct_month_start", mapVo.get("year_month_start").toString().substring(4, 6));

		mapVo.put("acct_year_end", mapVo.get("year_month_end").toString().substring(0, 4));

		mapVo.put("acct_month_end", mapVo.get("year_month_end").toString().substring(4, 6));
		
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		String empBoundsCalculation = aphiEmpBonusCalculationService.queryEmpBonusCalculationByReport(getPage(mapVo));

		return JSONObject.parseObject(empBoundsCalculation);
	}
	
	//职工绩效工资核算计算表 查询动态表头
	@RequestMapping(value = "/hrp/hpm/report/queryHpmEmpBonusCalculationGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmEmpBonusCalculationGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if(mapVo.get("copy_code") == null){			
	          mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String resultJson = aphiEmpBonusCalculationService.queryEmpBonusCalculationGrid(mapVo);

		return resultJson;
	}

}
