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
import com.chd.hrp.hpm.entity.AphiDeptBalancePercConf;
import com.chd.hrp.hpm.service.AphiDeptBalancePercConfService;

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
public class AphiDeptBalancePercConfController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptBalancePercConfController.class);

	@Resource(name = "aphiDeptBalancePercConfService")
	private final AphiDeptBalancePercConfService aphiDeptBalancePercConfService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfMainPage", method = RequestMethod.GET)
	public String deptBalancePercConfMainPage(Model mode) throws Exception {
		
		return "hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfAddPage", method = RequestMethod.GET)
	public String deptBalancePercConfAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/addHpmDeptBalancePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeptBalancePercConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String deptBalancePercConfJson = aphiDeptBalancePercConfService.addDeptBalancePercConf(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/queryHpmDeptBalancePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptBalancePercConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String deptBalancePercConf = aphiDeptBalancePercConfService.queryDeptBalancePercConf(getPage(mapVo));

		return JSONObject.parseObject(deptBalancePercConf);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/deleteHpmDeptBalancePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptBalancePercConf(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

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

		String deptBalancePercConfJson = aphiDeptBalancePercConfService.deleteDeptBalancePercConf(mapVo, checkIds);

		return JSONObject.parseObject(deptBalancePercConfJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfUpdatePage", method = RequestMethod.GET)
	public String deptBalancePercConfUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AphiDeptBalancePercConf deptBalancePercConf = aphiDeptBalancePercConfService.queryDeptBalancePercConfByCode(mapVo);

		mode.addAttribute("dept_no", deptBalancePercConf.getDept_no());
		mode.addAttribute("dept_id", deptBalancePercConf.getDept_id());

		mode.addAttribute("dept_name", deptBalancePercConf.getDept_name());

		mode.addAttribute("dept_percent", deptBalancePercConf.getDept_percent());

		return "hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/updateHpmDeptBalancePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeptBalancePercConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String deptBalancePercConfJson = aphiDeptBalancePercConfService.updateDeptBalancePercConf(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);
	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/createHpmDeptBalancePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createDeptBalancePercConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String deptBalancePercConfJson = aphiDeptBalancePercConfService.createDeptBalancePercConf(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);
	}

	// 快速添加跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfFasePage", method = RequestMethod.GET)
	public String deptBalancePercConfFasePage(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		mode.addAttribute("checkIds", checkIds);

		return "hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfFast";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/fastHpmDeptBalancePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fastDeptBalancePercConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String deptBalancePercConfJson = aphiDeptBalancePercConfService.fastDeptBalancePercConf(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);

	}
//
//	/** 上传处理方法 */
//	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/readDeptBalancePercConfFiles", method = RequestMethod.POST)
//	public String readDeptBalancePercConfFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		List<String[]> list = UploadUtil.readFile(plupload, request, response);
//
//		List<AphiDeptBalancePercConf> errorList = new ArrayList<AphiDeptBalancePercConf>();
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
//		try {
//
//			for (int i = 1; i < list.size(); i++) {
//
//				AphiDeptBalancePercConf deptBalancePercConf = new AphiDeptBalancePercConf();
//
//				StringBuffer err_sb = new StringBuffer();
//
//				String temp[] = list.get(i);
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					mapVo.put("dept_code", temp[0]);
//
//					deptBalancePercConf.setDept_code(temp[0]);
//
//				} else {
//
//					err_sb.append("科室编码不能为空！");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[1])) {
//
//					mapVo.put("dept_percent", temp[1]);
//
//					deptBalancePercConf.setDept_percent(Double.parseDouble(temp[1]));
//
//				} else {
//
//					err_sb.append("计提比例不能为空！");
//
//				}
//
//				AphiDeptBalancePercConf DeptBalancePercConf = aphiDeptBalancePercConfService.queryDeptBalancePercConfByCode(mapVo);
//
//				if (DeptBalancePercConf != null) {
//
//					err_sb.append("数据编码已经存在！");
//
//				}
//
//				AphiDept dept = deptService.queryDeptByCode(mapVo);
//
//				if (dept == null) {
//
//					err_sb.append("科室编码不存在");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					deptBalancePercConf.setError_type(err_sb.toString());
//
//					errorList.add(deptBalancePercConf);
//
//				} else {
//
//					aphiDeptBalancePercConfService.addDeptBalancePercConf(mapVo);
//
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//
//			AphiDeptBalancePercConf DeptBalancePercConf = new AphiDeptBalancePercConf();
//
//			DeptBalancePercConf.setError_type("导入系统出错");
//
//			errorList.add(DeptBalancePercConf);
//
//			response.getWriter().print(JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//			return null;
//		}
//
//		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//		return "/hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfImportMessage";
//
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/addBatchDeptBalancePercConf", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchDeptBalancePercConf(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
//
//		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
//
//		List<AphiDeptBalancePercConf> list_err = new ArrayList<AphiDeptBalancePercConf>();
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
//				AphiDeptBalancePercConf deptBalancePercConf = new AphiDeptBalancePercConf();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				// Set<String> key = jsonObj.keySet();
//				mapVo.put("group_id", SessionManager.getGroupId());
//				mapVo.put("hos_id", SessionManager.getHosId());
//				mapVo.put("copy_code", SessionManager.getCopyCode());
//
//				mapVo.put("dept_code", jsonObj.get("dept_code"));
//
//				mapVo.put("dept_percent", jsonObj.get("dept_percent"));
//
//				mapVo.put("is_acc", jsonObj.get("is_acc"));
//
//				AphiDeptBalancePercConf hbpc = aphiDeptBalancePercConfService.queryDeptBalancePercConfByCode(mapVo);
//
//				if (hbpc != null) {
//
//					err_sb.append("数据编码已经存在！ ");
//				}
//
//				if (err_sb.toString().length() > 0) {
//					deptBalancePercConf.setDept_code(jsonObj.get("dept_code").toString());
//
//					deptBalancePercConf.setDept_percent(Double.parseDouble(jsonObj.get("dept_percent").toString()));
//
//					deptBalancePercConf.setError_type(err_sb.toString());
//
//					list_err.add(deptBalancePercConf);
//
//				} else {
//
//					s = aphiDeptBalancePercConfService.addDeptBalancePercConf(mapVo);
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
//	}
	
	//导入
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfImportPage", method = RequestMethod.GET)
	public String deptBalancePercConfImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfImport";

	}
	
	//导入跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/hpmDeptBalancePercConfImport" ,method = RequestMethod.POST)
	@ResponseBody
	public String hpmdeptBalancePercConfImport(@RequestParam Map<String, Object> mapVo){
		try {
			String impJson = aphiDeptBalancePercConfService.hpmdeptBalancePercConfImport(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmdeptbalancepercconf/downTemplateHpmDeptBalancePercConf", method = RequestMethod.GET)
	public String downTemplateDeptBalancePercConf(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "计提比例维护.xlsx");
		return null;
	}

}
