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
import com.chd.hrp.sys.entity.Perm;
import com.chd.hrp.sys.serviceImpl.InfoDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.PermServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class PermController extends BaseController{
	private static Logger logger = Logger.getLogger(PermController.class);
	
	
	@Resource(name = "permService")
	private final PermServiceImpl permService = null;
   
	@Resource(name = "infoDictService")
	private final InfoDictServiceImpl infoDictService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/perm/permMainPage", method = RequestMethod.GET)
	public String permMainPage(Model mode) throws Exception {

		return "hrp/sys/perm/permMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/perm/permAddPage", method = RequestMethod.GET)
	public String permAddPage(Model mode) throws Exception {

		return "hrp/sys/perm/permAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/perm/addPerm", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addPerm(@RequestParam(value = "ParamVo") String paramVo,String group_id,String hos_id, Model mode) throws Exception {
		
		String permJson = "";
		Map<String, Object> delMapVo = new HashMap<String, Object>();
		delMapVo.put("group_id", group_id);
		delMapVo.put("hos_id", hos_id);
		permService.deletePerm(delMapVo);
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", id.split("@")[0]);// 实际实体类变量
			mapVo.put("mod_code", id.split("@")[1]);
			permJson = permService.addPerm(mapVo);
		}
		return JSONObject.parseObject(permJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/perm/queryPerm", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String perm = permService.queryPerm(getPage(mapVo));

		return JSONObject.parseObject(perm);
		
	}
	
	@RequestMapping(value = "/hrp/sys/perm/queryAllPerm", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAllPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String perm = permService.queryAllPerm(mapVo);

		return JSONObject.parseObject(perm);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/perm/deletePerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String permJson = permService.deletePerm(mapVo);
		
	   return JSONObject.parseObject(permJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/perm/permUpdatePage", method = RequestMethod.GET)
	
	public String permUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Perm perm = new Perm();
		perm = permService.queryPermByCode(mapVo);
		mode.addAttribute("group_id", perm.getGroup_id());
		mode.addAttribute("hos_id", perm.getHos_id());
		mode.addAttribute("mod_code", perm.getMod_code());
		
		return "hrp/sys/perm/permUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/perm/updatePerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String permJson = permService.updatePerm(mapVo);
		
		return JSONObject.parseObject(permJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/perm/importPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String permJson = permService.importPerm(mapVo);
		
		return JSONObject.parseObject(permJson);
	}
	
	@RequestMapping(value = "/hrp/sys/perm/queryInfoDictByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupDictByMenu(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("is_stop", "0");
		
		String group = infoDictService.queryInfoDictByMenu(mapVo);

		return JSONObject.parseObject(group);

	}

}

