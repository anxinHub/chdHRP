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
import com.chd.hrp.htcg.service.making.HtcgSchemeRecipeGroupRuleService;

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
public class HtcgSchemeRecipeGroupRuleController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemeRecipeGroupRuleController.class);

	@Resource(name = "htcgSchemeRecipeGroupRuleService")
	private final HtcgSchemeRecipeGroupRuleService htcgSchemeRecipeGroupRuleService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/schemerecipegrouprule/htcgSchemeRecipeGroupRuleMainPage", method = RequestMethod.GET)
	public String schemeRecipeGroupRuleMainPage(Model mode) throws Exception {
		return "hrp/htcg/making/schemerecipegrouprule/htcgSchemeRecipeGroupRuleMain";

	}

	// 生成
	@RequestMapping(value = "/hrp/htcg/making/schemerecipegrouprule/initHtcgSchemeRecipeGroupRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgSchemeRecipeGroupRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String schemePathConfJson = "";
		try {
			schemePathConfJson = htcgSchemeRecipeGroupRuleService.initHtcgSchemeRecipeGroupRule(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemePathConfJson = e.getMessage();
		}
		return JSONObject.parseObject(schemePathConfJson);

	}
	// 查询
	@RequestMapping(value = "/hrp/htcg/making/schemerecipegrouprule/queryHtcgSchemeRecipeGroupRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemeRecipeGroupRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String schemeRecipeGroupRule = htcgSchemeRecipeGroupRuleService.queryHtcgSchemeRecipeGroupRule(getPage(mapVo));
		return JSONObject.parseObject(schemeRecipeGroupRule);

	}

	// 删除
	@RequestMapping(value = "/hrp/htcg/making/schemerecipegrouprule/deleteHtcgSchemeRecipeGroupRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemeRecipeGroupRule(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id",  ids[0]);
			mapVo.put("hos_id",  ids[1]);
			mapVo.put("copy_code",  ids[2]);
			mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			listVo.add(mapVo);
		}
		String schemeRecipeGroupRuleJson = "";
		try {
			schemeRecipeGroupRuleJson = htcgSchemeRecipeGroupRuleService.deleteBatchHtcgSchemeRecipeGroupRule(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeRecipeGroupRuleJson = e.getMessage();
		}
		return JSONObject.parseObject(schemeRecipeGroupRuleJson);
	}
	
	@RequestMapping(value = "/hrp/htcg/making/schemerecipegrouprule/saveHtcgSchemeRecipeGroupRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcgSchemeRecipeGroupRule(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
            String[] ids=id.split("@");
            mapVo.put("group_id", ids[0]);
    		mapVo.put("hos_id", ids[1]);
    		mapVo.put("copy_code", ids[2]);
			mapVo.put("scheme_code", ids[3]);// 实际实体类变量
			mapVo.put("recipe_group_code", ids[4]);// 实际实体类变量
			mapVo.put("total_percent", ids[5]);// 实际实体类变量
			listVo.add(mapVo);
		}
		String schemeMrRuleJson = htcgSchemeRecipeGroupRuleService.updateBatchHtcgSchemeRecipeGroupRule(listVo);
		return JSONObject.parseObject(schemeMrRuleJson);
	}
}
