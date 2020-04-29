/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.check.house;

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
import com.chd.hrp.ass.entity.check.house.AssCheckPlanHouse;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.house.AssCheckDetailHouseService;
import com.chd.hrp.ass.service.check.house.AssCheckHouseService;
import com.chd.hrp.ass.service.check.house.AssCheckPlanHouseService;
import com.chd.hrp.ass.service.check.house.AssChkInDetailHouseService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;

/**
 * @Description: 051101 盘点任务单(土地)
 * @Table: ASS_CHECK_PLAN_House
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssCheckPlanHouseController extends BaseController {

	private static Logger logger = Logger
			.getLogger(AssCheckPlanHouseController.class);

	// 引入Service服务
	@Resource(name = "assCheckPlanHouseService")
	private final AssCheckPlanHouseService assCheckPlanHouseService = null;

	// 引入Service服务
	@Resource(name = "assCheckHouseService")
	private final AssCheckHouseService assCheckHouseService = null;

	// 引入Service服务
	@Resource(name = "assCheckDetailHouseService")
	private final AssCheckDetailHouseService assCheckDetailHouseService = null;

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

	@Resource(name = "storeDictService")
	private final StoreDictServiceImpl storeDictService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assChkInDetailHouseService")
	private final AssChkInDetailHouseService assChkInDetailHouseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/assCheckPlanHouseMainPage", method = RequestMethod.GET)
	public String assCheckPlanHouseMainPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/asscheck/plan/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/assCheckPlanHouseAddPage", method = RequestMethod.GET)
	public String assCheckPlanHouseAddPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/asscheck/plan/add";

	}

	// 科室盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/assCheckDpHousePage", method = RequestMethod.GET)
	public String assCheckDpHousePage(@RequestParam Map<String, Object> mapVo,
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
		mode.addAttribute("ass_05095",MyConfig.getSysPara("05095"));
		
		return "hrp/ass/asshouse/asscheck/plan/dpAdd";
	}

	// 盘盈登记跳转
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/assCheckSpHousePage", method = RequestMethod.GET)
	public String assCheckSpHousePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		mode.addAttribute("check_no", mapVo.get("check_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05095",MyConfig.getSysPara("05095"));
		
		return "hrp/ass/asshouse/asscheck/plan/spAdd";
	}

	/**
	 * @Description 添加数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/addAssCheckPlanHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckPlanHouse(
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
		
		String assCheckPlanHouseJson = assCheckPlanHouseService.add(mapVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);

	}

	
	/**
	 * @Description 添加数据仓盘盈入库
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/saveHouseInventory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHouseInventory(@RequestParam(value = "data1")  String paramVo, Model mode)
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
			
			
			AssCheckHouse state = assCheckHouseService.queryState(mapVo);
			  
			if (state.getState() == 0) {
				return JSONObject.parseObject("{\"warn\":\"新建的状态下,不能生成盘亏单! \"}");
			}
			listVo.add(mapVo);
			
		
			
			
		}
		
		
		String assChkInDetailHouse= assChkInDetailHouseService.saveHouseInventory(listVo);

		return JSONObject.parseObject(assChkInDetailHouse);

	}
	
	/**
	 * @Description 添加数据仓库盘点明细_医用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/saveAssCheckSDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCheckSDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssCheckHouse state = assCheckHouseService.queryState(mapVo);
		  
		if (state.getState() != 0) {
			return JSONObject.parseObject("{\"warn\":\"审核之后的数据,不能保存! \"}");
		}
		String assCheckSpDetailSpecialJson = assCheckDetailHouseService.saveAssCheckSDetailHouse(mapVo);

		return JSONObject.parseObject(assCheckSpDetailSpecialJson);

	}
	
	
	/**
	 * @Description 更新跳转页面 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/assCheckPlanHouseUpdatePage", method = RequestMethod.GET)
	public String assCheckPlanHouseUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		AssCheckPlanHouse assCheckPlanHouse = new AssCheckPlanHouse();
		AssCheckHouse assCheckHouse = new AssCheckHouse();

		assCheckPlanHouse = assCheckPlanHouseService.queryByCode(mapVo);

		if (assCheckPlanHouse != null
				&& assCheckPlanHouse.getCheck_plan_no() != null) {
			mapVo.put("check_plan_no", assCheckPlanHouse.getCheck_plan_no());
		}

		mode.addAttribute("group_id", assCheckPlanHouse.getGroup_id());
		mode.addAttribute("hos_id", assCheckPlanHouse.getHos_id());
		mode.addAttribute("copy_code", assCheckPlanHouse.getCopy_code());
		mode.addAttribute("check_plan_no", assCheckPlanHouse.getCheck_plan_no());
		mode.addAttribute("summary", assCheckPlanHouse.getSummary());
		mode.addAttribute("beg_date", DateUtil.dateToString(
				assCheckPlanHouse.getBeg_date(), "yyyy-MM-dd"));
		mode.addAttribute("end_date", DateUtil.dateToString(
				assCheckPlanHouse.getEnd_date(), "yyyy-MM-dd"));
		mode.addAttribute("fin_date", DateUtil.dateToString(
				assCheckPlanHouse.getFin_date(), "yyyy-MM-dd"));
		mode.addAttribute("mak_emp", assCheckPlanHouse.getMak_emp());
		mode.addAttribute("mak_date", DateUtil.dateToString(
				assCheckPlanHouse.getMak_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assCheckPlanHouse.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(
				assCheckPlanHouse.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assCheckPlanHouse.getState());

		assCheckHouse = assCheckHouseService.queryByCode(mapVo);
		if (assCheckHouse != null) {
			mode.addAttribute("check_no", assCheckHouse.getCheck_no());
			mode.addAttribute("state_name",assCheckHouse.getState_name());
		} else {
			mode.addAttribute("check_no", "");
			if (mapVo.get("state").toString().equals("0")) {
				mode.addAttribute("state_name","新建");
			}else {
			mode.addAttribute("state_name","审核");
			}
		}
		
		mode.addAttribute("ass_05060",MyConfig.getSysPara("05060"));

		return "hrp/ass/asshouse/asscheck/plan/update";
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/updateAssCheckPlanHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCheckPlanHouse(
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

		String assCheckPlanHouseJson = assCheckPlanHouseService.update(mapVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);
	}

	/**
	 * @Description 更新数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/addOrUpdateAssCheckPlanHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssCheckPlanHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanHouseJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assCheckPlanHouseJson = assCheckPlanHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);
	}

	/**
	 * @Description 删除数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/deleteAssCheckPlanHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckPlanHouse(
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
					"ASS_CHECK_PLAN_HOUSE", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的数据被以下业务使用：【"
						+ str.substring(0, str.length() - 1)
						+ "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			AssCheckPlanHouse assCheckPlanHouse = assCheckPlanHouseService
					.queryByCode(MmapVo);

			if (assCheckPlanHouse.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(MmapVo);
		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已审核的数据不能删除! \"}");
		}
		String assCheckPlanHouseJson = assCheckPlanHouseService
				.deleteBatch(listVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);

	}

	/**
	 * @Description 查询数据 051101 盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/queryAssCheckPlanHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckPlanHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assCheckPlanHouse = assCheckPlanHouseService
				.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanHouse);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/HouseAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> HouseAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanHouseJson = assCheckPlanHouseService.audit(mapVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/HouseUnAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> HouseUnAudit(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanHouseJson = assCheckPlanHouseService.unAudit(mapVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);
	}

	/**
	 * @Description 盘点完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/HouseFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> HouseFinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanHouseJson = assCheckPlanHouseService.finish(mapVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);
	}

	/**
	 * @Description 取消完成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/HouseUnFinish", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> HouseUnfinish(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckPlanHouseJson = assCheckPlanHouseService.unFinish(mapVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/HouseGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> HouseGenerate(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanHouseJson = assCheckPlanHouseService.generate(mapVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);
	}

	/**
	 * @Description 生成盘点的
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/HouseGenerateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> HouseGenerateDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		mapVo.put("user_id", SessionManager.getUserId());

		String assCheckPlanHouseJson = assCheckPlanHouseService
				.generateDetail(mapVo);

		return JSONObject.parseObject(assCheckPlanHouseJson);
	}

	/**
	 * @Description 查询数据 科室盘点任务单(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/queryAssCheckHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanHouse = assCheckHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assCheckPlanHouse);

	}

	/**
	 * @Description 查询数据 盘点任务单明细(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/queryAssCheckDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckDetailHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		String assCheckPlanInassets = assCheckDetailHouseService
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
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/queryByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByTree(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("user_id", SessionManager.getUserId());
		
		List<?> l_map = assCheckPlanHouseService.queryByTree(mapVo);

		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	/**
	 * @Description 科室盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/assCheckHouseAddPage", method = RequestMethod.GET)
	public String assCheckHouseAddPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/asshouse/asscheck/plan/addDept";

	}

	/**
	 * @Description 仓库盘点添加跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/assCheckSHouseAddPage", method = RequestMethod.GET)
	public String assCheckSHouseAddPage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mode.addAttribute("check_plan_no", mapVo.get("check_plan_no"));
		return "hrp/ass/asshouse/asscheck/plan/addStore";

	}

	/**
	 * @Description 添加科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/addAssCheckHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCheckHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckHouseService.add(mapVo);
		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 删除科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/delAssCheckHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAssCheckHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckHouseService.delete(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 审核仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/auditHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckPlanHouseService.auditCheckHouse(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 消审仓库盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/unAuditHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckPlanHouseService.unAuditCheckHouse(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量审核科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/auditBatchAssCheckHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditBatchAssCheckHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "1");

		String json = assCheckHouseService.auditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 批量消审 科室盘点单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/unAuditBatchAssCheckHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditBatchAssCheckHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("check_emp", SessionManager.getUserId());

		mapVo.put("check_date", DateUtil.getCurrenDate());

		mapVo.put("state", "0");

		String json = assCheckHouseService.unAuditBatch(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 科室盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/copyAmountAssCheckHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAmountAssCheckHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDetailHouseService.copyAmount(mapVo);

		return JSONObject.parseObject(json);
	}

	/**
	 * @Description 复制账面数量 盘点
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/delAmountAssCheckHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delAmountAssCheckHouse(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String json = assCheckDetailHouseService.delCard(mapVo);

		return JSONObject.parseObject(json);
	}
	/**
	 * 状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/queryAssInSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckDetailHouseService.queryAssInSpecialState(mapVo);
		
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
	@RequestMapping(value = "/hrp/ass/asshouse/asscheck/plan/queryAssInSpecialStates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInSpecialStates(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assCheckDetailHouseService.queryAssInSpecialState(mapVo);
		
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
