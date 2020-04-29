package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiIncomeItem;
import com.chd.hrp.hpm.entity.AphiIncomeitemIncreaseData;
import com.chd.hrp.hpm.entity.AphiIncomeitemPerc;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiIncomeItemService;
import com.chd.hrp.hpm.service.AphiIncomeitemIncreaseDataService;

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
public class AphiIncomeitemIncreaseDataController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiIncomeitemIncreaseDataController.class);

	@Resource(name = "aphiIncomeitemIncreaseDataService")
	private final AphiIncomeitemIncreaseDataService aphiIncomeitemIncreaseDataService = null;

	// @Resource(name = "aphiIncomeItem1_1Service")
	// private final AphiIncomeItemService aphiIncomeItem1_1Service = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/hpmIncomeitemIncreaseDataMainPage", method = RequestMethod.GET)
	public String incomeitemIncreaseDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemincreasedata/hpmIncomeitemIncreaseDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/hpmIncomeitemIncreaseDataAddPage", method = RequestMethod.GET)
	public String incomeitemIncreaseDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemincreasedata/hpmIncomeitemIncreaseDataAdd";

	}

	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/hpmIncomeitemIncreaseDataImportPage", method = RequestMethod.GET)
	public String incomeitemIncreaseDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemincreasedata/hpmIncomeitemIncreaseDataImport";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/addHpmIncomeitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmIncomeitemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemIncreaseDataJson = aphiIncomeitemIncreaseDataService.addIncomeitemIncreaseData(mapVo);

		return JSONObject.parseObject(incomeitemIncreaseDataJson);

	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/initHpmIncomeitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmIncomeitemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String hospTargetDataJson = "";

		hospTargetDataJson = aphiIncomeitemIncreaseDataService.initIncomeitemIncreaseData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/queryHpmIncomeitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmIncomeitemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}

		String incomeitemIncreaseData = aphiIncomeitemIncreaseDataService.queryIncomeitemIncreaseData(getPage(mapVo));

		return JSONObject.parseObject(incomeitemIncreaseData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/deleteHpmIncomeitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmIncomeitemIncreaseData(@RequestParam String checkIds, Model mode) throws Exception {

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

		String incomeitemIncreaseDataJson = aphiIncomeitemIncreaseDataService.deleteIncomeitemIncreaseData(mapVo, checkIds);

		return JSONObject.parseObject(incomeitemIncreaseDataJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/hpmIncomeitemIncreaseDataUpdatePage", method = RequestMethod.GET)
	public String incomeitemIncreaseDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AphiIncomeitemIncreaseData incomeitemIncreaseData = new AphiIncomeitemIncreaseData();

		incomeitemIncreaseData = aphiIncomeitemIncreaseDataService.queryIncomeitemIncreaseDataByCode(mapVo);

		// mode.addAttribute("comp_code",
		// incomeitemIncreaseData.getComp_code());

		mode.addAttribute("copy_code", incomeitemIncreaseData.getCopy_code());

		mode.addAttribute("acct_year", incomeitemIncreaseData.getAcct_year());

		mode.addAttribute("acct_month", incomeitemIncreaseData.getAcct_month());

		mode.addAttribute("income_item_code", incomeitemIncreaseData.getIncome_item_code());

		mode.addAttribute("income_item_name", incomeitemIncreaseData.getIncome_item_name());

		mode.addAttribute("income_money", incomeitemIncreaseData.getIncome_money());

		mode.addAttribute("income_ret_money", incomeitemIncreaseData.getIncome_ret_money());

		return "hrp/hpm/hpmincomeitemincreasedata/hpmIncomeitemIncreaseDataUpdate";

	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/updateHpmIncomeitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmIncomeitemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemIncreaseDataJson = aphiIncomeitemIncreaseDataService.updateIncomeitemIncreaseData(mapVo);

		return JSONObject.parseObject(incomeitemIncreaseDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/increaseCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> increaseCalculate(@RequestParam String checkIds, Model mode) throws Exception {

		String result = "";

		long income_ret_money = 0;

		for (String ids : checkIds.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String income_item_code = ids.split(";")[0];

			String acct_year = ids.split(";")[1];

			String acct_month = ids.split(";")[2];

			String income_money = ids.split(";")[3];

			mapVo.put("income_item_code", income_item_code);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			AphiSchemeConf sc = aphiIncomeitemIncreaseDataService.getSchemeSeqNo(mapVo);// 通过年月获得scheme_seq_no

			mapVo.put("scheme_seq_no", sc.getScheme_seq_no());

			AphiIncomeitemPerc inComeItemConf = aphiIncomeitemIncreaseDataService.getIncomeItemPercSeqByCode(mapVo);// 根据项目编码和方案序列获得权重

			if (inComeItemConf != null) {

				income_ret_money = Long.parseLong(income_money) * inComeItemConf.getIncome_percent();// 计算income_ret_money

			}

			mapVo.put("income_ret_money", income_ret_money);

			result = aphiIncomeitemIncreaseDataService.calculate(mapVo);// 将计算结果更新到数据表
		}

		return JSONObject.parseObject(result);
	}

	/**
	 * 导入
	 */
	// @RequestMapping(value =
	// "/hrp/hpm/hpmincomeitemincreasedata/readIncomeitemIncreaseDataFiles", method =
	// RequestMethod.POST)
	// public String readCostItemFiles(Plupload plupload, HttpServletRequest
	// request, HttpServletResponse response, Model mode) throws IOException {
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// if (mapVo.get("group_id") == null) {
	// mapVo.put("group_id", SessionManager.getGroupId());
	// }
	// if (mapVo.get("hos_id") == null) {
	// mapVo.put("hos_id", SessionManager.getHosId());
	// }
	// if (mapVo.get("copy_code") == null) {
	// mapVo.put("copy_code", SessionManager.getCopyCode());
	// }
	//
	// List<AphiIncomeitemIncreaseData> list2 = new
	// ArrayList<AphiIncomeitemIncreaseData>();
	//
	// List<String[]> list = UploadUtil.readFile(plupload, request, response);
	//
	// try {
	//
	// for (int i = 1; i < list.size(); i++) {
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// AphiIncomeitemIncreaseData incomeitemIncreaseData = new
	// AphiIncomeitemIncreaseData();
	//
	// String temp[] = list.get(i);// 行
	//
	// if (StringUtils.isNotEmpty(temp[0])) {
	//
	// incomeitemIncreaseData.setAcct_year(temp[0]);
	//
	// mapVo.put("acct_year", temp[0]);
	//
	// } else {
	//
	// err_sb.append("核算年度为空 ");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[1])) {
	//
	// incomeitemIncreaseData.setAcct_month(temp[1]);
	//
	// mapVo.put("acct_month", temp[1]);
	//
	// } else {
	//
	// err_sb.append("核算月份为空 ");
	//
	// }
	// if (StringUtils.isNotEmpty(temp[2])) {
	//
	// incomeitemIncreaseData.setIncome_item_code(temp[2]);
	//
	// mapVo.put("income_item_code", temp[2]);
	//
	// } else {
	//
	// err_sb.append("收入项目为空 ");
	//
	// }
	//
	// incomeitemIncreaseData.setIncome_money(Double.parseDouble(temp[3]));
	//
	// mapVo.put("income_money", temp[3]);
	//
	// incomeitemIncreaseData.setIncome_ret_money(Double.parseDouble(temp[4]));
	//
	// mapVo.put("income_ret_money", temp[4]);
	//
	// AphiIncomeitemIncreaseData iiid =
	// aphiIncomeitemIncreaseDataService.queryIncomeitemIncreaseDataByCode(mapVo);
	//
	// if (iiid != null) {
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// AphiIncomeItem ii =
	// aphiIncomeItem1_1Service.queryIncomeItemByCode1_1(mapVo);
	//
	// if (ii == null) {
	//
	// err_sb.append("收入项目不存在");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// incomeitemIncreaseData.setError_type(err_sb.toString());
	//
	// list2.add(incomeitemIncreaseData);
	//
	// } else {
	// String chargeItemDictJson =
	// aphiIncomeitemIncreaseDataService.addIncomeitemIncreaseData(mapVo);
	//
	// }
	//
	// }
	//
	// } catch (DataAccessException e) {
	// AphiIncomeitemIncreaseData dkd = new AphiIncomeitemIncreaseData();
	//
	// dkd.setError_type("导入系统出错");
	//
	// list2.add(dkd);
	//
	// response.getWriter().print(JsonListBeanUtil.listToJson(list2,
	// list2.size()));
	//
	// return null;
	// }
	//
	// mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(list2,
	// list2.size()));
	//
	// return "/hrp/hpm/hpmincomeitemincreasedata/hpmIncomeitemIncreaseDataImportMessage";
	//
	// }
	//
	// @RequestMapping(value =
	// "/hrp/hpm/hpmincomeitemincreasedata/addBatchIncomeitemIncreaseDataDict", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object>
	// addBatchIncomeitemIncreaseDataDict(@RequestParam(value = "ParamVo")
	// String paramVo, Model mode, HttpServletResponse response)
	// throws Exception {
	//
	// List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
	//
	// List<AphiIncomeitemIncreaseData> list_err = new
	// ArrayList<AphiIncomeitemIncreaseData>();
	//
	// JSONArray json = JSONArray.parseArray(paramVo);
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// String s = null;
	//
	// Iterator it = json.iterator();
	// try {
	// while (it.hasNext()) {
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// AphiIncomeitemIncreaseData incomeitemIncreaseData = new
	// AphiIncomeitemIncreaseData();
	//
	// JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
	//
	// // Set<String> key = jsonObj.keySet();
	//
	// mapVo.put("group_id", SessionManager.getGroupId());
	// mapVo.put("hos_id", SessionManager.getHosId());
	// mapVo.put("copy_code", SessionManager.getCopyCode());
	//
	// mapVo.put("acct_year", jsonObj.get("acct_year"));
	//
	// mapVo.put("acct_month", jsonObj.get("acct_month"));
	//
	// mapVo.put("income_item_code", jsonObj.get("income_item_code"));
	//
	// mapVo.put("income_money", jsonObj.get("income_money"));
	//
	// mapVo.put("income_ret_money", jsonObj.get("income_ret_money"));
	//
	// AphiIncomeitemIncreaseData iiid =
	// aphiIncomeitemIncreaseDataService.queryIncomeitemIncreaseDataByCode(mapVo);
	//
	// if (iiid != null) {
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// AphiIncomeItem ii =
	// aphiIncomeItem1_1Service.queryIncomeItemByCode1_1(mapVo);
	//
	// if (ii == null) {
	//
	// err_sb.append("收入项目不存在");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// incomeitemIncreaseData.setAcct_year((String) jsonObj.get("acct_year"));
	//
	// incomeitemIncreaseData.setAcct_month((String) jsonObj.get("acct_month"));
	//
	// incomeitemIncreaseData.setIncome_item_code((String)
	// jsonObj.get("income_item_code"));
	//
	// incomeitemIncreaseData.setIncome_money(Double.parseDouble(jsonObj.get("income_money").toString()));
	//
	// incomeitemIncreaseData.setIncome_ret_money(Double.parseDouble(jsonObj.get("income_ret_money").toString()));
	//
	// incomeitemIncreaseData.setError_type(err_sb.toString());
	//
	// list_err.add(incomeitemIncreaseData);
	// } else {
	//
	// s = aphiIncomeitemIncreaseDataService.addIncomeitemIncreaseData(mapVo);
	//
	// }
	// }
	//
	// } catch (DataAccessException e) {
	//
	// return
	// JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
	//
	// }
	//
	// if (list_err.size() > 0) {
	//
	// return JSONObject.parseObject(JsonListBeanUtil.listToJson(list_err,
	// list_err.size()));
	//
	// } else {
	//
	// return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
	//
	// }
	//
	// }

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemincreasedata/downTemplateIncomeitemIncreaseData", method = RequestMethod.GET)
	public String downTemplateIncomeitemIncreaseData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		printTemplate(request, response, "htc\\数据准备\\总额预算计将模式", "全院收入数据准备.xls");
		return null;
	}

}
