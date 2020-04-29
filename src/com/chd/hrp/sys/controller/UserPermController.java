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
import com.chd.hrp.sys.entity.UserPerm;
import com.chd.hrp.sys.serviceImpl.UserPermServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class UserPermController extends BaseController{
	private static Logger logger = Logger.getLogger(UserPermController.class);
	
	
	@Resource(name = "userPermService")
	private final UserPermServiceImpl userPermService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/userperm/userPermMainPage", method = RequestMethod.GET)
	public String userPermMainPage(Model mode) throws Exception {

		return "hrp/sys/userperm/userPermMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/userperm/userPermAddPage", method = RequestMethod.GET)
	public String userPermAddPage(Model mode) throws Exception {

		return "hrp/sys/userperm/userPermAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/userperm/addUserPerm", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addUserPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null || mapVo.get("hos_id")==""){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		try{
			String userPermJson = userPermService.addUserPerm(mapVo);
			return JSONObject.parseObject(userPermJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/userperm/queryUserPerm", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryUserPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		String userPerm = userPermService.queryUserPerm(getPage(mapVo));

		return JSONObject.parseObject(userPerm);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/userperm/deleteUserPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUserPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		String userPermJson = userPermService.deleteUserPerm(mapVo);
	   return JSONObject.parseObject(userPermJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/userperm/userPermUpdatePage", method = RequestMethod.GET)
	
	public String userPermUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        UserPerm userPerm = new UserPerm();
		userPerm = userPermService.queryUserPermByCode(mapVo);
		mode.addAttribute("group_id", userPerm.getGroup_id());
		mode.addAttribute("hos_id", userPerm.getHos_id());
		mode.addAttribute("user_id", userPerm.getUser_id());
		mode.addAttribute("perm_code", userPerm.getPerm_code());
		mode.addAttribute("mod_code", userPerm.getMod_code());
		
		return "hrp/sys/userperm/userPermUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/userperm/updateUserPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		String userPermJson = userPermService.updateUserPerm(mapVo);
		
		return JSONObject.parseObject(userPermJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/userperm/importUserPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importUserPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		String userPermJson = userPermService.importUserPerm(mapVo);
		
		return JSONObject.parseObject(userPermJson);
	}
	
	
	// 查询权限菜单
	@RequestMapping(value = "/hrp/sys/userperm/queryUserPermByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUserPermByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null || mapVo.get("hos_id")==""){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		try{
			String userPerm = userPermService.queryUserPermByMenu(mapVo);
			return JSONObject.parseObject(userPerm);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	@RequestMapping(value = "/hrp/sys/userperm/queryModPermByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryModPermByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null || mapVo.get("hos_id")=="") {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		mapVo.put("type_code", SessionManager.getTypeCode());
		try{
			String userPerm = userPermService.queryModPermByTree(mapVo);
			return JSONObject.parseObject(userPerm);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}

	}

}

