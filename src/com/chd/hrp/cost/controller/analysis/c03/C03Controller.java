/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c03;

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
import com.chd.hrp.cost.service.analysis.c03.C03Service;

/**
 * @Title. @Description. 分类分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C03Controller extends BaseController {

	@Resource(name = "c03Service")
	private final C03Service c03Service = null;
	
	/**
	*收入分类分析<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/analysis/c03/c0301MainPage", method = RequestMethod.GET)
	public String c0301MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c03/c0301Main";

	}
	
	
	/**
	*收入分类分析<BR>
	*添加
	*/
		@RequestMapping(value = "/hrp/cost/analysis/c03/costAnalysisC0301AddPage", method = RequestMethod.GET)
		public String costAnalysisC0301AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c03/c0301Add";

		}
		
		/**
		*收入分类分析<BR>
		*保存
		*/
		@RequestMapping(value = "/hrp/cost/analysis/c03/addCostAnalysisC0301", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addCostAnalysisC0301(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
			
			String costAnalysisC0301Json = c03Service.addCostAnalysisC0301(mapVo);

			return JSONObject.parseObject(costAnalysisC0301Json);
			
		}
	/**
	*收入分类分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c03/queryAnalysisC0301", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0301(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		    mapVo.put("group_id", SessionManager.getGroupId());
		    
		}
		
		if(mapVo.get("hos_id") == null){
			
		    mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
            mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		
		String analysis = c03Service.queryAnalysisC0301(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	/**
	 *收入分类分析明细<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c03/c0302MainPage", method = RequestMethod.GET)
	public String c0302MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c03/c0302Main";
		
	}
	
	/**
	 *收入分类分析明细<BR>
	 *添加页面
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c03/costAnalysisC0302AddPage", method = RequestMethod.GET)
	public String costAnalysisC0302AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c03/c0302Add";

	}
	
	
	
	/**
	*收入分类分析明细
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c03/addCostAnalysisC0302", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostAnalysisC0302(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String costAnalysisC0301Json = c03Service.addCostAnalysisC0301(mapVo);

		return JSONObject.parseObject(costAnalysisC0301Json);
		
	}
	/**
	 *收入分类分析明细<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c03/queryAnalysisC0302", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0302(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c03Service.queryAnalysisC0302(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c03/c0303MainPage", method = RequestMethod.GET)
	public String c0303MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c03/c0303Main";
		
	}
	
	
	// 添加页面
		@RequestMapping(value = "/hrp/cost/analysis/c03/costAnalysisC0303AddPage", method = RequestMethod.GET)
		public String costAnalysisC0303AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c03/c0303Add";

		}
	
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c03/queryAnalysisC0303", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0303(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c03Service.queryAnalysisC0303(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c03/c0304MainPage", method = RequestMethod.GET)
	public String c0304MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c03/c0304Main";
		
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/cost/analysis/c03/costAnalysisC0304AddPage", method = RequestMethod.GET)
	public String costAnalysisC0304AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c03/c0304Add";

	}
	
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c03/queryAnalysisC0304", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0304(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		
		String analysis = c03Service.queryAnalysisC0304(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c03/c0305MainPage", method = RequestMethod.GET)
	public String c0305MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c03/c0305Main";
		
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/cost/analysis/c03/costAnalysisC0305AddPage", method = RequestMethod.GET)
	public String costAnalysisC0305AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c03/c0305Add";

	}
	
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c03/queryAnalysisC0305", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0305(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c03Service.queryAnalysisC0305(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c03/c0306MainPage", method = RequestMethod.GET)
	public String c0306MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c03/c0306Main";
		
	}
	
	
	// 添加页面
	@RequestMapping(value = "/hrp/cost/analysis/c03/costAnalysisC0306AddPage", method = RequestMethod.GET)
	public String costAnalysisC0306AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c03/c0306Add";

	}
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c03/queryAnalysisC0306", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0306(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c03Service.queryAnalysisC0306(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c03/c0307MainPage", method = RequestMethod.GET)
	public String c0307MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c03/c0307Main";
		
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/cost/analysis/c03/costAnalysisC0307AddPage", method = RequestMethod.GET)
	public String costAnalysisC0307AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c03/c0307Add";

	}
	
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c03/queryAnalysisC0307", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0307(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c03Service.queryAnalysisC0307(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c03/c0308MainPage", method = RequestMethod.GET)
	public String c0308MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c03/c0308Main";
		
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/cost/analysis/c03/costAnalysisC0308AddPage", method = RequestMethod.GET)
	public String costAnalysisC0308AddPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c03/c0308Add";

	}
	
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c03/queryAnalysisC0308", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0308(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String analysis = c03Service.queryAnalysisC0308(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
}
