package com.chd.hrp.acc.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.logicalcobwebs.concurrent.FJTask.Par;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.AccBadDebtsService;

@Controller
public class AccBadDebtsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccBadDebtsController.class);
	
	@Resource(name = "accBadDebtsService")
	private final AccBadDebtsService accBadDebtsService = null;
	
	@RequestMapping(value = "/hrp/acc/accBadDebts/accBadDebtsMainPage", method = RequestMethod.GET)
	public String accBadDebtsMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		//返回当前月
		int month = new Date().getMonth() + 1;
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", month < 10 ? "0"+month:month);
		return "hrp/acc/accBadDebts/accBadDebtsMain";
	}
	
	//查询
	@RequestMapping(value = "/hrp/acc/accBadDebts/queryBadDebts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBadDebts(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		return JSON.parseObject(accBadDebtsService.queryBadDebts(mapVo));
	}
	
	//添加
	@RequestMapping(value = "/hrp/acc/accBadDebts/addBadDebts", method = RequestMethod.POST)
	@ResponseBody
	public String addBadDebts(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		return accBadDebtsService.addBadDebts(mapVo);
	}
	
	//查询科目
	@RequestMapping(value = "/hrp/acc/accBadDebts/queryAccSubjSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccSubjSelect(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		return JSON.toJSONString(accBadDebtsService.queryAccSubjSelect(mapVo));
	}
	
	//导入
	@RequestMapping(value = "/hrp/acc/accBadDebts/importBadDebts", method = RequestMethod.POST)
	@ResponseBody
	public String importBadDebts(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		return JSON.toJSONString(accBadDebtsService.importBadDebts(mapVo));
	}
	
}
