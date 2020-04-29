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

import org.apache.commons.lang3.StringUtils;
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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiCostitem;
import com.chd.hrp.hpm.entity.AphiCostitemIncreaseData;
import com.chd.hrp.hpm.entity.AphiCostitemPerc;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiCostitemIncreaseDataService;
import com.chd.hrp.hpm.service.AphiCostitemService;

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
public class AphiCostitemIncreaseDataController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiCostitemIncreaseDataController.class);

	@Resource(name = "aphiCostitemIncreaseDataService")
	private final AphiCostitemIncreaseDataService aphiCostitemIncreaseDataService = null;
//
//	@Resource(name = "costitem1_1Service")
//	private final AphiCostitemService costitem1_1Service = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/hpmCostitemIncreaseDataMainPage", method = RequestMethod.GET)
	public String costitemIncreaseDataMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcostitemincreasedata/hpmCostitemIncreaseDataMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/hpmCostitemIncreaseDataAddPage", method = RequestMethod.GET)
	public String costitemIncreaseDataAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcostitemincreasedata/hpmCostitemIncreaseDataAdd";

	}

	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/hpmCostitemIncreaseDataImportPage", method = RequestMethod.GET)
	public String costitemIncreaseDataImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcostitemincreasedata/hpmCostitemIncreaseDataImport";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/addHpmCostitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmCostitemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String costitemIncreaseDataJson = aphiCostitemIncreaseDataService.addCostitemIncreaseData(mapVo);

		return JSONObject.parseObject(costitemIncreaseDataJson);

	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/initHpmCostitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHpmCostitemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String hospTargetDataJson = "";

		hospTargetDataJson = aphiCostitemIncreaseDataService.initCostitemIncreaseData(mapVo);

		return JSONObject.parseObject(hospTargetDataJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/queryHpmCostitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmCostitemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String costitemIncreaseData = aphiCostitemIncreaseDataService.queryCostitemIncreaseData(getPage(mapVo));

		return JSONObject.parseObject(costitemIncreaseData);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/deleteCostitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostitemIncreaseData(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String costitemIncreaseDataJson = aphiCostitemIncreaseDataService.deleteCostitemIncreaseData(mapVo, checkIds);

		return JSONObject.parseObject(costitemIncreaseDataJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/hpmCostitemIncreaseDataUpdatePage", method = RequestMethod.GET)
	public String costitemIncreaseDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		AphiCostitemIncreaseData costitemIncreaseData = new AphiCostitemIncreaseData();

		costitemIncreaseData = aphiCostitemIncreaseDataService.queryCostitemIncreaseDataByCode(mapVo);

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", costitemIncreaseData.getCopy_code());

		mode.addAttribute("acct_year", costitemIncreaseData.getAcct_year());

		mode.addAttribute("acct_month", costitemIncreaseData.getAcct_month());

		mode.addAttribute("cost_item_code", costitemIncreaseData.getCost_item_code());

		mode.addAttribute("cost_item_name", costitemIncreaseData.getCost_item_name());

		mode.addAttribute("prim_cost", costitemIncreaseData.getPrim_cost());

		mode.addAttribute("prim_cost_ret", costitemIncreaseData.getPrim_cost_ret());

		return "hrp/hpm/hpmcostitemincreasedata/hpmCostitemIncreaseDataUpdate";

	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/updateHpmCostitemIncreaseData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmCostitemIncreaseData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String costitemIncreaseDataJson = aphiCostitemIncreaseDataService.updateCostitemIncreaseData(mapVo);

		return JSONObject.parseObject(costitemIncreaseDataJson);

	}

	// 计算
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/increaseCalculate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> calculate(@RequestParam String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		String result = "";

		double prim_cost_ret = 0;

		for (String ids : checkIds.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String cost_item_code = ids.split(";")[0];

			String acct_year = ids.split(";")[1];

			String acct_month = ids.split(";")[2];

			String prim_cost = ids.split(";")[3];

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

			AphiSchemeConf sc = aphiCostitemIncreaseDataService.getSchemeSeqNo(mapVo);// 通过年月获得scheme_seq_no

			mapVo.put("scheme_seq_no", sc.getScheme_seq_no());

			AphiCostitemPerc costItemConf = aphiCostitemIncreaseDataService.getCostItemPercSeqByCode(mapVo);// 根据科室编码,项目编码和方案序列获得权重

			if (costItemConf != null) {

				prim_cost_ret = Double.parseDouble(prim_cost) * costItemConf.getCost_percent();// 计算prim_cost_retr

			}

			mapVo.put("prim_cost_ret", prim_cost_ret);

			result = aphiCostitemIncreaseDataService.calculate(mapVo);// 将计算结果更新到数据表
		}

		return JSONObject.parseObject(result);
	}

	/**
	 * 导入
	 */
//	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/readCostitemIncreaseDataFiles", method = RequestMethod.POST)
//	public String readCostitemIncreaseDataFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
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
//		mapVo.put("copy_code", COPY_CODE);
//
//		List<AphiCostitemIncreaseData> list2 = new ArrayList<AphiCostitemIncreaseData>();
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
//				AphiCostitemIncreaseData costitemIncreaseData = new AphiCostitemIncreaseData();
//
//				String temp[] = list.get(i);// 行
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					costitemIncreaseData.setAcct_year(temp[0]);
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
//					costitemIncreaseData.setAcct_month(temp[1]);
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
//					costitemIncreaseData.setCost_item_code(temp[2]);
//
//					mapVo.put("cost_item_code", temp[2]);
//
//				} else {
//
//					err_sb.append("支出项目为空  ");
//
//				}
//
//				costitemIncreaseData.setPrim_cost(Double.parseDouble(temp[3]));
//
//				mapVo.put("prim_cost", temp[3]);
//
//				costitemIncreaseData.setPrim_cost_ret(Double.parseDouble(temp[4]));
//
//				mapVo.put("prim_cost_ret", temp[4]);
//
//				AphiCostitemIncreaseData ciid = aphiCostitemIncreaseDataService.queryCostitemIncreaseDataByCode(mapVo);
//
//				if (ciid != null) {
//
//					err_sb.append("数据编码已经存在！ ");
//
//				}
//
//				AphiCostitem ci = costitem1_1Service.queryCostitemByCode1_1(mapVo);
//
//				if (ci == null) {
//
//					err_sb.append("支出项目不存在");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					costitemIncreaseData.setError_type(err_sb.toString());
//
//					list2.add(costitemIncreaseData);
//
//				} else {
//
//					aphiCostitemIncreaseDataService.addCostitemIncreaseData(mapVo);
//
//				}
//
//			}
//
//		} catch (DataAccessException e) {
//			AphiCostitemIncreaseData cd = new AphiCostitemIncreaseData();
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
//		return "/hrp/hpm/hpmcostitemincreasedata/hpmCostitemIncreaseDataImportMessage";
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/addBatchCostitemIncreaseDataDict", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchCostitemIncreaseDataDict(@RequestParam(value = "ParamVo") String paramVo, Model mode, HttpServletResponse response)
//			throws Exception {
//
//		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
//
//		List<AphiCostitemIncreaseData> list_err = new ArrayList<AphiCostitemIncreaseData>();
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
//				AphiCostitemIncreaseData costitemIncreaseData = new AphiCostitemIncreaseData();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				// Set<String> key = jsonObj.keySet();
//
//				if (mapVo.get("group_id") == null) {
//
//					mapVo.put("group_id", SessionManager.getGroupId());
//				}
//
//				if (mapVo.get("hos_id") == null) {
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
//				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));
//
//				mapVo.put("prim_cost", jsonObj.get("prim_cost"));
//
//				mapVo.put("prim_cost_ret", jsonObj.get("prim_cost_ret"));
//
//				AphiCostitemIncreaseData ciid = aphiCostitemIncreaseDataService.queryCostitemIncreaseDataByCode(mapVo);
//
//				if (ciid != null) {
//
//					err_sb.append("数据编码已经存在！ ");
//
//				}
//
//				AphiCostitem ci = costitem1_1Service.queryCostitemByCode1_1(mapVo);
//
//				if (ci == null) {
//
//					err_sb.append("支出项目不存在");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					costitemIncreaseData.setAcct_year((String) jsonObj.get("acct_year"));
//
//					costitemIncreaseData.setAcct_month((String) jsonObj.get("acct_month"));
//
//					costitemIncreaseData.setCost_item_code((String) jsonObj.get("cost_item_code"));
//
//					costitemIncreaseData.setPrim_cost(Double.parseDouble(jsonObj.get("prim_cost").toString()));
//
//					costitemIncreaseData.setPrim_cost_ret(Double.parseDouble(jsonObj.get("prim_cost_ret").toString()));
//
//					costitemIncreaseData.setError_type(err_sb.toString());
//
//					list_err.add(costitemIncreaseData);
//				} else {
//
//					s = aphiCostitemIncreaseDataService.addCostitemIncreaseData(mapVo);
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
	@RequestMapping(value = "/hrp/hpm/hpmcostitemincreasedata/downTemplateCostitemIncreaseData", method = RequestMethod.GET)
	public String downTemplateCostitemIncreaseData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\数据准备\\总额预算计将模式", "全院支出数据准备.xls");
		return null;
	}

}
