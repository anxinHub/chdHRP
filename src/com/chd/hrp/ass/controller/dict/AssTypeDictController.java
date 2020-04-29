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
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssTypeDictService;

/**
 * @Description: 050101 资产分类字典
 * @Table: ASS_TYPE_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssTypeDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssTypeDictController.class);

	// 引入Service服务
	@Resource(name = "assTypeDictService")
	private final AssTypeDictService assTypeDictService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/assTypeDictMainPage", method = RequestMethod.GET)
	public String assTypeDictMainPage(Model mode) throws Exception {
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_TYPE_DICT");
		mapVo.put("mod_code", "05");
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String rules_view = Strings.removeFirst(rules_v);
		int first_length = 0;
		if (level_length != null) {
			// 获取第一级长度
			first_length = (Integer) level_length.get(1);

		}
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);

		return "hrp/ass/asstypedict/assTypeDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/assTypeDictAddPage", method = RequestMethod.GET)
	public String assTypeDictAddPage(Model mode) throws Exception {
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_TYPE_DICT");
		mapVo.put("mod_code", "05");
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String rules_view = Strings.removeFirst(rules_v);
		int first_length = 0;
		if (level_length != null) {
			// 获取第一级长度
			first_length = (Integer) level_length.get(1);

		}
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);

		return "hrp/ass/asstypedict/assTypeDictAdd";

	}

	/**
	 * @Description 添加数据 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/addAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String assTypeDictJson = "";
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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_type_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_type_name").toString()));
		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_TYPE_DICT");
		mapVo.put("mod_code", "05");

		String ass_type_code = mapVo.get("ass_type_code").toString();

		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		if (null != mapVo.get("ass_type_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
			Object level = level_map.get(ass_type_code.length());

			String rules_v = rules.get("rules_view").toString();
			String s_view = Strings.removeFirst(rules_v);

			// 当第一级为0时 不验证规则
			if (!rules_level_length.get(1).toString().equals("0")) {

				if (null != level) {
					int int_level = (Integer) level;
					mapVo.put("type_level", level);
					if (int_level == 1) {
						mapVo.put("type_level", level);
						mapVo.put("super_code", "");
					} else {
						mapVo.put("type_level", level);
						int v_level = int_level - 1;
						int end = (Integer) rules_level_length.get(v_level);
						mapVo.put("super_code", ass_type_code.substring(0, end));
					}
				} else {
					assTypeDictJson = "{\"error\":\"编码不符合要求,请重新添加.编码规则：" + s_view + "\"}";
					return JSONObject.parseObject(assTypeDictJson);
				}
			}

		}

		try {
			assTypeDictJson = assTypeDictService.addAssTypeDict(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assTypeDictJson);

	}

	/**
	 * 获取一级性质编码 添加页面填充使用 zhaon
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/getFirstNaturs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getFirstNaturs(@RequestParam Map<String, Object> mapVo) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Integer first_length = Integer.parseInt(mapVo.get("first_length").toString());
		mapVo.put("ass_type_code", mapVo.get("ass_type_code").toString().substring(0, first_length));

		AssTypeDict assTypeDict = assTypeDictService.queryAssTypeDictByCode(mapVo);

		if (assTypeDict != null && !"".equals(assTypeDict.getAss_naturs())) {
			return JSONObject
					.parseObject("{\"naturs_code\":\"" + assTypeDict.getAss_naturs() + "\",\"state\":\"true\"}");
		}

		return JSONObject.parseObject("{\"naturs_code\":\"\",\"state\":\"true\"}");
	}

	/**
	 * @Description 更新跳转页面 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/assTypeDictUpdatePage", method = RequestMethod.GET)
	public String assTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssTypeDict assTypeDict = new AssTypeDict();

		assTypeDict = assTypeDictService.queryAssTypeDictByCode(mapVo);

		mode.addAttribute("group_id", assTypeDict.getGroup_id());
		mode.addAttribute("hos_id", assTypeDict.getHos_id());
		mode.addAttribute("copy_code", assTypeDict.getCopy_code());
		mode.addAttribute("ass_type_id", assTypeDict.getAss_type_id());
		mode.addAttribute("ass_type_code", assTypeDict.getAss_type_code());
		mode.addAttribute("ass_type_name", assTypeDict.getAss_type_name());
		mode.addAttribute("super_code", assTypeDict.getSuper_code());

		mode.addAttribute("spell_code", assTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", assTypeDict.getWbx_code());
		mode.addAttribute("is_last", assTypeDict.getIs_last());
		mode.addAttribute("type_level", assTypeDict.getType_level());
		mode.addAttribute("ass_naturs", assTypeDict.getAss_naturs());
		mode.addAttribute("is_stop", assTypeDict.getIs_stop());
		mode.addAttribute("manage_depre_amount", assTypeDict.getManage_depre_amount());
		// 是否停用字段，当分类被引用时，不允许修改，是否末级分类字段， 当分类被引用时，不允许修改。zhaon
		String ass_type_id = mapVo.get("ass_type_id").toString();
		Boolean isDisabled = false;
		// Boolean lastIsDisabled = false;
		String str = assBaseService.isExistsDataByTable("ass_type_dict", ass_type_id);
		if (Strings.isNotBlank(str)) {
			isDisabled = true;
		}
		// 如果是末级 不允许修改 zhaon
		/*
		 * mapVo.put("ass_type_code", assTypeDict.getAcc_type_code());
		 * List<AssTypeDict> child =
		 * assTypeDictService.queryAssTypeDiceChild(mapVo); if (child ==null ||
		 * child.size()==0) { lastIsDisabled = true; }
		 */

		mode.addAttribute("isDisabled", isDisabled);
		// mode.addAttribute("lastIsDisabled",lastIsDisabled);

		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_TYPE_DICT");
		mapVo.put("mod_code", "05");
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String rules_view = Strings.removeFirst(rules_v);
		int first_length = 0;
		if (level_length != null) {
			// 获取第一级长度
			first_length = (Integer) level_length.get(1);

		}
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);
		return "hrp/ass/asstypedict/assTypeDictUpdate";

	}

	/**
	 * @Description 更新数据 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/updateAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assTypeDictJson = "";

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

		// 存在下级分类时，不允许修改 zhaon
		String retErrot;
		List<AssTypeDict> child = assTypeDictService.queryAssTypeDiceChild(mapVo);
		if (child != null && child.size() > 0) {
			retErrot = "{\"error\":\"存在下级分类，不允许修改\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_type_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_type_name").toString()));
		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_TYPE_DICT");
		mapVo.put("mod_code", "05");

		String ass_type_code = mapVo.get("ass_type_code").toString();

		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		if (null != mapVo.get("ass_type_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
			Object level = level_map.get(ass_type_code.length());

			String rules_v = rules.get("rules_view").toString();
			String s_view = Strings.removeFirst(rules_v);

			// 当第一级为0时 不验证规则
			if (!level_length.get(1).toString().equals("0")) {

				if (null != level) {
					int int_level = (Integer) level;
					mapVo.put("type_level", level);
					mapVo.put("super_code", "");
					if (int_level == 1) {
						mapVo.put("type_level", level);
						mapVo.put("super_code", "");
					} else {
						mapVo.put("type_level", level);
						int v_level = int_level - 1;
						int end = (Integer) level_length.get(v_level);
						mapVo.put("super_code", ass_type_code.substring(0, end));
					}
				}
			}

		}
		try {

			assTypeDictJson = assTypeDictService.updateAssTypeDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assTypeDictJson);
	}

	/**
	 * 是否停用字段，当分类被引用时，不允许修改，是否末级分类字段， 当分类被引用时，不允许修改。zhaon
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/updateCheckReference", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCheckReference(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String ass_type_id = mapVo.get("ass_type_id").toString();
		String str = assBaseService.isExistsDataByTable("ass_type_dict", ass_type_id);
		if (Strings.isNotBlank(str)) {
			String retErrot = "{\"error\":\"无法更改，资产类别被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		return JSONObject.parseObject("{\"state\":\"true\"}");
	}

	/**
	 * @Description 删除数据 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/deleteAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTypeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assTypeDictJson = "";
		String retErrot = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 判断是否被其他表引用
			String str = assBaseService.isExistsDataByTable("ass_type_dict", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的资产类别被以下业务使用：【" + str.substring(0, str.length() - 1)
						+ "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			// 判断是否被子分类引用
			String str_sup = assBaseService.isExistsDataByTable("ass_type_dict", ids[3], "1");

			if (Strings.isNotBlank(str_sup)) {
				retErrot = "{\"error\":\"删除失败，选择的资产类别被以下业务使用：【" + str_sup.substring(0, str_sup.length() - 1)
						+ "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_type_id", ids[3]);
			List<AssTypeDict> sta = assTypeDictService.querycount(mapVo);
			if (sta.size() > 0) {
				retErrot = "{\"error\":\"删除失败!请先删除下级\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			try {
				assTypeDictJson = assTypeDictService.deleteAssTypeDict(mapVo);
				// listVo.add(mapVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		// String assTypeDictJson =
		// assTypeDictService.deleteBatchAssTypeDict(listVo);

		return JSONObject.parseObject(assTypeDictJson);

	}

	/**
	 * @Description 查询数据 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/queryAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String assTypeDict = assTypeDictService.queryAssTypeDict(getPage(mapVo));

		return JSONObject.parseObject(assTypeDict);

	}

	/**
	 * @Description 下载导入模版 050101 资产分类字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/asstypedict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "资产分类字典.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050101 资产分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/assTypeDictImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String readAssTypeDictFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("user_id", SessionManager.getUserId());

			String reJson = assTypeDictService.readAssTypeDictFiles(mapVo);

			return reJson;

		} catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}
	}

	/**
	 * @Description 批量添加数据 050101 资产分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/addBatchAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssTypeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		Map<String, Object> is_last_map = new HashMap<String, Object>();
		is_last_map.put("是", "1");
		is_last_map.put("否", "0");
		is_last_map.put("1", "1");
		is_last_map.put("0", "0");
		Map<String, Object> is_naturs_map = new HashMap<String, Object>();
		is_naturs_map.put("房屋及建筑物", "01");
		is_naturs_map.put("专用设备", "02");
		is_naturs_map.put("一般设备", "03");
		is_naturs_map.put("其他固定资产", "04");
		is_naturs_map.put("01", "01");
		is_naturs_map.put("02", "02");
		is_naturs_map.put("03", "03");
		is_naturs_map.put("04", "04");
		List<AssTypeDict> list_err = new ArrayList<AssTypeDict>();

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

				AssTypeDict assTypeDict = new AssTypeDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("ass_type_code"))) {

					assTypeDict.setAss_type_code((String) jsonObj.get("ass_type_code"));

					mapVo.put("ass_type_code", jsonObj.get("ass_type_code"));

				} else {

					err_sb.append("分类编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_type_name"))) {

					assTypeDict.setAss_type_name((String) jsonObj.get("ass_type_name"));

					mapVo.put("ass_type_name", jsonObj.get("ass_type_name"));

				} else {

					err_sb.append("分类名称为空  ");

				}

				if (StringTool.isNotBlank(is_last_map.get(jsonObj.get("is_last").toString()))) {

					assTypeDict
							.setIs_last(Integer.valueOf(is_last_map.get(jsonObj.get("is_last").toString()).toString()));

					mapVo.put("is_last", jsonObj.get("is_last"));

				} else {

					err_sb.append("是否末级分类为空或者不符合要求  ");

				}

				if (StringTool.isNotBlank(is_naturs_map.get(jsonObj.get("ass_naturs").toString()))) {

					assTypeDict.setAss_naturs(is_naturs_map.get(jsonObj.get("ass_naturs").toString()).toString());

					mapVo.put("ass_naturs", jsonObj.get("ass_naturs"));

				} else {

					err_sb.append("性质编码为空  ");

				}
				if (StringTool.isNotBlank(is_last_map.get(jsonObj.get("is_stop").toString()))) {

					assTypeDict
							.setIs_stop(Integer.valueOf(is_last_map.get(jsonObj.get("is_stop").toString()).toString()));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}

				// 添加编码规则判断
				mapVo.put("proj_code", "ASS_TYPE_DICT");
				mapVo.put("mod_code", "05");

				String ass_type_code = mapVo.get("ass_type_code").toString();

				Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
				if (null != mapVo.get("ass_type_code")) {
					Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
					Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
					Object level = level_map.get(ass_type_code.length());

					String rules_v = rules.get("rules_view").toString();
					String s_view = Strings.removeFirst(rules_v);
					if (null != level) {
						int int_level = (Integer) level;
						mapVo.put("type_level", level);
						mapVo.put("super_code", "");
						if (int_level == 1) {
							mapVo.put("type_level", level);
							mapVo.put("super_code", "");
						} else {
							mapVo.put("type_level", level);
							int v_level = int_level - 1;
							int end = (Integer) level_length.get(v_level);
							mapVo.put("super_code", ass_type_code.substring(0, end));
						}
					}

				}

				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo.get("group_id"));
				map_code.put("hos_id", mapVo.get("hos_id"));
				map_code.put("copy_code", mapVo.get("copy_code"));
				map_code.put("ass_type_code", mapVo.get("ass_type_code"));

				AssTypeDict data_exc_extis_code = assTypeDictService.queryAssTypeDictByUniqueness(map_code);

				if (data_exc_extis_code != null) {

					err_sb.append("编码已经存在！ ");

				}
				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo.get("group_id"));
				map_name.put("hos_id", mapVo.get("hos_id"));
				map_name.put("copy_code", mapVo.get("copy_code"));
				map_name.put("ass_type_code", mapVo.get("ass_type_code"));

				AssTypeDict data_exc_extis_name = assTypeDictService.queryAssTypeDictByUniqueness(map_name);

				if (data_exc_extis_name != null) {

					err_sb.append("名称已经存在！ ");

				}

				// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
				if (StringTool.isNotBlank(mapVo.get("super_code"))) {
					Map<String, Object> map_super = new HashMap<String, Object>();
					map_super.put("group_id", mapVo.get("group_id"));
					map_super.put("hos_id", mapVo.get("hos_id"));
					map_super.put("copy_code", mapVo.get("copy_code"));
					map_super.put("ass_type_code", mapVo.get("super_code"));

					AssTypeDict data_exc_extis_super = assTypeDictService.queryAssTypeDictByUniqueness(map_super);

					if (null != data_exc_extis_super) {
						mapVo.put("ass_naturs", data_exc_extis_super.getAss_naturs());

						// 判断上级编码是否为末级
						if (data_exc_extis_super.getIs_last() == 1) {
							Map<String, Object> update_is_last = new HashMap<String, Object>();
							update_is_last.put("group_id", mapVo.get("group_id"));
							update_is_last.put("hos_id", mapVo.get("hos_id"));
							update_is_last.put("copy_code", mapVo.get("copy_code"));
							update_is_last.put("ass_type_id", data_exc_extis_super.getAss_type_id());
							update_is_last.put("is_last", "0");
							update_is_last.put("super_code", data_exc_extis_super.getSuper_code());

							assTypeDictService.updateAssTypeDict(update_is_last);
						}
					} else {
						err_sb.append("上级编码不存在或者！ ");
					}
				}

				if (err_sb.toString().length() > 0) {

					assTypeDict.setError_type(err_sb.toString());

					list_err.add(assTypeDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_type_name").toString()));

					try {

						String dataJson = assTypeDictService.addAssTypeDict(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

					}
				}

			}

		} catch (DataAccessException e) {

			AssTypeDict data_exc = new AssTypeDict();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

	/**
	 * @Description 查询数据 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asstypedict/queryAssTypeDictByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTypeDictByTree(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<?> l_map = assTypeDictService.queryAssTypeDictByTree(mapVo);
		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}
}
