/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;



import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.prm.entity.PrmEmpScheme;
import com.chd.hrp.prm.service.PrmEmpKpiAdService;
import com.chd.hrp.prm.service.PrmEmpKpiLedService;
import com.chd.hrp.prm.service.PrmEmpKpiSectionService;
import com.chd.hrp.prm.service.PrmEmpSchemeAuditService;
import com.chd.hrp.prm.service.PrmEmpSchemeService;
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
public class PrmEmpSchemeAuditController extends BaseController {

	private static Logger logger = Logger
			.getLogger(PrmEmpSchemeAuditController.class);

	//引入Service服务
	@Resource(name = "prmEmpSchemeService")
	private final PrmEmpSchemeService prmEmpSchemeService = null;
	
	
	// 引入Service服务
	@Resource(name = "prmEmpSchemeAuditService")
	private final PrmEmpSchemeAuditService prmEmpSchemeAuditService = null;
	
	// 引入Service服务
	@Resource(name = "prmEmpKpiSectionService")
	private final PrmEmpKpiSectionService prmEmpKpiSectionService = null;
	
	// 引入Service服务
	@Resource(name = "prmEmpKpiAdService")
	private final PrmEmpKpiAdService prmEmpKpiAdService = null;
	
	@Resource(name = "prmEmpKpiLedService")
	private final PrmEmpKpiLedService prmEmpKpiLedService = null;
	
	@Resource(name = "prmGoalService")
	private final PrmGoalService prmGoalService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/prmEmpSchemeauditMainPage", method = RequestMethod.GET)
	public String prmEmpSchemeMainPage(Model mode) throws Exception {

		return "hrp/prm/prmempschemeaudit/prmEmpSchemeAuditMain";

	}
	
	
	/**
	 * @Description 
	 * 引用制定查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/queryPrmEmpSchemeAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpSchemeAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmEmpScheme = prmEmpSchemeService.queryPrmEmpSchemeLeftName(getPage(mapVo));

		return JSONObject.parseObject(prmEmpScheme);
		
	}
	
	
	/**
	 * @Description 科室指示灯设置 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/prmEmpSchemeKpiLedAudit", method = RequestMethod.GET)
	public String prmEmpSchemeKpiLedAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		PrmEmpScheme prmEmpScheme = prmEmpSchemeService.queryPrmEmpSchemeByCode(mapVo);

		mode.addAttribute("group_id", prmEmpScheme.getGroup_id());

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

		return "hrp/prm/prmempschemeaudit/prmEmpKpiLedAddAudit";

	}
	
	/**
	 * @Description 查询数据科室设定表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/queryPrmEmpKpiLedAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiLedAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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
	
	/**
	 * @Description 查询数据 评分方法区间法(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/queryPrmEmpSectionByEditerGridAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpSectionByEditerGridAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/queryPrmEmpKpiAdByEditerGridAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpKpiAdByEditerGridAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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

	
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/auditPrmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmEmpScheme(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
        
        
		 String auditPrmEmpSchemeJson = prmEmpSchemeAuditService.auditPrmEmpScheme(mapVo);
		 
			return JSONObject.parseObject(auditPrmEmpSchemeJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/reAuditPrmEmpScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmEmpScheme(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
        
		 String auditPrmEmpSchemeJson  =  prmEmpSchemeAuditService.reAuditPrmEmpScheme(mapVo);
  
			return JSONObject.parseObject(auditPrmEmpSchemeJson);
	
	}
	
	/**
	 * @Description 以下是取值方法设定页面所用到的方法
	 * @param mode
	 * @return String
	 * @throws Exception
	 */

	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/prmFormulaMethodEmpSchemeAudit", method = RequestMethod.GET)
	public String prmFormulaMethodEmpSchemeAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		mode.addAttribute("method_code", mapVo.get("method_code"));
		
		mode.addAttribute("method_name", mapVo.get("method_name"));
		
		mode.addAttribute("fun_code", prmEmpScheme.getFun_code());
		
		mode.addAttribute("fun_name", prmEmpScheme.getFun_name());
		
		mode.addAttribute("fun_method_chs", prmEmpScheme.getFun_method_chs());
		
		mode.addAttribute("formula_code", prmEmpScheme.getFormula_code());
		
		mode.addAttribute("formula_name", prmEmpScheme.getFormula_name());
		
		mode.addAttribute("formula_method_chs", prmEmpScheme.getFormula_method_chs());
		
		return "hrp/prm/prmempschemeaudit/prmEmpKpiMethodAdAudit";

	}
	
	/**
	 * @Description 科室区间法评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/prmEmpSchemeSectionAudit", method = RequestMethod.GET)
	public String prmEmpSchemeSectionAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return "hrp/prm/prmempschemeaudit/prmEmpKpiSectionMainAudit";

	}
	
	
	/**
	 * @Description 加扣分法 评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/prmEmpSchemesAdAudit", method = RequestMethod.GET)
	public String prmEmpSchemesAdAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return "hrp/prm/prmempschemeaudit/prmEmpKpiAdMainAudit";

	}
	
	
	/**
	 * @Description 查询树形菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmempschemeaudit/queryEmpSchemeTreeAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpSchemeTreeAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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
