/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.serviceImpl.UserServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class UserController extends BaseController{
	private static Logger logger = Logger.getLogger(UserController.class);
	
	
	@Resource(name = "userService")
	private final UserServiceImpl userService = null;
   
	
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/user/userMainPage", method = RequestMethod.GET)
	public String userMainPage(Model mode) throws Exception {

		return "hrp/sys/user/userMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/user/userAddPage", method = RequestMethod.GET)
	public String userAddPage(Model mode) throws Exception {

		return "hrp/sys/user/userAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/user/addUser", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		String hosId=SessionManager.getHosId();
		mapVo.put("hos_id", hosId);
		mapVo.put("user_pwd", "123456");
		mapVo.put("sj_id", SessionManager.getUserId());
		int typeCode=0;
		if(hosId.equals("0")){
			typeCode=1;//集团用户
		}
		mapVo.put("type_code", typeCode);
		
		try{
			String userJson = userService.addUser(mapVo);

			return JSONObject.parseObject(userJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/user/queryUser", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("type_code", SessionManager.getTypeCode());
		mapVo.put("user_id", SessionManager.getUserId());

		try{
			String user = userService.queryUser(getPage(mapVo));
			return JSONObject.parseObject(user);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
	}
	
	@RequestMapping(value = "/hrp/sys/user/queryAllUser", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAllUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		if(mapVo.get("type_code")==null){
			mapVo.put("type_code", SessionManager.getTypeCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		
		String user = userService.queryUser(getPage(mapVo));

		return JSONObject.parseObject(user);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/user/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUser(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
            mapVo.put("user_id", id.split(";")[0]);//实际实体类变量
            listVo.add(mapVo);
        }
		try{
			String userJson = userService.deleteBatchUser(listVo);
			return JSONObject.parseObject(userJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/user/userUpdatePage", method = RequestMethod.GET)
	
	public String userUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        User user = new User();
		user = userService.queryUserByCode(mapVo);
		mode.addAttribute("user_id", user.getUser_id());
		mode.addAttribute("user_code", user.getUser_code());
		mode.addAttribute("user_name", user.getUser_name());
		mode.addAttribute("emp_code", user.getEmp_code());
		mode.addAttribute("emp_id", user.getEmp_id());
		mode.addAttribute("emp_name", user.getEmp_name());
		mode.addAttribute("is_stop", user.getIs_stop());
		mode.addAttribute("spell_code", user.getSpell_code());
		mode.addAttribute("wbx_code", user.getWbx_code());
		mode.addAttribute("note", user.getNote());
		mode.addAttribute("phone", user.getPhone());
		mode.addAttribute("sj_user", user.getSj_user());
		
		return "hrp/sys/user/userUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/user/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		try{
			String userJson = userService.updateUser(mapVo);
			
			return JSONObject.parseObject(userJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/user/importUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("type_code", "1");
		String userJson = userService.importUser(mapVo);
		
		return JSONObject.parseObject(userJson);
	}
	
	//用户密码修改页面跳转
	@RequestMapping(value="/userPasswordPage",method=RequestMethod.GET)
	public String userPasswordPage(Model mode) throws Exception{
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_code", SessionManager.getUserCode());
		return "../../userPassword";
	}
	
	//用户密码修改
	@RequestMapping(value = "/hrp/sys/user/updateUserPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserPassword(@RequestParam Map<String,Object> mapVo,Model mode){
		String resultJson="";
		try {
			resultJson=userService.updateUserPassword(mapVo);
		}catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			resultJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			resultJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(resultJson);
	}

	//重置用户密码
	@RequestMapping(value = "/hrp/sys/user/reUserPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reUserPassword(@RequestParam Map<String,Object> mapVo,Model mode){
		String resultJson="";
		try {
			resultJson=userService.reUserPassword(mapVo);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			resultJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(resultJson);
	}
	
	//批量重置用户密码
	@RequestMapping(value = "/hrp/sys/user/updateBatchUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchUserPwd(@RequestParam(value = "ParamVo") String paramVo,Model mode){
		String resultJson="";
		try {
			resultJson=userService.updateBatchUserPwd(paramVo);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			resultJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(resultJson);
	}
}

