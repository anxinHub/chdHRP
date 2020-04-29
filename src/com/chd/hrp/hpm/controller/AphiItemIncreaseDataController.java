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
import com.chd.hrp.hpm.entity.AphiItemIncreaseData;
import com.chd.hrp.hpm.entity.AphiItemIncreasePercConf;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiItemIncreaseDataService;

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
public class AphiItemIncreaseDataController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiItemIncreaseDataController.class);

	@Resource(name = "aphiItemIncreaseDataService")
	private final AphiItemIncreaseDataService aphiItemIncreaseDataService = null;

	//
	// @Resource(name = "aphiItemService")
	// private final AphiItemService aphiItemService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataMainPage", method = RequestMethod.GET)
	public String itemIncreaseDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataAddPage", method = RequestMethod.GET)
	public String itemIncreaseDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataAdd";

	}

	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataImportPage", method = RequestMethod.GET)
	public String itemIncreaseDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataImport";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/addHpmItemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmItemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String itemIncreaseDataJson = aphiItemIncreaseDataService.addItemIncreaseData(mapVo);

		return JSONObject.parseObject(itemIncreaseDataJson);

	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/initHpmItemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmItemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String hospTargetDataJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		hospTargetDataJson = aphiItemIncreaseDataService.initItemIncreaseData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/queryHpmItemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmItemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String itemIncreaseData = aphiItemIncreaseDataService.queryItemIncreaseData(getPage(mapVo));

		return JSONObject.parseObject(itemIncreaseData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/deleteHpmItemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmItemIncreaseData(@RequestParam String checkIds, Model mode) throws Exception {

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

		String itemIncreaseDataJson = aphiItemIncreaseDataService.deleteItemIncreaseData(mapVo, checkIds);

		return JSONObject.parseObject(itemIncreaseDataJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataUpdatePage", method = RequestMethod.GET)
	public String itemIncreaseDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AphiItemIncreaseData itemIncreaseData = new AphiItemIncreaseData();

		itemIncreaseData = aphiItemIncreaseDataService.queryItemIncreaseDataByCode(mapVo);

		// mode.addAttribute("group_id", itemIncreaseData.getGroupId());
		// mode.addAttribute("hos_id", itemIncreaseData.getHosId());
		mode.addAttribute("copy_code", itemIncreaseData.getCopy_code());

		mode.addAttribute("acct_year", itemIncreaseData.getAcct_year());

		mode.addAttribute("acct_month", itemIncreaseData.getAcct_month());

		mode.addAttribute("item_code", itemIncreaseData.getItem_code());

		mode.addAttribute("item_name", itemIncreaseData.getItem_name());

		mode.addAttribute("last_money", itemIncreaseData.getLast_money());

		mode.addAttribute("this_money", itemIncreaseData.getThis_money());

		return "hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/updateHpmItemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmItemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String itemIncreaseDataJson = aphiItemIncreaseDataService.updateItemIncreaseData(mapVo);

		return JSONObject.parseObject(itemIncreaseDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> itemIncreaseDataCalculate(@RequestParam String checkIds, Model mode) throws Exception {

		String result = "";

		double this_money = 0;

		for (String ids : checkIds.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String item_code = ids.split(";")[0];

			String acct_year = ids.split(";")[1];

			String acct_month = ids.split(";")[2];

			String last_money = ids.split(";")[3];

			mapVo.put("item_code", item_code);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			AphiSchemeConf sc = aphiItemIncreaseDataService.getSchemeSeqNo(mapVo);// 通过年月获得scheme_seq_no

			mapVo.put("scheme_seq_no", sc.getScheme_seq_no());

			AphiItemIncreasePercConf itemIncreasePercConf = aphiItemIncreaseDataService.getItemIncreasePercConfByCode(mapVo);

			if (itemIncreasePercConf != null) {

				this_money = Double.parseDouble(last_money) * itemIncreasePercConf.getIncrease_percent();

			}

			mapVo.put("this_money", this_money);

			result = aphiItemIncreaseDataService.calculate(mapVo);// 将计算结果更新到数据表
		}

		return JSONObject.parseObject(result);
	}

	// /**
	// * 导入
	// */
	// @RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/readItemIncreaseDataFiles", method =
	// RequestMethod.POST)
	// public String readItemIncreaseDataFiles(Plupload plupload,
	// HttpServletRequest request, HttpServletResponse response, Model mode)
	// throws IOException {
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
	// List<AphiItemIncreaseData> list2 = new ArrayList<AphiItemIncreaseData>();
	//
	// List<String[]> list = UploadUtil.readFile(plupload, request, response);
	//
	// try {
	//
	// for (int i = 1; i < list.size(); i++) {
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// AphiItemIncreaseData itemIncreaseData = new AphiItemIncreaseData();
	//
	// String temp[] = list.get(i);// 行
	//
	// if (temp[2].length() == 1) {
	//
	// temp[2] = "0" + temp[2];
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[0])) {
	//
	// itemIncreaseData.setAcct_year(temp[0]);
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
	// itemIncreaseData.setAcct_month(temp[1]);
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
	// itemIncreaseData.setItem_code(temp[2]);
	//
	// mapVo.put("item_code", temp[2]);
	//
	// } else {
	//
	// err_sb.append("奖金项目为空 ");
	//
	// }
	//
	// itemIncreaseData.setLast_money(Double.parseDouble(temp[3]));
	//
	// mapVo.put("last_money", temp[3]);
	//
	// itemIncreaseData.setThis_money(Double.parseDouble(temp[4]));
	//
	// mapVo.put("this_money", temp[4]);
	//
	// AphiItemIncreaseData iid =
	// aphiItemIncreaseDataService.queryItemIncreaseDataByCode(mapVo);
	//
	// if (iid != null) {
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// AphiItem item = aphiItemService.queryItemByCode(mapVo);
	//
	// if (item == null) {
	//
	// err_sb.append("奖金项目不存在");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// itemIncreaseData.setError_type(err_sb.toString());
	//
	// list2.add(itemIncreaseData);
	//
	// } else {
	// String chargeItemDictJson =
	// aphiItemIncreaseDataService.addItemIncreaseData(mapVo);
	//
	// }
	//
	// }
	//
	// } catch (DataAccessException e) {
	// AphiItemIncreaseData dkd = new AphiItemIncreaseData();
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
	// return "/hrp/hpm/hpmitemincreasedata/hpmItemIncreaseDataImportMessage";
	//
	// }
	//
	// @RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/addBatchItemIncreaseDataDict", method
	// = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object>
	// addBatchItemIncreaseDataDict(@RequestParam(value = "ParamVo") String
	// paramVo, Model mode, HttpServletResponse response)
	// throws Exception {
	//
	// List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
	//
	// List<AphiItemIncreaseData> list_err = new
	// ArrayList<AphiItemIncreaseData>();
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
	// AphiItemIncreaseData itemIncreaseData = new AphiItemIncreaseData();
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
	// mapVo.put("item_code", jsonObj.get("item_code"));
	//
	// mapVo.put("last_money", jsonObj.get("last_money"));
	//
	// mapVo.put("this_money", jsonObj.get("this_money"));
	//
	// AphiItemIncreaseData iid =
	// aphiItemIncreaseDataService.queryItemIncreaseDataByCode(mapVo);
	//
	// if (iid != null) {
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// AphiItem item = aphiItemService.queryItemByCode(mapVo);
	//
	// if (item == null) {
	//
	// err_sb.append("奖金项目不存在");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// itemIncreaseData.setAcct_year((String) jsonObj.get("acct_year"));
	//
	// itemIncreaseData.setAcct_month((String) jsonObj.get("acct_month"));
	//
	// itemIncreaseData.setItem_code((String) jsonObj.get("item_code"));
	//
	// itemIncreaseData.setLast_money(Double.parseDouble(jsonObj.get("last_money").toString()));
	//
	// itemIncreaseData.setThis_money(Double.parseDouble(jsonObj.get("this_money").toString()));
	//
	// itemIncreaseData.setError_type(err_sb.toString());
	//
	// list_err.add(itemIncreaseData);
	// } else {
	//
	// s = aphiItemIncreaseDataService.addItemIncreaseData(mapVo);
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
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasedata/downTemplateItemIncreaseData", method = RequestMethod.GET)
	public String downTemplateItemIncreaseData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\数据准备\\总额预算计将模式", "历史奖金数据准备.xls");
		return null;
	}

}
