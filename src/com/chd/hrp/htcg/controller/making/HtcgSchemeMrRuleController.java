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
import com.chd.hrp.htcg.entity.making.HtcgSchemeMrRule;
import com.chd.hrp.htcg.service.making.HtcgSchemeMrRuleService;

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
public class HtcgSchemeMrRuleController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemeMrRuleController.class);

	@Resource(name = "htcgSchemeMrRuleService")
	private final HtcgSchemeMrRuleService htcgSchemeMrRuleService = null;



	// 维护页面跳转                                                                                               
	@RequestMapping(value = "/hrp/htcg/making/schememrrule/htcgSchemeMrRuleMainPage", method = RequestMethod.GET)
	public String htcgSchemeMrRuleMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/schememrrule/htcgSchemeMrRuleMain";

	}


	// 生成
	@RequestMapping(value = "/hrp/htcg/making/schememrrule/initHtcgSchemeMrRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgSchemeMrRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String schemePathConfJson = "";
		try {
			schemePathConfJson = htcgSchemeMrRuleService.initHtcgSchemeMrRule(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemePathConfJson = e.getMessage();
		}
		return JSONObject.parseObject(schemePathConfJson);

	}
	

	// 查询
	@RequestMapping(value = "/hrp/htcg/making/schememrrule/queryHtcgSchemeMrRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemeMrRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String schemeMrRule = htcgSchemeMrRuleService.queryHtcgSchemeMrRule(getPage(mapVo));
		return JSONObject.parseObject(schemeMrRule);

	}
	   // 保存
		@RequestMapping(value = "/hrp/htcg/making/schememrrule/saveHtcgSchemeMrRule", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> saveHtcgSchemeMrRule(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
	            String[] ids=id.split("@");
	            mapVo.put("group_id", ids[0]);
	    		mapVo.put("hos_id", ids[1]);
	    		mapVo.put("copy_code", ids[2]);
				mapVo.put("scheme_code", ids[3]);// 实际实体类变量
				mapVo.put("drgs_code", ids[4]);// 实际实体类变量
				mapVo.put("mr_rule_code", ids[5]);// 实际实体类变量
				mapVo.put("mr_rule_count", ids[6]);// 实际实体类变量
				listVo.add(mapVo);
			}
			String schemeMrRuleJson = "";
          try {
        	  
        	    schemeMrRuleJson= htcgSchemeMrRuleService.updateBatchHtcgSchemeMrRule(listVo);
        	    
			  } catch (Exception e) {
				// TODO: handle exception
				  schemeMrRuleJson = e.getMessage();
			}
			return JSONObject.parseObject(schemeMrRuleJson);
		}
	// 删除
	@RequestMapping(value = "/hrp/htcg/making/schememrrule/deleteHtcgSchemeMrRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemeMrRule(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
		
		String schemeMrRuleJson = "";
		
		try {
			schemeMrRuleJson = htcgSchemeMrRuleService.deleteBatchHtcgSchemeMrRule(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeMrRuleJson = e.getMessage();
		}

		 return JSONObject.parseObject(schemeMrRuleJson);

	}

	
	
}
