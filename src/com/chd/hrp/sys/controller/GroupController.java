/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.hrp.sys.entity.Group;
import com.chd.hrp.sys.entity.User;
import com.chd.hrp.sys.serviceImpl.GroupDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.GroupServiceImpl;
import com.chd.hrp.sys.serviceImpl.UserServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class GroupController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupController.class);
	
	
	@Resource(name = "groupService")
	private final GroupServiceImpl groupService = null;
	
	@Resource(name = "groupDictService")
	private final GroupDictServiceImpl groupDictService = null;
	
	@Resource(name = "userService")
	private final UserServiceImpl userService = null;
   
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/group/groupMainPage", method = RequestMethod.GET)
	public String groupMainPage(Model mode) throws Exception {

		return "hrp/sys/group/groupMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/group/groupAddPage", method = RequestMethod.GET)
	public String groupAddPage(Model mode) throws Exception {

		return "hrp/sys/group/groupAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/group/addGroup", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		try{
			String groupJson = groupService.addGroup(mapVo);
			return JSONObject.parseObject(groupJson);
		}catch(Exception e){

			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
		
	}
	
	@RequestMapping(value = "/hrp/sys/group/addGroupDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addGroupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String dictType = mapVo.get("dictType").toString();
		if(dictType.equals("0")){//编码变更
			groupService.updateGroupCode(mapVo);
		}else{//名称变更
			groupService.updateGroupName(mapVo);
		}
		
		String groupDictJson = null;
		
		if(Integer.parseInt(mapVo.get("reserve").toString()) == 1){
			
			 groupDictJson = groupDictService.addGroupDict(mapVo);
			 
		}else {
			
			groupDictJson =  "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}

		return JSONObject.parseObject(groupDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/group/queryGroup", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String group = groupService.queryGroup(getPage(mapVo));

		return JSONObject.parseObject(group);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/group/deleteGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteGroup(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String groupJson = "";
		try{
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
	            mapVo.put("group_id", id);//实际实体类变量
	            groupJson = groupService.deleteGroup(mapVo);
	           
	        }
			 return JSONObject.parseObject(groupJson);
		}catch(Exception e){
			 return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
		
	  
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/group/groupUpdatePage", method = RequestMethod.GET)
	
	public String groupUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Group group = new Group();
		group = groupService.queryGroupByCode(mapVo);
		mode.addAttribute("group_id", group.getGroup_id());
		mode.addAttribute("group_code", group.getGroup_code());
		mode.addAttribute("group_name", group.getGroup_name());
		mode.addAttribute("group_simple", group.getGroup_simple());
		mode.addAttribute("group_sort", group.getGroup_sort());
		mode.addAttribute("group_legal", group.getGroup_legal());
		mode.addAttribute("group_contact", group.getGroup_contact());
		mode.addAttribute("group_phone", group.getGroup_phone());
		mode.addAttribute("group_email", group.getGroup_email());
		mode.addAttribute("group_address", group.getGroup_address());
		mode.addAttribute("is_stop", group.getIs_stop());
		mode.addAttribute("note", group.getNote());
		mode.addAttribute("spell_code", group.getSpell_code());
		mode.addAttribute("wbx_code", group.getWbx_code());
		return "hrp/sys/group/groupUpdate";
	}
	
	
	//根据编码查询
	@RequestMapping(value = "/hrp/sys/group/queryGroupByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupByCode(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		
		Group group  = groupService.queryGroupByCode(mapVo);
		//String user = userService.queryUserByAdmin(mapVo);
		//group.setUser_code(user);
		Map<String, Object> reMap=new HashMap<String, Object>();
//		JSONObject jsonObj = new JSONObject();
//		jsonObj.put("group", group);
//		response.getWriter().print(jsonObj.toString());
		reMap.put("group", group);
		return reMap;
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/group/updateGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try{
			String groupJson = groupService.updateGroup(mapVo);
			
			return JSONObject.parseObject(groupJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	

	
	// 导入
	@RequestMapping(value = "/hrp/sys/group/importGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String groupJson = groupService.importGroup(mapVo);
		
		return JSONObject.parseObject(groupJson);
	}
	
	//查询group菜单
	@RequestMapping(value = "/hrp/sys/group/queryGroupByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String group = groupService.queryGroupByMenu(mapVo);

		return JSONObject.parseObject(group);
		
	}

}

