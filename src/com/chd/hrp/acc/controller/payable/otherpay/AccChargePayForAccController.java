package com.chd.hrp.acc.controller.payable.otherpay;
 
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.payable.BudgChargeApply;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyDetService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyForAccService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
@Controller
public class AccChargePayForAccController extends BaseController{
	private static Logger logger = Logger.getLogger(AccChargePayForAccController.class);	
	
	@Resource(name = "budgChargeApplyService")
	private final BudgChargeApplyService budgChargeApplyService = null;
	
	@Resource(name = "budgChargeApplyForAccService")
	private final BudgChargeApplyForAccService budgChargeApplyForAccService = null;
	
	@Resource(name = "budgChargeApplyDetService")
	private final BudgChargeApplyDetService budgChargeApplyDetService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	@RequestMapping(value = "/hrp/acc/payable/otherpay/payforacc/applyMainPage", method = RequestMethod.GET)
	public String applyMainPage(Model mode) throws Exception {
		
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

		return "hrp/acc/payable/otherpay/payforacc/main";
	}
	

	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/payforacc/budgChargeApplyUpdatePage", method = RequestMethod.GET)
	public String assInMainUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		BudgChargeApply budgChargeApply = budgChargeApplyService.queryBudgChargeApplyByCode(mapVo);

		mode.addAttribute("group_id",	   budgChargeApply.getGroup_id());
		mode.addAttribute("hos_id",	   budgChargeApply.getHos_id());
		mode.addAttribute("copy_code",	   budgChargeApply.getCopy_code());
		mode.addAttribute("apply_code",	   budgChargeApply.getApply_code());
		mode.addAttribute("apply_date",	   DateUtil.dateToString(budgChargeApply.getApply_date(),"yyyy-MM-dd"));
		mode.addAttribute("dept_id",	   budgChargeApply.getDept_id());
		mode.addAttribute("dept_no",   budgChargeApply.getDept_no());
		mode.addAttribute("proj_id",	   budgChargeApply.getProj_id()       	    );
		mode.addAttribute("proj_no",	   budgChargeApply.getProj_no()       	    );
		mode.addAttribute("emp_id",	   budgChargeApply.getEmp_id ()       	    );
		mode.addAttribute("emp_no",	   budgChargeApply.getEmp_no    ()    	    );
		mode.addAttribute("dept_code",	   budgChargeApply.getDept_code());
		mode.addAttribute("dept_name",   budgChargeApply.getDept_name());
		mode.addAttribute("proj_code",	   budgChargeApply.getProj_code()       	    );
		mode.addAttribute("proj_name",	   budgChargeApply.getProj_name()       	    );
		mode.addAttribute("emp_code",	   budgChargeApply.getEmp_code ()       	    );
		mode.addAttribute("emp_name",	   budgChargeApply.getEmp_name    ()    	    );
		mode.addAttribute("payment_amount", budgChargeApply.getPayment_amount ()	    );
		mode.addAttribute("remark",	   budgChargeApply.getRemark ()       	    );
		mode.addAttribute("maker",	   budgChargeApply.getMaker     ()    	    );
		mode.addAttribute("maker_name",	   budgChargeApply.getMaker_name     ()    	    );
		mode.addAttribute("make_date",	   DateUtil.dateToString(budgChargeApply.getMake_date (),"yyyy-MM-dd" )   	    );
		mode.addAttribute("checker",	   budgChargeApply.getChecker    ()   	    );
		mode.addAttribute("checker_name",	   budgChargeApply.getChecker_name    ()   	    );
		mode.addAttribute("check_date",	   DateUtil.dateToString(budgChargeApply.getCheck_date  (),"yyyy-MM-dd")  	    );
		mode.addAttribute("payer", budgChargeApply.getPayer());
		mode.addAttribute("payer_name", budgChargeApply.getPayer_name());
		mode.addAttribute("pay_date", DateUtil.dateToString(budgChargeApply.getPay_date(),"yyyy-MM-dd"));
		mode.addAttribute("state", budgChargeApply.getState());
		mode.addAttribute("state_name",	   budgChargeApply.getState_name());
		mode.addAttribute("pay_way", budgChargeApply.getPay_way());
		mode.addAttribute("pay_way_name", budgChargeApply.getPay_way_name());
		 
