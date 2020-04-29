package com.chd.hrp.mat.controller.account.report.outCategoryCount;

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
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportDeptCountService;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportDeptTypeCountService;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportMatTypeCountService;

/**
 * @Description: 出库分类统计:物资分类统计
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatAccountReportMatTypeCountController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatAccountReportMatTypeCountController.class);
	
	@Resource(name = "matAccountReportMatTypeCountService")
	private final MatAccountReportMatTypeCountService matAccountReportMatTypeCountService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/matTypeCountPage", method = RequestMethod.GET)
	public String matTypeCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("hos_name", SessionManager.getHosName());

		return "hrp/mat/account/report/outCategoryCount/matTypeCount";
	}
	
	/**
	 * @Description 
	 * @param mode物资分类统计 查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/outCategoryCount/queryMatTypeCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatTypeCount(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		if(mapVo.get("dept_id") == null || "".equals(mapVo.get("dept_id"))){
			mapVo.put("dept_id", "00");
		}
		
		String matAccountReportMatTypeCountJson = matAccountReportMatTypeCountService.queryMatTypeCount(getPage(mapVo));
		
		return JSONObject.parseObject(matAccountReportMatTypeCountJson);
	}
	
}
