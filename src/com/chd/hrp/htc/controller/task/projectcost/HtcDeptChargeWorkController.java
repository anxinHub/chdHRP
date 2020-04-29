package com.chd.hrp.htc.controller.task.projectcost;

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
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeWorkService;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcDeptChargeWorkController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcDeptChargeWorkController.class);

	@Resource(name = "htcDeptChargeWorkService")
	private final HtcDeptChargeWorkService htcDeptChargeWorkService = null;


	// 定义页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargework/htcDeptChargeWorkDefineMainPage", method = RequestMethod.GET)
	public String htcDeptChargeWorkDefineMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptchargework/htcDeptChargeWorkDefineMain";

	}
	
	// 查询核算科室对应收费项目
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargework/queryHtcPlanDeptCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcPlanDeptCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeWorkJson = htcDeptChargeWorkService.queryHtcPlanDeptCharge(getPage(mapVo));

		return JSONObject.parseObject(htcDeptChargeWorkJson);

	}
	
	// 查询核算科室对应作业
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargework/queryHtcPlanDeptWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcPlanDeptWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcDeptChargeWorkJson = htcDeptChargeWorkService.queryHtcPlanDeptWork(getPage(mapVo));
		return JSONObject.parseObject(htcDeptChargeWorkJson);
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargework/queryHtcDeptChargeWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptChargeWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeWorkJson = htcDeptChargeWorkService.queryHtcDeptChargeWork(getPage(mapVo));

		return JSONObject.parseObject(htcDeptChargeWorkJson);

	}
	
	// 保存
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargework/saveHtcDeptChargeWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcDeptChargeWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String htcDeptChargeWorkJson = null;
		try {
			
			htcDeptChargeWorkJson = htcDeptChargeWorkService.saveHtcDeptChargeWork(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptChargeWorkJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptChargeWorkJson);
	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargework/deleteHtcDeptChargeWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcDeptChargeWork(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("plan_code", ids[4]);
			mapVo.put("proj_dept_no", ids[5]);
			mapVo.put("proj_dept_id", ids[6]);
			mapVo.put("charge_item_id", ids[7]);
			mapVo.put("work_code", ids[8]);
			listVo.add(mapVo);
		}
		String htcDeptChargeWorkJson = null;
		try {
			htcDeptChargeWorkJson = htcDeptChargeWorkService.deleteBatchHtcDeptChargeWork(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptChargeWorkJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcDeptChargeWorkJson);
	}
		
	
	// 项目作业查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargework/htcDeptChargeWorkMainPage", method = RequestMethod.GET)
	public String deptChargeWorkMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptchargework/htcDeptChargeWorkMain";

	}


}
