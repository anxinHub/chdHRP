
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
import com.chd.hrp.ass.entity.dict.AssCheckItemDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssCheckItemDictService;

/**
 * 
 * @Description: 050109 检查项目字典
 * @Table: ASS_CHECK_ITEM_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssCheckItemDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssCheckItemDictController.class);

	// 引入Service服务
	@Resource(name = "assCheckItemDictService")
	private final AssCheckItemDictService assCheckItemDictService = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/assCheckItemDictMainPage", method = RequestMethod.GET)
	public String assCheckItemDictMainPage(Model mode) throws Exception {

		return "hrp/ass/asscheckitemdict/assCheckItemDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/assCheckItemDictAddPage", method = RequestMethod.GET)
	public String assCheckItemDictAddPage(Model mode) throws Exception {

		return "hrp/ass/asscheckitemdict/assCheckItemDictAdd";

	}

	/**
	 * @Description 添加数据 050109 检查项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/addAssCheckItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckItemDictJson = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("check_item_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("check_item_name").toString()));

		try {

			String retErrot = "";

			// 根据编号获取对象
			AssCheckItemDict assCheckItemDict = assCheckItemDictService.queryAssCheckItemDictByCode(mapVo);
			if (assCheckItemDict != null) {

				retErrot = "{\"error\":\"编码：【" + assCheckItemDict.getCheck_item_code()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			// 根据名称获取对象
			AssCheckItemDict assCheckItemDictName = assCheckItemDictService.queryByName(mapVo);
			if (assCheckItemDictName != null) {

				retErrot = "{\"error\":\"名称：【" + assCheckItemDictName.getCheck_item_name()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}

			assCheckItemDictJson = assCheckItemDictService.addAssCheckItemDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assCheckItemDictJson);

	}

	/**
	 * @Description 更新跳转页面 050109 检查项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/assCheckItemDictUpdatePage", method = RequestMethod.GET)
	public String assCheckItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		AssCheckItemDict assCheckItemDict = new AssCheckItemDict();

		assCheckItemDict = assCheckItemDictService.queryAssCheckItemDictByCode(mapVo);

		mode.addAttribute("group_id", assCheckItemDict.getGroup_id());
		mode.addAttribute("hos_id", assCheckItemDict.getHos_id());
		mode.addAttribute("copy_code", assCheckItemDict.getCopy_code());
		mode.addAttribute("check_item_code", assCheckItemDict.getCheck_item_code());
		mode.addAttribute("check_item_name", assCheckItemDict.getCheck_item_name());
		mode.addAttribute("spell_code", assCheckItemDict.getSpell_code());
		mode.addAttribute("wbx_code", assCheckItemDict.getWbx_code());
		mode.addAttribute("is_stop", assCheckItemDict.getIs_stop());

		return "hrp/ass/asscheckitemdict/assCheckItemDictUpdate";
	}

	/**
	 * @Description 更新数据 050109 检查项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/updateAssCheckItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckItemDictJson = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("check_item_name").toString()));

		try {

			assCheckItemDictJson = assCheckItemDictService.updateAssCheckItemDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assCheckItemDictJson);
	}

	/**
	 * @Description 删除数据 050109 检查项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/deleteAssCheckItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assCheckItemDictJson = "";
		String str = "";
		boolean falg = true;
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_CHECK_ITEM_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_CHECK_ITEM_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_item_code", ids[3]);

			listVo.add(mapVo);

		}
		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的 检查项目被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		try {

			assCheckItemDictJson = assCheckItemDictService.deleteBatchAssCheckItemDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assCheckItemDictJson);

	}

	/**
	 * @Description 查询数据 050109 检查项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/queryAssCheckItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckItemDict = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {

			assCheckItemDict = assCheckItemDictService.queryAssCheckItemDict(getPage(mapVo));

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assCheckItemDict);

	}

	/**
	 * @Description 导入跳转页面 050109 检查项目字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/assCheckItemDictImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assCheckItemDictImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {

			String reJson = assCheckItemDictService.readAssCheckItemDictFiles(mapVo);

			return reJson;

		} catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}
	}

	/**
	 * @Description 下载导入模版 050109 检查项目字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/asscheckitemdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "检查项目维护字典.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050109 检查项目字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/readCostBonusCostRelaFiles", method = RequestMethod.POST)
	public String readCostBonusCostRelaFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssCheckItemDict> list_err = new ArrayList<AssCheckItemDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssCheckItemDict assCheckItemDict = new AssCheckItemDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				if (StringTool.isNotBlank(temp[0])) {
					assCheckItemDict.setCheck_item_code(temp[0]);
					mapVo.put("check_item_code", temp[0]);

				} else {

					err_sb.append("检查项目编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assCheckItemDict.setCheck_item_name(temp[1]);

					mapVo.put("check_item_name", temp[1]);

				} else {

					err_sb.append("检查项目名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					assCheckItemDict.setIs_stop(Integer.valueOf(temp[2]));

					mapVo.put("is_stop", temp[2]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssCheckItemDict data_exc_extis = assCheckItemDictService.queryAssCheckItemDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assCheckItemDict.setError_type(err_sb.toString());

					list_err.add(assCheckItemDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("check_item_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("check_item_name").toString()));

					try {

						String dataJson = assCheckItemDictService.addAssCheckItemDict(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssCheckItemDict data_exc = new AssCheckItemDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050109 检查项目字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asscheckitemdict/addBatchAssCheckItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssCheckItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssCheckItemDict> list_err = new ArrayList<AssCheckItemDict>();

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

				AssCheckItemDict assCheckItemDict = new AssCheckItemDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("group_id"))) {

					assCheckItemDict.setGroup_id(Long.valueOf((String) jsonObj.get("group_id")));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集体ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {

					assCheckItemDict.setHos_id(Long.valueOf((String) jsonObj.get("hos_id")));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {

					assCheckItemDict.setCopy_code((String) jsonObj.get("copy_code"));

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("check_item_code"))) {

					assCheckItemDict.setCheck_item_code((String) jsonObj.get("check_item_code"));

					mapVo.put("check_item_code", jsonObj.get("check_item_code"));

				} else {

					err_sb.append("验收项目编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("check_item_name"))) {

					assCheckItemDict.setCheck_item_name((String) jsonObj.get("check_item_name"));

					mapVo.put("check_item_name", jsonObj.get("check_item_name"));

				} else {

					err_sb.append("验收项目名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assCheckItemDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssCheckItemDict data_exc_extis = assCheckItemDictService.queryAssCheckItemDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assCheckItemDict.setError_type(err_sb.toString());

					list_err.add(assCheckItemDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("check_item_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("check_item_name").toString()));

					try {

						String dataJson = assCheckItemDictService.addAssCheckItemDict(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssCheckItemDict data_exc = new AssCheckItemDict();

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
