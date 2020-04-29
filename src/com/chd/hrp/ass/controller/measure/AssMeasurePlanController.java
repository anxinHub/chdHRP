/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.measure;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.hrp.ass.entity.measure.AssMeasurePlan;
import com.chd.hrp.ass.service.HrpAssSelectService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.measure.AssMeasurePlanAssService;
import com.chd.hrp.ass.service.measure.AssMeasurePlanService;

/**
 * 
 * @Description: 051204 检查计量计划
 * @Table: ASS_MEASURE_PLAN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0 
 */

@Controller
public class AssMeasurePlanController extends BaseController {

	private static Logger logger = Logger.getLogger(AssMeasurePlanController.class);

	// 引入Service服务assMeasurePlanAssService
	@Resource(name = "assMeasurePlanService")
	private final AssMeasurePlanService assMeasurePlanService = null;
	// 引入Service服务assMeasurePlanAssService
		@Resource(name = "assMeasurePlanAssService")
		private final AssMeasurePlanAssService assMeasurePlanAssService = null;
		@Resource(name="hrpAssSelectService")
		private final HrpAssSelectService hrpAssSelectService=null;
		
		// 引入Service服务
		@Resource(name = "assBaseService")
		private final AssBaseService assBaseService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/assMeasurePlanMainPage", method = RequestMethod.GET)
	public String assMeasurePlanMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05105",MyConfig.getSysPara("05105"));
		
