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

/**
 * @Description: 出库分类统计:科室统计
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedAccountReportDeptCountController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedAccountReportDeptCountController.class);
	
	@Resource(name = "medAccountReportDeptCountService")
	private final MedAccountReportDeptCountService medAccountReportDeptCountService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/medDeptCountPage", method = RequestMethod.GET)
	public String medDeptCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/account/report/outCategoryCount/medDeptCount";
	}
	
	/**
	 * @Description 
	 * @param mode科室统计 查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/queryMedDeptCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedDeptCount(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAccountReportDeptCountJson = medAccountReportDeptCountService.queryMedAccountReportDeptCount(getPage(mapVo));
		
		return JSONObject.parseObject(medAccountReportDeptCountJson);
	}
	
	//
	/**
	 * @Description 
	 * 查询药品收费类别-表头
	 * @param mapVo
	 * @return map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/queryDeptCountHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptCountHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String reJson=null;
		
		try {
			reJson=medAccountReportDeptCountService.queryDeptCountHead(mapVo);
			
		} catch (Exception e) {
				// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}
	
	/**
	 * @Description 移库汇总表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/medTransferCountPage", method = RequestMethod.GET)
	public String medTransferCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/account/report/outCategoryCount/medTransferCount";
	}
	
	/**
	 * 移库汇总表查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/outCategoryCount/queryMedTransferCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedTransferCount(@RequestParam Map<String,Object> mapVo ,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medAccountReportDeptCountJson = medAccountReportDeptCountService.queryMedTransferCount(getPage(mapVo));
		
		return JSONObject.parseObject(medAccountReportDeptCountJson);
	}
}
