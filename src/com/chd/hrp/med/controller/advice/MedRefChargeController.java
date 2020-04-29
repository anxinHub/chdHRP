package com.chd.hrp.med.controller.advice;

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
import com.chd.hrp.med.service.advice.MedRefChargeService;

@Controller
public class MedRefChargeController extends BaseController{
	private static Logger logger = Logger.getLogger(MedRefChargeController.class);
	
	@Resource(name = "medRefChargeService")
	private final MedRefChargeService medRefChargeService = null;
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/medRefChargeMainPage", method = RequestMethod.GET)
	public String medRefChargeMainPage(Model mode) throws Exception {

		return "hrp/med/advice/writeoffsetting/medRefChargeMain";
	}
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/queryMedRefCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedRefCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){	
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());        
		}
		String medRefCharge = medRefChargeService.queryMedRefCharge(getPage(mapVo));

		return JSONObject.parseObject(medRefCharge);
	}
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/addMedRefCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedRefCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String medRefChargeJson = "";
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
			medRefChargeJson = medRefChargeService.addMedRefCharge(detailVo);
		}

		return JSONObject.parseObject(medRefChargeJson);
	}
	
	
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/deleteMedRefCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedRefCharge(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
		
		String medRefStoreDeptJson = medRefChargeService.deleteBatchMedRefCharge(listVo);

		return JSONObject.parseObject(medRefStoreDeptJson);
	}
	
}
