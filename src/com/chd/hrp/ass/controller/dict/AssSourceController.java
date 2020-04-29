package com.chd.hrp.ass.controller.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.sys.controller.SourceController;
import com.chd.hrp.sys.entity.Source;
import com.chd.hrp.sys.serviceImpl.SourceServiceImpl;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssSourceController extends BaseController {
	private static Logger logger = Logger.getLogger(SourceController.class);

	@Resource(name = "sourceService")
	private final SourceServiceImpl sourceService = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/ass/asssource/sourceMainPage", method = RequestMethod.GET)
	public String sourceMainPage(Model mode) throws Exception {
		return "hrp/ass/asssource/sourceMain";
	}

	// 添加页面
	@RequestMapping(value = "/hrp/ass/asssource/sourceAddPage", method = RequestMethod.GET)
	public String sourceAddPage(Model mode) throws Exception {
		return "hrp/ass/asssource/sourceAdd";
	}

	// 保存
	@RequestMapping(value = "/hrp/ass/asssource/addAssSource", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> addAssSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		/*
		 * String retErrot="";
		 * 
		 * //根据编号获取对象 Source source = sourceService.querySourceByCode(mapVo); if
		 * (source !=null) {
		 * 
		 * retErrot = "{\"error\":\"编码：【" + source.getSource_code()+
		 * "】重复,请修改后添加！\",\"state\":\"false\"}"; return
		 * JSONObject.parseObject(retErrot);
		 * 
		 * } //根据名称获取对象 Source sourceName = sourceService.queryByName(mapVo); if
		 * (sourceName!=null) {
		 * 
		 * retErrot = "{\"error\":\"名称：【" + sourceName.getSource_name()+
		 * "】重复,请修改后添加！\",\"state\":\"false\"}"; return
		 * JSONObject.parseObject(retErrot);
		 * 
		 * }
		 */

		String sourceJson = sourceService.addSource(mapVo);

		return JSONObject.parseObject(sourceJson);
	}

	// 删除
	@RequestMapping(value = "/hrp/ass/asssource/deleteAssSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSource(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		String str = "";
		boolean falg = true;
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("source_id", id.split("@")[2]);
			mapVo.put("source_code", id.split("@")[3]);

			str = str + assBaseService.isExistsDataByTable("HOS_SOURCE", id.split("@")[2]) == null ? ""
					: assBaseService.isExistsDataByTable("HOS_SOURCE", id.split("@")[2]);
			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}

			listVo.add(mapVo);
		}
		if (!falg) {
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的资金来源被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}");
		}

		String sourceJson = sourceService.deleteBatchSource(listVo);
		return JSONObject.parseObject(sourceJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/ass/asssource/sourceUpdatePage", method = RequestMethod.GET)

	public String sourceUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Source source = new Source();
		source = sourceService.querySourceByCode(mapVo);
		mode.addAttribute("group_id", source.getGroup_id());
		mode.addAttribute("hos_id", source.getHos_id());
		mode.addAttribute("source_id", source.getSource_id());
		mode.addAttribute("source_code", source.getSource_code());
		mode.addAttribute("source_name", source.getSource_name());
		mode.addAttribute("nature_code", source.getNature_code());
		mode.addAttribute("nature_name", source.getNature_name());
		mode.addAttribute("spell_code", source.getSpell_code());
		mode.addAttribute("wbx_code", source.getWbx_code());
		mode.addAttribute("is_stop", source.getIs_stop());
		mode.addAttribute("note", source.getNote());
		mode.addAttribute("source_attr", source.getSource_attr());

		return "hrp/ass/asssource/sourceUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/ass/asssource/updateAssSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		String sourceJson = sourceService.updateSource(mapVo);

		return JSONObject.parseObject(sourceJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/ass/asssource/queryAssSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		String source = sourceService.querySource(getPage(mapVo));
		return JSONObject.parseObject(source);
	}

}