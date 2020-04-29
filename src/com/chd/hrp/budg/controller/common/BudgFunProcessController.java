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
import com.chd.hrp.budg.entity.BudgFun;
import com.chd.hrp.budg.service.common.BudgFunProcessService;
import com.chd.hrp.budg.service.common.BudgFunService;
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
public class BudgFunProcessController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgFunProcessController.class);
	
	//引入Service服务
	@Resource(name = "budgFunProcessService")
	private final BudgFunProcessService budgFunProcessService = null;
	
	@Resource(name = "budgFunService")
	private final BudgFunService budgFunService = null;
   
	/**
	 * @Description 
	 *  计算过程查看 页面 跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/budgFunProcessPage", method = RequestMethod.GET)
	public String budgFunProcessPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        BudgFun budgFun = budgFunService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", budgFun.getGroup_id());
		mode.addAttribute("hos_id", budgFun.getHos_id());
		mode.addAttribute("copy_code", budgFun.getCopy_code());
		mode.addAttribute("fun_code", budgFun.getFun_code());
		mode.addAttribute("fun_name", budgFun.getFun_name());
		mode.addAttribute("type_code", budgFun.getType_code());
		mode.addAttribute("type_name", budgFun.getType_name());
		mode.addAttribute("fun_method_chs", budgFun.getFun_method_chs());
		mode.addAttribute("fun_method_eng", budgFun.getFun_method_eng());
		
		mode.addAttribute("index_code", mapVo.get("index_code"));
		
		mode.addAttribute("index_type_code", mapVo.get("index_type_code"));
		
		if( mapVo.get("year") != null ){
			
			mode.addAttribute("year", mapVo.get("year"));
		}
		
		
		if( mapVo.get("budg_year") != null ){
			
			mode.addAttribute("budg_year", mapVo.get("budg_year"));
		}
		
		if( mapVo.get("budg_level") != null ){
			mode.addAttribute("budg_level", mapVo.get("budg_level"));
		}
		
		
		if(mapVo.get("month") != null){
			mode.addAttribute("month", mapVo.get("month"));
		}
		
		if(mapVo.get("dept_id") != null){
			mode.addAttribute("dept_id", mapVo.get("dept_id"));
		}

		return "hrp/budg/common/budgfun/budgFunProcess";

	}

	
	
	/**
	 * @Description 
	 * 取值函数 过程查看查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/common/budgfun/queryFunProcess", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFunProcess(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String budgFun = null ;
		
		try {
			
			budgFun = budgFunProcessService.queryFunProcess(mapVo);
			
		} catch (Exception e) {

			budgFun = e.getMessage();

		}
				
		return JSONObject.parseObject(budgFun);
		
	}
    
}

