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
import com.chd.hrp.med.service.medpayquery.MedAccountReportInvArrBillUnArrService;

/**
 * @Description: 货到票未到明细表
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedAccountReportInvArrBillUnArrController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedAccountReportInvArrBillUnArrController.class);
	
	@Resource(name = "medAccountReportInvArrBillUnArrService")
	private final MedAccountReportInvArrBillUnArrService medAccountReportInvArrBillUnArrService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpayquery/medInvArrBillUnArrPage", method = RequestMethod.GET)
	public String medInvArrBillUnArrPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/medpayquery/medInvArrBillUnArr";
	}
	
	/**
	 * @Description 查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/medpayquery/queryMedInvArrBillUnArr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvArrBillUnArr(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medAccountReportInvArrBillUnArrJson = medAccountReportInvArrBillUnArrService.queryMedAccountReportInvArrBillUnArr(getPage(mapVo));
		
		return JSONObject.parseObject(medAccountReportInvArrBillUnArrJson) ; 
	}
}
