
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
import com.chd.hrp.ass.entity.dict.AssDeviceDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssDeviceDictService;

/**
 * 
 * @Description: 050105 设备来源
 * @Table: ASS_FILE_TYPE_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDeviceDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDeviceDictController.class);

	// 引入Service服务
	@Resource(name = "assDeviceDictService")
	private final AssDeviceDictService assDeviceDictService = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/assDeviceDictMainPage", method = RequestMethod.GET)
	public String assDeviceDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assdevicedict/assDeviceDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/assDeviceDictAddPage", method = RequestMethod.GET)
	public String assDeviceDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assdevicedict/assDeviceDictAdd";

	}

	/**
	 * @Description 添加数据 050105 设备来源
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/addAssDeviceDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDeviceDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assDeviceDictJson = "";
		String retErrot = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("device_name").toString()));
		mapVo.put("wbx_code", StringTool.toPinyinShouZiMu(mapVo.get("device_name").toString()));

		try {
			// 获取对象050105 设备来源
			AssDeviceDict assDeviceDict = assDeviceDictService.queryAssDeviceDictByCode(mapVo);
			if (assDeviceDict != null) {

				retErrot = "{\"error\":\"编码：【" + assDeviceDict.getDevice_code()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			// 获取对象050105 设备来源
			AssDeviceDict assDeviceDictName = assDeviceDictService.queryAssDeviceDictByName(mapVo);
			if (assDeviceDictName != null) {

				retErrot = "{\"error\":\"名称：【" + assDeviceDictName.getDevice_name()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}

			assDeviceDictJson = assDeviceDictService.addAssDeviceDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assDeviceDictJson);

	}

	/**
	 * @Description 更新跳转页面 050105 设备来源
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/assDeviceDictUpdatePage", method = RequestMethod.GET)
	public String assDeviceDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());


		AssDeviceDict assDeviceDict = new AssDeviceDict();

		assDeviceDict = assDeviceDictService.queryAssDeviceDictByCode(mapVo);

		mode.addAttribute("group_id", assDeviceDict.getGroup_id());
		mode.addAttribute("hos_id", assDeviceDict.getHos_id());
		mode.addAttribute("device_code", assDeviceDict.getDevice_code());
		mode.addAttribute("device_name", assDeviceDict.getDevice_name());
		mode.addAttribute("spell_code", assDeviceDict.getSpell_code());
		mode.addAttribute("wbx_code", assDeviceDict.getWbx_code());
		mode.addAttribute("is_stop", assDeviceDict.getIs_stop());

		return "hrp/ass/assdevicedict/assDeviceDictUpdate";
	}

	/**
	 * @Description 更新数据 050105 设备来源
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/updateAssDeviceDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssDeviceDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assDeviceDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("device_name").toString()));
		mapVo.put("wbx_code", StringTool.toPinyinShouZiMu(mapVo.get("device_name").toString()));

		try {

			assDeviceDictJson = assDeviceDictService.updateAssDeviceDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assDeviceDictJson);
	}

	/**
	 * @Description 删除数据 050105 设备来源
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/deleteAssDeviceDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDeviceDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assDeviceDictJson = "";
		String str = "";
		boolean falg = true;
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_DEVICE_DICT", ids[2]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_DEVICE_DICT", ids[2]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("device_code", ids[2]);

			listVo.add(mapVo);

		}
		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的设备来源被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		try {

			assDeviceDictJson = assDeviceDictService.deleteBatchAssDeviceDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assDeviceDictJson);

	}

	/**
	 * @Description 查询数据 050105 设备来源
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/queryAssDeviceDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDeviceDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		String assDeviceDict = assDeviceDictService.queryAssDeviceDict(getPage(mapVo));

		return JSONObject.parseObject(assDeviceDict);

	}

	/**
	 * @Description 导入跳转页面 050105 设备来源
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/assDeviceDictImportPage", method = RequestMethod.GET)
	public String assDeviceDictImportPage(Model mode) throws Exception {

		return "hrp/ass/assdevicedict/assDeviceDictImport";

	}

	/**
	 * @Description 下载导入模版 050105 设备来源
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assdevicedict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "base\\downTemplate", "设备来源.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050105 设备来源
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/readCostBonusCostRelaFiles", method = RequestMethod.POST)
	public String readCostBonusCostRelaFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssDeviceDict> list_err = new ArrayList<AssDeviceDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size() - 1; i++) {

				StringBuffer err_sb = new StringBuffer();

				AssDeviceDict assDeviceDict = new AssDeviceDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				if (StringTool.isNotBlank(temp[0])) {

					assDeviceDict.setDevice_code(temp[0]);

					mapVo.put("device_code", temp[0]);

				} else {

					err_sb.append("设备来源编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assDeviceDict.setDevice_name(temp[1]);

					mapVo.put("device_name", temp[1]);

				} else {

					err_sb.append("设备来源名称为空  ");

				}
				if (StringTool.isNotBlank(temp[2])) {

					assDeviceDict.setIs_stop(Integer.valueOf(temp[2]));

					mapVo.put("is_stop", temp[2]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssDeviceDict data_exc_extis = assDeviceDictService.queryAssDeviceDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assDeviceDict.setError_type(err_sb.toString());

					list_err.add(assDeviceDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("device_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("device_name").toString()));

					try {

						String dataJson = assDeviceDictService.addAssDeviceDict(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssDeviceDict data_exc = new AssDeviceDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050105 设备来源
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assdevicedict/addBatchAssDeviceDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssDeviceDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssDeviceDict> list_err = new ArrayList<AssDeviceDict>();

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

				AssDeviceDict assDeviceDict = new AssDeviceDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("device_code"))) {

					assDeviceDict.setDevice_code((String) jsonObj.get("device_code"));

					mapVo.put("device_code", jsonObj.get("device_code"));

				} else {

					err_sb.append("设备来源编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("device_name"))) {

					assDeviceDict.setDevice_name((String) jsonObj.get("device_name"));

					mapVo.put("device_name", jsonObj.get("device_name"));

				} else {

					err_sb.append("设备来源名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assDeviceDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssDeviceDict data_exc_extis = assDeviceDictService.queryAssDeviceDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assDeviceDict.setError_type(err_sb.toString());

					list_err.add(assDeviceDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("device_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("device_name").toString()));

					try {

						String dataJson = assDeviceDictService.addAssDeviceDict(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssDeviceDict data_exc = new AssDeviceDict();

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
