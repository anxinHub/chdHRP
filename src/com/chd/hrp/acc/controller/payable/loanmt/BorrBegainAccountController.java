package com.chd.hrp.acc.controller.payable.loanmt;

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
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrBegainDetService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrBegainService;
@Controller
public class BorrBegainAccountController extends BaseController{
	private static Logger logger = Logger.getLogger(BorrBegainAccountController.class);	
	
	@Resource(name = "budgBorrBegainService")
	private final BudgBorrBegainService budgBorrBegainService = null;
	
	@Resource(name = "budgBorrBegainDetService")
	private final BudgBorrBegainDetService budgBorrBegainDetService = null;
	
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/account/begainAccountPage", method = RequestMethod.GET)
	public String begainAccountPage(Model mode) throws Exception {

		return "hrp/acc/payable/loanmt/account/account";
	}
	
	
	/**
	 * @Description 记账 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/account/accountBudgBorrApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> accountBudgBorrApply( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String result = "";
		boolean flag = budgBorrBegainService.isAccount(mapVo);
		if(!flag){
			return JSONObject.parseObject("{\"error\":\"期初借款已经记账，不允许再次记账! \"}");
		}
		
		String account_date = mapVo.get("account_date").toString();
		
		mapVo.put("mark_date", DateUtil.stringToDate(account_date,"yyyy-MM-dd"));
		
		mapVo.put("state", 1);
		
		mapVo.put("user_id", SessionManager.getUserId());
		try{
			result = budgBorrBegainService.accountBudgBorrBegain(mapVo);
		
			return JSONObject.parseObject(result);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
}
