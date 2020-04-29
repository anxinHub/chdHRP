/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c05;

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
import com.chd.hrp.cost.service.analysis.c05.C05Service;

/**
 * @Title. @Description. 对比分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C05Controller extends BaseController {

	@Resource(name = "c05Service")
	private final C05Service c05Service = null;
	
	/**
	*医疗业务成本比较分析表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0501MainPage", method = RequestMethod.GET)
	public String c0501MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c05/c0501Main";

	}
	
	
	/**
	*医疗业务成本比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0501AddPage", method = RequestMethod.GET)
	public String costAnalysisC0501AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0501Add";

	}
	
	/**
	*医疗业务成本比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0501", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0501(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0501Json = c05Service.addAnalysisC0501(mapVo);

		return JSONObject.parseObject(addAnalysisC0501Json);
		
	}
	/**
	*医疗业务成本比较分析表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0501", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0501(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		  mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
		  mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
           mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String analysis = c05Service.queryAnalysisC0501(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	/**
	 *医疗成本比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0502MainPage", method = RequestMethod.GET)
	public String c0502MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0502Main";
		
	}
	
	/**
	*医疗成本比较分析表<BR>
	*添加页面跳转
	*/
		@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0502AddPage", method = RequestMethod.GET)
		public String costAnalysisC0502AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0502Add";

		}
		/**
		*医疗成本比较分析表<BR>
		*保存
		*/
		@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0502", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addCostAnalysisC0502(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			if(mapVo.get("group_id") == null){
				
			   mapVo.put("group_id", SessionManager.getGroupId());
			
			}
			
			if(mapVo.get("hos_id") == null){
				
			       mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
				
	               mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String acc_year = mapVo.get("acc_year").toString();
			
			String acc_month = mapVo.get("acc_month").toString();
			
			mapVo.put("acc_year", acc_year);
			
			mapVo.put("acc_month", acc_month);
			
			mapVo.put("year_month", acc_year + acc_month);
			
			String addAnalysisC0502Json = c05Service.addAnalysisC0502(mapVo);

			return JSONObject.parseObject(addAnalysisC0502Json);
			
		}
	/**
	 *医疗成本比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0502", method = RequestMethod.POST)
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
		
		String analysis = c05Service.queryAnalysisC0502(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医疗全成本比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0503MainPage", method = RequestMethod.GET)
	public String c0503MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0503Main";
		
	}
	
	
	/**
	*医疗全成本比较分析表<BR>
	*添加页面跳转
	*/
		@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0503AddPage", method = RequestMethod.GET)
		public String costAnalysisC0503AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0503Add";

		}
		
		/**
		*医疗全成本比较分析表<BR>
		*保存
		*/
		@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0503", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addCostAnalysisC0503(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			if(mapVo.get("group_id") == null){
				
			   mapVo.put("group_id", SessionManager.getGroupId());
			
			}
			
			if(mapVo.get("hos_id") == null){
				
			       mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
				
	               mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String acc_year = mapVo.get("acc_year").toString();
			
			String acc_month = mapVo.get("acc_month").toString();
			
			mapVo.put("acc_year", acc_year);
			
			mapVo.put("acc_month", acc_month);
			
			mapVo.put("year_month", acc_year + acc_month);
			
			String addAnalysisC0503Json = c05Service.addAnalysisC0503(mapVo);

			return JSONObject.parseObject(addAnalysisC0503Json);
			
		}
	/**
	 *医疗全成本比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0503", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0503(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0503(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医院全成本比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0504MainPage", method = RequestMethod.GET)
	public String c0504MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0504Main";
		
	}
	
	/**
	*医院全成本比较分析表<BR>
	*添加页面跳转
	*/
		@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0504AddPage", method = RequestMethod.GET)
		public String costAnalysisC0504AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0504Add";

		}
		
		
		
		/**
		*医院全成本比较分析表<BR>
		*保存
		*/
		@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0504", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addCostAnalysisC0504(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			if(mapVo.get("group_id") == null){
				
			   mapVo.put("group_id", SessionManager.getGroupId());
			
			}
			
			if(mapVo.get("hos_id") == null){
				
			       mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
				
	               mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String acc_year = mapVo.get("acc_year").toString();
			
			String acc_month = mapVo.get("acc_month").toString();
			
			mapVo.put("acc_year", acc_year);
			
			mapVo.put("acc_month", acc_month);
			
			mapVo.put("year_month", acc_year + acc_month);
			
			String addAnalysisC0504Json = c05Service.addAnalysisC0504(mapVo);

			return JSONObject.parseObject(addAnalysisC0504Json);
			
		}
	/**
	 *医院全成本比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0504", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0504(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0504(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *科室医疗业务成本差异比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0505MainPage", method = RequestMethod.GET)
	public String c0505MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0505Main";
		
	}
	
	
	/**
	*科室医疗业务成本差异比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0505AddPage", method = RequestMethod.GET)
	public String costAnalysisC0505AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0505Add";

	}
	
	/**
	*科室医疗业务成本差异比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0505", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0505(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0505Json = c05Service.addAnalysisC0505(mapVo);

		return JSONObject.parseObject(addAnalysisC0505Json);
		
	}
	
	/**
	 *科室医疗业务成本差异比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0505", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0505(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		 if(mapVo.get("group_id") == null){
			 
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		 
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0505(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *科室医疗成本差异比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0506MainPage", method = RequestMethod.GET)
	public String c0506MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0506Main";
		
	}
	
	
	/**
	*科室医疗成本差异比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0506AddPage", method = RequestMethod.GET)
	public String costAnalysisC0506AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0506Add";

	}
	
	/**
	*科室医疗成本差异比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0506", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0506(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0506Json = c05Service.addAnalysisC0506(mapVo);

		return JSONObject.parseObject(addAnalysisC0506Json);
		
	}
	
	/**
	 *科室医疗成本差异比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0506", method = RequestMethod.POST)
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
		
		String analysis = c05Service.queryAnalysisC0506(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *科室医疗全成本差异比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0507MainPage", method = RequestMethod.GET)
	public String c0507MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0507Main";
		
	}
	
	/**
	*科室医疗全成本差异比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0507AddPage", method = RequestMethod.GET)
	public String costAnalysisC0507AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0507Add";

	}
	
	
	
	/**
	*科室医疗全成本差异比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0507", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0507(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0507Json = c05Service.addAnalysisC0507(mapVo);

		return JSONObject.parseObject(addAnalysisC0507Json);
		
	}
	
	/**
	 *科室医疗全成本差异比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0507", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0507(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		  
		 if(mapVo.get("group_id") == null){
			 
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		 
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0507(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *科室医院全成本差异比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0508MainPage", method = RequestMethod.GET)
	public String c0508MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0508Main";
		
	}
	
	/**
	*科室医院全成本差异比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0508AddPage", method = RequestMethod.GET)
	public String costAnalysisC0508AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0508Add";

	}
	
	/**
	*科室医院全成本差异比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0508", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0508(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0508Json = c05Service.addAnalysisC0508(mapVo);

		return JSONObject.parseObject(addAnalysisC0508Json);
		
	}
	
	/**
	 *科室医院全成本差异比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0508", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0508(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		 if(mapVo.get("group_id") == null){
			 
			mapVo.put("group_id", SessionManager.getGroupId());
		  }
		 
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0508(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医疗业务成本分类比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0509MainPage", method = RequestMethod.GET)
	public String c0509MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0509Main";
		
	}
	
	/**
	*医疗业务成本分类比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0509AddPage", method = RequestMethod.GET)
	public String costAnalysisC0509AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0509Add";

	}
	
	/**
	*医疗业务成本分类比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0509", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0509(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0509Json = c05Service.addAnalysisC0509(mapVo);

		return JSONObject.parseObject(addAnalysisC0509Json);
		
	}
	
	/**
	 *医疗业务成本分类比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0509", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0509(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		   
		 if(mapVo.get("group_id") == null){
			 
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		 
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String analysis = c05Service.queryAnalysisC0509(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医疗成本分类比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0510MainPage", method = RequestMethod.GET)
	public String c0510MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0510Main";
		
	}
	
	/**
	*医疗成本分类比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0510AddPage", method = RequestMethod.GET)
	public String costAnalysisC0510AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0510Add";

	}
	
	
	/**
	*医疗成本分类比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0510", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0510(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0510Json = c05Service.addAnalysisC0510(mapVo);

		return JSONObject.parseObject(addAnalysisC0510Json);
		
	}
	
	/**
	 *医疗成本分类比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0510", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0510(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0510(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医疗全成本分类比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0511MainPage", method = RequestMethod.GET)
	public String c0511MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0511Main";
		
	}
	
	/**
	*医疗全成本分类比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0511AddPage", method = RequestMethod.GET)
	public String costAnalysisC0511AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0511Add";

	}
	
	/**
	*医疗全成本分类比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0511", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0511(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0511Json = c05Service.addAnalysisC0511(mapVo);

		return JSONObject.parseObject(addAnalysisC0511Json);
		
	}
	
	/**
	 *医疗全成本分类比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0511", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0511(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String analysis = c05Service.queryAnalysisC0511(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医院全成本分类比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0512MainPage", method = RequestMethod.GET)
	public String c0512MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0512Main";
		
	}
	
	/**
	*医院全成本分类比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0512AddPage", method = RequestMethod.GET)
	public String costAnalysisC0512AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0512Add";

	}
	

	/**
	*医院全成本分类比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0512", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0512(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0512Json = c05Service.addAnalysisC0512(mapVo);

		return JSONObject.parseObject(addAnalysisC0512Json);
		
	}
	
	/**
	 *医院全成本分类比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0512", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0512(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0512(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医疗业务成本差异比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0513MainPage", method = RequestMethod.GET)
	public String c0513MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0513Main";
		
	}
	
	/**
	*医疗业务成本差异比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0513AddPage", method = RequestMethod.GET)
	public String costAnalysisC0513AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0513Add";

	}
	
	/**
	*医疗业务成本差异比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0513", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0513(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0513Json = c05Service.addAnalysisC0513(mapVo);

		return JSONObject.parseObject(addAnalysisC0513Json);
		
	}
	
	/**
	 *医疗业务成本差异比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0513", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0513(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		 if(mapVo.get("group_id") == null){
			 
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		 
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0513(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医疗成本差异比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0514MainPage", method = RequestMethod.GET)
	public String c0514MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0514Main";
		
	}
	
	
	/**
	*医疗成本差异比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0514AddPage", method = RequestMethod.GET)
	public String costAnalysisC0514AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0514Add";

	}
	
	/**
	*医疗成本差异比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0514", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0514(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0514Json = c05Service.addAnalysisC0514(mapVo);

		return JSONObject.parseObject(addAnalysisC0514Json);
		
	}
	
	/**
	 *医疗成本差异比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0514", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0514(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		 if(mapVo.get("group_id") == null){
			 
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		 
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0514(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医疗全成本差异比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0515MainPage", method = RequestMethod.GET)
	public String c0515MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0515Main";
		
	}
	
	
	/**
	*医疗全成本差异比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0515AddPage", method = RequestMethod.GET)
	public String costAnalysisC0515AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0515Add";

	}
	
	
	/**
	*医疗全成本差异比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0515", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0515(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0515Json = c05Service.addAnalysisC0515(mapVo);

		return JSONObject.parseObject(addAnalysisC0515Json);
		
	}
	
	
	/**
	 *医疗全成本差异比较分析表BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0515", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0515(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		 if(mapVo.get("group_id") == null){
			 
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		 
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c05Service.queryAnalysisC0515(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医院全成本差异比较分析表<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c05/c0516MainPage", method = RequestMethod.GET)
	public String c0516MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c05/c0516Main";
		
	}
	
	/**
	*医院全成本差异比较分析表<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/costAnalysisC0516AddPage", method = RequestMethod.GET)
	public String costAnalysisC0516AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c05/c0516Add";

	}
	
	
	/**
	*医院全成本差异比较分析表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c05/addCostAnalysisC0516", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostAnalysisC0516(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		       mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
               mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String acc_year = mapVo.get("acc_year").toString();
		
		String acc_month = mapVo.get("acc_month").toString();
		
		mapVo.put("acc_year", acc_year);
		
		mapVo.put("acc_month", acc_month);
		
		mapVo.put("year_month", acc_year + acc_month);
		
		String addAnalysisC0516Json = c05Service.addAnalysisC0516(mapVo);

		return JSONObject.parseObject(addAnalysisC0516Json);
		
	}
	
	/**
	 *医院全成本差异比较分析表<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c05/queryAnalysisC0516", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0516(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		  
		 if(mapVo.get("group_id") == null){
			  
			mapVo.put("group_id", SessionManager.getGroupId());
		  }
		  
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String analysis = c05Service.queryAnalysisC0516(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
}
