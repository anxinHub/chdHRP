package com.chd.hrp.ass.controller.repair.statis;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.repair.statis.AssRepStatisToUserService;

@Controller
@RequestMapping(value="hrp/ass/repair/statis/")
public class AssRepStatisToUserController {
	@Resource(name = "assRepStatisToUserService")
	private final AssRepStatisToUserService assRepStatisToUserService = null;
	/**
	 * @Description 
	 * 工程师报表跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "assRepStatisToUserMain", method = RequestMethod.GET)
	public String assRepReportCentreMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/statis/assRepStatisToUserMain";

	}
	/**
	 * @Description 
	 * 子页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "assRepStaisByRepUserPaga", method = RequestMethod.GET)
	public String assRepStaisByRepUserPaga(Model mode) throws Exception {
		
		return "hrp/ass/repair/statis/assRepStaisByRepUser";
		
	}
	/**
	 * @Description 
	 * 子页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "assRepStaisByRepUserPa", method = RequestMethod.GET)
	public String assRepStaisByRepUserPa(Model mode) throws Exception {
		
		return "hrp/ass/repair/statis/assRepStaisByRepetitionUser";
		
	}
	/**
	 * @Description 
	 * 子页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "assRepStaisByRepUserP", method = RequestMethod.GET)
	public String assRepStaisByRepUserP(Model mode) throws Exception {
		
		return "hrp/ass/repair/statis/assRepStaisByUnfinishedUser";
		
	}
	/**
	 * @Description 
	 * 修改页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assRepReportCentreUpdatePage", method = RequestMethod.GET)
	public String assRepReportCentreUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object>map=assRepStatisToUserService.queryAssRepairByCode(mapVo);
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
		return "hrp/ass/repair/statis/assRepReportCentreUpdate";
		
	}
	
	/**
	 * @Description 
	 * 卡片页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/assCardMainPage", method = RequestMethod.GET)
	public String assCardMain(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> map = assRepStatisToUserService.queryCardDataByCode(mapVo);
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
		Map<String,Object>map=assRepStatisToUserService.queryAssRepairByCode(mapVo);
		mode.addAttribute("rep_code", mapVo.get("rep_code"));
		mode.addAttribute("urg_note", map.get("URG_NOTE"));
		mode.addAttribute("is_urg", map.get("IS_URG"));
		return "hrp/ass/repair/statis/cuiDanMainPage";
		
	}
	/**
	 * @Description 
	 * 主页面tree
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "queryRepTeamTree", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryRepTeamTree(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String teamList=assRepStatisToUserService.queryRepTeamTree(mapVo);
		return teamList;
		
	}
	/**
	 * @Description 
	 * 按工程师报表主页
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "queryRepCountByRepUserId", method = RequestMethod.POST)
	@ResponseBody
	public String queryRepCountByRepUserId(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String teamList=assRepStatisToUserService.queryRepCountByRepUserId(mapVo);
		return teamList;
		
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mode
	 * @return String 
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssRepReportCentreCenter", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRepReportCentreCenter(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String AssRepReportCentreCenter = assRepStatisToUserService.queryAssRepReportCentreCenter(mapVo);
		return AssRepReportCentreCenter;
		
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mode
	 * @return String 
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssRepRepetitionReport", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRepRepetitionReport(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String AssRepReportCentreCenter = assRepStatisToUserService.queryAssRepReportCenter(mapVo);
		return AssRepReportCentreCenter;
		
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mode
	 * @return String 
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssRepUnfinishedReport", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRepUnfinishedReport(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String AssRepReportCentreCenter = assRepStatisToUserService.queryAssRepUnfinishedCenter(mapVo);
		return AssRepReportCentreCenter;
		
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
		String AssRepReportCentreCenter = assRepStatisToUserService.queryTimeLineRender(mapVo);
		return AssRepReportCentreCenter;
		
	}
	/**
	 * 查看单据页面  图片url
	 */
	@RequestMapping(value="queryImgUrlByRepCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryImgUrlByRepCode( @RequestParam Map<String,Object>  mapVo , Model mode ){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String AssRepReportCentre =assRepStatisToUserService.queryImgUrlByRepCode(mapVo);
		return AssRepReportCentre;	
	}
	/**
	 * @Description 
	 * 工程师报表跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "assRepStatiToDeptMain", method = RequestMethod.GET)
	public String assRepStatiToDeptMain(Model mode) throws Exception {
		return "hrp/ass/repair/statis/assRepStatisToDeptMain";
		
	}
	/**
	 * @Description 
	 * 按部门报表主页
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "queryRepCountByRepDeptId", method = RequestMethod.POST)
	@ResponseBody
	public String queryRepCountByRepDeptId(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String teamList=assRepStatisToUserService.queryRepCountByRepDeptId(mapVo);
		return teamList;
		
	}
	/**
	 * @Description 
	 * 子页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "assRepStaisByRepDeptPaga", method = RequestMethod.GET)
	public String assRepStaisByRepDeptPaga(Model mode) throws Exception {
		
		return "hrp/ass/repair/statis/assRepStaisByRepDept";
	}
	/**
	 * @Description 
	 * 维修次数统计报表跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "assRepStatisCountMain", method = RequestMethod.GET)
	public String assRepStatisCountMain(Model mode) throws Exception {
		return "hrp/ass/repair/statis/assRepStatisCountMain";
		
	}
	/**
	 * @Description 
	 * 按部门报表主页
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "queryRepCountByRepCardNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryRepCountByRepCardNo(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String teamList=assRepStatisToUserService.queryRepCountByRepCardNo(mapVo);
		return teamList;
		
	}
	/**
	 * @Description 
	 * 子页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */						  
	@RequestMapping(value = "assRepStaisByCardNoPaga", method = RequestMethod.GET)
	public String assRepStaisByCardNoPaga(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("ass_card_no",mapVo.get("ass_card_no"));
		
		return "hrp/ass/repair/statis/assRepStaisByCardNo";
	}
}

