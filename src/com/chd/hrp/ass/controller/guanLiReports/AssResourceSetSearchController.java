/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.guanLiReports;
 

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
import com.chd.hrp.ass.service.guanLiReports.AssResourceSetService; 
 

/**
 * @Description: 050701 资产来源查询
 * @Table: ASS_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssResourceSetSearchController extends BaseController {

	private static Logger logger = Logger.getLogger(AssResourceSetSearchController.class);

	// 引入Service服务
		@Resource(name = "assResourceSetService")
		private final AssResourceSetService assResourceSetService = null;
		
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/guanLiReports/assResourceSetPage", method = RequestMethod.GET)
	public String assResourceSetPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assResourceSet";

	}
	
	/**
	 * @Description 查询数据 050701 资产来源 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssResourceSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssResourceSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assResourceSet = assResourceSetService.queryAssResourceSet(getPage(mapVo));

		return JSONObject.parseObject(assResourceSet);

	}

}
