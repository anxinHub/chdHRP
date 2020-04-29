
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
import com.chd.hrp.ass.entity.dict.AssDepreMethodDict;
import com.chd.hrp.ass.service.dict.AssDepreMethodDictService;

/**
 * 
 * @Description: 050106 资产折旧方法字典
 * @Table: ASS_DEPRE_METHOD_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssDepreMethodDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDepreMethodDictController.class);

	// 引入Service服务
	@Resource(name = "assDepreMethodDictService")
	private final AssDepreMethodDictService assDepreMethodDictService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/assDepreMethodDictMainPage", method = RequestMethod.GET)
	public String assDepreMethodDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assdepremethoddict/assDepreMethodDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/assDepreMethodDictAddPage", method = RequestMethod.GET)
	public String assDepreMethodDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assdepremethoddict/assDepreMethodDictAdd";

	}

	/**
	 * @Description 添加数据 050106 资产折旧方法字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/addAssDepreMethodDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDepreMethodDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assDepreMethodDictJson = "";

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

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_depre_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_depre_name").toString()));

		try {

			assDepreMethodDictJson = assDepreMethodDictService.addAssDepreMethodDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assDepreMethodDictJson);

	}

	/**
	 * @Description 更新跳转页面 050106 资产折旧方法字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/assDepreMethodDictUpdatePage", method = RequestMethod.GET)
	public String assDepreMethodDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssDepreMethodDict assDepreMethodDict = new AssDepreMethodDict();

		assDepreMethodDict = assDepreMethodDictService.queryAssDepreMethodDictByCode(mapVo);

		mode.addAttribute("ass_depre_code", assDepreMethodDict.getAss_depre_code());
		mode.addAttribute("ass_depre_name", assDepreMethodDict.getAss_depre_name());
		mode.addAttribute("ass_depre_define", assDepreMethodDict.getAss_depre_define());
		mode.addAttribute("spell_code", assDepreMethodDict.getSpell_code());
		mode.addAttribute("wbx_code", assDepreMethodDict.getWbx_code());
		mode.addAttribute("is_stop", assDepreMethodDict.getIs_stop());

		return "hrp/ass/assdepremethoddict/assDepreMethodDictUpdate";
	}

	/**
	 * @Description 更新数据 050106 资产折旧方法字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/updateAssDepreMethodDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssDepreMethodDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assDepreMethodDictJson = "";

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

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_depre_name").toString()));

		mapVo.put("wbx_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_depre_name").toString()));

		try {

			assDepreMethodDictJson = assDepreMethodDictService.updateAssDepreMethodDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assDepreMethodDictJson);
	}

	/**
	 * @Description 删除数据 050106 资产折旧方法字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/deleteAssDepreMethodDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreMethodDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assDepreMethodDictJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("ass_depre_code", ids[0]);

			listVo.add(mapVo);

		}

		try {

			assDepreMethodDictJson = assDepreMethodDictService.deleteBatchAssDepreMethodDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assDepreMethodDictJson);

	}

	/**
	 * @Description 查询数据 050106 资产折旧方法字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/queryAssDepreMethodDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreMethodDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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

		String assDepreMethodDict = assDepreMethodDictService.queryAssDepreMethodDict(getPage(mapVo));

		return JSONObject.parseObject(assDepreMethodDict);

	}

	/**
	 * @Description 导入跳转页面 050106 资产折旧方法字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/assDepreMethodDictImportPage", method = RequestMethod.GET)
	public String assDepreMethodDictImportPage(Model mode) throws Exception {

		return "hrp/ass/assdepremethoddict/assDepreMethodDictImport";

	}

	/**
	 * @Description 下载导入模版 050106 资产折旧方法字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assdepremethoddict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "base\\downTemplate", "资产折旧方法.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050106 资产折旧方法字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/readCostBonusCostRelaFiles", method = RequestMethod.POST)
	public String readCostBonusCostRelaFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssDepreMethodDict> list_err = new ArrayList<AssDepreMethodDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size() - 1; i++) {

				StringBuffer err_sb = new StringBuffer();

				AssDepreMethodDict assDepreMethodDict = new AssDepreMethodDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					assDepreMethodDict.setAss_depre_code(temp[0]);

					mapVo.put("ass_depre_code", temp[0]);

				} else {

					err_sb.append("折旧方法编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assDepreMethodDict.setAss_depre_name(temp[1]);

					mapVo.put("ass_depre_name", temp[1]);

				} else {

					err_sb.append("折旧方法名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					assDepreMethodDict.setAss_depre_define(temp[2]);

					mapVo.put("ass_depre_define", temp[2]);

				} else {

					err_sb.append("折旧方法描述为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					assDepreMethodDict.setIs_stop(Integer.valueOf(temp[3]));

					mapVo.put("is_stop", temp[3]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssDepreMethodDict data_exc_extis = assDepreMethodDictService.queryAssDepreMethodDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assDepreMethodDict.setError_type(err_sb.toString());

					list_err.add(assDepreMethodDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_depre_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_depre_name").toString()));

					try {

						String dataJson = assDepreMethodDictService.addAssDepreMethodDict(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssDepreMethodDict data_exc = new AssDepreMethodDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050106 资产折旧方法字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assdepremethoddict/addBatchAssDepreMethodDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssDepreMethodDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssDepreMethodDict> list_err = new ArrayList<AssDepreMethodDict>();

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

				AssDepreMethodDict assDepreMethodDict = new AssDepreMethodDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("ass_depre_code"))) {

					assDepreMethodDict.setAss_depre_code((String) jsonObj.get("ass_depre_code"));

					mapVo.put("ass_depre_code", jsonObj.get("ass_depre_code"));

				} else {

					err_sb.append("折旧方法编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_depre_name"))) {

					assDepreMethodDict.setAss_depre_name((String) jsonObj.get("ass_depre_name"));

					mapVo.put("ass_depre_name", jsonObj.get("ass_depre_name"));

				} else {

					err_sb.append("折旧方法名称为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_depre_define"))) {

					assDepreMethodDict.setAss_depre_define((String) jsonObj.get("ass_depre_define"));

					mapVo.put("ass_depre_define", jsonObj.get("ass_depre_define"));

				} else {

					err_sb.append("折旧方法描述为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assDepreMethodDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssDepreMethodDict data_exc_extis = assDepreMethodDictService.queryAssDepreMethodDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assDepreMethodDict.setError_type(err_sb.toString());

					list_err.add(assDepreMethodDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_depre_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_depre_name").toString()));

					try {

						String dataJson = assDepreMethodDictService.addAssDepreMethodDict(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssDepreMethodDict data_exc = new AssDepreMethodDict();

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
