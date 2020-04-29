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
import com.chd.hrp.hr.service.salarymanagement.accumulationfund.HrBehoovePayFundService;
/**
 * 【薪资管理-公积金等】：公积金缴费基数
 * @author yang
 *
 */
@Controller
@RequestMapping(value="/hrp/hr/salarymanagement/accumulationfund/behoovePayFund")
public class HrBehoovePayFundController extends BaseController {

	private static Logger logger = Logger.getLogger(HrBehoovePayFundController.class);
	
	// 引入service
	@Resource(name="hrBehoovePayFundService")
	private final HrBehoovePayFundService hrBehoovePayFundService = null;
	
	// 主页面
	@RequestMapping(value = "/behoovePayFundMainPage", method = RequestMethod.GET)
	public String mainPage() throws Exception{
		return "hrp/hr/salarymanagement/accumulationfund/behoovePayFund/hrBehoovePayFundMain";
	}
	
	// 主查询
	@RequestMapping(value = "/queryHrFund", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrFund(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try{
			String jsonStr = hrBehoovePayFundService.queryHrFund(getPage(paramMap));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
