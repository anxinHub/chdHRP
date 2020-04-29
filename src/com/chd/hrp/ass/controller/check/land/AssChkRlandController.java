package com.chd.hrp.ass.controller.check.land;

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
import com.chd.hrp.ass.entity.check.land.AssChkAland;
import com.chd.hrp.ass.entity.check.land.AssChkRdetailLand;
import com.chd.hrp.ass.entity.check.land.AssChkRland;
import com.chd.hrp.ass.service.check.land.AssChkAdetailLandService;
import com.chd.hrp.ass.service.check.land.AssChkAlandService;
import com.chd.hrp.ass.service.check.land.AssChkRdetailLandService;
import com.chd.hrp.ass.service.check.land.AssChkRlandService;

/**
 * 
 * @Description: 051001资产盘亏记录(土地)
 * @Table: ASS_CHK_R_LAND
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssChkRlandController extends BaseController {
	private static Logger logger = Logger.getLogger(AssChkRlandController.class);

	// 引入Service服务
	@Resource(name = "assChkRlandService")
	private final AssChkRlandService assChkRlandService = null;

	@Resource(name = "assChkRdetailLandService")
	private final AssChkRdetailLandService assChkRdetailLandService = null;
	
	@Resource(name="assChkAlandService")
    private final AssChkAlandService assChkAlandService=null;
	
	@Resource(name="assChkAdetailLandService")
    private final AssChkAdetailLandService assChkAdetailLandService=null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/assChkRecordLandMainPage", method = RequestMethod.GET)
	public String assBackLandMainPage(Model mode) throws Exception {

		return "hrp/ass/assland/asscheck/chkrecord/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/assChkRecordLandAddPage", method = RequestMethod.GET)
	public String assBackLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asscheck/chkrecord/add";

	}
	
	/**
	 * @Description 引入盘亏申请跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		
		mode.addAttribute("note", mapVo.get("note"));
		return "hrp/ass/assland/asscheck/chkrecord/Import";

	}
	/**
	 * 查询盘亏申请
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/queryCheckApLand",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkALand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackLand = assChkAlandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackLand);

	}
	/**
	 * 查看盘亏申报明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/assCheckViewUpdatePage", method = RequestMethod.GET)
	public String assChkALandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_chk_no", mapVo.get("ass_chk_no"));

		AssChkAland assChkALand = new AssChkAland();

		assChkALand = assChkAlandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkALand.getGroup_id());
		mode.addAttribute("hos_id", assChkALand.getHos_id());
		mode.addAttribute("copy_code", assChkALand.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkALand.getAss_chk_no());
		mode.addAttribute("note", assChkALand.getNote());
		mode.addAttribute("create_emp", assChkALand.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkALand.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkALand.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkALand.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkALand.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkALand.getAudit_emp_name());
		mode.addAttribute("state", assChkALand.getState());
		mode.addAttribute("state_name", assChkALand.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asscheck/chkrecord/Viewupdate";
	}
	//查询明细列表  
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/queryAssChkApplyDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkAdetailLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
		String assBackDetailLand = assChkAdetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailLand);
		
	}
	//添加明细到记录
		@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/addAssPlanDeptImport", method = RequestMethod.POST)
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
			
			String assBackDetailLand = assChkAdetailLandService.query(getPage(mapVo));

			return JSONObject.parseObject(assBackDetailLand);*/
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInGeneral = assChkRlandService.initAssCheckLand(mapVo);

			return JSONObject.parseObject(assAllotInGeneral);
		}
		
		//hrp/ass/assspecial/check/chkrecord/assViewSellOutGeneralPage
		/**
		 * @Description 追溯盘亏申请单跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/assViewSpecialPage", method = RequestMethod.GET)
		public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("rss_chk_no", mapVo.get("ass_chk_no"));
			return "hrp/ass/assland/asscheck/chkrecord/assPlanViewApply";
		}
		//queryCheckApSpecialByImport
		// 追溯盘亏申请
		@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/queryCheckApSpecialByImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assChkAlandService.queryAssApplyDeptByPlanDept(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	/**
	 * @Description 添加数据 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/saveAssChkRecordLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkRland(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssChkRland assChkRland = assChkRlandService.queryByCode(mapVo);
		if(assChkRland != null){
			if(assChkRland.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assChkRlandJson = assChkRlandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChkRlandJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/assChkRecordLandUpdatePage", method = RequestMethod.GET)
	public String assChkRlandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChkRland assChkRland = new AssChkRland();

		assChkRland = assChkRlandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkRland.getGroup_id());
		mode.addAttribute("hos_id", assChkRland.getHos_id());
		mode.addAttribute("copy_code", assChkRland.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkRland.getAss_chk_no());
		mode.addAttribute("note", assChkRland.getNote());
		mode.addAttribute("create_emp", assChkRland.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkRland.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkRland.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkRland.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkRland.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkRland.getAudit_emp_name());
		mode.addAttribute("state", assChkRland.getState());
		mode.addAttribute("state_name", assChkRland.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asscheck/chkrecord/update";
	}


	/**
	 * @Description 删除数据 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/deleteAssChkRecordLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkRland(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkRland assChkRland = assChkRlandService.queryByCode(mapVo);
			if(assChkRland != null){
				if(assChkRland.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
	      String de=    assChkRlandService.deleteBatchAssApplyPlanMap(listVo);

		String assChkRlandJson = assChkRlandService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkRlandJson);

	}

	/**
	 * @Description 查询数据 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/queryAssChkRecordLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkRland(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackLand = assChkRlandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackLand);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/queryAssChkRecordDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkRdetailLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailLand = assChkRdetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailLand);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/deleteAssChkRecordDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkRdetailLand(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssChkRland assChkRland = assChkRlandService.queryByCode(mapVo);
				if(assChkRland != null){
					if(assChkRland.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assChkRdetailLandJson = assChkRdetailLandService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkRdetailLandJson);
			
	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkrecord/updateConfirmChkRecordLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkRland(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkRland assChkRland = assChkRlandService.queryByCode(mapVo);
			if(assChkRland == null || assChkRland.getState() == 2){
				continue;
			}
			
			List<AssChkRdetailLand> detailList = assChkRdetailLandService.queryByAssChkNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssChkRdetailLand detail : detailList) {
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

		String assChkRlandJson = assChkRlandService.updateConfirmChkRland(listVo,listCardVo);

		return JSONObject.parseObject(assChkRlandJson);

	}
	
}
