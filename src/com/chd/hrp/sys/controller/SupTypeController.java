/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.sys.controller;

import java.io.IOException;
import java.util.*;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.sys.entity.SupType;
import com.chd.hrp.sys.serviceImpl.SupTypeServiceImpl;
import com.sun.org.apache.bcel.internal.generic.RETURN;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class SupTypeController extends BaseController {
	private static Logger logger = Logger.getLogger(SupTypeController.class);

	@Resource(name = "supTypeService")
	private final SupTypeServiceImpl supTypeService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/suptype/supTypeMainPage", method = RequestMethod.GET)
	public String supTypeMainPage(Model mode) throws Exception {

		return "hrp/sys/suptype/supTypeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/suptype/supTypeAddPage", method = RequestMethod.GET)
	public String supTypeAddPage(Model mode) throws Exception {

		return "hrp/sys/suptype/supTypeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/suptype/addSupType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSupType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String supTypeJson = supTypeService.addSupType(mapVo);

		return JSONObject.parseObject(supTypeJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/sys/suptype/querySupType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String supType = supTypeService.querySupType(getPage(mapVo));

		return JSONObject.parseObject(supType);

	}

	// 删除
	@RequestMapping(value = "/hrp/sys/suptype/deleteSupType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSupType(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("type_code", id.split("@")[2]);
			listVo.add(mapVo);
		}
		String supTypeJson = supTypeService.deleteBatchSupType(listVo);
		return JSONObject.parseObject(supTypeJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/suptype/supTypeUpdatePage", method = RequestMethod.GET)
	public String supTypeUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		SupType supType = new SupType();
		supType = supTypeService.querySupTypeByCode(mapVo);
		mode.addAttribute("group_id", supType.getGroup_id());
		mode.addAttribute("hos_id", supType.getHos_id());
		mode.addAttribute("type_code", supType.getType_code());
		mode.addAttribute("type_name", supType.getType_name());
		mode.addAttribute("spell_code", supType.getSpell_code());
		mode.addAttribute("wbx_code", supType.getWbx_code());
		mode.addAttribute("is_stop", supType.getIs_stop());
		mode.addAttribute("note", supType.getNote());

		return "hrp/sys/suptype/supTypeUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/sys/suptype/updateSupType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSupType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String supTypeJson = supTypeService.updateSupType(mapVo);

		return JSONObject.parseObject(supTypeJson);
	}

	// 导入
	@RequestMapping(value = "/hrp/sys/suptype/importSupType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importSupType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String supTypeJson = supTypeService.importSupType(mapVo);

		return JSONObject.parseObject(supTypeJson);
	}

	@RequestMapping(value = "/hrp/sys/suptype/supTypeImportPage", method = RequestMethod.GET)
	public String costChargeKindArrtImportPage(Model mode) throws Exception {

		return "hrp/sys/suptype/supTypeImport";

	}

	@RequestMapping(value = "hrp/sys/suptype/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sys\\医院信息", "供应商类别.xls");

		return null;
	}

	/**
	 * 供应商类别<BR>
	 * 导入
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "/hrp/sys/suptype/readSupTypeFiles", method = RequestMethod.POST)
	public String readSupTypeFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws Exception {

		List<SupType> list_err = new ArrayList<SupType>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				SupType supType = new SupType();

				String temp[] = list.get(i);// 行

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(temp[0])) {

					mapVo.put("type_code", temp[0]);

					supType.setType_code(temp[0]);

				} else {

					err_sb.append("供应商分类编码为空");
				}

				if (StringTool.isNotBlank(temp[1])) {

					mapVo.put("type_name", temp[1]);

					supType.setType_name(temp[1]);

				} else {

					err_sb.append("供应商分类名称为空");
				}

				if (StringTool.isNotBlank(temp[2])) {

					mapVo.put("is_stop", temp[2]);

					supType.setIs_stop(Integer.parseInt(temp[2].toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (temp.length -1 >= 3) {

					if (StringTool.isNotBlank(temp[3])) {

						mapVo.put("note", temp[3]);

						supType.setNote(temp[3]);

					}

				} else {

					mapVo.put("note", "");

					supType.setNote("");
				}

				SupType eSupType = supTypeService.querySupTypeByCode(mapVo);

				if (eSupType != null) {

					err_sb.append("供应商分类编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					supType.setError_type(err_sb.toString());

					list_err.add(supType);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo
							.get("type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get(
							"type_name").toString()));

					supTypeService.addSupType(mapVo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			SupType data_exc = new SupType();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	@RequestMapping(value = "/hrp/sys/suptype/addImportSupTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImportSupTypeDict(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<SupType> list_err = new ArrayList<SupType>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				StringBuffer err_sb = new StringBuffer();

				SupType supType = new SupType();

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(jsonObj.get("type_code"))) {

					mapVo.put("type_code", jsonObj.get("type_code").toString());

					supType.setType_code(jsonObj.get("type_code").toString());

				} else {

					err_sb.append("供应商分类编码为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("type_name"))) {

					mapVo.put("type_name", jsonObj.get("type_name").toString());

					supType.setType_name(jsonObj.get("type_name").toString());

				} else {

					err_sb.append("供应商分类名称为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					mapVo.put("is_stop",
							Integer.parseInt(jsonObj.get("is_stop").toString()));

					supType.setIs_stop(Integer.parseInt(jsonObj.get("is_stop")
							.toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					mapVo.put("note", jsonObj.get("note").toString());

					supType.setNote(jsonObj.get("note").toString());

				} else {

					mapVo.put("note", "");

					supType.setNote("");
				}

				SupType eSupType = supTypeService.querySupTypeByCode(mapVo);

				if (eSupType != null) {

					err_sb.append("供应商分类编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					supType.setError_type(err_sb.toString());

					list_err.add(supType);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));

					supTypeService.addSupType(mapVo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			return JSONObject
					.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));

		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}
	}
}
