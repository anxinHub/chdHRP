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
import com.chd.hrp.hpm.entity.AphiIncomeitemPointData;
import com.chd.hrp.hpm.service.AphiIncomeitemPointDataService;

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
public class AphiIncomeitemPointDataController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiIncomeitemPointDataController.class);

	@Resource(name = "aphiIncomeitemPointDataService")
	private final AphiIncomeitemPointDataService aphiIncomeitemPointDataService = null;

//	@Resource(name = "aphiDeptService")
//	private final AphiDeptService aphiDeptService = null;
//
//	@Resource(name = "aphiIncomeItem1_1Service")
//	private final AphiIncomeItemService aphiIncomeItem1_1Service = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/hpmIncomeitemPointDataMainPage", method = RequestMethod.GET)
	public String incomeitemPointDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitempointdata/hpmIncomeitemPointDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/hpmIncomeitemPointDataAddPage", method = RequestMethod.GET)
	public String incomeitemPointDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitempointdata/hpmIncomeitemPointDataAdd";

	}

	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/hpmIncomeitemPointDataImportPage", method = RequestMethod.GET)
	public String incomeitemPointDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitempointdata/hpmIncomeitemPointDataImport";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/addHpmIncomeitemPointData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmIncomeitemPointData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemDataJson = aphiIncomeitemPointDataService.addIncomeitemPointData(mapVo);

		return JSONObject.parseObject(incomeitemDataJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/initHpmIncomeitemPointData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmIncomeitemPointData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String hospTargetDataJson = aphiIncomeitemPointDataService.createIncomeitemPointData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);
	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/queryHpmIncomeitemPointData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmIncomeitemPointData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String incomeitemData = aphiIncomeitemPointDataService.queryIncomeitemPointData(getPage(mapVo));

		return JSONObject.parseObject(incomeitemData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/deleteHpmIncomeitemPointData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmIncomeitemPointData(@RequestParam String checkIds, Model mode) throws Exception {

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

		String incomeitemDataJson = aphiIncomeitemPointDataService.deleteIncomeitemPointData(mapVo, checkIds);

		return JSONObject.parseObject(incomeitemDataJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/hpmIncomeitemPointDataUpdatePage", method = RequestMethod.GET)
	public String incomeitemPointDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AphiIncomeitemPointData incomeitemPointData = new AphiIncomeitemPointData();

		incomeitemPointData = aphiIncomeitemPointDataService.queryIncomeitemPointDataByCode(mapVo);

		// mode.addAttribute("group_id", incomeitemPointData.getGroupId());
		// mode.addAttribute("hos_id", incomeitemPointData.getHosId());
		mode.addAttribute("copy_code", incomeitemPointData.getCopy_code());

		mode.addAttribute("acct_year", incomeitemPointData.getAcct_year());

		mode.addAttribute("acct_month", incomeitemPointData.getAcct_month());

		mode.addAttribute("dept_id", incomeitemPointData.getDept_id());

		mode.addAttribute("income_item_code", incomeitemPointData.getIncome_item_code());

		mode.addAttribute("dept_name", incomeitemPointData.getDept_name());

		mode.addAttribute("income_item_name", incomeitemPointData.getIncome_item_name());

		mode.addAttribute("work_amount", incomeitemPointData.getWork_amount());

		mode.addAttribute("order_ret_money", incomeitemPointData.getWork_point());

		return "hrp/hpm/hpmincomeitempointdata/hpmIncomeitemPointDataUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/updateHpmIncomeitemPointData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmIncomeitemPointData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemDataJson = aphiIncomeitemPointDataService.updateIncomeitemPointData(mapVo);

		return JSONObject.parseObject(incomeitemDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/pointCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> calculate(@RequestParam String paras, Model mode) throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("paras", paras);

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String result = aphiIncomeitemPointDataService.calculate(mapVo);// 将计算结果更新到数据表

		return JSONObject.parseObject(result);

	}

	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/incomeItemPointDataFastPage", method = RequestMethod.GET)
	public String incomeItemPointDataFastPage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		String[] paraArray = paras.split("@");

		mode.addAttribute("acct_yearm", paraArray[0]);

		if (!"null".equals(paraArray[1])) {

			mode.addAttribute("dept_code", paraArray[1]);

		}

		if (!"null".equals(paraArray[2])) {

			mode.addAttribute("work_item_code", paraArray[2]);

		}

		if (!"null".equals(paraArray[3])) {

			mode.addAttribute("dept_kind_code", paraArray[3]);

		}

		return "hrp/hpm/hpmincomeitempointdata/hpmIncomeItemPointDataFast";

	}

//	/**
//	 * 导入
//	 */
//	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/readIncomeitemPointDataFiles", method = RequestMethod.POST)
//	public String readIncomeitemPointDataFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		if (mapVo.get("group_id") == null) {
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//		if (mapVo.get("hos_id") == null) {
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//		if (mapVo.get("copy_code") == null) {
//			mapVo.put("copy_code", SessionManager.getCopyCode());
//		}
//
//		List<AphiIncomeitemPointData> list2 = new ArrayList<AphiIncomeitemPointData>();
//
//		List<String[]> list = UploadUtil.readFile(plupload, request, response);
//
//		// List<Item> errorList = new ArrayList<Item>();
//
//		try {
//
//			for (int i = 1; i < list.size(); i++) {
//
//				StringBuffer err_sb = new StringBuffer();
//
//				AphiIncomeitemPointData incomeitemPointData = new AphiIncomeitemPointData();
//
//				String temp[] = list.get(i);// 行
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					incomeitemPointData.setAcct_year(temp[0]);
//
//					mapVo.put("acct_year", temp[0]);
//
//				} else {
//
//					err_sb.append("核算年度为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[1])) {
//
//					incomeitemPointData.setAcct_month(temp[1]);
//
//					mapVo.put("acct_month", temp[1]);
//
//				} else {
//
//					err_sb.append("核算月份为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[2])) {
//
//					incomeitemPointData.setDept_code(temp[2]);
//
//					mapVo.put("dept_code", temp[2]);
//
//				} else {
//
//					err_sb.append("科室为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[3])) {
//
//					incomeitemPointData.setIncome_item_code(temp[3]);
//
//					mapVo.put("income_item_code", temp[3]);
//
//				} else {
//
//					err_sb.append("收入项目为空  ");
//
//				}
//
//				incomeitemPointData.setWork_amount(Double.parseDouble(temp[4]));
//
//				mapVo.put("work_amount", temp[4]);
//
//				incomeitemPointData.setWork_point(Double.parseDouble(temp[5]));
//
//				mapVo.put("work_point", temp[5]);
//
//				AphiIncomeitemPointData iipd = aphiIncomeitemPointDataService.queryIncomeitemPointDataByCode(mapVo);
//
//				if (iipd != null) {
//
//					err_sb.append("数据编码已经存在！");
//
//				}
//
//				AphiDept dept = aphiDeptService.queryDeptByCode(mapVo);
//
//				if (dept == null) {
//
//					err_sb.append("科室不存在");
//
//				}
//
//				AphiIncomeItem ii = aphiIncomeItem1_1Service.queryIncomeItemByCode1_1(mapVo);
//
//				if (ii == null) {
//
//					err_sb.append("收入项目不存在");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					incomeitemPointData.setError_type(err_sb.toString());
//
//					list2.add(incomeitemPointData);
//
//				} else {
//
//					aphiIncomeitemPointDataService.addIncomeitemPointData(mapVo);
//
//				}
//
//			}
//
//		} catch (DataAccessException e) {
//			AphiIncomeitemPointData cd = new AphiIncomeitemPointData();
//
//			cd.setError_type("导入系统出错");
//
//			list2.add(cd);
//
//			response.getWriter().print(JsonListBeanUtil.listToJson(list2, list2.size()));
//
//			return null;
//		}
//
//		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(list2, list2.size()));
//		return "/hrp/hpm/hpmincomeitempointdata/hpmIncomeitemPointDataImportMessage";
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/addBatchIncomeitemPointDataDict", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchIncomeitemPointDataDict(@RequestParam(value = "ParamVo") String paramVo, Model mode, HttpServletResponse response)
//			throws Exception {
//
//		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
//
//		List<AphiIncomeitemPointData> list_err = new ArrayList<AphiIncomeitemPointData>();
//
//		JSONArray json = JSONArray.parseArray(paramVo);
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		String s = null;
//
//		Iterator it = json.iterator();
//		try {
//			while (it.hasNext()) {
//
//				StringBuffer err_sb = new StringBuffer();
//
//				AphiIncomeitemPointData incomeitemPointData = new AphiIncomeitemPointData();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				// Set<String> key = jsonObj.keySet();
//
//				mapVo.put("group_id", SessionManager.getGroupId());
//				mapVo.put("hos_id", SessionManager.getHosId());
//				mapVo.put("copy_code", SessionManager.getCopyCode());
//
//				mapVo.put("acct_year", jsonObj.get("acct_year"));
//
//				mapVo.put("acct_month", jsonObj.get("acct_month"));
//
//				mapVo.put("dept_code", jsonObj.get("dept_code"));
//
//				mapVo.put("income_item_code", jsonObj.get("income_item_code"));
//
//				mapVo.put("work_amount", jsonObj.get("work_amount"));
//
//				mapVo.put("work_point", jsonObj.get("work_point"));
//
//				AphiIncomeitemPointData iipd = aphiIncomeitemPointDataService.queryIncomeitemPointDataByCode(mapVo);
//
//				if (iipd != null) {
//
//					err_sb.append("数据编码已经存在！");
//
//				}
//
//				AphiDept dept = aphiDeptService.queryDeptByCode(mapVo);
//
//				if (dept == null) {
//
//					err_sb.append("科室不存在");
//
//				}
//
//				AphiIncomeItem ii = aphiIncomeItem1_1Service.queryIncomeItemByCode1_1(mapVo);
//
//				if (ii == null) {
//
//					err_sb.append("收入项目不存在");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					incomeitemPointData.setAcct_year((String) jsonObj.get("acct_year"));
//
//					incomeitemPointData.setAcct_month((String) jsonObj.get("acct_month"));
//
//					incomeitemPointData.setDept_code((String) jsonObj.get("dept_code"));
//
//					incomeitemPointData.setIncome_item_code((String) jsonObj.get("income_item_code"));
//
//					incomeitemPointData.setWork_amount(Double.parseDouble(jsonObj.get("work_amount").toString()));
//
//					incomeitemPointData.setWork_point(Double.parseDouble(jsonObj.get("work_point").toString()));
//
//					incomeitemPointData.setError_type(err_sb.toString());
//
//					list_err.add(incomeitemPointData);
//
//				} else {
//
//					s = aphiIncomeitemPointDataService.addIncomeitemPointData(mapVo);
//
//				}
//			}
//
//		} catch (DataAccessException e) {
//
//			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
//
//		}
//
//		if (list_err.size() > 0) {
//
//			return JSONObject.parseObject(JsonListBeanUtil.listToJson(list_err, list_err.size()));
//
//		} else {
//
//			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
//
//		}
//
//	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempointdata/downTemplateIncomeitemPointData", method = RequestMethod.GET)
	public String downTemplateIncomeitemPointData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\数据准备\\RBRVS计将模式", "核算项目点数准备.xls");
		return null;
	}

}
