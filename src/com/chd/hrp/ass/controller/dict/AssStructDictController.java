/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.dict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
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
import com.chd.hrp.ass.entity.dict.AssStructDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssStructDictService;

/**
 * 
 * @Description: 资产建筑结构
 * @Table: ASS_STRUCT_DICT
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssStructDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssStructDictController.class);

	// 引入Service服务
	@Resource(name = "assStructDictService")
	private final AssStructDictService assStructDictService = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/assStructDictMainPage", method = RequestMethod.GET)
	public String assStructDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assstructdict/assStructDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/assStructDictAddPage", method = RequestMethod.GET)
	public String assStructDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assstructdict/assStructDictAdd";

	}

	/**
	 * @Description 导入跳转页面 建筑结构
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/assStructDictImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assStructDictImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		try {

			String reJson = assStructDictService.assStructDictImportPage(mapVo);

			return reJson;

		} catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}

	}

	/**
	 * @Description 添加数据 资产建筑结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/addAssStructDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssStructDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("struct_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("struct_name").toString()));

		String retErrot = "";

		// 根据编号获取对象
		AssStructDict assStructDict = assStructDictService.queryByCode(mapVo);
		if (assStructDict != null) {

			retErrot = "{\"error\":\"编码：【" + assStructDict.getStruct_code() + "】重复,请修改后添加！\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);

		}
		// 根据名称获取对象
		AssStructDict assStructDictName = assStructDictService.queryByName(mapVo);
		if (assStructDictName != null) {

			retErrot = "{\"error\":\"名称：【" + assStructDictName.getStruct_name() + "】重复,请修改后添加！\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);

		}

		String assStructDictJson = assStructDictService.add(mapVo);

		return JSONObject.parseObject(assStructDictJson);

	}

	/**
	 * @Description 更新跳转页面 资产建筑结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/assStructDictUpdatePage", method = RequestMethod.GET)
	public String assStructDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssStructDict assStructDict = new AssStructDict();

		assStructDict = assStructDictService.queryByCode(mapVo);

		mode.addAttribute("group_id", assStructDict.getGroup_id());
		mode.addAttribute("hos_id", assStructDict.getHos_id());
		mode.addAttribute("copy_code", assStructDict.getCopy_code());
		mode.addAttribute("struct_code", assStructDict.getStruct_code());
		mode.addAttribute("struct_name", assStructDict.getStruct_name());
		mode.addAttribute("spell_code", assStructDict.getSpell_code());
		mode.addAttribute("wbx_code", assStructDict.getWbx_code());
		mode.addAttribute("is_stop", assStructDict.getIs_stop());

		return "hrp/ass/assstructdict/assStructDictUpdate";
	}

	/**
	 * @Description 更新数据 资产建筑结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/updateAssStructDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssStructDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("struct_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("struct_name").toString()));

		String assStructDictJson = assStructDictService.update(mapVo);

		return JSONObject.parseObject(assStructDictJson);
	}

	/**
	 * @Description 删除数据 资产建筑结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/deleteAssStructDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssStructDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		String str = "";
		boolean falg = true;
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_STRUCT_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_STRUCT_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("struct_code", ids[3]);

			listVo.add(mapVo);

		}
		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的 资产建筑结构被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		String assStructDictJson = assStructDictService.deleteBatch(listVo);

		return JSONObject.parseObject(assStructDictJson);

	}

	/**
	 * @Description 查询数据 资产建筑结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/queryAssStructDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssStructDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assStructDict = assStructDictService.query(getPage(mapVo));

		return JSONObject.parseObject(assStructDict);

	}

	/**
	 * @Description 导入跳转页面 资产建筑结构
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/assStructDictImportPage", method = RequestMethod.GET)
	public String assStructDictImportPage(Model mode) throws Exception {

		return "hrp/ass/assstructdict/assStructDictImport";

	}

	/**
	 * @Description 下载导入模版 资产建筑结构
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assstructdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "资产建筑结构.xls");

		return null;
	}

	/**
	 * @Description 导入数据 资产建筑结构
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/readAssStructDictFiles", method = RequestMethod.POST)
	public String readAssStructDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssStructDict> list_err = new ArrayList<AssStructDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssStructDict assStructDict = new AssStructDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assStructDict.setStruct_code(temp[3]);
					mapVo.put("struct_code", temp[3]);

				} else {

					err_sb.append("建筑结构代码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assStructDict.setStruct_name(temp[4]);
					mapVo.put("struct_name", temp[4]);

				} else {

					err_sb.append("建筑结构名称为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assStructDict.setSpell_code(temp[5]);
					mapVo.put("spell_code", temp[5]);

				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assStructDict.setWbx_code(temp[6]);
					mapVo.put("wbx_code", temp[6]);

				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assStructDict.setIs_stop(Integer.valueOf(temp[7]));
					mapVo.put("is_stop", temp[7]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssStructDict data_exc_extis = assStructDictService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assStructDict.setError_type(err_sb.toString());

					list_err.add(assStructDict);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = assStructDictService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssStructDict data_exc = new AssStructDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 资产建筑结构
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assstructdict/addBatchAssStructDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssStructDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssStructDict> list_err = new ArrayList<AssStructDict>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AssStructDict assStructDict = new AssStructDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("struct_code"))) {

					assStructDict.setStruct_code((String) jsonObj.get("struct_code"));
					mapVo.put("struct_code", jsonObj.get("struct_code"));
				} else {

					err_sb.append("建筑结构代码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("struct_name"))) {

					assStructDict.setStruct_name((String) jsonObj.get("struct_name"));
					mapVo.put("struct_name", jsonObj.get("struct_name"));
				} else {

					err_sb.append("建筑结构名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {

					assStructDict.setSpell_code((String) jsonObj.get("spell_code"));
					mapVo.put("spell_code", jsonObj.get("spell_code"));
				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {

					assStructDict.setWbx_code((String) jsonObj.get("wbx_code"));
					mapVo.put("wbx_code", jsonObj.get("wbx_code"));
				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assStructDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));
					mapVo.put("is_stop", jsonObj.get("is_stop"));
				} else {

					err_sb.append("是否停用为空  ");

				}

				AssStructDict data_exc_extis = assStructDictService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assStructDict.setError_type(err_sb.toString());

					list_err.add(assStructDict);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = assStructDictService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssStructDict data_exc = new AssStructDict();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

}
