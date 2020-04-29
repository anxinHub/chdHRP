/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c04;

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
import com.chd.hrp.cost.service.analysis.c04.C04Service;

/**
 * @Title. @Description. 习性分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C04Controller extends BaseController {

	@Resource(name = "c04Service")
	private final C04Service c04Service = null;
	
	/**
	*科室成本信息习性分析表（变动性）<BR>
	*维护页面跳转
	*hrp/cost/analysis/c04/c0401MainPage
	*/
	
	@RequestMapping(value = "/hrp/cost/analysis/c04/c0401MainPage", method = RequestMethod.GET)
	public String c0401MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c04/c0401Main";
    }
	
	/**
	*科室成本信息习性分析表（变动性）<BR>
	*添加页面跳转
	*/
		@RequestMapping(value = "/hrp/cost/analysis/c04/costAnalysisC0401AddPage", method = RequestMethod.GET)
		public String costAnalysisC0401AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c04/c0401Add";

		}
	
		/**
		*收入分类分析<BR>
		*保存
		*/
		@RequestMapping(value = "/hrp/cost/analysis/c04/addCostAnalysisC0401", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addCostAnalysisC0401(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
			
			String addAnalysisC0401Json = c04Service.addAnalysisC0401(mapVo);

			return JSONObject.parseObject(addAnalysisC0401Json);
			
		}
		
	/**
	*科室成本信息习性分析表（变动性）<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c04/queryAnalysisC0401", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0401(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
           mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		
		String analysis = c04Service.queryAnalysisC0401(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	/**
	 *科室成本信息习性分析表（可控性）<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c04/c0402MainPage", method = RequestMethod.GET)
	public String c0402MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c04/c0402Main";
		
	}
	
	/**
	*科室成本信息习性分析表（可控性）<BR>
	*添加页面跳转
	*/
		@RequestMapping(value = "/hrp/cost/analysis/c04/costAnalysisC0402AddPage", method = RequestMethod.GET)
		public String costAnalysisC0402AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c04/c0402Add";

		}
	
	/**
	 *科室成本信息习性分析表（可控性）<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c04/queryAnalysisC0402", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0402(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			 
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c04Service.queryAnalysisC0402(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医院成本信息习性分析表（变动性）<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c04/c0403MainPage", method = RequestMethod.GET)
	public String c0403MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c04/c0403Main";
		
	}
	
	/**
	*医院成本信息习性分析表（变动性）<BR>
	*添加页面跳转
	*/
		@RequestMapping(value = "/hrp/cost/analysis/c04/costAnalysisC0403AddPage", method = RequestMethod.GET)
		public String costAnalysisC0403AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c04/c0403Add";

		}
	
		/**
		*医院成本信息习性分析表（变动性）<BR>
		*保存
		*/
		@RequestMapping(value = "/hrp/cost/analysis/c04/addCostAnalysisC0403", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addCostAnalysisC0403(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
			
			String addAnalysisC0403Json = c04Service.addAnalysisC0403(mapVo);

			return JSONObject.parseObject(addAnalysisC0403Json);
			
		}
		
	/**
	 *医院成本信息习性分析表（变动性）<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c04/queryAnalysisC0403", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0403(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c04Service.queryAnalysisC0403(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *医院成本信息习性分析表（可控性）<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c04/c0404MainPage", method = RequestMethod.GET)
	public String c0404MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c04/c0404Main";
		
	}
	
	/**
	*医院成本信息习性分析表（可控性）<BR>
	*添加页面跳转
	*/
		@RequestMapping(value = "/hrp/cost/analysis/c04/costAnalysisC0404AddPage", method = RequestMethod.GET)
		public String costAnalysisC0404AddPage(Model mode) throws Exception {

			return "hrp/cost/analysis/c04/c0404Add";

		}
	
	/**
	 *医院成本信息习性分析表（可控性）<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c04/queryAnalysisC0404", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0404(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		String analysis = c04Service.queryAnalysisC0404(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
}
