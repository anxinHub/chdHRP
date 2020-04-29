package com.chd.hrp.ass.controller.summary;

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
import com.chd.hrp.ass.service.biandongdept.AssBianDongDeptMainService;
import com.chd.hrp.ass.service.summary.AssSummaryService;

@Controller
public class AssSummaryController extends BaseController{

	private static Logger logger = Logger.getLogger(AssSummaryController.class);
	
	@Resource(name = "assSummaryService")
	private final AssSummaryService assSummaryService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/summary/assSummaryMainPage", method = RequestMethod.GET)
	public String assSummaryMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/asssummary/assSummaryMain";

	}
	
	
	/**
	 * @Description 
	 * 查询数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/summary/queryAssSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSummary(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
 
		if(mapVo.get("year_month") != null && mapVo.get("year_month") !=""){
			String year = mapVo.get("year_month").toString().substring(0, 4);
			String month = mapVo.get("year_month").toString().substring(4, 6);
			mapVo.put("acc_year",year);
			mapVo.put("acc_month",month);
		}
		
		String assBianDongDeptMain = assSummaryService.queryAssSummary(getPage(mapVo));

		return JSONObject.parseObject(assBianDongDeptMain);
		
	}
}
