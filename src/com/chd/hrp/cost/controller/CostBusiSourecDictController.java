package com.chd.hrp.cost.controller;

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
import com.chd.hrp.cost.entity.CostBusiSourecDict;
import com.chd.hrp.cost.service.CostBusiSourecDictService;
/**
* @Title. @Description.
* 开单收入统计
* @Author: linuxxu
* @email: linuxxu@s-chd.com
* @Version: 1.0
*/
@Controller
public class CostBusiSourecDictController extends BaseController{

	private static Logger logger = Logger.getLogger(CostBusiSourecDictController.class);
	
	@Resource(name = "costBusiSourecDictService")
	private final CostBusiSourecDictService costBusiSourecDictService = null;
	
	@RequestMapping(value = "/hrp/cost/costbusisourecdict/costBusiSourecDictMainPage", method = RequestMethod.GET)
	public String costBusiSourecDictMainPage(Model mode) throws Exception {

		return "hrp/cost/costbusisourecdict/costBusiSourecDictMain";

	}
	
	@RequestMapping(value = "/hrp/cost/costbusisourecdict/queryCostBusiSourecDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostBusiSourecDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		String busiSourecDict = costBusiSourecDictService.queryCostBusiSourecDict(getPage(mapVo));

		return JSONObject.parseObject(busiSourecDict);

	}
	
	
	// 添加页面  
	@RequestMapping(value = "/hrp/cost/costbusisourecdict/costBusiSourecDictAddPage", method = RequestMethod.GET)
	public String costBusiSourecDictAddPage(Model mode) throws Exception {

		return "hrp/cost/costbusisourecdict/costBusiSourecDictAdd";
	}
	
	@RequestMapping(value = "/hrp/cost/costbusisourecdict/addCostBusiSourecDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostBusiSourecDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			String busiSourecDictJson;

		  	try {
		  		
		  		busiSourecDictJson = costBusiSourecDictService.addCostBusiSourecDict(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				busiSourecDictJson = e.getMessage();
			}

			return JSONObject.parseObject(busiSourecDictJson);

	}
	
	
	@RequestMapping(value = "/hrp/cost/costbusisourecdict/deleteCostBusiSourecDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostBusiSourecDict(@RequestParam(value="ParamVo") String paramVo,Model mode)
			throws Exception {
		
		   String busiSourecDictJson = "";
		   
		   List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		   
			for (String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				mapVo.put("busi_data_source_type", ids[0]);
				mapVo.put("busi_data_source_code", ids[1]);
				
				listVo.add(mapVo);
			}
			
		try {
			
			busiSourecDictJson = costBusiSourecDictService.deleteBatchCostBusiSourecDict(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			busiSourecDictJson = e.getMessage();
		}

		return JSONObject.parseObject(busiSourecDictJson);

	}
	
	// 修改页面跳转 updateCostBusiSourecDict
		@RequestMapping(value = "/hrp/cost/costbusisourecdict/CostBusiSourecDictUpdatePage", method = RequestMethod.GET)
		
		public String CostBusiSourecDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			CostBusiSourecDict costBusiSourecDict = costBusiSourecDictService.queryCostBusiSourecDictByCode(mapVo);
				
			mode.addAttribute("busi_data_source_type", costBusiSourecDict.getBusi_data_source_type());
			
			mode.addAttribute("busi_data_source_code", costBusiSourecDict.getBusi_data_source_code());
			
			mode.addAttribute("busi_data_source_name", costBusiSourecDict.getBusi_data_source_name());

			return "hrp/cost/costbusisourecdict/costBusiSourecDictUpdate";
		}

		// 修改保存
		@RequestMapping(value = "/hrp/cost/costbusisourecdict/updateCostBusiSourecDict", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateCostBusiSourecDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			String busiSourecDictJson = "";
		
			try {
		  		
				busiSourecDictJson = costBusiSourecDictService.updateCostBusiSourecDict(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				busiSourecDictJson = e.getMessage();
			}

			return JSONObject.parseObject(busiSourecDictJson);
		}
	
}
