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
public class HosRoleController extends BaseController{
	private static Logger logger = Logger.getLogger(HosRoleController.class);
	
	
	@Resource(name = "roleService")
	private final RoleServiceImpl roleService = null;
   
	@Resource(name = "roleUserService")
	private final RoleUserServiceImpl roleUserService = null;
	
	@Resource(name = "userService")
	private final UserServiceImpl userService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/hosrole/hosRoleMainPage", method = RequestMethod.GET)
	public String roleMainPage(Model mode) throws Exception {

		return "hrp/sys/hosrole/roleMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/hosrole/hosRoleAddPage", method = RequestMethod.GET)
	public String roleAddPage(Model mode) throws Exception {

		return "hrp/sys/hosrole/roleAdd";

	}
	
	@RequestMapping(value = "/hrp/sys/hosrole/queryUserByRole", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryUserByRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		List<User> userList = new ArrayList<User>();
		List<RoleUser> list = roleUserService.queryUserByRole(mapVo);
		for(RoleUser roleUser : list){
			mapVo.put("user_id", roleUser.getUser_id());
			User user = userService.queryUserByCode(mapVo);
			userList.add(user);
		}
		String result = JsonListBeanUtil.listToJson(userList);
		return JSONObject.parseObject(result);
		
	}
	
	@RequestMapping(value = "/hrp/sys/hosrole/hosRoleUserPage", method = RequestMethod.GET)
	public String roleUserPage(Model mode,String role_id,String group_id,String hos_id) throws Exception {
		mode.addAttribute("role_id", role_id);
		mode.addAttribute("group_id", group_id);
		mode.addAttribute("hos_id", hos_id);
		return "hrp/sys/hosrole/roleUser";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/hosrole/addHosRole", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String roleJson = roleService.addRole(mapVo);

		return JSONObject.parseObject(roleJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/hosrole/queryHosRole", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String role = roleService.queryRole(getPage(mapVo));

		return JSONObject.parseObject(role);
		
	}
	
	// 查询菜单
		@RequestMapping(value = "/hrp/sys/hosrole/queryHosRoleByMenu", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryRoleByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			String role = roleService.queryRoleByMenu(mapVo);

			return JSONObject.parseObject(role);
			
		}
	
	//删除
	@RequestMapping(value = "/hrp/sys/hosrole/deleteHosRole", method = RequestMethod.POST)
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
		String roleJson = roleService.deleteBatchRole(listVo);
	   return JSONObject.parseObject(roleJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/hosrole/hosRoleUpdatePage", method = RequestMethod.GET)
	
	public String roleUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        Role role = new Role();
		role = roleService.queryRoleByCode(mapVo);
		mode.addAttribute("role_id", role.getRole_id());
		mode.addAttribute("group_id", role.getGroup_id());
		mode.addAttribute("hos_id", role.getHos_id());
		mode.addAttribute("role_name", role.getRole_name());
		mode.addAttribute("is_stop", role.getIs_stop());
		mode.addAttribute("spell_code", role.getSpell_code());
		mode.addAttribute("wbx_code", role.getWbx_code());
		mode.addAttribute("note", role.getNote());
		
		return "hrp/sys/hosrole/roleUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/hosrole/updateHosRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String roleJson = roleService.updateRole(mapVo);
		
		return JSONObject.parseObject(roleJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/hosrole/importHosRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String roleJson = roleService.importRole(mapVo);
		
		return JSONObject.parseObject(roleJson);
	}

}

