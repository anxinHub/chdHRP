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
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.CostIncomeDetailService;

/**
* @Title. @Description.
* 科室收入数据明细表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostIncomeDetailController extends BaseController{
	private static Logger logger = Logger.getLogger(CostIncomeDetailController.class);
	
	
	@Resource(name = "costIncomeDetailService")
	private final CostIncomeDetailService costIncomeDetailService = null;
	
	/**
	 * 
	* @Title: costIncomeDetailMainPage
	* @Description: 科室收入采集(项目)页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月12日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomedetail/costIncomeDetailMainPage", method = RequestMethod.GET)
	public String costIncomeDetailMainPage(Model mode) throws Exception {
		return "hrp/cost/costincomedetail/costIncomeDetailMain";

	}
	
	
	
	/**
	 * 
	* @Title: queryCostIncomeDetail
	* @Description: 科室收入采集(项目)页面查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月12日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costincomedetail/queryCostIncomeDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostIncomeDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String costIncomeDetail = costIncomeDetailService.queryCostIncomeDetail(getPage(mapVo));

		return JSONObject.parseObject(costIncomeDetail);
		
	}
	
	/**
	*科室收入数据明细表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costincomedetail/deleteCostIncomeDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostIncomeDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("income_detail_id",ids[0]);   
            listVo.add(mapVo);
        }
		String costIncomeDetailJson;
		try {
			
			costIncomeDetailJson = costIncomeDetailService.deleteBatchCostIncomeDetail(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			costIncomeDetailJson = e.getMessage();
		}
	   return JSONObject.parseObject(costIncomeDetailJson);
			
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
		@RequestMapping(value="/hrp/cost/costincomedetail/impCostIncomeDetail",method = RequestMethod.POST)  
		@ResponseBody
	    public String impCostIncomeDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 
			try {
				
				String costIncomeMainJson=costIncomeDetailService.impCostIncomeDetail(mapVo);
				return costIncomeMainJson;
			} catch (Exception e) {
				return "{\"error\":\""+e.getMessage()+"\"}";
			}
	    }  
		
	
	/**
	*科室收入数据明细表<BR>
	*采集页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costincomedetail/costIncomeDetailCollectPage", method = RequestMethod.GET)
	public String costIncomeDetailCollectPage(Model mode) throws Exception {

		return "hrp/cost/costincomedetail/costIncomeDetailCollect";
	}

	/**
	*科室收入数据明细表<BR>
	*采集
	*/
	@RequestMapping(value = "/hrp/cost/costincomedetail/addCostIncomeDetailByHis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostIncomeDetailByHis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String costIncomeDetailJson;
		try {
			costIncomeDetailJson = costIncomeDetailService.addCostIncomeDetailByHis(mapVo);
		} catch (Exception e) {
			
			costIncomeDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(costIncomeDetailJson);
	}
}

