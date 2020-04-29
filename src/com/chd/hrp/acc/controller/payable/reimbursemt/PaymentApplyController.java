package com.chd.hrp.acc.controller.payable.reimbursemt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.NumberUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccEmpAccount;
import com.chd.hrp.acc.entity.payable.BudgPaymentApply;
import com.chd.hrp.acc.service.emp.AccEmpAccountService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyDetService;
import com.chd.hrp.acc.service.payable.reimbursemt.BudgPaymentApplyService;
@Controller
public class PaymentApplyController extends BaseController{
	private static Logger logger = Logger.getLogger(PaymentApplyController.class);	
	
	@Resource(name = "budgPaymentApplyService")
	private final BudgPaymentApplyService budgPaymentApplyService = null;
	
	@Resource(name = "budgPaymentApplyDetService")
	private final BudgPaymentApplyDetService budgPaymentApplyDetService = null;
	
	@Resource(name = "accEmpAccountService")
	private final AccEmpAccountService accEmpAccountService = null;
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/applyMainPage", method = RequestMethod.GET)
	public String ApplyMainPage(Model mode) throws Exception {
		mode.addAttribute("isDisplay", "1".equals(MyConfig.getSysPara("02008"))?true:false);
		return "hrp/acc/payable/reimbursemt/apply/main";
	}
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/applyAddPage", method = RequestMethod.GET)
	public String applyAddPage(Model mode) throws Exception {
		HashMap<String,Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("emp_id", SessionManager.getEmpCode()); 
		
		Map<String,Object> map = budgPaymentApplyService.queryAddPageData(mapVo);
		
		mode.addAllAttributes(map);
		return "hrp/acc/payable/reimbursemt/apply/add";
	}
	
	
	/**
	 * @Description 添加数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/addBudgPaymentApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgPaymentApply(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		mapVo.put("payment_amount", "0.0");
		mapVo.put("pay_amount", "0.0");
		mapVo.put("maker", SessionManager.getUserId());
		mapVo.put("make_date", new Date());
		String result = "";
		try {
			result = budgPaymentApplyService.addBudgPaymentApply(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(result);
			String apply_code = (String) jsonObj.get("apply_code");
			mapVo.put("apply_code", apply_code);
			budgPaymentApplyDetService.delete(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		JSONObject jsonObj = JSONArray.parseObject(result);
		String apply_code = (String) jsonObj.get("apply_code");
		String assInDetailJson = "";
		//List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		for (Map<String, Object> detailVo : detail) {
			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}
			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}
			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			detailVo.put("apply_code", apply_code);
			String source_id = detailVo.get("source_id_no") == null ? "" : detailVo.get("source_id_no").toString();
			String payment_item_id = detailVo.get("payment_item_id_no") == null ? "" : detailVo.get("payment_item_id_no").toString();
			if ("".equals(source_id)) {
				continue;
			}

			if (detailVo.get("offset_amount") == null || detailVo.get("offset_amount").toString().equals("0")|| detailVo.get("offset_amount").toString().equals("")) {
				detailVo.put("offset_amount", "0");
				
			}
			detailVo.put("payment_amount", Double.parseDouble(detailVo.get("payment_amount").toString()));
			
			detailVo.put("pay_amount",NumberUtil.sub(Double.parseDouble(detailVo.get("pay_amount").toString()),Double.parseDouble(detailVo.get("offset_amount").toString())));

			if (detailVo.get("amount") == null || detailVo.get("amount").toString().equals("0")|| detailVo.get("amount").toString().equals("")) {
				
				detailVo.put("amount", "0");
			} else {
				detailVo.put("amount", Integer.parseInt(detailVo.get("amount").toString()));
			}

			detailVo.put("source_id", source_id.split("\\.")[1]);
			detailVo.put("payment_item_id", payment_item_id.split("@")[0]);
			detailVo.put("payment_item_no", payment_item_id.split("@")[1]);
			try {
				assInDetailJson = budgPaymentApplyDetService.addOrUpdate(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		
		JSONObject json = JSONArray.parseObject(assInDetailJson);
		json.put("apply_code", apply_code);
		json.put("group_id", SessionManager.getGroupId());
		json.put("hos_id", SessionManager.getHosId());
		json.put("copy_code", SessionManager.getCopyCode());
		return JSONObject.parseObject(json.toJSONString());

	}

	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/budgPaymentApplyUpdatePage", method = RequestMethod.GET)
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
		mode.addAttribute("start_date", DateUtil.dateToString(budgPaymentApply.getStart_date(),"yyyy-MM-dd"));
		mode.addAttribute("end_date", DateUtil.dateToString(budgPaymentApply.getEnd_date(),"yyyy-MM-dd"));
		mode.addAttribute("address", budgPaymentApply.getAddress());
		mode.addAttribute("use_apply_code", budgPaymentApply.getUse_apply_code());
		
		mode.addAttribute("isDisplay", "1".equals(MyConfig.getSysPara("02008"))?true:false);
		mode.addAttribute("p02004", MyConfig.getSysPara("02004"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		return "hrp/acc/payable/reimbursemt/apply/update";
	}

	
	//提交
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/updatePaymentApplySubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePaymentApplySubmit( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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

			if (!budgPaymentApply.getState().equals("01")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"操作失败 必须为新建的单据才能提交! \"}");
		}
		try{
			String assInMainJson = budgPaymentApplyService.updateSubmitAndWithdraw(listVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	//撤回
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/updatePaymentApplyWithdraw", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePaymentApplyWithdraw( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			
			mapVo.put("state", "01");

			if (!budgPaymentApply.getState().equals("02")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"操作失败 必须为提交的单据才能撤回! \"}");
		}
		try{
			String assInMainJson = budgPaymentApplyService.updateSubmitAndWithdraw(listVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	


	/**
	 * @Description 删除数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/deleteBudgPaymentApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgPaymentApply( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

			if (!budgPaymentApply.getState().equals("01")) {
				flag = false;
				break;
			}
						
			listVo.add(mapVo);
		}
		
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 已提交的数据不能删除! \"}");
		}
		try{
			String assInMainJson = budgPaymentApplyService.deleteBatchBudgPaymentApply(listVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	//删除明细
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/deleteBudgPaymentApplyDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgPaymentApplyDet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
			
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
		for ( String id: paramVo.split(",")) {
					
				Map<String, Object> mapVo=new HashMap<String, Object>();
					
				String[] ids=id.split("@");
					
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("apply_code", ids[3])   ;
				mapVo.put("source_id", ids[4])   ;
				mapVo.put("payment_item_id", ids[5]);
					
		      listVo.add(mapVo);
		      
		 }
		try{    
			String budgBorrBegainDetJson = budgPaymentApplyDetService.deleteBatch(listVo);
				
			return JSONObject.parseObject(budgBorrBegainDetJson);
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
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/queryBudgPaymentApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgPaymentApply( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
	
	/**
	 * @Description 查询职工账号
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/queryGetEmpCardNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGetEmpCardNo(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse rsp) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		AccEmpAccount accEmpAccount = accEmpAccountService.queryAccEmpAccountByEmp(mapVo);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("accEmpAccount", accEmpAccount);
		rsp.getWriter().print(jsonObj.toJSONString());
		return null;
	}
	
	
	
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/queryBudgPaymentApplyDet", method = RequestMethod.POST)
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
		String assInMain = budgPaymentApplyDetService.queryForUpdate(getPage(mapVo));
		return JSONObject.parseObject(assInMain);
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/paymentApplyPrintSetPage", method = RequestMethod.GET)
	public String paymentApplyPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	
	/**
	 * @Description 
	 * 打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/queryPaymentApplyPrintTemlate", method = RequestMethod.POST)
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
	
	
	
	// 用户借款申请下拉框  查询自己id下的
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/queryUseApplyCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryUseApplyCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		if (mapVo.get("group_id") == null) {
					
			mapVo.put("group_id", SessionManager.getGroupId());
				
		}
				
		if (mapVo.get("hos_id") == null) {
					
			mapVo.put("hos_id", SessionManager.getHosId());
				
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		/*if(mapVo.get("emp_id") == null) {
			return null;
		}*/
		//mapVo.put("emp_id", SessionManager.getEmpCode());
		
		String dept = budgPaymentApplyService.queryUseApplyCode(mapVo);
			
		return dept;

	}
	
	//queryMoneyApplyDet
	/**
	 * 选择借款号  use_apply_code 自动带出明细信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/queryMoneyApplyDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMoneyApplyDet( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String assInMain = budgPaymentApplyService.queryMoneyApplyDet(mapVo);
		return JSONObject.parseObject(assInMain);
	}
	
	
	/**
	 * @Description 查询职工账号
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/queryGetEmpCardNoSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryGetEmpCardNoSelect(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse rsp) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("emp_id") == null) {
			
			return null;
		}
		
		String data = budgPaymentApplyService.queryGetEmpCardNoSelect(mapVo);

		return data;
	}
	
	/**
	 * excel导入报销申请 
	 */
	@RequestMapping(value = "/hrp/acc/payable/reimbursemt/apply/importBudgPaymentApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importBudgPaymentApply(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception {
		try{
			paraMap.put("state", "01");
			String resultJson = budgPaymentApplyService.importBudgPaymentApply(paraMap);
			return JSONObject.parseObject(resultJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"导入失败.\",\"state\":\"false\"}");
		}
	}
	
}
