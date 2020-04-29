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
import com.chd.hrp.htc.service.task.readydata.HtcProjectCostPeopleCostDetailService;

@Controller
public class HtcProjectCostPeopleCostDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcProjectCostPeopleCostDetailController.class);
	
	
	@Resource(name = "htcProjectCostPeopleCostDetailService")
	private final HtcProjectCostPeopleCostDetailService htcProjectCostPeopleCostDetailService = null;
	
    /*
     * 主页面跳转
     * */
	@RequestMapping(value = "/hrp/htc/task/readydata/deptpeople/htcTaskProjectCostDeptPeopleCostDetailMainPage", method = RequestMethod.GET)
	public String htcTaskProjectCostDeptPeopleCostDetailMainPage(Model mode) throws Exception {
		return "hrp/htc/task/readydata/deptpeople/htcTaskProjectCostDeptPeopleCostDetailMain";
	}
	
	@RequestMapping(value = "/hrp/htc/task/readydata/deptpeople/disposeHtcTaskProjectCostDeptPeopleCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> disposeHtcTaskProjectCostDeptPeopleCostDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String ProjectCostDeptPeopleCostDetail = "";
		
		try {
			
			ProjectCostDeptPeopleCostDetail = htcProjectCostPeopleCostDetailService.disposeHtcTaskProjectCostDeptPeopleCostDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			ProjectCostDeptPeopleCostDetail = e.getMessage();
		}
	
		return JSONObject.parseObject(ProjectCostDeptPeopleCostDetail);
	
	}
	
	
	// 查询                                     
	@RequestMapping(value = "/hrp/htc/task/readydata/deptpeople/queryHtcTaskProjectCostDeptPeopleCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcTaskProjectCostDeptPeopleCostDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String ProjectCostDeptPeopleCostDetail = htcProjectCostPeopleCostDetailService.queryHtcTaskProjectCostDeptPeopleCostDetail(getPage(mapVo));

		return JSONObject.parseObject(ProjectCostDeptPeopleCostDetail);

	}
}
