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
import com.chd.hrp.med.service.account.report.MedAccountReportInvStockService;

/**
 * 
 * @Description: 药品库存汇总表  
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedAccountReportInvStockController extends BaseController {

	private static Logger logger = Logger.getLogger(MedAccountReportInvStockController.class);

	// 引入Service服务
	@Resource(name = "medAccountReportInvStockService")
	private final MedAccountReportInvStockService medAccountReportInvStockService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInvStockPage", method = RequestMethod.GET)
	public String MedInvStockPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/account/report/medInvStock";
	}

	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/queryMedAccountReportInvStock", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAccountReportInvStock(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history",MyConfig.getSysPara("03001"));
		
		//String medJson = medAccountReportInvStockService.queryMedAccountReportInvStock(mapVo);
		String medJson = medAccountReportInvStockService.collectMedAccountReportInvStock(mapVo);
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 查询本期增加减少字段
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/queryMedAccountReportInvStockColumns", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAccountReportInvStockColumns(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String medJson = medAccountReportInvStockService.queryMedAccountReportInvStockColumns(mapVo);
		
		return JSONObject.parseObject(medJson);
	}
}
