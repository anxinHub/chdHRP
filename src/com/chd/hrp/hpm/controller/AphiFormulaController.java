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
import com.chd.hrp.hpm.entity.AphiFormula;
import com.chd.hrp.hpm.service.AphiFormulaService;

/**
 * 
 * @Description: 9906 绩效计算公式表
 * @Table: PRM_FORMULA
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiFormulaController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiFormulaController.class);

	// 引入Service服务
	@Resource(name = "aphiFormulaService")
	private final AphiFormulaService aphiFormulaService = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmformula/hpmFormulaMainPage", method = RequestMethod.GET)
	public String hpmFormulaMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmformula/hpmFormulaMain";

	}

	@RequestMapping(value = "/hrp/hpm/hpmformula/hpmFormulaPage", method = RequestMethod.GET)
	public String hpmFormulaPage(Model mode) throws Exception {

		return "hrp/hpm/hpmformula/hpmFormula";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmformula/hpmFormulaAddPage", method = RequestMethod.GET)
	public String hpmFormulaAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmformula/hpmFormulaAdd";

	}

	/**
	 * @Description 添加数据 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmformula/addHpmFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmFormula(
			@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		
		String hpmFormulaJson = null;
		
		try {
			
			hpmFormulaJson = aphiFormulaService.addPrmFormula(mapVo);
			
		} catch (Exception e) {
			
			hpmFormulaJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}

		return JSONObject.parseObject(hpmFormulaJson);

	}

	/**
	 * @Description 更新跳转页面 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmformula/hpmFormulaUpdatePage", method = RequestMethod.GET)
	public String hpmFormulaUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

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

		AphiFormula hpmFormula = new AphiFormula();

		hpmFormula = aphiFormulaService.queryPrmFormulaByCode(mapVo);

		mode.addAttribute("group_id", hpmFormula.getGroup_id());
		
		mode.addAttribute("hos_id", hpmFormula.getHos_id());
		
		mode.addAttribute("copy_code", hpmFormula.getCopy_code());
		
		mode.addAttribute("formula_code", hpmFormula.getFormula_code());
		
		mode.addAttribute("formula_name", hpmFormula.getFormula_name());
		
		String formula_method_chs = hpmFormula.getFormula_method_chs();
		
		formula_method_chs = formula_method_chs.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		
		mode.addAttribute("formula_method_chs",formula_method_chs);
		
		mode.addAttribute("formula_method_eng",hpmFormula.getFormula_method_eng());
		
		mode.addAttribute("is_stop", hpmFormula.getIs_stop());

		return "hrp/hpm/hpmformula/hpmFormulaUpdate";
	}

	/**
	 * @Description 更新数据 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmformula/updateHpmFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmFormula(
			@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		
		String hpmFormulaJson = null;
		
		try {
			
			hpmFormulaJson = aphiFormulaService.updatePrmFormula(mapVo);
			
		} catch (Exception e) {

			hpmFormulaJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}

		return JSONObject.parseObject(hpmFormulaJson);
	}

	/**
	 * @Description 删除数据 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmformula/deleteHpmFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmFormula(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("formula_code", ids[3]);

			listVo.add(mapVo);

		}

		String hpmFormulaJson = aphiFormulaService.deleteBatchPrmFormula(listVo);

		return JSONObject.parseObject(hpmFormulaJson);

	}

	/**
	 * @Description 查询数据 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmformula/queryHpmFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmFormula(
			@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String hpmFormula = aphiFormulaService.queryPrmFormula(getPage(mapVo));

		return JSONObject.parseObject(hpmFormula);

	}

	/**
	 * @Description 导入跳转页面 9906 绩效计算公式表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmformula/hpmFormulaImportPage", method = RequestMethod.GET)
	public String hpmFormulaImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmformula/hpmFormulaImport";

	}

	/**
	 * @Description 下载导入模版 9906 绩效计算公式表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmformula/downFormulaTemplate", method = RequestMethod.GET)
	public String downFormulaTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\基础设置", "绩效计算公式表模板.xls");

		return null;
	}

	/**
	 * @Description 导入数据 9906 绩效计算公式表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmformula/readHpmFormulaFiles", method = RequestMethod.POST)
	public String readHpmFormulaFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<AphiFormula> list_err = new ArrayList<AphiFormula>();

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

				AphiFormula hpmFormula = new AphiFormula();

				String temp[] = list.get(i);// 行
				 
				if (StringTool.isNotBlank(temp[0])) {

					hpmFormula.setFormula_code(temp[0]);

					mapVo.put("formula_code", temp[0]);

				} else {

					err_sb.append("公式编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					hpmFormula.setFormula_name(temp[1]);

					mapVo.put("formula_name", temp[1]);

				} else {

					err_sb.append("公式名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					hpmFormula.setFormula_method_chs(temp[2]);

					mapVo.put("formula_method_chs", temp[2]);

				} else {

					err_sb.append("计算公式(中文）为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					hpmFormula.setFormula_method_eng(temp[3]);

					mapVo.put("formula_method_eng", temp[3]);

				} else {

					err_sb.append("计算公式(英文)为空  ");

				}
 
					mapVo.put("is_stop", temp[4]);
 
				AphiFormula data_exc_extis = aphiFormulaService.queryPrmFormulaByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					hpmFormula.setError_type(err_sb.toString());

					list_err.add(hpmFormula);

				} else {

					String dataJson = aphiFormulaService.addPrmFormula(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AphiFormula data_exc = new AphiFormula();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	

}
