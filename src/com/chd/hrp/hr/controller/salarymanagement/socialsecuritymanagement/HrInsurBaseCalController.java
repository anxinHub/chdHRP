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
import com.chd.hrp.hr.service.salarymanagement.socialSecurityManage.HrInsurBaseCalService;
/**
 * 【薪资管理-社保管理】：缴费基数设置
 * @author yang
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/insurBaseCal")
public class HrInsurBaseCalController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrInsurBaseCalController.class);
	
	// service
	@Resource(name = "hrInsurBaseCalService")
	private final HrInsurBaseCalService hrInsurBaseCalService = null;
	
	// 主页面
	@RequestMapping(value = "/insurBaseCalSetMainPage", method = RequestMethod.GET)
	public String mainPage() throws Exception {
		return "hrp/hr/salarymanagement/socialsecuritymanagement/insurBaseCal/hrInsurBaseCalSetMain";
	}
	
	// 公式设置页面
	@RequestMapping(value = "/insurBaseCalEditPage", method = RequestMethod.GET)
	public String insurBaseCalEditPage() throws Exception{
		return "hrp/hr/salarymanagement/socialsecuritymanagement/insurBaseCal/hrInsurBaseCalEdit";
	}
	
	// 查询
	@RequestMapping(value = "/queryHrInsurBaseCal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrInsurBaseCal(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try{
			String jsonStr = hrInsurBaseCalService.queryHrInsurBaseCal(getPage(paramMap));
			return JSONObject.parseObject(jsonStr);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 删除
	@RequestMapping(value = "/deleteHrInsurBaseCal", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrInsurBaseCal(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try {
			return hrInsurBaseCalService.deleteHrInsurBaseCal(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 添加
	@RequestMapping(value = "/addHrInsurBaseCal", method = RequestMethod.POST)
	@ResponseBody
	public String addHrInsurBaseCal(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try {
			return hrInsurBaseCalService.addHrInsurBaseCal(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 保存（更新）公式
	@RequestMapping(value = "/updateHrInsurBaseCal", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrInsurBaseCal(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		try {
			return hrInsurBaseCalService.updateHrInsurBaseCal(paramMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	// 公式设置 左侧树
	@RequestMapping(value = "/queryInsurBaseSetFunTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInsurBaseSetFunTree(@RequestParam Map<String, Object> paramMap, Model mode) throws Exception{
		String json = hrInsurBaseCalService.queryInsurBaseSetFunTree(paramMap);
		return JSONObject.parseObject(json);
	}

}
