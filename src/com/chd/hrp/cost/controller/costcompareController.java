/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.CostCompareService;
import com.chd.hrp.cost.service.analysis.c05.C05Service;

/**
 * @Title. @Description. 对比分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class costcompareController extends BaseController {

	@Resource(name = "costCompareService")
	private final CostCompareService costCompareService = null;
	
	
	/**
	 *医疗成本比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcompare/c0502MainPage", method = RequestMethod.GET)
	public String c0502MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcompare/c0502Main";
		
	}
	
	
	/**
	 *医疗成本比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcompare/queryAnalysisC0502", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0502(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = costCompareService.queryAnalysisC0502(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医疗全成本比较分析表<BR>
	 *维护页面跳转
	 */
	
	
	/**
	 *科室医疗成本差异比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcompare/c0506MainPage", method = RequestMethod.GET)
	public String c0506MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcompare/c0506Main";
		
	}
	
	
	
	
	/**
	 *科室医疗成本差异比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcompare/queryAnalysisC0506", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0506(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		  if(mapVo.get("group_id") == null){
			  
			mapVo.put("group_id", SessionManager.getGroupId());
			
		  }
		  
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = costCompareService.queryAnalysisC0506(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	
}
