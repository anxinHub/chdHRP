/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.cost.entity.CostDeptNature;
import com.chd.hrp.cost.entity.CostDeptTypeDict;
import com.chd.hrp.cost.entity.CostItemDict;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.serviceImpl.CostDeptNatureServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostDeptTypeDictServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostItemDictNoServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostItemDictServiceImpl;

/**
 * @Title. @Description. 成本项目字典
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostItemDictController extends BaseController {

	private static Logger logger = Logger.getLogger(CostItemDictController.class);

	@Resource(name = "costItemDictService")
	private final CostItemDictServiceImpl costItemDictService = null;
	
	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoServiceImpl costItemDictNoService = null;

	@Resource(name = "costDeptTypeDictService")
	private final CostDeptTypeDictServiceImpl costDeptTypeDictService = null;

	@Resource(name = "costDeptNatureService")
	private final CostDeptNatureServiceImpl costDeptNatureService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * 成本项目字典<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costitemdict/costItemDictMainPage", method = RequestMethod.GET)
	public String costItemDictMainPage(Model mode) throws Exception {
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
		mapVo.put("proj_code", "COST_ITEM_DICT");
		mapVo.put("mod_code", "03");
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
		return "hrp/cost/costitemdict/costItemDictMain";

	}

	/**
	 * 成本项目字典<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costitemdict/costItemDictAddPage", method = RequestMethod.GET)
	public String costItemDictAddPage(Model mode) throws Exception {
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
		mapVo.put("proj_code", "COST_ITEM_DICT");
		mapVo.put("mod_code", "03");
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
		return "hrp/cost/costitemdict/costItemDictAdd";

	}

	@RequestMapping(value = "/hrp/cost/costitemdict/costItemDictChangePage", method = RequestMethod.GET)
	public String costItemDictChangePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		CostItemDict costItemDict = costItemDictService.queryCostItemDictByCode(mapVo);
		mode.addAttribute("group_id", costItemDict.getGroup_id());
		mode.addAttribute("hos_id", costItemDict.getHos_id());
		mode.addAttribute("copy_code", costItemDict.getCopy_code());
		mode.addAttribute("cost_type_id", costItemDict.getCost_type_id());
		mode.addAttribute("cost_type_name", costItemDict.getCost_type_name());
		mode.addAttribute("cost_item_id", costItemDict.getCost_item_id());
		mode.addAttribute("cost_item_code", costItemDict.getCost_item_code());
		mode.addAttribute("cost_item_name", costItemDict.getCost_item_name());
		mode.addAttribute("supp_item_code", costItemDict.getSupp_item_code());
		mode.addAttribute("supp_item_name", costItemDict.getSupp_item_name());
		mode.addAttribute("nature_id", costItemDict.getNature_id());
		mode.addAttribute("nature_code", costItemDict.getNature_id());
		mode.addAttribute("nature_name", costItemDict.getNature_name());
		mode.addAttribute("item_grade", costItemDict.getItem_grade());
		mode.addAttribute("is_last", costItemDict.getIs_last());
		mode.addAttribute("is_stop", costItemDict.getIs_stop());
		mode.addAttribute("spell_code", costItemDict.getSpell_code());
		mode.addAttribute("wbx_code", costItemDict.getWbx_code());
		mode.addAttribute("busi_data_source", costItemDict.getBusi_data_source());
		mode.addAttribute("para_type_code", costItemDict.getPara_type_code());
		return "hrp/cost/costitemdict/costItemDictChange";
	}

	/**
	 * 成本项目字典<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costitemdict/addCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costItemDictJson = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		// 根据名称生成拼音码
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_item_name").toString()));
		// 根据名称生成五笔码
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_item_name").toString()));

		// 添加编码规则判断
		mapVo.put("proj_code", "COST_ITEM_DICT");
		mapVo.put("mod_code", "03");

		String cost_item_code = mapVo.get("cost_item_code").toString();

		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		if (null != mapVo.get("cost_item_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
			Object level = level_map.get(cost_item_code.length());

			String rules_v = rules.get("rules_view").toString();
			String s_view = Strings.removeFirst(rules_v);
			if (null != level) {
				int int_level = (Integer) level;
				mapVo.put("item_grade", level);
				if (int_level == 1) {
					mapVo.put("item_grade", level);
					mapVo.put("supp_item_code", "0");
				} else {
					mapVo.put("item_grade", level);
					int v_level = int_level - 1;
					int end = (Integer) rules_level_length.get(v_level);
					mapVo.put("supp_item_code", cost_item_code.substring(0, end));
				}
			} else {
				costItemDictJson = "{\"error\":\"编码不符合要求,请重新添加.编码规则：" + s_view + "\"}";
				return JSONObject.parseObject(costItemDictJson);
			}

		}

		costItemDictJson = costItemDictService.addCostItemDict(mapVo);

		return JSONObject.parseObject(costItemDictJson);

	}

	/**
	 * 成本项目字典<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costitemdict/queryCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode()); 
		}
		// String para_value = (String)
		// ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
	/*	String para_value = String.valueOf((SessionManager.getCostParaMap().get("03001") == null) ? 0 : SessionManager.getCostParaMap().get("03001")
		        .toString());
		mapVo.put("is_flag", para_value);*/

		String costItemDict = costItemDictService.queryCostItemDict(getPage(mapVo));

		return JSONObject.parseObject(costItemDict);

	}

	/**
	 * 成本项目字典<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costitemdict/deleteCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot="";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_item_dict", ids[4]);
			if(Strings.isNotBlank(str)){
				retErrot = "{\"error\":\"删除失败，选择的成本项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("cost_type_id", ids[3]);
			mapVo.put("cost_item_id", ids[4]);
			listVo.add(mapVo);
		}
		String costItemDictJson = costItemDictService.deleteBatchCostItemDict(listVo);
		return JSONObject.parseObject(costItemDictJson);

	}

	/**
	 * 成本项目字典<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costitemdict/costItemDictUpdatePage", method = RequestMethod.GET)
	public String costItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());
		
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		CostItemDictNo costItemNoDict = costItemDictNoService.queryCostItemDictNoByCode(mapVo);
		
		mode.addAttribute("group_id", costItemNoDict.getGroup_id()); 
		mode.addAttribute("hos_id", costItemNoDict.getHos_id());
		mode.addAttribute("copy_code", costItemNoDict.getCopy_code());
		mode.addAttribute("cost_type_id", costItemNoDict.getCost_type_id());
		mode.addAttribute("cost_type_name", costItemNoDict.getCost_type_name());
		mode.addAttribute("cost_item_id", costItemNoDict.getCost_item_id());
		mode.addAttribute("cost_item_no", costItemNoDict.getCost_item_no());
		mode.addAttribute("cost_item_code", costItemNoDict.getCost_item_code());
		mode.addAttribute("cost_item_name", costItemNoDict.getCost_item_name());
		mode.addAttribute("supp_item_code", costItemNoDict.getSupp_item_code());
		mode.addAttribute("supp_item_name", costItemNoDict.getSupp_item_name());
		mode.addAttribute("nature_id", costItemNoDict.getNature_id());
		mode.addAttribute("nature_name", costItemNoDict.getNature_name());
		mode.addAttribute("busi_data_source", costItemNoDict.getBusi_data_source());
		mode.addAttribute("busi_data_source_name", costItemNoDict.getBusi_data_source_name());
		mode.addAttribute("item_grade", costItemNoDict.getItem_grade());
		mode.addAttribute("is_last", costItemNoDict.getIs_last());
		mode.addAttribute("is_stop", costItemNoDict.getIs_stop());
		mode.addAttribute("spell_code", costItemNoDict.getSpell_code());
		mode.addAttribute("wbx_code", costItemNoDict.getWbx_code());
		mode.addAttribute("busi_data_source", costItemNoDict.getBusi_data_source());
		mode.addAttribute("para_type_code", costItemNoDict.getPara_type_code());
		mode.addAttribute("tree_code", mapVo.get("tree_code"));
		return "hrp/cost/costitemdict/costItemDictUpdate";
	}

	/**
	 * 成本项目字典<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costitemdict/updateCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costItemDictJson = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		// 根据名称生成拼音码
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_item_name").toString()));
		// 根据名称生成五笔码
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_item_name").toString()));

		// 添加编码规则判断
		mapVo.put("proj_code", "COST_ITEM_DICT");
		mapVo.put("mod_code", "03");

		String cost_item_code = mapVo.get("cost_item_code").toString();

		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		if (null != mapVo.get("cost_item_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
			Object level = level_map.get(cost_item_code.length());

			String rules_v = rules.get("rules_view").toString();
			String s_view = Strings.removeFirst(rules_v);
			if (null != level) {
				int int_level = (Integer) level;
				mapVo.put("item_grade", level);
				if (int_level == 1) {
					mapVo.put("item_grade", level);
					mapVo.put("supp_item_code", "0");
				} else {
					mapVo.put("item_grade", level);
					int v_level = int_level - 1;
					int end = (Integer) rules_level_length.get(v_level);
					mapVo.put("supp_item_code", cost_item_code.substring(0, end));
				}
			} else {
				costItemDictJson = "{\"error\":\"编码不符合要求,请重新添加.编码规则：" + s_view + "\"}";
				return JSONObject.parseObject(costItemDictJson);
			}

		}

		costItemDictJson = costItemDictService.updateCostItemDict(mapVo);

		return JSONObject.parseObject(costItemDictJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costitemdict/costItemDictImportPage", method = RequestMethod.GET)
	public String costItemDictImportPage(Model mode) throws Exception {

		return "hrp/cost/costitemdict/costItemDictImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costitemdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "cost\\基础设置", "成本项目.xls");
		return null;
	}

	/**
	 * 成本项目字典<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costitemdict/readCostItemDictFiles", method = RequestMethod.POST)
	public String readCostItemDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		// 添加编码规则判断
		map.put("proj_code", "COST_ITEM_DICT");
		map.put("mod_code", "03");

		Map<Object, Object> rules = assBaseService.getHosRules(map);

		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");

		String rules_v = rules.get("rules_view").toString();
		String s_view = Strings.removeFirst(rules_v);
		Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
		Object level = null;
		List<CostItemDict> list_err = new ArrayList<CostItemDict>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
				CostItemDict costItemDict = new CostItemDict();
				String temp[] = list.get(i);// 行
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
				
				mapVo.put("is_last", "1");

				if (StringUtils.isNotEmpty(temp[0])) {
					mapVo.put("cost_type_code", temp[0]);
					CostDeptTypeDict deptTypeDict = costDeptTypeDictService.queryCostDeptTypeDictByTypeCode(mapVo);
					costItemDict.setCost_type_code(temp[0]);
					costItemDict.setCost_type_name(temp[1]);
					if (deptTypeDict != null) {
						mapVo.put("cost_type_name", deptTypeDict.getCost_type_name());
						mapVo.put("cost_type_id", deptTypeDict.getCost_type_id());
						
					} else {
						err_sb.append("成本类型不存在  ");
					}
				} else {
					err_sb.append("成本类型编码为空  ");
				}

				if (StringUtils.isNotEmpty(temp[2])) {
					costItemDict.setCost_item_code(temp[2]);
					mapVo.put("cost_item_code", temp[2]);
				} else {

					err_sb.append("成本项目编码为空  ");
				}
				if (StringUtils.isNotEmpty(temp[3])) {
					costItemDict.setCost_item_name(temp[3]);
					mapVo.put("cost_item_name", temp[3]);
				} else {
					err_sb.append("成本项目名称为空  ");
				}
				if (StringUtils.isNotEmpty(temp[4])) {
					mapVo.put("nature_code", temp[4]);
					CostDeptNature deptNature = costDeptNatureService.queryCostDeptNatureByCode(mapVo);
					costItemDict.setNature_code(temp[4]);
					costItemDict.setNature_name(temp[5]);
					if (deptNature != null) {
						mapVo.put("nature_id", deptNature.getNature_id());
					} else {
						err_sb.append("成本习性不存在  ");
					}
				} else {
					err_sb.append("成本习性编码为空  ");
				}
				if (StringUtils.isNotEmpty(temp[6])) {
					costItemDict.setBusi_data_source(temp[6]);
					mapVo.put("busi_data_source", temp[6]);
				} else {
					err_sb.append("成本项目来源编码为空  ");
				}

				if (StringUtils.isNotEmpty(temp[8])) {
					costItemDict.setPara_type_code(temp[8]);
					mapVo.put("para_type_code", temp[8]);
					
				} else {
					err_sb.append("分摊类型不能为空 ");
				}
				if (StringUtils.isNotEmpty(temp[10])) {
					if("0".equals(temp[10]) || "1".equals(temp[10]) || "是".equals(temp[10]) || "否".equals(temp[10])){
						costItemDict.setIs_last(Integer.getInteger(temp[10]));
						mapVo.put("is_stop", temp[10]);
					}else{
						err_sb.append("停用标志不符合要求： 0 1 是 否 ");
					}
					
				} else {
					err_sb.append("停用标志为空 ");
				}
				

				level = level_map.get(temp[2].length());
				if (null != level) {
					int int_level = (Integer) level;
					mapVo.put("item_grade", level);
					if (int_level == 1) {
						mapVo.put("item_grade", level);
						mapVo.put("supp_item_code", "0");
					} else {
						mapVo.put("item_grade", level);
						int v_level = int_level - 1;
						int end = (Integer) rules_level_length.get(v_level);
						mapVo.put("supp_item_code", temp[2].substring(0, end));
					}

				} else {
					err_sb.append("成本项目编码不符合编码规则" + s_view);
						costItemDict.setError_type(err_sb.toString());
						list_err.add(costItemDict);
					continue;
				}

				CostItemDict data_exc_extis = costItemDictService.queryCostItemDictByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
					costItemDict.setError_type(err_sb.toString());
					list_err.add(costItemDict);
					continue;
				}
				
				// 判断上级编码是否为空 不为空则反查上级编码所属分类
				if (!"0".equals(mapVo.get("supp_item_code").toString())) {
					Map<String, Object> map_super = new HashMap<String, Object>();
					map_super.put("group_id", mapVo.get("group_id"));
					map_super.put("hos_id", mapVo.get("hos_id"));
					map_super.put("copy_code", mapVo.get("copy_code"));
					map_super.put("cost_item_code", mapVo.get("supp_item_code"));

					CostItemDict data_exc_extis_super = costItemDictService.queryCostItemDictByCode(map_super);

					if (null != data_exc_extis_super) {
						mapVo.put("cost_type_id", data_exc_extis_super.getCost_type_id());
					} else {

						err_sb.append("上级编码不存在！ ");
						costItemDict.setError_type(err_sb.toString());
						list_err.add(costItemDict);
						continue;
					}
					// 判断上级编码是否为末级
					if (data_exc_extis_super.getIs_last() == 1) {
						Map<String, Object> update_is_last = new HashMap<String, Object>();
						update_is_last.put("group_id", mapVo.get("group_id"));
						update_is_last.put("hos_id", mapVo.get("hos_id"));
						update_is_last.put("copy_code", mapVo.get("copy_code"));
						update_is_last.put("cost_item_id", data_exc_extis_super.getCost_item_id());
						update_is_last.put("is_last", "0");
						update_is_last.put("is_stop", "0");
						costItemDictService.updateCostItemDictInput(update_is_last);
					}
				}
				
				if (err_sb.toString().length() > 0) {
					costItemDict.setError_type(err_sb.toString());
					list_err.add(costItemDict);
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_item_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_item_name").toString()));
					String dataJson = costItemDictService.addCostItemDictInput(mapVo);
				}
			}
		}
		catch (DataAccessException e) {
			CostItemDict data_exc = new CostItemDict();
			data_exc.setError_type("导入系统出错");
			list_err.add(data_exc);
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
	}

	/**
	 * 成本项目字典<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costitemdict/addBatchCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<CostItemDict> list_err = new ArrayList<CostItemDict>();
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
		String s = null;
		Iterator it = json.iterator();
		try {
			while (it.hasNext()) {
				StringBuffer err_sb = new StringBuffer();
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				mapVo.put("cost_type_code", jsonObj.get("cost_type_code"));
				mapVo.put("cost_type_name", jsonObj.get("cost_type_name"));
				CostDeptTypeDict deptTypeDict = costDeptTypeDictService.queryCostDeptTypeDictByTypeCode(mapVo);
				if (deptTypeDict != null) {
					mapVo.put("cost_type_id", deptTypeDict.getCost_type_id());
				} else {
					err_sb.append("成本分类不存在  ");
				}
				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));
				mapVo.put("cost_item_name", jsonObj.get("cost_item_name"));
				mapVo.put("supp_item_code", jsonObj.get("supp_item_code"));
				mapVo.put("nature_code", jsonObj.get("nature_code"));
				mapVo.put("nature_name", jsonObj.get("nature_name"));
				CostDeptNature deptNature = costDeptNatureService.queryCostDeptNatureByCode(mapVo);
				if (deptNature != null) {
					mapVo.put("nature_id", deptNature.getNature_id());
				} else {
					err_sb.append("成本习性不存在  ");
				}
				mapVo.put("busi_data_source", jsonObj.get("busi_data_source"));
				// mapVo.put("busi_data_source_name",
				// jsonObj.get("busi_data_source_name"));
				mapVo.put("item_grade", jsonObj.get("item_grade"));
				mapVo.put("is_last", jsonObj.get("is_last"));
				mapVo.put("is_stop", jsonObj.get("is_stop"));
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_item_name").toString()));
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_item_name").toString()));
				CostItemDict data_exc_extis = costItemDictService.queryCostItemDictByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				CostItemDict costItemDict = new CostItemDict();
				if (err_sb.toString().length() > 0) {
					costItemDict.setCost_type_code(mapVo.get("cost_type_code").toString());
					costItemDict.setCost_type_name(mapVo.get("cost_type_name").toString());
					costItemDict.setCost_item_code(mapVo.get("cost_item_code").toString());
					costItemDict.setCost_item_name(mapVo.get("cost_item_name").toString());
					costItemDict.setSupp_item_code(mapVo.get("supp_item_code").toString());
					costItemDict.setNature_code(mapVo.get("nature_code").toString());
					costItemDict.setNature_name(mapVo.get("nature_name").toString());
					costItemDict.setBusi_data_source(mapVo.get("busi_data_source").toString());
					// costItemDict.setBusi_data_source_name(mapVo.get("busi_data_source_name").toString());
					costItemDict.setItem_grade(Integer.getInteger(mapVo.get("item_grade").toString()));
					costItemDict.setItem_grade(Integer.getInteger("item_grade"));
					costItemDict.setIs_last(Integer.getInteger(mapVo.get("is_last").toString()));
					costItemDict.setIs_stop(Integer.getInteger(mapVo.get("is_stop").toString()));
					costItemDict.setError_type(err_sb.toString());
					list_err.add(costItemDict);
				} else {
					costItemDictService.addCostItemDict(mapVo);
				}
			}
		}
		catch (DataAccessException e) {
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));
		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
	}

	/**
	 * @Description 查询数据部门字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costitemdict/queryCostItemDictByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostItemDictByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		List<?> l_map = costItemDictService.queryCostItemDictByTree(mapVo);
		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}
	
	/**
	 * 成本项目字典<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costitemdict/costItemDictBatchPage", method = RequestMethod.GET)
	public String costItemDictBatchPage(Model mode) throws Exception {
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
		return "hrp/cost/costitemdict/costItemDictBatch";

	}
	
	/**
	 * 成本项目字典<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costitemdict/updateCostItemBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostItemBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costItemDictJson = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		costItemDictJson = costItemDictService.updateCostItemBatch(mapVo);

		return JSONObject.parseObject(costItemDictJson);
	}
}
