/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.hpm.entity.AphiFunType;
import com.chd.hrp.hpm.service.AphiFunTypeService;

/**
 * 
 * @Description: 9909 绩效函数分类表
 * @Table: PRM_FUN_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiFunTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiFunTypeController.class);

	// 引入Service服务
	@Resource(name = "aphiFunTypeService")
	private final AphiFunTypeService aphiFunTypeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/hpmFunTypeMainPage", method = RequestMethod.GET)
	public String hpmFunTypeMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfuntype/hpmFunTypeMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/hpmFunTypeAddPage", method = RequestMethod.GET)
	public String hpmFunTypeAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfuntype/hpmFunTypeAdd";

	}

	/**
	 * @Description 添加数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/addHpmFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmFunType(
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
		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		
		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("type_name").toString()));

		String hpmFunTypeJson = aphiFunTypeService.addPrmFunType(mapVo);

		return JSONObject.parseObject(hpmFunTypeJson);

	}

	/**
	 * @Description 更新跳转页面 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/hpmFunTypeUpdatePage", method = RequestMethod.GET)
	public String hpmFunTypeUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		AphiFunType hpmFunType = new AphiFunType();

		hpmFunType = aphiFunTypeService.queryPrmFunTypeByCode(mapVo);
		
		mode.addAttribute("group_id", hpmFunType.getGroup_id());
		
		mode.addAttribute("hos_id", hpmFunType.getHos_id());
		
		mode.addAttribute("copy_code", hpmFunType.getCopy_code());
		
		mode.addAttribute("type_code", hpmFunType.getType_code());
		
		mode.addAttribute("type_name", hpmFunType.getType_name());
		
		mode.addAttribute("spell_code", hpmFunType.getSpell_code());
		
		mode.addAttribute("wbx_code", hpmFunType.getWbx_code());
		
		mode.addAttribute("is_stop", hpmFunType.getIs_stop());
		
		return "hrp/hpm/hpmfuntype/hpmFunTypeUpdate";
	}

	/**
	 * @Description 更新数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/updateHpmFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmFunType(
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
		
		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		
		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("type_name").toString()));
		
		String hpmFunTypeJson = aphiFunTypeService.updatePrmFunType(mapVo);

		return JSONObject.parseObject(hpmFunTypeJson);
	}

	/**
	 * @Description 删除数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/deleteHpmFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmFunType(
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
			
			mapVo.put("type_code", ids[3]);

			listVo.add(mapVo);

		}

		String hpmFunTypeJson = aphiFunTypeService.deleteBatchPrmFunType(listVo);

		return JSONObject.parseObject(hpmFunTypeJson);

	}

	/**
	 * @Description 查询数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/queryHpmFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmFunType(
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
		
		String hpmFunType = aphiFunTypeService.queryPrmFunType(getPage(mapVo));

		return JSONObject.parseObject(hpmFunType);

	}

	/**
	 * @Description 导入跳转页面 9909 绩效函数分类表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/hpmFunTypeImportPage", method = RequestMethod.GET)
	public String hpmFunTypeImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfuntype/hpmFunTypeImport";

	}

	/**
	 * @Description 下载导入模版 9909 绩效函数分类表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmfuntype/downFunTypeTemplate", method = RequestMethod.GET)
	public String downFunTypeTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\基础设置", "函数分类表模板.xls");

		return null;
	}
	
	/**
	 * @Description 导入数据 9909 绩效函数分类表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/readHpmFunTypeFiles", method = RequestMethod.POST)
	public String readHpmFunTypeFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<AphiFunType> list_err = new ArrayList<AphiFunType>();

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

				AphiFunType hpmFunType = new AphiFunType();

				String temp[] = list.get(i);// 行
				
				if (StringTool.isNotBlank(temp[0])) {

					hpmFunType.setType_code(temp[0]);

					mapVo.put("type_code", temp[0]);

				} else {

					err_sb.append("分类编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					hpmFunType.setType_name(temp[1]);

					mapVo.put("type_name", temp[1]);

				} else {

					err_sb.append("分类名称为空  ");

				}
				
					mapVo.put("is_stop", temp[2]);
 
					AphiFunType data_exc_extis = aphiFunTypeService.queryPrmFunTypeByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				
				if (err_sb.toString().length() > 0) {

					hpmFunType.setError_type(err_sb.toString());

					list_err.add(hpmFunType);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("type_name").toString()));

					String dataJson = aphiFunTypeService.addPrmFunType(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AphiFunType data_exc = new AphiFunType();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	

	// 函数分类树
	@RequestMapping(value = "/hrp/hpm/hpmfuntype/queryHpmFunTypeTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmFunTypeTree(
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

		String funTree = aphiFunTypeService.queryPrmFunTree(mapVo);

		return JSONObject.parseObject(funTree);

	}

}
