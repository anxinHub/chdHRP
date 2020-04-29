
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
import com.chd.hrp.htcg.entity.info.HtcgClpStepDict;
import com.chd.hrp.htcg.service.info.HtcgClpStepDictService;

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
public class HtcgClpStepDictController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgClpStepDictController.class);
	
	
	@Resource(name = "htcgClpStepDictService")
	private final HtcgClpStepDictService htcgClpStepDictService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/info/clpstepdict/htcgClpStepDictMainPage", method = RequestMethod.GET)
	public String htcgClpStepDictMainPage(Model mode) throws Exception {

		return "hrp/htcg/info/clpstepdict/htcgClpStepDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/info/clpstepdict/htcgClpStepDictAddPage", method = RequestMethod.GET)
	public String htcgClpStepDictAddPage(Model mode) throws Exception {

		return "hrp/htcg/info/clpstepdict/htcgClpStepDictAdd";

	}
	// 保存
	@RequestMapping(value = "/hrp/htcg/info/clpstepdict/addHtcgClpStepDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHtcgClpStepDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String clpStepDictJson;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("clp_step_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("clp_step_name").toString()));
        
		try {
			clpStepDictJson = htcgClpStepDictService.addHtcgClpStepDict(mapVo);
		} catch (Exception e) {
			clpStepDictJson=e.getMessage();
		}
		return JSONObject.parseObject(clpStepDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/info/clpstepdict/queryHtcgClpStepDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgClpStepDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String clpStepDict = htcgClpStepDictService.queryHtcgClpStepDict(getPage(mapVo));
		return JSONObject.parseObject(clpStepDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/info/clpstepdict/deleteHtcgClpStepDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgClpStepDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("clp_step_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String clpStepDictJson;
		try {
			clpStepDictJson = htcgClpStepDictService.deleteBatchHtcgClpStepDict(listVo);
		} catch (Exception e) {
			clpStepDictJson=e.getMessage();
		}
	   return JSONObject.parseObject(clpStepDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/info/clpstepdict/htcgClpStepDictUpdatePage", method = RequestMethod.GET)
	
	public String htcgClpStepDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

        HtcgClpStepDict htcgClpStepDict = htcgClpStepDictService.queryHtcgClpStepDictByCode(mapVo);
		mode.addAttribute("group_id", htcgClpStepDict.getGroup_id());
		mode.addAttribute("hos_id", htcgClpStepDict.getHos_id());
		mode.addAttribute("copy_code", htcgClpStepDict.getCopy_code());
		mode.addAttribute("clp_step_code", htcgClpStepDict.getClp_step_code());
		mode.addAttribute("clp_step_name", htcgClpStepDict.getClp_step_name());
		mode.addAttribute("spell_code", htcgClpStepDict.getSpell_code());
		mode.addAttribute("wbx_code", htcgClpStepDict.getWbx_code());
		mode.addAttribute("is_stop", htcgClpStepDict.getIs_stop());
		
		return "hrp/htcg/info/clpstepdict/htcgClpStepDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/info/clpstepdict/updateHtcgClpStepDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgClpStepDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("clp_step_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("clp_step_name").toString()));
        
		String clpStepDictJson;
		
		try {
			clpStepDictJson = htcgClpStepDictService.updateHtcgClpStepDict(mapVo);
		} catch (Exception e) {
			clpStepDictJson=e.getMessage();
		}
		
		return JSONObject.parseObject(clpStepDictJson);
	}
   
    
	@RequestMapping(value = "/hrp/htcg/info/clpstepdict/importHtcgClpStepDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgClpStepDict(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		String clpStepDictJson;
		
		try {

			clpStepDictJson =  htcgClpStepDictService.importHtcgClpStepDict(mapVo);

		}
		catch (Exception e) {

			clpStepDictJson = e.getMessage();

		}

		return JSONObject.parseObject(clpStepDictJson);
	}
}

