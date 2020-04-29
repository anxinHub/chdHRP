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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.CostInComeCollectionService;
 
/**
 * @Title. @Description. 科室成本收入归集
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostInComeCollectionController extends BaseController {

	private static Logger logger = Logger.getLogger(CostInComeCollectionController.class);

	@Resource(name = "costInComeCollectionService")
	private final CostInComeCollectionService costInComeCollectionService = null;

	/**
	 * 科室成本收入归集<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costincomecollection/costCollectionMainPage", method = RequestMethod.GET)
	public String costAssDetailMainPage(Model mode) throws Exception {

		return "hrp/cost/costincomecollection/costInComeCollectionMain";

	}
	/**
	 * 科室成本收入归集<BR>
	 * 维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costincomecollection/costCollectionMainPrmPage", method = RequestMethod.GET)
	public String costAssDetailMainPrmPage(Model mode) throws Exception {
		
		return "hrp/cost/costincomecollection/costInComeCollectionMainPrm";
		
	}

	@RequestMapping(value = "/hrp/cost/costincomecollection/queryIncomeCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIncomeCollection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String queryIncomeCollection = costInComeCollectionService.queryIncomeCollection(getPage(mapVo));
		return JSONObject.parseObject(queryIncomeCollection);
	}
	
	
	
	@RequestMapping(value = "/hrp/cost/costincomecollection/queryIncomeCollectionPrmHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIncomeCollectionPrmHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
		    String queryIncomeCollection = costInComeCollectionService.queryIncomeCollectionPrmHead(getPage(mapVo));
		    return JSONObject.parseObject(queryIncomeCollection);
		
	}
	
	@RequestMapping(value = "/hrp/cost/costincomecollection/queryIncomeCollectionPrm", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryIncomeCollectionPrm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String queryIncomeCollection = costInComeCollectionService.queryIncomeCollectionPrm(getPage(mapVo));
		
		return JSONObject.parseObject(queryIncomeCollection);
		
	}
	
	/**
	 * 
	* @Title: costCollectionCheckPage
	* @Description: 校验收入数据
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月12日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomecollection/costCollectionCheckPage", method = RequestMethod.GET)
	public String costCollectionCheckPage(Model mode) throws Exception {
		
		String para_value = MyConfig.getSysPara("03002");
		
		String pageUrl="";
		
		if("1".equals(para_value)){
			pageUrl = "hrp/cost/costincomecollection/costCollectionMainCheck";
		}
		if("2".equals(para_value)){
			pageUrl = "hrp/cost/costincomecollection/costCollectionDetailCheck";
		}
		
		return pageUrl;

	}
	
	/**
	 * 
	* @Title: queryIncomeCollection
	* @Description: 校验收入数据查询(类别)
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月12日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomecollection/queryCostCollectionMainCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostCollectionMainCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String year_month = mapVo.get("year_month").toString();
		mapVo.put("acc_year", year_month.substring(0, 4));
		mapVo.put("acc_month", year_month.substring(4, 6));
		String costCollectionCheckJson = costInComeCollectionService.queryCostCollectionMainCheck(getPage(mapVo));
		return JSONObject.parseObject(costCollectionCheckJson);
	}
	
	/**
	 * 
	* @Title: queryIncomeCollection
	* @Description: 校验收入数据查询(项目)
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月12日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomecollection/queryCostCollectionDetailCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostCollectionDetailCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String year_month = mapVo.get("year_month").toString();
		mapVo.put("acc_year", year_month.substring(0, 4));
		mapVo.put("acc_month", year_month.substring(4, 6));
		String costCollectionCheckJson = costInComeCollectionService.queryCostCollectionDetailCheck(getPage(mapVo));
		return JSONObject.parseObject(costCollectionCheckJson);
	}
	

	/**
	 * 成本收入<BR>
	 */
	@RequestMapping(value = "/hrp/cost/costincomecollection/addIncomeCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addIncomeCollection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_user", SessionManager.getUserId());

		String year_month = mapVo.get("year_month").toString();

		mapVo.put("acc_year", year_month.substring(0, 4));

		mapVo.put("acc_month", year_month.substring(4, 6));

		String addIncomeCollectionJson = costInComeCollectionService.addIncomeCollection(mapVo);

		return JSONObject.parseObject(addIncomeCollectionJson);

	}
}
