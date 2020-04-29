/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.controller.InternetBank;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.hrp.acc.service.InternetBank.AccBankNetChargeService;
import com.chd.hrp.acc.serviceImpl.AccYearMonthServiceImpl;

/**
 * 
 * @Description: state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款
 * @Table: MAT_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccBankNetChargeController extends BaseController {

	private static Logger logger = Logger.getLogger(AccBankNetChargeController.class);

	// 引入Service服务
	@Resource(name = "accBankNetChargeService")
	private final AccBankNetChargeService accBankNetChargeService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/accBankNetChargeMainPage", method = RequestMethod.GET)
	public String accBankNetChargeMainPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/accbanknetcharge/accBankNetChargeMain";

	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/accBankNetChargeOrderPage", method = RequestMethod.GET)
	public String accBankNetChargeOrderPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/accbanknetcharge/accBankNetChargeOrder";

	}
	
	/**
	 * @Description 明细界面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/queryAccBankNetChargeRdPage", method = RequestMethod.GET)
	public String queryAccBankNetChargeRdPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("recaccnamecn", mapVo.get("recaccnamecn"));
		
		mode.addAttribute("apply_code", mapVo.get("apply_code"));

		mode.addAttribute("group_id", mapVo.get("group_id"));
		
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		return "hrp/acc/internetbank/accbanknetcharge/accBankNetChargeRdOrder";
		
	}

	/**
	 * @Description 查询数据 state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/queryAccChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccChargeApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		System.out.println(SessionManager.getAcctYear());
		String matPayMain = accBankNetChargeService.queryAccChargeApply(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/queryAccBankNetCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matPayMain = accBankNetChargeService.queryAccBankNetCharge(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);

	}
	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/queryAccBankNetChargeRd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetChargeRd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matPayMain = accBankNetChargeService.queryAccBankNetChargeRd(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);

	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/collectAccBankNetChargePage", method = RequestMethod.GET)
	public String collectAccBankNetChargePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("paramVo", mapVo.get("paramVo"));
		
		mode.addAttribute("payFlag", mapVo.get("payFlag"));
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		double totalAmt = accBankNetChargeService.sumTotalAmtByDay(mapVo);
		
		mode.addAttribute("totalAmt", totalAmt);
		
		String wage_year_month = MyConfig.getAccYearMonth(SessionManager.getGroupId(), SessionManager.getHosId(), SessionManager.getCopyCode()).getCurYearMonthWage();
		
		mode.addAttribute("wage_year_month", wage_year_month.substring(0, 4)+wage_year_month.substring(4, 6));
		
		return "hrp/acc/internetbank/accbanknetcharge/accBankNetChargeAdd";

	}

	/**
	 * 网上支付
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/collectAccBankNetCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccBankNetCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null||"".equals(mapVo.get("acc_year"))) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		String matProtocolMainJson = "";
		try {
			matProtocolMainJson =accBankNetChargeService.addAccBankNetCharge(mapVo);
		} catch (Exception e) {

			matProtocolMainJson = e.getMessage();
		}

		return JSONObject.parseObject(matProtocolMainJson);
	}
	
	/**
	 * 添加付款单明细信息
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/updateAccBankNetCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankNetCharge(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		// 添加 list
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		Map<String,String> exitesFseqnoMap = new HashMap<String,String>();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("acc_year", ids[3]);
			
			mapVo.put("fseqno", ids[4]);

			mapVo.put("user_id", SessionManager.getUserId());

			if(exitesFseqnoMap.get(ids[4])==null){
				
				exitesFseqnoMap.put(ids[4], ids[4]);
				
				listVo.add(mapVo);
			}
			
		}

		String str = null;
		
		try {
			
			str = accBankNetChargeService.updateAccBankNetCharge(listVo);
			
		} catch (Exception e) {
			
			str = e.getMessage();
			
		}

		return JSONObject.parseObject(str);
	}
	
	/**
	 * 添加付款单明细信息
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/updateAccBankNetState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankNetState(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		// 添加 list
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("acc_year", ids[3]);
			
			mapVo.put("fseqno", ids[4]);
			
			mapVo.put("unit_id", ids[5]);
			
			mapVo.put("state", ids[6]);

			mapVo.put("user_id", SessionManager.getUserId());

			listVo.add(mapVo);
		}
		
		String str = null;
		
		try {
			
			str = accBankNetChargeService.updateAccBankNetState(listVo);
			
		} catch (Exception e) {
			
			str = e.getMessage();
			
		}

		return JSONObject.parseObject(str);
	}
	
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetcharge/queryAccBankNetChargeNum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetChargeNum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		String matProtocolMainJson = "";
		
		try {
			
			matProtocolMainJson =accBankNetChargeService.queryAccBankNetChargeNum(mapVo);
		
		} catch (Exception e) {

			matProtocolMainJson = e.getMessage();
		}

		return JSONObject.parseObject(matProtocolMainJson);
	}

}
