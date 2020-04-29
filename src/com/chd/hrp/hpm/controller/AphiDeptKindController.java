/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiDeptKind;
import com.chd.hrp.hpm.service.AphiDeptKindService;

/**
 * 
 * @Description: 8803 科室分类字典表
 * @Table: Prm_DEPT_KIND
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiDeptKindController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptKindController.class);

	// 引入Service服务
	@Resource(name = "aphiDeptKindService")
	private final AphiDeptKindService aphiDeptKindService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkind/hpmDeptKindMainPage", method = RequestMethod.GET)
	public String hpmDeptKindMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptkind/hpmDeptKindMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkind/hpmDeptKindAddPage", method = RequestMethod.GET)
	public String hpmDeptKindAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptkind/hpmDeptKindAdd";

	}

	/**
	 * @Description 添加数据 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkind/addHpmDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dept_kind_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dept_kind_name").toString()));

		String PrmDeptKindJson = aphiDeptKindService.addPrmDeptKind(mapVo);

		return JSONObject.parseObject(PrmDeptKindJson);

	}

	/**
	 * @Description 更新跳转页面 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkind/hpmDeptKindUpdatePage", method = RequestMethod.GET)
	public String hpmDeptKindUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AphiDeptKind PrmDeptKind = new AphiDeptKind();

		PrmDeptKind = aphiDeptKindService.queryPrmDeptKindByCode(mapVo);

		mode.addAttribute("group_id", PrmDeptKind.getGroup_id());

		mode.addAttribute("hos_id", PrmDeptKind.getHos_id());

		mode.addAttribute("copy_code", PrmDeptKind.getCopy_code());

		mode.addAttribute("dept_kind_code", PrmDeptKind.getDept_kind_code());

		mode.addAttribute("dept_kind_name", PrmDeptKind.getDept_kind_name());

		mode.addAttribute("dept_kind_note", PrmDeptKind.getDept_kind_note());

		mode.addAttribute("spell_code", PrmDeptKind.getSpell_code());

		mode.addAttribute("wbx_code", PrmDeptKind.getWbx_code());

		mode.addAttribute("is_stop", PrmDeptKind.getIs_stop());

		return "hrp/hpm/hpmdeptkind/hpmDeptKindUpdate";
	}

	/**
	 * @Description 更新数据 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkind/updateHpmDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dept_kind_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dept_kind_name").toString()));

		String PrmDeptKindJson = aphiDeptKindService.updatePrmDeptKind(mapVo);

		return JSONObject.parseObject(PrmDeptKindJson);
	}

	/**
	 * @Description 删除数据 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkind/deleteHpmDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDeptKind(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("dept_kind_code", ids[3]);

			listVo.add(mapVo);

		}

		String PrmDeptKindJson = aphiDeptKindService.deleteBatchPrmDeptKind(listVo);

		return JSONObject.parseObject(PrmDeptKindJson);

	}

	/**
	 * @Description 查询数据 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkind/queryHpmDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String PrmDeptKind = aphiDeptKindService.queryPrmDeptKind(getPage(mapVo));

		return JSONObject.parseObject(PrmDeptKind);

	}

	/**
	 * @Description 导入跳转页面 8803 科室分类字典表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkind/hpmDeptKindImportPage", method = RequestMethod.GET)
	public String hpmDeptKindImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptkind/hpmDeptKindImport";

	}

	/**
	 * @Description 下载导入模版 8803 科室分类字典表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmdeptkind/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\基础设置", "科室分类字典模板.xls");

		return null;
	}

	/**
	 * @Description 导入数据 8803 科室分类字典表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkind/readHpmDeptKindFiles", method = RequestMethod.POST)
	public String readHpmDeptKindFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<AphiDeptKind> list_err = new ArrayList<AphiDeptKind>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

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

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AphiDeptKind PrmDeptKind = new AphiDeptKind();

				String temp[] = list.get(i);// 行

				if (StringTool.isNotBlank(temp[0])) {

					PrmDeptKind.setDept_kind_code(temp[0]);

					mapVo.put("dept_kind_code", temp[0]);

				} else {

					err_sb.append("科室分类编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					PrmDeptKind.setDept_kind_name(temp[1]);

					mapVo.put("dept_kind_name", temp[1]);

				} else {

					err_sb.append("科室分类名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					PrmDeptKind.setDept_kind_note(temp[2]);

					mapVo.put("dept_kind_note", temp[2]);

				} else {

					err_sb.append("科室分类说明为空  ");

				}

				/* 是否停用默认为否 */

				mapVo.put("is_stop", 0);

				AphiDeptKind data_exc_extis = aphiDeptKindService.queryPrmDeptKindByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					PrmDeptKind.setError_type(err_sb.toString());

					list_err.add(PrmDeptKind);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dept_kind_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dept_kind_name").toString()));

					String dataJson = aphiDeptKindService.addPrmDeptKind(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AphiDeptKind data_exc = new AphiDeptKind();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

}
