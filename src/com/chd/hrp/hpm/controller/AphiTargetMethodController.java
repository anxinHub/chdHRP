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
import com.chd.hrp.hpm.entity.AphiTargetMethod;
import com.chd.hrp.hpm.service.AphiTargetMethodService;

/**
 * 
 * @Description: 9904 绩效指标取值方法配置表
 * @Table: PRM_TARGET_METHOD
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiTargetMethodController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiTargetMethodController.class);

	// 引入Service服务
	@Resource(name = "aphiTargetMethodService")
	private final AphiTargetMethodService aphiTargetMethodService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/hpmTargetMethodMainPage", method = RequestMethod.GET)
	public String hpmTargetMethodMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmtargetmethod/hpmTargetMethodMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/hpmTargetMethodAddPage", method = RequestMethod.GET)
	public String hpmTargetMethodAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmtargetmethod/hpmTargetMethodAdd";

	}

	/**
	 * @Description 添加数据 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/addHpmTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmTargetMethod(
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

		String hpmTargetMethodJson = aphiTargetMethodService.addPrmTargetMethod(mapVo);

		return JSONObject.parseObject(hpmTargetMethodJson);

	}

	/**
	 * @Description 更新跳转页面 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/hpmTargetMethodUpdatePage", method = RequestMethod.GET)
	public String hpmTargetMethodUpdatePage(
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

		AphiTargetMethod hpmTargetMethod = new AphiTargetMethod();

		hpmTargetMethod = aphiTargetMethodService.queryPrmTargetMethodNatureByCode(mapVo);

		mode.addAttribute("target_code", hpmTargetMethod.getTarget_code());

		mode.addAttribute("method_code", hpmTargetMethod.getMethod_code());

		mode.addAttribute("method_name", hpmTargetMethod.getMethod_name());

		mode.addAttribute("formula_code", hpmTargetMethod.getFormula_code());

		mode.addAttribute("fun_code", hpmTargetMethod.getFun_code());

		mode.addAttribute("target_name", hpmTargetMethod.getTarget_name());

		mode.addAttribute("nature_code", hpmTargetMethod.getNature_code());

		mode.addAttribute("nature_name", hpmTargetMethod.getNature_name());

		mode.addAttribute("formula_name", hpmTargetMethod.getFormula_name());
		
		
		if( hpmTargetMethod.getFormula_method_chs() == null){
			mode.addAttribute("formula_method_chs","");
		}else{
			String formula_method_chs = hpmTargetMethod.getFormula_method_chs();
			formula_method_chs = formula_method_chs.replaceAll("(\r\n|\r|\n|\n\r)", "");
			formula_method_chs = formula_method_chs.replaceAll("&gt;", ">").replaceAll("&lt;", "<");
			mode.addAttribute("formula_method_chs",formula_method_chs);
		}
		
		mode.addAttribute("fun_method_chs",hpmTargetMethod.getFun_method_chs());

		mode.addAttribute("fun_name", hpmTargetMethod.getFun_name());

		mode.addAttribute("fun_note", hpmTargetMethod.getFun_note());

		mode.addAttribute("f_p_v", hpmTargetMethod.getF_p_v());

		return "hrp/hpm/hpmtargetmethod/hpmTargetMethodUpdate";
	}

	/**
	 * @Description 更新数据 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/updateHpmTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmTargetMethod(
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
		
		String hpmTargetMethodJson = aphiTargetMethodService.updatePrmTargetMethod(mapVo);

		return JSONObject.parseObject(hpmTargetMethodJson);
	}

	/**
	 * @Description 删除数据 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/deleteHpmTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmTargetMethod(
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

		String hpmTargetMethodJson = aphiTargetMethodService.deleteBatchPrmTargetMethod(listVo);

		return JSONObject.parseObject(hpmTargetMethodJson);

	}

	/**
	 * @Description 查询数据 9904 绩效指标取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/queryHpmTargetMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmTargetMethod(
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
		
		String hpmTargetMethod = aphiTargetMethodService.queryPrmTargetMethod(getPage(mapVo));

		return JSONObject.parseObject(hpmTargetMethod);

	}

	/**
	 * @Description 导入跳转页面 9904 绩效指标取值方法配置表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/hpmTargetMethodImportPage", method = RequestMethod.GET)
	public String hpmTargetMethodImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmtargetmethod/hpmTargetMethodImport";

	}

	/**
	 * @Description 下载导入模版 9904 绩效指标取值方法配置表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmtargetmethod/downFunTargetMethodTemplate", method = RequestMethod.GET)
	public String downFunTargetMethodTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {
		
		printTemplate(request, response, "hpm\\基础设置", "取值方法字典模板.xls.xls");

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
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/readHpmTargetMethodFiles", method = RequestMethod.POST)
	public String readHpmTargetMethodFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<AphiTargetMethod> list_err = new ArrayList<AphiTargetMethod>();

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

				AphiTargetMethod hpmTargetMethod = new AphiTargetMethod();

				String temp[] = list.get(i);// 行
				
				if (StringTool.isNotBlank(temp[0])) {

					hpmTargetMethod.setTarget_code(temp[0]);

					mapVo.put("target_code", temp[0]);

				} else {

					err_sb.append("指标编码为空  ");

				}
 
				if (StringTool.isNotBlank(temp[1])) {

					hpmTargetMethod.setMethod_code(temp[1]);

					mapVo.put("method_code", temp[1]);

				} else {

					err_sb.append("01:手工录入 02:计算公式 03:取值函数为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					hpmTargetMethod.setFormula_code(temp[2]);

					mapVo.put("formula_code", temp[2]);

				} else {

					err_sb.append("取值公式为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					hpmTargetMethod.setFun_code(temp[3]);

					mapVo.put("fun_code", temp[3]);

				} else {

					err_sb.append("取值函数为空  ");

				}
				

				mapVo.put("fun_para_code", "0");

				AphiTargetMethod data_exc_extis = aphiTargetMethodService.queryPrmTargetMethodByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					hpmTargetMethod.setError_type(err_sb.toString());

					list_err.add(hpmTargetMethod);

				} else {

					String dataJson = aphiTargetMethodService.addPrmTargetMethod(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AphiTargetMethod data_exc = new AphiTargetMethod();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	
	// 自己加的方法 查询表target hpm_target_nature hpm_target_method
	// hpm_target_method_para
	/**
	 * @Description 查询数据 9903 指标取值方法参数表 关联指标表、指标性质表、取值方法参数表、取值方法配置表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmtargetmethod/queryHpmTargetMethodNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmTargetMethodNature(
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
		String hpmTargetMethodNature = aphiTargetMethodService
				.queryPrmTargetMethodNature(getPage(mapVo));

		return JSONObject.parseObject(hpmTargetMethodNature);

	}
}
