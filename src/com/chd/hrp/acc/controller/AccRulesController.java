/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
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
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccRulesController extends BaseController{
	private static Logger logger = Logger.getLogger(AccRulesController.class);
	
	
	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/acc/rules/rulesMainPage", method = RequestMethod.GET)
	public String rulesMainPage(Model mode) throws Exception {

		return "hrp/acc/accrules/accRulesMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/acc/rules/rulesAddPage", method = RequestMethod.GET)
	public String rulesAddPage(Model mode) throws Exception {

		return "hrp/acc/accrules/accRulesAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/acc/rules/addRules", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String rulesJson = rulesService.addRules(mapVo);

		return JSONObject.parseObject(rulesJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/acc/rules/queryRules", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "01");
		String rules = rulesService.queryRules(getPage(mapVo));

		return JSONObject.parseObject(rules);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/acc/rules/deleteRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRules(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String rulesJson = rulesService.deleteBatchRules(listVo);
	   return JSONObject.parseObject(rulesJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/acc/rules/rulesUpdatePage", method = RequestMethod.GET)
	
	public String rulesUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Rules rules = new Rules();
		rules = rulesService.queryRulesByCode(mapVo);
		mode.addAttribute("group_id", rules.getGroup_id());
		mode.addAttribute("hos_id", rules.getHos_id());
		mode.addAttribute("mod_code", rules.getMod_code());
		mode.addAttribute("proj_code", rules.getProj_code());
		mode.addAttribute("proj_name", rules.getProj_name());
		mode.addAttribute("max_level", rules.getMax_level());
		mode.addAttribute("max_length", rules.getMax_length());
		mode.addAttribute("level1", rules.getLevel1());
		mode.addAttribute("level2", rules.getLevel2());
		mode.addAttribute("level3", rules.getLevel3());
		mode.addAttribute("level4", rules.getLevel4());
		mode.addAttribute("level5", rules.getLevel5());
		mode.addAttribute("level6", rules.getLevel6());
		mode.addAttribute("level7", rules.getLevel7());
		mode.addAttribute("level8", rules.getLevel8());
		mode.addAttribute("level9", rules.getLevel9());
		mode.addAttribute("level10", rules.getLevel10());
		mode.addAttribute("is_auto", rules.getIs_auto());
		
		return "hrp/acc/accrules/accRulesUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/acc/rules/updateRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "01");
		String rulesJson = rulesService.updateRules(mapVo);
		
		return JSONObject.parseObject(rulesJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/acc/rules/importRules", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String rulesJson = rulesService.importRules(mapVo);
		
		return JSONObject.parseObject(rulesJson);
	}

}

