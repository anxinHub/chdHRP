/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.controller.sysstruc;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hr.entity.sysstruc.HrCaltrans;
import com.chd.hrp.hr.entity.sysstruc.HrFunType;
import com.chd.hrp.hr.service.sysstruc.HrFunTypeService;

/**
 * 
 * @Description: 9909 绩效函数分类表
 * @Table: PRM_FUN_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrcaltrans")
public class HrFunTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(HrFunTypeController.class);

	// 引入Service服务
	@Resource(name = "hrFunTypeService")
	private final HrFunTypeService hrFunTypeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunTypeSetMainPage", method = RequestMethod.GET)
	public String hrFunTypeMainPage(Model mode) throws Exception {

		return "hrp/hr/sysstruc/hrfuntype/hrFunTypeMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunTypeAddPage", method = RequestMethod.GET)
	public String hrFunTypeAddPage(Model mode) throws Exception {

		return "hrp/hr/sysstruc/hrfuntype/hrFunTypeAdd";

	}

	/**
	 * @Description 添加数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunTypeSetadd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addhrFunType(
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

		String hrFunTypeJson = hrFunTypeService.addPrmFunType(mapVo);

		return JSONObject.parseObject(hrFunTypeJson);

	}

	/**
	 * @Description 更新跳转页面 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunTypeUpdatePage", method = RequestMethod.GET)
	public String hrFunTypeUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		HrFunType hrFunType = new HrFunType();

		hrFunType = hrFunTypeService.queryPrmFunTypeByCode(mapVo);
		
		mode.addAttribute("group_id", hrFunType.getGroup_id());
		
		mode.addAttribute("hos_id", hrFunType.getHos_id());
		
		mode.addAttribute("copy_code", hrFunType.getCopy_code());
		
		mode.addAttribute("type_code", hrFunType.getType_code());
		
		mode.addAttribute("type_name", hrFunType.getType_name());
		
		mode.addAttribute("spell_code", hrFunType.getSpell_code());
		
		mode.addAttribute("wbx_code", hrFunType.getWbx_code());
		
		mode.addAttribute("is_stop", hrFunType.getIs_stop());
		
		return "hrp/hr/sysstruc/hrfuntype/hrFunTypeUpdate";
	}

	/**
	 * @Description 更新数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunTypeSetupdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatehrFunType(
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
		
		String hrFunTypeJson = hrFunTypeService.updatePrmFunType(mapVo);

		return JSONObject.parseObject(hrFunTypeJson);
	}

	/**
	 * @Description 删除数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunTypeSetdelete", method = RequestMethod.POST)
	@ResponseBody
	public String deletehrFunType(@RequestParam String param, Model mode) throws Exception {

		try {
			List<HrFunType> listVo = JSONArray.parseArray(param, HrFunType.class);
			return hrFunTypeService.deleteBatchPrmFunType(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 查询数据 9909 绩效函数分类表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunTypeSetquery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryhrFunType(
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
		
		String hrFunType = hrFunTypeService.queryPrmFunType(getPage(mapVo));

		return JSONObject.parseObject(hrFunType);

	}

	/**
	 * @Description 导入跳转页面 9909 绩效函数分类表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunTypeImportPage", method = RequestMethod.GET)
	public String hrFunTypeImportPage(Model mode) throws Exception {

		return "hrp/hr/sysstruc/hrfuntype/hrFunTypeImport";

	}

	/**
	 * @Description 下载导入模版 9909 绩效函数分类表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/downFunTypeTemplate", method = RequestMethod.GET)
	public String downFunTypeTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hr\\基础设置", "函数分类表模板.xls");

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
	@RequestMapping(value = "/readhrFunTypeFiles", method = RequestMethod.POST)
	public String readhrFunTypeFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<HrFunType> list_err = new ArrayList<HrFunType>();

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

				HrFunType hrFunType = new HrFunType();

				String temp[] = list.get(i);// 行
				
				if (StringTool.isNotBlank(temp[0])) {

					hrFunType.setType_code(temp[0]);

					mapVo.put("type_code", temp[0]);

				} else {

					err_sb.append("分类编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					hrFunType.setType_name(temp[1]);

					mapVo.put("type_name", temp[1]);

				} else {

					err_sb.append("分类名称为空  ");

				}
				
					mapVo.put("is_stop", temp[2]);
 
					HrFunType data_exc_extis = hrFunTypeService.queryPrmFunTypeByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				
				if (err_sb.toString().length() > 0) {

					hrFunType.setError_type(err_sb.toString());

					list_err.add(hrFunType);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("type_name").toString()));

					String dataJson = hrFunTypeService.addPrmFunType(mapVo);

				}

			}

		} catch (DataAccessException e) {

			HrFunType data_exc = new HrFunType();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;

	}
	

	// 函数分类树
	@RequestMapping(value = "/queryHrFunTypeTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrFunTypeTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return hrFunTypeService.queryPrmFunTree(mapVo);
	}

}
