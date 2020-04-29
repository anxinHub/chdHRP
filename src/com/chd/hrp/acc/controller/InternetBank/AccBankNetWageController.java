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
import com.chd.hrp.acc.service.InternetBank.AccBankNetWageService;

/**
 * 
 * @Description: state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款
 * @Table: MAT_PAY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccBankNetWageController extends BaseController {

	private static Logger logger = Logger.getLogger(AccBankNetWageController.class);

	// 引入Service服务
	@Resource(name = "accBankNetWageService")
	private final AccBankNetWageService accBankNetWageService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/accAccBankNetWageMainPage", method = RequestMethod.GET)
	public String accAccBankNetWageMainPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/accbanknetwage/accBankNetWageMain";

	}

	/**
	 * @Description 指令查询界面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/accAccBankNetWageOrderPage", method = RequestMethod.GET)
	public String accAccBankNetWageOrderPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/accbanknetwage/accBankNetWageOrder";

	}

	/**
	 * @Description 明细界面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/queryAccBankNetWageRdPage", method = RequestMethod.GET)
	public String queryAccBankNetWageRdPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("fseqno", mapVo.get("fseqno"));

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		return "hrp/acc/internetbank/accbanknetwage/accBankNetWageRdOrder";

	}
	
	/**
	 * @Description 明细界面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/queryAccBankNetWageFailRdPage", method = RequestMethod.GET)
	public String queryAccBankNetWageFailRdPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		return "hrp/acc/internetbank/accbanknetwage/accBankNetWageFailRdOrder";

	}

	/**
	 * @Description 网上银行补录
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/payAccBankNetWagePage", method = RequestMethod.GET)
	public String payAccBankNetWagePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("wage_code", mapVo.get("wage_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("item_code", mapVo.get("item_code"));
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		
		mode.addAttribute("fseqno", mapVo.get("fseqno"));
		
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
		
		String payFlag = (String)mapVo.get("payFlag");
		
		double totalAmt = accBankNetWageService.sumTotalAmtByDay(mapVo);
		
		double wageAmt = 0.00;
		
		if("0".equals(payFlag)){
			
			wageAmt = accBankNetWageService.sumWage(mapVo);
			
		}else if("1".equals(payFlag)){
			
			wageAmt = accBankNetWageService.totalAmtByFSeqNo(mapVo);
			
		}else if("2".equals(payFlag)){
			
			String paramVo = String.valueOf(mapVo.get("paramVo"));
			
			String[] paraSpl = paramVo.split(",");
			
			StringBuffer paraSb = new StringBuffer();
			
			for(int i = 0 ;i < paraSpl.length; i++){
				
				if(i != 0){
					
					paraSb.append(",");
				}
				
				paraSb.append("'"+paraSpl[i]+"'");
				
			}
			
			 String sql = " and iseqno in ("+paraSb.toString()+") ";
			 
			 mapVo.put("sql", sql);
			 
			 wageAmt = accBankNetWageService.sumTotalAmtByISeqNo(mapVo);
			
		}else if ("3".equals(payFlag)) {

			String paramVo = String.valueOf(mapVo.get("paramVo"));

			String[] paraSpl = paramVo.split(",");

			StringBuffer paraISb = new StringBuffer();
			
			StringBuffer paraFSb = new StringBuffer();
			
			StringBuffer paraIDSb = new StringBuffer();
			
			StringBuffer paraNOSb = new StringBuffer();

			for (int i = 0; i < paraSpl.length; i++) {

				if (i != 0) {

					paraISb.append(",");
					
					paraFSb.append(",");
					
					paraIDSb.append(",");
					
					paraNOSb.append(",");
				}

				paraISb.append("'" + paraSpl[i].split(";")[0] + "'");
				
				paraFSb.append("'" + paraSpl[i].split(";")[1] + "'");
				
				paraIDSb.append("'" + paraSpl[i].split(";")[2] + "'");
				
				paraNOSb.append("'" + paraSpl[i].split(";")[3] + "'");

			}

			String sql = " and fseqno in (" + paraFSb.toString() + ") and iseqno in (" + paraISb.toString() + ") "
					+ " and erpsqn in (" + paraIDSb.toString() + ") ";

			mapVo.put("sql", sql);

			wageAmt = accBankNetWageService.sumTotalAmtByISeqNo(mapVo);

		}
		
		String recaccnoNullWarring = accBankNetWageService.recaccnoNullWarring(mapVo);
		
		mode.addAttribute("totalAmt", totalAmt);
		
		mode.addAttribute("wageAmt", wageAmt);
		
		mode.addAttribute("recaccnoNullWarring", recaccnoNullWarring);

		return "hrp/acc/internetbank/accbanknetwage/accBankNetWageAdd";

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/queryAccBankNetWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String str = accBankNetWageService.queryAccBankNetWage(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/queryAccBankNetWageRd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankNetWageRd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String str = accBankNetWageService.queryAccBankNetWageRd(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/queryAccWagePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWagePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String str = accBankNetWageService.queryAccWagePay(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	/**
	 * @Description 补录银行信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/collectAccBankNetWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccBankNetWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		mapVo.put("settle_user", SessionManager.getUserId());

		String str = "";
		try {
			str = accBankNetWageService.addAccBankNetWage(mapVo);
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
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/updateAccBankNetWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBankNetWage(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		// 添加 list
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("acc_year", ids[3]);
			
			mapVo.put("acc_month", ids[4]);
			
			mapVo.put("fseqno", ids[5]);

			listVo.add(mapVo);
		}
		
		String str = null;
		
		try {
			
			str = accBankNetWageService.updateAccBankNetWage(listVo);
			
		} catch (Exception e) {
			
			str = e.getMessage();
			
		}

		return JSONObject.parseObject(str);
	}

	/**
	 * @Description 查询工资支付左面的树形菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/internetbank/accbanknetwage/queryAccBankNetWageTree", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccBankNetWageTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return accBankNetWageService.queryAccBankNetWageTree(mapVo);

	}
}
