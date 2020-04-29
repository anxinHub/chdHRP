package com.chd.hrp.ass.controller.check.general;

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
import com.chd.hrp.ass.service.check.general.AssCheckAllocationGeneralService;

@Controller
public class AssCheckAllocationGeneralController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssCheckAllocationGeneralController.class);

	@Resource(name = "assCheckAllocationGeneralService")
	private final AssCheckAllocationGeneralService assCheckAllocationGeneralService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/allocation/assCheckPlanGeneralAllocationMainPage", method = RequestMethod.GET)
	public String assCheckPlanGeneralAllocationMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assgeneral/asscheck/allocation/main";

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
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/allocation/queryAssCheckAllocationGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckAllocationGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assCheckAllocationGeneral = null;
		try {
			assCheckAllocationGeneral = assCheckAllocationGeneralService.queryAssCheckAllocationGeneral(getPage(mapVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return JSONObject.parseObject(assCheckAllocationGeneral);
		
	}
	
	
	
}
