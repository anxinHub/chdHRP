/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmGoalService;
import com.chd.hrp.prm.service.PrmHosFunStackService;
import com.chd.hrp.prm.service.PrmHosKpiAdService;
import com.chd.hrp.prm.service.PrmHosKpiLedService;
import com.chd.hrp.prm.service.PrmHosKpiSectionService;
import com.chd.hrp.prm.service.PrmHosSchemeService;
import com.chd.hrp.prm.service.PrmLedService;

/**
 * 
 * @Description: 0203 医院绩效方案表
 * @Table: PRM_HOS_SCHEME
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmHosSchemeController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmHosSchemeController.class);

	// 引入Service服务
	@Resource(name = "prmHosSchemeService")
	private final PrmHosSchemeService prmHosSchemeService = null;

	// 引入Service服务
	@Resource(name = "prmHosKpiSectionService")
	private final PrmHosKpiSectionService prmHosKpiSectionService = null;

	// 引入Service服务
	@Resource(name = "prmHosKpiAdService")
	private final PrmHosKpiAdService prmHosKpiAdService = null;
	
	@Resource(name = "prmHosFunStackService")
	private final PrmHosFunStackService prmHosFunStackService = null;

	@Resource(name = "prmLedService")
	private final PrmLedService prmLedService = null;

	@Resource(name = "prmHosKpiLedService")
	private final PrmHosKpiLedService prmHosKpiLedService = null;
	
	@Resource(name = "prmGoalService")
	private final PrmGoalService prmGoalService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/prmHosSchemeMainPage", method = RequestMethod.GET)
	public String prmHosSchemeMainPage(Model mode) throws Exception {

		return "hrp/prm/prmhosscheme/prmHosSchemeMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/prmHosSchemeAddPage", method = RequestMethod.GET)
	public String prmHosSchemeAddPage(Model mode) throws Exception {

		return "hrp/prm/prmhosscheme/prmHosSchemeAdd";

	}

	/**
	 * @Description 添加数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/addPrmHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmHosScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}
		mapVo.put("start_month", mapVo.get("acc_month"));
		mapVo.put("is_audit", 1);
		Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
		
		if(isAudit > 0){
			return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
		}

		String prmHosSchemeJson = prmHosSchemeService.addPrmHosScheme(mapVo);

		return JSONObject.parseObject(prmHosSchemeJson);

	}

	/**
	 * @Description 更新跳转页面 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/prmHosSchemeUpdatePage", method = RequestMethod.GET)
	public String prmHosSchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}

		PrmHosScheme prmHosScheme = new PrmHosScheme();

		prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(mapVo);

		mode.addAttribute("group_id", prmHosScheme.getGroup_id());
		mode.addAttribute("hos_id", prmHosScheme.getHos_id());
		mode.addAttribute("copy_code", prmHosScheme.getCopy_code());
		mode.addAttribute("acc_year", prmHosScheme.getAcc_year());
		mode.addAttribute("acc_month", prmHosScheme.getAcc_month());
		mode.addAttribute("goal_code", prmHosScheme.getGoal_code());
		mode.addAttribute("kpi_code", prmHosScheme.getKpi_code());
		mode.addAttribute("check_hos_id", prmHosScheme.getCheck_hos_id());
		mode.addAttribute("super_kpi_code", prmHosScheme.getSuper_kpi_code());
		mode.addAttribute("order_no", prmHosScheme.getOrder_no());
		// mode.addAttribute("level", prmHosScheme.getLevel());
		mode.addAttribute("is_last", prmHosScheme.getIs_last());
		mode.addAttribute("ratio", prmHosScheme.getRatio());
		mode.addAttribute("goal_value", prmHosScheme.getGoal_value());
		mode.addAttribute("grade_meth_code", prmHosScheme.getGrade_meth_code());
		mode.addAttribute("method_code", prmHosScheme.getMethod_code());
		mode.addAttribute("formula_code", prmHosScheme.getFormula_code());
		mode.addAttribute("fun_code", prmHosScheme.getFun_code());

		return "hrp/prm/prmhosscheme/prmHosSchemeUpdate";
	}

	/**
	 * @Description 更新数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/updatePrmHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmHosScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}
		mapVo.put("start_month", mapVo.get("acc_month"));
		mapVo.put("is_audit", 1);
		Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
		
		if(isAudit > 0){
			return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
		}
		String prmHosSchemeJson = prmHosSchemeService.updatePrmHosScheme(mapVo);

		return JSONObject.parseObject(prmHosSchemeJson);
	}

	/**
	 * @Description 删除数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/deletePrmHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmHosScheme(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		boolean flag = false;
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String [] str = paramVo.split(",");
		for (String id : str) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("start_month", ids[4]);
			mapVo.put("goal_code", ids[5]);
			
			mapVo.put("is_audit", 1);
			Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
			
			if(isAudit > 0){
				return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
			}
			mapVo.put("kpi_code", ids[6]);
			mapVo.put("check_hos_id", ids[7]);

			List<PrmHosScheme> list = prmHosSchemeService.queryPrmHosSchemeBySuperKpiCode(mapVo);
			if (list.size() > 0) {
				boolean v_str = false;
				for(PrmHosScheme prmHosScheme : list){
					v_str = false;
					for (String i : str) {
						if(prmHosScheme.getKpi_code().equals(i.split("@")[6])){
							v_str = true;
							break;
						}
					}
					if(v_str == false){
						break;
					}
				}
				if(v_str == false){
					flag = true;
					break;
				}
			}
			listVo.add(mapVo);

		}

		if (flag) {
			return JSONObject.parseObject("{\"error\":\"删除失败 请先删除下级指标\"}");
		}
		prmHosKpiAdService.deleteBatchPrmHosKpiAd(listVo);
		prmHosKpiSectionService.deleteBatchPrmHosKpiSection(listVo);
		prmHosKpiLedService.deleteBatchPrmHosKpiLed(listVo);
		prmHosFunStackService.deleteBatchPrmHosFunStack(listVo);
		String prmHosSchemeJson = prmHosSchemeService.deleteBatchPrmHosScheme(listVo);

		return JSONObject.parseObject(prmHosSchemeJson);

	}

	/**
	 * @Description 查询数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/queryPrmHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}
		if (mapVo.get("super_kpi_code") != null) {
			if (mapVo.get("super_kpi_code").toString().endsWith("goal")) {
				mapVo.put("super_kpi_code", mapVo.get("kpi_code"));
				mapVo.put("kpi_code", "");
			} else {
				mapVo.put("super_kpi_code", mapVo.get("super_kpi_code").toString());
			}
		}
		mapVo.put("user_id", SessionManager.getUserId());
		
		mapVo.put("mod_code", SessionManager.getModCode());
		String prmHosScheme = prmHosSchemeService.queryPrmHosSchemeLeftName(getPage(mapVo));

		return JSONObject.parseObject(prmHosScheme);

	}

	/**
	 * @Description 更新数据 0203生成页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/prmHosSchemeCreate", method = RequestMethod.GET)
	public String prmHosSchemeCreate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/prm/prmhosscheme/prmHosSchemeCreate";

	}

	/**
	 * @Description 更新数据 0203 按照生成条件覆盖生成或者增量生成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/createPrmHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmHosScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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
		
		mapVo.put("start_month", mapVo.get("acc_month"));
		mapVo.put("is_audit", 1);
		Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
		
		if(isAudit > 0){
			return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
		}

		String createPrmHosScheme = prmHosSchemeService.createPrmHosScheme(mapVo);

		return JSONObject.parseObject(createPrmHosScheme);
	}

	/**
	 * @Description 添加数据 0203 医院绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/savePrmHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePrmHosScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		List<Map<String, Object>> kpi_data_batch = new ArrayList<Map<String, Object>>();

		JSONArray kpi_data_json = JSONArray.parseArray((String) mapVo.get("kpi_data"));

		Iterator kpi_data_it = kpi_data_json.iterator();

		try {

			while (kpi_data_it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(kpi_data_it.next().toString());

				Map<String, Object> mapKpi = new HashMap<String, Object>();

				if (!jsonObj.containsKey("group_id")) {
					continue;
				}

				mapKpi.put("group_id", jsonObj.get("group_id"));

				mapKpi.put("hos_id", jsonObj.get("hos_id"));

				mapKpi.put("copy_code", jsonObj.get("copy_code"));

				mapKpi.put("acc_year", jsonObj.get("acc_year"));

				mapKpi.put("acc_month", jsonObj.get("acc_month"));

				mapKpi.put("goal_code", jsonObj.get("goal_code"));
				
				mapKpi.put("start_month", mapKpi.get("acc_month"));
				mapKpi.put("is_audit", 1);
				Long isAudit = prmGoalService.queryPrmGoalByAudit(mapKpi);
				
				if(isAudit > 0){
					return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
				}

				mapKpi.put("kpi_code", jsonObj.get("kpi_code"));

				mapKpi.put("check_hos_id", jsonObj.get("check_hos_id"));

				mapKpi.put("ratio", jsonObj.get("ratio")==null  || "".equals(jsonObj.get("ratio").toString())?"0":jsonObj.get("ratio").toString());

				mapKpi.put("goal_value", jsonObj.get("goal_value")==null || "".equals(jsonObj.get("goal_value").toString())?"0":jsonObj.get("goal_value").toString());

				mapKpi.put("full_score", jsonObj.get("full_score")==null || "".equals(jsonObj.get("full_score").toString())?"0":jsonObj.get("full_score").toString());

				mapKpi.put("grade_meth_code", jsonObj.get("grade_meth_code"));

				String method_code = String.valueOf(jsonObj.get("method_code"));

				String formula_code = String.valueOf(jsonObj.get("formula_code"));

				String fun_code = String.valueOf(jsonObj.get("fun_code"));

				if(method_code != null && !method_code.equals("null")){
					if ("01".equals(method_code)) {
						mapKpi.put("method_code", jsonObj.get("method_code"));
						mapKpi.put("formula_code", "");
						mapKpi.put("fun_code", "");
					}else if("02".equals(method_code)){
						mapKpi.put("method_code", method_code);
						mapKpi.put("formula_code", formula_code);
						mapKpi.put("fun_code", "");
					}else if("03".equals(method_code)){
						mapKpi.put("method_code", method_code);
						mapKpi.put("formula_code", "");
						mapKpi.put("fun_code", fun_code);
					}
				}

				kpi_data_batch.add(mapKpi);

			}
		} catch (DataAccessException e) {

		}

		String prmHosSchemeJson = prmHosSchemeService.updateBatchPrmHosScheme(kpi_data_batch);

		return JSONObject.parseObject(prmHosSchemeJson);

	}

	/**
	 * @Description 科室区间法评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/prmHosSchemeSection", method = RequestMethod.GET)
	public String prmHosSchemeSection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		PrmHosScheme prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmHosScheme.getHos_code());
		
		mode.addAttribute("hos_name", prmHosScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", prmHosScheme.getAcc_year());

		mode.addAttribute("acc_month", prmHosScheme.getAcc_month());

		mode.addAttribute("goal_code", prmHosScheme.getGoal_code());

		mode.addAttribute("nature_name",prmHosScheme.getNature_name());

		mode.addAttribute("kpi_code", prmHosScheme.getKpi_code());

		mode.addAttribute("kpi_name", prmHosScheme.getKpi_name());

		mode.addAttribute("check_hos_id", prmHosScheme.getCheck_hos_id());
		
		mode.addAttribute("check_hos_code", prmHosScheme.getCheck_hos_code());
		
		mode.addAttribute("check_hos_name", prmHosScheme.getCheck_hos_name());

		mode.addAttribute("ratio", prmHosScheme.getRatio());

		mode.addAttribute("goal_value", prmHosScheme.getGoal_value());

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmhosscheme/prmHosKpiSectionMain";

	}
	
	
	/**
	 * @Description 查询数据 评分方法区间法(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/queryPrmHosSectionByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosSectionByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String HosKpiSection = prmHosKpiSectionService.queryPrmHosKpiSection(getPage(mapVo));

		return JSONObject.parseObject(HosKpiSection);
	}
	
	

	/**
	 * @Description 区间法评分标准设定
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/saveHosSchemeSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHosSchemeSection(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		List<Map<String, Object>> dataUpdatedBatch = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> dataAddedBatch = new ArrayList<Map<String, Object>>();// 存放明细

		String dataSection = String.valueOf(mapVo.get("dataSection"));

		JSONArray dataSectionJson = JSONArray.parseArray(dataSection);

		Iterator dataSectionJsonIt = dataSectionJson.iterator();

		while (dataSectionJsonIt.hasNext()) {

			JSONObject jsonObj = JSONObject.parseObject(dataSectionJsonIt.next().toString());

			try {
				
							
				/* ADD */
				if (((String) jsonObj.get("__status")).equals("add")) {

					Map<String, Object> mapAdd = new HashMap<String, Object>();

					mapAdd.put("group_id", SessionManager.getGroupId());

					mapAdd.put("hos_id", SessionManager.getHosId());

					mapAdd.put("copy_code", SessionManager.getCopyCode());

					mapAdd.put("section", Integer.parseInt(jsonObj.get("__index").toString()) + 1);

					mapAdd.put("acc_year", String.valueOf(mapVo.get("acc_year")));

					mapAdd.put("acc_month", String.valueOf(mapVo.get("acc_month")));

					mapAdd.put("goal_code", String.valueOf(mapVo.get("goal_code")));
					
					mapAdd.put("start_month", mapAdd.get("acc_month"));
					mapAdd.put("is_audit", 1);
					Long isAudit = prmGoalService.queryPrmGoalByAudit(mapAdd);
					
					if(isAudit > 0){
						return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
					}

					mapAdd.put("check_hos_id", String.valueOf(mapVo.get("check_hos_id")));

					mapAdd.put("kpi_code", String.valueOf(mapVo.get("kpi_code")));

					mapAdd.put("kpi_beg_value", jsonObj.get("kpi_beg_value") == null || "".equals(jsonObj.get("kpi_beg_value").toString())?"0":jsonObj.get("kpi_beg_value").toString());

					mapAdd.put("kpi_end_value", jsonObj.get("kpi_end_value") == null || "".equals(jsonObj.get("kpi_end_value").toString())?"0":jsonObj.get("kpi_end_value").toString());

					mapAdd.put("kpi_beg_score", jsonObj.get("kpi_beg_score") == null || "".equals(jsonObj.get("kpi_beg_score").toString())?"0":jsonObj.get("kpi_beg_score").toString());

					mapAdd.put("kpi_end_score", jsonObj.get("kpi_end_score") == null || "".equals(jsonObj.get("kpi_end_score").toString())?"0":jsonObj.get("kpi_end_score").toString());

					dataAddedBatch.add(mapAdd);

				}

				/* Update */
				if (((String) jsonObj.get("__status")).equals("update")) {
					
					if (!jsonObj.containsKey("group_id")) {
						continue;
					}

					Map<String, Object> mapUpdate = new HashMap<String, Object>();

					mapUpdate.put("group_id", SessionManager.getGroupId());

					mapUpdate.put("hos_id", SessionManager.getHosId());

					mapUpdate.put("copy_code", SessionManager.getCopyCode());

					mapUpdate.put("section", Integer.parseInt(jsonObj.get("__index").toString()) + 1);

					mapUpdate.put("acc_year", String.valueOf(mapVo.get("acc_year")));

					mapUpdate.put("acc_month", String.valueOf(mapVo.get("acc_month")));

					mapUpdate.put("goal_code", String.valueOf(mapVo.get("goal_code")));
					
					mapUpdate.put("start_month", mapUpdate.get("acc_month"));
					mapUpdate.put("is_audit", 1);
					Long isAudit = prmGoalService.queryPrmGoalByAudit(mapUpdate);
					
					if(isAudit > 0){
						return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
					}

					mapUpdate.put("check_hos_id", String.valueOf(mapVo.get("check_hos_id")));

					mapUpdate.put("kpi_code", String.valueOf(mapVo.get("kpi_code")));

					mapUpdate.put("kpi_beg_value", jsonObj.get("kpi_beg_value") == null || "".equals(jsonObj.get("kpi_beg_value").toString())?"0":jsonObj.get("kpi_beg_value").toString());

					mapUpdate.put("kpi_end_value", jsonObj.get("kpi_end_value") == null || "".equals(jsonObj.get("kpi_end_value").toString())?"0":jsonObj.get("kpi_end_value").toString());

					mapUpdate.put("kpi_beg_score", jsonObj.get("kpi_beg_score") == null || "".equals(jsonObj.get("kpi_beg_score").toString())?"0":jsonObj.get("kpi_beg_score").toString());

					mapUpdate.put("kpi_end_score", jsonObj.get("kpi_end_score") == null || "".equals(jsonObj.get("kpi_end_score").toString())?"0":jsonObj.get("kpi_end_score").toString());

					dataUpdatedBatch.add(mapUpdate);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}

		}
		String addBatchPrmHosKpiSectionJson = "";

		String updateBatchPrmHosKpiSectionJson = "";

		if (dataAddedBatch.size() > 0) {

			addBatchPrmHosKpiSectionJson = prmHosKpiSectionService.addBatchPrmHosKpiSection(dataAddedBatch);
		}

		if (dataUpdatedBatch.size() > 0) {
			updateBatchPrmHosKpiSectionJson = prmHosKpiSectionService.updateBatchPrmHosKpiSection(dataUpdatedBatch);
		}

		String StringJson = "";
		if (addBatchPrmHosKpiSectionJson != "") {

			StringJson = addBatchPrmHosKpiSectionJson;

		} else if (updateBatchPrmHosKpiSectionJson != "") {

			StringJson = updateBatchPrmHosKpiSectionJson;
		} else {

			StringJson = "{\"msg\":\"数据没变化.\",\"state\":\"true\"}";
		}

		return JSONObject.parseObject(StringJson);

	}

	/**
	 * @Description 指示灯设定
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/saveHosKpiLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHosKpiLed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		List<Map<String, Object>> dataUpdatedBatch = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> dataAddedBatch = new ArrayList<Map<String, Object>>();// 存放明细

		String dataSection = String.valueOf(mapVo.get("dataSection"));

		JSONArray dataSectionJson = JSONArray.parseArray(dataSection);

		Iterator dataSectionJsonIt = dataSectionJson.iterator();

		while (dataSectionJsonIt.hasNext()) {

			JSONObject jsonObj = JSONObject.parseObject(dataSectionJsonIt.next().toString());

			try {

				/* ADD */
				if (((String) jsonObj.get("__status")).equals("add")) {

					Map<String, Object> mapAdd = new HashMap<String, Object>();

					mapAdd.put("group_id", SessionManager.getGroupId());

					mapAdd.put("hos_id", SessionManager.getHosId());

					mapAdd.put("copy_code", SessionManager.getCopyCode());

					mapAdd.put("section", Integer.parseInt(jsonObj.get("__index").toString()) + 1);

					mapAdd.put("acc_year", String.valueOf(mapVo.get("acc_year")));

					mapAdd.put("acc_month", String.valueOf(mapVo.get("acc_month")));

					mapAdd.put("goal_code", String.valueOf(mapVo.get("goal_code")));
					
					mapAdd.put("start_month", mapAdd.get("acc_month"));
					mapAdd.put("is_audit", 1);
					Long isAudit = prmGoalService.queryPrmGoalByAudit(mapAdd);
					
					if(isAudit > 0){
						return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
					}

					mapAdd.put("check_hos_id", String.valueOf(mapVo.get("check_hos_id")));

					mapAdd.put("kpi_code", String.valueOf(mapVo.get("kpi_code")));

					mapAdd.put("sec_code", String.valueOf(jsonObj.get("sec_code")));

					mapAdd.put("kpi_beg_score", jsonObj.get("kpi_beg_score") == null || "".equals(jsonObj.get("kpi_beg_score").toString())?"0":jsonObj.get("kpi_beg_score").toString());

					mapAdd.put("kpi_end_score", jsonObj.get("kpi_end_score") == null || "".equals(jsonObj.get("kpi_end_score").toString())?"0":jsonObj.get("kpi_end_score").toString());

					dataAddedBatch.add(mapAdd);

				}

				/* Update */
				if (((String) jsonObj.get("__status")).equals("update")) {

					Map<String, Object> mapUpdate = new HashMap<String, Object>();

					mapUpdate.put("group_id", SessionManager.getGroupId());

					mapUpdate.put("hos_id", SessionManager.getHosId());

					mapUpdate.put("copy_code", SessionManager.getCopyCode());

					mapUpdate.put("section", Integer.parseInt(jsonObj.get("__index").toString()) + 1);

					mapUpdate.put("acc_year", String.valueOf(mapVo.get("acc_year")));

					mapUpdate.put("acc_month", String.valueOf(mapVo.get("acc_month")));

					mapUpdate.put("goal_code", String.valueOf(mapVo.get("goal_code")));
					
					mapUpdate.put("start_month", mapUpdate.get("acc_month"));
					mapUpdate.put("is_audit", 1);
					Long isAudit = prmGoalService.queryPrmGoalByAudit(mapUpdate);
					
					if(isAudit > 0){
						return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
					}

					mapUpdate.put("check_hos_id", String.valueOf(mapVo.get("check_hos_id")));

					mapUpdate.put("kpi_code", String.valueOf(mapVo.get("kpi_code")));

					mapUpdate.put("sec_code", String.valueOf(jsonObj.get("sec_code")));

					mapUpdate.put("kpi_beg_score", jsonObj.get("kpi_beg_score") == null || "".equals(jsonObj.get("kpi_beg_score").toString())?"0":jsonObj.get("kpi_beg_score").toString());

					mapUpdate.put("kpi_end_score", jsonObj.get("kpi_end_score") == null || "".equals(jsonObj.get("kpi_end_score").toString())?"0":jsonObj.get("kpi_end_score").toString());

					dataUpdatedBatch.add(mapUpdate);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}

		}
		String addBatchPrmHosKpiSectionJson = "";

		String updateBatchPrmHosKpiSectionJson = "";

		if (dataAddedBatch.size() > 0) {

			addBatchPrmHosKpiSectionJson = prmHosKpiLedService.addBatchPrmHosKpiLed(dataAddedBatch);
		}

		if (dataUpdatedBatch.size() > 0) {
			updateBatchPrmHosKpiSectionJson = prmHosKpiLedService.updateBatchPrmHosKpiLed(dataUpdatedBatch);
		}

		String StringJson = "";
		if (addBatchPrmHosKpiSectionJson != "") {

			StringJson = addBatchPrmHosKpiSectionJson;

		} else if (updateBatchPrmHosKpiSectionJson != "") {

			StringJson = updateBatchPrmHosKpiSectionJson;
		} else {

			StringJson = "{\"msg\":\"数据没变化.\",\"state\":\"true\"}";
		}

		return JSONObject.parseObject(StringJson);

	}

	/**
	 * @Description 删除数据 0203 医院绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/deletePrmHosKpiSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmHosKpiSection(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("goal_code", ids[5]);
			
			mapVo.put("start_month", mapVo.get("acc_month"));
			mapVo.put("is_audit", 1);
			Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
			
			if(isAudit > 0){
				return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
			}
			mapVo.put("kpi_code", ids[6]);
			mapVo.put("check_hos_id", ids[7]);
			mapVo.put("section", ids[8]);

			listVo.add(mapVo);

		}
		String prmHosSchemeJson = prmHosKpiSectionService.deleteBatchPrmHosKpiSection(listVo);

		return JSONObject.parseObject(prmHosSchemeJson);

	}

	/**
	 * @Description 删除数据 0203 指示灯设定表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/deletePrmHosKpiLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmHosKpiLed(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("goal_code", ids[5]);
			
			mapVo.put("start_month", mapVo.get("acc_month"));
			mapVo.put("is_audit", 1);
			Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
			
			if(isAudit > 0){
				return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
			}
			mapVo.put("kpi_code", ids[6]);
			mapVo.put("check_hos_id", ids[7]);
			mapVo.put("sec_code", ids[8]);

			listVo.add(mapVo);

		}
		String prmHosSchemeJson = prmHosKpiLedService.deleteBatchPrmHosKpiLed(listVo);

		return JSONObject.parseObject(prmHosSchemeJson);

	}

	/**
	 * @Description 加扣分法 评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/prmHosSchemesAd", method = RequestMethod.GET)
	public String prmHosSchemesAd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		PrmHosScheme prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmHosScheme.getHos_code());
		
		mode.addAttribute("hos_name", prmHosScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name",mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("check_hos_id", mapVo.get("check_hos_id"));
		
		mode.addAttribute("check_hos_code", prmHosScheme.getCheck_hos_code());

		mode.addAttribute("check_hos_name", prmHosScheme.getCheck_hos_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));
		
		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmhosscheme/prmHosKpiAdMain";

	}
	
	
	/**
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/queryPrmHosKpiAdByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiAdByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String prmHosKpiAd = prmHosKpiAdService.queryPrmHosKpiAd(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiAd);
	}
	

	/**
	 * @Description 加扣法
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/saveHosSchemeKpiAd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHosSchemeKpiAd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		List<Map<String, Object>> dataUpdatedBatch = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> dataAddedBatch = new ArrayList<Map<String, Object>>();// 存放明细

		String dataSection = String.valueOf(mapVo.get("dataSection"));

		JSONArray dataSectionJson = JSONArray.parseArray(dataSection);
		

		Iterator dataSectionJsonIt = dataSectionJson.iterator();

		while (dataSectionJsonIt.hasNext()) {

			JSONObject jsonObj = JSONObject.parseObject(dataSectionJsonIt.next().toString());

			try {

				/* ADD */
				if (((String) jsonObj.get("__status")).equals("add")) {

					Map<String, Object> mapAdd = new HashMap<String, Object>();

					mapAdd.put("group_id", SessionManager.getGroupId());

					mapAdd.put("hos_id", SessionManager.getHosId());

					mapAdd.put("copy_code", SessionManager.getCopyCode());

					mapAdd.put("acc_year", String.valueOf(mapVo.get("acc_year")));

					mapAdd.put("acc_month", String.valueOf(mapVo.get("acc_month")));

					mapAdd.put("goal_code", String.valueOf(mapVo.get("goal_code")));
					
					mapAdd.put("start_month", mapAdd.get("acc_month"));
					mapAdd.put("is_audit", 1);
					Long isAudit = prmGoalService.queryPrmGoalByAudit(mapAdd);
					
					if(isAudit > 0){
						return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
					}

					mapAdd.put("check_hos_id", String.valueOf(mapVo.get("check_hos_id")));

					mapAdd.put("kpi_code", String.valueOf(mapVo.get("kpi_code")));

					mapAdd.put("kpi_range_value", jsonObj.get("kpi_range_value") == null || "".equals(jsonObj.get("kpi_range_value").toString())?"0":jsonObj.get("kpi_range_value").toString());

					mapAdd.put("kpi_range_score", jsonObj.get("kpi_range_score") == null || "".equals(jsonObj.get("kpi_range_score").toString())?"0":jsonObj.get("kpi_range_score").toString());

					dataAddedBatch.add(mapAdd);

				}

				/* Update */
				if (((String) jsonObj.get("__status")).equals("update")) {

					Map<String, Object> mapUpdate = new HashMap<String, Object>();

					mapUpdate.put("group_id", SessionManager.getGroupId());

					mapUpdate.put("hos_id", SessionManager.getHosId());

					mapUpdate.put("copy_code", SessionManager.getCopyCode());

					mapUpdate.put("acc_year", String.valueOf(mapVo.get("acc_year")));

					mapUpdate.put("acc_month", String.valueOf(mapVo.get("acc_month")));

					mapUpdate.put("goal_code", String.valueOf(mapVo.get("goal_code")));
					
					mapUpdate.put("start_month", mapUpdate.get("acc_month"));
					mapUpdate.put("is_audit", 1);
					Long isAudit = prmGoalService.queryPrmGoalByAudit(mapUpdate);
					
					if(isAudit > 0){
						return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
					}

					mapUpdate.put("check_hos_id", Long.parseLong(mapVo.get("check_hos_id").toString()));

					mapUpdate.put("kpi_code", String.valueOf(mapVo.get("kpi_code")));

					mapUpdate.put("kpi_range_value", jsonObj.get("kpi_range_value") == null || "".equals(jsonObj.get("kpi_range_value").toString())?"0":jsonObj.get("kpi_range_value").toString());

					mapUpdate.put("kpi_range_score", jsonObj.get("kpi_range_score") == null || "".equals(jsonObj.get("kpi_range_score").toString())?"0":jsonObj.get("kpi_range_score").toString());

					dataUpdatedBatch.add(mapUpdate);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}

		}
		String addBatchPrmHosKpiAdServiceJson = "";

		String updateBatchPrmHosKpiAdServiceJson = "";

		if (dataAddedBatch.size() > 0) {

			addBatchPrmHosKpiAdServiceJson = prmHosKpiAdService.addBatchPrmHosKpiAd(dataAddedBatch);
		}

		if (dataUpdatedBatch.size() > 0) {
			updateBatchPrmHosKpiAdServiceJson = prmHosKpiAdService.updateBatchPrmHosKpiAd(dataUpdatedBatch);
		}

		String StringJson = "";
		if (addBatchPrmHosKpiAdServiceJson != "") {

			StringJson = addBatchPrmHosKpiAdServiceJson;

		} else if (updateBatchPrmHosKpiAdServiceJson != "") {

			StringJson = updateBatchPrmHosKpiAdServiceJson;
		} else {

			StringJson = "{\"msg\":\"数据没变化.\",\"state\":\"true\"}";
		}

		return JSONObject.parseObject(StringJson);

	}

	/**
	 * @Description 加扣法删除
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/deletePrmHosKpiKpiAd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmHosKpiKpiAd(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("goal_code", ids[5]);
			
			mapVo.put("start_month", mapVo.get("acc_month"));
			mapVo.put("is_audit", 1);
			Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
			
			if(isAudit > 0){
				return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
			}
			mapVo.put("kpi_code", ids[6]);
			mapVo.put("check_hos_id", ids[7]);

			listVo.add(mapVo);
		}

		String prmHosSchemeJson = prmHosKpiAdService.deleteBatchPrmHosKpiAd(listVo);

		return JSONObject.parseObject(prmHosSchemeJson);

	}

	/**
	 * @Description 以下是取值方法设定页面所用到的方法
	 * @param mode
	 * @return String
	 * @throws Exception
	 */

	@RequestMapping(value = "/hrp/prm/prmhosscheme/prmFormulaMethodHosScheme", method = RequestMethod.GET)
	public String prmFormulaMethodHosScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		PrmHosScheme prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name",
				mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("check_hos_id", mapVo.get("check_hos_id"));

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));
		
		mode.addAttribute("method_code", mapVo.get("method_code"));
		
		mode.addAttribute("method_name", mapVo.get("method_name"));
		
		mode.addAttribute("fun_code", prmHosScheme.getFun_code());
		
		mode.addAttribute("fun_name", prmHosScheme.getFun_name());
		
		mode.addAttribute("fun_method_chs", prmHosScheme.getFun_method_chs());
		
		mode.addAttribute("formula_code", prmHosScheme.getFormula_code());
		
		mode.addAttribute("formula_name", prmHosScheme.getFormula_name());
		
		mode.addAttribute("formula_method_chs", prmHosScheme.getFormula_method_chs());
		
		return "hrp/prm/prmhosscheme/prmHosKpiMethodAd";

	}

	/**
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/queryPrmHosLedByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosLedByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String prmHosScheme = prmLedService.queryPrmLedByEditerGrid(mapVo);

		return JSONObject.parseObject(prmHosScheme);
	}

	/**
	 * @Description 科室指示灯设置 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/prmHosSchemeKpiLed", method = RequestMethod.GET)
	public String prmHosSchemeKpiLed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		PrmHosScheme prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(mapVo);
		
		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmHosScheme.getHos_code());
		
		mode.addAttribute("hos_name", prmHosScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name",mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("check_hos_id", mapVo.get("check_hos_id"));
		
		mode.addAttribute("check_hos_code", prmHosScheme.getCheck_hos_code());
		
		mode.addAttribute("check_hos_name", prmHosScheme.getCheck_hos_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));
		
		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmhosscheme/prmHosKpiLedAdd";

	}

	/**
	 * @Description 查询数据科室设定表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/queryPrmHosKpiLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiLed(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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

		String prmHosKpiAd = prmHosKpiLedService.queryPrmHosKpiLed(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiAd);

	}

	@RequestMapping(value = "/hrp/prm/prmhosscheme/saveMethodFormulaFunHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMethodFormulaFunHosScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		List<Map<String, Object>> kpi_data_batch = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> stackList = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapKpi = new HashMap<String, Object>();

		mapKpi.put("group_id", mapVo.get("group_id"));

		mapKpi.put("hos_id", mapVo.get("hos_id"));

		mapKpi.put("copy_code", mapVo.get("copy_code"));

		mapKpi.put("acc_year", mapVo.get("acc_year"));

		mapKpi.put("acc_month", mapVo.get("acc_month"));

		mapKpi.put("goal_code", mapVo.get("goal_code"));
		
		mapKpi.put("start_month", mapKpi.get("acc_month"));
		mapKpi.put("is_audit", 1);
		Long isAudit = prmGoalService.queryPrmGoalByAudit(mapKpi);
		
		if(isAudit > 0){
			return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
		}

		mapKpi.put("kpi_code", mapVo.get("kpi_code"));

		mapKpi.put("check_hos_id", mapVo.get("check_hos_id"));
		
		mapKpi.put("ratio", mapVo.get("ratio"));

		mapKpi.put("goal_value", mapVo.get("goal_value"));
		

		String method_code = String.valueOf(mapVo.get("method_code"));

		if("01".equals(method_code)){
			mapKpi.put("method_code", mapVo.get("method_code"));

			mapKpi.put("formula_code", "");

			mapKpi.put("fun_code", "");
		}else if ("02".equals(method_code)) {

			mapKpi.put("method_code", mapVo.get("method_code"));

			mapKpi.put("formula_code", mapVo.get("formula_code"));

			mapKpi.put("fun_code", "");

		} else if ("03".equals(method_code)) {
			if(mapVo.containsKey("fun_stack_data")){
				if (!mapVo.get("fun_stack_data").toString().equals("undefined")) {
					prmHosFunStackService.deletePrmHosFunStack(mapKpi);
					List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("fun_stack_data").toString());
	
					for (Map<String, Object> detailVo : detail) {
	
						detailVo.put("group_id", mapVo.get("group_id"));
	
						detailVo.put("hos_id", mapVo.get("hos_id"));
	
						detailVo.put("copy_code", mapVo.get("copy_code"));
						
						detailVo.put("acc_year", mapVo.get("acc_year"));
	
						detailVo.put("acc_month", mapVo.get("acc_month"));
						
						detailVo.put("goal_code", mapVo.get("goal_code"));
						
						detailVo.put("kpi_code", mapVo.get("kpi_code"));
	
						detailVo.put("check_hos_id", mapVo.get("check_hos_id"));
	
						stackList.add(detailVo);
					}
					prmHosFunStackService.addBatchPrmHosFunStack(stackList);
				}
			}
			mapKpi.put("method_code", mapVo.get("method_code"));

			mapKpi.put("formula_code", "");

			mapKpi.put("fun_code", mapVo.get("fun_code"));
		}

		kpi_data_batch.add(mapKpi);

		String prmHosSchemeJson = prmHosSchemeService.updateBatchPrmHosScheme(kpi_data_batch);

		return JSONObject.parseObject(prmHosSchemeJson);
	}

	/**
	 * @Description 以下是引入的方法
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/introduceHosScheme", method = RequestMethod.GET)
	public String introduceHosScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("check_hos_id", mapVo.get("check_hos_id"));
		
		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		return "hrp/prm/prmhosscheme/prmIntroduceHosScheme";

	}

	@RequestMapping(value = "/hrp/prm/prmhosscheme/saveIntroduceHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveIntroduceHosScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("start_month", mapVo.get("acc_month"));
		mapVo.put("is_audit", 1);
		Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
		
		if(isAudit > 0){
			return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
		}

		String IntroduceHosScheme = prmHosSchemeService.saveIntroduceHosScheme(mapVo);

		return JSONObject.parseObject(IntroduceHosScheme);
	}

	/**
	 * @Description 查询树形菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosscheme/queryHosSchemeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosSchemeTree(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		String prmHosKpiObj = prmHosSchemeService.queryPrmHosKpiTree(mapVo);

		return JSONObject.parseObject(prmHosKpiObj);

	}
}
