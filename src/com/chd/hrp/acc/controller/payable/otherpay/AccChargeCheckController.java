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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.payable.BudgChargeApply;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyDetService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyService;
@Controller
public class AccChargeCheckController extends BaseController{
	private static Logger logger = Logger.getLogger(AccChargeCheckController.class);	
	
	@Resource(name = "budgChargeApplyService")
	private final BudgChargeApplyService budgChargeApplyService = null;
	
	@Resource(name = "budgChargeApplyDetService")
	private final BudgChargeApplyDetService budgChargeApplyDetService = null;
	
	
	//主页面跳转
	@RequestMapping(value = "/hrp/acc/payable/otherpay/check/applyMainPage", method = RequestMethod.GET)
	public String ApplyMainPage(Model mode) throws Exception {

		return "hrp/acc/payable/otherpay/check/main";
	}
	

	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/check/budgChargeApplyUpdatePage", method = RequestMethod.GET)
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
		mode.addAttribute("a02004", MyConfig.getSysPara("02004"));
		
		mode.addAttribute("source_id", budgChargeApply.getSource_id());
		
		mode.addAttribute("source_code", budgChargeApply.getSource_code());
		
		mode.addAttribute("source_name", budgChargeApply.getSource_name());

		mode.addAttribute("payment_item_id", budgChargeApply.getPayment_item_id());
		
		mode.addAttribute("payment_item_no", budgChargeApply.getPayment_item_no());
		
		mode.addAttribute("payment_item_code", budgChargeApply.getPayment_item_code());
		
		mode.addAttribute("payment_item_name", budgChargeApply.getPayment_item_name());
		
		mode.addAttribute("use_apply_code", budgChargeApply.getUse_apply_code());
		
		mode.addAttribute("isDisplay", "1".equals(MyConfig.getSysPara("02008"))?true:false);
		
		mapVo.put("proj_id",	   budgChargeApply.getProj_id()       	    );
		mapVo.put("payment_item_id",	   budgChargeApply.getPayment_item_id()       	    );
		mapVo.put("source_id",	   budgChargeApply.getSource_id()       	    );
		mapVo.put("use_apply_code",	   budgChargeApply.getUse_apply_code());
		
//		String data = budgChargeApplyService.queryMoneyApplyDet(mapVo);
		
//		Map<String, List> map = ChdJson.fromJsonAsMap(List.class, data);
//		Map<String, Object> mapD = (Map<String,Object>)map.get("Rows").get(0);
//		
//		mode.addAttribute("use_apply_code_up", mapD.get("apply_amount"));
		
		
		
		
		
		/*//用款申请额度
		//预算额度
		//已执行额度
		HashMap<String,Object> mapData = new HashMap<String, Object>();
		mapData.put("group_id", SessionManager.getGroupId());

		mapData.put("hos_id", SessionManager.getHosId());

		mapData.put("copy_code", SessionManager.getCopyCode());
		
		mapData.put("apply_code", budgChargeApply.getUse_apply_code());
		
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
				
			}
			
			//归口科室（取自支出项目归口设置）
		}else {
			mapData.put("flag", "B");
			mapData.put("apply_date", budgPaymentApply.getApply_date().toString().substring(0,4));//申请年度
			mapData.put("year", budgPaymentApply.getApply_date().toString().substring(0,4));//申请年
			mapData.put("proj_id", budgPaymentApply.getProj_id());//项目id
			mapData.put("proj_no", budgPaymentApply.getProj_no());//项目no
		}
		*/
		
		
		
		
		
		
		
		
		
		
		
		return "hrp/acc/payable/otherpay/check/update";
	}


	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/check/auditBudgChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBudgChargeApply( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			
			mapVo.put("checker", SessionManager.getUserId());

			mapVo.put("check_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			if (!budgChargeApply.getState().equals("02")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是提交的才能审核! \"}");
		}
		
		String assInMainJson = "";
		try{
			assInMainJson = budgChargeApplyService.auditBudgChargeApply(listVo);
			
		}catch(Exception e){
			
			assInMainJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/check/reAuditBudgChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditBudgChargeApply( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("apply_code", ids[3]);
			
			BudgChargeApply budgChargeApply = budgChargeApplyService.queryBudgChargeApplyByCode(mapVo);
			
			mapVo.put("state", "02");
			
			mapVo.put("checker", "");
			
			mapVo.put("check_date", "");

			if (!budgChargeApply.getState().equals("03")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"消审失败 状态必须是审核的才能消审! \"}");
		}
		try{
			String assInMainJson = budgChargeApplyService.reAuditBudgChargeApply(listVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	
	/**
	 * @Description 主查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/check/queryBudgChargeApplyCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeApplyCheck( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assInMain = budgChargeApplyService.queryBudgChargeApply(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	
	
	//查询明细
	@RequestMapping(value = "/hrp/acc/payable/otherpay/check/queryBudgChargeApplyDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeApplyDet( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
