package com.chd.hrp.med.controller.advice;

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
import com.chd.hrp.med.service.advice.MedAdviceService;

@Controller
public class MedAdviceController extends BaseController{
	private static Logger logger = Logger.getLogger(MedAdviceController.class);
	
	@Resource(name = "medAdviceService")
	private final MedAdviceService medAdviceService = null;
	
	@RequestMapping(value = "/hrp/med/advice/medAdviceMainPage", method = RequestMethod.GET)
	public String medAdviceMainPage(Model mode) throws Exception {

		return "hrp/med/advice/medAdviceMain";

	}
	
	@RequestMapping(value = "/hrp/med/advice/queryMedAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){	
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());      
		}
		
		String medAdvice = medAdviceService.queryMedAdvice(getPage(mapVo));

		return JSONObject.parseObject(medAdvice);
	}
	
	
	@RequestMapping(value = "/hrp/med/advice/initMedAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initMedAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){	
	    	mapVo.put("group_id", SessionManager.getGroupId());		
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());        
		}
		String medAdvice = medAdviceService.initMedAdvice(mapVo);

		return JSONObject.parseObject(medAdvice);
	}
	
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/advice/medAdviceInPrintSetPage", method = RequestMethod.GET)
	public String medAdvicePrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
}
