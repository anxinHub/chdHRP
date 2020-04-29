/**
* @Copyright: Copyright (c) 2016-10-20 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;

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
import com.chd.hrp.cost.service.CostExecDeptService;

/**
* @Title. @Description.
* 执行收入统计
* @Author: linuxxu
* @email: linuxxu@s-chd.com
* @Version: 1.0
*/
@Controller
public class CostExecDeptController extends BaseController{

	private static Logger logger = Logger.getLogger(CostExecDeptController.class);
	
	
	@Resource(name = "costExecDeptService")
	private final CostExecDeptService costExecDeptService = null;
	
	/**
	 * 
	* @Title: costIncomeDetailMainPage
	* @Description: 执行收入统计页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月14日   
	* @author sjy
	 */
	
	@RequestMapping(value = "/hrp/cost/costexecdept/costExecDeptMainPage", method = RequestMethod.GET)
	public String costIncomeDetailMainPage(Model mode) throws Exception {
		return "hrp/cost/costexecdept/costExecDeptMain";

	}
	
	
  /**
	* 
	* @Title: queryCostExecSum
	* @Description: 执行收入统计查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月14日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costexecdept/queryCostExecDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostExecDept(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
	        String para_value = MyConfig.getSysPara("03002");
	        
	        String costExecDeptJson = "";
	        
	        if("1".equals(para_value)){
	        	costExecDeptJson = costExecDeptService.queryCostExecDeptMain(getPage(mapVo));
	        }else if("2".equals(para_value)){
	        	costExecDeptJson = costExecDeptService.queryCostExecDeptDetail(getPage(mapVo));
	        }
			
			return JSONObject.parseObject(costExecDeptJson);

	}
}
