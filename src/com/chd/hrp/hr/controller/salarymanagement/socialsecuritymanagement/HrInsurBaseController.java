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
import com.chd.hrp.hr.service.salarymanagement.socialSecurityManage.HrInsurBaseService;
/**
 * 【薪资管理-社保管理】：社保缴费基数
 * @author yang
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/insurBase")
public class HrInsurBaseController extends BaseController {
	
	private static Logger logger = Logger.getLogger(HrInsurBaseController.class);
	
	@Resource(name = "hrInsurBaseService")
	private final HrInsurBaseService hrInsurBaseService = null;
	
	// 主页面
	@RequestMapping(value = "/insurBaseMainPage", method = RequestMethod.GET)
	public String mainPage() throws Exception {
		return "hrp/hr/salarymanagement/socialsecuritymanagement/insurBase/hrInsurBaseMain";
	}
	
	// 主查询
	@RequestMapping(value = "/queryHrInsurBase", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrInsurBase(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try{
			return hrInsurBaseService.queryHrInsurBase(getPage(paramMap));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

}
