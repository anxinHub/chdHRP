package com.chd.hrp.htc.controller.task.readydata;

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
import com.chd.hrp.htc.entity.task.basic.HtcWorkCauseDict;
import com.chd.hrp.htc.service.task.basic.HtcWorkCauseDictService;
import com.chd.hrp.htc.service.task.readydata.HtcWorkCauseDataService;

@Controller
public class HtcWorkCauseDataController extends BaseController{

private static Logger logger = Logger.getLogger(HtcWorkCauseDataController.class);
	
	@Resource(name = "htcWorkCauseDataService")
	private final HtcWorkCauseDataService htcWorkCauseDataService = null;
	
	@Resource(name = "htcWorkCauseDictService")
	private final HtcWorkCauseDictService htcWorkCauseDictService = null;
	/*
     * 主页面跳转
     * */
	@RequestMapping(value = "/hrp/htc/task/readydata/workcausedata/htcWorkCauseDataMainPage", method = RequestMethod.GET)
	public String htcWorkCauseDataMainPage(Model mode) throws Exception {
		return "hrp/htc/task/readydata/workcausedata/htcWorkCauseDataMain";
	}
	
	// 查询                                     
	@RequestMapping(value = "/hrp/htc/task/readydata/workcausedata/queryHtcWorkCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcWorkCauseDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String htcWorkCauseDictJson = htcWorkCauseDataService.queryHtcWorkCauseDict(getPage(mapVo));

		return JSONObject.parseObject(htcWorkCauseDictJson);

	}
	
	/*
     * 主页面跳转
     * */
	@RequestMapping(value = "/hrp/htc/task/readydata/workcausedata/htcWorkCauseDataSaveMainPage", method = RequestMethod.GET)
	public String htcWorkCauseDataSaveMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		HtcWorkCauseDict  htcWorkCauseDict = htcWorkCauseDictService.queryHtcWorkCauseDictByCode(mapVo);
		mode.addAttribute("group_id",htcWorkCauseDict.getGroup_id());
		mode.addAttribute("hos_id",htcWorkCauseDict.getHos_id());
		mode.addAttribute("copy_code",htcWorkCauseDict.getCopy_code());
		mode.addAttribute("work_cause_code",htcWorkCauseDict.getWork_cause_code());
		mode.addAttribute("work_cause_name",htcWorkCauseDict.getWork_cause_name());
		return "hrp/htc/task/readydata/workcausedata/htcWorkCauseDataSaveMain";
	}
	
	// 查询                                     
	@RequestMapping(value = "/hrp/htc/task/readydata/workcausedata/queryHtcWorkCauseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcWorkCauseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcWorkCauseDictJson = htcWorkCauseDataService.queryHtcWorkCauseData(getPage(mapVo));
		return JSONObject.parseObject(htcWorkCauseDictJson);

	}
	
	//采集           
	@RequestMapping(value = "/hrp/htc/task/readydata/workcausedata/collectHtcWorkCauseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHtcWorkCauseData(@RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	   String htcWorkCauseDictJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> map = new HashMap<String, Object>();
			String[] ids = id.split("@");
			map.put("group_id", ids[0]);
			map.put("hos_id", ids[1]);
			map.put("copy_code", ids[2]);
			map.put("acc_year", ids[3]);
			map.put("plan_code", ids[4]);
			map.put("proj_dept_no", ids[5]);
			map.put("proj_dept_id", ids[6]);
			map.put("charge_item_id", ids[7]);
			map.put("work_code", ids[8]);
			map.put("work_cause_code", ids[9]);
			map.put("work_cause_data", ids[10]);
			listVo.add(map);
		}
		try {
			htcWorkCauseDictJson = htcWorkCauseDataService.collectHtcWorkCauseData(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcWorkCauseDictJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcWorkCauseDictJson);	

	}
}
