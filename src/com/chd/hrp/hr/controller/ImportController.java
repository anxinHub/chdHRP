package com.chd.hrp.hr.controller;

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
import com.chd.hrp.hr.service.ImportService;



/**
 * 
 * @author Administrator
 *添加
 */
@Controller
@RequestMapping(value = "/hrp/hr/{nm|os|pf|pt|am|srm|em|tm|mm|hm|pom|lm|sm|cm|rm}/")
public class ImportController  extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ImportController.class);
	
	// 引入Service服务
	@Resource(name = "importService")
	private final ImportService importService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "{*}/{*}ImprotPage", method = RequestMethod.GET)
	public String BaseAddPage(HttpServletRequest request,@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
        //mode中放入一些基本参数 如果页面使用可以随时调用
		mode.addAttribute("group_id",  SessionManager.getGroupId());
		mode.addAttribute("hos_id",    SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("user_id",   SessionManager.getUserId());
		mode.addAttribute("tab_code", mapVo.get("tab_code"));
		mode.addAttribute("ui", mapVo.get("ui"));
		return "hrp/hr/sc/hrImport";
	}

	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "importData{*}", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		
       String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
		
		servletPath = servletPath.substring(servletPath.lastIndexOf("/")+11, servletPath.length()).replace(".do", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
		mapVo.put("design_code","import"+servletPath);  		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if (mapVo.get("user_code") == null) {
			mapVo.put("user_code", SessionManager.getUserCode());
		}
		
		String reJson = importService.importData(mapVo);
		return reJson;
	}
	
}