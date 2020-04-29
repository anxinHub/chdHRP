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
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptWorkRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptWorkRelaService;

@Controller
public class HtcDeptWorkRelaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcDeptWorkRelaController.class);

	@Resource(name = "htcDeptWorkRelaService")
	private final HtcDeptWorkRelaService htcDeptWorkRelaService = null;
	
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkrela/htcDeptWorkRelaMainPage", method = RequestMethod.GET)
	public String htcDeptWorkRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptworkrela/htcDeptWorkRelaMain";
	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkrela/htcDeptWorkRelaAddPage", method = RequestMethod.GET)
	public String htcDeptWorkRelaAddPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptworkrela/htcDeptWorkRelaAdd";

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkrela/queryHtcDeptWorkRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptWorkRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptWorkRelaJson = htcDeptWorkRelaService.queryHtcDeptWorkRela(getPage(mapVo));

		return JSONObject.parseObject(htcDeptWorkRelaJson);

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkrela/addHtcDeptWorkRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptWorkRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptWorkRelaJson = "";
		try {
			htcDeptWorkRelaJson = htcDeptWorkRelaService.addHtcDeptWorkRela(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptWorkRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptWorkRelaJson);

	}
	
	// 删除
		@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkrela/deleteHtcDeptWorkRela", method = RequestMethod.POST)
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
				mapVo.put("work_code", ids[7]);
				listVo.add(mapVo);
			}
			String htcDeptWorkRelaJson = "";
			try {
				htcDeptWorkRelaJson = htcDeptWorkRelaService.deleteBatchHtcDeptWorkRela(listVo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				htcDeptWorkRelaJson = e.getMessage();
			}
			
			return JSONObject.parseObject(htcDeptWorkRelaJson);

		}
	
		// 修改页面跳转
		@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkrela/htcDeptWorkRelaUpdatePage", method = RequestMethod.GET)
		public String htcDeptWorkRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
			HtcDeptWorkRela htcDeptWorkRela = htcDeptWorkRelaService.queryHtcDeptWorkRelaByCode(mapVo);
			mode.addAttribute("group_id", htcDeptWorkRela.getGroup_id());
			mode.addAttribute("hos_id", htcDeptWorkRela.getHos_id());
			mode.addAttribute("copy_code", htcDeptWorkRela.getCopy_code());
			mode.addAttribute("acc_year", htcDeptWorkRela.getAcc_year());
			mode.addAttribute("plan_code", htcDeptWorkRela.getPlan_code());
			mode.addAttribute("plan_name", htcDeptWorkRela.getPlan_name());
			mode.addAttribute("proj_dept_no", htcDeptWorkRela.getProj_dept_no());
			mode.addAttribute("proj_dept_id", htcDeptWorkRela.getProj_dept_id());
			mode.addAttribute("proj_dept_code", htcDeptWorkRela.getProj_dept_code());
			mode.addAttribute("proj_dept_name", htcDeptWorkRela.getProj_dept_name());
			mode.addAttribute("work_code", htcDeptWorkRela.getWork_code());
			mode.addAttribute("work_name", htcDeptWorkRela.getWork_name());
			return "hrp/htc/task/projectcost/deptworkrela/htcDeptWorkRelaUpdate";
		}

		// 修改保存
		@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkrela/updateHtcDeptWorkRela", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateHtcDeptWorkRela(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String htcDeptWorkRelaJson = "";
			try {
				  htcDeptWorkRelaJson = htcDeptWorkRelaService.updateHtcDeptWorkRela(mapVo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				 htcDeptWorkRelaJson = e.getMessage();
			}

			return JSONObject.parseObject(htcDeptWorkRelaJson);
		}
}
