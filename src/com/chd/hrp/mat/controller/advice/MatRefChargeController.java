package com.chd.hrp.mat.controller.advice;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.service.advice.MatRefChargeService;

@Controller
public class MatRefChargeController extends BaseController{
	private static Logger logger = Logger.getLogger(MatRefChargeController.class);
	
	@Resource(name = "matRefChargeService")
	private final MatRefChargeService matRefChargeService = null;
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/matRefChargeMainPage", method = RequestMethod.GET)
	public String matRefChargeMainPage(Model mode) throws Exception {

		return "hrp/mat/advice/writeoffsetting/matRefChargeMain";
	}
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/queryMatRefCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatRefCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){	
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());        
		}
		String matRefCharge = matRefChargeService.queryMatRefCharge(getPage(mapVo));

		return JSONObject.parseObject(matRefCharge);
	}
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/addMatRefCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatRefCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String matRefChargeJson = "";
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		for (Map<String, Object> detailVo : detail) {
			
			if(detailVo.get("group_id") == null){
				detailVo.put("group_id", SessionManager.getGroupId());
			}
			if(detailVo.get("hos_id") == null){
				detailVo.put("hos_id", SessionManager.getHosId());
			}
			if(detailVo.get("copy_code") == null){
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}
			if(!detailVo.containsKey("inv_id")){
				break;
			}
			matRefChargeJson = matRefChargeService.addMatRefCharge(detailVo);
		}

		return JSONObject.parseObject(matRefChargeJson);
	}
	
	
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/deleteMatRefCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatRefCharge(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("group_id", id.split("@")[0]);
			
			mapVo.put("hos_id", id.split("@")[1]);
			
			mapVo.put("copy_code", id.split("@")[2]);
			
			mapVo.put("inv_id", id.split("@")[3]);
			
			mapVo.put("charge_item_id", id.split("@")[4]);
			
			listVo.add(mapVo);
		}
		
		String matRefStoreDeptJson = matRefChargeService.deleteBatchMatRefCharge(listVo);

		return JSONObject.parseObject(matRefStoreDeptJson);
	}
	
}
