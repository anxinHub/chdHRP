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
import com.chd.hrp.hpm.entity.AphiCompanyScheme;

/**
 * alfred
 */

@Controller
public class AphiCompanySchemeController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiCompanySchemeController.class);

//	@Resource(name = "aphiCompanySchemeService")
//	private final AphiCompanySchemeService aphiCompanySchemeService = null;
//
////	@Resource(name = "aphiItemService")
////	private final AphiItemService aphiItemService = null;
//
//	// 维护页面跳转
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/hpmCompanySchemeMainPage", method = RequestMethod.GET)
//	public String companySchemeMainPage(Model mode) throws Exception {
//
//		return "hrp/hpm/hpmcompanyscheme/hpmCompanySchemeMain";
//
//	}
//
//	// 添加页面
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/hpmCompanySchemeAddPage", method = RequestMethod.GET)
//	public String companySchemeAddPage(Model mode) throws Exception {
//
//		return "hrp/hpm/hpmcompanyscheme/hpmCmpanySchemeAdd";
//
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/hpmCompanySchemeImportPage", method = RequestMethod.GET)
//	public String companySchemeImportPage(Model mode) throws Exception {
//
//		return "hrp/hpm/hpmcompanyscheme/hpmCompanySchemeImport";
//
//	}
//
//	// 保存
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/addHpmCompanyScheme", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addCompanyScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
//		String companySchemeJson = aphiCompanySchemeService.addCompanyScheme(mapVo);
//
//		return JSONObject.parseObject(companySchemeJson);
//
//	}
//
//	// 查询
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/queryHpmCompanyScheme", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> queryCompanyScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
//		String companyScheme = aphiCompanySchemeService.queryCompanyScheme(getPage(mapVo));
//
//		return JSONObject.parseObject(companyScheme);
//
//	}
//
//	// 删除
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/deleteHpmCompanyScheme", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> deleteCompanyScheme(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {
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
//		String companySchemeJson = aphiCompanySchemeService.deleteCompanyScheme(map, checkIds);
//
//		return JSONObject.parseObject(companySchemeJson);
//	}
//
//	// 修改页面跳转
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/hpmCompanySchemeUpdatePage", method = RequestMethod.GET)
//	public String companySchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
//		AphiCompanyScheme companyScheme = aphiCompanySchemeService.queryCompanySchemeByCode(mapVo);
//
//		mode.addAttribute("item_code", companyScheme.getItem_code());
//
//		mode.addAttribute("method_code", companyScheme.getMethod_code());
//
//		mode.addAttribute("formula_code", companyScheme.getFormula_code());
//
//		mode.addAttribute("fun_code", companyScheme.getFun_code());
//
//		mode.addAttribute("item_name", companyScheme.getItem_name());
//
//		mode.addAttribute("item_note", companyScheme.getItem_note());
//
//		mode.addAttribute("method_name", companyScheme.getMethod_name());
//
//		mode.addAttribute("formula_name", companyScheme.getFormula_name());
//
//		mode.addAttribute("fun_name", companyScheme.getFun_name());
//
//		return "hrp/hpm/hpmcompanyscheme/hpmCompanySchemeUpdate";
//	}
//
//	// 修改保存
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/updateHpmCompanyScheme", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> updateCompanyScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
//		String companySchemeJson = aphiCompanySchemeService.updateCompanyScheme(mapVo);
//
//		return JSONObject.parseObject(companySchemeJson);
//	}

