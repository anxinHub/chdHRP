package com.chd.hrp.acc.controller.payable.reimbursemt;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.payable.BudgBorrDept;
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.entity.payable.BudgPaymentApply;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyDetService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyService;
@Controller
public class PaymentPayController extends BaseController{
	private static Logger logger = Logger.getLogger(PaymentPayController.class);	
	
	@Resource(name = "budgPaymentApplyService")
	private final BudgPaymentApplyService budgPaymentApplyService = null;
	
	@Resource(name = "budgPaymentApplyDetService")
	private final BudgPaymentApplyDetService budgPaymentApplyDetService = null;
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/pay/applyMainPage", method = RequestMethod.GET)
	public String ApplyMainPage(Model mode) throws Exception {

		return "hrp/acc/payable/reimbursemt/pay/main";
	}
	

	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/pay/budgPaymentApplyUpdatePage", method = RequestMethod.GET)
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

		BudgPaymentApply budgPaymentApply = budgPaymentApplyService.queryBudgPaymentApplyByCode(mapVo);

		mode.addAttribute("group_id",	   budgPaymentApply.getGroup_id());
		mode.addAttribute("hos_id",	   budgPaymentApply.getHos_id());
		mode.addAttribute("copy_code",	   budgPaymentApply.getCopy_code());
		mode.addAttribute("apply_code",	   budgPaymentApply.getApply_code());
		mode.addAttribute("apply_date",	   DateUtil.dateToString(budgPaymentApply.getApply_date(),"yyyy-MM-dd"));
		mode.addAttribute("dept_id",	   budgPaymentApply.getDept_id());
		mode.addAttribute("dept_no",   budgPaymentApply.getDept_no());
		mode.addAttribute("proj_id",	   budgPaymentApply.getProj_id()       	    );
		mode.addAttribute("proj_no",	   budgPaymentApply.getProj_no()       	    );
		mode.addAttribute("emp_id",	   budgPaymentApply.getEmp_id ()       	    );
		mode.addAttribute("emp_no",	   budgPaymentApply.getEmp_no    ()    	    );
		mode.addAttribute("dept_code",	   budgPaymentApply.getDept_code());
		mode.addAttribute("dept_name",   budgPaymentApply.getDept_name());
		mode.addAttribute("proj_code",	   budgPaymentApply.getProj_code()       	    );
		mode.addAttribute("proj_name",	   budgPaymentApply.getProj_name()       	    );
		mode.addAttribute("emp_code",	   budgPaymentApply.getEmp_code ()       	    );
		mode.addAttribute("emp_name",	   budgPaymentApply.getEmp_name    ()    	    );
		mode.addAttribute("payment_amount", budgPaymentApply.getPayment_amount ()	    );
		mode.addAttribute("offset_amount", budgPaymentApply.getOffset_amount ()	    );
		mode.addAttribute("pay_amount", budgPaymentApply.getPay_amount ()	    );
		mode.addAttribute("remark",	   budgPaymentApply.getRemark ()       	    );
		mode.addAttribute("maker",	   budgPaymentApply.getMaker     ()    	    );
		mode.addAttribute("maker_name",	   budgPaymentApply.getMaker_name     ()    	    );
		mode.addAttribute("make_date",	   DateUtil.dateToString(budgPaymentApply.getMake_date (),"yyyy-MM-dd" )   	    );
		mode.addAttribute("checker",	   budgPaymentApply.getChecker    ()   	    );
		mode.addAttribute("checker_name",	   budgPaymentApply.getChecker_name    ()   	    );
		mode.addAttribute("check_date",	   DateUtil.dateToString(budgPaymentApply.getCheck_date  (),"yyyy-MM-dd")  	    );
		mode.addAttribute("payer", budgPaymentApply.getPayer());
		mode.addAttribute("payer_name", budgPaymentApply.getPayer_name());
		mode.addAttribute("pay_date", DateUtil.dateToString(budgPaymentApply.getPay_date(),"yyyy-MM-dd"));
		mode.addAttribute("state", budgPaymentApply.getState());
		mode.addAttribute("state_name",	   budgPaymentApply.getState_name());
		mode.addAttribute("pay_way", budgPaymentApply.getPay_way());
		mode.addAttribute("pay_way_name", budgPaymentApply.getPay_way_name());
		mode.addAttribute("phone", budgPaymentApply.getPhone());
		mode.addAttribute("card_no", budgPaymentApply.getCard_no());
		
		String p02001="0";
		/*if(SessionManager.getBudgParaMap().get("02001")!=null){
			p02001=SessionManager.getBudgParaMap().get("02001").toString();
		}*/
		p02001 = MyConfig.getSysPara("02001");
		mapVo.put("emp_id", budgPaymentApply.getEmp_id ());
		mapVo.put("proj_id", budgPaymentApply.getProj_id ());
		
		if(budgPaymentApply.getProj_id() == null){
			BudgBorrDept budgBorrDept = budgPaymentApplyService.queryBudgBorrDeptByCode(mapVo);
			if(budgBorrDept == null && p02001.equals("0")){
				mode.addAttribute("is_yes", "0");
			}else{
				mode.addAttribute("is_yes", "1");
			}
		}else{
			BudgBorrProj budgBorrProj = budgPaymentApplyService.queryBudgBorrProjByCode(mapVo);
			if(budgBorrProj == null && p02001.equals("0")){
				mode.addAttribute("is_yes", "0");
			}else{
				mode.addAttribute("is_yes", "1");
			}
		}
		
		
		//项目总预算
		//可用余额
		//到账可用余额
		HashMap<String,Object> mapData = new HashMap<String, Object>();
		mapData.put("group_id", SessionManager.getGroupId());

		mapData.put("hos_id", SessionManager.getHosId());

