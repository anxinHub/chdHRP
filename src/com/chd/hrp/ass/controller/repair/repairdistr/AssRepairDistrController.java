package com.chd.hrp.ass.controller.repair.repairdistr;
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
import com.chd.base.SessionManager;
import com.chd.hrp.ass.controller.repair.AssInvArrtController;
import com.chd.hrp.ass.service.repair.repairdistr.AssRepairDistrService;
@Controller
@RequestMapping(value = "hrp/ass/repair/repairdistr")
public class AssRepairDistrController {
	
	private static Logger logger = Logger.getLogger(AssInvArrtController.class);
	
	@Resource(name = "assRepairDistrService")
	private final AssRepairDistrService assRepairDistrService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "assRepairDistrMainPage", method = RequestMethod.GET)
	public String assLocationMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/repairdistr/assRepairDistrMain";

	}
	/**
	 * @Description 
	 * 选择维修人员跳转
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "assRepairDistrSubmitPage", method = RequestMethod.GET)
	public String assRepairDistrSubmitPage(Model mode) throws Exception {
		
		return "hrp/ass/repair/repairdistr/assRepairDistrSubmit";
		
	}

	/**
	 * @Description 
	 * 修改页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assRepairDistrUpdatePage", method = RequestMethod.GET)
	public String assRepairDistrUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object>map=assRepairDistrService.queryAssRepairByCode(mapVo);
		mode.addAttribute("group_id", map.get("GROUP_ID"));
		mode.addAttribute("hos_id", map.get("HOS_ID"));
		mode.addAttribute("copy_code", map.get("COPY_CODE"));
		mode.addAttribute("rep_code", map.get("REP_CODE"));
		mode.addAttribute("fau_code", map.get("FAU_CODE"));
		mode.addAttribute("eme_status", map.get("EME_STATUS"));
		mode.addAttribute("rep_dept", map.get("REP_DEPT"));
		mode.addAttribute("loc_code", map.get("LOC_CODE"));
		String cardNo="";
		if(map.get("ASS_CARD_NO")==null){
			cardNo= "none";
		}else{ 
			cardNo=map.get("ASS_CARD_NO").toString();
		}
		mode.addAttribute("ass_card_no",cardNo);
		mode.addAttribute("ass_name",map.get("ASS_NAME"));
		mode.addAttribute("rep_user", map.get("REP_USER"));
		mode.addAttribute("phone", map.get("PHONE"));
		mode.addAttribute("fau_note", map.get("FAU_NOTE"));
		return "hrp/ass/repair/repairdistr/assRepairDistrUpdate";
		
	}
	
	@RequestMapping(value = "backAssRepairPage", method = RequestMethod.GET)
	public String backAssRepairPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("rep_code", mapVo.get("rep_code"));
		return "hrp/ass/repair/repairdistr/assRepairDistrBack";
		
	}
	 
	 
	/**
	 * @Description 
	 * 主页面EtGrid数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssRepairByState", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRepairByState(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return assRepairDistrService.queryAssRepairByState(mapVo);
		
	}
	
	
	/**
	 * @Description 
	 * 回退
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "backAssRepair", method = RequestMethod.POST)
	@ResponseBody
	public String backAssRepair(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return assRepairDistrService.backAssRepair(mapVo);
		
	}
	
	/**
	 * @Description 
	 * 主页面EtGrid数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "updateRepairDistrState", method = RequestMethod.POST)
	@ResponseBody
	public String updateRepairDistrState(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return assRepairDistrService.updateRepairDistrState(mapVo);
		
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
		
		String assInvArrtJson = assRepairDistrService.update(mapVo);
		
		return JSONObject.parseObject(assInvArrtJson);
		
	}
	@RequestMapping(value="queryImgUrlByRepCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryImgUrlByRepCode( @RequestParam Map<String,Object>  mapVo , Model mode ){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair =assRepairDistrService.queryImgUrlByRepCode(mapVo);
		return assMyRepair;	
	}
	
}
