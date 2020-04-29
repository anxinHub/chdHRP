
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
import com.chd.hrp.prm.entity.PrmDeptFormulaMethod;
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.service.PrmDeptFormuLaMethodService;
import com.chd.hrp.prm.service.PrmDeptFunStackService;
import com.chd.hrp.prm.service.PrmDeptKpiAdService;
import com.chd.hrp.prm.service.PrmDeptKpiLedService;
import com.chd.hrp.prm.service.PrmDeptKpiSectionService;
import com.chd.hrp.prm.service.PrmDeptSchemeService;
import com.chd.hrp.prm.service.PrmGoalService;
import com.chd.hrp.prm.service.PrmLedService;

/**
 * 
 * @Description: 0303 科室绩效方案表
 * @Table: PRM_DEPT_SCHEME
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptSchemeController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptSchemeController.class);

	// 引入Service服务
	@Resource(name = "prmDeptSchemeService")
	private final PrmDeptSchemeService prmDeptSchemeService = null;

	// 引入Service服务
	@Resource(name = "prmDeptKpiSectionService")
	private final PrmDeptKpiSectionService prmDeptKpiSectionService = null;

	// 引入Service服务
	@Resource(name = "prmDeptKpiAdService")
	private final PrmDeptKpiAdService prmDeptKpiAdService = null;

	@Resource(name = "prmLedService")
	private final PrmLedService prmLedService = null;

	@Resource(name = "prmDeptFunStackService")
	private final PrmDeptFunStackService prmDeptFunStackService = null;
	
	@Resource(name = "prmDeptKpiLedService")
	private final PrmDeptKpiLedService prmDeptKpiLedService = null;
	
	@Resource(name = "prmGoalService")
	private final PrmGoalService prmGoalService = null;
	
	@Resource(name = "prmDeptFormuLaMethodService")
	private final PrmDeptFormuLaMethodService prmDeptFormuLaMethodService = null;
	

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/prmDeptSchemeMainPage", method = RequestMethod.GET)
	public String prmDeptSchemeMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptscheme/prmDeptSchemeMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/prmDeptSchemeAddPage", method = RequestMethod.GET)
	public String prmDeptSchemeAddPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptscheme/prmDeptSchemeAdd";

	}

	/**
	 * @Description 添加数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/addPrmDeptScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String prmDeptSchemeJson = prmDeptSchemeService.addPrmDeptScheme(mapVo);

		return JSONObject.parseObject(prmDeptSchemeJson);

	}

	/**
	 * @Description 更新跳转页面 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/prmDeptSchemeUpdatePage", method = RequestMethod.GET)
	public String prmDeptSchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmDeptScheme prmDeptScheme = new PrmDeptScheme();

		prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeByCode(mapVo);

		mode.addAttribute("group_id", prmDeptScheme.getGroup_id());
		mode.addAttribute("hos_id", prmDeptScheme.getHos_id());
		mode.addAttribute("copy_code", prmDeptScheme.getCopy_code());
		mode.addAttribute("acc_year", prmDeptScheme.getAcc_year());
		mode.addAttribute("acc_month", prmDeptScheme.getAcc_month());
		mode.addAttribute("goal_code", prmDeptScheme.getGoal_code());
		mode.addAttribute("kpi_code", prmDeptScheme.getKpi_code());
		mode.addAttribute("dept_no", prmDeptScheme.getDept_no());
		mode.addAttribute("dept_id", prmDeptScheme.getDept_id());
		mode.addAttribute("super_kpi_code", prmDeptScheme.getSuper_kpi_code());
		mode.addAttribute("order_no", prmDeptScheme.getOrder_no());
		// mode.addAttribute("level", prmDeptScheme.getLevel());
		mode.addAttribute("is_last", prmDeptScheme.getIs_last());
		mode.addAttribute("ratio", prmDeptScheme.getRatio());
		mode.addAttribute("goal_value", prmDeptScheme.getGoal_value());
		mode.addAttribute("grade_meth_code", prmDeptScheme.getGrade_meth_code());
		mode.addAttribute("method_code", prmDeptScheme.getMethod_code());
		mode.addAttribute("formula_code", prmDeptScheme.getFormula_code());
		mode.addAttribute("fun_code", prmDeptScheme.getFun_code());

		return "hrp/prm/prmdeptscheme/prmDeptSchemeUpdate";
	}

	/**
	 * @Description 更新数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/updatePrmDeptScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmDeptSchemeJson = prmDeptSchemeService.updatePrmDeptScheme(mapVo);

		return JSONObject.parseObject(prmDeptSchemeJson);
	}

	/**
	 * @Description 删除数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/deletePrmDeptScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptScheme(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("dept_no", ids[7]);
			mapVo.put("dept_id", ids[8]);
			
			mapVo.put("is_audit", 1);
			Long isAudit = prmGoalService.queryPrmGoalByAudit(mapVo);
			
			if(isAudit > 0){
				return JSONObject.parseObject("{\"error\":\"该战略已经审核不能继续操作.\"}");
			}

			List<PrmDeptScheme> list = prmDeptSchemeService.queryPrmDeptSchemeBySuperKpiCode(mapVo);
			if (list.size() > 0) {
				boolean v_str = false;
				for(PrmDeptScheme prmDeptScheme : list){
					v_str = false;
					for (String i : str) {
						if(prmDeptScheme.getKpi_code().equals(i.split("@")[6])){
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
		prmDeptKpiAdService.deleteBatchPrmDeptKpiAd(listVo);
		prmDeptKpiSectionService.deleteBatchPrmDeptKpiSection(listVo);
		prmDeptKpiLedService.deleteBatchPrmDeptKpiLed(listVo);
		prmDeptFunStackService.deleteBatchPrmDeptFunStack(listVo);
		prmDeptFormuLaMethodService.deleteBatchDeptFormuLaMethod(listVo);
		String prmDeptSchemeJson = prmDeptSchemeService.deleteBatchPrmDeptScheme(listVo);

		return JSONObject.parseObject(prmDeptSchemeJson);

	}

	/**
	 * @Description 查询数据 0303 科室绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/queryPrmDeptScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeLeftName(getPage(mapVo));

		return JSONObject.parseObject(prmDeptScheme);

	}

	/**
	 * @Description 更新数据 0203生成页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/prmDeptSchemeCreate", method = RequestMethod.GET)
	public String prmHosSchemeCreate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/prm/prmdeptscheme/prmDeptSchemeCreate";

	}

	/**
	 * @Description 更新数据 0203 按照生成条件覆盖生成或者增量生成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/createPrmDeptScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String createPrmDeptScheme = prmDeptSchemeService.createPrmDeptScheme(mapVo);

		return JSONObject.parseObject(createPrmDeptScheme);
	}

	/**
	 * @Description 添加数据 0203 医院绩效方案表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/savePrmDeptScheme", method = RequestMethod.POST)
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

				mapKpi.put("dept_no", jsonObj.get("dept_no"));

				mapKpi.put("dept_id", jsonObj.get("dept_id"));

				mapKpi.put("ratio", jsonObj.get("ratio") == null  || "".equals(jsonObj.get("ratio").toString())?"0":jsonObj.get("ratio").toString());

				mapKpi.put("goal_value", jsonObj.get("goal_value") == null || "".equals(jsonObj.get("goal_value").toString())?"0":jsonObj.get("goal_value").toString());

				mapKpi.put("full_score", jsonObj.get("full_score") == null || "".equals(jsonObj.get("full_score").toString())?"0":jsonObj.get("full_score").toString());

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

		String prmDeptSchemeJson = prmDeptSchemeService.updateBatchPrmDeptScheme(kpi_data_batch);

		return JSONObject.parseObject(prmDeptSchemeJson);

	}

	/**
	 * @Description 科室区间法评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/prmDeptSchemeSection", method = RequestMethod.GET)
	public String prmDeptSchemeSection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		PrmDeptScheme prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeByCode(mapVo);

		//String deptKpiSection = prmDeptKpiSectionService.queryPrmDeptKpiSection(getPage(mapVo));

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmDeptScheme.getHos_code());

		mode.addAttribute("hos_name", prmDeptScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", prmDeptScheme.getAcc_year());

		mode.addAttribute("acc_month", prmDeptScheme.getAcc_month());

		mode.addAttribute("goal_code", prmDeptScheme.getGoal_code());

		mode.addAttribute("nature_name",prmDeptScheme.getNature_name());

		mode.addAttribute("kpi_code", prmDeptScheme.getKpi_code());

		mode.addAttribute("kpi_name", prmDeptScheme.getKpi_name());

		mode.addAttribute("dept_no", prmDeptScheme.getDept_no());

		mode.addAttribute("dept_id", prmDeptScheme.getDept_id());
		
		mode.addAttribute("dept_code", prmDeptScheme.getDept_code());

		mode.addAttribute("dept_name", prmDeptScheme.getDept_name());

		mode.addAttribute("ratio", prmDeptScheme.getRatio());

		mode.addAttribute("goal_value", prmDeptScheme.getGoal_value());

		//mode.addAttribute("deptKpiSection", deptKpiSection);
		
		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmdeptscheme/prmDeptKpiSectionMain";

	}

	/**
	 * @Description 计算公式评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/prmDeptSchemesFormuLa", method = RequestMethod.GET)
	public String prmDeptSchemesFormuLa(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		PrmDeptScheme prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeByCode(mapVo);

		//String deptKpiSection = prmDeptKpiSectionService.queryPrmDeptKpiSection(getPage(mapVo));

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmDeptScheme.getHos_code());

		mode.addAttribute("hos_name", prmDeptScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", prmDeptScheme.getAcc_year());

		mode.addAttribute("acc_month", prmDeptScheme.getAcc_month());

		mode.addAttribute("goal_code", prmDeptScheme.getGoal_code());

		mode.addAttribute("nature_name",prmDeptScheme.getNature_name());

		mode.addAttribute("kpi_code", prmDeptScheme.getKpi_code());

		mode.addAttribute("kpi_name", prmDeptScheme.getKpi_name());

		mode.addAttribute("dept_no", prmDeptScheme.getDept_no());

		mode.addAttribute("dept_id", prmDeptScheme.getDept_id());
		
		mode.addAttribute("dept_code", prmDeptScheme.getDept_code());

		mode.addAttribute("dept_name", prmDeptScheme.getDept_name());

		mode.addAttribute("ratio", prmDeptScheme.getRatio());

		mode.addAttribute("goal_value", prmDeptScheme.getGoal_value());

		//mode.addAttribute("deptKpiSection", deptKpiSection);
		
		mode.addAttribute("full_score", mapVo.get("full_score"));

		PrmDeptFormulaMethod formula_method_chs= prmDeptFormuLaMethodService.queryPrmDeptFormuLaMethod(mapVo);
		if(formula_method_chs!=null){
			mode.addAttribute("formula_method_chs",formula_method_chs.getFormula_method_chs());
	
		}
	
		return "hrp/prm/prmdeptscheme/prmDeptFormuLaSectionAdd";

	}
	//savePrmDeptFormula
	/**
	 * @Description 查询数据 评分方法区间法(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/queryPrmDeptSectionByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptSectionByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String deptKpiSection = prmDeptKpiSectionService.queryPrmDeptKpiSection(getPage(mapVo));

		return JSONObject.parseObject(deptKpiSection);
	}
	
	
	/**
	 * @Description 计算公式评分标准设定
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/savePrmDeptFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePrmDeptFormula(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str="";
		try {
			
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
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
			str=prmDeptFormuLaMethodService.savePrmDeptFormula(mapVo);
			
		} catch (Exception e) {
			e.printStackTrace();
			str=e.getMessage();
		}
		return JSONObject.parseObject(str);
	
		
	}
	/**
	 * @Description 区间法评分标准设定
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/saveDeptSchemeSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDeptSchemeSection(@RequestParam Map<String, Object> mapVo, Model mode)
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

					mapAdd.put("dept_no", String.valueOf(mapVo.get("dept_no")));

					mapAdd.put("dept_id", String.valueOf(mapVo.get("dept_id")));

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

					mapUpdate.put("dept_no", String.valueOf(mapVo.get("dept_no")));

					mapUpdate.put("dept_id", String.valueOf(mapVo.get("dept_id")));

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
		String addBatchPrmDeptKpiSectionJson = "";

		String updateBatchPrmDeptKpiSectionJson = "";

		if (dataAddedBatch.size() > 0) {
			addBatchPrmDeptKpiSectionJson = prmDeptKpiSectionService.addBatchPrmDeptKpiSection(dataAddedBatch);
		}

		if (dataUpdatedBatch.size() > 0) {
			updateBatchPrmDeptKpiSectionJson = prmDeptKpiSectionService.updateBatchPrmDeptKpiSection(dataUpdatedBatch);
		}

		String StringJson = "";
		if (addBatchPrmDeptKpiSectionJson != "") {

			StringJson = addBatchPrmDeptKpiSectionJson;

		} else if (updateBatchPrmDeptKpiSectionJson != "") {

			StringJson = updateBatchPrmDeptKpiSectionJson;
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
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/saveDeptKpiLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDeptKpiLed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

					mapAdd.put("dept_no", String.valueOf(mapVo.get("dept_no")));

					mapAdd.put("dept_id", String.valueOf(mapVo.get("dept_id")));

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

					mapUpdate.put("dept_no", String.valueOf(mapVo.get("dept_no")));

					mapUpdate.put("dept_id", String.valueOf(mapVo.get("dept_id")));

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
		String addBatchPrmDeptKpiSectionJson = "";

		String updateBatchPrmDeptKpiSectionJson = "";

		if (dataAddedBatch.size() > 0) {

			addBatchPrmDeptKpiSectionJson = prmDeptKpiLedService.addBatchPrmDeptKpiLed(dataAddedBatch);
		}

		if (dataUpdatedBatch.size() > 0) {
			
			updateBatchPrmDeptKpiSectionJson = prmDeptKpiLedService.updateBatchPrmDeptKpiLed(dataUpdatedBatch);
		}

		String StringJson = "";
		if (addBatchPrmDeptKpiSectionJson != "") {

			StringJson = addBatchPrmDeptKpiSectionJson;

		} else if (updateBatchPrmDeptKpiSectionJson != "") {

			StringJson = updateBatchPrmDeptKpiSectionJson;
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
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/deletePrmDeptKpiSection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptKpiSection(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("dept_no", ids[7]);
			mapVo.put("dept_id", ids[8]);
			mapVo.put("section", ids[9]);

			listVo.add(mapVo);

		}
		String prmDeptSchemeJson = prmDeptKpiSectionService.deleteBatchPrmDeptKpiSection(listVo);

		return JSONObject.parseObject(prmDeptSchemeJson);

	}

	/**
	 * @Description 删除数据 0203 指示灯设定表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/deletePrmDeptKpiLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptKpiLed(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("dept_no", ids[7]);
			mapVo.put("dept_id", ids[8]);
			mapVo.put("sec_code", ids[9]);

			listVo.add(mapVo);

		}
		String prmDeptSchemeJson = prmDeptKpiLedService.deleteBatchPrmDeptKpiLed(listVo);

		return JSONObject.parseObject(prmDeptSchemeJson);

	}

	/**
	 * @Description 加扣分法 评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/prmDeptSchemesAd", method = RequestMethod.GET)
	public String prmDeptSchemesAd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		PrmDeptScheme prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmDeptScheme.getHos_code());

		mode.addAttribute("hos_name", prmDeptScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name",mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("dept_no", mapVo.get("dept_no"));

		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		
		mode.addAttribute("dept_code", prmDeptScheme.getDept_code());

		mode.addAttribute("dept_name", prmDeptScheme.getDept_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));
		
		mode.addAttribute("full_score", mapVo.get("full_score"));

		//mode.addAttribute("prmDeptKpiAd", prmDeptKpiAd);

		return "hrp/prm/prmdeptscheme/prmDeptKpiAdMain";

	}
	
	
	/**
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/queryPrmDeptKpiAdByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiAdByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmDeptKpiAd = prmDeptKpiAdService.queryPrmDeptKpiAd(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiAd);
	}
	

	/**
	 * @Description 加扣法
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/saveDeptSchemeKpiAd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDeptSchemeKpiAd(@RequestParam Map<String, Object> mapVo, Model mode)
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

					mapAdd.put("dept_no", String.valueOf(mapVo.get("dept_no")));

					mapAdd.put("dept_id", String.valueOf(mapVo.get("dept_id")));

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

					mapUpdate.put("dept_no", Long.parseLong(mapVo.get("dept_no").toString()));

					mapUpdate.put("dept_id", Long.parseLong(mapVo.get("dept_id").toString()));

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
		String addBatchPrmDeptKpiAdServiceJson = "";

		String updateBatchPrmDeptKpiAdServiceJson = "";

		if (dataAddedBatch.size() > 0) {

			addBatchPrmDeptKpiAdServiceJson = prmDeptKpiAdService.addBatchPrmDeptKpiAd(dataAddedBatch);
		}

		if (dataUpdatedBatch.size() > 0) {
			updateBatchPrmDeptKpiAdServiceJson = prmDeptKpiAdService.updateBatchPrmDeptKpiAd(dataUpdatedBatch);
		}

		String StringJson = "";
		if (addBatchPrmDeptKpiAdServiceJson != "") {

			StringJson = addBatchPrmDeptKpiAdServiceJson;

		} else if (updateBatchPrmDeptKpiAdServiceJson != "") {

			StringJson = updateBatchPrmDeptKpiAdServiceJson;
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
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/deletePrmDeptKpiKpiAd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptKpiKpiAd(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("dept_no", ids[7]);
			mapVo.put("dept_id", ids[8]);

			listVo.add(mapVo);
		}

		String prmDeptSchemeJson = prmDeptKpiAdService.deleteBatchPrmDeptKpiAd(listVo);

		return JSONObject.parseObject(prmDeptSchemeJson);

	}

	/**
	 * @Description 以下是取值方法设定页面所用到的方法
	 * @param mode
	 * @return String
	 * @throws Exception
	 */

	@RequestMapping(value = "/hrp/prm/prmdeptscheme/prmFormulaMethodDeptScheme", method = RequestMethod.GET)
	public String prmFormulaMethodDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		PrmDeptScheme prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmDeptScheme.getHos_code());

		mode.addAttribute("hos_name", prmDeptScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name",mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("dept_no", mapVo.get("dept_no"));

		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		
		mode.addAttribute("dept_code", prmDeptScheme.getDept_code());

		mode.addAttribute("dept_name", prmDeptScheme.getDept_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));
		
		mode.addAttribute("method_code", mapVo.get("method_code"));
		
		mode.addAttribute("method_name", mapVo.get("method_name"));
		
		mode.addAttribute("fun_code", prmDeptScheme.getFun_code());
		
		mode.addAttribute("fun_name", prmDeptScheme.getFun_name());
		
		mode.addAttribute("fun_method_chs", prmDeptScheme.getFun_method_chs());
		
		mode.addAttribute("formula_code", prmDeptScheme.getFormula_code());
		
		mode.addAttribute("formula_name", prmDeptScheme.getFormula_name());
		
		mode.addAttribute("formula_method_chs", prmDeptScheme.getFormula_method_chs());
		
		return "hrp/prm/prmdeptscheme/prmDeptKpiMethodAd";

	}

	/**
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/queryPrmDeptLedByEditerGrid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptLedByEditerGrid(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmDeptScheme = prmLedService.queryPrmLedByEditerGrid(mapVo);

		return JSONObject.parseObject(prmDeptScheme);
	}

	/**
	 * @Description 科室指示灯设置 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/prmDeptSchemeKpiLed", method = RequestMethod.GET)
	public String prmDeptSchemeKpiLed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		//String prmDeptKpiAd = prmDeptKpiLedService.queryPrmDeptKpiLed(getPage(mapVo));
		
		PrmDeptScheme prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeByCode(mapVo);

		mode.addAttribute("group_id", prmDeptScheme.getGroup_id());

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("hos_code", prmDeptScheme.getHos_code());

		mode.addAttribute("hos_name", prmDeptScheme.getHos_name());

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("nature_name",mapVo.get("nature_name"));

		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));

		mode.addAttribute("kpi_name", mapVo.get("kpi_name"));

		mode.addAttribute("dept_no", mapVo.get("dept_no"));

		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		
		mode.addAttribute("dept_code", prmDeptScheme.getDept_code());

		mode.addAttribute("dept_name", prmDeptScheme.getDept_name());

		mode.addAttribute("ratio", mapVo.get("ratio"));

		mode.addAttribute("goal_value", mapVo.get("goal_value"));
		
		mode.addAttribute("full_score", mapVo.get("full_score"));

		//mode.addAttribute("deptKpiSection", prmDeptKpiAd);

		return "hrp/prm/prmdeptscheme/prmDeptKpiLedAdd";

	}

	/**
	 * @Description 查询数据科室设定表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/queryPrmDeptKpiLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiLed(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmDeptKpiAd = prmDeptKpiLedService.queryPrmDeptKpiLed(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiAd);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptscheme/saveMethodFormulaFunDeptScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMethodFormulaFunDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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

		mapKpi.put("dept_no", mapVo.get("dept_no"));

		mapKpi.put("dept_id", mapVo.get("dept_id"));
		
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
					prmDeptFunStackService.deletePrmDeptFunStack(mapKpi);
					List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("fun_stack_data").toString());

					for (Map<String, Object> detailVo : detail) {

						detailVo.put("group_id", mapVo.get("group_id"));

						detailVo.put("hos_id", mapVo.get("hos_id"));

						detailVo.put("copy_code", mapVo.get("copy_code"));
						
						detailVo.put("acc_year", mapVo.get("acc_year"));

						detailVo.put("acc_month", mapVo.get("acc_month"));
						
						detailVo.put("goal_code", mapVo.get("goal_code"));
						
						detailVo.put("kpi_code", mapVo.get("kpi_code"));

						detailVo.put("dept_no", mapVo.get("dept_no"));

						detailVo.put("dept_id", mapVo.get("dept_id"));

						stackList.add(detailVo);
					}
					prmDeptFunStackService.addBatchPrmDeptFunStack(stackList);
				}
			}
			mapKpi.put("method_code", mapVo.get("method_code"));

			mapKpi.put("formula_code", "");

			mapKpi.put("fun_code", mapVo.get("fun_code"));
		}

		kpi_data_batch.add(mapKpi);

		String prmDeptSchemeJson = prmDeptSchemeService.updateBatchPrmDeptScheme(kpi_data_batch);

		return JSONObject.parseObject(prmDeptSchemeJson);
	}

	/**
	 * @Description 以下是引入的方法
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/introduceDeptScheme", method = RequestMethod.GET)
	public String introduceHosScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("dept_no", mapVo.get("dept_no"));

		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		
		mode.addAttribute("goal_code", mapVo.get("goal_code"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("dept_text", mapVo.get("dept_text"));

		return "hrp/prm/prmdeptscheme/prmIntroduceDeptScheme";

	}

	@RequestMapping(value = "/hrp/prm/prmdeptscheme/saveIntroduceDeptScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveIntroduceDeptScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String IntroduceDeptScheme = prmDeptSchemeService.saveIntroduceDeptScheme(mapVo);

		return JSONObject.parseObject(IntroduceDeptScheme);
	}

	/**
	 * @Description 查询树形菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptscheme/queryDeptSchemeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptSchemeTree(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmHosKpiObj = prmDeptSchemeService.queryPrmHosKpiTree(mapVo);

		return JSONObject.parseObject(prmHosKpiObj);

	}
}
