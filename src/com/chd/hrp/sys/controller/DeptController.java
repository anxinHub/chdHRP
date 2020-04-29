/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.sys.controller;

import java.io.IOException;
import java.lang.reflect.Method;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.serviceImpl.AccDeptAttrServiceImpl;
import com.chd.hrp.sys.entity.Dept;
import com.chd.hrp.sys.entity.DeptKind;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.DeptKindServiceImpl;
import com.chd.hrp.sys.serviceImpl.DeptServiceImpl;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class DeptController extends BaseController {

	private static Logger logger = Logger.getLogger(DeptController.class);

	@Resource(name = "deptService")
	private final DeptServiceImpl deptService = null;

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

	@Resource(name = "deptKindService")
	private final DeptKindServiceImpl deptKindService = null;

	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;


	@Resource(name = "accDeptAttrService")
	private final AccDeptAttrServiceImpl accDeptAttrService = null;
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/dept/deptMainPage", method = RequestMethod.GET)
	public String deptMainPage(Model mode) throws Exception {

		return "hrp/sys/dept/deptMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/dept/deptAddPage", method = RequestMethod.GET)
	public String deptAddPage(Model mode) throws Exception {

		return "hrp/sys/dept/deptAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/dept/addDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try{

		String deptJson = deptService.addDept(mapVo);

		return JSONObject.parseObject(deptJson);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/hrp/sys/dept/addDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try{
		String dictType = mapVo.get("dictType").toString();
		if (dictType.equals("0")) {
			deptService.updateDeptCode(mapVo);
		} else {
			deptService.updateDeptName(mapVo);
		}

		String deptDictJson = deptDictService.addDeptDict(mapVo);
		return JSONObject.parseObject(deptDictJson);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/dept/queryDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String dept = deptService.queryDept(getPage(mapVo));

		return JSONObject.parseObject(dept);

	}

	// 删除
	@RequestMapping(value = "/hrp/sys/dept/deleteDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDept(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		try{
		String deptJson = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("dept_code", id.split("@")[2]);
			mapVo.put("dept_id", id.split("@")[3]);
			mapVo.put("super_code", id.split("@")[4]);
			listVo.add(mapVo);
		}

		deptJson = deptService.deleteBatchDept(listVo);
		return JSONObject.parseObject(deptJson);
	}catch (Exception e) {
		logger.error(e.getMessage(),e);
		return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
	}
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/dept/deptUpdatePage", method = RequestMethod.GET)
	public String deptUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Dept dept = new Dept();
		dept = deptService.queryDeptByCode(mapVo);
		mode.addAttribute("group_id", dept.getGroup_id());
		mode.addAttribute("hos_id", dept.getHos_id());
		mode.addAttribute("dept_id", dept.getDept_id());
		mode.addAttribute("dept_code", dept.getDept_code());
		mode.addAttribute("kind_code", dept.getKind_code());
		mode.addAttribute("kind_name", dept.getKind_name());
		mode.addAttribute("dept_name", dept.getDept_name());
		mode.addAttribute("super_code", dept.getSuper_code());
		mode.addAttribute("udefine_code", dept.getUdefine_code());
		mode.addAttribute("sort_code", dept.getSort_code());
		mode.addAttribute("is_stop", dept.getIs_stop());
		mode.addAttribute("is_disable", dept.getIs_disable());
		mode.addAttribute("is_last", dept.getIs_last());
		mode.addAttribute("spell_code", dept.getSpell_code());
		mode.addAttribute("wbx_code", dept.getWbx_code());
		mode.addAttribute("note", dept.getNote());
		mode.addAttribute("dept_level", dept.getDept_level());
		/* AccDeptAttr accDeptAttr = new AccDeptAttr();
			accDeptAttr = accDeptAttrService.queryAccDeptAttrByCode(mapVo);
			//mode.addAttribute("dept_no", accDeptAttr.getDept_no());
			mode.addAttribute("group_id", accDeptAttr.getGroup_id());
			mode.addAttribute("hos_id", accDeptAttr.getHos_id());
			//mode.addAttribute("dept_id", accDeptAttr.getDept_id());
			mode.addAttribute("type_code", accDeptAttr.getType_code());
			mode.addAttribute("natur_code", accDeptAttr.getNatur_code());
			mode.addAttribute("out_code", accDeptAttr.getOut_code());
			mode.addAttribute("emp_id", accDeptAttr.getEmp_id());
			mode.addAttribute("is_manager", accDeptAttr.getIs_manager());
			mode.addAttribute("is_stock", accDeptAttr.getIs_stock());*/
		AccDeptAttr accDeptAttr = accDeptAttrService.queryDeptByCode(mapVo);
		if(accDeptAttr != null){
			/*mode.addAttribute("group_id", accDeptAttr.getGroup_id());
			mode.addAttribute("hos_id", accDeptAttr.getHos_id());
			mode.addAttribute("dept_id", accDeptAttr.getDept_id());
			mode.addAttribute("dept_code", accDeptAttr.getDept_code());
			mode.addAttribute("dept_name", accDeptAttr.getDept_name());
			mode.addAttribute("super_code", accDeptAttr.getSuper_code());
			mode.addAttribute("super_name", accDeptAttr.getSuper_name());
			mode.addAttribute("kind_code", accDeptAttr.getKind_code());
			mode.addAttribute("kind_name", accDeptAttr.getKind_name());*/
			mode.addAttribute("type_code", accDeptAttr.getType_code());
			mode.addAttribute("natur_code", accDeptAttr.getNatur_code());
			mode.addAttribute("out_code", accDeptAttr.getOut_code());
			mode.addAttribute("emp_id", accDeptAttr.getEmp_id());
			mode.addAttribute("type_name", accDeptAttr.getType_name());
			mode.addAttribute("natur_name", accDeptAttr.getNatur_name());
			mode.addAttribute("out_name", accDeptAttr.getOut_name());
			mode.addAttribute("emp_name", accDeptAttr.getEmp_name());
			mode.addAttribute("emp_code", accDeptAttr.getEmp_code());
			mode.addAttribute("is_manager", accDeptAttr.getIs_manager());
			mode.addAttribute("is_stock", accDeptAttr.getIs_stock());
		}
		return "hrp/sys/dept/deptUpdate";
	}

	@RequestMapping(value = "/hrp/sys/dept/queryDeptByCode", method = RequestMethod.POST)
	public Map<String, Object> queryDeptByCode(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletResponse response) throws Exception {

		Dept dept = new Dept();
		dept = deptService.queryDeptByCode(mapVo);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("dept", dept);
		response.getWriter().print(jsonObj.toString());
		return null;
	}

	// 修改保存
	@RequestMapping(value = "/hrp/sys/dept/updateDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
   try{
		String deptJson = deptService.updateDept(mapVo);

		return JSONObject.parseObject(deptJson);
   }catch (Exception e) {
		logger.error(e.getMessage(),e);
		return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
	}
	}

	@RequestMapping(value = "/hrp/sys/dept/queryDeptByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptByMenu(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String deptDict = deptService.queryDeptByMenu(mapVo);

		response.getWriter().print(deptDict);
		return null;

	}

	@RequestMapping(value = "/hrp/sys/dept/deptImportPage", method = RequestMethod.GET)
	public String costChargeKindArrtImportPage(Model mode) throws Exception {

		return "hrp/sys/dept/deptImport";

	}

	@RequestMapping(value = "hrp/sys/dept/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sys\\医院信息", "部门维护.xls");

		return null;
	}

	/**
	 * 分摊参数<BR>
	 * 导入
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "/hrp/sys/dept/readDeptFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws Exception {
		// 获取部门编码规则
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		// map.put("copy_code", SessionManager.getCopyCode()); // 部门表没有账套
		map.put("proj_code", "HOS_DEPT");// 添加编码规则判断
		map.put("mod_code", "00");
		Map<Object, Object> rules = sysBaseService.getHosRules(map);
		
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		String rules_v = rules.get("rules_view").toString();
		String s_view = Strings.removeFirst(rules_v);
		Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
		
		Object level = null;
		List<Dept> list_err = new ArrayList<Dept>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
				Dept costDeptParaDict = new Dept();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("rules", s_view);
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}

				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				
				// 部门表不用账套
//				if (mapVo.get("copy_code") == null) {
//					mapVo.put("copy_code", SessionManager.getCopyCode());
//				}

				if (StringUtils.isNotEmpty(temp[0])) {
					costDeptParaDict.setDept_code(temp[0]);
					mapVo.put("dept_code", temp[0]);
				} else {
					err_sb.append("部门编码为空  ");
				}

				if (StringUtils.isNotEmpty(temp[1])) {
					costDeptParaDict.setDept_name(temp[1]);
					mapVo.put("dept_name", temp[1]);
				} else {
					err_sb.append("部门名称为空  ");
				}

				costDeptParaDict.setKind_code(temp[2]);
				costDeptParaDict.setKind_name(temp[3]);
				mapVo.put("kind_code", temp[2]);

				DeptKind deptKind = new DeptKind();
				deptKind = deptKindService.queryDeptKindByCode(mapVo);
				if (deptKind == null) {
					err_sb.append("部门分类不存在 ");
				}
				
				if (StringUtils.isNotEmpty(temp[4])) {
					costDeptParaDict.setType_code(temp[4]);
					costDeptParaDict.setType_name(temp[5]);
					mapVo.put("type_code", temp[4]);
				} else {
					err_sb.append("部门类型为空  ");
				}

				if (StringUtils.isNotEmpty(temp[6]) || temp[6].length() != 0) {
					costDeptParaDict.setNatur_code(temp[6]);
					costDeptParaDict.setNatur_name(temp[7]);
					mapVo.put("natur_code", temp[6]);
				} else {
					err_sb.append("部门性质为空  ");
				}

				if (StringUtils.isNotEmpty(temp[8]) || temp[8].length() != 0) {

					/*
					 * costDeptParaDict.setNatur_code(temp[6]);
					 * costDeptParaDict.setNatur_name(temp[7]);
					 */
					mapVo.put("out_code", temp[8]);
				}

				if (StringUtils.isNotEmpty(temp[9]) || temp[9].length() != 0) {
					mapVo.put("is_stock", temp[9]);
				}
				if (StringUtils.isNotEmpty(temp[10]) || temp[10].length() != 0) {
					mapVo.put("is_manager", temp[10]);
				}
				costDeptParaDict.setUdefine_code(temp[11]);
				mapVo.put("udefine_code", temp[11]);

				if (StringUtils.isNotEmpty(temp[12])) {
					costDeptParaDict.setIs_stop(Integer.parseInt(temp[12]));
					mapVo.put("is_stop", temp[12]);
					mapVo.put("is_disable", temp[12]);
				} else {
					err_sb.append("是否停用为空  ");
				}/*
				if (StringUtils.isNotEmpty(temp[13])) {
					costDeptParaDict.setIs_disable(Integer.parseInt(temp[13]));
					mapVo.put("is_disable", temp[13]);
				} else {
					err_sb.append("是否启用为空  ");
				}*/

				level = level_map.get(temp[0].length());
				if (null != level) {
					int int_level = (Integer) level;
					mapVo.put("item_grade", level);
					if (int_level == 1) {
						mapVo.put("dept_level", level);
						mapVo.put("super_code", "0");
						mapVo.put("is_last", "1");
					} else {
						mapVo.put("dept_level", level);
						int v_level = int_level - 1;
						int end = (Integer) rules_level_length.get(v_level);
						mapVo.put("super_code", temp[0].substring(0, end));
						// 查询是否有上级
						List<Map<String, Object>> dept = deptService.querySuper(mapVo);
						if (dept.size() != 0) {
							Map<String, Object> isLast = new HashMap<String, Object>();
							isLast.put("group_id", SessionManager.getGroupId());
							isLast.put("hos_id", SessionManager.getHosId());
							isLast.put("is_last", 0);
							isLast.put("dept_id", temp[0].substring(0, end));
							deptService.updateIsLast(isLast);

						}
						mapVo.put("is_last", "1");
					}
				} else {
					err_sb.append("部门编码不符合编码规则" + s_view);
				}

				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				byCodeMap.put("group_id", mapVo.get("group_id"));
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				//byCodeMap.put("copy_code", mapVo.get("copy_code"));// 部门不用账套
				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				byCodeMap.put("kind_code", mapVo.get("kind_code"));

				Dept data_exc_extis = deptService.queryDeptByCode(byCodeMap);

				// 根据不同业务提示相关信息
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
					/*
					 * mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dept_name"). toString()));
					 * mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dept_name").toString()));
					 * deptService.updateDept(mapVo);
					 */
				}

				/*
				 * // 判断上级编码是否为空 不为空则反查上级编码所属分类 if
				 * (!"0".equals(mapVo.get("super_code").toString())) {
				 * Map<String, Object> map_super = new HashMap<String,
				 * Object>(); map_super.put("group_id", mapVo.get("group_id"));
				 * map_super.put("hos_id", mapVo.get("hos_id"));
				 * map_super.put("copy_code", mapVo.get("copy_code"));
				 * map_super.put("dept_code", mapVo.get("super_code"));
				 * 
				 * Map<String, Object> data_exc_extis_super =
				 * deptService.queryDeptByCodeName(map_super);
				 * 
				 * if (null == data_exc_extis_super) { err_sb.append("上级编码不存在！ "
				 * );
				 * 
				 * continue; } // 判断上级编码是否为末级 if
				 * (data_exc_extis_super.getIs_last() == 1) { Map<String,
				 * Object> update_is_last = new HashMap<String, Object>();
				 * update_is_last.put("group_id", mapVo.get("group_id"));
				 * update_is_last.put("hos_id", mapVo.get("hos_id"));
				 * update_is_last.put("copy_code", mapVo.get("copy_code"));
				 * update_is_last.put("type_code", mapVo.get("type_code"));
				 * update_is_last.put("natur_code", mapVo.get("natur_code"));
				 * update_is_last.put("dept_id",
				 * data_exc_extis_super.getDept_id());
				 * update_is_last.put("is_last", "0");
				 * update_is_last.put("is_stop", "0");
				 * deptService.updateDeptInput(update_is_last); } }
				 */

				if (err_sb.toString().length() > 0) {
					costDeptParaDict.setError_type(err_sb.toString());
					list_err.add(costDeptParaDict);
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dept_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dept_name").toString()));
					// list_batch.add(mapVo);
					deptService.addDeptInput(mapVo);
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			Dept data_exc = new Dept();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		// if (list_batch.size() > 0) {
		//
		// deptService.addBatchDept(list_batch);
		//
		// }
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
	}

	@RequestMapping(value = "/hrp/sys/dept/addImportDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImportDeptDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		// List<Map<String, Object>> list_batch = new ArrayList<Map<String,
		// Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", SessionManager.getGroupId());

		map.put("hos_id", SessionManager.getHosId());

		map.put("copy_code", SessionManager.getCopyCode());

		map.put("proj_code", "HOS_DEPT");

		map.put("mod_code", "00");

		Rules rules = rulesService.queryRulesByCode(map);

		StringBuffer sb = new StringBuffer();

		for (int i = 1; i <= 10; i++) {
			Method m = (Method) rules.getClass().getMethod("get" + ("Level" + i));
			Object obj = m.invoke(rules, new Object[] {});
			if (obj.equals("0")) {
				break;
			}
			if (i == 10) {
				sb.append(obj);
			} else {
				sb.append(obj + "-");
			}

		}
		List<Dept> list_err = new ArrayList<Dept>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {
			while (it.hasNext()) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				StringBuffer err_sb = new StringBuffer();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				mapVo.put("rules", sb.toString());
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

				mapVo.put("kind_code", jsonObj.get("kind_code"));

				mapVo.put("kind_name", jsonObj.get("kind_name"));

				mapVo.put("super_code", jsonObj.get("super_code"));

				mapVo.put("udefine_code", jsonObj.get("udefine_code"));

				mapVo.put("sort_code", jsonObj.get("sort_code"));

				mapVo.put("is_stop", jsonObj.get("is_stop"));

				mapVo.put("is_last", jsonObj.get("is_last"));

				mapVo.put("note", jsonObj.get("note"));

				mapVo.put("dept_level", jsonObj.get("dept_level"));

				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dept_name").toString()));

				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dept_name").toString()));

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));

				byCodeMap.put("dept_code", mapVo.get("dept_code"));

				Dept data_exc_extis = deptService.queryDeptByCode(byCodeMap);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}

				byCodeMap.put("kind_code", mapVo.get("kind_code"));
				DeptKind deptKind = deptKindService.queryDeptKindByCode(byCodeMap);
				if (deptKind == null) {
					err_sb.append("部门分类不存在  ");
				}

				Dept costDeptParaDict = new Dept();

				if (err_sb.toString().length() > 0) {

					costDeptParaDict.setDept_code(mapVo.get("dept_code").toString());

					costDeptParaDict.setKind_code(mapVo.get("kind_code").toString());

					costDeptParaDict.setKind_name(mapVo.get("kind_name").toString());

					costDeptParaDict.setDept_name(mapVo.get("dept_name").toString());

					costDeptParaDict.setSuper_code(mapVo.get("super_code").toString());

					costDeptParaDict.setUdefine_code(mapVo.get("udefine_code").toString());

					costDeptParaDict.setSort_code(Long.parseLong(mapVo.get("sort_code").toString()));

					costDeptParaDict.setIs_stop(Integer.parseInt(mapVo.get("is_stop").toString()));

					costDeptParaDict.setIs_last(Integer.parseInt(mapVo.get("is_last").toString()));

					costDeptParaDict.setNote(mapVo.get("note").toString());

					costDeptParaDict.setDept_level(Integer.parseInt(mapVo.get("dept_level").toString()));

					costDeptParaDict.setError_type(err_sb.toString());

					list_err.add(costDeptParaDict);

				} else {

					// list_batch.add(mapVo);
					deptService.addDept(mapVo);
				}
			}

			// if (list_batch.size() > 0) {

			// deptService.addBatchDept(list_batch);

			// }
		}
		catch (DataAccessException e) {

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}
		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}
	}

	@RequestMapping(value = "/hrp/sys/dept/getRules", method = RequestMethod.POST)
	@ResponseBody
	public String getRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("proj_code", "HOS_DEPT");

		mapVo.put("mod_code", "00");

		Rules rules = rulesService.queryRulesByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		for (int i = 1; i <= 10; i++) {
			Method m = (Method) rules.getClass().getMethod("get" + ("Level" + i));
			Object obj = m.invoke(rules, new Object[] {});
			if (obj.equals("0")) {
				break;
			}
			if (i == 10) {
				sb.append(obj);
			} else {
				sb.append(obj + "-");
			}

		}
		return sb.toString();

	}

	@RequestMapping(value = "/hrp/sys/dept/getSuperCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSuperCode(@RequestParam Map<String, Object> mapVo, Model mode) {

		String deptJson = deptService.getSuperCode(mapVo);
		return JSONObject.parseObject(deptJson);
	}
	/**
	 * 组织架构图页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/dept/deptOrgPage", method = RequestMethod.GET)
	public String deptOrgPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/sys/dept/deptOrg";

	}
	@RequestMapping(value = "/hrp/sys/dept/queryDeptOrg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryDeptOrg(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String reJson=null;
		try{
			reJson=deptService.queryDeptOrg(mapVo);
		}catch(Exception e){
			reJson="{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(reJson);
		
	}
}