		mapData.put("copy_code", SessionManager.getCopyCode());
		
		mapData.put("apply_code", budgPaymentApply.getApply_code());
		
		String p02007 = MyConfig.getSysPara("02007");
		if (budgPaymentApply.getProj_id()==null || "".equals(budgPaymentApply.getProj_id())) {
			
			mapData.put("flag", "A");
			
			if ("0".equals(p02007)) {
				
				//{"code":0,"value":"科室年"},
				mapData.put("year", budgPaymentApply.getApply_date().toString().substring(0,4));
				mapData.put("dept_id", budgPaymentApply.getDept_id());
				mapData.put("dept_no", budgPaymentApply.getDept_no());
				
			}else if ("1".equals(p02007)) {
				
				//{"code":1,"value":"科室月"},
				mapData.put("year", budgPaymentApply.getApply_date().toString().substring(0,4));//申请年
				mapData.put("month", budgPaymentApply.getApply_date().toString().substring(5,7));//申请月
				mapData.put("yearMonth", budgPaymentApply.getApply_date().toString().substring(0,4)+budgPaymentApply.getApply_date().toString().substring(5,7));//申请月
				mapData.put("dept_id", budgPaymentApply.getDept_id());
				mapData.put("dept_no", budgPaymentApply.getDept_no());
				
			}else if ("2".equals(p02007)) {
				
				//{"code":2,"value":"医院年"},
				mapData.put("year", budgPaymentApply.getApply_date().toString().substring(0,4));//申请年
				
			}else if ("3".equals(p02007)) {
				
				//{"code":3,"value":"按医院月"}
				mapData.put("year", budgPaymentApply.getApply_date().toString().substring(0,4));//申请年
				mapData.put("month", budgPaymentApply.getApply_date().toString().substring(5,7));//申请月
				mapData.put("yearMonth", budgPaymentApply.getApply_date().toString().substring(0,4)+budgPaymentApply.getApply_date().toString().substring(5,7));//申请月
				
			}else{
				mapData.put("year", budgPaymentApply.getApply_date().toString().substring(0,4));
			}
			
			//归口科室（取自支出项目归口设置）
		}else {
			mapData.put("flag", "B");
			mapData.put("apply_date", budgPaymentApply.getApply_date().toString().substring(0,4));//申请年度
			mapData.put("year", budgPaymentApply.getApply_date().toString().substring(0,4));//申请年
			mapData.put("proj_id", budgPaymentApply.getProj_id());//项目id
			mapData.put("proj_no", budgPaymentApply.getProj_no());//项目no
		}
		
		Map<String,Object> map =budgPaymentApplyService.queryBudgPro(mapData);
		
		mode.addAttribute("pro_budg_money", map.get("pro_budg_money"));
		mode.addAttribute("balance", map.get("balance"));
		mode.addAttribute("Account_balance", map.get("Account_balance"));
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		return "hrp/acc/payable/reimbursemt/pay/update";
	}


	/**
	 * @Description 确认支付
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/pay/confirmPaymentApplyPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmPaymentApplyPay(@RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> paramMapVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("apply_code", ids[3]);
			
			BudgPaymentApply budgPaymentApply = budgPaymentApplyService.queryBudgPaymentApplyByCode(mapVo);
			
			mapVo.put("state", "04");
			
			mapVo.put("payer", SessionManager.getUserId());

			mapVo.put("pay_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			
			mapVo.put("pay_way", ids[5]);

			if (!budgPaymentApply.getState().equals("03")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"确认失败 状态必须是已审核的才能确认! \"}");
		}
		try{
			
			//SysConfigServiceImpl
			
			
			String p02001="0";
			/*if(SessionManager.getBudgParaMap().get("02001")!=null){
				p02001=SessionManager.getBudgParaMap().get("02001").toString();
			}*/
			p02001 = MyConfig.getSysPara("02001");
			paramMapVo.put("p02001", p02001);
			String assInMainJson = budgPaymentApplyService.updateConfirmPay(listVo,paramMapVo);
			return JSONObject.parseObject(assInMainJson);
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
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/pay/queryBudgPaymentApplyPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPaymentApplyPay( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assInMain = budgPaymentApplyService.queryBudgPaymentApply(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/pay/queryBudgPaymentApplyDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPaymentApplyDet( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String p02001="0";
		/*if(SessionManager.getBudgParaMap().get("02001")!=null){
			p02001=SessionManager.getBudgParaMap().get("02001").toString();
		}*/
		p02001 = MyConfig.getSysPara("02001");
		mapVo.put("p02001", p02001);
		String assInMain = budgPaymentApplyService.queryBudgPaymentApplyDet(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/pay/queryBudgPaymentApplyDetUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPaymentApplyDetUpdate( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String assInMain = budgPaymentApplyDetService.queryForUpdate(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	
	
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/pay/getBorrowAmount", method = RequestMethod.POST)
	@ResponseBody
	public String getBorrowAmount( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		JSONObject json = new JSONObject();
		
		if(mapVo.get("proj_id") == null || mapVo.get("proj_id").toString().equals("")){
			BudgBorrDept budgBorrDept = budgPaymentApplyService.queryBudgBorrDeptByCode(mapVo);
			json.put("result", budgBorrDept);
		}else{
			BudgBorrProj budgBorrProj = budgPaymentApplyService.queryBudgBorrProjByCode(mapVo);
			json.put("result", budgBorrProj);
		}

		return json.toJSONString();
	}
	
	/**
	 * @Description 
	 * 打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/pay/queryPaymentPayPrintTemlateForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPaymentApplyPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson = budgPaymentApplyService.queryBorrPaymentApplyPrintTemlate(mapVo);
		} catch (Exception e) {
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
}
