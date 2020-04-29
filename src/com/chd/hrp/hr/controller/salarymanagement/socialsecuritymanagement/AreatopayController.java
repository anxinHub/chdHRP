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

import com.alibaba.fastjson.JSON;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.salarymanagement.socialsecuritymanagement.AreatopayService;

@Controller
public class AreatopayController extends BaseController{

	private static Logger logger = Logger.getLogger(AreatopayController.class);

	@Resource(name = "areatopayService")
	private final AreatopayService areatopayService = null;
	
	/**
	 * 缴费项目
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/areatopay/areatopayMainPage", method = RequestMethod.GET)
	public String areatopayMainPage() throws Exception {
		return "hrp/hr/salarymanagement/socialsecuritymanagement/areatopay/areatopayMain";
	}
	
	/**
	 * 社保险种下拉加载
	**/
	@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/areatopay/queryAreatopayInsurtypeSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryAreatopayInsurtypeSelect(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(areatopayService.queryAreatopayInsurtypeSelect(mapVo));
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 缴费基数添加
	**/
	@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/areatopay/addAreapay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAreapay(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(areatopayService.addAreapay(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 社保险种查询
	**/
	@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/areatopay/queryAreatopay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAreatopay(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.parseObject(areatopayService.queryAreatopay(getPage(mapVo)));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 社保险种条件查询下拉加载
	**/
	@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/areatopay/queryAreatopayInsurtypeSelects", method = RequestMethod.POST)
	@ResponseBody
	public String queryAreatopayInsurtypeSelects(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return JSON.toJSONString(areatopayService.queryAreatopayInsurtypeSelects(mapVo));
		} catch (Exception e) {
			return JSON.toJSONString("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 社保险种条件查询下拉加载
	**/
	@RequestMapping(value = "/hrp/hr/salarymanagement/socialsecuritymanagement/areatopay/importAreatopay", method = RequestMethod.POST)
	@ResponseBody
	public String importAreatopay(@RequestParam Map<String, Object> mapVo,Model model) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return areatopayService.importAreatopay(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
}
