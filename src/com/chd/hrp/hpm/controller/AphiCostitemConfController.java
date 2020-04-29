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
import com.chd.hrp.hpm.service.AphiCostitemConfService;
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
public class AphiCostitemConfController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiCostitemConfController.class);

	@Resource(name = "aphiCostitemConfService")
	private final AphiCostitemConfService aphiCostitemConfService = null;

	@Resource(name = "aphiCostitemService")
	private final AphiCostitemService aphiCostitemService = null;

//	@Resource(name = "aphiDeptService")
//	private final AphiDeptService aphiDeptService = null;

	// 维护页面跳转
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfMainPage01",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfMainPage03",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfMainPage04"
							}, method = RequestMethod.GET)
	public String hpmCostitemConfMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmcostitemconf/hpmCostitemConfMain";

	}

	// 添加页面
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfAddPage01",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfAddPage03",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfAddPage04"
							}, method = RequestMethod.GET)
	public String hpmCostitemConfAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmcostitemconf/hpmCostitemConfAdd";

	}

	// 保存
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/addHpmCostitemConf01",
							"/hrp/hpm/hpmcostitemconf/addHpmCostitemConf03",
							"/hrp/hpm/hpmcostitemconf/addHpmCostitemConf04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmCostitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costitemConfJson = aphiCostitemConfService.addCostitemConf(mapVo);

		return JSONObject.parseObject(costitemConfJson);

	}

	// 查询
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/queryHpmCostitemConf01",
							"/hrp/hpm/hpmcostitemconf/queryHpmCostitemConf03",
							"/hrp/hpm/hpmcostitemconf/queryHpmCostitemConf04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmCostitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costitemConf = aphiCostitemConfService.queryCostitemConf(getPage(mapVo));

		return JSONObject.parseObject(costitemConf);

	}

	// 删除
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/deleteHpmCostitemConf01",
							"/hrp/hpm/hpmcostitemconf/deleteHpmCostitemConf03",
							"/hrp/hpm/hpmcostitemconf/deleteHpmCostitemConf04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostitemConf(@RequestParam String checkIds, Model mode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		String costitemConfJson = aphiCostitemConfService.deleteCostitemConf(map, checkIds);

		return JSONObject.parseObject(costitemConfJson);

	}

	// 修改页面跳转
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfUpdatePage01",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfUpdatePage03",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfUpdatePage04"
							}, method = RequestMethod.GET)
	public String costitemConfUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("user_id", SessionManager.getUserId());
		
		AphiCostitemConf costitemConf = new AphiCostitemConf();

		costitemConf = aphiCostitemConfService.queryCostitemConfByCode(mapVo);

		// mode.addAttribute("group_id", costitemConf.getGroupId());
		// mode.addAttribute("hos_id", costitemConf.getHosId());
		mode.addAttribute("copy_code", costitemConf.getCopy_code());

		mode.addAttribute("dept_id", costitemConf.getDept_id());
		mode.addAttribute("dept_no", costitemConf.getDept_no());

		mode.addAttribute("cost_item_code", costitemConf.getCost_item_code());

		mode.addAttribute("dept_name", costitemConf.getDept_name());

		mode.addAttribute("cost_iitem_name", costitemConf.getCost_iitem_name());

		mode.addAttribute("is_acc", costitemConf.getIs_acc());

		mode.addAttribute("is_prim_cost", costitemConf.getIs_prim_cost());

		mode.addAttribute("is_calc_cost", costitemConf.getIs_calc_cost());

		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmcostitemconf/hpmCostitemConfUpdate";
	}

	// 修改保存
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/updateHpmCostitemConf01",
							"/hrp/hpm/hpmcostitemconf/updateHpmCostitemConf03",
							"/hrp/hpm/hpmcostitemconf/updateHpmCostitemConf04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmCostitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costitemConfJson = aphiCostitemConfService.updateCostitemConf(mapVo);

		return JSONObject.parseObject(costitemConfJson);
	}

	// 生成
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/hpmCreateHpmCostitemConf01",
							"/hrp/hpm/hpmcostitemconf/hpmCreateHpmCostitemConf03",
							"/hrp/hpm/hpmcostitemconf/hpmCreateHpmCostitemConf04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createHpmCostitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemConfJson = aphiCostitemConfService.createCostitemConf(mapVo);

		return JSONObject.parseObject(incomeitemConfJson);

	}

	// 复制页面
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfCopy01",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfCopy03",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfCopy04"
							}, method = RequestMethod.GET)
	public String hpmCostitemConfCopy(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
		return "hrp/hpm/hpmcostitemconf/hpmCostitemConfCopy";

	}

	// 复制页面保存
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/hpmCopyHpmCostitemConf01",
							"/hrp/hpm/hpmcostitemconf/hpmCopyHpmCostitemConf03",
							"/hrp/hpm/hpmcostitemconf/hpmCopyHpmCostitemConf04"
							}, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyHpmCostitemConf(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemConfJson = aphiCostitemConfService.copyCostitemConf(mapVo);

		return JSONObject.parseObject(incomeitemConfJson);

	}

