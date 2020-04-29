package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.HashMap;
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
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiEmpDutyData;
import com.chd.hrp.hpm.service.AphiEmpDutyDataService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiEmpDutyDataController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiEmpDutyDataController.class);

	@Resource(name = "aphiEmpDutyDataService")
	private final AphiEmpDutyDataService aphiEmpDutyDataService = null;

	/**
	 * 主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/hpmEmpDutyDataMainPage", method = RequestMethod.GET)
	public String hpmEmpDutyDataMain(Model mode) throws Exception {

		return "hrp/hpm/hpmempdutydata/hpmEmpDutyDataMain";

	}
	
	
	/**
	 * 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/queryHpmEmpDutyData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpDutyData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String emp = aphiEmpDutyDataService.queryEmpDutyData(getPage(mapVo));

		return JSONObject.parseObject(emp);

	}

	/**
	 * 添加页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/addHpmEmpDutyDataPage", method = RequestMethod.GET)
	public String addHpmEmpDutyDataPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempdutydata/hpmEmpDutyDataAdd";

	}

	/**
	 * 添加保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/addHpmEmpDutyData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmEmpDutyData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		String empJson = aphiEmpDutyDataService.addEmpDutyData(mapVo);

		return JSONObject.parseObject(empJson);

	}

	/**
	 * 生成页面
	 * @param acct_yearm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/initHpmEmpDutyDataPage", method = RequestMethod.GET)
	public String EmpDutyDataFastPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("dept_id",mapVo.get("dept_id"));
		mode.addAttribute("dept_no",mapVo.get("dept_no"));
		mode.addAttribute("acct_yearm",mapVo.get("acct_yearm"));

		return "hrp/hpm/hpmempdutydata/hpmEmpDutyDataFast";

	}

	/**
	 * 生成保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/initHpmEmpDutyData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initEmpDutyData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		String deptKindTargetDataJson = aphiEmpDutyDataService.initEmpDutyData(mapVo);

		return JSONObject.parseObject(deptKindTargetDataJson);

	}

	/**
	 * 删除
	 * @param checkIds
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/deleteHpmEmpDutyData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEmpDutyData(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map map = new HashMap();

		if (map.get("group_id") == null) {

			map.put("group_id", SessionManager.getGroupId());
		}

		if (map.get("hos_id") == null) {

			map.put("hos_id", SessionManager.getHosId());
		}

		map.put("copy_code", COPY_CODE);

		String empJson = aphiEmpDutyDataService.deleteEmpDutyData(map, checkIds);

		return JSONObject.parseObject(empJson);
	}

	/**
	 * 修改页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/updateHpmEmpDutyDataPage", method = RequestMethod.GET)
	public String EmpDutyDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		if(MyConfig.getSysPara("09001") == null){
			mapVo.put("para_value", 0);
		}else{
			mapVo.put("para_value", MyConfig.getSysPara("09001"));
		}

		AphiEmpDutyData emp = new AphiEmpDutyData();

		emp = aphiEmpDutyDataService.queryEmpDutyDataByCode(mapVo);
		

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", emp.getCopy_code());

		mode.addAttribute("acct_year", emp.getAcct_year());

		mode.addAttribute("acct_month", emp.getAcct_month());

		mode.addAttribute("emp_id", emp.getEmp_id());
		
		mode.addAttribute("emp_no", emp.getEmp_no());

		mode.addAttribute("emp_name", emp.getEmp_name());

		mode.addAttribute("emp_duty_amount", emp.getEmp_duty_amount());

		return "hrp/hpm/hpmempdutydata/hpmEmpDutyDataUpdate";
	}

	/**
	 * 修改保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/updateHpmEmpDutyData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEmpDutyData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String empJson = aphiEmpDutyDataService.updateEmpDutyData(mapVo);

		return JSONObject.parseObject(empJson);
	}

	/**
	 * 导入页面
	 * @return
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/hpmEmpDutyDataImportPage",method = RequestMethod.GET)
	public String hpmEmpDutyDataImportPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
		
		return "hrp/hpm/hpmempdutydata/hpmEmpDutyDataImport";
	}
	
	/**
	 * 导入保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/hpmEmpDutyDataImport",method = RequestMethod.POST)
	@ResponseBody
	public String importHpmEmpDutyData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
		
		try {
			
			if(MyConfig.getSysPara("09001") == null){
				mapVo.put("para_value", 0);
			}else{
				mapVo.put("para_value", MyConfig.getSysPara("09001"));
			}
			
			String impJson = aphiEmpDutyDataService.importHpmEmpDutyData(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * 下载模板
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempdutydata/downTemplateHpmEmpDutyData",method = RequestMethod.GET)
	public String downTemplateHpmEmpDutyData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		
		printTemplate(request, response, "hpm\\downTemplate", "职工岗位系数数据准备.xlsx");
		
		return null;
	}
}
