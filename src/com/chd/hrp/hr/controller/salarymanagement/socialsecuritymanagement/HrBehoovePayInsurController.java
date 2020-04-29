package com.chd.hrp.hr.controller.salarymanagement.socialsecuritymanagement;

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
import com.chd.hrp.hr.service.salarymanagement.socialSecurityManage.HrBehoovePayInsurService;
/**
 * 【薪资管理-社保管理】：应缴社保查询
 * @author yang
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/behoovePayInsur")
public class HrBehoovePayInsurController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HrBehoovePayInsurController.class);
	
	@Resource(name = "hrBehoovePayInsurService")
	private final HrBehoovePayInsurService hrBehoovePayInsurService = null;
	
	// 主页面
	@RequestMapping(value = "/behoovePayInsurMainPage", method = RequestMethod.GET)
	public String mainPage() throws Exception {
		return "hrp/hr/salarymanagement/socialsecuritymanagement/behoovePayInsur/hrBehoovePayInsurMain";
	}
	
	// 返回前台grid表格表头
	@RequestMapping(value = "/queryBehoovePayInsurHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBehoovePayInsurHead(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception{
		try{
			String jsonStr = hrBehoovePayInsurService.queryBehoovePayInsurHead(paramMap);
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 主查询
	@RequestMapping(value = "/queryBehoovePayInsur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBehoovePayInsur(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try{
			return hrBehoovePayInsurService.queryBehoovePayInsur(getPage(paramMap));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
