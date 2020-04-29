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
import com.chd.hrp.htc.entity.task.deptcost.HtcPeopleCostDetail;
import com.chd.hrp.htc.service.task.deptcost.HtcPeopleCostDetailService;

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
public class HtcPeopleCostDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcPeopleCostDetailController.class);

	@Resource(name = "htcPeopleCostDetailService")
	private final HtcPeopleCostDetailService htcPeopleCostDetailService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplecostdetail/htcPeopleCostDetailMainPage", method = RequestMethod.GET)
	public String peopleCostDetailMainPage(Model mode) throws Exception {

		return "hrp/htc/task/deptcost/peoplecostdetail/htcPeopleCostDetailMain";

	}

	// 采集页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplecostdetail/htcPeopleCostDetailCollectPage", method = RequestMethod.GET)
	public String peopleCostDetailCollectPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/peoplecostdetail/htcPeopleCostDetailCollect";
	}
	
	
	// 采集
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplecostdetail/collectHtcPeopleCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectHtcPeopleCostDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String peopleCostDetailJson = htcPeopleCostDetailService.collectHtcPeopleCostDetail(mapVo);
		return JSONObject.parseObject(peopleCostDetailJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplecostdetail/queryHtcPeopleCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPeopleCostDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String peopleCostDetail = htcPeopleCostDetailService.queryHtcPeopleCostDetail(getPage(mapVo));
		return JSONObject.parseObject(peopleCostDetail);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplecostdetail/deleteHtcPeopleCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePeopleCostDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			mapVo.put("people_code", ids[8]);
			mapVo.put("cost_item_no", ids[9]);
			mapVo.put("cost_item_id", ids[10]);
			listVo.add(mapVo);
		}
		
		String peopleCostDetailJson = "";
		try {
			peopleCostDetailJson = htcPeopleCostDetailService.deleteBatchHtcPeopleCostDetail(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			peopleCostDetailJson = e.getMessage();
		}
		return JSONObject.parseObject(peopleCostDetailJson);

	}

	// 数据核对页面
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplecostdetail/htcPeopleCostDetailCheckPage", method = RequestMethod.GET)
	public String peopleCostDetailCheckPage(Model mode) throws Exception {
		return "hrp/htc/task/deptcost/peoplecostdetail/htcPeopleCostDetailCheck";
	}

	// 数据核对
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplecostdetail/checkHtcPeopleCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkPeopleCostDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("start_year_month", mapVo.get("start_year_month"));
		mapVo.put("end_year_month", mapVo.get("end_year_month"));
		String flag = mapVo.get("check_way").toString();
		String peopleCostDetailJson = "";
		if ("month".equals(flag)) {
			peopleCostDetailJson = htcPeopleCostDetailService.queryHtcPeopleCostDetailSumMonth(getPage(mapVo));
		} else if("all".equals(flag)){
			peopleCostDetailJson = htcPeopleCostDetailService.queryHtcPeopleCostDetailSummary(getPage(mapVo));
		}
		return JSONObject.parseObject(peopleCostDetailJson);
	}                     
	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplecostdetail/htcPeopleCostDetailUpdatePage", method = RequestMethod.GET)
	public String htcPeopleCostDetailUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		HtcPeopleCostDetail htcPeopleCostDetail = htcPeopleCostDetailService.queryHtcPeopleCostDetailByCode(mapVo);
		mode.addAttribute("group_id", htcPeopleCostDetail.getGroup_id());
		mode.addAttribute("hos_id", htcPeopleCostDetail.getHos_id());
		mode.addAttribute("copy_code", htcPeopleCostDetail.getCopy_code());
		mode.addAttribute("acc_year", htcPeopleCostDetail.getAcc_year());
		mode.addAttribute("acc_month", htcPeopleCostDetail.getAcc_month());
		mode.addAttribute("dept_no", htcPeopleCostDetail.getDept_no());
		mode.addAttribute("dept_id", htcPeopleCostDetail.getDept_id());
		mode.addAttribute("dept_code", htcPeopleCostDetail.getDept_code());
		mode.addAttribute("dept_name", htcPeopleCostDetail.getDept_name());
		mode.addAttribute("people_type_code", htcPeopleCostDetail.getPeople_type_code());
		mode.addAttribute("people_type_name", htcPeopleCostDetail.getPeople_type_name());
		mode.addAttribute("people_code", htcPeopleCostDetail.getPeople_code());
		mode.addAttribute("people_name", htcPeopleCostDetail.getPeople_name());
		mode.addAttribute("cost_item_no", htcPeopleCostDetail.getCost_item_no());
		mode.addAttribute("cost_item_id", htcPeopleCostDetail.getCost_item_id());
		mode.addAttribute("cost_item_code", htcPeopleCostDetail.getCost_item_code());
		mode.addAttribute("cost_item_name", htcPeopleCostDetail.getCost_item_name());
		mode.addAttribute("amount", htcPeopleCostDetail.getAmount());
		mode.addAttribute("orig_amount", htcPeopleCostDetail.getOrig_amount());
		return "hrp/htc/task/deptcost/peoplecostdetail/htcPeopleCostDetailUpdate";
	}

	@RequestMapping(value = "/hrp/htc/task/deptcost/peoplecostdetail/updateHtcPeopleCostDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcPeopleCostDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String peopleCostDetailJson = "";
		try {
			peopleCostDetailJson = htcPeopleCostDetailService.updateHtcPeopleCostDetail(mapVo);
		} catch (Exception e) {
			peopleCostDetailJson = e.getMessage();
		}
		return JSONObject.parseObject(peopleCostDetailJson);
	}
	
}
