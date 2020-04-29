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
import com.chd.hrp.htcg.service.calculation.HtcgDrugPrimCostService;

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
public class HtcgDrugPrimCostController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgDrugPrimCostController.class);

	@Resource(name = "htcgDrugPrimCostService")
	private final HtcgDrugPrimCostService htcgDrugPrimCostService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/calculation/drugprimcost/htcgDrugPrimCostMainPage", method = RequestMethod.GET)
	public String htcgDrugPrimCostMainPage(Model mode) throws Exception {
		return "hrp/htcg/calculation/drugprimcost/htcgDrugPrimCostMain";

	}

	// 生成
	@RequestMapping(value = "/hrp/htcg/calculation/drugprimcost/initHtcgDrugPrimCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgDrugPrimCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String drugAdviceJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			drugAdviceJson = htcgDrugPrimCostService.initHtcgDrugPrimCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);
	}

	

	// 查询
	@RequestMapping(value = "/hrp/htcg/calculation/drugprimcost/queryHtcgDrugPrimCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgDrugPrimCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drugPrimCost = htcgDrugPrimCostService.queryHtcgDrugPrimCost(getPage(mapVo));
		return JSONObject.parseObject(drugPrimCost);

	}

	// 批量填充加成率
	@RequestMapping(value = "/hrp/htcg/calculation/drugprimcost/updateBatchMarkupPercentDrugPrimCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchMarkupPercentDrugPrimCost(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			mapVo.put("markup_percent", ids[7]);
			listVo.add(mapVo);
		}
		String drugPrimCostJson = null;
		try {
			drugPrimCostJson = htcgDrugPrimCostService.updateBatchMarkupPercentDrugPrimCost(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			drugPrimCostJson = e.getMessage();
			
		}
		return JSONObject.parseObject(drugPrimCostJson);

	}
		
	// 计算成本
	@RequestMapping(value = "/hrp/htcg/calculation/drugprimcost/calHtcgDrugPrimCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> calHtcgDrugPrimCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String drugAdviceJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			drugAdviceJson = htcgDrugPrimCostService.calHtcgDrugPrimCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);
		 
	}
	// 删除
	@RequestMapping(value = "/hrp/htcg/calculation/drugprimcost/deleteHtcgDrugPrimCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrugPrimCost(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		String drugPrimCostJson = null;
		try {
			drugPrimCostJson = htcgDrugPrimCostService.deleteBatchHtcgDrugPrimCost(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			drugPrimCostJson = e.getMessage();
					
		}
		return JSONObject.parseObject(drugPrimCostJson);

	}
}
