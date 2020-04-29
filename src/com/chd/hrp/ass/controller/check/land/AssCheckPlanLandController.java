/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.check.land;

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
import com.chd.hrp.ass.entity.check.land.AssCheckLand;
import com.chd.hrp.ass.entity.check.land.AssCheckPlanLand;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.land.AssCheckDetailLandService;
import com.chd.hrp.ass.service.check.land.AssCheckLandService;
import com.chd.hrp.ass.service.check.land.AssCheckPlanLandService;
import com.chd.hrp.ass.service.check.land.AssChkInDetailLandService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;

/**
 * @Description: 051101 盘点任务单(土地)
 * @Table: ASS_CHECK_PLAN_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssCheckPlanLandController extends BaseController {

	private static Logger logger = Logger
			.getLogger(AssCheckPlanLandController.class);

	// 引入Service服务
	@Resource(name = "assCheckPlanLandService")
	private final AssCheckPlanLandService assCheckPlanLandService = null;

	// 引入Service服务
	@Resource(name = "assCheckLandService")
	private final AssCheckLandService assCheckLandService = null;

	// 引入Service服务
	@Resource(name = "assCheckDetailLandService")
	private final AssCheckDetailLandService assCheckDetailLandService = null;

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

	@Resource(name = "assChkInDetailLandService")
	private final AssChkInDetailLandService assChkInDetailLandService = null;
	
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
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/assCheckPlanLandMainPage", method = RequestMethod.GET)
	public String assCheckPlanLandMainPage(Model mode) throws Exception {

		return "hrp/ass/assland/asscheck/plan/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/assCheckPlanLandAddPage", method = RequestMethod.GET)
	public String assCheckPlanLandAddPage(Model mode) throws Exception {

		return "hrp/ass/assland/asscheck/plan/add";

	}

	// 科室盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/assCheckDpLandPage", method = RequestMethod.GET)
	public String assCheckDpLandPage(@RequestParam Map<String, Object> mapVo,
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
		mode.addAttribute("ass_05096",MyConfig.getSysPara("05096"));
		
		return "hrp/ass/assland/asscheck/plan/dpAdd";
	}

	// 盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/assCheckSpLandPage", method = RequestMethod.GET)
	public String assCheckSpLandPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		mode.addAttribute("check_no", mapVo.get("check_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05096",MyConfig.getSysPara("05096"));
		
		return "hrp/ass/assland/asscheck/plan/spAdd";
	}

	/**
	 * @Description 添加数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/addAssCheckPlanLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckPlanLand(
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
		
		String assCheckPlanLandJson = assCheckPlanLandService.add(mapVo);

		return JSONObject.parseObject(assCheckPlanLandJson);

	}

	
	/**
	 * @Description 添加数据仓库盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/saveAssCheckSDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckSDetailLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssCheckLand state = assCheckLandService.queryState(mapVo);
		  
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核的状态下,不能保存单据! \"}");
		}
		
		
		String assCheckSpDetailSpecialJson = assCheckDetailLandService.saveAssCheckSDetailLand(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 更新跳转页面 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/assCheckPlanLandUpdatePage", method = RequestMethod.GET)
	public String assCheckPlanLandUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		AssCheckPlanLand assCheckPlanLand = new AssCheckPlanLand();
		AssCheckLand assCheckLand = new AssCheckLand();

		assCheckPlanLand = assCheckPlanLandService.queryByCode(mapVo);

		if (assCheckPlanLand != null
				&& assCheckPlanLand.getCheck_plan_no() != null) {
			mapVo.put("check_plan_no", assCheckPlanLand.getCheck_plan_no());
		}

		mode.addAttribute("group_id", assCheckPlanLand.getGroup_id());
		mode.addAttribute("hos_id", assCheckPlanLand.getHos_id());
		mode.addAttribute("copy_code", assCheckPlanLand.getCopy_code());
		mode.addAttribute("check_plan_no", assCheckPlanLand.getCheck_plan_no());
		mode.addAttribute("summary", assCheckPlanLand.getSummary());
		mode.addAttribute("beg_date", DateUtil.dateToString(
				assCheckPlanLand.getBeg_date(), "yyyy-MM-dd"));
		mode.addAttribute("end_date", DateUtil.dateToString(
				assCheckPlanLand.getEnd_date(), "yyyy-MM-dd"));
		mode.addAttribute("fin_date", DateUtil.dateToString(
				assCheckPlanLand.getFin_date(), "yyyy-MM-dd"));
		mode.addAttribute("mak_emp", assCheckPlanLand.getMak_emp());
		mode.addAttribute("mak_date", DateUtil.dateToString(
				assCheckPlanLand.getMak_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assCheckPlanLand.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(
				assCheckPlanLand.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assCheckPlanLand.getState());

		assCheckLand = assCheckLandService.queryByCode(mapVo);
		if (assCheckLand != null) {
			mode.addAttribute("check_no", assCheckLand.getCheck_no());
			mode.addAttribute("state_name",assCheckLand.getState_name());
		} else {
			mode.addAttribute("check_no", "");
			if (mapVo.get("state").toString().equals("0")) {
				mode.addAttribute("state_name","新建");
			}else {
			mode.addAttribute("state_name","审核");}
		}
		
		mode.addAttribute("ass_05070",MyConfig.getSysPara("05070"));
		
		return "hrp/ass/assland/asscheck/plan/update";
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/updateAssCheckPlanLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckPlanLand(
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

		String assCheckPlanLandJson = assCheckPlanLandService.update(mapVo);

		return JSONObject.parseObject(assCheckPlanLandJson);
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/addOrUpdateAssCheckPlanLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssCheckPlanLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanLandJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assCheckPlanLandJson = assCheckPlanLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckPlanLandJson);
	}

	/**
	 * @Description 删除数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/deleteAssCheckPlanLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckPlanLand(
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
					"ASS_CHECK_PLAN_LAND", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的数据被以下业务使用：【"
						+ str.substring(0, str.length() - 1)
						+ "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			AssCheckPlanLand assCheckPlanLand = assCheckPlanLandService
					.queryByCode(MmapVo);

			if (assCheckPlanLand.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(MmapVo);
		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已审核的数据不能删除! \"}");
		}
		String assCheckPlanLandJson = assCheckPlanLandService
				.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckPlanLandJson);

	}

	/**
	 * @Description 查询数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/queryAssCheckPlanLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckPlanLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assCheckPlanLand = assCheckPlanLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanLand);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/landAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> landAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanLandJson = assCheckPlanLandService.audit(mapVo);

		return JSONObject.parseObject(assCheckPlanLandJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/landUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> landUnAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanLandJson = assCheckPlanLandService.unAudit(mapVo);

		return JSONObject.parseObject(assCheckPlanLandJson);
	}

	/**
	 * @Description 盘点完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/landFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> landFinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanLandJson = assCheckPlanLandService.finish(mapVo);

		return JSONObject.parseObject(assCheckPlanLandJson);
	}

	/**
	 * @Description 取消完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/landUnfinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> landUnfinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanLandJson = assCheckPlanLandService.unFinish(mapVo);

		return JSONObject.parseObject(assCheckPlanLandJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/landGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> landGenerate(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanLandJson = assCheckPlanLandService.generate(mapVo);

		return JSONObject.parseObject(assCheckPlanLandJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/landGenerateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> landGenerateDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanLandJson = assCheckPlanLandService
				.generateDetail(mapVo);

		return JSONObject.parseObject(assCheckPlanLandJson);
	}

	/**
	 * @Description 查询数据 科室盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/queryAssCheckLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanLand = assCheckLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanLand);

	}

	/**
	 * @Description 查询数据 盘点任务单明细(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/queryAssCheckDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDetailLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanInassets = assCheckDetailLandService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanInassets);

	}

	/**
	 * @Description 盘点单树状结构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/queryByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByTree(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());

		List<?> l_map = assCheckPlanLandService.queryByTree(mapVo);

		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	
	/**
	 * @Description 添加数据仓盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/saveLandInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveLandInventory(@RequestParam(value = "data1")  String paramVo, Model mode)
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
			
			
			AssCheckLand state = assCheckLandService.queryState(mapVo);
			  
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			listVo.add(mapVo);
			
		}
		
		
		String assChkInDetailLand= assChkInDetailLandService.saveLandInventory(listVo);

		return JSONObject.parseObject(assChkInDetailLand);

	}
	
	/**
	 * @Description 科室盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/assCheckLandAddPage", method = RequestMethod.GET)
	public String assCheckLandAddPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assland/asscheck/plan/addDept";

	}

	/**
	 * @Description 仓库盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/assCheckSlandAddPage", method = RequestMethod.GET)
	public String assCheckSlandAddPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assland/asscheck/plan/addStore";

	}

	/**
	 * @Description 添加科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/addAssCheckLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckLandService.add(mapVo);
		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/delAssCheckLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckLandService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/auditLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckPlanLandService.auditCheckLand(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/unAuditLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckPlanLandService.unAuditCheckLand(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/auditBatchAssCheckLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckLandService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审 科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/unAuditBatchAssCheckLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckLandService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/copyAmountAssCheckLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDetailLandService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/delAmountAssCheckLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckLand(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDetailLandService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}
	/**
	 * 状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/queryAssInSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckDetailLandService.queryAssInSpecialState(mapVo);
		
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
	@RequestMapping(value = "/hrp/ass/assland/asscheck/plan/queryAssInSpecialStates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialStates(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckDetailLandService.queryAssInSpecialState(mapVo);
		
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
