package com.chd.hrp.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.hrp.sys.service.SysProcErrorService;

@Controller
@RequestMapping(value = "/hrp/sys/error")
public class ProcessErrorController {

	private static Logger logger = Logger.getLogger(ProcessErrorController.class);
	
	@Resource(name = "sysProcErrorService")
	private final SysProcErrorService sysProcErrorService = null;
	
	@RequestMapping(value = "/mainPage")
	public String initProcError(){
		return "hrp/sys/error/main";
	}
	@RequestMapping(value = "/queryErrors")
	@ResponseBody
	public Map<String, Object> getAllProcError(String modCode,String mtype){
		String json=sysProcErrorService.queryErrorsByModCode(modCode,mtype);
		 return JSONObject.parseObject(json);
	}
	@ResponseBody
	@RequestMapping(value = "/queryMods")
	public String getAllMods(){
		String json=sysProcErrorService.getMods();
		 return json;
	}
	
	@RequestMapping(value = "/executeSql")
	@ResponseBody
	public Map<String, Object> reExecuteSql(@RequestParam(value="ParamVo") String paramVo, Model mode) throws DocumentException{
		
		String hosLevelJson = sysProcErrorService.errorExecuteSql(paramVo);
	    return JSONObject.parseObject(hosLevelJson);
	   
		
	}
	
}
