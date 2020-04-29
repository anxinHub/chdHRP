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
import com.chd.hrp.hpm.entity.AphiIncomeitemPerc;
import com.chd.hrp.hpm.service.AphiIncomeitemPercService;

/**
 * alfred 收入计提比例
 */

@Controller
public class AphiIncomeitemPercController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiIncomeitemPercController.class);

	@Resource(name = "aphiIncomeitemPercService")
	private final AphiIncomeitemPercService aphiIncomeitemPercService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/hpmIncomeitemPercMainPage", method = RequestMethod.GET)
	public String incomeitemPercMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("nature_code", mapVo.get("nature_code"));
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmincomeitemperc/hpmIncomeitemPercMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/hpmIncomeitemPercAddPage", method = RequestMethod.GET)
	public String incomeitemPercAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemperc/hpmIncomeitemPercAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/addHpmIncomeitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmIncomeitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String incomeitemPercJson = aphiIncomeitemPercService.addIncomeitemPerc(mapVo);

		return JSONObject.parseObject(incomeitemPercJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/queryHpmIncomeitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmIncomeitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String incomeitemPerc = aphiIncomeitemPercService.queryIncomeitemPerc(getPage(mapVo));

		return JSONObject.parseObject(incomeitemPerc);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/deleteHpmIncomeitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmIncomeitemPerc(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map map = new HashMap();

		if (map.get("group_id") == null) {

			map.put("group_id", SessionManager.getGroupId());
		}

		if (map.get("hos_id") == null) {

			map.put("hos_id", SessionManager.getHosId());
		}

		map.put("copy_code", COPY_CODE);

		String incomeitemPercJson = aphiIncomeitemPercService.deleteIncomeitemPerc(map, checkIds);

		return JSONObject.parseObject(incomeitemPercJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/hpmIncomeitemPercUpdatePage", method = RequestMethod.GET)
	public String incomeitemPercUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		AphiIncomeitemPerc incomeitemPerc = aphiIncomeitemPercService.queryIncomeitemPercByCode(mapVo);

		mode.addAttribute("income_item_code", incomeitemPerc.getIncome_item_code());

		mode.addAttribute("income_item_name", incomeitemPerc.getIncome_item_name());

		mode.addAttribute("is_acc", incomeitemPerc.getIs_acc());

		mode.addAttribute("income_percent", incomeitemPerc.getIncome_percent());

		return "hrp/hpm/hpmincomeitemperc/hpmIncomeitemPercUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/updateHpmIncomeitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmIncomeitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String incomeitemPercJson = aphiIncomeitemPercService.updateIncomeitemPerc(mapVo);

		return JSONObject.parseObject(incomeitemPercJson);
	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/createHpmIncomeitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmIncomeitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptBalancePercConfJson = aphiIncomeitemPercService.createIncomeitemPerc(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);
	}

	// 快速添加跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/hpmIncomeitemPercFasePage", method = RequestMethod.GET)
	public String incomeitemPercFasePage(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		mode.addAttribute("checkIds", checkIds);

		return "hrp/hpm/hpmincomeitemperc/hpmIncomeitemPercFast";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/fastHpmIncomeitemPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fastHpmIncomeitemPerc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptBalancePercConfJson = aphiIncomeitemPercService.fastIncomeitemPerc(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);

	}

	// /** 上传处理方法 */
	// @RequestMapping(value =
	// "/hrp/hpm/hpmincomeitemperc/readIncomeitemPercFiles", method =
	// RequestMethod.POST)
	// public String readIncomeitemPercFiles(Plupload plupload,
	// HttpServletRequest request, HttpServletResponse response, Model mode)
	// throws IOException {
	//
	// String COPY_CODE = SessionManager.getCopyCode();
	//
	// List<String[]> list = UploadUtil.readFile(plupload, request, response);
	//
	// List<AphiIncomeitemPerc> errorList = new ArrayList<AphiIncomeitemPerc>();
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
	// try {
	//
	// for (int i = 1; i < list.size(); i++) {
	//
	// AphiIncomeitemPerc incomeitemPerc = new AphiIncomeitemPerc();
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// String temp[] = list.get(i);
	//
	// if (StringUtils.isNotEmpty(temp[0])) {
	//
	// incomeitemPerc.setIncome_item_code(temp[0]);
	//
	// mapVo.put("income_item_code", temp[0]);
	//
	// } else {
	//
	// err_sb.append("收入项目编码不能为空");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[1])) {
	//
	// incomeitemPerc.setIs_acc(Integer.parseInt(temp[1]
	// .toString()));
	//
	// mapVo.put("is_acc", temp[1]);
	//
	// } else {
	//
	// err_sb.append("是否参与核算不能为空");
	//
	// }
	// if (StringUtils.isNotEmpty(temp[2])) {
	//
	// incomeitemPerc.setIncome_percent(Long.parseLong(temp[2]
	// .toString()));
	//
	// mapVo.put("income_percent", temp[2]);
	//
	// } else {
	//
	// err_sb.append("计提比例不能为空");
	//
	// }
	//
	// AphiIncomeItem IncomeItem = aphiIncomeItem3_1Service
	// .queryIncomeItemByCode3_1(mapVo);
	//
	// if (IncomeItem == null) {
	//
	// err_sb.append("收费项目编码不存在");
	// }
	//
	// if (err_sb.toString().length() > 0) {
	//
	// incomeitemPerc.setError_type(err_sb.toString());
	//
	// errorList.add(incomeitemPerc);
	//
	// } else {
	//
	// aphiIncomeitemPercService.addIncomeitemPerc(mapVo);
	//
	// }
	//
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	//
	// AphiIncomeitemPerc incomeitemPerc = new AphiIncomeitemPerc();
	//
	// incomeitemPerc.setError_type("系统导入错误！");
	//
	// errorList.add(incomeitemPerc);
	//
	// response.getWriter().print(
	// JsonListBeanUtil.listToJson(errorList, errorList.size()));
	//
	// return null;
	// }
	//
	// System.out.println(JsonListBeanUtil.listToJson(errorList,
	// errorList.size()));
	//
	// mode.addAttribute("resultsJson",
	// JsonListBeanUtil.listToJson(errorList, errorList.size()));
	//
	// return "/hrp/hpm/hpmincomeitemperc/incomeItemPercImportMessage";
	// }
	//
	// @RequestMapping(value =
	// "/hrp/hpm/hpmincomeitemperc/addBatchIncomeitemPerc", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> addBatchIncomeitemPerc(
	// @RequestParam(value = "ParamVo") String paramVo, Model mode)
	// throws Exception {
	//
	// String COPY_CODE = SessionManager.getCopyCode();
	//
	// List<AphiIncomeitemPerc> errorList = new ArrayList<AphiIncomeitemPerc>();
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
	// JSONArray json = JSONArray.parseArray(paramVo);
	//
	// Iterator it = json.iterator();
	//
	// try {
	//
	// while (it.hasNext()) {
	//
	// AphiIncomeitemPerc incomeitemPerc = new AphiIncomeitemPerc();
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// JSONObject jsonObj = JSONObject.parseObject(it.next()
	// .toString());
	//
	// mapVo.put("income_item_code", jsonObj.get("income_item_code"));
	//
	// mapVo.put("is_acc", jsonObj.get("is_acc"));
	//
	// mapVo.put("income_percent", jsonObj.get("income_percent"));
	//
	// AphiIncomeItem IncomeItem = aphiIncomeItem3_1Service
	// .queryIncomeItemByCode3_1(mapVo);
	//
	// if (IncomeItem == null) {
	//
	// err_sb.append("收费项目编码不存在");
	// }
	// if (err_sb.toString().length() > 0) {
	//
	// incomeitemPerc.setIncome_item_code(jsonObj.get(
	// "income_item_code").toString());
	//
	// incomeitemPerc.setIs_acc(Integer.parseInt(jsonObj.get(
	// "is_acc").toString()));
	//
	// incomeitemPerc.setIncome_percent(Long.parseLong(jsonObj
	// .get("income_percent").toString()));
	//
	// incomeitemPerc.setError_type(err_sb.toString());
	//
	// errorList.add(incomeitemPerc);
	//
	// } else {
	//
	// aphiIncomeitemPercService.addIncomeitemPerc(mapVo);
	//
	// }
	//
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// return JSONObject
	// .parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
	// }
	// if (errorList.size() > 0) {
	//
	// return JSONObject.parseObject(JsonListBeanUtil.listToJson(
	// errorList, errorList.size()));
	//
	// } else {
	//
	// return JSONObject
	// .parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
	//
	// }
	//
	// }
	
	//导入
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/hpmIncomeItemPercImportPage", method = RequestMethod.GET)
	public String hpmIncomeitemPercImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemperc/hpmIncomeItemPercImport";
	}
	
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/hpmIncomeItemPercImport", method = RequestMethod.POST)
	@ResponseBody
	public String hpmIncomeitemPercImport(@RequestParam Map<String, Object> mapVo){
		try {
			String impJson = aphiIncomeitemPercService.hpmIncomeitemPercImport(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmincomeitemperc/downTemplateHpmIncomeitemPerc", method = RequestMethod.GET)
	public String downTemplateIncomeitemPerc(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "收入计提比例.xlsx");
		return null;
	}

}
