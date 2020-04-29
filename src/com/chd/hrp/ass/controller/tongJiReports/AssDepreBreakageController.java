/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.tongJiReports;
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
import com.chd.hrp.ass.service.disposal.AssDisposalMainService;
import com.chd.hrp.ass.service.tongJiReports.AssDepreBreakageService;
 

/**
 * @Description: 050701 资产报损查询
 * @Table: ASS_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDepreBreakageController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDepreBreakageController.class);

	// 引入Service服务
	@Resource(name = "assDepreBreakageService")
	private final AssDepreBreakageService assDepreBreakageService = null;
		
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/tongJiReports/assDepreBreakagePage", method = RequestMethod.GET)
	public String assDepreBreakagePage(Model mode) throws Exception {

		return "hrp/ass/tongJiReports/assDepreBreakage";

	}
	
	
	/**
	 * @Description 
	 * 查询数据 051001资产处置主表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssDepreBreakage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreBreakage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
 
	    
		String assDepreBreakage = assDepreBreakageService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreBreakage);
		
	}
	 

 
}
