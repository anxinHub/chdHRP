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
import com.chd.hrp.htc.service.task.deptcost.HtcPeopleBonusDetailService;


@Controller
public class HtcPeopleBonusDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcPeopleBonusDetailController.class);

	@Resource(name = "htcPeopleBonusDetailService")
	private final HtcPeopleBonusDetailService htcPeopleBonusDetailService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplebonusdetail/htcPeopleBonusDetailMainPage", method = RequestMethod.GET)
	public String peopleBonusDetailMainPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/peoplebonusdetail/htcPeopleBonusDetailMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplebonusdetail/htcPeopleBonusDetailAddPage", method = RequestMethod.GET)
	public String peopleBonusDetailAddPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/peoplebonusdetail/htcPeopleBonusDetailAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplebonusdetail/addHtcPeopleBonusDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcPeopleBonusDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
		mapVo.put("acc_month", mapVo.get("year_month").toString().substring(4, 6));

		String peopleBonusDetailJson = "";
		try {
			peopleBonusDetailJson = htcPeopleBonusDetailService.addHtcPeopleBonusDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleBonusDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleBonusDetailJson);

	}
    // 查询表头
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplebonusdetail/queryHtcPeopleBonusItemClumHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcPeopleBonusItemClumHead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String peopleBonusDetail = htcPeopleBonusDetailService.queryHtcPeopleBonusItemClumHead(mapVo);
		return JSONObject.parseObject(peopleBonusDetail);

	}
	
	// 查询
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplebonusdetail/queryHtcPeopleBonusDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcPeopleBonusDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String peopleBonusDetail = htcPeopleBonusDetailService.queryHtcPeopleBonusDetail(getPage(mapVo));

		return JSONObject.parseObject(peopleBonusDetail);

	}
	

	// 删除
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplebonusdetail/deleteHtcPeopleBonusDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePeopleBonusDetail(@RequestParam(value = "ParamVo", required = true) String paramVo, Model mode) throws Exception {

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
		
		String peopleBonusDetailJson = "";
		try {
			peopleBonusDetailJson = htcPeopleBonusDetailService.deleteBatchHtcPeopleBonusDetail(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleBonusDetailJson = e.getMessage();
		}
		
		return JSONObject.parseObject(peopleBonusDetailJson);

	}

	// 修改
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplebonusdetail/updateHtcPeopleBonusDetailItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcPeopleBonusDetailItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


		String peopleBonusDetailJson = "";
		try {
			peopleBonusDetailJson = htcPeopleBonusDetailService.updateHtcPeopleBonusDetailItem(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleBonusDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleBonusDetailJson);

	}
}
