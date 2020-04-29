package com.chd.hrp.ass.controller.repair.assmassrepairsche;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.repair.assmassrepairsche.AssMassRepairScheService;

@Controller
@RequestMapping(value = "hrp/ass/repair/assMassRepairSche/")
public class AssMassRepairScheController {  
	
private static Logger logger = Logger.getLogger(AssMassRepairScheController.class);

	//引入service服务
	@Resource(name="assMassRepairScheService")
	private final AssMassRepairScheService assMassRepairScheService = null ;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "assMassRepairScheMainPage", method = RequestMethod.GET)  
	public String assMassRepairScheMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/assmassrepairsche/assMassRepairScheMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "assMassRepairScheAddPage", method = RequestMethod.GET)
	public String assMassRepairScheAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("rep_team_code",mapVo.get("rep_team_code"));
		mode.addAttribute("acc_year",mapVo.get("acc_year").toString().split("-")[0]);
		mode.addAttribute("acc_month",mapVo.get("acc_year").toString().split("-")[1]);
		return "hrp/ass/repair/assmassrepairsche/assMassRepairScheAdd";

	}
	/**
	 * @Description 
	 * 修改页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assMassRepairScheUpdatePage", method = RequestMethod.GET)
	public String assMassRepairScheUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("rep_team_code", mapVo.get("rep_user").toString().split("@")[1]);
		mapVo.put("rep_user", mapVo.get("rep_user").toString().split("@")[0]);
		Map<String,Object>massRepair=assMassRepairScheService.queryAssRepairUserByCode(mapVo);
		mode.addAttribute("rep_team_code",massRepair.get("REP_TEAM_CODE"));
		mode.addAttribute("rep_user",massRepair.get("REP_USER"));
		mode.addAttribute("phone1",massRepair.get("PHONE1"));
		mode.addAttribute("phone2",massRepair.get("PHONE2"));
		mode.addAttribute("sort_code",massRepair.get("SORT_CODE"));
		return "hrp/ass/repair/assmassrepairsche/assMassRepairScheUpdate";
		
	}
	/**
	 * @Description 
	 * 修改员工数据， 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "updateAssRepairUser", method = RequestMethod.POST)
	public String updateAssRepairUser(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeam = assMassRepairScheService.updateAssRepairUser(mapVo);
		
		return RepTeam;
	}
	/**
	 * @Description 
	 * 页面查询Tree 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssrRepTeamTree", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryAssrRepTeamTree(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeam = assMassRepairScheService.queryAssrRepTeamTree(mapVo);
		
		return RepTeam;
	}
	/**
	 * @Description 
	 * 根据TreeID查询员工数据 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssRepUser", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRepUser(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeam = assMassRepairScheService.queryAssRepUser(mapVo);
		
		return RepTeam;
	}
	/**
	 * @Description 
	 * 更新工作状态，工作/休息 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "updateWorkDa", method = RequestMethod.POST)
	@ResponseBody
	public String updateWorkDa(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeam = assMassRepairScheService.updateWorkDa(mapVo);
		
		return RepTeam;
	}
	/**
	 * @Description 
	 * 添加员工同时添加排班表默认上班 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "addAssMassRepairSche", method = RequestMethod.POST)
	@ResponseBody
	public String addAssMassRepairSche(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeam = assMassRepairScheService.addRepUser(mapVo);
		
		return RepTeam;
	}
	/**
	 * @Description 
	 * 添加员工同时添加排班表默认上班 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "updateWorkDay", method = RequestMethod.POST)
	@ResponseBody
	public String updateWorkDay(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeam = assMassRepairScheService.updateWorkDay(mapVo);
		
		return RepTeam;
	}
	/**
	 * @Description 删除数据 系统结构列名
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAssMassRepairSche", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAssMassRepairSche(@RequestParam Map<String,Object> mapVo, Model mode)throws Exception {
		//列信息
		List<Map> listVo = JSONArray.parseArray(mapVo.get("ParamVo").toString(), Map.class);
		try {
			for (Map map : listVo) {
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("acc_month", map.get("acc_year").toString().split("-")[1]);
				map.put("acc_year", map.get("acc_year").toString().split("-")[0]);
			}
			return assMassRepairScheService.deleteRepUser(listVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	/**
	 * @Description 
	 * 添加员工同时添加排班表默认上班 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "updateUserSort", method = RequestMethod.POST)
	@ResponseBody
	public String updateUserSort(@RequestParam Map<String, Object>  mapVo,Model mode) throws Exception {
		//列信息
			List<Map> listVo = JSONArray.parseArray(mapVo.get("ParamVo").toString(), Map.class);
			for (Map map : listVo) {
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
			}
		
		String RepTeam = assMassRepairScheService.updateUserSort(listVo);
		
		return RepTeam;
	}

}
