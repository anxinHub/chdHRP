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
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptMateRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptMateRelaService;

@Controller
public class HtcDeptMateRelaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcDeptMateRelaController.class);
	
	@Resource(name = "htcDeptMateRelaService")
	private final HtcDeptMateRelaService htcDeptMateRelaService = null;

	@RequestMapping(value = "/hrp/htc/task/projectcost/deptmaterela/htcDeptMateRelaMainPage", method = RequestMethod.GET)
	public String htcDeptMateRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptmaterela/htcDeptMateRelaMain";
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptmaterela/htcDeptMateRelaAddPage", method = RequestMethod.GET)
	public String htcDeptMateRelaAddPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptmaterela/htcDeptMateRelaAdd";
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptmaterela/queryHtcDeptMateRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptMateRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptMateRelaJson = htcDeptMateRelaService.queryHtcDeptMateRela(getPage(mapVo));

		return JSONObject.parseObject(htcDeptMateRelaJson);

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptmaterela/addHtcDeptMateRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptMateRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptMateRelaJson = null;
		try {
			htcDeptMateRelaJson = htcDeptMateRelaService.addHtcDeptMateRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptMateRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptMateRelaJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptmaterela/deleteHtcDeptMateRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcDeptWorkRela(
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
			mapVo.put("mate_code", ids[7]);
			listVo.add(mapVo);
		}
		String htcDeptMateRelaJson = null;
		try {
			htcDeptMateRelaJson = htcDeptMateRelaService.deleteBatchHtcDeptMateRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptMateRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcDeptMateRelaJson);
	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptmaterela/htcDeptMateRelaUpdatePage", method = RequestMethod.GET)
	public String htcDeptMateRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		HtcDeptMateRela htcDeptMateRela = htcDeptMateRelaService.queryHtcDeptMateRelaByCode(mapVo);
		mode.addAttribute("group_id", htcDeptMateRela.getGroup_id());
		mode.addAttribute("hos_id", htcDeptMateRela.getHos_id());
		mode.addAttribute("copy_code", htcDeptMateRela.getCopy_code());
		mode.addAttribute("acc_year", htcDeptMateRela.getAcc_year());
		mode.addAttribute("plan_code", htcDeptMateRela.getPlan_code());
		mode.addAttribute("plan_name", htcDeptMateRela.getPlan_name());
		mode.addAttribute("proj_dept_no", htcDeptMateRela.getProj_dept_no());
		mode.addAttribute("proj_dept_id", htcDeptMateRela.getProj_dept_id());
		mode.addAttribute("proj_dept_code", htcDeptMateRela.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcDeptMateRela.getProj_dept_name());
		mode.addAttribute("mate_code", htcDeptMateRela.getMate_code());
		mode.addAttribute("mate_name", htcDeptMateRela.getMate_name());
		return "hrp/htc/task/projectcost/deptmaterela/htcDeptMateRelaUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptmaterela/updateHtcDeptMateRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcDeptMateRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptMateRelaJson = null;
		try {
			htcDeptMateRelaJson = htcDeptMateRelaService.updateHtcDeptMateRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptMateRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptMateRelaJson);
	}
}