		mode.addAttribute("bank_name", budgChargeApply.getBank_name());
		mode.addAttribute("bank_location", budgChargeApply.getBank_location());
		
		mapVo.put("emp_id", budgChargeApply.getEmp_id ());
		mapVo.put("proj_id", budgChargeApply.getProj_id ());
		

		mode.addAttribute("source_id", budgChargeApply.getSource_id());
		

		mode.addAttribute("a02004", MyConfig.getSysPara("02004"));

		mode.addAttribute("source_code", budgChargeApply.getSource_code());
		
		mode.addAttribute("source_name", budgChargeApply.getSource_name());

		mode.addAttribute("payment_item_id", budgChargeApply.getPayment_item_id());
		
		mode.addAttribute("payment_item_no", budgChargeApply.getPayment_item_no());
		
		mode.addAttribute("payment_item_code", budgChargeApply.getPayment_item_code());
		
		mode.addAttribute("payment_item_name", budgChargeApply.getPayment_item_name());
		
		
		return "hrp/acc/payable/otherpay/payforacc/update";
	}


	/**
	 * @Description 确认支付
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/payforacc/confirmBudgChargeApplyForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmBudgChargeApplyForAcc( @RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> paramMapVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("apply_code", ids[3]);
			
			BudgChargeApply budgChargeApply = budgChargeApplyService.queryBudgChargeApplyByCode(mapVo);
			
			mapVo.put("state", "04");
			
			mapVo.put("payer", SessionManager.getUserId());

			mapVo.put("pay_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			
			mapVo.put("pay_way", ids[5]);

			listVo.add(mapVo);
		}
		try{
//			String p02001="0";
//			if(SessionManager.getBudgParaMap().get("02001")!=null){
//				p02001=SessionManager.getBudgParaMap().get("02001").toString();
//			}
//			paramMapVo.put("p02001", p02001);
			
			String str = budgChargeApplyService.confirmBudgChargeApply(listVo,paramMapVo);
			
			return JSONObject.parseObject(str);
			
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	/**
	 * @Description 取消确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/payforacc/unConfirmBudgChargeApplyForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unConfirmBudgChargeApplyForAcc( @RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> paramMapVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("apply_code", ids[3]);
			
			BudgChargeApply budgChargeApply = budgChargeApplyService.queryBudgChargeApplyByCode(mapVo);
			
			mapVo.put("state", "03");
			
			mapVo.put("payer", "");

			mapVo.put("pay_date","");
			
			mapVo.put("pay_way", "");
			
			mapVo.put("vouch_id", "undefined".equals(ids[4]) ? "" : ids[4]);
			
			mapVo.put("log_table", ids[5]);

			if (!budgChargeApply.getState().equals("04")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"取消确认失败 状态必须是确认的才能取消确认! \"}");
		}
		try{
//			String p02001="0";
//			if(SessionManager.getBudgParaMap().get("02001")!=null){
//				p02001=SessionManager.getBudgParaMap().get("02001").toString();
//			}
//			paramMapVo.put("p02001", p02001);
			
			String str = budgChargeApplyService.unConfirmBudgChargeApply(listVo,paramMapVo);
			
			return JSONObject.parseObject(str);
			
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}

	
	
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/payforacc/queryBudgChargeApplyPayForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeApplyPayForAcc( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String str = budgChargeApplyForAccService.queryBudgChargeApplyPayForAcc(getPage(mapVo));

		return JSONObject.parseObject(str);

	}
	
	
	@RequestMapping(value = "/hrp/acc/payable/otherpay/payforacc/queryBudgChargeApplyDetUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeApplyDetUpdate( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String assInMain = budgChargeApplyDetService.queryForUpdate(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	
}
