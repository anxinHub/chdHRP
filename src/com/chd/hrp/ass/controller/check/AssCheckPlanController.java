package com.chd.hrp.ass.controller.check;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.check.AssCheckPlan;
import com.chd.hrp.ass.entity.check.general.AssCheckDGeneral;
import com.chd.hrp.ass.entity.check.general.AssCheckSGeneral;
import com.chd.hrp.ass.entity.check.other.AssCheckDOther;
import com.chd.hrp.ass.entity.check.other.AssCheckSOther;
import com.chd.hrp.ass.entity.check.special.AssCheckDspecial;
import com.chd.hrp.ass.entity.check.special.AssCheckSspecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.AssCheckPlanService;
import com.chd.hrp.ass.service.check.general.AssCheckDGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckDdetailGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckDpDetailGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckSGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckSdetailGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckSpDetailGeneralService;
import com.chd.hrp.ass.service.check.general.AssChkInDetailGeneralService;
import com.chd.hrp.ass.service.check.other.AssCheckDOtherService;
import com.chd.hrp.ass.service.check.other.AssCheckDdetailOtherService;
import com.chd.hrp.ass.service.check.other.AssCheckDpDetailOtherService;
import com.chd.hrp.ass.service.check.other.AssCheckSOtherService;
import com.chd.hrp.ass.service.check.other.AssCheckSdetailOtherService;
import com.chd.hrp.ass.service.check.other.AssCheckSpDetailOtherService;
import com.chd.hrp.ass.service.check.other.AssChkInDetailOtherService;
import com.chd.hrp.ass.service.check.special.AssCheckDdetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssCheckDpDetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssCheckDspecialService;
import com.chd.hrp.ass.service.check.special.AssCheckPlanSpecialService;
import com.chd.hrp.ass.service.check.special.AssCheckSdetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssCheckSpDetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssCheckSspecialService;
import com.chd.hrp.ass.service.check.special.AssChkInDetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssChkInMainSpecialService;
import com.chd.hrp.ass.service.check.special.AssChkRdetailSpecialService;
import com.chd.hrp.ass.service.dict.AssTypeDictService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;
@Controller
public class AssCheckPlanController extends BaseController{
	private static Logger logger = Logger.getLogger(AssCheckPlanController.class);
	
	@Resource(name = "assCheckPlanService")
	private final AssCheckPlanService assCheckPlanService = null;
	
	
	// 引入Service服务
	@Resource(name = "assCheckPlanSpecialService")
	private final AssCheckPlanSpecialService assCheckPlanSpecialService = null;
	

	// 引入Service服务
	@Resource(name = "assCheckSspecialService")
	private final AssCheckSspecialService assCheckSspecialService = null;
	
	@Resource(name = "assCheckSGeneralService")
	private final AssCheckSGeneralService assCheckSGeneralService = null;
	
	@Resource(name = "assCheckSOtherService")
	private final AssCheckSOtherService assCheckSOtherService = null;
	
	
	
	@Resource(name = "assCheckSdetailSpecialService")
	private final AssCheckSdetailSpecialService assCheckSdetailSpecialService = null;
	
	@Resource(name = "assCheckSdetailGeneralService")
	private final AssCheckSdetailGeneralService assCheckSdetailGeneralService = null;
	
	@Resource(name = "assCheckSdetailOtherService")
	private final AssCheckSdetailOtherService assCheckSdetailOtherService = null;
	
	

	// 引入Service服务
	@Resource(name = "assCheckDspecialService")
	private final AssCheckDspecialService assCheckDspecialService = null;

	@Resource(name = "assCheckDGeneralService")
	private final AssCheckDGeneralService assCheckDGeneralService = null;
	
	@Resource(name = "assCheckDOtherService")
	private final AssCheckDOtherService assCheckDOtherService = null;
	
	
	
	@Resource(name = "assCheckDdetailSpecialService")
	private final AssCheckDdetailSpecialService assCheckDdetailSpecialService = null;
	
	@Resource(name = "assCheckDdetailGeneralService")
	private final AssCheckDdetailGeneralService assCheckDdetailGeneralService = null;
	
	@Resource(name = "assCheckDdetailOtherService")
	private final AssCheckDdetailOtherService assCheckDdetailOtherService = null;
	

	// 引入Service服务
	
	
	@Resource(name = "assTypeDictService")
	private final AssTypeDictService assTypeDictService = null;
	
	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;
	
