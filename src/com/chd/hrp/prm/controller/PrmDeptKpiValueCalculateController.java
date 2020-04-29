/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.prm.service.PrmDeptKpiValueCalculateService;

/**
 * 
 * @Description: 0308 科室KPI指标数据准备表
 * @Table: PRM_DEPT_KPI_VALUE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptKpiValueCalculateController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptKpiValueCalculateController.class);

	// 引入Service服务
	@Resource(name = "prmDeptKpiValueCalculateService")
	private final PrmDeptKpiValueCalculateService prmDeptKpiValueCalculateService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/prmDeptKpiValueCalculateMainPage", method = RequestMethod.GET)
	public String prmDeptKpiValueMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkpivalue/prmDeptKpiValueCalculateMain";

	}

	/**
	 * @Description 查询数据 0308 科室KPI指标数据准备表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/queryPrmDeptKpiValueSchemeCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiValueSchemeCalculate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		mapVo.put("sql", "phs.method_code in ('02','03')");
		
		String prmDeptKpiValue = prmDeptKpiValueCalculateService.queryPrmDeptKpiValueSchemeCalculate(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiValue);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/createPrmDeptKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmDeptKpiValueCalculate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		String prmDeptKpiValue = prmDeptKpiValueCalculateService.createDeptKpiValueCalculate(mapVo);

		return JSONObject.parseObject(prmDeptKpiValue);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/auditPrmDeptKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmHosTargetData(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		String prmHosTargetDataJsonCalculate = "";

		for (String id : paramVo.split(",")) {

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("goal_code", ids[5]);
			mapVo.put("kpi_code", ids[6]);
			mapVo.put("dept_no", ids[7]);
			mapVo.put("dept_id", ids[8]);
			mapVo.put("user_code", SessionManager.getUserCode());
			mapVo.put("is_audit", 1);
			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			prmHosTargetDataJsonCalculate = prmDeptKpiValueCalculateService.auditPrmDeptKpiValueCalculate(mapVo);
		}

		return JSONObject.parseObject(prmHosTargetDataJsonCalculate);
	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/reAuditPrmDeptKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmDeptKpiValueCalculate(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		String prmHosTargetDataJsonCalculate = "";

		for (String id : paramVo.split(",")) {

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("goal_code", ids[5]);
			mapVo.put("kpi_code", ids[6]);
			mapVo.put("dept_no", ids[7]);
			mapVo.put("dept_id", ids[8]);
			mapVo.put("user_code", "");
			mapVo.put("is_audit", 0);
			mapVo.put("audit_date", "");

			prmHosTargetDataJsonCalculate = prmDeptKpiValueCalculateService.reauditPrmDeptKpiValueCalculate(mapVo);
		}

		return JSONObject.parseObject(prmHosTargetDataJsonCalculate);
	}
	
	/**
	 * @Description CREATE
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/collectPrmDeptKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectPrmDeptKpiValueCalculate(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		mapVo.put("acc_year", paramVo);

		String deptKpiValueCalculate = prmDeptKpiValueCalculateService.collectDeptKpiValueCalculate(mapVo);

		return JSONObject.parseObject(deptKpiValueCalculate);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/saveBatchDeptKpiValueCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchDeptKpiValueCalculate(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("kpi_code", ids[5]);
			mapVo.put("goal_code", ids[6]);
			mapVo.put("dept_no", ids[7]);
			mapVo.put("dept_id", ids[8]);
			mapVo.put("kpi_value", ids[9]);

			listVo.add(mapVo);

		}

		String PrmDeptKpiValueCalculateJson = prmDeptKpiValueCalculateService.updateBatchPrmDeptKpiValueCalculate(listVo);

		return JSONObject.parseObject(PrmDeptKpiValueCalculateJson);
	}

}
