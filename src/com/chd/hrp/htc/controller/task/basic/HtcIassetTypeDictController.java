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
import com.chd.hrp.htc.entity.task.basic.HtcIassetTypeDict;
import com.chd.hrp.htc.service.task.basic.HtcIassetTypeDictService;

/**
 * 2015-3-18 author:alfred
 */
@Controller
public class HtcIassetTypeDictController extends BaseController {

	private static Logger logger = Logger
			.getLogger(HtcIassetTypeDictController.class);

	@Resource(name = "htcIassetTypeDictService")
	private final HtcIassetTypeDictService htcIassetTypeDictService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/iassettypedict/htcIassetTypeDictMainPage", method = RequestMethod.GET)
	public String htcIassetTypeDictMainPage(Model mode) throws Exception {
		return "hrp/htc/task/basic/iassettypedict/htcIassetTypeDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/iassettypedict/htcIassetTypeDictAddPage", method = RequestMethod.GET)
	public String htcIassetTypeDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/iassettypedict/htcIassetTypeDictAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/iassettypedict/addHtcIassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIassetTypeDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String iassetTypeDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_type_name").toString()));

		try {
			iassetTypeDictJson = htcIassetTypeDictService.addHtcIassetTypeDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			iassetTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(iassetTypeDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/iassettypedict/queryHtcIassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIassetTypeDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String iassetTypeDict = htcIassetTypeDictService.queryHtcIassetTypeDict(getPage(mapVo));

		return JSONObject.parseObject(iassetTypeDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/iassettypedict/deleteHtcIassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcIassetTypeDict(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {
		String iassetTypeDictJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("asset_type_code", ids[3]);
			listVo.add(mapVo);
		}
		try {
			iassetTypeDictJson = htcIassetTypeDictService.deleteBatchHtcIassetTypeDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			iassetTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(iassetTypeDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/iassettypedict/htcIassetTypeDictUpdatePage", method = RequestMethod.GET)
	public String htcIassetTypeDictUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		HtcIassetTypeDict iassetTypeDict = htcIassetTypeDictService.queryHtcIassetTypeDictByCode(mapVo);
		mode.addAttribute("group_id",iassetTypeDict.getGroup_id());
		mode.addAttribute("hos_id",iassetTypeDict.getHos_id());
		mode.addAttribute("copy_code",iassetTypeDict.getCopy_code());
		mode.addAttribute("asset_type_code",iassetTypeDict.getAsset_type_code());
		mode.addAttribute("asset_type_name",iassetTypeDict.getAsset_type_name());
		mode.addAttribute("supper_code", iassetTypeDict.getSupper_code());
		mode.addAttribute("is_last", iassetTypeDict.getIs_last());
		mode.addAttribute("is_stop", iassetTypeDict.getIs_stop());
		return "hrp/htc/task/basic/iassettypedict/htcIassetTypeDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/iassettypedict/updateHtcIassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcIassetTypeDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String iassetTypeDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_type_name").toString()));
		try {
			iassetTypeDictJson = htcIassetTypeDictService.updateHtcIassetTypeDict(mapVo);
		} catch (Exception e) {
			iassetTypeDictJson = e.getMessage();
		}
		return JSONObject.parseObject(iassetTypeDictJson);
	}


}
