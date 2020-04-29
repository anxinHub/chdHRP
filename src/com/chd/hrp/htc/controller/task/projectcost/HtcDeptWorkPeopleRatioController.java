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
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptWorkPeopleRatio;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptWorkPeopleRatioService;

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
public class HtcDeptWorkPeopleRatioController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcDeptWorkPeopleRatioController.class);
	
	
	@Resource(name = "htcDeptWorkPeopleRatioService")
	private final HtcDeptWorkPeopleRatioService htcDeptWorkPeopleRatioService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkpeopleratio/htcDeptWorkPeopleRatioMainPage", method = RequestMethod.GET)
	public String deptWorkPeopleRatioMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptworkpeopleratio/htcDeptWorkPeopleRatioMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkpeopleratio/htcDeptWorkPeopleRatioAddPage", method = RequestMethod.GET)
	public String deptWorkPeopleRatioAddPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptworkpeopleratio/htcDeptWorkPeopleRatioAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkpeopleratio/addHtcDeptWorkPeopleRatio", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeptWorkPeopleRatio(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptWorkPeopleRatioJson = "";
		try {
			deptWorkPeopleRatioJson = htcDeptWorkPeopleRatioService.addHtcDeptWorkPeopleRatio(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptWorkPeopleRatioJson = e.getMessage();
		}

		return JSONObject.parseObject(deptWorkPeopleRatioJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkpeopleratio/queryHtcDeptWorkPeopleRatio", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryDeptWorkPeopleRatio(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptWorkPeopleRatio = htcDeptWorkPeopleRatioService.queryHtcDeptWorkPeopleRatio(getPage(mapVo));

		return JSONObject.parseObject(deptWorkPeopleRatio);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkpeopleratio/deleteHtcDeptWorkPeopleRatio", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptWorkPeopleRatio(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("plan_code", ids[4]);
			mapVo.put("proj_dept_no", ids[5]);
			mapVo.put("proj_dept_id", ids[6]);
			mapVo.put("title_code", ids[7]);
			mapVo.put("work_code", ids[8]); 
            listVo.add(mapVo);
        }
		String deptWorkPeopleRatioJson = "";
		try {
			deptWorkPeopleRatioJson = htcDeptWorkPeopleRatioService.deleteBatchHtcDeptWorkPeopleRatio(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptWorkPeopleRatioJson = e.getMessage();
		}
		return JSONObject.parseObject(deptWorkPeopleRatioJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkpeopleratio/htcDeptWorkPeopleRatioUpdatePage", method = RequestMethod.GET)
	public String deptWorkPeopleRatioUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		HtcDeptWorkPeopleRatio htcDeptWorkPeopleRatio = htcDeptWorkPeopleRatioService.queryHtcDeptWorkPeopleRatioByCode(mapVo);
		mode.addAttribute("group_id", htcDeptWorkPeopleRatio.getGroup_id());
		mode.addAttribute("hos_id", htcDeptWorkPeopleRatio.getHos_id());
		mode.addAttribute("copy_code", htcDeptWorkPeopleRatio.getCopy_code());
		mode.addAttribute("acc_year", htcDeptWorkPeopleRatio.getAcc_year());
		mode.addAttribute("plan_code", htcDeptWorkPeopleRatio.getPlan_code());
		mode.addAttribute("plan_name", htcDeptWorkPeopleRatio.getPlan_name());
		mode.addAttribute("proj_dept_no", htcDeptWorkPeopleRatio.getProj_dept_no());
		mode.addAttribute("proj_dept_id", htcDeptWorkPeopleRatio.getProj_dept_id());
		mode.addAttribute("proj_dept_code", htcDeptWorkPeopleRatio.getProj_dept_code());
		mode.addAttribute("proj_dept_name", htcDeptWorkPeopleRatio.getProj_dept_name());
		mode.addAttribute("title_code", htcDeptWorkPeopleRatio.getTitle_code());
		mode.addAttribute("title_name", htcDeptWorkPeopleRatio.getTitle_name());
		mode.addAttribute("work_code", htcDeptWorkPeopleRatio.getWork_code());
		mode.addAttribute("work_name", htcDeptWorkPeopleRatio.getWork_name());
		mode.addAttribute("oper_percent", htcDeptWorkPeopleRatio.getOper_percent());
		return "hrp/htc/task/projectcost/deptworkpeopleratio/htcDeptWorkPeopleRatioUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptworkpeopleratio/updateHtcDeptWorkPeopleRatio", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeptWorkPeopleRatio(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptWorkPeopleRatioJson = "";
		try {
			deptWorkPeopleRatioJson = htcDeptWorkPeopleRatioService.updateHtcDeptWorkPeopleRatio(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptWorkPeopleRatioJson = e.getMessage();
		}
		
		return JSONObject.parseObject(deptWorkPeopleRatioJson);
	}

}

