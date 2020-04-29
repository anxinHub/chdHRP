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
import com.chd.hrp.prm.entity.PrmFunType;
import com.chd.hrp.prm.service.PrmFunTypeService;

/**
 * 
 * @Description: 9909 绩效函数分类表
 * @Table: PRM_FUN_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmFunTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmFunTypeController.class);

	// 引入Service服务
	@Resource(name = "prmFunTypeService")
	private final PrmFunTypeService prmFunTypeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfuntype/prmFunTypeMainPage", method = RequestMethod.GET)
	public String prmFunTypeMainPage(Model mode) throws Exception {

		return "hrp/prm/prmfuntype/prmFunTypeMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfuntype/prmFunTypeAddPage", method = RequestMethod.GET)
	public String prmFunTypeAddPage(Model mode) throws Exception {

		return "hrp/prm/prmfuntype/prmFunTypeAdd";

	}

	/**
	 * @Description 添加数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfuntype/addPrmFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmFunType(
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

		String prmFunTypeJson = prmFunTypeService.addPrmFunType(mapVo);

		return JSONObject.parseObject(prmFunTypeJson);

	}

	/**
	 * @Description 更新跳转页面 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfuntype/prmFunTypeUpdatePage", method = RequestMethod.GET)
	public String prmFunTypeUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		PrmFunType prmFunType = new PrmFunType();

		prmFunType = prmFunTypeService.queryPrmFunTypeByCode(mapVo);
		
		mode.addAttribute("group_id", prmFunType.getGroup_id());
		
		mode.addAttribute("hos_id", prmFunType.getHos_id());
		
		mode.addAttribute("copy_code", prmFunType.getCopy_code());
		
		mode.addAttribute("type_code", prmFunType.getType_code());
		
		mode.addAttribute("type_name", prmFunType.getType_name());
		
		mode.addAttribute("spell_code", prmFunType.getSpell_code());
		
		mode.addAttribute("wbx_code", prmFunType.getWbx_code());
		
		mode.addAttribute("is_stop", prmFunType.getIs_stop());
		
		return "hrp/prm/prmfuntype/prmFunTypeUpdate";
	}

	/**
	 * @Description 更新数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfuntype/updatePrmFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmFunType(
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
		
		String prmFunTypeJson = prmFunTypeService.updatePrmFunType(mapVo);

		return JSONObject.parseObject(prmFunTypeJson);
	}

	/**
	 * @Description 删除数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfuntype/deletePrmFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmFunType(
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

		String prmFunTypeJson = prmFunTypeService.deleteBatchPrmFunType(listVo);

		return JSONObject.parseObject(prmFunTypeJson);

	}

	/**
	 * @Description 查询数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfuntype/queryPrmFunType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmFunType(
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
		
		String prmFunType = prmFunTypeService.queryPrmFunType(getPage(mapVo));

		return JSONObject.parseObject(prmFunType);

	}

	/**
	 * @Description 导入跳转页面 9909 绩效函数分类表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfuntype/prmFunTypeImportPage", method = RequestMethod.GET)
	public String prmFunTypeImportPage(Model mode) throws Exception {

		return "hrp/prm/prmfuntype/prmFunTypeImport";

	}

	/**
	 * @Description 下载导入模版 9909 绩效函数分类表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmfuntype/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "prm\\基础设置", "函数分类表模板.xls");

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
	@RequestMapping(value = "/hrp/prm/prmfuntype/readPrmFunTypeFiles", method = RequestMethod.POST)
	public String readPrmFunTypeFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<PrmFunType> list_err = new ArrayList<PrmFunType>();

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

				PrmFunType prmFunType = new PrmFunType();

				String temp[] = list.get(i);// 行
				
				if (StringTool.isNotBlank(temp[0])) {

					prmFunType.setType_code(temp[0]);

					mapVo.put("type_code", temp[0]);

				} else {

					err_sb.append("分类编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					prmFunType.setType_name(temp[1]);

					mapVo.put("type_name", temp[1]);

				} else {

					err_sb.append("分类名称为空  ");

				}
				
					mapVo.put("is_stop", temp[2]);
 
				PrmFunType data_exc_extis = prmFunTypeService.queryPrmFunTypeByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				
				if (err_sb.toString().length() > 0) {

					prmFunType.setError_type(err_sb.toString());

					list_err.add(prmFunType);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("type_name").toString()));

					String dataJson = prmFunTypeService.addPrmFunType(mapVo);

				}

			}

		} catch (DataAccessException e) {

			PrmFunType data_exc = new PrmFunType();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	

	// 函数分类树
	@RequestMapping(value = "/hrp/prm/prmfuntype/queryPrmFunTypeTree", method = RequestMethod.POST)
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

		String funTree = prmFunTypeService.queryPrmFunTree(mapVo);

		return JSONObject.parseObject(funTree);

	}

}
