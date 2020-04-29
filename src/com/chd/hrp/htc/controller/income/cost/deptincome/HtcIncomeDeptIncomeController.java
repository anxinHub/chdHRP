package com.chd.hrp.htc.controller.income.cost.deptincome;

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
import com.chd.hrp.htc.entity.income.cost.deptincome.HtcIncomeDeptIncome;
import com.chd.hrp.htc.service.income.cost.deptincome.HtcIncomeDeptIncomeService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcIncomeDeptIncomeController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcIncomeDeptIncomeController.class);

	@Resource(name = "htcIncomeDeptIncomeService")
	private final HtcIncomeDeptIncomeService htcIncomeDeptIncomeService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/income/cost/deptincome/htcIncomeDeptIncomeMainPage", method = RequestMethod.GET)
	public String htcIncomeDeptIncomeMainPage(Model mode) throws Exception {
		

		return "hrp/htc/income/cost/deptincome/htcIncomeDeptIncomeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/income/cost/deptincome/htcIncomeDeptIncomeAddPage", method = RequestMethod.GET)
	public String htcIncomeDeptIncomeAddPage(Model mode) throws Exception {
		
		return "hrp/htc/income/cost/deptincome/htcIncomeDeptIncomeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/income/cost/deptincome/addHtcIncomeDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIncomeDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		String  deptIncomeJson = "";
		
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_user", SessionManager.getUserId());
			
			mapVo.put("create_date",new Date());
		
	 	try {
	  		
	 		deptIncomeJson = htcIncomeDeptIncomeService.addHtcIncomeDeptIncome(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptIncomeJson = e.getMessage();
		}

		return JSONObject.parseObject(deptIncomeJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/income/cost/deptincome/queryHtcIncomeDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeDeptIncome(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
		 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		  String deptIncomeJson = htcIncomeDeptIncomeService.queryHtcIncomeDeptIncome(getPage(mapVo));

		 return JSONObject.parseObject(deptIncomeJson);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/income/cost/deptincome/deleteHtcIncomeDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcIncomeDeptIncome(
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
			
			deptIncomeJson = htcIncomeDeptIncomeService.deleteBatchHtcIncomeDeptIncome(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptIncomeJson = e.getMessage();
		}
		

		return JSONObject.parseObject(deptIncomeJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/income/cost/deptincome/htcIncomeDeptIncomeUpdatePage", method = RequestMethod.GET)
	public String htcIncomeDeptIncomeUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {


		HtcIncomeDeptIncome htcIncomeDeptIncome = htcIncomeDeptIncomeService.queryHtcIncomeDeptIncomeByCode(mapVo);
		
		mode.addAttribute("group_id", htcIncomeDeptIncome.getGroup_id());
		mode.addAttribute("hos_id", htcIncomeDeptIncome.getHos_id());
		mode.addAttribute("copy_code", htcIncomeDeptIncome.getCopy_code());
		mode.addAttribute("acc_year", htcIncomeDeptIncome.getAcc_year());
		mode.addAttribute("acc_month", htcIncomeDeptIncome.getAcc_month());
		mode.addAttribute("income_detail_id", htcIncomeDeptIncome.getIncome_detail_id());
		mode.addAttribute("appl_dept_id", htcIncomeDeptIncome.getAppl_dept_id());
		mode.addAttribute("appl_dept_no", htcIncomeDeptIncome.getAppl_dept_no());
		mode.addAttribute("appl_dept_code", htcIncomeDeptIncome.getAppl_dept_code());
		mode.addAttribute("appl_dept_name", htcIncomeDeptIncome.getAppl_dept_name());
		mode.addAttribute("exec_dept_id", htcIncomeDeptIncome.getExec_dept_id());
		mode.addAttribute("exec_dept_no", htcIncomeDeptIncome.getExec_dept_no());
		mode.addAttribute("exec_dept_code", htcIncomeDeptIncome.getExec_dept_code());
		mode.addAttribute("exec_dept_name", htcIncomeDeptIncome.getExec_dept_name());
		mode.addAttribute("charge_kind_id", htcIncomeDeptIncome.getCharge_kind_id());
		mode.addAttribute("charge_kind_code", htcIncomeDeptIncome.getCharge_kind_code());
		mode.addAttribute("charge_kind_name", htcIncomeDeptIncome.getCharge_kind_name());
		mode.addAttribute("charge_item_id", htcIncomeDeptIncome.getCharge_item_id());
		mode.addAttribute("charge_item_code", htcIncomeDeptIncome.getCharge_item_code());
		mode.addAttribute("charge_item_name", htcIncomeDeptIncome.getCharge_item_name());
		mode.addAttribute("price", htcIncomeDeptIncome.getPrice());
		mode.addAttribute("num", htcIncomeDeptIncome.getNum());
		mode.addAttribute("money", htcIncomeDeptIncome.getMoney());
		mode.addAttribute("busi_data_source_code", htcIncomeDeptIncome.getBusi_data_source_code());
		mode.addAttribute("busi_data_source_name", htcIncomeDeptIncome.getBusi_data_source_name());
		mode.addAttribute("create_user", htcIncomeDeptIncome.getCreate_user());
		mode.addAttribute("create_date", htcIncomeDeptIncome.getCreate_date());
		
		
		return "hrp/htc/income/cost/deptincome/htcIncomeDeptIncomeUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/income/cost/deptincome/updateHtcIncomeDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcIncomeDeptIncome(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
	
		   String deptIncomeJson = "";
		
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
            mapVo.put("create_user", SessionManager.getUserId());
			
			mapVo.put("create_date",new Date());
			
			try {
				
				deptIncomeJson = htcIncomeDeptIncomeService.updateHtcIncomeDeptIncome(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				deptIncomeJson = e.getMessage();
			}
			

		return JSONObject.parseObject(deptIncomeJson);
	}
	
	
	@RequestMapping(value="/hrp/htc/income/cost/deptincome/impHtcIncomeDeptIncome",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcIncomeDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String deptIncomeJson = "";
		 
		try {
			 deptIncomeJson= htcIncomeDeptIncomeService.impHtcIncomeDeptIncome(mapVo);
			 
		} catch (Exception e) {
			
			deptIncomeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptIncomeJson);
    }  
	
}
