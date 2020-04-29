package com.chd.hrp.htc.controller.task.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.hrp.htc.entity.task.basic.HtcIassetDict;
import com.chd.hrp.htc.service.task.basic.HtcIassetDictService;

/**
 * 2015-3-18 author:alfred
 */
@Controller
public class HtcIassetDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcIassetDictController.class);

	@Resource(name = "htcIassetDictService")
	private final HtcIassetDictService htcIassetDictService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/iassetdict/htcIassetDictMainPage", method = RequestMethod.GET)
	public String htcIassetDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/iassetdict/htcIassetDictMain";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/iassetdict/htcIassetDictAddPage", method = RequestMethod.GET)
	public String htcIassetDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/iassetdict/htcIassetDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/iassetdict/addHtcIassetDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIassetDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String iassetDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_name").toString()));
		
		try {
			iassetDictJson = htcIassetDictService.addHtcIassetDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			iassetDictJson = e.getMessage();
		}

		return JSONObject.parseObject(iassetDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/iassetdict/queryHtcIassetDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIassetDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String iassetDict = htcIassetDictService.queryHtcIassetDict(getPage(mapVo));

		return JSONObject.parseObject(iassetDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/iassetdict/deleteHtcIassetDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIassetDict(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mod) throws Exception {

		String iassetDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("asset_code", ids[3]);
			listVo.add(mapVo);
		}

		try {
			iassetDictJson = htcIassetDictService.deleteBatchHtcIassetDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			iassetDictJson = e.getMessage();
		}

		return JSONObject.parseObject(iassetDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/iassetdict/htcIassetDictUpdatePage", method = RequestMethod.GET)
	public String htcIassetDictUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		HtcIassetDict iassetDict = htcIassetDictService.queryHtcIassetDictByCode(mapVo);

		mode.addAttribute("group_id", iassetDict.getGroup_id());
		mode.addAttribute("hos_id", iassetDict.getHos_id());
		mode.addAttribute("copy_code", iassetDict.getCopy_code());
		mode.addAttribute("asset_code", iassetDict.getAsset_code());
		mode.addAttribute("asset_name", iassetDict.getAsset_name());
		mode.addAttribute("asset_type_code", iassetDict.getAsset_type_code());
		mode.addAttribute("asset_type_name", iassetDict.getAsset_type_name());
		mode.addAttribute("prim_value", iassetDict.getPrim_value());
		mode.addAttribute("start_date", iassetDict.getStart_date());
		mode.addAttribute("end_date", iassetDict.getEnd_date());
		mode.addAttribute("dep_year", iassetDict.getDep_year());

		return "hrp/htc/task/basic/iassetdict/htcIassetDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/iassetdict/updateHtcIassetDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcIassetDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String iassetDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_name").toString()));

		try {
			iassetDictJson = htcIassetDictService.updateHtcIassetDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			iassetDictJson = e.getMessage();
		}

		return JSONObject.parseObject(iassetDictJson);
	}


}
