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
import com.chd.hrp.hpm.service.AphiDeptDictService;
import com.chd.hrp.hpm.service.AphiDeptIncomeService;
import com.chd.hrp.hpm.service.AphiIncomeItemService;
import com.chd.hrp.hpm.service.AphiIncomeitemDataService;
import com.chd.hrp.hpm.service.AphiSelectDictService;

@Controller
public class AphiDeptIncomeController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiDeptIncomeController.class);
	
	
	@Resource(name = "aphiDeptIncomeService")
	private final AphiDeptIncomeService aphiDeptIncomeService = null;
	
	@Resource(name = "aphiDeptDictService")
	private final AphiDeptDictService aphiDeptDictService = null;
	
	@Resource(name = "aphiIncomeItemService")
	private AphiIncomeItemService aphiIncomeItemService = null;
	// 主页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptincome/hpmDeptIncomeMainPage", method = RequestMethod.GET)
	public String hpmDeptIncomeMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptincome/hpmDeptIncomeMain";

	}
	
	//增加页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptincome/hpmDeptIncomeAddPage", method = RequestMethod.GET)
	public String hpmDeptIncomeAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptincome/hpmDeptIncomeAdd";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptincome/queryHpmDeptIncomeMaping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptIncomeMaping(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
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
	
		String DeptIncome = aphiDeptIncomeService.queryDeptIncomeMain(getPage(mapVo));
	
		return JSONObject.parseObject(DeptIncome);
	
	}
	
	
	//增加页面科室查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptincome/queryDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String deptIncome = aphiDeptDictService.queryDeptIncomeAdd(getPage(mapVo));

		return JSONObject.parseObject(deptIncome);

	}
	
	// 增加页面项目查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptincome/queryDeptItemAdd", method = RequestMethod.POST)
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

		String incomeItem = aphiIncomeItemService.queryIncomeitem(getPage(mapVo));

		return JSONObject.parseObject(incomeItem);

	}
	
	//科室与收入项目关系配置保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptincome/addHpmDeptIncomeMaping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmDeptIncomeMaping(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

				mapAdd.put("income_item_code", String.valueOf(jsonObj.get("income_item_code")));

				dataAddedBatch.add(mapAdd);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}

		}
		String addBatchPrmEmpKpiSectionJson = "";

		if (dataAddedBatch.size() > 0) {
			aphiDeptIncomeService.deleteBatchDeptIncomeMaping(dataAddedBatch);
			addBatchPrmEmpKpiSectionJson = aphiDeptIncomeService.addBatchDeptIncomeMaping(dataAddedBatch);
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
	@RequestMapping(value = "/hrp/hpm/hpmdeptincome/deleteHpmDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDeptIncome(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("income_item_code", ids[6]);

			listVo.add(mapVo);

		}

		String PrmDeptMapingJson = aphiDeptIncomeService.deleteHpmDeptIncome(listVo);

		return JSONObject.parseObject(PrmDeptMapingJson);

	}
	
	

}
