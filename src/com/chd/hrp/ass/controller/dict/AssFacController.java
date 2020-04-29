/**
 * c * @Copyright: Copyright (c) 2015-2-14
 * 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.ass.controller.dict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.net.aso.f;

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
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Emp;
import com.chd.hrp.sys.entity.Fac;
import com.chd.hrp.sys.entity.FacType;
import com.chd.hrp.sys.service.FacTypeService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.FacDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.FacServiceImpl;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssFacController extends BaseController {

	private static Logger logger = Logger.getLogger(AssFacController.class);

	@Resource(name = "facService")
	private final FacServiceImpl facService = null;

	@Resource(name = "facDictService")
	private final FacDictServiceImpl facDictService = null;

	@Resource(name = "facTypeService")
	private final FacTypeService facTypeService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/ass/fac/assfacMainPage", method = RequestMethod.GET)
	public String facMainPage(Model mode) throws Exception {

		return "hrp/ass/fac/facMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/ass/fac/assfacAddPage", method = RequestMethod.GET)
	public String facAddPage(Model mode) throws Exception {

		return "hrp/ass/fac/facAdd";

	}

	@RequestMapping(value = "/hrp/ass/fac/assfacChangePage", method = RequestMethod.GET)
	public String facChangePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Fac fac = new Fac();
		fac = facService.queryFacByCode(mapVo);
		mode.addAttribute("group_id", fac.getGroup_id());
		mode.addAttribute("hos_id", fac.getHos_id());
		mode.addAttribute("fac_id", fac.getFac_id());
		mode.addAttribute("fac_code", fac.getFac_code());
		mode.addAttribute("type_code", fac.getType_code());
		mode.addAttribute("type_name", fac.getType_name());
		mode.addAttribute("fac_name", fac.getFac_name());
		mode.addAttribute("fac_sort", fac.getFac_sort());
		mode.addAttribute("spell_code", fac.getSpell_code());
		mode.addAttribute("wbx_code", fac.getWbx_code());
		mode.addAttribute("is_stop", fac.getIs_stop());
		mode.addAttribute("note", fac.getNote());

		return "hrp/ass/fac/facChange";
	}

	// 保存
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hrp/ass/fac/assaddFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addFac(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String supJson = "";

		Map<String, Map<String, Object>> mv = (Map<String, Map<String, Object>>) SessionManager.queryHosRulesMap();
		if (null != mv) {
			if (mv.get("HOS_FAC").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
				supJson = facService.addFacTz(mapVo);
			} else {
				supJson = facService.addFac(mapVo);
			}
		} else {
			supJson = facService.addFac(mapVo);
		}

		return JSONObject.parseObject(supJson);

	}

	@RequestMapping(value = "/hrp/ass/fac/assaddFacDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String facJson = facDictService.addFacDict(mapVo);

		return JSONObject.parseObject(facJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/ass/fac/assqueryFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryFac(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//不同系统过滤筛选出相应系统的生产商
		String modCode = SessionManager.getModCode();//获取系统编码
		//物流管理系统modCode=04
		//耐用品管理modCode=0413
		if(modCode!=null && modCode.startsWith("04")) {
			mapVo.put("is_mat", "1");
		}
		//固定资产管理系统modCode=05
		if(modCode!=null && modCode.startsWith("05")) {
			mapVo.put("is_ass", "1");
		}
		//药品管理系统modCode=08
		if(modCode!=null && modCode.startsWith("08")) {
			mapVo.put("is_med", "1");
		}
		
		String fac = facService.queryFac(getPage(mapVo));
		return JSONObject.parseObject(fac);
	}

	// 删除
	@RequestMapping(value = "/hrp/ass/fac/assdeleteFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFac(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		String facJson = "";
		String str = "";
		boolean falg = true;
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("fac_code", id.split("@")[3]);
			mapVo.put("fac_id", id.split("@")[2]);
			
			str = str + assBaseService.isExistsDataByTable("HOS_FAC", id.split("@")[2])== null ? ""
					: assBaseService.isExistsDataByTable("HOS_FAC", id.split("@")[2]);
			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			 
			listVo.add(mapVo);
			
			
		}
		
		if (!falg) {
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的生产厂家被以下业务使用：【" + str.substring(0, str.length() - 1)
			+ "】。\",\"state\":\"false\"}");
		}
		
		facJson = facService.deleteBatchFac(listVo);

		return JSONObject.parseObject(facJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/ass/fac/assfacUpdatePage", method = RequestMethod.GET)
	public String facUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Fac fac = new Fac();
		fac = facService.queryFacByCode(mapVo);
		mode.addAttribute("group_id", fac.getGroup_id());
		mode.addAttribute("hos_id", fac.getHos_id());
		mode.addAttribute("fac_id", fac.getFac_id());
		mode.addAttribute("fac_code", fac.getFac_code());
		mode.addAttribute("type_code", fac.getType_code());
		mode.addAttribute("type_name", fac.getType_name());
		mode.addAttribute("fac_name", fac.getFac_name());
		mode.addAttribute("fac_sort", fac.getFac_sort());
		mode.addAttribute("spell_code", fac.getSpell_code());
		mode.addAttribute("wbx_code", fac.getWbx_code());
		mode.addAttribute("is_stop", fac.getIs_stop());
		mode.addAttribute("note", fac.getNote());
		mode.addAttribute("is_mat", fac.getIs_mat());
		mode.addAttribute("is_ass", fac.getIs_ass());
		mode.addAttribute("is_med", fac.getIs_med());
		mode.addAttribute("is_sup", fac.getIs_sup());

		return "hrp/ass/fac/facUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/ass/fac/assupdateFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateFac(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String supJson;
		Map<String, Map<String, Object>> mv = (Map<String, Map<String, Object>>) SessionManager.queryHosRulesMap();
		if (null != mv) {
			if (mv.get("HOS_FAC").get("is_auto").toString().equals("1")) {
				// 台州单独自动生成编码规则
				supJson = facService.updateFacTz(mapVo);
			} else {
				supJson = facService.updateFac(mapVo);
			}
		} else {
			supJson = facService.updateFac(mapVo);
		}

		return JSONObject.parseObject(supJson);
	}

	// 导入
	@RequestMapping(value = "/hrp/ass/fac/assimportFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importFac(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String facJson = facService.importFac(mapVo);

		return JSONObject.parseObject(facJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/ass/fac/asssysFacImportPage", method = RequestMethod.GET)
	public String sysFacImportPage(Model mode) throws Exception {

		return "hrp/ass/fac/facImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/ass/fac/assdownTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "sys\\医院信息", "生产厂商.xls");
		return null;
	}

	/**
	 * 导入生产厂商<BR>
	 */
	@RequestMapping(value = "/hrp/ass/fac/assreadSysFacFiles", method = RequestMethod.POST)
	public String readSysFacFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		// 根据系统参数判断 供应商编码是否个性化定义 1表示个性化 0表示 按默认规则生成
		Map<String, Map<String, Object>> mv = (Map<String, Map<String, Object>>) SessionManager.queryHosRulesMap();

		Map<String, Object> entityMap = new HashMap<String, Object>();

		entityMap.put("group_id", SessionManager.getGroupId());

		entityMap.put("hos_id", SessionManager.getHosId());

		entityMap.put("copy_code", SessionManager.getCopyCode());

		entityMap.put("proj_code", "HOS_FAC");
		entityMap.put("mod_code", "00");

		Map<Object, Object> rules = assBaseService.getHosRules(entityMap);

		Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

		String rules_v = rules.get("rules_view").toString();

		String s_view = Strings.removeFirst(rules_v);

		List<Fac> list_err = new ArrayList<Fac>();

		List<Map<String, Object>> aList = new ArrayList<Map<String, Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				Fac fac = new Fac();

				String temp[] = list.get(i);// 行

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

					Object fac_code = temp[0].length();

					/*if (fac_code != rules_level_length.get(1)) {

						err_sb.append("编码不符合要求,请重新添加！");
					}*/

					mapVo.put("fac_code", temp[0]);

					fac.setFac_code(temp[0]);

				} else {

					err_sb.append("生成厂商编码不能为空！");
				}

				if (StringUtils.isNotEmpty(temp[1])) {

					mapVo.put("fac_name", temp[1]);

					fac.setFac_name(temp[1]);

				} else {

					err_sb.append("生成厂商名称不能为空!");
				}

				if (StringUtils.isNotEmpty(temp[2])) {

					Map<String, Object> kindMap = new HashMap<String, Object>();

					kindMap.put("group_id", SessionManager.getGroupId());

					kindMap.put("hos_id", SessionManager.getHosId());

					kindMap.put("type_code", temp[2]);

					FacType facType = facTypeService.queryFacTypeByCode(kindMap);

					if (facType == null) {

						err_sb.append("生产厂商类别编码不存在!");

					} else {

						mapVo.put("type_code", temp[2]);

					}

					fac.setType_code(temp[2]);

				} else {

					err_sb.append("生产厂商类别编码不能为空!");
				}

				if (StringUtils.isNotEmpty(temp[3])) {

					mapVo.put("type_name", temp[3]);

					fac.setType_name(temp[3]);

				} else {

					err_sb.append("生产厂商类别名称不能为空!");
				}

				if (StringUtils.isNotEmpty(temp[4])) {

					mapVo.put("is_mat", temp[4]);

					fac.setIs_mat(Integer.parseInt(temp[4].toString()));

				} else {

					err_sb.append("物流管理不能为空!");
				}
				if (StringUtils.isNotEmpty(temp[5])) {

					mapVo.put("is_ass", temp[5]);

					fac.setIs_ass(Integer.parseInt(temp[5].toString()));

				} else {

					err_sb.append("固定资产不能为空!");
				}
				if (StringUtils.isNotEmpty(temp[6])) {

					mapVo.put("is_med", temp[6]);

					fac.setIs_med(Integer.parseInt(temp[6].toString()));

				} else {

					err_sb.append("药品管理不能为空!");
				}
				if (StringUtils.isNotEmpty(temp[7])) {

					mapVo.put("is_sup", temp[7]);

					fac.setIs_sup(Integer.parseInt(temp[7].toString()));

				} else {

					err_sb.append("供应商平台不能为空!");
				}
				
				if (StringUtils.isNotEmpty(temp[8])) {

					mapVo.put("is_stop", temp[8]);

					fac.setIs_stop(Integer.parseInt(temp[8].toString()));

				} else {

					err_sb.append("是否停用不能为空!");
				}
				
				/* 备注默认可以为空 */
				if (StringUtils.isNotEmpty(temp[9])) {

					mapVo.put("note", temp[9]);

					fac.setNote(temp[9]);

				} else {

					mapVo.put("note", "");

					fac.setNote("");
				}


				Map<String, Object> utilMap = new HashMap<String, Object>();
				utilMap.put("group_id", entityMap.get("group_id"));
				utilMap.put("hos_id", entityMap.get("hos_id"));
				utilMap.put("copy_code", "");
				utilMap.put("field_table", "HOS_FAC");
				utilMap.put("field_key1", "");
				utilMap.put("field_value1", "");
				utilMap.put("field_key2", "");
				utilMap.put("field_value2", "");

				utilMap.remove("field_key2");
				utilMap.put("field_sort", "fac_sort");
				int count = sysFunUtilMapper.querySysMaxSort(utilMap);
				mapVo.put("fac_sort", count);
				
				Fac fac1 = facService.queryFacByCode(mapVo);

				if (fac1 != null) {

					err_sb.append("编码已存在!");

				}

				if (err_sb.toString().length() > 0) {

					fac.setError_type(err_sb.toString());

					list_err.add(fac);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fac_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("fac_name").toString()));

					// 暂时注释
					// facService.addImportFac(mapVo);

					if (null != mv) {
						if (mv.get("HOS_FAC").get("is_auto").toString().equals("1")) {
							// 台州单独自动生成编码规则
							facService.addFacTz(mapVo);
						} else {
							facService.addFac(mapVo);
						}
					} else {
						facService.addFac(mapVo);
					}

				}
			}

		}
		catch (Exception e) {
			Fac data_exc = new Fac();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/ass/fac/assaddBatchSysFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchSysFac(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		// 根据系统参数判断 供应商编码是否个性化定义 1表示个性化 0表示 按默认规则生成
		Map<String, Map<String, Object>> mv = (Map<String, Map<String, Object>>) SessionManager.queryHosRulesMap();

		Map<String, Object> entityMap = new HashMap<String, Object>();

		entityMap.put("group_id", SessionManager.getGroupId());

		entityMap.put("hos_id", SessionManager.getHosId());

		entityMap.put("copy_code", SessionManager.getCopyCode());

		entityMap.put("proj_code", "HOS_FAC");
		entityMap.put("mod_code", "00");

		Map<Object, Object> rules = assBaseService.getHosRules(entityMap);

		Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

		String rules_v = rules.get("rules_view").toString();

		String s_view = Strings.removeFirst(rules_v);

		List<Fac> list_err = new ArrayList<Fac>();

		JSONArray json = JSONArray.parseArray(paramVo);

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

		String s = null;

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				Fac fac = new Fac();

				mapVo.put("fac_code", jsonObj.get("fac_code").toString());

				mapVo.put("fac_name", jsonObj.get("fac_name").toString());

				mapVo.put("type_code", jsonObj.get("type_code").toString());

				mapVo.put("type_name", jsonObj.get("type_name").toString());

				mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));

				mapVo.put("note", jsonObj.get("note").toString());

				Object fac_code = mapVo.get("fac_code").toString().length();

				if (fac_code != rules_level_length.get(1)) {

					err_sb.append("编码不符合要求,请重新添加！");
				}

				FacType facType = facTypeService.queryFacTypeByCode(mapVo);

				if (facType == null) {

					err_sb.append("生产厂商类别编码不存在!");

				}

				Map<String, Object> facMap = new HashMap<String, Object>();

				facMap.put("group_id", entityMap.get("group_id"));

				facMap.put("hos_id", entityMap.get("hos_id"));

				facMap.put("fac_code", mapVo.get("fac_code"));

				Fac fac1 = facService.queryFacByCode(facMap);

				if (fac1 != null) {

					err_sb.append("编码已存在!");

				}

				Map<String, Object> utilMap = new HashMap<String, Object>();

				utilMap.put("group_id", entityMap.get("group_id"));
				utilMap.put("hos_id", entityMap.get("hos_id"));
				utilMap.put("copy_code", "");
				utilMap.put("field_table", "HOS_FAC");
				utilMap.put("field_key1", "");
				utilMap.put("field_value1", "");
				utilMap.put("field_key2", "");
				utilMap.put("field_value2", "");

				utilMap.remove("field_key2");
				utilMap.put("field_sort", "fac_sort");
				int count = sysFunUtilMapper.querySysMaxSort(utilMap);

				mapVo.put("fac_sort", count);

				if (err_sb.toString().length() > 0) {

					fac.setFac_code(mapVo.get("fac_code").toString());

					fac.setFac_name(mapVo.get("fac_name").toString());

					fac.setType_code(mapVo.get("type_code").toString());

					fac.setType_name(mapVo.get("type_name").toString());

					fac.setIs_stop(Integer.parseInt(mapVo.get("is_stop").toString()));

					fac.setNote(mapVo.get("note").toString());

					fac.setError_type(err_sb.toString());

					list_err.add(fac);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fac_code").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("fac_code").toString()));
					// 暂时注释
					// facService.addImportFac(mapVo);

					if (null != mv) {
						if (mv.get("HOS_FAC").get("is_auto").toString().equals("1")) {
							// 台州单独自动生成编码规则
							facService.addFacTz(mapVo);
						} else {
							facService.addFac(mapVo);
						}
					} else {
						facService.addFac(mapVo);
					}
				}
			}

		}
		catch (Exception e) {
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}
	}
}
