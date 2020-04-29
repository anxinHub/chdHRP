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
import com.chd.hrp.hpm.entity.AphiDeptLimitConf;
import com.chd.hrp.hpm.service.AphiDeptLimitConfService;

/**
 * alfred
 */

@Controller
public class AphiDeptLimitConfController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptLimitConfController.class);

	@Resource(name = "aphiDeptLimitConfService")
	private final AphiDeptLimitConfService aphiDeptLimitConfService = null;

	// @Resource(name = "aphiDeptService")
	// private final AphiDeptService aphiDeptService = null;

	// 维护页面跳转
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfMainPage01",
			"/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfMainPage02" }, method = RequestMethod.GET)
	public String hpmDeptLimitConfMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("nature_code", mapVo.get("nature_code"));
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfMain";

	}

	// 添加页面
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfAddPage01",
			"/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfAddPage02" }, method = RequestMethod.GET)
	public String hpmDeptLimitConfAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfAdd";

	}

	// 保存
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/addHpmDeptLimitConf01",
			"/hrp/hpm/hpmdeptlimitconf/addHpmDeptLimitConf02" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmDeptLimitConf(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String deptLimitConfJson = aphiDeptLimitConfService.addDeptLimitConf(mapVo);

		return JSONObject.parseObject(deptLimitConfJson);

	}

	// 查询
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/queryHpmDeptLimitConf01",
			"/hrp/hpm/hpmdeptlimitconf/queryHpmDeptLimitConf02" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptLimitConf(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String deptLimitConf = aphiDeptLimitConfService.queryDeptLimitConf(getPage(mapVo));

		return JSONObject.parseObject(deptLimitConf);

	}

	// 删除
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/deleteHpmDeptLimitConf01",
			"/hrp/hpm/hpmdeptlimitconf/deleteHpmDeptLimitConf02" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDeptLimitConf(
			@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		Map map = new HashMap();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		String deptLimitConfJson = aphiDeptLimitConfService.deleteDeptLimitConf(map, checkIds);

		return JSONObject.parseObject(deptLimitConfJson);
	}

	// 修改页面跳转
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfUpdatePage01",
			"/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfUpdatePage02" }, method = RequestMethod.GET)
	public String hpmDeptLimitConfUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AphiDeptLimitConf deptLimitConf = aphiDeptLimitConfService.queryDeptLimitConfByCode(mapVo);

		mode.addAttribute("dept_id", deptLimitConf.getDept_id());
		mode.addAttribute("dept_no", deptLimitConf.getDept_no());

		mode.addAttribute("is_limit", deptLimitConf.getIs_limit());

		mode.addAttribute("lower_money", deptLimitConf.getLower_money());

		mode.addAttribute("upper_money", deptLimitConf.getUpper_money());

		mode.addAttribute("dept_name", deptLimitConf.getDept_name());

		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfUpdate";
	}

	// 修改保存
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/updateHpmDeptLimitConf01",
			"/hrp/hpm/hpmdeptlimitconf/updateHpmDeptLimitConf02" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmDeptLimitConf(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String deptLimitConfJson = aphiDeptLimitConfService.updateDeptLimitConf(mapVo);

		return JSONObject.parseObject(deptLimitConfJson);
	}

	// 生成
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/createHpmDeptBalancePercConf01",
			"/hrp/hpm/hpmdeptlimitconf/createHpmDeptBalancePercConf02" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmDeptBalancePercConf(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String deptBalancePercConfJson = aphiDeptLimitConfService.createDeptLimitConf(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);
	}

	// 快速添加跳转
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/hpmDeptBalancePercConfFastPage01",
			"/hrp/hpm/hpmdeptlimitconf/hpmDeptBalancePercConfFastPage02" }, method = RequestMethod.GET)
	public String hpmDeptBalancePercConfFastPage(@RequestParam(value = "checkIds", required = true) String checkIds,
			Model mode) throws Exception {

		mode.addAttribute("checkIds", checkIds);
		return "hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfFast";

	}

	// 保存
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/fastHpmDeptLimitConf01",
			"/hrp/hpm/hpmdeptlimitconf/fastHpmDeptLimitConf02" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fasthpmDeptBalancePercConf(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String deptBalancePercConfJson = aphiDeptLimitConfService.fastDeptLimitConf(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);

	}

	// /** 上传处理方法 */
	// @RequestMapping(value = "/hrp/hpm/hpmdeptlimitconf/readDeptLimitConfFiles",
	// method = RequestMethod.POST)
	// public String readDeptLimitConfFiles(Plupload plupload, HttpServletRequest
	// request, HttpServletResponse response, Model mode) throws IOException {
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// List<String[]> list = UploadUtil.readFile(plupload, request, response);
	//
	// List<AphiDeptLimitConf> errorList = new ArrayList<AphiDeptLimitConf>();
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
	// AphiDeptLimitConf deptLimitConf = new AphiDeptLimitConf();
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// String temp[] = list.get(i);
	//
	// if (StringUtils.isNotEmpty(temp[0])) {
	//
	// deptLimitConf.setDept_code(temp[0]);
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
	// deptLimitConf.setIs_limit(Integer.parseInt(temp[1].toString()));
	//
	// mapVo.put("is_limit", temp[1]);
	//
	// } else {
	//
	// err_sb.append("是否控制不能为空！");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[2])) {
	//
	// deptLimitConf.setLower_money(Double.parseDouble(temp[2].toString()));
	//
	// mapVo.put("lower_money", temp[2]);
	//
	// } else {
	//
	// err_sb.append("保底线不能为空！");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[3])) {
	//
	// deptLimitConf.setUpper_money(Double.parseDouble(temp[3].toString()));
	//
	// mapVo.put("upper_money", temp[3]);
	//
	// } else {
	//
	// err_sb.append("封顶线不能为空！");
	//
	// }
	//
	// AphiDept Dept = aphiDeptService.queryDeptByCode(mapVo);
	//
	// if (Dept == null) {
	//
	// err_sb.append("科室编码不存在！");
	// }
	//
	// AphiDeptLimitConf DeptLimitConf =
	// aphiDeptLimitConfService.queryDeptLimitConfByCode(mapVo);
	//
	// if (DeptLimitConf != null) {
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// deptLimitConf.setError_type(err_sb.toString());
	//
	// errorList.add(deptLimitConf);
	//
	// } else {
	//
	// aphiDeptLimitConfService.addDeptLimitConf(mapVo);
	//
	// }
	//
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// AphiDeptLimitConf DeptLimitConf = new AphiDeptLimitConf();
	//
	// DeptLimitConf.setError_type("系统导入错误！");
	//
	// errorList.add(DeptLimitConf);
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
	// return "/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfImportMessage";
	//
	// }
	//
	// @RequestMapping(value = "/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConf", method
	// = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> deptLimitConf(@RequestParam(value = "ParamVo")
	// String paramVo, Model mode) throws Exception {
	//
	// JSONArray json = JSONArray.parseArray(paramVo);
	//
	// List<AphiDeptLimitConf> errorList = new ArrayList<AphiDeptLimitConf>();
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
	//
	// while (it.hasNext()) {
	//
	// AphiDeptLimitConf deptLimitConf = new AphiDeptLimitConf();
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
	//
	// mapVo.put("dept_code", jsonObj.get("dept_code"));
	//
	// mapVo.put("is_limit", jsonObj.get("is_limit"));
	//
	// mapVo.put("lower_money", jsonObj.get("lower_money"));
	//
	// mapVo.put("upper_money", jsonObj.get("upper_money"));
	//
	// AphiDept Dept = aphiDeptService.queryDeptByCode(mapVo);
	//
	// if (Dept == null) {
	//
	// err_sb.append("科室编码不存在！");
	// }
	//
	// AphiDeptLimitConf DeptLimitConf =
	// aphiDeptLimitConfService.queryDeptLimitConfByCode(mapVo);
	//
	// if (DeptLimitConf != null) {
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// deptLimitConf.setDept_code(jsonObj.get("dept_code").toString());
	//
	// deptLimitConf.setIs_limit(Integer.parseInt(jsonObj.get("is_limit").toString()));
	//
	// deptLimitConf.setLower_money(Double.parseDouble(jsonObj.get("lower_money").toString()));
	//
	// deptLimitConf.setUpper_money(Double.parseDouble(jsonObj.get("upper_money").toString()));
	//
	// deptLimitConf.setError_type(err_sb.toString());
	//
	// errorList.add(deptLimitConf);
	//
	// } else {
	//
	// aphiDeptLimitConfService.addDeptLimitConf(mapVo);
	//
	// }
	//
	// }
	//
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

	// 导入
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfImportPage01",
			"/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfImportPage02" }, method = RequestMethod.GET)
	public String hpmDeptLimitConfImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfImport";
	}

	// 跳转导入
	@RequestMapping(value = { "/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfImport01",
			"/hrp/hpm/hpmdeptlimitconf/hpmDeptLimitConfImport02" }, method = RequestMethod.POST)
	@ResponseBody
	public String hpmDeptLimitConfImport(@RequestParam Map<String, Object> mapVo) {
		try {
			String impJson = aphiDeptLimitConfService.hpmDeptLimitConfImport(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmdeptlimitconf/downTemplateHpmDeptLimitConf", method = RequestMethod.GET)
	public String downTemplateDeptLimitConf(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "绩效工资上下限维护.xlsx");
		return null;
	}
}
