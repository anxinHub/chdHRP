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
import com.chd.hrp.sys.entity.GroupPerm;
import com.chd.hrp.sys.serviceImpl.GroupDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.GroupPermServiceImpl;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class GroupPermController extends BaseController {
	private static Logger logger = Logger.getLogger(GroupPermController.class);

	@Resource(name = "groupPermService")
	private final GroupPermServiceImpl groupPermService = null;

	@Resource(name = "groupDictService")
	private final GroupDictServiceImpl groupDictService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/groupperm/groupPermMainPage", method = RequestMethod.GET)
	public String groupPermMainPage(Model mode) throws Exception {

		return "hrp/sys/groupperm/groupPermMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/groupperm/groupPermAddPage", method = RequestMethod.GET)
	public String groupPermAddPage(Model mode) throws Exception {

		return "hrp/sys/groupperm/groupPermAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/groupperm/addGroupPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addGroupPerm(
			@RequestParam(value = "ParamVo") String paramVo,String group_id, Model mode)
			throws Exception {
		String groupPermJson = "";
		Map<String, Object> delMapVo = new HashMap<String, Object>();
		delMapVo.put("group_id", group_id);
		groupPermService.deleteGroupPermByGroupId(delMapVo);
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("group_id", id.split("@")[0]);// 实际实体类变量
			mapVo.put("mod_code", id.split("@")[1]);
			groupPermJson = groupPermService.addGroupPerm(mapVo);
		}

		return JSONObject.parseObject(groupPermJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/sys/groupperm/queryGroupPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupPerm(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String groupPerm = groupPermService.queryGroupPerm(getPage(mapVo));

		return JSONObject.parseObject(groupPerm);

	}
	
	//不分页查询
	@RequestMapping(value = "/hrp/sys/groupperm/queryAllGroupPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllGroupPerm(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String groupPerm = groupPermService.queryAllGroupPerm(mapVo);

		return JSONObject.parseObject(groupPerm);

	}

	// 删除
	@RequestMapping(value = "/hrp/sys/groupperm/deleteGroupPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteGroupPerm(
			String group_id, Model mode)
			throws Exception {
		Map<String, Object> delMapVo = new HashMap<String, Object>();
		delMapVo.put("group_id", group_id);
		String groupPermJson = groupPermService.deleteGroupPermByGroupId(delMapVo);
		
//		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
//		for (String id : paramVo.split(",")) {
//			Map<String, Object> mapVo = new HashMap<String, Object>();
//
//			mapVo.put("temp", id);// 实际实体类变量
//			listVo.add(mapVo);
//		}
//		String groupPermJson = groupPermService.deleteBatchGroupPerm(listVo);
		return JSONObject.parseObject(groupPermJson);

	}
	
	
	//根据groupId删除
	@RequestMapping(value = "/hrp/sys/groupperm/deleteGroupPermByGroupId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteGroupPermByGroupId(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String groupPermJson = groupPermService.deleteGroupPermByGroupId(mapVo);
		return JSONObject.parseObject(groupPermJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/groupperm/groupPermUpdatePage", method = RequestMethod.GET)
	public String groupPermUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		GroupPerm groupPerm = new GroupPerm();
		groupPerm = groupPermService.queryGroupPermByCode(mapVo);
		mode.addAttribute("group_id", groupPerm.getGroup_id());
		mode.addAttribute("mod_code", groupPerm.getMod_code());

		return "hrp/sys/groupperm/groupPermUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/sys/groupperm/updateGroupPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateGroupPerm(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String groupPermJson = groupPermService.updateGroupPerm(mapVo);

		return JSONObject.parseObject(groupPermJson);
	}

	// 导入
	@RequestMapping(value = "/hrp/sys/groupperm/importGroupPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importGroupPerm(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String groupPermJson = groupPermService.importGroupPerm(mapVo);

		return JSONObject.parseObject(groupPermJson);
	}

	// 查询groupDict菜单
	@RequestMapping(value = "/hrp/sys/groupperm/queryGroupDictByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupDictByMenu(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("is_stop", "0");
		String group = groupDictService.queryGroupDictByMenu(mapVo);

		return JSONObject.parseObject(group);

	}

}
