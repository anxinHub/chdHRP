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
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptFassetRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptFassetRelaService;

@Controller
public class HtcDeptFassetRelaController extends BaseController{

	private static Logger logger = Logger.getLogger(HtcDeptFassetRelaController.class);
	
	@Resource(name = "htcDeptFassetRelaService")
	private final HtcDeptFassetRelaService htcDeptFassetRelaService = null;
	
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptfassetrela/htcDeptFassetRelaMainPage", method = RequestMethod.GET)
	public String htcDeptFassetRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptfassetrela/htcDeptFassetRelaMain";
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptfassetrela/htcDeptFassetRelaAddPage", method = RequestMethod.GET)
	public String htcDeptFassetRelaAddPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptfassetrela/htcDeptFassetRelaAdd";
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptfassetrela/queryHtcDeptFassetRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptFassetRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptFassetRelaJson = htcDeptFassetRelaService.queryHtcDeptFassetRela(getPage(mapVo));

		return JSONObject.parseObject(htcDeptFassetRelaJson);

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptfassetrela/addHtcDeptFassetRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptFassetRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptFassetRelaJson = null;
		try {
			htcDeptFassetRelaJson = htcDeptFassetRelaService.addHtcDeptFassetRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptFassetRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptFassetRelaJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptfassetrela/deleteHtcDeptFassetRela", method = RequestMethod.POST)
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
			mapVo.put("asset_code", ids[7]);
			listVo.add(mapVo);
		}
		String htcDeptFassetRelaJson = null;
		try {
			htcDeptFassetRelaJson = htcDeptFassetRelaService.deleteBatchHtcDeptFassetRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptFassetRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcDeptFassetRelaJson);
	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptfassetrela/htcDeptFassetRelaUpdatePage", method = RequestMethod.GET)
	public String htcDeptFassetRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		HtcDeptFassetRela htcDeptFassetRela = htcDeptFassetRelaService.queryHtcDeptFassetRelaByCode(mapVo);
		mode.addAttribute("group_id", htcDeptFassetRela.getGroup_id());
		mode.addAttribute("hos_id", htcDeptFassetRela.getHos_id());
		mode.addAttribute("copy_code", htcDeptFassetRela.getCopy_code());
		mode.addAttribute("acc_year", htcDeptFassetRela.getAcc_year());
		mode.addAttribute("plan_code", htcDeptFassetRela.getPlan_code());
		mode.addAttribute("plan_name", htcDeptFassetRela.getPlan_name());
		mode.addAttribute("proj_dept_no", htcDeptFassetRela.getProj_dept_no());
		mode.addAttribute("proj_dept_id", htcDeptFassetRela.getProj_dept_id());
		mode.addAttribute("proj_dept_code", htcDeptFassetRela.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcDeptFassetRela.getProj_dept_name());
		mode.addAttribute("asset_code", htcDeptFassetRela.getAsset_code());
		mode.addAttribute("asset_name", htcDeptFassetRela.getAsset_name());
		return "hrp/htc/task/projectcost/deptfassetrela/htcDeptFassetRelaUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptfassetrela/updateHtcDeptFassetRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcDeptFassetRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptFassetRelaJson = null;
		try {
			htcDeptFassetRelaJson = htcDeptFassetRelaService.updateHtcDeptFassetRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptFassetRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptFassetRelaJson);
	}
}
