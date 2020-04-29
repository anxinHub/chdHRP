/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.account.report;

import java.util.*;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.med.service.account.report.MedAccountReportDeptOutService;

/**
 * 
 * @Description: 科室出库查询表  
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedAccountReportDeptOutController extends BaseController {

	private static Logger logger = Logger.getLogger(MedAccountReportDeptOutController.class);

	// 引入Service服务
	@Resource(name = "medAccountReportDeptOutService")
	private final MedAccountReportDeptOutService medAccountReportDeptOutService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medDeptOutPage", method = RequestMethod.GET)
	public String MedDeptOutPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/account/report/medDeptOut";
	}

	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/queryMedAccountReportDeptOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAccountReportDeptOut(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date").toString())){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date").toString())){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		String medJson = medAccountReportDeptOutService.queryMedAccountReportDeptOut(mapVo);
		
		return JSONObject.parseObject(medJson);
	}
	
	
	/**
	 * 其他入库查询
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInByTypePage", method = RequestMethod.GET)
	public String medInByTypePage(Model mode) throws Exception {

		return "hrp/med/account/report/medInByType";
	}
	
	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/queryMedInByType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInByType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String medJson = medAccountReportDeptOutService.queryMedInByType(mapVo);
		
		return JSONObject.parseObject(medJson);
	}
	/**
	 * 其他入库明细页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medOtherInDetailPage", method = RequestMethod.GET)
	public String medOtherInDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/account/report/medOtherInDetail";
	}
	
	/**
	 * @Description 其他入库明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/queryMedOtherInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOtherInDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String medJson = medAccountReportDeptOutService.queryMedOtherInDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * 其他出库明细页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medOtherOutDetailPage", method = RequestMethod.GET)
	public String medOtherOutDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/account/report/medOtherOutDetail";
	}
	
	/**
	 * @Description 其他入库明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/queryMedOtherOutDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOtherOutDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String medJson = medAccountReportDeptOutService.queryMedOtherOutDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medJson);
	}
}
