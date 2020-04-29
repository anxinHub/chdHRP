/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.controller.base.budgcostfasset;

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
import com.chd.hrp.budg.service.base.budgcostfasset.BudgCostFassetTypeService;

/**
 * @Description: 050101 资产分类字典
 * @Table: ASS_TYPE_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class BudgCostFassetTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgCostFassetTypeController.class);

	// 引入Service服务
	@Resource(name = "budgCostFassetTypeService")
	private final BudgCostFassetTypeService budgCostFassetTypeService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/budgCostFassetTypeMainPage", method = RequestMethod.GET)
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
		// 获取第一级长度
		int first_length = (Integer) level_length.get(1);
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);
		return "hrp/budg/base/budgcostfasset/costfassettype/budgCostFassetTypeMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/budgCostFassetTypeAddPage", method = RequestMethod.GET)
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
		// 获取第一级长度
		int first_length = (Integer) level_length.get(1);
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);

		return "hrp/budg/base/budgcostfasset/costfassettype/budgCostFassetTypeAdd";

	}

	/**
	 * @Description 添加数据 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/addBudgCostFassetType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgCostFassetType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

		assTypeDictJson = budgCostFassetTypeService.add(mapVo);

		return JSONObject.parseObject(assTypeDictJson);

	}

	/**
	 * @Description 更新跳转页面 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/budgCostFassetTypeUpdatePage", method = RequestMethod.GET)
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

		assTypeDict = budgCostFassetTypeService.queryByCode(mapVo);

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

		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_TYPE_DICT");
		mapVo.put("mod_code", "05");
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String rules_view = Strings.removeFirst(rules_v);
		// 获取第一级长度
		int first_length = (Integer) level_length.get(1);
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);
		return "hrp/budg/base/budgcostfasset/costfassettype/budgCostFassetTypeUpdate";

	}

	/**
	 * @Description 更新数据 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/updateBudgCostFassetType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCostFassetType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String assTypeDictJson = budgCostFassetTypeService.update(mapVo);

		return JSONObject.parseObject(assTypeDictJson);
	}

	/**
	 * @Description 删除数据 050101 资产分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/deleteBudgCostFassetType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgCostFassetType(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assTypeDictJson = "";
		String retErrot = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
            //判断是否被其他表引用
			String str = assBaseService.isExistsDataByTable("ass_type_dict", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的资产类别被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			//判断是否被子分类引用
			String str_sup = assBaseService.isExistsDataByTable("ass_type_dict", ids[3],"1");

			if (Strings.isNotBlank(str_sup)) {
				retErrot = "{\"error\":\"删除失败，选择的资产类别被以下业务使用：【" + str_sup.substring(0, str_sup.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_type_id", ids[3]);

			assTypeDictJson = budgCostFassetTypeService.delete(mapVo);
			// listVo.add(mapVo);

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
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/queryBudgCostFassetType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCostFassetType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String assTypeDict = budgCostFassetTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(assTypeDict);

	}

	/**
	 * @Description 导入跳转页面 050101 资产分类字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/budgCostFassetTypeImportPage", method = RequestMethod.GET)
	public String assTypeDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcostfasset/costfassettype/budgCostFassetTypeImport";

	}

	/**
	 * @Description 下载导入模版 050101 资产分类字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/base/budgcostfasset/costfassettype/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

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
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/readAssTypeDictFiles", method = RequestMethod.POST)
	public String readAssTypeDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

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

		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssTypeDict assTypeDict = new AssTypeDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					
					if(temp[0].equals(error[0])){
						err_sb.append("第"+i+"行数据与第 "+j+"行分类编码相同;");
					}
					
					if(temp[1].equals(error[1])){
						err_sb.append("第"+i+"行数据与第 "+j+"行分类名称相同;");
					}
				} 

				if (StringTool.isNotBlank(temp[0])) {

					assTypeDict.setAss_type_code(temp[0]);

					mapVo.put("ass_type_code", temp[0]);

				} else {

					err_sb.append("分类编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assTypeDict.setAss_type_name(temp[1]);

					mapVo.put("ass_type_name", temp[1]);

				} else {

					err_sb.append("分类名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					assTypeDict.setIs_last(Integer.valueOf(is_last_map.get(temp[2].toString()).toString()));
					if (StringTool.isNotBlank(is_last_map.get(temp[2].toString()))) {
						mapVo.put("is_last", is_last_map.get(temp[2].toString()));
					} else {
						err_sb.append("是否末级分类编码不符合要求 是或者否  0或者1 ");
					}

				} else {

					err_sb.append("是否末级分类为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {
					assTypeDict.setAss_naturs(temp[3]);
					if (StringTool.isNotBlank(is_naturs_map.get(temp[3].toString()))) {
						mapVo.put("ass_naturs", is_naturs_map.get(temp[3].toString()));
					} else {
						err_sb.append("性质编码不符合要求  ");
					}

				} else {

					err_sb.append("性质编码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assTypeDict.setIs_stop(Integer.valueOf(is_last_map.get(temp[4].toString()).toString()));

					if (StringTool.isNotBlank(is_last_map.get(temp[4].toString()))) {
						mapVo.put("is_stop", is_last_map.get(temp[4].toString()));
					} else {
						err_sb.append("是否停用不符合要求 是或者否  0或者1 ");
					}

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

				// 判断唯一性 编码

				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo.get("group_id"));
				map_code.put("hos_id", mapVo.get("hos_id"));
				map_code.put("copy_code", mapVo.get("copy_code"));
				map_code.put("ass_type_code", mapVo.get("ass_type_code"));

				AssTypeDict data_exc_extis_code = budgCostFassetTypeService.queryByUniqueness(map_code);

				if (data_exc_extis_code != null) {

					err_sb.append("编码已经存在！ ");

				}
				// 判断唯一性 编码

				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo.get("group_id"));
				map_name.put("hos_id", mapVo.get("hos_id"));
				map_name.put("copy_code", mapVo.get("copy_code"));
				map_name.put("ass_type_name", mapVo.get("ass_type_name"));

				AssTypeDict data_exc_extis_name = budgCostFassetTypeService.queryByUniqueness(map_name);

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

					AssTypeDict data_exc_extis_super = budgCostFassetTypeService.queryByUniqueness(map_super);

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
							budgCostFassetTypeService.update(update_is_last);
						}
					} else {
						err_sb.append("上级编码不存在！ ");
					}
				}

				if (err_sb.toString().length() > 0) {

					assTypeDict.setError_type(err_sb.toString());

					list_err.add(assTypeDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_type_name").toString()));

					addList.add(mapVo);

				}

			}
			if(list_err.size() == 0){
				String dataJson = budgCostFassetTypeService.addBatch(addList);
			}
		}
		catch (DataAccessException e) {

			AssTypeDict data_exc = new AssTypeDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

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
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/addBatchAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssTypeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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

					assTypeDict.setIs_last(Integer.valueOf(is_last_map.get(jsonObj.get("is_last").toString()).toString()));

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

					assTypeDict.setIs_stop(Integer.valueOf(is_last_map.get(jsonObj.get("is_stop").toString()).toString()));

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

				AssTypeDict data_exc_extis_code = budgCostFassetTypeService.queryByUniqueness(map_code);

				if (data_exc_extis_code != null) {

					err_sb.append("编码已经存在！ ");

				}
				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo.get("group_id"));
				map_name.put("hos_id", mapVo.get("hos_id"));
				map_name.put("copy_code", mapVo.get("copy_code"));
				map_name.put("ass_type_code", mapVo.get("ass_type_code"));

				AssTypeDict data_exc_extis_name = budgCostFassetTypeService.queryByUniqueness(map_name);

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

					AssTypeDict data_exc_extis_super = budgCostFassetTypeService.queryByUniqueness(map_super);

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
							
							budgCostFassetTypeService.update(update_is_last);
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

					String dataJson = budgCostFassetTypeService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

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
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/costfassettype/queryAssTypeDictByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTypeDictByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		List<?> l_map = budgCostFassetTypeService.queryAssTypeDictByTree(mapVo);
		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}
}
