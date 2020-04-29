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
import com.chd.hrp.htc.entity.task.basic.HtcWageCostRela;
import com.chd.hrp.htc.service.task.basic.HtcWageCostRelaService;

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
public class HtcWageCostRelaController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcWageCostRelaController.class);


	@Resource(name = "htcWageCostRelaService")
	private final HtcWageCostRelaService htcWageCostRelaService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/wagecostrela/htcWageCostRelaMainPage", method = RequestMethod.GET)
	public String htcWageCostRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/basic/wagecostrela/htcWageCostRelaMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/wagecostrela/htcWageCostRelaAddPage", method = RequestMethod.GET)
	public String htcWageCostRelaAddPage(Model mode) throws Exception {
		return "hrp/htc/task/basic/wagecostrela/htcWageCostRelaAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/wagecostrela/addHtcWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcWageCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String wageCostRelaJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		try {
			wageCostRelaJson = htcWageCostRelaService.addHtcWageCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			wageCostRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(wageCostRelaJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/wagecostrela/queryHtcWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcWageCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String wageCostRela = htcWageCostRelaService.queryHtcWageCostRela(getPage(mapVo));

		return JSONObject.parseObject(wageCostRela);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/wagecostrela/deleteHtcWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcWageCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		String wageCostRelaJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("people_type_code", ids[4]);
			mapVo.put("wage_item_code", ids[5]);
			listVo.add(mapVo);
		}
		try {
			wageCostRelaJson = htcWageCostRelaService.deleteBatchHtcWageCostRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			wageCostRelaJson =  e.getMessage();
		}
		
		return JSONObject.parseObject(wageCostRelaJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/wagecostrela/htcWageCostRelaUpdatePage", method = RequestMethod.GET)
	public String htcWageCostRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		HtcWageCostRela wageCostRela =  htcWageCostRelaService.queryHtcWageCostRelaByCode(mapVo);
		mode.addAttribute("group_id", wageCostRela.getGroup_id());
		mode.addAttribute("hos_id", wageCostRela.getHos_id());
		mode.addAttribute("copy_code", wageCostRela.getCopy_code());
		mode.addAttribute("acc_year", wageCostRela.getAcc_year());
		mode.addAttribute("people_type_code", wageCostRela.getPeople_type_code());
		mode.addAttribute("people_type_name", wageCostRela.getPeople_type_name());
		mode.addAttribute("wage_item_code", wageCostRela.getWage_item_code());
		mode.addAttribute("wage_item_name", wageCostRela.getWage_item_name());
		mode.addAttribute("cost_item_no", wageCostRela.getCost_item_no());
		mode.addAttribute("cost_item_id", wageCostRela.getCost_item_id());
		mode.addAttribute("cost_item_code", wageCostRela.getCost_item_code());
		mode.addAttribute("cost_item_name", wageCostRela.getCost_item_name());
		return "hrp/htc/task/basic/wagecostrela/htcWageCostRelaUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/wagecostrela/updateHtcWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcWageCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String wageCostRelaJson = "";
		
		try {
			wageCostRelaJson = htcWageCostRelaService.updateHtcWageCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			wageCostRelaJson =  e.getMessage();
		}

		return JSONObject.parseObject(wageCostRelaJson);
	}

   
}
