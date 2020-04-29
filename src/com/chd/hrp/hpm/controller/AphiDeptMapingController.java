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
import java.util.Iterator;
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
import com.chd.hrp.hpm.entity.AphiDeptMaping;
import com.chd.hrp.hpm.service.AphiDeptMapingService;
import com.chd.hrp.hpm.service.AphiDeptService;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;

/**
 * 
 * @Description: 8806 奖金科室映射表
 * @Table: Prm_DEPT_MAPING
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiDeptMapingController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptMapingController.class);

	// 引入Service服务
	@Resource(name = "aphiDeptMapingService")
	private final AphiDeptMapingService aphiDeptMapingService = null;

	@Resource(name = "aphiDeptService")
	private final AphiDeptService aphiDeptService = null;

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/hpmDeptMapingMainPage", method = RequestMethod.GET)
	public String hpmDeptMapingMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptmaping/hpmDeptMapingMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/hpmDeptMapingAddPage", method = RequestMethod.GET)
	public String hpmDeptMapingAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptmaping/hpmDeptMapingAdd";

	}

	/**
	 * @Description 添加数据 8806 奖金科室映射表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/addHpmDeptMaping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmDeptMaping(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		List<Map<String, Object>> dataAddedBatch = new ArrayList<Map<String, Object>>();// 存放明细

		String dataSection = String.valueOf(mapVo.get("maping_data"));

		JSONArray dataSectionJson = JSONArray.parseArray(dataSection);

		Iterator dataSectionJsonIt = dataSectionJson.iterator();

		while (dataSectionJsonIt.hasNext()) {

			JSONObject jsonObj = JSONObject.parseObject(dataSectionJsonIt.next().toString());

			try {

				/* ADD */

				Map<String, Object> mapAdd = new HashMap<String, Object>();

				mapAdd.put("group_id", SessionManager.getGroupId());

				mapAdd.put("hos_id", SessionManager.getHosId());

				mapAdd.put("copy_code", SessionManager.getCopyCode());

				mapAdd.put("dept_id", String.valueOf(jsonObj.get("dept_id")));

				mapAdd.put("sys_dept_id", String.valueOf(jsonObj.get("sys_dept_id")));

				mapAdd.put("ref_code", String.valueOf(jsonObj.get("ref_code")));

				mapAdd.put("spilt_perc", String.valueOf(jsonObj.get("spilt_perc") == null ? "" : jsonObj.get("spilt_perc")));

				dataAddedBatch.add(mapAdd);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();

			}

		}
		String addBatchPrmEmpKpiSectionJson = "";

		if (dataAddedBatch.size() > 0) {
			aphiDeptMapingService.deleteBatchPrmDeptMaping(dataAddedBatch);
			addBatchPrmEmpKpiSectionJson = aphiDeptMapingService.addBatchPrmDeptMaping(dataAddedBatch);
		}

		String StringJson = "";
		if (addBatchPrmEmpKpiSectionJson != "") {

			StringJson = addBatchPrmEmpKpiSectionJson;

		} else {

			StringJson = "{\"msg\":\"数据没变化.\",\"state\":\"true\"}";
		}

		return JSONObject.parseObject(StringJson);

	}

	/**
	 * @Description 更新跳转页面 8806 奖金科室映射表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/hpmDeptMapingUpdatePage", method = RequestMethod.GET)
	public String hpmDeptMapingUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AphiDeptMaping PrmDeptMaping = new AphiDeptMaping();

		PrmDeptMaping = aphiDeptMapingService.queryPrmDeptMapingByCode(mapVo);

		mode.addAttribute("group_id", PrmDeptMaping.getGroup_id());
		mode.addAttribute("hos_id", PrmDeptMaping.getHos_id());
		mode.addAttribute("copy_code", PrmDeptMaping.getCopy_code());
		mode.addAttribute("dept_id", PrmDeptMaping.getDept_id());
		mode.addAttribute("sys_dept_id", PrmDeptMaping.getSys_dept_id());
		mode.addAttribute("ref_code", PrmDeptMaping.getRef_code());
		mode.addAttribute("spilt_perc", PrmDeptMaping.getSpilt_perc());

		return "hrp/hpm/hpmdeptmaping/hpmDeptMapingUpdate";
	}

	/**
	 * @Description 更新数据 8806 奖金科室映射表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/updateHpmDeptMaping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmDeptMaping(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String PrmDeptMapingJson = aphiDeptMapingService.updatePrmDeptMaping(mapVo);

		return JSONObject.parseObject(PrmDeptMapingJson);
	}

	/**
	 * @Description 删除数据 8806 奖金科室映射表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/deleteHpmDeptMapping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDeptMaping(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("dept_id", ids[3]);
			mapVo.put("sys_dept_id", ids[4]);

			listVo.add(mapVo);

		}

		String PrmDeptMapingJson = aphiDeptMapingService.deleteBatchPrmDeptMaping(listVo);

		return JSONObject.parseObject(PrmDeptMapingJson);

	}

	/**
	 * @Description 查询数据 8806 奖金科室映射表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/queryHpmDeptMaping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptMaping(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}
		String PrmDeptMaping = aphiDeptMapingService.queryPrmDeptMaping(getPage(mapVo));

		return JSONObject.parseObject(PrmDeptMaping);

	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/queryHpmDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}
		String PrmDeptMaping = aphiDeptService.queryPrmDept(getPage(mapVo));

		return JSONObject.parseObject(PrmDeptMaping);

	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/queryDeptDictByHpmDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptDictByHpmDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}
		String PrmDeptMaping = deptDictService.queryDeptDictByPrmDept(getPage(mapVo));

		return JSONObject.parseObject(PrmDeptMaping);

	}

	/**
	 * @Description 导入跳转页面 8806 奖金科室映射表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptmaping/hpmDeptMapingImportPage", method = RequestMethod.GET)
	public String hpmDeptMapingImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptmaping/hpmDeptMapingImport";

	}

	/**
	 * @Description 下载导入模版 8806 奖金科室映射表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmdeptmaping/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "base\\目录", "模版.xls");

		return null;
	}

	/**
	 * @Description 导入数据 8806 奖金科室映射表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/cost/hpmdeptmaping/readHpmDeptMapingFiles", method = RequestMethod.POST)
	public String readHpmDeptMapingFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<AphiDeptMaping> list_err = new ArrayList<AphiDeptMaping>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AphiDeptMaping PrmDeptMaping = new AphiDeptMaping();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					PrmDeptMaping.setGroup_id(Long.valueOf(temp[0]));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集团ID为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					PrmDeptMaping.setHos_id(Long.valueOf(temp[1]));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					PrmDeptMaping.setCopy_code(temp[2]);

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					PrmDeptMaping.setDept_id(Double.valueOf(temp[4]));

					mapVo.put("dept_id", temp[4]);

				} else {

					err_sb.append("部门ID为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					PrmDeptMaping.setSys_dept_id(temp[6]);

					mapVo.put("sys_dept_id", temp[6]);

				} else {

					err_sb.append("平台科室ID为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					PrmDeptMaping.setRef_code(temp[7]);

					mapVo.put("ref_code", temp[7]);

				} else {

					err_sb.append("01:平行 02:拆分 C03:合并为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					PrmDeptMaping.setSpilt_perc(Double.valueOf(temp[8]));

					mapVo.put("spilt_perc", temp[8]);

				} else {

					err_sb.append("拆分比例为空  ");

				}

				AphiDeptMaping data_exc_extis = aphiDeptMapingService.queryPrmDeptMapingByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					PrmDeptMaping.setError_type(err_sb.toString());

					list_err.add(PrmDeptMaping);

				} else {

					String dataJson = aphiDeptMapingService.addPrmDeptMaping(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AphiDeptMaping data_exc = new AphiDeptMaping();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 8806 奖金科室映射表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/cost/hpmdeptmaping/addBatchHpmDeptMaping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchHpmDeptMaping(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<AphiDeptMaping> list_err = new ArrayList<AphiDeptMaping>();

		JSONArray json = JSONArray.parseArray(paramVo);

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

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AphiDeptMaping PrmDeptMaping = new AphiDeptMaping();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("group_id"))) {

					PrmDeptMaping.setGroup_id(Long.valueOf((String) jsonObj.get("group_id")));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集团ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {

					PrmDeptMaping.setHos_id(Long.valueOf((String) jsonObj.get("hos_id")));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {

					PrmDeptMaping.setCopy_code((String) jsonObj.get("copy_code"));

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {

					PrmDeptMaping.setDept_id(Double.valueOf((String) jsonObj.get("dept_id")));

					mapVo.put("dept_id", jsonObj.get("dept_id"));

				} else {

					err_sb.append("部门ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("sys_dept_id"))) {

					PrmDeptMaping.setSys_dept_id((String) jsonObj.get("sys_dept_id"));

					mapVo.put("sys_dept_id", jsonObj.get("sys_dept_id"));

				} else {

					err_sb.append("平台科室ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ref_code"))) {

					PrmDeptMaping.setRef_code((String) jsonObj.get("ref_code"));

					mapVo.put("ref_code", jsonObj.get("ref_code"));

				} else {

					err_sb.append("01:平行 02:拆分 C03:合并为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("spilt_perc"))) {

					PrmDeptMaping.setSpilt_perc(Double.valueOf((String) jsonObj.get("spilt_perc")));

					mapVo.put("spilt_perc", jsonObj.get("spilt_perc"));

				} else {

					err_sb.append("拆分比例为空  ");

				}

				AphiDeptMaping data_exc_extis = aphiDeptMapingService.queryPrmDeptMapingByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					PrmDeptMaping.setError_type(err_sb.toString());

					list_err.add(PrmDeptMaping);

				} else {

					String dataJson = aphiDeptMapingService.addPrmDeptMaping(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AphiDeptMaping data_exc = new AphiDeptMaping();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

}
