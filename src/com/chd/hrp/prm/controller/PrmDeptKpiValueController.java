
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
import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmDeptKpiValue;
import com.chd.hrp.prm.entity.PrmTargetMethod;
import com.chd.hrp.prm.service.PrmDeptKpiValueService;
import com.chd.hrp.prm.service.PrmTargetMethodService;

/**
 * 
 * @Description: 0308 科室KPI指标数据准备表
 * @Table: PRM_DEPT_KPI_VALUE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptKpiValueController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptKpiValueController.class);

	// 引入Service服务
	@Resource(name = "prmDeptKpiValueService")
	private final PrmDeptKpiValueService prmDeptKpiValueService = null;
	
	@Resource(name = "prmTargetMethodService")
	private final PrmTargetMethodService prmTargetMethodService = null;
	

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/prmDeptKpiValueMainPage", method = RequestMethod.GET)
	public String prmDeptKpiValueMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkpivalue/prmDeptKpiValueMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/prmDeptKpiValueAddPage", method = RequestMethod.GET)
	public String prmDeptKpiValueAddPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkpivalue/prmDeptKpiValueAdd";

	}

	/**
	 * @Description 添加数据 0308 科室KPI指标数据准备表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/addPrmDeptKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmDeptKpiValue(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmDeptKpiValueJson = prmDeptKpiValueService.addPrmDeptKpiValue(mapVo);

		return JSONObject.parseObject(prmDeptKpiValueJson);

	}

	/**
	 * @Description 更新跳转页面 0308 科室KPI指标数据准备表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/prmDeptKpiValueUpdatePage", method = RequestMethod.GET)
	public String prmDeptKpiValueUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmDeptKpiValue prmDeptKpiValue = new PrmDeptKpiValue();

		prmDeptKpiValue = prmDeptKpiValueService.queryPrmDeptKpiValueByCode(mapVo);

		mode.addAttribute("group_id", prmDeptKpiValue.getGroup_id());
		mode.addAttribute("hos_id", prmDeptKpiValue.getHos_id());
		mode.addAttribute("copy_code", prmDeptKpiValue.getCopy_code());
		mode.addAttribute("acc_year", prmDeptKpiValue.getAcc_year());
		mode.addAttribute("acc_month", prmDeptKpiValue.getAcc_month());
		mode.addAttribute("goal_code", prmDeptKpiValue.getGoal_code());
		mode.addAttribute("kpi_code", prmDeptKpiValue.getKpi_code());
		mode.addAttribute("dept_no", prmDeptKpiValue.getDept_no());
		mode.addAttribute("dept_id", prmDeptKpiValue.getDept_id());
		mode.addAttribute("kpi_value", prmDeptKpiValue.getKpi_value());
		mode.addAttribute("is_audit", prmDeptKpiValue.getIs_audit());
		mode.addAttribute("user_code", prmDeptKpiValue.getUser_code());
		mode.addAttribute("audit_date", prmDeptKpiValue.getAudit_date());

		return "hrp/prm/prmdeptkpivalue/prmDeptKpiValueUpdate";
	}

	/**
	 * @Description 更新数据 0308 科室KPI指标数据准备表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/updatePrmDeptKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmDeptKpiValue(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String prmDeptKpiValueJson = prmDeptKpiValueService.updatePrmDeptKpiValue(mapVo);

		return JSONObject.parseObject(prmDeptKpiValueJson);
	}

	/**
	 * @Description 删除数据 0308 科室KPI指标数据准备表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/deletePrmDeptKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptKpiValue(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("goal_code", ids[5]);
			mapVo.put("kpi_code", ids[6]);
			mapVo.put("dept_no", ids[7]);
			mapVo.put("dept_id", ids[8]);

			listVo.add(mapVo);

		}

		String prmDeptKpiValueJson = prmDeptKpiValueService.deleteBatchPrmDeptKpiValue(listVo);

		return JSONObject.parseObject(prmDeptKpiValueJson);

	}

	/**
	 * @Description 查询数据 0308 科室KPI指标数据准备表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/queryPrmDeptKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiValue(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String prmDeptKpiValue = prmDeptKpiValueService.queryPrmDeptKpiValue(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiValue);

	}

	/**
	 * @Description 查询数据 0308 科室KPI指标数据准备表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/queryPrmDeptKpiValueScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptKpiValueScheme(@RequestParam Map<String, Object> mapVo, Model mode)
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
		

		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String prmDeptKpiValue = prmDeptKpiValueService.queryPrmDeptKpiValueScheme(getPage(mapVo));

		return JSONObject.parseObject(prmDeptKpiValue);

	}

	/**
	 * @Description 导入跳转页面 0308 科室KPI指标数据准备表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/prmDeptKpiValueImportPage", method = RequestMethod.GET)
	public String prmDeptKpiValueImportPage(Model mode) throws Exception {

		return "hrp/prm/prmdeptkpivalue/prmDeptKpiValueImport";

	}

	/**
	 * @Description 下载导入模版 0308 科室KPI指标数据准备表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmdeptkpivalue/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "prm\\业务功能", "科室kpi指标数据采集.xlsx");

		return null;
	}

	

	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/createDeptKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createPrmHosTargetData(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String prmDeptKpiValue = prmDeptKpiValueService.createDeptKpiValue(mapVo);

		return JSONObject.parseObject(prmDeptKpiValue);

	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/auditPrmDeptKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditPrmHosTargetData(@RequestParam Map<String,Object> mapVo) throws Exception {

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
		
		String prmHosTargetDataJson = "";
		
		try {
			
			prmHosTargetDataJson = prmDeptKpiValueService.auditPrmDeptKpiValue(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			prmHosTargetDataJson = e.getMessage();
		}

		return JSONObject.parseObject(prmHosTargetDataJson);
	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/reAuditDeptKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reAuditHosKpiValue(@RequestParam Map<String,Object> mapVo) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("user_code","");
		mapVo.put("is_audit", 0);
		mapVo.put("audit_date","");
		
		String prmHosTargetDataJson = "";
		
		try {
			
			prmHosTargetDataJson = prmDeptKpiValueService.reauditPrmDeptKpiValue(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			prmHosTargetDataJson = e.getMessage();
		}

		return JSONObject.parseObject(prmHosTargetDataJson);
	}

	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/saveBatchDeptKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchDeptKpiValue(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		List<Map<String, Object>> kpi_data_batch = new ArrayList<Map<String, Object>>();

		JSONArray kpi_data_json = JSONArray.parseArray((String) mapVo.get("kpi_value_data"));

		Iterator kpi_data_it = kpi_data_json.iterator();
		boolean flag = false;
		try {

			while (kpi_data_it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(kpi_data_it.next().toString());

				Map<String, Object> mapKpi = new HashMap<String, Object>();

				if (!jsonObj.containsKey("group_id")) {
					continue;
				}

				mapKpi.put("group_id", jsonObj.get("group_id").toString());

				mapKpi.put("hos_id", jsonObj.get("hos_id").toString());

				mapKpi.put("copy_code", jsonObj.get("copy_code").toString());

				mapKpi.put("acc_year", jsonObj.get("acc_year").toString());

				mapKpi.put("acc_month", jsonObj.get("acc_month").toString());

				mapKpi.put("goal_code", jsonObj.get("goal_code").toString());
				
				mapKpi.put("dept_id", jsonObj.get("dept_id").toString());

				mapKpi.put("dept_no", jsonObj.get("dept_no").toString());
				
				mapKpi.put("kpi_code", jsonObj.get("kpi_code").toString());
				
				//Long isAudit = prmDeptKpiValueService.queryPrmDeptKpiValueByAudit(mapKpi);
				
				//if(isAudit > 0){
					//flag = true;
				//}


				mapKpi.put("kpi_value", jsonObj.get("kpi_value")==null || "".equals(jsonObj.get("kpi_value").toString())?"0":jsonObj.get("kpi_value").toString());

				kpi_data_batch.add(mapKpi);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		//if(flag){
			//return JSONObject.parseObject("{\"error\":\"存在已审核的指标不能继续操作.\"}");
		//}
		
		String PrmDeptKpiValueJson = prmDeptKpiValueService.updateBatchPrmDeptKpiValue(kpi_data_batch);

		return JSONObject.parseObject(PrmDeptKpiValueJson);
	}
	
	public Map<String,PrmDeptDict> getDeptMap(List<PrmDeptDict> deptDictList){
		
		if(deptDictList.size() == 0 ){
			
			return null;
		}
		
		Map<String,PrmDeptDict> deptMap = new HashMap<String,PrmDeptDict>();
		
		for(PrmDeptDict deptDict : deptDictList){
			
			deptMap.put(deptDict.getDept_name(), deptDict);
		}
		
		return deptMap;
	}
	
	// 导入
	@RequestMapping(value = "/hrp/prm/prmdeptkpivalue/importDeptKpiValue", method = RequestMethod.POST)
	@ResponseBody
	public String importDeptKpiValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		String importJson = null ;
			
		try {
				
			importJson = prmDeptKpiValueService.importPrmDeptKpiValue(mapVo);
				
		} catch (Exception e) {
			// TODO: handle exception
				
			importJson = e.getMessage();
		}
			
		return importJson;
	}
}
