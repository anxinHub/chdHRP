package com.chd.hrp.mat.controller.outsum;

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
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.outsum.MatOutSumService;

/**
 *@author Fan Yang
 *@date   2020年4月21日
 *@email   yangfan1105@dhcc.com.cn
 */
@Controller
@RequestMapping(value="/hrp/mat/outsum")
public class MatOutSumController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatOutSumController.class);
	
	@Resource(name = "matOutSumService")
	private final MatOutSumService matOutSumService = null;
	
	@RequestMapping(value="/singleSumPage", method = RequestMethod.GET)
	public String matDeptSingleSumPage(Model mode) throws Exception{
		return "hrp/mat/outsum/singleSumPage";
	}
	
	@RequestMapping(value = "/queryOutSumSingleSum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryOutSumSingleSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matOutSumService.queryOutSumSingleSum(mapVo);
		
		return JSONObject.parseObject(retJson);
	}
	
	@RequestMapping(value="/deptStoreCrossPage", method = RequestMethod.GET)
	public String matDeptStoreCrossPage(Model mode) throws Exception{
		return "hrp/mat/outsum/matDeptStoreCrossPage";
	}
	
	@RequestMapping(value = "/queryDeptStoreCross", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptStoreCross(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matOutSumService.queryDeptStoreCross(mapVo);
		
		return JSONObject.parseObject(retJson);
	}
}
