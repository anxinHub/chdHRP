
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
import com.chd.hrp.prm.entity.PrmEmpScheme;
import com.chd.hrp.prm.service.PrmEmpFunStackService;
import com.chd.hrp.prm.service.PrmEmpKpiAdService;
import com.chd.hrp.prm.service.PrmEmpKpiLedService;
import com.chd.hrp.prm.service.PrmEmpKpiSectionService;
import com.chd.hrp.prm.service.PrmEmpSchemeService;
import com.chd.hrp.prm.service.PrmGoalService;
import com.chd.hrp.prm.service.PrmLedService;

/**
 * 
 * @Description:
 * 0403 职工绩效方案表
 * @Table:
 * PRM_EMP_SCHEME
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Controller
public class PrmEmpSchemeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(PrmEmpSchemeController.class);

	// 引入Service服务
	@Resource(name = "prmEmpSchemeService")
	private final PrmEmpSchemeService prmEmpSchemeService = null;

	// 引入Service服务
	@Resource(name = "prmEmpKpiSectionService")
	private final PrmEmpKpiSectionService prmEmpKpiSectionService = null;

	// 引入Service服务
	@Resource(name = "prmEmpKpiAdService")
	private final PrmEmpKpiAdService prmEmpKpiAdService = null;

	@Resource(name = "prmLedService")
	private final PrmLedService prmLedService = null;

	@Resource(name = "prmEmpKpiLedService")
	private final PrmEmpKpiLedService prmEmpKpiLedService = null;
	
	@Resource(name = "prmEmpFunStackService")
	private final PrmEmpFunStackService prmEmpFunStackService = null;
	
	@Resource(name = "prmGoalService")
	private final PrmGoalService prmGoalService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/prmEmpSchemeMainPage", method = RequestMethod.GET)
	public String prmEmpSchemeMainPage(Model mode) throws Exception {

		return "hrp/prm/prmempscheme/prmEmpSchemeMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/prmEmpSchemeAddPage", method = RequestMethod.GET)
	public String prmEmpSchemeAddPage(Model mode) throws Exception {

		return "hrp/prm/prmempscheme/prmEmpSchemeAdd";

	}

	/**
	 * @Description 添加数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/addPrmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String prmEmpSchemeJson = prmEmpSchemeService.addPrmEmpScheme(mapVo);

		return JSONObject.parseObject(prmEmpSchemeJson);

	}

	/**
	 * @Description 更新跳转页面 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/prmEmpSchemeUpdatePage", method = RequestMethod.GET)
	public String prmEmpSchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmEmpScheme prmEmpScheme = new PrmEmpScheme();

		prmEmpScheme = prmEmpSchemeService.queryPrmEmpSchemeByCode(mapVo);

		mode.addAttribute("group_id", prmEmpScheme.getGroup_id());
		mode.addAttribute("hos_id", prmEmpScheme.getHos_id());
		mode.addAttribute("copy_code", prmEmpScheme.getCopy_code());
		mode.addAttribute("acc_year", prmEmpScheme.getAcc_year());
		mode.addAttribute("acc_month", prmEmpScheme.getAcc_month());
		mode.addAttribute("goal_code", prmEmpScheme.getGoal_code());
		mode.addAttribute("kpi_code", prmEmpScheme.getKpi_code());
		mode.addAttribute("emp_no", prmEmpScheme.getEmp_no());
		mode.addAttribute("emp_id", prmEmpScheme.getEmp_id());
		mode.addAttribute("super_kpi_code", prmEmpScheme.getSuper_kpi_code());
		mode.addAttribute("order_no", prmEmpScheme.getOrder_no());
		// mode.addAttribute("level", prmEmpScheme.getLevel());
		mode.addAttribute("is_last", prmEmpScheme.getIs_last());
		mode.addAttribute("ratio", prmEmpScheme.getRatio());
		mode.addAttribute("goal_value", prmEmpScheme.getGoal_value());
		mode.addAttribute("grade_meth_code", prmEmpScheme.getGrade_meth_code());
		mode.addAttribute("method_code", prmEmpScheme.getMethod_code());
		mode.addAttribute("formula_code", prmEmpScheme.getFormula_code());
		mode.addAttribute("fun_code", prmEmpScheme.getFun_code());

		return "hrp/prm/prmempscheme/prmEmpSchemeUpdate";
	}

	/**
	 * @Description 更新数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/updatePrmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmEmpSchemeJson = prmEmpSchemeService.updatePrmEmpScheme(mapVo);

		return JSONObject.parseObject(prmEmpSchemeJson);
	}

	/**
	 * @Description 删除数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/deletePrmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmEmpScheme(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("kpi_code", ids[6]);
			mapVo.put("emp_no", ids[7]);
			mapVo.put("emp_id", ids[8]);
			
			mapVo.put("is_audit", 1);
			Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
			
			if(isAudit > 0){
				return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
			}

			List<PrmEmpScheme> list = prmEmpSchemeService.queryPrmEmpSchemeBySuperKpiCode(mapVo);
			if (list.size() > 0) {
				boolean v_str = false;
				for(PrmEmpScheme prmEmpScheme : list){
					v_str = false;
					for (String i : str) {
						if(prmEmpScheme.getKpi_code().equals(i.split("@")[6])){
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
		prmEmpKpiAdService.deleteBatchPrmEmpKpiAd(listVo);
		prmEmpKpiSectionService.deleteBatchPrmEmpKpiSection(listVo);
		prmEmpKpiLedService.deleteBatchPrmEmpKpiLed(listVo);
		prmEmpFunStackService.deleteBatchPrmEmpFunStack(listVo);
		String prmEmpSchemeJson = prmEmpSchemeService.deleteBatchPrmEmpScheme(listVo);

		return JSONObject.parseObject(prmEmpSchemeJson);

	}

	/**
	 * @Description 查询数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/queryPrmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmEmpScheme = prmEmpSchemeService.queryPrmEmpSchemeLeftName(getPage(mapVo));

		return JSONObject.parseObject(prmEmpScheme);

	}

	/**
	 * @Description 更新数据 0203生成页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/prmEmpSchemeCreate", method = RequestMethod.GET)
	public String prmHosSchemeCreate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/prm/prmempscheme/prmEmpSchemeCreate";

	}

	/**
	 * @Description 更新数据 0203 按照生成条件覆盖生成或者增量生成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/createPrmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String createPrmEmpScheme = prmEmpSchemeService.createPrmEmpScheme(mapVo);

		return JSONObject.parseObject(createPrmEmpScheme);
	}

	/**
	 * @Description 添加数据 0203 医院绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/savePrmEmpScheme", method = RequestMethod.POST)
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

				mapKpi.put("emp_no", jsonObj.get("emp_no"));

				mapKpi.put("emp_id", jsonObj.get("emp_id"));

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

		String prmEmpSchemeJson = prmEmpSchemeService.updateBatchPrmEmpScheme(kpi_data_batch);

		return JSONObject.parseObject(prmEmpSchemeJson);

	}

	/**
	 * @Description 科室区间法评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/prmEmpSchemeSection", method = RequestMethod.GET)
	public String prmEmpSchemeSection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		PrmEmpScheme prmEmpScheme = prmEmpSchemeService.queryPrmEmpSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmEmpScheme.getHos_code());
		
		mode.addAttribute("hos_name", prmEmpScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", prmEmpScheme.getAcc_year());

		mode.addAttribute("acc_month", prmEmpScheme.getAcc_month());

		mode.addAttribute("goal_code", prmEmpScheme.getGoal_code());

		mode.addAttribute("nature_name",prmEmpScheme.getNature_name());

		mode.addAttribute("kpi_code", prmEmpScheme.getKpi_code());

		mode.addAttribute("kpi_name", prmEmpScheme.getKpi_name());

		mode.addAttribute("emp_no", prmEmpScheme.getEmp_no());

		mode.addAttribute("emp_id", prmEmpScheme.getEmp_id());
		
		mode.addAttribute("emp_code", prmEmpScheme.getEmp_code());

		mode.addAttribute("emp_name", prmEmpScheme.getEmp_name());

		mode.addAttribute("ratio", prmEmpScheme.getRatio());

		mode.addAttribute("goal_value", prmEmpScheme.getGoal_value());

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmempscheme/prmEmpKpiSectionMain";

	}
	
	
	/**
	 * @Description 查询数据 评分方法区间法(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/queryPrmEmpSectionByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpSectionByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String EmpKpiSection = prmEmpKpiSectionService.queryPrmEmpKpiSection(getPage(mapVo));

		return JSONObject.parseObject(EmpKpiSection);
	}
	
	

	/**
	 * @Description 区间法评分标准设定
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/saveEmpSchemeSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveEmpSchemeSection(@RequestParam Map<String, Object> mapVo, Model mode)
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

					mapAdd.put("emp_no", String.valueOf(mapVo.get("emp_no")));

					mapAdd.put("emp_id", String.valueOf(mapVo.get("emp_id")));

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

					mapUpdate.put("emp_no", String.valueOf(mapVo.get("emp_no")));

					mapUpdate.put("emp_id", String.valueOf(mapVo.get("emp_id")));

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
		String addBatchPrmEmpKpiSectionJson = "";

		String updateBatchPrmEmpKpiSectionJson = "";

		if (dataAddedBatch.size() > 0) {

			addBatchPrmEmpKpiSectionJson = prmEmpKpiSectionService.addBatchPrmEmpKpiSection(dataAddedBatch);
		}

		if (dataUpdatedBatch.size() > 0) {
			updateBatchPrmEmpKpiSectionJson = prmEmpKpiSectionService.updateBatchPrmEmpKpiSection(dataUpdatedBatch);
		}

		String StringJson = "";
		if (addBatchPrmEmpKpiSectionJson != "") {

			StringJson = addBatchPrmEmpKpiSectionJson;

		} else if (updateBatchPrmEmpKpiSectionJson != "") {

			StringJson = updateBatchPrmEmpKpiSectionJson;
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
	@RequestMapping(value = "/hrp/prm/prmempscheme/saveEmpKpiLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveEmpKpiLed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

					mapAdd.put("emp_no", String.valueOf(mapVo.get("emp_no")));

					mapAdd.put("emp_id", String.valueOf(mapVo.get("emp_id")));

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

					mapUpdate.put("emp_no", String.valueOf(mapVo.get("emp_no")));

					mapUpdate.put("emp_id", String.valueOf(mapVo.get("emp_id")));

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
		String addBatchPrmEmpKpiSectionJson = "";

		String updateBatchPrmEmpKpiSectionJson = "";

		if (dataAddedBatch.size() > 0) {

			addBatchPrmEmpKpiSectionJson = prmEmpKpiLedService.addBatchPrmEmpKpiLed(dataAddedBatch);
		}

		if (dataUpdatedBatch.size() > 0) {
			updateBatchPrmEmpKpiSectionJson = prmEmpKpiLedService.updateBatchPrmEmpKpiLed(dataUpdatedBatch);
		}

		String StringJson = "";
		if (addBatchPrmEmpKpiSectionJson != "") {

			StringJson = addBatchPrmEmpKpiSectionJson;

		} else if (updateBatchPrmEmpKpiSectionJson != "") {

			StringJson = updateBatchPrmEmpKpiSectionJson;
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
	@RequestMapping(value = "/hrp/prm/prmempscheme/deletePrmEmpKpiSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmEmpKpiSection(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("emp_no", ids[7]);
			mapVo.put("emp_id", ids[8]);
			mapVo.put("section", ids[9]);

			listVo.add(mapVo);

		}
		String prmEmpSchemeJson = prmEmpKpiSectionService.deleteBatchPrmEmpKpiSection(listVo);

		return JSONObject.parseObject(prmEmpSchemeJson);

	}

	/**
	 * @Description 删除数据 0203 指示灯设定表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/deletePrmEmpKpiLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmEmpKpiLed(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("emp_no", ids[7]);
			mapVo.put("emp_id", ids[8]);
			mapVo.put("sec_code", ids[9]);

			listVo.add(mapVo);

		}
		String prmEmpSchemeJson = prmEmpKpiLedService.deleteBatchPrmEmpKpiLed(listVo);

		return JSONObject.parseObject(prmEmpSchemeJson);

	}

	/**
	 * @Description 加扣分法 评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/prmEmpSchemesAd", method = RequestMethod.GET)
	public String prmEmpSchemesAd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		//String prmEmpKpiAd = prmEmpKpiAdService.queryPrmEmpKpiAd(getPage(mapVo));
	
		PrmEmpScheme prmEmpScheme = prmEmpSchemeService.queryPrmEmpSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmEmpScheme.getHos_code());
		
		mode.addAttribute("hos_name", prmEmpScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name",mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("emp_no", mapVo.get("emp_no"));

		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		
		mode.addAttribute("emp_code", prmEmpScheme.getEmp_code());

		mode.addAttribute("emp_name", prmEmpScheme.getEmp_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));
		
		mode.addAttribute("full_score", mapVo.get("full_score"));

		//mode.addAttribute("prmEmpKpiAd", prmEmpKpiAd);

		return "hrp/prm/prmempscheme/prmEmpKpiAdMain";

	}
	
	
	/**
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/queryPrmEmpKpiAdByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiAdByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmEmpKpiAd = prmEmpKpiAdService.queryPrmEmpKpiAd(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiAd);
	}
	

	/**
	 * @Description 加扣法
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/saveEmpSchemeKpiAd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveEmpSchemeKpiAd(@RequestParam Map<String, Object> mapVo, Model mode)
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

					mapAdd.put("emp_no", String.valueOf(mapVo.get("emp_no")));

					mapAdd.put("emp_id", String.valueOf(mapVo.get("emp_id")));

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

					mapUpdate.put("emp_no", Long.parseLong(mapVo.get("emp_no").toString()));

					mapUpdate.put("emp_id", Long.parseLong(mapVo.get("emp_id").toString()));

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
		String addBatchPrmEmpKpiAdServiceJson = "";

		String updateBatchPrmEmpKpiAdServiceJson = "";

		if (dataAddedBatch.size() > 0) {

			addBatchPrmEmpKpiAdServiceJson = prmEmpKpiAdService.addBatchPrmEmpKpiAd(dataAddedBatch);
		}

		if (dataUpdatedBatch.size() > 0) {
			updateBatchPrmEmpKpiAdServiceJson = prmEmpKpiAdService.updateBatchPrmEmpKpiAd(dataUpdatedBatch);
		}

		String StringJson = "";
		if (addBatchPrmEmpKpiAdServiceJson != "") {

			StringJson = addBatchPrmEmpKpiAdServiceJson;

		} else if (updateBatchPrmEmpKpiAdServiceJson != "") {

			StringJson = updateBatchPrmEmpKpiAdServiceJson;
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
	@RequestMapping(value = "/hrp/prm/prmempscheme/deletePrmEmpKpiKpiAd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmEmpKpiKpiAd(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("emp_no", ids[7]);
			mapVo.put("emp_id", ids[8]);

			listVo.add(mapVo);
		}

		String prmEmpSchemeJson = prmEmpKpiAdService.deleteBatchPrmEmpKpiAd(listVo);

		return JSONObject.parseObject(prmEmpSchemeJson);

	}

	/**
	 * @Description 以下是取值方法设定页面所用到的方法
	 * @param mode
	 * @return String
	 * @throws Exception
	 */

	@RequestMapping(value = "/hrp/prm/prmempscheme/prmFormulaMethodEmpScheme", method = RequestMethod.GET)
	public String prmFormulaMethodEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		mode.addAttribute("emp_no", mapVo.get("emp_no"));

		mode.addAttribute("emp_id", mapVo.get("emp_id"));

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));
		
		mode.addAttribute("method_code", mapVo.get("method_code"));
		
		mode.addAttribute("method_name", mapVo.get("method_name"));
		
		mode.addAttribute("fun_code", mapVo.get("fun_code"));
		
		mode.addAttribute("fun_name", mapVo.get("fun_name"));
		
		mode.addAttribute("fun_method_chs", mapVo.get("fun_method_chs"));
		
		mode.addAttribute("formula_code", mapVo.get("formula_code"));
		
		mode.addAttribute("formula_name", mapVo.get("formula_name"));
		
		mode.addAttribute("formula_method_chs", mapVo.get("formula_method_chs"));
		
		return "hrp/prm/prmempscheme/prmEmpKpiMethodAd";

	}

	/**
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/queryPrmEmpLedByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpLedByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmEmpScheme = prmLedService.queryPrmLedByEditerGrid(mapVo);

		return JSONObject.parseObject(prmEmpScheme);
	}

	/**
	 * @Description 科室指示灯设置 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/prmEmpSchemeKpiLed", method = RequestMethod.GET)
	public String prmEmpSchemeKpiLed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		//String prmEmpKpiAd = prmEmpKpiLedService.queryPrmEmpKpiLed(getPage(mapVo));
		
		PrmEmpScheme prmEmpScheme = prmEmpSchemeService.queryPrmEmpSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmEmpScheme.getHos_code());
		
		mode.addAttribute("hos_name", prmEmpScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name",mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("emp_no", mapVo.get("emp_no"));

		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		
		mode.addAttribute("emp_code", prmEmpScheme.getEmp_code());

		mode.addAttribute("emp_name", prmEmpScheme.getEmp_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));
		
		mode.addAttribute("full_score", mapVo.get("full_score"));

		//mode.addAttribute("EmpKpiSection", prmEmpKpiAd);

		return "hrp/prm/prmempscheme/prmEmpKpiLedAdd";

	}

	/**
	 * @Description 查询数据科室设定表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/queryPrmEmpKpiLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiLed(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmEmpKpiAd = prmEmpKpiLedService.queryPrmEmpKpiLed(getPage(mapVo));

		return JSONObject.parseObject(prmEmpKpiAd);

	}

	@RequestMapping(value = "/hrp/prm/prmempscheme/saveMethodFormulaFunEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMethodFormulaFunEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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

		mapKpi.put("emp_no", mapVo.get("emp_no"));

		mapKpi.put("emp_id", mapVo.get("emp_id"));
		
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
					prmEmpFunStackService.deletePrmEmpFunStack(mapKpi);
					List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("fun_stack_data").toString());
	
					for (Map<String, Object> detailVo : detail) {
	
						detailVo.put("group_id", mapVo.get("group_id"));
	
						detailVo.put("hos_id", mapVo.get("hos_id"));
	
						detailVo.put("copy_code", mapVo.get("copy_code"));
						
						detailVo.put("acc_year", mapVo.get("acc_year"));
	
						detailVo.put("acc_month", mapVo.get("acc_month"));
						
						detailVo.put("goal_code", mapVo.get("goal_code"));
						
						detailVo.put("kpi_code", mapVo.get("kpi_code"));
	
						detailVo.put("emp_no", mapVo.get("emp_no"));
	
						detailVo.put("emp_id", mapVo.get("emp_id"));
	
						stackList.add(detailVo);
					}
					prmEmpFunStackService.addBatchPrmEmpFunStack(stackList);
				}
			}
			mapKpi.put("method_code", mapVo.get("method_code"));

			mapKpi.put("formula_code", "");

			mapKpi.put("fun_code", mapVo.get("fun_code"));
		}

		kpi_data_batch.add(mapKpi);

		String prmEmpSchemeJson = prmEmpSchemeService.updateBatchPrmEmpScheme(kpi_data_batch);

		return JSONObject.parseObject(prmEmpSchemeJson);
	}

	/**
	 * @Description 以下是引入的方法
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/introduceEmpScheme", method = RequestMethod.GET)
	public String introduceHosScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("emp_no", mapVo.get("emp_no"));

		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		
		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		return "hrp/prm/prmempscheme/prmIntroduceEmpScheme";

	}

	@RequestMapping(value = "/hrp/prm/prmempscheme/saveIntroduceEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveIntroduceEmpScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String IntroduceEmpScheme = prmEmpSchemeService.saveIntroduceEmpScheme(mapVo);

		return JSONObject.parseObject(IntroduceEmpScheme);
	}

	/**
	 * @Description 查询树形菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempscheme/queryEmpSchemeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpSchemeTree(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmHosKpiObj = prmEmpSchemeService.queryPrmHosKpiTree(mapVo);

		return JSONObject.parseObject(prmHosKpiObj);

	}
}

