package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiIncomeitemConf;
import com.chd.hrp.hpm.entity.AphiIncomeitemData;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiIncomeitemDataService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiIncomeitemDataController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiIncomeitemDataController.class);

	@Resource(name = "aphiIncomeitemDataService")
	private final AphiIncomeitemDataService aphiIncomeitemDataService = null;

	// @Resource(name = "aphiDeptService")
	// private final AphiDeptService aphiDeptService = null;
	//
	// @Resource(name = "aphiIncomeItem1_1Service")
	// private final AphiIncomeItemService aphiIncomeItem1_1Service = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataMainPage", method = RequestMethod.GET)
	public String incomeitemDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataAddPage", method = RequestMethod.GET)
	public String incomeitemDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataAdd";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataChoosePage", method = RequestMethod.GET)
	public String hpmIncomeitemDataChoosePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("checkIds", mapVo.get("checkIds"));

		return "hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataChoose";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/addHpmIncomeitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addIncomeitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemDataJson = aphiIncomeitemDataService.addIncomeitemData(mapVo);

		return JSONObject.parseObject(incomeitemDataJson);

	}

	/*
	 * @RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/getIncomeItemTargetValue",
	 * method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Map<String, Object> getEmpTargetValue(
	 * 
	 * @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 * 
	 * String targetValueJson =
	 * aphiIncomeitemDataService.getIncomeitemTargetValue(mapVo);
	 * 
	 * return JSONObject.parseObject(targetValueJson);
	 * 
	 * }
	 */

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/initHpmIncomeitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initIncomeitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		String hospTargetDataJson = "";

		hospTargetDataJson = aphiIncomeitemDataService.initIncomeitemData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/queryHpmIncomeitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIncomeitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}

		String incomeitemData = aphiIncomeitemDataService.queryIncomeitemData(getPage(mapVo));

		return JSONObject.parseObject(incomeitemData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/deleteHpmIncomeitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIncomeitemData(@RequestParam String checkIds, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemDataJson = aphiIncomeitemDataService.deleteIncomeitemDataCodes(mapVo, checkIds);

		return JSONObject.parseObject(incomeitemDataJson);
	}

	// 修改页面跳转

	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataUpdatePage", method = RequestMethod.GET)
	public String incomeitemDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		AphiIncomeitemData incomeitemData = new AphiIncomeitemData();

		incomeitemData = aphiIncomeitemDataService.queryIncomeitemDataByCode(mapVo);

		mode.addAttribute("acct_year", incomeitemData.getAcct_year());

		mode.addAttribute("acct_month", incomeitemData.getAcct_month());

		mode.addAttribute("dept_id", incomeitemData.getDept_id());
		mode.addAttribute("dept_no", incomeitemData.getDept_no());
		mode.addAttribute("income_item_code", incomeitemData.getIncome_item_code());

		mode.addAttribute("dept_name", incomeitemData.getDept_name());

		mode.addAttribute("income_item_name", incomeitemData.getIncome_item_name());

		mode.addAttribute("order_money", incomeitemData.getOrder_money());

		mode.addAttribute("order_ret_money", incomeitemData.getOrder_ret_money());

		mode.addAttribute("perform_money", incomeitemData.getPerform_money());

		mode.addAttribute("perform_ret_money", incomeitemData.getPerform_ret_money());

		mode.addAttribute("income_tot_money", incomeitemData.getIncome_tot_money());

		return "hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/updateHpmIncomeitemData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIncomeitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemDataJson = aphiIncomeitemDataService.updateIncomeitemData(mapVo);

		return JSONObject.parseObject(incomeitemDataJson);
	}

	// 计算
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/calculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> calculate(@RequestParam String checkIds, Model mode) throws Exception {

		String result = "";

		double order_ret_money = 0;

		double perform_ret_money = 0;

		double income_tot_money = 0;

		for (String ids : checkIds.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String dept_id = ids.split(";")[0];

			String dept_no = ids.split(";")[1];
			
			String income_item_code = ids.split(";")[2];

			String acct_year = ids.split(";")[3];

			String acct_month = ids.split(";")[4];

			String order_money = ids.split(";")[5];

			String perform_money = ids.split(";")[6];

			mapVo.put("dept_id", dept_id);
			mapVo.put("dept_no", dept_no);
			mapVo.put("income_item_code", income_item_code);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			AphiSchemeConf sc = aphiIncomeitemDataService.getSchemeSeqNo(mapVo);// 通过年月获得scheme_seq_no

			mapVo.put("scheme_seq_no", sc.getScheme_seq_no());

			AphiIncomeitemConf inComeItemConf = aphiIncomeitemDataService.getIncomeItemConfByCode(mapVo);// 根据科室编码,项目编码和方案序列获得权重

			if (inComeItemConf != null) {

				order_ret_money = Double.parseDouble(order_money) * inComeItemConf.getOrder_perc();// 计算order_ret_money

				perform_ret_money = Double.parseDouble(perform_money) * inComeItemConf.getPerform_perc();// 计算perform_ret_money

				income_tot_money = order_ret_money + perform_ret_money;// 计算income_tot_money

			}
			mapVo.put("order_ret_money", order_ret_money);

			mapVo.put("perform_ret_money", perform_ret_money);

			mapVo.put("income_tot_money", income_tot_money);

			result = aphiIncomeitemDataService.calculate(mapVo);// 将计算结果更新到数据表
		}

		return JSONObject.parseObject(result);
	}

	//导入
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataImportPage", method = RequestMethod.GET)
	public String incomeitemDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataImport";

	}
	
	//导入跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/hpmIncomeitemDataImport",method= RequestMethod.POST)
	@ResponseBody
	public String incomeitemDataImport(@RequestParam Map<String, Object> mapVo, Model mode){
		try {
			String impJosn = aphiIncomeitemDataService.incomeitemDataImport(mapVo);
			return impJosn;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/downTemplateHpmIncomeitemData", method = RequestMethod.GET)
	public String downTemplateIncomeitemData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\downTemplate", "收入项目数据准备.xlsx");

		return null;
	}
	
	//his迁移数据到hrp
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemdata/addHisHrp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHisHrp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemDataJson = aphiIncomeitemDataService.addHisHrp(mapVo);

		return JSONObject.parseObject(incomeitemDataJson);
	}

}
