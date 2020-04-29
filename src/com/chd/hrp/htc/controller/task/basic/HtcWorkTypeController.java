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
import com.chd.hrp.htc.entity.task.basic.HtcWorkType;
import com.chd.hrp.htc.service.task.basic.HtcWorkTypeService;

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
public class HtcWorkTypeController extends BaseController {
	private static Logger logger = Logger.getLogger(HtcWorkTypeController.class);

	@Resource(name = "htcWorkTypeService")
	private final HtcWorkTypeService htcWorkTypeService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/worktype/htcWorkTypeMainPage", method = RequestMethod.GET)
	public String htcWorkTypeMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/worktype/htcWorkTypeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/worktype/htcWorkTypeAddPage", method = RequestMethod.GET)
	public String htcWorkTypeAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/worktype/htcWorkTypeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/worktype/addHtcWorkType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcWorkType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String workTypeJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("work_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("work_type_name").toString()));
		
		try {
			workTypeJson = htcWorkTypeService.addHtcWorkType(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workTypeJson = e.getMessage();
		}

		return JSONObject.parseObject(workTypeJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/worktype/queryHtcWorkType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcWorkType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String workType = htcWorkTypeService.queryHtcWorkType(getPage(mapVo));

		return JSONObject.parseObject(workType);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/worktype/deleteHtcWorkType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcWorkType(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {
		String workTypeJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("work_type_code", ids[3]);
			listVo.add(mapVo);
		}
		
		try {
			workTypeJson =  htcWorkTypeService.deleteBatchHtcWorkType(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workTypeJson = e.getMessage();
		}
		return JSONObject.parseObject(workTypeJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/worktype/htcWorkTypeUpdatePage", method = RequestMethod.GET)
	public String htcWorkTypeUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		HtcWorkType workType = htcWorkTypeService.queryHtcWorkTypeByCode(mapVo);
		mode.addAttribute("group_id", workType.getGroup_id());
		mode.addAttribute("hos_id", workType.getHos_id());
		mode.addAttribute("copy_code", workType.getCopy_code());
		mode.addAttribute("work_type_name", workType.getWork_type_name());
		mode.addAttribute("work_type_code", workType.getWork_type_code());
		mode.addAttribute("work_type_name", workType.getWork_type_name());
		mode.addAttribute("work_type_remark", workType.getWork_type_remark());
		mode.addAttribute("is_last", workType.getIs_last());
		mode.addAttribute("is_stop", workType.getIs_stop());
		return "hrp/htc/task/basic/worktype/htcWorkTypeUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/worktype/updateHtcWorkType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcWorkType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String workTypeJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("work_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("work_type_name").toString()));
		
		try {
			workTypeJson = htcWorkTypeService.updateHtcWorkType(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			workTypeJson = e.getMessage();
		}

		return JSONObject.parseObject(workTypeJson);
	}
	
	

}
