package com.chd.hrp.hr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.InsertService;



/**
 * 
 * @author Administrator
 *添加
 */
@Controller
@RequestMapping(value = "/hrp/hr/{nm|os|pf|pt|am|srm|em|tm|mm|hm|pom|lm|sm|cm|rm}/")
public class InsertController  extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(InsertController.class);
	
	// 引入Service服务
	@Resource(name = "insertService")
	private final InsertService insertService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;


	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/**/add{*}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBaseInfo(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, Model mode) throws Exception {
	String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
		
		servletPath = servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length()).replace(".do", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
		mapVo.put("design_code",servletPath);  				
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
	
		
		String str = insertService.addBaseInfo(mapVo);

		return JSONObject.parseObject(str);

	}
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/**/add{*}"+"Batch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBaseInfoBatch(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, Model mode) throws Exception {
		
	String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
		
	servletPath = servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length()).replace("Batch.do", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
		mapVo.put("design_code",servletPath);		
		String str = null ;
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		
	
		str = insertService.addBaseInfo(mapVo);
		

		return JSONObject.parseObject(str);

	}
	
}