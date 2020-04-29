/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.warning;
import java.util.Date;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.warning.MatOrderArriveDateWarningService;
/**
 * 
 * @Description:
 * @Table:
 * MAT_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MatOrderArriveDateWarningController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatOrderArriveDateWarningController.class);
	
	//引入Service服务
	@Resource(name = "matOrderArriveDateWarningService")
	private final MatOrderArriveDateWarningService matOrderArriveDateWarningService = null ;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/warning/matorderarrivedatewarning/matOrderArriveDateWarningMainPage", method = RequestMethod.GET)
	public String matOrderArriveDateWarningMainPage(Model mode) throws Exception {

		return "hrp/mat/warning/matorderarrivedatewarning/matOrderArriveDateWarningMain";

	}

	/**
	 * @Description 
	 * 查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/						  
	@RequestMapping(value = "/hrp/mat/warning/matorderarrivedatewarning/queryMatOrderArriveDateWarning", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderArriveDateWarning(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("query_date") == null){
			mapVo.put("query_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		}
		String matProtocolMain = matOrderArriveDateWarningService.queryMatOrderArriveDateWarning(getPage(mapVo));

		return JSONObject.parseObject(matProtocolMain);
		
	}
	
	@RequestMapping(value = "/hrp/mat/warning/matorderarrivedatewarning/matOrderInfoPage", method = RequestMethod.GET)
	public String matOrderInfoPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		
		mode.addAttribute("begin_date", mapVo.get("begin_date"));
		mode.addAttribute("end_date", mapVo.get("end_date"));
		mode.addAttribute("query_date", mapVo.get("query_date"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("sup_code", mapVo.get("sup_code"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		mode.addAttribute("state", mapVo.get("state"));
		
		return "hrp/mat/warning/matorderarrivedatewarning/matOrderInfo";

	}
	/**
	 * @Description 
	 * 查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/						  
	@RequestMapping(value = "/hrp/mat/warning/matorderarrivedatewarning/queryMatOrderInvInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderInvInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String matProtocolMain = matOrderArriveDateWarningService.queryMatOrderInvInfo(getPage(mapVo));

		return JSONObject.parseObject(matProtocolMain);
		
	}
}

