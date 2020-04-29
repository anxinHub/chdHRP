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
import com.chd.base.SessionManager;
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiDeptTargetData;
import com.chd.hrp.hpm.service.AphiDeptTargetDataService;

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
public class AphiDeptTargetDataController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptTargetDataController.class);

	@Resource(name = "aphiDeptTargetDataService")
	private final AphiDeptTargetDataService aphiDeptTargetDataService = null;

//	@Resource(name = "aphiDeptService")
//	private final AphiDeptService aphiDeptService = null;

//	@Resource(name = "aphiTargetService")
//	private final AphiTargetService aphiTargetService = null;

//	@Resource(name = "aphiDeptMapingService")
//	private final AphiDeptMapingService aphiDeptMapingService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataMainPage", method = RequestMethod.GET)
	public String deptTargetDataMainPage(Model mode) throws Exception {
		
		if (!SessionManager.existsUserPerm("addHpmDeptTargetData")) {

			mode.addAttribute("is_add_show", true);

		} else {
			mode.addAttribute("is_add_show", false);
		}
		
		if (!SessionManager.existsUserPerm("initHpmDeptTargetData")) {

			mode.addAttribute("is_create_show", true);

		} else {
			mode.addAttribute("is_create_show", false);
		}
		
		if (!SessionManager.existsUserPerm("deleteHpmDeptTargetData")) {

			mode.addAttribute("is_del_show", true);

		} else {
			mode.addAttribute("is_del_show", false);
		}
		
		if (!SessionManager.existsUserPerm("auditHpmDeptTargetData")) {

			mode.addAttribute("is_audit_show", true);

		} else {
			mode.addAttribute("is_audit_show", false);
		}
		
		if (SessionManager.existsUserPerm("isShowZeroHpmDeptTargetData")) {

			mode.addAttribute("is_show_zero", true);

		} else {
			mode.addAttribute("is_show_zero", false);
		}
		
		
		return "hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataAddPage", method = RequestMethod.GET)
	public String deptTargetDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataAdd";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataViewPage", method = RequestMethod.GET)
	public String deptTargetDataViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("year_month", mapVo.get("year_month"));

		return "hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataView";

	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataImportPage", method = RequestMethod.GET)
	public String deptTargetDataImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("dept_kind_code", mapVo.get("dept_kind_code") == null ? "" :mapVo.get("dept_kind_code"));
		mode.addAttribute("acct_yearm", String.valueOf(mapVo.get("acct_yearm")));
		
		return "hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataImport";

	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/sysDeptTargetDataImportPage", method = RequestMethod.GET)
	public String sysDeptTargetDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdepttargetdata/sysDeptTargetDataImport";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/addHpmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmDeptTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		mapVo.put("user_code", "");
		
		mapVo.put("is_audit", 0);
		
		mapVo.put("audit_time", "");
		
		String deptTargetDataJson = aphiDeptTargetDataService.addDeptTargetData(mapVo);

		return JSONObject.parseObject(deptTargetDataJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/initHpmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initDeptTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("user_id") == null){
			
			mapVo.put("user_id", SessionManager.getUserId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptKindTargetDataJson = aphiDeptTargetDataService.initDeptTargetData(mapVo);

		return JSONObject.parseObject(deptKindTargetDataJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/queryHpmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		
		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}
		
		if("false".equals(mapVo.get("is_show_zero"))){//不显示指标值为0 的数据
			
			mapVo.put("is_show_zero", "0");
		}
		
		String deptTargetData = aphiDeptTargetDataService.queryDeptTargetData(getPage(mapVo));

		return JSONObject.parseObject(deptTargetData);

	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/queryDeptTargetViewGrid", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptTargetViewGrid(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("user_id") == null) {
			
			mapVo.put("user_id", SessionManager.getUserId());
			
		}
		mapVo.put("copy_code", COPY_CODE);

		String resultJson = aphiDeptTargetDataService.queryDeptTargetViewGrid(mapVo);

		return resultJson;

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/queryDeptTargetView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptTargetView(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		
		if (mapVo.get("user_id") == null) {
			
			mapVo.put("user_id", SessionManager.getUserId());
			
		}

		if (!"".equals(mapVo.get("year_month")) && mapVo.get("year_month") != null) {

			String acct_year = mapVo.get("year_month").toString().substring(0, 4);

			String acct_month = mapVo.get("year_month").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}
		String data = aphiDeptTargetDataService.queryDeptTargetView(getPage(mapVo));

		return JSONObject.parseObject(data);

	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/queryDeptTargetDataByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptTargetDataByDay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}
		String deptTargetData = aphiDeptTargetDataService.queryDeptTargetDataByDay(mapVo);

		return JSONObject.parseObject(deptTargetData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/deleteHpmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptTargetData(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptTargetDataJson = aphiDeptTargetDataService.deleteDeptTargetData(mapVo, checkIds);

		return JSONObject.parseObject(deptTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/deleteDeptTargetDataByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptTargetDataByDay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String deptTargetDataJson = aphiDeptTargetDataService.deleteBatchDeptTargetData(mapVo);

		return JSONObject.parseObject(deptTargetDataJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataUpdatePage", method = RequestMethod.GET)
	public String deptTargetDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		
		mapVo.put("copy_code", COPY_CODE);
		mapVo.put("user_id", SessionManager.getUserId());

		AphiDeptTargetData deptTargetData = new AphiDeptTargetData();

		deptTargetData = aphiDeptTargetDataService.queryDeptTargetDataByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", deptTargetData.getCopy_code());

		mode.addAttribute("acct_year", deptTargetData.getAcct_year());

		mode.addAttribute("acct_month", deptTargetData.getAcct_month());

		mode.addAttribute("target_code", deptTargetData.getTarget_code());

		mode.addAttribute("dept_id", deptTargetData.getDept_id());
		
		mode.addAttribute("dept_no", deptTargetData.getDept_no());

		mode.addAttribute("target_name", deptTargetData.getTarget_name());

		mode.addAttribute("dept_name", deptTargetData.getDept_name());

		mode.addAttribute("target_value", deptTargetData.getTarget_value());

		mode.addAttribute("is_audit", deptTargetData.getIs_audit());

		mode.addAttribute("user_code", deptTargetData.getUser_code());

		mode.addAttribute("audit_time", deptTargetData.getAudit_time());

		return "hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataUpdate";
	}
//科目
	@RequestMapping(value = "hrp/hpm/hpmdepttargetdata/hpmBookDeptSelectorPage", method = RequestMethod.GET)
	public String hpmBookDeptSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/hpm/hpmdepttargetdata/hpmBookDeptSelector";
	}
	
	//指标
@RequestMapping(value = "hrp/hpm/hpmdepttargetdata/hpmtargetSelectorPage", method = RequestMethod.GET)
		public String hpmtargetSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
			
			return "hrp/hpm/hpmdepttargetdata/hpmtargetSelector";
		}
	
	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/updateHpmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeptTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptTargetDataJson = aphiDeptTargetDataService.updateDeptTargetData(mapVo);

		return JSONObject.parseObject(deptTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/auditHpmDeptTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditHpmDeptTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String deptTargetDataJson = "";
		
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		
		deptTargetDataJson = aphiDeptTargetDataService.shenhe(mapVo);

		return JSONObject.parseObject(deptTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/getDeptTargetValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDeptTargetValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String targetValueJson = aphiDeptTargetDataService.getDeptTargetValue(mapVo);

		return JSONObject.parseObject(targetValueJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataChoosePage", method = RequestMethod.GET)
	public String deptTargetDataChoosePage(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		String[] paraArray = paras.split("@");

		mode.addAttribute("acct_yearm", paraArray[0]);

		if (!"null".equals(paraArray[1])) {

			mode.addAttribute("target_code", paraArray[1]);

		}

		if (!"null".equals(paraArray[2])) {

			mode.addAttribute("dept_id", paraArray[2]);

		}
		
		if (!"null".equals(paraArray[3])) {

			mode.addAttribute("dept_no", paraArray[3]);

		}

		return "hrp/hpm/hpmdepttargetdata/hpmDeptTargetDataChoose";

	}
	
	
	// 导入
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/importAphiDeptTargerData", method = RequestMethod.POST)
	@ResponseBody
	public String importAphiDeptTargerData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		String reJson = null;
		
		try {
			reJson=aphiDeptTargetDataService.importDeptTargerData(mapVo);
			return reJson;
		} catch (Exception e) {
			reJson = e.getMessage();
		}
			return reJson;
	}
	


//	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/addBatchSysDeptTargetDataDict", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchSysDeptTargetDataDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
//
//		if (mapVo.get("group_id") == null) {
//
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//
//		if (mapVo.get("hos_id") == null) {
//
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//
//		mapVo.put("copy_code", SessionManager.getCopyCode());
//
//		// 导入 先查询出所有科室 根据his的汉字科室名称 以及页面的传入的科室分类
//
//		// mapVo.put("dept_kind_code", mapVo.get("dept_kind_code"));
//
//		List<AphiDeptMaping> sysDeptList = aphiDeptMapingService.queryDeptMapingList(mapVo);
//
//		Map<String, String> sysDeptMap = new HashMap<String, String>();
//
//		for (AphiDeptMaping dm : sysDeptList) {
//
//			sysDeptMap.put(dm.getSys_dept_name(), dm.getDept_code());
//
//		}
//
//		// String target_code = String.valueOf(mapVo.get("target_code"));
//
//		String year_month = String.valueOf(mapVo.get("year_month"));
//
//		String acct_year = year_month.substring(0, 4);
//
//		String acct_month = year_month.substring(4, 6);
//
//		// mapVo.put("target_code", target_code);
//
//		mapVo.put("acct_year", acct_year);
//
//		mapVo.put("acct_month", acct_month);
//
//		List<AphiDeptTargetData> deptList = aphiDeptTargetDataService.queryDeptTargetDataByTargetCode(mapVo);
//
//		Map<String, String> deptMap = new HashMap<String, String>();
//
//		for (AphiDeptTargetData dtd : deptList) {
//
//			deptMap.put(dtd.getDept_code().toString(), String.valueOf(dtd.getTarget_value()));
//
//		}
//
//		List<AphiDeptTargetData> errorList = new ArrayList<AphiDeptTargetData>();
//
//		Map<String, Map<String, Object>> addMap = new HashMap<String, Map<String, Object>>();
//
//		Map<String, Map<String, Object>> updateMap = new HashMap<String, Map<String, Object>>();
//
//		JSONArray json = JSONArray.parseArray(String.valueOf(mapVo.get("data")));
//
//		Iterator it = json.iterator();
//
//		try {
//			while (it.hasNext()) {
//
//				StringBuffer errStr = new StringBuffer();
//
//				AphiDeptTargetData deptTargetData = new AphiDeptTargetData();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				Map<String, Object> map01 = new HashMap<String, Object>();
//
//				map01.putAll(mapVo);
//
//				if (sysDeptMap.get(jsonObj.get("dept_code")) != null) {
//
//					map01.put("dept_code", sysDeptMap.get(jsonObj.get("dept_code")));
//
//				} else {
//
//					errStr.append("找不到该科室对应关系  ");
//
//				}
//
//				map01.put("target_value", jsonObj.get("target_value"));
//
//				if (deptMap.get(sysDeptMap.get(jsonObj.get("dept_code"))) != null) {
//
//					Map map02 = updateMap.get(sysDeptMap.get(jsonObj.get("dept_code")));
//
//					if (map02 != null) {
//
//						map02.put("target_value",
//								Double.parseDouble(map02.get("target_value").toString()) + Double.parseDouble(map01.get("target_value").toString()));
//
//						updateMap.put(sysDeptMap.get(jsonObj.get("dept_code")), map02);
//
//					} else {
//
//						updateMap.put(sysDeptMap.get(jsonObj.get("dept_code")), map01);
//
//					}
//
//				} else {
//
//					addMap.put(sysDeptMap.get(jsonObj.get("dept_code")), map01);
//
//				}
//
//				if (errStr.toString().length() > 0) {
//
//					deptTargetData.setDept_code((String) jsonObj.get("dept_code"));
//
//					deptTargetData.setTarget_value(Double.parseDouble(jsonObj.get("target_value").toString()));
//
//					deptTargetData.setError_type(errStr.toString());
//
//					errorList.add(deptTargetData);
//				}
//			}
//
//			for (String key : addMap.keySet()) {
//				aphiDeptTargetDataService.addDeptTargetData(addMap.get(key));
//			}
//			for (String key : updateMap.keySet()) {
//				aphiDeptTargetDataService.updateDeptTargetData(updateMap.get(key));
//			}
//
//		} catch (DataAccessException e) {
//
//			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
//
//		}
//
//		if (errorList.size() > 0) {
//
//			return JSONObject.parseObject(JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//		} else {
//
//			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
//
//		}
//
//	}
	
	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmdepttargetdata/downTemplateDeptTargetData", method = RequestMethod.GET)
	public String downTemplateDeptTargetData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "科室指标数据采集.xlsx");
		return null;
	}

}
