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
import com.chd.hrp.htc.entity.task.deptcost.HtcDeptMaterialDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcDeptMaterialDetailService;

/**
 * 2015-3-18 author:alfred
 */

@Controller
public class HtcDeptMaterialDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcDeptMaterialDetailController.class);

	@Resource(name = "htcDeptMaterialDetailService")
	private final HtcDeptMaterialDetailService htcDeptMaterialDetailService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialDetailMainPage", method = RequestMethod.GET)
	public String deptMaterialDetailMainPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialDetailMain";
	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialDetailAddPage", method = RequestMethod.GET)
	public String deptMaterialDetailAddPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialDetailAdd";

	}
	// 保存
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/addHtcDeptMaterialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcDeptMaterialDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
		mapVo.put("acc_month", mapVo.get("year_month").toString().substring(4, 6));
		String deptMaterialDetailJson = "";
		try {
			deptMaterialDetailJson = htcDeptMaterialDetailService
					.addHtcDeptMaterialDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptMaterialDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(deptMaterialDetailJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/queryHtcDeptMaterialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptMaterialDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String deptMaterialDetail = htcDeptMaterialDetailService.queryHtcDeptMaterialDetail(getPage(mapVo));
		return JSONObject.parseObject(deptMaterialDetail);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/deleteHtcDeptMaterialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcDeptMaterialDetail(@RequestParam(value = "ParamVo", required = true) String paramVo,Model mode) throws Exception {
		
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
			mapVo.put("mate_code", ids[7]);// 实际实体类变量
			listVo.add(mapVo);
		}
		String deptMaterialDetailJson = "";
		try {
			deptMaterialDetailJson = htcDeptMaterialDetailService.deleteBatchHtcDeptMaterialDetail(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptMaterialDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(deptMaterialDetailJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialDetailUpdatePage", method = RequestMethod.GET)
	public String htcDeptMaterialDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		HtcDeptMaterialDetail htcDeptMaterialDetail = htcDeptMaterialDetailService.queryHtcDeptMaterialDetailByCode(mapVo);
		mode.addAttribute("group_id",htcDeptMaterialDetail.getGroup_id());
		mode.addAttribute("hos_id",htcDeptMaterialDetail.getHos_id());
		mode.addAttribute("copy_code",htcDeptMaterialDetail.getCopy_code());
		mode.addAttribute("acc_year",htcDeptMaterialDetail.getAcc_year());
		mode.addAttribute("acc_month",htcDeptMaterialDetail.getAcc_month());
		mode.addAttribute("dept_no",htcDeptMaterialDetail.getDept_no());
		mode.addAttribute("dept_id",htcDeptMaterialDetail.getDept_id());
		mode.addAttribute("dept_code",htcDeptMaterialDetail.getDept_code());
		mode.addAttribute("dept_name",htcDeptMaterialDetail.getDept_name());
		mode.addAttribute("mate_type_code",htcDeptMaterialDetail.getMate_type_code());
		mode.addAttribute("mate_type_name",htcDeptMaterialDetail.getMate_type_name());
		mode.addAttribute("mate_code",htcDeptMaterialDetail.getMate_code());
		mode.addAttribute("mate_name",htcDeptMaterialDetail.getMate_name());
		mode.addAttribute("is_charge",htcDeptMaterialDetail.getIs_charge());
		mode.addAttribute("num",htcDeptMaterialDetail.getNum());
		mode.addAttribute("amount",htcDeptMaterialDetail.getAmount());
		mode.addAttribute("source_id",htcDeptMaterialDetail.getSource_id());
		mode.addAttribute("source_code",htcDeptMaterialDetail.getSource_code());
		mode.addAttribute("source_name",htcDeptMaterialDetail.getSource_name());
		return "hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialDetailUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/updateHtcDeptMaterialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcDeptMaterialDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("acc_year", mapVo.get("year_month").toString().substring(0, 4));
		mapVo.put("acc_month", mapVo.get("year_month").toString().substring(4, 6));
		String deptMaterialDetailJson = "";
		try {
			deptMaterialDetailJson = htcDeptMaterialDetailService
					.updateHtcDeptMaterialDetail(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			deptMaterialDetailJson = e.getMessage();
		}

		return JSONObject.parseObject(deptMaterialDetailJson);
	}

	//归集页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialCostDetailMainPage", method = RequestMethod.GET)
	public String htcDeptMaterialCostDetailMainPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialCostDetailMain";

	}
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialCostDetailCheckPage", method = RequestMethod.GET)
	public String htcDeptMaterialCostDetailCheckPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/deptmaterialdetail/htcDeptMaterialCostDetailCheck";
	}

	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/queryHtcDeptMaterialCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcDeptMaterialCostDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String deptMaterialDetail = htcDeptMaterialDetailService.queryHtcDeptMaterialCostDetail(getPage(mapVo));

		return JSONObject.parseObject(deptMaterialDetail);

	}
	
	// 处理核对
	@RequestMapping(value = "/hrp/htc/task/deptcost/deptmaterialdetail/checkHtcDeptMaterialCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkHtcDeptMaterialCostDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String deptMaterialDetail = htcDeptMaterialDetailService.checkHtcDeptMaterialCostDetail(getPage(mapVo));

		return JSONObject.parseObject(deptMaterialDetail);
	}
}
