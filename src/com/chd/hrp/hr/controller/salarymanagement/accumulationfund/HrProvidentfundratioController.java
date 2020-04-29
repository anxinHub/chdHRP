package com.chd.hrp.hr.controller.salarymanagement.accumulationfund;

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
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.salarymanagement.accumulationfund.HrProvidentfundratioService;

@Controller
public class HrProvidentfundratioController  extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrProvidentfundratioController.class);
	
	//注入依赖
	@Resource(name = "hrProvidentfundratioService")
	private final HrProvidentfundratioService hrProvidentfundratioService = null;

	//进入薪资核算主页面
	@RequestMapping(value = "/hrp/hr/salarymanagement/accumulationfund/providentfundratio/providentfundratioMainPage", method = RequestMethod.GET)
	public String providentfundratioMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception{
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo = hrProvidentfundratioService.queryProvidentfundratio(mapVo);
		mode.addAttribute("vo",mapVo);
		
		return "hrp/hr/salarymanagement/accumulationfund/providentfundratio/providentfundratioMain";
	}
	
	//社保缴纳费率添加
	@RequestMapping(value = "/hrp/hr/salarymanagement/accumulationfund/providentfundratio/saveProvidentfundratio", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveProvidentfundratio(@RequestParam Map<String, Object> mapVo, Model mode)
		throws Exception{
		try{
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			return JSONObject.parseObject(hrProvidentfundratioService.saveProvidentfundratio(mapVo));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
}
