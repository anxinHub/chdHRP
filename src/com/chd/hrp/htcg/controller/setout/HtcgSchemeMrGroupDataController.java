package com.chd.hrp.htcg.controller.setout;
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
import com.chd.hrp.htcg.service.setout.HtcgSchemeMrGroupDataService;

@Controller
public class HtcgSchemeMrGroupDataController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemeMrGroupDataController.class);

	@Resource(name = "htcgSchemeMrGroupDataService")
	private final HtcgSchemeMrGroupDataService htcgSchemeMrGroupDataService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/setout/schemeMrGroupData/htcgSchemeMrGroupDataMainPage", method = RequestMethod.GET)
	public String schemeMrGroupDataMainPage(Model mode) throws Exception {

		return "hrp/htcg/setout/schemeMrGroupData/htcgSchemeMrGroupDataMain";

	}


	// 入组
	@RequestMapping(value = "/hrp/htcg/setout/schemeMrGroupData/initHtcgSchemeMrGroupData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initSchemeMrGroupData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String drugAdviceJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			drugAdviceJson = htcgSchemeMrGroupDataService.initHtcgSchemeMrGroupData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(drugAdviceJson);
		 
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/setout/schemeMrGroupData/queryHtcgSchemeMrGroupData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySchemeMrGroupData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drugAdvice = htcgSchemeMrGroupDataService.queryHtcgSchemeMrGroupData(getPage(mapVo));

		return JSONObject.parseObject(drugAdvice);

	}

	// 删除
	@RequestMapping(value = "/hrp/htcg/setout/schemeMrGroupData/deleteHtcgSchemeMrGroupData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSchemeMrGroupData(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("period_type_code", ids[3]);
			mapVo.put("acc_year", ids[4]);
			mapVo.put("acc_month", ids[5]);
			mapVo.put("scheme_code", ids[6]);
			mapVo.put("drgs_code", ids[7]);
			mapVo.put("mr_no", ids[8]);
			mapVo.put("in_hos_no", ids[9]);
			listVo.add(mapVo);
		}
		
		String drugAdviceJson =null;
		
		try {
			drugAdviceJson = htcgSchemeMrGroupDataService.deleteBatchHtcgSchemeMrGroupData(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		return JSONObject.parseObject(drugAdviceJson);

	}
}
