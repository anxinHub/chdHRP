package com.chd.hrp.ass.controller.repair.assfaulttype;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.service.repair.assfaulttype.AssFaultTypeService;


@Controller
@RequestMapping(value = "hrp/ass/repair/assFaultType")
public class AssFaultTypeController {
	

	@Resource(name = "assFaultTypeService")
	private final AssFaultTypeService assFaultTypeService = null;
	
	private static Logger logger = Logger.getLogger(AssFaultTypeController.class);

	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "assFaultTypeMainPage", method = RequestMethod.GET)
	public String assFaultTypeMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/assfaulttype/assFaultTypeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "assFaultTypeAddPage", method = RequestMethod.GET)
	public String assFaultTypeAddPage(Model mode) throws Exception {

		return "hrp/ass/repair/assfaulttype/assFaultTypeAdd";

	}
	/**
	 * @Description 
	 * 修改页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assFaultTypeUpdatePage", method = RequestMethod.GET)
	public String assFaultTypeUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> faultTypeMap = assFaultTypeService.queryAssFaultTypeByCode(mapVo);
		mode.addAttribute("fau_code",faultTypeMap.get("FAU_CODE"));
		mode.addAttribute("fau_name",faultTypeMap.get("FAU_NAME"));
		mode.addAttribute("is_stop",faultTypeMap.get("IS_STOP"));
		mode.addAttribute("super_code",faultTypeMap.get("SUPER_CODE"));
		mode.addAttribute("spell_code",faultTypeMap.get("SPELL_CODE"));
		mode.addAttribute("wbx_code",faultTypeMap.get("WBX_CODE"));
		return "hrp/ass/repair/assfaulttype/assFaultTypeUpdate";
		
	}

	/**
	 * @Description 
	 * 主页面Tree 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssFaultTypeTree", method = RequestMethod.POST ,produces = "application/json;charset=UTF-8") 
	@ResponseBody
	public String queryAssFaultTypeTree(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return assFaultTypeService.queryAssFaultTypeTree(mapVo);
		
	}
	/**
	 * @Description 
	 * 主页面EtGrid数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssFaultType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssFaultType(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return assFaultTypeService.queryAssFaultType(mapVo);
		
	}

	/**
	 * @Description 
	 * 添加数据 addAssFaultType
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "addAssFaultType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssFaultType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fau_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("fau_name").toString()));
       
		String assInvArrtJson = assFaultTypeService.add(mapVo);

		return JSONObject.parseObject(assInvArrtJson);
		
	}
	/**
	 * @Description 
	 * 更新数据 UpdateAssFaultType
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "updateAssFaultType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssFaultType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assInvArrtJson = assFaultTypeService.update(mapVo);
		
		return JSONObject.parseObject(assInvArrtJson);
		
	}
	/**
	 * @Description 
	 * 删除数据 deleteAssFaultType
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteAssFaultType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssFaultType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		List<Map> listVo = JSONArray.parseArray( mapVo.get("ParamVo").toString(),Map.class);
		for (Map  map: listVo) {
			map.put("group_id",SessionManager.getGroupId());
			map.put("hos_id",SessionManager.getHosId());
			map.put("copy_code",SessionManager.getCopyCode());
			
		}
		
		String assInvArrtJson = assFaultTypeService.deleteAssFaultTypeBatch(listVo);
		
		return JSONObject.parseObject(assInvArrtJson);
		
	}
	
}
