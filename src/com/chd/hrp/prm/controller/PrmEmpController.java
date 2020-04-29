package com.chd.hrp.prm.controller;

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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiEmp;
import com.chd.hrp.prm.entity.PrmEmp;
import com.chd.hrp.prm.service.PrmEmpService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmEmpController extends BaseController {
	private static Logger logger = Logger.getLogger(PrmEmpController.class);

	@Resource(name = "prmEmpService")
	private final PrmEmpService prmEmpService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/prm/prmemp/prmEmpMainPage", method = RequestMethod.GET)
	public String PrmEmpMainPage(Model mode) throws Exception {

		return "hrp/prm/prmemp/prmEmpMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/prm/prmemp/prmEmpAddPage", method = RequestMethod.GET)
	public String PrmEmpAddPage(Model mode) throws Exception {
		return "hrp/prm/prmemp/prmEmpAdd";
	}

	@RequestMapping(value = "/hrp/prm/prmemp/prmEmpInitPage", method = RequestMethod.GET)
	public String PrmEmpInitPage(Model mode) throws Exception {
		return "hrp/prm/prmemp/prmEmpInit";
	}

	// 保存
	@RequestMapping(value = "/hrp/prm/prmemp/addPrmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String prmEmp = prmEmpService.addPrmEmp(mapVo);
		return JSONObject.parseObject(prmEmp);

	}

	@RequestMapping(value = "/hrp/prm/prmemp/initBatchPrmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initPrmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String PrmEmp =prmEmpService.initBatchPrmEmp(mapVo);

		return JSONObject.parseObject(PrmEmp);

	}

	// 查询
	@RequestMapping(value = "/hrp/prm/prmemp/queryPrmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		if (mapVo.get("is_dept") != null && "1".equals(mapVo.get("is_dept"))) {

			mapVo.put("sql", " AND ae.dept_id is not null");
		}

		String PrmEmp = prmEmpService.queryPrmEmp(getPage(mapVo));

		return JSONObject.parseObject(PrmEmp);

	}

	// 查询
	@RequestMapping(value = "/hrp/prm/prmemp/queryPrmEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		if (mapVo.get("is_dept") != null && "1".equals(mapVo.get("is_dept"))) {

			mapVo.put("sql", " AND ae.dept_id is not null");
		}

		String PrmEmp = prmEmpService.queryPrmEmpDict(getPage(mapVo));

		return JSONObject.parseObject(PrmEmp);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/prm/prmemp/empUpdatePage", method = RequestMethod.GET)
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

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		PrmEmp emp = new PrmEmp();

		emp = prmEmpService.queryPrmEmpByCode(mapVo);

		mode.addAttribute("emp_id", emp.getEmp_id());

		mode.addAttribute("emp_code", emp.getEmp_code());

		mode.addAttribute("emp_name", emp.getEmp_name());

		mode.addAttribute("duty_code", emp.getDuty_code());

		mode.addAttribute("duty_name", emp.getDuty_name());

		mode.addAttribute("dept_id", emp.getDept_id());

		mode.addAttribute("dept_no", emp.getDept_no());
		
		mode.addAttribute("dept_code", emp.getDept_code());

		mode.addAttribute("dept_name", emp.getDept_name());

		mode.addAttribute("spell_code", emp.getSpell_code());

		mode.addAttribute("wbx_code", emp.getWbx_code());

		mode.addAttribute("is_stop", emp.getIs_stop());

		mode.addAttribute("is_acc", emp.getIs_acc());

		return "/hrp/prm/prmemp/prmEmpUpdate";
	}

	@RequestMapping(value = "/hrp/prm/prmemp/updatePrmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		// List<Map<String, Object>> listVo = new ArrayList<Map<String,
		// Object>>();

		String dept_id_no = mapVo.get("dept_id").toString();

		mapVo.put("dept_id", dept_id_no.split("\\.")[0]);

		mapVo.put("dept_no", dept_id_no.split("\\.")[1]);

		mapVo.put("note", "更新");

		mapVo.put("user_code", SessionManager.getUserCode());

		mapVo.put("create_date", new Date());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_name").toString()));

		// listVo.add(mapVo);

		String emp = prmEmpService.updatePrmEmp(mapVo);

		return JSONObject.parseObject(emp);
	}

	@RequestMapping(value = "/hrp/prm/prmemp/deletePrmEmp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmEmp(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String emp = prmEmpService.deleteBatchPrmEmp(listVo);

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
	@RequestMapping(value = "hrp/prm/prmemp/downTemplateEmp", method = RequestMethod.GET)
	public String downTemplateEmp(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "prm\\基础设置", "职工维护.xlsx");

		return null;
	}

	/**
	 * @Description 导入跳转页面 8801 职工字典表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemp/prmEmpImportPage", method = RequestMethod.GET)
	public String PrmEmpImportPage(Model mode) throws Exception {

		return "hrp/prm/prmemp/prmEmpImport";

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
	@RequestMapping(value = "/hrp/prm/prmemp/importPrmEmp", method = RequestMethod.POST)
	@ResponseBody
	public String importPrmEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String reJson = null;

		try {
			reJson = prmEmpService.importPrmEmp(mapVo);
			return reJson;
		} catch (Exception e) {
			reJson = e.getMessage();
		}
		return reJson;
	}
}
