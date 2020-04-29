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
import com.chd.hrp.sys.entity.RoleUser;
import com.chd.hrp.sys.serviceImpl.RoleUserServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class RoleUserController extends BaseController{
	private static Logger logger = Logger.getLogger(RoleUserController.class);
	
	
	@Resource(name = "roleUserService")
	private final RoleUserServiceImpl roleUserService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/roleuser/roleUserMainPage", method = RequestMethod.GET)
	public String roleUserMainPage(Model mode) throws Exception {

		return "hrp/sys/roleuser/roleUserMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/roleuser/roleUserAddPage", method = RequestMethod.GET)
	public String roleUserAddPage(Model mode) throws Exception {

		return "hrp/sys/roleuser/roleUserAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/roleuser/addRoleUser", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addRoleUser(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String roleUserJson = "";
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("id", "");
			mapVo.put("user_id", id.split("@")[0]);
            mapVo.put("group_id", id.split("@")[1]);
            mapVo.put("hos_id", id.split("@")[2]);
            mapVo.put("role_id", id.split("@")[3]);
            roleUserJson = roleUserService.addRoleUser(mapVo);
        }
		
	   return JSONObject.parseObject(roleUserJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/roleuser/queryRoleUser", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryRoleUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String roleUser = roleUserService.queryRoleUser(getPage(mapVo));

		return JSONObject.parseObject(roleUser);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/roleuser/deleteRoleUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleUser(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		String paramVo= map.get("ParamVo").toString();
		String roleUserJson = "";
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("role_id", id.split("@")[0]);
            mapVo.put("group_id", id.split("@")[1]);
            mapVo.put("hos_id", id.split("@")[2]);
            mapVo.put("user_id", id.split("@")[3]);
            roleUserJson = roleUserService.deleteRoleUser(mapVo);
        }
		
	   return JSONObject.parseObject(roleUserJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/roleuser/roleUserUpdatePage", method = RequestMethod.GET)
	
	public String roleUserUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        RoleUser roleUser = new RoleUser();
		roleUser = roleUserService.queryRoleUserByCode(mapVo);
		mode.addAttribute("id", roleUser.getId());
		mode.addAttribute("group_id", roleUser.getGroup_id());
		mode.addAttribute("hos_id", roleUser.getHos_id());
		mode.addAttribute("user_id", roleUser.getUser_id());
		mode.addAttribute("role_id", roleUser.getRole_id());
		
		return "hrp/sys/roleuser/roleUserUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/roleuser/updateRoleUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRoleUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String roleUserJson = roleUserService.updateRoleUser(mapVo);
		
		return JSONObject.parseObject(roleUserJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/roleuser/importRoleUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importRoleUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String roleUserJson = roleUserService.importRoleUser(mapVo);
		
		return JSONObject.parseObject(roleUserJson);
	}

}

