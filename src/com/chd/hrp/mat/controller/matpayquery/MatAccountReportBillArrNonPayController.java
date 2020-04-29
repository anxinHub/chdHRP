package com.chd.hrp.mat.controller.matpayquery;

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
import com.chd.hrp.mat.service.matpayquery.MatAccountReportBillArrNonPayService;

/**
 * @Description: 票到款未付明细表
 * @Table: 无针对表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAccountReportBillArrNonPayController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatAccountReportBillArrNonPayController.class);
	
	@Resource(name = "matAccountReportBillArrNonPayService")
	private final MatAccountReportBillArrNonPayService matAccountReportBillArrNonPayService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/matBillArrNonPayPage", method = RequestMethod.GET)
	public String matBillArrNonPayPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));

		return "hrp/mat/matpayquery/matBillArrNonPay";
	}
	
	/**
	 * @Description 查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/queryMatAccountReportBillArrNonPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountReportBillArrNonPay(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matAccountReportBillArrNonPayJson="";
		//System.out.println(",,,,,,,,,,,,,"+mapVo.get("show_detail"));
		if(mapVo.get("show_detail").equals("1")){
			matAccountReportBillArrNonPayJson = matAccountReportBillArrNonPayService.queryMatAccountReportBillArrNonPayMain(getPage(mapVo));
		}else{
			matAccountReportBillArrNonPayJson = matAccountReportBillArrNonPayService.queryMatAccountReportBillArrNonPay(getPage(mapVo));
		}
		
		
		return JSONObject.parseObject(matAccountReportBillArrNonPayJson) ; 
	}
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/matPayBillDetailPage", method = RequestMethod.GET)
	public String matPayBillDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));

		return "hrp/mat/matpayquery/matPayBillDetail";
	}
	
	/**
	 * @Description 查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/queryMatPayBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayBillDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matAccountReportBillArrNonPayService.queryMatPayBillDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matJson) ; 
	}
	/**
	 * 票到款未付总表
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/matBillArrNonPaySumPage", method = RequestMethod.GET)
	public String matBillArrNonPaySumPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));

		return "hrp/mat/matpayquery/matBillArrNonPaySum";
	}
	/**
	 * 票到款未付总表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/queryMatAccountReportBillArrNonPaySum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountReportBillArrNonPaySum(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matAccountReportBillArrNonPayService.queryMatAccountReportBillArrNonPaySum(getPage(mapVo));
		
		return JSONObject.parseObject(matJson) ; 
	}
	
	/**
	 * 供应商金额汇总表
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/matSupInPayUnpaidAmountMoneyMainPage", method = RequestMethod.GET)
	public String matSupInPayUnpaidAmountMoneyMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/matpayquery/querySupInPayUnpaidAmountMoneyMain";
	}
	 
	/**
	 * 供应商应付款,已付款,未付款汇总查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpayquery/querySupInPayUnpaidAmountMoney", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupInPayUnpaidAmountMoney(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		
		String matJson = matAccountReportBillArrNonPayService.querySupInPayUnpaidAmountMoney(mapVo);
		
		return JSONObject.parseObject(matJson) ; 
	}
}
