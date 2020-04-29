package com.chd.hrp.hr.controller.report;

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
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.report.HosPostDistributionService;

/**
 * 岗位分布表
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/report")
public class HrPostDistributionController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrPostDistributionController.class);

	// 引入Service服务
	@Resource(name = "hosPostDistributionService")
	private final HosPostDistributionService hosPostDistributionService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPostDistributionMainPage", method = RequestMethod.GET)
	public String hrPostDistributionMainPage(Model mode) throws Exception {

		return "hrp/hr/report/postDistributionMain";

	}


	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrPostDistribution", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrPostDistribution(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String hosEmpKind = hosPostDistributionService.queryHrPostDistribution(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}

	
}
