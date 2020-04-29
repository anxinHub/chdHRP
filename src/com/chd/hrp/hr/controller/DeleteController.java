package com.chd.hrp.hr.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItem;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.DeleteService;
import com.chd.hrp.hr.service.ExportExcelService;



/**
 * 
 * @author Administrator
 *删除
 */
@Controller
@RequestMapping(value = "/hrp/hr/{nm|os|pf|pt|am|srm|em|tm|mm|hm|pom|lm|sm|cm|rm}/")
public class DeleteController  extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(DeleteController.class);
	
	// 引入Service服务
	@Resource(name = "deleteService")
	private final DeleteService deleteService = null;

	// 引入Service服务
	@Resource(name = "exportExcelService")
	private final ExportExcelService exportExcelService = null;
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/**/delete{*}", method = RequestMethod.POST)
	@ResponseBody
	public String deleteBaseInfo(@RequestParam String paramVo,HttpServletRequest request, Model mode) throws Exception {

		String str = "";
		boolean falg = true;
	
		try {
			String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
			
			servletPath = servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length()).replace(".do", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
	             
			return deleteService.deleteBaseInfo(paramVo,servletPath);

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	
}