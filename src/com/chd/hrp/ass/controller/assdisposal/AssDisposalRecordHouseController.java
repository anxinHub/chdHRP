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
import com.chd.hrp.ass.entity.assdisposal.house.AssDisposalApplyHouse;
import com.chd.hrp.ass.entity.assdisposal.house.AssDisposalRecordDetailHouse;
import com.chd.hrp.ass.entity.assdisposal.house.AssDisposalRecordHouse;
import com.chd.hrp.ass.service.assdisposal.house.AssDisposalApplyDetailHouseService;
import com.chd.hrp.ass.service.assdisposal.house.AssDisposalApplyHouseService;
import com.chd.hrp.ass.service.assdisposal.house.AssDisposalRecordDetailHouseService;
import com.chd.hrp.ass.service.assdisposal.house.AssDisposalRecordHouseService;

/**
 * 
 * @Description: 051001资产处置记录(土地)
 * @Table: ASS_DISPOSAL_R_House 
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssDisposalRecordHouseController extends BaseController {
	private static Logger logger = Logger.getLogger(AssDisposalRecordHouseController.class);

	// 引入Service服务
	@Resource(name = "assDisposalRecordHouseService")
	private final AssDisposalRecordHouseService assDisposalRecordHouseService = null;

	@Resource(name = "assDisposalRecordDetailHouseService")
	private final AssDisposalRecordDetailHouseService assDisposalRecordDetailHouseService = null;
	
	// 引入Service服务
		@Resource(name = "assDisposalApplyHouseService")
		private final AssDisposalApplyHouseService assDisposalApplyHouseService = null;

		@Resource(name = "assDisposalApplyDetailHouseService")
		private final AssDisposalApplyDetailHouseService assDisposalApplyDetailHouseService = null;
		
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/assDisposalRecordHouseMainPage", method = RequestMethod.GET)
	public String assBackHouseMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05063",MyConfig.getSysPara("05063"));
		
		return "hrp/ass/asshouse/assdisposal/disposalrecord/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/assDisposalRecordHouseAddPage", method = RequestMethod.GET)
	public String assBackHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assdisposal/disposalrecord/add";

	}
	
	/**
	 * @Description 添加数据 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/saveAssDisposalRecordHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDisposalRecordHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssDisposalRecordHouse assDisposalRecordHouse = assDisposalRecordHouseService.queryByCode(mapVo);
		if(assDisposalRecordHouse != null){
			if(assDisposalRecordHouse.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assDisposalRecordHouseJson = assDisposalRecordHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDisposalRecordHouseJson);

	}
	/**
	 * 引入处置申请
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("dispose_type", mapVo.get("dispose_type"));
		mode.addAttribute("note", mapVo.get("note"));
		return "hrp/ass/asshouse/assdisposal/disposalrecord/Import";

	}
	
	/**
 * @Description 查询数据 资产处置申报(专用设备)
 * @param mapVo
 * @param mode
 * @return Map<String, Object>
 * @throws Exception
 */																	   
