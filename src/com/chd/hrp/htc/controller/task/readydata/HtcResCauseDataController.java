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
import com.chd.hrp.htc.entity.task.basic.HtcResCauseDict;
import com.chd.hrp.htc.service.task.basic.HtcResCauseDictService;
import com.chd.hrp.htc.service.task.readydata.HtcResCauseDataService;

@Controller
public class HtcResCauseDataController extends BaseController{
	
    private static Logger logger = Logger.getLogger(HtcResCauseDataController.class);
	
	@Resource(name = "htcResCauseDataService")
	private final HtcResCauseDataService htcResCauseDataService = null;
	
	@Resource(name = "htcResCauseDictService")
	private final HtcResCauseDictService htcResCauseDictService = null;

	/*
     * 主页面跳转
     * */
	@RequestMapping(value = "/hrp/htc/task/readydata/rescausedata/htcResCauseDataMainPage", method = RequestMethod.GET)
	public String htcResCauseDataMainPage(Model mode) throws Exception {
		return "hrp/htc/task/readydata/rescausedata/htcResCauseDataMain";
	}
	
	// 查询                                     
	@RequestMapping(value = "/hrp/htc/task/readydata/rescausedata/queryHtcResCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcResCauseDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String htcResCauseDictJson = htcResCauseDataService.queryHtcResCauseDict(getPage(mapVo));

		return JSONObject.parseObject(htcResCauseDictJson);

	}
	
	/*
     * 主页面跳转
     * */
	@RequestMapping(value = "/hrp/htc/task/readydata/rescausedata/htcResCauseDataSaveMainPage", method = RequestMethod.GET)
	public String htcResCauseDataSaveMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		HtcResCauseDict  htcResCauseDict = htcResCauseDictService.queryHtcResCauseDictByCode(mapVo);
		mode.addAttribute("group_id",htcResCauseDict.getGroup_id());
		mode.addAttribute("hos_id",htcResCauseDict.getHos_id());
		mode.addAttribute("copy_code",htcResCauseDict.getCopy_code());
		mode.addAttribute("res_cause_code",htcResCauseDict.getRes_cause_code());
		mode.addAttribute("res_cause_name",htcResCauseDict.getRes_cause_name());
		mode.addAttribute("fun_value",htcResCauseDict.getFun_value());
		return "hrp/htc/task/readydata/rescausedata/htcResCauseDataSaveMain";
	}
	
	// 查询                                     
	@RequestMapping(value = "/hrp/htc/task/readydata/rescausedata/queryHtcResCauseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcResCauseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String htcResCauseDictJson = htcResCauseDataService.queryHtcResCauseData(getPage(mapVo));

		return JSONObject.parseObject(htcResCauseDictJson);

	}
	
	//采集           
	@RequestMapping(value = "/hrp/htc/task/readydata/rescausedata/collectHtcResCauseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHtcResCauseData(@RequestParam(value = "ParamVo") String paramVo,@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	   String htcResCauseDataJson = "";
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
			map.put("work_code", ids[7]);
			map.put("res_cause_code", ids[8]);
			map.put("res_cause_data", ids[9]);
			listVo.add(map);
		}
		try {
			htcResCauseDataJson = htcResCauseDataService.collectHtcResCauseData(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcResCauseDataJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcResCauseDataJson);	

	}
}
