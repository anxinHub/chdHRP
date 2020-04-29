package com.chd.hrp.htcg.controller.calculation;
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
import com.chd.hrp.htcg.service.calculation.HtcgDrugAdminCostService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgDrugAdminCostController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgDrugAdminCostController.class);

	@Resource(name = "htcgDrugAdminCostService")
	private final HtcgDrugAdminCostService htcgDrugAdminCostService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/calculation/drugadmincost/htcgDrugAdminCostMainPage", method = RequestMethod.GET)
	public String htcgDrugAdminCostMainPage(Model mode) throws Exception {

		return "hrp/htcg/calculation/drugadmincost/htcgDrugAdminCostMain";

	}
	// 生成
	@RequestMapping(value = "/hrp/htcg/calculation/drugadmincost/initHtcgDrugAdminCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgDrugAdminCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String drugAdviceJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			drugAdviceJson = htcgDrugAdminCostService.initHtcgDrugAdminCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/calculation/drugadmincost/queryHtcgDrugAdminCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgDrugAdminCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drugAdminCost = htcgDrugAdminCostService.queryHtcgDrugAdminCost(getPage(mapVo));

		return JSONObject.parseObject(drugAdminCost);

	}

	/*
	 * 成本采集
	 * */
	@RequestMapping(value = "/hrp/htcg/calculation/drugadmincost/collectHtcgDeptDrugAdminCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHtcgDeptDrugAdminCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String drugAdviceJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			drugAdviceJson = htcgDrugAdminCostService.collectHtcgDeptDrugAdminCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);
		 

	}
	/*
	 * 查询成本采集
	 * */
    @RequestMapping(value = "/hrp/htcg/calculation/drugadmincost/queryHtcgDeptDrugAdminCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgDeptDrugAdminCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String drugAdviceJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			drugAdviceJson = htcgDrugAdminCostService.queryHtcgDeptDrugAdminCost(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);
	}
  
	/*
	 * 工作量分摊
	 * */
	@RequestMapping(value = "/hrp/htcg/calculation/drugadmincost/workloadHtcgDrugAdminCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> workloadHtcgDrugAdminCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String drugAdviceJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			drugAdviceJson = htcgDrugAdminCostService.workloadHtcgDrugAdminCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);
	}

	/*
	 * 收支配比分摊
	 * */
	@RequestMapping(value = "/hrp/htcg/calculation/drugadmincost/revenueHtcgDrugAdminCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> revenueHtcgDrugAdminCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String drugAdviceJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			drugAdviceJson = htcgDrugAdminCostService.revenueHtcgDrugAdminCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/htcg/calculation/drugadmincost/deleteHtcgDrugAdminCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrugAdminCost(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("period_type_code", ids[3]);
			mapVo.put("acc_year", ids[4]);
			mapVo.put("acc_month", ids[5]);
			mapVo.put("drug_code", ids[6]);
			listVo.add(mapVo);
		}
		String drugAdminCostJson = null;
		try {
			drugAdminCostJson = htcgDrugAdminCostService.deleteBatchHtcgDrugAdminCost(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			drugAdminCostJson = e.getMessage();
		}
		return JSONObject.parseObject(drugAdminCostJson);

	}
}
