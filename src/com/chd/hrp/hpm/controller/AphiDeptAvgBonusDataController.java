package com.chd.hrp.hpm.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.AphiDeptAvgBonusDataService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiDeptAvgBonusDataController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiDeptAvgBonusDataController.class);

	@Resource(name = "aphiDeptAvgBonusDataService")
	private final AphiDeptAvgBonusDataService aphiDeptAvgBonusDataService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hpm/deptavgbonusdata/deptAvgBonusDataMainPage", method = RequestMethod.GET)
	public String deptAvgBonusDataMainPage(Model mode) throws Exception {

		return "hpm/deptavgbonusdata/deptAvgBonusDataMain";

	}

	// 查询
	@RequestMapping(value = "/hpm/deptavgbonusdata/queryDeptAvgBonusData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptAvgBonusData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		String ACCT_YEAR = SessionManager.getAcctYear();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (mapVo.containsKey("acct_year_month")) {

			if (mapVo.get("acct_year_month") != null && !"".equals(mapVo.get("acct_year_month"))) {

				mapVo.put("acct_year", mapVo.get("acct_year_month").toString().substring(0, 4));

				mapVo.put("acct_month", mapVo.get("acct_year_month").toString().substring(4, mapVo.get("acct_year_month").toString().length()));
			}
		}

		String deptAvgBonusData = aphiDeptAvgBonusDataService.queryDeptAvgBonusData(getPage(mapVo));

		return JSONObject.parseObject(deptAvgBonusData);
	}

	// 查询奖金核算的表头
	@RequestMapping(value = "/hpm/deptavgbonusdata/queryDeptAvgBonusDataGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptAvgBonusDataGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiDeptAvgBonusDataService.queryDeptAvgBonusDataGrid(mapVo);

		return resultJson;

	}

	@RequestMapping(value = "/hpm/deptavgbonusdata/initDeptAvgBonusData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initDeptAvgBonusData(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiDeptAvgBonusDataService.initDeptAvgBonusData(mapVo);

		return JSONObject.parseObject(resultJson);
	}

	@RequestMapping(value = "/hpm/deptavgbonusdata/collectDeptAvgBonusData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectDeptAvgBonusData(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiDeptAvgBonusDataService.updateDeptAvgBonusData(mapVo);

		return JSONObject.parseObject(resultJson);
	}

	// 奖金查询的数据 查询表头根据指标
	@RequestMapping(value = "/hpm/deptavgbonusdata/exportDeptAvgBonusDataExcel")
	@ResponseBody
	public String exportDeptAvgBonusDataExcel(@RequestParam(value = "paras", required = true) String paras, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String[] paraArray = paras.split("@");

		mapVo.put("acct_year", paraArray[0].substring(0, 4));

		mapVo.put("acct_month", paraArray[0].substring(4, 6));

		if (!"null".equals(paraArray[1])) {

			mapVo.put("target_code", paraArray[1]);

		}

		String fileName = "业务科室人均奖核算";

		// 组织表头；

		LinkedHashMap<String, String> headMap = new LinkedHashMap<String, String>();

		List<AphiItem> list = aphiDeptAvgBonusDataService.getGridTitleMap(mapVo);

		headMap.put("acct_year&acct_month", "核算年月");

		headMap.put("dept_code", "科室编码");

		headMap.put("dept_name", "科室名称");

		for (int i = 0; i < list.size(); i++) {

			AphiItem item = list.get(i);

			headMap.put("bonus_money" + i, item.getItem_name());

		}

		headMap.put("dept_bonus", "合计");

		headMap.put("emp_count", "科室人数");

		headMap.put("dept_avg_bonus", "人均奖");

		// 组织内容

		String deptBonusData = aphiDeptAvgBonusDataService.queryDeptAvgBonusData(getPage(mapVo));

		//UploadUtil.exportExcel(request, response, JSONObject.parseObject(deptBonusData), headMap, fileName);

		return null;

	}
}
