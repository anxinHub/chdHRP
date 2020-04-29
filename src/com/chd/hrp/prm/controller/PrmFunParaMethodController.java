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
import com.chd.hrp.prm.entity.PrmFunParaMethod;
import com.chd.hrp.prm.service.PrmFunParaMethodService;

/**
 * 
 * @Description: 9912 绩效函数参数取值表
 * @Table: PRM_FUN_PARA_METHOD
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmFunParaMethodController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmFunParaMethodController.class);

	// 引入Service服务
	@Resource(name = "prmFunParaMethodService")
	private final PrmFunParaMethodService prmFunParaMethodService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/prmFunParaMethodMainPage", method = RequestMethod.GET)
	public String prmFunParaMethodMainPage(Model mode) throws Exception {

		return "hrp/prm/prmfunparamethod/prmFunParaMethodMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/prmFunParaMethodAddPage", method = RequestMethod.GET)
	public String prmFunParaMethodAddPage(Model mode) throws Exception {

		return "hrp/prm/prmfunparamethod/prmFunParaMethodAdd";

	}

	/**
	 * @Description 添加数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/addPrmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmFunParaMethod(
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
		
		String prmFunParaMethodJson = prmFunParaMethodService.addPrmFunParaMethod(mapVo);

		return JSONObject.parseObject(prmFunParaMethodJson);

	}

	/**
	 * @Description 更新跳转页面 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/prmFunParaMethodUpdatePage", method = RequestMethod.GET)
	public String prmFunParaMethodUpdatePage(
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
		PrmFunParaMethod prmFunParaMethod = new PrmFunParaMethod();
		
		prmFunParaMethod = prmFunParaMethodService.queryPrmFunParaMethodByCode(mapVo);
		
		mode.addAttribute("para_code", prmFunParaMethod.getPara_code());
		
		mode.addAttribute("para_name", prmFunParaMethod.getPara_name());
		
		mode.addAttribute("para_sql", prmFunParaMethod.getPara_sql());
		
		mode.addAttribute("is_stop", prmFunParaMethod.getIs_stop());

		return "hrp/prm/prmfunparamethod/prmFunParaMethodUpdate";
	}

	/**
	 * @Description 更新数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/updatePrmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmFunParaMethod(
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
		
		String prmFunParaMethodJson = prmFunParaMethodService.updatePrmFunParaMethod(mapVo);

		return JSONObject.parseObject(prmFunParaMethodJson);
	}

	/**
	 * @Description 删除数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/deletePrmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmFunParaMethod(
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

		String prmFunParaMethodJson = prmFunParaMethodService.deleteBatchPrmFunParaMethod(listVo);

		return JSONObject.parseObject(prmFunParaMethodJson);

	}

	/**
	 * @Description 查询数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/queryPrmFunParaMethod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmFunParaMethod(
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
		String prmFunParaMethod = prmFunParaMethodService.queryPrmFunParaMethod(getPage(mapVo));

		return JSONObject.parseObject(prmFunParaMethod);

	}

	/**
	 * @Description 导入跳转页面 9912 绩效函数参数取值表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/prmFunParaMethodImportPage", method = RequestMethod.GET)
	public String prmFunParaMethodImportPage(Model mode) throws Exception {

		return "hrp/prm/prmfunparamethod/prmFunParaMethodImport";

	}

	/**
	 * @Description 下载导入模版 9912 绩效函数参数取值表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmfunparamethod/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "prm\\基础设置", "绩效函数参数取值表模版.xls");

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
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/readPrmFunParaMethodFiles", method = RequestMethod.POST)
	public String readPrmFunParaMethodFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<PrmFunParaMethod> list_err = new ArrayList<PrmFunParaMethod>();

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

				PrmFunParaMethod prmFunParaMethod = new PrmFunParaMethod();

				String temp[] = list.get(i);// 行

				if (StringTool.isNotBlank(temp[0])) {

					prmFunParaMethod.setPara_code(temp[0]);

					mapVo.put("para_code", temp[0]);

				} else {

					err_sb.append("参数代码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					prmFunParaMethod.setPara_name(temp[1]);

					mapVo.put("para_name", temp[1]);

				} else {

					err_sb.append("参数名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					prmFunParaMethod.setPara_sql(temp[2]);

					mapVo.put("para_sql", temp[2]);

				} else {

					err_sb.append("取值方法为空  ");

				}

				mapVo.put("is_stop", temp[3]);

				PrmFunParaMethod data_exc_extis = prmFunParaMethodService.queryPrmFunParaMethodByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					prmFunParaMethod.setError_type(err_sb.toString());

					list_err.add(prmFunParaMethod);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));
					
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));

					String dataJson = prmFunParaMethodService.addPrmFunParaMethod(mapVo);

				}

			}

		} catch (DataAccessException e) {

			PrmFunParaMethod data_exc = new PrmFunParaMethod();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	
	// 应用模式字典
	@RequestMapping(value = "/hrp/prm/prmfunparamethod/queryPrmFunParaByDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmFunParaByDict(
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
		return prmFunParaMethodService.queryPrmFunParaByDict(mapVo);

	}

}
