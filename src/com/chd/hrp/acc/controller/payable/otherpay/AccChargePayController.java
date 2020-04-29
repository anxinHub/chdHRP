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
import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.payable.BudgChargeApply;
import com.chd.hrp.acc.service.payable.otherpay.AccChargePayService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyDetService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyService;
@Controller
public class AccChargePayController extends BaseController{
	private static Logger logger = Logger.getLogger(AccChargePayController.class);	
	
	@Resource(name = "budgChargeApplyService")
	private final BudgChargeApplyService budgChargeApplyService = null;
	
	@Resource(name = "budgChargeApplyDetService")
	private final BudgChargeApplyDetService budgChargeApplyDetService = null;
	
	@Resource(name = "accChargePayService")
	private final AccChargePayService accChargePayService = null;
	
	@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/applyMainPage", method = RequestMethod.GET)
	public String ApplyMainPage(Model mode) throws Exception {

		return "hrp/acc/payable/otherpay/pay/main";
	}
	
	/**
	 * 【应收管理-其他付款-费用支付】：收款单位/个人   信息修改
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/budgUnitUpdate", method = RequestMethod.GET)
	public String budgUnitUpdate(Model mode) throws Exception {
		return "hrp/acc/payable/otherpay/pay/budgUnitUpdate";
	}
	

	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/budgChargeApplyUpdatePage", method = RequestMethod.GET)
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
		mode.addAttribute("a02004", MyConfig.getSysPara("02004"));
		

		mode.addAttribute("source_id", budgChargeApply.getSource_id());
		


		mode.addAttribute("source_code", budgChargeApply.getSource_code());
		
		mode.addAttribute("source_name", budgChargeApply.getSource_name());

		mode.addAttribute("payment_item_id", budgChargeApply.getPayment_item_id());
		
		mode.addAttribute("payment_item_no", budgChargeApply.getPayment_item_no());
		
		mode.addAttribute("payment_item_code", budgChargeApply.getPayment_item_code());
		
		mode.addAttribute("payment_item_name", budgChargeApply.getPayment_item_name());
		
		
		return "hrp/acc/payable/otherpay/pay/update";
	}


	/**
	 * @Description 确认支付
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/confirmBudgChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmBudgChargeApply( @RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> paramMapVo, Model mode) throws Exception {
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

			if (!budgChargeApply.getState().equals("03")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"确认失败 状态必须是已审核的才能确认! \"}");
		}
		try{
			String p02001="0";
			/*if(SessionManager.getBudgParaMap().get("02001")!=null){
				p02001=SessionManager.getBudgParaMap().get("02001").toString();
			}*/
			p02001 = MyConfig.getSysPara("02001");
			paramMapVo.put("p02001", p02001);
			String assInMainJson = budgChargeApplyService.confirmBudgChargeApply(listVo,paramMapVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	
	/**
	 * @Description 取消支付
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/confirmBudgChargeCancel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmBudgChargeCancel( @RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> paramMapVo, Model mode) throws Exception {
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
			
			mapVo.put("state", "03");//03 已审核
			
			/*mapVo.put("payer", SessionManager.getUserId());
			
			mapVo.put("pay_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			
			mapVo.put("pay_way", ids[5]);*/
			
			if (!budgChargeApply.getState().equals("04")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"确认失败 状态必须是已支付的才能取消确认! \"}");
		}
		try{
			String p02001="0";
			/*if(SessionManager.getBudgParaMap().get("02001")!=null){
				p02001=SessionManager.getBudgParaMap().get("02001").toString();
			}*/
			p02001 = MyConfig.getSysPara("02001");
			paramMapVo.put("p02001", p02001);
			String assInMainJson = budgChargeApplyService.confirmBudgChargeCancel(listVo,paramMapVo);
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
	@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/queryBudgChargeApplyPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeApplyPay( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
	
	/*@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/queryBudgChargeApplyDet", method = RequestMethod.POST)
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
		String p02001="0";
		if(SessionManager.getBudgParaMap().get("02001")!=null){
			p02001=SessionManager.getBudgParaMap().get("02001").toString();
		}
		mapVo.put("p02001", p02001);
		String assInMain = budgChargeApplyService.queryBudgChargeApplyDet(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}*/
	
	
	@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/queryBudgChargeApplyDetUpdate", method = RequestMethod.POST)
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
	
	/**
	 * 更新收款单位
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/updateBudgUnit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgUnit(@RequestParam Map<String, Object> paraMap, Model mode) throws Exception {
		try{
			String resultJson = accChargePayService.updateBudgUnit(paraMap);
			return JSONObject.parseObject(resultJson);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 【应收管理-其他付款-费用支付】：excel导入
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/pay/importBudgChargeApplyState04", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> importBudgChargeApply(@RequestParam Map<String, Object> paraMap, Model mode){
		paraMap.put("state", "04");
		String resultJson = budgChargeApplyService.importBudgChargeApply(paraMap);
		return JSONObject.parseObject(resultJson);
	}
	
}
