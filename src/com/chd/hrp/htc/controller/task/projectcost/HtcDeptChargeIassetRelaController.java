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
import com.chd.hrp.htc.service.task.projectcost.HtcDeptChargeIassetRelaService;

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
public class HtcDeptChargeIassetRelaController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcDeptChargeIassetRelaController.class);

	@Resource(name = "htcDeptChargeIassetRelaService")
	private final HtcDeptChargeIassetRelaService htcDeptChargeIassetRelaService = null;

	/*
	 * 无形资产资源定义页面跳转
	 * */
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeiassetrela/htcDeptChargeIassetRelaDefineMainPage", method = RequestMethod.GET)
	public String htcDeptChargeIassetRelaDefineMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptchargeiassetrela/htcDeptChargeIassetRelaDefineMain";

	}

	/*
	 * 无形资产资源查询页面跳转
	 * */
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeiassetrela/htcDeptChargeIassetRelaMainPage", method = RequestMethod.GET)
	public String htcDeptChargeIassetRelaMainPage(Model mode) throws Exception {
		return "hrp/htc/task/projectcost/deptchargeiassetrela/htcDeptChargeIassetRelaMain";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeiassetrela/saveHtcDeptChargeIassetRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcDeptChargeIassetRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String htcDeptChargeIassetRelaJson = "";
		try {
			
			htcDeptChargeIassetRelaJson = htcDeptChargeIassetRelaService.saveHtcDeptChargeIassetRela(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			htcDeptChargeIassetRelaJson = e.getMessage();
		}

		return JSONObject.parseObject(htcDeptChargeIassetRelaJson);
		

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeiassetrela/queryHtcDeptChargeIassetRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptChargeIassetRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeIassetRelaJson = htcDeptChargeIassetRelaService.queryHtcDeptChargeIassetRela(getPage(mapVo));

		return JSONObject.parseObject(htcDeptChargeIassetRelaJson);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeiassetrela/deleteHtcDeptChargeIassetRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcDeptChargeIassetRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("asset_code", ids[10]);
			listVo.add(mapVo);
		}
		String htcDeptChargeIassetRelaJson = "";
		try {
			htcDeptChargeIassetRelaJson = htcDeptChargeIassetRelaService.deleteBatchHtcDeptChargeIassetRela(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			htcDeptChargeIassetRelaJson = e.getMessage();
		}
		return JSONObject.parseObject(htcDeptChargeIassetRelaJson);

	}

	
	// 查询收费项目
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeiassetrela/queryHtcDeptChargeIassetRelaCharge", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptChargeIassetRelaCharge(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeIassetRelaJson = htcDeptChargeIassetRelaService.queryHtcDeptChargeIassetRelaCharge(getPage(mapVo));
		
		return JSONObject.parseObject(htcDeptChargeIassetRelaJson);
	}

	// 查询无形资产
	@RequestMapping(value = "/hrp/htc/task/projectcost/deptchargeiassetrela/queryHtcDeptChargeIassetRelaIasset", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptChargeIassetRelaFasset(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String htcDeptChargeIassetRelaJson = htcDeptChargeIassetRelaService.queryHtcDeptChargeIassetRelaIasset(getPage(mapVo));
		
		return JSONObject.parseObject(htcDeptChargeIassetRelaJson);

	}
	
}
