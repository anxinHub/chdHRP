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
import com.chd.hrp.prm.entity.PrmDeptScheme;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmDeptKpiAdService;
import com.chd.hrp.prm.service.PrmDeptKpiLedService;
import com.chd.hrp.prm.service.PrmDeptKpiSectionService;
import com.chd.hrp.prm.service.PrmDeptSchemeAuditService;
import com.chd.hrp.prm.service.PrmDeptSchemeService;
import com.chd.hrp.prm.service.PrmEmpSchemeAuditService;
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
public class PrmDeptSchemeAuditController extends BaseController {

	private static Logger logger = Logger
			.getLogger(PrmDeptSchemeAuditController.class);

	//引入Service服务
	@Resource(name = "prmDeptSchemeService")
	private final PrmDeptSchemeService prmDeptSchemeService = null;
	
	// 引入Service服务
	@Resource(name = "prmDeptSchemeAuditService")
	private final PrmDeptSchemeAuditService prmDeptSchemeAuditService = null;
	
	// 引入Service服务
	@Resource(name = "prmDeptKpiSectionService")
	private final PrmDeptKpiSectionService prmDeptKpiSectionService = null;
	
	// 引入Service服务
	@Resource(name = "prmDeptKpiAdService")
	private final PrmDeptKpiAdService prmDeptKpiAdService = null;
	
	@Resource(name = "prmDeptKpiLedService")
	private final PrmDeptKpiLedService prmDeptKpiLedService = null;
	
	@Resource(name = "prmGoalService")
	private final PrmGoalService prmGoalService = null;
	
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/prmDeptSchemeauditMainPage", method = RequestMethod.GET)
	public String prmEmpSchemeMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptschemeaudit/prmDeptSchemeAuditMain";

	}
	
	
	/**
	 * @Description 
	 * 引用制定查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/queryPrmDeptSchemeAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptSchemeAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeLeftName(getPage(mapVo));

		return JSONObject.parseObject(prmDeptScheme);
		
	}
	
	
	/**
	 * @Description 科室指示灯设置 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/prmDeptSchemeKpiLedAudit", method = RequestMethod.GET)
	public String prmDeptSchemeKpiLedAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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

		return "hrp/prm/prmdeptschemeaudit/prmDeptKpiLedAddAudit";

	}
	
	/**
	 * @Description 查询数据科室设定表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/queryPrmDeptKpiLedAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiLedAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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
	
	/**
	 * @Description 查询数据 评分方法区间法(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/queryPrmDeptSectionByEditerGridAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptSectionByEditerGridAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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
	 * @Description 查询数据 指示灯表(编辑表格使用)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/queryPrmDeptKpiAdByEditerGridAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiAdByEditerGridAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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

	
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/auditPrmDeptScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmDeptScheme(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
        
        
		 String auditPrmDeptSchemeJson = prmDeptSchemeAuditService.auditPrmDeptScheme(mapVo);
		 
			return JSONObject.parseObject(auditPrmDeptSchemeJson);
	}
	
	
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/reAuditPrmDeptScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmDeptScheme(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
        
		 String auditPrmDeptSchemeJson  =  prmDeptSchemeAuditService.reAuditPrmDeptScheme(mapVo);
  
			return JSONObject.parseObject(auditPrmDeptSchemeJson);
	
	}
	
	/**
	 * @Description 以下是取值方法设定页面所用到的方法
	 * @param mode
	 * @return String
	 * @throws Exception
	 */

	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/prmFormulaMethodDeptSchemeAudit", method = RequestMethod.GET)
	public String prmFormulaMethodDeptSchemeAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		return "hrp/prm/prmdeptschemeaudit/prmDeptKpiMethodAdAudit";

	}
	
	/**
	 * @Description 科室区间法评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/prmDeptSchemeSectionAudit", method = RequestMethod.GET)
	public String prmDeptSchemeSectionAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		PrmDeptScheme prmDeptScheme = prmDeptSchemeService.queryPrmDeptSchemeByCode(mapVo);

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

		mode.addAttribute("full_score", mapVo.get("full_score"));

		return "hrp/prm/prmdeptschemeaudit/prmDeptKpiSectionMainAudit";

	}
	
	
	/**
	 * @Description 加扣分法 评分标准设定 页面跳转
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/prmDeptSchemesAdAudit", method = RequestMethod.GET)
	public String prmDeptSchemesAdAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return "hrp/prm/prmdeptschemeaudit/prmDeptKpiAdMainAudit";

	}
	
	
	/**
	 * @Description 查询树形菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptschemeaudit/queryDeptSchemeTreeAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptSchemeTreeAudit(@RequestParam Map<String, Object> mapVo, Model mode)
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
