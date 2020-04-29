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
import com.chd.hrp.htc.entity.task.basic.HtcWorkDict;
import com.chd.hrp.htc.service.task.basic.HtcWorkDictService;

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
public class HtcWorkDictController extends BaseController {
	private static Logger logger = Logger.getLogger(HtcWorkDictController.class);

	@Resource(name = "htcWorkDictService")
	private final HtcWorkDictService htcWorkDictService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/workdict/htcWorkDictMainPage", method = RequestMethod.GET)
	public String htcWorkDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/workdict/htcWorkDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/workdict/htcWorkDictAddPage", method = RequestMethod.GET)
	public String htcWorkDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/workdict/htcWorkDictAdd";

	}
	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/workdict/addHtcWorkDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcWorkDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String workDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("work_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("work_name").toString()));
		
		try {
			workDictJson = htcWorkDictService.addHtcWorkDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workDictJson = e.getMessage();
		}

		return JSONObject.parseObject(workDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/workdict/queryHtcWorkDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcWorkDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String workDict = htcWorkDictService.queryHtcWorkDict(getPage(mapVo));

		return JSONObject.parseObject(workDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/workdict/deleteHtcWorkDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcWorkDict(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {

		String workDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("work_code", ids[3]);
			listVo.add(mapVo);
		}
		
		try {
			workDictJson = htcWorkDictService.deleteBatchHtcWorkDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workDictJson = e.getMessage();
		}
		return JSONObject.parseObject(workDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/workdict/htcWorkDictUpdatePage", method = RequestMethod.GET)
	public String htcWorkDictUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		HtcWorkDict htcWorkDict = htcWorkDictService.queryHtcWorkDictByCode(mapVo);
		mode.addAttribute("group_id", htcWorkDict.getGroup_id());
		mode.addAttribute("hos_id", htcWorkDict.getHos_id());
		mode.addAttribute("copy_code", htcWorkDict.getCopy_code());
		mode.addAttribute("work_code", htcWorkDict.getWork_code());
		mode.addAttribute("work_name", htcWorkDict.getWork_name());
		mode.addAttribute("work_type_code", htcWorkDict.getWork_type_code());
		mode.addAttribute("work_type_name", htcWorkDict.getWork_type_name());
		mode.addAttribute("work_remark", htcWorkDict.getWork_remark());
		mode.addAttribute("is_people", htcWorkDict.getIs_people());
		mode.addAttribute("is_material", htcWorkDict.getIs_material());
		mode.addAttribute("is_asset", htcWorkDict.getIs_asset());
		return "hrp/htc/task/basic/workdict/htcWorkDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/workdict/updateHtcWorkDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcWorkDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String workDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("work_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("work_name").toString()));
		
		try {
			workDictJson = htcWorkDictService.updateHtcWorkDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workDictJson = e.getMessage();
		}

		return JSONObject.parseObject(workDictJson);
	}
}
