package com.chd.hrp.htc.controller.relative.plan.set;

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
import com.chd.hrp.htc.entity.relative.plan.set.HtcRelativePlanSet;
import com.chd.hrp.htc.service.relative.plan.set.HtcRelativePlanSetService;

@Controller
public class HtcRelativePlanSetController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcRelativePlanSetController.class);

	@Resource(name = "htcRelativePlanSetService")
	private final HtcRelativePlanSetService htcRelativePlanSetService = null;
	
    // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/relative/plan/set/htcRelativePlanSetMainPage", method = RequestMethod.GET)
	public String htcRelativePlanSetMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/plan/set/htcRelativePlanSetMain";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/relative/plan/set/htcRelativePlanSetAddPage", method = RequestMethod.GET)
	public String htcRelativePlanSetAddPage(Model mode) throws Exception {

		return "hrp/htc/relative/plan/set/htcRelativePlanSetAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/relative/plan/set/addHtcRelativePlanSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcRelativePlanSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String planSetJson = "";
		
		try {
			planSetJson = htcRelativePlanSetService.addHtcRelativePlanSet(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			planSetJson = "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 AddHtcRelativePlanSet\"}";
		}

		return JSONObject.parseObject(planSetJson);

	}
		
		// 查询
		@RequestMapping(value = "/hrp/htc/relative/plan/set/queryHtcRelativePlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHtcRelativePlanSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());

			String planSet = htcRelativePlanSetService.queryHtcRelativePlanSet(getPage(mapVo));

			return JSONObject.parseObject(planSet);

		}
		
		// 删除
		@RequestMapping(value = "/hrp/htc/relative/plan/set/deleteHtcRelativePlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteHtcRelativePlanSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
			
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
				planSetJson = htcRelativePlanSetService.deleteBatchHtcRelativePlanSet(listVo);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				planSetJson = "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcRelativePlanSet\"}";
			}
			
			return JSONObject.parseObject(planSetJson);

		}
		
		
		// 修改页面跳转
		@RequestMapping(value = "/hrp/htc/relative/plan/set/htcRelativePlanSetUpdatePage", method = RequestMethod.GET)
		public String htcRelativePlanSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			HtcRelativePlanSet htcRelativePlanSet = htcRelativePlanSetService.queryHtcRelativePlanSetByCode(mapVo);
			
			mode.addAttribute("group_id", htcRelativePlanSet.getGroup_id());
			mode.addAttribute("hos_id", htcRelativePlanSet.getHos_id());
			mode.addAttribute("copy_code", htcRelativePlanSet.getCopy_code());
			mode.addAttribute("acc_year", htcRelativePlanSet.getAcc_year());
			mode.addAttribute("plan_code", htcRelativePlanSet.getPlan_code());
			mode.addAttribute("plan_name", htcRelativePlanSet.getPlan_name());
			mode.addAttribute("start_month", htcRelativePlanSet.getStart_month());
			mode.addAttribute("end_month", htcRelativePlanSet.getEnd_month());
			mode.addAttribute("is_check", htcRelativePlanSet.getIs_check());
			mode.addAttribute("menthod", htcRelativePlanSet.getMethod());
			mode.addAttribute("is_current", htcRelativePlanSet.getIs_current());

			return "hrp/htc/relative/plan/set/htcRelativePlanSetUpdate";
		}
		
		// 修改保存
		@RequestMapping(value = "/hrp/htc/relative/plan/set/updateHtcRelativePlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateHtcRelativePlanSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			String planSetJson = "";
			try {
				planSetJson = htcRelativePlanSetService.updateHtcRelativePlanSet(mapVo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				planSetJson = "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 UpdateHtcRelativePlanSet\"}";
			}
			return JSONObject.parseObject(planSetJson);
		}
		
		// 设置科室页面调整
		@RequestMapping(value = "/hrp/htc/relative/plan/set/htcRelativePlanSetDeptPage", method = RequestMethod.GET)
		public String htcRelativePlanSetDeptPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("group_id", mapVo.get("group_id"));
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
			mode.addAttribute("acc_year", mapVo.get("acc_year"));
			mode.addAttribute("plan_code", mapVo.get("plan_code"));
			return "hrp/htc/relative/plan/set/htcRelativePlanSetDept";
		}
	
		
		// 维护页面跳转
		@RequestMapping(value = "/hrp/htc/relative/plan/audit/htcRelativePlanSetAuditMainPage", method = RequestMethod.GET)
		public String relativePlanSetAuditMainPage(Model mode) throws Exception {
	
			return "hrp/htc/relative/plan/audit/htcRelativePlanSetAuditMain";
	
		}
		
		// 查询
		@RequestMapping(value = "/hrp/htc/relative/plan/audit/queryHtcRelativePlanSetAudit", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHtcRelativePlanSetAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
	
			String planSet = htcRelativePlanSetService.queryHtcRelativePlanSetAudit(getPage(mapVo));
	
			return JSONObject.parseObject(planSet);
	
		}
				
		// 审核
		@RequestMapping(value = "/hrp/htc/relative/plan/audit/auditHtcRelativePlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> auditHtcRelativePlanSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
				
				planSetJson = htcRelativePlanSetService.auditHtcRelativePlanSet(listVo);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				planSetJson = e.getMessage();
			}
			
			return JSONObject.parseObject(planSetJson);

		}
		
		// 消审
		@RequestMapping(value = "/hrp/htc/relative/plan/audit/cancelAuditHtcRelativePlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> cancelAuditHtcRelativePlanSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
			
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
				planSetJson = htcRelativePlanSetService.cancelAuditHtcRelativePlanSet(listVo);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				planSetJson = e.getMessage();
			}
			
			return JSONObject.parseObject(planSetJson);

		}
		
		
		// 维护页面跳转
		@RequestMapping(value = "/hrp/htc/relative/plan/history/htcRelativePlanSetHistoryMainPage", method = RequestMethod.GET)
		public String htcRelativePlanSetHistoryMainPage(Model mode) throws Exception {
	
			return "hrp/htc/relative/plan/history/htcRelativePlanSetHistoryMain";
	
		}
		
		//历史方案 查询
		@RequestMapping(value = "/hrp/htc/relative/plan/history/queryHtcRelativePlanHistory", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHtcRelativePlanHistory(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
	
			String planSet = htcRelativePlanSetService.queryHtcRelativePlanHistory(getPage(mapVo));
	
			return JSONObject.parseObject(planSet);
	
		}
}
