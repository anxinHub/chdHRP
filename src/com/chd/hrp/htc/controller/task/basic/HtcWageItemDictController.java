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
import com.chd.hrp.htc.entity.task.basic.HtcWageItemDict;
import com.chd.hrp.htc.service.task.basic.HtcWageItemDictService;

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
public class HtcWageItemDictController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcWageItemDictController.class);
	
	
	@Resource(name = "htcWageItemDictService")
	private final HtcWageItemDictService htcWageItemDictService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/wageitemdict/htcWageItemDictMainPage", method = RequestMethod.GET)
	public String htcWageItemDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/wageitemdict/htcWageItemDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/wageitemdict/htcWageItemDictAddPage", method = RequestMethod.GET)
	public String htcWageItemDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/wageitemdict/htcWageItemDictAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/wageitemdict/addHtcWageItemDict", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> addHtcWageItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String wageItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
		
		try {
			
			wageItemDictJson = htcWageItemDictService.addHtcWageItemDict(mapVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block

			wageItemDictJson = "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcWageItemDict\"}";
		}

		return JSONObject.parseObject(wageItemDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/wageitemdict/queryHtcWageItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcWageItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String wageItemDict = htcWageItemDictService.queryHtcWageItemDict(getPage(mapVo));

		return JSONObject.parseObject(wageItemDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/task/basic/wageitemdict/deleteHtcWageItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcWageItemDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String wageItemDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			String[] ids = id.split("@");
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("wage_item_code", ids[0]);
			
			listVo.add(mapVo);
		}
		
		try {
			
			wageItemDictJson = htcWageItemDictService.deleteBatchHtcWageItemDict(listVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			wageItemDictJson = "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcWageItemDict\"}";
		}
		return JSONObject.parseObject(wageItemDictJson);			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/wageitemdict/htcWageItemDictUpdatePage", method = RequestMethod.GET)
	
	public String htcWageItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		HtcWageItemDict wageItemDict = htcWageItemDictService.queryHtcWageItemDictByCode(mapVo);
		
		mode.addAttribute("group_id", wageItemDict.getGroup_id());
		mode.addAttribute("hos_id", wageItemDict.getHos_id());
		mode.addAttribute("copy_code", wageItemDict.getCopy_code());
		mode.addAttribute("wage_item_code", wageItemDict.getWage_item_code());
		mode.addAttribute("wage_item_name", wageItemDict.getWage_item_name());
		mode.addAttribute("is_stop", wageItemDict.getIs_stop());
		mode.addAttribute("remark", wageItemDict.getRemark());
		mode.addAttribute("sort_code", wageItemDict.getSort_code());
		mode.addAttribute("spell_code", wageItemDict.getSpell_code());
		mode.addAttribute("wbx_code", wageItemDict.getWbx_code());
		
		return "hrp/htc/task/basic/wageitemdict/htcWageItemDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/wageitemdict/updateHtcWageItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcWageItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String wageItemDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
	
		try {
			
			wageItemDictJson = htcWageItemDictService.updateHtcWageItemDict(mapVo);
			
		} catch (Exception e) {

			wageItemDictJson = "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 updateHtcWageItemDict\"}";
		}

		return JSONObject.parseObject(wageItemDictJson);
	}
	
	/**
	*<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/htc/task/basic/wageitemdict/impHtcWageItemDict",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcWageItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String peopleTypeDictJson = "";
		 
		try {
			
			 peopleTypeDictJson=htcWageItemDictService.impHtcWageItemDict(mapVo);
			 
		} catch (Exception e) {
			
			peopleTypeDictJson = e.getMessage();
		}
		
		return JSONObject.parseObject(peopleTypeDictJson);
    }  
	
}

