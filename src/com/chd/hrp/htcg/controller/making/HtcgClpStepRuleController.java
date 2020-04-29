package com.chd.hrp.htcg.controller.making;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.hrp.htcg.entity.making.HtcgClpStepRule;
import com.chd.hrp.htcg.service.making.HtcgClpStepRuleService;
import com.chd.hrp.htcg.serviceImpl.making.HtcgClpStepRuleServiceImpl;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgClpStepRuleController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgClpStepRuleController.class);

	@Resource(name = "htcgClpStepRuleService")
	private final HtcgClpStepRuleService htcgClpStepRuleService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/clpsteprule/htcgClpStepRuleMainPage", method = RequestMethod.GET)
	public String htcgClpStepRuleMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/clpsteprule/htcgClpStepRuleMain";

	}

	// 生成
	@RequestMapping(value = "/hrp/htcg/making/clpsteprule/initHtcgClpStepRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgClpStepRule(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String schemeMrRuleJson = "";
		try {
			
			schemeMrRuleJson = htcgClpStepRuleService.initHtcgClpStepRule(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			schemeMrRuleJson = e.getMessage();
		}
		return JSONObject.parseObject(schemeMrRuleJson);

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/making/clpsteprule/queryHtcgClpStepRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgClpStepRule(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String clpStepRule = htcgClpStepRuleService.queryHtcgClpStepRule(getPage(mapVo));

		return JSONObject.parseObject(clpStepRule);

	}


	@RequestMapping(value = "/hrp/htcg/making/clpsteprule/saveHtcgClpStepRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcgClpStepRule(
			@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			mapVo.put("clp_step_code", ids[4]);// 实际实体类变量
			mapVo.put("beg_date", ids[5].equals("-")?"":ids[5]);// 实际实体类变量
			mapVo.put("end_date", ids[6].equals("-")?"":ids[6]);// 实际实体类变量
			mapVo.put("place", ids[7].equals("-")?"":ids[7]);// 实际实体类变量
			listVo.add(mapVo);
		}
		
		String schemeMrRuleJson = "";
		try {
			schemeMrRuleJson = htcgClpStepRuleService.updateBatchHtcgClpStepRule(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeMrRuleJson = e.getMessage();
		}
		return JSONObject.parseObject(schemeMrRuleJson);
	}
	
	// 删除
	@RequestMapping(value = "/hrp/htcg/making/clpsteprule/deleteHtcgClpStepRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgClpStepRule(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			mapVo.put("clp_step_code", ids[4]);// 实际实体类变量
			listVo.add(mapVo);
		}
		String clpStepRuleJson = "";
		try {
			clpStepRuleJson = htcgClpStepRuleService.deleteBatchHtcgClpStepRule(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			clpStepRuleJson = e.getMessage();
		}
		return JSONObject.parseObject(clpStepRuleJson);

	}
}
