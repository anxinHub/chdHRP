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
import com.chd.hrp.prm.entity.PrmHosKpi;
import com.chd.hrp.prm.service.PrmHosKpiService;

/**
 * 
 * @Description: 0202 医院绩效方案指标制定表
 * @Table: PRM_HOS_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmHosKpiController extends BaseController {
	
	private static Logger logger = Logger.getLogger(PrmHosKpiController.class);
	
	@Resource(name = "prmHosKpiService")
	private final PrmHosKpiService prmHosKpiService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/prmHosKpiMainPage", method = RequestMethod.GET)
	public String prmHosKpiObjMainPage(Model mode) throws Exception {

		return "hrp/prm/prmhoskpi/prmHosKpiMain";

	}
	
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/prmHosKpiAddPage", method = RequestMethod.GET)
	public String prmHosKpiAddPage(Model mode) throws Exception {

		return "hrp/prm/prmhoskpi/prmHosKpiAdd";

	}
	
	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/queryPrmHosKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiHos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String prmHosKpiJson = prmHosKpiService.queryPrmHosKpi(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiJson);

	}
	
	/**
	 * @Description 加载菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/queryPrmHosKpiTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null || "".equals(mapVo.get("hos_id"))) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String prmHosKpiObj = prmHosKpiService.queryPrmHosKpiTree(mapVo);

		return JSONObject.parseObject(prmHosKpiObj);

	}
	
	
	/**
	 * @Description 添加
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/addPrmHosKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmHosKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String prmHosKpiJson = prmHosKpiService.addPrmHosKpi(mapVo);

		return JSONObject.parseObject(prmHosKpiJson);

	}
	
	/**
	 * @Description 删除
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/deletePrmHosKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmHosKpi(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
		String prmHosKpiJson = prmHosKpiService.deleteBatchPrmHosKpi(listVo);
		return JSONObject.parseObject(prmHosKpiJson);
	}
	
	/**
	 * @Description 修改页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/prmHosKpiUpdatePage", method = RequestMethod.GET)
	public String prmHosKpiUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		PrmHosKpi phk = prmHosKpiService.queryPrmHosKpiByCode(mapVo);
		
		mode.addAttribute("group_id", phk.getGroup_id());
		mode.addAttribute("hos_id", phk.getHos_id());
		mode.addAttribute("copy_code", phk.getCopy_code());
		mode.addAttribute("acc_year", phk.getAcc_year());
		mode.addAttribute("goal_code", phk.getGoal_code());
		mode.addAttribute("kpi_code", phk.getKpi_code());
		mode.addAttribute("kpi_name", phk.getKpi_name());
		mode.addAttribute("dim_code", phk.getDim_code());
		mode.addAttribute("dim_name", phk.getDim_name());
		mode.addAttribute("nature_code", phk.getNature_code());
		mode.addAttribute("is_last",phk.getIs_last());
		mode.addAttribute("super_kpi_code", phk.getSuper_kpi_code());
		mode.addAttribute("is_audit", phk.getIs_audit());
		
		return "hrp/prm/prmhoskpi/prmHosKpiUpdate";

	}
	
	/**
	 * @Description 修改
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/updatePrmHosKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmHosKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String prmHosKpiJson = prmHosKpiService.updatePrmHosKpi(mapVo);

		return JSONObject.parseObject(prmHosKpiJson);

	}
	
	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/queryPrmHosKpis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String prmHosKpiJson = prmHosKpiService.queryPrmHosKpis(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiJson);

	}
	
	/**
	 * @Description 选择 批量保存指标
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhoskpi/addBatchPrmHosKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchPrmHosKpi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			
			prmHosKpiJson = prmHosKpiService.addBatchPrmHosKpi(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			prmHosKpiJson = e.getMessage();
			
		}

		return JSONObject.parseObject(prmHosKpiJson);

	}
	
}
