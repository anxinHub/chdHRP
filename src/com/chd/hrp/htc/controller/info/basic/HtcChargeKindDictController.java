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
import com.chd.hrp.htc.entity.info.basic.HtcChargeKindDict;
import com.chd.hrp.htc.service.info.basic.HtcChargeKindDictService;

/**
 * 2015-3-18 author:alfred
 */

@Controller
public class HtcChargeKindDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcChargeKindDictController.class);

	@Resource(name = "htcChargeKindDictService")
	private final HtcChargeKindDictService htcChargeKindDictService = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/chargekind/htcChargeKindDictMainPage", method = RequestMethod.GET)
	public String htcChargeKindDictMainPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/chargekind/htcChargeKindDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/info/basic/chargekind/htcChargeKindDictAddPage", method = RequestMethod.GET)
	public String htcChargeKindDictAddPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/chargekind/htcChargeKindDictAdd";

	}


	// 保存
	@RequestMapping(value = "/hrp/htc/info/basic/chargekind/addHtcChargeKindDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcChargeKindDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String chargeKindDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_kind_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_kind_name").toString()));
		
		try {
			chargeKindDictJson = htcChargeKindDictService.addHtcChargeKindDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(chargeKindDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/info/basic/chargekind/queryHtcChargeKindDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcChargeKindDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String chargeKindDict = htcChargeKindDictService.queryHtcChargeKindDict(getPage(mapVo));

		return JSONObject.parseObject(chargeKindDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/info/basic/chargekind/deleteHtcChargeKindDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcChargeKindDict(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {
		
		String chargeKindDictJson = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_charge_kind_arrt", ids[3]);
			if(Strings.isNotBlank(str)){
				chargeKindDictJson = "{\"error\":\"删除失败，选择的收费类别被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(chargeKindDictJson);
			}
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("charge_kind_id",ids[3]); 
            listVo.add(mapVo);
        }
		try {
			chargeKindDictJson = htcChargeKindDictService.deleteBatchHtcChargeKindDict(listVo);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return JSONObject.parseObject(chargeKindDictJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/chargekind/htcChargeKindDictUpdatePage", method = RequestMethod.GET)
	public String htcChargeKindDictUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		HtcChargeKindDict chargeKindDict = htcChargeKindDictService.queryHtcChargeKindDictByCode(mapVo);

		mode.addAttribute("group_id", chargeKindDict.getGroup_id());
		mode.addAttribute("hos_id", chargeKindDict.getHos_id());
		mode.addAttribute("copy_code", chargeKindDict.getCopy_code());
		mode.addAttribute("income_type_id", chargeKindDict.getIncome_type_id());
		mode.addAttribute("income_type_code", chargeKindDict.getIncome_type_code());
		mode.addAttribute("income_type_name", chargeKindDict.getIncome_type_name());
		mode.addAttribute("income_item_id_in", chargeKindDict.getIncome_item_id_in());
		mode.addAttribute("income_item_code_in", chargeKindDict.getIncome_item_code_in());
		mode.addAttribute("income_item_name_in", chargeKindDict.getIncome_item_name_in());
		mode.addAttribute("income_item_id_out", chargeKindDict.getIncome_item_id_out());
		mode.addAttribute("income_item_code_out", chargeKindDict.getIncome_item_code_out());
		mode.addAttribute("income_item_name_out", chargeKindDict.getIncome_item_name_out());
		mode.addAttribute("charge_kind_id", chargeKindDict.getCharge_kind_id());
		mode.addAttribute("charge_kind_code", chargeKindDict.getCharge_kind_code());
		mode.addAttribute("charge_kind_name", chargeKindDict.getCharge_kind_name());
		mode.addAttribute("spell_code", chargeKindDict.getSpell_code());
		mode.addAttribute("wbx_code", chargeKindDict.getWbx_code());

		return "hrp/htc/info/basic/chargekind/htcChargeKindDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/info/basic/chargekind/updateHtcChargeKindDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcChargeKindDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String chargeKindDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_kind_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_kind_name").toString()));
		
		try {
			chargeKindDictJson = htcChargeKindDictService.updateHtcChargeKindDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(chargeKindDictJson);
	}
}