@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/queryAssDisposalApplySpecial", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssDisposalApplySpecial(@RequestParam Map<String, Object> mapVo, Model mode)
		throws Exception {

	mapVo.put("group_id", SessionManager.getGroupId());

	mapVo.put("hos_id", SessionManager.getHosId());

	mapVo.put("copy_code", SessionManager.getCopyCode());

	String assBackSpecial = assDisposalApplyHouseService.query(getPage(mapVo));

	return JSONObject.parseObject(assBackSpecial);

}
	/**
	 * 查询处置申请明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/assCheckViewUpdatePage", method = RequestMethod.GET)
	public String assCheckViewUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalApplyHouse assDisposalApplySpecial = new AssDisposalApplyHouse();

		assDisposalApplySpecial = assDisposalApplyHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalApplySpecial.getGroup_id());
		mode.addAttribute("hos_id", assDisposalApplySpecial.getHos_id());
		mode.addAttribute("copy_code", assDisposalApplySpecial.getCopy_code());
		mode.addAttribute("dis_a_no", assDisposalApplySpecial.getDis_a_no());
		mode.addAttribute("dispose_type", assDisposalApplySpecial.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalApplySpecial.getDispose_type_name());
		mode.addAttribute("note", assDisposalApplySpecial.getNote());
		mode.addAttribute("create_emp", assDisposalApplySpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalApplySpecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalApplySpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assDisposalApplySpecial.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalApplySpecial.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalApplySpecial.getAudit_emp_name());
		mode.addAttribute("state", assDisposalApplySpecial.getState());
		mode.addAttribute("state_name", assDisposalApplySpecial.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assdisposal/disposalrecord/ViewUpdate";
	}
	//查询明细列表  查看明细页使用
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/queryAssDisposalApplyDetailSpecial", method = RequestMethod.POST)
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
		String assBackDetailSpecial = assDisposalApplyDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);
		
	}
	//查询明细列表  添加页使用
		@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/addAssPlanDeptImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		/*	mapVo.put("group_id", SessionManager.getGroupId());
				
			mapVo.put("hos_id", SessionManager.getHosId());
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			String assBackDetailSpecial = assDisposalApplyDetailHouseService.query(getPage(mapVo));

			return JSONObject.parseObject(assBackDetailSpecial);
			*/
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInGeneral = assDisposalRecordHouseService.initAssCheckSpecial(mapVo);

			return JSONObject.parseObject(assAllotInGeneral);
		}
		
		//hrp/ass/assspecial/check/chkrecord/assViewSellOutGeneralPage
		/**
		 * @Description 追溯盘亏申请单跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/assViewSpecialPage", method = RequestMethod.GET)
		public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("dis_r_no", mapVo.get("dis_r_no"));
			return "hrp/ass/asshouse/assdisposal/disposalrecord/assPlanViewApply";
		}
		//queryCheckApSpecialByImport
		// 追溯盘亏申请
		@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/queryCheckApSpecialByImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assDisposalApplyHouseService.queryAssApplyDeptByPlanDept(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	/**
	 * @Description 更新跳转页面 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/assDisposalRecordHouseUpdatePage", method = RequestMethod.GET)
	public String assDisposalRecordHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalRecordHouse assDisposalRecordHouse = new AssDisposalRecordHouse();

		assDisposalRecordHouse = assDisposalRecordHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalRecordHouse.getGroup_id());
		mode.addAttribute("hos_id", assDisposalRecordHouse.getHos_id());
		mode.addAttribute("copy_code", assDisposalRecordHouse.getCopy_code());
		mode.addAttribute("dis_r_no", assDisposalRecordHouse.getDis_r_no());
		mode.addAttribute("dispose_type", assDisposalRecordHouse.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalRecordHouse.getDispose_type_name());
		mode.addAttribute("note", assDisposalRecordHouse.getNote());
		mode.addAttribute("create_emp", assDisposalRecordHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalRecordHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalRecordHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("record_date", DateUtil.dateToString(assDisposalRecordHouse.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalRecordHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalRecordHouse.getAudit_emp_name());
		mode.addAttribute("state", assDisposalRecordHouse.getState());
		mode.addAttribute("state_name", assDisposalRecordHouse.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05063",MyConfig.getSysPara("05063"));
		
		return "hrp/ass/asshouse/assdisposal/disposalrecord/update";
	}


	/**
	 * @Description 删除数据 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/deleteAssDisposalRecordHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalRecordHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalRecordHouse assDisposalRecordHouse = assDisposalRecordHouseService.queryByCode(mapVo);
			if(assDisposalRecordHouse != null){
				if(assDisposalRecordHouse.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
	      String de=    assDisposalRecordHouseService.deleteBatchAssApplyPlanMap(listVo);

		String assDisposalRecordHouseJson = assDisposalRecordHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assDisposalRecordHouseJson);

	}

	/**
	 * @Description 查询数据 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/queryAssDisposalRecordHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDisposalRecordHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());


		String assBackHouse = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackHouse = assDisposalRecordHouseService.query(getPage(mapVo));
			 
		}else{

			assBackHouse = assDisposalRecordHouseService.queryDetails(getPage(mapVo));
			 
		}	
		
		return JSONObject.parseObject(assBackHouse);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/queryAssDisposalRecordDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalRecordDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailHouse = assDisposalRecordDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailHouse);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/deleteAssDisposalRecordDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalRecordDetailHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssDisposalRecordHouse assDisposalRecordHouse = assDisposalRecordHouseService.queryByCode(mapVo);
				if(assDisposalRecordHouse != null){
					if(assDisposalRecordHouse.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assDisposalRecordDetailHouseJson = assDisposalRecordDetailHouseService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assDisposalRecordDetailHouseJson);
			
	}
	
	/**
	 * @Description 确认处置 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/updateConfirmDisposalRecordHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDisposalRecordHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalRecordHouse assDisposalRecordHouse = assDisposalRecordHouseService.queryByCode(mapVo);
			if(assDisposalRecordHouse == null || assDisposalRecordHouse.getState() > 0){
				continue;
			}
			List<AssDisposalRecordDetailHouse> detailList = assDisposalRecordDetailHouseService.queryByDisRNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalRecordDetailHouse detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalRecordHouse.getDispose_type());
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

		String assDisposalRecordHouseJson = assDisposalRecordHouseService.updateConfirmDisposalRecordHouse(listVo,listCardVo);
		return JSONObject.parseObject(assDisposalRecordHouseJson);

	}
	 
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalrecord/queryAssDisposalRecordHouseStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssDisposalRecordHouseStates(@RequestParam Map<String, Object> mapVo, Model mode)
	throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assDisposalRecordHouseService.queryAssDisposalRecordHouseStates(mapVo);

if(list.size() == 0){
	
	return JSONObject.parseObject("{\"state\":\"true\"}");
	
}else{
	
	String  str = "" ;
	for(String item : list){
		
		str += item +"," ;
	}
	
	return JSONObject.parseObject("{\"error\":\"盘点单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}

}
