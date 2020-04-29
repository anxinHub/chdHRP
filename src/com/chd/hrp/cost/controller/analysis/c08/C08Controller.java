/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c08;

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
import com.chd.hrp.cost.service.analysis.c08.C08Service;

/**
 * @Title. @Description. 指标分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C08Controller extends BaseController {

	@Resource(name = "c08Service")
	private final C08Service c08Service = null;
	
	
	
	/**
	*医院工作量情况页面<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/analysis/c08/c0800MainPage", method = RequestMethod.GET)
	public String c0800MainPage(Model mode) throws Exception {
		return "hrp/cost/analysis/c08/c0800Main";

	}
	
	/**
	*医院工作量情况查询<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c08/queryAnalysisC0800", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0800(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysis = c08Service.queryAnalysisC0800(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	
	/**
	*结余分析主页面<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/analysis/c08/c0801MainPage", method = RequestMethod.GET)
	public String c0801MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c08/c0801Main";

	}
	/**
	*结余分析查询<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c08/queryAnalysisC0801", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0801(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c08Service.queryAnalysisC0801(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c08/c0802MainPage", method = RequestMethod.GET)
	public String c0802MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c08/c0802Main";
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c08/queryAnalysisC0802", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0802(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c08Service.queryAnalysisC0802(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
}