//	/** 上传处理方法 */
//	@RequestMapping(value = "/hrp/hpm/hpmcostitemconf/readCostItemConfFiles", method = RequestMethod.POST)
//	public String readCostItemConfFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		List<String[]> list = UploadUtil.readFile(plupload, request, response);
//
//		List<AphiCostitemConf> errorList = new ArrayList<AphiCostitemConf>();
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
//				AphiCostitemConf costitemConf = new AphiCostitemConf();
//
//				StringBuffer err_sb = new StringBuffer();
//
//				String temp[] = list.get(i);
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					costitemConf.setDept_code(temp[0]);
//
//					mapVo.put("dept_code", temp[0]);
//
//				} else {
//
//					err_sb.append("科室编码不能为空!");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[1])) {
//
//					costitemConf.setCost_item_code(temp[1]);
//
//					mapVo.put("cost_item_code", temp[1]);
//
//				} else {
//
//					err_sb.append("支出项目编码不能为空！");
//				}
//
//				if (StringUtils.isNotEmpty(temp[2])) {
//
//					costitemConf.setIs_acc(Integer.parseInt(temp[2]));
//
//					mapVo.put("is_acc", temp[2]);
//
//				} else {
//
//					err_sb.append("是否参与核算不能为空！");
//				}
//
//				if (StringUtils.isNotEmpty(temp[3])) {
//
//					costitemConf.setIs_prim_cost(Integer.parseInt(temp[3]));
//
//					mapVo.put("is_prim_cost", temp[3]);
//
//				} else {
//
//					err_sb.append("直接成本不能为空！");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[4])) {
//
//					costitemConf.setIs_calc_cost(Integer.parseInt(temp[4]));
//
//				} else {
//
//					err_sb.append("间接成本不能为空！");
//
//				}
//
//				AphiCostitem Costitem = aphiCostitemService.queryCostitemByCode(mapVo);
//
//				if (Costitem == null) {
//
//					err_sb.append("支出项目编码不存在！");
//				}
//
//				AphiDept dept = aphiDeptService.queryDeptByCode(mapVo);
//
//				if (dept == null) {
//
//					err_sb.append("科室编码编码不存在！");
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					costitemConf.setError_type(err_sb.toString());
//
//					errorList.add(costitemConf);
//
//				} else {
//
//					aphiCostitemConfService.addCostitemConf(mapVo);
//				}
//
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			AphiCostitemConf costitemConf = new AphiCostitemConf();
//
//			costitemConf.setError_type("系统导入出错！");
//
//			errorList.add(costitemConf);
//
//			response.getWriter().print(JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//			return null;
//
//		}
//		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(errorList, errorList.size()));
//
//		return "/hrp/hpm/hpmcostitemconf/hpmCostItemConfImportMessage";
//
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmcostitemconf/addBatchCostItemconf", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchCostItemconf(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
//		JSONArray json = JSONArray.parseArray(paramVo);
//
//		List<AphiCostitemConf> errorList = new ArrayList<AphiCostitemConf>();
//
//		Iterator it = json.iterator();
//
//		try {
//
//			while (it.hasNext()) {
//
//				AphiCostitemConf costitemConf = new AphiCostitemConf();
//
//				StringBuffer err_sb = new StringBuffer();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				mapVo.put("dept_code", jsonObj.get("dept_code"));
//
//				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));
//
//				mapVo.put("is_acc", jsonObj.get("is_acc"));
//
//				mapVo.put("is_prim_cost", jsonObj.get("is_prim_cost"));
//
//				mapVo.put("is_calc_cost", jsonObj.get("is_calc_cost"));
//
//				AphiCostitem Costitem = aphiCostitemService.queryCostitemByCode(mapVo);
//
//				if (Costitem == null) {
//
//					err_sb.append("支出项目编码不存在！");
//				}
//
//				AphiDept dept = aphiDeptService.queryDeptByCode(mapVo);
//
//				if (dept == null) {
//
//					err_sb.append("科室编码编码不存在！");
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					costitemConf.setDept_code(jsonObj.get("dept_code").toString());
//
//					costitemConf.setCost_item_code(jsonObj.get("cost_item_code").toString());
//
//					costitemConf.setIs_acc(Integer.parseInt(jsonObj.get("is_acc").toString()));
//
//					costitemConf.setIs_prim_cost(Integer.parseInt(jsonObj.get("is_prim_cost").toString()));
//
//					costitemConf.setIs_calc_cost(Integer.parseInt(jsonObj.get("is_calc_cost").toString()));
//
//					costitemConf.setError_type(err_sb.toString());
//
//					errorList.add(costitemConf);
//
//				} else {
//
//					aphiCostitemConfService.addCostitemConf(mapVo);
//				}
//
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
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
	//导入
	@RequestMapping(value = {
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfImportPage01",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfImportPage03",
							"/hrp/hpm/hpmcostitemconf/hpmCostitemConfImportPage04"
							}, method = RequestMethod.GET)
	public String hpmCostitemConfImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			mode.addAttribute("app_mod_code", mapVo.get("app_mod_code"));
			return "hrp/hpm/hpmcostitemconf/hpmCostItemConfImport";
	}
	
	//导入跳转
	@RequestMapping(value = {
			"/hrp/hpm/hpmcostitemconf/hpmCostitemConfImport01",
			"/hrp/hpm/hpmcostitemconf/hpmCostitemConfImport03",
			"/hrp/hpm/hpmcostitemconf/hpmCostitemConfImport04"
			}, method = RequestMethod.POST)
	@ResponseBody
	public String hpmCostitemConfImport(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			
			try {
				String impJson = aphiCostitemConfService.hpmCostitemConfImport(mapVo);
				return impJson;
			} catch (Exception e) {
				return "{\"error\":\""+e.getMessage()+"\"}";
			}
			
	}
	

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmcostitemconf/downTemplateHpmCostitemConf", method = RequestMethod.GET)
	public String downTemplateCostitemConf(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\downTemplate", "支出项目配置.xlsx");

		return null;

	}

}
