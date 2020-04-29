
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.tongJiReports.AssDepreExpireService;
 

/**
 * @Description: 050701 璧勪骇鎶樻棫鍒版湡鏌ヨ
 * @Table: ASS_IN_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDepreExpireController extends BaseController {
	
	private static Logger logger = Logger.getLogger(AssDepreExpireController.class);
	
	@Resource(name = "assDepreExpireService")
	private final AssDepreExpireService assDepreExpireService = null;

		
	@RequestMapping(value = "/hrp/ass/tongJiReports/assDepreExpirePage", method = RequestMethod.GET)
	public String assDepreExpirePage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		return "hrp/ass/tongJiReports/assDepreExpire";

	}
	
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssDepreExpire", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreExpire(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMain = assDepreExpireService.queryAssDepreExpire(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
 
}
