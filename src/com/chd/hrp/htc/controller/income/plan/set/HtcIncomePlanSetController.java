package com.chd.hrp.htc.controller.income.plan.set;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.htc.entity.income.plan.set.HtcIncomePlanSet;
import com.chd.hrp.htc.service.income.plan.set.HtcIncomePlanSetService;

@Controller
public class HtcIncomePlanSetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcIncomePlanSetController.class);

	@Resource(name = "htcIncomePlanSetService")
	private final HtcIncomePlanSetService htcIncomePlanSetService = null;
	
    // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/income/plan/set/htcIncomePlanSetMainPage", method = RequestMethod.GET)
	public String htcIncomePlanSetMainPage(Model mode) throws Exception {

		return "hrp/htc/income/plan/set/htcIncomePlanSetMain";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/income/plan/set/htcIncomePlanSetAddPage", method = RequestMethod.GET)
	public String htcIncomePlanSetAddPage(Model mode) throws Exception {

		return "hrp/htc/income/plan/set/htcIncomePlanSetAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/income/plan/set/addHtcIncomePlanSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIncomePlanSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String planSetJson = "";
		
		try {
			planSetJson = htcIncomePlanSetService.addHtcIncomePlanSet(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			planSetJson = "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 AddHtcIncomePlanSet\"}";
		}

		return JSONObject.parseObject(planSetJson);

	}
		
		// 查询
		@RequestMapping(value = "/hrp/htc/income/plan/set/queryHtcIncomePlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHtcIncomePlanSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());

			String planSet = htcIncomePlanSetService.queryHtcIncomePlanSet(getPage(mapVo));

			return JSONObject.parseObject(planSet);

		}
		
		// 删除
		@RequestMapping(value = "/hrp/htc/income/plan/set/deleteHtcIncomePlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteHtcIncomePlanSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
			
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("acc_year", ids[3]);
				mapVo.put("plan_code", ids[4]);
				listVo.add(mapVo);
			}
			
			String planSetJson = "";
			
			try {
				planSetJson = htcIncomePlanSetService.deleteBatchHtcIncomePlanSet(listVo);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				planSetJson = "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcIncomePlanSet\"}";
			}
			
			return JSONObject.parseObject(planSetJson);

		}
		
		
		// 修改页面跳转
		@RequestMapping(value = "/hrp/htc/income/plan/set/htcIncomePlanSetUpdatePage", method = RequestMethod.GET)
		public String htcIncomePlanSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			HtcIncomePlanSet htcIncomePlanSet = htcIncomePlanSetService.queryHtcIncomePlanSetByCode(mapVo);
			
			mode.addAttribute("group_id", htcIncomePlanSet.getGroup_id());
			mode.addAttribute("hos_id", htcIncomePlanSet.getHos_id());
			mode.addAttribute("copy_code", htcIncomePlanSet.getCopy_code());
			mode.addAttribute("acc_year", htcIncomePlanSet.getAcc_year());
			mode.addAttribute("plan_code", htcIncomePlanSet.getPlan_code());
			mode.addAttribute("plan_name", htcIncomePlanSet.getPlan_name());
			mode.addAttribute("start_month", htcIncomePlanSet.getStart_month());
			mode.addAttribute("end_month", htcIncomePlanSet.getEnd_month());
			mode.addAttribute("is_check", htcIncomePlanSet.getIs_check());
			mode.addAttribute("menthod", htcIncomePlanSet.getMethod());
			mode.addAttribute("is_current", htcIncomePlanSet.getIs_current());

			return "hrp/htc/income/plan/set/htcIncomePlanSetUpdate";
		}
		
		// 修改保存
		@RequestMapping(value = "/hrp/htc/income/plan/set/updateHtcIncomePlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateHtcIncomePlanSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			String planSetJson = "";
			try {
				planSetJson = htcIncomePlanSetService.updateHtcIncomePlanSet(mapVo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				planSetJson = "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 UpdateHtcIncomePlanSet\"}";
			}
			return JSONObject.parseObject(planSetJson);
		}
		
		// 设置科室页面调整
		@RequestMapping(value = "/hrp/htc/income/plan/set/htcIncomePlanSetDeptPage", method = RequestMethod.GET)
		public String htcIncomePlanSetDeptPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("acc_year", mapVo.get("acc_year"));
			mode.addAttribute("plan_code", mapVo.get("plan_code"));
			return "hrp/htc/income/plan/set/htcIncomePlanSetDept";
		}
	
		
		// 维护页面跳转
		@RequestMapping(value = "/hrp/htc/income/plan/audit/htcIncomePlanSetAuditMainPage", method = RequestMethod.GET)
		public String incomePlanSetAuditMainPage(Model mode) throws Exception {
	
			return "hrp/htc/income/plan/audit/htcIncomePlanSetAuditMain";
	
		}
		
		// 查询
		@RequestMapping(value = "/hrp/htc/income/plan/audit/queryHtcIncomePlanSetAudit", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHtcIncomePlanSetAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
	
			String planSet = htcIncomePlanSetService.queryHtcIncomePlanSetAudit(getPage(mapVo));
	
			return JSONObject.parseObject(planSet);
	
		}
				
		// 审核
		@RequestMapping(value = "/hrp/htc/income/plan/audit/auditHtcIncomePlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> auditHtcIncomePlanSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("acc_year", ids[3]);
				mapVo.put("plan_code", ids[4]);
				listVo.add(mapVo);
			}
			String planSetJson = "";
			
			try {
				
				planSetJson = htcIncomePlanSetService.auditHtcIncomePlanSet(listVo);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				planSetJson = e.getMessage();
			}
			
			return JSONObject.parseObject(planSetJson);

		}
		
		// 消审
		@RequestMapping(value = "/hrp/htc/income/plan/audit/cancelAuditIncomeHtcPlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> cancelAuditIncomeHtcPlanSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
			
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("acc_year", ids[3]);
				mapVo.put("plan_code", ids[4]);
				listVo.add(mapVo);
			}
			
			String planSetJson = "";
			
			try {
				planSetJson = htcIncomePlanSetService.cancelAuditIncomeHtcPlanSet(listVo);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				planSetJson = e.getMessage();
			}
			
			return JSONObject.parseObject(planSetJson);

		}
		
		
		// 维护页面跳转
		@RequestMapping(value = "/hrp/htc/income/plan/history/htcIncomePlanSetHistoryMainPage", method = RequestMethod.GET)
		public String htcIncomePlanSetHistoryMainPage(Model mode) throws Exception {
	
			return "hrp/htc/income/plan/history/htcIncomePlanSetHistoryMain";
	
		}
		
		//历史方案 查询
		@RequestMapping(value = "/hrp/htc/income/plan/history/queryHtcIncomePlanHistory", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHtcIncomePlanHistory(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
	
			String planSet = htcIncomePlanSetService.queryHtcIncomePlanHistory(getPage(mapVo));
	
			return JSONObject.parseObject(planSet);
	
		}
}
