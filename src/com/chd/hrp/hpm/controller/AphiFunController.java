
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
import com.chd.hrp.hpm.entity.AphiFun;
import com.chd.hrp.hpm.service.AphiFunParaService;
import com.chd.hrp.hpm.service.AphiFunService;

/**
 * 
 * @Description: 9908 绩效函数表
 * @Table: PRM_FUN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiFunController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiFunController.class);

	// 引入Service服务
	@Resource(name = "aphiFunService")
	private final AphiFunService aphiFunService = null;

	@Resource(name = "aphiFunParaService")
	private final AphiFunParaService aphiFunParaService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfun/hpmFunMainPage", method = RequestMethod.GET)
	public String hpmFunMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfun/hpmFunMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfun/hpmFunAddPage", method = RequestMethod.GET)
	public String hpmFunAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfun/hpmFunAdd";

	}

	@RequestMapping(value = "/hrp/hpm/hpmfun/hpmFunPage", method = RequestMethod.GET)
	public String hpmFunPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("target_code", mapVo.get("target_code"));
		mode.addAttribute("fun_code", mapVo.get("fun_code"));
		return "hrp/hpm/hpmfun/hpmFun";
	}

	@RequestMapping(value = "/hrp/hpm/hpmfun/hpmDeptFunPage", method = RequestMethod.GET)
	public String hpmDeptFunPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		mode.addAttribute("goal_code", mapVo.get("goal_code"));
		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("fun_code", mapVo.get("fun_code"));
		return "hrp/hpm/hpmfun/hpmDeptFun";
	}


	/**
	 * @Description 添加数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfun/addHpmFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String hpmFunJson = null;
		
		try {
			
			hpmFunJson = aphiFunService.addPrmFun(mapVo);
			
		} catch (Exception e) {
			
			hpmFunJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}


		return JSONObject.parseObject(hpmFunJson);

	}

	/**
	 * @Description 更新跳转页面 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfun/hpmFunUpdatePage", method = RequestMethod.GET)
	public String hpmFunUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AphiFun hpmFun = new AphiFun();

		hpmFun = aphiFunService.queryPrmFunComtype(mapVo);

		mode.addAttribute("fun_code", hpmFun.getFun_code());

		mode.addAttribute("fun_name", hpmFun.getFun_name());

		mode.addAttribute("type_code", hpmFun.getType_code());

		mode.addAttribute("fun_method_chs", hpmFun.getFun_method_chs());

		mode.addAttribute("fun_method_eng", hpmFun.getFun_method_eng());

		mode.addAttribute("fun_note", hpmFun.getFun_note());

		mode.addAttribute("spell_code", hpmFun.getSpell_code());

		mode.addAttribute("wbx_code", hpmFun.getWbx_code());

		mode.addAttribute("is_pre", hpmFun.getIs_pre());

		mode.addAttribute("is_rec", hpmFun.getIs_rec());

		mode.addAttribute("is_stop", hpmFun.getIs_stop());

		mode.addAttribute("type_name", hpmFun.getType_name());

		mode.addAttribute("prc_name", hpmFun.getPrc_name());

		mode.addAttribute("pkg_name", hpmFun.getPkg_name());
		
		if(hpmFun.getFun_sql() != null){
			String fun_sql = hpmFun.getFun_sql();
			mode.addAttribute("fun_sql", fun_sql.replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		}else{
			mode.addAttribute("fun_sql", "");
		}
		
		
		
		

		return "hrp/hpm/hpmfun/hpmFunUpdate";
	}

	/**
	 * @Description 更新数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfun/updateHpmFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String hpmFunJson = aphiFunService.updatePrmFun(mapVo);

		return JSONObject.parseObject(hpmFunJson);
	}

	/**
	 * @Description 删除数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfun/deleteHpmFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmFun(@RequestParam(value = "ParamVo") String ParamVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : ParamVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("fun_code", ids[0]);

			mapVo.put("group_id", ids[1]);

			mapVo.put("hos_id", ids[2]);

			mapVo.put("copy_code", ids[3]);
			
			mapVo.put("pkg_name", ids[4]);

			listVo.add(mapVo);

		}
		String hpmFunJson = aphiFunService.deleteBatchPrmFun(listVo);
		return JSONObject.parseObject(hpmFunJson);
	}
	
	@RequestMapping(value = "/hrp/hpm/hpmfun/initHpmFunProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmFunProc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hpmFun = aphiFunService.initPrmFunProc(mapVo);

		return JSONObject.parseObject(hpmFun);

	}
	

	/**
	 * @Description 查询数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfun/queryHpmFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String hpmFun = aphiFunService.queryPrmFun(getPage(mapVo));

		return JSONObject.parseObject(hpmFun);

	}

	@RequestMapping(value = "/hrp/hpm/hpmfun/queryHpmFunParaByFunCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmFunParaByFunCode(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String hpmFun = aphiFunParaService.queryPrmFunParaByFunCode(getPage(mapVo));

		return JSONObject.parseObject(hpmFun);
	}

	@RequestMapping(value = "/hrp/hpm/hpmfun/deleteHpmFunParaByFunCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmFunParaByFunCode(@RequestParam(value = "ParamVo") String ParamVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : ParamVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("fun_code", ids[3]);
			
			mapVo.put("para_code", ids[4]);

			listVo.add(mapVo);

		}
		String hpmFunJson = aphiFunParaService.deleteBatchPrmFunPara(listVo);

		return JSONObject.parseObject(hpmFunJson);

	}

	/**
	 * @Description 导入跳转页面 9908 绩效函数表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmfun/hpmFunImportPage", method = RequestMethod.GET)
	public String hpmFunImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmfun/hpmFunImport";

	}

	/**
	 * @Description 下载导入模版 9908 绩效函数表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmfun/downFunTemplate", method = RequestMethod.GET)
	public String downFunTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "base\\目录", "模版.xls");

		return null;
	}

	/**
	 * @Description 导入数据 9908 绩效函数表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/cost/hpmfun/readHpmFunFiles", method = RequestMethod.POST)
	public String readHpmFunFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AphiFun> list_err = new ArrayList<AphiFun>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AphiFun hpmFun = new AphiFun();

				String temp[] = list.get(i);// 行

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					hpmFun.setGroup_id(Long.valueOf(temp[0]));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集团ID为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					hpmFun.setHos_id(Long.valueOf(temp[1]));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					hpmFun.setCopy_code(temp[2]);

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					hpmFun.setFun_code(temp[3]);

					mapVo.put("fun_code", temp[3]);

				} else {

					err_sb.append("函数编码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					hpmFun.setFun_name(temp[4]);

					mapVo.put("fun_name", temp[4]);

				} else {

					err_sb.append("函数名称为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					hpmFun.setType_code(temp[5]);

					mapVo.put("type_code", temp[5]);

				} else {

					err_sb.append("函数分类编码为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					hpmFun.setFun_method_chs(temp[6]);

					mapVo.put("fun_method_chs", temp[6]);

				} else {

					err_sb.append("取值函数(中文)为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					hpmFun.setFun_method_eng(temp[7]);

					mapVo.put("fun_method_eng", temp[7]);

				} else {

					err_sb.append("取值函数(英文)为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					hpmFun.setFun_note(temp[8]);

					mapVo.put("fun_note", temp[8]);

				} else {

					err_sb.append("函数说明为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					hpmFun.setSpell_code(temp[9]);

					mapVo.put("spell_code", temp[9]);

				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					hpmFun.setWbx_code(temp[10]);

					mapVo.put("wbx_code", temp[10]);

				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					hpmFun.setIs_pre(Integer.valueOf(temp[11]));

					mapVo.put("is_pre", temp[11]);

				} else {

					err_sb.append("0：否 1:是为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					hpmFun.setIs_rec(Integer.valueOf(temp[12]));

					mapVo.put("is_rec", temp[12]);

				} else {

					err_sb.append("0：否 1:是为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					hpmFun.setIs_stop(Integer.valueOf(temp[13]));

					mapVo.put("is_stop", temp[13]);

				} else {

					err_sb.append("0：否 1:是为空  ");

				}

				AphiFun data_exc_extis = aphiFunService.queryPrmFunByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					hpmFun.setError_type(err_sb.toString());

					list_err.add(hpmFun);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = aphiFunService.addPrmFun(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AphiFun data_exc = new AphiFun();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

}
