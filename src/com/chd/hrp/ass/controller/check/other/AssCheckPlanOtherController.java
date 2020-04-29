/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.check.other;

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
import com.chd.hrp.ass.entity.check.other.AssCheckDOther;
import com.chd.hrp.ass.entity.check.other.AssCheckPlanOther;
import com.chd.hrp.ass.entity.check.other.AssCheckSOther;
import com.chd.hrp.ass.entity.check.special.AssCheckPlanSpecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.other.AssCheckDOtherService;
import com.chd.hrp.ass.service.check.other.AssCheckDdetailOtherService;
import com.chd.hrp.ass.service.check.other.AssCheckPlanOtherService;
import com.chd.hrp.ass.service.check.other.AssCheckSOtherService;
import com.chd.hrp.ass.service.check.other.AssCheckSdetailOtherService;
import com.chd.hrp.ass.service.check.other.AssChkInDetailOtherService;
import com.chd.hrp.ass.service.check.special.AssChkInDetailSpecialService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;

/**
 * @Description: 051101 盘点任务单(一般设备)
 * @Table: ASS_CHECK_PLAN_Other
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssCheckPlanOtherController extends BaseController {

	private static Logger logger = Logger
			.getLogger(AssCheckPlanOtherController.class);

	// 引入Service服务
	@Resource(name = "assCheckPlanOtherService")
	private final AssCheckPlanOtherService assCheckPlanOtherService = null;

	// 引入Service服务
	@Resource(name = "assCheckSOtherService")
	private final AssCheckSOtherService assCheckSOtherService = null;

	// 引入Service服务
	@Resource(name = "assCheckDOtherService")
	private final AssCheckDOtherService assCheckDOtherService = null;

	// 引入Service服务
	@Resource(name = "assCheckSdetailOtherService")
	private final AssCheckSdetailOtherService assCheckSdetailOtherService = null;

	// 引入Service服务
	@Resource(name = "assCheckDdetailOtherService")
	private final AssCheckDdetailOtherService assCheckDdetailOtherService = null;

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

	@Resource(name = "assChkInDetailOtherService")
	private final AssChkInDetailOtherService assChkInDetailOtherService = null;	
	
	@Resource(name = "storeDictService")
	private final StoreDictServiceImpl storeDictService = null;
	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/assCheckPlanOtherMainPage", method = RequestMethod.GET)
	public String assCheckPlanOtherMainPage(Model mode) throws Exception {

		return "hrp/ass/assother/asscheck/plan/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/assCheckPlanOtherAddPage", method = RequestMethod.GET)
	public String assCheckPlanOtherAddPage(Model mode) throws Exception {

		return "hrp/ass/assother/asscheck/plan/add";

	}

	// 科室盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/assCheckDpOtherPage", method = RequestMethod.GET)
	public String assCheckDpOtherPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

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
		mode.addAttribute("ass_05093",MyConfig.getSysPara("05093"));
		
		return "hrp/ass/assother/asscheck/plan/dpAdd";
	}

	// 仓库盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/assCheckSpOtherPage", method = RequestMethod.GET)
	public String assCheckSpOtherPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		mode.addAttribute("check_no", mapVo.get("check_no"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mapVo.put("is_stop", "0");
		StoreDict storeDict = storeDictService.queryStoreDictByCode(mapVo);
		mode.addAttribute("store_code", storeDict.getStore_code());
		mode.addAttribute("store_name", storeDict.getStore_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05093",MyConfig.getSysPara("05093"));
		
		return "hrp/ass/assother/asscheck/plan/spAdd";
	}

	/**
	 * @Description 添加数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/addAssCheckPlanOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckPlanOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		if (mapVo.get("beg_date") != null) {
			mapVo.put("beg_date", DateUtil.stringToDate(mapVo.get("beg_date")
					.toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("end_date") != null) {
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date")
					.toString(), "yyyy-MM-dd"));
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
		
		String assCheckPlanOtherJson = assCheckPlanOtherService.add(mapVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);

	}

	/**
	 * @Description 添加数据仓盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/saveOtherInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveOtherInventory(@RequestParam(value = "data1")  String paramVo, Model mode)
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
			
			AssCheckSOther state = assCheckSOtherService.queryState(mapVo);
			 
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			listVo.add(mapVo);
			
		
			
			
		}
		
		
		String assChkInDetailOther= assChkInDetailOtherService.saveOtherInventory(listVo);

		return JSONObject.parseObject(assChkInDetailOther);

	}
	
	/**
	 * @Description 添加数据科室盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/saveDeptOtherInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDeptOtherInventory(@RequestParam(value = "data2")  String paramVo, Model mode)
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
			
			AssCheckDOther state = assCheckDOtherService.queryState(mapVo);
			 
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			listVo.add(mapVo);
		}
		
		
		String assChkInDetailOther= assChkInDetailOtherService.saveDeptOtherInventory(listVo);

		return JSONObject.parseObject(assChkInDetailOther);

	}
	
	/**
	 * @Description 添加数据仓库盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/saveAssCheckSDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckSDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		

		AssCheckSOther state = assCheckSOtherService.queryState(mapVo);
		 
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
		}
		String assCheckSpDetailSpecialJson = assCheckSdetailOtherService.saveAssCheckSDetailOther(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 添加数据科室盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/saveAssCheckDDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckDDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssCheckDOther state = assCheckDOtherService.queryState(mapVo);
		 
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
		}

		String assCheckSpDetailSpecialJson = assCheckDdetailOtherService.saveAssCheckDDetailOther(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 更新跳转页面 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/assCheckPlanOtherUpdatePage", method = RequestMethod.GET)
	public String assCheckPlanOtherUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		AssCheckPlanOther assCheckPlanOther = new AssCheckPlanOther();

		assCheckPlanOther = assCheckPlanOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assCheckPlanOther.getGroup_id());
		mode.addAttribute("hos_id", assCheckPlanOther.getHos_id());
		mode.addAttribute("copy_code", assCheckPlanOther.getCopy_code());
		mode.addAttribute("check_plan_no", assCheckPlanOther.getCheck_plan_no());
		mode.addAttribute("summary", assCheckPlanOther.getSummary());
		mode.addAttribute("beg_date", DateUtil.dateToString(
				assCheckPlanOther.getBeg_date(), "yyyy-MM-dd"));
		mode.addAttribute("end_date", DateUtil.dateToString(
				assCheckPlanOther.getEnd_date(), "yyyy-MM-dd"));
		mode.addAttribute("fin_date", DateUtil.dateToString(
				assCheckPlanOther.getFin_date(), "yyyy-MM-dd"));
		mode.addAttribute("mak_emp", assCheckPlanOther.getMak_emp());
		mode.addAttribute("mak_date", DateUtil.dateToString(
				assCheckPlanOther.getMak_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assCheckPlanOther.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(
				assCheckPlanOther.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assCheckPlanOther.getState());

		mode.addAttribute("ass_05040",MyConfig.getSysPara("ass_05040"));
		
		return "hrp/ass/assother/asscheck/plan/update";
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/updateAssCheckPlanOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckPlanOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("beg_date") != null) {
			mapVo.put("beg_date",
					DateUtil.stringToDate(mapVo.get("beg_date").toString()));
		}
		if (mapVo.get("end_date") != null) {
			mapVo.put("end_date",
					DateUtil.stringToDate(mapVo.get("end_date").toString()));
		}

		String assCheckPlanOtherJson = assCheckPlanOtherService.update(mapVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/addOrUpdateAssCheckPlanOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssCheckPlanOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanOtherJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assCheckPlanOtherJson = assCheckPlanOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);
	}

	/**
	 * @Description 删除数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/deleteAssCheckPlanOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckPlanOther(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			String str = assBaseService.isExistsDataByTable(
					"ASS_CHECK_PLAN_OTHER", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的数据被以下业务使用：【"
						+ str.substring(0, str.length() - 1)
						+ "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			AssCheckPlanOther assCheckPlanOther = assCheckPlanOtherService
					.queryByCode(MmapVo);

			if (assCheckPlanOther.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(MmapVo);
		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已审核的数据不能删除! \"}");
		}

		String assCheckPlanOtherJson = assCheckPlanOtherService
				.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);

	}

	/**
	 * @Description 查询数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/queryAssCheckPlanOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckPlanOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assCheckPlanOther = assCheckPlanOtherService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanOther);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/OtherAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> OtherAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanOtherJson = assCheckPlanOtherService.audit(mapVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/OtherUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> OtherUnAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanOtherJson = assCheckPlanOtherService.unAudit(mapVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);
	}

	/**
	 * @Description 盘点完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/OtherFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> OtherFinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanOtherJson = assCheckPlanOtherService.finish(mapVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);
	}

	/**
	 * @Description 取消完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/OtherUnfinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> OtherUnfinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanOtherJson = assCheckPlanOtherService.unFinish(mapVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/OtherGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> OtherGenerate(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanOtherJson = assCheckPlanOtherService.generate(mapVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/OtherGenerateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> OtherGenerateDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanOtherJson = assCheckPlanOtherService
				.generateDetail(mapVo);

		return JSONObject.parseObject(assCheckPlanOtherJson);
	}

	/**
	 * @Description 查询数据 仓库盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/queryAssCheckSOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanOther = assCheckSOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanOther);

	}

	/**
	 * @Description 查询数据 科室盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/queryAssCheckDOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanOther = assCheckDOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanOther);

	}

	/**
	 * @Description 查询数据 仓库盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/queryAssCheckSdetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSdetailOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanOther = assCheckSdetailOtherService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanOther);

	}

	/**
	 * @Description 查询数据 科室盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/queryAssCheckDdetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDdetailOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanOther = assCheckDdetailOtherService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanOther);

	}

	/**
	 * @Description 盘点单树状结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/queryByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByTree(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());

		List<?> l_map = assCheckPlanOtherService.queryByTree(mapVo);

		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	/**
	 * @Description 科室盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/assCheckDOtherAddPage", method = RequestMethod.GET)
	public String assCheckDOtherAddPage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assother/asscheck/plan/addDept";

	}

	/**
	 * @Description 仓库盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/assCheckSOtherAddPage", method = RequestMethod.GET)
	public String assCheckSOtherAddPage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assother/asscheck/plan/addStore";

	}

	/**
	 * @Description 添加科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/addAssCheckDOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckDOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDOtherService.add(mapVo);
		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 添加仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/addAssCheckSOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckSOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSOtherService.add(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/delAssCheckDOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckDOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/delAssCheckSOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckSOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/auditOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckPlanOtherService.auditCheckOther(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/unAuditOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckPlanOtherService.unAuditCheckOther(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/auditBatchAssCheckSOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckSOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/unAuditBatchAssCheckSOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckSOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/auditBatchAssCheckDOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckDOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/unAuditBatchAssCheckDOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckDOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/copyAmountAssCheckDOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckDOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/copyAmountAssCheckSOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckSOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/delAmountAssCheckSOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckSOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/delAmountAssCheckDOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckDOther(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/queryAssInSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckSdetailOtherService.queryAssInSpecialState(mapVo);
		
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
	@RequestMapping(value = "/hrp/ass/assother/asscheck/plan/queryAssInSpecialStates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialStates(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckDdetailOtherService.queryAssInSpecialStates(mapVo);
		
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
