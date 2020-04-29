/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.warning;
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
import com.chd.hrp.med.service.warning.MedBatchWarningService;
/**
 * 
 * @Description:
 * @Table:
 * MED_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedBatchWarningController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedBatchWarningController.class);
	
	//引入Service服务
	@Resource(name = "medBatchWarningService")
	private final MedBatchWarningService medBatchWarningService = null ;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/warning/medbatchwarning/medBatchWarningMainPage", method = RequestMethod.GET)
	public String medBatchWarningMainPage(Model mode) throws Exception {

		return "hrp/med/warning/medbatchwarning/medBatchWarningMain";

	}

	/**
	 * @Description 
	 * 查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/warning/medbatchwarning/queryMedBatchWarning", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedBatchWarning(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("is_com") == null){
			
			mapVo.put("is_com", "");
	        
		}
		if(mapVo.get("queryDate") == null){
			
			mapVo.put("queryDate", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
	        
		}
		String medProtocolMain = medBatchWarningService.queryMedBatchWarning(getPage(mapVo));

		return JSONObject.parseObject(medProtocolMain);
		
	}
	
}

