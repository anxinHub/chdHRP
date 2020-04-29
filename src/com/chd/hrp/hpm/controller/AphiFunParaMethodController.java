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
import com.chd.hrp.hpm.entity.AphiFunParaMethod;
import com.chd.hrp.hpm.service.AphiFunParaMethodService;

/**
 * 
 * @Description: 9912 绩效函数参数取值表
 * @Table: PRM_FUN_PARA_METHOD
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiFunParaMethodController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiFunParaMethodController.class);

	// 引入Service服务
	@Resource(name = "aphiFunParaMethodService")
	private final AphiFunParaMethodService aphiFunParaMethodService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/hpmFunParaMethodMainPage", method = RequestMethod.GET)
	public String hpmFunParaMethodMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfunparamethod/hpmFunParaMethodMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/hpmFunParaMethodAddPage", method = RequestMethod.GET)
	public String hpmFunParaMethodAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfunparamethod/hpmFunParaMethodAdd";

	}

	/**
	 * @Description 添加数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/addHpmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmFunParaMethod(
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

		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));
		
		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("para_name").toString()));
		
		mapVo.put("is_stop", "0");
		
		String hpmFunParaMethodJson = aphiFunParaMethodService.addPrmFunParaMethod(mapVo);

		return JSONObject.parseObject(hpmFunParaMethodJson);

	}

	/**
	 * @Description 更新跳转页面 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/hpmFunParaMethodUpdatePage", method = RequestMethod.GET)
	public String hpmFunParaMethodUpdatePage(
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
		AphiFunParaMethod hpmFunParaMethod = new AphiFunParaMethod();
		
		hpmFunParaMethod = aphiFunParaMethodService.queryPrmFunParaMethodByCode(mapVo);
		
		mode.addAttribute("para_code", hpmFunParaMethod.getPara_code());
		
		mode.addAttribute("para_name", hpmFunParaMethod.getPara_name());
		
		mode.addAttribute("para_sql", hpmFunParaMethod.getPara_sql());
		
		mode.addAttribute("is_stop", hpmFunParaMethod.getIs_stop());

		return "hrp/hpm/hpmfunparamethod/hpmFunParaMethodUpdate";
	}

	/**
	 * @Description 更新数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/updateHpmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmFunParaMethod(
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
		
		mapVo.put("is_stop", "0");
		
		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));
		
		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("para_name").toString()));
		
		String hpmFunParaMethodJson = aphiFunParaMethodService.updatePrmFunParaMethod(mapVo);

		return JSONObject.parseObject(hpmFunParaMethodJson);
	}

	/**
	 * @Description 删除数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/deleteHpmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmFunParaMethod(
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
			
			mapVo.put("para_code", ids[3]);

			listVo.add(mapVo);

		}

		String hpmFunParaMethodJson = aphiFunParaMethodService.deleteBatchPrmFunParaMethod(listVo);

		return JSONObject.parseObject(hpmFunParaMethodJson);

	}

	/**
	 * @Description 查询数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/queryHpmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmFunParaMethod(
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
		String hpmFunParaMethod = aphiFunParaMethodService.queryPrmFunParaMethod(getPage(mapVo));

		return JSONObject.parseObject(hpmFunParaMethod);

	}

	/**
	 * @Description 导入跳转页面 9912 绩效函数参数取值表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/hpmFunParaMethodImportPage", method = RequestMethod.GET)
	public String hpmFunParaMethodImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfunparamethod/hpmFunParaMethodImport";

	}

	/**
	 * @Description 下载导入模版 9912 绩效函数参数取值表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmfunparamethod/downFunParaMethodTemplate", method = RequestMethod.GET)
	public String downFunParaMethodTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {
		
		printTemplate(request, response, "hpm\\基础设置", "绩效函数参数取值表模版.xls");

		return null;
	}

	/**
	 * @Description 导入数据 9912 绩效函数参数取值表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/readHpmFunParaMethodFiles", method = RequestMethod.POST)
	public String readHpmFunParaMethodFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<AphiFunParaMethod> list_err = new ArrayList<AphiFunParaMethod>();

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

				AphiFunParaMethod hpmFunParaMethod = new AphiFunParaMethod();

				String temp[] = list.get(i);// 行

				if (StringTool.isNotBlank(temp[0])) {

					hpmFunParaMethod.setPara_code(temp[0]);

					mapVo.put("para_code", temp[0]);

				} else {

					err_sb.append("参数代码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					hpmFunParaMethod.setPara_name(temp[1]);

					mapVo.put("para_name", temp[1]);

				} else {

					err_sb.append("参数名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					hpmFunParaMethod.setPara_sql(temp[2]);

					mapVo.put("para_sql", temp[2]);

				} else {

					err_sb.append("取值方法为空  ");

				}

				mapVo.put("is_stop", temp[3]);

				AphiFunParaMethod data_exc_extis = aphiFunParaMethodService.queryPrmFunParaMethodByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					hpmFunParaMethod.setError_type(err_sb.toString());

					list_err.add(hpmFunParaMethod);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));
					
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));

					String dataJson = aphiFunParaMethodService.addPrmFunParaMethod(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AphiFunParaMethod data_exc = new AphiFunParaMethod();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	
	// 应用模式字典
	@RequestMapping(value = "/hrp/hpm/hpmfunparamethod/queryHpmFunParaByDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHpmFunParaByDict(
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
		return aphiFunParaMethodService.queryPrmFunParaByDict(mapVo);

	}

}
