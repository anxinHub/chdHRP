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
import com.chd.hrp.htcg.entity.making.HtcgSchemeRecipeMergeRule;
import com.chd.hrp.htcg.service.making.HtcgSchemeRecipeMergeRuleService;
import com.chd.hrp.htcg.serviceImpl.making.HtcgSchemeRecipeMergeRuleServiceImpl;

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
public class HtcgSchemeRecipeMergeRuleController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemeRecipeMergeRuleController.class);

	@Resource(name = "htcgSchemeRecipeMergeRuleService")
	private final HtcgSchemeRecipeMergeRuleService htcgSchemeRecipeMergeRuleService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/htcgSchemeRecipeMergeRuleMainPage", method = RequestMethod.GET)
	public String schemeRecipeMergeRuleMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/schemerecipemergerule/htcgSchemeRecipeMergeRuleMain";

	}

	
	// 生成
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/initHtcgSchemeRecipeMergeRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgSchemeRecipeMergeRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcgSchemeRecipeMergeRuleJson = "";
		try {
			htcgSchemeRecipeMergeRuleJson = htcgSchemeRecipeMergeRuleService.initHtcgSchemeRecipeMergeRule(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcgSchemeRecipeMergeRuleJson = e.getMessage();
		}
		return JSONObject.parseObject(htcgSchemeRecipeMergeRuleJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/queryHtcgSchemeRecipeMergeRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemeRecipeMergeRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String schemeRecipeMergeRule = htcgSchemeRecipeMergeRuleService.queryHtcgSchemeRecipeMergeRule(getPage(mapVo));
		return JSONObject.parseObject(schemeRecipeMergeRule);

	}
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/saveHtcgSchemeRecipeMergeRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcgSchemeRecipeMergeRule(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
            String[] ids=id.split("@");
            mapVo.put("group_id", ids[0]);
    		mapVo.put("hos_id", ids[1]);
    		mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);
			mapVo.put("drgs_code", ids[4]);
			mapVo.put("recipe_merge_code", ids[5]);
			listVo.add(mapVo);
		}
		String htcgSchemeRecipeMergeRuleJson = "";
		try {
			htcgSchemeRecipeMergeRuleJson = htcgSchemeRecipeMergeRuleService.updateBatchHtcgSchemeRecipeMergeRule(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcgSchemeRecipeMergeRuleJson = e.getMessage();
		}
		return JSONObject.parseObject(htcgSchemeRecipeMergeRuleJson);
	}
	
	// 删除
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/deleteHtcgSchemeRecipeMergeRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemeRecipeMergeRule(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			 String[] ids=id.split("@");
			 mapVo.put("group_id", ids[0]);
			 mapVo.put("hos_id", ids[1]);
			 mapVo.put("copy_code", ids[2]);
			 mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			 mapVo.put("drgs_code", ids[4]);// 实际实体类变量
			listVo.add(mapVo);
		}
		String htcgSchemeRecipeMergeRuleJson = "";
		try {
			htcgSchemeRecipeMergeRuleJson = htcgSchemeRecipeMergeRuleService.deleteBatchHtcgSchemeRecipeMergeRule(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcgSchemeRecipeMergeRuleJson = e.getMessage();
		}

		return JSONObject.parseObject(htcgSchemeRecipeMergeRuleJson);

	}
	
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/htcgSchemeSimilarTreatMainPage", method = RequestMethod.GET)
	public String htcgSchemeSimilarTreatMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("scheme_code", mapVo.get("scheme_code"));
		mode.addAttribute("drgs_code", mapVo.get("drgs_code"));
		return "hrp/htcg/making/schemerecipemergerule/htcgSchemeSimilarTreatMain";

	}
	
}
