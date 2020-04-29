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
import com.chd.hrp.cost.service.CostApplDeptService;
/**
* @Title. @Description.
* 开单收入统计
* @Author: linuxxu
* @email: linuxxu@s-chd.com
* @Version: 1.0
*/
@Controller
public class CostApplDeptMainController extends BaseController{

	private static Logger logger = Logger.getLogger(CostApplDeptMainController.class);
	
	@Resource(name = "costApplDeptService")
	private final CostApplDeptService costApplDeptService = null;
	
	/**
	 * 
	* @Title: costIncomeDetailMainPage
	* @Description: 开单收入统计页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月14日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costappldept/costApplDeptMainPage", method = RequestMethod.GET)
	public String costIncomeDetailMainPage(Model mode) throws Exception {
		return "hrp/cost/costappldept/costApplDeptMain";

	}
	
	/**
	 * 
	* @Title: costIncomeDetailMainPage
	* @Description: 开单收入统计查询
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月14日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costappldept/queryCostApplDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostApplDept(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		     String para_value = MyConfig.getSysPara("03002");
	        
	        String costApplDeptJson = "";
	        
	        if("1".equals(para_value)){
	        	costApplDeptJson = costApplDeptService.queryCostApplDeptMain(getPage(mapVo));
	        }else if("2".equals(para_value)){
	        	costApplDeptJson = costApplDeptService.queryCostApplDeptDetail(getPage(mapVo));
	        }
	        
			return JSONObject.parseObject(costApplDeptJson);

	}
}
