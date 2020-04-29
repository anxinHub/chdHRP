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
import com.chd.hrp.prm.dao.PrmDeptHipMapper;
import com.chd.hrp.prm.dao.PrmDeptMapper;
import com.chd.hrp.prm.entity.PrmDept;
import com.chd.hrp.prm.entity.PrmDeptHip;
import com.chd.hrp.prm.entity.PrmDeptMapingHip;
import com.chd.hrp.prm.service.PrmDeptMapingHipService;
import com.chd.hrp.prm.service.PrmDeptService;
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
public class PrmDeptMapingHipController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptMapingHipController.class);

	// 引入Service服务
	@Resource(name = "prmDeptMapingHipService")
	private final PrmDeptMapingHipService prmDeptMapingHipService = null;

	@Resource(name = "prmDeptService")
	private final PrmDeptService prmDeptService = null; 

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;
	
	@Resource(name = "prmDeptMapper")
	private final PrmDeptMapper prmDeptMapper = null;
	
	@Resource(name = "prmDeptHipMapper")
	private final PrmDeptHipMapper prmDeptHipMapper = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/prmDeptMapingHipMainPage", method = RequestMethod.GET)
	public String prmDeptMapingHipMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptmapinghip/prmDeptMapingHipMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/prmDeptMapingHipAddPage", method = RequestMethod.GET)
	public String prmDeptMapingHipAddPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptmapinghip/prmDeptMapingHipAdd";

	}

	/**
	 * @Description 添加数据 8806 奖金科室映射表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/addPrmDeptMapingHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmDeptMapingHip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			prmDeptMapingHipService.deleteBatchPrmDeptMapingHip(dataAddedBatch);
			addBatchPrmEmpKpiSectionJson = prmDeptMapingHipService.addBatchPrmDeptMapingHip(dataAddedBatch);
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
	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/prmDeptMapingHipUpdatePage", method = RequestMethod.GET)
	public String prmDeptMapingHipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmDeptMapingHip prmDeptMapingHip = new PrmDeptMapingHip();

		prmDeptMapingHip = prmDeptMapingHipService.queryPrmDeptMapingHipByCode(mapVo);

		mode.addAttribute("group_id", prmDeptMapingHip.getGroup_id());
		mode.addAttribute("hos_id", prmDeptMapingHip.getHos_id());
		mode.addAttribute("copy_code", prmDeptMapingHip.getCopy_code());
		mode.addAttribute("dept_id", prmDeptMapingHip.getDept_id());
		mode.addAttribute("sys_dept_id", prmDeptMapingHip.getSys_dept_id());
		mode.addAttribute("ref_code", prmDeptMapingHip.getRef_code());
		mode.addAttribute("spilt_perc", prmDeptMapingHip.getSpilt_perc());

		return "hrp/prm/prmdeptmapinghip/prmDeptMapingHipUpdate";
	}

	/**
	 * @Description 更新数据 8806 奖金科室映射表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/updatePrmDeptMapingHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmDeptMapingHip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String PrmDeptMapingHipJson = prmDeptMapingHipService.updatePrmDeptMapingHip(mapVo);

		return JSONObject.parseObject(PrmDeptMapingHipJson);
	}

	/**
	 * @Description 删除数据 8806 奖金科室映射表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/deletePrmDeptMappingHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptMappingHip(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String PrmDeptMapingHipJson = prmDeptMapingHipService.deleteBatchPrmDeptMapingHip(listVo);

		return JSONObject.parseObject(PrmDeptMapingHipJson);

	}

	/**
	 * @Description 查询数据 8806 奖金科室映射表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/queryPrmDeptMapingHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptMapingHip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String PrmDeptMapingHip = prmDeptMapingHipService.queryPrmDeptMapingHip(getPage(mapVo));

		return JSONObject.parseObject(PrmDeptMapingHip);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/queryPrmDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String PrmDeptMapingHip = prmDeptService.queryPrmDept(getPage(mapVo));

		return JSONObject.parseObject(PrmDeptMapingHip);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/queryPrmDeptHipByMaping", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptHipByMaping(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String PrmDeptMapingHip = prmDeptMapingHipService.queryPrmDeptHipByMaping(getPage(mapVo));

		return JSONObject.parseObject(PrmDeptMapingHip);

	}

	/**
	 * @Description 导入跳转页面 8806 奖金科室映射表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/prmDeptMapingHipImportPage", method = RequestMethod.GET)
	public String prmDeptMapingHipImportPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptmapinghip/prmDeptMapingHipImport";

	}

	/**
	 * @Description 下载导入模版 8806 奖金科室映射表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmdeptmapinghip/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "prm\\基础设置", "科室与系统平台模板.xls");

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
	@RequestMapping(value = "/hrp/prm/prmdeptmapinghip/readPrmDeptMapingHipFiles", method = RequestMethod.POST)
	public String readPrmDeptMapingHipFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<PrmDeptMapingHip> list_err = new ArrayList<PrmDeptMapingHip>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				PrmDeptMapingHip PrmDeptMapingHip = new PrmDeptMapingHip();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				if (StringTool.isNotBlank(temp[0])) {

					PrmDeptMapingHip.setGroup_id(Long.valueOf(temp[0]));

					mapVo.put("dept_id", temp[0]);

				} else {

					err_sb.append("部门ID为空");

				}

				if (StringTool.isNotBlank(temp[1])) {

					PrmDeptMapingHip.setHos_id(Long.valueOf(temp[1]));

					mapVo.put("sys_dept_id", temp[1]);

				} else {

					err_sb.append("平台科室ID为空");

				}

				if (StringTool.isNotBlank(temp[2])) {

					PrmDeptMapingHip.setCopy_code(temp[2]);

					mapVo.put("ref_code", temp[2]);

				} else {

					err_sb.append("01:平行 02:拆分 C03:合并为空 ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					PrmDeptMapingHip.setDept_id(Double.valueOf(temp[3]));

					mapVo.put("spilt_perc", temp[3]);

				} else {

					err_sb.append("拆分比例为空");

				}

				/*if (StringTool.isNotBlank(temp[6])) {

					PrmDeptMapingHip.setSys_dept_id(temp[6]);

					mapVo.put("sys_dept_id", temp[6]);

				} else {

					err_sb.append("01:平行 02:拆分 C03:合并为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					PrmDeptMapingHip.setRef_code(temp[7]);

					mapVo.put("ref_code", temp[7]);

				} else {

					err_sb.append("01:平行 02:拆分 C03:合并为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					PrmDeptMapingHip.setSpilt_perc(Double.valueOf(temp[8]));

					mapVo.put("spilt_perc", temp[8]);

				} else {

					err_sb.append("拆分比例为空  ");

				}*/

				//判断科室是否存在
				List<PrmDept> prmDept = prmDeptMapper.queryPrmDept(mapVo);
				if(prmDept.size() == 0 || prmDept.size() > 2){
					err_sb.append("科室不存在或者查出数据过多！ ");
				}
				
				//判断系统平台科室是否存在
				List<PrmDeptHip> prmDeptHip = prmDeptHipMapper.queryPrmDeptHips(mapVo);
				if(prmDeptHip.size() == 0 || prmDeptHip.size() > 2){
					err_sb.append("平台科室不存在或者查出数据过多！ ");
				}
				
				PrmDeptMapingHip data_exc_extis = prmDeptMapingHipService.queryPrmDeptMapingHipByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					PrmDeptMapingHip.setError_type(err_sb.toString());

					list_err.add(PrmDeptMapingHip);

				} else {

					String dataJson = prmDeptMapingHipService.addPrmDeptMapingHip(mapVo);

				}

			}

		} catch (DataAccessException e) {

			PrmDeptMapingHip data_exc = new PrmDeptMapingHip();

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
	@RequestMapping(value = "/hrp/cost/prmdeptmaping/addBatchPrmDeptMapingHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchPrmDeptMapingHip(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<PrmDeptMapingHip> list_err = new ArrayList<PrmDeptMapingHip>();

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

				PrmDeptMapingHip PrmDeptMapingHip = new PrmDeptMapingHip();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("group_id"))) {

					PrmDeptMapingHip.setGroup_id(Long.valueOf((String) jsonObj.get("group_id")));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集团ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {

					PrmDeptMapingHip.setHos_id(Long.valueOf((String) jsonObj.get("hos_id")));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {

					PrmDeptMapingHip.setCopy_code((String) jsonObj.get("copy_code"));

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {

					PrmDeptMapingHip.setDept_id(Double.valueOf((String) jsonObj.get("dept_id")));

					mapVo.put("dept_id", jsonObj.get("dept_id"));

				} else {

					err_sb.append("部门ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("sys_dept_id"))) {

					PrmDeptMapingHip.setSys_dept_id((String) jsonObj.get("sys_dept_id"));

					mapVo.put("sys_dept_id", jsonObj.get("sys_dept_id"));

				} else {

					err_sb.append("平台科室ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ref_code"))) {

					PrmDeptMapingHip.setRef_code((String) jsonObj.get("ref_code"));

					mapVo.put("ref_code", jsonObj.get("ref_code"));

				} else {

					err_sb.append("01:平行 02:拆分 C03:合并为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("spilt_perc"))) {

					PrmDeptMapingHip.setSpilt_perc(Double.valueOf((String) jsonObj.get("spilt_perc")));

					mapVo.put("spilt_perc", jsonObj.get("spilt_perc"));

				} else {

					err_sb.append("拆分比例为空  ");

				}

				PrmDeptMapingHip data_exc_extis = prmDeptMapingHipService.queryPrmDeptMapingHipByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					PrmDeptMapingHip.setError_type(err_sb.toString());

					list_err.add(PrmDeptMapingHip);

				} else {

					String dataJson = prmDeptMapingHipService.addPrmDeptMapingHip(mapVo);

				}

			}

		} catch (DataAccessException e) {

			PrmDeptMapingHip data_exc = new PrmDeptMapingHip();

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
