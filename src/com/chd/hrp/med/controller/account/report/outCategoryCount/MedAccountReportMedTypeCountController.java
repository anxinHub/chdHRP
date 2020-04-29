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
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportDeptCountService;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportDeptTypeCountService;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportMedTypeCountService;

/**
 * @Description: 出库分类统计:药品分类统计
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedAccountReportMedTypeCountController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedAccountReportMedTypeCountController.class);
	
	@Resource(name = "medAccountReportMedTypeCountService")
	private final MedAccountReportMedTypeCountService medAccountReportMedTypeCountService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/medTypeCountPage", method = RequestMethod.GET)
	public String medTypeCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/account/report/outCategoryCount/medTypeCount";
	}
	
	/**
	 * @Description 
	 * @param mode药品分类统计 查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/queryMedTypeCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedTypeCount(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		if(mapVo.get("dept_id") == null || "".equals(mapVo.get("dept_id"))){
			mapVo.put("dept_id", "00");
		}
		
		String medAccountReportMedTypeCountJson = medAccountReportMedTypeCountService.queryMedTypeCount(getPage(mapVo));
		
		return JSONObject.parseObject(medAccountReportMedTypeCountJson);
	}
	
}
