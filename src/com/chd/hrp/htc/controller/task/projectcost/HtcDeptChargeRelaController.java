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
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeRelaService;
@Controller
public class HtcDeptChargeRelaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcDeptChargeRelaController.class);
	
	@Resource(name = "htcDeptChargeRelaService")
	private final HtcDeptChargeRelaService htcDeptChargeRelaService = null;
	
	
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargerela/htcDeptChargeRelaMainPage", method = RequestMethod.GET)
	public String htcDeptChargeRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptchargerela/htcDeptChargeRelaMain";
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargerela/htcDeptChargeRelaAddPage", method = RequestMethod.GET)
	public String htcDeptChargeRelaAddPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptchargerela/htcDeptChargeRelaAdd";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargerela/queryHtcDeptChargeRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptChargeRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeRelaJson = htcDeptChargeRelaService.queryHtcDeptChargeRela(getPage(mapVo));

		return JSONObject.parseObject(htcDeptChargeRelaJson);
	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargerela/addHtcDeptChargeRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptChargeRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeRelaJson = "";
		try {
			htcDeptChargeRelaJson = htcDeptChargeRelaService.addHtcDeptChargeRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptChargeRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptChargeRelaJson);
	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargerela/deleteHtcDeptChargeRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcDeptChargeRela(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception {
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
			listVo.add(mapVo);
		}
		String htcDeptChargeRelaJson = "";
		try {
			htcDeptChargeRelaJson = htcDeptChargeRelaService.deleteBatchHtcDeptChargeRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptChargeRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcDeptChargeRelaJson);

	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargerela/htcDeptChargeRelaUpdatePage", method = RequestMethod.GET)
	public String htcDeptWorkRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		HtcDeptChargeRela htcDeptChargeRela = htcDeptChargeRelaService.queryHtcDeptChargeRelaByCode(mapVo);
		mode.addAttribute("group_id", htcDeptChargeRela.getGroup_id());
		mode.addAttribute("hos_id", htcDeptChargeRela.getHos_id());
		mode.addAttribute("copy_code", htcDeptChargeRela.getCopy_code());
		mode.addAttribute("acc_year", htcDeptChargeRela.getAcc_year());
		mode.addAttribute("plan_code", htcDeptChargeRela.getPlan_code());
		mode.addAttribute("plan_name", htcDeptChargeRela.getPlan_name());
		mode.addAttribute("proj_dept_no", htcDeptChargeRela.getProj_dept_no());
		mode.addAttribute("proj_dept_id", htcDeptChargeRela.getProj_dept_id());
		mode.addAttribute("proj_dept_code", htcDeptChargeRela.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcDeptChargeRela.getProj_dept_name());
		mode.addAttribute("charge_item_id", htcDeptChargeRela.getCharge_item_id());
		mode.addAttribute("charge_item_code", htcDeptChargeRela.getCharge_item_code());
		mode.addAttribute("charge_item_name", htcDeptChargeRela.getCharge_item_name());
		return "hrp/htc/task/projectcost/deptchargerela/htcDeptChargeRelaUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargerela/updateHtcDeptChargeRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcDeptChargeRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeRelaJson = "";
		try {
			htcDeptChargeRelaJson = htcDeptChargeRelaService.updateHtcDeptChargeRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptChargeRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptChargeRelaJson);
	}


}
