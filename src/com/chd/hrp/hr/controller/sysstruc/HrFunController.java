
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
import com.chd.hrp.hr.entity.sysstruc.HrFun;
import com.chd.hrp.hr.entity.sysstruc.HrFunType;
import com.chd.hrp.hr.service.sysstruc.HrFunParaService;
import com.chd.hrp.hr.service.sysstruc.HrFunService;

/**
 * 
 * @Description: 9908 绩效函数表
 * @Table: PRM_FUN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrcaltrans")
public class HrFunController extends BaseController {

	private static Logger logger = Logger.getLogger(HrFunController.class);

	// 引入Service服务
	@Resource(name = "hrFunService")
	private final HrFunService hrFunService = null;

	@Resource(name = "hrFunParaService")
	private final HrFunParaService hrFunParaService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunSetMainPage", method = RequestMethod.GET)
	public String hrFunMainPage(Model mode) throws Exception {

		return "hrp/hr/sysstruc/hrfun/hrFunMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunAddPage", method = RequestMethod.GET)
	public String hrFunAddPage(Model mode) throws Exception {

		return "hrp/hr/sysstruc/hrfun/hrFunAdd";

	}

	@RequestMapping(value = "/hrFunPage", method = RequestMethod.GET)
	public String hrFunPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("target_code", mapVo.get("target_code"));
		mode.addAttribute("fun_code", mapVo.get("fun_code"));
		return "hrp/hr/hrfun/hrFun";
	}

	@RequestMapping(value = "/hrDeptFunPage", method = RequestMethod.GET)
	public String hrDeptFunPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		mode.addAttribute("goal_code", mapVo.get("goal_code"));
		mode.addAttribute("kpi_code", mapVo.get("kpi_code"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("fun_code", mapVo.get("fun_code"));
		return "hrp/hr/sysstruc/hrfun/hrDeptFun";
	}


	/**
	 * @Description 添加数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunSetadd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addhrFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String hrFunJson = null;
		
		try {
			
			hrFunJson = hrFunService.addPrmFun(mapVo);
			
		} catch (Exception e) {
			
			hrFunJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}


		return JSONObject.parseObject(hrFunJson);

	}

	/**
	 * @Description 更新跳转页面 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunUpdatePage", method = RequestMethod.GET)
	public String hrFunUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		HrFun hrFun = new HrFun();

		hrFun = hrFunService.queryPrmFunComtype(mapVo);

		mode.addAttribute("fun_code", hrFun.getFun_code());

		mode.addAttribute("fun_name", hrFun.getFun_name());

		mode.addAttribute("type_code", hrFun.getType_code());

		mode.addAttribute("fun_method_chs", hrFun.getFun_method_chs());

		mode.addAttribute("fun_method_eng", hrFun.getFun_method_eng());

		mode.addAttribute("fun_note", hrFun.getFun_note());

		mode.addAttribute("spell_code", hrFun.getSpell_code());

		mode.addAttribute("wbx_code", hrFun.getWbx_code());

		mode.addAttribute("is_pre", hrFun.getIs_pre());

		mode.addAttribute("is_rec", hrFun.getIs_rec());

		mode.addAttribute("is_stop", hrFun.getIs_stop());

		mode.addAttribute("type_name", hrFun.getType_name());

		mode.addAttribute("prc_name", hrFun.getPrc_name());

		mode.addAttribute("pkg_name", hrFun.getPkg_name());
		
		if(hrFun.getFun_sql() != null){
			String fun_sql = new String(hrFun.getFun_sql(),"UTF-8");
			mode.addAttribute("fun_sql", fun_sql.replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		}else{
			mode.addAttribute("fun_sql", "");
		}
		
		
		
		

		return "hrp/hr/sysstruc/hrfun/hrFunUpdate";
	}

	/**
	 * @Description 更新数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunSetupdate", method = RequestMethod.POST)
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
		String hrFunJson = hrFunService.updatePrmFun(mapVo);

		return JSONObject.parseObject(hrFunJson);
	}

	/**
	 * @Description 删除数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunSetdelete", method = RequestMethod.POST)
	@ResponseBody
	public String hrFunSetdelete(@RequestParam String param, Model mode) throws Exception {

			try {
				List<HrFun> listVo = JSONArray.parseArray(param, HrFun.class);
				return hrFunService.deleteBatchPrmFun(listVo);
			} catch (Exception e) {
				return "{\"error\":\"" + e.getMessage() + "\"}";
			}
		/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

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
		String hrFunJson = hrFunService.deleteBatchPrmFun(listVo);
		return JSONObject.parseObject(hrFunJson);*/
	}
	
	@RequestMapping(value = "/inithrFunProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inithrFunProc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hrFun = hrFunService.initPrmFunProc(mapVo);

		return JSONObject.parseObject(hrFun);

	}
	

	/**
	 * @Description 查询数据 9908 绩效函数表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunSetquery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryhrFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String hrFun = hrFunService.queryPrmFun(getPage(mapVo));

		return JSONObject.parseObject(hrFun);

	}

	@RequestMapping(value = "/queryhrFunParaByFunCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryhrFunParaByFunCode(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String hrFun = hrFunParaService.queryPrmFunParaByFunCode(getPage(mapVo));

		return JSONObject.parseObject(hrFun);
	}

	@RequestMapping(value = "/deletehrFunParaByFunCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletehrFunParaByFunCode(@RequestParam(value = "ParamVo") String ParamVo, Model mode)
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
		String hrFunJson = hrFunParaService.deleteBatchPrmFunPara(listVo);

		return JSONObject.parseObject(hrFunJson);

	}

	/**
	 * @Description 导入跳转页面 9908 绩效函数表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFunImportPage", method = RequestMethod.GET)
	public String hrFunImportPage(Model mode) throws Exception {

		return "hrp/hr/sysstruc/hrfun/hrFunImport";

	}

	/**
	 * @Description 下载导入模版 9908 绩效函数表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/downFunTemplate", method = RequestMethod.GET)
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
	@RequestMapping(value = "/readhrFunFiles", method = RequestMethod.POST)
	public String readhrFunFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<HrFun> list_err = new ArrayList<HrFun>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				HrFun hrFun = new HrFun();

				String temp[] = list.get(i);// 行

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					hrFun.setGroup_id(Long.valueOf(temp[0]));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集团ID为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					hrFun.setHos_id(Long.valueOf(temp[1]));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					hrFun.setCopy_code(temp[2]);

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					hrFun.setFun_code(temp[3]);

					mapVo.put("fun_code", temp[3]);

				} else {

					err_sb.append("函数编码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					hrFun.setFun_name(temp[4]);

					mapVo.put("fun_name", temp[4]);

				} else {

					err_sb.append("函数名称为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					hrFun.setType_code(temp[5]);

					mapVo.put("type_code", temp[5]);

				} else {

					err_sb.append("函数分类编码为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					hrFun.setFun_method_chs(temp[6]);

					mapVo.put("fun_method_chs", temp[6]);

				} else {

					err_sb.append("取值函数(中文)为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					hrFun.setFun_method_eng(temp[7]);

					mapVo.put("fun_method_eng", temp[7]);

				} else {

					err_sb.append("取值函数(英文)为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					hrFun.setFun_note(temp[8]);

					mapVo.put("fun_note", temp[8]);

				} else {

					err_sb.append("函数说明为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					hrFun.setSpell_code(temp[9]);

					mapVo.put("spell_code", temp[9]);

				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					hrFun.setWbx_code(temp[10]);

					mapVo.put("wbx_code", temp[10]);

				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					hrFun.setIs_pre(Integer.valueOf(temp[11]));

					mapVo.put("is_pre", temp[11]);

				} else {

					err_sb.append("0：否 1:是为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					hrFun.setIs_rec(Integer.valueOf(temp[12]));

					mapVo.put("is_rec", temp[12]);

				} else {

					err_sb.append("0：否 1:是为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					hrFun.setIs_stop(Integer.valueOf(temp[13]));

					mapVo.put("is_stop", temp[13]);

				} else {

					err_sb.append("0：否 1:是为空  ");

				}

				HrFun data_exc_extis = hrFunService.queryPrmFunByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					hrFun.setError_type(err_sb.toString());

					list_err.add(hrFun);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = hrFunService.addPrmFun(mapVo);

				}

			}

		} catch (DataAccessException e) {

			HrFun data_exc = new HrFun();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

}
