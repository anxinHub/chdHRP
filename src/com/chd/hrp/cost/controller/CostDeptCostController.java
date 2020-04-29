/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.cost.controller;

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
import com.chd.hrp.cost.service.CostDeptCostCheckService;
import com.chd.hrp.cost.service.CostDeptCostService;

/** 
 * @Description: 成本_科室成本核算总表
 * @Table: COST_DEPT_COST
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostDeptCostController extends BaseController {

	private static Logger logger = Logger.getLogger(CostDeptCostController.class);

	// 引入Service服务
	@Resource(name = "costDeptCostService")
	private final CostDeptCostService costDeptCostService = null;
	// 引入Service服务
	@Resource(name = "costDeptCostCheckService")
	private final CostDeptCostCheckService costDeptCostCheckService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcost/costDeptCostMainPage", method = RequestMethod.GET)
	public String costDeptCostMainPage(Model mode) throws Exception {

		return "hrp/cost/costdeptcost/costDeptCostMain";

	}
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcost/costDeptCostCheckPage", method = RequestMethod.GET)
	public String costDeptCostCheckPage(Model mode) throws Exception {

		return "hrp/cost/costdeptcost/costDeptCostCheck";

	}
	
	/**
	 * @Description 查询数据 成本_科室成本核算总表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcost/queryCostDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		System.err.println("=======================================" + mapVo +"=============================");
		
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
		String costDeptCost = costDeptCostService.query(getPage(mapVo));

		return JSONObject.parseObject(costDeptCost);

	}
	
	/**
	 * @Description 查询数据 成本_科室成本核算总表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcost/queryCostDeptCostCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptCostCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		System.err.println("=======================================" + mapVo +"=============================");
		
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
		String costDeptCost = costDeptCostService.queryCheck(getPage(mapVo));

		return JSONObject.parseObject(costDeptCost);

	}

	/**
	 * @Description 成本分摊核算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcost/generate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String costDeptCostJson = costDeptCostService.generate(mapVo);

		return JSONObject.parseObject(costDeptCostJson);

	}
	/**
	 * @Description 成本分摊校验
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcost/check", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> check(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String costDeptCostJson = costDeptCostService.saveCostDeptCostCheckProc(mapVo);
		
		return JSONObject.parseObject(costDeptCostJson);
		
	}
	/**
	 * @Description 成本分摊生成 存储过程
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcost/saveProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveProc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String costDeptCostJson = costDeptCostService.saveCostDeptCostProc(mapVo);
		
		return JSONObject.parseObject(costDeptCostJson);
		
	}
	/**
	 * @Description 成本分摊校验 存储在H2数据库中
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptcost/checkH2Db", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkH2Db(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String costDeptCostJson = costDeptCostCheckService.checkH2Db(mapVo);
		
		return JSONObject.parseObject(costDeptCostJson);
		
	}
	
}
