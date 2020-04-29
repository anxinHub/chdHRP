/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.common;
import java.util.*;

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
import com.chd.hrp.budg.service.common.BudgCountProcessService;
import com.chd.hrp.budg.service.common.BudgFormulaSetService;
/**
 * 
 * @Description:
 * 部件类型表
 * @Table:
 * COM_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class BudgCountProcessController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgCountProcessController.class);
	
	//引入Service服务
	@Resource(name = "budgCountProcessService")
	private final BudgCountProcessService budgCountProcessService = null;
	
	@Resource(name = "budgFormulaSetService")
	private final BudgFormulaSetService budgFormulaSetService = null;
   
	/**
	 * @Description 
	 *  计算过程查看 页面 跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/budgFormulaProcessPage", method = RequestMethod.GET)
	public String budgFormulaProcessPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        Map<String, Object> budgFormulaSet = budgFormulaSetService.queryByCode(mapVo);
		
		Map<String,Object> map = budgFormulaSetService.queryFormulaStack(mapVo);
		
		mode.addAttribute("group_id", budgFormulaSet.get("group_id"));
		mode.addAttribute("hos_id", budgFormulaSet.get("hos_id"));
		mode.addAttribute("copy_code", budgFormulaSet.get("copy_code"));
		mode.addAttribute("formula_id", budgFormulaSet.get("formula_id"));
		mode.addAttribute("formula_name", budgFormulaSet.get("formula_name"));
		mode.addAttribute("formula_ca", budgFormulaSet.get("formula_ca"));
		mode.addAttribute("formula_en", budgFormulaSet.get("formula_en"));
		mode.addAttribute("formula_stack", map.get("formula_stack"));
		
		mode.addAttribute("index_code", mapVo.get("index_code"));
		mode.addAttribute("element_type_code", mapVo.get("element_type_code"));
		mode.addAttribute("element_level", mapVo.get("element_level"));
		mode.addAttribute("year", mapVo.get("year"));
		
		if(mapVo.get("month") != null){
			mode.addAttribute("month", mapVo.get("month"));
		}
		
		if(mapVo.get("dept_id") != null){
			mode.addAttribute("dept_id", mapVo.get("dept_id"));
		}

		return "hrp/budg/common/budgformula/budgFormulaProcess";

	}

	
	
	/**
	 * @Description 
	 * 查询  计算过程查看数据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgformula/queryCountProcess", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCountProcess(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		List<String> indexList = new ArrayList<String>();//存放 正在计算的 指标（service 校验用）
		
		indexList.add(String.valueOf(mapVo.get("element_type_code"))+mapVo.get("index_code")+mapVo.get("element_level")+"01");
		
		String budgFormulaSet = budgCountProcessService.queryCountProcess(mapVo,indexList);

		return JSONObject.parseObject(budgFormulaSet);
		
	}
    
}

