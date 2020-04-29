package com.chd.hrp.mat.controller.account.report;

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
import com.chd.hrp.mat.service.account.report.MatAccountReportInvTranService;
import com.chd.hrp.mat.service.account.report.MatAccountReportReducedBudgService;
/**
 * 
 * @author weixiaofeng
 *
 */
@Controller
public class MatAccountReportReducedBudgController extends BaseController {

	private static Logger logger = Logger.getLogger(MatAccountReportReducedBudgController.class);

	// 引入Service服务
	@Resource(name = "matAccountReportReducedBudgService")
	private final MatAccountReportReducedBudgService matAccountReportReducedBudgService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matReducedBudgPage", method = RequestMethod.GET)
	public String matReducedBudgPage(Model mode) throws Exception {
		
		return "hrp/mat/account/report/matReducedBudg";
	}

	/**
	 * @Description 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/queryReducedBudg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReducedBudg(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matJson = matAccountReportReducedBudgService.queryReducedBudg(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
}
