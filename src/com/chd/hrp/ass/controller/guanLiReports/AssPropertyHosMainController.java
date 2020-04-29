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
import com.chd.hrp.ass.service.guanLiReports.AssPropertyHosMainService;
/**
 * 
 * @author 资产汇总   全院   (财务折旧)
 *
 */
@Controller
public class AssPropertyHosMainController extends BaseController {
	

	
	private static Logger logger = Logger.getLogger(AssPropertyHosMainController.class);
	
	//引入Service服务
	@Resource(name = "assPropertyHosMainService")
	private final AssPropertyHosMainService assPropertyHosMainService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/assPropertyHosMainPage", method = RequestMethod.GET)
	public String assPropertyHosMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_year_month",MyConfig.getCurAccYearMonth());
		return "hrp/ass/guanLiReports/assPropertyHosMain";

	}

	/**
	 * @Description 
	 * 查询数据 050301 购置计划单
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/guanLiReports/queryAssPropertyHosMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPropertyHosMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("is_stop", "0");
		
		if(mapVo.get("year_month") != null){
			String year = mapVo.get("year_month").toString().substring(0, 4);
			String month = mapVo.get("year_month").toString().substring(4, 6);
			mapVo.put("acc_year",year);
			mapVo.put("acc_month",month);
		}
		
		//String assPlanDept = assPropertyHosMainService.query(getPage(mapVo));
		String assPlanDept = assPropertyHosMainService.queryAssPropertyHosMain(getPage(mapVo));

		return JSONObject.parseObject(assPlanDept);
		
	}
 
	
}
