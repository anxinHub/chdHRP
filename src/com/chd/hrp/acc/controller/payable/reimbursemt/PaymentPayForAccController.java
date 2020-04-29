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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.payable.BudgBorrDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetDept;
import com.chd.hrp.acc.entity.payable.BudgBorrDetProj;
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.entity.payable.BudgPaymentApply;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyDetService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyForAccService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
@Controller
public class PaymentPayForAccController extends BaseController{
	private static Logger logger = Logger.getLogger(PaymentPayForAccController.class);	
	
	@Resource(name = "budgPaymentApplyService")
	private final BudgPaymentApplyService budgPaymentApplyService = null;
	
	@Resource(name = "budgPaymentApplyForAccService")
	private final BudgPaymentApplyForAccService budgPaymentApplyForAccService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/payforacc/applyMainPage", method = RequestMethod.GET)
	public String ApplyMainPage(Model mode) throws Exception {
		
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

		return "hrp/acc/payable/reimbursemt/payforacc/main";
	}
	
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/payforacc/queryBudgPaymentApplyPayForAcc", method = RequestMethod.POST)
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
		
		String assInMain = budgPaymentApplyForAccService.queryBudgPaymentApplyForAcc(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	

	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/payforacc/budgPaymentApplyUpdatePage", method = RequestMethod.GET)
	public String budgPaymentApplyUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

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
		
		mode.addAttribute("offset_state", mapVo.get("offset_state"));
		
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		
		mode.addAttribute("vouch_date", reMaxVouchDate);
		mode.addAttribute("02004", MyConfig.getSysPara("02004"));
		
		String p02001="0";
		if(MyConfig.getSysPara("02001")!=null){
			p02001=MyConfig.getSysPara("02001").toString();
		}
		
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
		
		return "hrp/acc/payable/reimbursemt/payforacc/update";
	}
	

	/**
	 * @Description 确认支付
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/payforacc/confirmPaymentApplyPayForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmPaymentApplyPayForAcc( @RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> paramMapVo, Model mode) throws Exception {
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
			return JSONObject.parseObject("{\"error\":\"确认失败 状态必须是已提交的才能确认! \"}");
		}
		try{
			String p02001="0";
			if(MyConfig.getSysPara("02001")!=null){
				p02001=MyConfig.getSysPara("02001").toString();
			}
			paramMapVo.put("p02001", p02001);
			String assInMainJson = budgPaymentApplyService.updateConfirmPay(listVo,paramMapVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	
	/**
	 * @Description 取消确认支付
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/payforacc/unConfirmPaymentApplyPayForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unConfirmPaymentApplyPayForAcc( @RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> paramMapVo, Model mode) throws Exception {
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
			
			mapVo.put("busi_type_code", ids[4]);
			mapVo.put("log_table", ids[5]);
			
			mapVo.put("state", "03");
			mapVo.put("payer", "");
			mapVo.put("pay_date","");
			mapVo.put("pay_way", "");

			if (!budgPaymentApply.getState().equals("04")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"取消确认失败 状态必须是已确认的才能取消确认! \"}");
		}
		try{
			String p02001="0";
			if(MyConfig.getSysPara("02001")!=null){
				p02001=MyConfig.getSysPara("02001").toString();
			}
			paramMapVo.put("p02001", p02001);
			String assInMainJson = budgPaymentApplyService.updateUnConfirmPay(listVo,paramMapVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	/**
	 * @Description 
	 * 打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/payforacc/queryPaymentPayPrintTemlateForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPaymentPayPrintTemlateForAcc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
