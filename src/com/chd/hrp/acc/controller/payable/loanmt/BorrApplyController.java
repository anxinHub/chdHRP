package com.chd.hrp.acc.controller.payable.loanmt;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccEmpAccount;
import com.chd.hrp.acc.entity.payable.BudgBorrApply;
import com.chd.hrp.acc.service.emp.AccEmpAccountService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrApplyDetService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrApplyService;
@Controller
public class BorrApplyController extends BaseController{
	private static Logger logger = Logger.getLogger(BorrApplyController.class);	
	
	@Resource(name = "budgBorrApplyService")
	private final BudgBorrApplyService budgBorrApplyService = null;
	
	@Resource(name = "budgBorrApplyDetService")
	private final BudgBorrApplyDetService budgBorrApplyDetService = null;
	
	@Resource(name = "accEmpAccountService")
	private final AccEmpAccountService accEmpAccountService = null;
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/applyMainPage", method = RequestMethod.GET)
	public String ApplyMainPage(Model mode) throws Exception {

		return "hrp/acc/payable/loanmt/apply/main";
	}
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/applyAddPage", method = RequestMethod.GET)
	public String applyAddPage(Model mode) throws Exception {

		return "hrp/acc/payable/loanmt/apply/add";
	}
	
	
	/**
	 * @Description 添加数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/addBudgBorrApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgBorrApply( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("maker", SessionManager.getUserId());
		String result = "";
		mapVo.put("make_date", new Date());
		try{
			result = budgBorrApplyService.addBudgBorrApply(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(result);
			String apply_code = (String) jsonObj.get("apply_code");
			mapVo.put("apply_code", apply_code);
			budgBorrApplyDetService.delete(mapVo);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		JSONObject jsonObj = JSONArray.parseObject(result);
		String apply_code = (String) jsonObj.get("apply_code");
		
		String assInDetailJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

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
			if("".equals(source_id)){
				continue;
			}
			
			detailVo.put("source_id", source_id.split("\\.")[1]);
			detailVo.put("payment_item_id", payment_item_id.split("@")[0]);
			detailVo.put("payment_item_no", payment_item_id.split("@")[1]);
			try{
				assInDetailJson = budgBorrApplyDetService.addOrUpdate(detailVo);
			}catch(Exception e){
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/budgBorrApplyUpdatePage", method = RequestMethod.GET)
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
		mode.addAttribute("a02002", MyConfig.getSysPara("02002"));
		return "hrp/acc/payable/loanmt/apply/update";
	}

	
	//提交
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/updateSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSubmit( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			
			BudgBorrApply budgBorrApply = budgBorrApplyService.queryBudgBorrApplyByCode(mapVo);
			
			mapVo.put("state", "02");

			if (!budgBorrApply.getState().equals("01")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"操作失败 必须为新建的单据才能提交! \"}");
		}
		try{
			String assInMainJson = budgBorrApplyService.updateSubmitAndWithdraw(listVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	//撤回
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/updateWithdraw", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWithdraw( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			
			BudgBorrApply budgBorrApply = budgBorrApplyService.queryBudgBorrApplyByCode(mapVo);
			
			mapVo.put("state", "01");

			if (!budgBorrApply.getState().equals("02")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"操作失败 必须为提交的单据才能撤回! \"}");
		}
		try{
			String assInMainJson = budgBorrApplyService.updateSubmitAndWithdraw(listVo);
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/deleteBudgBorrApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBorrApply( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			
			BudgBorrApply budgBorrApply = budgBorrApplyService.queryBudgBorrApplyByCode(mapVo);

			if (!budgBorrApply.getState().equals("01")) {
				flag = false;
				break;
			}
						
			listVo.add(mapVo);
		}
		
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 已审核的数据不能删除! \"}");
		}
		try{
			String assInMainJson = budgBorrApplyService.deleteBatchBudgBorrApply(listVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}

	}
	
	//删除明细
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/deleteBudgBorrApplyDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBorrApplyDet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			String budgBorrBegainDetJson = budgBorrApplyDetService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/queryBudgBorrApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBorrApply( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assInMain = budgBorrApplyService.queryBudgBorrApply(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	
	
	/**
	 * @Description 查询职工账号
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/queryGetEmpCardNo", method = RequestMethod.POST)
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
	
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/queryBudgBorrApplyDet", method = RequestMethod.POST)
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
	
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/borrApplyPrintSetPage", method = RequestMethod.GET)
	public String borrApplyPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/apply/queryBorrApplyPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBorrApplyPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	
}
