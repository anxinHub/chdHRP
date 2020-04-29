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

import org.activiti.engine.impl.interceptor.Session;
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
import com.chd.hrp.ass.entity.dict.AssAcceptItemDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssAcceptItemDictService;

/**
 * @Description: 050107 验收项目字典
 * @Table: ASS_ACCEPT_ITEM_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssAcceptItemDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssAcceptItemDictController.class);

	// 引入Service服务
	@Resource(name = "assAcceptItemDictService")
	private final AssAcceptItemDictService assAcceptItemDictService = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/assAcceptItemDictMainPage", method = RequestMethod.GET)
	public String assAcceptItemDictMainPage(Model mode) throws Exception {
		mode.addAttribute("hos_name",SessionManager.getHosName());
		mode.addAttribute("user_name",SessionManager.getUserName());
		return "hrp/ass/assacceptitemdict/assAcceptItemDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/assAcceptItemDictAddPage", method = RequestMethod.GET)
	public String assAcceptItemDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assacceptitemdict/assAcceptItemDictAdd";

	}

	/**
	 * @Description 添加数据 050107 验收项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/addAssAcceptItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssAcceptItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assAcceptItemDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("accept_item_name").toString()));

		try {

			String retErrot = "";

			// 根据编号获取对象
			AssAcceptItemDict assAcceptItemDict = assAcceptItemDictService.queryAssAcceptItemDictByCode(mapVo);
			if (assAcceptItemDict != null) {

				retErrot = "{\"error\":\"编码：【" + assAcceptItemDict.getAccept_item_code()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			// 根据名称获取对象
			AssAcceptItemDict assAcceptItemDictName = assAcceptItemDictService.queryByName(mapVo);
			if (assAcceptItemDictName != null) {

				retErrot = "{\"error\":\"名称：【" + assAcceptItemDictName.getAccept_item_name()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}

			assAcceptItemDictJson = assAcceptItemDictService.addAssAcceptItemDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptItemDictJson);

	}

	/**
	 * @Description 更新跳转页面 050107 验收项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/assAcceptItemDictUpdatePage", method = RequestMethod.GET)
	public String assAcceptItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssAcceptItemDict assAcceptItemDict = new AssAcceptItemDict();

		assAcceptItemDict = assAcceptItemDictService.queryAssAcceptItemDictByCode(mapVo);

		mode.addAttribute("group_id", assAcceptItemDict.getGroup_id());
		mode.addAttribute("hos_id", assAcceptItemDict.getHos_id());
		mode.addAttribute("copy_code", assAcceptItemDict.getCopy_code());
		mode.addAttribute("accept_item_code", assAcceptItemDict.getAccept_item_code());
		mode.addAttribute("accept_item_name", assAcceptItemDict.getAccept_item_name());
		mode.addAttribute("spell_code", assAcceptItemDict.getSpell_code());
		mode.addAttribute("wbx_code", assAcceptItemDict.getWbx_code());
		mode.addAttribute("is_stop", assAcceptItemDict.getIs_stop());

		return "hrp/ass/assacceptitemdict/assAcceptItemDictUpdate";
	}

	/**
	 * @Description 更新数据 050107 验收项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/updateAssAcceptItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssAcceptItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assAcceptItemDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("accept_item_code").toString()));
		try {

			assAcceptItemDictJson = assAcceptItemDictService.updateAssAcceptItemDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptItemDictJson);
	}

	/**
	 * @Description 删除数据 050107 验收项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/deleteAssAcceptItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAcceptItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assAcceptItemDictJson = "";
		String str = "";
		boolean falg = true;
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_ACCEPT_ITEM_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_ACCEPT_ITEM_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("accept_item_code", ids[3]);

			listVo.add(mapVo);

		}
		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的验收项目被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		try {

			assAcceptItemDictJson = assAcceptItemDictService.deleteBatchAssAcceptItemDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptItemDictJson);

	}

	/**
	 * @Description 查询数据 050107 验收项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/queryAssAcceptItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAcceptItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assAcceptItemDict = assAcceptItemDictService.queryAssAcceptItemDict(getPage(mapVo));

		return JSONObject.parseObject(assAcceptItemDict);
	}

	/**
	 * @Description 导入跳转页面 050107 验收项目字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/assAcceptItemDictImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assAcceptItemDictImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {

			String reJson = assAcceptItemDictService.readAssAcceptItemDictFiles(mapVo);

			return reJson;

		} catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}

	}

	/**
	 * @Description 下载导入模版 050107 验收项目字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assacceptitemdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "050107 验收项目字典集.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050107 验收项目字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/readAssAcceptItemDictFiles", method = RequestMethod.POST)
	public String readAssAcceptItemDictFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssAcceptItemDict> list_err = new ArrayList<AssAcceptItemDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssAcceptItemDict assAcceptItemDict = new AssAcceptItemDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					assAcceptItemDict.setGroup_id(Long.valueOf(temp[0]));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集体ID为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assAcceptItemDict.setHos_id(Long.valueOf(temp[1]));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					assAcceptItemDict.setCopy_code(temp[2]);

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					assAcceptItemDict.setAccept_item_code(temp[3]);

					mapVo.put("accept_item_code", temp[3]);

				} else {

					err_sb.append("验收项目编码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assAcceptItemDict.setAccept_item_name(temp[4]);

					mapVo.put("accept_item_name", temp[4]);

				} else {

					err_sb.append("验收项目名称为空  ");

				}

				// if (StringTool.isNotBlank(temp[5])) {

				// assAcceptItemDict.setSpell_code(temp[5]);

				// mapVo.put("spell_code", temp[5]);

				// } else {

				// / err_sb.append("拼音码为空 ");

				// }

				// if (StringTool.isNotBlank(temp[6])) {

				// assAcceptItemDict.setWbx_code(temp[6]);

				// mapVo.put("wbx_code", temp[6]);

				// } else {

				// err_sb.append("五笔码为空 ");

				// }

				if (StringTool.isNotBlank(temp[5])) {

					assAcceptItemDict.setIs_stop(Integer.valueOf(temp[5]));

					mapVo.put("is_stop", temp[5]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssAcceptItemDict data_exc_extis = assAcceptItemDictService.queryAssAcceptItemDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assAcceptItemDict.setError_type(err_sb.toString());

					list_err.add(assAcceptItemDict);

				} else {
					// mapVo.put("spell_code",
					// StringTool.toPinyinShouZiMu(mapVo.get("accept_item_name").toString()));
					// mapVo.put("wbx_code",
					// StringTool.toWuBi(mapVo.get("accept_item_name").toString()));

					try {
						String dataJson = assAcceptItemDictService.addAssAcceptItemDict(mapVo);
					} catch (Exception e) {
						return "{\"error\":\"" + e.getMessage() + " \"}";
					}
				}

			}

		} catch (DataAccessException e) {

			AssAcceptItemDict data_exc = new AssAcceptItemDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050107 验收项目字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assacceptitemdict/addBatchAssAcceptItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssAcceptItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssAcceptItemDict> list_err = new ArrayList<AssAcceptItemDict>();

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

				AssAcceptItemDict assAcceptItemDict = new AssAcceptItemDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("group_id"))) {

					assAcceptItemDict.setGroup_id(Long.valueOf((String) jsonObj.get("group_id")));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集体ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {

					assAcceptItemDict.setHos_id(Long.valueOf((String) jsonObj.get("hos_id")));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {

					assAcceptItemDict.setCopy_code((String) jsonObj.get("copy_code"));

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("accept_item_code"))) {

					assAcceptItemDict.setAccept_item_code((String) jsonObj.get("accept_item_code"));

					mapVo.put("accept_item_code", jsonObj.get("accept_item_code"));

				} else {

					err_sb.append("验收项目编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("accept_item_name"))) {

					assAcceptItemDict.setAccept_item_name((String) jsonObj.get("accept_item_name"));

					mapVo.put("accept_item_name", jsonObj.get("accept_item_name"));

				} else {

					err_sb.append("验收项目名称为空  ");

				}
				// if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {

				// assAcceptItemDict.setSpell_code((String)jsonObj.get("spell_code"));

				// mapVo.put("spell_code", jsonObj.get("spell_code"));

				// } else {

				// err_sb.append("拼音码为空 ");

				// }
				// if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {

				// assAcceptItemDict.setWbx_code((String)jsonObj.get("wbx_code"));

				// mapVo.put("wbx_code", jsonObj.get("wbx_code"));

				// } else {

				// err_sb.append("五笔码为空 ");

				// }
				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assAcceptItemDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssAcceptItemDict data_exc_extis = assAcceptItemDictService.queryAssAcceptItemDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assAcceptItemDict.setError_type(err_sb.toString());

					list_err.add(assAcceptItemDict);

				} else {
					// mapVo.put("spell_code",
					// StringTool.toPinyinShouZiMu(mapVo.get("accept_item_name").toString()));
					// mapVo.put("wbx_code",
					// StringTool.toWuBi(mapVo.get("accept_item_name").toString()));
					try {

						String dataJson = assAcceptItemDictService.addAssAcceptItemDict(mapVo);
					} catch (Exception e) {
						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssAcceptItemDict data_exc = new AssAcceptItemDict();

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
