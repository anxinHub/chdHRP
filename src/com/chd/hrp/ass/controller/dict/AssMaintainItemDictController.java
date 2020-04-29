
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
import com.chd.hrp.ass.entity.dict.AssMaintainItemDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssMaintainItemDictService;

/**
 * 
 * @Description: 050108 保养项目字典
 * @Table: ASS_MAINTAIN_ITEM_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMaintainItemDictController extends BaseController { 

	private static Logger logger = Logger.getLogger(AssMaintainItemDictController.class);

	// 引入Service服务
	@Resource(name = "assMaintainItemDictService")
	private final AssMaintainItemDictService assMaintainItemDictService = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/assMaintainItemDictMainPage", method = RequestMethod.GET)
	public String assMaintainItemDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainitemdict/assMaintainItemDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/assMaintainItemDictAddPage", method = RequestMethod.GET)
	public String assMaintainItemDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainitemdict/assMaintainItemDictAdd";

	}

	/**
	 * @Description 添加数据 050108 保养项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/addAssMaintainItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMaintainItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMaintainItemDictJson = "";

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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("maintain_item_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("maintain_item_name").toString()));

		try {

			String retErrot = "";

			// 根据编号获取对象
			AssMaintainItemDict assMaintainItemDict = assMaintainItemDictService.queryAssMaintainItemDictByCode(mapVo);
			if (assMaintainItemDict != null) {

				retErrot = "{\"error\":\"编码：【" + assMaintainItemDict.getMaintain_item_code()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			/*
			// 根据名称获取对象
			AssMaintainItemDict assMaintainItemDictName = assMaintainItemDictService.queryByName(mapVo);
			if (assMaintainItemDictName != null) {

				retErrot = "{\"error\":\"名称：【" + assMaintainItemDictName.getMaintain_item_name()
						+ "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}*/

			assMaintainItemDictJson = assMaintainItemDictService.addAssMaintainItemDict(getPage(mapVo));

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assMaintainItemDictJson);

	}

	/**
	 * @Description 更新跳转页面 050108 保养项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/assMaintainItemDictUpdatePage", method = RequestMethod.GET)
	public String assMaintainItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssMaintainItemDict assMaintainItemDict = new AssMaintainItemDict();

		assMaintainItemDict = assMaintainItemDictService.queryAssMaintainItemDictByCode(mapVo);

		mode.addAttribute("group_id", assMaintainItemDict.getGroup_id());
		mode.addAttribute("hos_id", assMaintainItemDict.getHos_id());
		mode.addAttribute("ass_name", assMaintainItemDict.getAss_name());
		mode.addAttribute("copy_code", assMaintainItemDict.getCopy_code());
		mode.addAttribute("maintain_item_code", assMaintainItemDict.getMaintain_item_code());
		mode.addAttribute("maintain_item_name", assMaintainItemDict.getMaintain_item_name());
		mode.addAttribute("spell_code", assMaintainItemDict.getSpell_code());
		mode.addAttribute("wbx_code", assMaintainItemDict.getWbx_code());
		mode.addAttribute("is_stop", assMaintainItemDict.getIs_stop());
		mode.addAttribute("maintain_degree", assMaintainItemDict.getMaintain_degree());
		
		return "hrp/ass/assmaintainitemdict/assMaintainItemDictUpdate";
	}

	/**
	 * @Description 更新数据 050108 保养项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/updateAssMaintainItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMaintainItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMaintainItemDictJson = "";

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

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("maintain_item_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("maintain_item_name").toString()));

		try {

			assMaintainItemDictJson = assMaintainItemDictService.updateAssMaintainItemDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assMaintainItemDictJson);
	}

	/**
	 * @Description 删除数据 050108 保养项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/deleteAssMaintainItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMaintainItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assMaintainItemDictJson = "";
		String str = "";
		boolean falg = true;
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_MAINTAIN_ITEM_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_MAINTAIN_ITEM_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("maintain_item_code", ids[3]);

			listVo.add(mapVo);

		}
		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的 保养项目被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		try {

			assMaintainItemDictJson = assMaintainItemDictService.deleteBatchAssMaintainItemDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assMaintainItemDictJson);

	}

	/**
	 * @Description 查询数据 050108 保养项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/queryAssMaintainItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assMaintainItemDict = assMaintainItemDictService.queryAssMaintainItemDict(getPage(mapVo));

		return JSONObject.parseObject(assMaintainItemDict);

	}

	/**
	 * @Description 导入跳转页面 050108 保养项目字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/assMaintainItemDictImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assMaintainItemDictImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {

			String reJson = assMaintainItemDictService.readAssMaintainItemDictFiles(mapVo);

			return reJson;

		} catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}

	}

	/**
	 * @Description 下载导入模版 050108 保养项目字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assmaintainitemdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "保养项目字典.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050108 保养项目字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/readAssMaintainItemDictFiles", method = RequestMethod.POST)
	public String readAssMaintainItemDictFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssMaintainItemDict> list_err = new ArrayList<AssMaintainItemDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssMaintainItemDict assMaintainItemDict = new AssMaintainItemDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[0])) {

					assMaintainItemDict.setMaintain_item_code(temp[0]);

					mapVo.put("maintain_item_code", temp[0]);

				} else {

					err_sb.append("保养项目编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assMaintainItemDict.setMaintain_item_name(temp[1]);

					mapVo.put("maintain_item_name", temp[1]);

				} else {

					err_sb.append("保养项目名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					assMaintainItemDict.setIs_stop(Integer.valueOf(temp[2]));

					mapVo.put("is_stop", temp[2]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssMaintainItemDict data_exc_extis = assMaintainItemDictService.queryAssMaintainItemDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMaintainItemDict.setError_type(err_sb.toString());

					list_err.add(assMaintainItemDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("maintain_item_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("maintain_item_name").toString()));

					try {

						String dataJson = assMaintainItemDictService.addAssMaintainItemDict(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssMaintainItemDict data_exc = new AssMaintainItemDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050108 保养项目字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainitemdict/addBatchAssMaintainItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssMaintainItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssMaintainItemDict> list_err = new ArrayList<AssMaintainItemDict>();

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

				AssMaintainItemDict assMaintainItemDict = new AssMaintainItemDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("maintain_item_code"))) {

					assMaintainItemDict.setMaintain_item_code((String) jsonObj.get("maintain_item_code"));

					mapVo.put("maintain_item_code", jsonObj.get("maintain_item_code"));

				} else {

					err_sb.append("保养项目编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("maintain_item_name"))) {

					assMaintainItemDict.setMaintain_item_name((String) jsonObj.get("maintain_item_name"));

					mapVo.put("maintain_item_name", jsonObj.get("maintain_item_name"));

				} else {

					err_sb.append("保养项目名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assMaintainItemDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}

				AssMaintainItemDict data_exc_extis = assMaintainItemDictService.queryAssMaintainItemDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMaintainItemDict.setError_type(err_sb.toString());

					list_err.add(assMaintainItemDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("maintain_item_name").toString()));

					try {

						String dataJson = assMaintainItemDictService.addAssMaintainItemDict(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssMaintainItemDict data_exc = new AssMaintainItemDict();

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
