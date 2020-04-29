package com.chd.hrp.ass.controller.zengjian;

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
import com.chd.hrp.ass.service.zengjian.AssZengJianMainService;


@Controller
public class AssZengJianMainController extends BaseController {
	private static Logger logger = Logger.getLogger(AssZengJianMainController.class);
	
	
	@Resource(name = "assZengJianMainService")
	private final AssZengJianMainService assZengJianMainService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assZengJian/assZengJianMainPage", method = RequestMethod.GET)
	public String assAcceptMainMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_year_month",MyConfig.getAccYearMonth());
		return "hrp/ass/assZengJian/assZengJianMain";

	}
	
	/**
	 * @Description 
	 * 查询数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assZengJian/queryAssZengJian", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssZengJian(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
      // mapVo.put("in_date", new Date());
		if(mapVo.get("year_month")!= null ){
			String year = mapVo.get("year_month").toString().substring(0, 4);
			String month = mapVo.get("year_month").toString().substring(4, 6);
			mapVo.put("acc_year",year);
			mapVo.put("acc_month",month);
		}
		String assZengJianMain = assZengJianMainService.queryAssZengJian(mapVo);

		return JSONObject.parseObject(assZengJianMain);
		
	}
	
}