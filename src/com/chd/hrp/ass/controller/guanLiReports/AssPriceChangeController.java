/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.guanLiReports;
import java.util.HashMap;
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
import com.chd.hrp.ass.service.guanLiReports.AssPriceChangeService;
 

/**
 * @Description:  资产原值变动
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssPriceChangeController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPriceChangeController.class);

	// 引入Service服务
	@Resource(name = "assPriceChangeService")
	private final AssPriceChangeService assPriceChangeService = null;
		
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/guanLiReports/assPriceChangeMainPage", method = RequestMethod.GET)
	public String assPriceChangeMainPage(Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("year_month", MyConfig.getCurAccYearMonth());
 
		mode.addAllAttributes(assPriceChangeService.queryBusTypes(mapVo));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assPriceChangeMain";

	}
	
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryBusTypes", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBusTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
 
		mode.addAllAttributes(assPriceChangeService.queryBusTypes(mapVo));
		return assPriceChangeService.queryBusTypes(mapVo);

	}
	
	
	/**
	 * @Description 
	 * 查询数据   资产原值变动
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssPriceChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPriceChange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
 
		if(mapVo.get("year_month") != null){
			String year = mapVo.get("year_month").toString().substring(0, 4);
			String month = mapVo.get("year_month").toString().substring(4, 6);
			mapVo.put("acc_year",year);
			mapVo.put("acc_month",month);
		}
		String assDepreBreakage = assPriceChangeService.query(getPage(mapVo));
		return JSONObject.parseObject(assDepreBreakage);
		
	}
	 

 
}
