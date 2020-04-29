package com.chd.hrp.ass.controller.check.inassets;

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
import com.chd.hrp.ass.entity.check.inassets.AssChkAinassets;
import com.chd.hrp.ass.entity.check.inassets.AssChkRdetailInassets;
import com.chd.hrp.ass.entity.check.inassets.AssChkRinassets;
import com.chd.hrp.ass.service.check.inassets.AssChkAdetailInassetsService;
import com.chd.hrp.ass.service.check.inassets.AssChkAinassetsService;
import com.chd.hrp.ass.service.check.inassets.AssChkRdetailInassetsService;
import com.chd.hrp.ass.service.check.inassets.AssChkRinassetsService;

/**
 * 
 * @Description: 051001资产盘亏记录(无形资产)
 * @Table: ASS_CHK_R_INASSETS
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssChkRinassetsController extends BaseController {
	private static Logger logger = Logger.getLogger(AssChkRinassetsController.class);

	// 引入Service服务
	@Resource(name = "assChkRinassetsService")
	private final AssChkRinassetsService assChkRinassetsService = null;

	@Resource(name = "assChkRdetailInassetsService")
	private final AssChkRdetailInassetsService assChkRdetailInassetsService = null;
	

   @Resource(name="assChkAinassetsService")
    private final AssChkAinassetsService assChkAinassetsService=null;
	
	@Resource(name="assChkAdetailInassetsService")
    private final AssChkAdetailInassetsService assChkAdetailInassetsService=null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/assChkRecordInassetsMainPage", method = RequestMethod.GET)
	public String assBackInassetsMainPage(Model mode) throws Exception {

		return "hrp/ass/assinassets/asscheck/chkrecord/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/assChkRecordInassetsAddPage", method = RequestMethod.GET)
	public String assBackInassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/asscheck/chkrecord/add";

	}
	/**
	 * @Description 引入盘亏申请跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		
		mode.addAttribute("note", mapVo.get("note"));
		return "hrp/ass/assinassets/asscheck/chkrecord/Import";

	}
	/**
	 * 查询盘亏申请
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/queryCheckApInassets",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkAInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackInassets = assChkAinassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackInassets);

	}
	/**
	 * 查看盘亏申报明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/assCheckViewUpdatePage", method = RequestMethod.GET)
	public String assChkAInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_chk_no", mapVo.get("ass_chk_no"));

		AssChkAinassets assChkAInassets = new AssChkAinassets();

		assChkAInassets = assChkAinassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkAInassets.getGroup_id());
		mode.addAttribute("hos_id", assChkAInassets.getHos_id());
		mode.addAttribute("copy_code", assChkAInassets.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkAInassets.getAss_chk_no());
		mode.addAttribute("note", assChkAInassets.getNote());
		mode.addAttribute("create_emp", assChkAInassets.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkAInassets.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkAInassets.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkAInassets.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkAInassets.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkAInassets.getAudit_emp_name());
		mode.addAttribute("state", assChkAInassets.getState());
		mode.addAttribute("state_name", assChkAInassets.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/asscheck/chkrecord/Viewupdate";
	}
	//查询明细列表  
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/queryAssChkApplyDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkAdetailInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
		String assBackDetailInassets = assChkAdetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailInassets);
		
	}
	//添加明细到记录
		@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/addAssPlanDeptImport", method = RequestMethod.POST)
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
			
			String assBackDetailInassets = assChkAdetailInassetsService.query(getPage(mapVo));

			return JSONObject.parseObject(assBackDetailInassets);*/
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInGeneral = assChkRinassetsService.initAssCheckInassets(mapVo);

			return JSONObject.parseObject(assAllotInGeneral);
		}
		/**
		 * @Description 追溯盘亏申请单跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/ViewinassetsPage", method = RequestMethod.GET)
		public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("rss_chk_no", mapVo.get("ass_chk_no"));
			return "/hrp/ass/assinassets/asscheck/chkrecord/assPlanViewApply";
		}
		//queryCheckApSpecialByImport
		// 追溯盘亏申请
		@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/queryCheckApSpecialByImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assChkAinassetsService.queryAssApplyDeptByPlanDept(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	/**
	 * @Description 添加数据 051001资产盘亏记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/saveAssChkRecordInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkRinassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssChkRinassets assChkRinassets = assChkRinassetsService.queryByCode(mapVo);
		if(assChkRinassets != null){
			if(assChkRinassets.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assChkRinassetsJson = assChkRinassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChkRinassetsJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产盘亏记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/assChkRecordInassetsUpdatePage", method = RequestMethod.GET)
	public String assChkRinassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChkRinassets assChkRinassets = new AssChkRinassets();

		assChkRinassets = assChkRinassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkRinassets.getGroup_id());
		mode.addAttribute("hos_id", assChkRinassets.getHos_id());
		mode.addAttribute("copy_code", assChkRinassets.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkRinassets.getAss_chk_no());
		mode.addAttribute("note", assChkRinassets.getNote());
		mode.addAttribute("create_emp", assChkRinassets.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkRinassets.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkRinassets.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkRinassets.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkRinassets.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkRinassets.getAudit_emp_name());
		mode.addAttribute("state", assChkRinassets.getState());
		mode.addAttribute("state_name", assChkRinassets.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/asscheck/chkrecord/update";
	}


	/**
	 * @Description 删除数据 051001资产盘亏记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/deleteAssChkRecordInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkRinassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkRinassets assChkRinassets = assChkRinassetsService.queryByCode(mapVo);
			if(assChkRinassets != null){
				if(assChkRinassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
	      String de=    assChkRinassetsService.deleteBatchAssApplyPlanMap(listVo);

		String assChkRinassetsJson = assChkRinassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkRinassetsJson);

	}

	/**
	 * @Description 查询数据 051001资产盘亏记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/queryAssChkRecordInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkRinassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackInassets = assChkRinassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackInassets);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/queryAssChkRecordDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkRdetailInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBackDetailInassets = assChkRdetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailInassets);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/deleteAssChkRecordDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkRdetailInassets(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssChkRinassets assChkRinassets = assChkRinassetsService.queryByCode(mapVo);
				if(assChkRinassets != null){
					if(assChkRinassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assChkRdetailInassetsJson = assChkRdetailInassetsService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkRdetailInassetsJson);
			
	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/check/chkrecord/updateConfirmChkRecordInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkRinassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkRinassets assChkRinassets = assChkRinassetsService.queryByCode(mapVo);
			if(assChkRinassets == null || assChkRinassets.getState() == 2){
				continue;
			}
			
			List<AssChkRdetailInassets> detailList = assChkRdetailInassetsService.queryByAssChkNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssChkRdetailInassets detail : detailList) {
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

		String assChkRinassetsJson = assChkRinassetsService.updateConfirmChkRinassets(listVo,listCardVo);

		return JSONObject.parseObject(assChkRinassetsJson);

	}
	
}
