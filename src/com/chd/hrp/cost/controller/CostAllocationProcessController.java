package com.chd.hrp.cost.controller;

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
import com.chd.hrp.cost.service.CostAllocationProcessService;

/**
 * @Title. @Description. 开单收入统计
 * 
 * @Author: linuxxu
 * @email: linuxxu@s-chd.com
 * @Version: 1.0
 */
@Controller
public class CostAllocationProcessController extends BaseController {

	private static Logger logger = Logger.getLogger(CostAllocationProcessController.class);

	@Resource(name = "costAllocationProcessService")
	private final CostAllocationProcessService costAllocationProcessService = null;

	/**
	 * 
	 * @Title: costIncomeDetailMainPage
	 * @Description: 开单收入统计页面跳转
	 * @param @param  mode
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 * @date 2020年2月14日
	 * @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costallocationprocess/costAllocationProcessMainPage", method = RequestMethod.GET)
	public String costAllocationProcessMainPage(Model mode) throws Exception {
		return "hrp/cost/costallocationprocess/costAllocationProcessMain";

	}

	/**
	 * 
	 * @Description: 成本分摊过程数据
	 * @param @param  mode
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 */
	@RequestMapping(value = "/hrp/cost/costallocationprocess/queryCostAllocationProcess", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostAllocationProcess(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
		String process = costAllocationProcessService.queryCostAllocationProcess(getPage(mapVo));
		
		return JSONObject.parseObject(process);

	}
}
