package com.chd.hrp.acc.controller.payable.reimbursemt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.payable.BudgBorrDept;
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.entity.payable.BudgPaymentApply;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyDetService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyService;
@Controller
public class PaymentCheckController extends BaseController{
	private static Logger logger = Logger.getLogger(PaymentCheckController.class);	
	
	@Resource(name = "budgPaymentApplyService")
	private final BudgPaymentApplyService budgPaymentApplyService = null;
	
	@Resource(name = "budgPaymentApplyDetService")
	private final BudgPaymentApplyDetService budgPaymentApplyDetService = null;
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/check/applyMainPage", method = RequestMethod.GET)
	public String ApplyMainPage(Model mode) throws Exception {

		mode.addAttribute("isDisplay", "1".equals(MyConfig.getSysPara("02008"))?true:false);
		return "hrp/acc/payable/reimbursemt/check/main";
	}
	

	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/check/budgPaymentApplyUpdatePage", method = RequestMethod.GET)
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
		mode.addAttribute("use_apply_code", budgPaymentApply.getUse_apply_code());
		
		
		mode.addAttribute("isDisplay", "1".equals(MyConfig.getSysPara("02008"))?true:false);
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		return "hrp/acc/payable/reimbursemt/check/update";
	}


	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/check/updateToExaminePaymentApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExaminePaymentApply( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			
			mapVo.put("state", "03");
			
			mapVo.put("checker", SessionManager.getUserId());

			mapVo.put("check_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			if (!budgPaymentApply.getState().equals("02")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是提交的才能审核! \"}");
		}
		try{
			String assInMainJson = budgPaymentApplyService.updateToExamine(listVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/check/updateNotToExaminePaymentApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExaminePaymentApply( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			
			BudgPaymentApply budgPaymentApply = budgPaymentApplyService.queryBudgPaymentApplyByCode(mapVo);
			
			mapVo.put("state", "02");
			
			mapVo.put("checker", "");
			
			mapVo.put("check_date", "");

			if (!budgPaymentApply.getState().equals("03")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"消审失败 状态必须是审核的才能消审! \"}");
		}
		try{
			String assInMainJson = budgPaymentApplyService.updateNotToExamine(listVo);
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
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/check/queryBudgPaymentApplyCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPaymentApplyCheck( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/check/queryBudgPaymentApplyDet", method = RequestMethod.POST)
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
		
		String data = budgPaymentApplyService.queryBudgPaymentApply(getPage(mapVo));
		data= data.replace("[", "").replace("]", "");
		JSONObject list = JSONObject.parseObject(data);
		Map<String,Object> map = (Map<String, Object>) list.get("Rows");
		
		
		String p02007 = MyConfig.getSysPara("02007");
		
		//预算额度:若项目不为空，根据申请年度、项目、资金来源、支出项目取项目预算分解BUDG_PROJ_APPLY_RESOLVE中预算金额合计；
		//预算额度:若项目为空，根据02007预算控制层次、申请年月、支出项目、归口科室（取自支出项目归口设置）从科室其他费用预算BUDG_EXPENSE中取支出预算合计。
		
		//已执行额度:若项目不为空，根据申请年度、项目、资金来源、支出项目取报销申请和费用支付申请中报销金额和付款金额合计值；
		//已执行额度:若项目为空，根据02007预算控制层次、申请年月、归口科室（取自支出项目归口设置）、支出项目取报销申请和费用支付申请中报销金额和付款金额合计值
		if (map.get("proj_id")==null || "".equals(map.get("proj_id"))) {
			
			mapVo.put("flag", "A");
			
			if ("1".equals(p02007)) {
				
				//{"code":1,"value":"科室月"},
				mapVo.put("year", map.get("apply_date").toString().substring(0,4));//申请年
				mapVo.put("month", map.get("apply_date").toString().substring(5,7));//申请月
				mapVo.put("yearMonth", map.get("apply_date").toString().substring(0,4)+map.get("apply_date").toString().substring(5,7));//申请月
				mapVo.put("dept_id", map.get("dept_id"));
				mapVo.put("dept_no", map.get("dept_no"));
				
			}else if ("2".equals(p02007)) {
				
				//{"code":2,"value":"医院年"},
				mapVo.put("year", map.get("apply_date").toString().substring(0,4));//申请年
				
			}else if ("3".equals(p02007)) {
				
				//{"code":3,"value":"按医院月"}
				mapVo.put("year", map.get("apply_date").toString().substring(0,4));//申请年
				mapVo.put("month", map.get("apply_date").toString().substring(5,7));//申请月
				mapVo.put("yearMonth", map.get("apply_date").toString().substring(0,4)+map.get("apply_date").toString().substring(5,7));//申请月
				
			}else{
				//{"code":0,"value":"科室年"},
				mapVo.put("year", map.get("apply_date").toString().substring(0,4));
				mapVo.put("dept_id", map.get("dept_id"));
				mapVo.put("dept_no", map.get("dept_no"));
			}
			
			mapVo.put("copy_code", map.get(""));//归口科室（取自支出项目归口设置）
		}else {
			mapVo.put("flag", "B");
			mapVo.put("year", map.get("apply_date").toString().substring(0,4));//申请年
			mapVo.put("proj_id", map.get("proj_id"));//项目id
			mapVo.put("proj_no", map.get("proj_no"));//项目no
		}
		
		
		
		String assInMain = budgPaymentApplyDetService.queryForUpdate(getPage(mapVo));

		return JSONObject.parseObject(assInMain);
	}
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/check/getBorrowAmount", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/check/queryPaymentCheckPrintTemlate", method = RequestMethod.POST)
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
