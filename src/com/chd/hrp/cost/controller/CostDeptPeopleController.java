/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostDeptPeople;
import com.chd.hrp.cost.entity.CostOutAcctVouch;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostDeptPeopleServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostOutAcctVouchServiceImpl;

/**
 * @Title. @Description. 科室人员表
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostDeptPeopleController extends BaseController {
	private static Logger logger = Logger
			.getLogger(CostDeptPeopleController.class);

	@Resource(name = "costDeptPeopleService")
	private final CostDeptPeopleServiceImpl costDeptPeopleService = null;

	@Resource(name = "costOutAcctVouchService")
	private final CostOutAcctVouchServiceImpl costOutAcctVouchService = null;

	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;

	/**
	 * 科室人员表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costdeptpeople/costDeptPeopleMainPage", method = RequestMethod.GET)
	public String costDeptPeopleMainPage(Model mode) throws Exception {
		return "hrp/cost/costdeptpeople/costDeptPeopleMain";
	}

	/**
	 * 科室人员表<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costdeptpeople/costDeptPeopleAddPage", method = RequestMethod.GET)
	public String costDeptPeopleAddPage(Model mode) throws Exception {

		return "hrp/cost/costdeptpeople/costDeptPeopleAdd";

	}

	/**
	 * 科室人员表<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costdeptpeople/addCostDeptPeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostDeptPeople(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
		
			mapVo.put("hos_id", SessionManager.getHosId());
	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String costDeptPeopleJson = costDeptPeopleService.addCostDeptPeople(mapVo);

		return JSONObject.parseObject(costDeptPeopleJson);

	}

	/**
	 * 科室人员表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costdeptpeople/queryCostDeptPeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptPeople(
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
 
		String para_value = MyConfig.getSysPara("03001");

		mapVo.put("is_flag", para_value);

		String costDeptPeople = costDeptPeopleService
				.queryCostDeptPeople(getPage(mapVo));

		return JSONObject.parseObject(costDeptPeople);

	}

	/**
	 * 科室人员表<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costdeptpeople/deleteCostDeptPeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostDeptPeople(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);

			mapVo.put("dept_id", ids[5]);

			mapVo.put("dept_no", ids[6]);

			listVo.add(mapVo);
		}
		String costDeptPeopleJson = costDeptPeopleService
				.deleteBatchCostDeptPeople(listVo);
		return JSONObject.parseObject(costDeptPeopleJson);

	}
	
	/**
	 * 科室人员表<BR>
	 * 按月刪除
	 */
	@RequestMapping(value = "/hrp/cost/costdeptpeople/deleteMonthlyCostDeptPeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMonthlyCostDeptPeople(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String costDeptPeopleJson;
		try {
			
			 costDeptPeopleJson = costDeptPeopleService.deleteMonthlyCostDeptPeople(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			costDeptPeopleJson = e.getMessage();
		}
	
		return JSONObject.parseObject(costDeptPeopleJson);

	}

	/**
	 * 科室人员表<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costdeptpeople/costDeptPeopleUpdatePage", method = RequestMethod.GET)
	public String costDeptPeopleUpdatePage(
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
		CostDeptPeople costDeptPeople = new CostDeptPeople();

		  String para_value = MyConfig.getSysPara("03001");
			
	    	mapVo.put("is_flag", para_value);

		costDeptPeople = costDeptPeopleService.queryCostDeptPeopleByCode(mapVo);

		mode.addAttribute("group_id", costDeptPeople.getGroup_id());

		mode.addAttribute("hos_id", costDeptPeople.getHos_id());

		mode.addAttribute("copy_code", costDeptPeople.getCopy_code());

		mode.addAttribute("year_month", costDeptPeople.getAcc_year().toString()
				+ costDeptPeople.getAcc_month().toString()); 

		mode.addAttribute("acc_year", costDeptPeople.getAcc_year());
		mode.addAttribute("acc_month", costDeptPeople.getAcc_month());

		mode.addAttribute("dept_id", costDeptPeople.getDept_id());

		mode.addAttribute("dept_no", costDeptPeople.getDept_no());

		mode.addAttribute("dept_code", costDeptPeople.getDept_code());

		mode.addAttribute("dept_name", costDeptPeople.getDept_name());

		mode.addAttribute("doctor_num", costDeptPeople.getDoctor_num());

		mode.addAttribute("nurse_num", costDeptPeople.getNurse_num());

		mode.addAttribute("technician_num", costDeptPeople.getTechnician_num());

		mode.addAttribute("pharmacist_num", costDeptPeople.getPharmacist_num());

		mode.addAttribute("manager_num", costDeptPeople.getManager_num());

		mode.addAttribute("supportor_num", costDeptPeople.getSupportor_num());

		mode.addAttribute("floater_num", costDeptPeople.getFloater_num());

		mode.addAttribute("out_employ_num", costDeptPeople.getOut_employ_num());

		mode.addAttribute("clearner_num", costDeptPeople.getClearner_num());
		
		mode.addAttribute("alldept_num", costDeptPeople.getAlldept_num());

		return "hrp/cost/costdeptpeople/costDeptPeopleUpdate";
	}

	/**
	 * 科室人员表<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costdeptpeople/updateCostDeptPeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostDeptPeople(
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

		String costDeptPeopleJson = costDeptPeopleService.updateCostDeptPeople(mapVo);
		return JSONObject.parseObject(costDeptPeopleJson);
	}
	
	// 人员采集添加继承功能
	@RequestMapping(value = "/hrp/cost/costdeptpeople/costDeptPeopleExtendMainPage", method = RequestMethod.GET)
	public String costDeptPeopleExtendMainPage(Model mode) throws Exception {
		return "hrp/cost/costdeptpeople/costDeptPeopleExtendMain";
	}

	
	@RequestMapping(value = "/hrp/cost/costdeptpeople/extendCostDeptPeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendCostDeptPeople(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String costDeptPeopleJson;

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		 
        
		try {
			
			 costDeptPeopleJson = costDeptPeopleService.extendCostDeptPeople(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			costDeptPeopleJson = e.getMessage();
		}

		return JSONObject.parseObject(costDeptPeopleJson);

	}
	
	
	
	// 导入页面
	@RequestMapping(value = "/hrp/cost/costdeptpeople/costDeptPeopleImportPage", method = RequestMethod.GET)
	public String costDeptPeopleImportPage(Model mode) throws Exception {

		return "hrp/cost/costdeptpeople/costDeptPeopleImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costdeptpeople/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "科室人员采集.xls");

		return null;
	}

	/**
	 * 科室人员表<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costdeptpeople/costDeptPeopleImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String readCostDeptPeopleFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		try {
			
			String reJson=costDeptPeopleService.readAssFinaDictFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}

		
	}

	/**
	 * 科室人员表<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costdeptpeople/addBatchCostDeptPeople", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostDeptPeople(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostDeptPeople> list_err = new ArrayList<CostDeptPeople>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {
			while (it.hasNext()) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				StringBuffer err_sb = new StringBuffer();

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {

					mapVo.put("copy_code", SessionManager.getCopyCode());
				}

				String year_month = String.valueOf(jsonObj.get("year_month"));

				mapVo.put("acc_year", year_month.substring(0, 4));

				mapVo.put("acc_month", year_month.substring(4, 6));

				mapVo.put("dept_code", jsonObj.get("dept_code"));

				mapVo.put("dept_name", jsonObj.get("dept_name"));

				if(jsonObj.get("doctor_num") != null){
				
				    mapVo.put("doctor_num", jsonObj.get("doctor_num"));
				    
				}else{
					
					mapVo.put("doctor_num", 0);
				}

				if(jsonObj.get("nurse_num") != null){
					
					mapVo.put("nurse_num", jsonObj.get("nurse_num"));
				    
				}else{
					
					mapVo.put("nurse_num", 0);
				}
				
				if(jsonObj.get("technician_num") != null){
					
					mapVo.put("technician_num", jsonObj.get("technician_num"));
				    
				}else{
					
					mapVo.put("technician_num", 0);
				}

				if(jsonObj.get("pharmacist_num") != null){
					
					mapVo.put("pharmacist_num", jsonObj.get("pharmacist_num"));
				    
				}else{
					
					mapVo.put("pharmacist_num", 0);
				}

				if(jsonObj.get("manager_num") != null){
					
					mapVo.put("manager_num", jsonObj.get("manager_num"));
				    
				}else{
					
					mapVo.put("manager_num", 0);
				}
				
				if(jsonObj.get("supportor_num") != null){
					
					mapVo.put("supportor_num", jsonObj.get("supportor_num"));
				    
				}else{
					
					mapVo.put("supportor_num", 0);
				}

				if(jsonObj.get("floater_num") != null){
					
					mapVo.put("floater_num", jsonObj.get("floater_num"));
				    
				}else{
					
					mapVo.put("floater_num", 0);
				}
				
				if(jsonObj.get("out_employ_num") != null){
					
					mapVo.put("out_employ_num", jsonObj.get("out_employ_num"));
				    
				}else{
					
					mapVo.put("out_employ_num", 0);
				}

			    if(jsonObj.get("clearner_num")!= null){
					
					mapVo.put("clearner_num", jsonObj.get("clearner_num"));
				    
				}else{
					
					mapVo.put("clearner_num", 0);
				}

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));

				byCodeMap.put("dept_code", mapVo.get("dept_code"));

				byCodeMap.put("is_stop", "0");

				CostOutAcctVouch costDept = costOutAcctVouchService
						.queryCostDeptByCode(byCodeMap);

				if (costDept != null) {

					mapVo.put("dept_id", costDept.getDept_id());

					mapVo.put("dept_no", costDept.getDept_no());

				} else {

					err_sb.append("科室不存在 ");

				}

				CostDeptPeople data_exc_extis = costDeptPeopleService
						.queryCostDeptPeopleByCode(mapVo);

				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}

				CostDeptPeople costDeptPeople = new CostDeptPeople();

				if (err_sb.toString().length() > 0) {
					
					costDeptPeople.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());

					costDeptPeople.setAcc_year(mapVo.get("acc_year").toString());

					costDeptPeople.setAcc_month(mapVo.get("acc_month").toString());

					costDeptPeople.setDept_code(mapVo.get("dept_code").toString());

					costDeptPeople.setDept_name(mapVo.get("dept_name").toString());

					costDeptPeople.setDoctor_num(Long.valueOf(mapVo.get("doctor_num").toString()));

					costDeptPeople.setNurse_num(Long.valueOf(mapVo.get("nurse_num").toString()));

					costDeptPeople.setTechnician_num(Long.valueOf(mapVo.get("technician_num").toString()));

					costDeptPeople.setPharmacist_num(Long.valueOf(mapVo.get("pharmacist_num").toString()));

					costDeptPeople.setManager_num(Long.valueOf(mapVo.get("manager_num").toString()));

					costDeptPeople.setSupportor_num(Long.valueOf(mapVo.get("supportor_num").toString()));

					costDeptPeople.setFloater_num(Long.valueOf(mapVo.get("floater_num").toString()));

					costDeptPeople.setOut_employ_num(Long.valueOf(mapVo.get("out_employ_num").toString()));

					costDeptPeople.setClearner_num(Long.valueOf(mapVo.get("clearner_num").toString()));

					costDeptPeople.setError_type(err_sb.toString());

					list_err.add(costDeptPeople);
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costDeptPeopleService.addBatchCostDeptPeople(list_batch);

			}
		} catch (DataAccessException e) {

			return JSONObject
					.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));

		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}
	}
	
	
	
	/**
	 * 科室人员表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costdeptpeople/costPeopleCollectAddPage", method = RequestMethod.GET)
	public String costDeptPeopleCollect(Model mode) throws Exception {

		return "hrp/cost/costdeptpeople/costPeopleCollect";

	}
	
	/**
	 * 科室人员表<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costdeptpeople/addPeopleCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPeopleCollect(
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
		String costDeptPeopleJson = costDeptPeopleService
				.addPeopleCollect(mapVo);

		return JSONObject.parseObject(costDeptPeopleJson);

	}
	
	
}
