package com.chd.hrp.acc.controller.InternetBank.cbc;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.InternetBank.cbc.AccCBCPayWageService;

@Controller
@RequestMapping(value = "/hrp/acc/internetbank/cbc/wagepay")
public class AccCBCPayWageController {
	@Resource(name="accCBCPayWageService")
	private final AccCBCPayWageService accCBCPayWageService  = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "accCBCPayWageMainPage", method = RequestMethod.GET)
	public String accAccCBCWageMainPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/cbc/wagepay/accCBCPayWageMain";

	}

	/**
	 * @Description 指令查询界面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "acCCBWageOrderPage", method = RequestMethod.GET)
	public String accAccCBCWageOrderPage(Model mode) throws Exception {

		return "hrp/acc/internetbank/cbc/wagepay/accCBCPayWageOrder";

	}

	/**
	 * @Description 网上银行补录
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "payAccCBCWagePage", method = RequestMethod.GET)
	public String payAccCBCWagePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
	//建行
	return "hrp/acc/internetbank/cbc/wagepay/accCBCPayWage";

	}

 
 

	 

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAccWagePayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWagePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("show_history", SessionManager.getCostParaMap().get("03001"));
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String str = accCBCPayWageService.queryAccWagePay(mapVo);

		return JSONObject.parseObject(str);

	}

	/**
	 * @Description 查询工资支付左面的树形菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAccCBCPayWageTree", method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAccCBCPayWageTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("show_history", SessionManager.getCostParaMap().get("03001"));
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		return accCBCPayWageService.queryAccCBCPayWageTree(mapVo);

	}
	/**
	 * @Description 查询工资支付左面的树形菜单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAccBankForInternet", method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAccBankForInternet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("show_history", SessionManager.getCostParaMap().get("03001"));
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		return accCBCPayWageService.queryAccBankForInternet(mapVo);
		
	}
	/**
	 * @Description 建行银企互联工资支付入口
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "accNetPayWageToCBC", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> AccNetPayWageToCBC(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("opt_user", SessionManager.getUserId());
		String str = "";
		try {
			str = accCBCPayWageService.accNetPayWageToCBC(mapVo);
		} catch (Exception e) {
			str = e.getMessage();
		}
		
		return JSONObject.parseObject(str);
		
	}
	
	/**
	 * @Description 查询工资支付指令状态
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAccCBCWageRd", method = RequestMethod.POST )
	@ResponseBody
	public String queryAccCBCWageRd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String wageRd = accCBCPayWageService.queryAccCBCWageRd(mapVo);
		return wageRd;
		
	}
	/**
	 * @Description 查询工资支付指令状态
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "updateAccCBCPayWage", method = RequestMethod.POST )
	@ResponseBody
	public String updateAccCBCPayWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String wageRd = accCBCPayWageService.updateAccCBCPayWage(mapVo);
		return wageRd;
		
	}
}
