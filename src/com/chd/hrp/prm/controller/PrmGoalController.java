/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.service.PrmGoalDetailKpiService;
import com.chd.hrp.prm.service.PrmGoalDetailService;
import com.chd.hrp.prm.service.PrmGoalService;

/**
 * 
 * @Description: 目标管理表
 * @Table: PRM_GOAL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmGoalController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmGoalController.class);

	// 引入Service服务
	@Resource(name = "prmGoalService")
	private final PrmGoalService prmGoalService = null;
	
	// 引入Service服务
	@Resource(name = "prmGoalDetailService")
	private final PrmGoalDetailService prmGoalDetailService = null;
	
	// 引入Service服务
	@Resource(name = "prmGoalDetailKpiService")
	private final PrmGoalDetailKpiService prmGoalDetailKpiService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/prmGoalMainPage", method = RequestMethod.GET)
	public String prmGoalMainPage(Model mode) throws Exception {

		return "hrp/prm/prmgoal/prmGoalMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/prmGoalAddPage", method = RequestMethod.GET)
	public String prmGoalAddPage(Model mode) throws Exception {

		return "hrp/prm/prmgoal/prmGoalAdd";

	}

	/**
	 * @Description 添加数据 目标管理表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/addPrmGoal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmGoal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo = defaultValue(mapVo);
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("goal_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("goal_name").toString()));
		
		//PRM_GOAL 0101 目标管理表 数据组织完成
		
		/*Map<String,List<Map<String, Object>>> map_batch = buildGoalDetail(mapVo);
		
		if(map_batch != null ){
			
			mapVo.put("list_goal_detail_batch", map_batch.get("list_goal_detail_batch"));// 组织数据 传递到service 使用Spring事物
			
			mapVo.put("list_goal_detail_kpi_batch", map_batch.get("list_goal_detail_kpi_batch"));// 组织数据 传递到service 使用Spring事物
		}*/
		
		String prmGoalJson = null;
		try {
			
			prmGoalJson = prmGoalService.addPrmGoal(mapVo);
			
		} catch (Exception e) {
			
			prmGoalJson = e.getMessage();
		}

		return JSONObject.parseObject(prmGoalJson);

	}

	public Map<String,List<Map<String, Object>>> buildGoalDetail(Map<String, Object> mapVo) {
		
		Map<String, List<Map<String, Object>>> map_batch = new HashMap<String, List<Map<String, Object>>>();
		
		List<Map<String, Object>> list_goal_detail_batch = new ArrayList<Map<String, Object>>();//存放明细
		
		List<Map<String, Object>> list_goal_detail_kpi_batch = new ArrayList<Map<String, Object>>();//存放明细

		// 解析目标管理明细表数据
		JSONObject kpi_target_data_json = JSONObject.parseObject((String) mapVo.get("kpi_target_data"));

		JSONArray goal_detail_json = JSONArray.parseArray((String) mapVo.get("goal_detail_data"));
		
		if("[{}]".equals(goal_detail_json.toString())){
			
			return null;
		}

		Iterator goal_detail_it = goal_detail_json.iterator();

		try {

			while (goal_detail_it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(goal_detail_it.next().toString());
				
				if(!"{}".equals(jsonObj.toString())){
					
					Map<String, Object> mapDetailVo = defaultDetailValue();
					
					mapDetailVo.put("group_id", mapVo.get("group_id"));
					
					mapDetailVo.put("hos_id", mapVo.get("hos_id"));
					
					mapDetailVo.put("copy_code", mapVo.get("copy_code"));
					
					mapDetailVo.put("goal_code", mapVo.get("goal_code"));
					
					mapDetailVo.put("acc_year", mapVo.get("acc_year"));
					
					
					if(validateJSON(String.valueOf(jsonObj.get("child_goal_note")))){
						
						mapDetailVo.put("child_goal_note",String.valueOf(jsonObj.get("child_goal_note")) );
						
					}
					
					mapDetailVo.put("child_goal_code",  jsonObj.get("child_goal_code"));
					
					mapDetailVo.put("child_goal_name",  jsonObj.get("child_goal_name"));
					
					if(!"{}".equals(kpi_target_data_json)){
						
						String kpi_target_data_json_temp = String.valueOf(kpi_target_data_json.get(jsonObj.get("__id")));
						
						mapDetailVo.put("kpi_target_data_json_temp", kpi_target_data_json_temp);
						
						list_goal_detail_kpi_batch.addAll(buildGoalDetailKpi(mapDetailVo));
						
					}
					list_goal_detail_batch.add(mapDetailVo);
				}
				
			}


		} catch (DataAccessException e) {

		}
		
		map_batch.put("list_goal_detail_batch", list_goal_detail_batch);
		
		map_batch.put("list_goal_detail_kpi_batch", list_goal_detail_kpi_batch);

		return map_batch;
	}
	
	
	public List<Map<String, Object>> buildGoalDetailKpi(Map<String, Object> mapDetailVo){
		
		List<Map<String, Object>> list_goal_detail_kpi_batch = new ArrayList<Map<String, Object>>();//存放KPI指标
		
		String kpi_target_data_json_temp  = (String)mapDetailVo.get("kpi_target_data_json_temp");
		
		if(kpi_target_data_json_temp == null ){
			
			return list_goal_detail_kpi_batch;
		}
		
		JSONArray kpi_target_data_json_object = JSONArray.parseArray(kpi_target_data_json_temp);

		Iterator kpi_target_data_it = kpi_target_data_json_object.iterator();

		while (kpi_target_data_it.hasNext()) {
			
			Map<String, Object> mapDetailKpiVo = defaultDetailKpiValue();
			
			mapDetailKpiVo.put("group_id", mapDetailVo.get("group_id"));

			mapDetailKpiVo.put("hos_id", mapDetailVo.get("hos_id"));

			mapDetailKpiVo.put("copy_code", mapDetailVo.get("copy_code"));
			
			mapDetailKpiVo.put("goal_code", mapDetailVo.get("goal_code"));
			
			mapDetailKpiVo.put("acc_year", mapDetailVo.get("acc_year"));
			
			mapDetailKpiVo.put("child_goal_code", mapDetailVo.get("child_goal_code"));
			
			JSONObject kpi_target_data_obj = JSONObject.parseObject(kpi_target_data_it.next().toString());
			
			mapDetailKpiVo.put("kpi_code", kpi_target_data_obj.get("kpi_code"));
			
			mapDetailKpiVo.put("kpi_name", kpi_target_data_obj.get("kpi_name"));
			
			mapDetailKpiVo.put("nature_code", kpi_target_data_obj.get("nature_code"));
			
			mapDetailKpiVo.put("goal_value", kpi_target_data_obj.get("goal_value"));
			
			if(validateJSON(String.valueOf(kpi_target_data_obj.get("action_note")))){
				
				mapDetailKpiVo.put("action_note",String.valueOf(kpi_target_data_obj.get("action_note")) );
				
			}
	
			list_goal_detail_kpi_batch.add(mapDetailKpiVo);
			
		}
		return list_goal_detail_kpi_batch;
		
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultDetailValue() {

		Map<String, Object> mapDetailVo = new HashMap<String, Object>();

		mapDetailVo.put("group_id", 0);
		
		mapDetailVo.put("hos_id", 0);
		
		mapDetailVo.put("copy_code", "");
		
		mapDetailVo.put("acc_year", "");
		
		mapDetailVo.put("goal_code", "");
		
		mapDetailVo.put("child_goal_code", "");
		
		mapDetailVo.put("child_goal_name", "");

		return mapDetailVo;
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultDetailKpiValue() {

		Map<String, Object> mapDetailKpiVo = new HashMap<String, Object>();

		mapDetailKpiVo.put("group_id", 0);

		mapDetailKpiVo.put("hos_id", 0);

		mapDetailKpiVo.put("copy_code", "");

		mapDetailKpiVo.put("acc_year", "");

		mapDetailKpiVo.put("goal_code", "");

		mapDetailKpiVo.put("child_goal_code", "");
		
		mapDetailKpiVo.put("kpi_code", "");
		
		mapDetailKpiVo.put("kpi_name", "");
		
		mapDetailKpiVo.put("nature_code", "");
		
		mapDetailKpiVo.put("super_kpi_code", "0");
		
		mapDetailKpiVo.put("is_last", 0);
		
		mapDetailKpiVo.put("goal_value", 0);
		
		mapDetailKpiVo.put("action_note", "");

		return mapDetailKpiVo;
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultValue(Map<String, Object> mapVo) {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", "");

		}

		if (mapVo.get("start_month") == null) {

			mapVo.put("start_month", "");

		}

		if (mapVo.get("goal_code") == null) {

			mapVo.put("goal_code", "");

		}

		if (mapVo.get("goal_name") == null) {

			mapVo.put("goal_name", "");

		}

		if (mapVo.get("spell_code") == null) {

			mapVo.put("spell_code", "");

		}

		if (mapVo.get("wbx_code") == null) {

			mapVo.put("wbx_code", "");

		}

		if (mapVo.get("goal_note") == null) {

			mapVo.put("goal_note", "");

		}

		if (mapVo.get("audit_date") == null) {

			mapVo.put("audit_date", "");

		}

		if (mapVo.get("audit_date") == null) {

			mapVo.put("audit_date", "");

		}

		if (mapVo.get("user_code") == null) {

			mapVo.put("user_code", "");

		}

		if (mapVo.get("is_audit") == null) {

			mapVo.put("is_audit", "0");

		}

		return mapVo;
	}

	/**
	 * @Description 更新跳转页面 目标管理表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/prmGoalUpdatePage", method = RequestMethod.GET)
	public String prmGoalUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmGoal prmGoal = new PrmGoal();

		prmGoal = prmGoalService.queryPrmGoalByCode(mapVo);

		mode.addAttribute("goal_code", prmGoal.getGoal_code());
		
		mode.addAttribute("acc_year", prmGoal.getAcc_year());
		
		mode.addAttribute("start_month", prmGoal.getStart_month());
		
		mode.addAttribute("goal_code", prmGoal.getGoal_code());
		
		mode.addAttribute("goal_name", prmGoal.getGoal_name());
		
		mode.addAttribute("goal_note", prmGoal.getGoal_note());
		
		mapVo.put("group_id", prmGoal.getGroup_id());
		
		mapVo.put("hos_id", prmGoal.getHos_id());
		
		mapVo.put("copy_code", prmGoal.getCopy_code());
		
		mapVo.put("goal_code",prmGoal.getGoal_code());
		
		mapVo.put("acc_year",prmGoal.getAcc_year());
		
		String goalDetail = prmGoalDetailService.queryPrmGoalDetail(getPage(mapVo));

		mode.addAttribute("goalDetail",JSONObject.parseObject(goalDetail));

		return "hrp/prm/prmgoal/prmGoalUpdate";
	}
	
	public boolean validateJSON(String str){
		
		if(str !=null  && str!="null" && str!="" && str!="0"){
			
			return true;
			
		}
		
		return false;
		
	}

	/**
	 * @Description 更新数据 目标管理表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/updatePrmGoal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmGoal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		/**
		mapVo = defaultValue(mapVo);

		// 修改 分三种情况 1） 新增一些数据 2）修改已经存在的数据 3)删除数据

		// 情况1 新增子目标数据 执行新增保存明细
		String goal_detail_added = String.valueOf(mapVo.get("goal_detail_added"));
		
		if(goal_detail_added != null){
			
			mapVo.put("goal_detail_data", goal_detail_added);
			
			Map<String, List<Map<String, Object>>> map_batch = buildGoalDetail(mapVo);

			mapVo.put("list_goal_detail_batch", map_batch.get("list_goal_detail_batch"));// 组织数据 传递到service 使用Spring事物

			mapVo.put("list_goal_detail_kpi_batch", map_batch.get("list_goal_detail_kpi_batch"));// 组织数据  传递到service  使用Spring事物
			 
			mapVo.put("add", mapVo);
			
		}
		
		//情况2 获取删除数据 
		
		String goal_detail_deleted = String.valueOf(mapVo.get("goal_detail_deleted"));
		
		if(goal_detail_deleted != null){
			
			mapVo.put("goal_detail_data", goal_detail_deleted);
			
			
			mapVo.put("del", mapVo);
			
		}


		// 情况3 获取修改数据

		String goal_detail_updated = String.valueOf(mapVo.get("goal_detail_updated"));

		if (goal_detail_updated != null) {

			mapVo.put("goal_detail_updated", goal_detail_updated);

			mapVo.put("update", mapVo);

		}

*/
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}
		
		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
  
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("goal_name").toString()));
		
		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("goal_name").toString()));
		/*
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			
		listVo.add(mapVo);*/
		
		String prmGoalJson = null;
		try {
			
			prmGoalJson = prmGoalService.updatePrmGoal(mapVo);
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			prmGoalJson = e.getMessage();
		}
		
		return JSONObject.parseObject(prmGoalJson);
	}

	/**
	 * @Description 删除数据 目标管理表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/deletePrmGoal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmGoal(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("acc_year", ids[3]);
			
			mapVo.put("goal_code", ids[4]);

			listVo.add(mapVo);

		}

		String prmGoalJson = prmGoalService.deleteBatchPrmGoal(listVo);

		return JSONObject.parseObject(prmGoalJson);

	}

	/**
	 * @Description 查询数据 目标管理表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/queryPrmGoalDetailKpiByGoal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmGoalDetailKpiByGoal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		
		String prmGoalDetailKpi = prmGoalDetailKpiService.queryPrmGoalDetailKpi(getPage(mapVo));

		return JSONObject.parseObject(prmGoalDetailKpi);

	}
	
	/**
	 * @Description 查询数据 目标管理表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/queryPrmGoal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmGoal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String prmGoal = prmGoalService.queryPrmGoalHos(getPage(mapVo));

		return JSONObject.parseObject(prmGoal);

	}

	
	/**
	 * @Description 执行保存功能  将数据保存到表PrmGoalDetail  (目标管理明细表)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/updateBatchPrmGoalDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchPrmGoalDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception { 
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String updatePrmGoalDetailJson = null;
		try {
			
			updatePrmGoalDetailJson = prmGoalDetailService.updateBatchPrmGoalDetailList(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			updatePrmGoalDetailJson = e.getMessage();
			
		}
		
		return JSONObject.parseObject(updatePrmGoalDetailJson);

	}
	
	
	/**
	 * @Description 删除数据 PrmGoalDetail (目标管理明细表)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/deletePrmGoalDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmGoalDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		//分割前台传来的参数
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("acc_year", ids[3]);
			
			mapVo.put("goal_code", ids[4]);
			
			mapVo.put("child_goal_code", ids[5]);
			
			listVo.add(mapVo);

		}

		String PrmGoalDetailJson =prmGoalDetailService.deleteBatchPrmGoalDetail(listVo);

		return JSONObject.parseObject(PrmGoalDetailJson);

	}
	
	/**
	 * @Description 执行保存功能  将数据保存到表PrmGoalDetailKpi（目标管理明细指标表）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/updateBatchPrmGoalDetailKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchPrmGoalDetailKpi(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception { 
 
		List<Map<String, Object>> list_goal_detail_kpi_batch = new ArrayList<Map<String, Object>>();//存放明细
		
		//解析前台传来的json串  目标管理明细指标表
		JSONArray goal_detail_kpi_data = JSONArray.parseArray((String) mapVo.get("goal_detail_kpi_data"));
		
		Iterator goal_detail_it =  goal_detail_kpi_data.iterator();

			while (goal_detail_it.hasNext()) {

				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				
				JSONObject jsonObj = JSONObject.parseObject(goal_detail_it.next().toString());
				
				mapDetailVo.put("group_id", jsonObj.get("group_id")); 

				mapDetailVo.put("hos_id", jsonObj.get("hos_id"));

				mapDetailVo.put("copy_code", jsonObj.get("copy_code"));
				 
				mapDetailVo.put("kpi_code", jsonObj.get("kpi_code"));
				
				mapDetailVo.put("kpi_name", jsonObj.get("kpi_name"));
				
				if(jsonObj.get("nature_code") != null){
					
					mapDetailVo.put("nature_code", jsonObj.get("nature_code"));
				}else{
					
					mapDetailVo.put("nature_code", "01");
				}
				
				mapDetailVo.put("super_kpi_code", "0");
				
				mapDetailVo.put("is_last",0);
 
				if (jsonObj.get("goal_value")==null){
					
					mapDetailVo.put("goal_value", 0);
					
				} else if (jsonObj.get("goal_value").equals("0"))
				{ 

					mapDetailVo.put("goal_value", 0);
				}
				else {
					
					mapDetailVo.put("goal_value", jsonObj.get("goal_value"));
				}
				
				if (jsonObj.get("action_note")==null){
					
					mapDetailVo.put("action_note", "0");
					
				} else if (jsonObj.get("action_note").equals("0")) {
					
					
					mapDetailVo.put("action_note", "0");
					
					
				}
				else {
					mapDetailVo.put("action_note", jsonObj.get("action_note"));
				}
				
				mapDetailVo.put("acc_year", mapVo.get("acc_year"));
				
				mapDetailVo.put("goal_code", mapVo.get("goal_code"));
				
				mapDetailVo.put("child_goal_code", mapVo.get("child_goal_code"));
				 
				list_goal_detail_kpi_batch.add(mapDetailVo);
				
			}
			
		String PrmGoalDetailKpiJson  = null;
		try {
			
			PrmGoalDetailKpiJson = prmGoalDetailKpiService.addBatchPrmGoalDetailKpi(list_goal_detail_kpi_batch);
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			PrmGoalDetailKpiJson = e.getMessage();
		}
			//执行添加方法    先删除 后添加

		return JSONObject.parseObject(PrmGoalDetailKpiJson);

	}
	
	
	/**
	 * @Description 删除数据 PrmGoalDetailKpi (目标管理明细指标表)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmgoal/deletePrmGoalDetailKpi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmGoalDetailKpi(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		//分割前台传的参数
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("acc_year", ids[3]);
			
			mapVo.put("goal_code", ids[4]);
			
			mapVo.put("child_goal_code", ids[5]);
			
			mapVo.put("kpi_code", ids[6]);
			
			listVo.add(mapVo);

		}

		String PrmGoalDetailKpiJson =prmGoalDetailKpiService.deleteBatchPrmGoalDetailKpi(listVo);

		return JSONObject.parseObject(PrmGoalDetailKpiJson);

	}
	

}
