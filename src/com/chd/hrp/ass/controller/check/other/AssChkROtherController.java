package com.chd.hrp.ass.controller.check.other;

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
import com.chd.hrp.ass.entity.check.other.AssChkRdetailOther;
import com.chd.hrp.ass.entity.check.other.AssChkAOther;
import com.chd.hrp.ass.entity.check.other.AssChkROther;
import com.chd.hrp.ass.service.check.other.AssChkRdetailOtherService;
import com.chd.hrp.ass.service.check.other.AssChkAOtherService;
import com.chd.hrp.ass.service.check.other.AssChkAdetailOtherService;
import com.chd.hrp.ass.service.check.other.AssChkROtherService;

/**
 * 
 * @Description: 051001资产盘亏记录(专用设备)
 * @Table: ASS_CHK_R_Other
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssChkROtherController extends BaseController {
	private static Logger logger = Logger.getLogger(AssChkROtherController.class);

	// 引入Service服务
	@Resource(name = "assChkROtherService")
	private final AssChkROtherService assChkROtherService = null;

	@Resource(name = "assChkRdetailOtherService")
	private final AssChkRdetailOtherService assChkRdetailOtherService = null;
	

   @Resource(name="assChkAOtherService")
    private final AssChkAOtherService assChkAOtherService=null;
	
	@Resource(name="assChkAdetailOtherService")
    private final AssChkAdetailOtherService assChkAdetailOtherService=null;
	
	

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/assChkRecordOtherMainPage", method = RequestMethod.GET)
	public String assBackOtherMainPage(Model mode) throws Exception {

		return "hrp/ass/assother/asscheck/chkrecord/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/assChkRecordOtherAddPage", method = RequestMethod.GET)
	public String assBackOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asscheck/chkrecord/add";

	}
	/**
	 * @Description 引入盘亏申请跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		
		mode.addAttribute("note", mapVo.get("note"));
		return "hrp/ass/assother/asscheck/chkrecord/Import";

	}
	/**
	 * 查询盘亏申请
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/queryCheckApOther",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkAOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackOther = assChkAOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackOther);

	}
	/**
	 * 查看盘亏申报明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/assCheckViewUpdatePage", method = RequestMethod.GET)
	public String assChkAOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_chk_no", mapVo.get("ass_chk_no"));

		AssChkAOther assChkAOther = new AssChkAOther();

		assChkAOther = assChkAOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkAOther.getGroup_id());
		mode.addAttribute("hos_id", assChkAOther.getHos_id());
		mode.addAttribute("copy_code", assChkAOther.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkAOther.getAss_chk_no());
		mode.addAttribute("note", assChkAOther.getNote());
		mode.addAttribute("create_emp", assChkAOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkAOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkAOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkAOther.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkAOther.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkAOther.getAudit_emp_name());
		mode.addAttribute("state", assChkAOther.getState());
		mode.addAttribute("state_name", assChkAOther.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asscheck/chkrecord/Viewupdate";
	}
	//查询明细列表  
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/queryAssChkApplyDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkAdetailOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
        
		String assBackDetailOther = assChkAdetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailOther);
		
	}
	//添加明细到记录
		@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/addAssPlanDeptImport", method = RequestMethod.POST)
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
			
	        
			String assBackDetailOther = assChkAdetailOtherService.query(getPage(mapVo));

			return JSONObject.parseObject(assBackDetailOther);*/
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInGeneral = assChkROtherService.initAssCheckOther(mapVo);

			return JSONObject.parseObject(assAllotInGeneral);
		}
		/**
		 * @Description 追溯盘亏申请单跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/assViewSpecialPage", method = RequestMethod.GET)
		public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("rss_chk_no", mapVo.get("ass_chk_no"));
			return "/hrp/ass/assother/asscheck/chkrecord/assPlanViewApply";
		}
		//queryCheckApSpecialByImport
		// 追溯盘亏申请
		@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/queryCheckApSpecialByImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assChkAOtherService.queryAssApplyDeptByPlanDept(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	/**
	 * @Description 添加数据 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/saveAssChkRecordOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkROther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssChkROther assChkROther = assChkROtherService.queryByCode(mapVo);
		if(assChkROther != null){
			if(assChkROther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assChkROtherJson = assChkROtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChkROtherJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/assChkRecordOtherUpdatePage", method = RequestMethod.GET)
	public String assChkROtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChkROther assChkROther = new AssChkROther();

		assChkROther = assChkROtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkROther.getGroup_id());
		mode.addAttribute("hos_id", assChkROther.getHos_id());
		mode.addAttribute("copy_code", assChkROther.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkROther.getAss_chk_no());
		mode.addAttribute("note", assChkROther.getNote());
		mode.addAttribute("create_emp", assChkROther.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkROther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkROther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkROther.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkROther.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkROther.getAudit_emp_name());
		mode.addAttribute("state", assChkROther.getState());
		mode.addAttribute("state_name", assChkROther.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asscheck/chkrecord/update";
	}


	/**
	 * @Description 删除数据 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/deleteAssChkRecordOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkROther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkROther assChkROther = assChkROtherService.queryByCode(mapVo);
			if(assChkROther != null){
				if(assChkROther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
	      String de=    assChkROtherService.deleteBatchAssApplyPlanMap(listVo);

		String assChkROtherJson = assChkROtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkROtherJson);

	}

	/**
	 * @Description 查询数据 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/queryAssChkRecordOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkROther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackOther = assChkROtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackOther);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/queryAssChkRecordDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkRdetailOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailOther = assChkRdetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailOther);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/deleteAssChkRecordDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkRdetailOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssChkROther assChkROther = assChkROtherService.queryByCode(mapVo);
				if(assChkROther != null){
					if(assChkROther.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assChkRdetailOtherJson = assChkRdetailOtherService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkRdetailOtherJson);
			
	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/check/chkrecord/updateConfirmChkRecordOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkROther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkROther assChkROther = assChkROtherService.queryByCode(mapVo);
			if(assChkROther == null || assChkROther.getState() > 0){
				continue;
			}
			
			List<AssChkRdetailOther> detailList = assChkRdetailOtherService.queryByAssChkNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssChkRdetailOther detail : detailList) {
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

		String assChkROtherJson = assChkROtherService.updateConfirmChkROther(listVo,listCardVo);

		return JSONObject.parseObject(assChkROtherJson);

	}
	
}
