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
import com.chd.hrp.hr.entity.sysstruc.HrFunParaMethod;
import com.chd.hrp.hr.service.sysstruc.HrFunParaMethodService;

/**
 * 
 * @Description: 9912 绩效函数参数取值表
 * @Table: PRM_FUN_PARA_METHOD
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrcaltrans")
public class HrFunParaMethodController extends BaseController {

	private static Logger logger = Logger.getLogger(HrFunParaMethodController.class);

	// 引入Service服务
	@Resource(name = "hrFunParaMethodService")
	private final HrFunParaMethodService hrFunParaMethodService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunParaMethodSetMainPage", method = RequestMethod.GET)
	public String hrFunParaMethodMainPage(Model mode) throws Exception {

		return "hrp/hr/sysstruc/hrfunparamethod/hrFunParaMethodMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunParaMethodAddPage", method = RequestMethod.GET)
	public String hrFunParaMethodAddPage(Model mode) throws Exception {

		return "hrp/hr/sysstruc/hrfunparamethod/hrFunParaMethodAdd";

	}

	/**
	 * @Description 添加数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunParaMethodSetadd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addhrFunParaMethod(
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
		
		String hrFunParaMethodJson = hrFunParaMethodService.addPrmFunParaMethod(mapVo);

		return JSONObject.parseObject(hrFunParaMethodJson);

	}

	/**
	 * @Description 更新跳转页面 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunParaMethodUpdatePage", method = RequestMethod.GET)
	public String hrFunParaMethodUpdatePage(
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
		mapVo.put("para_code", mapVo.get("par_code"));
		HrFunParaMethod hrFunParaMethod = new HrFunParaMethod();
		
		hrFunParaMethod = hrFunParaMethodService.queryPrmFunParaMethodByCode(mapVo);
		
		mode.addAttribute("para_code", hrFunParaMethod.getPara_code());
		
		mode.addAttribute("para_name", hrFunParaMethod.getPara_name());
		
		mode.addAttribute("para_sql", hrFunParaMethod.getPara_sql());
		
		mode.addAttribute("is_stop", hrFunParaMethod.getIs_stop());

		return "hrp/hr/sysstruc/hrfunparamethod/hrFunParaMethodUpdate";
	}

	/**
	 * @Description 更新数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunParaMethodSetupdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatehrFunParaMethod(
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
		
		String hrFunParaMethodJson = hrFunParaMethodService.updatePrmFunParaMethod(mapVo);

		return JSONObject.parseObject(hrFunParaMethodJson);
	}

	/**
	 * @Description 删除数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunParaMethodSetdelete", method = RequestMethod.POST)
	@ResponseBody
	public String hrFunParaMethodSetdelete(@RequestParam String param, Model mode) throws Exception {
		try {
			List<HrFunParaMethod> listVo = JSONArray.parseArray(param, HrFunParaMethod.class);
			return hrFunParaMethodService.deleteBatchPrmFunParaMethod(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
		
		/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

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

		String hrFunParaMethodJson = hrFunParaMethodService.deleteBatchPrmFunParaMethod(listVo);

		return JSONObject.parseObject(hrFunParaMethodJson);*/

	}

	/**
	 * @Description 查询数据 9912 绩效函数参数取值表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunParaMethodSetquery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryhrFunParaMethod(
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
		String hrFunParaMethod = hrFunParaMethodService.queryPrmFunParaMethod(getPage(mapVo));

		return JSONObject.parseObject(hrFunParaMethod);

	}

	/**
	 * @Description 导入跳转页面 9912 绩效函数参数取值表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunParaMethodImportPage", method = RequestMethod.GET)
	public String hrFunParaMethodImportPage(Model mode) throws Exception {

		return "hrp/hr/sysstruc/hrfunparamethod/hrFunParaMethodImport";

	}

	/**
	 * @Description 下载导入模版 9912 绩效函数参数取值表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/downFunParaMethodTemplate", method = RequestMethod.GET)
	public String downFunParaMethodTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {
		
		printTemplate(request, response, "hr\\基础设置", "绩效函数参数取值表模版.xls");

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
	@RequestMapping(value = "/readhrFunParaMethodFiles", method = RequestMethod.POST)
	public String readhrFunParaMethodFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<HrFunParaMethod> list_err = new ArrayList<HrFunParaMethod>();

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

				HrFunParaMethod hrFunParaMethod = new HrFunParaMethod();

				String temp[] = list.get(i);// 行

				if (StringTool.isNotBlank(temp[0])) {

					hrFunParaMethod.setPara_code(temp[0]);

					mapVo.put("para_code", temp[0]);

				} else {

					err_sb.append("参数代码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					hrFunParaMethod.setPara_name(temp[1]);

					mapVo.put("para_name", temp[1]);

				} else {

					err_sb.append("参数名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					hrFunParaMethod.setPara_sql(temp[2]);

					mapVo.put("para_sql", temp[2]);

				} else {

					err_sb.append("取值方法为空  ");

				}

				mapVo.put("is_stop", temp[3]);

				HrFunParaMethod data_exc_extis = hrFunParaMethodService.queryPrmFunParaMethodByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					hrFunParaMethod.setError_type(err_sb.toString());

					list_err.add(hrFunParaMethod);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));
					
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));

					String dataJson = hrFunParaMethodService.addPrmFunParaMethod(mapVo);

				}

			}

		} catch (DataAccessException e) {

			HrFunParaMethod data_exc = new HrFunParaMethod();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	
	// 应用模式字典
	@RequestMapping(value = "/queryhrFunParaByDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryhrFunParaByDict(
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
		return hrFunParaMethodService.queryPrmFunParaByDict(mapVo);

	}

}
