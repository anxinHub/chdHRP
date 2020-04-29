package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiEmpTargetData;
import com.chd.hrp.hpm.service.AphiEmpTargetDataService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiEmpTargetDataController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiEmpTargetDataController.class);

	@Resource(name = "aphiEmpTargetDataService")
	private final AphiEmpTargetDataService aphiEmpTargetDataService = null;

//	@Resource(name = "aphiTargetService")
//	private final AphiTargetService aphiTargetService = null;

//	@Resource(name = "aphiEmpService")
//	private final AphiEmpService aphiEmpService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/hpmEmpTargetDataMainPage", method = RequestMethod.GET)
	public String empTargetDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmemptargetdata/hpmEmpTargetDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/hpmEmpTargetDataAddPage", method = RequestMethod.GET)
	public String empTargetDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmemptargetdata/hpmEmpTargetDataAdd";

	}

	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/hpmEmpTargetDataImportPage", method = RequestMethod.GET)
	public String empTargetDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmemptargetdata/hpmEmpTargetDataImport";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/addHpmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addEmpTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		mapVo.put("is_audit", 0);
		mapVo.put("user_code", "");
		mapVo.put("audit_time", "");

		String empTargetDataJson = aphiEmpTargetDataService.addEmpTargetData(mapVo);

		return JSONObject.parseObject(empTargetDataJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/initHpmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initEmpTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptKindTargetDataJson = aphiEmpTargetDataService.initEmpTargetData(mapVo);

		return JSONObject.parseObject(deptKindTargetDataJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/queryHpmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		String empTargetData = aphiEmpTargetDataService.queryEmpTargetData(getPage(mapVo));

		return JSONObject.parseObject(empTargetData);

	}

	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/queryEmpTargetDataByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpTargetDataByDay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}

		String empTargetData = aphiEmpTargetDataService.queryEmpTargetDataByDay(mapVo);

		return JSONObject.parseObject(empTargetData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/deleteHpmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEmpTargetData(@RequestParam String checkIds, Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String empTargetDataJson = aphiEmpTargetDataService.deleteEmpTargetData(mapVo, checkIds);

		return JSONObject.parseObject(empTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/deleteEmpTargetDataByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEmpTargetDataByDay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}

		String empTargetDataJson = aphiEmpTargetDataService.deleteBatchEmpTargetData(mapVo);

		return JSONObject.parseObject(empTargetDataJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/hpmEmpTargetDataUpdatePage", method = RequestMethod.GET)
	public String empTargetDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("acct_year", mapVo.get("acct_year"));
		mode.addAttribute("acct_month",mapVo.get("acct_month"));
		mode.addAttribute("target_code", mapVo.get("target_code"));
		mode.addAttribute("emp_id", mapVo.get("emp_id"));
		mode.addAttribute("emp_no", mapVo.get("emp_no"));
		mode.addAttribute("target_value", mapVo.get("target_value"));

		return "hrp/hpm/hpmemptargetdata/hpmEmpTargetDataUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/updateHpmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEmpTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String empTargetDataJson = aphiEmpTargetDataService.updateEmpTargetData(mapVo);

		return JSONObject.parseObject(empTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/shenhe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> shenhe(@RequestParam String checkIds, Model mode) throws Exception {

		String user_code = SessionManager.getUserCode();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for (String ids : checkIds.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] data = ids.split(";");
			
			mapVo.put("group_id", data[0]);
			mapVo.put("hos_id", data[1]);
			mapVo.put("copy_code", data[2]);
			mapVo.put("acct_year", data[3]);
			mapVo.put("acct_month", data[4]);
			mapVo.put("target_code", data[5]);
			mapVo.put("emp_id", data[6]);
			mapVo.put("emp_no", data[7]);
			mapVo.put("is_audit", data[8]);

			if ("1".equals(data[8])) {
				mapVo.put("user_code", user_code);
				mapVo.put("audit_time", new Date());
			} else {
				mapVo.put("user_code", "");
				mapVo.put("audit_time", "");
			}
			
			list.add(mapVo);

		}
		
		String empTargetDataJson = null;
		
		try {
			empTargetDataJson = aphiEmpTargetDataService.shenhe(list);
			
		} catch (Exception e) {
			// TODO: handle exception
			empTargetDataJson = e.getMessage();
		}

		return JSONObject.parseObject(empTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/getEmpTargetValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getEmpTargetValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String targetValueJson = aphiEmpTargetDataService.getEmpTargetValue(mapVo);

		return JSONObject.parseObject(targetValueJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/hpmEmpTargetDataChoosePage", method = RequestMethod.GET)
	public String empTargetDataChoosePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		String[] paraArray = paras.split("@");

		mode.addAttribute("acct_yearm", paraArray[0]);

		if (!"null".equals(paraArray[1])) {

			mode.addAttribute("target_code", paraArray[1]);

		}

		if (!"null".equals(paraArray[2])) {

			mode.addAttribute("emp_id", paraArray[2]);

		}
		
		if (!"null".equals(paraArray[3])) {
			
			mode.addAttribute("emp_no", paraArray[3]);
			
		}

		return "hrp/hpm/hpmemptargetdata/hpmEmpTargetDataChoose";

	}
	
	/**
	 * @Description 导入数据 8801 职工字典表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/importHpmEmpTargetData", method = RequestMethod.POST)
	@ResponseBody
	public String importHpmEmpTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String reJson = null;
		
		try {
			reJson=aphiEmpTargetDataService.importEmpTargetData(mapVo);
			return reJson;
		} catch (Exception e) {
			reJson = e.getMessage();
		}
			return reJson;
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmemptargetdata/downTemplateEmpTargetData", method = RequestMethod.GET)
	public String downTemplateEmpTargetData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "职工指标数据采集.xlsx");
		return null;
	}
}
