/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;


import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmHosKpiAdService;
import com.chd.hrp.prm.service.PrmHosKpiLedService;
import com.chd.hrp.prm.service.PrmHosKpiSectionService;
import com.chd.hrp.prm.service.PrmHosSchemeAuditService;
import com.chd.hrp.prm.service.PrmHosSchemeService;
import com.chd.hrp.prm.service.PrmGoalService;


/**
 * 
 * @Description: 0203 医院绩效方案表
 * @Table: PRM_HOS_SCHEME
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmHosSchemeAuditController extends BaseController {

	private static Logger logger = Logger
			.getLogger(PrmHosSchemeAuditController.class);

	//引入Service服务
	@Resource(name = "prmHosSchemeService")
	private final PrmHosSchemeService prmHosSchemeService = null;
	
	
	// 引入Service服务
	@Resource(name = "prmHosSchemeAuditService")
	private final PrmHosSchemeAuditService prmHosSchemeAuditService = null;
	
	// 引入Service服务
	@Resource(name = "prmHosKpiSectionService")
	private final PrmHosKpiSectionService prmHosKpiSectionService = null;
	
	// 引入Service服务
	@Resource(name = "prmHosKpiAdService")
	private final PrmHosKpiAdService prmHosKpiAdService = null;
	
	@Resource(name = "prmHosKpiLedService")
	private final PrmHosKpiLedService prmHosKpiLedService = null;
	
	@Resource(name = "prmGoalService")
	private final PrmGoalService prmGoalService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/prmHosSchemeauditMainPage", method = RequestMethod.GET)
	public String prmHosSchemeMainPage(Model mode) throws Exception {

		return "hrp/prm/prmhosschemeaudit/prmHosSchemeAuditMain";

	}
	
	
	/**
	 * @Description 
	 * 引用制定查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/queryPrmHosSchemeAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosSchemeAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	  if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
         mapVo.put("acct_year", SessionManager.getAcctYear());
        
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
	 * @Description 科室指示灯设置 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/prmHosSchemeKpiLedAudit", method = RequestMethod.GET)
	public String prmHosSchemeKpiLedAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		PrmHosScheme prmHosScheme = prmHosSchemeService.queryPrmHosSchemeByCode(mapVo);

		mode.addAttribute("group_id", prmHosScheme.getGroup_id());

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

		return "hrp/prm/prmhosschemeaudit/prmHosKpiLedAddAudit";

	}
	
	/**
	 * @Description 查询数据科室设定表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/queryPrmHosKpiLedAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiLedAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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
	
	/**
	 * @Description 查询数据 评分方法区间法(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/queryPrmHosSectionByEditerGridAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosSectionByEditerGridAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/queryPrmHosKpiAdByEditerGridAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmHosKpiAdByEditerGridAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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

	
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/auditPrmHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmHosScheme(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
	Map<String, Object> mapVo = new HashMap<String, Object>();
	
	Map<String, Object> isAuditmapVo = new HashMap<String, Object>();
		
        for (String id : paramVo.split(",")) {

 
            	String[] ids=id.split("@");

    			mapVo.put("group_id", SessionManager.getGroupId());
    			mapVo.put("copy_code", SessionManager.getCopyCode());
     	    	mapVo.put("acc_year", ids[0])   ;
			    mapVo.put("start_month", ids[1].toString()) ;
			    mapVo.put("hos_id", ids[2])  ;
			    mapVo.put("goal_code", ids[3]);
			    
			    isAuditmapVo.put("group_id", mapVo.get("group_id"));
			    isAuditmapVo.put("copy_code", mapVo.get("copy_code"));
			    isAuditmapVo.put("acc_year", mapVo.get("acc_year"));
			    isAuditmapVo.put("start_month", mapVo.get("start_month"));
			    isAuditmapVo.put("hos_id", mapVo.get("hos_id"));
			    isAuditmapVo.put("goal_code", mapVo.get("goal_code"));
			    isAuditmapVo.put("is_audit", 1);
				Long isAudit = prmGoalService.queryPrmGoalByAudit(isAuditmapVo);
				
				if(isAudit > 0){
					return JSONObject.parseObject("{\"error\":\"该战略已经审核不能重复审核.\"}");
				}
			    
			    mapVo.put("user_code", SessionManager.getUserCode());
			    mapVo.put("is_audit", "1");
			    mapVo.put("audit_date",DateUtil.dateToString(new Date(),"yyyy-MM-dd"));

        }
        
        
		 String auditPrmHosSchemeJson = prmHosSchemeAuditService.auditPrmHosScheme(mapVo);
		 
			return JSONObject.parseObject(auditPrmHosSchemeJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/reAuditPrmHosScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmHosScheme(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		
        for (String id : paramVo.split(",")) {

 
            String[] ids=id.split("@");

    			mapVo.put("group_id", SessionManager.getGroupId());
    			mapVo.put("copy_code", SessionManager.getCopyCode());
     	    	mapVo.put("acc_year", ids[0])   ;
			    mapVo.put("start_month", ids[1])   ;
			    mapVo.put("hos_id", ids[2])   ;
			    mapVo.put("goal_code", ids[3]);
			    mapVo.put("user_code","");
			    mapVo.put("is_audit", "0");
			    mapVo.put("audit_date","");
        }
        
		 String auditPrmHosSchemeJson  =  prmHosSchemeAuditService.reAuditPrmHosScheme(mapVo);
  
			return JSONObject.parseObject(auditPrmHosSchemeJson);
	
	}
	
	/**
	 * @Description 以下是取值方法设定页面所用到的方法
	 * @param mode
	 * @return String
	 * @throws Exception
	 */

	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/prmFormulaMethodHosSchemeAudit", method = RequestMethod.GET)
	public String prmFormulaMethodHosSchemeAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		mode.addAttribute("method_code", mapVo.get("method_code"));
		
		mode.addAttribute("method_name", mapVo.get("method_name"));
		
		mode.addAttribute("fun_code", prmHosScheme.getFun_code());
		
		mode.addAttribute("fun_name", prmHosScheme.getFun_name());
		
		mode.addAttribute("fun_method_chs", prmHosScheme.getFun_method_chs());
		
		mode.addAttribute("formula_code", prmHosScheme.getFormula_code());
		
		mode.addAttribute("formula_name", prmHosScheme.getFormula_name());
		
		mode.addAttribute("formula_method_chs", prmHosScheme.getFormula_method_chs());
		
		return "hrp/prm/prmhosschemeaudit/prmHosKpiMethodAdAudit";

	}
	
	/**
	 * @Description 科室区间法评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/prmHosSchemeSectionAudit", method = RequestMethod.GET)
	public String prmHosSchemeSectionAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		mode.addAttribute("check_hos_id", mapVo.get("check_hos_id"));
		
		mode.addAttribute("check_hos_code", prmHosScheme.getCheck_hos_code());

		mode.addAttribute("check_hos_name", prmHosScheme.getCheck_hos_name());

		mode.addAttribute("ratio", prmHosScheme.getRatio());

		mode.addAttribute("goal_value", prmHosScheme.getGoal_value());

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmhosschemeaudit/prmHosKpiSectionMainAudit";

	}
	
	
	/**
	 * @Description 加扣分法 评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/prmHosSchemesAdAudit", method = RequestMethod.GET)
	public String prmHosSchemesAdAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return "hrp/prm/prmhosschemeaudit/prmHosKpiAdMainAudit";

	}
	
	
	/**
	 * @Description 查询树形菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmhosschemeaudit/queryHosSchemeTreeAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosSchemeTreeAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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
