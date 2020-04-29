package com.chd.hrp.ass.controller.tongJiReports;

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
import com.chd.hrp.ass.service.tongJiReports.AssInAndOutSummaryService;

@Controller
public class AssInAndOutSummaryController extends BaseController {
	   
	private static Logger logger = Logger.getLogger(AssInAndOutSummaryController.class);
	
	// 引入Service服务
	@Resource(name = "assInAndOutSummaryService")
	private final AssInAndOutSummaryService assInAndOutSummaryService = null;

	/**
	 * @Description 资产入出库统计页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/tongJiReports/assInAndOutSummaryPage", method = RequestMethod.GET)
	public String assInAndOutSummaryPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assInAndOutSummary";

	}
	
	/**
	 * @Description 查询数据 资产入出库统计
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssInAndOutSummary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInAndOutSummary(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String reJson = assInAndOutSummaryService.queryAssInAndOutSummary(getPage(mapVo));
		return JSONObject.parseObject(reJson);

	}
	
	/**
	 * @Description 
	 * 入出库查询-资产入库库存数量明细查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/tongJiReports/assInDetailPage", method = RequestMethod.GET)
	public String assInDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//mapVo.put("show_history", SessionManager.getCostParaMap().get("03001"));
		
		String assInDetail = assInAndOutSummaryService.queryAssInDetail(mapVo);
		
		
		mode.addAttribute("assInDetail", JSONObject.parseObject(assInDetail));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assInDetail";
	}
	
	/**
	 * @Description 
	 * 入出库查询-资产出库库存数量明细查询 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/tongJiReports/assOutDetailPage", method = RequestMethod.GET)
	public String assOutDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//mapVo.put("show_history", SessionManager.getCostParaMap().get("03001"));
		
		String assOutDetail = assInAndOutSummaryService.queryAssOutDetail(mapVo);
		
		mode.addAttribute("assOutDetail", JSONObject.parseObject(assOutDetail));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/tongJiReports/assOutDetail";
	}
	
}
