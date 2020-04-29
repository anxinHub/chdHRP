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
import com.chd.hrp.htc.entity.info.basic.HtcChargeItemDict;
import com.chd.hrp.htc.service.info.basic.HtcChargeItemDictService;

/**
 * 2015-3-18 author:alfred
 */
@Controller
public class HtcChargeItemDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcChargeItemDictController.class);

	@Resource(name = "htcChargeItemDictService")
	private final HtcChargeItemDictService htcChargeItemDictService = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/chargeitem/htcChargeItemDictMainPage", method = RequestMethod.GET)
	public String htcChargeItemDictMainPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/chargeitem/htcChargeItemDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/info/basic/chargeitem/htcChargeItemDictAddPage", method = RequestMethod.GET)
	public String htcChargeItemDictAddPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/chargeitem/htcChargeItemDictAdd";

	}


	// 保存
	@RequestMapping(value = "/hrp/htc/info/basic/chargeitem/addHtcChargeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcChargeItemDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String chargeItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_item_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_item_name").toString()));

		try {
			chargeItemDictJson = htcChargeItemDictService.addHtcChargeItemDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(chargeItemDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/info/basic/chargeitem/queryHtcChargeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcChargeItemDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String chargeItemDict = htcChargeItemDictService.queryHtcChargeItemDict(getPage(mapVo));

		return JSONObject.parseObject(chargeItemDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/info/basic/chargeitem/deleteHtcChargeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcChargeItemDict(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {
		
		String chargeItemDictJson = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_charge_item_arrt", ids[4]);
			if(Strings.isNotBlank(str)){
				chargeItemDictJson = "{\"error\":\"删除失败，选择的收费项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(chargeItemDictJson);
			}
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("charge_kind_id",ids[3]);   
			mapVo.put("charge_item_id",ids[4]); 
            listVo.add(mapVo);
        }
		try {
			chargeItemDictJson = htcChargeItemDictService.deleteBatchHtcChargeItemDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSONObject.parseObject(chargeItemDictJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/chargeitem/htcChargeItemDictUpdatePage", method = RequestMethod.GET)
	public String htcChargeItemDictUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HtcChargeItemDict chargeItemDict = htcChargeItemDictService.queryHtcChargeItemDictByCode(mapVo);

		mode.addAttribute("group_id", chargeItemDict.getGroup_id());
		mode.addAttribute("hos_id", chargeItemDict.getHos_id());
		mode.addAttribute("copy_code", chargeItemDict.getCopy_code());
		mode.addAttribute("charge_kind_id", chargeItemDict.getCharge_kind_id());
		mode.addAttribute("charge_kind_code", chargeItemDict.getCharge_kind_code());
		mode.addAttribute("charge_kind_name", chargeItemDict.getCharge_kind_name());
		mode.addAttribute("charge_item_id", chargeItemDict.getCharge_item_id());
		mode.addAttribute("charge_item_code", chargeItemDict.getCharge_item_code());
		mode.addAttribute("charge_item_name", chargeItemDict.getCharge_item_name());
		mode.addAttribute("price", chargeItemDict.getPrice());
		mode.addAttribute("is_stop", chargeItemDict.getIs_stop());
		mode.addAttribute("spell_code", chargeItemDict.getSpell_code());
		mode.addAttribute("wbx_code", chargeItemDict.getWbx_code());

		return "hrp/htc/info/basic/chargeitem/htcChargeItemDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/info/basic/chargeitem/updateHtcChargeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcChargeItemDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String chargeItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_item_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_item_name").toString()));
		
		try {
			chargeItemDictJson = htcChargeItemDictService.updateHtcChargeItemDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(chargeItemDictJson);
	}


    
}
