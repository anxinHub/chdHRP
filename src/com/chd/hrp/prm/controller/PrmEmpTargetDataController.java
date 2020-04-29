
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.prm.entity.PrmDeptTargetData;
import com.chd.hrp.prm.entity.PrmEmpTargetData;
import com.chd.hrp.prm.service.PrmEmpTargetDataService;

/**
 * 
 * @Description: 0412 职工绩效指标数据表
 * @Table: PRM_EMP_TARGET_DATA
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmEmpTargetDataController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmEmpTargetDataController.class);

	// 引入Service服务
	@Resource(name = "prmEmpTargetDataService")
	private final PrmEmpTargetDataService prmEmpTargetDataService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/prmEmpTargetDataMainPage", method = RequestMethod.GET)
	public String prmEmpTargetDataMainPage(Model mode) throws Exception {

		return "hrp/prm/prmemptargetdata/prmEmpTargetDataMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/prmEmpTargetDataAddPage", method = RequestMethod.GET)
	public String prmEmpTargetDataAddPage(Model mode) throws Exception {

		return "hrp/prm/prmemptargetdata/prmEmpTargetDataAdd";

	}

	/**
	 * @Description 添加数据 0412 职工绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/addPrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmEmpTargetData(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmEmpTargetDataJson = prmEmpTargetDataService.addPrmEmpTargetData(mapVo);

		return JSONObject.parseObject(prmEmpTargetDataJson);

	}

	/**
	 * @Description 更新跳转页面 0412 职工绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/prmEmpTargetDataUpdatePage", method = RequestMethod.GET)
	public String prmEmpTargetDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmEmpTargetData prmEmpTargetData = new PrmEmpTargetData();

		prmEmpTargetData = prmEmpTargetDataService.queryPrmEmpTargetDataByCode(mapVo);

		mode.addAttribute("group_id", prmEmpTargetData.getGroup_id());
		mode.addAttribute("hos_id", prmEmpTargetData.getHos_id());
		mode.addAttribute("copy_code", prmEmpTargetData.getCopy_code());
		mode.addAttribute("acc_year", prmEmpTargetData.getAcc_year());
		mode.addAttribute("acc_month", prmEmpTargetData.getAcc_month());
		mode.addAttribute("target_code", prmEmpTargetData.getTarget_code());
		mode.addAttribute("emp_no", prmEmpTargetData.getEmp_no());
		mode.addAttribute("emp_id", prmEmpTargetData.getEmp_id());
		mode.addAttribute("target_value", prmEmpTargetData.getTarget_value());
		mode.addAttribute("is_audit", prmEmpTargetData.getIs_audit());
		mode.addAttribute("user_code", prmEmpTargetData.getUser_code());
		mode.addAttribute("audit_date", prmEmpTargetData.getAudit_date());

		return "hrp/prm/prmemptargetdata/prmEmpTargetDataUpdate";
	}

	/**
	 * @Description 更新数据 0412 职工绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/updatePrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmEmpTargetData(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmEmpTargetDataJson = prmEmpTargetDataService.updatePrmEmpTargetData(mapVo);

		return JSONObject.parseObject(prmEmpTargetDataJson);
	}

	/**
	 * @Description 删除数据 0412 职工绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/deletePrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmEmpTargetData(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("target_code", ids[5]);
			mapVo.put("emp_no", ids[6]);
			mapVo.put("emp_id", ids[7]);

			PrmEmpTargetData prmEmpTargetData = prmEmpTargetDataService.queryPrmEmpTargetDataByCode(mapVo);

			if (prmEmpTargetData.getIs_audit() > 0) {
				return JSONObject.parseObject("{\"error\":\"删除失败 已审核的数据不能删除\"}");
			}

			listVo.add(mapVo);

		}

		String prmEmpTargetDataJson = prmEmpTargetDataService.deleteBatchPrmEmpTargetData(listVo);

		return JSONObject.parseObject(prmEmpTargetDataJson);

	}

	/**
	 * @Description 查询数据 0412 职工绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/queryPrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpTargetData(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmEmpTargetData = prmEmpTargetDataService.queryPrmEmpTargetData(getPage(mapVo));

		return JSONObject.parseObject(prmEmpTargetData);

	}

	/**
	 * @Description 查询数据 0412 职工绩效指标数据表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/queryPrmEmpTargetPrmTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmEmpTargetPrmTargetData(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmEmpTargetData = prmEmpTargetDataService.queryPrmEmpTargetPrmTargetData(getPage(mapVo));

		return JSONObject.parseObject(prmEmpTargetData);

	}

	/**
	 * @Description 导入跳转页面 0412 职工绩效指标数据表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/prmEmpTargetDataImportPage", method = RequestMethod.GET)
	public String prmEmpTargetDataImportPage(Model mode) throws Exception {

		return "hrp/prm/prmemptargetdata/prmEmpTargetDataImport";

	}

	/**
	 * @Description 下载导入模版 0412 职工绩效指标数据表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmemptargetdata/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "prm\\业务功能", "职工指标数据采集.xlsx");

		return null;
	}

	

	@RequestMapping(value = "/hrp/prm/prmemptargetdata/auditPrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditPrmEmpTargetData(@RequestParam Map<String,Object> mapVo, Model mode)
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
		
		mapVo.put("user_code", SessionManager.getUserCode());
		mapVo.put("is_audit", 1);
		mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));


		String prmEmpTargetDataJson = "";
		
		try {
			
			prmEmpTargetDataJson = prmEmpTargetDataService.auditPrmEmpTargetData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			prmEmpTargetDataJson = e.getMessage();
		}
		

		return JSONObject.parseObject(prmEmpTargetDataJson);
	}

	@RequestMapping(value = "/hrp/prm/prmemptargetdata/reAuditPrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmEmpTargetData(@RequestParam Map<String,Object> mapVo, Model mode)
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
		
		mapVo.put("user_code", "");
		mapVo.put("is_audit", 0);
		mapVo.put("audit_date", "");


		String prmEmpTargetDataJson = "";
		
		try {
			
			prmEmpTargetDataJson = prmEmpTargetDataService.reAuditPrmEmpTargetData(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			prmEmpTargetDataJson = e.getMessage();
		}

		return JSONObject.parseObject(prmEmpTargetDataJson);
	}

	@RequestMapping(value = "/hrp/prm/prmemptargetdata/createPrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmEmpTargetData(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		String prmHosTargetData = prmEmpTargetDataService.createPrmEmpTargetData(mapVo, paramVo);

		return JSONObject.parseObject(prmHosTargetData);

	}

	@RequestMapping(value = "/hrp/prm/prmemptargetdata/saveBatchPrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchPrmEmpTargetData(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);
			mapVo.put("target_code", ids[5]);
			mapVo.put("emp_no", ids[6]);
			mapVo.put("emp_id", ids[7]);
			mapVo.put("target_value", ids[8]);

			listVo.add(mapVo);

		}

		String prmEmpTargetDataJson = prmEmpTargetDataService.updateBatchPrmEmpTargetData(listVo);

		return JSONObject.parseObject(prmEmpTargetDataJson);
	}
	
	/**
	 * @Description 导入
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmemptargetdata/importPrmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importPrmEmpTargetData(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		String prmEmpTargetDataJson = "";
		try {
			
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			prmEmpTargetDataJson = prmEmpTargetDataService.importPrmEmpTargetData(mapVo);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			prmEmpTargetDataJson = e.getMessage();
		}
		
		return JSONObject.parseObject(prmEmpTargetDataJson);
	}
}
