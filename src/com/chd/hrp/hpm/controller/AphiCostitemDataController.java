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
import com.chd.hrp.hpm.entity.AphiCostitemConf;
import com.chd.hrp.hpm.entity.AphiCostitemData;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiCostitemDataService;

/**
 * 
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiCostitemDataController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiCostitemDataController.class);

	@Resource(name = "aphiCostitemDataService")
	private final AphiCostitemDataService aphiCostitemDataService = null;

	// @Resource(name = "aphiDeptService")
	// private final AphiDeptService aphiDeptService = null;
	//
	// @Resource(name = "aphiCostitem1_1Service")
	// private final AphiCostitemService aphiCostitem1_1Service = null;

	// 维护页面跳转
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/hpmCostitemDataMainPage02",
			"/hrp/hpm/hpmcostitemdata/hpmCostitemDataMainPage04" }, method = RequestMethod.GET)
	public String costitemDataMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));

		return "hrp/hpm/hpmcostitemdata/hpmCostitemDataMain";

	}

	// 添加页面
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/hpmCostitemDataAddPage02",
			"/hrp/hpm/hpmcostitemdata/hpmCostitemDataAddPage04" }, method = RequestMethod.GET)
	public String costitemDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcostitemdata/hpmCostitemDataAdd";

	}

	// 生成页面
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/hpmCostitemDataChoosePage02",
			"/hrp/hpm/hpmcostitemdata/hpmCostitemDataChoosePage04" }, method = RequestMethod.GET)
	public String hpmCostitemDataChoosePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("checkIds", mapVo.get("checkIds"));
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));

		return "hrp/hpm/hpmcostitemdata/hpmCostitemDataChoose";

	}

	// 保存
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/addHpmCostitemData02",
			"/hrp/hpm/hpmcostitemdata/addHpmCostitemData04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String costitemDataJson = aphiCostitemDataService.addCostitemData(mapVo);

		return JSONObject.parseObject(costitemDataJson);

	}

	// 生成
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/initHpmCostitemData02",
			"/hrp/hpm/hpmcostitemdata/initHpmCostitemData04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initCostitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		mapVo.put("user_id", SessionManager.getUserId());

		String hospTargetDataJson = aphiCostitemDataService.initCostitemData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	// 查询
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/queryHpmCostitemData02",
			"/hrp/hpm/hpmcostitemdata/queryHpmCostitemData04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostitemData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		mapVo.put("user_id", SessionManager.getUserId());

		String costitemData = aphiCostitemDataService.queryCostitemData(getPage(mapVo));

		return JSONObject.parseObject(costitemData);

	}

	// 删除
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/deleteHpmCostitemData02",
			"/hrp/hpm/hpmcostitemdata/deleteHpmCostitemData04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostitemData(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String costitemDataJson = aphiCostitemDataService.deleteCostitemDataCodes(mapVo, checkIds);

		return JSONObject.parseObject(costitemDataJson);
	}

	// 修改页面跳转
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/hpmCostitemDataUpdatePage02",
			"/hrp/hpm/hpmcostitemdata/hpmCostitemDataUpdatePage04" }, method = RequestMethod.GET)
	public String costitemDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);
		mapVo.put("user_id", SessionManager.getUserId());
		
		AphiCostitemData costitemData = new AphiCostitemData();

		costitemData = aphiCostitemDataService.queryCostitemDataByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", costitemData.getCopy_code());

		mode.addAttribute("acct_year", costitemData.getAcct_year());

		mode.addAttribute("acct_month", costitemData.getAcct_month());

		mode.addAttribute("dept_id", costitemData.getDept_id());
		mode.addAttribute("dept_no", costitemData.getDept_no());

		mode.addAttribute("cost_item_code", costitemData.getCost_item_code());

		mode.addAttribute("dept_name", costitemData.getDept_name());

		mode.addAttribute("cost_item_name", costitemData.getCost_iitem_name());

		mode.addAttribute("prim_cost", costitemData.getPrim_cost());

		mode.addAttribute("prim_cost_ret", costitemData.getPrim_cost_ret());

		mode.addAttribute("calc_cost", costitemData.getCalc_cost());

		mode.addAttribute("calc_cost_ret", costitemData.getCalc_cost_ret());

		mode.addAttribute("cost_tot_ret", costitemData.getCost_tot_ret());

		return "hrp/hpm/hpmcostitemdata/hpmCostitemDataUpdate";
	}

	// 修改保存
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/updateHpmCostitemData02",
			"/hrp/hpm/hpmcostitemdata/updateHpmCostitemData04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostitemData(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String costitemDataJson = aphiCostitemDataService.updateCostitemData(mapVo);

		return JSONObject.parseObject(costitemDataJson);
	}

	// 计算
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/calculate02",
			"/hrp/hpm/hpmcostitemdata/calculate04" }, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> calculate(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		String result = "";

		long calc_cost_ret = 0;

		long prim_cost_ret = 0;

		long cost_tot_ret = 0;

		for (String ids : checkIds.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String dept_id = ids.split(";")[0];
			String dept_no = ids.split(";")[1];
			String cost_item_code = ids.split(";")[2];

			String acct_year = ids.split(";")[3];

			String acct_month = ids.split(";")[4];

			String prim_cost = ids.split(";")[5];

			String calc_cost = ids.split(";")[6];

			mapVo.put("dept_id", dept_id);
			mapVo.put("dept_no", dept_no);
			mapVo.put("cost_item_code", cost_item_code);

			mapVo.put("acct_year", acct_year);

			mapVo.put("acct_month", acct_month);

			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("copy_code", COPY_CODE);

			AphiSchemeConf sc = aphiCostitemDataService.getSchemeSeqNo(mapVo);// 通过年月获得scheme_seq_no

			mapVo.put("scheme_seq_no", sc.getScheme_seq_no());

			AphiCostitemConf costItemConf = aphiCostitemDataService.getCostItemConfByCode(mapVo);// 根据科室编码,项目编码和方案序列获得权重

			if (costItemConf != null) {

				calc_cost_ret = Long.parseLong(calc_cost) * costItemConf.getIs_calc_cost();// 计算calc_cost_ret

				prim_cost_ret = Long.parseLong(prim_cost) * costItemConf.getIs_prim_cost();// 计算prim_cost_ret

				cost_tot_ret = calc_cost_ret + prim_cost_ret;// 计算cost_tot_ret
			}

			mapVo.put("calc_cost_ret", calc_cost_ret);

			mapVo.put("prim_cost_ret", prim_cost_ret);

			mapVo.put("cost_tot_ret", cost_tot_ret);

			result = aphiCostitemDataService.calculate(mapVo);// 将计算结果更新到数据表
		}

		return JSONObject.parseObject(result);
	}

	// 导入页面
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/hpmCostitemDataImportPage02",
			"/hrp/hpm/hpmcostitemdata/hpmCostitemDataImportPage04" }, method = RequestMethod.GET)
	public String costitemDataImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmcostitemdata/hpmCostitemDataImport";

	}

	// 导入页面跳转
	@RequestMapping(value = { "/hrp/hpm/hpmcostitemdata/hpmCostitemDataImport02",
			"/hrp/hpm/hpmcostitemdata/hpmCostitemDataImport04" }, method = RequestMethod.POST)
	@ResponseBody
	public String hpmcostitemDataImport(@RequestParam Map<String, Object> mapVo) {
		try {
			String impJosn = aphiCostitemDataService.hpmcostitemDataImport(mapVo);
			return impJosn;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	// /**
	// * 导入
	// */
	// @RequestMapping(value = "/hrp/hpm/hpmcostitemdata/readCostItemFiles", method
	// =
	// RequestMethod.POST)
	// public String readCostItemFiles(Plupload plupload,
	// HttpServletRequest request, HttpServletResponse response, Model mode)
	// throws IOException {
	//
	// String COPY_CODE = SessionManager.getCopyCode();
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// if(mapVo.get("group_id") == null){
	//
	// mapVo.put("group_id", SessionManager.getGroupId());
	// }
	//
	// if(mapVo.get("hos_id") == null){
	//
	// mapVo.put("hos_id", SessionManager.getHosId());
	// }
	//
	// mapVo.put("copy_code", COPY_CODE);
	//
	//
	// List<AphiCostitemData> list2 = new ArrayList<AphiCostitemData>();
	//
	// List<String[]> list = UploadUtil.readFile(plupload, request, response);
	//
	// try {
	//
	// for (int i = 1; i < list.size(); i++) {
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// AphiCostitemData costitemData = new AphiCostitemData();
	//
	// String temp[] = list.get(i);// 行
	//
	// if (StringUtils.isNotEmpty(temp[0])) {
	//
	// costitemData.setAcct_year(temp[0]);
	//
	// mapVo.put("acct_year", temp[0]);
	//
	// } else {
	//
	// err_sb.append("核算年度为空 ");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[1])) {
	//
	// costitemData.setAcct_month(temp[1]);
	//
	// mapVo.put("acct_month", temp[1]);
	//
	// } else {
	//
	// err_sb.append("核算月份为空 ");
	//
	// }
	// if (StringUtils.isNotEmpty(temp[2])) {
	//
	// costitemData.setDept_code(temp[2]);
	//
	// mapVo.put("dept_code", temp[2]);
	//
	// } else {
	//
	// err_sb.append("科室为空 ");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[3])) {
	//
	// costitemData.setCost_item_code(temp[3]);
	//
	// mapVo.put("cost_item_code", temp[3]);
	//
	// } else {
	//
	// err_sb.append("支出项目为空 ");
	//
	// }
	//
	// AphiCostitemData cid
	// =aphiCostitemDataService.queryCostitemDataByCode(mapVo);
	//
	// if (cid != null) {
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// AphiDept dept =aphiDeptService.queryDeptByCode(mapVo);
	//
	// if (dept == null) {
	//
	// err_sb.append("科室不存在");
	//
	// }
	//
	// AphiCostitem ci = aphiCostitem1_1Service.queryCostitemByCode1_1(mapVo);
	//
	// if (ci == null) {
	//
	// err_sb.append("支出项目不存在");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// costitemData.setError_type(err_sb.toString());
	//
	// list2.add(costitemData);
	//
	// } else {
	// String chargeItemDictJson =
	// aphiCostitemDataService.addCostitemData(mapVo);
	//
	// }
	//
	// }
	//
	// } catch (DataAccessException e) {
	// AphiCostitemData dkd = new AphiCostitemData();
	//
	// dkd.setError_type("导入系统出错");
	//
	// list2.add(dkd);
	//
	// response.getWriter().print(
	// JsonListBeanUtil.listToJson(list2, list2.size()));
	//
	// return null;
	// }
	//
	// mode.addAttribute("resultsJson",
	// JsonListBeanUtil.listToJson(list2, list2.size()));
	//
	// return "/hrp/hpm/hpmcostitemdata/hpmCostitemDataImportMessage";
	//
	// }
	//
	// @RequestMapping(value = "/hrp/hpm/hpmcostitemdata/addBatchCostitemDataDict",
	// method
	// = RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> addBatchCostitemDataDict(@RequestParam(value =
	// "ParamVo") String paramVo, Model mode, HttpServletResponse response)
	// throws Exception {
	//
	// List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
	//
	// List<AphiCostitemData> list_err = new ArrayList<AphiCostitemData>();
	//
	// JSONArray json = JSONArray.parseArray(paramVo);
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// String s = null;
	//
	// Iterator it = json.iterator();
	//
	// try {
	//
	// while (it.hasNext()) {
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// AphiCostitemData costitemData = new AphiCostitemData();
	//
	// JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
	//
	// // Set<String> key = jsonObj.keySet();
	//
	// if(mapVo.get("group_id") == null){
	//
	// mapVo.put("group_id", SessionManager.getGroupId());
	// }
	//
	// if(mapVo.get("hos_id") == null){
	//
	// mapVo.put("hos_id", SessionManager.getHosId());
	// }
	//
	// mapVo.put("copy_code", SessionManager.getCopyCode());
	//
	// mapVo.put("acct_year",jsonObj.get("acct_year"));
	//
	// mapVo.put("acct_month",jsonObj.get("acct_month"));
	//
	// mapVo.put("dept_code", jsonObj.get("dept_code"));
	//
	// mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));
	//
	// mapVo.put("prim_cost",jsonObj.get("prim_cost"));
	//
	// mapVo.put("prim_cost_ret",jsonObj.get("prim_cost_ret"));
	//
	// mapVo.put("calc_cost", jsonObj.get("calc_cost"));
	//
	// mapVo.put("calc_cost_ret", jsonObj.get("calc_cost_ret"));
	//
	// mapVo.put("cost_tot_ret", jsonObj.get("cost_tot_ret"));
	//
	// AphiCostitemData cid
	// =aphiCostitemDataService.queryCostitemDataByCode(mapVo);
	//
	// if (cid != null) {
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// AphiDept dept =aphiDeptService.queryDeptByCode(mapVo);
	//
	// if (dept == null) {
	//
	// err_sb.append("科室不存在");
	//
	// }
	//
	// AphiCostitem ci = aphiCostitem1_1Service.queryCostitemByCode1_1(mapVo);
	//
	// if (ci == null) {
	//
	// err_sb.append("支出项目不存在");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// costitemData.setAcct_year((String)jsonObj.get("acct_year"));
	//
	// costitemData.setAcct_month((String)jsonObj.get("acct_month"));
	//
	// costitemData.setDept_code((String)jsonObj.get("dept_code"));
	//
	// costitemData.setCost_item_code((String)jsonObj.get("cost_item_code"));
	//
	// costitemData.setPrim_cost((Double)jsonObj.get("prim_cost"));
	//
	// costitemData.setPrim_cost_ret((Double)jsonObj.get("prim_cost_ret"));
	//
	// costitemData.setCalc_cost((Double)jsonObj.get("calc_cost"));
	//
	// costitemData.setCalc_cost_ret((Double)jsonObj.get("calc_cost_ret"));
	//
	// costitemData.setCost_tot_ret((Double)jsonObj.get("cost_tot_ret"));
	//
	// costitemData.setError_type(err_sb.toString());
	//
	// list_err.add(costitemData);
	// } else {
	//
	// s = aphiCostitemDataService.addCostitemData(mapVo);
	//
	// }
	// }
	//
	// }
	// catch (DataAccessException e) {
	//
	// return
	// JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
	//
	// }
	//
	// if(list_err.size()>0){
	//
	// return JSONObject.parseObject(JsonListBeanUtil.listToJson(list_err,
	// list_err.size()));
	//
	// }else{
	//
	// return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
	//
	// }
	//
	//
	// }

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmcostitemdata/downTemplateHpmCostitemData", method = RequestMethod.GET)
	public String downTemplateCostitemData(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		printTemplate(request, response, "hpm\\downTemplate", "支出项目数据准备.xlsx");

		return null;

	}

}
