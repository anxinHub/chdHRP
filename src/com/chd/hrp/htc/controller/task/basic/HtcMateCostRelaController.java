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
import com.chd.hrp.htc.entity.task.basic.HtcMateCostRela;
import com.chd.hrp.htc.service.task.basic.HtcMateCostRelaService;

/**
 * 2015-3-18 author:alfred
 */

@Controller
public class HtcMateCostRelaController extends BaseController {

	private static Logger logger = Logger
			.getLogger(HtcMateCostRelaController.class);

	@Resource(name = "htcMateCostRelaService")
	private final HtcMateCostRelaService htcMateCostRelaService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/matecostrela/htcMateCostRelaMainPage", method = RequestMethod.GET)
	public String htcMateCostRelaMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/matecostrela/htcMateCostRelaMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/matecostrela/htcMateCostRelaAddPage", method = RequestMethod.GET)
	public String htcMateCostRelaAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/matecostrela/htcMateCostRelaAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/matecostrela/addHtcMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcMateCostRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String mateCostRelaJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
  		mapVo.put("acc_year", SessionManager.getAcctYear());

  		try {
			mateCostRelaJson = htcMateCostRelaService.addHtcMateCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mateCostRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(mateCostRelaJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/matecostrela/queryHtcMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcMateCostRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
  		if (mapVo.get("acct_year")==null ){
  			mapVo.put("acct_year", SessionManager.getAcctYear());
  		}

		String mateCostRela = htcMateCostRelaService
				.queryHtcMateCostRela(getPage(mapVo));

		return JSONObject.parseObject(mateCostRela);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/matecostrela/deleteHtcMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcMateCostRela(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {

		String mateCostRelaJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);// 实际实体类变量
			mapVo.put("hos_id", ids[1]);// 实际实体类变量
			mapVo.put("copy_code", ids[2]);// 实际实体类变量
			mapVo.put("acc_year", ids[3]);// 实际实体类变量
			mapVo.put("mate_type_code", ids[4]);// 实际实体类变量
			listVo.add(mapVo);
		}

		try {
			mateCostRelaJson = htcMateCostRelaService.deleteBatchHtcMateCostRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mateCostRelaJson =  e.getMessage();
		}

		return JSONObject.parseObject(mateCostRelaJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/matecostrela/htcMateCostRelaUpdatePage", method = RequestMethod.GET)
	public String htcMateCostRelaUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		HtcMateCostRela mateCostRela = htcMateCostRelaService.queryHtcMateCostRelaByCode(mapVo);
		mode.addAttribute("group_id", mateCostRela.getGroup_id());
		mode.addAttribute("hos_id", mateCostRela.getHos_id());
		mode.addAttribute("copy_code", mateCostRela.getCopy_code());
		mode.addAttribute("acc_year", mateCostRela.getAcc_year());
		mode.addAttribute("mate_type_code", mateCostRela.getMate_type_code());
		mode.addAttribute("mate_type_name", mateCostRela.getMate_type_name());
		mode.addAttribute("cost_item_no", mateCostRela.getCost_item_no());
		mode.addAttribute("cost_item_id", mateCostRela.getCost_item_id());
		mode.addAttribute("cost_item_code", mateCostRela.getCost_item_code());
		mode.addAttribute("cost_item_name", mateCostRela.getCost_item_name());
		return "hrp/htc/task/basic/matecostrela/htcMateCostRelaUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/matecostrela/updateHtcMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcMateCostRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String mateCostRelaJson = "";
		
  		try {
			mateCostRelaJson = htcMateCostRelaService.updateHtcMateCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mateCostRelaJson =  e.getMessage();
		}

		return JSONObject.parseObject(mateCostRelaJson);
	}

}
