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
import com.chd.base.SessionManager;
import com.chd.hrp.acc.serviceImpl.AccParaServiceImpl;
import com.chd.hrp.cost.controller.CostParaController;
import com.chd.hrp.cost.entity.CostPara;
import com.chd.hrp.cost.service.CostParaService;
 
@Controller
public class CostParaController extends BaseController{
	private static Logger logger = Logger.getLogger(CostParaController.class);
	

	@Resource(name = "accParaService")
	private final AccParaServiceImpl accParaService = null;
   
    
	/**
	*系统参数<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costpara/costParaMainPage", method = RequestMethod.GET)
	public String costParaMainPage(Model mode) throws Exception {

		return "hrp/cost/costpara/costParaMain";

	}
	
	/**
	*系统参数<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costpara/queryCostPara", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("mod_code", "03");
		String accPara = accParaService.queryAccPara(getPage(mapVo));

		return JSONObject.parseObject(accPara);
		
	}
	
	/**
	*系统参数<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costpara/updateBatchCostPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchCostPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String modCode=SessionManager.getModCode();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("mod_code", modCode);
		
		
		String accParaJson = "" ;
		
		try{
		 
			accParaJson = accParaService.updateBatchAccPara(mapVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(accParaJson);
	}
	

}
