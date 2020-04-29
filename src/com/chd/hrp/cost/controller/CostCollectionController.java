/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
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
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.CostCollectionService;
/**
 * @Title. @Description. 科室成本成本归集
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostCollectionController extends BaseController {
	private static Logger logger = Logger.getLogger(CostCollectionController.class);

	
	@Resource(name = "costCollectionService")
	private final CostCollectionService costCollectionService = null;
	/**
	 * 科室成本成本归集<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costcollection/costCollectionMainPage", method = RequestMethod.GET)
	public String costAssDetailMainPage(Model mode) throws Exception {

		return "hrp/cost/costcollection/costCollectionMain";

	}
	/**
	 * 科室成本成本归集<BR>
	 * 维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcollection/costCollectionMainPrmPage", method = RequestMethod.GET)
	public String costAssDetailMainPrmPage(Model mode) throws Exception {
		
		return "hrp/cost/costcollection/costCollectionMainPrm";
		
	}

	/**
	 * 科室成本成本归集查询<BR>
	 * 
	 */

	@RequestMapping(value = "/hrp/cost/costcollection/queryCostCollection", method = RequestMethod.POST) 
	@ResponseBody
	
	public Map<String, Object> queryCostCollection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String costCollectionJson = costCollectionService.queryCostCollection(getPage(mapVo));

		return JSONObject.parseObject(costCollectionJson);
		
	}
	
	/**
	 * 科室成本成本归集查询<BR>
	 * 
	 */
	@RequestMapping(value = "/hrp/cost/costcollection/queryCostCollectionPrmHead", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> queryCostCollectionPrmHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
		String costCollectionJson = costCollectionService.queryCostCollectionPrmHead(mapVo);
		return JSONObject.parseObject(costCollectionJson);
	}
	/**
	 * 科室成本成本归集查询<BR>
	 * 
	 */
	@RequestMapping(value = "/hrp/cost/costcollection/queryCostCollectionPrm", method = RequestMethod.POST) 
	@ResponseBody
	public Map<String, Object> queryCostCollectionPrm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
		String costCollectionJson = costCollectionService.queryCostCollectionPrm(getPage(mapVo));
		return JSONObject.parseObject(costCollectionJson);
	}
	
	/**
	*科室成本成本添加<BR>
	*
	*/
	@RequestMapping(value = "/hrp/cost/costcollection/addCostCollection", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostCollection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
			
           mapVo.put("copy_code", SessionManager.getCopyCode());
        
		  String year_month = mapVo.get("year_month").toString();

		  mapVo.put("acc_year", year_month.substring(0, 4));
		
		  mapVo.put("acc_month", year_month.substring(4, 6));
		  mapVo.put("user_id", SessionManager.getUserId());
		
		String addCostImputationJson = costCollectionService.addCostCollection(mapVo);
		
		
		return JSONObject.parseObject(addCostImputationJson);
		
	}
	
}
