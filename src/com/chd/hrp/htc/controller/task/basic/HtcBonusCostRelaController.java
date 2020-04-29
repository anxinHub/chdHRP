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
import com.chd.hrp.htc.entity.task.basic.HtcBonusCostRela;
import com.chd.hrp.htc.service.task.basic.HtcBonusCostRelaService;



/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcBonusCostRelaController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcBonusCostRelaController.class);

	@Resource(name = "htcBonusCostRelaService")
	private final HtcBonusCostRelaService htcBonusCostRelaService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/bonuscostrela/htcBonusCostRelaMainPage", method = RequestMethod.GET)
	public String htcBonusCostRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/basic/bonuscostrela/htcBonusCostRelaMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/bonuscostrela/htcBonusCostRelaAddPage", method = RequestMethod.GET)
	public String htcBonusCostRelaAddPage(Model mode) throws Exception {
		return "hrp/htc/task/basic/bonuscostrela/htcBonusCostRelaAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/bonuscostrela/addHtcBonusCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcBonusCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String bonusCostRelaJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		try {
			bonusCostRelaJson = htcBonusCostRelaService.addHtcBonusCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bonusCostRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(bonusCostRelaJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/bonuscostrela/queryHtcBonusCostRela", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryHtcBonusCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String bonusCostRela = htcBonusCostRelaService.queryHtcBonusCostRela(getPage(mapVo));
		return JSONObject.parseObject(bonusCostRela);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/bonuscostrela/deleteHtcBonusCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcBonusCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		String bonusCostRelaJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("people_type_code", ids[4]);// 实际实体类变量
			mapVo.put("bonus_item_code", ids[5]);// 实际实体类变量
			listVo.add(mapVo);
		}
		try {
			bonusCostRelaJson = htcBonusCostRelaService.deleteBatchHtcBonusCostRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bonusCostRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(bonusCostRelaJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/bonuscostrela/htcBonusCostRelaUpdatePage", method = RequestMethod.GET)
	public String htcBonusCostRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		HtcBonusCostRela bonusCostRela = htcBonusCostRelaService.queryHtcBonusCostRelaByCode(mapVo);
		mode.addAttribute("group_id", bonusCostRela.getGroup_id());
		mode.addAttribute("hos_id", bonusCostRela.getHos_id());
		mode.addAttribute("copy_code", bonusCostRela.getCopy_code());
		mode.addAttribute("acc_year", bonusCostRela.getAcc_year());
		mode.addAttribute("people_type_code", bonusCostRela.getPeople_type_code());
		mode.addAttribute("people_type_name", bonusCostRela.getPeople_type_name());
		mode.addAttribute("bonus_item_code", bonusCostRela.getBonus_item_code());
		mode.addAttribute("bonus_item_name", bonusCostRela.getBonus_item_name());
		mode.addAttribute("cost_item_no", bonusCostRela.getCost_item_no());
		mode.addAttribute("cost_item_id", bonusCostRela.getCost_item_id());
		mode.addAttribute("cost_item_code", bonusCostRela.getCost_item_code());
		mode.addAttribute("cost_item_name", bonusCostRela.getCost_item_name());
		return "hrp/htc/task/basic/bonuscostrela/htcBonusCostRelaUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/bonuscostrela/updateHtcBonusCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcBonusCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String bonusCostRelaJson = "";
		
		try {
			bonusCostRelaJson = htcBonusCostRelaService.updateHtcBonusCostRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bonusCostRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(bonusCostRelaJson);
	}
}
