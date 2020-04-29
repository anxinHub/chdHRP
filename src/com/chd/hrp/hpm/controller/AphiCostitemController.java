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
import com.chd.hrp.hpm.service.AphiCostitemService;

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
public class AphiCostitemController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiCostitemController.class);

	@Resource(name = "aphiCostitemService")
	private final AphiCostitemService aphiCostitemService = null;

	// 维护页面跳转
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitem/hpmCostitemMainPage01",
							"/hrp/hpm/hpmcostitem/hpmCostitemMainPage03",
							"/hrp/hpm/hpmcostitem/hpmCostitemMainPage04"
							}, method = RequestMethod.GET)
	public String hpmCostitemMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmcostitem/hpmCostitemMain";

	}

	// 添加页面
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitem/hpmCostitemAddPage01",
							"/hrp/hpm/hpmcostitem/hpmCostitemAddPage03",
							"/hrp/hpm/hpmcostitem/hpmCostitemAddPage04"
							}, method = RequestMethod.GET)
	public String hpmCostitemAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmcostitem/hpmCostitemAdd";

	}

	// 保存
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitem/addHpmCostitem01",
							"/hrp/hpm/hpmcostitem/addHpmCostitem03",
							"/hrp/hpm/hpmcostitem/addHpmCostitem04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmCostitem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}

		String costitemJson = aphiCostitemService.addCostitem(mapVo);

		return JSONObject.parseObject(costitemJson);

	}

	// 查询
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitem/queryHpmCostitem01",
							"/hrp/hpm/hpmcostitem/queryHpmCostitem03",
							"/hrp/hpm/hpmcostitem/queryHpmCostitem04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmCostitem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}

		String costitem = aphiCostitemService.queryCostitem(getPage(mapVo));

		return JSONObject.parseObject(costitem);

	}

	// 删除
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitem/deleteHpmCostitem01",
							"/hrp/hpm/hpmcostitem/deleteHpmCostitem03",
							"/hrp/hpm/hpmcostitem/deleteHpmCostitem04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostitem(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

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

		String costitemJson = aphiCostitemService.deleteCostitem(map, checkIds);

		return JSONObject.parseObject(costitemJson);
	}

	// 修改页面跳转
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitem/hpmCostitemUpdatePage01",
							"/hrp/hpm/hpmcostitem/hpmCostitemUpdatePage03",
							"/hrp/hpm/hpmcostitem/hpmCostitemUpdatePage04"
							}, method = RequestMethod.GET)
	public String hpmCostitemUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (mapVo.get("copy_code") == null) {
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}

		AphiCostitem costitem = aphiCostitemService.queryCostitemByCode(mapVo);

		// mode.addAttribute("group_id", costitem.getGroupId());
		// mode.addAttribute("hos_id", costitem.getHosId());
		mode.addAttribute("copy_code", costitem.getCopy_code());

		mode.addAttribute("cost_item_code", costitem.getCost_item_code());

		mode.addAttribute("cost_iitem_name", costitem.getCost_iitem_name());

		mode.addAttribute("cost_type_code", costitem.getCost_type_code());

		mode.addAttribute("cost_type_name", costitem.getCost_type_name());

		mode.addAttribute("cost_iitem_name", costitem.getCost_iitem_name());

		mode.addAttribute("spell_code", costitem.getSpell_code());

		mode.addAttribute("wbx_code", costitem.getWbx_code());

		mode.addAttribute("data_source", costitem.getData_source());

		mode.addAttribute("is_stop", costitem.getIs_stop());
		
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "/hrp/hpm/hpmcostitem/hpmCostitemUpdate";
		
	}

	// 修改保存
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitem/updateHpmCostitem01",
							"/hrp/hpm/hpmcostitem/updateHpmCostitem03",
							"/hrp/hpm/hpmcostitem/updateHpmCostitem04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmCostitem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costitemJson = aphiCostitemService.updateCostitem(mapVo);

		return JSONObject.parseObject(costitemJson);
	}