//	/** 上传处理方法 */
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/readCompanySchemeFiles", method = RequestMethod.POST)
//	public String readCompanySchemeFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
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
//		List<AphiCompanyScheme> list2 = new ArrayList<AphiCompanyScheme>();
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
//				AphiCompanyScheme companyScheme = new AphiCompanyScheme();
//
//				String temp[] = list.get(i);// 行
//
//				if (temp[2].length() == 1) {
//
//					temp[2] = "0" + temp[2];
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					companyScheme.setItem_code(temp[0]);
//
//					mapVo.put("item_code", temp[0]);
//
//				} else {
//
//					err_sb.append("奖金项目编码为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[1])) {
//
//					companyScheme.setMethod_code(temp[1]);
//
//					mapVo.put("method_code", temp[1]);
//
//				} else {
//
//					err_sb.append("取值方法为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[2])) {
//
//					companyScheme.setFormula_code(temp[2]);
//
//					mapVo.put("formula_code", temp[2]);
//
//				} else {
//
//					err_sb.append("取值公式为空  ");
//
//				}
//				if (StringUtils.isNotEmpty(temp[3])) {
//
//					companyScheme.setFun_code(temp[3]);
//
//					mapVo.put("fun_code", temp[3]);
//
//				} else {
//
//					err_sb.append("取值方法为空  ");
//
//				}
//
//				AphiCompanyScheme cs = aphiCompanySchemeService.queryCompanySchemeByCode(mapVo);
//
//				if (cs != null) {
//
//					err_sb.append("数据编码已经存在！");
//
//				}
//
//				AphiItem item = aphiItemService.queryItemByCode(mapVo);
//
//				if (item == null) {
//
//					err_sb.append("奖金项目不存在");
//
//				}
//
//				// AphiHpmTargetMethodPara hpmTargetMethodPara =
//				// aphiHpmTargetMethodParaService.queryTargetMethodParaByCode(mapVo);
//				//
//				// if(hpmTargetMethodPara == null){
//				//
//				// err_sb.append("取值方法不存在");
//				//
//				// }
//				//
//				//
//				// AphiHpmFormula hpmFormula =
//				// aphiHpmFormulaService.queryFormulaByCode(mapVo);
//				//
//				// if(hpmFormula == null){
//				//
//				// err_sb.append("取值公式不存在");
//				//
//				// }
//
//				if (err_sb.toString().length() > 0) {
//
//					companyScheme.setError_type(err_sb.toString());
//
//					list2.add(companyScheme);
//
//				} else {
//
//					aphiCompanySchemeService.addCompanyScheme(mapVo);
//
//				}
//
//			}
//
//		} catch (DataAccessException e) {
//
//			AphiCompanyScheme cd = new AphiCompanyScheme();
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
//		return "/hrp/hpm/hpmcompanyscheme/hpmCompanySchemeImportMessage";
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/addBatchcompanySchemeDict", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchcompanySchemeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode, HttpServletResponse response)
//			throws Exception {
//
//		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
//
//		List<AphiCompanyScheme> list_err = new ArrayList<AphiCompanyScheme>();
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
//				AphiCompanyScheme companyScheme = new AphiCompanyScheme();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				// Set<String> key = jsonObj.keySet();
//
//				if (mapVo.get("group_id") == null) {
//
//					mapVo.put("group_id", SessionManager.getGroupId());
//				}
//
//				if (mapVo.get("hos_id") == null) {
//
//					mapVo.put("hos_id", SessionManager.getHosId());
//				}
//
//				mapVo.put("copy_code", SessionManager.getCopyCode());
//
//				mapVo.put("item_code", jsonObj.get("item_code"));
//
//				mapVo.put("method_code", jsonObj.get("method_code"));
//
//				mapVo.put("formula_code", jsonObj.get("formula_code"));
//
//				mapVo.put("fun_code", jsonObj.get("fun_code"));
//
//				AphiCompanyScheme cs = aphiCompanySchemeService.queryCompanySchemeByCode(mapVo);
//
//				if (cs != null) {
//
//					err_sb.append("数据编码已经存在！ ");
//				}
//
//				AphiItem item = aphiItemService.queryItemByCode(mapVo);
//
//				if (item == null) {
//
//					err_sb.append("奖金项目不存在;");
//
//				}
//
//				// AphiHpmTargetMethodPara hpmTargetMethodPara =
//				// aphiHpmTargetMethodParaService.queryTargetMethodParaByCode(mapVo);
//				//
//				// if(hpmTargetMethodPara == null){
//				//
//				// err_sb.append("取值方法不存在;");
//				//
//				// }
//				//
//				//
//				// AphiHpmFormula hpmFormula =
//				// aphiHpmFormulaService.queryFormulaByCode(mapVo);
//				//
//				// if(hpmFormula == null){
//				//
//				// err_sb.append("取值公式不存在;");
//				//
//				// }
//
//				if (err_sb.toString().length() > 0) {
//
//					companyScheme.setItem_code((String) jsonObj.get("item_code"));
//
//					companyScheme.setMethod_code((String) jsonObj.get("method_code"));
//
//					companyScheme.setFormula_code((String) jsonObj.get("formula_code"));
//
//					companyScheme.setFun_code((String) jsonObj.get("fun_code"));
//
//					companyScheme.setError_type(err_sb.toString());
//
//					list_err.add(companyScheme);
//				} else {
//
//					s = aphiCompanySchemeService.addCompanyScheme(mapVo);
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
	@RequestMapping(value = "/hrp/hpm/hpmcompanyscheme/downTemplateCompanyScheme", method = RequestMethod.GET)
	public String downTemplateCompanyScheme(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\方案制定\\总额预算制计将模式\\核算方案配置", "全院总额核算方案配置.xlsx");
		return null;
	}

}
