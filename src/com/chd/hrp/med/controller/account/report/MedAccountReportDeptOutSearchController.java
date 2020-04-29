package com.chd.hrp.med.controller.account.report;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.med.service.account.report.MedAccountReportDeptOutSearchService;

/**
 * @Description: 科室出库查询表
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedAccountReportDeptOutSearchController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedAccountReportDeptOutSearchController.class);
	
	@Resource(name = "medAccountReportDeptOutSearchService")
	private final MedAccountReportDeptOutSearchService medAccountReportDeptOutSearchService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medDeptOutSearchPage", method = RequestMethod.GET)
	public String medDeptOutSearchPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/account/report/medDeptOutSearch";
	}
	
	/**
	 * @Description 查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/queryMedAccountReportDeptOutSearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAccountReportDeptOutSearch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history",MyConfig.getSysPara("03001"));
		
		String medAccountReportDeptOutSearchJson = medAccountReportDeptOutSearchService.queryMedAccountReportDeptOutSearch(getPage(mapVo));
		
		return JSONObject.parseObject(medAccountReportDeptOutSearchJson) ; 
	}
}
