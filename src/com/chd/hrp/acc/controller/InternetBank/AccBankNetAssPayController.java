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
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.InternetBank.AccBankNetAssPayService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.ass.entity.pay.main.AssPayMain;
import com.chd.hrp.ass.service.pay.main.AssPayMainService;

/**
 * 
 * @Description: state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款
 * @Table: MAT_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccBankNetAssPayController extends BaseController {

	private static Logger logger = Logger.getLogger(AccBankNetAssPayController.class);

	// 引入Service服务
	@Resource(name = "accBankNetAssPayService")
	private final AccBankNetAssPayService accBankNetAssPayService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	@Resource(name = "assPayMainService")
	private final AssPayMainService assPayMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/accBankNetAssPayMainPage", method = RequestMethod.GET)
	public String accBankNetAssPayMainPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/accbanknetasspay/accBankNetAssPayMain";

	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/accBankNetAssPayOrderPage", method = RequestMethod.GET)
	public String accBankNetAssPayOrderPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		
		mode.addAttribute("vouch_date", reMaxVouchDate);

		return "hrp/acc/internetbank/accbanknetasspay/accBankNetAssPayOrder";

	}
	
	/**
	 * @Description 明细界面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/queryAccBankNetAssPayRdPage", method = RequestMethod.GET)
	public String queryAccBankNetAssPayRdPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("erpsqn", mapVo.get("erpsqn"));
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		return "hrp/acc/internetbank/accbanknetasspay/accBankNetAssPayRdOrder";
		
	}

	/**
	 * @Description 查询数据 state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/queryAssPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		


		String matPayMain = accBankNetAssPayService.queryAssPayMain(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/queryAccBankNetAssPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetAssPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matPayMain = accBankNetAssPayService.queryAccBankNetAssPay(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);

	}
	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/queryAccBankNetAssPayRd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetAssPayRd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matPayMain = accBankNetAssPayService.queryAccBankNetAssPayRd(getPage(mapVo));

		return JSONObject.parseObject(matPayMain);

	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/collectAccBankNetAssPayPage", method = RequestMethod.GET)
	public String collectAccBankNetAssPayPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		String payFlag = (String)mapVo.get("payFlag");
		
		mode.addAttribute("paramVo", mapVo.get("paramVo"));
		
		mode.addAttribute("payFlag", payFlag);

		double totalAmt = accBankNetAssPayService.sumTotalAmtByDay(mapVo);
		
		mode.addAttribute("totalAmt", totalAmt);

		return "hrp/acc/internetbank/accbanknetasspay/accBankNetAssPayAdd";

	}

	/**
	 * 网上支付
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/collectAccBankNetAssPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccBankNetAssPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
			mapVo.put("acc_year", SessionManager.getAcctYear());

		String matProtocolMainJson = "";
		try {
			matProtocolMainJson =accBankNetAssPayService.addAccBankNetAssPay(mapVo);
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
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/updateAccBankNetAssPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankNetAssPay(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			
			mapVo.put("vouch_id", ids[5]);
			
			mapVo.put("vouch_state", ids[6]);

			mapVo.put("user_id", SessionManager.getUserId());
			
			mapVo.put("log_table", "ACC_BUSI_LOG_050402");

			listVo.add(mapVo);
		}
		
		String str = null;
		
		try {
			
			str = accBankNetAssPayService.updateAccBankNetAssPay(listVo);
			
		} catch (Exception e) {
			
			str = e.getMessage();
			
		}

		return JSONObject.parseObject(str);
	}
	
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/assPayMainUpdatePage", method = RequestMethod.GET)
	public String assPayMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssPayMain assPayMain = new AssPayMain();

		assPayMain = assPayMainService.queryByCode(mapVo);

		mode.addAttribute("group_id", assPayMain.getGroup_id());
		mode.addAttribute("hos_id", assPayMain.getHos_id());
		mode.addAttribute("copy_code", assPayMain.getCopy_code());
		mode.addAttribute("pay_no", assPayMain.getPay_no());
		mode.addAttribute("pay_date", DateUtil.dateToString(assPayMain.getPay_date(), "yyyy-MM-dd"));
		mode.addAttribute("ven_id", assPayMain.getVen_id());
		mode.addAttribute("ven_no", assPayMain.getVen_no());
		mode.addAttribute("ven_code", assPayMain.getVen_code());
		mode.addAttribute("ven_name", assPayMain.getVen_name());
		mode.addAttribute("pay_money", assPayMain.getPay_money());
		mode.addAttribute("state", assPayMain.getState());
		mode.addAttribute("create_emp", assPayMain.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assPayMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assPayMain.getAudit_emp());
		mode.addAttribute("audit_date", assPayMain.getAudit_date());
		mode.addAttribute("note", assPayMain.getNote());

		return "hrp/acc/internetbank/accbanknetasspay/accBankNetAssPayUpdate";
	}
	
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetasspay/queryAccBankNetAssPayNum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetAssPayNum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			
			matProtocolMainJson =accBankNetAssPayService.queryAccBankNetAssPayNum(mapVo);
		
		} catch (Exception e) {

			matProtocolMainJson = e.getMessage();
		}

		return JSONObject.parseObject(matProtocolMainJson);
	}
	
}
