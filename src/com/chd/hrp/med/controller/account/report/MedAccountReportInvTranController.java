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
import com.chd.hrp.med.service.account.report.MedAccountReportInvTranService;

/**
 * 
 * @Description: 药品调拨汇总表  
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedAccountReportInvTranController extends BaseController {

	private static Logger logger = Logger.getLogger(MedAccountReportInvTranController.class);

	// 引入Service服务
	@Resource(name = "medAccountReportInvTranService")
	private final MedAccountReportInvTranService medAccountReportInvTranService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInvTranPage", method = RequestMethod.GET)
	public String MedInvTranPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08011", MyConfig.getSysPara("08011"));

		return "hrp/med/account/report/medInvTran";
	}

	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/queryMedAccountReportInvTran", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAccountReportInvTran(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history",MyConfig.getSysPara("03001"));
		
		String medJson = medAccountReportInvTranService.queryMedAccountReportInvTran(mapVo);
		
		return JSONObject.parseObject(medJson);
	}
}
