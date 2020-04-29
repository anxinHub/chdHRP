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
import com.chd.hrp.ass.service.guanLiReports.AssScrapBySummryService;
/**
 * 
 * @author 资产报废汇总
 *
 */
@Controller
public class AssScrapBySummryController extends BaseController {
	

	
	private static Logger logger = Logger.getLogger(AssScrapBySummryController.class);
	
	//引入Service服务
	@Resource(name = "assScrapBySummryService")
	private final AssScrapBySummryService assScrapBySummryService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/assScrapBySummryPage", method = RequestMethod.GET)
	public String assScrapBySummryPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assScrapBySummry";

	}

	
	/**
	 * @Description 
	 * 查询数据 051001资产报废汇总  (资产处置 状态为  《登记》)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssScrapBySummry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssScrapBySummry(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
 
		String assScrapBySummry = assScrapBySummryService.query(getPage(mapVo));

		return JSONObject.parseObject(assScrapBySummry);
		
	}
 
	
}
