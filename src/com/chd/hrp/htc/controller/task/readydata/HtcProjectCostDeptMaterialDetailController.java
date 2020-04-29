package com.chd.hrp.htc.controller.task.readydata;

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
import com.chd.hrp.htc.service.task.readydata.HtcProjectCostDeptMaterialDetailService;

@Controller
public class HtcProjectCostDeptMaterialDetailController  extends BaseController{

	private static Logger logger = Logger.getLogger(HtcProjectCostDeptMaterialDetailController.class);
	
	@Resource(name = "htcProjectCostDeptMaterialDetailService")
	private final HtcProjectCostDeptMaterialDetailService htcProjectCostDeptMaterialDetailService = null;
    /*
     * 主页面跳转
     * */
	@RequestMapping(value = "/hrp/htc/task/readydata/deptmate/htcTaskProjectCostDeptMaterialCostDetailMainPage", method = RequestMethod.GET)
	public String htcTaskProjectCostDeptMaterialCostDetailMainPage(Model mode) throws Exception {
		return "hrp/htc/task/readydata/deptmate/htcTaskProjectCostDeptMaterialCostDetailMain";
	}
	
	
	@RequestMapping(value = "/hrp/htc/task/readydata/deptmate/disposeHtcTaskProjectCostDeptMaterialCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disposeHtcTaskProjectCostDeptMaterialCostDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String ProjectCostDeptMaterialCostDetail = "";
		
		try {
			
			ProjectCostDeptMaterialCostDetail = htcProjectCostDeptMaterialDetailService.disposeHtcTaskProjectCostDeptMaterialCostDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ProjectCostDeptMaterialCostDetail = e.getMessage();
		}
	
		return JSONObject.parseObject(ProjectCostDeptMaterialCostDetail);
	
	}
	
	
	// 查询                                     
	@RequestMapping(value = "/hrp/htc/task/readydata/deptmate/queryHtcTaskProjectCostDeptMaterialCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskProjectCostDeptMaterialCostDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String ProjectCostDeptMaterialCostDetail = htcProjectCostDeptMaterialDetailService.queryHtcTaskProjectCostDeptMaterialCostDetail(getPage(mapVo));

		return JSONObject.parseObject(ProjectCostDeptMaterialCostDetail);

	}
}
