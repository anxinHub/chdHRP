package com.chd.hrp.ass.controller.assdisposal;

import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.assdisposal.other.AssDisposalApplyOther;
import com.chd.hrp.ass.entity.assdisposal.other.AssDisposalRecordDetailOther;
import com.chd.hrp.ass.entity.assdisposal.other.AssDisposalRecordOther;
import com.chd.hrp.ass.service.assdisposal.other.AssDisposalApplyDetailOtherService;
import com.chd.hrp.ass.service.assdisposal.other.AssDisposalApplyOtherService;
import com.chd.hrp.ass.service.assdisposal.other.AssDisposalRecordDetailOtherService;
import com.chd.hrp.ass.service.assdisposal.other.AssDisposalRecordOtherService;

/**
 * 
 * @Description: 051001资产处置记录(其他固定资产)
 * @Table: ASS_DISPOSAL_R_Other 
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssDisposalRecordOtherController extends BaseController {     
	private static Logger logger = Logger.getLogger(AssDisposalRecordOtherController.class);

	// 引入Service服务
	@Resource(name = "assDisposalRecordOtherService")
	private final AssDisposalRecordOtherService assDisposalRecordOtherService = null;

	@Resource(name = "assDisposalRecordDetailOtherService")
	private final AssDisposalRecordDetailOtherService assDisposalRecordDetailOtherService = null;
	
	// 引入Service服务
		@Resource(name = "assDisposalApplyOtherService")
		private final AssDisposalApplyOtherService assDisposalApplyOtherService = null;

		@Resource(name = "assDisposalApplyDetailOtherService")
		private final AssDisposalApplyDetailOtherService assDisposalApplyDetailOtherService = null;
		
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/assDisposalRecordOtherMainPage", method = RequestMethod.GET)
	public String assBackOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05043",MyConfig.getSysPara("05043"));
		
		return "hrp/ass/assother/assdisposal/disposalrecord/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/assDisposalRecordOtherAddPage", method = RequestMethod.GET)
	public String assBackOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assdisposal/disposalrecord/add";

	}
	
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/assImportOtherPage", method = RequestMethod.GET)
	public String assImportOtherPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("dispose_type", mapVo.get("dispose_type"));
		mode.addAttribute("dispose_type_name", mapVo.get("dispose_type_name"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("note", mapVo.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assdisposal/disposalrecord/batchAdd";
	}
	
	/**
	 * @Description 添加数据 051001资产处置记录(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/saveAssDisposalRecordOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDisposalRecordOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		} 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
		AssDisposalRecordOther assDisposalRecordOther = assDisposalRecordOtherService.queryByCode(mapVo);
		if(assDisposalRecordOther != null){
			if(assDisposalRecordOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assDisposalRecordOtherJson = assDisposalRecordOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDisposalRecordOtherJson);

	}
	/**
	 * 引入处置申请
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("dispose_type", mapVo.get("dispose_type"));
		mode.addAttribute("note", mapVo.get("note"));
		return "hrp/ass/assother/assdisposal/disposalrecord/Import";

	}
	
	/**
 * @Description 查询数据 资产处置申报(其他固定资产)
 * @param mapVo
 * @param mode
 * @return Map<String, Object>
 * @throws Exception
 */																	   
