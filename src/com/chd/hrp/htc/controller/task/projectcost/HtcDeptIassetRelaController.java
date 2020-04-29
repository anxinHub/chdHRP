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
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptIassetRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptIassetRelaService;

@Controller
public class HtcDeptIassetRelaController  extends BaseController{

	private static Logger logger = Logger.getLogger(HtcDeptIassetRelaController.class);
	
	@Resource(name = "htcDeptIassetRelaService")
	private final HtcDeptIassetRelaService htcDeptIassetRelaService = null;
	
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptiassetrela/htcDeptIassetRelaMainPage", method = RequestMethod.GET)
	public String htcDeptIassetRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptiassetrela/htcDeptIassetRelaMain";
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptiassetrela/htcDeptIassetRelaAddPage", method = RequestMethod.GET)
	public String htcDeptIassetRelaAddPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptiassetrela/htcDeptIassetRelaAdd";
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptiassetrela/queryHtcDeptIassetRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptIassetRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptIassetRelaJson = htcDeptIassetRelaService.queryHtcDeptIassetRela(getPage(mapVo));

		return JSONObject.parseObject(htcDeptIassetRelaJson);

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptiassetrela/addHtcDeptIassetRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptIassetRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptIassetRelaJson = null;
		try {
			htcDeptIassetRelaJson = htcDeptIassetRelaService.addHtcDeptIassetRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptIassetRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptIassetRelaJson);

	}
	
	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptiassetrela/deleteHtcDeptIassetRela", method = RequestMethod.POST)
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
		String htcDeptIassetRelaJson = null;
		try {
			htcDeptIassetRelaJson = htcDeptIassetRelaService.deleteBatchHtcDeptIassetRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptIassetRelaJson = e.getMessage();
		}
		
		return JSONObject.parseObject(htcDeptIassetRelaJson);
	}
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptiassetrela/htcDeptIassetRelaUpdatePage", method = RequestMethod.GET)
	public String htcDeptIassetRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		HtcDeptIassetRela htcDeptIassetRela = htcDeptIassetRelaService.queryHtcDeptIassetRelaByCode(mapVo);
		mode.addAttribute("group_id", htcDeptIassetRela.getGroup_id());
		mode.addAttribute("hos_id", htcDeptIassetRela.getHos_id());
		mode.addAttribute("copy_code", htcDeptIassetRela.getCopy_code());
		mode.addAttribute("acc_year", htcDeptIassetRela.getAcc_year());
		mode.addAttribute("plan_code", htcDeptIassetRela.getPlan_code());
		mode.addAttribute("plan_name", htcDeptIassetRela.getPlan_name());
		mode.addAttribute("proj_dept_no", htcDeptIassetRela.getProj_dept_no());
		mode.addAttribute("proj_dept_id", htcDeptIassetRela.getProj_dept_id());
		mode.addAttribute("proj_dept_code", htcDeptIassetRela.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcDeptIassetRela.getProj_dept_name());
		mode.addAttribute("asset_code", htcDeptIassetRela.getAsset_code());
		mode.addAttribute("asset_name", htcDeptIassetRela.getAsset_name());
		return "hrp/htc/task/projectcost/deptiassetrela/htcDeptIassetRelaUpdate";
	}
	
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptiassetrela/updateHtcDeptIassetRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcDeptIassetRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptIassetRelaJson = null;
		try {
			htcDeptIassetRelaJson = htcDeptIassetRelaService.updateHtcDeptIassetRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptIassetRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptIassetRelaJson);
	}
}
