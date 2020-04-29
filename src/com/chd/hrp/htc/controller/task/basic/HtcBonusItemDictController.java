package com.chd.hrp.htc.controller.task.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.chd.hrp.htc.entity.task.basic.HtcBonusItemDict;
import com.chd.hrp.htc.service.task.basic.HtcBonusItemDictService;
/**
 * @Title.
 */
@Controller
public class HtcBonusItemDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcBonusItemDictController.class);

	@Resource(name = "htcBonusItemDictService")
	private final HtcBonusItemDictService htcBonusItemDictService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/bonusitemdict/htcBonusItemDictMainPage", method = RequestMethod.GET)
	public String htcBonusItemDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/bonusitemdict/htcBonusItemDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/bonusitemdict/htcBonusItemDictAddPage", method = RequestMethod.GET)
	public String htcBonusItemDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/bonusitemdict/htcBonusItemDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/bonusitemdict/addHtcBonusItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcBonusItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String bonusItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bonus_item_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bonus_item_name").toString()));
		
		try {
			bonusItemDictJson = htcBonusItemDictService.addHtcBonusItemDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bonusItemDictJson = e.getMessage();
		}

		return JSONObject.parseObject(bonusItemDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/bonusitemdict/queryHtcBonusItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcBonusItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("acc_year", SessionManager.getAcctYear());

		String bonusItemDict = htcBonusItemDictService.queryHtcBonusItemDict(getPage(mapVo));

		return JSONObject.parseObject(bonusItemDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/bonusitemdict/deleteHtcBonusItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcBonusItemDict(@RequestParam(value = "ParamVo") String ParamVo, Model mode) throws Exception {

		String bonusItemDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : ParamVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code",ids[2]);
		    mapVo.put("acc_year", ids[3]);
			mapVo.put("bonus_item_code", ids[4]);
			listVo.add(mapVo);
		}
		
		try {
			bonusItemDictJson = htcBonusItemDictService.deleteBatchHtcBonusItemDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bonusItemDictJson = e.getMessage();
		}

		return JSONObject.parseObject(bonusItemDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/bonusitemdict/htcBonusItemDictUpdatePage", method = RequestMethod.GET)
	public String htcBonusItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		HtcBonusItemDict bonusItemDict = new HtcBonusItemDict();
		
		bonusItemDict = htcBonusItemDictService.queryHtcBonusItemDictByCode(mapVo);
		mode.addAttribute("group_id", bonusItemDict.getGroup_id());
		mode.addAttribute("hos_id", bonusItemDict.getHos_id());
		mode.addAttribute("copy_code", bonusItemDict.getCopy_code());
		mode.addAttribute("acc_year", bonusItemDict.getAcc_year());
		mode.addAttribute("bonus_item_code", bonusItemDict.getBonus_item_code());
		mode.addAttribute("bonus_item_name", bonusItemDict.getBonus_item_name());
		mode.addAttribute("sort_code", bonusItemDict.getSort_code());
		mode.addAttribute("remark", bonusItemDict.getRemark());
		mode.addAttribute("is_stop", bonusItemDict.getIs_stop());

		return "hrp/htc/task/basic/bonusitemdict/htcBonusItemDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/bonusitemdict/updateHtcBonusItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcBonusItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String bonusItemDictJson = "";
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bonus_item_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bonus_item_name").toString()));
		
		try {
			bonusItemDictJson = htcBonusItemDictService.updateHtcBonusItemDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bonusItemDictJson = e.getMessage();
		}

		return JSONObject.parseObject(bonusItemDictJson);
	}

	
	
  

}
