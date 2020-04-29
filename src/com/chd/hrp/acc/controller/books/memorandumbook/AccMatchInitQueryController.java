package com.chd.hrp.acc.controller.books.memorandumbook;

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
import com.chd.hrp.acc.service.books.memorandumbook.AccMatchInitService;
@Controller
public class AccMatchInitQueryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccMatchInitQueryController.class);
	
	@Resource(name = "accMatchInitService")
	private final AccMatchInitService accMatchInitService = null;
	
	/**
	*财政配套资金初始账表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accmatchinit/queryAccMatchInitQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccMatchInitQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		String accMatchInitJson = accMatchInitService.queryAccMatchInit(getPage(mapVo));

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	
	/**
	*配套资金初始账表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accmatchinit/accMatchInitQueryMainPage", method = RequestMethod.GET)
	public String accMatchInitMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("attr_code", mapVo.get("attr_code"));
		return "hrp/acc/accmatchinit/accMatchInitMain";
	}
	
}
