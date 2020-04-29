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
import com.chd.hrp.sys.entity.GroupDict;
import com.chd.hrp.sys.serviceImpl.GroupDictServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class GroupDictController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupDictController.class);
	
	
	@Resource(name = "groupDictService")
	private final GroupDictServiceImpl groupDictService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/groupdict/groupDictMainPage", method = RequestMethod.GET)
	public String groupDictMainPage(Model mode) throws Exception {

		return "hrp/sys/groupdict/groupDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/groupdict/groupDictAddPage", method = RequestMethod.GET)
	public String groupDictAddPage(Model mode) throws Exception {

		return "hrp/sys/groupdict/groupDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/groupdict/addGroupDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addGroupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String groupDictJson = groupDictService.addGroupDict(mapVo);

		return JSONObject.parseObject(groupDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/groupdict/queryGroupDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryGroupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String groupDict = groupDictService.queryGroupDict(getPage(mapVo));

		return JSONObject.parseObject(groupDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/groupdict/deleteGroupDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteGroupDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String groupDictJson = groupDictService.deleteBatchGroupDict(listVo);
	   return JSONObject.parseObject(groupDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/groupdict/groupDictUpdatePage", method = RequestMethod.GET)
	
	public String groupDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        GroupDict groupDict = new GroupDict();
		groupDict = groupDictService.queryGroupDictByCode(mapVo);
		mode.addAttribute("group_no", groupDict.getGroup_no());
		mode.addAttribute("group_id", groupDict.getGroup_id());
		mode.addAttribute("group_code", groupDict.getGroup_code());
		mode.addAttribute("group_name", groupDict.getGroup_name());
		mode.addAttribute("group_simple", groupDict.getGroup_simple());
		mode.addAttribute("user_code", groupDict.getUser_code());
		mode.addAttribute("create_date", groupDict.getCreate_date());
		mode.addAttribute("note", groupDict.getNote());
		mode.addAttribute("is_stop", groupDict.getIs_stop());
		
		return "hrp/sys/groupdict/groupDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/groupdict/updateGroupDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGroupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String groupDictJson = groupDictService.updateGroupDict(mapVo);
		
		return JSONObject.parseObject(groupDictJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/groupdict/importGroupDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importGroupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String groupDictJson = groupDictService.importGroupDict(mapVo);
		
		return JSONObject.parseObject(groupDictJson);
	}
	
}

