/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.prm.entity.PrmDeptKpi;
import com.chd.hrp.prm.service.PrmDeptKpiService;

/**
 * 
 * @Description: 0302 科室绩效方案指标集表
 * @Table: PRM_DEPT_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptKpiController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptKpiController.class);

	// 引入Service服务
	@Resource(name = "prmDeptKpiService")
	private final PrmDeptKpiService prmDeptKpiService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpi/prmDeptKpiMainPage", method = RequestMethod.GET)
	public String prmDeptKpiMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkpi/prmDeptKpiMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpi/prmDeptKpiAddPage", method = RequestMethod.GET)
	public String prmDeptKpiAddPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkpi/prmDeptKpiAdd";

	}

	/**
	 * @Description 添加数据 0302 科室绩效指标集表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpi/addPrmDeptKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addprmDeptKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kpi_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kpi_name").toString()));
		mapVo.put("order_no", "");
		
		String prmDeptKpiJson = null;
		try {
			
			prmDeptKpiJson = prmDeptKpiService.addPrmDeptKpi(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			prmDeptKpiJson = e.getMessage();
		}
		
		return JSONObject.parseObject(prmDeptKpiJson);

	}


	// 返回用用于保存的默认值
	public Map<String, Object> DeptKpiValue(Map<String, Object> mapVo, JSONObject jSONObject) {

		Map<String, Object> map = new HashMap<String, Object>();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}

		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}

		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		map.put("goal_code", mapVo.get("goal_code"));
		map.put("kpi_code", jSONObject.get("kpi_code"));
		map.put("kpi_name", jSONObject.get("kpi_name"));
		map.put("nature_code", jSONObject.get("nature_code"));
		map.put("super_kpi_code", mapVo.get("super_kpi_code"));
		map.put("order_no", 0);
		map.put("kpi_level", 0);
		map.put("spell_code", StringTool.toPinyinShouZiMu((String) jSONObject.get("kpi_name")));
		map.put("wbx_code", StringTool.toWuBi((String) jSONObject.get("kpi_name")));
		map.put("is_last", 0);

		return map;
	}

	/**
	 * @Description 更新跳转页面 0302 科室绩效指标集表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpi/prmDeptKpiUpdatePage", method = RequestMethod.GET)
	public String prmDeptKpiUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		PrmDeptKpi pdk = prmDeptKpiService.queryPrmDeptKpiByCode(mapVo);

		mode.addAttribute("group_id", pdk.getGroup_id());
		mode.addAttribute("hos_id", pdk.getHos_id());
		mode.addAttribute("copy_code", pdk.getCopy_code());
		mode.addAttribute("acc_year", pdk.getAcc_year());
		mode.addAttribute("goal_code", pdk.getGoal_code());
		mode.addAttribute("kpi_code", pdk.getKpi_code());
		mode.addAttribute("kpi_name", pdk.getKpi_name());
		mode.addAttribute("dim_code", pdk.getDim_code());
		mode.addAttribute("dim_name", pdk.getDim_name());
		mode.addAttribute("nature_code", pdk.getNature_code());
		mode.addAttribute("is_last",pdk.getIs_last());
		mode.addAttribute("super_kpi_code", pdk.getSuper_kpi_code());
		mode.addAttribute("is_audit", pdk.getIs_audit());
		return "hrp/prm/prmdeptkpi/prmDeptKpiUpdate";
	}

	/**
	 * @Description 更新数据 0302 科室绩效指标集表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpi/updatePrmDeptKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmDeptKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kpi_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kpi_name").toString()));
		mapVo.put("order_no", "");
		
		String prmDeptKpiJson = null;
		
		try {
			
			prmDeptKpiJson = prmDeptKpiService.updatePrmDeptKpi(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			prmDeptKpiJson = e.getMessage();
		}

		return JSONObject.parseObject(prmDeptKpiJson);
	}

	/**
	 * @Description 删除数据 0302 科室绩效指标集表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpi/deletePrmDeptKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptKpi(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("goal_code", ids[4]);
			mapVo.put("kpi_code", ids[5]);
			listVo.add(mapVo);
		}
		
		String prmDeptKpiJson = null; 
		
		try {
			
			prmDeptKpiJson = prmDeptKpiService.deleteBatchPrmDeptKpi(listVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			prmDeptKpiJson = e.getMessage();
		}

		return JSONObject.parseObject(prmDeptKpiJson);

	}

	/**
	 * @Description 查询数据 0302 科室绩效指标集表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpi/queryPrmDeptKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		String prmDeptKpi = prmDeptKpiService.queryPrmDeptKpi(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpi);

	}

	/**
	 * @Description 查询数据 0202 科室绩效指标集表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpi/queryPrmDeptKpiTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String prmHosKpiObj = prmDeptKpiService.queryPrmDeptKpiTree(mapVo);

		return JSONObject.parseObject(prmHosKpiObj);

	}
	
	/**
	 * @Description 选择 批量保存指标
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpi/addBatchPrmDeptKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchPrmDeptKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String prmHosKpiJson = null;
		
		try {
			
			prmHosKpiJson = prmDeptKpiService.addBatchPrmDeptKpi(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			prmHosKpiJson = e.getMessage();
			
		}

		return JSONObject.parseObject(prmHosKpiJson);

	}

}
