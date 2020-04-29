package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiDeptKindTargetData;
import com.chd.hrp.hpm.service.AphiDeptKindTargetDataService;

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
public class AphiDeptKindTargetDataController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptKindTargetDataController.class);

	@Resource(name = "aphiDeptKindTargetDataService")
	private final AphiDeptKindTargetDataService aphiDeptKindTargetDataService = null;

//	@Resource(name = "aphiDeptKindService")
//	private final AphiDeptKindService aphiDeptKindService = null;

//	@Resource(name = "aphiTargetService")
//	private final AphiTargetService aphiTargetService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataMainPage", method = RequestMethod.GET)
	public String deptKindTargetDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataImportPage", method = RequestMethod.GET)
	public String deptKindTargetDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataImport";

	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataAddPage", method = RequestMethod.GET)
	public String deptKindTargetDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/addHpmDeptKindTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmDeptKindTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
			
		mapVo.put("user_code","");
		mapVo.put("is_audit", 0);
		mapVo.put("audit_time","");


		String deptKindTargetDataJson = aphiDeptKindTargetDataService.addDeptKindTargetData(mapVo);

		return JSONObject.parseObject(deptKindTargetDataJson);

	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/initHpmDeptKindTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initDeptKindTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptKindTargetDataJson = aphiDeptKindTargetDataService.initDeptKindTargetData(mapVo);

		return JSONObject.parseObject(deptKindTargetDataJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/queryHpmDeptKindTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDeptKindTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}
		String deptKindTargetData = aphiDeptKindTargetDataService.queryDeptKindTargetData(getPage(mapVo));

		return JSONObject.parseObject(deptKindTargetData);

	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/queryDeptKindTargetDataByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptKindTargetDataByDay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}

		String deptKindTargetData = aphiDeptKindTargetDataService.queryDeptKindTargetDataByDay(mapVo);

		return JSONObject.parseObject(deptKindTargetData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/deleteHpmDeptKindTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDeptKindTargetData(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptKindTargetDataJson = aphiDeptKindTargetDataService.deleteDeptKindTargetData(mapVo, checkIds);

		return JSONObject.parseObject(deptKindTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/deleteDeptKindTargetDataByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptKindTargetDataByDay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

			String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

			String acct_month = mapVo.get("acct_yearm").toString().substring(4);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

		}

		String hospTargetDataJson = aphiDeptKindTargetDataService.deleteBatchDeptKindTargetData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataUpdatePage", method = RequestMethod.GET)
	public String deptKindTargetDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		mapVo.put("copy_code", COPY_CODE);

		AphiDeptKindTargetData deptKindTargetData = new AphiDeptKindTargetData();

		deptKindTargetData = aphiDeptKindTargetDataService.queryDeptKindTargetDataByCode(mapVo);

		mode.addAttribute("group_id",mapVo.get("group_id"));
		mode.addAttribute("group_id",mapVo.get("hos_id"));

		mode.addAttribute("copy_code", deptKindTargetData.getCopy_code());

		mode.addAttribute("acct_year", deptKindTargetData.getAcct_year());

		mode.addAttribute("acct_month", deptKindTargetData.getAcct_month());

		mode.addAttribute("target_code", deptKindTargetData.getTarget_code());

		mode.addAttribute("dept_kind_code", deptKindTargetData.getDept_kind_code());

		mode.addAttribute("target_name", deptKindTargetData.getTarget_name());

		mode.addAttribute("dept_kind_name", deptKindTargetData.getDept_kind_name());

		mode.addAttribute("target_value", deptKindTargetData.getTarget_value());

		mode.addAttribute("is_audit", deptKindTargetData.getIs_audit());

		mode.addAttribute("user_code", deptKindTargetData.getUser_code());

		mode.addAttribute("audit_time", deptKindTargetData.getAudit_time());

		return "hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/updateHpmDeptKindTargetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmDeptKindTargetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("user_code") == null){
			
			mapVo.put("user_code", SessionManager.getUserCode());
		}
		
		mapVo.put("is_audit", 0);
		mapVo.put("audit_time","");


		String deptKindTargetDataJson = aphiDeptKindTargetDataService.updateDeptKindTargetData(mapVo);

		return JSONObject.parseObject(deptKindTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/shenhe", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> shenhe(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		String USER_CODE = SessionManager.getUserCode();

		String deptKindTargetDataJson = "";

		for (String ids : checkIds.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String target_code = ids.split(";")[0];

			String dept_kind_code = ids.split(";")[1];

			String acct_yearm = ids.split(";")[2];

			String is_audit = ids.split(";")[3];

			mapVo.put("target_code", target_code);

			mapVo.put("dept_kind_code", dept_kind_code);

			mapVo.put("acct_year", acct_yearm.substring(0, 4));

			mapVo.put("acct_month", acct_yearm.substring(4));

			if(mapVo.get("group_id") == null){
				
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if(mapVo.get("hos_id") == null){
				
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("copy_code", COPY_CODE);

			mapVo.put("is_audit", is_audit);

			if ("1".equals(is_audit)) {

				mapVo.put("user_code", USER_CODE);

				mapVo.put("audit_time", DateUtil.getCurrenDate("yyyy/MM/dd"));

			} else {

				mapVo.put("user_code", "");

				mapVo.put("audit_time", "");

			}

			deptKindTargetDataJson = aphiDeptKindTargetDataService.shenhe(mapVo);

		}

		return JSONObject.parseObject(deptKindTargetDataJson);
	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/getDeptKindTargetValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDeptKindTargetValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String targetValueJson = aphiDeptKindTargetDataService.getDeptKindTargetValue(mapVo);

		return JSONObject.parseObject(targetValueJson);

	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataChoose", method = RequestMethod.GET)
	public String hpmDeptKindTargetDataChoose(@RequestParam(value = "paras", required = true) String paras, Model mode) throws Exception {

		String[] paraArray = paras.split("@");
		
		mode.addAttribute("acct_yearm", paraArray[0]);
		
		if(!"null".equals(paraArray[1])){

			mode.addAttribute("target_code", paraArray[1]);
			
		}
		
		if(!"null".equals(paraArray[2])){

			mode.addAttribute("dept_kind_code", paraArray[2]);
			
		}

		return "hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataChoose";

	}

//	/**
//	 * 导入
//	 */
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/readDeptKindTargetDataFiles", method = RequestMethod.POST)
//	public String readDeptKindTargetDataFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		mapVo.put("copy_code", COPY_CODE);
//
//		List<AphiDeptKindTargetData> list2 = new ArrayList<AphiDeptKindTargetData>();
//
//		List<String[]> list = UploadUtil.readFile(plupload, request, response);
//
//		// List<Item> errorList = new ArrayList<Item>();
//
//		try {
//
//			for (int i = 1; i < list.size(); i++) {
//
//				StringBuffer err_sb = new StringBuffer();
//
//				AphiDeptKindTargetData deptKindTargetData = new AphiDeptKindTargetData();
//
//				String temp[] = list.get(i);// 行
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					deptKindTargetData.setAcct_year(temp[0]);
//
//					mapVo.put("acct_year", temp[0]);
//
//				} else {
//
//					err_sb.append("核算年度为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[1])) {
//
//					deptKindTargetData.setAcct_month(temp[1]);
//
//					mapVo.put("acct_month", temp[1]);
//
//				} else {
//
//					err_sb.append("核算月份为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[2])) {
//
//					deptKindTargetData.setDept_kind_code(temp[2]);
//
//					mapVo.put("dept_kind_code", temp[2]);
//
//				} else {
//
//					err_sb.append("科室分类为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[3])) {
//
//					deptKindTargetData.setTarget_code(temp[3]);
//
//					mapVo.put("target_code", temp[3]);
//
//				} else {
//
//					err_sb.append("指标名称为空  ");
//
//				}
//				if (StringUtils.isNotEmpty(temp[4])) {
//
//					deptKindTargetData.setTarget_value(Double.parseDouble(temp[4]));
//
//					mapVo.put("target_value", temp[4]);
//
//				} else {
//
//					err_sb.append("指标值为空  ");
//
//				}
//
//				AphiDeptKindTargetData dktd = aphiDeptKindTargetDataService.queryDeptKindTargetDataByCode(mapVo);
//
//				if (dktd != null) {
//
//					err_sb.append("数据编码已经存在！ ");
//
//				}
//
//				AphiDeptKind dk = aphiDeptKindService.queryDeptKindByCode(mapVo);
//
//				if (dk == null) {
//
//					err_sb.append("科室分类不存在");
//
//				}
//
////				AphiTarget tt = aphiTargetService.queryTargetByCode(mapVo);
////
////				if (tt == null) {
////
////					err_sb.append("指标名称不存在");
////
////				}
//
//				if (err_sb.toString().length() > 0) {
//
//					deptKindTargetData.setError_type(err_sb.toString());
//
//					list2.add(deptKindTargetData);
//
//				} else {
//
//					aphiDeptKindTargetDataService.addDeptKindTargetData(mapVo);
//
//				}
//
//			}
//
//		} catch (DataAccessException e) {
//			AphiDeptKindTargetData cd = new AphiDeptKindTargetData();
//
//			cd.setError_type("导入系统出错");
//
//			list2.add(cd);
//
//			response.getWriter().print(JsonListBeanUtil.listToJson(list2, list2.size()));
//
//			return null;
//		}
//
//		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(list2, list2.size()));
//		return "/hrp/hpm/hpmdeptkindtargetdata/hpmDeptKindTargetDataImportMessage";
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/addBatchDeptKindTargetDataDict", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchDeptKindTargetDataDict(@RequestParam(value = "ParamVo") String paramVo, Model mode, HttpServletResponse response) throws Exception {
//
//		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
//
//		List<AphiDeptKindTargetData> list_err = new ArrayList<AphiDeptKindTargetData>();
//
//		JSONArray json = JSONArray.parseArray(paramVo);
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		String s = null;
//
//		Iterator it = json.iterator();
//		try {
//			while (it.hasNext()) {
//
//				StringBuffer err_sb = new StringBuffer();
//
//				AphiDeptKindTargetData deptKindTargetData = new AphiDeptKindTargetData();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				// Set<String> key = jsonObj.keySet();
//
//				if(mapVo.get("group_id") == null){
//					
//					mapVo.put("group_id", SessionManager.getGroupId());
//				}
//				
//				if(mapVo.get("hos_id") == null){
//					
//					mapVo.put("hos_id", SessionManager.getHosId());
//				}
//
//				mapVo.put("copy_code", SessionManager.getCopyCode());
//
//				mapVo.put("acct_year", jsonObj.get("acct_year"));
//
//				mapVo.put("acct_month", jsonObj.get("acct_month"));
//
//				mapVo.put("dept_kind_code", jsonObj.get("dept_kind_code"));
//
//				mapVo.put("target_code", jsonObj.get("target_code"));
//
//				mapVo.put("target_value", jsonObj.get("target_value"));
//
//				mapVo.put("is_stop", jsonObj.get("is_stop"));
//
//				AphiDeptKindTargetData dktd = aphiDeptKindTargetDataService.queryDeptKindTargetDataByCode(mapVo);
//
//				if (dktd != null) {
//
//					err_sb.append("数据编码已经存在！ ");
//				}
//
//				AphiDeptKind dk = aphiDeptKindService.queryDeptKindByCode(mapVo);
//
//				if (dk == null) {
//
//					err_sb.append("科室分类不存在");
//
//				}
//
////				AphiTarget tt = aphiTargetService.queryTargetByCode(mapVo);
////
////				if (tt == null) {
////
////					err_sb.append("指标名称不存在");
////
////				}
//
//				if (err_sb.toString().length() > 0) {
//
//					deptKindTargetData.setAcct_year((String) jsonObj.get("acct_year"));
//
//					deptKindTargetData.setAcct_month((String) jsonObj.get("acct_month"));
//
//					deptKindTargetData.setDept_kind_code((String) jsonObj.get("dept_kind_code"));
//
//					deptKindTargetData.setTarget_code((String) jsonObj.get("target_code"));
//
//					deptKindTargetData.setTarget_value(Double.parseDouble(jsonObj.get("target_value").toString()));
//
//					deptKindTargetData.setError_type(err_sb.toString());
//
//					list_err.add(deptKindTargetData);
//				} else {
//
//					s = aphiDeptKindTargetDataService.addDeptKindTargetData(mapVo);
//
//				}
//			}
//
//		} catch (DataAccessException e) {
//
//			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
//
//		}
//
//		if (list_err.size() > 0) {
//
//			return JSONObject.parseObject(JsonListBeanUtil.listToJson(list_err, list_err.size()));
//
//		} else {
//
//			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
//
//		}
//
//	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtargetdata/downTemplateDeptKindTargetData", method = RequestMethod.GET)
	public String downTemplateDeptKindTargetData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\数据准备\\奖金指标数据采集", "科室分类指标数据采集.xls");
		return null;
	}
}
