package com.chd.hrp.htc.controller.task.deptcost;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.hrp.htc.entity.task.deptcost.HtcDeptIassetDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcDeptIassetDetailService;

/**
 * 2015-3-18 author:alfred
 */

@Controller
public class HtcDeptIassetDetailController extends BaseController {

	private static Logger logger = Logger
			.getLogger(HtcDeptIassetDetailController.class);

	@Resource(name = "htcDeptIassetDetailService")
	private final HtcDeptIassetDetailService htcDeptIassetDetailService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetDetailMainPage", method = RequestMethod.GET)
	public String htcDeptIassetDetailMainPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetDetailMain";
	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetDetailAddPage", method = RequestMethod.GET)
	public String htcDeptIassetDetailAddPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetDetailAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/addHtcDeptIassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptIassetDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
		mapVo.put("acc_month", mapVo.get("year_month").toString().substring(4, 6));

		String deptIassetDetailJson = "";
		try {
			deptIassetDetailJson = htcDeptIassetDetailService.addHtcDeptIassetDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptIassetDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(deptIassetDetailJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/queryHtcDeptIassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptIassetDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String deptIassetDetail = htcDeptIassetDetailService.queryHtcDeptIassetDetail(getPage(mapVo));

		return JSONObject.parseObject(deptIassetDetail);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/deleteHtcDeptIassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcDeptIassetDetail(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {

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
			mapVo.put("asset_code", ids[7]);// 实际实体类变量
			listVo.add(mapVo);
		}

		String deptIassetDetailJson = "";
		try {
			deptIassetDetailJson = htcDeptIassetDetailService.deleteBatchHtcDeptIassetDetail(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptIassetDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(deptIassetDetailJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetDetailUpdatePage", method = RequestMethod.GET)
	public String htcDeptIassetDetailUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		HtcDeptIassetDetail htcDeptIassetDetail = htcDeptIassetDetailService.queryHtcDeptIassetDetailByCode(mapVo);
		mode.addAttribute("group_id",htcDeptIassetDetail.getGroup_id());
		mode.addAttribute("hos_id",htcDeptIassetDetail.getHos_id());
		mode.addAttribute("copy_code",htcDeptIassetDetail.getCopy_code());
		mode.addAttribute("acc_year",htcDeptIassetDetail.getAcc_year());
		mode.addAttribute("acc_month",htcDeptIassetDetail.getAcc_month());
		mode.addAttribute("dept_no",htcDeptIassetDetail.getDept_no());
		mode.addAttribute("dept_id",htcDeptIassetDetail.getDept_id());
		mode.addAttribute("dept_code",htcDeptIassetDetail.getDept_code());
		mode.addAttribute("dept_name",htcDeptIassetDetail.getDept_name());
		mode.addAttribute("asset_type_code",htcDeptIassetDetail.getAsset_type_code());
		mode.addAttribute("asset_type_name",htcDeptIassetDetail.getAsset_type_name());
		mode.addAttribute("asset_code",htcDeptIassetDetail.getAsset_code());
		mode.addAttribute("asset_name",htcDeptIassetDetail.getAsset_name());
		mode.addAttribute("depre_amount",htcDeptIassetDetail.getDepre_amount());
		mode.addAttribute("source_id",htcDeptIassetDetail.getSource_id());
		mode.addAttribute("source_code",htcDeptIassetDetail.getSource_id());
		mode.addAttribute("source_name",htcDeptIassetDetail.getSource_name());
		return "hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetDetailUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/updateHtcDeptIassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcDeptIassetDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
		mapVo.put("acc_month", mapVo.get("year_month").toString().substring(4, 6));


		String deptIassetDetailJson = "";
		try {
			deptIassetDetailJson = htcDeptIassetDetailService
					.updateHtcDeptIassetDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptIassetDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(deptIassetDetailJson);
	}

	// 跳转归集页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetCostDetailMain", method = RequestMethod.GET)
	public String deptiassetdetailMainSum(Model mode) throws Exception {

		return "hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetCostDetailMain";

	}

	// d
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetCostDetailCheckPage", method = RequestMethod.GET)
	public String htcDeptIassetCostDetailCheckPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptiassetdetail/htcDeptIassetCostDetailCheck";

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/queryHtcDeptIassetCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptIassetCostDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String deptiassetdetail = htcDeptIassetDetailService.queryHtcDeptIassetCostDetail(getPage(mapVo));

		return JSONObject.parseObject(deptiassetdetail);

	}
	
	// 处理核对
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptiassetdetail/sumHtcDeptIassetCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sumDeptIassetCostDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String deptiassetdetail = htcDeptIassetDetailService.checkHtcDeptIassetCostDetail(getPage(mapVo));

		return JSONObject.parseObject(deptiassetdetail);

	}

	
}
