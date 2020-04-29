package com.chd.hrp.budg.controller.project.charge;

import java.text.SimpleDateFormat;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.budg.service.project.begin.BudgProjBeginService;
import com.chd.hrp.budg.service.project.begin.BudgProjYearService;
import com.chd.hrp.budg.service.project.charge.BudgChargeAccountBegainService;
/**
 * 
 * @Description:
 * 期初项目预算表
 * @Table:
 * COM_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class BudgChargeAccountBegainController extends BaseController {
private static Logger logger = Logger.getLogger(BudgChargeAccountBegainController.class);
	//引入Service服务
	@Resource(name = "budgChargeAccountBegainService")
	private final BudgChargeAccountBegainService budgChargeAccountBegainService = null;
	//引入Service服务
	@Resource(name = "budgProjBeginService")
	private final BudgProjBeginService budgProjBeginService = null;
	//引入Service服务
	@Resource(name = "budgProjYearService")
	private final BudgProjYearService budgProjYearService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/charge/budgChargeAccount", method = RequestMethod.GET)
	public String budgFunTypeMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			 mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//查询 初项目预算记账 状态
		Map<String,Object> stateMap = budgProjBeginService.queryBegainMark(mapVo);
		if(stateMap != null && !stateMap.isEmpty()){
			stateMap.put("msg", "数据已记账!");
		}
		mode.addAttribute("stateMap", stateMap);
		
		return "hrp/budg/project/charge/budgChargeAccountMain";

	}
	
	/**
	 * @Description 
	 * 期初记账
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/project/charge/ChargeAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgProjBegain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	        String budg_proj_begain=null;						
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("user_id",SessionManager.getUserId()); 
			mapVo.put("state","1"); 
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    mapVo.put("mark_date", dateFormat.parse(mapVo.get("mark_date").toString()));
			
	  		budg_proj_begain = budgChargeAccountBegainService.add(mapVo);
		  		
		    return JSONObject.parseObject(budg_proj_begain);
	}

	
}
