package com.chd.hrp.htc.controller.task.cost.deptincome;

import java.io.IOException;
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
import com.chd.base.SessionManager;
import com.chd.hrp.htc.entity.task.cost.deptincome.HtcTaskDeptIncome;
import com.chd.hrp.htc.service.task.cost.deptincome.HtcTaskDeptIncomeService;
@Controller
public class HtcTaskDeptIncomeController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcTaskDeptIncomeController.class);

	@Resource(name = "htcTaskDeptIncomeService")
	private final HtcTaskDeptIncomeService htcTaskDeptIncomeService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/cost/deptincome/htcTaskDeptIncomeMainPage", method = RequestMethod.GET)
	public String htcTaskDeptIncomeMainPage(Model mode) throws Exception {

		return "hrp/htc/task/cost/deptincome/htcTaskDeptIncomeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/cost/deptincome/htcTaskDeptIncomeAddPage", method = RequestMethod.GET)
	public String htcTaskDeptIncomeAddPage(Model mode) throws Exception {
		
		return "hrp/htc/task/cost/deptincome/htcTaskDeptIncomeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/cost/deptincome/addHtcTaskDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcTaskDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		String  deptIncomeJson = "";
		
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_user", SessionManager.getUserId());
			
			mapVo.put("create_date",new Date());
		
	 	try {
	  		
	 		deptIncomeJson = htcTaskDeptIncomeService.addHtcTaskDeptIncome(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptIncomeJson = e.getMessage();
		}

		return JSONObject.parseObject(deptIncomeJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/cost/deptincome/queryHtcTaskDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskDeptIncome(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
		 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		  String deptIncomeJson = htcTaskDeptIncomeService.queryHtcTaskDeptIncome(getPage(mapVo));

		 return JSONObject.parseObject(deptIncomeJson);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/cost/deptincome/deleteHtcTaskDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcTaskDeptIncome(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("&");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("income_detail_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String deptIncomeJson = "";
	
		try {
			
			deptIncomeJson = htcTaskDeptIncomeService.deleteBatchHtcTaskDeptIncome(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptIncomeJson = e.getMessage();
		}
		

		return JSONObject.parseObject(deptIncomeJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/cost/deptincome/htcTaskDeptIncomeUpdatePage", method = RequestMethod.GET)
	public String htcTaskDeptIncomeUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {


		HtcTaskDeptIncome htcTaskDeptIncome = htcTaskDeptIncomeService.queryHtcTaskDeptIncomeByCode(mapVo);
		
		mode.addAttribute("group_id", htcTaskDeptIncome.getGroup_id());
		mode.addAttribute("hos_id", htcTaskDeptIncome.getHos_id());
		mode.addAttribute("copy_code", htcTaskDeptIncome.getCopy_code());
		mode.addAttribute("acc_year", htcTaskDeptIncome.getAcc_year());
		mode.addAttribute("acc_month", htcTaskDeptIncome.getAcc_month());
		mode.addAttribute("income_detail_id", htcTaskDeptIncome.getIncome_detail_id());
		mode.addAttribute("appl_dept_id", htcTaskDeptIncome.getAppl_dept_id());
		mode.addAttribute("appl_dept_no", htcTaskDeptIncome.getAppl_dept_no());
		mode.addAttribute("appl_dept_code", htcTaskDeptIncome.getAppl_dept_code());
		mode.addAttribute("appl_dept_name", htcTaskDeptIncome.getAppl_dept_name());
		mode.addAttribute("exec_dept_id", htcTaskDeptIncome.getExec_dept_id());
		mode.addAttribute("exec_dept_no", htcTaskDeptIncome.getExec_dept_no());
		mode.addAttribute("exec_dept_code", htcTaskDeptIncome.getExec_dept_code());
		mode.addAttribute("exec_dept_name", htcTaskDeptIncome.getExec_dept_name());
		mode.addAttribute("charge_kind_id", htcTaskDeptIncome.getCharge_kind_id());
		mode.addAttribute("charge_kind_code", htcTaskDeptIncome.getCharge_kind_code());
		mode.addAttribute("charge_kind_name", htcTaskDeptIncome.getCharge_kind_name());
		mode.addAttribute("charge_item_id", htcTaskDeptIncome.getCharge_item_id());
		mode.addAttribute("charge_item_code", htcTaskDeptIncome.getCharge_item_code());
		mode.addAttribute("charge_item_name", htcTaskDeptIncome.getCharge_item_name());
		mode.addAttribute("price", htcTaskDeptIncome.getPrice());
		mode.addAttribute("num", htcTaskDeptIncome.getNum());
		mode.addAttribute("money", htcTaskDeptIncome.getMoney());
		mode.addAttribute("busi_data_source_code", htcTaskDeptIncome.getBusi_data_source_code());
		mode.addAttribute("busi_data_source_name", htcTaskDeptIncome.getBusi_data_source_name());
		mode.addAttribute("create_user", htcTaskDeptIncome.getCreate_user());
		mode.addAttribute("create_date", htcTaskDeptIncome.getCreate_date());
		
		
		return "hrp/htc/task/cost/deptincome/htcTaskDeptIncomeUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/cost/deptincome/updateHtcTaskDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcTaskDeptIncome(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
	
		   String deptIncomeJson = "";
		
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
            mapVo.put("create_user", SessionManager.getUserId());
			
			mapVo.put("create_date",new Date());
			
			try {
				
				deptIncomeJson = htcTaskDeptIncomeService.updateHtcTaskDeptIncome(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				deptIncomeJson = e.getMessage();
			}
			

		return JSONObject.parseObject(deptIncomeJson);
	}
	
	
	@RequestMapping(value="/hrp/htc/task/cost/deptincome/impHtcTaskDeptIncome",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcTaskDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String deptIncomeJson = "";
		 
		try {
			 deptIncomeJson= htcTaskDeptIncomeService.impHtcTaskDeptIncome(mapVo);
			 
		} catch (Exception e) {
			
			deptIncomeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptIncomeJson);
    }  
}
