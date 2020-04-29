package com.chd.hrp.ass.controller.check.land;

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
import com.chd.hrp.ass.service.check.land.AssCheckAllocationLandService;

@Controller
public class AssCheckAllocationLandController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssCheckAllocationLandController.class);

	@Resource(name = "assCheckAllocationLandService")
	private final AssCheckAllocationLandService assCheckAllocationLandService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/asscheck/allocation/assCheckPlanLandAllocationMainPage", method = RequestMethod.GET)
	public String assCheckPlanLandAllocationMainPage(Model mode) throws Exception {

		return "hrp/ass/assland/asscheck/allocation/main";

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
	@RequestMapping(value = "/hrp/ass/assland/asscheck/allocation/queryAssCheckAllocationLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckAllocationLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assCheckAllocationLand = null;
		try {
			assCheckAllocationLand = assCheckAllocationLandService.queryAssCheckAllocationLand(getPage(mapVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return JSONObject.parseObject(assCheckAllocationLand);
		
	}
	
	
	
}
