/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.hrp.prm.service.PrmDeptTargetDataCalculateService;

/**
 * 
 * @Description: 0312 科室绩效指标数据表
 * @Table: PRM_DEPT_TARGET_DATA
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptTargetDataCalculateController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptTargetDataController.class);

	// 引入Service服务
	@Resource(name = "prmDeptTargetDataCalculateService")
	private final PrmDeptTargetDataCalculateService prmDeptTargetDataCalculateService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/prmDeptTargetDataCalculateMainPage", method = RequestMethod.GET)
	public String prmDeptTargetDataMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdepttargetdata/prmDeptTargetDataCalculateMain";

	}

	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/queryPrmDeptTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptTargetDataCalculate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String prmDeptTargetDataCalculate = prmDeptTargetDataCalculateService.queryPrmDeptTargetPrmTargetDataCalculate(getPage(mapVo));

		return JSONObject.parseObject(prmDeptTargetDataCalculate);

	}

	/**
	 * @Description CREATE
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/createPrmDeptTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmDeptTargetDataCalculate(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		String prmHosTargetDataCalculate = prmDeptTargetDataCalculateService.createPrmDeptTargetDataCalculate(mapVo, paramVo);

		return JSONObject.parseObject(prmHosTargetDataCalculate);

	}
	
	/**
	 * @Description CREATE
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/collectPrmDeptTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectPrmDeptTargetDataCalculate(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String prmHosTargetDataCalculate = prmDeptTargetDataCalculateService.collectPrmDeptTargetDataCalculate(mapVo);

		return JSONObject.parseObject(prmHosTargetDataCalculate);

	}

	@RequestMapping(value = "/hrp/prm/prmdepttargetdata/saveBatchPrmDeptTargetDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchPrmDeptTargetDataCalculate(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("target_code", ids[5]);
			mapVo.put("dept_no", ids[6]);
			mapVo.put("dept_id", ids[7]);
			mapVo.put("target_value", ids[8]);

			listVo.add(mapVo);

		}

		String prmHosTargetDataJson = prmDeptTargetDataCalculateService.updateBatchPrmDeptTargetDataCalculate(listVo);

		return JSONObject.parseObject(prmHosTargetDataJson);
	}

}
