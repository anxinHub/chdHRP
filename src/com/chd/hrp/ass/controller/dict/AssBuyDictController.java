
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
import com.chd.hrp.ass.entity.dict.AssBuyDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssBuyDictService;

/**
 * 
 * @Description: 050105 申购类别
 * @Table: ASS_FILE_TYPE_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssBuyDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBuyDictController.class);

	// 引入Service服务
	@Resource(name = "assBuyDictService")
	private final AssBuyDictService assBuyDictService = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/assBuyDictMainPage", method = RequestMethod.GET)
	public String assBuyDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assbuydict/assBuyDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/assBuyDictAddPage", method = RequestMethod.GET)
	public String assBuyDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assbuydict/assBuyDictAdd";

	}

	/**
	 * @Description 添加数据 050105 申购类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/addAssBuyDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBuyDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assBuyDictJson = "";
		String retErrot = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("buy_name").toString()));
		mapVo.put("wbx_code", StringTool.toPinyinShouZiMu(mapVo.get("buy_name").toString()));

		try {
			// 获取对象050105 申购类别
			AssBuyDict assBuyDict = assBuyDictService.queryAssBuyDictByCode(mapVo);
			if (assBuyDict != null) {

				retErrot = "{\"error\":\"编码：【" + assBuyDict.getBuy_code()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			// 获取对象050105 申购类别
			AssBuyDict assBuyDictName = assBuyDictService.queryAssBuyDictByName(mapVo);
			if (assBuyDictName != null) {

				retErrot = "{\"error\":\"名称：【" + assBuyDictName.getBuy_name()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}

			assBuyDictJson = assBuyDictService.addAssBuyDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBuyDictJson);

	}

	/**
	 * @Description 更新跳转页面 050105 申购类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/assBuyDictUpdatePage", method = RequestMethod.GET)
	public String assBuyDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		AssBuyDict assBuyDict = new AssBuyDict();

		assBuyDict = assBuyDictService.queryAssBuyDictByCode(mapVo);

		mode.addAttribute("group_id", assBuyDict.getGroup_id());
		mode.addAttribute("hos_id", assBuyDict.getHos_id());
		mode.addAttribute("buy_code", assBuyDict.getBuy_code());
		mode.addAttribute("buy_name", assBuyDict.getBuy_name());
		mode.addAttribute("spell_code", assBuyDict.getSpell_code());
		mode.addAttribute("wbx_code", assBuyDict.getWbx_code());
		mode.addAttribute("is_stop", assBuyDict.getIs_stop());

		return "hrp/ass/assbuydict/assBuyDictUpdate";
	}

	/**
	 * @Description 更新数据 050105 申购类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/updateAssBuyDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBuyDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assBuyDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("buy_name").toString()));
		mapVo.put("wbx_code", StringTool.toPinyinShouZiMu(mapVo.get("buy_name").toString()));

		try {

			assBuyDictJson = assBuyDictService.updateAssBuyDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBuyDictJson);
	}

	/**
	 * @Description 删除数据 050105 申购类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/deleteAssBuyDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBuyDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assBuyDictJson = "";
		String str = "";
		boolean falg = true;
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_BUY_DICT", ids[2]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_BUY_DICT", ids[2]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("buy_code", ids[2]);

			listVo.add(mapVo);

		}
		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的申购类别被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		try {

			assBuyDictJson = assBuyDictService.deleteBatchAssBuyDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assBuyDictJson);

	}

	/**
	 * @Description 查询数据 050105 申购类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/queryAssBuyDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBuyDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		String assBuyDict = assBuyDictService.queryAssBuyDict(getPage(mapVo));

		return JSONObject.parseObject(assBuyDict);

	}

	/**
	 * @Description 导入跳转页面 050105 申购类别
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/assBuyDictImportPage", method = RequestMethod.GET)
	public String assBuyDictImportPage(Model mode) throws Exception {

		return "hrp/ass/assbuydict/assBuyDictImport";

	}

	/**
	 * @Description 下载导入模版 050105 申购类别
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assbuydict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "base\\downTemplate", "申购类别.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050105 申购类别
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/readCostBonusCostRelaFiles", method = RequestMethod.POST)
	public String readCostBonusCostRelaFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssBuyDict> list_err = new ArrayList<AssBuyDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size() - 1; i++) {

				StringBuffer err_sb = new StringBuffer();

				AssBuyDict assBuyDict = new AssBuyDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				if (StringTool.isNotBlank(temp[0])) {

					assBuyDict.setBuy_code(temp[0]);

					mapVo.put("buy_code", temp[0]);

				} else {

					err_sb.append("申购类别编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assBuyDict.setBuy_name(temp[1]);

					mapVo.put("buy_name", temp[1]);

				} else {

					err_sb.append("申购类别名称为空  ");

				}
				if (StringTool.isNotBlank(temp[2])) {

					assBuyDict.setIs_stop(Integer.valueOf(temp[2]));

					mapVo.put("is_stop", temp[2]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssBuyDict data_exc_extis = assBuyDictService.queryAssBuyDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assBuyDict.setError_type(err_sb.toString());

					list_err.add(assBuyDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("buy_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("buy_name").toString()));

					try {

						String dataJson = assBuyDictService.addAssBuyDict(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssBuyDict data_exc = new AssBuyDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050105 申购类别
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assbuydict/addBatchAssBuyDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssBuyDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssBuyDict> list_err = new ArrayList<AssBuyDict>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AssBuyDict assBuyDict = new AssBuyDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("buy_code"))) {

					assBuyDict.setBuy_code((String) jsonObj.get("buy_code"));

					mapVo.put("buy_code", jsonObj.get("buy_code"));

				} else {

					err_sb.append("申购类别编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("buy_name"))) {

					assBuyDict.setBuy_name((String) jsonObj.get("buy_name"));

					mapVo.put("buy_name", jsonObj.get("buy_name"));

				} else {

					err_sb.append("申购类别名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assBuyDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssBuyDict data_exc_extis = assBuyDictService.queryAssBuyDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assBuyDict.setError_type(err_sb.toString());

					list_err.add(assBuyDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("buy_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("buy_name").toString()));

					try {

						String dataJson = assBuyDictService.addAssBuyDict(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssBuyDict data_exc = new AssBuyDict();

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
