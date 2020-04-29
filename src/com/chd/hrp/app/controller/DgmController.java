package com.chd.hrp.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.hrp.app.service.DgmService;

/*浙江帝杰曼微信公众号接口*/
@Controller
@RequestMapping(value="/hrp/app/dgm")
public class DgmController {

	private static Logger logger = Logger.getLogger(DgmController.class);
	
	@Resource(name = "dgmService")
	private final DgmService dgmService = null;
	
	/*报修接口*/
	@RequestMapping(value = "/addrepair", method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String addRepair(@RequestParam Map<String, Object> map,HttpServletRequest request, HttpServletResponse response) {
		
		String reJson=null;
		try {
			
			reJson=dgmService.addRepair(map,request,response);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			reJson="{\"rtnmsg\":\""+e.getMessage()+"\"}";
		}
		return reJson;
		
	}
	
}
