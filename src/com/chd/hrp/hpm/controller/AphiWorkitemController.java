package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiWorkitem;
import com.chd.hrp.hpm.service.AphiWorkitemService;

/**
 * author:alfred
 */

@Controller
public class AphiWorkitemController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiWorkitemController.class);

	@Resource(name = "aphiWorkitemService")
	private final AphiWorkitemService aphiWorkitemService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/hpmWorkitemMainPage", method = RequestMethod.GET)
	public String workitemMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		return "hrp/hpm/hpmworkitem/hpmWorkitemMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/hpmWorkitemAddPage", method = RequestMethod.GET)
	public String workitemAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmworkitem/hpmWorkitemAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/addHpmWorkitem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addWorkitem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String workitemJson = aphiWorkitemService.addWorkitem(mapVo);

		return JSONObject.parseObject(workitemJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/queryHpmWorkitem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryWorkitem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String workitem = aphiWorkitemService.queryWorkitem(getPage(mapVo));

		return JSONObject.parseObject(workitem);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/deleteHpmWorkitem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteWorkitem(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		Map map = new HashMap();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		String workitemJson = aphiWorkitemService.deleteWorkitem(map, checkIds);

		return JSONObject.parseObject(workitemJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/hpmWorkitemUpdatePage", method = RequestMethod.GET)
	public String workitemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AphiWorkitem workitem = aphiWorkitemService.queryWorkitemByCode(mapVo);

		mode.addAttribute("work_item_code", workitem.getWork_item_code());

		mode.addAttribute("work_item_name", workitem.getWork_item_name());

		mode.addAttribute("is_stop", workitem.getIs_stop());

		return "hrp/hpm/hpmworkitem/hpmWorkitemUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/updateHpmWorkitem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateWorkitem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String workitemJson = aphiWorkitemService.updateWorkitem(mapVo);

		return JSONObject.parseObject(workitemJson);
	}

	/** 上传处理方法 */
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/readWorkItemFiles", method = RequestMethod.POST)
	public String readWorkItemFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		List<AphiWorkitem> errorList = new ArrayList<AphiWorkitem>();

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		try {

			for (int i = 1; i < list.size(); i++) {

				AphiWorkitem workitem = new AphiWorkitem();

				StringBuffer err_sb = new StringBuffer();

				String temp[] = list.get(i);

				if (StringUtils.isNotEmpty(temp[0])) {

					workitem.setWork_item_code(temp[0]);

					mapVo.put("work_item_code", temp[0]);

				} else {

					err_sb.append("工作量指标编码不能为空!");

				}

				if (StringUtils.isNotEmpty(temp[1])) {

					workitem.setWork_item_name(temp[1]);

					mapVo.put("work_item_name", temp[1]);

				} else {

					err_sb.append("工作量指标名称不能为空!");

				}

				if (StringUtils.isNotEmpty(temp[3])) {

					workitem.setIs_stop(Integer.parseInt(temp[3].toString()));

					mapVo.put("is_stop", temp[3]);

				} else {

					err_sb.append("是否为空不能为空!");

				}

				workitem.setData_source(temp[2]);

				mapVo.put("data_source", temp[2]);

				AphiWorkitem Workitem = aphiWorkitemService.queryWorkitemByCode(mapVo);

				if (Workitem != null) {

					err_sb.append("数据编码已经存在！");
				}

				if (err_sb.toString().length() > 0) {

					workitem.setError_type(err_sb.toString());

					errorList.add(workitem);

				} else {

					aphiWorkitemService.addWorkitem(mapVo);

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			AphiWorkitem Workitem = new AphiWorkitem();

			Workitem.setError_type("系统导入出错!");

			errorList.add(Workitem);

			response.getWriter().print(JsonListBeanUtil.listToJson(errorList, errorList.size()));

			return null;

		}

		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(errorList, errorList.size()));

		return "/hrp/hpm/hpmworkitem/hpmWorkItemImportMessage";
	}

//	@RequestMapping(value = "/hrp/hpm/hpmworkitem/addBatchWorkitem", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchWorkitem(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		List<AphiWorkitem> errorList = new ArrayList<AphiWorkitem>();
//
//		if (mapVo.get("group_id") == null) {
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//		if (mapVo.get("hos_id") == null) {
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//		if (mapVo.get("copy_code") == null) {
//			mapVo.put("copy_code", SessionManager.getCopyCode());
//		}
//
//		JSONArray json = JSONArray.parseArray(paramVo);
//
//		Iterator it = json.iterator();
//
//		try {
//
//			while (it.hasNext()) {
//
//				AphiWorkitem workitem = new AphiWorkitem();
//
//				StringBuffer err_sb = new StringBuffer();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				mapVo.put("work_item_code", jsonObj.get("work_item_code"));
//
//				mapVo.put("work_item_name", jsonObj.get("work_item_name"));
//
//				mapVo.put("data_source", jsonObj.get("data_source"));
//
//				mapVo.put("is_stop", jsonObj.get("is_stop"));
//
//				AphiWorkitem Workitem = aphiWorkitemService.queryWorkitemByCode(mapVo);
//
//				if (Workitem != null) {
//
//					err_sb.append("数据编码已经存在！");
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					workitem.setWork_item_code(jsonObj.get("work_item_code").toString());
//
//					workitem.setWork_item_name(jsonObj.get("work_item_name").toString());
//
//					workitem.setData_source(jsonObj.get("data_source").toString());
//
//					workitem.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
//
//					workitem.setError_type(err_sb.toString());
//
//					errorList.add(workitem);
//
//				} else {
//
//					aphiWorkitemService.addWorkitem(mapVo);
//
//				}
//
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
//		}
//
//		if (errorList.size() > 0) {
//
//			return JSONObject.parseObject(JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//		} else {
//
//			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
//
//		}
//
//	}
	
	//导入
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/hpmWorkitemImportPage", method = RequestMethod.GET)
	public String workitemImportPage(Model mode) throws Exception {
		return "hrp/hpm/hpmworkitem/hpmWorkItemImport";
	}
	
	//导入跳转
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/hpmWorkitemImport", method = RequestMethod.POST)
	@ResponseBody
	public String hpmWorkitemImport(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		try {
			String impJson = aphiWorkitemService.hpmWorkitemImport(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmworkitem/downTemplateHpmWorkitem", method = RequestMethod.GET)
	public String downTemplateWorkitem(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "工作量指标维护.xlsx");
		return null;
	}

}
