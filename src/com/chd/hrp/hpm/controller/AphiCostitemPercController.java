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
import com.chd.hrp.hpm.entity.AphiCostitemPerc;
import com.chd.hrp.hpm.service.AphiCostitemPercService;

/**
 * alfred 支出计提比例
 */

@Controller
public class AphiCostitemPercController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiCostitemPercController.class);

	@Resource(name = "aphiCostitemPercService")
	private final AphiCostitemPercService aphiCostitemPercService = null;

	// @Resource(name = "aphiCostitem1_1Service")
	// private final AphiCostitemService aphiCostitem1_1Service = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/hpmCostitemPercMainPage", method = RequestMethod.GET)
	public String costitemPercMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcostitemperc/hpmCostitemPercMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/hpmCostitemPercAddPage", method = RequestMethod.GET)
	public String costitemPercAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcostitemperc/hpmCostitemPercAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/addHpmCostitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSpendingCostitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String costitemPercJson = aphiCostitemPercService.addCostitemPerc(mapVo);

		return JSONObject.parseObject(costitemPercJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/queryHpmCostitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySpendingCostitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String costitemPerc = aphiCostitemPercService.queryCostitemPerc(getPage(mapVo));

		return JSONObject.parseObject(costitemPerc);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/deleteHpmCostitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSpendingCostitemPerc(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map map = new HashMap();

		if (map.get("group_id") == null) {

			map.put("group_id", SessionManager.getGroupId());
		}

		if (map.get("hos_id") == null) {

			map.put("hos_id", SessionManager.getHosId());
		}

		map.put("copy_code", COPY_CODE);

		String costitemPercJson = aphiCostitemPercService.deleteCostitemPerc(map, checkIds);

		return JSONObject.parseObject(costitemPercJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/hpmCostitemPercUpdatePage", method = RequestMethod.GET)
	public String costitemPercUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		AphiCostitemPerc costitemPerc = aphiCostitemPercService.queryCostitemPercByCode(mapVo);

		mode.addAttribute("cost_item_code", costitemPerc.getCost_item_code());

		mode.addAttribute("cost_item_name", costitemPerc.getCost_item_name());

		mode.addAttribute("is_acc", costitemPerc.getIs_acc());

		mode.addAttribute("cost_percent", costitemPerc.getCost_percent());

		return "/hrp/hpm/hpmcostitemperc/hpmCostitemPercUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/updateHpmCostitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSpendingCostitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String costitemPercJson = aphiCostitemPercService.updateCostitemPerc(mapVo);

		return JSONObject.parseObject(costitemPercJson);
	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/createHpmCostitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createSpendingCostitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptBalancePercConfJson = aphiCostitemPercService.createCostitemPerc(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);
	}

	// 快速添加跳转
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/hpmCostitemPercFastPage", method = RequestMethod.GET)
	public String spendingCostitemPercFastPage(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		mode.addAttribute("checkIds", checkIds);

		return "hrp/hpm/hpmcostitemperc/hpmCostitemPercFast";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/fastHpmCostitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fastSpendingCostitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptBalancePercConfJson = aphiCostitemPercService.fastCostitemPerc(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);

	}

	// /** 上传处理方法 */
	// @RequestMapping(value =
	// "/hrp/hpm/hpmcostitemperc/readSpendingCostitemPercFiles", method =
	// RequestMethod.POST)
	// public String readSpendingCostitemPercFiles(Plupload plupload,
	// HttpServletRequest request, HttpServletResponse response, Model mode)
	// throws IOException {
	//
	// String COPY_CODE = SessionManager.getCopyCode();
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// if (mapVo.get("group_id") == null) {
	//
	// mapVo.put("group_id", SessionManager.getGroupId());
	// }
	//
	// if (mapVo.get("hos_id") == null) {
	//
	// mapVo.put("hos_id", SessionManager.getHosId());
	// }
	//
	// mapVo.put("copy_code", COPY_CODE);
	//
	// List<AphiCostitemPerc> list2 = new ArrayList<AphiCostitemPerc>();
	//
	// List<String[]> list = UploadUtil.readFile(plupload, request, response);
	//
	// // List<Item> errorList = new ArrayList<Item>();
	//
	// try {
	//
	// for (int i = 1; i < list.size(); i++) {
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// AphiCostitemPerc costitemPerc = new AphiCostitemPerc();
	//
	// String temp[] = list.get(i);// 行
	//
	// if (temp[2].length() == 1) {
	//
	// temp[2] = "0" + temp[2];
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[0])) {
	//
	// costitemPerc.setCost_item_code(temp[0]);
	//
	// mapVo.put("cost_item_code", temp[0]);
	//
	// } else {
	//
	// err_sb.append("支出项目为空  ");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[1])) {
	//
	// costitemPerc.setIs_acc(Integer.parseInt(temp[1]));
	//
	// mapVo.put("is_acc", temp[1]);
	//
	// } else {
	//
	// err_sb.append("是否参与核算为空  ");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[2])) {
	//
	// costitemPerc.setCost_percent(Double.parseDouble(temp[2]));
	//
	// mapVo.put("cost_percent", temp[2]);
	//
	// } else {
	//
	// err_sb.append("计提比例为空  ");
	//
	// }
	//
	// AphiCostitemPerc cip =
	// aphiCostitemPercService.queryCostitemPercByCode(mapVo);
	//
	// if (cip != null) {
	//
	// err_sb.append("数据编码已经存在！ ");
	//
	// }
	//
	// AphiCostitem ci = aphiCostitem1_1Service.queryCostitemByCode1_1(mapVo);
	//
	// if (ci == null) {
	//
	// err_sb.append("支出项目不存在 ");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// costitemPerc.setError_type(err_sb.toString());
	//
	// list2.add(costitemPerc);
	//
	// } else {
	//
	// aphiCostitemPercService.addCostitemPerc(mapVo);
	//
	// }
	//
	// }
	//
	// } catch (DataAccessException e) {
	// AphiCostitemPerc cd = new AphiCostitemPerc();
	//
	// cd.setError_type("导入系统出错");
	//
	// list2.add(cd);
	//
	// response.getWriter().print(JsonListBeanUtil.listToJson(list2,
	// list2.size()));
	//
	// return null;
	// }
	//
	// mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(list2,
	// list2.size()));
	// return "/hrp/hpm/hpmcostitemperc/hpmCostitemPercImportMessage";
	// }
	//
	// @RequestMapping(value =
	// "/hrp/hpm/hpmcostitemperc/addBatchSpendingCostitemPercDict", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object>
	// addBatchSpendingCostitemPercDict(@RequestParam(value = "ParamVo") String
	// paramVo, Model mode, HttpServletResponse response)
	// throws Exception {
	//
	// List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
	//
	// List<AphiCostitemPerc> list_err = new ArrayList<AphiCostitemPerc>();
	//
	// JSONArray json = JSONArray.parseArray(paramVo);
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// String s = null;
	//
	// Iterator it = json.iterator();
	// try {
	// while (it.hasNext()) {
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// AphiCostitemPerc costitemPerc = new AphiCostitemPerc();
	//
	// JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
	//
	// // Set<String> key = jsonObj.keySet();
	//
	// if (mapVo.get("group_id") == null) {
	//
	// mapVo.put("group_id", SessionManager.getGroupId());
	// }
	//
	// if (mapVo.get("hos_id") == null) {
	//
	// mapVo.put("hos_id", SessionManager.getHosId());
	// }
	//
	// mapVo.put("copy_code", SessionManager.getCopyCode());
	//
	// mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));
	//
	// mapVo.put("is_acc", jsonObj.get("is_acc"));
	//
	// mapVo.put("comp_percent", jsonObj.get("comp_percent"));
	//
	// AphiCostitemPerc cip =
	// aphiCostitemPercService.queryCostitemPercByCode(mapVo);
	//
	// if (cip != null) {
	//
	// err_sb.append("数据编码已经存在！ ");
	//
	// }
	//
	// AphiCostitem ci = aphiCostitem1_1Service.queryCostitemByCode1_1(mapVo);
	//
	// if (ci == null) {
	//
	// err_sb.append("支出项目不存在 ");
	//
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// costitemPerc.setGroup_id((Long) jsonObj.get("group_id"));
	//
	// costitemPerc.setHos_id((Long) jsonObj.get("hos_id"));
	//
	// costitemPerc.setCopy_code((String) jsonObj.get("copy_code"));
	//
	// costitemPerc.setCost_item_code((String) jsonObj.get("cost_item_code"));
	//
	// costitemPerc.setIs_acc((Integer) jsonObj.get("is_acc"));
	//
	// costitemPerc.setCost_percent(Double.parseDouble(jsonObj.get("cost_percent").toString()));
	//
	// costitemPerc.setError_type(err_sb.toString());
	//
	// list_err.add(costitemPerc);
	// } else {
	//
	// s = aphiCostitemPercService.addCostitemPerc(mapVo);
	//
	// }
	// }
	//
	// } catch (DataAccessException e) {
	//
	// return
	// JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
	//
	// }
	//
	// if (list_err.size() > 0) {
	//
	// return JSONObject.parseObject(JsonListBeanUtil.listToJson(list_err,
	// list_err.size()));
	//
	// } else {
	//
	// return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
	//
	// }
	//
	// }
	
	//导入
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/hpmCostitemPercImportPage", method = RequestMethod.GET)
	public String hpmCostitemPercImportPage(Model mode) throws Exception {
		
		return "hrp/hpm/hpmcostitemperc/hpmCostitemPercImport";
	}
	
	//导入跳转
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/hpmCostitemPercImport", method = RequestMethod.POST)
	@ResponseBody
	public String hpmCostitemConfImport(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			
		try {
			String impJson = aphiCostitemPercService.hpmCostitemConfImport(mapVo);
			return impJson;
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
			
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmcostitemperc/downTemplateHpmCostitemPerc", method = RequestMethod.GET)
	public String downTemplateSpendingCostitemPerc(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "支出计提比例.xlsx");
		return null;
	}

}
