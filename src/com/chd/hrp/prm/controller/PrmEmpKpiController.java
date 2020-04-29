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
import com.chd.hrp.prm.entity.PrmEmpKpi;
import com.chd.hrp.prm.service.PrmEmpKpiService;
import com.chd.hrp.prm.service.PrmEmpSchemeService;

/**
 * 
 * @Description: 0402 职工绩效指标表
 * @Table: PRM_EMP_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmEmpKpiController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmEmpKpiController.class);

	// 引入Service服务
	@Resource(name = "prmEmpKpiService")
	private final PrmEmpKpiService prmEmpKpiService = null;
	
	
	// 引入Service服务
		@Resource(name = "prmEmpSchemeService")
		private final PrmEmpSchemeService prmEmpSchemeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpi/prmEmpKpiMainPage", method = RequestMethod.GET)
	public String prmEmpKpiMainPage(Model mode) throws Exception {

		return "hrp/prm/prmempkpi/prmEmpKpiMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpi/prmEmpKpiAddPage", method = RequestMethod.GET)
	public String prmEmpKpiAddPage(Model mode) throws Exception {

		return "hrp/prm/prmempkpi/prmEmpKpiAdd";

	}

	/**
	 * @Description 添加数据 0402 职工绩效指标表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpi/addPrmEmpKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmEmpKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kpi_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kpi_name").toString()));
		mapVo.put("order_no", "");
		
		String prmEmpKpiJson = null;
		try {
			
			prmEmpKpiJson = prmEmpKpiService.addPrmEmpKpi(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			prmEmpKpiJson = e.getMessage();
		}
		
		return JSONObject.parseObject(prmEmpKpiJson);

	}
	
	/**
	 * @Description 添加数据 0402 职工绩效指标表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpi/addBatchPrmEmpKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchPrmEmpKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String prmEmpKpiJson = null;
		try {
			
			prmEmpKpiJson = prmEmpKpiService.addBatchPrmEmpKpi(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			prmEmpKpiJson = e.getMessage();
		}
		
		return JSONObject.parseObject(prmEmpKpiJson);

	}
	
	/**
	 * @Description 更新跳转页面 0402 职工绩效指标表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpi/prmEmpKpiUpdatePage", method = RequestMethod.GET)
	public String prmEmpKpiUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		PrmEmpKpi pek = prmEmpKpiService.queryPrmEmpKpiByCode(mapVo);
		
		mode.addAttribute("group_id", pek.getGroup_id());
		mode.addAttribute("hos_id", pek.getHos_id());
		mode.addAttribute("copy_code", pek.getCopy_code());
		mode.addAttribute("acc_year", pek.getAcc_year());
		mode.addAttribute("goal_code", pek.getGoal_code());
		mode.addAttribute("kpi_code", pek.getKpi_code());
		mode.addAttribute("kpi_name", pek.getKpi_name());
		mode.addAttribute("dept_code", pek.getDept_id()+"."+pek.getDept_no());
		mode.addAttribute("dim_code", pek.getDim_code());
		mode.addAttribute("dim_name", pek.getDim_name());
		mode.addAttribute("nature_code", pek.getNature_code());
		mode.addAttribute("is_last",pek.getIs_last());
		mode.addAttribute("super_kpi_code", pek.getSuper_kpi_code());
		mode.addAttribute("is_audit", pek.getIs_audit());

		return "hrp/prm/prmempkpi/prmEmpKpiUpdate";
	}

	/**
	 * @Description 更新数据 0402 职工绩效指标表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpi/updatePrmEmpKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmEmpKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String prmEmpKpiJson = null;
		
		try {
			
			prmEmpKpiJson = prmEmpKpiService.updatePrmEmpKpi(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			prmEmpKpiJson = e.getMessage();
		}

		return JSONObject.parseObject(prmEmpKpiJson) ;
	}

	/**
	 * @Description 删除数据 0402 职工绩效指标表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpi/deletePrmEmpKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmEmpKpi(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("dept_no", ids[5]);
			mapVo.put("dept_id", ids[6]);
			mapVo.put("kpi_code", ids[7]);
			
			listVo.add(mapVo);

		}
		
		String prmEmpKpiJson = null;
		
		try {
			
			prmEmpKpiJson = prmEmpKpiService.deleteBatchPrmEmpKpi(listVo);

		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			prmEmpKpiJson = e.getMessage();
		}
		
		return JSONObject.parseObject(prmEmpKpiJson);

	}

	/**
	 * @Description 查询数据 0402 职工绩效指标表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpi/queryPrmEmpKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String prmEmpKpiJson = prmEmpKpiService.queryPrmEmpKpi(getPage(mapVo));
		
		return JSONObject.parseObject(prmEmpKpiJson);

	}

	/**
	 * @Description 查询数据 0202 职工绩效指标表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempkpi/queryPrmEmpKpiTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String prmHosKpiObj = prmEmpKpiService.queryPrmEmpKpiTree(mapVo);

		return JSONObject.parseObject(prmHosKpiObj);

	}
	
	// 返回用用于保存的默认值
		public Map<String, Object> EmpKpiValue(Map<String, Object> mapVo) {
					
			Map<String,Object> map = new HashMap<String, Object>();
					
			if (map.get("group_id") == null) {
				map.put("group_id", SessionManager.getGroupId());
			}

			if (map.get("hos_id") == null) {
				map.put("hos_id", SessionManager.getHosId());
			}

			if (map.get("copy_code") == null) {
				map.put("copy_code", SessionManager.getCopyCode());
			}

			if (map.get("acc_year") == null) {
				map.put("acc_year", SessionManager.getAcctYear());
			}
			
				map.put("goal_code", mapVo.get("goal_code"));
				map.put("super_kpi_code", mapVo.get("super_kpi_code"));
				map.put("order_no",  0);
				map.put("kpi_level",  0);
				map.put("is_last",  1);
			return map;
	}
}
