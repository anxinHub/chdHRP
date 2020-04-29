package com.chd.hrp.ass.controller.repair.assmyinformrepair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.ass.service.card.AssCardBasicService;
import com.chd.hrp.ass.service.repair.assmyinformrepair.AssMyInformRepairService;

@Controller
@RequestMapping(value = "hrp/ass/repair/assMyInformRepair/")
public class AssMyInformRepairController {
	
	@Resource(name="assMyInformRepairService")
	private final AssMyInformRepairService assMyInformRepairService  = null ;
	@Resource(name = "assCardBasicService")
	private final AssCardBasicService assCardBasicService = null;

	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "assMyRepairMainPage", method = RequestMethod.GET)
	public String assLocationMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/assmyinformrepair/assMyRepairMain";

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
		Map<String,Object>map=assMyInformRepairService.queryAssRepairByCode(mapVo);
		mode.addAttribute("rep_code", mapVo.get("rep_code"));
		mode.addAttribute("urg_note", map.get("URG_NOTE"));
		mode.addAttribute("is_urg", map.get("IS_URG"));
		return "hrp/ass/repair/assmyinformrepair/cuiDanMainPage";
		
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "assMyRepairAddPage", method = RequestMethod.GET)
	public String assLocationAddPage(Model mode) throws Exception {
		Map<String,Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return "hrp/ass/repair/assmyinformrepair/assMyRepairAdd";

	}
	
	/**
	 * @Description 
	 * 卡片页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assCardMainPage", method = RequestMethod.GET)
	public String assCardMain(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> map = assMyInformRepairService.queryCardDataByCode(mapVo);
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
	 * 修改页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assMyRepairUpdatePage", method = RequestMethod.GET)
	public String assMyRepairUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object>map=assMyInformRepairService.queryAssRepairByCode(mapVo);
		mode.addAttribute("group_id", map.get("GROUP_ID"));
		mode.addAttribute("hos_id", map.get("HOS_ID"));
		mode.addAttribute("seq_no", map.get("SEQ_NO"));
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
		return "hrp/ass/repair/assmyinformrepair/assMyRepairUpdate";
		
	}
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssMyRepairCenter", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssMyRepairCenter(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMyRepairCenter = assMyInformRepairService.queryAssMyRepairCenter(mapVo);
		return assMyRepairCenter;
		
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
		String assMyRepairCenter = assMyInformRepairService.queryTimeLineRender(mapVo);
		return assMyRepairCenter;
		
	}
	
	// 保存
		@RequestMapping(value = "addAssMyRepaor", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssMyRepaor(@RequestParam Map<String,Object> mapVo, Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {
			String retJson="{ }";
			try {
			       retJson = assMyInformRepairService.addAssMyRepaor(mapVo,request,response);
				if(retJson.equals("error")){
					return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
					}
				} catch (Exception e) {
				retJson = "{\"error\":\""+e.getMessage()+"\"}";
			}

			return JSONObject.parseObject(retJson);
		}
		
		// 更新
		@RequestMapping(value = "updateAssMyRepair", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateAssMyRepair(@RequestParam Map<String, Object> mapVo,  Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {
			String retJson;
			try {
				retJson = assMyInformRepairService.updateAssMyRepair(mapVo,request, response);
			} catch (Exception e) {
				retJson = "{\"error\":\""+e.getMessage()+"\"}";
			}
			
			return JSONObject.parseObject(retJson);
		}
		
		@RequestMapping(value="deleteAssMyRepair", method = RequestMethod.POST)
		@ResponseBody
		public String deleteAssMyRepair( @RequestParam Map<String,Object>  mapVo , Model mode ){
			List<String> repCode  =JSONArray.parseArray(mapVo.get("ParamVo").toString() , String.class);
			String assMyRepair =assMyInformRepairService.deleteAssMyRepairBatch(repCode);
			return assMyRepair;	
		}
		@RequestMapping(value="saveAssMyRepair", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> saveAssMyRepair( @RequestParam Map<String,Object>  mapVo , Model mode ){
			//rep_code=12,12,12
			Map<String, Object> resMap=new HashMap<String, Object>();
			
			try{
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				resMap=assMyInformRepairService.saveAssMyRepair(mapVo);
			}catch(Exception e){
				resMap.put("error", e.getMessage());
			}
			return resMap;	
		}
		@RequestMapping(value="queryImgUrlByRepCode", method = RequestMethod.POST)
		@ResponseBody
		public String queryImgUrlByRepCode( @RequestParam Map<String,Object>  mapVo , Model mode ){
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String assMyRepair =assMyInformRepairService.queryImgUrlByRepCode(mapVo);
			return assMyRepair;	
		}
		@RequestMapping(value="cuiAssRepirByRepCode", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> cuiAssRepirByRepCode( @RequestParam Map<String,Object>  mapVo , Model mode ){
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			Map<String, Object> assMyRepair =assMyInformRepairService.cuiAssRepirByRepCode(mapVo);
			return assMyRepair;	
		}
		
		@RequestMapping(value="submitRepScore", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> submitRepScore( @RequestParam Map<String,Object>  mapVo , Model mode ){
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			Map<String, Object> assMyRepair =assMyInformRepairService.submitRepScore(mapVo);
			return assMyRepair;	
		}
	
}
