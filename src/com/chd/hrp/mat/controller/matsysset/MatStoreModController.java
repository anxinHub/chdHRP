/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.mat.controller.matsysset;
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
import com.chd.base.SessionManager;
import com.chd.hrp.mat.serviceImpl.base.MatStoreModServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class MatStoreModController extends BaseController{
	private static Logger logger = Logger.getLogger(MatStoreModController.class);
	
	
	 @Resource(name = "matStoreModService")
	private final MatStoreModServiceImpl matStoreModService = null; 
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/mat/matsysset/matStoreModMainPage", method = RequestMethod.GET)
	public String matStoreModMainPage(Model mode) throws Exception {

		return "hrp/mat/matsysset/matStoreModMain";

	}

	// 保存
	@RequestMapping(value = "/hrp/mat/matsysset/addStoreModStart", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addStoreModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
 
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("user_id", SessionManager.getUserId());
				
				mapVo.put("opt_date", new Date());
				
				String modJson =  matStoreModService.addStoreModStart(mapVo) ;
				
				return JSONObject.parseObject(modJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/mat/matsysset/queryStoreMod", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryStoreMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
				mapVo.put("group_id", SessionManager.getGroupId());
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
		
			    String storeMod = matStoreModService.queryStoreMod(mapVo);
			    
				return JSONObject.parseObject(storeMod);
		
	}
	
	 

}