		return "hrp/ass/assmeasureplan/assMeasurePlanMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/assMeasurePlanAddPage", method = RequestMethod.GET)
	public String assMeasurePlanAddPage(Model mode) throws Exception {

		return "hrp/ass/assmeasureplan/assMeasurePlanAdd";

	}
	
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/assMeasurePlanImportPage", method = RequestMethod.GET)
	public String assMeasurePlanAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("ass_nature", mapVo.get("ass_nature"));
		
		return "hrp/ass/assmeasureplan/assMeasurePlanImports";

	}

	/**
	 * @Description 添加数据 051204 检查计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/addAssMeasurePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMeasurePlan(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mapVo.put("create_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
		
		mapVo.put("create_emp", SessionManager.getUserId());

		mapVo.put("state", "0");

		mapVo.put("audit_emp", "");

		mapVo.put("audit_date", "");
		
		/*try {
			String assMeasurePlanJson = assMeasurePlanService.addOrUpdateAssMeasurePlan(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(assMeasurePlanJson);
			String plan_id = (String) jsonObj.get("plan_id");
			String plan_no = (String) jsonObj.get("plan_no");
			String assMeasurePlanAssJson = "";

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());

				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}

				detailVo.put("plan_id", plan_id);

				if (detailVo.get("detail_id") == null) {
					detailVo.put("detail_id", "0");
				}
				if (detailVo.get("plan_exec_date") == null) {

					detailVo.put("plan_exec_date", "");

				} else {

					detailVo.put("plan_exec_date",
							DateUtil.stringToDate(detailVo.get("plan_exec_date").toString(), "yyyy-MM-dd"));

				}
				mapVo.put("create_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
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
				
				assMeasurePlanAssJson = assMeasurePlanAssService.addOrUpdateAssMeasurePlanAss(detailVo);

			}
			JSONObject json = JSONArray.parseObject(assMeasurePlanAssJson);
			json.put("plan_id", plan_id);
			json.put("plan_no", plan_no);
			return JSONObject.parseObject(json.toJSONString());
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}*/
		return JSONObject.parseObject(assMeasurePlanService.addOrUpdateAssMeasurePlan(mapVo));
	}

	/**
	 * @Description 更新跳转页面 051204 检查计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/assMeasurePlanUpdatePage", method = RequestMethod.GET)
	public String assMeasurePlanUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssMeasurePlan assMeasurePlan = new AssMeasurePlan();

		assMeasurePlan = assMeasurePlanService.queryAssMeasurePlanByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assMeasurePlan.getGroup_id());

		mode.addAttribute("hos_id", assMeasurePlan.getHos_id());

		mode.addAttribute("copy_code", assMeasurePlan.getCopy_code());

		mode.addAttribute("plan_id", assMeasurePlan.getPlan_id());

		mode.addAttribute("plan_no", assMeasurePlan.getPlan_no());

		mode.addAttribute("plan_name", assMeasurePlan.getPlan_name());

		mode.addAttribute("plan_year", assMeasurePlan.getPlan_year());

		mode.addAttribute("ass_nature", assMeasurePlan.getAss_nature());

		mode.addAttribute("is_inner", assMeasurePlan.getIs_inner());
		
		mode.addAttribute("outer_measure_org", assMeasurePlan.getOuter_measure_org());
		
		mode.addAttribute("create_emp", assMeasurePlan.getCreate_emp());

		mode.addAttribute("create_date", sdf.format(assMeasurePlan.getCreate_date()));
	//	hrpAssSelectService.q

		if (assMeasurePlan.getAudit_emp() == null) {

			mode.addAttribute("audit_emp", "");

		} else {

			mode.addAttribute("audit_emp", assMeasurePlan.getAudit_emp());

		}

		if (assMeasurePlan.getAudit_date() == null) {

			mode.addAttribute("audit_date", "");

		} else {

			mode.addAttribute("audit_date", sdf.format(assMeasurePlan.getAudit_date()));

		}

		mode.addAttribute("state", assMeasurePlan.getState());

		mode.addAttribute("check_way", assMeasurePlan.getCheck_way());

		mode.addAttribute("note", assMeasurePlan.getNote());
		
		mode.addAttribute("measure_cycle", assMeasurePlan.getMeasure_cycle());

		return "hrp/ass/assmeasureplan/assMeasurePlanUpdate";
	}

	/**
	 * @Description 更新数据 051204 检查计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/updateAssMeasurePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMeasurePlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			String assMeasurePlanJson = assMeasurePlanService.updateAssMeasurePlan(mapVo);

			return JSONObject.parseObject(assMeasurePlanJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051204 检查计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/addOrUpdateAssMeasurePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMeasurePlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMeasurePlanJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			try {
				assMeasurePlanJson = assMeasurePlanService.addOrUpdateAssMeasurePlan(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		return JSONObject.parseObject(assMeasurePlanJson);
	}

	/**
	 * @Description 删除数据 051204 检查计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/deleteAssMeasurePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMeasurePlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		String assMeasurePlanJson;
		String str = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_id", ids[3]);
		/*	str = str + assBaseService.isExistsDataByTable("ASS_MEASURE_PLAN", id.split("@")[3])== null ? ""
					: assBaseService.isExistsDataByTable("ASS_MEASURE_PLAN", id.split("@")[3]);
			if (Strings.isNotBlank(str)) {
				flag = false;
				continue;
			}*/
			
			
			AssMeasurePlan assMeasurePlan = assMeasurePlanService.queryAssMeasurePlanByCode(mapVo);
			if (assMeasurePlan.getState() != 0) {

				flag = false;

				break;

			}

			listVo.add(mapVo);
		
		}
		/*if (!flag) {
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的计划被以下业务使用：【" + str.substring(0, str.length() - 1)
			+ "】。\",\"state\":\"false\"}");
		}*/
		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被审核或被终止! \"}");

		}
		try {
			assMeasurePlanAssService.deleteBatchAssMeasurePlanAss(listVo);
			assMeasurePlanJson = assMeasurePlanService.deleteBatchAssMeasurePlan(listVo);

			return JSONObject.parseObject(assMeasurePlanJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051204 检查计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/queryAssMeasurePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMeasurePlan(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assMeasurePlan = assMeasurePlanService.queryAssMeasurePlan(getPage(mapVo));

		return JSONObject.parseObject(assMeasurePlan);

	}

	/**
	 * @Description 审核数据 051201检查计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/auditMeasurePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMeasurePlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			if (Integer.parseInt(ids[4]) != 0) {

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

			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核!  \"}");

		}
		try {
			String assMeasurePlanJson = assMeasurePlanService.updateBatchAssMeasurePlan(listVo);

			return JSONObject.parseObject(assMeasurePlanJson);

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 消审数据 051201 检查计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/backMeasurePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backMeasurePlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			String assMeasurePlanJson = assMeasurePlanService.updateBatchAssMeasurePlanBack(listVo);

			return JSONObject.parseObject(assMeasurePlanJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 终止计划 051201检查计量计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/stopMeasurePlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> stopMeasurePlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		boolean flag = true;
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
			AssMeasurePlan assMeasurePlan = assMeasurePlanService.queryAssMeasurePlanByCode(mapVo);
			if (assMeasurePlan.getState()==2) {	
				flag = false;

			break;

		}

		listVo.add(mapVo);

	}

	if (flag == false) {

		return JSONObject.parseObject("{\"error\":\"计划终止失败！ 该计划已被被终止! \"}");

	}
		try {
			String assMeasurePlanJson = assMeasurePlanService.updateBatchAssMeasurePlanStop(listVo);

			return JSONObject.parseObject(assMeasurePlanJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	/**
	 * @Description 根据资产性质来选择资产卡片号 下拉框表格
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/choseAssCardNo", method = RequestMethod.POST)
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
		
		
		if(mapVo.get("where") != null){
			JSONObject jb = JSONObject.parseObject(mapVo.get("where").toString());
			JSONArray ja = JSONArray.parseArray(jb.get("rules").toString());
			Iterator iterator = ja.iterator();
			while(iterator.hasNext()){
				JSONObject jo = (JSONObject)iterator.next();
				mapVo.put(jo.get("field").toString(), jo.get("value").toString());
			}
		}
		

		if (mapVo.get("ass_nature").equals("02")) {
			assMaintainPlanJson = assMeasurePlanService.queryAssCardSpecial(getPage(mapVo));
		}
		if (mapVo.get("ass_nature").equals("03")) {

		assMaintainPlanJson = assMeasurePlanService.queryAssCardGeneral(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("01")) {

			assMaintainPlanJson = assMeasurePlanService.queryAssCardHouse(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("05")) {

			assMaintainPlanJson = assMeasurePlanService.queryAssCardInassets(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("04")) {

			assMaintainPlanJson = assMeasurePlanService.queryAssCardOther(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("06")) {

			assMaintainPlanJson = assMeasurePlanService.queryAssCardLand(getPage(mapVo));

		}

		return JSONObject.parseObject(assMaintainPlanJson);

	}
	
	/**
	 * 计量计划单状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasureplan/queryAssMeasurePlanState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMeasurePlanState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assMeasurePlanService.queryAssMeasurePlanState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"计量计划单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}	
}
