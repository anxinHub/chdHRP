package com.chd.hrp.ass.controller.check.house;

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
import com.chd.hrp.ass.entity.check.general.AssChkAGeneral;
import com.chd.hrp.ass.entity.check.house.AssChkAHouse;
import com.chd.hrp.ass.entity.check.house.AssChkRHouse;
import com.chd.hrp.ass.entity.check.house.AssChkRdetailHouse;
import com.chd.hrp.ass.service.check.general.AssChkAGeneralService;
import com.chd.hrp.ass.service.check.general.AssChkAdetailGeneralService;
import com.chd.hrp.ass.service.check.house.AssChkAHouseService;
import com.chd.hrp.ass.service.check.house.AssChkAdetailHouseService;
import com.chd.hrp.ass.service.check.house.AssChkRHouseService;
import com.chd.hrp.ass.service.check.house.AssChkRdetailHouseService;

/**
 * 
 * @Description: 051001资产盘亏记录(土地)
 * @Table: ASS_CHK_R_House
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssChkRHouseController extends BaseController {
	private static Logger logger = Logger.getLogger(AssChkRHouseController.class);

	// 引入Service服务
	@Resource(name = "assChkRHouseService")
	private final AssChkRHouseService assChkRHouseService = null;

	@Resource(name = "assChkRdetailHouseService")
	private final AssChkRdetailHouseService assChkRdetailHouseService = null;
	
	@Resource(name="assChkAHouseService")
    private final AssChkAHouseService assChkAHouseService=null;
	
	@Resource(name="assChkAdetailHouseService")
    private final AssChkAdetailHouseService assChkAdetailHouseService=null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/assChkRecordHouseMainPage", method = RequestMethod.GET)
	public String assBackHouseMainPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/asscheck/chkrecord/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/assChkRecordHouseAddPage", method = RequestMethod.GET)
	public String assBackHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asscheck/chkrecord/add";

	}
	/**
	 * @Description 引入盘亏申请跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		
		mode.addAttribute("note", mapVo.get("note"));
		return "hrp/ass/asshouse/asscheck/chkrecord/Import";

	}
	/**
	 * 查询盘亏申请
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/queryCheckApHouse",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkAGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackGeneral = assChkAHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackGeneral);

	}
	/**
	 * 查看盘亏申报明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/assCheckViewUpdatePage", method = RequestMethod.GET)
	public String assChkAGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_chk_no", mapVo.get("ass_chk_no"));

		AssChkAHouse assChkAGeneral = new AssChkAHouse();

		assChkAGeneral = assChkAHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkAGeneral.getGroup_id());
		mode.addAttribute("hos_id", assChkAGeneral.getHos_id());
		mode.addAttribute("copy_code", assChkAGeneral.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkAGeneral.getAss_chk_no());
		mode.addAttribute("note", assChkAGeneral.getNote());
		mode.addAttribute("create_emp", assChkAGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkAGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkAGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkAGeneral.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkAGeneral.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkAGeneral.getAudit_emp_name());
		mode.addAttribute("state", assChkAGeneral.getState());
		mode.addAttribute("state_name", assChkAGeneral.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asscheck/chkrecord/Viewupdate";
	}
	//查询明细列表  
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/queryAssChkApplyDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkAdetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("ass_chk_no").toString();
		String car[] =carno.split(",");
		  final List<String> ids = new ArrayList<String>();  
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("ass_chk_no", ids);
		String assBackDetailGeneral = assChkAdetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailGeneral);
		
	}
	//添加明细到记录
		@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/addAssPlanDeptImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		/*
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String carno = mapVo.get("ass_chk_no").toString();
			String car[] =carno.split(",");
			  final List<String> ids = new ArrayList<String>();  
			for (int i = 0; i < car.length; i++) {
				ids.add(car[i]);
			}
			mapVo.put("ass_chk_no", ids);
			
			
			String assBackDetailGeneral = assChkAdetailHouseService.query(getPage(mapVo));

			return JSONObject.parseObject(assBackDetailGeneral);*/
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInGeneral = assChkRHouseService.initAssCheckHouse(mapVo);

			return JSONObject.parseObject(assAllotInGeneral);
		}
		
		//hrp/ass/assspecial/check/chkrecord/assViewSellOutGeneralPage
		/**
		 * @Description 追溯盘亏申请单跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/assViewSpecialPage", method = RequestMethod.GET)
		public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("rss_chk_no", mapVo.get("ass_chk_no"));
			return "hrp/ass/asshouse/asscheck/chkrecord/assPlanViewApply";
		}
		//queryCheckApSpecialByImport
		// 追溯盘亏申请
		@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/queryCheckApSpecialByImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assChkAHouseService.queryAssApplyDeptByPlanDept(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	/**
	 * @Description 添加数据 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/saveAssChkRecordHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkRHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssChkRHouse assChkRHouse = assChkRHouseService.queryByCode(mapVo);
		if(assChkRHouse != null){
			if(assChkRHouse.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assChkRHouseJson = assChkRHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChkRHouseJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/assChkRecordHouseUpdatePage", method = RequestMethod.GET)
	public String assChkRHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChkRHouse assChkRHouse = new AssChkRHouse();

		assChkRHouse = assChkRHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkRHouse.getGroup_id());
		mode.addAttribute("hos_id", assChkRHouse.getHos_id());
		mode.addAttribute("copy_code", assChkRHouse.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkRHouse.getAss_chk_no());
		mode.addAttribute("note", assChkRHouse.getNote());
		mode.addAttribute("create_emp", assChkRHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkRHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkRHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkRHouse.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkRHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkRHouse.getAudit_emp_name());
		mode.addAttribute("state", assChkRHouse.getState());
		mode.addAttribute("state_name", assChkRHouse.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asscheck/chkrecord/update";
	}


	/**
	 * @Description 删除数据 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/deleteAssChkRecordHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkRHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_chk_no", ids[3]);
			
			AssChkRHouse assChkRHouse = assChkRHouseService.queryByCode(mapVo);
			if(assChkRHouse != null){
				if(assChkRHouse.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
	      String de=    assChkRHouseService.deleteBatchAssApplyPlanMap(listVo);

		String assChkRHouseJson = assChkRHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkRHouseJson);

	}

	/**
	 * @Description 查询数据 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/queryAssChkRecordHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkRHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackHouse = assChkRHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackHouse);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/queryAssChkRecordDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkRdetailHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailHouse = assChkRdetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailHouse);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/deleteAssChkRecordDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkRdetailHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("ass_chk_no", ids[3])   ;
				mapVo.put("ass_card_no", ids[4]);
				AssChkRHouse assChkRHouse = assChkRHouseService.queryByCode(mapVo);
				if(assChkRHouse != null){
					if(assChkRHouse.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assChkRdetailHouseJson = assChkRdetailHouseService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkRdetailHouseJson);
			
	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkrecord/updateConfirmChkRecordHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkRHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("ass_chk_no", ids[3]);
			mapVo.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssChkRHouse assChkRHouse = assChkRHouseService.queryByCode(mapVo);
			if(assChkRHouse == null || assChkRHouse.getState() > 0){
				continue;
			}
			
			List<AssChkRdetailHouse> detailList = assChkRdetailHouseService.queryByAssChkNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssChkRdetailHouse detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", 12);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if(map.containsKey(detail.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已盘亏的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作盘亏确认! \"}");
			}
			listVo.add(mapVo);

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assChkRHouseJson = assChkRHouseService.updateConfirmChkRHouse(listVo,listCardVo);

		return JSONObject.parseObject(assChkRHouseJson);

	}
	
}
