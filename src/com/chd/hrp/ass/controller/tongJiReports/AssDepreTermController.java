
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
 

@Controller
public class AssDepreTermController extends BaseController {
	
private static Logger logger = Logger.getLogger(AssDepreExpireController.class);
	
	@Resource(name = "assDepreExpireService")
	private final AssDepreExpireService assDepreExpireService = null;

		
	@RequestMapping(value = "/hrp/ass/tongJiReports/assDepreTermPage", method = RequestMethod.GET)
	public String assDepreTermPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		return "hrp/ass/tongJiReports/assDepreTerm";

	}
	
	@RequestMapping(value = "/hrp/ass/tongJiReports/queryAssDepreTerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreTerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMain = assDepreExpireService.queryAssDepreTerm(getPage(mapVo));

		return JSONObject.parseObject(assInMain);

	}
 
}
