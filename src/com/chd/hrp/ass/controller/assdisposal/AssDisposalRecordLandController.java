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
import com.chd.hrp.ass.entity.assdisposal.land.AssDisposalApplyLand;
import com.chd.hrp.ass.entity.assdisposal.land.AssDisposalRecordDetailLand;
import com.chd.hrp.ass.entity.assdisposal.land.AssDisposalRecordLand;
import com.chd.hrp.ass.service.assdisposal.land.AssDisposalApplyDetailLandService;
import com.chd.hrp.ass.service.assdisposal.land.AssDisposalApplyLandService;
import com.chd.hrp.ass.service.assdisposal.land.AssDisposalRecordDetailLandService;
import com.chd.hrp.ass.service.assdisposal.land.AssDisposalRecordLandService;

/**
 * 
 * @Description: 051001资产处置记录(土地)
 * @Table: ASS_DISPOSAL_R_LAND 
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssDisposalRecordLandController extends BaseController {
	private static Logger logger = Logger.getLogger(AssDisposalRecordLandController.class);

	// 引入Service服务
	@Resource(name = "assDisposalRecordLandService")
	private final AssDisposalRecordLandService assDisposalRecordLandService = null;

	@Resource(name = "assDisposalRecordDetailLandService")
	private final AssDisposalRecordDetailLandService assDisposalRecordDetailLandService = null;
	
	// 引入Service服务
		@Resource(name = "assDisposalApplyLandService")
		private final AssDisposalApplyLandService assDisposalApplyLandService = null;

		@Resource(name = "assDisposalApplyDetailLandService")
		private final AssDisposalApplyDetailLandService assDisposalApplyDetailLandService = null;
		
		
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/assDisposalRecordLandMainPage", method = RequestMethod.GET)
	public String assBackLandMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05073",MyConfig.getSysPara("05073"));
		
		return "hrp/ass/assland/assdisposal/disposalrecord/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/assDisposalRecordLandAddPage", method = RequestMethod.GET)
	public String assBackLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assdisposal/disposalrecord/add";

	}
	
	/**
	 * @Description 添加数据 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/saveAssDisposalRecordLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDisposalRecordLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssDisposalRecordLand assDisposalRecordLand = assDisposalRecordLandService.queryByCode(mapVo);
		if(assDisposalRecordLand != null){
			if(assDisposalRecordLand.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assDisposalRecordLandJson = assDisposalRecordLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDisposalRecordLandJson);

	}
	/**
	 * 引入处置申请
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("dispose_type", mapVo.get("dispose_type"));
		mode.addAttribute("note", mapVo.get("note"));
		return "hrp/ass/assland/assdisposal/disposalrecord/Import";

	}
	
	/**
 * @Description 查询数据 资产处置申报(土地)
 * @param mapVo
 * @param mode
 * @return Map<String, Object>
 * @throws Exception
 */																	   
