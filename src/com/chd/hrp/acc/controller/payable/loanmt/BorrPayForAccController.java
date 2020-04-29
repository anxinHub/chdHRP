package com.chd.hrp.acc.controller.payable.loanmt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.chd.hrp.acc.entity.payable.BudgBorrApply;
import com.chd.hrp.acc.entity.payable.BudgBorrDept;
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrApplyService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrPayForAccService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.mat.service.matpay.MatPayForAccMainService;

@Controller
public class BorrPayForAccController extends BaseController {
	private static Logger logger = Logger.getLogger(BorrPayForAccController.class);

	@Resource(name = "budgBorrApplyService")
	private final BudgBorrApplyService budgBorrApplyService = null;

	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;

	@Resource(name = "budgBorrPayForAccService")
	private final BudgBorrPayForAccService budgBorrPayForAccService = null;
	
	@Resource(name = "budgPaymentApplyService")
	private final BudgPaymentApplyService budgPaymentApplyService = null;
	
	@Resource(name = "matPayForAccMainService")
	private final MatPayForAccMainService matPayForAccMainService = null;

	@RequestMapping(value = "/hrp/acc/payable/loanmt/payforacc/applyMainPage", method = RequestMethod.GET)
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
		
		return "hrp/acc/payable/loanmt/payforacc/main";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/payforacc/queryBudgBorrPayForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBorrPayForAcc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String assInMain = budgBorrPayForAccService.queryBudgBorrPayForAcc(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	
	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/payforacc/budgBorrApplyUpdatePage", method = RequestMethod.GET)
	public String budgBorrApplyUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		BudgBorrApply budgBorrApply = budgBorrApplyService.queryBudgBorrApplyByCode(mapVo);

		mode.addAttribute("group_id",	   budgBorrApply.getGroup_id());
		mode.addAttribute("hos_id",	   budgBorrApply.getHos_id());
		mode.addAttribute("copy_code",	   budgBorrApply.getCopy_code());
		mode.addAttribute("apply_code",	   budgBorrApply.getApply_code());
		mode.addAttribute("apply_date",	   DateUtil.dateToString(budgBorrApply.getApply_date(),"yyyy-MM-dd"));
		mode.addAttribute("dept_id",	   budgBorrApply.getDept_id());
		mode.addAttribute("dept_no",   budgBorrApply.getDept_no());
		mode.addAttribute("proj_id",	   budgBorrApply.getProj_id()       	    );
		mode.addAttribute("proj_no",	   budgBorrApply.getProj_no()       	    );
		mode.addAttribute("emp_id",	   budgBorrApply.getEmp_id ()       	    );
		mode.addAttribute("emp_no",	   budgBorrApply.getEmp_no    ()    	    );
		mode.addAttribute("dept_code",	   budgBorrApply.getDept_code());
		mode.addAttribute("dept_name",   budgBorrApply.getDept_name());
		mode.addAttribute("proj_code",	   budgBorrApply.getProj_code()       	    );
		mode.addAttribute("proj_name",	   budgBorrApply.getProj_name()       	    );
		mode.addAttribute("emp_code",	   budgBorrApply.getEmp_code ()       	    );
		mode.addAttribute("emp_name",	   budgBorrApply.getEmp_name    ()    	    );
		mode.addAttribute("borrow_amount", budgBorrApply.getBorrow_amount ()	    );
		mode.addAttribute("remark",	   budgBorrApply.getRemark ()       	    );
		mode.addAttribute("maker",	   budgBorrApply.getMaker     ()    	    );
		mode.addAttribute("maker_name",	   budgBorrApply.getMaker_name     ()    	    );
		mode.addAttribute("make_date",	   DateUtil.dateToString(budgBorrApply.getMake_date (),"yyyy-MM-dd" )   	    );
		mode.addAttribute("checker",	   budgBorrApply.getChecker    ()   	    );
		mode.addAttribute("checker_name",	   budgBorrApply.getChecker_name    ()   	    );
		mode.addAttribute("check_date",	   DateUtil.dateToString(budgBorrApply.getCheck_date  (),"yyyy-MM-dd")  	    );
		mode.addAttribute("payer", budgBorrApply.getPayer());
		mode.addAttribute("payer_name", budgBorrApply.getPayer_name());
		mode.addAttribute("pay_date", DateUtil.dateToString(budgBorrApply.getPay_date(),"yyyy-MM-dd"));
		mode.addAttribute("state", budgBorrApply.getState());
		mode.addAttribute("state_name",	   budgBorrApply.getState_name());
		mode.addAttribute("pay_way", budgBorrApply.getPay_way());
		mode.addAttribute("pay_way_name", budgBorrApply.getPay_way_name());
		mode.addAttribute("phone", budgBorrApply.getPhone());
		mode.addAttribute("card_no", budgBorrApply.getCard_no());
		
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		mode.addAttribute("vouch_date", reMaxVouchDate);
		mode.addAttribute("a02002", MyConfig.getSysPara("02002"));
		return "hrp/acc/payable/loanmt/payforacc/update";
	}
	
	/**
	 * @Description 
	 * 打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/loanmt/payforacc/queryBorrPayPrintTemlateForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBorrPayPrintTemlateForAcc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=budgBorrApplyService.queryBorrApplyPrintTemlate(mapVo);
		} catch (Exception e) {
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/payforacc/queryBudgBorrApplyDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBorrApplyDet( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assInMain = budgBorrApplyService.queryBudgBorrApplyDet(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}

	@RequestMapping(value = "/hrp/acc/payable/loanmt/payforacc/getBorrowAmount", method = RequestMethod.POST)
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
	 * @Description 确认支付
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/payforacc/confirmPayForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmPayForAcc(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("apply_code", ids[3]);

			BudgBorrApply budgBorrApply = budgBorrApplyService.queryBudgBorrApplyByCode(mapVo);

			mapVo.put("state", "04");

			mapVo.put("payer", SessionManager.getUserId());

			mapVo.put("pay_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("pay_way", ids[5]);

//			if (!budgBorrApply.getState().equals("03")) {
//				flag = false;
//				break;
//			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"确认失败 状态必须是已审核的才能确认! \"}");
		}
		try {
			String assInMainJson = budgBorrApplyService.updateConfirmPay(listVo);
			return JSONObject.parseObject(assInMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

	}
	
	/**
	 * 取消确认
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/payforacc/unConfirmPayForAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unConfirmPayForAcc(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("apply_code", ids[3]);
				mapVo.put("vouch_id", "null".equals(ids[4]) ? "" : ids[4]);
				mapVo.put("log_table", ids[5]);
				
				mapVo.put("payer", "");
				mapVo.put("pay_date", "");
				
				mapVo.put("state", "02");
				
				

	      listVo.add(mapVo);
	      
	    }
	    
		String budgBorrApplyJson = "";
		try{
			budgBorrApplyJson = budgBorrApplyService.unConfirmPay(listVo);
		}catch(Exception e){
			
			budgBorrApplyJson= e.getMessage();
		}
				
			
	    return JSONObject.parseObject(budgBorrApplyJson);
	}
}
