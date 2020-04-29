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
import com.chd.base.util.StringTool;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptChargeMateRela;
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeMateRelaService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcDeptChargeMateRelaController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcDeptChargeMateRelaController.class);

	@Resource(name = "htcDeptChargeMateRelaService")
	private final HtcDeptChargeMateRelaService htcDeptChargeMateRelaService = null;

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargematerela/htcDeptChargeMateRelaDefineMainPage", method = RequestMethod.GET)
	public String htcDeptChargeMateRelaDefineMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptchargematerela/htcDeptChargeMateRelaDefineMain";

	}
		
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargematerela/htcDeptChargeMateRelaMainPage", method = RequestMethod.GET)
	public String deptChargeMateRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptchargematerela/htcDeptChargeMateRelaMain";

	}


	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargematerela/saveHtcDeptChargeMateRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcDeptChargeMateRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String htcDeptChargeMateRelaJson = "";
		try {
			
			htcDeptChargeMateRelaJson = htcDeptChargeMateRelaService.saveHtcDeptChargeMateRela(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcDeptChargeMateRelaJson = e.getMessage();
		}
		return JSONObject.parseObject(htcDeptChargeMateRelaJson);
		

	}

	// 查询                                                                                                                                                                
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargematerela/queryHtcDeptChargeMateRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptChargeMateRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeMateRelaJson = htcDeptChargeMateRelaService.queryHtcDeptChargeMateRela(getPage(mapVo));

		return JSONObject.parseObject(htcDeptChargeMateRelaJson);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargematerela/deleteHtcDeptChargeMateRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcDeptChargeMateRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			mapVo.put("server_dept_no", ids[8]);
			mapVo.put("server_dept_id", ids[9]);
			mapVo.put("mate_code", ids[10]);
			listVo.add(mapVo);
		}
		String htcDeptChargeMateRelaJson = "";
		try {
			htcDeptChargeMateRelaJson = htcDeptChargeMateRelaService.deleteBatchHtcDeptChargeMateRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptChargeMateRelaJson = e.getMessage();
		}
		return JSONObject.parseObject(htcDeptChargeMateRelaJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargematerela/queryHtcDeptChargeMateRelaCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptChargeMateRelaCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeMateRelaJson = htcDeptChargeMateRelaService.queryHtcDeptChargeMateRelaCharge(getPage(mapVo));
		
		return JSONObject.parseObject(htcDeptChargeMateRelaJson);

	}
		
	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargematerela/queryHtcDeptChargeMateRelaMate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptChargeMateRelaMate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String htcDeptChargeMateRelaJson = htcDeptChargeMateRelaService.queryHtcDeptChargeMateRelaMate(getPage(mapVo));
		
		return JSONObject.parseObject(htcDeptChargeMateRelaJson);
	}

}
