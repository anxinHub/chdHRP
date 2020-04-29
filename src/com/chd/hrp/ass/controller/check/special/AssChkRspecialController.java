package com.chd.hrp.ass.controller.check.special;

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
import com.chd.hrp.ass.entity.check.special.AssChkAdetailSpecial;
import com.chd.hrp.ass.entity.check.special.AssChkAspecial;
import com.chd.hrp.ass.entity.check.special.AssChkRdetailSpecial;
import com.chd.hrp.ass.entity.check.special.AssChkRspecial;
import com.chd.hrp.ass.service.check.special.AssChkAdetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssChkAspecialService;
import com.chd.hrp.ass.service.check.special.AssChkRdetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssChkRspecialService;

/**
 * 
 * @Description: 051001资产盘亏记录(专用设备)
 * @Table: ASS_CHK_R_SPECIAL
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssChkRspecialController extends BaseController {
	private static Logger logger = Logger.getLogger(AssChkRspecialController.class);

	// 引入Service服务
	@Resource(name = "assChkRspecialService")
	private final AssChkRspecialService assChkRspecialService = null;
	
	@Resource(name = "assChkAspecialService")
	private final AssChkAspecialService assChkAspecialService = null;

	@Resource(name = "assChkAdetailSpecialService")
	private final AssChkAdetailSpecialService assChkAdetailSpecialService = null;
	
	@Resource(name = "assChkRdetailSpecialService")
	private final AssChkRdetailSpecialService assChkRdetailSpecialService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/assChkRecordSpecialMainPage", method = RequestMethod.GET)
	public String assBackSpecialMainPage(Model mode) throws Exception {

		return "hrp/ass/assspecial/asscheck/chkrecord/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/assChkRecordSpecialAddPage", method = RequestMethod.GET)
	public String assBackSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asscheck/chkrecord/add";

	}
	/**
	 * @Description 引入盘亏申请跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		
		mode.addAttribute("note", mapVo.get("note"));
	
		return "hrp/ass/assspecial/asscheck/chkrecord/Import";

	}
	/**
	 * 查询盘亏申请
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/queryCheckApSpecial",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkAspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackSpecial = assChkAspecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackSpecial);

	}
	/**
	 * 查看盘亏申报明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/assCheckViewUpdatePage", method = RequestMethod.GET)
	public String assChkAspecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_chk_no", mapVo.get("ass_chk_no"));

		AssChkAspecial assChkAspecial = new AssChkAspecial();

		assChkAspecial = assChkAspecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkAspecial.getGroup_id());
		mode.addAttribute("hos_id", assChkAspecial.getHos_id());
		mode.addAttribute("copy_code", assChkAspecial.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkAspecial.getAss_chk_no());
		mode.addAttribute("note", assChkAspecial.getNote());
		mode.addAttribute("create_emp", assChkAspecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkAspecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkAspecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkAspecial.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkAspecial.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkAspecial.getAudit_emp_name());
		mode.addAttribute("state", assChkAspecial.getState());
		mode.addAttribute("state_name", assChkAspecial.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asscheck/chkrecord/Viewupdate";
	}
	//查询明细列表  
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/queryAssChkApplyDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkAdetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
		
        
		String assBackDetailSpecial = assChkAdetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);
		
	}
	//添加明细到记录
		@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/addAssPlanDeptImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			
			/*mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
				
			mapVo.put("copy_code", SessionManager.getCopyCode());
	     
			String carno = mapVo.get("ass_chk_no").toString();
			String car[] =carno.split(",");
			  final List<String> ids = new ArrayList<String>();  
			for (int i = 0; i < car.length; i++) {
				ids.add(car[i]);
			}
			mapVo.put("ass_chk_no", ids);
			
			String assBackDetailSpecial = assChkAdetailSpecialService.query(getPage(mapVo));

			return JSONObject.parseObject(assBackDetailSpecial);*/
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInGeneral = assChkRspecialService.initAssCheckSpecial(mapVo);

			return JSONObject.parseObject(assAllotInGeneral);
		}
		
		//hrp/ass/assspecial/check/chkrecord/assViewSellOutGeneralPage
		/**
		 * @Description 追溯盘亏申请单跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/assViewSpecialPage", method = RequestMethod.GET)
		public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("rss_chk_no", mapVo.get("ass_chk_no"));
			return "hrp/ass/assspecial/asscheck/chkrecord/assPlanViewApply";
		}
		//queryCheckApSpecialByImport
		// 追溯盘亏申请
		@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/queryCheckApSpecialByImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assChkAspecialService.queryAssApplyDeptByPlanDept(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	/**
	 * @Description 添加数据 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/saveAssChkRecordSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkRspecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssChkRspecial assChkRspecial = assChkRspecialService.queryByCode(mapVo);
		if(assChkRspecial != null){
			if(assChkRspecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assChkRspecialJson = assChkRspecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChkRspecialJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/assChkRecordSpecialUpdatePage", method = RequestMethod.GET)
	public String assChkRspecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChkRspecial assChkRspecial = new AssChkRspecial();

		assChkRspecial = assChkRspecialService.queryByCode(mapVo);
		mode.addAttribute("is_import", mapVo.get("is_import"));
		mode.addAttribute("group_id", assChkRspecial.getGroup_id());
		mode.addAttribute("hos_id", assChkRspecial.getHos_id());
		mode.addAttribute("copy_code", assChkRspecial.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkRspecial.getAss_chk_no());
		mode.addAttribute("note", assChkRspecial.getNote());
		mode.addAttribute("create_emp", assChkRspecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkRspecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkRspecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkRspecial.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkRspecial.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkRspecial.getAudit_emp_name());
		mode.addAttribute("state", assChkRspecial.getState());
		mode.addAttribute("state_name", assChkRspecial.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asscheck/chkrecord/update";
	}


	/**
	 * @Description 删除数据 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/deleteAssChkRecordSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkRspecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkRspecial assChkRspecial = assChkRspecialService.queryByCode(mapVo);
			if(assChkRspecial != null){
				if(assChkRspecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
	      String de=    assChkRspecialService.deleteBatchAssApplyPlanMap(listVo);

		String assChkRspecialJson = assChkRspecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkRspecialJson);

	}

	/**
	 * @Description 查询数据 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/queryAssChkRecordSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkRspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String assBackSpecial="";
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		/*if (mapVo.get("ass_card_no")!=null ||mapVo.get("ass_card_no") !="" ) {
			mapVo.put("ass_card_no", mapVo.get("ass_card_no"));
			assBackSpecial =	assChkAdetailSpecialService.query(getPage(mapVo));
		}else {*/
		assBackSpecial = assChkRspecialService.query(getPage(mapVo));
		/*}*/
		return JSONObject.parseObject(assBackSpecial);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/queryAssChkRecordDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkRdetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		String assBackDetailSpecial ="";
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
	/*	if (mapVo.get("ass_chk_no")!=null ||mapVo.get("ass_chk_no") !="" ) {
			mapVo.put("ass_chk_no", mapVo.get("ass_chk_no"));
			 assBackDetailSpecial =	assChkAdetailSpecialService.query(getPage(mapVo));
		}else {*/
        
		 assBackDetailSpecial = assChkRdetailSpecialService.query(getPage(mapVo));
	/*	 }*/

		return JSONObject.parseObject(assBackDetailSpecial);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/deleteAssChkRecordDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkRdetailSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssChkRspecial assChkRspecial = assChkRspecialService.queryByCode(mapVo);
				if(assChkRspecial != null){
					if(assChkRspecial.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assChkRdetailSpecialJson = assChkRdetailSpecialService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkRdetailSpecialJson);
			
	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/chkrecord/updateConfirmChkRecordSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkRspecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkRspecial assChkRspecial = assChkRspecialService.queryByCode(mapVo);
			if(assChkRspecial == null || assChkRspecial.getState() == 2){
				continue;
			}
			
			List<AssChkRdetailSpecial> detailList = assChkRdetailSpecialService.queryByAssChkNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssChkRdetailSpecial detail : detailList) {
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

		String assChkRspecialJson = assChkRspecialService.updateConfirmChkRspecial(listVo,listCardVo);

		return JSONObject.parseObject(assChkRspecialJson);

	}
	
}
