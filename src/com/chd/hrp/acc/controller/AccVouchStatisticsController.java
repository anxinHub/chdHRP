package com.chd.hrp.acc.controller;

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
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccVouchStatisticsServiceImpl;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccVouchStatisticsController extends BaseController {
	private static Logger logger = Logger.getLogger(AccVouchStatisticsController.class);

	@Resource(name = "accVouchStatisticsService")
	private final AccVouchStatisticsServiceImpl accVouchStatisticsService = null;
	/**
	 * 凭证统计<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/statistics/accVouchStatisticsMainPage", method = RequestMethod.GET)
	public String accVouchStatisticsMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accvouch/statistics/accVouchStatisticsMain";

	}
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/statistics/queryAccVouchStatistics", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccVouchStatistics(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String accVouch = accVouchStatisticsService.queryAccVouchStatistics(getPage(mapVo));

		return JSONObject.parseObject(accVouch);

	}
}
