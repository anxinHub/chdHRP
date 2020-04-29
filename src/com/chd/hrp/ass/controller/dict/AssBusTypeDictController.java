
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

import org.apache.log4j.Logger;
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
import com.chd.hrp.ass.entity.dict.AssBusTypeDict;
import com.chd.hrp.ass.service.dict.AssBusTypeDictService;

/**
 * 
 * @Description: 050111 业务类型
 * @Table: ASS_BUS_TYPE_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssBusTypeDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBusTypeDictController.class);

	// 引入Service服务
	@Resource(name = "assBusTypeDictService")
	private final AssBusTypeDictService assBusTypeDictService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/assBusTypeDictMainPage", method = RequestMethod.GET)
	public String assBusTypeDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assbustypedict/assBusTypeDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/assBusTypeDictAddPage", method = RequestMethod.GET)
	public String assBusTypeDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assbustypedict/assBusTypeDictAdd";

	}

	/**
	 * @Description 添加数据 050111 业务类型
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/addAssBusTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBusTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assBusTypeDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bus_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bus_type_name").toString()));

		try {

			assBusTypeDictJson = assBusTypeDictService.addAssBusTypeDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBusTypeDictJson);

	}

	/**
	 * @Description 更新跳转页面 050111 业务类型
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/assBusTypeDictUpdatePage", method = RequestMethod.GET)
	public String assBusTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBusTypeDict assBusTypeDict = new AssBusTypeDict();

		assBusTypeDict = assBusTypeDictService.queryAssBusTypeDictByCode(mapVo);

		mode.addAttribute("group_id", assBusTypeDict.getGroup_id());
		mode.addAttribute("hos_id", assBusTypeDict.getHos_id());
		mode.addAttribute("copy_code", assBusTypeDict.getCopy_code());
		mode.addAttribute("bus_type_code", assBusTypeDict.getBus_type_code());
		mode.addAttribute("bus_type_name", assBusTypeDict.getBus_type_name());
		mode.addAttribute("spell_code", assBusTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", assBusTypeDict.getWbx_code());
		mode.addAttribute("is_stop", assBusTypeDict.getIs_stop());
		mode.addAttribute("in_out_type", assBusTypeDict.getIn_out_type());

		return "hrp/ass/assbustypedict/assBusTypeDictUpdate";
	}

	/**
	 * @Description 更新数据 050111 业务类型
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/updateAssBusTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBusTypeDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assBusTypeDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bus_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bus_type_name").toString()));
		try {

			assBusTypeDictJson = assBusTypeDictService.updateAssBusTypeDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assBusTypeDictJson);
	}

	/**
	 * @Description 删除数据 050111 业务类型
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/deleteAssBusTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBusTypeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assBusTypeDictJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("bus_type_code", ids[3]);

			listVo.add(mapVo);

		}
		try {

			assBusTypeDictJson = assBusTypeDictService.deleteBatchAssBusTypeDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assBusTypeDictJson);

	}

	/**
	 * @Description 查询数据 050111 业务类型
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/queryAssBusTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBusTypeDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assBusTypeDict = assBusTypeDictService.queryAssBusTypeDict(getPage(mapVo));

		return JSONObject.parseObject(assBusTypeDict);

	}

	/**
	 * @Description 导入跳转页面 050111 业务类型
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/assBusTypeDictImportPage", method = RequestMethod.GET)
	public String assBusTypeDictImportPage(Model mode) throws Exception {

		return "hrp/ass/assbustypedict/assBusTypeDictImport";

	}

	/**
	 * @Description 下载导入模版 050111 业务类型
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assbustypedict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "base\\downTemplate", "业务类型.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050111 业务类型
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/readCostBonusCostRelaFiles", method = RequestMethod.POST)
	public String readCostBonusCostRelaFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssBusTypeDict> list_err = new ArrayList<AssBusTypeDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size() - 1; i++) {

				StringBuffer err_sb = new StringBuffer();

				AssBusTypeDict assBusTypeDict = new AssBusTypeDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[0])) {

					assBusTypeDict.setBus_type_code(temp[0]);

					mapVo.put("bus_type_code", temp[0]);

				} else {

					err_sb.append("业务类别编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assBusTypeDict.setBus_type_name(temp[1]);

					mapVo.put("bus_type_name", temp[1]);

				} else {

					err_sb.append("业务类别名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					assBusTypeDict.setIs_stop(Integer.valueOf(temp[2]));

					mapVo.put("is_stop", temp[2]);

				} else {

					err_sb.append("是否停用为空  ");

				}
				if (StringTool.isNotBlank(temp[3])) {

					assBusTypeDict.setIn_out_type(Integer.valueOf(temp[3]));

					mapVo.put("in_out_type", temp[3]);

				} else {

					err_sb.append("增加减少类型为空  ");

				}

				AssBusTypeDict data_exc_extis = assBusTypeDictService.queryAssBusTypeDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assBusTypeDict.setError_type(err_sb.toString());

					list_err.add(assBusTypeDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bus_type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bus_type_name").toString()));

					try {

						String dataJson = assBusTypeDictService.addAssBusTypeDict(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssBusTypeDict data_exc = new AssBusTypeDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050111 业务类型
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assbustypedict/addBatchAssBusTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssBusTypeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssBusTypeDict> list_err = new ArrayList<AssBusTypeDict>();

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

				AssBusTypeDict assBusTypeDict = new AssBusTypeDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {

					assBusTypeDict.setBus_type_code((String) jsonObj.get("bus_type_code"));

					mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));

				} else {

					err_sb.append("业务类别编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("bus_type_name"))) {

					assBusTypeDict.setBus_type_name((String) jsonObj.get("bus_type_name"));

					mapVo.put("bus_type_name", jsonObj.get("bus_type_name"));

				} else {

					err_sb.append("业务类别名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assBusTypeDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("in_out_type"))) {

					assBusTypeDict.setIs_stop(Integer.valueOf((String) jsonObj.get("in_out_type")));

					mapVo.put("in_out_type", jsonObj.get("in_out_type"));

				} else {

					err_sb.append("增加减少类型为空  ");

				}

				AssBusTypeDict data_exc_extis = assBusTypeDictService.queryAssBusTypeDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assBusTypeDict.setError_type(err_sb.toString());

					list_err.add(assBusTypeDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bus_type_name").toString()));

					mapVo.put("spell_code", StringTool.toWuBi(mapVo.get("bus_type_name").toString()));

					try {

						String dataJson = assBusTypeDictService.addAssBusTypeDict(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssBusTypeDict data_exc = new AssBusTypeDict();

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
