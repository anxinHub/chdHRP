/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.prm.entity.PrmTargetMethod;
import com.chd.hrp.prm.service.PrmTargetMethodService;

/**
 * 
 * @Description: 9904 绩效指标取值方法配置表
 * @Table: PRM_TARGET_METHOD
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmTargetMethodController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmTargetMethodController.class);

	// 引入Service服务
	@Resource(name = "prmTargetMethodService")
	private final PrmTargetMethodService prmTargetMethodService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/prmTargetMethodMainPage", method = RequestMethod.GET)
	public String prmTargetMethodMainPage(Model mode) throws Exception {

		return "hrp/prm/prmtargetmethod/prmTargetMethodMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/prmTargetMethodAddPage", method = RequestMethod.GET)
	public String prmTargetMethodAddPage(Model mode) throws Exception {

		return "hrp/prm/prmtargetmethod/prmTargetMethodAdd";

	}

	/**
	 * @Description 添加数据 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/addPrmTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmTargetMethod(
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

		mapVo.put("fun_para_code", "0");

		String prmTargetMethodJson = prmTargetMethodService.addPrmTargetMethod(mapVo);

		return JSONObject.parseObject(prmTargetMethodJson);

	}

	/**
	 * @Description 更新跳转页面 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/prmTargetMethodUpdatePage", method = RequestMethod.GET)
	public String prmTargetMethodUpdatePage(
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

		PrmTargetMethod prmTargetMethod = new PrmTargetMethod();

		prmTargetMethod = prmTargetMethodService.queryPrmTargetMethodNatureByCode(mapVo);

		mode.addAttribute("target_code", prmTargetMethod.getTarget_code());

		mode.addAttribute("method_code", prmTargetMethod.getMethod_code());

		mode.addAttribute("method_name", prmTargetMethod.getMethod_name());

		mode.addAttribute("formula_code", prmTargetMethod.getFormula_code());

		mode.addAttribute("fun_code", prmTargetMethod.getFun_code());

		mode.addAttribute("target_name", prmTargetMethod.getTarget_name());

		mode.addAttribute("nature_code", prmTargetMethod.getNature_code());

		mode.addAttribute("nature_name", prmTargetMethod.getNature_name());

		mode.addAttribute("formula_name", prmTargetMethod.getFormula_name());

		mode.addAttribute("formula_method_chs",prmTargetMethod.getFormula_method_chs());
		
		mode.addAttribute("fun_method_chs",prmTargetMethod.getFun_method_chs());

		mode.addAttribute("fun_name", prmTargetMethod.getFun_name());

		mode.addAttribute("fun_note", prmTargetMethod.getFun_note());

		mode.addAttribute("f_p_v", prmTargetMethod.getF_p_v());

		return "hrp/prm/prmtargetmethod/prmTargetMethodUpdate";
	}

	/**
	 * @Description 更新数据 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/updatePrmTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmTargetMethod(
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
		
		mapVo.put("fun_para_code", "0");
		
		String prmTargetMethodJson = prmTargetMethodService.updatePrmTargetMethod(mapVo);

		return JSONObject.parseObject(prmTargetMethodJson);
	}

	/**
	 * @Description 删除数据 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/deletePrmTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmTargetMethod(
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
			
			mapVo.put("target_code", ids[3]);

			listVo.add(mapVo);

		}

		String prmTargetMethodJson = prmTargetMethodService.deleteBatchPrmTargetMethod(listVo);

		return JSONObject.parseObject(prmTargetMethodJson);

	}

	/**
	 * @Description 查询数据 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/queryPrmTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmTargetMethod(
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
		
		String prmTargetMethod = prmTargetMethodService.queryPrmTargetMethod(getPage(mapVo));

		return JSONObject.parseObject(prmTargetMethod);

	}

	/**
	 * @Description 导入跳转页面 9904 绩效指标取值方法配置表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/prmTargetMethodImportPage", method = RequestMethod.GET)
	public String prmTargetMethodImportPage(Model mode) throws Exception {

		return "hrp/prm/prmtargetmethod/prmTargetMethodImport";

	}

	/**
	 * @Description 下载导入模版 9904 绩效指标取值方法配置表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmtargetmethod/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "prm\\基础设置", "取值方法字典模板.xls.xls");

		return null;
	}

	/**
	 * @Description 导入数据 9904 绩效指标取值方法配置表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/readPrmTargetMethodFiles", method = RequestMethod.POST)
	public String readPrmTargetMethodFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<PrmTargetMethod> list_err = new ArrayList<PrmTargetMethod>();

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

				PrmTargetMethod prmTargetMethod = new PrmTargetMethod();

				String temp[] = list.get(i);// 行
				
				if (StringTool.isNotBlank(temp[0])) {

					prmTargetMethod.setTarget_code(temp[0]);

					mapVo.put("target_code", temp[0]);

				} else {

					err_sb.append("指标编码为空  ");

				}
 
				if (StringTool.isNotBlank(temp[1])) {

					prmTargetMethod.setMethod_code(temp[1]);

					mapVo.put("method_code", temp[1]);

				} else {

					err_sb.append("01:手工录入 02:计算公式 03:取值函数为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					prmTargetMethod.setFormula_code(temp[2]);

					mapVo.put("formula_code", temp[2]);

				} else {

					err_sb.append("取值公式为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					prmTargetMethod.setFun_code(temp[3]);

					mapVo.put("fun_code", temp[3]);

				} else {

					err_sb.append("取值函数为空  ");

				}
				

				mapVo.put("fun_para_code", "0");

				PrmTargetMethod data_exc_extis = prmTargetMethodService.queryPrmTargetMethodByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					prmTargetMethod.setError_type(err_sb.toString());

					list_err.add(prmTargetMethod);

				} else {

					String dataJson = prmTargetMethodService.addPrmTargetMethod(mapVo);

				}

			}

		} catch (DataAccessException e) {

			PrmTargetMethod data_exc = new PrmTargetMethod();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	
	// 自己加的方法 查询表target prm_target_nature prm_target_method
	// prm_target_method_para
	/**
	 * @Description 查询数据 9903 指标取值方法参数表 关联指标表、指标性质表、取值方法参数表、取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmtargetmethod/queryPrmTargetMethodNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmTargetMethodNature(
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
		String prmTargetMethodNature = prmTargetMethodService
				.queryPrmTargetMethodNature(getPage(mapVo));

		return JSONObject.parseObject(prmTargetMethodNature);

	}
}
