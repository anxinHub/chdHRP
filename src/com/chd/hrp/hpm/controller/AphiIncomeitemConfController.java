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
import com.chd.hrp.hpm.service.AphiIncomeitemConfService;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiIncomeitemConfController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiIncomeitemConfController.class);

	@Resource(name = "aphiIncomeitemConfService")
	private final AphiIncomeitemConfService aphiIncomeitemConfService = null;

	// @Resource(name = "aphiDeptService")
	// private final AphiDeptService aphiDeptService = null;
	//
	// @Resource(name = "aphiIncomeItemService")
	// private final AphiIncomeItemService aphiIncomeItemService = null;

	// 维护页面跳转
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfMainPage01",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfMainPage03",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfMainPage04" }, method = RequestMethod.GET)
	public String hpmIncomeitemConfMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfMain";

	}

	// 添加页面
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfAddPage01",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfAddPage03",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfAddPage04" }, method = RequestMethod.GET)
	public String hpmIncomeitemConfAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfAdd";

	}

	// 保存
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/addHpmIncomeitemConf01",
			"/hrp/hpm/hpmincomeitemconf/addHpmIncomeitemConf03",
			"/hrp/hpm/hpmincomeitemconf/addHpmIncomeitemConf04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addIncomeitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		// mapVo.put("dept_id", mapVo.get("dept_id").toString().split(",")[0]);
		// mapVo.put("dept_no", mapVo.get("dept_id").toString().split(",")[1]);
		String incomeitemConfJson = aphiIncomeitemConfService.addIncomeitemConf(mapVo);

		return JSONObject.parseObject(incomeitemConfJson);

	}

	// 查询
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/queryHpmIncomeitemConf01",
			"/hrp/hpm/hpmincomeitemconf/queryHpmIncomeitemConf03",
			"/hrp/hpm/hpmincomeitemconf/queryHpmIncomeitemConf04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmIncomeitemConf(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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

		String incomeitemConf = aphiIncomeitemConfService.queryIncomeitemConf(getPage(mapVo));

		return JSONObject.parseObject(incomeitemConf);

	}

	// 删除
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/deleteHpmIncomeitemConf01",
			"/hrp/hpm/hpmincomeitemconf/deleteHpmIncomeitemConf03",
			"/hrp/hpm/hpmincomeitemconf/deleteHpmIncomeitemConf04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmIncomeitemConf(
			@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {
		Map mapVo = new HashMap();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String incomeitemConfJson = aphiIncomeitemConfService.deleteIncomeitemConf(mapVo, checkIds);

		return JSONObject.parseObject(incomeitemConfJson);
	}

	// // 生成
	// @RequestMapping(value = {
	// "/hrp/hpm/hpmincomeitemconf/createHpmIncomeitemConf01",
	// "/hrp/hpm/hpmincomeitemconf/createHpmIncomeitemConf03"
	// }, method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> createHpmIncomeitemConf(@RequestParam Map<String,
	// Object> mapVo, Model mode) throws Exception {
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
	// String incomeitemConfJson =
	// aphiIncomeitemConfService.createIncomeitemConf(mapVo);
	//
	// return JSONObject.parseObject(incomeitemConfJson);
	//
	// }

	// 修改页面跳转
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfUpdatePage01",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfUpdatePage03",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfUpdatePage04" }, method = RequestMethod.GET)
	public String incomeitemConfUpdate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AphiIncomeitemConf incomeitemConf = new AphiIncomeitemConf();

		incomeitemConf = aphiIncomeitemConfService.queryIncomeitemConfByCode(mapVo);

		if (incomeitemConf != null) {

			mode.addAttribute("dept_id", incomeitemConf.getDept_id());
			mode.addAttribute("dept_no", incomeitemConf.getDept_no());
			mode.addAttribute("income_item_code", incomeitemConf.getIncome_item_code());

			mode.addAttribute("dept_name", incomeitemConf.getDept_name());

			mode.addAttribute("income_item_name", incomeitemConf.getIncome_item_name());

			mode.addAttribute("is_acc", incomeitemConf.getIs_acc());

			mode.addAttribute("order_perc", incomeitemConf.getOrder_perc());

			mode.addAttribute("perform_perc", incomeitemConf.getPerform_perc());

			mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		}

		return "hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfUpdate";

	}

	// 修改保存
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/updateHpmIncomeitemConf01",
			"/hrp/hpm/hpmincomeitemconf/updateHpmIncomeitemConf03",
			"/hrp/hpm/hpmincomeitemconf/updateHpmIncomeitemConf04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmIncomeitemConf(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String incomeitemConfJson = aphiIncomeitemConfService.updateIncomeitemConf(mapVo);

		return JSONObject.parseObject(incomeitemConfJson);
	}

	// 生成
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/createHpmIncomeitemConf01",
			"/hrp/hpm/hpmincomeitemconf/createHpmIncomeitemConf03",
			"/hrp/hpm/hpmincomeitemconf/createHpmIncomeitemConf04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmIncomeitemConf(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String json = aphiIncomeitemConfService.createIncomeitemConf(mapVo);

		return JSONObject.parseObject(json);

	}

	// 复制页面
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/hpmIncomeItemConfCopyPage01",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeItemConfCopyPage03",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeItemConfCopyPage04" }, method = RequestMethod.GET)
	public String incomeItemConfCopy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmincomeitemconf/hpmIncomeItemConfCopy";

	}

	// 复制页面保存
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/copyHpmIncomeItemConf01",
			"/hrp/hpm/hpmincomeitemconf/copyHpmIncomeItemConf03",
			"/hrp/hpm/hpmincomeitemconf/copyHpmIncomeItemConf04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyHpmIncomeitemConf(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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

		String incomeitemConfJson = aphiIncomeitemConfService.copyIncomeitemConf(mapVo);

		return JSONObject.parseObject(incomeitemConfJson);

	}

	// /** 上传处理方法 */
	// @RequestMapping(value = "/hpm/income/incomeitem/readIncomeItemConfFiles",
	// method = RequestMethod.POST)
	// public String readIncomeItemConfFiles(Plupload plupload, HttpServletRequest
	// request, HttpServletResponse response, Model mode) throws IOException {
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// List<String[]> list = UploadUtil.readFile(plupload, request, response);
	//
	// List<AphiIncomeitemConf> errorList = new ArrayList<AphiIncomeitemConf>();
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
	// try {
	//
	// for (int i = 1; i < list.size(); i++) {
	//
	// AphiIncomeitemConf IncomeitemConf = new AphiIncomeitemConf();
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// String temp[] = list.get(i);
	//
	// if (StringUtils.isNotEmpty(temp[0])) {
	//
	// IncomeitemConf.setDept_code((temp[0]));
	//
	// mapVo.put("dept_code", temp[0]);
	//
	// } else {
	//
	// err_sb.append("科室编码不能为空！");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[1])) {
	//
	// IncomeitemConf.setIncome_item_code(temp[1]);
	//
	// mapVo.put("income_item_code", temp[1]);
	//
	// } else {
	//
	// err_sb.append("收入项目编码不能为空！");
	// }
	//
	// if (StringUtils.isNotEmpty(temp[2])) {
	//
	// if (temp[2].equals("1")) {
	//
	// IncomeitemConf.setIs_acc(true);
	// }
	//
	// if (temp[2].equals("0")) {
	//
	// IncomeitemConf.setIs_acc(false);
	// }
	//
	// mapVo.put("is_acc", temp[2]);
	//
	// } else {
	//
	// err_sb.append("是否参与不能为空！");
	// }
	//
	// if (StringUtils.isNotEmpty(temp[3])) {
	//
	// IncomeitemConf.setOrder_perc(Long.parseLong(temp[3]));
	//
	// mapVo.put("perform_perc", temp[3]);
	//
	// } else {
	//
	// err_sb.append("开单权重不能为空！");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[4])) {
	//
	// mapVo.put("order_perc", temp[3]);
	//
	// IncomeitemConf.setPerform_perc(Long.parseLong(temp[4]));
	//
	// } else {
	//
	// err_sb.append("执行权重不能为空！");
	//
	// }
	//
	// AphiDept dept = aphiDeptService.queryDeptByCode(mapVo);
	//
	// if (dept == null) {
	//
	// err_sb.append("科室编码不存在");
	// }
	//
	// AphiIncomeitemConf incomeitemConf =
	// aphiIncomeitemConfService.queryIncomeitemConfByCode(mapVo);
	//
	// if (incomeitemConf != null) {
	//
	// err_sb.append("数据编码已经存在！");
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// IncomeitemConf.setError_type(err_sb.toString());
	//
	// errorList.add(IncomeitemConf);
	//
	// } else {
	//
	// aphiIncomeitemConfService.addIncomeitemConf(mapVo);
	//
	// }
	//
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// AphiIncomeitemConf IncomeitemConf = new AphiIncomeitemConf();
	//
	// IncomeitemConf.setError_type("系统导入出错！");
	//
	// errorList.add(IncomeitemConf);
	//
	// response.getWriter().print(JsonListBeanUtil.listToJson(errorList,
	// errorList.size()));
	//
	// return null;
	//
	// }
	//
	// mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(errorList,
	// errorList.size()));
	//
	// return "/hpm/income/incomeitem/incomeItemConfImportMessage";
	// }
	//
	// @RequestMapping(value = "/hpm/income/incomeitem/addBatchIncomeItemconf",
	// method = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> addBatchIncomeItemconf(@RequestParam(value =
	// "ParamVo") String paramVo, Model mode) throws Exception {
	//
	// List<AphiIncomeitemConf> errorList = new ArrayList<AphiIncomeitemConf>();
	//
	// JSONArray json = JSONArray.parseArray(paramVo);
	//
	// Iterator it = json.iterator();
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
	// try {
	// while (it.hasNext()) {
	//
	// AphiIncomeitemConf IncomeitemConf = new AphiIncomeitemConf();
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
	//
	// mapVo.put("dept_code", jsonObj.get("dept_code"));
	//
	// mapVo.put("income_item_code", jsonObj.get("income_item_code"));
	//
	// mapVo.put("is_acc", jsonObj.get("is_acc"));
	//
	// mapVo.put("order_perc", jsonObj.get("order_perc"));
	//
	// mapVo.put("perform_perc", jsonObj.get("perform_perc"));
	//
	// AphiDept dept = aphiDeptService.queryDeptByCode(mapVo);
	//
	// if (dept == null) {
	//
	// err_sb.append("科室编码不存在");
	// }
	//
	// AphiIncomeitemConf incomeitemConf =
	// aphiIncomeitemConfService.queryIncomeitemConfByCode(mapVo);
	//
	// if (incomeitemConf != null) {
	//
	// err_sb.append("数据编码已经存在！");
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// IncomeitemConf.setDept_code(jsonObj.get("dept_code").toString());
	//
	// IncomeitemConf.setIncome_item_code(jsonObj.get("income_item_code").toString());
	//
	// if ("true".equals(String.valueOf(jsonObj.get("is_acc")))) {
	//
	// IncomeitemConf.setIs_acc(true);
	//
	// }
	//
	// if ("false".equals(String.valueOf(jsonObj.get("is_acc")))) {
	//
	// IncomeitemConf.setIs_acc(false);
	//
	// }
	//
	// IncomeitemConf.setOrder_perc(Long.parseLong(jsonObj.get("order_perc").toString()));
	//
	// IncomeitemConf.setPerform_perc(Long.parseLong(jsonObj.get("perform_perc").toString()));
	//
	// IncomeitemConf.setError_type(err_sb.toString());
	//
	// errorList.add(IncomeitemConf);
	//
	// } else {
	//
	// aphiIncomeitemConfService.addIncomeitemConf(mapVo);
	// }
	//
	// }
	// } catch (Exception e) {
	// // TODO: handle exception
	// return
	// JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
	// }
	//
	// if (errorList.size() > 0) {
	//
	// return JSONObject.parseObject(JsonListBeanUtil.listToJson(errorList,
	// errorList.size()));
	//
	// } else {
	//
	// return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
	//
	// }
	//
	// }
	//
	// // 下载导入模板
	// // @RequestMapping(value="/hpm/income/incomeitem/downTemplate", method =
	// // RequestMethod.GET)
	// // public String downTemplate(Plupload plupload,HttpServletRequest request,
	// // HttpServletResponse response,Model mode) throws IOException {
	// //
	// printTemplate(request,response,"hpm\\方案制定\\收支结余计将模式\\收入项目维护","收入项目配置.xlsx");
	// // return null;
	// // }

	// 导入
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfImportPage01",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfImportPage03",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfImportPage04" }, method = RequestMethod.GET)
	public String hpmIncomeitemConfImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmincomeitemconf/hpmIncomeItemConfImport";
	}

	// 导入跳转
	@RequestMapping(value = { "/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfImport01",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfImport03",
			"/hrp/hpm/hpmincomeitemconf/hpmIncomeitemConfImport04" }, method = RequestMethod.POST)
	@ResponseBody
	public String hpmIncomeitemConfImport(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			String impJson = aphiIncomeitemConfService.hpmIncomeitemConfImport(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemconf/downTemplateHpmIncomeitemConf", method = RequestMethod.GET)
	public String downTemplateIncomeitemData(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\downTemplate", "收入项目配置.xlsx");

		return null;
	}

}
