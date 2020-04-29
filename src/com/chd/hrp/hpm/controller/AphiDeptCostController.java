package com.chd.hrp.hpm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hpm.service.AphiCostitemService;
import com.chd.hrp.hpm.service.AphiDeptCostService;
import com.chd.hrp.hpm.service.AphiDeptDictService;

@Controller
public class AphiDeptCostController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AphiDeptCostController.class);
	
	
	@Resource(name = "aphiDeptCostService")
	private final AphiDeptCostService aphiDeptCostService = null;
	
	@Resource(name = "aphiDeptDictService")
	private final AphiDeptDictService aphiDeptDictService = null;
	
	@Resource(name = "aphiCostitemService")
	private final AphiCostitemService aphiCostitemService = null;
	
	// 主页面跳转                                                          
	@RequestMapping(value = "/hrp/hpm/hpmdeptcostitem/hpmDeptCostItemMainPage", method = RequestMethod.GET)
	public String hpmDeptCostItemMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptcostitem/hpmDeptCostItemMain";

	}
	
	//增加页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptcostitem/hpmDeptCostItemAddPage", method = RequestMethod.GET)
	public String hpmDeptIncomeAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptcostitem/hpmDeptCostItemAdd";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptcostitem/queryHpmDeptCostItemMaping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptCostItemMaping(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
	
		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {
	
			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);
	
			String acct_month = mapVo.get("acct_yearm").toString().substring(4);
	
			mapVo.put("acct_year", acct_year);
	
			mapVo.put("acct_month", acct_month);
	
		}
	
		String DeptCost = aphiDeptCostService.queryDeptCostMain(getPage(mapVo));
	
		return JSONObject.parseObject(DeptCost);
	
	}
	
	//增加页面科室查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptcostitem/queryDeptCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String deptCost = aphiDeptDictService.queryDeptIncomeAdd(getPage(mapVo));

		return JSONObject.parseObject(deptCost);

	}
	
	// 增加页面项目查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptcostitem/queryDeptItemAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptItemAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}

		String costItem = aphiCostitemService.queryCostitem(getPage(mapVo));

		return JSONObject.parseObject(costItem);

	}
	
	//科室与收入项目关系配置保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptcostitem/addHpmDeptCostItemMaping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmDeptCostItemMaping(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		List<Map<String, Object>> dataAddedBatch = new ArrayList<Map<String, Object>>();// 存放明细

		String dataSection = String.valueOf(mapVo.get("maping_data"));

		JSONArray dataSectionJson = JSONArray.parseArray(dataSection);

		Iterator dataSectionJsonIt = dataSectionJson.iterator();

		while (dataSectionJsonIt.hasNext()) {

			JSONObject jsonObj = JSONObject.parseObject(dataSectionJsonIt.next().toString());

			try {

				/* ADD */

				Map<String, Object> mapAdd = new HashMap<String, Object>();

				mapAdd.put("group_id", SessionManager.getGroupId());

				mapAdd.put("hos_id", SessionManager.getHosId());

				mapAdd.put("copy_code", SessionManager.getCopyCode());
				
				mapAdd.put("acct_year", String.valueOf(jsonObj.get("acct_year")));
				
				mapAdd.put("acct_month", String.valueOf(jsonObj.get("acct_month")));

				mapAdd.put("dept_id", String.valueOf(jsonObj.get("dept_id")));
				
				mapAdd.put("dept_no", String.valueOf(jsonObj.get("dept_no")));

				mapAdd.put("cost_item_code", String.valueOf(jsonObj.get("cost_item_code")));

				dataAddedBatch.add(mapAdd);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}

		}
		String addBatchPrmEmpKpiSectionJson = "";

		if (dataAddedBatch.size() > 0) {
			aphiDeptCostService.deleteBatchDeptCostMaping(dataAddedBatch);
			addBatchPrmEmpKpiSectionJson = aphiDeptCostService.addBatchDeptCostMaping(dataAddedBatch);
		}

		String StringJson = "";
		if (addBatchPrmEmpKpiSectionJson != "") {

			StringJson = addBatchPrmEmpKpiSectionJson;

		} else {

			StringJson = "{\"msg\":\"数据没变化.\",\"state\":\"true\"}";
		}

		return JSONObject.parseObject(StringJson);

	}
	
	
	//增加页面删除
	@RequestMapping(value = "/hrp/hpm/hpmdeptcostitem/deleteHpmDeptCostItemMapping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDeptCostItemMapping(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acct_year", ids[3]);
			mapVo.put("acct_month", ids[4]);
			mapVo.put("dept_id", ids[5]);
			mapVo.put("cost_item_code", ids[6]);

			listVo.add(mapVo);

		}

		String PrmDeptMapingJson = aphiDeptCostService.deleteHpmDeptCost(listVo);

		return JSONObject.parseObject(PrmDeptMapingJson);

	}

	

}