@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/queryAssDisposalApplySpecial", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssDisposalApplySpecial(@RequestParam Map<String, Object> mapVo, Model mode)
		throws Exception {

	mapVo.put("group_id", SessionManager.getGroupId());

	mapVo.put("hos_id", SessionManager.getHosId());

	mapVo.put("copy_code", SessionManager.getCopyCode());

	String assBackSpecial = assDisposalApplyLandService.query(getPage(mapVo));

	return JSONObject.parseObject(assBackSpecial);

}
	/**
	 * 查询处置申请明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/assCheckViewUpdatePage", method = RequestMethod.GET)
	public String assCheckViewUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalApplyLand assDisposalApplySpecial = new AssDisposalApplyLand();

		assDisposalApplySpecial = assDisposalApplyLandService.queryByCode(mapVo);

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
		
		return "hrp/ass/assland/assdisposal/disposalrecord/ViewUpdate";
	}
	//查询明细列表  查看明细页使用
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/queryAssDisposalApplyDetailSpecial", method = RequestMethod.POST)
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
		String assBackDetailSpecial = assDisposalApplyDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);
		
	}

	//查询明细列表  添加页使用
		@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/addAssPlanDeptImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		/*	mapVo.put("group_id", SessionManager.getGroupId());
				
			mapVo.put("hos_id", SessionManager.getHosId());
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	        
			String assBackDetailSpecial = assDisposalApplyDetailLandService.query(getPage(mapVo));

			return JSONObject.parseObject(assBackDetailSpecial);*/
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInGeneral = assDisposalRecordLandService.initAssCheckSpecial(mapVo);

			return JSONObject.parseObject(assAllotInGeneral);
		}
		
		//hrp/ass/assspecial/check/chkrecord/assViewSellOutGeneralPage
		/**
		 * @Description 追溯盘亏申请单跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/assViewSpecialPage", method = RequestMethod.GET)
		public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("dis_r_no", mapVo.get("dis_r_no"));
			return "hrp/ass/assland/assdisposal/disposalrecord/assPlanViewApply";
		}
		//queryCheckApSpecialByImport
		// 追溯盘亏申请
		@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/queryCheckApSpecialByImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assDisposalApplyLandService.queryAssApplyDeptByPlanDept(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	/**
	 * @Description 更新跳转页面 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/assDisposalRecordLandUpdatePage", method = RequestMethod.GET)
	public String assDisposalRecordLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalRecordLand assDisposalRecordLand = new AssDisposalRecordLand();

		assDisposalRecordLand = assDisposalRecordLandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalRecordLand.getGroup_id());
		mode.addAttribute("hos_id", assDisposalRecordLand.getHos_id());
		mode.addAttribute("copy_code", assDisposalRecordLand.getCopy_code());
		mode.addAttribute("dis_r_no", assDisposalRecordLand.getDis_r_no());
		mode.addAttribute("dispose_type", assDisposalRecordLand.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalRecordLand.getDispose_type_name());
		mode.addAttribute("note", assDisposalRecordLand.getNote());
		mode.addAttribute("create_emp", assDisposalRecordLand.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalRecordLand.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalRecordLand.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("record_date", DateUtil.dateToString(assDisposalRecordLand.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalRecordLand.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalRecordLand.getAudit_emp_name());
		mode.addAttribute("state", assDisposalRecordLand.getState());
		mode.addAttribute("state_name", assDisposalRecordLand.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05073",MyConfig.getSysPara("05073"));
		
		return "hrp/ass/assland/assdisposal/disposalrecord/update";
	}


	/**
	 * @Description 删除数据 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/deleteAssDisposalRecordLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalRecordLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalRecordLand assDisposalRecordLand = assDisposalRecordLandService.queryByCode(mapVo);
			if(assDisposalRecordLand != null){
				if(assDisposalRecordLand.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
	      String de=    assDisposalRecordLandService.deleteBatchAssApplyPlanMap(listVo);

		String assDisposalRecordLandJson = assDisposalRecordLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assDisposalRecordLandJson);

	}

	/**
	 * @Description 查询数据 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/queryAssDisposalRecordLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDisposalRecordLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		
		String assBackLand = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackLand = assDisposalRecordLandService.query(getPage(mapVo));
			 
		}else{

			assBackLand = assDisposalRecordLandService.queryDetails(getPage(mapVo));
			 
		}

		return JSONObject.parseObject(assBackLand);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/queryAssDisposalRecordDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalRecordDetailLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailLand = assDisposalRecordDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailLand);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/deleteAssDisposalRecordDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalRecordDetailLand(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssDisposalRecordLand assDisposalRecordLand = assDisposalRecordLandService.queryByCode(mapVo);
				if(assDisposalRecordLand != null){
					if(assDisposalRecordLand.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assDisposalRecordDetailLandJson = assDisposalRecordDetailLandService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assDisposalRecordDetailLandJson);
			
	}
	
	/**
	 * @Description 确认处置 051001资产处置记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/updateConfirmDisposalRecordLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDisposalRecordLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalRecordLand assDisposalRecordLand = assDisposalRecordLandService.queryByCode(mapVo);
			if(assDisposalRecordLand == null || assDisposalRecordLand.getState() == 2){
				continue;
			}
			List<AssDisposalRecordDetailLand> detailList = assDisposalRecordDetailLandService.queryByDisRNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalRecordDetailLand detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalRecordLand.getDispose_type());
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

		String assDisposalRecordLandJson = assDisposalRecordLandService.updateConfirmDisposalRecordLand(listVo,listCardVo);
		return JSONObject.parseObject(assDisposalRecordLandJson);

	}
	

	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/assland/assdisposal/disposalrecord/queryAssDisposalRecordLandStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssDisposalRecordLandStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assDisposalRecordLandService.queryAssDisposalRecordLandStates(mapVo);

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
