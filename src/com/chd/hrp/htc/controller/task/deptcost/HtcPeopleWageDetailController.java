package com.chd.hrp.htc.controller.task.deptcost;

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
import com.chd.hrp.htc.service.task.deptcost.HtcPeopleWageDetailService;


@Controller
public class HtcPeopleWageDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcPeopleWageDetailController.class);

	@Resource(name = "htcPeopleWageDetailService")
	private final HtcPeopleWageDetailService htcPeopleWageDetailService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplewagedetail/htcPeopleWageDetailMainPage", method = RequestMethod.GET)
	public String htcPeopleWageDetailMainPage(Model mode) throws Exception {

		return "hrp/htc/task/deptcost/peoplewagedetail/htcPeopleWageDetailMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplewagedetail/htcPeopleWageDetailAddPage", method = RequestMethod.GET)
	public String htcPeopleWageDetailAddPage(Model mode) throws Exception {

		return "hrp/htc/task/deptcost/peoplewagedetail/htcPeopleWageDetailAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplewagedetail/addHtcPeopleWageDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcPeopleWageDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
		mapVo.put("acc_month", mapVo.get("year_month").toString().substring(4, 6));
		
		String peopleWageDetailJson = "";
		try {
			peopleWageDetailJson = htcPeopleWageDetailService.addHtcPeopleWageDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleWageDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleWageDetailJson);

	}

	// 查询表头
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplewagedetail/queryHtcPeopleWageItemClumHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcPeopleWageItemClumHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		String peopleWageDetail = htcPeopleWageDetailService.queryHtcPeopleWageItemClumHead(mapVo);

		return JSONObject.parseObject(peopleWageDetail);

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplewagedetail/queryHtcPeopleWageDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcPeopleWageDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String peopleWageDetail = htcPeopleWageDetailService.queryHtcPeopleWageDetail(getPage(mapVo));
		return JSONObject.parseObject(peopleWageDetail);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplewagedetail/deleteHtcPeopleWageDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcPeopleWageDetail(@RequestParam(value = "ParamVo", required = true) String paramVo, Model mode) throws Exception {
		
		String peopleWageDetailJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("dept_no", ids[5]);
			mapVo.put("dept_id", ids[6]);
			mapVo.put("people_type_code", ids[7]);
			mapVo.put("people_code", ids[8]);// 实际实体类变量
			listVo.add(mapVo);
		}
		try {
			peopleWageDetailJson = htcPeopleWageDetailService.deleteBatchHtcPeopleWageDetail(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleWageDetailJson = e.getMessage();
		}
	
		return JSONObject.parseObject(peopleWageDetailJson);
	}

	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplewagedetail/updateHtcPeopleWageDetailItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcPeopleWageDetailItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String peopleWageDetailJson = "";
		try {
			peopleWageDetailJson = htcPeopleWageDetailService.updateHtcPeopleWageDetailItem(mapVo);
		} catch (Exception e) {
			peopleWageDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleWageDetailJson);

	}
	
}
