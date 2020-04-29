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
import com.chd.hrp.hpm.entity.AphiDeptKindScheme;

/**
 * alfred
 */

@Controller
public class AphiDeptKindSchemeController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptKindSchemeController.class);

//	@Resource(name = "aphiDeptKindSchemeService")
//	private final AphiDeptKindSchemeService aphiDeptKindSchemeService = null;
//
//	// @Resource(name = "aphiDeptKindService")
//	// private final AphiDeptKindService aphiDeptKindService = null;
//	//
//	// @Resource(name = "aphiItemService")
//	// private final AphiItemService aphiItemService = null;
//	//
//	// @Resource(name = "aphiHpmTargetMethodParaService")
//	// private final AphiHpmTargetMethodParaService
//	// aphiHpmTargetMethodParaService = null;
//	//
//	// @Resource(name = "aphiHpmFormulaService")
//	// private final AphiHpmFormulaService aphiHpmFormulaService = null;
//
//	// 维护页面跳转
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/hpmDeptKindSchemeMainPage", method = RequestMethod.GET)
//	public String deptKindSchemeMainPage(Model mode) throws Exception {
//
//		return "hrp/hpm/hpmdeptkindscheme/hpmDeptKindSchemeMain";
//
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/hpmDeptKindSchemeImportPage", method = RequestMethod.GET)
//	public String deptKindSchemeImportPage(Model mode) throws Exception {
//
//		return "hrp/hpm/hpmdeptkindscheme/hpmDeptKindSchemeImport";
//
//	}
//
//	// 添加页面
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/hpmDeptKindSchemeAddPage", method = RequestMethod.GET)
//	public String deptKindSchemeAddPage(Model mode) throws Exception {
//
//		return "hrp/hpm/hpmdeptkindscheme/hpmDeptKindSchemeAdd";
//
//	}
//
//	// 保存
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/addHpmDeptKindScheme", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addDeptKindScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		if (mapVo.get("group_id") == null) {
//
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//
//		if (mapVo.get("hos_id") == null) {
//
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//
//		mapVo.put("copy_code", COPY_CODE);
//
//		String deptKindSchemeJson = aphiDeptKindSchemeService.addDeptKindScheme(mapVo);
//
//		return JSONObject.parseObject(deptKindSchemeJson);
//
//	}
//
//	// 查询
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/queryHpmDeptKindScheme", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> queryDeptKindScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		if (mapVo.get("group_id") == null) {
//
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//
//		if (mapVo.get("hos_id") == null) {
//
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//
//		mapVo.put("copy_code", COPY_CODE);
//
//		String deptKindScheme = aphiDeptKindSchemeService.queryDeptKindScheme(getPage(mapVo));
//
//		return JSONObject.parseObject(deptKindScheme);
//
//	}
//
//	// 删除
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/deleteHpmDeptKindScheme", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> deleteDeptKindScheme(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		Map map = new HashMap();
//
//		if (map.get("group_id") == null) {
//
//			map.put("group_id", SessionManager.getGroupId());
//		}
//
//		if (map.get("hos_id") == null) {
//
//			map.put("hos_id", SessionManager.getHosId());
//		}
//
//		map.put("copy_code", COPY_CODE);
//
//		String deptKindSchemeJson = aphiDeptKindSchemeService.deleteDeptKindScheme(map, checkIds);
//
//		return JSONObject.parseObject(deptKindSchemeJson);
//	}
//
//	// 修改页面跳转
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/hpmDeptKindSchemeUpdatePage", method = RequestMethod.GET)
//	public String deptKindSchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		if (mapVo.get("group_id") == null) {
//
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//
//		if (mapVo.get("hos_id") == null) {
//
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//
//		mapVo.put("copy_code", COPY_CODE);
//
//		AphiDeptKindScheme deptKindScheme = aphiDeptKindSchemeService.queryDeptKindSchemeByCode(mapVo);
//
//		mode.addAttribute("dept_kind_code", deptKindScheme.getDept_kind_code());
//
//		mode.addAttribute("item_code", deptKindScheme.getItem_code());
//
//		mode.addAttribute("method_code", deptKindScheme.getMethod_code());
//
//		mode.addAttribute("formula_code", deptKindScheme.getFormula_code());
//
//		mode.addAttribute("fun_code", deptKindScheme.getFun_code());
//
//		mode.addAttribute("item_name", deptKindScheme.getItem_name());
//
//		mode.addAttribute("item_note", deptKindScheme.getItem_note());
//
//		mode.addAttribute("method_name", deptKindScheme.getMethod_name());
//
//		mode.addAttribute("formula_name", deptKindScheme.getFormula_name());
//
//		mode.addAttribute("fun_name", deptKindScheme.getFun_name());
//
//		mode.addAttribute("dept_kind_name", deptKindScheme.getDept_kind_name());
//
//		return "hrp/hpm/hpmdeptkindscheme/hpmDeptKindSchemeUpdate";
//	}
//
//	// 修改保存
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/updateHpmDeptKindScheme", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> updateDeptKindScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		if (mapVo.get("group_id") == null) {
//
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//
//		if (mapVo.get("hos_id") == null) {
//
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//
//		mapVo.put("copy_code", COPY_CODE);
//
//		String deptKindSchemeJson = aphiDeptKindSchemeService.updateDeptKindScheme(mapVo);
//
//		return JSONObject.parseObject(deptKindSchemeJson);
//	}

	// /**
	// * 导入
	// */
	// @RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/readDeptKindSchemeFiles",
	// method = RequestMethod.POST)
	// public String readDeptKindSchemeFiles(Plupload plupload,
	// HttpServletRequest request, HttpServletResponse response, Model mode)
	// throws IOException {
	//
	// String COPY_CODE = SessionManager.getCopyCode();
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// if(mapVo.get("group_id") == null){
	//
	// mapVo.put("group_id", SessionManager.getGroupId());
	// }
	//
	// if(mapVo.get("hos_id") == null){
	//
	// mapVo.put("hos_id", SessionManager.getHosId());
	// }
	//
	// mapVo.put("copy_code", COPY_CODE);
	//
	// List<AphiDeptKindScheme> list2 = new ArrayList<AphiDeptKindScheme>();
	//
	// List<String[]> list = UploadUtil.readFile(plupload, request, response);
	//
	// // List<Item> errorList = new ArrayList<Item>();
	//
	// try {
	//
	// for (int i = 1; i < list.size(); i++) {
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// AphiDeptKindScheme deptKindScheme = new AphiDeptKindScheme();
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
	// deptKindScheme.setDept_kind_code(temp[0]);
	//
	// mapVo.put("dept_kind_code", temp[0]);
	//
	// } else {
	//
	// err_sb.append("科室分类名称为空  ");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[1])) {
	//
	// deptKindScheme.setItem_code(temp[1]);
	//
	// mapVo.put("item_code", temp[1]);
	//
	// } else {
	//
	// err_sb.append("奖金项目编码为空  ");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[2])) {
	//
	// deptKindScheme.setMethod_code(temp[2]);
	//
	// mapVo.put("method_code", temp[2]);
	//
	// } else {
	//
	// err_sb.append("取值方法为空  ");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[3])) {
	//
	// deptKindScheme.setFormula_code(temp[3]);
	//
	// mapVo.put("formula_code", temp[3]);
	//
	// } else {
	//
	// err_sb.append("取值公式为空  ");
	//
	// }
	// if (StringUtils.isNotEmpty(temp[4])) {
	//
	// deptKindScheme.setFun_code(temp[4]);
	//
	// mapVo.put("fun_code", temp[4]);
	//
	// } else {
	//
	// err_sb.append("取值方法为空  ");
	//
	// }
	//
	// AphiDeptKindScheme dks
	// =aphiDeptKindSchemeService.queryDeptKindSchemeByCode(mapVo);
	//
	// if (dks != null) {
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
	// AphiDeptKind deptKind = aphiDeptKindService.queryDeptKindByCode(mapVo);
	//
	// if(deptKind == null){
	//
	// err_sb.append("科室名称不存在");
	//
	// }
	//
	// // AphiHpmTargetMethodPara hpmTargetMethodPara =
	// aphiHpmTargetMethodParaService.queryTargetMethodParaByCode(mapVo);
	// //
	// // if(hpmTargetMethodPara == null){
	// //
	// // err_sb.append("取值方法不存在");
	// //
	// // }
	// //
	// //
	// // AphiHpmFormula hpmFormula =
	// aphiHpmFormulaService.queryFormulaByCode(mapVo);
	// //
	// // if(hpmFormula == null){
	// //
	// // err_sb.append("取值公式不存在");
	// //
	// // }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// deptKindScheme.setError_type(err_sb.toString());
	//
	// list2.add(deptKindScheme);
	//
	// } else {
	//
	// aphiDeptKindSchemeService.addDeptKindScheme(mapVo);
	//
	// }
	//
	// }
	//
	// } catch (DataAccessException e) {
	//
	// AphiDeptKindScheme cd = new AphiDeptKindScheme();
	//
	// cd.setError_type("导入系统出错");
	//
	// list2.add(cd);
	//
	// response.getWriter().print(JsonListBeanUtil.listToJson(list2,
	// list2.size()));
	//
	// return null;
	// }
	//
	// mode.addAttribute("resultsJson",JsonListBeanUtil.listToJson(list2,
	// list2.size()));
	// return "/hrp/hpm/hpmdeptkindscheme/hpmDeptKindSchemeImportMessage";
	// }
	//
	// @RequestMapping(value =
	// "/hrp/hpm/hpmdeptkindscheme/addBatchDeptKindSchemeDict", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> addBatchDeptKindSchemeDict(
	// @RequestParam(value = "ParamVo") String paramVo, Model mode,
	// HttpServletResponse response) throws Exception {
	//
	// List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
	//
	// List<AphiDeptKindScheme> list_err = new ArrayList<AphiDeptKindScheme>();
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
	// AphiDeptKindScheme deptKindScheme = new AphiDeptKindScheme();
	//
	// JSONObject jsonObj = JSONObject.parseObject(it.next()
	// .toString());
	//
	// // Set<String> key = jsonObj.keySet();
	//
	// if(mapVo.get("group_id") == null){
	//
	// mapVo.put("group_id", SessionManager.getGroupId());
	// }
	//
	// if(mapVo.get("hos_id") == null){
	//
	// mapVo.put("hos_id", SessionManager.getHosId());
	// }
	//
	// mapVo.put("copy_code", SessionManager.getCopyCode());
	//
	// mapVo.put("dept_kind_code", jsonObj.get("dept_kind_code"));
	//
	// mapVo.put("item_code", jsonObj.get("item_code"));
	//
	// mapVo.put("method_code", jsonObj.get("method_code"));
	//
	// mapVo.put("formula_code", jsonObj.get("formula_code"));
	//
	// mapVo.put("fun_code", jsonObj.get("fun_code"));
	//
	// AphiDeptKindScheme dks =
	// aphiDeptKindSchemeService.queryDeptKindSchemeByCode(mapVo);
	//
	// if (dks != null) {
	//
	// err_sb.append("数据编码已经存在！ ");
	// }
	//
	// AphiItem item = aphiItemService.queryItemByCode(mapVo);
	//
	// if (item == null) {
	//
	// err_sb.append("奖金项目不存在;");
	//
	// }
	//
	// AphiDeptKind deptKind = aphiDeptKindService.queryDeptKindByCode(mapVo);
	//
	// if(deptKind == null){
	//
	// err_sb.append("科室分类名称不存在;");
	//
	// }
	//
	// // AphiHpmTargetMethodPara hpmTargetMethodPara =
	// aphiHpmTargetMethodParaService.queryTargetMethodParaByCode(mapVo);
	// //
	// // if(hpmTargetMethodPara == null){
	// //
	// // err_sb.append("取值方法不存在;");
	// //
	// // }
	// //
	// //
	// // AphiHpmFormula hpmFormula =
	// aphiHpmFormulaService.queryFormulaByCode(mapVo);
	// //
	// // if(hpmFormula == null){
	// //
	// // err_sb.append("取值公式不存在;");
	// //
	// // }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// deptKindScheme.setItem_code((String) jsonObj.get("item_code"));
	//
	// deptKindScheme.setDept_kind_code((String) jsonObj.get("dept_kind_code"));
	//
	// deptKindScheme.setMethod_code((String) jsonObj.get("method_code"));
	//
	// deptKindScheme.setFormula_code((String) jsonObj.get("formula_code"));
	//
	// deptKindScheme.setFun_code((String) jsonObj.get("fun_code"));
	//
	// deptKindScheme.setError_type(err_sb.toString());
	//
	// list_err.add(deptKindScheme);
	// } else {
	//
	// s =aphiDeptKindSchemeService.addDeptKindScheme(mapVo);
	//
	// }
	// }
	//
	// } catch (DataAccessException e) {
	//
	// return JSONObject
	// .parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
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
	// return JSONObject
	// .parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
	//
	// }
	//
	// }
	//

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindscheme/downTemplateDeptKindScheme", method = RequestMethod.GET)
	public String downTemplateDeptKindScheme(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\方案制定\\总额预算制计将模式\\核算方案配置", "科室类别核算方案配置.xlsx");
		return null;
	}
}
