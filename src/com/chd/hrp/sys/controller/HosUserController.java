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
import com.chd.base.ChdToken;
import com.chd.base.SessionManager;
import com.chd.base.util.WisdomCloud;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.serviceImpl.UserServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class HosUserController extends BaseController{
	private static Logger logger = Logger.getLogger(HosUserController.class);
	
	
	@Resource(name = "userService")
	private final UserServiceImpl userService = null;
   
	
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/hosuser/hosUserMainPage", method = RequestMethod.GET)
	public String userMainPage(Model mode) throws Exception {

		return "hrp/sys/hosuser/userMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/hosuser/hosUserAddPage", method = RequestMethod.GET)
	public String userAddPage(Model mode) throws Exception {

		return "hrp/sys/hosuser/userAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/hosuser/addHosUser", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_pwd", "123456");
		mapVo.put("mod_code", "");
		mapVo.put("type_code", "0");
		String userJson = userService.addUser(mapVo);

		return JSONObject.parseObject(userJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/hosuser/queryHosUser", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("type_code", "0");
		String user = userService.queryUser(getPage(mapVo));

		return JSONObject.parseObject(user);
		
	}
	
	@RequestMapping(value = "/hrp/sys/hosuser/queryUserByRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUserByRole(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("type_code", "0");
		String user = userService.queryUserByRole(getPage(mapVo));

		return JSONObject.parseObject(user);
		
	}
	
	
	//删除
	@RequestMapping(value = "/hrp/sys/hosuser/deleteHosUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUser(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("type_code", "0");
            mapVo.put("user_id", id.split(";")[0]);//实际实体类变量
            listVo.add(mapVo);
        }
		String userJson = userService.deleteBatchUser(listVo);
	   return JSONObject.parseObject(userJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/hosuser/hosUserUpdatePage", method = RequestMethod.GET)
	
	public String userUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        User user = new User();
		user = userService.queryUserByCode(mapVo);
		mode.addAttribute("user_id", user.getUser_id());
		mode.addAttribute("group_id", user.getGroup_id());
		mode.addAttribute("hos_id", user.getHos_id());
		mode.addAttribute("user_code", user.getUser_code());
		mode.addAttribute("user_name", user.getUser_name());
		mode.addAttribute("user_pwd", user.getUser_pwd());
		mode.addAttribute("emp_id", user.getEmp_id());
		mode.addAttribute("emp_code", user.getEmp_code());
		mode.addAttribute("emp_name", user.getEmp_name());
		mode.addAttribute("type_code", user.getType_code());
		mode.addAttribute("is_stop", user.getIs_stop());
		mode.addAttribute("spell_code", user.getSpell_code());
		mode.addAttribute("wbx_code", user.getWbx_code());
		mode.addAttribute("note", user.getNote());
		mode.addAttribute("mod_code", user.getMod_code());
		mode.addAttribute("phone", user.getPhone());
		
		return "hrp/sys/hosuser/userUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/hosuser/updateHosUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_pwd", "123456");
		mapVo.put("mod_code", SessionManager.getModCode());
		mapVo.put("type_code", "0");
		String userJson = userService.updateUser(mapVo);
		
		return JSONObject.parseObject(userJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/hosuser/importHosUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("type_code", "0");
		String userJson = userService.importUser(mapVo);
		
		return JSONObject.parseObject(userJson);
	}

}

