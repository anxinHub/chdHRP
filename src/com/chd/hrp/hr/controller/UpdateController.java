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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.orangnize.HosStation;
import com.chd.hrp.hr.service.UpdateService;
import com.chd.hrp.hr.service.base.HrBaseService;



/**
 * 
 * @author Administrator
 *修改
 */
@Controller
@RequestMapping(value = "/hrp/hr/{nm|os|pf|pt|am|srm|em|tm|mm|hm|pom|lm|sm|cm|rm}/")
public class UpdateController  extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(UpdateController.class);
	
	// 引入Service服务
	@Resource(name = "updateService")
	private final UpdateService updateService = null;

	

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/**/update{*}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBaseInfo(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, Model mode) throws Exception {
		  String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
			
			servletPath = servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length()).replace(".do", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
			mapVo.put("design_code",servletPath);  	
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		try {
			String str = updateService.updateBaseInfo(mapVo);

			return JSONObject.parseObject(str);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/**
	 * @Description 更新数据批量
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/**/update{*}"+"Batch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBaseInfoBatch(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, Model mode) throws Exception {
		String str = null ;
       String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
		
		servletPath = servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length()).replace("Batch.do", "");//    servletPath示例：hrp/hr/os/basestation/baseStationMain
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
		try {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		/**
		 * 解析整行保存的表格数据
		 */
		if(mapVo.containsKey("ParamVo")){
			
			List<Map> listVo = JSONArray.parseArray(mapVo.get("ParamVo").toString(), Map.class);
			for (Map map : listVo) {
				for(Object key : map.keySet()){
	        		   Object value = map.get(key);
	        		   mapVo.put(key.toString(), value);
			}
				 str = updateService.updateBaseInfo(mapVo);
			}
			
			
		}
	
		
		

		return JSONObject.parseObject(str);

	
		} catch (Exception e) {
		logger.error(e.getMessage(), e);
		return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
	}
	}

}