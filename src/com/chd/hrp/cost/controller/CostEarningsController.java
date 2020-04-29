/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.util.Date;
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
import com.chd.hrp.cost.service.CostEarningsService;
import com.chd.hrp.cost.service.analysis.c01.C01Service;

/**
 * @Title. @Description. 结余分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class CostEarningsController extends BaseController {

	@Resource(name = "costEarningsService")
	private final CostEarningsService costEarningsService = null;
	
	/**
	*结余分析主页面<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costearnings/c0101MainPage", method = RequestMethod.GET)
	public String c0101MainPage(Model mode) throws Exception {

		return "hrp/cost/costearnings/c0101Main";
	}
	
	
		
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costearnings/c0102MainPage", method = RequestMethod.GET)
	public String c0102MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costearnings/c0102Main";
	}
	
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costearnings/c0103MainPage", method = RequestMethod.GET)
	public String c0103MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costearnings/c0103Main";
	}
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costearnings/c0104MainPage", method = RequestMethod.GET)
	public String c0104MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costearnings/c0104Main";
	}
	
	
	/**
	*结余分析查询<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costearnings/queryAnalysisC0101", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0101(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String analysis = costEarningsService.queryAnalysisC0101(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costearnings/queryAnalysisC0102", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0102(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = costEarningsService.queryAnalysisC0102(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costearnings/queryAnalysisC0103", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0103(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = costEarningsService.queryAnalysisC0103(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costearnings/queryAnalysisC0104", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0104(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = costEarningsService.queryAnalysisC0104(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
}
