/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.check.general;

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
import com.chd.hrp.ass.entity.check.general.AssCheckDGeneral;
import com.chd.hrp.ass.entity.check.general.AssCheckPlanGeneral;
import com.chd.hrp.ass.entity.check.general.AssCheckSGeneral;
import com.chd.hrp.ass.entity.check.special.AssCheckPlanSpecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.general.AssCheckDGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckDdetailGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckPlanGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckSGeneralService;
import com.chd.hrp.ass.service.check.general.AssCheckSdetailGeneralService;
import com.chd.hrp.ass.service.check.general.AssChkInDetailGeneralService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;

/**
 * @Description: 051101 盘点任务单(一般设备)
 * @Table: ASS_CHECK_PLAN_General
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssCheckPlanGeneralController extends BaseController {

	private static Logger logger = Logger
			.getLogger(AssCheckPlanGeneralController.class);

	// 引入Service服务
	@Resource(name = "assCheckPlanGeneralService")
	private final AssCheckPlanGeneralService assCheckPlanGeneralService = null;

	// 引入Service服务
	@Resource(name = "assCheckSGeneralService")
	private final AssCheckSGeneralService assCheckSGeneralService = null;

	// 引入Service服务
	@Resource(name = "assCheckDGeneralService")
	private final AssCheckDGeneralService assCheckDGeneralService = null;

	// 引入Service服务
	@Resource(name = "assCheckSdetailGeneralService")
	private final AssCheckSdetailGeneralService assCheckSdetailGeneralService = null;

	// 引入Service服务
	@Resource(name = "assCheckDdetailGeneralService")
	private final AssCheckDdetailGeneralService assCheckDdetailGeneralService = null;
	
	@Resource(name = "assChkInDetailGeneralService")
	private final AssChkInDetailGeneralService assChkInDetailGeneralService = null;

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

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
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/assCheckPlanGeneralMainPage", method = RequestMethod.GET)
	public String assCheckPlanGeneralMainPage(Model mode) throws Exception {

		return "hrp/ass/assgeneral/asscheck/plan/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/assCheckPlanGeneralAddPage", method = RequestMethod.GET)
	public String assCheckPlanGeneralAddPage(Model mode) throws Exception {

		return "hrp/ass/assgeneral/asscheck/plan/add";

	}

	// 科室盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/assCheckDpGeneralPage", method = RequestMethod.GET)
	public String assCheckDpGeneralPage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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
		mode.addAttribute("ass_05092",MyConfig.getSysPara("05092"));
		
		return "hrp/ass/assgeneral/asscheck/plan/dpAdd";
	}

	// 仓库盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/assCheckSpGeneralPage", method = RequestMethod.GET)
	public String assCheckSpGeneralPage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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
		mode.addAttribute("ass_05092",MyConfig.getSysPara("05092"));
		
		return "hrp/ass/assgeneral/asscheck/plan/spAdd";
	}

	/**
	 * @Description 添加数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/addAssCheckPlanGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckPlanGeneral(
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
		
		String assCheckPlanGeneralJson = assCheckPlanGeneralService.add(mapVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);

	}

	/**
	 * @Description 更新跳转页面 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/assCheckPlanGeneralUpdatePage", method = RequestMethod.GET)
	public String assCheckPlanGeneralUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		AssCheckPlanGeneral assCheckPlanGeneral = new AssCheckPlanGeneral();

		assCheckPlanGeneral = assCheckPlanGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assCheckPlanGeneral.getGroup_id());
		mode.addAttribute("hos_id", assCheckPlanGeneral.getHos_id());
		mode.addAttribute("copy_code", assCheckPlanGeneral.getCopy_code());
		mode.addAttribute("check_plan_no",
				assCheckPlanGeneral.getCheck_plan_no());
		mode.addAttribute("summary", assCheckPlanGeneral.getSummary());
		mode.addAttribute("beg_date", DateUtil.dateToString(
				assCheckPlanGeneral.getBeg_date(), "yyyy-MM-dd"));
		mode.addAttribute("end_date", DateUtil.dateToString(
				assCheckPlanGeneral.getEnd_date(), "yyyy-MM-dd"));
		mode.addAttribute("fin_date", DateUtil.dateToString(
				assCheckPlanGeneral.getFin_date(), "yyyy-MM-dd"));
		mode.addAttribute("mak_emp", assCheckPlanGeneral.getMak_emp());
		mode.addAttribute("mak_date", DateUtil.dateToString(
				assCheckPlanGeneral.getMak_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assCheckPlanGeneral.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(
				assCheckPlanGeneral.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assCheckPlanGeneral.getState());

		mode.addAttribute("ass_05030",MyConfig.getSysPara("05030"));
		
		return "hrp/ass/assgeneral/asscheck/plan/update";
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/updateAssCheckPlanGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckPlanGeneral(
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

		String assCheckPlanGeneralJson = assCheckPlanGeneralService
				.update(mapVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/addOrUpdateAssCheckPlanGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssCheckPlanGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanGeneralJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(4, 6);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		assCheckPlanGeneralJson = assCheckPlanGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);
	}

	/**
	 * @Description 删除数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/deleteAssCheckPlanGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckPlanGeneral(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> MmapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			MmapVo.put("group_id", ids[0]);
			MmapVo.put("hos_id", ids[1]);
			MmapVo.put("copy_code", ids[2]);
			MmapVo.put("check_plan_no", ids[3]);

			String retErrot = "";

			String str = assBaseService.isExistsDataByTable(
					"ASS_CHECK_PLAN_GENERAL", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的数据被以下业务使用：【"
						+ str.substring(0, str.length() - 1)
						+ "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			AssCheckPlanGeneral assCheckPlanGeneral = assCheckPlanGeneralService
					.queryByCode(MmapVo);

			if (assCheckPlanGeneral.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(MmapVo);
		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已审核的数据不能删除! \"}");
		}

		String assCheckPlanGeneralJson = assCheckPlanGeneralService
				.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);

	}

	/**
	 * @Description 查询数据 051101 盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/queryAssCheckPlanGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckPlanGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assCheckPlanGeneral = assCheckPlanGeneralService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanGeneral);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/GeneralAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GeneralAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanGeneralJson = assCheckPlanGeneralService
				.audit(mapVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/GeneralUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GeneralUnAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanGeneralJson = assCheckPlanGeneralService
				.unAudit(mapVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);
	}

	/**
	 * @Description 盘点完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/GeneralFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GeneralFinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanGeneralJson = assCheckPlanGeneralService
				.finish(mapVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);
	}

	/**
	 * @Description 取消完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/GeneralUnfinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GeneralUnfinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanGeneralJson = assCheckPlanGeneralService
				.unFinish(mapVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/GeneralGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GeneralGenerate(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanGeneralJson = assCheckPlanGeneralService
				.generate(mapVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/GeneralGenerateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> GeneralGenerateDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanGeneralJson = assCheckPlanGeneralService
				.generateDetail(mapVo);

		return JSONObject.parseObject(assCheckPlanGeneralJson);
	}

	/**
	 * @Description 查询数据 仓库盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/queryAssCheckSGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanGeneral = assCheckSGeneralService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanGeneral);

	}

	/**
	 * @Description 查询数据 科室盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/queryAssCheckDGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanGeneral = assCheckDGeneralService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanGeneral);

	}

	/**
	 * @Description 查询数据 仓库盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/queryAssCheckSdetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSdetailGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanGeneral = assCheckSdetailGeneralService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanGeneral);

	}

	/**
	 * @Description 添加数据仓盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/saveGeneralInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveGeneralInventory(@RequestParam(value = "data1")  String paramVo, Model mode)
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
			
			AssCheckSGeneral state = assCheckSGeneralService.queryState(mapVo);
			 
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}

			listVo.add(mapVo);
			
		}
		
		
		String assChkInDetailGeneral= assChkInDetailGeneralService.saveGeneralInventory(listVo);

		return JSONObject.parseObject(assChkInDetailGeneral);

	}
	
	
	/**
	 * @Description 添加数据科室盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/saveDeptGeneralInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDeptGeneralInventory(@RequestParam(value = "data2")  String paramVo, Model mode)
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
			
			AssCheckDGeneral state = assCheckDGeneralService.queryState(mapVo);
			 
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}

			listVo.add(mapVo);
		}
		
		
		String assChkInDetailGeneral= assChkInDetailGeneralService.saveDeptGeneralInventory(listVo);

		return JSONObject.parseObject(assChkInDetailGeneral);

	}
	
	/**
	 * @Description 查询数据 科室盘点任务单(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/queryAssCheckDdetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDdetailGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanGeneral = assCheckDdetailGeneralService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanGeneral);

	}

	/**
	 * @Description 盘点单树状结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/queryByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByTree(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		
		List<?> l_map = assCheckPlanGeneralService.queryByTree(mapVo);

		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	/**
	 * @Description 科室盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/assCheckDGeneralAddPage", method = RequestMethod.GET)
	public String assCheckDGeneralAddPage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assgeneral/asscheck/plan/addDept";

	}

	/**
	 * @Description 仓库盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/assCheckSGeneralAddPage", method = RequestMethod.GET)
	public String assCheckSGeneralAddPage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assgeneral/asscheck/plan/addStore";

	}

	/**
	 * @Description 添加数据仓库盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/saveAssCheckSDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckSDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssCheckSGeneral state = assCheckSGeneralService.queryState(mapVo);
		 
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
		}

		String assCheckSpDetailSpecialJson = assCheckSdetailGeneralService.saveAssCheckSDetailGeneral(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 添加数据科室盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/saveAssCheckDDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckDDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssCheckDGeneral state = assCheckDGeneralService.queryState(mapVo);
		 
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
		}


		String assCheckSpDetailSpecialJson = assCheckDdetailGeneralService.saveAssCheckDDetailGeneral(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 添加科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/addAssCheckDGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckDGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDGeneralService.add(mapVo);
		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 添加仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/addAssCheckSGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckSGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSGeneralService.add(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/delAssCheckDGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckDGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDGeneralService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/delAssCheckSGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckSGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSGeneralService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/auditGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckPlanGeneralService.auditCheckGeneral(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/unAuditGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckPlanGeneralService.unAuditCheckGeneral(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/auditBatchAssCheckSGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckSGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckSGeneralService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/unAuditBatchAssCheckSGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckSGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSGeneralService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/auditBatchAssCheckDGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckDGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckDGeneralService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审 科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/unAuditBatchAssCheckDGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckDGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDGeneralService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/copyAmountAssCheckDGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckDGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDdetailGeneralService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/copyAmountAssCheckSGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckSGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckSdetailGeneralService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/delAmountAssCheckSGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckSGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckSdetailGeneralService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/delAmountAssCheckDGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckDGeneral(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDdetailGeneralService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}
	/**
	 * 状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/queryAssInSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckSdetailGeneralService.queryAssInSpecialState(mapVo);
		
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
	@RequestMapping(value = "/hrp/ass/assgeneral/asscheck/plan/queryAssInSpecialStates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialStates(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckDdetailGeneralService.queryAssInSpecialStates(mapVo);
		
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
