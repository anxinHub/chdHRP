package com.chd.hrp.ass.controller.repair.assRepRule;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.repair.assRepRule.AssRepRuleService;

@Controller
@RequestMapping(value = "hrp/ass/repair/assRepRule/")
public class AssRepRuleController {

	@Resource(name = "assRepRuleService")
	private final AssRepRuleService assRepRuleService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assRepRuleMainPage", method = RequestMethod.GET )
	public String assLocationMainPage(Model mode) throws Exception {

		return "/hrp/ass/repair/assRepRule/assRepRuleMain";

	}

	/**
	 * @Description 主页面维修工程师tree数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryRepTeamUser", method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryRepTeamUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeamUser = assRepRuleService.queryRepTeamUser(mapVo);
		return RepTeamUser;

	}
	
	/**
	 * @Description 主页面维修工程师tree数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryTreeDataByUserId", method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryTreeDataByUserId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeamUser = assRepRuleService.queryTreeDataByUserId(mapVo); 
		return RepTeamUser;
		
	}
	/**
	 * @Description 保存派单规则
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "saveRepRule", method = RequestMethod.POST )
	@ResponseBody
	public String saveRepRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeamUser = assRepRuleService.saveRepRule(mapVo);
		return RepTeamUser;
		
	}
	
	//卡片tree
		@RequestMapping(value = "queryAsscardTree", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
		@ResponseBody
		public String queryAsscardTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("user_id") == null) {
				mapVo.put("user_id", SessionManager.getUserId());
			}
			
			if(mapVo.get("dept_id") != null){
				mapVo.put("dept_id", mapVo.get("dept_id").toString().split("@")[0]);
			}
			String hrpMatSelect = assRepRuleService.queryAsscardTree(mapVo);
			return hrpMatSelect;
		}
		//资产分类
		@RequestMapping(value = "queryAssTypeTree", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
		@ResponseBody
		public String queryAssTypeTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("user_id") == null) {
				mapVo.put("user_id", SessionManager.getUserId());
			}
			String hrpMatSelect = assRepRuleService.queryAssTypeTree(mapVo);
			return hrpMatSelect;
		}
		//资产分类
		@RequestMapping(value = "queryAssFaultTypeTree", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
		@ResponseBody
		public String queryAssFaultTypeTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("user_id") == null) {
				mapVo.put("user_id", SessionManager.getUserId());
			}
			String hrpMatSelect = assRepRuleService.queryAssFaultTypeTree(mapVo);
			return hrpMatSelect;
		}
}
