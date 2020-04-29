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
import com.chd.hrp.htcg.service.calculation.HtcgMaterialPrimCostService;

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
public class HtcgMaterialPrimCostController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgMaterialPrimCostController.class);

	@Resource(name = "htcgMaterialPrimCostService")
	private final HtcgMaterialPrimCostService htcgMaterialPrimCostService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/calculation/materialprimcost/htcgMaterialPrimCostMainPage", method = RequestMethod.GET)
	public String htcgMaterialPrimCostMainPage(Model mode) throws Exception {
		return "hrp/htcg/calculation/materialprimcost/htcgMaterialPrimCostMain";

	}

	// 生成
	@RequestMapping(value = "/hrp/htcg/calculation/materialprimcost/initHtcgMaterialPrimCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgMaterialPrimCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String materialPrimCost = "";
		try {
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
			 mapVo.put("copy_code", SessionManager.getCopyCode());
			 materialPrimCost =  htcgMaterialPrimCostService.initHtcgMaterialPrimCost(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			materialPrimCost = e.getMessage();
		}
		 return JSONObject.parseObject(materialPrimCost);
		 
	
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/calculation/materialprimcost/queryHtcgMaterialPrimCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMaterialPrimCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 mapVo.put("group_id", SessionManager.getGroupId());
		 mapVo.put("hos_id", SessionManager.getHosId());
		 mapVo.put("copy_code", SessionManager.getCopyCode());
		String materialPrimCost = htcgMaterialPrimCostService.queryHtcgMaterialPrimCost(getPage(mapVo));
		return JSONObject.parseObject(materialPrimCost);

	}
	// 批量填充加成率
	@RequestMapping(value = "/hrp/htcg/calculation/materialprimcost/updatebatchMarkupPercentMaterialPrimCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatebatchMarkupPercentMaterialPrimCost(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		String materialPrimCost = "";
		try {
			
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
				mapVo.put("mate_code", ids[6]);
				mapVo.put("markup_percent", ids[7]);
				listVo.add(mapVo);
			}
			materialPrimCost = htcgMaterialPrimCostService.updatebatchMarkupPercentMaterialPrimCost(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			materialPrimCost = e.getMessage();
		}
		
		 return JSONObject.parseObject(materialPrimCost);
		 
	}
	
	// 计算成本
		@RequestMapping(value = "/hrp/htcg/calculation/materialprimcost/calHtcgMaterialPrimCost", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> calHtcgMaterialPrimCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			String materialPrimCost = "";
			try {
				  mapVo.put("group_id", SessionManager.getGroupId());
				 mapVo.put("hos_id", SessionManager.getHosId());
				 mapVo.put("copy_code", SessionManager.getCopyCode());
				 materialPrimCost =  htcgMaterialPrimCostService.calHtcgMaterialPrimCost(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				materialPrimCost = e.getMessage();
			}
			 return JSONObject.parseObject(materialPrimCost);
			 
			
		}
		
	// 删除
	@RequestMapping(value = "/hrp/htcg/calculation/materialprimcost/deleteHtcgMaterialPrimCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMaterialPrimCost(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("mate_code", ids[6]);
			listVo.add(mapVo);
		}
		String materialPrimCost = null;
		try {
			materialPrimCost = htcgMaterialPrimCostService.deleteBatchHtcgMaterialPrimCost(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			materialPrimCost = e.getMessage();
		}
		return JSONObject.parseObject(materialPrimCost);

	}
	
	
}
