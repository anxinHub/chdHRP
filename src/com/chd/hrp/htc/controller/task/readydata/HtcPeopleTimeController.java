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
import com.chd.hrp.htc.entity.task.readydata.HtcPeopleTime;
import com.chd.hrp.htc.service.task.readydata.HtcPeopleTimeService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcPeopleTimeController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcPeopleTimeController.class);
	
	
	@Resource(name = "htcPeopleTimeService")
	private final HtcPeopleTimeService htcPeopleTimeService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/readydata/peopletime/htcPeopleTimeMainPage", method = RequestMethod.GET)
	public String htcPeopleTimeMainPage(Model mode) throws Exception {

		return "hrp/htc/task/readydata/peopletime/htcPeopleTimeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/readydata/peopletime/htcPeopleTimeAddPage", method = RequestMethod.GET)
	public String htcPeopleTimeAddPage(Model mode) throws Exception {
		return "hrp/htc/task/readydata/peopletime/htcPeopleTimeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/readydata/peopletime/addHtcPeopleTime", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHtcPeopleTime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcPeopleTimeJson= "";
		try {
			htcPeopleTimeJson = htcPeopleTimeService.addHtcPeopleTime(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcPeopleTimeJson = e.getMessage();
		}

		return JSONObject.parseObject(htcPeopleTimeJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/readydata/peopletime/queryHtcPeopleTime", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcPeopleTime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcPeopleTimeJson = htcPeopleTimeService.queryHtcPeopleTime(getPage(mapVo));

		return JSONObject.parseObject(htcPeopleTimeJson);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/task/readydata/peopletime/deleteHtcPeopleTime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcPeopleTime(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("plan_code", ids[4]);
			mapVo.put("proj_dept_no", ids[5]);
			mapVo.put("proj_dept_id", ids[6]);
			mapVo.put("people_code", ids[7]);
			listVo.add(mapVo);
		}
		String htcPeopleTimeJson = "";
		try {
			htcPeopleTimeJson = htcPeopleTimeService.deleteBatchHtcPeopleTime(listVo);
		} catch (Exception e) {
			htcPeopleTimeJson = e.getMessage();
		}
	   return JSONObject.parseObject(htcPeopleTimeJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/readydata/peopletime/htcPeopleTimeUpdatePage", method = RequestMethod.GET)
	
	public String htcPeopleTimeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		HtcPeopleTime htcPeopleTime = htcPeopleTimeService.queryHtcPeopleTimeByCode(mapVo);
		mode.addAttribute("group_id", htcPeopleTime.getGroup_id());
		mode.addAttribute("hos_id", htcPeopleTime.getHos_id());
		mode.addAttribute("copy_code", htcPeopleTime.getCopy_code());
		mode.addAttribute("acc_year", htcPeopleTime.getAcc_year());
		mode.addAttribute("plan_code", htcPeopleTime.getPlan_code());
		mode.addAttribute("plan_name", htcPeopleTime.getPlan_name());
		mode.addAttribute("proj_dept_no", htcPeopleTime.getProj_dept_no());
		mode.addAttribute("proj_dept_id", htcPeopleTime.getProj_dept_id());
		mode.addAttribute("proj_dept_code", htcPeopleTime.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcPeopleTime.getProj_dept_name());
		mode.addAttribute("people_code", htcPeopleTime.getPeople_code());
		mode.addAttribute("people_name", htcPeopleTime.getPeople_name());
		mode.addAttribute("time_sum", htcPeopleTime.getTime_sum());
		return "hrp/htc/task/readydata/peopletime/htcPeopleTimeUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/readydata/peopletime/updateHtcPeopleTime", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcPeopleTime(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcPeopleTimeJson = "";
		try {
			htcPeopleTimeJson = htcPeopleTimeService.updateHtcPeopleTime(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcPeopleTimeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcPeopleTimeJson);
	}

}

