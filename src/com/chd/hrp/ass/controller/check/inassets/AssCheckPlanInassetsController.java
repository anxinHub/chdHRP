/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.check.inassets;

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
import com.chd.hrp.ass.entity.check.inassets.AssCheckDinassets;
import com.chd.hrp.ass.entity.check.inassets.AssCheckPlanInassets;
import com.chd.hrp.ass.entity.check.inassets.AssCheckSinassets;
import com.chd.hrp.ass.entity.check.special.AssCheckPlanSpecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.inassets.AssCheckDdetailInassetsService;
import com.chd.hrp.ass.service.check.inassets.AssCheckDinassetsService;
import com.chd.hrp.ass.service.check.inassets.AssCheckPlanInassetsService;
import com.chd.hrp.ass.service.check.inassets.AssCheckSdetailInassetsService;
import com.chd.hrp.ass.service.check.inassets.AssCheckSinassetsService;
import com.chd.hrp.ass.service.check.inassets.AssChkInDetailInassetsService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;

/**
 * @Description: 051101 盘点任务单(无形资产)
 * @Table: ASS_CHECK_PLAN_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssCheckPlanInassetsController extends BaseController {

	private static Logger logger = Logger
			.getLogger(AssCheckPlanInassetsController.class);

	// 引入Service服务
	@Resource(name = "assCheckPlanInassetsService")
	private final AssCheckPlanInassetsService assCheckPlanInassetsService = null;

	// 引入Service服务
	@Resource(name = "assCheckSinassetsService")
	private final AssCheckSinassetsService assCheckSinassetsService = null;

	// 引入Service服务
	@Resource(name = "assCheckDinassetsService")
	private final AssCheckDinassetsService assCheckDinassetsService = null;

	// 引入Service服务
	@Resource(name = "assCheckSdetailInassetsService")
	private final AssCheckSdetailInassetsService assCheckSdetailInassetsService = null;

	// 引入Service服务
	@Resource(name = "assCheckDdetailInassetsService")
	private final AssCheckDdetailInassetsService assCheckDdetailInassetsService = null;

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

	@Resource(name = "storeDictService")
	private final StoreDictServiceImpl storeDictService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assChkInDetailInassetsService")
	private final AssChkInDetailInassetsService assChkInDetailInassetsService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/assCheckPlanInassetsMainPage", method = RequestMethod.GET)
	public String assCheckPlanInassetsMainPage(Model mode) throws Exception {

		return "hrp/ass/assinassets/asscheck/plan/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/assCheckPlanInassetsAddPage", method = RequestMethod.GET)
	public String assCheckPlanInassetsAddPage(Model mode) throws Exception {

		return "hrp/ass/assinassets/asscheck/plan/add";

	}

	// 科室盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/assCheckDpInassetsPage", method = RequestMethod.GET)
	public String assCheckDpInassetsPage(
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
		mode.addAttribute("ass_05094",MyConfig.getSysPara("05094"));
		
		return "hrp/ass/assinassets/asscheck/plan/dpAdd";
	}

	// 仓库盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/assCheckSpInassetsPage", method = RequestMethod.GET)
	public String assCheckSpInassetsPage(
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
		mode.addAttribute("ass_05094",MyConfig.getSysPara("05094"));
		
		return "hrp/ass/assinassets/asscheck/plan/spAdd";
	}

	/**
	 * @Description 添加数据 051101 盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/addAssCheckPlanInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckPlanInassets(
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
		
		String assCheckPlanInassetsJson = assCheckPlanInassetsService
				.add(mapVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);

	}

	/**
	 * @Description 添加数据仓盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/saveInassetsInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveInassetsInventory(@RequestParam(value = "data1")  String paramVo, Model mode)
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
			
			AssCheckSinassets state = assCheckSinassetsService.queryState(mapVo);
			 
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建状态下,不能生成盈亏单! \"}");
			}

			
			
			listVo.add(mapVo);
			
		
			
			
		}
		
		
		String assChkInDetailInassets= assChkInDetailInassetsService.saveInassetsInventory(listVo);

		return JSONObject.parseObject(assChkInDetailInassets);

	}
	
	/**
	 * @Description 添加数据科室盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/saveDeptInassetsInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveDeptInassetsInventory(@RequestParam(value = "data2")  String paramVo, Model mode)
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
			
			
			AssCheckDinassets state = assCheckDinassetsService.queryState(mapVo);
			 
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建状态下,不能生成盈亏单! \"}");
			}

			listVo.add(mapVo);
		}
		
		
		String assChkInDetailInassets= assChkInDetailInassetsService.saveDeptInassetsInventory(listVo);

		return JSONObject.parseObject(assChkInDetailInassets);

	}
	
	/**
	 * @Description 更新跳转页面 051101 盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/assCheckPlanInassetsUpdatePage", method = RequestMethod.GET)
	public String assCheckPlanInassetsUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		AssCheckPlanInassets assCheckPlanInassets = new AssCheckPlanInassets();

		assCheckPlanInassets = assCheckPlanInassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assCheckPlanInassets.getGroup_id());
		mode.addAttribute("hos_id", assCheckPlanInassets.getHos_id());
		mode.addAttribute("copy_code", assCheckPlanInassets.getCopy_code());
		mode.addAttribute("check_plan_no",
				assCheckPlanInassets.getCheck_plan_no());
		mode.addAttribute("summary", assCheckPlanInassets.getSummary());
		mode.addAttribute("beg_date", DateUtil.dateToString(
				assCheckPlanInassets.getBeg_date(), "yyyy-MM-dd"));
		mode.addAttribute("end_date", DateUtil.dateToString(
				assCheckPlanInassets.getEnd_date(), "yyyy-MM-dd"));
		mode.addAttribute("fin_date", DateUtil.dateToString(
				assCheckPlanInassets.getFin_date(), "yyyy-MM-dd"));
		mode.addAttribute("mak_emp", assCheckPlanInassets.getMak_emp());
		mode.addAttribute("mak_date", DateUtil.dateToString(
				assCheckPlanInassets.getMak_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assCheckPlanInassets.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(
				assCheckPlanInassets.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assCheckPlanInassets.getState());

		mode.addAttribute("ass_05050",MyConfig.getSysPara("05050"));
		
		return "hrp/ass/assinassets/asscheck/plan/update";
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/updateAssCheckPlanInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckPlanInassets(
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

		String assCheckPlanInassetsJson = assCheckPlanInassetsService
				.update(mapVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);
	}

	
	/**
	 * @Description 添加数据仓库盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/saveAssCheckSDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckSDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssCheckSinassets state = assCheckSinassetsService.queryState(mapVo);
		 
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
		}

		String assCheckSpDetailSpecialJson = assCheckSdetailInassetsService.saveAssCheckSDetailInassets(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	
	
	/**
	 * @Description 添加数据科室盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/saveAssCheckDDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckDDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssCheckDinassets state = assCheckDinassetsService.queryState(mapVo);
		 
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核状态下,不能保存! \"}");
		}

		String assCheckSpDetailSpecialJson = assCheckDdetailInassetsService.saveAssCheckDDetailInassets(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	/**
	 * @Description 更新数据 051101 盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/addOrUpdateAssCheckPlanInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssCheckPlanInassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanInassetsJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assCheckPlanInassetsJson = assCheckPlanInassetsService
				.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);
	}

	/**
	 * @Description 删除数据 051101 盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/deleteAssCheckPlanInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckPlanInassets(
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
					"ASS_CHECK_PLAN_INASSETS", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的数据被以下业务使用：【"
						+ str.substring(0, str.length() - 1)
						+ "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			AssCheckPlanInassets assCheckPlanInassets = assCheckPlanInassetsService
					.queryByCode(MmapVo);

			if (assCheckPlanInassets.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(MmapVo);
		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已审核的数据不能删除! \"}");
		}

		String assCheckPlanInassetsJson = assCheckPlanInassetsService
				.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);

	}

	/**
	 * @Description 查询数据 051101 盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/queryAssCheckPlanInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckPlanInassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assCheckPlanInassets = assCheckPlanInassetsService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanInassets);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/inassetsAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inassetsAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanInassetsJson = assCheckPlanInassetsService
				.audit(mapVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/inassetsUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inassetsUnAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanInassetsJson = assCheckPlanInassetsService
				.unAudit(mapVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);
	}

	/**
	 * @Description 盘点完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/inassetsFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inassetsFinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanInassetsJson = assCheckPlanInassetsService
				.finish(mapVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);
	}

	/**
	 * @Description 取消完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/inassetsUnFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inassetsUnfinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanInassetsJson = assCheckPlanInassetsService
				.unFinish(mapVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/inassetsGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inassetsGenerate(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanInassetsJson = assCheckPlanInassetsService
				.generate(mapVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/inassetsGenerateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inassetsGenerateDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanInassetsJson = assCheckPlanInassetsService
				.generateDetail(mapVo);

		return JSONObject.parseObject(assCheckPlanInassetsJson);
	}

	/**
	 * @Description 查询数据 仓库盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/queryAssCheckSinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanInassets = assCheckSinassetsService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanInassets);

	}

	/**
	 * @Description 查询数据 科室盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/queryAssCheckDinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanInassets = assCheckDinassetsService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanInassets);

	}

	/**
	 * @Description 查询数据 仓库盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/queryAssCheckSdetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckSdetailInassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanInassets = assCheckSdetailInassetsService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanInassets);

	}

	/**
	 * @Description 查询数据 科室盘点任务单(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/queryAssCheckDdetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDdetailInassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanInassets = assCheckDdetailInassetsService
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
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/queryByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByTree(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		
		List<?> l_map = assCheckPlanInassetsService.queryByTree(mapVo);

		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	/**
	 * @Description 科室盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/assCheckDinassetsAddPage", method = RequestMethod.GET)
	public String assCheckDinassetsAddPage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assinassets/asscheck/plan/addDept";

	}

	/**
	 * @Description 仓库盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/assCheckSinassetsAddPage", method = RequestMethod.GET)
	public String assCheckSinassetsAddPage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/assinassets/asscheck/plan/addStore";

	}

	/**
	 * @Description 添加科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/addAssCheckDinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckDinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDinassetsService.add(mapVo);
		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 添加仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/addAssCheckSinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckSinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSinassetsService.add(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/delAssCheckDinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckDinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDinassetsService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/delAssCheckSinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckSinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSinassetsService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/auditInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditInassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckPlanInassetsService.auditCheckInassets(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/unAuditInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditInassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckPlanInassetsService.unAuditCheckInassets(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/auditBatchAssCheckSinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckSinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckSinassetsService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/unAuditBatchAssCheckSinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckSinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckSinassetsService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/auditBatchAssCheckDinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckDinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckDinassetsService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审 科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/unAuditBatchAssCheckDinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckDinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckDinassetsService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/copyAmountAssCheckDinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckDinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDdetailInassetsService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/copyAmountAssCheckSinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckSinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckSdetailInassetsService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/delAmountAssCheckSinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckSinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckSdetailInassetsService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/delAmountAssCheckDinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckDinassets(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDdetailInassetsService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}
	/**
	 * 状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/queryAssInSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckSdetailInassetsService.queryAssInSpecialState(mapVo);
		
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
	@RequestMapping(value = "/hrp/ass/assinassets/asscheck/plan/queryAssInSpecialStates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialStates(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckDdetailInassetsService.queryAssInSpecialStates(mapVo);
		
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
