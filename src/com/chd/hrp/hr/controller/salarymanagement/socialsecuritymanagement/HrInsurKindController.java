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
import com.chd.hrp.hr.service.salarymanagement.socialSecurityManage.HrInsurKindService;
/**
 * 【薪资管理-社保管理】：社保险种
 * @author yang
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/insurKind")
public class HrInsurKindController extends BaseController {

	private static Logger logger = Logger.getLogger(HrInsurKindController.class);

	// 引入service
	@Resource(name = "hrInsurKindService")
	private final HrInsurKindService hrInsurKindService = null;

	/**
	 * 社保险种主页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrInsurKindMainPage", method = RequestMethod.GET)
	public String mainPage() throws Exception {
		return "hrp/hr/salarymanagement/socialsecuritymanagement/insurKind/hrInsurKindMain";
	}
	
	// 社保险种主查询
	@RequestMapping(value = "/queryInsurKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInsurKind(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception{
		try{
			return hrInsurKindService.queryInsurKind(getPage(mapVo));
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@RequestMapping(value = "/saveInsureKind", method = RequestMethod.POST)
	@ResponseBody
	public String addInsureKind(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception{
		try{
			return hrInsurKindService.saveInsureKind(mapVo);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	@RequestMapping(value = "/deleteInsureKind", method = RequestMethod.POST)
	@ResponseBody
	public String deleteInsureKind(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception{
		try{
			return hrInsurKindService.deleteInsureKind(mapVo);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
}
