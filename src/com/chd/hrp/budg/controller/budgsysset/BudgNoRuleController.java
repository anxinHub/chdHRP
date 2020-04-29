/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.budg.controller.budgsysset;

import java.util.*;
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
import com.chd.hrp.budg.service.budgsysset.BudgNoRulesService;



/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class BudgNoRuleController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgNoRuleController.class);

	@Resource(name = "budgNoRuleService")
	private final BudgNoRulesService budgNoRuleService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/budg/budgsysset/budgNoRule/budgNoRulesMainPage", method = RequestMethod.GET)
	public String rulesMainPage(Model mode) throws Exception {

		return "hrp/budg/budgsysset/budgNoRule/budgNoRuleMain";

	}

	// 保存
	@RequestMapping(value = "/hrp/budg/budgsysset/budgNoRule/saveRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveRules(@RequestParam(value="formPara") String formPara, Model mode) throws Exception {
		   
		List<Map<String,Object>> listVo  = new ArrayList<Map<String,Object>>();
		for ( String id: formPara.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("table_code", id.split("@")[0]);
            mapVo.put("table_name",id.split("@")[1]);
            mapVo.put("prefixe",id.split("@")[2]);
            mapVo.put("is_year",id.split("@")[3]);
            mapVo.put("is_budg_year",id.split("@")[4]);
            mapVo.put("is_month",id.split("@")[5]);
            mapVo.put("seq_no",id.split("@")[6]);
			
            listVo.add(mapVo);
        }
		String accParaJson = budgNoRuleService.updateBatchRules(listVo);
		
		return JSONObject.parseObject(accParaJson);
	}
	// 查询
	@RequestMapping(value = "/hrp/budg/budgsysset/budgNoRule/queryBudgNoRules", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryBudgNoRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String rules = budgNoRuleService.queryRules(mapVo);

		return JSONObject.parseObject(rules);

	}

	// 修改保存
	@RequestMapping(value = "/hrp/budg/budgsysset/budgNoRule/updateRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String rulesJson = budgNoRuleService.updateRules(mapVo);

		return JSONObject.parseObject(rulesJson);
	}

}
