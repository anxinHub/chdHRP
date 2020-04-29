package com.chd.hrp.ass.controller.repair.repairrecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.repair.repairrecord.AssRepairRecordService;

@Controller
@RequestMapping(value = "hrp/ass/repair/repairrecord")
public class AssRepairRecordController extends BaseController {

	@Resource(name = "assRepairRecordService")
	private final AssRepairRecordService assRepairRecordService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/assRepairRecordMainPage", method = RequestMethod.GET)
	public String assRepairRecordnMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/repairrecord/assRepairRecordMain";
	}

	/**
	 * @Description 添加页跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "addAssRepairRecordPage", method = RequestMethod.GET)
	public String assLocationAddPage(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return "hrp/ass/repair/repairrecord/assRepairRecordAdd";
	}
	//卡片
	/**
	 * @Description 添加页跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assCardRecordMainPage", method = RequestMethod.GET)
	public String assCardRecordMainPage(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> map = assRepairRecordService.queryRecordCardDataByCode(mapVo);
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
		return "hrp/ass/repair/repairrecord/assCardRecordMain";
	}
	// 保存
	@RequestMapping(value = "addAssRepairRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRepairRecord(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String retJson = "{ }";
		try {
			retJson = assRepairRecordService.addAssRepairRecord(mapVo, request,response);
			if (retJson.equals("error")) {
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
		} catch (Exception e) {
			retJson = "{\"error\":\"" + e.getMessage() + "\"}";
		}

		return JSONObject.parseObject(retJson);
	}

	/**
	 * @Description 主页面数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAssRepairCenter", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRepairCenter(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(!mapVo.get("rep_dept").toString().equals("")){
			mapVo.put("rep_dept", mapVo.get("rep_dept").toString().split("@")[0]);
		}
		String assRepairCenter = assRepairRecordService.queryAssRepairRecord(getPage(mapVo));
		return assRepairCenter;

	}

	/**
	 * @Description 修改页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "updateAssRepairRecordPage", method = RequestMethod.GET)
	public String assMyRepairUpdatePage(	@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String, Object> map = assRepairRecordService.queryAssRecordByCode(mapVo);
		mode.addAttribute("group_id", map.get("GROUP_ID"));
		mode.addAttribute("hos_id", map.get("HOS_ID"));
		mode.addAttribute("copy_code", map.get("COPY_CODE"));
		mode.addAttribute("rep_code", map.get("REP_CODE"));
		mode.addAttribute("fau_code", map.get("FAU_CODE"));
		mode.addAttribute("app_time", map.get("APP_TIME").toString().substring(0,10));
		mode.addAttribute("begin_date", map.get("APP_TIME").toString().substring(11,16));
		if(map.get("COMP_TIME")!=null){
			mode.addAttribute("comp_time", map.get("COMP_TIME").toString().substring(0,10));
			mode.addAttribute("end_date", map.get("COMP_TIME").toString().substring(11,16));
		}
		
		mode.addAttribute("eme_status", map.get("EME_STATUS"));
		mode.addAttribute("rep_dept", map.get("REP_DEPT"));
		mode.addAttribute("loc_code", map.get("LOC_CODE"));
		mode.addAttribute("seq_no", map.get("SEQ_NO"));
		String cardNo = "";
		if (map.get("ASS_CARD_NO") == null) {
			cardNo = "none";
		} else {
			cardNo = map.get("ASS_CARD_NO").toString();
		}
		mode.addAttribute("ass_card_no", cardNo);
		mode.addAttribute("ass_name", map.get("ASS_NAME"));
		mode.addAttribute("rep_user", map.get("REP_USER"));
		mode.addAttribute("phone", map.get("PHONE"));
		mode.addAttribute("fau_note", map.get("FAU_NOTE"));
		mode.addAttribute("ass_name", map.get("ASS_NAME"));
		mode.addAttribute("rep_comp_user", map.get("REP_COMP_USER"));
		mode.addAttribute("rep_comp", map.get("REP_COMP"));
		mode.addAttribute("rep_bz", map.get("REP_BZ"));
		mode.addAttribute("is_base", map.get("IS_BASE"));
		mode.addAttribute("is_card", map.get("IS_CARD"));
		return "hrp/ass/repair/repairrecord/assRepairRecordUpdate";

	}

	// 更新
	@RequestMapping(value = "updateAssRepairRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRepairRecord(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String retJson;
		try {
			retJson = assRepairRecordService.updateAssRepairRecord(mapVo,request, response);
		} catch (Exception e) {
			retJson = "{\"error\":\"" + e.getMessage() + "\"}";
		}

		return JSONObject.parseObject(retJson);
	}

	/**
	 * 删除记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "deleteAssRepairRecord", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAssRepairRecord(@RequestParam Map<String, Object> mapVo, Model mode) {
		List<String> repCode = JSONArray.parseArray(mapVo.get("ParamVo").toString(), String.class);
		String assMyRepair = assRepairRecordService.deleteAssRepairRecord(repCode);
		return assMyRepair;
	}

	/**
	 * 材料下拉
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "queryMatInvDictSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvDictSelect(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair = assRepairRecordService.queryMatInvDictSelect(mapVo);
		return assMyRepair;
	}

	/**
	 * 材料明细
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "queryMatInvDictSelectInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvDictSelectInfo(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair = assRepairRecordService.queryMatInvDictSelectInfo(mapVo);
		return JSONObject.parseObject(assMyRepair);
	}

	/**
	 * 查询文件
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "queryImgUrlByRecordCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryImgUrlByRecordCode(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair = assRepairRecordService.queryImgUrlByRecordCode(mapVo);
		return assMyRepair;
	}

	/**
	 * 修改页面查询材料
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "queryInvUpdate", method = RequestMethod.POST)
	@ResponseBody
	public String queryInvUpdate(@RequestParam Map<String, Object> mapVo,Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepair = assRepairRecordService.queryInvUpdate(getPage(mapVo));
		return assMyRepair;
	}
	/**
	 * @Description 
	 * 时间轴数据 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryTimeLineRecordRender", method = RequestMethod.POST)
	@ResponseBody
	public String queryTimeLineRecordRender(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepairCenter = assRepairRecordService.queryTimeLineRecordRender(mapVo);
		return assMyRepairCenter;
		
	}
	//
}
