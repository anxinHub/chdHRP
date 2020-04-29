
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
import com.chd.hrp.prm.entity.PrmFun;
import com.chd.hrp.prm.service.PrmFunParaService;
import com.chd.hrp.prm.service.PrmFunService;

/**
 * 
 * @Description: 9908 绩效函数表
 * @Table: PRM_FUN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmFunController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmFunController.class);

	// 引入Service服务
	@Resource(name = "prmFunService")
	private final PrmFunService prmFunService = null;

	@Resource(name = "prmFunParaService")
	private final PrmFunParaService prmFunParaService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfun/prmFunMainPage", method = RequestMethod.GET)
	public String prmFunMainPage(Model mode) throws Exception {

		return "hrp/prm/prmfun/prmFunMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfun/prmFunAddPage", method = RequestMethod.GET)
	public String prmFunAddPage(Model mode) throws Exception {

		return "hrp/prm/prmfun/prmFunAdd";

	}

	@RequestMapping(value = "/hrp/prm/prmfun/prmFunPage", method = RequestMethod.GET)
	public String prmFunPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("target_code", mapVo.get("target_code"));
		mode.addAttribute("fun_code", mapVo.get("fun_code"));
		return "hrp/prm/prmfun/prmFun";
	}

	@RequestMapping(value = "/hrp/prm/prmfun/prmDeptFunPage", method = RequestMethod.GET)
	public String prmDeptFunPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		mode.addAttribute("goal_code", mapVo.get("goal_code"));
		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("fun_code", mapVo.get("fun_code"));
		return "hrp/prm/prmfun/prmDeptFun";
	}

	@RequestMapping(value = "/hrp/prm/prmfun/prmEmpFunPage", method = RequestMethod.GET)
	public String prmEmpFunPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		mode.addAttribute("goal_code", mapVo.get("goal_code"));
		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));
		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		mode.addAttribute("fun_code", mapVo.get("fun_code"));
		return "hrp/prm/prmfun/prmEmpFun";
	}

	@RequestMapping(value = "/hrp/prm/prmfun/prmHosFunPage", method = RequestMethod.GET)
	public String prmHosFunPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		mode.addAttribute("goal_code", mapVo.get("goal_code"));
		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));
		mode.addAttribute("check_hps_id", mapVo.get("check_hps_id"));
		mode.addAttribute("fun_code", mapVo.get("fun_code"));
		return "hrp/prm/prmfun/prmHosFun";
	}

	/**
	 * @Description 添加数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfun/addPrmFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String prmFunJson = prmFunService.addPrmFun(mapVo);

		return JSONObject.parseObject(prmFunJson);

	}

	/**
	 * @Description 更新跳转页面 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfun/prmFunUpdatePage", method = RequestMethod.GET)
	public String prmFunUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmFun prmFun = new PrmFun();

		prmFun = prmFunService.queryPrmFunComtype(mapVo);

		mode.addAttribute("fun_code", prmFun.getFun_code());

		mode.addAttribute("fun_name", prmFun.getFun_name());

		mode.addAttribute("type_code", prmFun.getType_code());

		mode.addAttribute("fun_method_chs", prmFun.getFun_method_chs());

		mode.addAttribute("fun_method_eng", prmFun.getFun_method_eng());

		mode.addAttribute("fun_note", prmFun.getFun_note());

		mode.addAttribute("spell_code", prmFun.getSpell_code());

		mode.addAttribute("wbx_code", prmFun.getWbx_code());

		mode.addAttribute("is_pre", prmFun.getIs_pre());

		mode.addAttribute("is_rec", prmFun.getIs_rec());

		mode.addAttribute("is_stop", prmFun.getIs_stop());

		mode.addAttribute("type_name", prmFun.getType_name());

		mode.addAttribute("prc_name", prmFun.getPrc_name());

		mode.addAttribute("pkg_name", prmFun.getPkg_name());
		
		if(prmFun.getFun_sql() != null){
			String fun_sql = new String(prmFun.getFun_sql(),"UTF-8");
			mode.addAttribute("fun_sql", fun_sql.replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		}else{
			mode.addAttribute("fun_sql", "");
		}
		
		
		
		

		return "hrp/prm/prmfun/prmFunUpdate";
	}

	/**
	 * @Description 更新数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfun/updatePrmFun", method = RequestMethod.POST)
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
		String prmFunJson = prmFunService.updatePrmFun(mapVo);

		return JSONObject.parseObject(prmFunJson);
	}

	/**
	 * @Description 删除数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfun/deletePrmFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmFun(@RequestParam(value = "ParamVo") String ParamVo, Model mode)
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
		String prmFunJson = prmFunService.deleteBatchPrmFun(listVo);
		return JSONObject.parseObject(prmFunJson);
	}
	
	@RequestMapping(value = "/hrp/prm/prmfun/initPrmFunProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initPrmFunProc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String prmFun = prmFunService.initPrmFunProc(mapVo);

		return JSONObject.parseObject(prmFun);

	}
	

	/**
	 * @Description 查询数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfun/queryPrmFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String prmFun = prmFunService.queryPrmFun(getPage(mapVo));

		return JSONObject.parseObject(prmFun);

	}

	@RequestMapping(value = "/hrp/prm/prmfun/queryPrmFunParaByFunCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmFunParaByFunCode(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmFun = prmFunParaService.queryPrmFunParaByFunCode(getPage(mapVo));

		return JSONObject.parseObject(prmFun);
	}

	@RequestMapping(value = "/hrp/prm/prmfun/deletePrmFunParaByFunCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmFunParaByFunCode(@RequestParam(value = "ParamVo") String ParamVo, Model mode)
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
		String prmFunJson = prmFunParaService.deleteBatchPrmFunPara(listVo);

		return JSONObject.parseObject(prmFunJson);

	}

	/**
	 * @Description 导入跳转页面 9908 绩效函数表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmfun/prmFunImportPage", method = RequestMethod.GET)
	public String prmFunImportPage(Model mode) throws Exception {

		return "hrp/prm/prmfun/prmFunImport";

	}

	/**
	 * @Description 下载导入模版 9908 绩效函数表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmfun/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
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
	@RequestMapping(value = "/hrp/cost/prmfun/readPrmFunFiles", method = RequestMethod.POST)
	public String readPrmFunFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<PrmFun> list_err = new ArrayList<PrmFun>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				PrmFun prmFun = new PrmFun();

				String temp[] = list.get(i);// 行

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					prmFun.setGroup_id(Long.valueOf(temp[0]));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集团ID为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					prmFun.setHos_id(Long.valueOf(temp[1]));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					prmFun.setCopy_code(temp[2]);

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					prmFun.setFun_code(temp[3]);

					mapVo.put("fun_code", temp[3]);

				} else {

					err_sb.append("函数编码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					prmFun.setFun_name(temp[4]);

					mapVo.put("fun_name", temp[4]);

				} else {

					err_sb.append("函数名称为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					prmFun.setType_code(temp[5]);

					mapVo.put("type_code", temp[5]);

				} else {

					err_sb.append("函数分类编码为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					prmFun.setFun_method_chs(temp[6]);

					mapVo.put("fun_method_chs", temp[6]);

				} else {

					err_sb.append("取值函数(中文)为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					prmFun.setFun_method_eng(temp[7]);

					mapVo.put("fun_method_eng", temp[7]);

				} else {

					err_sb.append("取值函数(英文)为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					prmFun.setFun_note(temp[8]);

					mapVo.put("fun_note", temp[8]);

				} else {

					err_sb.append("函数说明为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					prmFun.setSpell_code(temp[9]);

					mapVo.put("spell_code", temp[9]);

				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					prmFun.setWbx_code(temp[10]);

					mapVo.put("wbx_code", temp[10]);

				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					prmFun.setIs_pre(Integer.valueOf(temp[11]));

					mapVo.put("is_pre", temp[11]);

				} else {

					err_sb.append("0：否 1:是为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					prmFun.setIs_rec(Integer.valueOf(temp[12]));

					mapVo.put("is_rec", temp[12]);

				} else {

					err_sb.append("0：否 1:是为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					prmFun.setIs_stop(Integer.valueOf(temp[13]));

					mapVo.put("is_stop", temp[13]);

				} else {

					err_sb.append("0：否 1:是为空  ");

				}

				PrmFun data_exc_extis = prmFunService.queryPrmFunByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					prmFun.setError_type(err_sb.toString());

					list_err.add(prmFun);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = prmFunService.addPrmFun(mapVo);

				}

			}

		} catch (DataAccessException e) {

			PrmFun data_exc = new PrmFun();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

}
