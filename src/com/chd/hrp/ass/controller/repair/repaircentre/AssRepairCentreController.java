package com.chd.hrp.ass.controller.repair.repaircentre;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.repair.repaircentre.AssRepairCentreService;
@Controller
@RequestMapping(value ="hrp/ass/repair/repaircentre")
public class AssRepairCentreController extends BaseController {
	
	
	@Resource(name="assRepairCentreService")
	private final AssRepairCentreService  assRepairCentreService = null ;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						 
	@RequestMapping(value = "/assRepairCentreMainPage", method = RequestMethod.GET)
	public String assRepairCentrenMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/repaircentre/assRepairCentreMain";
	}

	/**
	 * @Description 
	 * 催单面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "cuiDanMainPage", method = RequestMethod.GET)
	public String cuiDanMainPage(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object>map=assRepairCentreService.queryAssRepairByCode(mapVo);
		mode.addAttribute("rep_code", mapVo.get("rep_code"));
		mode.addAttribute("urg_note", map.get("URG_NOTE"));
		mode.addAttribute("is_urg", map.get("IS_URG"));
		return "hrp/ass/repair/repaircentre/cuiDanMainPage";
	}
	/**
	 * @Description 
	 * 转单页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/assRepairCentreTurnBillsPage", method = RequestMethod.GET)
	public String assRepairCentreTurnBillsPage(Model mode) throws Exception {

		return "hrp/ass/repair/repaircentre/assRepairCentreTurnBills";

	}
	/**
	 * @Description 
	 * 完成维修跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/assRepairCentreEndUpdatePage", method = RequestMethod.GET)
	public String assRepairCentreEndUpdate(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> map = assRepairCentreService.queryAssRepairByCode(mapVo);
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
		return "hrp/ass/repair/repaircentre/assRepairCentreEndUpdate";
		
	}
	/**
	 * @Description 
	 * 误报页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/assRepairCentreExceptionPage", method = RequestMethod.GET)
	public String assRepairCentreException(Model mode) throws Exception {
		
		return "hrp/ass/repair/repaircentre/assRepairCentreException";
		
	}
	/**
	 * @Description 
	 * 卡片页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/assCardMainPage", method = RequestMethod.GET)
	public String assCardMain(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> map = assRepairCentreService.queryCardDataByCode(mapVo);
		mode.addAttribute("ass_card_no",map.get("ASS_CARD_NO"));
		mode.addAttribute("ass_name",map.get("ASS_NAME"));
		mode.addAttribute("fac_name",map.get("FAC_NAME"));
		mode.addAttribute("sup_name",map.get("SUP_NAME"));
		mode.addAttribute("ass_spec",map.get("ASS_SPEC"));
		mode.addAttribute("ass_brand",map.get("ASS_BRAND"));
		mode.addAttribute("fac_name",map.get("FAC_NAME"));
		mode.addAttribute("sup_name",map.get("SUP_NAME"));
		mode.addAttribute("contract_no",map.get("CONTRACT_NO"));
		mode.addAttribute("accept_date",map.get("ACCEPT_DATE"));
		mode.addAttribute("emp_name",map.get("EMP_NAME"));
		mode.addAttribute("run_date",map.get("RUN_DATE"));
		mode.addAttribute("usage_code",map.get("USAGE_CODE"));
		mode.addAttribute("dept_name",map.get("DEPT_NAME"));
		mode.addAttribute("ass_seq_no",map.get("ASS_SEQ_NO"));
		return "hrp/ass/repair/repaircentre/assCardMain";
		
	}
	/**
	 * @Description 
	 * 查看保修单据页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRepBillMainPage", method = RequestMethod.GET)
	public String queryRepBillMainPage(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object>map=assRepairCentreService.queryAssRepairByCode(mapVo);
		//Map<String,Object>map1=assRepairCentreService.queryAssRepInvCode(mapVo);
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
		mode.addAttribute("reason", map.get("REP_NOTE"));
		mode.addAttribute("seq_no", map.get("SEQ_NO"));
		return "hrp/ass/repair/repaircentre/queryRepBillMain";
		
	}
	/**
	 * @Description 
	 * 主页面数据
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAssRepairCenter", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRepairCenter(@RequestParam Map<String, Object> mapVo,  Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String  assRepairCenter = assRepairCentreService.queryAssRepairCenter(mapVo);
		return assRepairCenter;
		
	}
	/**
	 * @Description 
	 * 时间轴数据 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryTimeLineRender", method = RequestMethod.POST)
	@ResponseBody
	public String queryTimeLineRender(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepairCenter = assRepairCentreService.queryTimeLineRender(mapVo);
		return assMyRepairCenter;
		
	}
	
	/**
	 * @Description 转单页面维修工程师tree数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryRepTeamUser", method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryRepTeamUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String RepTeamUser = assRepairCentreService.queryRepTeamUser(mapVo);
		return RepTeamUser;

	}
	/**
	 * @Description 
	 * 接单
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "repairReceiving", method = RequestMethod.POST)
	@ResponseBody
	public String repairReceiving(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepairCenter = assRepairCentreService.repairReceiving(mapVo);
		return assMyRepairCenter;
		
	}
	/**
	 * @Description 
	 * 转单
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "updateRepUser", method = RequestMethod.POST)
	@ResponseBody
	public String updateRepUser(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepairCenter = assRepairCentreService.updateRepUser(mapVo);
		return assMyRepairCenter;
		
	}
	/**
	 * @Description 
	 * 误报
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "updateEndRepUser", method = RequestMethod.POST)
	@ResponseBody
	public String updateEndRepUser(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepairCenter = assRepairCentreService.updateEndRepUser(mapVo);
		return assMyRepairCenter;
		
	}
	/**
	 * 单据页图片展示
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="queryImgUrlByRepCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryImgUrlByRepCode( @RequestParam Map<String,Object>  mapVo , Model mode ){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair =assRepairCentreService.queryImgUrlByRepCode(mapVo);
		return assMyRepair;	
	}
	
	/**
	 * 完成维修页材料grid
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="queryMatInvDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvDict( @RequestParam Map<String,Object>  mapVo , Model mode ){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair =assRepairCentreService.queryMatInvDict(getPage(mapVo));
		return JSONObject.parseObject(assMyRepair);
	}
	
	/**
	 * 完成维修
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="updateEndRepairState", method = RequestMethod.POST)
	@ResponseBody
	public String updateEndRepairState( @RequestParam Map<String,Object>  mapVo , Model mode ){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair =assRepairCentreService.updateEndRepairState(mapVo);
		return assMyRepair;	
	}
	/**
	 * 完成维修
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="queryCountState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryCountState( @RequestParam Map<String,Object>  mapVo , Model mode ){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> countState =assRepairCentreService.queryCountState(mapVo); 
		return countState;	
	}
	/**
	 * 单据页面
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value="queryAssRepInv", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRepInv( @RequestParam Map<String,Object>  mapVo , Model mode ){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair =assRepairCentreService.queryAssRepInv(mapVo);
		return assMyRepair;	
	}
}
