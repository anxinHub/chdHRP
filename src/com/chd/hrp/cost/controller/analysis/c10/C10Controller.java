/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c10;

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
import com.chd.hrp.cost.service.analysis.c10.C10Service;

/**
 * @Title. @Description. 指标分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C10Controller extends BaseController {

	@Resource(name = "c10Service")
	private final C10Service c10Service = null;
	
	/**
	*结余分析主页面<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c10/c1001MainPage", method = RequestMethod.GET)
	public String c1001MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c10/c1001Main";
	}
	
	/**
	*生成页面跳转<BR>
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c10/analysisC1001AddPage", method = RequestMethod.GET)
	public String analysisC1001AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c10/c1001Add";
	}
	
	/**
	*结余分析查询<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c10/queryAnalysisC1001", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC1001(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c10Service.queryAnalysisC1001(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	
	/**
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c10/addAnalysisC1001", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAnalysisC1001(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String c1001Json = c10Service.addAnalysisC1001(mapVo);

		return JSONObject.parseObject(c1001Json);
		
	}
	
}
