package com.chd.hrp.htc.controller.task.plan.deptrela;
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
import com.chd.hrp.htc.entity.task.plan.deptrela.HtcTaskDeptRela;
import com.chd.hrp.htc.service.task.plan.deptrela.HtcTaskDeptRelaService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcTaskDeptRelaController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcTaskDeptRelaController.class);
	
	
	@Resource(name = "htcTaskDeptRelaService")
	private final HtcTaskDeptRelaService htcTaskDeptRelaService = null;
    
	
	 // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/plan/deptrela/htcTaskDeptRelaMainPage", method = RequestMethod.GET)
	public String htcTaskPlanSetMainPage(Model mode) throws Exception {

		return "hrp/htc/task/plan/deptrela/htcTaskDeptRelaMain";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/plan/deptrela/htcTaskDeptRelaAddPage", method = RequestMethod.GET)
	public String htcTaskDeptRelaAddPage(Model mode) throws Exception {

		return "hrp/htc/task/plan/deptrela/htcTaskDeptRelaAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/plan/deptrela/addHtcTaskDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcTaskDeptRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String deptRelaJson = "";
		
		try {
			
			deptRelaJson = htcTaskDeptRelaService.addHtcTaskDeptRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(deptRelaJson);

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/plan/deptrela/queryHtcTaskDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskDeptRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String deptRelaJson = htcTaskDeptRelaService.queryHtcTaskDeptRela(getPage(mapVo));

		return JSONObject.parseObject(deptRelaJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/task/plan/deptrela/deleteHtcTaskDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcTaskDeptRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("plan_code", ids[4]);
			mapVo.put("dept_id", ids[5]);
			listVo.add(mapVo);
		}
		
		String deptRelaJson = "";
		
		try {
			deptRelaJson = htcTaskDeptRelaService.deleteBatchHtcTaskDeptRela(listVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptRelaJson);

	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/plan/deptrela/htcTaskDeptRelaUpdatePage", method = RequestMethod.GET)
	public String htcTaskDeptRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		HtcTaskDeptRela htcTaskDeptRela = htcTaskDeptRelaService.queryHtcTaskDeptRelaByCode(mapVo);

		mode.addAttribute("group_id", htcTaskDeptRela.getGroup_id());
		mode.addAttribute("hos_id", htcTaskDeptRela.getHos_id());
		mode.addAttribute("copy_code", htcTaskDeptRela.getCopy_code());
		mode.addAttribute("acc_year", htcTaskDeptRela.getAcc_year());
		mode.addAttribute("plan_code", htcTaskDeptRela.getPlan_code());
		mode.addAttribute("plan_name", htcTaskDeptRela.getPlan_name());
		mode.addAttribute("dept_id", htcTaskDeptRela.getDept_id());
		mode.addAttribute("dept_no", htcTaskDeptRela.getDept_no());
		mode.addAttribute("dept_code", htcTaskDeptRela.getDept_code());
		mode.addAttribute("dept_name", htcTaskDeptRela.getDept_name());
		mode.addAttribute("proj_dept_id", htcTaskDeptRela.getProj_dept_id());
		mode.addAttribute("proj_dept_no", htcTaskDeptRela.getProj_dept_no());
		mode.addAttribute("proj_dept_code", htcTaskDeptRela.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcTaskDeptRela.getProj_dept_name());

		return "hrp/htc/task/plan/deptrela/htcTaskDeptRelaUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/plan/deptrela/updateHtcTaskDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcTaskDeptRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String deptRelaJson = "";
		try {
			deptRelaJson = htcTaskDeptRelaService.updateHtcTaskDeptRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptRelaJson = e.getMessage();
		}
		return JSONObject.parseObject(deptRelaJson);
	}

}

