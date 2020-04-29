/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.htc.controller.info.basic;
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
import com.chd.hrp.htc.service.info.basic.HtcCostItemDictNoService;

/**
* @Title. @Description.
* 成本项目变更表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class HtcCostItemDictNoController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcCostItemDictNoController.class);
	
	
	@Resource(name = "htcCostItemDictNoService")
	private final HtcCostItemDictNoService htcCostItemDictNoService = null;
   
   

	
	/**
	*成本项目变更表<BR>
	*查询
	*/ 
	@RequestMapping(value = "/hrp/htc/info/basic/costitemno/queryHtcCostItemDictNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcCostItemDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String costItemDictNo = htcCostItemDictNoService.queryHtcCostItemDictNo(getPage(mapVo));

		return JSONObject.parseObject(costItemDictNo);
		
	}
	
}

