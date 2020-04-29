package com.chd.hrp.htc.controller.task.basic;

import java.io.IOException;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.htc.entity.task.basic.HtcPeopleDict;
import com.chd.hrp.htc.service.task.basic.HtcPeopleDictService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * 
 * @Version: 1.0
 */

@Controller
public class HtcPeopleDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcPeopleDictController.class);

	@Resource(name = "htcPeopleDictService")
	private final HtcPeopleDictService htcPeopleDictService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/peopledict/htcPeopleDictMainPage", method = RequestMethod.GET)
	public String htcPeopleDictMainPage(Model mode) throws Exception {
		return "hrp/htc/task/basic/peopledict/htcPeopleDictMain";

	}

	// 添加页面
	
	@RequestMapping(value = "/hrp/htc/task/basic/peopledict/htcPeopleDictAddPage", method = RequestMethod.GET)
	public String htcPeopleDictAddPage(Model mode) throws Exception {
		return "hrp/htc/task/basic/peopledict/htcPeopleDictAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/peopledict/addHtcPeopleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcPeopleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String peopleDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("people_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("people_name").toString()));
		
		try {
			peopleDictJson = htcPeopleDictService.addHtcPeopleDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleDictJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleDictJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/peopledict/queryHtcPeopleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcPeopleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String peopleDict = htcPeopleDictService.queryHtcPeopleDict(getPage(mapVo));

		return JSONObject.parseObject(peopleDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/peopledict/deleteHtcPeopleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcPeopleDict(@RequestParam(value = "ParamVo") String ParamVo, Model mode) throws Exception {

		String peopleDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : ParamVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("people_code", ids[0]);
			listVo.add(mapVo);
		}
		
		try {
			peopleDictJson = htcPeopleDictService.deleteBatchHtcPeopleDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleDictJson = e.getMessage();
		}
		
		return JSONObject.parseObject(peopleDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/peopledict/htcPeopleDictUpdatePage", method = RequestMethod.GET)
	public String htcPeopleDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HtcPeopleDict peopleDict =  htcPeopleDictService.queryHtcPeopleDictByCode(mapVo);
		mode.addAttribute("group_id", peopleDict.getGroup_id());
		mode.addAttribute("hos_id", peopleDict.getHos_id());
		mode.addAttribute("copy_code", peopleDict.getCopy_code());
		mode.addAttribute("people_code", peopleDict.getPeople_code());
		mode.addAttribute("people_name", peopleDict.getPeople_name());
		mode.addAttribute("title_code", peopleDict.getTitle_code());
		mode.addAttribute("title_name", peopleDict.getTitle_name());
		mode.addAttribute("people_type_code", peopleDict.getPeople_type_code());
		mode.addAttribute("people_type_name", peopleDict.getPeople_type_name());
		mode.addAttribute("dept_no", peopleDict.getDept_no());
		mode.addAttribute("dept_id", peopleDict.getDept_id());
		mode.addAttribute("dept_code", peopleDict.getDept_code());
		mode.addAttribute("dept_name", peopleDict.getDept_name());
		mode.addAttribute("spell_code", peopleDict.getSpell_code());
		mode.addAttribute("wbx_code", peopleDict.getWbx_code());
		mode.addAttribute("is_stop", peopleDict.getIs_stop());
		mode.addAttribute("people_note", peopleDict.getPeople_note());

		return "hrp/htc/task/basic/peopledict/htcPeopleDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/peopledict/updateHtcPeopleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcPeopleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String peopleDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("people_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("people_name").toString()));
		
		try {
			peopleDictJson = htcPeopleDictService.updateHtcPeopleDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleDictJson = e.getMessage();
		}
		
		return JSONObject.parseObject(peopleDictJson);
	}
	
	   //同步系统平台人员
	@RequestMapping(value = "/hrp/htc/task/basic/peopledict/synchroHtcPeopleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> synchroHtcPeopleDict(Model mode)
			throws Exception {
		
       String peopleDictJson = "";
       
        Map<String, Object> mapVo = new HashMap<String, Object>();
		
        mapVo.put("group_id", SessionManager.getGroupId());
		
	    mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
	  		
			peopleDictJson = htcPeopleDictService.synchroHtcPeopleDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			peopleDictJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleDictJson);

	}
	
	/**
	*<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/htc/task/basic/peopledict/impHtcPeopleDict",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcPeopleTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String peopleDictJson = "";
		 
		try {
			
			peopleDictJson=htcPeopleDictService.impHtcPeopleDict(mapVo);
			
		} catch (Exception e) {
			
			peopleDictJson = e.getMessage();
		}
		return JSONObject.parseObject(peopleDictJson);
    }  
	
}
