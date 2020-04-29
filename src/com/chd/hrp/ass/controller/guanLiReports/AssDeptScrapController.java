package com.chd.hrp.ass.controller.guanLiReports;

import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.ass.service.guanLiReports.AssDeptScrapService;
@Controller
public class AssDeptScrapController extends BaseController {
	@Resource(name = "assDeptScrapService")
	private final AssDeptScrapService assDeptScrapService = null;
	
	@RequestMapping(value = "/hrp/ass/guanLiReports/assDeptScrapSummryPage", method = RequestMethod.GET)
	public String assDeptScrapSummryPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assDeptScrapSummry";
	}
	
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssDeptScrapSummry", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDeptScrapSummry(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
 
		String assScrapBySummry = assDeptScrapService.queryAssDeptScrapSummry(getPage(mapVo));

		return JSONObject.parseObject(assScrapBySummry);
		
	}
}
