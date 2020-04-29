/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.util.HashMap;
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
import com.chd.hrp.acc.service.AccVouchFlowService;

/**
* @Title. @Description.
* 凭证制单流程
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

@Controller
public class AccVouchFlowController extends BaseController{
	private static Logger logger = Logger.getLogger(AccVouchFlowController.class);
	
	
	@Resource(name = "accVouchFlowService")
	private final AccVouchFlowService accVouchFlowService = null;
   
   
	/**
	* 主页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accvouchflow/accVouchFlowMainPage", method = RequestMethod.GET)
	public String accVouchFlowMainPage(Model mode) throws Exception {

		return "hrp/acc/accvouchflow/accVouchFlowMain";

	}
	
	/**
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accvouchflow/queryAccVouchFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVouchFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accVouchFlow = accVouchFlowService.queryAccVouchFlow(getPage(mapVo));

		return JSONObject.parseObject("{\"data\":"+accVouchFlow+"}");
	}
	
	/**
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accvouchflow/saveAccVouchFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccVouchFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			
			retMap = accVouchFlowService.saveAccVouchFlow(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
}

