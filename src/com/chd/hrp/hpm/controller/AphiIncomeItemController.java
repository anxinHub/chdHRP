/** 
 * 2015-2-2 
 * author:alfred
 */

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiIncomeItem;
import com.chd.hrp.hpm.service.AphiIncomeItemService;

@Controller
public class AphiIncomeItemController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiIncomeItemController.class);

	@Resource(name = "aphiIncomeItemService")
	private AphiIncomeItemService aphiIncomeItemService = null;

	// 收入项目表维护页面跳转
	@RequestMapping(value = { 
						"hrp/hpm/hpmincomeitem/hpmIncomeItemMainPage01", 
						"hrp/hpm/hpmincomeitem/hpmIncomeItemMainPage03",
						"hrp/hpm/hpmincomeitem/hpmIncomeItemMainPage04"
						}, method = RequestMethod.GET)
	public String hpmIncomeItemMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "/hrp/hpm/hpmincomeitem/hpmIncomeItemMain";

	}

	// 收入项目表添加页面
	@RequestMapping(value = { 
						"hrp/hpm/hpmincomeitem/addHpmIncomeItem01", 
						"hrp/hpm/hpmincomeitem/addHpmIncomeItem03",
						"hrp/hpm/hpmincomeitem/addHpmIncomeItem04"
						}, method = RequestMethod.GET)
	public String addHpmIncomeItemPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "/hrp/hpm/hpmincomeitem/hpmIncomeItemAdd";

	}

	// 保存增加的收入项目
	@RequestMapping(value = { 
						"hrp/hpm/hpmincomeitem/addHpmIncomeItem01", 
						"hrp/hpm/hpmincomeitem/addHpmIncomeItem03", 
						"hrp/hpm/hpmincomeitem/addHpmIncomeItem04"
						}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmIncomeItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}

		String incomeItemJson = aphiIncomeItemService.addIncomeitem(mapVo);

		return JSONObject.parseObject(incomeItemJson);

	}

	// 查询
	@RequestMapping(value = { 
						"hrp/hpm/hpmincomeitem/queryHpmIncomeItem01", 
						"hrp/hpm/hpmincomeitem/queryHpmIncomeItem03",
						"hrp/hpm/hpmincomeitem/queryHpmIncomeItem04"
						}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmIncomeItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}

		String incomeItem = aphiIncomeItemService.queryIncomeitem(getPage(mapVo));

		return JSONObject.parseObject(incomeItem);

	}

	// 删除
	@RequestMapping(value = { 
							"hrp/hpm/hpmincomeitem/deleteHpmIncomeItem01", 
							"hrp/hpm/hpmincomeitem/deleteHpmIncomeItem03",
							"hrp/hpm/hpmincomeitem/deleteHpmIncomeItem04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIncomeItem(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		Map map = new HashMap();

		if (map.get("group_id") == null) {
			
			map.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (map.get("hos_id") == null) {
			
			map.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (map.get("copy_code") == null) {
			
			map.put("copy_code", SessionManager.getCopyCode());
			
		}

		String incomeItemJson = aphiIncomeItemService.deleteIncomeItem(map, checkIds);

		return JSONObject.parseObject(incomeItemJson);
	}

	// 修改页面跳转
	@RequestMapping(value = { 
							"hrp/hpm/hpmincomeitem/hpmIncomeItemUpdatePage01", 
							"hrp/hpm/hpmincomeitem/hpmIncomeItemUpdatePage03",
							"hrp/hpm/hpmincomeitem/hpmIncomeItemUpdatePage04"
							}, method = RequestMethod.GET)
	public String incomeItemUpdate(@RequestParam(value = "income_item_code", required = true) String income_item_code,
			@RequestParam(value = "app_mod_code", required = true) String app_mod_code,
			Model mode) throws Exception {

		AphiIncomeItem incomeItem = new AphiIncomeItem();

		Map<String, Object> map = new HashMap();
		
		map.put("income_item_code", income_item_code);

		if (map.get("group_id") == null) {
			
			map.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (map.get("hos_id") == null) {
			
			map.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (map.get("copy_code") == null) {
			
			map.put("copy_code", SessionManager.getCopyCode());
			
		}

		incomeItem = aphiIncomeItemService.queryIncomeItemByCode(map);

		// -------------------------------------------------------------------------------------------

		mode.addAttribute("income_item_code", incomeItem.getIncome_item_code());

		mode.addAttribute("income_item_name", incomeItem.getIncome_item_name());

		mode.addAttribute("is_stop", incomeItem.getIs_stop());
		mode.addAttribute("app_mod_code", app_mod_code);
		return "/hrp/hpm/hpmincomeitem/hpmIncomeItemUpdate";
	}

	// 修改保存
	@RequestMapping(value = { 
						"hrp/hpm/hpmincomeitem/upateHpmIncomeItem01", 
						"hrp/hpm/hpmincomeitem/upateHpmIncomeItem03",
						"hrp/hpm/hpmincomeitem/upateHpmIncomeItem04"
						}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upateHpmIncomeItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}

		String incomeItemJson = aphiIncomeItemService.updateIncomeItem(mapVo);

		return JSONObject.parseObject(incomeItemJson);
	}

//	/** 上传处理方法 */
//	@RequestMapping(value = "/hpm/income/incomeitem/readFile", method = RequestMethod.POST)
//	public String readFile(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		List<String[]> list = UploadUtil.readFile(plupload, request, response);
//
//		List<AphiIncomeItem> errorList = new ArrayList<AphiIncomeItem>();
//
//		if (mapVo.get("group_id") == null) {
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//		if (mapVo.get("hos_id") == null) {
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//		if (mapVo.get("copy_code") == null) {
//			mapVo.put("copy_code", SessionManager.getCopyCode());
//		}
//
//		try {
//
//			for (int i = 1; i < list.size(); i++) {
//
//				AphiIncomeItem incomeItem = new AphiIncomeItem();
//
//				StringBuffer err_sb = new StringBuffer();
//
//				String temp[] = list.get(i);
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					incomeItem.setIncome_item_code(temp[0]);
//
//					mapVo.put("income_item_code", temp[0]);
//				} else {
//
//					err_sb.append("收入项目编码不能为空！");
//				}
//
//				if (StringUtils.isNotEmpty(temp[1])) {
//
//					incomeItem.setIncome_item_name(temp[1]);
//
//					mapVo.put("income_item_name", temp[1]);
//				} else {
//
//					err_sb.append("收入项目名称不能为空！");
//				}
//
//				if (!temp[2].equals("")) {
//
//					incomeItem.setIs_stop(Integer.parseInt(temp[2]));
//
//					mapVo.put("is_stop", temp[2]);
//
//				} else {
//
//					err_sb.append("是否停用不能为空！");
//				}
//
//				AphiIncomeItem IncomeItem = aphiIncomeItemService.queryIncomeItemByCode(mapVo);
//
//				if (IncomeItem != null) {
//
//					err_sb.append("数据编码已经存在！");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					incomeItem.setError_type(err_sb.toString());
//
//					errorList.add(incomeItem);
//
//				} else {
//
//					aphiIncomeItemService.addIncomeitem(mapVo);
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//
//			AphiIncomeItem IncomeItem = new AphiIncomeItem();
//
//			IncomeItem.setError_type("系统导入出错！");
//
//			errorList.add(IncomeItem);
//
//			response.getWriter().print(JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//			return null;
//
//		}
//
//		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//		return "/hpm/income/incomeitem/incomeItemImportMessage";
//
//	}
//
//	@RequestMapping(value = "/hpm/income/incomeitem/addBatchIncomeItemDict", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchIncomeItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
//
//		List<AphiIncomeItem> list = new ArrayList<AphiIncomeItem>();
//
//		JSONArray json = JSONArray.parseArray(paramVo);
//
//		Iterator it = json.iterator();
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		if (mapVo.get("group_id") == null) {
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//		if (mapVo.get("hos_id") == null) {
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//		if (mapVo.get("copy_code") == null) {
//			mapVo.put("copy_code", SessionManager.getCopyCode());
//		}
//
//		try {
//
//			while (it.hasNext()) {
//
//				AphiIncomeItem incomeItem = new AphiIncomeItem();
//
//				StringBuffer err_sb = new StringBuffer();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				mapVo.put("income_item_code", jsonObj.get("income_item_code"));
//
//				mapVo.put("income_item_name", jsonObj.get("income_item_name"));
//
//				mapVo.put("is_stop", jsonObj.get("is_stop"));
//
//				AphiIncomeItem IncomeItem = aphiIncomeItemService.queryIncomeItemByCode(mapVo);
//
//				if (IncomeItem != null) {
//
//					err_sb.append("数据编码已经存在！");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					incomeItem.setIncome_item_code(jsonObj.get("income_item_code").toString());
//
//					incomeItem.setIncome_item_name(jsonObj.get("income_item_name").toString());
//
//					incomeItem.setIs_stop((Integer) jsonObj.get("is_stop"));
//
//					incomeItem.setError_type(err_sb.toString());
//
//					list.add(incomeItem);
//				} else {
//
//					aphiIncomeItemService.addIncomeitem(mapVo);
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
//		}
//
//		System.out.println(JsonListBeanUtil.listToJson(list, list.size()));
//		if (list.size() > 0) {
//
//			return JSONObject.parseObject(JsonListBeanUtil.listToJson(list, list.size()));
//
//		} else {
//
//			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
//
//		}
//	}
//
	
	//导入
	@RequestMapping(value = { 
						"hrp/hpm/hpmincomeitem/hpmIncomeItemImportPage01", 
						"hrp/hpm/hpmincomeitem/hpmIncomeItemImportPage03",
						"hrp/hpm/hpmincomeitem/hpmIncomeItemImportPage04"
						}, method = RequestMethod.GET)
	public String hpmIncomeItemImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "/hrp/hpm/hpmincomeitem/hpmIncomeItemImport";
	}
	
	//导入跳转
	@RequestMapping(value = { 
			"hrp/hpm/hpmincomeitem/hpmIncomeItemImport01", 
			"hrp/hpm/hpmincomeitem/hpmIncomeItemImport03",
			"hrp/hpm/hpmincomeitem/hpmIncomeItemImport04"
			}, method = RequestMethod.POST)
	@ResponseBody
	public String hpmIncomeItemImport(@RequestParam Map<String, Object> mapVo){
		try {
			String impJosn = aphiIncomeItemService.hpmIncomeItemImport(mapVo);
			return impJosn;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
		
		
	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmincomeitem/downTemplateHpmIncomeItem", method = RequestMethod.GET)
	public String downTemplateIncomeitemData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\downTemplate", "收入项目维护.xlsx");

		return null;
	}
}
