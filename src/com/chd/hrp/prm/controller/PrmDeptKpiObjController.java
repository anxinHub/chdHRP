/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

import java.util.HashMap;
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
import com.chd.hrp.prm.service.PrmDeptKpiObjService;
import com.chd.hrp.prm.service.PrmDeptKpiService;
import com.chd.hrp.prm.service.PrmDeptSchemeService;

/**
 * 
 * @Description: 0302 科室绩效方案核算对象表
 * @Table: PRM_DEPT_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptKpiObjController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptKpiObjController.class);

	// 引入Service服务
	@Resource(name = "prmDeptKpiObjService")
	private final PrmDeptKpiObjService prmDeptKpiObjService = null;

	// 引入Service服务
	@Resource(name = "prmDeptKpiService")
	private final PrmDeptKpiService prmDeptKpiService = null;

	// 引入Service服务
	@Resource(name = "prmDeptSchemeService")
	private final PrmDeptSchemeService prmDeptSchemeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiobj/prmDeptKpiObjMainPage", method = RequestMethod.GET)
	public String prmDeptKpiObjMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkpiobj/prmDeptKpiObjMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiobj/prmDeptKpiObjAddPage", method = RequestMethod.GET)
	public String prmDeptKpiObjAddPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkpiobj/prmDeptKpiObjAdd";

	}

	/**
	 * @Description 添加数据 0302 科室绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiobj/addPrmDeptKpiObj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmDeptKpiObj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		if(mapVo.get("group_id") == null ){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null ){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null ){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("is_stop", 0);
		String prmDeptKpiObjJson = null;
		
		try {
			
			prmDeptKpiObjJson = prmDeptKpiObjService.addPrmDeptKpiObj(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			prmDeptKpiObjJson = e.getMessage();
		}
		
		return JSONObject.parseObject(prmDeptKpiObjJson);
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

		if (map.get("acc_year") == null) {

			map.put("acc_year", SessionManager.getAcctYear());

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
	 * @Description 查询数据 0302 科室绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiobj/queryPrmDeptKpiObj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiObj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String prmDeptKpiObj = prmDeptKpiObjService.queryPrmDeptKpiObj(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiObj);

	}

	/**
	 * @Description 查询数据 0202 科室绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiobj/queryPrmDeptKpiTree", method = RequestMethod.POST)
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
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String prmHosKpiObj = prmDeptKpiService.queryPrmDeptKpiTree(mapVo);

		return JSONObject.parseObject(prmHosKpiObj);

	}

	/**
	 * @Description 查询数据 0202 医院绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiobj/queryPrmDeptKpiObjDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiObjHos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String prmHosKpiObj = prmDeptKpiObjService.queryPrmDeptKpiObjDept(getPage(mapVo));

		return JSONObject.parseObject(prmHosKpiObj);

	}

	/**
	 * @Description 查询数据 0202 科室绩效方案核算对象表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpiobj/queryPrmDeptColumns", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmDeptColumns(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());

		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("is_stop", '0');

		String hosInfo = prmDeptKpiObjService.queryPrmDept(mapVo);

		return hosInfo;

	}

}
