/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c09;

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
import com.chd.hrp.cost.service.analysis.c09.C09Service;

/**
 * @Title. @Description. 盘亏分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C09Controller extends BaseController {

	@Resource(name = "c09Service")
	private final C09Service c09Service = null;
	
	/**
	*结余分析主页面<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/analysis/c09/c0901MainPage", method = RequestMethod.GET)
	public String c0901MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c09/c0901Main";

	}
	
	// 添加页面
			@RequestMapping(value = "/hrp/cost/analysis/c09/costAnalysisC0901AddPage", method = RequestMethod.GET)
			public String costAnalysisC0901AddPage(Model mode) throws Exception {

				return "hrp/cost/analysis/c09/c0901Add";

			}
			
			/**
			*科室收入数据明细表<BR>
			*保存
			*/
			@RequestMapping(value = "/hrp/cost/analysis/c09/addCostAnalysisC0901", method = RequestMethod.POST)
			@ResponseBody
			
			public Map<String, Object> addCostAnalysisC0901(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
		        mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("create_user", SessionManager.getUserId());
				mapVo.put("create_date", new Date());
				String costAnalysisC0901Json = c09Service.addCostAnalysisC0901(mapVo);

				return JSONObject.parseObject(costAnalysisC0901Json);
				
			}
	/**
	*结余分析查询<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c09/queryAnalysisC0901", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0901(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysis = c09Service.queryAnalysisC0901(getPage(mapVo));
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/c0902MainPage", method = RequestMethod.GET)
	public String c0902MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c09/c0902Main";
		
	}
	/**
	 *生成页跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/costAnalysisC0902AddPage", method = RequestMethod.GET)
	public String costAnalysisC0902AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c09/c0902Add";
	}
	
	/**
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c09/addCostAnalysisC0902", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0902(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String costAnalysisC0902Json = c09Service.addCostAnalysisC0902(mapVo);

		return JSONObject.parseObject(costAnalysisC0902Json);
	}
	
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/queryAnalysisC0902", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0902(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String analysis = c09Service.queryAnalysisC0902(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c09/c0903MainPage", method = RequestMethod.GET)
	public String c0903MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c09/c0903Main";
	}
	/**
	 *生成页跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/costAnalysisC0903AddPage", method = RequestMethod.GET)
	public String costAnalysisC0903AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c09/c0903Add";
	}
	
	/**
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c09/addCostAnalysisC0903", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0903(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String costAnalysisC0903Json = c09Service.addCostAnalysisC0903(mapVo);

		return JSONObject.parseObject(costAnalysisC0903Json);
	}
	
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/queryAnalysisC0903", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0903(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String analysis = c09Service.queryAnalysisC0903(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/c0904MainPage", method = RequestMethod.GET)
	public String c0904MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c09/c0904Main";
	}
	/**
	 *生成页跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/costAnalysisC0904AddPage", method = RequestMethod.GET)
	public String costAnalysisC0904AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c09/c0904Add";
	}
	
	/**
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c09/addCostAnalysisC0904", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0904(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String costAnalysisC0904Json = c09Service.addCostAnalysisC0904(mapVo);

		return JSONObject.parseObject(costAnalysisC0904Json);
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/queryAnalysisC0904", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0904(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c09Service.queryAnalysisC0904(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/c0905MainPage", method = RequestMethod.GET)
	public String c0905MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c09/c0905Main";
	}
	/**
	 *生成页跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/costAnalysisC0905AddPage", method = RequestMethod.GET)
	public String costAnalysisC0905AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c09/c0905Add";
	}
	
	/**
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c09/addCostAnalysisC0905", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0905(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String costAnalysisC0905Json = c09Service.addCostAnalysisC0905(mapVo);

		return JSONObject.parseObject(costAnalysisC0905Json);
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/queryAnalysisC0905", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0905(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c09Service.queryAnalysisC0905(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/c0906MainPage", method = RequestMethod.GET)
	public String c0906MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c09/c0906Main";
	}
	/**
	 *生成页跳转
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/costAnalysisC0906AddPage", method = RequestMethod.GET)
	public String costAnalysisC0906AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c09/c0906Add";
	}
	
	/**
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c09/addCostAnalysisC0906", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0906(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String costAnalysisC0906Json = c09Service.addCostAnalysisC0906(mapVo);

		return JSONObject.parseObject(costAnalysisC0906Json);
	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c09/queryAnalysisC0906", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0906(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String analysis = c09Service.queryAnalysisC0906(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
}