@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/queryAssDisposalApplySpecial", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssDisposalApplyOther(@RequestParam Map<String, Object> mapVo, Model mode)
		throws Exception {

	mapVo.put("group_id", SessionManager.getGroupId());

	mapVo.put("hos_id", SessionManager.getHosId());

	mapVo.put("copy_code", SessionManager.getCopyCode());

	String assBackOther = assDisposalApplyOtherService.query(getPage(mapVo));

	return JSONObject.parseObject(assBackOther);

}
	/**
	 * 查询处置申请明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/assCheckViewUpdatePage", method = RequestMethod.GET)
	public String assCheckViewUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalApplyOther assDisposalApplyOther = new AssDisposalApplyOther();

		assDisposalApplyOther = assDisposalApplyOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalApplyOther.getGroup_id());
		mode.addAttribute("hos_id", assDisposalApplyOther.getHos_id());
		mode.addAttribute("copy_code", assDisposalApplyOther.getCopy_code());
		mode.addAttribute("dis_a_no", assDisposalApplyOther.getDis_a_no());
		mode.addAttribute("dispose_type", assDisposalApplyOther.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalApplyOther.getDispose_type_name());
		mode.addAttribute("note", assDisposalApplyOther.getNote());
		mode.addAttribute("create_emp", assDisposalApplyOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalApplyOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalApplyOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assDisposalApplyOther.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalApplyOther.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalApplyOther.getAudit_emp_name());
		mode.addAttribute("state", assDisposalApplyOther.getState());
		mode.addAttribute("state_name", assDisposalApplyOther.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assdisposal/disposalrecord/ViewUpdate";
	}
	//查询明细列表  查看明细页使用
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/queryAssDisposalApplyDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalApplyDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("dis_a_no").toString();
		String car[] = carno.split(",");
		final List<String> ids = new ArrayList<String>();
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("dis_a_no", ids);
        
		String assBackDetailSpecial = assDisposalApplyDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);
		
	}
	//查询明细列表  添加页使用
		@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/addAssPlanDeptImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
	/*		mapVo.put("group_id", SessionManager.getGroupId());
				
			mapVo.put("hos_id", SessionManager.getHosId());
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			String assBackDetailOther = assDisposalApplyDetailOtherService.query(getPage(mapVo));

			return JSONObject.parseObject(assBackDetailOther);*/
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInGeneral = assDisposalRecordOtherService.initAssCheckSpecial(mapVo);

			return JSONObject.parseObject(assAllotInGeneral);
		}
		
		//hrp/ass/assspecial/check/chkrecord/assViewSellOutGeneralPage
		/**
		 * @Description 追溯盘亏申请单跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/assViewSpecialPage", method = RequestMethod.GET)
		public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("dis_r_no", mapVo.get("dis_r_no"));
			return "hrp/ass/assother/assdisposal/disposalrecord/assPlanViewApply";
		}
		//queryCheckApSpecialByImport
		// 追溯盘亏申请
		@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/queryCheckApSpecialByImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assDisposalApplyOtherService.queryAssApplyDeptByPlanDept(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	/**
	 * @Description 更新跳转页面 051001资产处置记录(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/assDisposalRecordOtherUpdatePage", method = RequestMethod.GET)
	public String assDisposalRecordOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalRecordOther assDisposalRecordOther = new AssDisposalRecordOther();

		assDisposalRecordOther = assDisposalRecordOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalRecordOther.getGroup_id());
		mode.addAttribute("hos_id", assDisposalRecordOther.getHos_id());
		mode.addAttribute("copy_code", assDisposalRecordOther.getCopy_code());
		mode.addAttribute("dis_r_no", assDisposalRecordOther.getDis_r_no());
		mode.addAttribute("dispose_type", assDisposalRecordOther.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalRecordOther.getDispose_type_name());
		mode.addAttribute("note", assDisposalRecordOther.getNote());
		mode.addAttribute("create_emp", assDisposalRecordOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalRecordOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalRecordOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("record_date", DateUtil.dateToString(assDisposalRecordOther.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalRecordOther.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalRecordOther.getAudit_emp_name());
		mode.addAttribute("state", assDisposalRecordOther.getState());
		mode.addAttribute("state_name", assDisposalRecordOther.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05043",MyConfig.getSysPara("05043"));
		
		return "hrp/ass/assother/assdisposal/disposalrecord/update";
	}


	/**
	 * @Description 删除数据 051001资产处置记录(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/deleteAssDisposalRecordOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalRecordOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("dis_r_no", ids[3]);
			
			AssDisposalRecordOther assDisposalRecordOther = assDisposalRecordOtherService.queryByCode(mapVo);
			if(assDisposalRecordOther != null){
				if(assDisposalRecordOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
      String de=    assDisposalRecordOtherService.deleteBatchAssApplyPlanMap(listVo);
		String assDisposalRecordOtherJson = assDisposalRecordOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assDisposalRecordOtherJson);

	}

	/**
	 * @Description 查询数据 051001资产处置记录(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/queryAssDisposalRecordOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDisposalRecordOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		
		String assBackOther = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackOther = assDisposalRecordOtherService.query(getPage(mapVo));
			 
		}else{

			assBackOther = assDisposalRecordOtherService.queryDetails(getPage(mapVo));
			 
		}

		return JSONObject.parseObject(assBackOther);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/queryAssDisposalRecordDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalRecordDetailOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailOther = assDisposalRecordDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailOther);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/deleteAssDisposalRecordDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalRecordDetailOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("dis_r_no", ids[3])   ;
				mapVo.put("ass_card_no", ids[4]);
				AssDisposalRecordOther assDisposalRecordOther = assDisposalRecordOtherService.queryByCode(mapVo);
				if(assDisposalRecordOther != null){
					if(assDisposalRecordOther.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assDisposalRecordDetailOtherJson = assDisposalRecordDetailOtherService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assDisposalRecordDetailOtherJson);
			
	}
	
	/**
	 * @Description 确认处置 051001资产处置记录(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/updateConfirmDisposalRecordOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDisposalRecordOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("dis_r_no", ids[3]);
			mapVo.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssDisposalRecordOther assDisposalRecordOther = assDisposalRecordOtherService.queryByCode(mapVo);
			if(assDisposalRecordOther == null || assDisposalRecordOther.getState() > 0){
				continue;
			}
			List<AssDisposalRecordDetailOther> detailList = assDisposalRecordDetailOtherService.queryByDisRNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalRecordDetailOther detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalRecordOther.getDispose_type());
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					listCardMap.put("dispose_cost", detail.getDispose_cost());
					listCardMap.put("dispose_income", detail.getDispose_income());
					listCardMap.put("dispose_tax", detail.getDispose_tax());
					if(map.containsKey(detail.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置确认! \"}");
			}
			listVo.add(mapVo);

		}

		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assDisposalRecordOtherJson = assDisposalRecordOtherService.updateConfirmDisposalRecordOther(listVo,listCardVo);
		return JSONObject.parseObject(assDisposalRecordOtherJson);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/updateUnConfirmDisposalRecordOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUnConfirmDisposalRecordOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("dis_r_no", ids[3]);
			mapVo.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssDisposalRecordOther assDisposalRecordOther = assDisposalRecordOtherService.queryByCode(mapVo);
			if(assDisposalRecordOther == null || assDisposalRecordOther.getState() == 0){
				continue;
			}
			List<AssDisposalRecordDetailOther> detailList = assDisposalRecordDetailOtherService.queryByDisRNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalRecordDetailOther detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalRecordOther.getDispose_type());
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					listCardMap.put("dispose_cost", detail.getDispose_cost());
					listCardMap.put("dispose_income", detail.getDispose_income());
					listCardMap.put("dispose_tax", detail.getDispose_tax());
					listCardVo.add(listCardMap);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置取消确认! \"}");
			}
			listVo.add(mapVo);

		}

		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复取消确认操作! \"}");
		}

		String assDisposalRecordOtherJson = assDisposalRecordOtherService.updateUnConfirmDisposalRecordOther(listVo,listCardVo);
		return JSONObject.parseObject(assDisposalRecordOtherJson);

	}
	
	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalrecord/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<String> list = assDisposalRecordOtherService.queryState(mapVo);
		
		if(list.size()>0){
			for (String tran_no : list) {
				str += tran_no +" ";
			}
			return JSONObject.parseObject("{\"error\":\"单号为"+str+"的数据是未确认,无法打印!\",\"state\":\"true\"}");
			
		}else{
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
		
	}
	
}
