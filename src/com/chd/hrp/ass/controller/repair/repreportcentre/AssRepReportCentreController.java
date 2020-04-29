package com.chd.hrp.ass.controller.repair.repreportcentre;

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
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.card.AssCardBasicService;
import com.chd.hrp.ass.service.repair.repreportcentre.AssRepReportCentreService;

@Controller
@RequestMapping(value = "hrp/ass/repair/repreportcentre/")
public class AssRepReportCentreController {
	
	@Resource(name="assRepReportCentreService")
	private final AssRepReportCentreService assRepReportCentreService  = null ;
	@Resource(name = "assCardBasicService")
	private final AssCardBasicService assCardBasicService = null;

	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/						  
	@RequestMapping(value = "assRepReportCentreMainPage", method = RequestMethod.GET)
	public String assRepReportCentreMainPage(Model mode) throws Exception {

		return "hrp/ass/repair/repreportcentre/assRepReportCentreMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "assRepReportCentreAddPage", method = RequestMethod.GET)
	public String assLocationAddPage(Model mode) throws Exception {
		Map<String,Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		mode.addAttribute("rep_code", assRepReportCentreService.queryMaxNo(mapVo));
		return "hrp/ass/repair/repreportcentre/assRepReportCentreAdd";

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
		Map<String,Object> map = assRepReportCentreService.queryCardDataByCode(mapVo);
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
		mode.addAttribute("usage_name",map.get("EQUI_USAGE_NAME"));
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
	@RequestMapping(value = "assRepReportCentreUpdatePage", method = RequestMethod.GET)
	public String assRepReportCentreUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object>map=assRepReportCentreService.queryAssRepairByCode(mapVo);
		mode.addAttribute("group_id", map.get("GROUP_ID"));
		mode.addAttribute("hos_id", map.get("HOS_ID"));
		mode.addAttribute("copy_code", map.get("COPY_CODE"));
		mode.addAttribute("rep_code", map.get("REP_CODE"));
		mode.addAttribute("fau_code", map.get("FAU_CODE"));
		mode.addAttribute("eme_status", map.get("EME_STATUS"));
		mode.addAttribute("rep_dept", map.get("REP_DEPT"));
		mode.addAttribute("loc_code", map.get("LOC_CODE"));
		mode.addAttribute("seq_no", map.get("SEQ_NO"));
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
		return "hrp/ass/repair/repreportcentre/assRepReportCentreUpdate";
		
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
		Map<String,Object>map=assRepReportCentreService.queryAssRepairByCode(mapVo);
		mode.addAttribute("rep_code", mapVo.get("rep_code"));
		mode.addAttribute("urg_note", map.get("URG_NOTE"));
		mode.addAttribute("is_urg", map.get("IS_URG"));
		return "hrp/ass/repair/repreportcentre/cuiDanMainPage";
		
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
		String AssRepReportCentreCenter = assRepReportCentreService.queryAssRepReportCentreCenter(mapVo);
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
		String AssRepReportCentreCenter = assRepReportCentreService.queryTimeLineRender(mapVo);
		return AssRepReportCentreCenter;
		
	}
	
	// 保存
		@RequestMapping(value = "addAssRepReportCentre", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssRepReportCentre(@RequestParam Map<String,Object> mapVo, Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {
			String retJson="{ }";
			try {
			       retJson = assRepReportCentreService.addAssRepReportCentre(mapVo,request,response);
				if(retJson.equals("error")){
					return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
					}
				} catch (Exception e) {
				retJson = "{\"error\":\""+e.getMessage()+"\"}";
			}

			return JSONObject.parseObject(retJson);
		}
		
		// 更新
		@RequestMapping(value = "updateAssRepReportCentre", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateAssRepReportCentre(@RequestParam Map<String, Object> mapVo,  Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {
			String retJson;
			try {
				retJson = assRepReportCentreService.updateAssRepReportCentre(mapVo,request, response);
			} catch (Exception e) {
				retJson = "{\"error\":\""+e.getMessage()+"\"}";
			}
			
			return JSONObject.parseObject(retJson);
		}
		
		@RequestMapping(value="deleteAssRepReportCentre", method = RequestMethod.POST)
		@ResponseBody
		public String deleteAssRepReportCentre( @RequestParam Map<String,Object>  mapVo , Model mode ){
			List<String> repCode  =JSONArray.parseArray(mapVo.get("ParamVo").toString() , String.class);
			String AssRepReportCentre =assRepReportCentreService.deleteAssRepReportCentreBatch(repCode);
			return AssRepReportCentre;	
		}
		@RequestMapping(value="saveAssRepReportCentre", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> saveAssRepReportCentre( @RequestParam Map<String,Object>  mapVo , Model mode ){
			//rep_code=12,12,12
			Map<String, Object> resMap=new HashMap<String, Object>();
			
			try{
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				resMap=assRepReportCentreService.saveAssRepReportCentre(mapVo);
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
			String AssRepReportCentre =assRepReportCentreService.queryImgUrlByRepCode(mapVo);
			return AssRepReportCentre;	
		}
		/**
		 * 催单
		 * @param mapVo
		 * @param mode
		 * @return
		 */
		@RequestMapping(value="cuiAssRepirByRepCode", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> cuiAssRepirByRepCode( @RequestParam Map<String,Object>  mapVo , Model mode ){
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			Map<String, Object> assMyRepair =assRepReportCentreService.cuiAssRepirByRepCode(mapVo);
			return assMyRepair;	
		}
	
		@RequestMapping(value="submitRepScore", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> submitRepScore( @RequestParam Map<String,Object>  mapVo , Model mode ){
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			Map<String, Object> assMyRepair =assRepReportCentreService.submitRepScore(mapVo);
			return assMyRepair;	
		}
	
}
