/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.serviceImpl.AccDeptAttrServiceImpl;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.cost.entity.CostRiskDetail;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;

/**
 * @Title. @Description. 科室成本明细数据表_医辅分摊
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostDeptDictController extends BaseController {

	private static Logger logger = Logger
			.getLogger(CostDeptDictController.class);

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

	@Resource(name = "accDeptAttrService")
	private final AccDeptAttrServiceImpl accDeptAttrService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@RequestMapping(value = "/hrp/cost/costdeptdict/costDeptDictMainPage", method = RequestMethod.GET)
	public String costDeptDictMainPage(Model mode) throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		// 添加编码规则判断
		mapVo.put("proj_code", "HOS_DEPT");
		mapVo.put("mod_code", "00");
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		Map<Object, Object> level_map = (Map<Object, Object>) rules
				.get("rules_type_level");
		Map<Object, Object> level_length = (Map<Object, Object>) rules
				.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String rules_view = Strings.removeFirst(rules_v);
		// 获取第一级长度
		int first_length = (Integer) level_length.get(1);
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);

		return "hrp/cost/costdeptdict/costDeptDictMain";

	}

	/**
	 * @Description 批量管理页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptdict/costDeptDictManagePage", method = RequestMethod.GET)
	public String costDeptDictManagePage(Model mode,
			@RequestParam(value = "ParamVo") String paramVo,
			@RequestParam Map<String, Object> mapVo) throws Exception {
		mode.addAttribute("paramVo", paramVo);

		return "hrp/cost/costdeptdict/costDeptDictManage";

	}

	// 查询
	@RequestMapping(value = "/hrp/cost/costdeptdict/queryCostDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		// 2016/11/4 lxj
		// 增加未停用条件,SQL语句中is_stop是活条件,多处调用了
		//mapVo.put("is_stop", "0");

		String deptDict = deptDictService.queryDeptDict(getPage(mapVo));

		return JSONObject.parseObject(deptDict);

	}

	/**
	 * @Description 查询数据部门字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptdict/queryDeptDictByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptDictByTree(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		List<?> l_map = deptDictService.queryDeptDictByTree(mapVo);
		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}

	@RequestMapping(value = "/hrp/cost/costdeptdict/costDeptDictUpdatePage", method = RequestMethod.GET)
	public String costDeptDictUpdatePage(Model mode,
			@RequestParam Map<String, Object> mapVo) throws Exception {
		String result = "";
		AccDeptAttr accDeptAttr = accDeptAttrService.queryDeptByCode(mapVo);
		//如果不是末级,不可以修改部门类型和部门性质
		mode.addAttribute("is_last", mapVo.get("is_last"));
		if (accDeptAttr != null) {
			mode.addAttribute("group_id", accDeptAttr.getGroup_id());
			mode.addAttribute("hos_id", accDeptAttr.getHos_id());
			mode.addAttribute("dept_id", accDeptAttr.getDept_id());
			mode.addAttribute("dept_code", accDeptAttr.getDept_code());
			mode.addAttribute("dept_name", accDeptAttr.getDept_name());
			// mode.addAttribute("super_code", accDeptAttr.getSuper_code());
			// mode.addAttribute("super_name", accDeptAttr.getSuper_name());
			mode.addAttribute("kind_code", accDeptAttr.getKind_code());
			mode.addAttribute("kind_name", accDeptAttr.getKind_name());
			mode.addAttribute("type_code", accDeptAttr.getType_code());
			// mode.addAttribute("natur_code", accDeptAttr.getNatur_code());
			// mode.addAttribute("out_code", accDeptAttr.getOut_code());
			// mode.addAttribute("emp_id", accDeptAttr.getEmp_id());
			mode.addAttribute("type_name", accDeptAttr.getType_name());
			mode.addAttribute("natur_code", accDeptAttr.getNatur_code());
			mode.addAttribute("natur_name", accDeptAttr.getNatur_name());
			mode.addAttribute("para_code", accDeptAttr.getPara_code());
			mode.addAttribute("para_name", accDeptAttr.getPara_name());
			mode.addAttribute("tree_code", mapVo.get("tree_code"));
			// mode.addAttribute("out_name", accDeptAttr.getOut_name());
			// mode.addAttribute("emp_name", accDeptAttr.getEmp_name());
			// mode.addAttribute("is_manager", accDeptAttr.getIs_manager());
			// mode.addAttribute("is_stock", accDeptAttr.getIs_stock());
			result = "hrp/cost/costdeptdict/costDeptDictUpdate";
		} else {

			DeptDict deptDict = deptDictService.queryDeptDictByCode(mapVo);
			mode.addAttribute("group_id", deptDict.getGroup_id());
			mode.addAttribute("hos_id", deptDict.getHos_id());
			mode.addAttribute("dept_id", deptDict.getDept_id());
			mode.addAttribute("dept_no", deptDict.getDept_no());
			mode.addAttribute("dept_code", deptDict.getDept_code());
			mode.addAttribute("dept_name", deptDict.getDept_name());
			mode.addAttribute("type_code", deptDict.getType_code());
			mode.addAttribute("natur_code", deptDict.getNatur_code());
			mode.addAttribute("kind_code", deptDict.getKind_code());
			result = "hrp/cost/costdeptdict/costDeptDictAdd";

		}

		return result;
	}

	@RequestMapping(value = "/hrp/cost/costdeptdict/addAccDeptAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccDeptAttr(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accDeptAttrJson = accDeptAttrService.addAccDeptAttr(mapVo);

		return JSONObject.parseObject(accDeptAttrJson);

	}

	@RequestMapping(value = "/hrp/cost/costdeptdict/updateAccDeptAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccDeptAttr(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
		
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
			if (StringUtils.isEmpty(mapVo.get("para_code").toString())) {
				
				mapVo.put("is_cost", "1");
				
			} 
		String accDeptAttrJson = accDeptAttrService.updateAccDeptAttr(mapVo);

		return JSONObject.parseObject(accDeptAttrJson);
	}

	/**
	 * 批量管理
	 * 
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costdeptdict/costDeptDictManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> costDeptDictManage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String assDeptJson = null;
		String paramVo = (String) mapVo.get("paramVo");
		//System.out.println("+" + mapVo.get("paramVo"));
		for (String id : paramVo.split(",")) {

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("dept_id", ids[2]);

			AccDeptAttr accDeptAttr = accDeptAttrService.queryDeptByCode(mapVo);
			if (accDeptAttr != null) {
				Map<String, Object> mapA = new HashMap<String, Object>();
				mapA.put("group_id", accDeptAttr.getGroup_id());
				mapA.put("hos_id", accDeptAttr.getHos_id());
				mapA.put("dept_id", accDeptAttr.getDept_id());
				mapA.put("type_code", mapVo.get("type_code"));
				mapA.put("natur_code", mapVo.get("natur_code"));
				mapA.put("para_code", mapVo.get("para_code"));
				if (StringUtils.isEmpty(mapA.get("type_code").toString()) && StringUtils.isEmpty(mapA.get("natur_code").toString())
				        && StringUtils.isEmpty(mapA.get("para_code").toString())) {
					mapA.put("is_cost", "1");
				} 
				assDeptJson = accDeptAttrService.updateBatchManageAccDeptAttr(mapA);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", ids[0]);
				map.put("hos_id", ids[1]);
				map.put("dept_id", ids[2]);
				map.put("type_code", mapVo.get("type_code"));
				map.put("natur_code", mapVo.get("natur_code"));
				map.put("para_code", mapVo.get("para_code"));

				assDeptJson = accDeptAttrService.addAccDeptAttr(map);
			}
		}
		return JSONObject.parseObject(assDeptJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costdeptdict/costDeptDictImportPage", method = RequestMethod.GET)
	public String costRiskDetailImportPage(Model mode) throws Exception {

		return "hrp/cost/costdeptdict/costDeptDictImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costdeptdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "成本科室部门性质.xls");

		return null;
	}

	@RequestMapping(value = "/hrp/cost/costdeptdict/readCostDeptDictFiles", method = RequestMethod.POST)
	public String readCostDeptDictFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		int temp_len = 0;
		List<AccDeptAttr> list_err = new ArrayList<AccDeptAttr>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AccDeptAttr accDeptAttr = new AccDeptAttr();

				String temp[] = list.get(i);// 行

				temp_len = temp.length;

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {

					mapVo.put("copy_code", SessionManager.getCopyCode());

				}

				if (StringUtils.isNotEmpty(temp[0])) {

					Map<String, Object> dept_map = new HashMap<String, Object>();

					if (dept_map.get("group_id") == null) {

						dept_map.put("group_id", SessionManager.getGroupId());
					}
					if (dept_map.get("hos_id") == null) {

						dept_map.put("hos_id", SessionManager.getHosId());
					}
					if (dept_map.get("copy_code") == null) {

						dept_map.put("copy_code", SessionManager.getCopyCode());

					}

					dept_map.put("dept_code", temp[0].toString());

					DeptDict deptDict = deptDictService
							.queryDeptDictByCode(dept_map);

					if (deptDict != null) {

						mapVo.put("dept_id", deptDict.getDept_id());

						mapVo.put("dept_no", deptDict.getDept_no());

					} else {

						err_sb.append("科室编码不存在");

					}

					accDeptAttr.setDept_code(temp[0].toString());

				} else {

					err_sb.append("科室编码不能为空");

				}

				if (StringUtils.isNotEmpty(temp[1])) {

					mapVo.put("dept_name", temp[1].toString());

					accDeptAttr.setDept_name(temp[1].toString());

				} else {

					err_sb.append("科室名称不能为空");

				}

				if (temp_len > 2) {

					if (StringUtils.isNotEmpty(temp[2])) {

						mapVo.put("type_code", temp[2]);

						accDeptAttr.setType_code(temp[2]);

					}

				} else {

					mapVo.put("type_code", "");

				}

				if (temp_len > 3) {

					if (StringUtils.isNotEmpty(temp[3])) {

						mapVo.put("type_name", temp[3]);

						accDeptAttr.setType_name(temp[3]);

					}

				} else {

					mapVo.put("type_name", "");

				}

				if (temp_len > 4) {

					if (StringUtils.isNotEmpty(temp[4])) {

						mapVo.put("natur_code", temp[4]);

						accDeptAttr.setNatur_code(temp[4]);

					}
				} else {

					mapVo.put("natur_code", "");

				}

				if (temp_len > 5) {

					if (StringUtils.isNotEmpty(temp[5])) {

						mapVo.put("natur_name", temp[5]);

						accDeptAttr.setNatur_name(temp[5]);

					}

				} else {

					mapVo.put("natur_name", "");

				}

				if (err_sb.toString().length() > 0) {

					accDeptAttr.setError_type(err_sb.toString());

					list_err.add(accDeptAttr);

				} else {

					AccDeptAttr accDeptAttrex = accDeptAttrService
							.queryDeptByCode(mapVo);

					if (accDeptAttrex != null) {

						accDeptAttrService.updateAccDeptAttr(mapVo);

					} else {

						accDeptAttrService.addAccDeptAttr(mapVo);
					}

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			AccDeptAttr data_exc = new AccDeptAttr();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	@RequestMapping(value = "/hrp/cost/costdeptdict/addBatchCostDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostDeptDict(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<AccDeptAttr> list_err = new ArrayList<AccDeptAttr>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				StringBuffer err_sb = new StringBuffer();

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {

					mapVo.put("copy_code", SessionManager.getCopyCode());
				}

				mapVo.put("dept_code", jsonObj.get("dept_code"));

				mapVo.put("dept_name", jsonObj.get("dept_name"));
				
				mapVo.put("type_code", jsonObj.get("type_code"));
				
				mapVo.put("type_name", jsonObj.get("type_name"));
				
				mapVo.put("natur_code", jsonObj.get("natur_code"));
				
				mapVo.put("natur_name", jsonObj.get("natur_name"));

				Map<String, Object> dept_map = new HashMap<String, Object>();

				if (dept_map.get("group_id") == null) {

					dept_map.put("group_id", SessionManager.getGroupId());
				}
				if (dept_map.get("hos_id") == null) {

					dept_map.put("hos_id", SessionManager.getHosId());
				}
				if (dept_map.get("copy_code") == null) {

					dept_map.put("copy_code", SessionManager.getCopyCode());

				}

				dept_map.put("dept_code", mapVo.get("dept_code"));

				DeptDict deptDict = deptDictService
						.queryDeptDictByCode(dept_map);

				if (deptDict != null) {

					mapVo.put("dept_id", deptDict.getDept_id());

					mapVo.put("dept_no", deptDict.getDept_no());

				} else {

					err_sb.append("科室编码不存在");

				}

				if (err_sb.toString().length() > 0) {

					AccDeptAttr accDeptAttr = new AccDeptAttr();

				        accDeptAttr.setDept_code(jsonObj.get("dept_code").toString());

						accDeptAttr.setDept_name(jsonObj.get("dept_name").toString());
						
						if (mapVo.get("type_code")!= null) {
						
							accDeptAttr.setType_code(mapVo.get("type_code").toString());
						}
						
						if (mapVo.get("type_name")!= null) {
							
							accDeptAttr.setType_name(mapVo.get("type_name").toString());
						}
						
						if (mapVo.get("natur_code")!= null) {
							
							accDeptAttr.setNatur_code(mapVo.get("natur_code").toString());
						}
						
						if (mapVo.get("natur_name")!= null) {
							
							accDeptAttr.setNatur_name(mapVo.get("natur_name").toString());
						}

						accDeptAttr.setError_type(err_sb.toString());
						
					list_err.add(accDeptAttr);

				} else {

					AccDeptAttr accDeptAttrex = accDeptAttrService
							.queryDeptByCode(mapVo);

					if (accDeptAttrex != null) {

						accDeptAttrService.updateAccDeptAttr(mapVo);

					} else {

						accDeptAttrService.addAccDeptAttr(mapVo);
					}

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			return JSONObject
					.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));

		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}
}
