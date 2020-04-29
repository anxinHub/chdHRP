package com.chd.hrp.htc.controller.relative.cost.deptincome;

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
import com.chd.hrp.htc.entity.relative.cost.deptincome.HtcRelativeDeptIncome;
import com.chd.hrp.htc.service.relative.cost.deptincome.HtcRelativeDeptIncomeService;

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
public class HtcRelativeDeptIncomeController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcRelativeDeptIncomeController.class);

	@Resource(name = "htcRelativeDeptIncomeService")
	private final HtcRelativeDeptIncomeService htcRelativeDeptIncomeService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/relative/cost/deptincome/htcRelativeDeptIncomeMainPage", method = RequestMethod.GET)
	public String htcRelativeDeptIncomeMainPage(Model mode) throws Exception {
		

		return "hrp/htc/relative/cost/deptincome/htcRelativeDeptIncomeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/relative/cost/deptincome/htcRelativeDeptIncomeAddPage", method = RequestMethod.GET)
	public String htcRelativeDeptIncomeAddPage(Model mode) throws Exception {
		
		return "hrp/htc/relative/cost/deptincome/htcRelativeDeptIncomeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/relative/cost/deptincome/addHtcRelativeDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcRelativeDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		String  deptIncomeJson = "";
		
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_user", SessionManager.getUserId());
			
			mapVo.put("create_date",new Date());
		
	 	try {
	  		
	 		deptIncomeJson = htcRelativeDeptIncomeService.addHtcRelativeDeptIncome(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptIncomeJson = e.getMessage();
		}

		return JSONObject.parseObject(deptIncomeJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/relative/cost/deptincome/queryHtcRelativeDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeDeptIncome(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
		 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		  String deptIncomeJson = htcRelativeDeptIncomeService.queryHtcRelativeDeptIncome(getPage(mapVo));

		 return JSONObject.parseObject(deptIncomeJson);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/relative/cost/deptincome/deleteHtcRelativeDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcRelativeDeptIncome(
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
			
			deptIncomeJson = htcRelativeDeptIncomeService.deleteBatchHtcRelativeDeptIncome(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			deptIncomeJson = e.getMessage();
		}
		

		return JSONObject.parseObject(deptIncomeJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/relative/cost/deptincome/htcRelativeDeptIncomeUpdatePage", method = RequestMethod.GET)
	public String htcRelativeDeptIncomeUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {


		HtcRelativeDeptIncome htcRelativeDeptIncome = htcRelativeDeptIncomeService.queryHtcRelativeDeptIncomeByCode(mapVo);
		
		mode.addAttribute("group_id", htcRelativeDeptIncome.getGroup_id());
		mode.addAttribute("hos_id", htcRelativeDeptIncome.getHos_id());
		mode.addAttribute("copy_code", htcRelativeDeptIncome.getCopy_code());
		mode.addAttribute("acc_year", htcRelativeDeptIncome.getAcc_year());
		mode.addAttribute("acc_month", htcRelativeDeptIncome.getAcc_month());
		mode.addAttribute("income_detail_id", htcRelativeDeptIncome.getIncome_detail_id());
		mode.addAttribute("appl_dept_id", htcRelativeDeptIncome.getAppl_dept_id());
		mode.addAttribute("appl_dept_no", htcRelativeDeptIncome.getAppl_dept_no());
		mode.addAttribute("appl_dept_code", htcRelativeDeptIncome.getAppl_dept_code());
		mode.addAttribute("appl_dept_name", htcRelativeDeptIncome.getAppl_dept_name());
		mode.addAttribute("exec_dept_id", htcRelativeDeptIncome.getExec_dept_id());
		mode.addAttribute("exec_dept_no", htcRelativeDeptIncome.getExec_dept_no());
		mode.addAttribute("exec_dept_code", htcRelativeDeptIncome.getExec_dept_code());
		mode.addAttribute("exec_dept_name", htcRelativeDeptIncome.getExec_dept_name());
		mode.addAttribute("charge_kind_id", htcRelativeDeptIncome.getCharge_kind_id());
		mode.addAttribute("charge_kind_code", htcRelativeDeptIncome.getCharge_kind_code());
		mode.addAttribute("charge_kind_name", htcRelativeDeptIncome.getCharge_kind_name());
		mode.addAttribute("charge_item_id", htcRelativeDeptIncome.getCharge_item_id());
		mode.addAttribute("charge_item_code", htcRelativeDeptIncome.getCharge_item_code());
		mode.addAttribute("charge_item_name", htcRelativeDeptIncome.getCharge_item_name());
		mode.addAttribute("price", htcRelativeDeptIncome.getPrice());
		mode.addAttribute("num", htcRelativeDeptIncome.getNum());
		mode.addAttribute("money", htcRelativeDeptIncome.getMoney());
		mode.addAttribute("busi_data_source_code", htcRelativeDeptIncome.getBusi_data_source_code());
		mode.addAttribute("busi_data_source_name", htcRelativeDeptIncome.getBusi_data_source_name());
		mode.addAttribute("create_user", htcRelativeDeptIncome.getCreate_user());
		mode.addAttribute("create_date", htcRelativeDeptIncome.getCreate_date());
		
		
		return "hrp/htc/relative/cost/deptincome/htcRelativeDeptIncomeUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/relative/cost/deptincome/updateHtcRelativeDeptIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcRelativeDeptIncome(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
	
		   String deptIncomeJson = "";
		
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
            mapVo.put("create_user", SessionManager.getUserId());
			
			mapVo.put("create_date",new Date());
			
			try {
				
				deptIncomeJson = htcRelativeDeptIncomeService.updateHtcRelativeDeptIncome(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				deptIncomeJson = e.getMessage();
			}
			

		return JSONObject.parseObject(deptIncomeJson);
	}
	
	
	@RequestMapping(value="/hrp/htc/relative/cost/deptincome/impHtcRelativeDeptIncome",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcRelativeDeptIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String deptIncomeJson = "";
		 
		try {
			 deptIncomeJson= htcRelativeDeptIncomeService.impHtcRelativeDeptIncome(mapVo);
			 
		} catch (Exception e) {
			
			deptIncomeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptIncomeJson);
    }  
	
}
