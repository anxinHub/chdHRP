package com.chd.hrp.acc.controller.payable.otherpay;
 
import java.util.ArrayList;
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
import com.chd.hrp.acc.service.emp.AccEmpAccountService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyDetService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyService;
@Controller
public class AccChargeApplyController extends BaseController{
	private static Logger logger = Logger.getLogger(AccChargeApplyController.class);	
	
	@Resource(name = "budgChargeApplyService")
	private final BudgChargeApplyService budgChargeApplyService = null;
	
	@Resource(name = "budgChargeApplyDetService")
	private final BudgChargeApplyDetService budgChargeApplyDetService = null;
	
	@Resource(name = "accEmpAccountService")
	private final AccEmpAccountService accEmpAccountService = null;
	
	/**
	 * 【应收管理-其他付款-费用申请】：主页面
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/applyMainPage", method = RequestMethod.GET)
	public String ApplyMainPage(Model mode) throws Exception {
		mode.addAttribute("isDisplay", "1".equals(MyConfig.getSysPara("02008"))?true:false);
		return "hrp/acc/payable/otherpay/apply/main";
	}
	
	/**
	 * 【应收管理-其他付款-费用申请】：主页面添加
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/applyAddPage", method = RequestMethod.GET)
	public String applyAddPage(Model mode) throws Exception {
		
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//		
//		mapVo.put("group_id", SessionManager.getGroupId());
//		mapVo.put("hos_id", SessionManager.getHosId());
//		mapVo.put("emp_id", SessionManager.getEmpCode());
		
//		BudgChargeApply budgChargeApply = budgChargeApplyService.queryUserDataById(mapVo);
//		
//		mode.addAttribute("emp_id",budgChargeApply.getEmp_id());
//		mode.addAttribute("emp_no",budgChargeApply.getEmp_no());
//		mode.addAttribute("emp_code",budgChargeApply.getEmp_code());
//		mode.addAttribute("emp_name",budgChargeApply.getEmp_name());
//		mode.addAttribute("dept_id",budgChargeApply.getDept_id());
//		mode.addAttribute("dept_no",budgChargeApply.getDept_no());
//		mode.addAttribute("dept_code",budgChargeApply.getDept_code());
//		mode.addAttribute("dept_name",budgChargeApply.getDept_name());
		
		return "hrp/acc/payable/otherpay/apply/add";
	}
	
	
	/**
	 * @Description 添加
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/addBudgChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgChargeApply( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String budgChargeApplyJson = "";
		try{
			budgChargeApplyJson = budgChargeApplyService.addBudgChargeApply(mapVo);
			
		}catch(Exception e){
			
			budgChargeApplyJson = e.getMessage();
			
			logger.error(e.getMessage(),e);
		}
		
		return JSONObject.parseObject(budgChargeApplyJson);

	}

	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/budgChargeApplyUpdatePage", method = RequestMethod.GET)
	public String budgChargeApplyUpdatePage(@RequestParam Map<String, Object> mapVo,
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
		
		return "hrp/acc/payable/otherpay/apply/update";
	}

	
	/**
	 * @Description 修改 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/updateBudgChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgChargeApply( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String budgChargeApplyJson = "";
		try{
			budgChargeApplyJson = budgChargeApplyService.updateBudgChargeApply(mapVo);
			
		}catch(Exception e){
			
			budgChargeApplyJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		return JSONObject.parseObject(budgChargeApplyJson);

	}
	
	
	//提交
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/submitBudgChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitBudgChargeApply( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("apply_code", ids[3]);
			
			mapVo.put("state", "02");

			listVo.add(mapVo);
		}
		
		String assInMainJson = "";

		try{
			assInMainJson = budgChargeApplyService.updateBudgChargeApplyState(listVo);
			
		}catch(Exception e){
			
			assInMainJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		return JSONObject.parseObject(assInMainJson);
		
	}
	
	//撤回
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/unSubmitBudgChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unSubmitBudgChargeApply( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("apply_code", ids[3]);
			
			mapVo.put("state", "01");

			listVo.add(mapVo);
		}
		
		String assInMainJson = null;
		
		try{
			assInMainJson = budgChargeApplyService.updateBudgChargeApplyState(listVo);
			
		}catch(Exception e){
			
			assInMainJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		return JSONObject.parseObject(assInMainJson);
	}
	


	/**
	 * @Description 删除数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/deleteBudgChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgChargeApply( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("apply_code", ids[3]);
			
			listVo.add(mapVo);
		}
		
		String assInMainJson = "";
		
		try{
			assInMainJson = budgChargeApplyService.deleteBatchBudgChargeApply(listVo);
			
		}catch(Exception e){
			
			assInMainJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		return JSONObject.parseObject(assInMainJson);
	}
	
	
	
	//删除明细
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/deleteBudgChargeApplyDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgChargeApplyDet(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
			
		String BudgChargeApplyDetJson = "";
		
		try{    
			BudgChargeApplyDetJson = budgChargeApplyDetService.deleteBudgChargeApplyDet(mapVo);
			
		}catch(Exception e){
			
			BudgChargeApplyDetJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}		
		return JSONObject.parseObject(BudgChargeApplyDetJson);
	}

	/**
	 * @Description 主查询 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/queryBudgChargeApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgChargeApply( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
	
	//明细查询
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/queryBudgChargeApplyDet", method = RequestMethod.POST)
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
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/paymentApplyPrintSetPage", method = RequestMethod.GET)
	public String paymentApplyPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	
	
	//收款单位选择页面跳转
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/accUnitSelectorPage", method = RequestMethod.GET)
	public String accUnitSelectorPage(Model mode) throws Exception {

		return "hrp/acc/payable/otherpay/apply/accUnitSelector";
	}
	
	
	/**
	 * @Description 
	 * 收款单位查询
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/queryBudgUnit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgUnit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String reJson= budgChargeApplyService.queryBudgUnit(getPage(mapVo));
		
		return JSONObject.parseObject(reJson);
	}
	
	
	/**
	 * @Description 
	 * 收款单位删除
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/deleteBudgUnit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgUnit( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("unit_id", ids[2]);
			
			listVo.add(mapVo);
		}
		
		String reJson= budgChargeApplyService.deleteBudgUnit(listVo);
		
		return JSONObject.parseObject(reJson);
	}
	
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/queryBudgUnitSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgUnitSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		return budgChargeApplyService.queryBudgUnitSelect(getPage(mapVo));
	}
	
	
	/**
	 * 根据用户申请号带出数据
	 * @return
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/queryMoneyApplyDet",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> queryMoneyApplyDet(@RequestParam Map<String, Object> mapVo, Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String data = budgChargeApplyService.queryMoneyApplyDet(mapVo);
		
		return JSONObject.parseObject(data);
	}
	
	/**
	 * 【应收管理-其他付款-费用支付】：excel导入
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/importBudgChargeApplySingleState01", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> importBudgChargeApplySingle(@RequestParam Map<String, Object> paraMap, Model mode){
		paraMap.put("state", "01");
		String resultJson = budgChargeApplyService.importBudgChargeApplySingle(paraMap);
		return JSONObject.parseObject(resultJson);
	}
	
	/**
	 * 【应收管理-其他付款-费用支付】：excel导入
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/apply/importBudgChargeApplyState01", method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> importBudgChargeApply(@RequestParam Map<String, Object> paraMap, Model mode){
		paraMap.put("state", "01");
		String resultJson = budgChargeApplyService.importBudgChargeApply(paraMap);
		return JSONObject.parseObject(resultJson);
	}
	
}
