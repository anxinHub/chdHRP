package com.chd.hrp.acc.controller.payable.loanmt;

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
import com.chd.hrp.acc.entity.payable.BudgBorrProj;
import com.chd.hrp.acc.entity.payable.BudgBorrReturn;
import com.chd.hrp.acc.service.payable.base.BudgBorrDeptService;
import com.chd.hrp.acc.service.payable.base.BudgBorrDetDeptService;
import com.chd.hrp.acc.service.payable.base.BudgBorrDetProjService;
import com.chd.hrp.acc.service.payable.base.BudgBorrProjService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrReturnDetService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrReturnService;
@Controller
public class BorrReturnController extends BaseController{
	private static Logger logger = Logger.getLogger(BorrReturnController.class);	
	
	@Resource(name = "budgBorrReturnService")
	private final BudgBorrReturnService budgBorrReturnService = null;
	
	@Resource(name = "budgBorrReturnDetService")
	private final BudgBorrReturnDetService budgBorrReturnDetService = null;
	
	@Resource(name = "budgBorrDeptService")
	private final BudgBorrDeptService budgBorrDeptService = null;
	
	@Resource(name = "budgBorrProjService")
	private final BudgBorrProjService budgBorrProjService = null;
	
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/returnMainPage", method = RequestMethod.GET)
	public String returnMainPage(Model mode) throws Exception {

		return "hrp/acc/payable/loanmt/return/main";
	}
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/returnAddPage", method = RequestMethod.GET)
	public String returnAddPage(Model mode) throws Exception {

		return "hrp/acc/payable/loanmt/return/add";
	}
	
	
	/**
	 * @Description 添加数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/addBudgBorrReturn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgBorrReturn( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		mapVo.put("make_date", new Date());
		String result = "";
		try{
			result = budgBorrReturnService.addBudgBorrReturn(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(result);
			String return_code = (String) jsonObj.get("return_code");
			mapVo.put("return_code", return_code);
			budgBorrReturnDetService.delete(mapVo);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		JSONObject jsonObj = JSONArray.parseObject(result);
		String return_code = (String) jsonObj.get("return_code");
		
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
			
			if (detailVo.get("return_amount") == null || detailVo.get("return_amount").equals("") || detailVo.get("return_amount").equals("0")){
				continue;
			}
			
			detailVo.put("return_code", return_code);
			 

			String source_id = detailVo.get("source_id_no") == null ? "" : detailVo.get("source_id_no").toString();
			
			String payment_item_id = detailVo.get("payment_item_id_no") == null ? "" : detailVo.get("payment_item_id_no").toString();
			if("".equals(source_id)){
				continue;
			}
			
			detailVo.put("source_id", source_id.split("\\.")[1]);
			detailVo.put("payment_item_id", payment_item_id.split("@")[0]);
			detailVo.put("payment_item_no", payment_item_id.split("@")[1]);
			try{
				assInDetailJson = budgBorrReturnDetService.addOrUpdate(detailVo);
			}catch(Exception e){
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}
		}
		JSONObject json = JSONArray.parseObject(assInDetailJson);
		json.put("return_code", return_code);
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/budgBorrReturnUpdatePage", method = RequestMethod.GET)
	public String budgBorrReturnUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		BudgBorrReturn budgBorrReturn = budgBorrReturnService.queryBudgBorrReturnByCode(mapVo);

		mode.addAttribute("group_id",	   budgBorrReturn.getGroup_id());
		mode.addAttribute("hos_id",	   budgBorrReturn.getHos_id());
		mode.addAttribute("copy_code",	   budgBorrReturn.getCopy_code());
		mode.addAttribute("return_code",	   budgBorrReturn.getReturn_code());
		mode.addAttribute("return_date",	   DateUtil.dateToString(budgBorrReturn.getReturn_date(),"yyyy-MM-dd"));
		mode.addAttribute("dept_id",	   budgBorrReturn.getDept_id());
		mode.addAttribute("dept_no",   budgBorrReturn.getDept_no());
		mode.addAttribute("proj_id",	   budgBorrReturn.getProj_id()       	    );
		mode.addAttribute("proj_no",	   budgBorrReturn.getProj_no()       	    );
		mode.addAttribute("emp_id",	   budgBorrReturn.getEmp_id ()       	    );
		mode.addAttribute("emp_no",	   budgBorrReturn.getEmp_no    ()    	    );
		mode.addAttribute("dept_code",	   budgBorrReturn.getDept_code());
		mode.addAttribute("dept_name",   budgBorrReturn.getDept_name());
		mode.addAttribute("proj_code",	   budgBorrReturn.getProj_code()       	    );
		mode.addAttribute("proj_name",	   budgBorrReturn.getProj_name()       	    );
		mode.addAttribute("emp_code",	   budgBorrReturn.getEmp_code ()       	    );
		mode.addAttribute("emp_name",	   budgBorrReturn.getEmp_name    ()    	    );
		mode.addAttribute("return_amount", budgBorrReturn.getReturn_amount ()	    );
		mode.addAttribute("maker",	   budgBorrReturn.getMaker     ()    	    );
		mode.addAttribute("maker_name",	   budgBorrReturn.getMaker_name     ()    	    );
		mode.addAttribute("make_date",	   DateUtil.dateToString(budgBorrReturn.getMake_date (),"yyyy-MM-dd" )   	    );
		mode.addAttribute("operator",	   budgBorrReturn.getOperator()   	    );
		mode.addAttribute("operator_name",	   budgBorrReturn.getOperator_name    ()   	    );
		mode.addAttribute("state", budgBorrReturn.getState());
		mode.addAttribute("state_name",	   budgBorrReturn.getState_name());
		mode.addAttribute("pay_way", budgBorrReturn.getPay_way());
		mode.addAttribute("pay_way_name", budgBorrReturn.getPay_way_name());
		mode.addAttribute("a02003", MyConfig.getSysPara("02003")); 
		return "hrp/acc/payable/loanmt/return/update";
	}
	
	/**
	 * @Description 确认支付
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/confirmReturn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmReturn( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("return_code", ids[3]);
			
			BudgBorrReturn budgBorrReturn = budgBorrReturnService.queryBudgBorrReturnByCode(mapVo);
			
			mapVo.put("state", "02");
			
			mapVo.put("operator", SessionManager.getUserId());
			
			if (!budgBorrReturn.getState().equals("01")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"确认失败 状态必须是已新建的才能确认! \"}");
		}
		try{
			String assInMainJson = budgBorrReturnService.updateConfirmPay(listVo);
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/deleteBudgBorrReturn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBorrReturn( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("return_code", ids[3]);
			
			BudgBorrReturn budgBorrReturn = budgBorrReturnService.queryBudgBorrReturnByCode(mapVo);

			if (!budgBorrReturn.getState().equals("01")) {
				flag = false;
				break;
			}
						
			listVo.add(mapVo);
		}
		
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 已审核的数据不能删除! \"}");
		}
		try{
			String assInMainJson = budgBorrReturnService.deleteBatchBudgBorrReturn(listVo);

			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	//删除明细
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/deleteBudgBorrReturnDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBorrReturnDet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("return_code", ids[3])   ;
				mapVo.put("source_id", ids[4])   ;
				mapVo.put("payment_item_id", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
		try{
			String budgBorrBegainDetJson = budgBorrReturnDetService.deleteBatch(listVo);
			
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/queryBudgBorrReturn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBorrReturn( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assInMain = budgBorrReturnService.queryBudgBorrReturn(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/queryReturnDetForUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryReturnDetForUpdate( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String assInMain = "";
		if(mapVo.get("proj_id") == null || mapVo.get("proj_id").toString().equals("")){
			mapVo.put("is_dept", "1");
		}else{
			mapVo.put("is_dept", "0");
		}
		
		assInMain = budgBorrReturnDetService.query(getPage(mapVo));
			
		return JSONObject.parseObject(assInMain);

	}
	
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/queryBudgBorrReturnDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBorrReturnDet( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String assInMain = "";
		if(mapVo.get("proj_id") == null || mapVo.get("proj_id").toString().equals("")){
			assInMain = budgBorrReturnDetService.queryReturnDeptDetail(getPage(mapVo));
		}else{
			assInMain = budgBorrReturnDetService.queryReturnProjDetail(getPage(mapVo));
		}

		return JSONObject.parseObject(assInMain);

	}
	
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/getBorrowAmount", method = RequestMethod.POST)
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
			BudgBorrDept budgBorrDept = budgBorrDeptService.queryByCode(mapVo);
			json.put("result", budgBorrDept);
		}else{
			BudgBorrProj budgBorrProj = budgBorrProjService.queryByCode(mapVo);
			json.put("result", budgBorrProj);
		}

		return json.toJSONString();
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/borrReturnPrintSetPage", method = RequestMethod.GET)
	public String borrReturnPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/return/queryBorrReturnPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBorrReturnPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson = budgBorrReturnService.queryBorrReturnPrintTemlate(mapVo);
		} catch (Exception e) {
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
}
