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
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeWorkDetailService;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcDeptChargeWorkDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcDeptChargeWorkDetailController.class);

	@Resource(name = "htcDeptChargeWorkDetailService")
	private final HtcDeptChargeWorkDetailService htcDeptChargeWorkDetailService = null;


	// 活劳动资源定义页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeworkdetail/htcDeptChargeWorkDetailDefineMainPage", method = RequestMethod.GET)
	public String htcDeptChargeWorkDetailDefineMainPage(Model mode) throws Exception {
		
		return "hrp/htc/task/projectcost/deptchargeworkdetail/htcDeptChargeWorkDetailDefineMain";

	}
	
	//活劳动资源查询页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeworkdetail/htcDeptChargeWorkDetailMainPage", method = RequestMethod.GET)
	public String htcDeptChargeWorkDetailMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptchargeworkdetail/htcDeptChargeWorkDetailMain";

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeworkdetail/queryHtcDeptChargeWorkDetailWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptChargeWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeWorkDetailJson = htcDeptChargeWorkDetailService.queryHtcDeptChargeWorkDetailWork(getPage(mapVo));

		return JSONObject.parseObject(htcDeptChargeWorkDetailJson);

	}	
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeworkdetail/queryHtcDeptChargeWorkDetailTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptChargeWorkDetailTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeWorkDetailJson = htcDeptChargeWorkDetailService.queryHtcDeptChargeWorkDetailTitle(getPage(mapVo));

		return JSONObject.parseObject(htcDeptChargeWorkDetailJson);

	}	
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeworkdetail/queryHtcDeptChargeWorkDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptChargeWorkDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeWorkDetailJson = htcDeptChargeWorkDetailService.queryHtcDeptChargeWorkDetail(getPage(mapVo));

		return JSONObject.parseObject(htcDeptChargeWorkDetailJson);

	}

	
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeworkdetail/saveHtcDeptChargeWorkDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcDeptChargeWorkDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeWorkDetailJson = null;
		try {
			
			htcDeptChargeWorkDetailJson = htcDeptChargeWorkDetailService.saveHtcDeptChargeWorkDetail(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcDeptChargeWorkDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptChargeWorkDetailJson);

	}
	
	
	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeworkdetail/deleteHtcDeptChargeWorkDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptChargeWorkDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("work_code", ids[8]);
			mapVo.put("title_code", ids[9]);
			listVo.add(mapVo);
		}
		
		String htcDeptChargeWorkDetailJson = "";
		
		try {
			
			htcDeptChargeWorkDetailJson = htcDeptChargeWorkDetailService.deleteBatchHtcDeptChargeWorkDetail(listVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptChargeWorkDetailJson = e.getMessage();
		}
		return JSONObject.parseObject(htcDeptChargeWorkDetailJson);

	}
}
