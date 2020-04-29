package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiEmp;
import com.chd.hrp.hpm.service.AphiEmpService;

/**
 * 
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiEmpController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiEmpController.class);

	@Resource(name = "aphiEmpService")
	private final AphiEmpService aphiEmpService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmemp/hpmEmpMainPage", method = RequestMethod.GET)
	public String PrmEmpMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmemp/hpmEmpMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmemp/hpmEmpAddPage", method = RequestMethod.GET)
	public String PrmEmpAddPage(Model mode) throws Exception {
		return "hrp/hpm/hpmemp/hpmEmpAdd";
	}

	@RequestMapping(value = "/hrp/hpm/hpmemp/hpmEmpInitPage", method = RequestMethod.GET)
	public String PrmEmpInitPage(Model mode) throws Exception {
		return "hrp/hpm/hpmemp/hpmEmpInit";
	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmemp/addHpmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String aphiEmp = aphiEmpService.addPrmEmp(mapVo);
		return JSONObject.parseObject(aphiEmp);

	}

	@RequestMapping(value = "/hrp/hpm/hpmemp/initBatchHpmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String PrmEmp = aphiEmpService.initBatchPrmEmp(mapVo);

		return JSONObject.parseObject(PrmEmp);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmemp/queryHpmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", MyConfig.getCurAccYear());

		}

		if (mapVo.get("is_dept") != null && "1".equals(mapVo.get("is_dept"))) {

			mapVo.put("sql", " AND ae.dept_id is not null");
		}

		String PrmEmp = aphiEmpService.queryPrmEmp(getPage(mapVo));

		return JSONObject.parseObject(PrmEmp);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmemp/queryAphiEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAphiEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", MyConfig.getCurAccYear());

		}

		if (mapVo.get("is_dept") != null && "1".equals(mapVo.get("is_dept"))) {

			mapVo.put("sql", " AND a.dept_id is not null");
		}

		if (MyConfig.getSysPara("09004") == null) {
			mapVo.put("dept_para", 0);
		} else {
			mapVo.put("dept_para", MyConfig.getSysPara("09004"));
		}
		if (MyConfig.getSysPara("09001") == null) {
			mapVo.put("emp_para", 0);
		} else {
			mapVo.put("emp_para", MyConfig.getSysPara("09001"));
		}

		if ("0".equals(mapVo.get("dept_para").toString())) {
			if ("0".equals(mapVo.get("emp_para").toString())) {
				mapVo.put("from_para", "temp_all_aphi");
			} else {
				mapVo.put("from_para", "temp_dept_aphi");
			}
		} else {
			if ("0".equals(mapVo.get("emp_para").toString())) {
				mapVo.put("from_para", "temp_emp_aphi");
			} else {
				mapVo.put("from_para", "temp_no_aphi");
			}
		}

		String PrmEmp = aphiEmpService.queryAphiEmpDict(getPage(mapVo));

		return JSONObject.parseObject(PrmEmp);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmemp/empUpdatePage", method = RequestMethod.GET)
	public String empUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", MyConfig.getCurAccYear());

		}

		AphiEmp emp = new AphiEmp();

		emp = aphiEmpService.queryPrmEmpByCode(mapVo);

		mode.addAttribute("emp_id", emp.getEmp_id());

		mode.addAttribute("emp_code", emp.getEmp_code());

		mode.addAttribute("emp_name", emp.getEmp_name());

		mode.addAttribute("duty_code", emp.getDuty_code());

		mode.addAttribute("duty_name", emp.getDuty_name());

		mode.addAttribute("dept_id", emp.getDept_id());

		mode.addAttribute("dept_no", emp.getDept_no());

		mode.addAttribute("dept_name", emp.getDept_name());

		mode.addAttribute("spell_code", emp.getSpell_code());

		mode.addAttribute("wbx_code", emp.getWbx_code());

		mode.addAttribute("is_stop", emp.getIs_stop());

		mode.addAttribute("is_acc", emp.getIs_acc());

		return "/hrp/hpm/hpmemp/hpmEmpUpdate";
	}

	@RequestMapping(value = "/hrp/hpm/hpmemp/updateHpmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", MyConfig.getCurAccYear());

		}

		// List<Map<String, Object>> listVo = new ArrayList<Map<String,
		// Object>>();

		String dept_id_no = mapVo.get("dept_id").toString();

		mapVo.put("dept_id", dept_id_no.split(",")[0]);

		mapVo.put("dept_no", dept_id_no.split(",")[1]);

		mapVo.put("note", "更新");

		mapVo.put("user_code", SessionManager.getUserCode());

		mapVo.put("create_date", new Date());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_name").toString()));

		// listVo.add(mapVo);

		String emp = aphiEmpService.updatePrmEmp(mapVo);

		return JSONObject.parseObject(emp);
	}

	@RequestMapping(value = "/hrp/hpm/hpmemp/deleteHpmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmEmp(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("emp_id", ids[3]);

			mapVo.put("is_stop", "1");

			listVo.add(mapVo);

		}

		String emp = aphiEmpService.deleteBatchPrmEmp(listVo);

		return JSONObject.parseObject(emp);

	}

	/**
	 * @Description 下载导入模版 0308 职工表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmemp/downTemplateEmp", method = RequestMethod.GET)
	public String downTemplateEmp(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		printTemplate(request, response, "hpm\\downTemplate", "职工维护.xlsx");

		return null;
	}

	/**
	 * @Description 导入跳转页面 8801 职工字典表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmemp/hpmEmpImportPage", method = RequestMethod.GET)
	public String PrmEmpImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmemp/hpmEmpImport";

	}

	/**
	 * @Description 导入数据 8801 职工字典表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmemp/importHpmEmp", method = RequestMethod.POST)
	@ResponseBody
	public String importHpmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String reJson = null;

		try {
			reJson = aphiEmpService.importAphiEmp(mapVo);
			return reJson;
		} catch (Exception e) {
			reJson = e.getMessage();
		}
		return reJson;
	}

	// 页面跳转-系统平台职工
	@RequestMapping(value = "/hrp/hpm/hpmemp/hpmSysEmpMainPage", method = RequestMethod.GET)
	public String hpmSysEmpMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmemp/hpmSysEmpMain";

	}

	// 查询-系统平台职工
	@RequestMapping(value = "/hrp/hpm/hpmemp/queryHpmSysEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmSysEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", MyConfig.getCurAccYear());

		}

		if (mapVo.get("emp_name") != null) {

			mapVo.put("emp_name", mapVo.get("emp_name").toString().toUpperCase());

		}

		if (mapVo.get("is_dept") != null && "1".equals(mapVo.get("is_dept"))) {

			mapVo.put("sql", " AND ae.dept_id is not null");
		}

		String PrmEmp = aphiEmpService.querySysEmp(getPage(mapVo));

		return JSONObject.parseObject(PrmEmp);

	}
}
