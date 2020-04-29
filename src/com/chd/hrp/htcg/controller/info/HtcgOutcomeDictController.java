
/*
 *
 */package com.chd.hrp.htcg.controller.info;
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
import com.chd.hrp.htcg.entity.info.HtcgOutcomeDict;
import com.chd.hrp.htcg.service.info.HtcgOutcomeDictService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgOutcomeDictController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgOutcomeDictController.class);
	
	
	@Resource(name = "htcgOutcomeDictService")
	private final HtcgOutcomeDictService htcgOutcomeDictService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/info/outcomedict/htcgOutcomeDictMainPage", method = RequestMethod.GET)
	public String htcgOutcomeDictMainPage(Model mode) throws Exception {

		return "hrp/htcg/info/outcomedict/htcgOutcomeDictMain";

	}
	


	// 添加页面 
	@RequestMapping(value = "/hrp/htcg/info/outcomedict/htcgOutcomeDictAddPage", method = RequestMethod.GET)
	public String htcgOutcomeDictAddPage(Model mode) throws Exception {

		return "hrp/htcg/info/outcomedict/htcgOutcomeDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htcg/info/outcomedict/addHtcgOutcomeDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHtcgOutcomeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
       
		String htcgoutcomeDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("outcome_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("outcome_name").toString()));
		
		try {
			
			htcgoutcomeDictJson = htcgOutcomeDictService.addHtcgOutcomeDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			htcgoutcomeDictJson = e.getMessage();
		}
		

		return JSONObject.parseObject(htcgoutcomeDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/info/outcomedict/queryHtcgOutcomeDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgOutcomeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String outcomeDict = htcgOutcomeDictService.queryHtcgOutcomeDict(getPage(mapVo));

		return JSONObject.parseObject(outcomeDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/info/outcomedict/deleteHtcgOutcomeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgOutcomeDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("outcome_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String htcgoutcomeDictJson = "";
		try {
			htcgoutcomeDictJson = htcgOutcomeDictService.deleteBatchHtcgOutcomeDict(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcgoutcomeDictJson = e.getMessage();
		}
	   return JSONObject.parseObject(htcgoutcomeDictJson);
			
	}
	
	// 修改页面跳转 
	@RequestMapping(value = "/hrp/htcg/info/outcomedict/htcgOutcomeDictUpdatePage", method = RequestMethod.GET)
	
	public String htcgOutcomeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        HtcgOutcomeDict outcomeDict =  htcgOutcomeDictService.queryHtcgOutcomeDictByCode(mapVo);
		mode.addAttribute("group_id", outcomeDict.getGroup_id());
		mode.addAttribute("hos_id", outcomeDict.getHos_id());
		mode.addAttribute("copy_code", outcomeDict.getCopy_code());
		mode.addAttribute("outcome_code", outcomeDict.getOutcome_code());
		mode.addAttribute("outcome_name", outcomeDict.getOutcome_name());
		mode.addAttribute("spell_code", outcomeDict.getSpell_code());
		mode.addAttribute("wbx_code", outcomeDict.getWbx_code());
		mode.addAttribute("is_stop", outcomeDict.getIs_stop());
		
		return "hrp/htcg/info/outcomedict/htcgOutcomeDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/info/outcomedict/updateHtcgOutcomeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgOutcomeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcgoutcomeDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("outcome_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("outcome_name").toString()));
		try {
			htcgoutcomeDictJson = htcgOutcomeDictService.updateHtcgOutcomeDict(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcgoutcomeDictJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcgoutcomeDictJson);
	}
	
	@RequestMapping(value = "/hrp/htcg/info/identityType/importHtcgOutcomeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgOutcomeDict(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String htcgoutcomeDictJson = "";
		try {
			htcgoutcomeDictJson = htcgOutcomeDictService.importHtcgOutcomeDict(mapVo);
		}
		catch (Exception e) {
			htcgoutcomeDictJson = e.getMessage();
		}
		return JSONObject.parseObject(htcgoutcomeDictJson);
	}

}

