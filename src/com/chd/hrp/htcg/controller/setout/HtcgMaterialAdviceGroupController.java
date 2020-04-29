package com.chd.hrp.htcg.controller.setout;

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
import com.chd.hrp.htcg.service.setout.HtcgMaterialAdviceGroupService;

@Controller
public class HtcgMaterialAdviceGroupController extends BaseController {
	private static Logger logger = Logger
			.getLogger(HtcgMaterialAdviceGroupController.class);

	@Resource(name = "htcgMaterialAdviceGroupService")
	private final HtcgMaterialAdviceGroupService htcgMaterialAdviceGroupService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/setout/materialAdviceGroup/htcgMaterialAdviceGroupMainPage", method = RequestMethod.GET)
	public String htcgMaterialAdviceGroupMainPage(Model mode) throws Exception {
		return "hrp/htcg/setout/materialAdviceGroup/htcgMaterialAdviceGroupMain";

	}

	@RequestMapping(value = "/hrp/htcg/setout/materialAdviceGroup/initHtcgMaterialAdviceGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgMaterialAdviceGroup(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		String materialAdviceJson = "";
		
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			materialAdviceJson = htcgMaterialAdviceGroupService.initHtcgMaterialAdviceGroup(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			materialAdviceJson = e.getMessage();
		}
		 return JSONObject.parseObject(materialAdviceJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/setout/materialAdviceGroup/queryHtcgMaterialAdviceGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMaterialAdviceGroup(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String materialAdviceJson = htcgMaterialAdviceGroupService.queryHtcgMaterialAdviceGroup(getPage(mapVo));

		return JSONObject.parseObject(materialAdviceJson);

	}

	// 删除
	@RequestMapping(value = "/hrp/htcg/setout/materialAdviceGroup/deleteHtcgMaterialAdviceGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMaterialAdviceGroup(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("period_type_code", ids[3]);
			mapVo.put("acc_year", ids[4]);
			mapVo.put("acc_month", ids[5]);
			mapVo.put("scheme_code", ids[6]);
			mapVo.put("drgs_code", ids[7]);
			mapVo.put("mr_no", ids[8]);
			mapVo.put("in_hos_no", ids[9]);
			mapVo.put("advice_date", ids[10]);
			mapVo.put("order_by_no", ids[11]);
			mapVo.put("order_by_id", ids[12]);
			mapVo.put("perform_by_no", ids[13]);
			mapVo.put("perform_by_id", ids[14]);
			mapVo.put("mate_code", ids[15]);
			listVo.add(mapVo);
		}	
		String materialAdviceJson = "";
		try {
			materialAdviceJson = htcgMaterialAdviceGroupService.deleteBatchHtcgMaterialAdviceGroup(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			materialAdviceJson = e.getMessage();
		}
		return JSONObject.parseObject(materialAdviceJson);

	}
}
