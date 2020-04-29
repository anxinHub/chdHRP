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
import com.chd.hrp.prm.service.PrmEmpKpiObjService;
import com.chd.hrp.prm.service.PrmEmpKpiService;
import com.chd.hrp.prm.service.PrmEmpSchemeService;

/**
 * 
 * @Description: 0402 职工绩效方案核算对象表
 * @Table: PRM_EMP_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmEmpKpiObjController extends BaseController {
	
		private static Logger logger = Logger.getLogger(PrmEmpKpiObjController.class);
	
		// 引入Service服务
		@Resource(name = "prmEmpKpiObjService")
		private final PrmEmpKpiObjService prmEmpKpiObjService = null;
	
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
		@RequestMapping(value = "/hrp/prm/prmempkpiobj/prmEmpKpiObjMainPage", method = RequestMethod.GET)
		public String prmEmpKpiObjMainPage(Model mode) throws Exception {
	
			return "hrp/prm/prmempkpiobj/prmEmpKpiObjMain";
	
		}
	
		
		/**
		 * @Description 添加数据 0402 职工绩效方案核算对象表
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/prm/prmempkpiobj/addPrmEmpKpiObj", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addPrmEmpKpiObj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
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
			String prmEmpKpiObjJson = null;
			
			try {
				
				prmEmpKpiObjJson = prmEmpKpiObjService.addPrmEmpKpiObj(mapVo);
				
			} catch (Exception e) {
				
				logger.error(e.getMessage(), e);
				
				prmEmpKpiObjJson = e.getMessage();
			}
			
			return JSONObject.parseObject(prmEmpKpiObjJson);
			
		}
	
		/**
		 * @Description 查询数据 0402 职工绩效方案核算对象表
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/prm/prmempkpiobj/queryPrmEmpKpiObj", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryPrmEmpKpiObj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
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
			
			String prmEmpKpiObj = prmEmpKpiObjService.queryPrmEmpKpiObj(getPage(mapVo));
	
			return JSONObject.parseObject(prmEmpKpiObj);
	
		}
	
		/**
		 * @Description 查询数据 0202 科室绩效方案核算对象表
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/prm/prmempkpiobj/queryPrmEmpKpiTree", method = RequestMethod.POST)
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
			
			if (mapVo.get("acc_year") == null) {
				mapVo.put("acc_year", SessionManager.getAcctYear());
			}
			
			if(mapVo.get("user_id") == null){
				mapVo.put("user_id", SessionManager.getUserId());
			}
	
			String prmHosKpiObj = prmEmpKpiService.queryPrmEmpKpiTree(mapVo);
	
			return JSONObject.parseObject(prmHosKpiObj);
	
		}
	
		/**
		 * @Description 查询数据 0202 科室绩效方案核算对象表
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/prm/prmempkpiobj/queryPrmEmpColumns", method = RequestMethod.POST)
		@ResponseBody
		public String queryPrmEmpColumns(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
	
			mapVo.put("is_stop", 0);
	
			String hosInfo = prmEmpKpiObjService.queryPrmEmp(mapVo);
	
			return hosInfo;
	
		}
		
		// 返回用用于保存的默认值
		public Map<String, Object> EmpKpiValue(Map<String, Object> mapVo,JSONObject jSONObject) {
						
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
			map.put("kpi_code", jSONObject.get("kpi_code"));
			map.put("kpi_name", jSONObject.get("kpi_name"));
			map.put("dept_no", 1);
			map.put("dept_id", 3);
			map.put("nature_code", jSONObject.get("nature_code"));
			map.put("super_kpi_code", mapVo.get("super_kpi_code"));
			map.put("order_no", 0);
			map.put("kpi_level", 0);
			map.put("spell_code", StringTool.toPinyinShouZiMu((String)jSONObject.get("kpi_name")));
			map.put("wbx_code",  StringTool.toWuBi((String)jSONObject.get("kpi_name")));
			map.put("is_last",  0);
	
			return map;
		}
	}
