/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
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
import com.chd.hrp.sys.entity.RolePerm;
import com.chd.hrp.sys.serviceImpl.RolePermServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class HosRolePermController extends BaseController{
	private static Logger logger = Logger.getLogger(HosRolePermController.class);
	
	
	@Resource(name = "rolePermService")
	private final RolePermServiceImpl rolePermService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/hosroleperm/hosRolePermMainPage", method = RequestMethod.GET)
	public String rolePermMainPage(Model mode) throws Exception {

		return "hrp/sys/hosroleperm/rolePermMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/hosroleperm/hosRolePermAddPage", method = RequestMethod.GET)
	public String rolePermAddPage(Model mode) throws Exception {

		return "hrp/sys/hosroleperm/rolePermAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/hosroleperm/addHosRolePerm", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addRolePerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		try{
			String rolePermJson = rolePermService.addRolePerm(mapVo);
			return JSONObject.parseObject(rolePermJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":"+e.getMessage()+"}");
		}
		
		
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/hosroleperm/queryHosRolePerm", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryRolePerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String rolePerm = rolePermService.queryRolePerm(getPage(mapVo));

		return JSONObject.parseObject(rolePerm);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/hosroleperm/deleteHosRolePerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRolePerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String rolePermJson = rolePermService.deleteRolePerm(mapVo);
	   return JSONObject.parseObject(rolePermJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/hosroleperm/hosRolePermUpdatePage", method = RequestMethod.GET)
	
	public String rolePermUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        RolePerm rolePerm = new RolePerm();
		rolePerm = rolePermService.queryRolePermByCode(mapVo);
		mode.addAttribute("group_id", rolePerm.getGroup_id());
		mode.addAttribute("hos_id", rolePerm.getHos_id());
		mode.addAttribute("role_id", rolePerm.getRole_id());
		mode.addAttribute("perm_code", rolePerm.getPerm_code());
		mode.addAttribute("mod_code", rolePerm.getMod_code());
		
		return "hrp/sys/hosroleperm/rolePermUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/hosroleperm/updateHosRolePerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRolePerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String rolePermJson = rolePermService.updateRolePerm(mapVo);
		
		return JSONObject.parseObject(rolePermJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/hosroleperm/importHosRolePerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importRolePerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String rolePermJson = rolePermService.importRolePerm(mapVo);
		
		return JSONObject.parseObject(rolePermJson);
	}
	
	//查询上级编码
	@RequestMapping(value = "/hrp/sys/hosroleperm/queryHosRolePermByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRolePermByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String rolePerm = rolePermService.queryRolePermByMenu(getPage(mapVo));

		return JSONObject.parseObject(rolePerm);
		
	}

}

