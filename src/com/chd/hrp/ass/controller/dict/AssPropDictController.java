
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
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
import com.chd.hrp.ass.entity.dict.AssPropDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssPropDictService;

/**
 * 
 * @Description: 050107 产权形式字典
 * @Table: ASS_ACCEPT_ITEM_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssPropDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPropDictController.class);

	// 引入Service服务
	@Resource(name = "assPropDictService")
	private final AssPropDictService assPropDictService = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/assPropDictMainPage", method = RequestMethod.GET)
	public String assPropDictMainPage(Model mode) throws Exception {

		return "hrp/ass/asspropdict/assPropDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/assPropDictAddPage", method = RequestMethod.GET)
	public String assPropDictAddPage(Model mode) throws Exception {

		return "hrp/ass/asspropdict/assPropDictAdd";

	}

	/**
	 * @Description 添加数据 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/addAssPropDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPropDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assAcceptItemDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("prop_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("prop_name").toString()));
		try {

			String retErrot = "";

			// 根据编号获取对象
			AssPropDict assPropDict = assPropDictService.queryAssPropDictByCode(mapVo);
			if (assPropDict != null) {

				retErrot = "{\"error\":\"编码：【" + assPropDict.getProp_code() + "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			// 根据名称获取对象
			AssPropDict assPropDictName = assPropDictService.queryByName(mapVo);
			if (assPropDictName != null) {

				retErrot = "{\"error\":\"名称：【" + assPropDictName.getProp_name() + "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			assAcceptItemDictJson = assPropDictService.addAssPropDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptItemDictJson);

	}

	/**
	 * @Description 更新跳转页面 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/assPropDictUpdatePage", method = RequestMethod.GET)
	public String assAcceptItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssPropDict assAcceptItemDict = new AssPropDict();

		assAcceptItemDict = assPropDictService.queryAssPropDictByCode(mapVo);

		mode.addAttribute("group_id", assAcceptItemDict.getGroup_id());
		mode.addAttribute("hos_id", assAcceptItemDict.getHos_id());
		mode.addAttribute("copy_code", assAcceptItemDict.getCopy_code());
		mode.addAttribute("prop_code", assAcceptItemDict.getProp_code());
		mode.addAttribute("prop_name", assAcceptItemDict.getProp_name());
		mode.addAttribute("spell_code", assAcceptItemDict.getSpell_code());
		mode.addAttribute("wbx_code", assAcceptItemDict.getWbx_code());
		mode.addAttribute("is_stop", assAcceptItemDict.getIs_stop());

		return "hrp/ass/asspropdict/assPropDictUpdate";
	}

	/**
	 * @Description 更新数据 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/updateAssPropDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssPropDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assAcceptItemDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("prop_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("prop_name").toString()));
		try {

			assAcceptItemDictJson = assPropDictService.updateAssPropDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptItemDictJson);
	}

	/**
	 * @Description 删除数据 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/deleteAssPropDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPropDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assAcceptItemDictJson = "";

		String str = "";
		boolean falg = true;
		String retErrot = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_PROP_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_PROP_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("prop_code", ids[3]);

			listVo.add(mapVo);

		}
		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的 产权形式字典被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		try {

			assAcceptItemDictJson = assPropDictService.deleteBatchAssPropDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptItemDictJson);

	}

	/**
	 * @Description 查询数据 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/queryAssPropDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPropDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assAcceptItemDict = assPropDictService.queryAssPropDict(getPage(mapVo));

		return JSONObject.parseObject(assAcceptItemDict);
	}

	/**
	 * @Description 导入跳转页面 050107 产权形式字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/assPropDictImport", method = RequestMethod.POST)
	@ResponseBody
	public String assPropDictImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {

			String reJson = assPropDictService.assPropDictImport(mapVo);

			return reJson;

		} catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}
	}

	/**
	 * @Description 下载导入模版 050107 产权形式字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/asspropdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "050107 产权形式字典集.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050107 产权形式字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/readAssPropDictFiles", method = RequestMethod.POST)
	public String readAssPropDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssPropDict> list_err = new ArrayList<AssPropDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssPropDict assAcceptItemDict = new AssPropDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					assAcceptItemDict.setGroup_id(Long.valueOf(temp[0]));

					mapVo.put("group_id", SessionManager.getGroupId());

				}

				if (StringTool.isNotBlank(temp[1])) {

					assAcceptItemDict.setHos_id(Long.valueOf(temp[1]));

					mapVo.put("hos_id", SessionManager.getHosId());

				}

				if (StringTool.isNotBlank(temp[2])) {

					assAcceptItemDict.setCopy_code(temp[2]);

					mapVo.put("copy_code", SessionManager.getCopyCode());

				}

				if (StringTool.isNotBlank(temp[3])) {

					assAcceptItemDict.setProp_code(temp[3]);

					mapVo.put("prop_code", temp[3]);

				} else {

					err_sb.append("验收项目编码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assAcceptItemDict.setProp_name(temp[4]);

					mapVo.put("prop_name", temp[4]);

				} else {

					err_sb.append("验收项目名称为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assAcceptItemDict.setIs_stop(Integer.valueOf(temp[5]));

					mapVo.put("is_stop", temp[5]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssPropDict data_exc_extis = assPropDictService.queryAssPropDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assAcceptItemDict.setError_type(err_sb.toString());

					list_err.add(assAcceptItemDict);

				} else {

					try {
						String dataJson = assPropDictService.addAssPropDict(mapVo);
					} catch (Exception e) {
						return "{\"error\":\"" + e.getMessage() + " \"}";
					}
				}

			}

		} catch (DataAccessException e) {

			AssPropDict data_exc = new AssPropDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050107 产权形式字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asspropdict/addBatchAssPropDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssPropDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssPropDict> list_err = new ArrayList<AssPropDict>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AssPropDict assAcceptItemDict = new AssPropDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("prop_code"))) {

					assAcceptItemDict.setProp_code((String) jsonObj.get("prop_code"));

					mapVo.put("accept_item_code", jsonObj.get("prop_code"));

				} else {

					err_sb.append("验收项目编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("prop_name"))) {

					assAcceptItemDict.setProp_name((String) jsonObj.get("prop_name"));

					mapVo.put("prop_name", jsonObj.get("prop_name"));

				} else {

					err_sb.append("验收项目名称为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assAcceptItemDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssPropDict data_exc_extis = assPropDictService.queryAssPropDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assAcceptItemDict.setError_type(err_sb.toString());

					list_err.add(assAcceptItemDict);

				} else {
					try {

						String dataJson = assPropDictService.addAssPropDict(mapVo);
					} catch (Exception e) {
						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssPropDict data_exc = new AssPropDict();

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