	@Resource(name = "storeDictService")
	private final StoreDictServiceImpl storeDictService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
		
	//引入Service服务
	@Resource(name = "assChkInMainSpecialService")
	private final AssChkInMainSpecialService assChkInMainSpecialService = null;
	
	
	//引入Service服务
	@Resource(name = "assChkInDetailSpecialService")
	private final AssChkInDetailSpecialService assChkInDetailSpecialService = null;	
	
	@Resource(name = "assChkInDetailGeneralService")
	private final AssChkInDetailGeneralService assChkInDetailGeneralService = null;	
	
	@Resource(name = "assChkInDetailOtherService")
	private final AssChkInDetailOtherService assChkInDetailOtherService = null;	
	
	
	
	@Resource(name = "assChkRdetailSpecialService")
	private final AssChkRdetailSpecialService assChkRdetailSpecialService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/assCheckPlanMainPage", method = RequestMethod.GET)
	public String assCheckPlanMainPage(Model mode) throws Exception {

		return "hrp/ass/asscheck/plan/main";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/assCheckPlanAddPage", method = RequestMethod.GET)
	public String assCheckPlanAddPage(Model mode) throws Exception {

		return "hrp/ass/asscheck/plan/add";

	}

	// 科室盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/asscheck/plan/assCheckDpPage", method = RequestMethod.GET)
	public String assCheckDpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		mode.addAttribute("check_plan_no_s", mapVo.get("check_plan_no_s"));
		mode.addAttribute("check_plan_no_g", mapVo.get("check_plan_no_g"));
		mode.addAttribute("check_plan_no_o", mapVo.get("check_plan_no_o"));
		mode.addAttribute("check_no", mapVo.get("check_no"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		DeptDict deptDict = deptDictService.queryDeptDictByCode(mapVo);
		mode.addAttribute("dept_code", deptDict.getDept_code());
		mode.addAttribute("dept_name", deptDict.getDept_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asscheck/plan/dpAdd";
	}

	// 仓库盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/assCheckSpPage", method = RequestMethod.GET)
	public String assCheckSpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		mode.addAttribute("check_plan_no_s", mapVo.get("check_plan_no_s"));
		mode.addAttribute("check_plan_no_g", mapVo.get("check_plan_no_g"));
		mode.addAttribute("check_plan_no_o", mapVo.get("check_plan_no_o"));
		mode.addAttribute("check_no", mapVo.get("check_no"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mapVo.put("is_stop", "0");
		mode.addAttribute("ass_card_no", mapVo.get("ass_card_nos"));
		
		
		StoreDict storeDict = storeDictService.queryStoreDictByCode(mapVo);
		mode.addAttribute("store_code", storeDict.getStore_code());
		mode.addAttribute("store_name", storeDict.getStore_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asscheck/plan/spAdd";
	}

	/**
	 * @Description 添加数据 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/addAssCheckPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckPlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		if (mapVo.get("beg_date") != null) {
			mapVo.put("beg_date", DateUtil.stringToDate(mapVo.get("beg_date").toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("end_date") != null) {
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}

		mapVo.put("mak_emp", SessionManager.getUserId());
		mapVo.put("mak_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");
		
		mapVo.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
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
		String assCheckPlanSpecialJson = assCheckPlanService.add(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);

	}

	/**
	 * @Description 更新跳转页面 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/assCheckPlanUpdatePage", method = RequestMethod.GET)
	public String assCheckPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		AssCheckPlan assCheckPlan = new AssCheckPlan();

		assCheckPlan = assCheckPlanService.queryByCode(mapVo);

		mode.addAttribute("group_id", assCheckPlan.getGroup_id());
		mode.addAttribute("hos_id", assCheckPlan.getHos_id());
		mode.addAttribute("copy_code", assCheckPlan.getCopy_code());
		mode.addAttribute("check_plan_no", assCheckPlan.getCheck_plan_no());
		mode.addAttribute("check_plan_no_s", assCheckPlan.getCheck_plan_no_s());
		mode.addAttribute("check_plan_no_g", assCheckPlan.getCheck_plan_no_g());
		mode.addAttribute("check_plan_no_o", assCheckPlan.getCheck_plan_no_o());
		mode.addAttribute("summary", assCheckPlan.getSummary());
		mode.addAttribute("beg_date", DateUtil.dateToString(assCheckPlan.getBeg_date(), "yyyy-MM-dd"));
		mode.addAttribute("end_date", DateUtil.dateToString(assCheckPlan.getEnd_date(), "yyyy-MM-dd"));
		mode.addAttribute("fin_date", DateUtil.dateToString(assCheckPlan.getFin_date(), "yyyy-MM-dd"));
		mode.addAttribute("mak_emp", assCheckPlan.getMak_emp());
		mode.addAttribute("mak_date", DateUtil.dateToString(assCheckPlan.getMak_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assCheckPlan.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(assCheckPlan.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assCheckPlan.getState());

		return "hrp/ass/asscheck/plan/update";
	}
	
	/**
	 * @Description 更新跳转页面 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/assCheckPlanEchartsMainPage", method = RequestMethod.GET)
	public String assCheckPlanEchartsMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());

		AssCheckPlan assCheckPlan = new AssCheckPlan();

		assCheckPlan = assCheckPlanService.queryByCode(mapVo);

		mode.addAttribute("group_id", assCheckPlan.getGroup_id());
		mode.addAttribute("hos_id", assCheckPlan.getHos_id());
		mode.addAttribute("copy_code", assCheckPlan.getCopy_code());
		mode.addAttribute("check_plan_no", assCheckPlan.getCheck_plan_no());
		mode.addAttribute("check_plan_no_s", assCheckPlan.getCheck_plan_no_s());
		mode.addAttribute("check_plan_no_g", assCheckPlan.getCheck_plan_no_g());
		mode.addAttribute("check_plan_no_o", assCheckPlan.getCheck_plan_no_o());
		mode.addAttribute("summary", assCheckPlan.getSummary());
		mode.addAttribute("state", assCheckPlan.getState());

		return "hrp/ass/asscheck/plan/echartsMain";
	}
	
	//仓库统计图
	@RequestMapping(value ="/hrp/ass/asscheck/plan/queryCheckPlanStore", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryCheckPlanStore(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		String reJson="{\"Rows\":[]}";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			
			reJson = assCheckPlanService.queryCheckPlanStore(getPage(mapVo));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JSONObject.parseObject(reJson);
    }
	
	//科室统计图
	@RequestMapping(value ="/hrp/ass/asscheck/plan/queryCheckPlanDept", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryCheckPlanDept(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		String reJson="{\"Rows\":[]}";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			
			reJson = assCheckPlanService.queryCheckPlanDept(getPage(mapVo));
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JSONObject.parseObject(reJson);
    }

	/**
	 * @Description 添加数据仓库盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/saveAssCheckSDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckSDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCheckSpDetailSpecialJson="";
		if("02".equals(mapVo.get("ass_natrue"))){
			mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
			AssCheckSspecial state_s = assCheckSspecialService.queryState(mapVo);
			if (state_s.getState() != 0) {
				return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
			}
			assCheckSpDetailSpecialJson = assCheckSdetailSpecialService.saveAssCheckSDetailSpecial(mapVo);
		}else if("03".equals(mapVo.get("ass_natrue"))){
			mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
			AssCheckSGeneral state_g = assCheckSGeneralService.queryState(mapVo);
			if (state_g.getState() != 0) {
				return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
			}
			assCheckSpDetailSpecialJson = assCheckSdetailGeneralService.saveAssCheckSDetailGeneral(mapVo);
		}else if("04".equals(mapVo.get("ass_natrue"))){
			mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
			AssCheckSOther state_o = assCheckSOtherService.queryState(mapVo);
			if (state_o.getState() != 0) {
				return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
			}
			assCheckSpDetailSpecialJson = assCheckSdetailOtherService.saveAssCheckSDetailOther(mapVo);
			
		}
		
		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 添加数据仓盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/saveInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveInventory(@RequestParam(value = "data1")  String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo_s = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_g = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_o = new ArrayList<Map<String, Object>>();
		for(String id : paramVo.split(",")){
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("ass_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("group_id", ids[3]);
			mapVo.put("store_id", ids[4]);
			mapVo.put("ass_card_no", ids[5]);
			mapVo.put("ass_type_name", ids[6]);
			mapVo.put("ass_name", ids[7]);
			mapVo.put("store_name", ids[8]);
			mapVo.put("acc_amount", ids[9]);
			mapVo.put("check_amount", ids[10]);
			mapVo.put("pl_amount", ids[11]);
			mapVo.put("pl_reason", ids[12]);
			mapVo.put("store_no", ids[13]);
			mapVo.put("ass_no", ids[14]);
			mapVo.put("check_no", ids[15]);
			mapVo.put("check_plan_no", ids[17]);
			
			AssCheckSspecial state_s = assCheckSspecialService.queryState(mapVo);
			 
			if (state_s.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			
			listVo_s.add(mapVo);
			
		}
		
		for(String id : paramVo.split(",")){
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("ass_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("group_id", ids[3]);
			mapVo.put("store_id", ids[4]);
			mapVo.put("ass_card_no", ids[5]);
			mapVo.put("ass_type_name", ids[6]);
			mapVo.put("ass_name", ids[7]);
			mapVo.put("store_name", ids[8]);
			mapVo.put("acc_amount", ids[9]);
			mapVo.put("check_amount", ids[10]);
			mapVo.put("pl_amount", ids[11]);
			mapVo.put("pl_reason", ids[12]);
			mapVo.put("store_no", ids[13]);
			mapVo.put("ass_no", ids[14]);
			mapVo.put("check_no", ids[15]);
			mapVo.put("check_plan_no", ids[18]);
			
			AssCheckSGeneral state_g = assCheckSGeneralService.queryState(mapVo);
			 
			if (state_g.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			
			AssCheckSOther state_o = assCheckSOtherService.queryState(mapVo);
			 
			if (state_o.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			
			listVo_g.add(mapVo);
			
		}
		
		for(String id : paramVo.split(",")){
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("ass_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("group_id", ids[3]);
			mapVo.put("store_id", ids[4]);
			mapVo.put("ass_card_no", ids[5]);
			mapVo.put("ass_type_name", ids[6]);
			mapVo.put("ass_name", ids[7]);
			mapVo.put("store_name", ids[8]);
			mapVo.put("acc_amount", ids[9]);
			mapVo.put("check_amount", ids[10]);
			mapVo.put("pl_amount", ids[11]);
			mapVo.put("pl_reason", ids[12]);
			mapVo.put("store_no", ids[13]);
			mapVo.put("ass_no", ids[14]);
			mapVo.put("check_no", ids[15]);
			mapVo.put("check_plan_no", ids[19]);
			
			AssCheckSOther state_o = assCheckSOtherService.queryState(mapVo);
			 
			if (state_o.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			
			listVo_o.add(mapVo);
			
		}
		
		assChkInDetailSpecialService.saveInventory(listVo_s);
		assChkInDetailGeneralService.saveGeneralInventory(listVo_g);
		String assChkInDetailSpecial= assChkInDetailOtherService.saveOtherInventory(listVo_o);

		return JSONObject.parseObject(assChkInDetailSpecial);

	}
	
	/**
	 * @Description 添加数据科室盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/saveDeptInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDeptInventory(@RequestParam(value = "data2")  String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo_s = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_g = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_o = new ArrayList<Map<String, Object>>();
		for(String id : paramVo.split(",")){
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("ass_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("group_id", ids[3]);
			mapVo.put("dept_id", ids[4]);
			mapVo.put("ass_card_no", ids[5]);
			mapVo.put("ass_type_name", ids[6]);
			mapVo.put("ass_name", ids[7]);
			mapVo.put("dept_name", ids[8]);
			mapVo.put("acc_amount", ids[9]);
			mapVo.put("check_amount", ids[10]);
			mapVo.put("pl_amount", ids[11]);
			mapVo.put("pl_reason", ids[12]);
			mapVo.put("dept_no", ids[13]);
			mapVo.put("ass_no", ids[14]);
			mapVo.put("check_no", ids[15]);
			mapVo.put("check_plan_no", ids[17]);
			
			AssCheckDspecial state_s = assCheckDspecialService.queryState(mapVo);
			 
			if (state_s.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			listVo_s.add(mapVo);
		}
		
		for(String id : paramVo.split(",")){
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("ass_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("group_id", ids[3]);
			mapVo.put("dept_id", ids[4]);
			mapVo.put("ass_card_no", ids[5]);
			mapVo.put("ass_type_name", ids[6]);
			mapVo.put("ass_name", ids[7]);
			mapVo.put("dept_name", ids[8]);
			mapVo.put("acc_amount", ids[9]);
			mapVo.put("check_amount", ids[10]);
			mapVo.put("pl_amount", ids[11]);
			mapVo.put("pl_reason", ids[12]);
			mapVo.put("dept_no", ids[13]);
			mapVo.put("ass_no", ids[14]);
			mapVo.put("check_no", ids[15]);
			mapVo.put("check_plan_no", ids[18]);
			
			AssCheckDGeneral state_g = assCheckDGeneralService.queryState(mapVo);
			 
			if (state_g.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			
			listVo_g.add(mapVo);
		}
		
		
		for(String id : paramVo.split(",")){
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			
			mapVo.put("ass_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("group_id", ids[3]);
			mapVo.put("dept_id", ids[4]);
			mapVo.put("ass_card_no", ids[5]);
			mapVo.put("ass_type_name", ids[6]);
			mapVo.put("ass_name", ids[7]);
			mapVo.put("dept_name", ids[8]);
			mapVo.put("acc_amount", ids[9]);
			mapVo.put("check_amount", ids[10]);
			mapVo.put("pl_amount", ids[11]);
			mapVo.put("pl_reason", ids[12]);
			mapVo.put("dept_no", ids[13]);
			mapVo.put("ass_no", ids[14]);
			mapVo.put("check_no", ids[15]);
			mapVo.put("check_plan_no", ids[18]);
			
			AssCheckDOther state_o = assCheckDOtherService.queryState(mapVo);
			 
			if (state_o.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			
			listVo_o.add(mapVo);
		}
		
		assChkInDetailSpecialService.saveDeptInventory(listVo_s);
		assChkInDetailGeneralService.saveDeptGeneralInventory(listVo_g);
		String assChkInDetailSpecial= assChkInDetailOtherService.saveDeptOtherInventory(listVo_o);

		return JSONObject.parseObject(assChkInDetailSpecial);

	}
	
	/**
	 * @Description 添加数据科室盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/saveAssCheckDDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckDDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCheckSpDetailSpecialJson="";
		if("02".equals(mapVo.get("ass_natrue"))){
			mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
			AssCheckDspecial state_s = assCheckDspecialService.queryState(mapVo);
			if (state_s.getState() != 0) {
				return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
			}
			assCheckSpDetailSpecialJson = assCheckDdetailSpecialService.saveAssCheckDDetailSpecial(mapVo);
		}else if ("03".equals(mapVo.get("ass_natrue"))){
			mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
			AssCheckDGeneral state_g = assCheckDGeneralService.queryState(mapVo);
			if (state_g.getState() != 0) {
				return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
			}
			assCheckSpDetailSpecialJson = assCheckDdetailGeneralService.saveAssCheckDDetailGeneral(mapVo);
			
		}else if ("04".equals(mapVo.get("ass_natrue"))){
			mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
			AssCheckDOther state_o = assCheckDOtherService.queryState(mapVo);
			if (state_o.getState() != 0) {
				return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
			}
			assCheckSpDetailSpecialJson = assCheckDdetailOtherService.saveAssCheckDDetailOther(mapVo);
		}
		
		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 更新数据 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/updateAssCheckPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckPlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("beg_date") != null) {
			mapVo.put("beg_date", DateUtil.stringToDate(mapVo.get("beg_date").toString()));
		}
		if (mapVo.get("end_date") != null) {
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString()));
		}

		String assCheckPlanJson = assCheckPlanService.update(mapVo);

		return JSONObject.parseObject(assCheckPlanJson);
	}



	/**
	 * @Description 删除数据 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/deleteAssCheckPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckPlan(@RequestParam (value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_s = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_g = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_o = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		String retErrot = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> MmapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			MmapVo.put("group_id", ids[0]);
			MmapVo.put("hos_id", ids[1]);
			MmapVo.put("copy_code", ids[2]);
			MmapVo.put("check_plan_no", ids[3]);
			AssCheckPlan assCheckPlan =assCheckPlanService.queryByCode(MmapVo);
			
			if(assCheckPlan.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(MmapVo);
		}
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> MmapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			MmapVo.put("group_id", ids[0]);
			MmapVo.put("hos_id", ids[1]);
			MmapVo.put("copy_code", ids[2]);
			MmapVo.put("check_plan_no", ids[3]);
			AssCheckPlan assCheckPlan =assCheckPlanService.queryByCode(MmapVo);
			
			if(assCheckPlan.getState() > 0){
				flag = false;
				break;
			}
			MmapVo.put("check_plan_no", ids[4]);
			String str = assBaseService.isExistsDataByTable("ASS_CHECK_PLAN_SPECIAL", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，专用设备-选择的数据被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			
			listVo_s.add(MmapVo);
		}
		
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> MmapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			MmapVo.put("group_id", ids[0]);
			MmapVo.put("hos_id", ids[1]);
			MmapVo.put("copy_code", ids[2]);
			MmapVo.put("check_plan_no", ids[3]);
			AssCheckPlan assCheckPlan =assCheckPlanService.queryByCode(MmapVo);
			
			if(assCheckPlan.getState() > 0){
				flag = false;
				break;
			}
			MmapVo.put("check_plan_no", ids[5]);
			String str = assBaseService.isExistsDataByTable("ASS_CHECK_PLAN_GENERAL", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，一般设备-选择的数据被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			listVo_g.add(MmapVo);
		}
		
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> MmapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			MmapVo.put("group_id", ids[0]);
			MmapVo.put("hos_id", ids[1]);
			MmapVo.put("copy_code", ids[2]);
			MmapVo.put("check_plan_no", ids[3]);
			AssCheckPlan assCheckPlan =assCheckPlanService.queryByCode(MmapVo);
			
			if(assCheckPlan.getState() > 0){
				flag = false;
				break;
			}
			MmapVo.put("check_plan_no", ids[6]);
			String str = assBaseService.isExistsDataByTable("ASS_CHECK_PLAN_OTHER", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，一般设备-选择的数据被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			listVo_o.add(MmapVo);
		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已审核的数据不能删除! \"}");
		}

	
		
		String assCheckPlanSpecialJson = assCheckPlanService.deleteBatch(listVo,listVo_s,listVo_g,listVo_o);

		return JSONObject.parseObject(assCheckPlanSpecialJson);

	}

	/**
	 * @Description 查询数据 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryAssCheckPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckPlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assCheckPlanSpecial = assCheckPlanService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/checkAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCheckPlanSpecialJson = assCheckPlanService.audit(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/checkUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkUnAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCheckPlanSpecialJson = assCheckPlanService.unAudit(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 盘点完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/checkFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkFinish(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCheckPlanSpecialJson = assCheckPlanService.finish(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 取消完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/checkUnFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkUnfinish(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCheckPlanSpecialJson = assCheckPlanService.unFinish(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/checkGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkGenerate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanSpecialJson = assCheckPlanService.generate(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/checkGenerateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkGenerateDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanSpecialJson = assCheckPlanService.generateDetail(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 查询数据 仓库盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryAssCheckS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckS(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanSpecial = assCheckPlanService.queryAssCheckS(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 查询数据 科室盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryAssCheckD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckD(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanSpecial = assCheckPlanService.queryAssCheckD(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 查询数据 仓库盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryAssCheckSdetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSdetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanSpecial = assCheckPlanService.queryAssCheckSdetail(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 查询数据 科室盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryAssCheckDdetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDdetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanSpecial = assCheckPlanService.queryAssCheckDdetail(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 盘点单树状结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());

		List<?> l_map = assCheckPlanService.queryByTree(mapVo);

		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	/**
	 * @Description 科室盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/assCheckDAddPage", method = RequestMethod.GET)
	public String assCheckDAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		mode.addAttribute("check_plan_no_s", mapVo.get("check_plan_no_s"));
		mode.addAttribute("check_plan_no_g", mapVo.get("check_plan_no_g"));
		mode.addAttribute("check_plan_no_o", mapVo.get("check_plan_no_o"));
		return "hrp/ass/asscheck/plan/addDept";

	}

	/**
	 * @Description 仓库盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/assCheckSAddPage", method = RequestMethod.GET)
	public String assCheckSAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		mode.addAttribute("check_plan_no_s", mapVo.get("check_plan_no_s"));
		mode.addAttribute("check_plan_no_g", mapVo.get("check_plan_no_g"));
		mode.addAttribute("check_plan_no_o", mapVo.get("check_plan_no_o"));
		return "hrp/ass/asscheck/plan/addStore";

	}

	/**
	 * @Description 添加科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/addAssCheckD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckD(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		String r1 = assCheckDspecialService.add(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		String r2 = assCheckDGeneralService.add(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String r3 = assCheckDOtherService.add(mapVo);
		
		return JSONObject.parseObject(r3);
	}

	/**
	 * @Description 添加仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/addAssCheckS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckS(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		String r1 = assCheckSspecialService.add(mapVo);
		
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		String r2 = assCheckSGeneralService.add(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String r3 = assCheckSOtherService.add(mapVo);

		return JSONObject.parseObject(r3);
	}

	/**
	 * @Description 删除科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/delAssCheckD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckD(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckDspecialService.delete(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckDGeneralService.delete(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckDOtherService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/delAssCheckS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckS(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckSspecialService.delete(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckSGeneralService.delete(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckSOtherService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/auditCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckPlanService.auditCheck(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/unAuditCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");
		
		String json = assCheckPlanService.unAuditCheck(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/auditBatchAssCheckS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckS(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckSspecialService.auditBatch(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckSGeneralService.auditBatch(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckSOtherService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/unAuditBatchAssCheckS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckS(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckSspecialService.unAuditBatch(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckSGeneralService.unAuditBatch(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckSOtherService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/auditBatchAssCheckD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckD(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckDspecialService.auditBatch(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckDGeneralService.auditBatch(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckDOtherService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审 科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/unAuditBatchAssCheckD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckD(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckDspecialService.unAuditBatch(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckDGeneralService.unAuditBatch(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckDOtherService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/copyAmountAssCheckD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckD(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckDdetailSpecialService.copyAmount(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckDdetailGeneralService.copyAmount(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckDdetailOtherService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/copyAmountAssCheckS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckS(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_card_no_all_g", mapVo.get("ass_card_no_all"));
		mapVo.put("ass_card_no_all_o", mapVo.get("ass_card_no_all"));

		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckSdetailSpecialService.copyAmount(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckSdetailGeneralService.copyAmount(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckSdetailOtherService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/delAmountAssCheckS", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckS(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckSdetailSpecialService.delCard(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckSdetailGeneralService.delCard(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckSdetailOtherService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/delAmountAssCheckD", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckD(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		assCheckDdetailSpecialService.delCard(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckDdetailGeneralService.delCard(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		String json = assCheckDdetailOtherService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}
	/**
	 * 状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryAssInState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list_s = assCheckDdetailSpecialService.queryAssInSpecialStates(mapVo);
				
		List<String> list_g = assCheckDdetailGeneralService.queryAssInSpecialStates(mapVo);
				
		List<String> list_o = assCheckDdetailOtherService.queryAssInSpecialStates(mapVo);
				
		List<String> list = new ArrayList<String>();
				
		list.addAll(list_s);
				
		list.addAll(list_g);
				
		list.addAll(list_o);
		
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
	/**
	 * 状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryAssInStates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialStates(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list_s = assCheckDdetailSpecialService.queryAssInSpecialStates(mapVo);
		
		List<String> list_g = assCheckDdetailGeneralService.queryAssInSpecialStates(mapVo);
		
		List<String> list_o = assCheckDdetailOtherService.queryAssInSpecialStates(mapVo);
		
		List<String> list = new ArrayList<String>();
		
		list.addAll(list_s);
		
		list.addAll(list_g);
		
		list.addAll(list_o);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list_s){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"盘点单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
	@Resource(name = "assCheckSpDetailGeneralService")
	private final AssCheckSpDetailGeneralService assCheckSpDetailGeneralService = null;
	
	@Resource(name = "assCheckSpDetailSpecialService")
	private final AssCheckSpDetailSpecialService assCheckSpDetailSpecialService = null;
	
	@Resource(name = "assCheckSpDetailOtherService")
	private final AssCheckSpDetailOtherService assCheckSpDetailOtherService = null;
	
	@RequestMapping(value = "/hrp/ass/asscheck/plan/saveAssCheckSpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckSpDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		String assCheckSpDetailGeneralJson = assCheckSpDetailSpecialService.addOrUpdate(mapVo);

		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckSpDetailGeneralJson = assCheckSpDetailGeneralService.addOrUpdate(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		assCheckSpDetailGeneralJson = assCheckSpDetailOtherService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assCheckSpDetailGeneralJson);

	}
	
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryAssCheckSpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSpDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		String assCheckSpDetailGeneral = assCheckSpDetailSpecialService.queryAll(getPage(mapVo));

		return JSONObject.parseObject(assCheckSpDetailGeneral);

	}

	
	@RequestMapping(value = "/hrp/ass/asscheck/plan/deleteAssCheckSpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckSpDetail(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo_s = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_g = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_o = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_plan_no", ids[4]);
			mapVo.put("check_no", ids[7]);
			mapVo.put("store_id", ids[8]);
			mapVo.put("store_no", ids[9]);
			mapVo.put("ass_id", ids[10]);
			mapVo.put("ass_no", ids[11]);

			listVo_s.add(mapVo);

		}
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_plan_no", ids[5]);
			mapVo.put("check_no", ids[7]);
			mapVo.put("store_id", ids[8]);
			mapVo.put("store_no", ids[9]);
			mapVo.put("ass_id", ids[10]);
			mapVo.put("ass_no", ids[11]);

			listVo_g.add(mapVo);

		}
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_plan_no", ids[6]);
			mapVo.put("check_no", ids[7]);
			mapVo.put("store_id", ids[8]);
			mapVo.put("store_no", ids[9]);
			mapVo.put("ass_id", ids[10]);
			mapVo.put("ass_no", ids[11]);

			listVo_o.add(mapVo);

		}

		assCheckSpDetailSpecialService.deleteBatch(listVo_s);
		assCheckSpDetailGeneralService.deleteBatch(listVo_g);
		String assCheckSpDetailGeneralJson = assCheckSpDetailOtherService.deleteBatch(listVo_o);

		return JSONObject.parseObject(assCheckSpDetailGeneralJson);

	}
	
	@Resource(name = "assCheckDpDetailGeneralService")
	private final AssCheckDpDetailGeneralService assCheckDpDetailGeneralService = null;
	
	@Resource(name = "assCheckDpDetailSpecialService")
	private final AssCheckDpDetailSpecialService assCheckDpDetailSpecialService = null;
	
	@Resource(name = "assCheckDpDetailOtherService")
	private final AssCheckDpDetailOtherService assCheckDpDetailOtherService = null;
	
	@RequestMapping(value = "/hrp/ass/asscheck/plan/saveAssCheckDpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckDpDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_s"));
		String assCheckSpDetailGeneralJson = assCheckDpDetailSpecialService.addOrUpdate(mapVo);

		mapVo.put("check_plan_no", mapVo.get("check_plan_no_g"));
		assCheckSpDetailGeneralJson = assCheckDpDetailGeneralService.addOrUpdate(mapVo);
		
		mapVo.put("check_plan_no", mapVo.get("check_plan_no_o"));
		assCheckSpDetailGeneralJson = assCheckDpDetailOtherService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assCheckSpDetailGeneralJson);

	}
	
	@RequestMapping(value = "/hrp/ass/asscheck/plan/queryAssCheckDpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDpDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		String assCheckSpDetailGeneral = assCheckDpDetailSpecialService.queryAll(getPage(mapVo));

		return JSONObject.parseObject(assCheckSpDetailGeneral);

	}
	
	@RequestMapping(value = "/hrp/ass/asscheck/plan/deleteAssCheckDpDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckDpDetail(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo_s = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_g = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo_o = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_plan_no", ids[4]);
			mapVo.put("check_no", ids[7]);
			mapVo.put("store_id", ids[8]);
			mapVo.put("store_no", ids[9]);
			mapVo.put("ass_id", ids[10]);
			mapVo.put("ass_no", ids[11]);

			listVo_s.add(mapVo);

		}
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_plan_no", ids[5]);
			mapVo.put("check_no", ids[7]);
			mapVo.put("store_id", ids[8]);
			mapVo.put("store_no", ids[9]);
			mapVo.put("ass_id", ids[10]);
			mapVo.put("ass_no", ids[11]);

			listVo_g.add(mapVo);

		}
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_plan_no", ids[6]);
			mapVo.put("check_no", ids[7]);
			mapVo.put("store_id", ids[8]);
			mapVo.put("store_no", ids[9]);
			mapVo.put("ass_id", ids[10]);
			mapVo.put("ass_no", ids[11]);

			listVo_o.add(mapVo);

		}

		assCheckDpDetailSpecialService.deleteBatch(listVo_s);
		assCheckDpDetailGeneralService.deleteBatch(listVo_g);
		String assCheckSpDetailGeneralJson = assCheckDpDetailOtherService.deleteBatch(listVo_o);

		return JSONObject.parseObject(assCheckSpDetailGeneralJson);

	}
	
}
