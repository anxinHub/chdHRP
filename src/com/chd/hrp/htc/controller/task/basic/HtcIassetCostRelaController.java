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
import com.chd.hrp.htc.entity.task.basic.HtcIassetCostRela;
import com.chd.hrp.htc.service.task.basic.HtcIassetCostRelaService;

/**
 * 2015-3-18 author:alfred
 */

@Controller
public class HtcIassetCostRelaController extends BaseController {

	private static Logger logger = Logger
			.getLogger(HtcIassetCostRelaController.class);

	@Resource(name = "htcIassetCostRelaService")
	private final HtcIassetCostRelaService 	htcIassetCostRelaService = null;
	

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/iassetcostrela/htcIassetCostRelaMainPage", method = RequestMethod.GET)
	public String htcIassetCostRelaMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/iassetcostrela/htcIassetCostRelaMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/iassetcostrela/htcIassetCostRelaAddPage", method = RequestMethod.GET)
	public String htcIassetCostRelaAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/iassetcostrela/htcIassetCostRelaAdd";

	}


	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/iassetcostrela/addHtcIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIassetCostRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String iassetCostRelaJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
	  	mapVo.put("hos_id", SessionManager.getHosId());
	  	mapVo.put("copy_code", SessionManager.getCopyCode());	
		mapVo.put("acc_year", SessionManager.getAcctYear());

		try {
			iassetCostRelaJson = htcIassetCostRelaService.addHtcIassetCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(iassetCostRelaJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/iassetcostrela/queryHtcIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
	  	mapVo.put("hos_id", SessionManager.getHosId());
	  	mapVo.put("copy_code", SessionManager.getCopyCode());	

		String iassetCostRela = htcIassetCostRelaService.queryHtcIassetCostRela(getPage(mapVo));

		return JSONObject.parseObject(iassetCostRela);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/iassetcostrela/deleteHtcIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcIassetCostRela(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {
		
		String iassetCostRelaJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("asset_type_code", ids[4]);// 实际实体类变量
			listVo.add(mapVo);
		}
		try {
			iassetCostRelaJson = htcIassetCostRelaService.deleteBatchHtcIassetCostRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(iassetCostRelaJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/iassetcostrela/htcIassetCostRelaUpdatePage", method = RequestMethod.GET)
	public String htcIassetCostRelaUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		HtcIassetCostRela iassetCostRela = htcIassetCostRelaService.queryHtcIassetCostRelaByCode(mapVo);
		mode.addAttribute("group_id", iassetCostRela.getGroup_id());
		mode.addAttribute("hos_id", iassetCostRela.getHos_id());
		mode.addAttribute("copy_code", iassetCostRela.getCopy_code());
		mode.addAttribute("acc_year", iassetCostRela.getAcc_year());
		mode.addAttribute("asset_type_code", iassetCostRela.getAsset_type_code());
		mode.addAttribute("asset_type_name", iassetCostRela.getAsset_type_name());
		mode.addAttribute("cost_item_no", iassetCostRela.getCost_item_no());
		mode.addAttribute("cost_item_id", iassetCostRela.getCost_item_id());
		mode.addAttribute("cost_item_code", iassetCostRela.getCost_item_code());
		mode.addAttribute("cost_item_name", iassetCostRela.getCost_item_name());
		return "hrp/htc/task/basic/iassetcostrela/htcIassetCostRelaUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/iassetcostrela/updateHtcIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcIassetCostRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String iassetCostRelaJson = "";
		try {
			iassetCostRelaJson = htcIassetCostRelaService.updateHtcIassetCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(iassetCostRelaJson);
	}


}
