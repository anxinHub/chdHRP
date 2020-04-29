package com.chd.hrp.hpm.controller;

import java.io.IOException;
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
import com.chd.base.SessionManager;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiItemIncreasePercConf;
import com.chd.hrp.hpm.service.AphiItemIncreasePercConfService;

/**
 * alfred 奖金增量比例维护
 */

@Controller
public class AphiItemIncreasePercConfController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiItemIncreasePercConfController.class);

	@Resource(name = "aphiItemIncreasePercConfService")
	private final AphiItemIncreasePercConfService aphiItemIncreasePercConfService = null;

	// @Resource(name = "aphiItemService")
	// private final AphiItemService aphiItemService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfMainPage", method = RequestMethod.GET)
	public String itemIncreasePercConfMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfAddPage", method = RequestMethod.GET)
	public String itemIncreasePercConfAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasepercconf/addHpmItemIncreasePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmItemIncreasePercConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String itemIncreasePercConfJson = aphiItemIncreasePercConfService.addItemIncreasePercConf(mapVo);

		return JSONObject.parseObject(itemIncreasePercConfJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasepercconf/queryHpmItemIncreasePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmItemIncreasePercConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String itemIncreasePercConf = aphiItemIncreasePercConfService.queryItemIncreasePercConf(getPage(mapVo));

		return JSONObject.parseObject(itemIncreasePercConf);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasepercconf/deleteHpmItemIncreasePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmItemIncreasePercConf(@RequestParam String mapVo, Model mode) throws Exception {

		String itemIncreasePercConfJson = "";

		List<Map<String, Object>> list = JsonListMapUtil.getListMap(mapVo);

		itemIncreasePercConfJson = aphiItemIncreasePercConfService.deleteBatchItemIncreasePercConf(list);

		return JSONObject.parseObject(itemIncreasePercConfJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfUpdatePage", method = RequestMethod.GET)
	public String itemIncreasePercConfUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		AphiItemIncreasePercConf itemIncreasePercConf = new AphiItemIncreasePercConf();

		itemIncreasePercConf = aphiItemIncreasePercConfService.queryItemIncreasePercConfByCode(mapVo);

		mode.addAttribute("item_code", itemIncreasePercConf.getItem_code());

		mode.addAttribute("item_name", itemIncreasePercConf.getItem_name());

		mode.addAttribute("increase_percent", itemIncreasePercConf.getIncrease_percent());

		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasepercconf/updateHpmItemIncreasePercConf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmItemIncreasePercConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String itemIncreasePercConfJson = aphiItemIncreasePercConfService.updateItemIncreasePercConf(mapVo);

		return JSONObject.parseObject(itemIncreasePercConfJson);

	}

	// /** 上传处理方法 */
	// @RequestMapping(value =
	// "/hrp/hpm/hpmitemincreasepercconf/readItemIncreasePercConfFiles", method
	// = RequestMethod.POST)
	// public String readItemIncreasePercConfFiles(Plupload plupload,
	// HttpServletRequest request, HttpServletResponse response, Model mode)
	// throws IOException {
	//
	// String COPY_CODE = SessionManager.getCopyCode();
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// List<String[]> list = UploadUtil.readFile(plupload, request, response);
	//
	// List<AphiItemIncreasePercConf> errorList = new
	// ArrayList<AphiItemIncreasePercConf>();
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
	// AphiItemIncreasePercConf itemIncreasePercConf = new
	// AphiItemIncreasePercConf();
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// String temp[] = list.get(i);
	//
	// if (StringUtils.isNotEmpty(temp[0])) {
	//
	// itemIncreasePercConf.setItem_code(temp[0]);
	//
	// mapVo.put("item_code", temp[0]);
	// } else {
	//
	// err_sb.append("奖金项目编码不能为空！");
	//
	// }
	//
	// if (StringUtils.isNotEmpty(temp[1])) {
	//
	// itemIncreasePercConf.setIncrease_percent(Double.parseDouble(temp[1].toString()));
	//
	// mapVo.put("increase_percent", temp[1]);
	// } else {
	//
	// err_sb.append("增长比例不能为空！");
	//
	// }
	//
	// AphiItemIncreasePercConf ItemIncreasePercConf =
	// aphiItemIncreasePercConfService.queryItemIncreasePercConfByCode(mapVo);
	//
	// if(ItemIncreasePercConf != null){
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// AphiItem Item = aphiItemService.queryItemByCode(mapVo);
	//
	// if(Item ==null){
	//
	// err_sb.append("不存在的奖金项目编码！");
	//
	// }
	//
	//
	// if(err_sb.toString().length() >0){
	//
	// itemIncreasePercConf.setError_type(err_sb.toString());
	//
	// errorList.add(itemIncreasePercConf);
	//
	// }else{
	//
	// aphiItemIncreasePercConfService.addItemIncreasePercConf(mapVo);
	//
	// }
	// }
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	//
	// AphiItemIncreasePercConf ItemIncreasePercConf = new
	// AphiItemIncreasePercConf();
	//
	// ItemIncreasePercConf.setError_type("系统导入错误！");
	//
	// errorList.add(ItemIncreasePercConf);
	//
	// response.getWriter().print(
	// JsonListBeanUtil.listToJson(errorList, errorList.size()));
	//
	// return null;
	// }
	//
	// mode.addAttribute("resultsJson",
	// JsonListBeanUtil.listToJson(errorList, errorList.size()));
	//
	// return
	// "hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfImportMessage";
	// }
	//
	// @RequestMapping(value =
	// "/hrp/hpm/hpmitemincreasepercconf/addBatchItemIncreasePercConf", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public Map<String, Object> addBatchItemIncreasePercConf(
	// @RequestParam(value = "/ParamVo") String paramVo, Model mode)
	// throws Exception {
	//
	// String COPY_CODE = SessionManager.getCopyCode();
	//
	// Map<String, Object> mapVo = new HashMap<String, Object>();
	//
	// List<AphiItemIncreasePercConf> errorList = new
	// ArrayList<AphiItemIncreasePercConf>();
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
	// while(it.hasNext()){
	//
	// AphiItemIncreasePercConf itemIncreasePercConf = new
	// AphiItemIncreasePercConf();
	//
	// StringBuffer err_sb = new StringBuffer();
	//
	// JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
	//
	// mapVo.put("item_code", jsonObj.get("item_code"));
	//
	// mapVo.put("increase_percent", jsonObj.get("increase_percent"));
	//
	// AphiItemIncreasePercConf ItemIncreasePercConf =
	// aphiItemIncreasePercConfService.queryItemIncreasePercConfByCode(mapVo);
	//
	// if(ItemIncreasePercConf != null){
	//
	// err_sb.append("数据编码已经存在！");
	//
	// }
	//
	// AphiItem Item = aphiItemService.queryItemByCode(mapVo);
	//
	// if(Item ==null){
	//
	// err_sb.append("不存在的奖金项目编码！");
	//
	// }
	//
	//
	// if(err_sb.toString().length() >0){
	//
	// itemIncreasePercConf.setItem_code(jsonObj.get("item_code").toString());
	//
	// itemIncreasePercConf.setIncrease_percent(Double.parseDouble(jsonObj.get("increase_percent").toString()));
	//
	// itemIncreasePercConf.setError_type(err_sb.toString());
	//
	// errorList.add(itemIncreasePercConf);
	//
	// }else{
	//
	// aphiItemIncreasePercConfService.addItemIncreasePercConf(mapVo);
	//
	// }
	//
	// }
	//
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// return JSONObject
	// .parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
	// }
	//
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
	//
	// }
	//
	//
	
	//导入
	@RequestMapping(value = "/hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfImportPage", method = RequestMethod.GET)
	public String itemIncreasePercConfImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfImport";
	}

	@RequestMapping(value = "/hrp/hpm/hpmitemincreasepercconf/hpmItemIncreasePercConfImport", method = RequestMethod.POST)
	@ResponseBody
	public String hpmItemIncreasePercConfImport(@RequestParam Map<String, Object> mapVo,Model mode){
		try {
			/*String impJson = aphiItemIncreasePercConfService.hpmItemIncreasePercConfImport(mapVo);
			return impJson;*/
			return "";
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	// 下载导入模板
	@RequestMapping(value = "hrp/hpm/hpmitemincreasepercconf/downTemplateHpmItemIncreasePercConf", method = RequestMethod.GET)
	public String downTemplateItemIncreasePercConf(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "奖金增量比例维护.xls");
		return null;
	}

}
