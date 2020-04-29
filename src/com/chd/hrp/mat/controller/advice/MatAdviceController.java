package com.chd.hrp.mat.controller.advice;

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
import com.chd.hrp.mat.service.advice.MatAdviceService;

@Controller
public class MatAdviceController extends BaseController{
	private static Logger logger = Logger.getLogger(MatAdviceController.class);
	
	@Resource(name = "matAdviceService")
	private final MatAdviceService matAdviceService = null;
	
	@RequestMapping(value = "/hrp/mat/advice/matAdviceMainPage", method = RequestMethod.GET)
	public String matAdviceMainPage(Model mode) throws Exception {

		return "hrp/mat/advice/matAdviceMain";

	}
	
	@RequestMapping(value = "/hrp/mat/advice/queryMatAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){	
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());      
		}
		
		String matAdvice = matAdviceService.queryMatAdvice(getPage(mapVo));

		return JSONObject.parseObject(matAdvice);
	}
	
	
	@RequestMapping(value = "/hrp/mat/advice/initMatAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initMatAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){	
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());        
		}
		String matAdvice = matAdviceService.initMatAdvice(mapVo);

		return JSONObject.parseObject(matAdvice);
	}
}
