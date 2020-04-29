
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.controller.dict;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.dict.AssFileTypeDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssFileTypeDictService;

/**
 * 
 * @Description: 050105 文档类别
 * @Table: ASS_FILE_TYPE_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssFileTypeDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssFileTypeDictController.class);

	// 引入Service服务
	@Resource(name = "assFileTypeDictService")
	private final AssFileTypeDictService assFileTypeDictService = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/assFileTypeDictMainPage", method = RequestMethod.GET)
	public String assFileTypeDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assfiletypedict/assFileTypeDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/assFileTypeDictAddPage", method = RequestMethod.GET)
	public String assFileTypeDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assfiletypedict/assFileTypeDictAdd";

	}

	/**
	 * @Description 添加数据 050105 文档类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/addAssFileTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssFileTypeDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assFileTypeDictJson = "";
		String retErrot = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", SessionManager.getModCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));
		mapVo.put("wbx_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));

		try {
			// 获取对象050105 文档类别
			AssFileTypeDict assFileTypeDict = assFileTypeDictService.queryAssFileTypeDictByCode(mapVo);
			if (assFileTypeDict != null) {

				retErrot = "{\"error\":\"编码：【" + assFileTypeDict.getEqui_usage_code()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			// 获取对象050105 文档类别
			AssFileTypeDict assFileTypeDictName = assFileTypeDictService.queryAssFileTypeDictByName(mapVo);
			if (assFileTypeDictName != null) {

				retErrot = "{\"error\":\"名称：【" + assFileTypeDictName.getEqui_usage_name()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}

			assFileTypeDictJson = assFileTypeDictService.addAssFileTypeDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assFileTypeDictJson);

	}

	/**
	 * @Description 更新跳转页面 050105 文档类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/assFileTypeDictUpdatePage", method = RequestMethod.GET)
	public String assFileTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssFileTypeDict assFileTypeDict = new AssFileTypeDict();

		assFileTypeDict = assFileTypeDictService.queryAssFileTypeDictByCode(mapVo);

		mode.addAttribute("group_id", assFileTypeDict.getGroup_id());
		mode.addAttribute("hos_id", assFileTypeDict.getHos_id());
		mode.addAttribute("copy_code", assFileTypeDict.getCopy_code());
		mode.addAttribute("mod_code", assFileTypeDict.getMod_code());
		mode.addAttribute("equi_usage_code", assFileTypeDict.getEqui_usage_code());
		mode.addAttribute("equi_usage_name", assFileTypeDict.getEqui_usage_name());
		mode.addAttribute("spell_code", assFileTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", assFileTypeDict.getWbx_code());
		mode.addAttribute("is_stop", assFileTypeDict.getIs_stop());

		return "hrp/ass/assfiletypedict/assFileTypeDictUpdate";
	}

	/**
	 * @Description 更新数据 050105 文档类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/updateAssFileTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssFileTypeDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assFileTypeDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));
		mapVo.put("wbx_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));

		try {

			assFileTypeDictJson = assFileTypeDictService.updateAssFileTypeDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assFileTypeDictJson);
	}

	/**
	 * @Description 删除数据 050105 文档类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/deleteAssFileTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssFileTypeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assFileTypeDictJson = "";
		String str = "";
		boolean falg = true;
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_FILE_TYPE_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_FILE_TYPE_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("mod_code", ids[3]);
			mapVo.put("equi_usage_code", ids[4]);

			listVo.add(mapVo);

		}
		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的文档类别被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		try {

			assFileTypeDictJson = assFileTypeDictService.deleteBatchAssFileTypeDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assFileTypeDictJson);

	}

	/**
	 * @Description 查询数据 050105 文档类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/queryAssFileTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssFileTypeDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assFileTypeDict = assFileTypeDictService.queryAssFileTypeDict(getPage(mapVo));

		return JSONObject.parseObject(assFileTypeDict);

	}

	/**
	 * @Description 导入跳转页面 050105 文档类别
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/assFileTypeDictImportPage", method = RequestMethod.GET)
	public String assFileTypeDictImportPage(Model mode) throws Exception {

		return "hrp/ass/assfiletypedict/assFileTypeDictImport";

	}

	/**
	 * @Description 下载导入模版 050105 文档类别
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assfiletypedict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "base\\downTemplate", "文档类别.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050105 文档类别
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/readCostBonusCostRelaFiles", method = RequestMethod.POST)
	public String readCostBonusCostRelaFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssFileTypeDict> list_err = new ArrayList<AssFileTypeDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size() - 1; i++) {

				StringBuffer err_sb = new StringBuffer();

				AssFileTypeDict assFileTypeDict = new AssFileTypeDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[0])) {

					assFileTypeDict.setMod_code(temp[0]);

					mapVo.put("mod_code", temp[0]);

				} else {

					err_sb.append("系统编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assFileTypeDict.setEqui_usage_code(temp[1]);

					mapVo.put("equi_usage_code", temp[1]);

				} else {

					err_sb.append("文档类别编码为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					assFileTypeDict.setEqui_usage_name(temp[2]);

					mapVo.put("equi_usage_name", temp[2]);

				} else {

					err_sb.append("文档类别名称为空  ");

				}
				if (StringTool.isNotBlank(temp[3])) {

					assFileTypeDict.setIs_stop(Integer.valueOf(temp[3]));

					mapVo.put("is_stop", temp[3]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssFileTypeDict data_exc_extis = assFileTypeDictService.queryAssFileTypeDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assFileTypeDict.setError_type(err_sb.toString());

					list_err.add(assFileTypeDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("equi_usage_name").toString()));

					try {

						String dataJson = assFileTypeDictService.addAssFileTypeDict(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssFileTypeDict data_exc = new AssFileTypeDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050105 文档类别
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assfiletypedict/addBatchAssFileTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssFileTypeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssFileTypeDict> list_err = new ArrayList<AssFileTypeDict>();

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

				AssFileTypeDict assFileTypeDict = new AssFileTypeDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("mod_code"))) {

					assFileTypeDict.setMod_code((String) jsonObj.get("mod_code"));

					mapVo.put("mod_code", jsonObj.get("mod_code"));

				} else {

					err_sb.append("系统编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("equi_usage_code"))) {

					assFileTypeDict.setEqui_usage_code((String) jsonObj.get("equi_usage_code"));

					mapVo.put("equi_usage_code", jsonObj.get("equi_usage_code"));

				} else {

					err_sb.append("文档类别编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("equi_usage_name"))) {

					assFileTypeDict.setEqui_usage_name((String) jsonObj.get("equi_usage_name"));

					mapVo.put("equi_usage_name", jsonObj.get("equi_usage_name"));

				} else {

					err_sb.append("文档类别名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assFileTypeDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssFileTypeDict data_exc_extis = assFileTypeDictService.queryAssFileTypeDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assFileTypeDict.setError_type(err_sb.toString());

					list_err.add(assFileTypeDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("equi_usage_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("equi_usage_name").toString()));

					try {

						String dataJson = assFileTypeDictService.addAssFileTypeDict(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssFileTypeDict data_exc = new AssFileTypeDict();

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
