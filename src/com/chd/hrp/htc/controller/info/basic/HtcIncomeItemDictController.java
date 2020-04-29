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
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.htc.entity.info.basic.HtcIncomeItemDict;
import com.chd.hrp.htc.service.info.basic.HtcIncomeItemDictService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcIncomeItemDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcIncomeItemDictController.class);

	@Resource(name = "htcIncomeItemDictService")
	private final HtcIncomeItemDictService incomeItemDictService = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/incomeitem/htcIncomeItemDictMainPage", method = RequestMethod.GET)
	public String htcIncomeItemDictMainPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/incomeitem/htcIncomeItemDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/info/basic/incomeitem/htcIncomeItemDictAddPage", method = RequestMethod.GET)
	public String htcIncomeItemDictAddPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/incomeitem/htcIncomeItemDictAdd";

	}


	// 保存
	@RequestMapping(value = "/hrp/htc/info/basic/incomeitem/addHtcIncomeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIncomeItemDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String incomeItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
	  	mapVo.put("hos_id", SessionManager.getHosId());
	  	mapVo.put("copy_code", SessionManager.getCopyCode());
	  	mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("income_item_name").toString()));
	  	mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("income_item_name").toString()));
	  	
		try {
			incomeItemDictJson = incomeItemDictService.addHtcIncomeItemDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(incomeItemDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/info/basic/incomeitem/queryHtcIncomeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeItemDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
	  	mapVo.put("hos_id", SessionManager.getHosId());
	  	mapVo.put("copy_code", SessionManager.getCopyCode());
		
	  	String incomeItemDict = incomeItemDictService.queryHtcIncomeItemDict(getPage(mapVo));

		return JSONObject.parseObject(incomeItemDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/info/basic/incomeitem/deleteHtcIncomeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcIncomeItemDict(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		
		String incomeItemDictJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_income_item_arrt", ids[3]);
			if(Strings.isNotBlank(str)){
				incomeItemDictJson = "{\"error\":\"删除失败，选择的收入项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(incomeItemDictJson);
			}
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("income_item_id", ids[3]);
			listVo.add(mapVo);
		}

		try {
			incomeItemDictJson = incomeItemDictService.deleteBatchHtcIncomeItemDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(incomeItemDictJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/incomeitem/htcIncomeItemDictUpdatePage", method = RequestMethod.GET)
	public String htcIncomeItemDictUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
	  	mapVo.put("hos_id", SessionManager.getHosId());
	  	mapVo.put("copy_code", SessionManager.getCopyCode());
		
	  	HtcIncomeItemDict incomeItemDict = incomeItemDictService.queryHtcIncomeItemDictByCode(mapVo);
		
		mode.addAttribute("group_id", incomeItemDict.getGroup_id());
		mode.addAttribute("hos_id", incomeItemDict.getHos_id());
		mode.addAttribute("copy_code", incomeItemDict.getCopy_code());
		mode.addAttribute("income_item_id",incomeItemDict.getIncome_item_id());
		mode.addAttribute("income_item_code",incomeItemDict.getIncome_item_code());
		mode.addAttribute("income_item_name",incomeItemDict.getIncome_item_name());
		mode.addAttribute("is_stop", incomeItemDict.getIs_stop());
		mode.addAttribute("spell_code", incomeItemDict.getSpell_code());
		mode.addAttribute("wbx_code", incomeItemDict.getWbx_code());

		return "hrp/htc/info/basic/incomeitem/htcIncomeItemDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/info/basic/incomeitem/updateHtcIncomeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcIncomeItemDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String incomeItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
	  	mapVo.put("hos_id", SessionManager.getHosId());
	  	mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("income_item_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("income_item_name").toString()));
		
		try {
			incomeItemDictJson = incomeItemDictService.updateHtcIncomeItemDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(incomeItemDictJson);
	}

	

}
