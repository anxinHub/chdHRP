package com.chd.hrp.htc.controller.income.plan.deptrela;

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
import com.chd.hrp.htc.entity.income.plan.deptrela.HtcIncomeDeptRela;
import com.chd.hrp.htc.entity.income.plan.set.HtcIncomePlanSet;
import com.chd.hrp.htc.service.income.plan.deptrela.HtcIncomeDeptRelaService;

@Controller
public class HtcIncomeDeptRelaController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcIncomeDeptRelaController.class);
	
	@Resource(name = "htcIncomeDeptRelaService")
	private final HtcIncomeDeptRelaService htcIncomeDeptRelaService = null;
	
	 // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/income/plan/deptrela/htcIncomeDeptRelaMainPage", method = RequestMethod.GET)
	public String htcIncomePlanSetMainPage(Model mode) throws Exception {

		return "hrp/htc/income/plan/deptrela/htcIncomeDeptRelaMain";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/income/plan/deptrela/htcIncomeDeptRelaAddPage", method = RequestMethod.GET)
	public String htcIncomeDeptRelaAddPage(Model mode) throws Exception {

		return "hrp/htc/income/plan/deptrela/htcIncomeDeptRelaAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/income/plan/deptrela/addHtcIncomeDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcIncomeDeptRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String deptRelaJson = "";
		
		try {
			
			deptRelaJson = htcIncomeDeptRelaService.addHtcIncomeDeptRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(deptRelaJson);

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/income/plan/deptrela/queryHtcIncomeDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcIncomeDeptRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String deptRelaJson = htcIncomeDeptRelaService.queryHtcIncomeDeptRela(getPage(mapVo));

		return JSONObject.parseObject(deptRelaJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/income/plan/deptrela/deleteHtcIncomeDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcIncomeDeptRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
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
			deptRelaJson = htcIncomeDeptRelaService.deleteBatchHtcIncomeDeptRela(listVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptRelaJson);

	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/income/plan/deptrela/htcIncomeDeptRelaUpdatePage", method = RequestMethod.GET)
	public String htcIncomeDeptRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		HtcIncomeDeptRela htcIncomeDeptRela = htcIncomeDeptRelaService.queryHtcIncomeDeptRelaByCode(mapVo);

		mode.addAttribute("group_id", htcIncomeDeptRela.getGroup_id());
		mode.addAttribute("hos_id", htcIncomeDeptRela.getHos_id());
		mode.addAttribute("copy_code", htcIncomeDeptRela.getCopy_code());
		mode.addAttribute("acc_year", htcIncomeDeptRela.getAcc_year());
		mode.addAttribute("plan_code", htcIncomeDeptRela.getPlan_code());
		mode.addAttribute("plan_name", htcIncomeDeptRela.getPlan_name());
		mode.addAttribute("dept_id", htcIncomeDeptRela.getDept_id());
		mode.addAttribute("dept_no", htcIncomeDeptRela.getDept_no());
		mode.addAttribute("dept_code", htcIncomeDeptRela.getDept_code());
		mode.addAttribute("dept_name", htcIncomeDeptRela.getDept_name());
		mode.addAttribute("proj_dept_id", htcIncomeDeptRela.getProj_dept_id());
		mode.addAttribute("proj_dept_no", htcIncomeDeptRela.getProj_dept_no());
		mode.addAttribute("proj_dept_code", htcIncomeDeptRela.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcIncomeDeptRela.getProj_dept_name());

		return "hrp/htc/income/plan/deptrela/htcIncomeDeptRelaUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/income/plan/deptrela/updateHtcIncomeDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcIncomeDeptRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String deptRelaJson = "";
		try {
			deptRelaJson = htcIncomeDeptRelaService.updateHtcIncomeDeptRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptRelaJson = e.getMessage();
		}
		return JSONObject.parseObject(deptRelaJson);
	}
		
}
