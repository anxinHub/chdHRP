package com.chd.hrp.htc.controller.relative.plan.chargeitemvalue;

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
import com.chd.hrp.htc.entity.relative.plan.chargeitemvalue.HtcRelativeChargeItemValue;
import com.chd.hrp.htc.service.relative.plan.chargeitemvalue.HtcRelativeChargeItemValueService;

@Controller
public class HtcRelativeChargeItemValueController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcRelativeChargeItemValueController.class);
	
	@Resource(name = "htcRelativeChargeItemValueService")
	private final HtcRelativeChargeItemValueService htcRelativeChargeItemValueService = null;
	
	 // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/relative/plan/chargeitemvalue/htcRelativeChargeItemValueMainPage", method = RequestMethod.GET)
	public String htcRelativeChargeItemValueMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/plan/chargeitemvalue/htcRelativeChargeItemValueMain";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/relative/plan/chargeitemvalue/htcRelativeChargeItemValueAddPage", method = RequestMethod.GET)
	public String htcRelativeChargeItemValueAddPage(Model mode) throws Exception {

		return "hrp/htc/relative/plan/chargeitemvalue/htcRelativeChargeItemValueAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/relative/plan/chargeitemvalue/addHtcRelativeChargeItemValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcRelativeChargeItemValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String chargeItemValueJson = "";
		
		try {
			
			chargeItemValueJson = htcRelativeChargeItemValueService.addHtcRelativeChargeItemValue(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			chargeItemValueJson = e.getMessage();
		}

		return JSONObject.parseObject(chargeItemValueJson);

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/relative/plan/chargeitemvalue/queryHtcRelativeChargeItemValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeChargeItemValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String chargeItemValueJson = htcRelativeChargeItemValueService.queryHtcRelativeChargeItemValue(getPage(mapVo));

		return JSONObject.parseObject(chargeItemValueJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/relative/plan/chargeitemvalue/deleteHtcRelativeChargeItemValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcRelativeChargeItemValue(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("&");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("plan_code", ids[4]);
			mapVo.put("proj_dept_id", ids[5]);
			mapVo.put("charge_item_id", ids[6]);
			listVo.add(mapVo);
		}
		
		String chargeItemValueJson = "";
		
		try {
			chargeItemValueJson = htcRelativeChargeItemValueService.deleteBatchHtcRelativeChargeItemValue(listVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			chargeItemValueJson = e.getMessage();
		}
		
		return JSONObject.parseObject(chargeItemValueJson);

	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/relative/plan/chargeitemvalue/htcRelativeChargeItemValueUpdatePage", method = RequestMethod.GET)
	public String htcRelativeChargeItemValueUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		HtcRelativeChargeItemValue htcRelativeChargeItemValue = htcRelativeChargeItemValueService.queryHtcRelativeChargeItemValueByCode(mapVo);

		mode.addAttribute("group_id", htcRelativeChargeItemValue.getGroup_id());
		mode.addAttribute("hos_id", htcRelativeChargeItemValue.getHos_id());
		mode.addAttribute("copy_code", htcRelativeChargeItemValue.getCopy_code());
		mode.addAttribute("acc_year", htcRelativeChargeItemValue.getAcc_year());
		mode.addAttribute("plan_code", htcRelativeChargeItemValue.getPlan_code());
		mode.addAttribute("plan_name", htcRelativeChargeItemValue.getPlan_name());
		mode.addAttribute("proj_dept_id", htcRelativeChargeItemValue.getProj_dept_id());
		mode.addAttribute("proj_dept_no", htcRelativeChargeItemValue.getProj_dept_no());
		mode.addAttribute("proj_dept_code", htcRelativeChargeItemValue.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcRelativeChargeItemValue.getProj_dept_name());
		mode.addAttribute("charge_item_id", htcRelativeChargeItemValue.getCharge_item_id());
		mode.addAttribute("charge_item_code", htcRelativeChargeItemValue.getCharge_item_code());
		mode.addAttribute("charge_item_name", htcRelativeChargeItemValue.getCharge_item_name());
		mode.addAttribute("value_ratio", htcRelativeChargeItemValue.getValue_ratio());

		return "hrp/htc/relative/plan/chargeitemvalue/htcRelativeChargeItemValueUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/relative/plan/chargeitemvalue/updateHtcRelativeChargeItemValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcRelativeChargeItemValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String chargeItemValueJson = "";
		try {
			chargeItemValueJson = htcRelativeChargeItemValueService.updateHtcRelativeChargeItemValue(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			chargeItemValueJson = e.getMessage();
		}
		return JSONObject.parseObject(chargeItemValueJson);
	}
		
}
