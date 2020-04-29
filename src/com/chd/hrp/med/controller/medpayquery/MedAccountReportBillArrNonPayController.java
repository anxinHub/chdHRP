package com.chd.hrp.med.controller.medpayquery;

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
import com.chd.hrp.med.service.medpayquery.MedAccountReportBillArrNonPayService;

/**
 * @Description: 票到款未付明细表
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedAccountReportBillArrNonPayController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedAccountReportBillArrNonPayController.class);
	
	@Resource(name = "medAccountReportBillArrNonPayService")
	private final MedAccountReportBillArrNonPayService medAccountReportBillArrNonPayService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpayquery/medBillArrNonPayPage", method = RequestMethod.GET)
	public String medBillArrNonPayPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/medpayquery/medBillArrNonPay";
	}
	
	/**
	 * @Description 查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpayquery/queryMedAccountReportBillArrNonPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAccountReportBillArrNonPay(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medAccountReportBillArrNonPayJson="";
		System.out.println(",,,,,,,,,,,,,"+mapVo.get("show_detail"));
		if("1".equals(mapVo.get("show_detail"))){
			medAccountReportBillArrNonPayJson = medAccountReportBillArrNonPayService.queryMedAccountReportBillArrNonPayMain(getPage(mapVo));
		}else{
			medAccountReportBillArrNonPayJson = medAccountReportBillArrNonPayService.queryMedAccountReportBillArrNonPay(getPage(mapVo));
		}
		
		
		return JSONObject.parseObject(medAccountReportBillArrNonPayJson) ; 
	}
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpayquery/medPayBillDetailPage", method = RequestMethod.GET)
	public String medPayBillDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/medpayquery/medPayBillDetail";
	}
	
	/**
	 * @Description 查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpayquery/queryMedPayBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPayBillDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medAccountReportBillArrNonPayService.queryMedPayBillDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medJson) ; 
	}
}
