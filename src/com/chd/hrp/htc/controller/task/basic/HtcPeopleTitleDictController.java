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
import com.chd.hrp.htc.entity.task.basic.HtcPeopleTitleDict;
import com.chd.hrp.htc.service.task.basic.HtcPeopleTitleDictService;

/** 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcPeopleTitleDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcPeopleTitleDictController.class);

	@Resource(name = "htcPeopleTitleDictService")
	private final HtcPeopleTitleDictService htcPeopleTitleDictService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/peopletitledict/htcPeopleTitleDictMainPage", method = RequestMethod.GET)
	public String htcPeopleTitleDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/peopletitledict/htcPeopleTitleDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/peopletitledict/htcPeopleTitleDictAddPage", method = RequestMethod.GET)
	public String htcPeopleTitleDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/peopletitledict/htcPeopleTitleDictAdd";

	}
	

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/peopletitledict/addHtcPeopleTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcPeopleTitleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String peopleTitleDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());	
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
		
	  	try {
	  		peopleTitleDictJson = htcPeopleTitleDictService.addHtcPeopleTitleDict(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			peopleTitleDictJson = e.getMessage();
		}
	  	
		return JSONObject.parseObject(peopleTitleDictJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/peopletitledict/queryHtcPeopleTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcPeopleTitleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String peopleTitleDict = htcPeopleTitleDictService.queryHtcPeopleTitleDict(getPage(mapVo));

		return JSONObject.parseObject(peopleTitleDict);
		
	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/peopletitledict/deleteHtcPeopleTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePeopleTitleDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		String peopleTitleDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("title_code", ids[0]);
			listVo.add(mapVo);
		}
		
		try {
			peopleTitleDictJson = htcPeopleTitleDictService.deleteBatchHtcPeopleTitleDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleTitleDictJson = e.getMessage();
		}
		return JSONObject.parseObject(peopleTitleDictJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/peopletitledict/htcPeopleTitleDictUpdatePage", method = RequestMethod.GET)
	public String htcPeopleTitleDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		HtcPeopleTitleDict peopleTitleDict = htcPeopleTitleDictService.queryHtcPeopleTitleDictByCode(mapVo);
		mode.addAttribute("group_id", peopleTitleDict.getGroup_id());
		mode.addAttribute("hos_id", peopleTitleDict.getHos_id());
		mode.addAttribute("copy_code", peopleTitleDict.getCopy_code());
		mode.addAttribute("title_code", peopleTitleDict.getTitle_code());
		mode.addAttribute("title_name", peopleTitleDict.getTitle_name());
		mode.addAttribute("title_desc", peopleTitleDict.getTitle_desc());
		mode.addAttribute("is_stop", peopleTitleDict.getIs_stop());
		mode.addAttribute("spell_code", peopleTitleDict.getSpell_code());
		mode.addAttribute("wbx_code", peopleTitleDict.getWbx_code());

		return "hrp/htc/task/basic/peopletitledict/htcPeopleTitleDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/peopletitledict/updateHtcPeopleTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcPeopleTitleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String peopleTitleDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("title_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("title_name").toString()));
		
		try {
			peopleTitleDictJson = htcPeopleTitleDictService.updateHtcPeopleTitleDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleTitleDictJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleTitleDictJson);
	}
	
	//同步系统平台人员职称
	@RequestMapping(value = "/hrp/htc/task/basic/peopletitledict/synchroHtcPeopleTitleDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> synchroHtcPeopleTitleDict(Model mode)
			throws Exception {
		
       String peopleTitleDictJson = "";
       
        Map<String, Object> mapVo = new HashMap<String, Object>();
		
        mapVo.put("group_id", SessionManager.getGroupId());
		
	    mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
	  		
			peopleTitleDictJson = htcPeopleTitleDictService.synchroHtcPeopleTitleDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			peopleTitleDictJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleTitleDictJson);

	}
	
	/**
	*<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/htc/task/basic/peopletitledict/impHtcPeopleTitleDict",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcPeopleTitleDict(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		String peopleTitleDictJson = "";
		 
		try {
			
			peopleTitleDictJson=htcPeopleTitleDictService.impHtcPeopleTitleDict(mapVo);
			
		} catch (Exception e) {
			
			peopleTitleDictJson = e.getMessage();
			
		}
		
		return JSONObject.parseObject(peopleTitleDictJson);
		
    }  
}
