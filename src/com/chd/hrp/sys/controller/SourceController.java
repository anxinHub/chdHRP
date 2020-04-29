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
import com.chd.hrp.sys.entity.Source;
import com.chd.hrp.sys.serviceImpl.SourceServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class SourceController extends BaseController{
	private static Logger logger = Logger.getLogger(SourceController.class);
	
	
	@Resource(name = "sourceService")
	private final SourceServiceImpl sourceService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/source/sourceMainPage", method = RequestMethod.GET)
	public String sourceMainPage(Model mode) throws Exception {

		return "hrp/sys/source/sourceMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/source/sourceAddPage", method = RequestMethod.GET)
	public String sourceAddPage(Model mode) throws Exception {

		return "hrp/sys/source/sourceAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/source/addSource", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String sourceJson = sourceService.addSource(mapVo);

		return JSONObject.parseObject(sourceJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/source/querySource", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> querySource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		String source = sourceService.querySource(getPage(mapVo));

		return JSONObject.parseObject(source);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/source/deleteSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSource(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("source_code", id.split("@")[2]);
            mapVo.put("source_id", id.split("@")[3]);
            listVo.add(mapVo);
        }
		String sourceJson = sourceService.deleteBatchSource(listVo);
	   return JSONObject.parseObject(sourceJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/source/sourceUpdatePage", method = RequestMethod.GET)
	
	public String sourceUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Source source = new Source();
		source = sourceService.querySourceByCode(mapVo);
		mode.addAttribute("group_id", source.getGroup_id());
		mode.addAttribute("hos_id", source.getHos_id());
		mode.addAttribute("source_id", source.getSource_id());
		mode.addAttribute("source_code", source.getSource_code());
		mode.addAttribute("source_name", source.getSource_name());
		mode.addAttribute("nature_code", source.getNature_code());
		mode.addAttribute("nature_name", source.getNature_name());
		mode.addAttribute("spell_code", source.getSpell_code());
		mode.addAttribute("wbx_code", source.getWbx_code());
		mode.addAttribute("is_stop", source.getIs_stop());
		mode.addAttribute("note", source.getNote());
		mode.addAttribute("source_attr", source.getSource_attr());
		
		return "hrp/sys/source/sourceUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/source/updateSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		String sourceJson = sourceService.updateSource(mapVo);
		
		return JSONObject.parseObject(sourceJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/source/importSource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String sourceJson = sourceService.importSource(mapVo);
		
		return JSONObject.parseObject(sourceJson);
	}

}

