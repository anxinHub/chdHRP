package com.chd.hrp.ass.controller.repair.asslocation;
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
import com.chd.hrp.ass.controller.repair.AssInvArrtController;
import com.chd.hrp.ass.service.repair.asslocation.AssLocationService;
@Controller
@RequestMapping(value = "hrp/ass/repair/assLocation")
public class AssLocationController {
	
	private static Logger logger = Logger.getLogger(AssInvArrtController.class);
	
	@Resource(name = "assLocationService")
	private final AssLocationService assLocationService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "assLocationMainPage", method = RequestMethod.GET)
	public String assLocationMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/assLocation/assLocationMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "assLocationAddPage", method = RequestMethod.GET)
	public String assLocationAddPage(Model mode) throws Exception {

		return "hrp/ass/repair/assLocation/assLocationAdd";

	}
	/**
	 * @Description 
	 * 修改页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assLocationUpdatePage", method = RequestMethod.GET)
	public String assLocationUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		Map<String,Object> assLocation=assLocationService.queryAssLocationByCode(mapVo);
		mode.addAttribute("group_id",assLocation.get("GROUP_ID"));
		mode.addAttribute("hos_id",assLocation.get("HOS_ID"));
		mode.addAttribute("copy_code",assLocation.get("COPY_CODE"));
		mode.addAttribute("loc_code",assLocation.get("LOC_CODE"));
		mode.addAttribute("loc_name",assLocation.get("LOC_NAME"));
		mode.addAttribute("super_code",assLocation.get("SUPER_CODE"));
		mode.addAttribute("is_stop",assLocation.get("IS_STOP"));
		mode.addAttribute("spell_code",assLocation.get("SPELL_CODE"));
		mode.addAttribute("wbx_code",assLocation.get("WBX_CODE"));
		return "hrp/ass/repair/assLocation/assLocationUpdate";
		
	}
	/**
	 * @Description 
	 * 主页面Tree 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssLocationTree", method = RequestMethod.POST ,produces = "application/json;charset=UTF-8") 
	@ResponseBody
	public String queryAssLocationTree(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return assLocationService.queryAssLocationTree(mapVo);
		
	}
	/**
	 * @Description 
	 * 主页面EtGrid数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssLocation", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssLocation(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return assLocationService.queryAssLocation(mapVo);
		
	}

	/**
	 * @Description 
	 * 添加数据 addAssLocation
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "addAssLocation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssLocation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("loc_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("loc_name").toString()));
       
		String assInvArrtJson = assLocationService.add(mapVo);

		return JSONObject.parseObject(assInvArrtJson);
		
	}
	/**
	 * @Description 
	 * 更新数据 UpdateAssLocation
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "updateAssLocation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssLocation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assInvArrtJson = assLocationService.update(mapVo);
		
		return JSONObject.parseObject(assInvArrtJson);
		
	}
	/**
	 * @Description 
	 * 删除数据 deleteAssLocation
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteAssLocation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssLocation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		List<Map> listVo = JSONArray.parseArray( mapVo.get("ParamVo").toString(),Map.class);
		for (Map  map: listVo) {
			map.put("group_id",SessionManager.getGroupId());
			map.put("hos_id",SessionManager.getHosId());
			map.put("copy_code",SessionManager.getCopyCode());
			
		}
		
		String assInvArrtJson = assLocationService.deleteAssLocationBatch(listVo);
		
		return JSONObject.parseObject(assInvArrtJson);
		
	}
	
}
