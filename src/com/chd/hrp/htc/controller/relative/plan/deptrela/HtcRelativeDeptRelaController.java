package com.chd.hrp.htc.controller.relative.plan.deptrela;

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
import com.chd.hrp.htc.entity.relative.plan.deptrela.HtcRelativeDeptRela;
import com.chd.hrp.htc.service.relative.plan.deptrela.HtcRelativeDeptRelaService;

@Controller
public class HtcRelativeDeptRelaController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcRelativeDeptRelaController.class);
	
	@Resource(name = "htcRelativeDeptRelaService")
	private final HtcRelativeDeptRelaService htcRelativeDeptRelaService = null;
	
	 // 维护页面跳转
	@RequestMapping(value = "/hrp/htc/relative/plan/deptrela/htcRelativeDeptRelaMainPage", method = RequestMethod.GET)
	public String htcRelativePlanSetMainPage(Model mode) throws Exception {

		return "hrp/htc/relative/plan/deptrela/htcRelativeDeptRelaMain";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/relative/plan/deptrela/htcRelativeDeptRelaAddPage", method = RequestMethod.GET)
	public String htcRelativeDeptRelaAddPage(Model mode) throws Exception {

		return "hrp/htc/relative/plan/deptrela/htcRelativeDeptRelaAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/relative/plan/deptrela/addHtcRelativeDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcRelativeDeptRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String deptRelaJson = "";
		
		try {
			
			deptRelaJson = htcRelativeDeptRelaService.addHtcRelativeDeptRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(deptRelaJson);

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/relative/plan/deptrela/queryHtcRelativeDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcRelativeDeptRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String deptRelaJson = htcRelativeDeptRelaService.queryHtcRelativeDeptRela(getPage(mapVo));

		return JSONObject.parseObject(deptRelaJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/relative/plan/deptrela/deleteHtcRelativeDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcRelativeDeptRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
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
			deptRelaJson = htcRelativeDeptRelaService.deleteBatchHtcRelativeDeptRela(listVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptRelaJson);

	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/relative/plan/deptrela/htcRelativeDeptRelaUpdatePage", method = RequestMethod.GET)
	public String htcRelativeDeptRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		HtcRelativeDeptRela htcRelativeDeptRela = htcRelativeDeptRelaService.queryHtcRelativeDeptRelaByCode(mapVo);

		mode.addAttribute("group_id", htcRelativeDeptRela.getGroup_id());
		mode.addAttribute("hos_id", htcRelativeDeptRela.getHos_id());
		mode.addAttribute("copy_code", htcRelativeDeptRela.getCopy_code());
		mode.addAttribute("acc_year", htcRelativeDeptRela.getAcc_year());
		mode.addAttribute("plan_code", htcRelativeDeptRela.getPlan_code());
		mode.addAttribute("plan_name", htcRelativeDeptRela.getPlan_name());
		mode.addAttribute("dept_id", htcRelativeDeptRela.getDept_id());
		mode.addAttribute("dept_no", htcRelativeDeptRela.getDept_no());
		mode.addAttribute("dept_code", htcRelativeDeptRela.getDept_code());
		mode.addAttribute("dept_name", htcRelativeDeptRela.getDept_name());
		mode.addAttribute("proj_dept_id", htcRelativeDeptRela.getProj_dept_id());
		mode.addAttribute("proj_dept_no", htcRelativeDeptRela.getProj_dept_no());
		mode.addAttribute("proj_dept_code", htcRelativeDeptRela.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcRelativeDeptRela.getProj_dept_name());

		return "hrp/htc/relative/plan/deptrela/htcRelativeDeptRelaUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/relative/plan/deptrela/updateHtcRelativeDeptRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcRelativeDeptRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String deptRelaJson = "";
		try {
			deptRelaJson = htcRelativeDeptRelaService.updateHtcRelativeDeptRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptRelaJson = e.getMessage();
		}
		return JSONObject.parseObject(deptRelaJson);
	}
		
}