//	/** 上传处理方法 */
//	@RequestMapping(value = "/hrp/hpm/hpmcostitem/readCostItemFiles", method = RequestMethod.POST)
//	public String readCostItemFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		List<String[]> list = UploadUtil.readFile(plupload, request, response);
//
//		List<AphiCostitem> list_err = new ArrayList<AphiCostitem>();
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
//			for (int i = 1; i < list.size(); i++) {
//
//				AphiCostitem costitem = new AphiCostitem();
//
//				StringBuffer err_sb = new StringBuffer();
//
//				String temp[] = list.get(i);
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					mapVo.put("cost_item_code", temp[0]);
//
//					costitem.setCost_item_code(temp[0]);
//
//				} else {
//
//					err_sb.append("支出项目编码不能为空！");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[1])) {
//
//					mapVo.put("cost_iitem_name", temp[1]);
//
//					costitem.setCost_iitem_name(temp[1]);
//
//				} else {
//
//					err_sb.append("支出项目名称不能为空！");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[2])) {
//
//					mapVo.put("cost_type_code", temp[2]);
//
//					costitem.setCost_type_code(temp[2]);
//
//				} else {
//
//					err_sb.append("支出项目分类编码不能为空！");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[3])) {
//
//					mapVo.put("is_stop", temp[3]);
//
//					costitem.setIs_stop(Integer.parseInt(temp[3].toString()));
//
//				} else {
//
//					err_sb.append("是否停用不能为空！");
//
//				}
//
//				AphiCostitem Costitem = aphiCostitemService.queryCostitemByCode(mapVo);
//
//				if (Costitem != null) {
//
//					err_sb.append("数据编码已经存在！");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					costitem.setError_type(err_sb.toString());
//
//					list_err.add(costitem);
//
//				} else {
//
//					aphiCostitemService.addCostitem(mapVo);
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//
//			AphiCostitem costitem = new AphiCostitem();
//
//			costitem.setError_type("导入系统出错");
//
//			list_err.add(costitem);
//
//			response.getWriter().print(JsonListBeanUtil.listToJson(list_err, list_err.size()));
//
//			return null;
//		}
//
//		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(list_err, list_err.size()));
//
//		return "/hrp/hpm/hpmcostitem/costItemImportMessage";
//
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmcostitem/addBatchCostItem", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchCostItem(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
//
//		List<AphiCostitem> list_err = new ArrayList<AphiCostitem>();
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		JSONArray json = JSONArray.parseArray(paramVo);
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
//		Iterator it = json.iterator();
//
//		try {
//
//			while (it.hasNext()) {
//
//				AphiCostitem costitem = new AphiCostitem();
//
//				StringBuffer err_sb = new StringBuffer();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));
//
//				mapVo.put("cost_iitem_name", jsonObj.get("cost_iitem_name"));
//
//				mapVo.put("cost_type_code", jsonObj.get("cost_type_code"));
//
//				mapVo.put("is_stop", jsonObj.get("is_stop"));
//
//				AphiCostitem Costitem = aphiCostitemService.queryCostitemByCode(mapVo);
//
//				if (Costitem != null) {
//
//					err_sb.append("数据编码已经存在！");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					costitem.setCost_item_code(jsonObj.get("cost_item_code").toString());
//
//					costitem.setCost_iitem_name(jsonObj.get("cost_iitem_name").toString());
//
//					costitem.setCost_type_code(jsonObj.get("cost_type_code").toString());
//
//					costitem.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
//
//					costitem.setError_type(err_sb.toString());
//
//					list_err.add(costitem);
//				} else {
//
//					aphiCostitemService.addCostitem(mapVo);
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
	
	//导入
	@RequestMapping(value = {
			"/hrp/hpm/hpmcostitem/hpmCostitemImportPage01",
			"/hrp/hpm/hpmcostitem/hpmCostitemImportPage03",
			"/hrp/hpm/hpmcostitem/hpmCostitemImportPage04"
			}, method = RequestMethod.GET)
	public String costitemImportPage(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmcostitem/hpmCostItemImport";
	}
	
	//导入跳转
	@RequestMapping(value = {
			"/hrp/hpm/hpmcostitem/hpmCostitemImport01",
			"/hrp/hpm/hpmcostitem/hpmCostitemImport03",
			"/hrp/hpm/hpmcostitem/hpmCostitemImport04"
			}, method = RequestMethod.POST)
	@ResponseBody
	public String hpmcostitemImport(@RequestParam Map<String,Object> mapVo){
		try {
			String impJson = aphiCostitemService.hpmcostitemImport(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmcostitem/downTemplateHpmCostitem", method = RequestMethod.GET)
	public String downTemplateCostitem(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "支出项目维护.xlsx");
		return null;
	}

}
