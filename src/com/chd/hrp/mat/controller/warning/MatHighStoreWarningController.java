/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.warning;
import java.util.*;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.warning.MatSafeStoreWarningService;

@Controller
public class MatHighStoreWarningController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatHighStoreWarningController.class);
	
	//引入Service服务
	@Resource(name = "matSafeStoreWarningService")
	private final MatSafeStoreWarningService matSafeStoreWarningService = null ;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/warning/mathighstorewarning/matHighStoreWarningMainPage", method = RequestMethod.GET)
	public String matBatchWarningMainPage(Model mode) throws Exception {

		return "hrp/mat/warning/mathighstorewarning/matHighStoreWarningMain";

	}

	/**
	 * @Description 
	 * 查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/warning/mathighstorewarning/queryMatHighStoreWarning", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatHighStoreWarning(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matProtocolMain = matSafeStoreWarningService.queryMatHighStoreWarning(getPage(mapVo));

		return JSONObject.parseObject(matProtocolMain);
		
	}
	
}

