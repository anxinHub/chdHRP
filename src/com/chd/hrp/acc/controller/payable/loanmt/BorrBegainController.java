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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.payable.BudgBorrBegain;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrBegainDetService;
import com.chd.hrp.acc.service.payable.loanmt.BudgBorrBegainService;
@Controller
public class BorrBegainController extends BaseController{
	private static Logger logger = Logger.getLogger(BorrBegainController.class);	
	
	@Resource(name = "budgBorrBegainService")
	private final BudgBorrBegainService budgBorrBegainService = null;
	
	@Resource(name = "budgBorrBegainDetService")
	private final BudgBorrBegainDetService budgBorrBegainDetService = null;
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/begainMainPage", method = RequestMethod.GET)
	public String begainMainPage(Model mode) throws Exception {

		return "hrp/acc/payable/loanmt/begain/main";
	}
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/begainAddPage", method = RequestMethod.GET)
	public String begainAddPage(Model mode) throws Exception {

		return "hrp/acc/payable/loanmt/begain/add";
	}
	
	
	/**
	 * @Description 添加数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/addBudgBorrBegain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgBorrBegain( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String assInDetailJson = "";
		boolean flag = budgBorrBegainService.isAccount(mapVo);
		if(!flag){
			return JSONObject.parseObject("{\"error\":\"期初借款已经记账，不允许添加数据! \"}");
		}
		
		mapVo.put("maker", SessionManager.getUserId());
		
		mapVo.put("make_date", new Date());
		String result = "";
		try{
			result = budgBorrBegainService.addBudgBorrBegain(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(result);
			String begin_code = (String) jsonObj.get("begin_code");
			mapVo.put("begin_code", begin_code);
			budgBorrBegainDetService.delete(mapVo);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		JSONObject jsonObj = JSONArray.parseObject(result);
		String begin_code = (String) jsonObj.get("begin_code");
		
		

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
			detailVo.put("begin_code", begin_code);
			

			String source_id = detailVo.get("source_id_no") == null ? "" : detailVo.get("source_id_no").toString();
			
			String payment_item_id = detailVo.get("payment_item_id_no") == null ? "" : detailVo.get("payment_item_id_no").toString();
			if("".equals(source_id)){
				continue;
			}
			
			detailVo.put("source_id", source_id.split("\\.")[1]);
			detailVo.put("payment_item_id", payment_item_id.split("@")[0]);
			detailVo.put("payment_item_no", payment_item_id.split("@")[1]);
			try{
				assInDetailJson = budgBorrBegainDetService.addOrUpdate(detailVo);
			}catch(Exception e){
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}

		}
		JSONObject json = JSONArray.parseObject(assInDetailJson);
		json.put("begin_code", begin_code);
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/budgBorrBegainUpdatePage", method = RequestMethod.GET)
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

		BudgBorrBegain budgBorrBegain = budgBorrBegainService.queryBudgBorrBegainByCode(mapVo);

		mode.addAttribute("group_id",	   budgBorrBegain.getGroup_id());
		mode.addAttribute("hos_id",	   budgBorrBegain.getHos_id());
		mode.addAttribute("copy_code",	   budgBorrBegain.getCopy_code());
		mode.addAttribute("begin_code",	   budgBorrBegain.getBegin_code());
		mode.addAttribute("dept_id",	   budgBorrBegain.getDept_id());
		mode.addAttribute("dept_no",   budgBorrBegain.getDept_no());
		mode.addAttribute("proj_id",	   budgBorrBegain.getProj_id()       	    );
		mode.addAttribute("proj_no",	   budgBorrBegain.getProj_no()       	    );
		mode.addAttribute("emp_id",	   budgBorrBegain.getEmp_id ()       	    );
		mode.addAttribute("emp_no",	   budgBorrBegain.getEmp_no    ()    	    );
		mode.addAttribute("dept_code",	   budgBorrBegain.getDept_code());
		mode.addAttribute("dept_name",   budgBorrBegain.getDept_name());
		mode.addAttribute("proj_code",	   budgBorrBegain.getProj_code()       	    );
		mode.addAttribute("proj_name",	   budgBorrBegain.getProj_name()       	    );
		mode.addAttribute("emp_code",	   budgBorrBegain.getEmp_code ()       	    );
		mode.addAttribute("emp_name",	   budgBorrBegain.getEmp_name    ()    	    );
		mode.addAttribute("remain_amount", budgBorrBegain.getRemain_amount ()	    );
		mode.addAttribute("remark",	   budgBorrBegain.getRemark ()       	    );
		mode.addAttribute("maker",	   budgBorrBegain.getMaker     ()    	    );
		mode.addAttribute("maker_name",	   budgBorrBegain.getMaker_name     ()    	    );
		mode.addAttribute("make_date",	   DateUtil.dateToString(budgBorrBegain.getMake_date (),"yyyy-MM-dd" )   	    );
		mode.addAttribute("checker",	   budgBorrBegain.getChecker    ()   	    );
		mode.addAttribute("checker_name",	   budgBorrBegain.getChecker_name    ()   	    );
		mode.addAttribute("check_date",	   DateUtil.dateToString(budgBorrBegain.getCheck_date  (),"yyyy-MM-dd")  	    );
		mode.addAttribute("state",	   budgBorrBegain.getState()    );
		mode.addAttribute("state_name",	   budgBorrBegain.getState().equals("0")?"新建":"审核"  	    );
		return "hrp/acc/payable/loanmt/begain/update";
	}


	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/updateToExamineBegain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamineBegain( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> entityMap = new HashMap<String, Object>();
		if (entityMap.get("group_id") == null) {
			entityMap.put("group_id", SessionManager.getGroupId());
		}

		if (entityMap.get("hos_id") == null) {
			entityMap.put("hos_id", SessionManager.getHosId());
		}

		if (entityMap.get("copy_code") == null) {
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		String assInMainJson = "";
		boolean isFlag = budgBorrBegainService.isAccount(entityMap);
		if(!isFlag){
			return JSONObject.parseObject("{\"error\":\"期初借款已经记账，不允许审核! \"}");
		}
		
		boolean flag = true;
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("begin_code", ids[3]);
			
			BudgBorrBegain budgBorrBegain = budgBorrBegainService.queryBudgBorrBegainByCode(mapVo);
			
			mapVo.put("checker", SessionManager.getUserId());

			mapVo.put("check_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			if (!budgBorrBegain.getState().equals("0")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核! \"}");
		}
		try{
			assInMainJson = budgBorrBegainService.updateToExamine(listVo);
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/updateNotToExamineBegain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamineBegain( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		if (entityMap.get("group_id") == null) {
			entityMap.put("group_id", SessionManager.getGroupId());
		}

		if (entityMap.get("hos_id") == null) {
			entityMap.put("hos_id", SessionManager.getHosId());
		}

		if (entityMap.get("copy_code") == null) {
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		String assInMainJson = "";
		boolean isFlag = budgBorrBegainService.isAccount(entityMap);
		if(!isFlag){
			return JSONObject.parseObject("{\"error\":\"期初借款已经记账，不允许消审! \"}");
		}
		
		boolean flag = true;
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("begin_code", ids[3]);
			
			BudgBorrBegain budgBorrBegain = budgBorrBegainService.queryBudgBorrBegainByCode(mapVo);
			
			mapVo.put("checker", "");
			
			mapVo.put("check_date", "");

			if (!budgBorrBegain.getState().equals("1")) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"消审失败 状态必须是审核的才能消审! \"}");
		}
		try{
			assInMainJson = budgBorrBegainService.updateNotToExamine(listVo);
			return JSONObject.parseObject(assInMainJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
	}
	
	
	//删除明细
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/deleteBudgBorrBegainDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBorrBegainDet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		if (entityMap.get("group_id") == null) {
			entityMap.put("group_id", SessionManager.getGroupId());
		}

		if (entityMap.get("hos_id") == null) {
			entityMap.put("hos_id", SessionManager.getHosId());
		}

		if (entityMap.get("copy_code") == null) {
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		String budgBorrBegainDetJson = "";
		boolean isFlag = budgBorrBegainService.isAccount(entityMap);
		if(!isFlag){
			return JSONObject.parseObject("{\"error\":\"期初借款已经记账，不允许删除明细! \"}");
		}
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("begin_code", ids[3])   ;
				mapVo.put("source_id", ids[4])   ;
				mapVo.put("payment_item_id", ids[5]);
				
	      listVo.add(mapVo);
	      
	    }
		try{
			budgBorrBegainDetJson = budgBorrBegainDetService.deleteBatch(listVo);
			return JSONObject.parseObject(budgBorrBegainDetJson);
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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/deleteBudgBorrBegain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgBorrBegain( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		if (entityMap.get("group_id") == null) {
			entityMap.put("group_id", SessionManager.getGroupId());
		}

		if (entityMap.get("hos_id") == null) {
			entityMap.put("hos_id", SessionManager.getHosId());
		}

		if (entityMap.get("copy_code") == null) {
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		boolean isFlag = budgBorrBegainService.isAccount(entityMap);
		if(!isFlag){
			return JSONObject.parseObject("{\"error\":\"期初借款已经记账，不允许删除! \"}");
		}
		
		
		boolean flag = true;
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("begin_code", ids[3]);
			
			BudgBorrBegain budgBorrBegain = budgBorrBegainService.queryBudgBorrBegainByCode(mapVo);

			if (!budgBorrBegain.getState().equals("0")) {
				flag = false;
				break;
			}
						
			listVo.add(mapVo);
		}
		
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 已审核的数据不能删除! \"}");
		}
		try{
			String assInMainJson = budgBorrBegainService.deleteBatchBudgBorrBegain(listVo);

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
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/queryBudgBorrBegain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBorrBegain( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assInMain = budgBorrBegainService.queryBudgBorrBegain(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
	
	@RequestMapping(value = "/hrp/acc/payable/loanmt/begain/queryBudgBorrBegainDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBorrBegainDet( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assInMain = budgBorrBegainService.queryBudgBorrBegainDet(getPage(mapVo));

		return JSONObject.parseObject(assInMain);
	}
	
	
	
	
}
