package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.HashMap;
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
import com.chd.hrp.hpm.entity.AphiWorkitemConf;
import com.chd.hrp.hpm.service.AphiWorkitemConfService;
import com.chd.hrp.hpm.service.AphiWorkitemService;

/**
 * author:alfred
 */

@Controller
public class AphiWorkitemConfController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiWorkitemConfController.class);

	@Resource(name = "aphiWorkitemConfService")
	private final AphiWorkitemConfService aphiWorkitemConfService = null;

	@Resource(name = "aphiWorkitemService")
	private final AphiWorkitemService aphiWorkitemService = null;

	/*@Resource(name = "aphiDeptService")
	private final AphiDeptService aphiDeptService = null;*/

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/hpmWorkitemConfMainPage", method = RequestMethod.GET)
	public String workitemConfMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmworkitemconf/hpmWorkitemConfMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/hpmWorkitemConfAddPage", method = RequestMethod.GET)
	public String workitemConfAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmworkitemconf/hpmWorkitemConfAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/addHpmWorkitemConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addWorkitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String workitemConfJson = aphiWorkitemConfService.addWorkitemConf(mapVo);

		return JSONObject.parseObject(workitemConfJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/queryHpmWorkitemConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWorkitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String workitemConf = aphiWorkitemConfService.queryWorkitemConf(getPage(mapVo));

		return JSONObject.parseObject(workitemConf);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/deleteHpmWorkitemConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteWorkitemConf(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		Map<String,Object> map = new HashMap<String,Object>();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		String workitemConfJson = aphiWorkitemConfService.deleteWorkitemConf(map, checkIds);

		return JSONObject.parseObject(workitemConfJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/hpmWorkitemConfUpdatePage", method = RequestMethod.GET)
	public String workitemConfUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		AphiWorkitemConf workitemConf = aphiWorkitemConfService.queryWorkitemConfByCode(mapVo);

		mode.addAttribute("dept_id", workitemConf.getDept_id());
		mode.addAttribute("dept_no", workitemConf.getDept_no());

		mode.addAttribute("work_item_code", workitemConf.getWork_item_code());

		mode.addAttribute("is_acc", workitemConf.getIs_acc());

		mode.addAttribute("work_standard", workitemConf.getWork_standard());

		mode.addAttribute("dept_name", workitemConf.getDept_name());

		mode.addAttribute("work_item_name", workitemConf.getWork_item_name());

		return "hrp/hpm/hpmworkitemconf/hpmWorkitemConfUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/updateHpmWorkitemConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWorkitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String workitemConfJson = aphiWorkitemConfService.updateWorkitemConf(mapVo);

		return JSONObject.parseObject(workitemConfJson);
	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/createHpmWorkitemConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createWorkitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemConfJson = aphiWorkitemConfService.createWorkitemConf(mapVo);

		return JSONObject.parseObject(incomeitemConfJson);

	}

	// 复制页面
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/hpmWorkitemConfCopyPage", method = RequestMethod.GET)
	public String workitemConfCopyPage(Model mode) throws Exception {

		return "hrp/hpm/hpmworkitemconf/hpmWorkitemConfCopy";

	}

	// 复制页面保存
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/copyHpmWorkitemConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyWorkitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemConfJson = aphiWorkitemConfService.copyWorkitemConf(mapVo);

		return JSONObject.parseObject(incomeitemConfJson);

	}

	//导入
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/hpmWorkitemConfImportPage", method = RequestMethod.GET)
	public String workitemConfImportPage(Model mode) throws Exception {
		return "hrp/hpm/hpmworkitemconf/hpmWorkItemConfImport";
	}
	
	//导入跳转
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/importHpmWorkitemConf", method = RequestMethod.POST)
	@ResponseBody
	public String importHpmWorkitemConf(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			String impJson = aphiWorkitemConfService.importHpmWorkitemConf(mapVo);
			return impJson;
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmworkitemconf/downTemplateHpmWorkitemConf", method = RequestMethod.GET)
	public String downTemplateWorkitemConf(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "工作量指标配置.xlsx");
		return null;
	}

}
