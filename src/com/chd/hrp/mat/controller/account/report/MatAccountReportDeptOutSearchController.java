package com.chd.hrp.mat.controller.account.report;

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
import com.chd.hrp.mat.service.account.report.MatAccountReportDeptOutSearchService;

/**
 * @Description: 科室出库查询表
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatAccountReportDeptOutSearchController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatAccountReportDeptOutSearchController.class);
	
	@Resource(name = "matAccountReportDeptOutSearchService")
	private final MatAccountReportDeptOutSearchService matAccountReportDeptOutSearchService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matDeptOutSearchPage", method = RequestMethod.GET)
	public String matDeptOutSearchPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("hos_name", SessionManager.getHosName());

		return "hrp/mat/account/report/matDeptOutSearch";
	}
	
	/**
	 * @Description 查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/queryMatAccountReportDeptOutSearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountReportDeptOutSearch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAccountReportDeptOutSearchJson = null;  
		if(mapVo.get("isSum").equals("0")){
			matAccountReportDeptOutSearchJson =  matAccountReportDeptOutSearchService.queryMatAccountReportDeptOutSearch(getPage(mapVo));
		}else{
			matAccountReportDeptOutSearchJson =  matAccountReportDeptOutSearchService.queryMatAccountReportDeptOutSearchByCollect(getPage(mapVo));
		}
		//String matAccountReportDeptOutSearchJson = matAccountReportDeptOutSearchService.queryMatAccountReportDeptOutSearch(getPage(mapVo));
		
		return JSONObject.parseObject(matAccountReportDeptOutSearchJson) ; 
	}
}
