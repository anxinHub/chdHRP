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
import com.chd.hrp.hr.service.salarymanagement.accumulationfund.HrFundBaseService;
/**
 * 【薪资管理-公积金等】：公积金缴费基数
 * @author yang
 *
 */
@Controller
@RequestMapping(value="/hrp/hr/salarymanagement/accumulationfund/fundBase")
public class HrFundBaseController extends BaseController {

	private static Logger logger = Logger.getLogger(HrFundBaseController.class);
	
	// 引入service
	@Resource(name="hrFundBaseService")
	private final HrFundBaseService hrFundBaseService = null;
	
	// 主页面
	@RequestMapping(value = "/fundBaseMainPage", method = RequestMethod.GET)
	public String mainPage() throws Exception{
		return "hrp/hr/salarymanagement/accumulationfund/fundBase/hrFundBaseMain";
	}
	
	// 主查询
	@RequestMapping(value = "/queryHrFundBase", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrFundBase(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try{
			String jsonStr = hrFundBaseService.queryHrFundBase(getPage(paramMap));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
