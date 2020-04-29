/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.repair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.service.repair.AssStatusService;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_INV_ARRT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssStatusController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssStatusController.class);
	
	//引入Service服务
	@Resource(name = "assStatusService")
	private final AssStatusService assStatusService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "/hrp/ass/repair/assstatus/assStatusMainPage", method = RequestMethod.GET)
	public String assStatusMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/assstatus/assStatusMain";

	}

	
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assstatus/saveAssStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssStatus(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assStatusJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		   assStatusJson = assStatusService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assStatusJson);
	}
	
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/repair/assstatus/queryAssStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssStatus(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	   
		String assStatus = assStatusService.query(getPage(mapVo));

		return JSONObject.parseObject(assStatus);
		
	}
	
 
   
    
}

