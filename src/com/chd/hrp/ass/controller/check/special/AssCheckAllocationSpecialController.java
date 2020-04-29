package com.chd.hrp.ass.controller.check.special;

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
import com.chd.hrp.ass.service.check.special.AssCheckAllocationSpecialService;

@Controller
public class AssCheckAllocationSpecialController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssCheckAllocationSpecialController.class);

	@Resource(name = "assCheckAllocationSpecialService")
	private final AssCheckAllocationSpecialService assCheckAllocationSpecialService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/allocation/assCheckPlanSpecialAllocationMainPage", method = RequestMethod.GET)
	public String assCheckPlanSpecialAllocationMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assspecial/asscheck/allocation/main";

	}
	
	/**
	 * @Description 
	 * 查询数据 全院固定资产分布
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object> key:'searchType' val:'sum'='汇总查询','detail'='明细查询','check'='盘点查询'
	 * 
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/allocation/queryAssCheckAllocationSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckAllocationSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assCheckAllocationSpecial = null;
		try {
			assCheckAllocationSpecial = assCheckAllocationSpecialService.queryAssCheckAllocationSpecial(getPage(mapVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return JSONObject.parseObject(assCheckAllocationSpecial);
		
	}
	
	
	
}
