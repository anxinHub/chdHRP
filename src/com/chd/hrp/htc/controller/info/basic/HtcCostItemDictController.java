package com.chd.hrp.htc.controller.info.basic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.htc.entity.info.basic.HtcCostItemDict;
import com.chd.hrp.htc.service.info.basic.HtcCostItemDictService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcCostItemDictController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcCostItemDictController.class);
	
	@Resource(name = "htcCostItemDictService")
	private final HtcCostItemDictService htcCostItemDictService = null;
    
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/costitem/htcCostItemDictMainPage", method = RequestMethod.GET)
	public String htcCostItemDictMainPage(Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());
		// 添加编码规则判断
		mapVo.put("proj_code", "COST_ITEM_DICT");
		
		mapVo.put("mod_code", "03");
		
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		
		String rules_v = rules.get("rules_view").toString();
		
		String rules_view = Strings.removeFirst(rules_v);
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		
		return "hrp/htc/info/basic/costitem/htcCostItemDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/info/basic/costitem/htcCostItemDictAddPage", method = RequestMethod.GET)
	public String htcCostItemDictAddPage(Model mode) throws Exception {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

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
		
		return "hrp/htc/info/basic/costitem/htcCostItemDictAdd";

	}

	// 保存
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hrp/htc/info/basic/costitem/addHtcCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcCostItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String costItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_item_name").toString()));
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
        
        try {
			costItemDictJson = htcCostItemDictService.addHtcCostItemDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(costItemDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/info/basic/costitem/queryHtcCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcCostItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String costItemDict = htcCostItemDictService.queryHtcCostItemDict(getPage(mapVo));
		
		return JSONObject.parseObject(costItemDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/info/basic/costitem/deleteHtcCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcCostItemDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String costItemDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_item_dict", ids[4]);
			if(Strings.isNotBlank(str)){
				costItemDictJson = "{\"error\":\"删除失败，选择的成本项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(costItemDictJson);
			}
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("cost_type_id", ids[3]);
			mapVo.put("cost_item_id", ids[4]);
			listVo.add(mapVo);
		}
		costItemDictJson = htcCostItemDictService.deleteBatchHtcCostItemDict(listVo);
		return JSONObject.parseObject(costItemDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/costitem/htcCostItemDictUpdatePage", method = RequestMethod.GET)
	public String htcCostItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acct_year", SessionManager.getAcctYear());
        HtcCostItemDict costItemDict =  htcCostItemDictService.queryHtcCostItemDictByCode(mapVo);
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
		mode.addAttribute("nature_name", costItemDict.getNature_name());
		mode.addAttribute("busi_data_source", costItemDict.getBusi_data_source());
		mode.addAttribute("busi_data_source_name", costItemDict.getBusi_data_source_name());
		mode.addAttribute("item_grade", costItemDict.getItem_grade());
		mode.addAttribute("is_last", costItemDict.getIs_last());
		mode.addAttribute("is_stop", costItemDict.getIs_stop());
		mode.addAttribute("spell_code", costItemDict.getSpell_code());
		mode.addAttribute("wbx_code", costItemDict.getWbx_code());
		mode.addAttribute("busi_data_source", costItemDict.getBusi_data_source());
		mode.addAttribute("para_type_code", costItemDict.getPara_type_code());
		mode.addAttribute("tree_code", mapVo.get("tree_code"));
		return "hrp/htc/info/basic/costitem/htcCostItemDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htc/info/basic/costitem/updateHtcCostItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcCostItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String costItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
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
        
		try {
			costItemDictJson = htcCostItemDictService.updateHtcCostItemDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JSONObject.parseObject(costItemDictJson);
	}
	
	
	/**
	 * @Description 查询数据部门字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htc/info/basic/costitem/queryHtcCostItemDictByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcCostItemDictByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("acct_year", SessionManager.getAcctYear());

		  List<?> l_map = htcCostItemDictService.queryHtcCostItemDictByTree(mapVo);
		
		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}
	
    /**
	 * 成本项目字典<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "hrp/htc/info/basic/costitem/htcCostItemDictBatchPage", method = RequestMethod.GET)
	public String costItemDictBatchPage(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		return "hrp/htc/basic/costitemdict/htcCostItemDictBatch";
	}
	
	/**
	 * 成本项目字典<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/htc/basic/costitemdict/updateHtcCostItemBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostItemBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		costItemDictJson = htcCostItemDictService.updateHtcCostItemBatch(mapVo);

		return JSONObject.parseObject(costItemDictJson);
	}
    
}

