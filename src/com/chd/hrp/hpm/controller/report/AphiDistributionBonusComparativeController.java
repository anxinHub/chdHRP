package com.chd.hrp.hpm.controller.report;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.service.report.AphiDistributionBonusComparativeService;

@Controller
public class AphiDistributionBonusComparativeController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDistributionBonusComparativeController.class);
	@Resource(name = "aphiDistributionBonusComparativeService")
	private AphiDistributionBonusComparativeService aphiDistributionBonusComparativeService = null;

	@RequestMapping(value = "/hrp/hpm/hpmreport/hpmDistributionBonusComparativeMainPage", method = RequestMethod.GET)
	public String hpmDistributionBonusComparativeMainPage(Model model) throws Exception {
		return "hrp/hpm/hpmreport/hpmDistributionBonusComparativeMain";
	}

	@RequestMapping(value = "/hrp/hpm/hpmreport/queryHpmDistributionBonusComparative", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDistributionBonusComparative(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

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
		
		String distributionBonusComparative = aphiDistributionBonusComparativeService.queryDistributionBonusComparative(getPage(mapVo));

		return JSONObject.parseObject(distributionBonusComparative);
	}
	
	// 奖金查询的数据 查询表头根据指标
	@RequestMapping(value = "/hrp/hpm/hpmreport/exportDistributionBonusComparativeExcel")
	@ResponseBody
	public String exportDistributionBonusComparativeExcel(@RequestParam(value = "paras", required = true) String paras, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String[] paraArray = paras.split("@");
		
		mapVo.put("acct_year_start", paraArray[0].substring(0, 4));

		mapVo.put("acct_month_start", paraArray[0].substring(4, 6));

		mapVo.put("acct_year_end", paraArray[1].substring(0, 4));

		mapVo.put("acct_month_end", paraArray[1].substring(4, 6));

		String fileName = "绩效工资分配对比表";

		// 组织表头；

		LinkedHashMap<String, String> headMap = new LinkedHashMap<String, String>();

		headMap.put("acct_year", "核算年月");

		if(!"null".equals(paraArray[2])){
			
			if("01".equals(paraArray[2]))
			{
				headMap.put("dept_kind_code", "科室分类代码");

				headMap.put("dept_kind_name", "科室分类名称");
			}else{
				
				headMap.put("dept_kind_code", "科室性质代码");

				headMap.put("dept_kind_name", "科室性质名称");
				
			}
		}else{
			
			headMap.put("dept_kind_code", "科室分类代码");

			headMap.put("dept_kind_name", "科室分类名称");
			
		}
		
		

		headMap.put("bonus_money", "奖金额");

		headMap.put("pro", "占比");

		headMap.put("emp_num", "人数");
		
		headMap.put("av", "人均奖");

		// 组织内容

		String distributionBonusComparative = aphiDistributionBonusComparativeService.queryDistributionBonusComparative(getPage(mapVo));

		//UploadUtil.exportExcel(request, response, JSONObject.parseObject(distributionBonusComparative), headMap, fileName);

		return null;

	}

}
