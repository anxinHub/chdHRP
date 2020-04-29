/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.hrp.cost.service.CostIncomeMainService;

/**
* @Title. @Description.
* 收入采集(类别)
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostIncomeMainController extends BaseController{
	private static Logger logger = Logger.getLogger(CostIncomeMainController.class);
	
	
	@Resource(name = "costIncomeMainService")
	private final CostIncomeMainService costIncomeMainService = null;
	
	
	
	/**
	 * 
	* @Title: costIncomeMainPage
	* @Description: 收入采集(类别)-页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String    
	* @date 2020年2月11日 下午4:05:06
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomemain/costIncomeMainPage", method = RequestMethod.GET)
	public String costIncomeMainPage(Model mode) throws Exception {
		return "hrp/cost/costincomemain/costIncomeMainMain";

	}
	
	
  /**
	* 
	* @Title: queryCostIncomeMain
	* @Description: 收入采集(类别)-查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2020年2月11日 
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomemain/queryCostIncomeMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostIncomeMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String costIncomeMain = costIncomeMainService.queryCostIncomeMain(getPage(mapVo));

		return JSONObject.parseObject(costIncomeMain);
		
	}
	
	
   /**
	* 
	* @Title: deleteCostIncomeMain
	* @Description: 删除
	* @param @param paramVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2020年2月11日
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomemain/deleteCostIncomeMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostIncomeMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("income_id",ids[0]);   
            listVo.add(mapVo);
        }
		String costIncomeMainJson = costIncomeMainService.deleteBatchCostIncomeMain(listVo);
		
	   return JSONObject.parseObject(costIncomeMainJson);
			
	}
	
   /**
    * 
	* @Title: impCostIncomeMain
	* @Description: 导入
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws IOException
	* @return String    
	* @date 2020年2月11日 
	* @author sjy
	 */
	@RequestMapping(value="/hrp/cost/costincomemain/impCostIncomeMain",method = RequestMethod.POST)  
	@ResponseBody
    public String impCostIncomeMain(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		try {
			
			String costIncomeMainJson=costIncomeMainService.impCostIncomeMain(mapVo);
			
			return costIncomeMainJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	
	
    }  
		
	/**
	* 
	* @Title: costIncomeMainCollectPage
	* @Description: 采集HIS数据页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月11日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomemain/costIncomeMainCollectPage", method = RequestMethod.GET)
	public String costIncomeMainCollectPage(Model mode) throws Exception {
		return "hrp/cost/costincomemain/costIncomeMainCollect";
	}

	/**
	 * 
	* @Title: addCostIncomeMainByHis
	* @Description: 通过DBLINK采集HIS收入(类别)
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月11日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomemain/addCostIncomeMainByHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostIncomeMainByHis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       
		String costIncomeMainJson;
		try {
			costIncomeMainJson = costIncomeMainService.addCostIncomeMainByHis(mapVo);
		} catch (Exception e) {
			
			costIncomeMainJson = e.getMessage();
		}

		return JSONObject.parseObject(costIncomeMainJson);
	}
	
  /**
	* 
	* @Title: deleteMonthlyCostIncomeMain
	* @Description: 按照核算月份区间删除
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月11日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomemain/deleteMonthlyCostIncomeMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMonthlyCostIncomeMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String costIncomeMainJson;
		try {
			costIncomeMainJson = costIncomeMainService.deleteMonthlyCostIncomeMain(mapVo);
		} catch (Exception e) {
			
			costIncomeMainJson = e.getMessage();
		}

		return JSONObject.parseObject(costIncomeMainJson);
	}
	
   }

