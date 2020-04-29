/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

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
import com.chd.hrp.prm.entity.PrmDeptKind;
import com.chd.hrp.prm.service.PrmDeptKindService;
import com.chd.hrp.sys.service.DeptKindService;

/**
 * 
 * @Description: 8803 科室分类字典表
 * @Table: Prm_DEPT_KIND
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptKindController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptKindController.class);

	// 引入Service服务
	@Resource(name = "prmDeptKindService")
	private final PrmDeptKindService prmDeptKindService = null;
	
	// 引入Service服务
	@Resource(name = "deptKindService")
	private final DeptKindService deptKindService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkind/prmDeptKindMainPage", method = RequestMethod.GET)
	public String PrmDeptKindMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkind/prmDeptKindMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkind/prmDeptKindAddPage", method = RequestMethod.GET)
	public String PrmDeptKindAddPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkind/prmDeptKindAdd";

	}

	/**
	 * @Description 添加数据 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkind/addPrmDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String PrmDeptKindJson = prmDeptKindService.addPrmDeptKind(mapVo);

		return JSONObject.parseObject(PrmDeptKindJson);

	}

	/**
	 * @Description 更新跳转页面 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkind/prmDeptKindUpdatePage", method = RequestMethod.GET)
	public String PrmDeptKindUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmDeptKind PrmDeptKind = new PrmDeptKind();

		PrmDeptKind = prmDeptKindService.queryPrmDeptKindByCode(mapVo);

		mode.addAttribute("group_id", PrmDeptKind.getGroup_id());

		mode.addAttribute("hos_id", PrmDeptKind.getHos_id());

		mode.addAttribute("copy_code", PrmDeptKind.getCopy_code());

		mode.addAttribute("dept_kind_code", PrmDeptKind.getDept_kind_code());

		mode.addAttribute("dept_kind_name", PrmDeptKind.getDept_kind_name());

		mode.addAttribute("dept_kind_note", PrmDeptKind.getDept_kind_note());

		mode.addAttribute("spell_code", PrmDeptKind.getSpell_code());

		mode.addAttribute("wbx_code", PrmDeptKind.getWbx_code());

		mode.addAttribute("is_stop", PrmDeptKind.getIs_stop());
		return "hrp/prm/prmdeptkind/prmDeptKindUpdate";
	}

	/**
	 * @Description 更新数据 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkind/updatePrmDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String PrmDeptKindJson = prmDeptKindService.updatePrmDeptKind(mapVo);

		return JSONObject.parseObject(PrmDeptKindJson);
	}

	
	/**
	 * @Description 删除数据 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkind/deletePrmDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptKind(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String PrmDeptKindJson = prmDeptKindService.deleteBatchPrmDeptKind(listVo);

		return JSONObject.parseObject(PrmDeptKindJson);

	}

	/**
	 * @Description 查询数据 8803 科室分类字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkind/queryPrmDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


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
		String PrmDeptKind = prmDeptKindService.queryPrmDeptKind(getPage(mapVo));

		return JSONObject.parseObject(PrmDeptKind);


	}
	
	

	/**
	 * @Description 导入跳转页面 8803 科室分类字典表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkind/prmDeptKindImportPage", method = RequestMethod.GET)
	public String PrmDeptKindImportPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkind/prmDeptKindImport";

	}

	/**
	 * @Description 下载导入模版 8803 科室分类字典表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmdeptkind/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "prm\\基础设置", "科室分类字典模板.xls");

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
	@RequestMapping(value = "/hrp/prm/prmdeptkind/readPrmDeptKindFiles", method = RequestMethod.POST)
	public String readPrmDeptKindFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {


		List<PrmDeptKind> list_err = new ArrayList<PrmDeptKind>();

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

				PrmDeptKind PrmDeptKind = new PrmDeptKind();

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

				PrmDeptKind data_exc_extis = prmDeptKindService.queryPrmDeptKindByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					PrmDeptKind.setError_type(err_sb.toString());

					list_err.add(PrmDeptKind);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dept_kind_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dept_kind_name").toString()));

					String dataJson = prmDeptKindService.addPrmDeptKind(mapVo);

				}

			}

		} catch (DataAccessException e) {

			PrmDeptKind data_exc = new PrmDeptKind();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;


	}

}
