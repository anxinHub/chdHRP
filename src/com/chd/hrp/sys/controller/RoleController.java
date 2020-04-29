/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.sys.entity.Role;
import com.chd.hrp.sys.entity.RoleUser;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.serviceImpl.RoleServiceImpl;
import com.chd.hrp.sys.serviceImpl.RoleUserServiceImpl;
import com.chd.hrp.sys.serviceImpl.UserServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class RoleController extends BaseController{
	private static Logger logger = Logger.getLogger(RoleController.class);
	
	
	@Resource(name = "roleService")
	private final RoleServiceImpl roleService = null;
	
	@Resource(name = "roleUserService")
	private final RoleUserServiceImpl roleUserService = null;
	
	@Resource(name = "userService")
	private final UserServiceImpl userService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/role/roleMainPage", method = RequestMethod.GET)
	public String roleMainPage(Model mode) throws Exception {

		return "hrp/sys/role/roleMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/role/roleAddPage", method = RequestMethod.GET)
	public String roleAddPage(Model mode) throws Exception {

		return "hrp/sys/role/roleAdd";

	}
	
	@RequestMapping(value = "/hrp/sys/role/roleUserPage", method = RequestMethod.GET)
	public String roleUserPage(Model mode,String role_id,String group_id,String hos_id) throws Exception {
		mode.addAttribute("role_id", role_id);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		return "hrp/sys/role/roleUser";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/role/addRole", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String reJson=null;
		try{
			reJson=roleService.addRole(mapVo);
		}catch(Exception e){
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/role/queryRole", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", "0");
		String role = roleService.queryRole(getPage(mapVo));

		return JSONObject.parseObject(role);
		
	}
	
	@RequestMapping(value = "/hrp/sys/role/queryUserByRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUserByRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String reJson=null;
		try{
			reJson=roleService.queryRoleUser(getPage(mapVo));
			
		}catch(Exception e){
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	// 查询菜单
	@RequestMapping(value = "/hrp/sys/role/queryRoleByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRoleByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("type_code", SessionManager.getTypeCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String role = roleService.queryRoleByMenu(mapVo);

		return JSONObject.parseObject(role);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/role/deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRole(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
            mapVo.put("role_id", id.split("@")[0]);
            mapVo.put("group_id", id.split("@")[1]);
            mapVo.put("hos_id", id.split("@")[2]);
            listVo.add(mapVo);
        }
		
		String roleJson=null;
		try{
			roleJson = roleService.deleteBatchRole(listVo);
		}catch(Exception e){
			roleJson="{\"error\":\""+e.getMessage()+"\"}";
		}
	   return JSONObject.parseObject(roleJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/role/roleUpdatePage", method = RequestMethod.GET)
	
	public String roleUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Role role = new Role();
        mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		role = roleService.queryRoleByCode(mapVo);
		mode.addAttribute("role_id", role.getRole_id());
		mode.addAttribute("group_id", role.getGroup_id());
		mode.addAttribute("hos_id", role.getHos_id());
		mode.addAttribute("role_name", role.getRole_name());
		mode.addAttribute("is_stop", role.getIs_stop());
		mode.addAttribute("spell_code", role.getSpell_code());
		mode.addAttribute("wbx_code", role.getWbx_code());
		mode.addAttribute("note", role.getNote());
		mode.addAttribute("sj_user", role.getSj_user());
		
		return "hrp/sys/role/roleUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/role/updateRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String roleJson = roleService.updateRole(mapVo);
		
		return JSONObject.parseObject(roleJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/role/importRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String roleJson = roleService.importRole(mapVo);
		
		return JSONObject.parseObject(roleJson);
	}

	// 角色添加用户，刨掉已经添加的用户
	@RequestMapping(value = "/hrp/sys/role/queryRoleUserByAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryRoleUserByAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("type_code", SessionManager.getTypeCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String role = roleService.queryRoleUserByAdd(getPage(mapVo));

		return JSONObject.parseObject(role);
		
	}
	
}

