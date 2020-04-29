package com.chd.hrp.htc.controller.task.plan.set;

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
import com.chd.hrp.htc.entity.task.plan.set.HtcTaskPlanSet;
import com.chd.hrp.htc.service.task.plan.set.HtcTaskPlanSetService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcTaskPlanSetController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcTaskPlanSetController.class);

	@Resource(name = "htcTaskPlanSetService")
	private final HtcTaskPlanSetService htcTaskPlanSetService = null;


	 // 维护页面跳转
		@RequestMapping(value = "/hrp/htc/task/plan/set/htcTaskPlanSetMainPage", method = RequestMethod.GET)
		public String htcTaskPlanSetMainPage(Model mode) throws Exception {

			return "hrp/htc/task/plan/set/htcTaskPlanSetMain";

		}
		
		// 添加页面
		@RequestMapping(value = "/hrp/htc/task/plan/set/htcTaskPlanSetAddPage", method = RequestMethod.GET)
		public String htcTaskPlanSetAddPage(Model mode) throws Exception {

			return "hrp/htc/task/plan/set/htcTaskPlanSetAdd";

		}
		
		// 保存
		@RequestMapping(value = "/hrp/htc/task/plan/set/addHtcTaskPlanSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addHtcTaskPlanSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			String planSetJson = "";
			
			try {
				planSetJson = htcTaskPlanSetService.addHtcTaskPlanSet(mapVo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				planSetJson = "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 AddHtcTaskPlanSet\"}";
			}

			return JSONObject.parseObject(planSetJson);

		}
			
			// 查询
			@RequestMapping(value = "/hrp/htc/task/plan/set/queryHtcTaskPlanSet", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> queryHtcTaskPlanSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("acc_year", SessionManager.getAcctYear());

				String planSet = htcTaskPlanSetService.queryHtcTaskPlanSet(getPage(mapVo));

				return JSONObject.parseObject(planSet);

			}
			
			// 删除
			@RequestMapping(value = "/hrp/htc/task/plan/set/deleteHtcTaskPlanSet", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> deleteHtcTaskPlanSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
				
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
					planSetJson = htcTaskPlanSetService.deleteBatchHtcTaskPlanSet(listVo);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					planSetJson = "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteBatchHtcTaskPlanSet\"}";
				}
				
				return JSONObject.parseObject(planSetJson);

			}
			
			
			// 修改页面跳转
			@RequestMapping(value = "/hrp/htc/task/plan/set/htcTaskPlanSetUpdatePage", method = RequestMethod.GET)
			public String htcTaskPlanSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

				HtcTaskPlanSet htcTaskPlanSet = htcTaskPlanSetService.queryHtcTaskPlanSetByCode(mapVo);
				
				mode.addAttribute("group_id", htcTaskPlanSet.getGroup_id());
				mode.addAttribute("hos_id", htcTaskPlanSet.getHos_id());
				mode.addAttribute("copy_code", htcTaskPlanSet.getCopy_code());
				mode.addAttribute("acc_year", htcTaskPlanSet.getAcc_year());
				mode.addAttribute("plan_code", htcTaskPlanSet.getPlan_code());
				mode.addAttribute("plan_name", htcTaskPlanSet.getPlan_name());
				mode.addAttribute("start_month", htcTaskPlanSet.getStart_month());
				mode.addAttribute("end_month", htcTaskPlanSet.getEnd_month());
				mode.addAttribute("is_check", htcTaskPlanSet.getIs_check());
				mode.addAttribute("menthod", htcTaskPlanSet.getMethod());
				mode.addAttribute("is_current", htcTaskPlanSet.getIs_current());

				return "hrp/htc/task/plan/set/htcTaskPlanSetUpdate";
			}
			
			// 修改保存
			@RequestMapping(value = "/hrp/htc/task/plan/set/updateHtcTaskPlanSet", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> updateHtcTaskPlanSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
				String planSetJson = "";
				try {
					planSetJson = htcTaskPlanSetService.updateHtcTaskPlanSet(mapVo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					planSetJson = "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 UpdateHtcTaskPlanSet\"}";
				}
				return JSONObject.parseObject(planSetJson);
			}
			
			// 设置科室页面调整
			@RequestMapping(value = "/hrp/htc/task/plan/set/htcTaskPlanSetDeptPage", method = RequestMethod.GET)
			public String htcTaskPlanSetDeptPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				mode.addAttribute("group_id", mapVo.get("group_id"));
				mode.addAttribute("hos_id", mapVo.get("hos_id"));
				mode.addAttribute("copy_code", mapVo.get("copy_code"));
				mode.addAttribute("acc_year", mapVo.get("acc_year"));
				mode.addAttribute("plan_code", mapVo.get("plan_code"));
				return "hrp/htc/task/plan/set/htcTaskPlanSetDept";
			}
		
			
			// 维护页面跳转
			@RequestMapping(value = "/hrp/htc/task/plan/audit/htcTaskPlanSetAuditMainPage", method = RequestMethod.GET)
			public String taskPlanSetAuditMainPage(Model mode) throws Exception {
		
				return "hrp/htc/task/plan/audit/htcTaskPlanSetAuditMain";
		
			}
			
			// 查询
			@RequestMapping(value = "/hrp/htc/task/plan/audit/queryHtcTaskPlanSetAudit", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> queryHtcTaskPlanSetAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("acc_year", SessionManager.getAcctYear());
		
				String planSet = htcTaskPlanSetService.queryHtcTaskPlanSetAudit(getPage(mapVo));
		
				return JSONObject.parseObject(planSet);
		
			}
					
			// 审核
			@RequestMapping(value = "/hrp/htc/task/plan/audit/auditHtcTaskPlanSet", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> auditHtcTaskPlanSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
					
					planSetJson = htcTaskPlanSetService.auditHtcTaskPlanSet(listVo);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					planSetJson = e.getMessage();
				}
				
				return JSONObject.parseObject(planSetJson);

			}
			
			// 消审
			@RequestMapping(value = "/hrp/htc/task/plan/audit/cancelAuditHtcTaskPlanSet", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> cancelAuditHtcTaskPlanSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
				
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
					planSetJson = htcTaskPlanSetService.cancelAuditHtcTaskPlanSet(listVo);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					planSetJson = e.getMessage();
				}
				
				return JSONObject.parseObject(planSetJson);

			}
			
			
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/plan/history/htcTaskPlanSetHistoryMainPage", method = RequestMethod.GET)
	public String htcTaskPlanSetHistoryMainPage(Model mode) throws Exception {

		return "hrp/htc/task/plan/history/htcTaskPlanSetHistoryMain";

	}
			
	//历史方案 查询
	@RequestMapping(value = "/hrp/htc/task/plan/history/queryHtcTaskPlanHistory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskPlanHistory(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		

		String planSet = htcTaskPlanSetService.queryHtcTaskPlanHistory(getPage(mapVo));

		return JSONObject.parseObject(planSet);

	}
}
