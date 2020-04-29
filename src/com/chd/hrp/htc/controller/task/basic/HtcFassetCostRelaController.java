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
import com.chd.hrp.htc.entity.task.basic.HtcFassetCostRela;
import com.chd.hrp.htc.service.task.basic.HtcFassetCostRelaService;

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
public class HtcFassetCostRelaController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcFassetCostRelaController.class);

	@Resource(name = "htcFassetCostRelaService")
	private final HtcFassetCostRelaService htcFassetCostRelaService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/fassetcostrela/htcFassetCostRelaMainPage", method = RequestMethod.GET)
	public String htcFassetCostRelaMainPage(Model mode) throws Exception {
		
		return "hrp/htc/task/basic/fassetcostrela/htcFassetCostRelaMain";
	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/fassetcostrela/htcFassetCostRelaAddPage", method = RequestMethod.GET)
	public String htcFassetCostRelaAddPage(Model mode) throws Exception {
		
		return "hrp/htc/task/basic/fassetcostrela/htcFassetCostRelaAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/fassetcostrela/addHtcFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcFassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String fassetCostRelaJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("acc_year", SessionManager.getAcctYear());
		
		try {
			fassetCostRelaJson = htcFassetCostRelaService.addHtcFassetCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fassetCostRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(fassetCostRelaJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/fassetcostrela/queryHtcFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcFassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String fassetCostRela = htcFassetCostRelaService.queryHtcFassetCostRela(getPage(mapVo));
		return JSONObject.parseObject(fassetCostRela);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/fassetcostrela/deleteHtcFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcFassetCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		String fassetCostRelaJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			if (mapVo.get("acct_year")==null ){
				mapVo.put("acct_year", SessionManager.getAcctYear());
			}
			
			mapVo.put("asset_type_code", ids[0]);
			listVo.add(mapVo);
		}
		
		try {
			fassetCostRelaJson = htcFassetCostRelaService.deleteBatchHtcFassetCostRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fassetCostRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(fassetCostRelaJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/fassetcostrela/htcFassetCostRelaUpdatePage", method = RequestMethod.GET)
	public String htcFassetCostRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		HtcFassetCostRela fassetCostRela = htcFassetCostRelaService.queryHtcFassetCostRelaByCode(mapVo);
		mode.addAttribute("group_id", fassetCostRela.getGroup_id());
		mode.addAttribute("hos_id", fassetCostRela.getHos_id());
		mode.addAttribute("copy_code", fassetCostRela.getCopy_code());
		mode.addAttribute("acc_year", fassetCostRela.getAcc_year());
		mode.addAttribute("asset_type_code", fassetCostRela.getAsset_type_code());
		mode.addAttribute("asset_type_name", fassetCostRela.getAsset_type_name());
		mode.addAttribute("cost_item_no", fassetCostRela.getCost_item_no());
		mode.addAttribute("cost_item_id", fassetCostRela.getCost_item_id());
		mode.addAttribute("cost_item_code", fassetCostRela.getCost_item_code());
		mode.addAttribute("cost_item_name", fassetCostRela.getCost_item_name());
		return "hrp/htc/task/basic/fassetcostrela/htcFassetCostRelaUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/fassetcostrela/updateHtcFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcFassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String fassetCostRelaJson = "";
		
		try {
			fassetCostRelaJson = htcFassetCostRelaService.updateHtcFassetCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fassetCostRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(fassetCostRelaJson);
	}
}
