/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c01;

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
import com.chd.hrp.cost.service.analysis.c01.C01Service;

/**
 * @Title. @Description. 结余分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C01Controller extends BaseController {

	@Resource(name = "c01Service")
	private final C01Service c01Service = null;
	
	/**
	*结余分析主页面<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0101MainPage", method = RequestMethod.GET)
	public String c0101MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c01/c0101Main";
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/cost/analysis/c01/costAnalysisC0101AddPage", method = RequestMethod.GET)
	public String costAnalysisC0101AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c01/c0101Add";
	}
	
	/**
	*科室收入数据明细表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c01/addCostAnalysisC0101", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0101(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("create_date", new Date());
		String costAnalysisC0101Json = c01Service.addCostAnalysisC0101(mapVo);

		return JSONObject.parseObject(costAnalysisC0101Json);
		
	}
		
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0102MainPage", method = RequestMethod.GET)
	public String c0102MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0102Main";
	}
	@RequestMapping(value = "/hrp/cost/analysis/c01/costAnalysisC0102AddPage", method = RequestMethod.GET)
	public String costAnalysisC0102AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c01/c0102Add";
	}
	
	/**
	*科室收入数据明细表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c01/addCostAnalysisC0102", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0102(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("create_date", new Date());
		String costAnalysisC0102Json = c01Service.addCostAnalysisC0102(mapVo);

		return JSONObject.parseObject(costAnalysisC0102Json);
		
	}
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0103MainPage", method = RequestMethod.GET)
	public String c0103MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0103Main";
	}
	@RequestMapping(value = "/hrp/cost/analysis/c01/costAnalysisC0103AddPage", method = RequestMethod.GET)
	public String costAnalysisC0103AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c01/c0103Add";
	}
	
	/**
	*科室收入数据明细表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c01/addCostAnalysisC0103", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0103(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("create_date", new Date());
		String costAnalysisC0103Json = c01Service.addCostAnalysisC0103(mapVo);

		return JSONObject.parseObject(costAnalysisC0103Json);
		
	}
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0104MainPage", method = RequestMethod.GET)
	public String c0104MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0104Main";
	}
	@RequestMapping(value = "/hrp/cost/analysis/c01/costAnalysisC0104AddPage", method = RequestMethod.GET)
	public String costAnalysisC0104AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c01/c0104Add";
	}
	
	/**
	*科室收入数据明细表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c01/addCostAnalysisC0104", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0104(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("create_date", new Date());
		String costAnalysisC0104Json = c01Service.addCostAnalysisC0104(mapVo);

		return JSONObject.parseObject(costAnalysisC0104Json);
		
	}
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0105MainPage", method = RequestMethod.GET)
	public String c0105MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0105Main";
	}
	@RequestMapping(value = "/hrp/cost/analysis/c01/costAnalysisC0105AddPage", method = RequestMethod.GET)
	public String costAnalysisC0105AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c01/c0105Add";
	}
	
	/**
	*科室收入数据明细表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c01/addCostAnalysisC0105", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0105(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("create_date", new Date());
		String costAnalysisC0105Json = c01Service.addCostAnalysisC0105(mapVo);

		return JSONObject.parseObject(costAnalysisC0105Json);
		
	}
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0106MainPage", method = RequestMethod.GET)
	public String c0106MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0106Main";
	}
	@RequestMapping(value = "/hrp/cost/analysis/c01/costAnalysisC0106AddPage", method = RequestMethod.GET)
	public String costAnalysisC0106AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c01/c0106Add";
	}
	
	/**
	*科室收入数据明细表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c01/addCostAnalysisC0106", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0106(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("create_date", new Date());
		String costAnalysisC0106Json = c01Service.addCostAnalysisC0106(mapVo);

		return JSONObject.parseObject(costAnalysisC0106Json);
		
	}
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0107MainPage", method = RequestMethod.GET)
	public String c0107MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0107Main";
	}
	@RequestMapping(value = "/hrp/cost/analysis/c01/costAnalysisC0107AddPage", method = RequestMethod.GET)
	public String costAnalysisC0107AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c01/c0107Add";
	}
	
	/**
	*科室收入数据明细表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c01/addCostAnalysisC0107", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0107(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("create_date", new Date());
		String costAnalysisC0107Json = c01Service.addCostAnalysisC0107(mapVo);

		return JSONObject.parseObject(costAnalysisC0107Json);
		
	}
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0108MainPage", method = RequestMethod.GET)
	public String c0108MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0108Main";
	}
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0109MainPage", method = RequestMethod.GET)
	public String c0109MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0109Main";
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0110MainPage", method = RequestMethod.GET)
	public String c0110MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0110Main";
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0111MainPage", method = RequestMethod.GET)
	public String c0111MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0111Main";
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0112MainPage", method = RequestMethod.GET)
	public String c0112MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0112Main";
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0113MainPage", method = RequestMethod.GET)
	public String c0113MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0113Main";
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0114MainPage", method = RequestMethod.GET)
	public String c0114MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0114Main";
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0115MainPage", method = RequestMethod.GET)
	public String c0115MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0115Main";
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0116MainPage", method = RequestMethod.GET)
	public String c0116MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0116Main";
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/c0117MainPage", method = RequestMethod.GET)
	public String c0117MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c01/c0117Main";
	}
	
	/**
	*结余分析查询<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0101", method = RequestMethod.POST)
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

		String analysis = c01Service.queryAnalysisC0101(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0102", method = RequestMethod.POST)
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
		
		
		String analysis = c01Service.queryAnalysisC0102(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0103", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0103(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());		
		
		String analysis = c01Service.queryAnalysisC0103(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0104", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0104(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());				
		
		String analysis = c01Service.queryAnalysisC0104(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0105", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0105(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());						
		
		String analysis = c01Service.queryAnalysisC0105(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0106", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0106(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());								
		String analysis = c01Service.queryAnalysisC0106(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0107", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0107(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());				
		String analysis = c01Service.queryAnalysisC0107(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0108", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0108(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());				
		
		String analysis = c01Service.queryAnalysisC0108(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0109", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0109(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("user_id", SessionManager.getUserId());				
		String analysis = c01Service.queryAnalysisC0109(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0110", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0110(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());				
		
		String analysis = c01Service.queryAnalysisC0110(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0111", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0111(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());		
		
		String analysis = c01Service.queryAnalysisC0111(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0112", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0112(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c01Service.queryAnalysisC0112(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0113", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0113(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c01Service.queryAnalysisC0113(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0114", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0114(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c01Service.queryAnalysisC0114(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0115", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0115(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c01Service.queryAnalysisC0115(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0116", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0116(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c01Service.queryAnalysisC0116(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c01/queryAnalysisC0117", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0117(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c01Service.queryAnalysisC0117(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
		
	}
}
