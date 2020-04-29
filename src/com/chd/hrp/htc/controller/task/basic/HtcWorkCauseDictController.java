package com.chd.hrp.htc.controller.task.basic;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.htc.entity.task.basic.HtcWorkCauseDict;
import com.chd.hrp.htc.service.task.basic.HtcWorkCauseDictService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcWorkCauseDictController extends BaseController {
	private static Logger logger = Logger
			.getLogger(HtcWorkCauseDictController.class);

	@Resource(name = "htcWorkCauseDictService")
	private final HtcWorkCauseDictService htcWorkCauseDictService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/workcausedict/htcWorkCauseDictMainPage", method = RequestMethod.GET)
	public String htcWorkCauseDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/workcausedict/htcWorkCauseDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/workcausedict/htcWorkCauseDictAddPage", method = RequestMethod.GET)
	public String htcWorkCauseDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/workcausedict/htcWorkCauseDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/workcausedict/addHtcWorkCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcWorkCauseDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String workCauseDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("work_cause_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("work_cause_name").toString()));
		mapVo.put("fun_value", 0);

		try {
			workCauseDictJson = htcWorkCauseDictService.addHtcWorkCauseDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workCauseDictJson = e.getMessage();
		}

		return JSONObject.parseObject(workCauseDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/workcausedict/queryHtcWorkCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcWorkCauseDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String workCauseDict = htcWorkCauseDictService.queryHtcWorkCauseDict(getPage(mapVo));

		return JSONObject.parseObject(workCauseDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/workcausedict/deleteHtcWorkCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcWorkCauseDict(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {

		String workCauseDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("work_cause_code", ids[3]);
			listVo.add(mapVo);
		}
		
		try {
			workCauseDictJson = htcWorkCauseDictService.deleteBatchHtcWorkCauseDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workCauseDictJson = e.getMessage();
		}
		return JSONObject.parseObject(workCauseDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/workcausedict/htcWorkCauseDictUpdatePage", method = RequestMethod.GET)
	public String htcWorkCauseDictUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		HtcWorkCauseDict workCauseDict = htcWorkCauseDictService.queryHtcWorkCauseDictByCode(mapVo);
		mode.addAttribute("group_id", workCauseDict.getGroup_id());
		mode.addAttribute("hos_id", workCauseDict.getHos_id());
		mode.addAttribute("copy_code", workCauseDict.getCopy_code());
		mode.addAttribute("work_cause_code", workCauseDict.getWork_cause_code());
		mode.addAttribute("work_cause_name", workCauseDict.getWork_cause_name());
		mode.addAttribute("work_remark", workCauseDict.getWork_remark());
		mode.addAttribute("fun_value", workCauseDict.getFun_value());

		return "hrp/htc/task/basic/workcausedict/htcWorkCauseDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/workcausedict/updateHtcWorkCauseDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcWorkCauseDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String workCauseDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("work_cause_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("work_cause_name").toString()));
		mapVo.put("fun_value", 0);
		
		try {
			workCauseDictJson = htcWorkCauseDictService.updateHtcWorkCauseDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workCauseDictJson = e.getMessage();
		}

		return JSONObject.parseObject(workCauseDictJson);
	}

}
