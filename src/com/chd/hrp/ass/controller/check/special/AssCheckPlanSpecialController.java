/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.check.special;

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
import com.chd.hrp.ass.entity.check.house.AssCheckHouse;
import com.chd.hrp.ass.entity.check.special.AssCheckDspecial;
import com.chd.hrp.ass.entity.check.special.AssCheckPlanSpecial;
import com.chd.hrp.ass.entity.check.special.AssCheckSspecial;
import com.chd.hrp.ass.entity.in.AssInMainSpecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.special.AssCheckDdetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssCheckDspecialService;
import com.chd.hrp.ass.service.check.special.AssCheckPlanSpecialService;
import com.chd.hrp.ass.service.check.special.AssCheckSdetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssCheckSspecialService;
import com.chd.hrp.ass.service.check.special.AssChkInDetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssChkInMainSpecialService;
import com.chd.hrp.ass.service.check.special.AssChkRdetailSpecialService;
import com.chd.hrp.ass.service.dict.AssTypeDictService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;

/**
 * @Description: 051101 盘点任务单(专用设备)
 * @Table: ASS_CHECK_PLAN_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssCheckPlanSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssCheckPlanSpecialController.class);

	// 引入Service服务
	@Resource(name = "assCheckPlanSpecialService")
	private final AssCheckPlanSpecialService assCheckPlanSpecialService = null;

	// 引入Service服务
	@Resource(name = "assCheckSspecialService")
	private final AssCheckSspecialService assCheckSspecialService = null;

	// 引入Service服务
	@Resource(name = "assCheckDspecialService")
	private final AssCheckDspecialService assCheckDspecialService = null;

	// 引入Service服务
	@Resource(name = "assCheckSdetailSpecialService")
	private final AssCheckSdetailSpecialService assCheckSdetailSpecialService = null;

	// 引入Service服务
	@Resource(name = "assCheckDdetailSpecialService")
	private final AssCheckDdetailSpecialService assCheckDdetailSpecialService = null;
	
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
	
	@Resource(name = "assChkRdetailSpecialService")
	private final AssChkRdetailSpecialService assChkRdetailSpecialService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/assCheckPlanSpecialMainPage", method = RequestMethod.GET)
	public String assCheckPlanSpecialMainPage(Model mode) throws Exception {

		return "hrp/ass/assspecial/asscheck/plan/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/assCheckPlanSpecialAddPage", method = RequestMethod.GET)
	public String assCheckPlanSpecialAddPage(Model mode) throws Exception {

		return "hrp/ass/assspecial/asscheck/plan/add";

	}

	// 科室盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/assCheckDpSpecialPage", method = RequestMethod.GET)
	public String assCheckDpSpecialPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		mode.addAttribute("check_no", mapVo.get("check_no"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		DeptDict deptDict = deptDictService.queryDeptDictByCode(mapVo);
		mode.addAttribute("dept_code", deptDict.getDept_code());
		mode.addAttribute("dept_name", deptDict.getDept_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05097",MyConfig.getSysPara("05097"));
		
		return "hrp/ass/assspecial/asscheck/plan/dpAdd";
	}

	// 仓库盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/assCheckSpSpecialPage", method = RequestMethod.GET)
	public String assCheckSpSpecialPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
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
		mode.addAttribute("ass_05097",MyConfig.getSysPara("05097"));
		
		return "hrp/ass/assspecial/asscheck/plan/spAdd";
	}

	/**
	 * @Description 添加数据 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/addAssCheckPlanSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckPlanSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String assCheckPlanSpecialJson = assCheckPlanSpecialService.add(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);

	}

	/**
	 * @Description 更新跳转页面 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/assCheckPlanSpecialUpdatePage", method = RequestMethod.GET)
	public String assCheckPlanSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		AssCheckPlanSpecial assCheckPlanSpecial = new AssCheckPlanSpecial();

		assCheckPlanSpecial = assCheckPlanSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assCheckPlanSpecial.getGroup_id());
		mode.addAttribute("hos_id", assCheckPlanSpecial.getHos_id());
		mode.addAttribute("copy_code", assCheckPlanSpecial.getCopy_code());
		mode.addAttribute("check_plan_no", assCheckPlanSpecial.getCheck_plan_no());
		mode.addAttribute("summary", assCheckPlanSpecial.getSummary());
		mode.addAttribute("beg_date", DateUtil.dateToString(assCheckPlanSpecial.getBeg_date(), "yyyy-MM-dd"));
		mode.addAttribute("end_date", DateUtil.dateToString(assCheckPlanSpecial.getEnd_date(), "yyyy-MM-dd"));
		mode.addAttribute("fin_date", DateUtil.dateToString(assCheckPlanSpecial.getFin_date(), "yyyy-MM-dd"));
		mode.addAttribute("mak_emp", assCheckPlanSpecial.getMak_emp());
		mode.addAttribute("mak_date", DateUtil.dateToString(assCheckPlanSpecial.getMak_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assCheckPlanSpecial.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(assCheckPlanSpecial.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assCheckPlanSpecial.getState());

		mode.addAttribute("ass_05020",MyConfig.getSysPara("05020"));
		
		return "hrp/ass/assspecial/asscheck/plan/update";
	}

	/**
	 * @Description 添加数据仓库盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/saveAssCheckSDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckSDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssCheckSspecial state = assCheckSspecialService.queryState(mapVo);
		 
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
		}

		String assCheckSpDetailSpecialJson = assCheckSdetailSpecialService.saveAssCheckSDetailSpecial(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 添加数据仓盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/saveInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveInventory(@RequestParam(value = "data1")  String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
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
			mapVo.put("check_plan_no", ids[16]);
			
			AssCheckSspecial state = assCheckSspecialService.queryState(mapVo);
			 
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			
			listVo.add(mapVo);
			
			
			
		}
		
		
		String assChkInDetailSpecial= assChkInDetailSpecialService.saveInventory(listVo);

		return JSONObject.parseObject(assChkInDetailSpecial);

	}
	
	/**
	 * @Description 添加数据科室盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/saveDeptInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDeptInventory(@RequestParam(value = "data2")  String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
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
			mapVo.put("check_plan_no", ids[16]);
			
			AssCheckDspecial state = assCheckDspecialService.queryState(mapVo);
			 
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			
			listVo.add(mapVo);
		}
		
		
		String assChkInDetailSpecial= assChkInDetailSpecialService.saveDeptInventory(listVo);

		return JSONObject.parseObject(assChkInDetailSpecial);

	}
	
	/**
	 * @Description 添加数据科室盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/saveAssCheckDDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckDDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssCheckDspecial state = assCheckDspecialService.queryState(mapVo);
		 
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
		}
		String assCheckSpDetailSpecialJson = assCheckDdetailSpecialService.saveAssCheckDDetailSpecial(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 更新数据 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/updateAssCheckPlanSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckPlanSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assCheckPlanSpecialJson = assCheckPlanSpecialService.update(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/addOrUpdateAssCheckPlanSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssCheckPlanSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanSpecialJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assCheckPlanSpecialJson = assCheckPlanSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 删除数据 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/deleteAssCheckPlanSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckPlanSpecial(@RequestParam (value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
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
			String str = assBaseService.isExistsDataByTable("ASS_CHECK_PLAN_SPECIAL", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的数据被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			AssCheckPlanSpecial assCheckPlanSpecial =assCheckPlanSpecialService.queryByCode(MmapVo);
			
			if(assCheckPlanSpecial.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(MmapVo);
		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已审核的数据不能删除! \"}");
		}

	
		
		String assCheckPlanSpecialJson = assCheckPlanSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);

	}

	/**
	 * @Description 查询数据 051101 盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/queryAssCheckPlanSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckPlanSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assCheckPlanSpecial = assCheckPlanSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/specialAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCheckPlanSpecialJson = assCheckPlanSpecialService.audit(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/specialUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialUnAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCheckPlanSpecialJson = assCheckPlanSpecialService.unAudit(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 盘点完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/specialFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialFinish(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCheckPlanSpecialJson = assCheckPlanSpecialService.finish(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 取消完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/specialUnFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialUnfinish(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCheckPlanSpecialJson = assCheckPlanSpecialService.unFinish(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/specialGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialGenerate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanSpecialJson = assCheckPlanSpecialService.generate(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/specialGenerateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> specialGenerateDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanSpecialJson = assCheckPlanSpecialService.generateDetail(mapVo);

		return JSONObject.parseObject(assCheckPlanSpecialJson);
	}

	/**
	 * @Description 查询数据 仓库盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/queryAssCheckSspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanSpecial = assCheckSspecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 查询数据 科室盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/queryAssCheckDspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanSpecial = assCheckDspecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 查询数据 仓库盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/queryAssCheckSdetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSdetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanSpecial = assCheckSdetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 查询数据 科室盘点任务单(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/queryAssCheckDdetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDdetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanSpecial = assCheckDdetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanSpecial);

	}

	/**
	 * @Description 盘点单树状结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/queryByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());

		List<?> l_map = assCheckPlanSpecialService.queryByTree(mapVo);

		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	/**
	 * @Description 科室盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/assCheckDspecialAddPage", method = RequestMethod.GET)
	public String assCheckDspecialAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assspecial/asscheck/plan/addDept";

	}

	/**
	 * @Description 仓库盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/assCheckSspecialAddPage", method = RequestMethod.GET)
	public String assCheckSspecialAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assspecial/asscheck/plan/addStore";

	}

	/**
	 * @Description 添加科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/addAssCheckDspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckDspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDspecialService.add(mapVo);
		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 添加仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/addAssCheckSspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckSspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSspecialService.add(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/delAssCheckDspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckDspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDspecialService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/delAssCheckSspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckSspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSspecialService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/auditSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckPlanSpecialService.auditCheckSpecial(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/unAuditSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckPlanSpecialService.unAuditCheckSpecial(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/auditBatchAssCheckSspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckSspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckSspecialService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/unAuditBatchAssCheckSspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckSspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSspecialService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/auditBatchAssCheckDspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckDspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckDspecialService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审 科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/unAuditBatchAssCheckDspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckDspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDspecialService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/copyAmountAssCheckDspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckDspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDdetailSpecialService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/copyAmountAssCheckSspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckSspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckSdetailSpecialService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/delAmountAssCheckSspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckSspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckSdetailSpecialService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/delAmountAssCheckDspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckDspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDdetailSpecialService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}
	/**
	 * 状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/queryAssInSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckSdetailSpecialService.queryAssInSpecialState(mapVo);
		
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
	@RequestMapping(value = "/hrp/ass/assspecial/asscheck/plan/queryAssInSpecialStates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialStates(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckDdetailSpecialService.queryAssInSpecialStates(mapVo);
		
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
