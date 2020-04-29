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
import com.chd.hrp.prm.entity.PrmFormula;
import com.chd.hrp.prm.service.PrmFormulaService;

/**
 * 
 * @Description: 9906 绩效计算公式表
 * @Table: PRM_FORMULA
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmFormulaController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmFormulaController.class);

	// 引入Service服务
	@Resource(name = "prmFormulaService")
	private final PrmFormulaService prmFormulaService = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmformula/prmFormulaMainPage", method = RequestMethod.GET)
	public String prmFormulaMainPage(Model mode) throws Exception {

		return "hrp/prm/prmformula/prmFormulaMain";

	}

	@RequestMapping(value = "/hrp/prm/prmformula/prmFormulaPage", method = RequestMethod.GET)
	public String prmFormulaPage(Model mode) throws Exception {

		return "hrp/prm/prmformula/prmFormula";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmformula/prmFormulaAddPage", method = RequestMethod.GET)
	public String prmFormulaAddPage(Model mode) throws Exception {

		return "hrp/prm/prmformula/prmFormulaAdd";

	}

	/**
	 * @Description 添加数据 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmformula/addPrmFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmFormula(
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
		
		String prmFormulaJson = null;
		
		try {
			
			prmFormulaJson = prmFormulaService.addPrmFormula(mapVo);
			
		} catch (Exception e) {
			
			prmFormulaJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}

		return JSONObject.parseObject(prmFormulaJson);

	}

	/**
	 * @Description 更新跳转页面 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmformula/prmFormulaUpdatePage", method = RequestMethod.GET)
	public String prmFormulaUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		PrmFormula prmFormula = new PrmFormula();

		prmFormula = prmFormulaService.queryPrmFormulaByCode(mapVo);

		mode.addAttribute("group_id", prmFormula.getGroup_id());
		
		mode.addAttribute("hos_id", prmFormula.getHos_id());
		
		mode.addAttribute("copy_code", prmFormula.getCopy_code());
		
		mode.addAttribute("formula_code", prmFormula.getFormula_code());
		
		mode.addAttribute("formula_name", prmFormula.getFormula_name());
		
		mode.addAttribute("formula_method_chs",prmFormula.getFormula_method_chs());
		
		mode.addAttribute("formula_method_eng",prmFormula.getFormula_method_eng());
		
		mode.addAttribute("is_stop", prmFormula.getIs_stop());

		return "hrp/prm/prmformula/prmFormulaUpdate";
	}

	/**
	 * @Description 更新数据 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmformula/updatePrmFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmFormula(
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
		
		String prmFormulaJson = null;
		
		try {
			
			prmFormulaJson = prmFormulaService.updatePrmFormula(mapVo);
			
		} catch (Exception e) {

			prmFormulaJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}

		return JSONObject.parseObject(prmFormulaJson);
	}

	/**
	 * @Description 删除数据 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmformula/deletePrmFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmFormula(
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

		String prmFormulaJson = prmFormulaService.deleteBatchPrmFormula(listVo);

		return JSONObject.parseObject(prmFormulaJson);

	}

	/**
	 * @Description 查询数据 9906 绩效计算公式表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmformula/queryPrmFormula", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmFormula(
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
		String prmFormula = prmFormulaService.queryPrmFormula(getPage(mapVo));

		return JSONObject.parseObject(prmFormula);

	}

	/**
	 * @Description 导入跳转页面 9906 绩效计算公式表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmformula/prmFormulaImportPage", method = RequestMethod.GET)
	public String prmFormulaImportPage(Model mode) throws Exception {

		return "hrp/prm/prmformula/prmFormulaImport";

	}

	/**
	 * @Description 下载导入模版 9906 绩效计算公式表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmformula/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "prm\\基础设置", "绩效计算公式表模板.xls");

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
	@RequestMapping(value = "/hrp/prm/prmformula/readPrmFormulaFiles", method = RequestMethod.POST)
	public String readPrmFormulaFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<PrmFormula> list_err = new ArrayList<PrmFormula>();

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

				PrmFormula prmFormula = new PrmFormula();

				String temp[] = list.get(i);// 行
				 
				if (StringTool.isNotBlank(temp[0])) {

					prmFormula.setFormula_code(temp[0]);

					mapVo.put("formula_code", temp[0]);

				} else {

					err_sb.append("公式编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					prmFormula.setFormula_name(temp[1]);

					mapVo.put("formula_name", temp[1]);

				} else {

					err_sb.append("公式名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					prmFormula.setFormula_method_chs(temp[2]);

					mapVo.put("formula_method_chs", temp[2]);

				} else {

					err_sb.append("计算公式(中文）为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					prmFormula.setFormula_method_eng(temp[3]);

					mapVo.put("formula_method_eng", temp[3]);

				} else {

					err_sb.append("计算公式(英文)为空  ");

				}
 
					mapVo.put("is_stop", temp[4]);
 
				PrmFormula data_exc_extis = prmFormulaService.queryPrmFormulaByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					prmFormula.setError_type(err_sb.toString());

					list_err.add(prmFormula);

				} else {

					String dataJson = prmFormulaService.addPrmFormula(mapVo);

				}

			}

		} catch (DataAccessException e) {

			PrmFormula data_exc = new PrmFormula();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	

}
