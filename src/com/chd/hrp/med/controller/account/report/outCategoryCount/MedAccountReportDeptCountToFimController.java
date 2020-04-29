package com.chd.hrp.med.controller.account.report.outCategoryCount;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportDeptCountToFimService;

/**
 * @Description: 出库分类统计:科室统计
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedAccountReportDeptCountToFimController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedAccountReportDeptCountToFimController.class);
	
	@Resource(name = "medAccountReportDeptCountToFimService")
	private final MedAccountReportDeptCountToFimService medAccountReportDeptCountToFimService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/medDeptCountToFimPage", method = RequestMethod.GET)
	public String medDeptCountPage(Model mode) throws Exception {

		return "hrp/med/account/report/outCategoryCount/medDeptCountToFim";
	}
	
	/**
	 * @Description 
	 * @param mode科室统计 查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/queryMedDeptCountToFim", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedDeptCountToFim(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAccountReportDeptCountJson = medAccountReportDeptCountToFimService.queryMedAccountReportDeptCount(getPage(mapVo));
		
		return JSONObject.parseObject(medAccountReportDeptCountJson);
	}
}
