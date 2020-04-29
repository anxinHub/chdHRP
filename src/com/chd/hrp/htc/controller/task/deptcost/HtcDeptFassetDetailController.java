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
import com.chd.hrp.htc.entity.task.deptcost.HtcDeptFassetDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcDeptFassetDetailService;

/**
 * 2015-3-18 author:alfred
 */

@Controller
public class HtcDeptFassetDetailController extends BaseController {

	private static Logger logger = Logger
			.getLogger(HtcDeptFassetDetailController.class);

	@Resource(name = "htcDeptFassetDetailService")
	private final HtcDeptFassetDetailService htcDeptFassetDetailService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetDetailMainPage", method = RequestMethod.GET)
	public String htcDeptFassetDetailMainPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetDetailMain";
	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetDetailAddPage", method = RequestMethod.GET)
	public String htcDeptFassetDetailAddPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetDetailAdd";
	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/addHtcDeptFassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptFassetDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
		mapVo.put("acc_month", mapVo.get("year_month").toString().substring(4, 6));
		String deptFassetDetailJson = "";
		try {
			deptFassetDetailJson = htcDeptFassetDetailService.addHtcDeptFassetDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptFassetDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(deptFassetDetailJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/queryHtcDeptFassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptFassetDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String deptFassetDetail = htcDeptFassetDetailService.queryHtcDeptFassetDetail(getPage(mapVo));

		return JSONObject.parseObject(deptFassetDetail);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/deleteHtcDeptFassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcDeptFassetDetail(
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

		String deptFassetDetailJson = "";
		try {
			deptFassetDetailJson = htcDeptFassetDetailService.deleteBatchHtcDeptFassetDetail(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptFassetDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(deptFassetDetailJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetDetailUpdatePage", method = RequestMethod.GET)
	public String htcDeptFassetDetailUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		HtcDeptFassetDetail htcDeptFassetDetail = htcDeptFassetDetailService.queryHtcDeptFassetDetailByCode(mapVo);
		mode.addAttribute("group_id",htcDeptFassetDetail.getGroup_id());
		mode.addAttribute("hos_id",htcDeptFassetDetail.getHos_id());
		mode.addAttribute("copy_code",htcDeptFassetDetail.getCopy_code());
		mode.addAttribute("acc_year",htcDeptFassetDetail.getAcc_year());
		mode.addAttribute("acc_month",htcDeptFassetDetail.getAcc_month());
		mode.addAttribute("dept_no",htcDeptFassetDetail.getDept_no());
		mode.addAttribute("dept_id",htcDeptFassetDetail.getDept_id());
		mode.addAttribute("dept_code",htcDeptFassetDetail.getDept_code());
		mode.addAttribute("dept_name",htcDeptFassetDetail.getDept_name());
		mode.addAttribute("asset_type_code",htcDeptFassetDetail.getAsset_type_code());
		mode.addAttribute("asset_type_name",htcDeptFassetDetail.getAsset_type_name());
		mode.addAttribute("asset_code",htcDeptFassetDetail.getAsset_code());
		mode.addAttribute("asset_name",htcDeptFassetDetail.getAsset_name());
		mode.addAttribute("depre_amount",htcDeptFassetDetail.getDepre_amount());
		mode.addAttribute("source_id",htcDeptFassetDetail.getSource_id());
		mode.addAttribute("source_code",htcDeptFassetDetail.getSource_id());
		mode.addAttribute("source_name",htcDeptFassetDetail.getSource_name());
		return "hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetDetailUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/updateHtcDeptFassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcDeptFassetDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
		mapVo.put("acc_month", mapVo.get("year_month").toString().substring(4, 6));
		String deptFassetDetailJson = "";
		try {
			deptFassetDetailJson = htcDeptFassetDetailService
					.updateHtcDeptFassetDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptFassetDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(deptFassetDetailJson);
	}

	// 跳转归集页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetCostDetailMainPage", method = RequestMethod.GET)
	public String htcDeptFassetCostDetailMainPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetCostDetailMain";

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/queryHtcDeptFassetCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptFassetCostDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String deptFassetDetail = htcDeptFassetDetailService.queryHtcDeptFassetCostDetail(getPage(mapVo));
		
		return JSONObject.parseObject(deptFassetDetail);

	}
	
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetCostDetailCheckPage", method = RequestMethod.GET)
	public String htcDeptFassetCostDetailCheckPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptfassetdetail/htcDeptFassetCostDetailCheck";
	}

	// 处理核对
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptfassetdetail/checkHtcDeptFassetCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkHtcDeptFassetCostDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String deptFassetDetail = htcDeptFassetDetailService.checkHtcDeptFassetCostDetail(getPage(mapVo));
		
		return JSONObject.parseObject(deptFassetDetail);

	}

}
