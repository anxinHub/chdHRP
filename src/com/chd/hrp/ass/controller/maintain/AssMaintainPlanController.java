/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.maintain;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlan;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
import com.chd.hrp.ass.service.HrpAssSelectService;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanItemService;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanService;
import com.chd.hrp.ass.service.maintain.AssMaintainRecService;

/**
 * 
 * @Description: 051202 保养计划
 * @Table: ASS_MAINTAIN_PLAN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMaintainPlanController extends BaseController { 
 
	private static Logger logger = Logger.getLogger(AssMaintainPlanController.class);

	// 引入Service服务
	@Resource(name = "assMaintainPlanService")
	private final AssMaintainPlanService assMaintainPlanService = null;

	@Resource(name = "assMaintainPlanItemService")
	private final AssMaintainPlanItemService assMaintainPlanItemService = null;

	// 引入Service服务
	@Resource(name = "hrpAssSelectService")
	private final HrpAssSelectService hrpAssSelectService = null;
	
	@Resource(name = "assMaintainRecService")
	private final AssMaintainRecService assMaintainRecService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/assMaintainPlanMainPage", method = RequestMethod.GET)
	public String assMaintainPlanMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05101",MyConfig.getSysPara("05101"));
		
		return "hrp/ass/assmaintainplan/assMaintainPlanMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/assMaintainPlanAddPage", method = RequestMethod.GET)
	public String assMaintainPlanAddPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainplan/assMaintainPlanAdd";

	}

	/**
	 * @Description 添加数据 051202 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/addAssMaintainPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMaintainPlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		try {
			mapVo.put("group_id", SessionManager.getGroupId());
	
			mapVo.put("hos_id", SessionManager.getHosId());
	
			mapVo.put("copy_code", SessionManager.getCopyCode());
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
			mapVo.put("create_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
	
			mapVo.put("create_emp", SessionManager.getUserId());
	
			mapVo.put("state", "0");
	
			mapVo.put("audit_emp", "");
	
			mapVo.put("audit_date", "");
	
			mapVo.put("stop_emp", "");
	
			mapVo.put("stop_date", "");
	
			if (mapVo.get("last_exec_date").toString() == "") {
	
				mapVo.put("last_exec_date", "");
			} else {
	
				mapVo.put("last_exec_date", DateUtil.stringToDate(mapVo.get("last_exec_date").toString(), "yyyy-MM-dd"));
	
			}
	
			if (mapVo.get("next_exec_date").toString() == "") {
				mapVo.put("next_exec_date", "");
			} else {
				mapVo.put("next_exec_date", DateUtil.stringToDate(mapVo.get("next_exec_date").toString(), "yyyy-MM-dd"));
			}
	
			if (mapVo.get("plan_start_date").toString() == "") {
				mapVo.put("plan_start_date", "");
			}
	
			else {
				mapVo.put("plan_start_date", DateUtil.stringToDate(mapVo.get("plan_start_date").toString(), "yyyy-MM-dd"));
			}
	
			if (mapVo.get("plan_end_date").toString() == "") {
				mapVo.put("plan_end_date", "");
			} else {
				mapVo.put("plan_end_date", DateUtil.stringToDate(mapVo.get("plan_end_date").toString(), "yyyy-MM-dd"));
			}
	
			if (mapVo.get("stop_date").toString() == "") {
				mapVo.put("stop_date", "");
			}
	
			else {
				mapVo.put("stop_date", DateUtil.stringToDate(mapVo.get("stop_date").toString(), "yyyy-MM-dd"));
			}
	
			/*String assMaintainPlanJson = assMaintainPlanService.addOrUpdateAssMaintainPlan(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(assMaintainPlanJson);
			String plan_id = (String) jsonObj.get("plan_id");
			String plan_no = (String) jsonObj.get("plan_no");
			String assMaintainPlanItemJson = "";

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());
				
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}

				detailVo.put("item_code", detailVo.get("maintain_item_code"));

				detailVo.put("item_name", detailVo.get("maintain_item_name"));

				detailVo.put("ass_card_no", detailVo.get("ass_card_no"));

				detailVo.put("plan_id", plan_id);

				if (detailVo.get("detail_id") == null) {
					detailVo.put("detail_id", "0");
				} else {
					detailVo.put("detail_id", detailVo.get("detail_id"));
				}
				
				String yearmonth=mapVo.get("ass_year").toString()+mapVo.get("ass_month").toString();
				//启动系统时间
				Map<String, Object> start = SessionManager.getModStartMonth();
				
				String startyearmonth = (String) start.get("05");
				
				int result = startyearmonth.compareTo(yearmonth);
				
				boolean b =	SessionManager.getAccYearMonthIsCheckOut(yearmonth,"ass_flag");
				
				if(result > 0 ){
					
					return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
					
				} else if(b == true){
					
					return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
					
				}
				
				
				assMaintainPlanItemJson = assMaintainPlanItemService.addOrUpdateAssMaintainPlanItem(detailVo);

			}
			JSONObject json = JSONArray.parseObject(assMaintainPlanItemJson);
			json.put("plan_id", plan_id);
			json.put("plan_no", plan_no);*/
			return JSONObject.parseObject(assMaintainPlanService.addOrUpdateMain(mapVo));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051202 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/assMaintainPlanUpdatePage", method = RequestMethod.GET)
	public String assMaintainPlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		AssMaintainPlan assMaintainPlan = new AssMaintainPlan();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		assMaintainPlan = assMaintainPlanService.queryAssMaintainPlanByCode(mapVo);

		mode.addAttribute("group_id", assMaintainPlan.getGroup_id());

		mode.addAttribute("hos_id", assMaintainPlan.getHos_id());

		mode.addAttribute("copy_code", assMaintainPlan.getCopy_code());

		mode.addAttribute("plan_id", assMaintainPlan.getPlan_id());

		mode.addAttribute("plan_no", assMaintainPlan.getPlan_no());

		mode.addAttribute("plan_name", assMaintainPlan.getPlan_name());

		mode.addAttribute("ass_year", assMaintainPlan.getAss_year());

		mode.addAttribute("ass_month", assMaintainPlan.getAss_month());

		mode.addAttribute("ass_nature", assMaintainPlan.getAss_nature());

		mode.addAttribute("naturs_name", assMaintainPlan.getNaturs_name());

		mode.addAttribute("maintain_degree", assMaintainPlan.getMaintain_degree());

		mode.addAttribute("cycle_unit", assMaintainPlan.getCycle_unit());

		mode.addAttribute("plan_cycle", assMaintainPlan.getPlan_cycle());

		mode.addAttribute("plan_exec_emp", assMaintainPlan.getPlan_exec_emp());

		mode.addAttribute("plan_exec_emp_name", assMaintainPlan.getPlan_exec_emp_name());

		mode.addAttribute("maintain_desc", assMaintainPlan.getMaintain_desc());

		mode.addAttribute("create_emp", assMaintainPlan.getCreate_emp());

		mode.addAttribute("create_date", sdf.format(assMaintainPlan.getCreate_date()));

		if (assMaintainPlan.getAudit_emp() == null) {

			mode.addAttribute("audit_emp", "");

		} else {

			mode.addAttribute("audit_emp", assMaintainPlan.getAudit_emp());

		}

		if (assMaintainPlan.getAudit_date() == null) {

			mode.addAttribute("audit_date", "");

		} else {

			mode.addAttribute("audit_date", sdf.format(assMaintainPlan.getAudit_date()));

		}

		mode.addAttribute("state", assMaintainPlan.getState());

		if (assMaintainPlan.getLast_exec_date() == null) {

			mode.addAttribute("last_exec_date", "");

		} else {

			mode.addAttribute("last_exec_date", sdf.format(assMaintainPlan.getLast_exec_date()));

		}

		if (assMaintainPlan.getNext_exec_date() == null) {

			mode.addAttribute("next_exec_date", "");

		} else {

			mode.addAttribute("next_exec_date", sdf.format(assMaintainPlan.getNext_exec_date()));

		}

		if (assMaintainPlan.getPlan_start_date() == null) {

			mode.addAttribute("plan_start_date", "");

		} else {

			mode.addAttribute("plan_start_date", sdf.format(assMaintainPlan.getPlan_start_date()));

		}

		if (assMaintainPlan.getPlan_end_date() == null) {

			mode.addAttribute("plan_end_date", "");

		} else {

			mode.addAttribute("plan_end_date", sdf.format(assMaintainPlan.getPlan_end_date()));

		}

		if (assMaintainPlan.getStop_date() == null) {

			mode.addAttribute("stop_date", "");

		} else {

			mode.addAttribute("stop_date", sdf.format(assMaintainPlan.getStop_date()));

		}

		if (assMaintainPlan.getStop_emp() == null) {

			mode.addAttribute("stop_emp", "");

		} else {

			mode.addAttribute("stop_emp", assMaintainPlan.getStop_emp());

		}

		return "hrp/ass/assmaintainplan/assMaintainPlanUpdate";
	}

	/**
	 * @Description 更新数据 051202 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/updateAssMaintainPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMaintainPlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("last_exec_date").toString() == "") {

			mapVo.put("last_exec_date", "");

		} else {

			mapVo.put("last_exec_date", DateUtil.stringToDate(mapVo.get("last_exec_date").toString(), "yyyy-MM-dd"));

		}

		if (mapVo.get("next_exec_date").toString() == "") {

			mapVo.put("next_exec_date", "");

		} else {

			mapVo.put("next_exec_date", DateUtil.stringToDate(mapVo.get("next_exec_date").toString(), "yyyy-MM-dd"));

		}

		if (mapVo.get("plan_start_date").toString() == "") {

			mapVo.put("plan_start_date", "");

		} else {

			mapVo.put("plan_start_date", DateUtil.stringToDate(mapVo.get("plan_start_date").toString(), "yyyy-MM-dd"));

		}

		if (mapVo.get("plan_end_date").toString() == "") {

			mapVo.put("plan_end_date", "");

		} else {

			mapVo.put("plan_end_date", DateUtil.stringToDate(mapVo.get("plan_end_date").toString(), "yyyy-MM-dd"));

		}

		if (mapVo.get("stop_date").toString() == "") {

			mapVo.put("stop_date", "");

		} else {

			mapVo.put("stop_date", DateUtil.stringToDate(mapVo.get("stop_date").toString(), "yyyy-MM-dd"));

		}

		if (mapVo.get("stop_emp") == "") {

			mapVo.put("stop_emp", "");

		} else {

			mapVo.put("stop_emp", mapVo.get("stop_emp"));

		}
		try {
			String assMaintainPlanJson = assMaintainPlanService.updateAssMaintainPlan(mapVo);

			return JSONObject.parseObject(assMaintainPlanJson);

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051202 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/addOrUpdateAssMaintainPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMaintainPlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMaintainPlanJson = "";

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {

				detailVo.put("group_id", SessionManager.getGroupId());

			}

			if (detailVo.get("hos_id") == null) {

				detailVo.put("hos_id", SessionManager.getHosId());

			}

			if (detailVo.get("copy_code") == null) {

				detailVo.put("copy_code", SessionManager.getCopyCode());

			}
			try {
				assMaintainPlanJson = assMaintainPlanService.addOrUpdateAssMaintainPlan(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assMaintainPlanJson); 
	}

	/**
	 * @Description 删除数据 051202 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/deleteAssMaintainPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMaintainPlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		String assMaintainPlanJson;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_id", ids[3]);

			if (Integer.parseInt(ids[4]) != 0) {

				flag = false;

				break;

			}
			listVo.add(mapVo);

		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被审核或被终止! \"}");
		}
		
		int list= assMaintainRecService.queryAssMainRecStateList(listVo);
		
		if(list > 0){
			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被保养记录引入! \"}");
		}
		
		try {
			assMaintainPlanItemService.deleteBatchAssMaintainPlanItem(listVo);
			assMaintainPlanJson = assMaintainPlanService.deleteBatchAssMaintainPlan(listVo);
			return JSONObject.parseObject(assMaintainPlanJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051202 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/queryAssMaintainPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainPlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		String assMaintainPlan = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assMaintainPlan = assMaintainPlanService.queryAssMaintainPlan(getPage(mapVo));
			 
		}else{

			assMaintainPlan = assMaintainPlanService.queryDetails(getPage(mapVo));
			 
		}
		
		return JSONObject.parseObject(assMaintainPlan);

	}

	/**
	 * @Description 根据资产性质来选择资产卡片号 下拉框表格
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/choseAssCardNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> choseAssCardNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		mapVo.put("store_id", mapVo.containsKey("store_id") == true ? mapVo.get("store_id") : "");

		mapVo.put("user_dept_id", mapVo.containsKey("user_dept_id") == true ? mapVo.get("user_dept_id") : "");

		String assMaintainPlanJson = "";

		if (("").equals(mapVo.get("ass_nature").toString())) {

			return JSONObject.parseObject("{\"error\":\"请先选择主表的资产性质! \"}");

		}

		if (mapVo.get("ass_nature").equals("02")) {
			assMaintainPlanJson = hrpAssSelectService.queryAssCardSpecial(getPage(mapVo));
		}
		if (mapVo.get("ass_nature").equals("03")) {

		assMaintainPlanJson = hrpAssSelectService.queryAssCardGeneral(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("01")) {

			assMaintainPlanJson = hrpAssSelectService.queryAssCardHouse(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("05")) {

			assMaintainPlanJson = hrpAssSelectService.queryAssCardInassets(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("04")) {

			assMaintainPlanJson = hrpAssSelectService.queryAssCardOther(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("06")) {

			assMaintainPlanJson = hrpAssSelectService.queryAssCardLand(getPage(mapVo));

		}

		return JSONObject.parseObject(assMaintainPlanJson);

	}

	/**
	 * @Description 审核数据 051201 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/auditMaintainPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMaintainPlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			String[] ids = id.split("@");

			Map<String, Object> mapVo = new HashMap<String, Object>();
			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_id", ids[3]);

			AssMaintainPlan assMaintainPlan = assMaintainPlanService.queryAssMaintainPlanByCode(mapVo);

			if (assMaintainPlan.getState() != 0) {
				flag = false;
				break;
			}

			mapVo.put("state", "1");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核! \"}");

		}
		try {
			String assMaintainPlanJson = assMaintainPlanService.updateBatchAssMaintainPlan(listVo);

			return JSONObject.parseObject(assMaintainPlanJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 消审数据 051201 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/backMaintainPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backMaintainPlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_id", ids[3]);

			mapVo.put("state", "0");

			mapVo.put("audit_emp", "");

			mapVo.put("audit_date", "");

			// 判断状态不是审核不能进行消审
			if (!ids[4].equals("1")) {

				flag = false;

				break;

			}

			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"消审失败 状态必须是审核的才能消审! \"}");

		}
		try {
			String assMaintainPlanJson = assMaintainPlanService.updateBatchAssMaintainPlanBack(listVo);

			return JSONObject.parseObject(assMaintainPlanJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 终止计划 051201 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/stopMaintainPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> stopMaintainPlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		for (String id : paramVo.split(",")) {

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_id", ids[3]);

			mapVo.put("state", "2");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("stop_emp", SessionManager.getUserId());

			mapVo.put("stop_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			listVo.add(mapVo);

		}
		try {
			String assMaintainPlanJson = assMaintainPlanService.updateBatchAssMaintainPlanStop(listVo);

			return JSONObject.parseObject(assMaintainPlanJson);

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	/**
	 * 保养计划单状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainplan/queryAssMainTainState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMainTainState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assMaintainPlanService.queryAssMainTainState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"保养计划单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
	
	// 查询保养项目
		@RequestMapping(value = "/hrp/ass/assmaintainplan/queryAssMaintainItem", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssAcceptItem(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			String assMaintainMain = "";

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			assMaintainMain = assMaintainPlanService.queryAssMaintainItem(getPage(mapVo));

			return JSONObject.parseObject(assMaintainMain);
		}
	
		
		// 生成保养计划项目
		@RequestMapping(value = "/hrp/ass/assmaintainplan/buildAssMaintainItem", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> buildAssMaintainItem(@RequestParam Map<String, Object> mapVo, Model model)
				throws Exception {
			String assMaintainMainJson = "";
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			assMaintainMainJson = assMaintainPlanService.buildAssMaintainItem(mapVo);

			return JSONObject.parseObject(assMaintainMainJson);
		}
		
		
		// 保存验收项目
		@RequestMapping(value = "/hrp/ass/assmaintainplan/saveAssMaintainItem", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> saveAssMaintainItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			String assAcceptDetailJson = "";

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("accept_item_data").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());

				if (detailVo.get("item_code") == null || "".equals(detailVo.get("item_code"))) {
					continue;
				}

				detailVo.put("is_normal", Integer.parseInt(detailVo.get("is_normal").toString()));

				listVo.add(detailVo);
			}

			try {
				assAcceptDetailJson = assMaintainPlanService.saveAssMaintainItem(listVo);
			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assAcceptDetailJson);

		}
		
		
		// 删除验收项目
		@RequestMapping(value = "/hrp/ass/assmaintainplan/deleteAssMaintainItem", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssMaintainItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			String assAcceptMainJson;
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				// 表的主键
				mapVo.put("group_id", ids[0]);

				mapVo.put("hos_id", ids[1]);

				mapVo.put("copy_code", ids[2]);

				mapVo.put("plan_id", ids[3]);

				mapVo.put("detail_id", ids[4]);

				mapVo.put("item_code", ids[5]);

				listVo.add(mapVo);
			}
			try {
				                 
				assAcceptMainJson = assMaintainPlanService.deleteAssMaintainItem(listVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assAcceptMainJson);
		}

}
