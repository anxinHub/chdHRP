package com.chd.hrp.cost.controller;

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
import com.chd.hrp.cost.entity.CostSubjItemMap;
import com.chd.hrp.cost.service.CostSubjItemMapService;

@Controller
public class CostSubjItemMapController extends BaseController{ 

	private static Logger logger = Logger.getLogger(CostSubjItemMapController.class);
	
	@Resource(name = "costSubjItemMapService") 
	private final CostSubjItemMapService costSubjItemMapService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/cost/costsubjitemmap/costSubjItemMapMainPage", method = RequestMethod.GET)
	public String costSubjItemMapMainPage(Model mode) throws Exception {
		return "hrp/cost/costsubjitemmap/costSubjItemMapMain";

	}
	
	// 添加页面 
	@RequestMapping(value = "/hrp/cost/costsubjitemmap/costSubjItemMapAddPage", method = RequestMethod.GET)
	public String costSubjItemMapAddPage(Model mode) throws Exception {

		return "hrp/cost/costsubjitemmap/costSubjItemMapAdd";

	}
	
	   // 保存
	@RequestMapping(value = "/hrp/cost/costsubjitemmap/addCostSubjItemMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostSubjItemMap(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
	  		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String costSubjItemMapJson = "";
		
		try {
	  		
			costSubjItemMapJson = costSubjItemMapService.addCostSubjItemMap(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			costSubjItemMapJson = e.getMessage();
		}
		
		return JSONObject.parseObject(costSubjItemMapJson);

	}
		
		
		// 查询
		@RequestMapping(value = "/hrp/cost/costsubjitemmap/queryCostSubjItemMap", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryCostSubjItemMap(
				@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {
			
	        mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
		  		
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			String costSubjItemMapJson = costSubjItemMapService.queryCostSubjItemMap(getPage(mapVo));

			return JSONObject.parseObject(costSubjItemMapJson);

		}
		
		// 删除
		@RequestMapping(value = "/hrp/cost/costsubjitemmap/deleteCostSubjItemMap", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteCostSubjItemMap(
				@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
			  	mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("acc_year", ids[3]);
				mapVo.put("subj_code", ids[4]);
				mapVo.put("item_code", ids[5]);
				listVo.add(mapVo);
			}
			
			String costSubjItemMapJson = "";
			
			try {
		
				costSubjItemMapJson = costSubjItemMapService.deleteBatchCostSubjItemMap(listVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				costSubjItemMapJson = e.getMessage();
			}

			return JSONObject.parseObject(costSubjItemMapJson);

		}
		
		
		// 修改跳转
		@RequestMapping(value = "/hrp/cost/costsubjitemmap/costSubjItemMapUpdatePage", method = RequestMethod.GET)
		public String costSubjItemMapUpdatePage(@RequestParam Map<String, Object> mapVo,
				Model mode) throws Exception {
			mapVo.put("acc_year", SessionManager.getAcctYear());
			CostSubjItemMap cosItemSubjRef = costSubjItemMapService.queryCostSubjItemMapByCode(mapVo);
			mode.addAttribute("group_id", cosItemSubjRef.getGroup_id());
			mode.addAttribute("hos_id", cosItemSubjRef.getHos_id());
			mode.addAttribute("copy_code", cosItemSubjRef.getCopy_code());
			mode.addAttribute("acc_year", cosItemSubjRef.getAcc_year());
			mode.addAttribute("subj_code", cosItemSubjRef.getSubj_code());
			mode.addAttribute("subj_name", cosItemSubjRef.getSubj_name());
			mode.addAttribute("item_code", cosItemSubjRef.getItem_code());
			mode.addAttribute("item_name", cosItemSubjRef.getItem_name());
			return "hrp/cost/costsubjitemmap/costSubjItemMapUpdate";
		}
		 
		
		
		// 修改保存
		@RequestMapping(value = "/hrp/cost/costsubjitemmap/updateCostSubjItemMap", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateCosItemSubjRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			String costSubjItemMapJson = "";
			try {
				costSubjItemMapJson = costSubjItemMapService.updateCostSubjItemMap(mapVo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				costSubjItemMapJson = e.getMessage();
			}
			return JSONObject.parseObject(costSubjItemMapJson);
		}
		
}
