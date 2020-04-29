
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.controller.dict;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.service.dict.AssNoDictService;

/**
 * 
 * @Description: 050102 资产变更字典
 * @Table: ASS_NO_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class AssNoDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssNoDictController.class);

	// 引入Service服务
	@Resource(name = "assNoDictService")
	private final AssNoDictService assNoDictService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assnodict/assNoDictMainPage", method = RequestMethod.GET)
	public String assNoDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assnodict/assNoDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assnodict/assNoDictAddPage", method = RequestMethod.GET)
	public String assNoDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assnodict/assNoDictAdd";

	}

	/**
	 * @Description 添加数据 050102 资产变更字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assnodict/addAssNoDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssNoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assNoDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

		try {

			assNoDictJson = assNoDictService.addAssNoDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assNoDictJson);

	}

	/**
	 * @Description 更新跳转页面 050102 资产变更字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assnodict/assNoDictUpdatePage", method = RequestMethod.GET)
	public String assNoDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssNoDict assNoDict = new AssNoDict();

		assNoDict = assNoDictService.queryAssNoDictByCode(mapVo);

		mode.addAttribute("group_id", assNoDict.getGroup_id());
		mode.addAttribute("hos_id", assNoDict.getHos_id());
		mode.addAttribute("copy_code", assNoDict.getCopy_code());
		mode.addAttribute("ass_id", assNoDict.getAss_id());
		mode.addAttribute("ass_no", assNoDict.getAss_no());
		mode.addAttribute("ass_code", assNoDict.getAss_code());
		mode.addAttribute("ass_name", assNoDict.getAss_name());
		mode.addAttribute("ass_type_id", assNoDict.getAss_type_id());
		mode.addAttribute("acc_type_code", assNoDict.getAcc_type_code());
		mode.addAttribute("ass_unit", assNoDict.getAss_unit());
		mode.addAttribute("is_measure", assNoDict.getIs_measure());
		mode.addAttribute("is_depre", assNoDict.getIs_depre());
		mode.addAttribute("ass_depre_code", assNoDict.getAss_depre_code());
		mode.addAttribute("depre_years", assNoDict.getDepre_years());
		mode.addAttribute("is_stop", assNoDict.getIs_stop());
		mode.addAttribute("ass_spec", assNoDict.getAss_spec());
		mode.addAttribute("ass_model", assNoDict.getAss_model());
		mode.addAttribute("fac_id", assNoDict.getFac_id());
		mode.addAttribute("fac_no", assNoDict.getFac_no());
		mode.addAttribute("ven_id", assNoDict.getVen_id());
		mode.addAttribute("ven_no", assNoDict.getVen_no());
		mode.addAttribute("usage_code", assNoDict.getUsage_code());
		mode.addAttribute("gb_code", assNoDict.getGb_code());
		mode.addAttribute("spell_code", assNoDict.getSpell_code());
		mode.addAttribute("wbx_code", assNoDict.getWbx_code());

		return "hrp/ass/assnodict/assNoDictUpdate";
	}

	/**
	 * @Description 更新数据 050102 资产变更字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assnodict/updateAssNoDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssNoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assNoDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

		try {

			assNoDictJson = assNoDictService.updateAssNoDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assNoDictJson);
	}

	/**
	 * @Description 删除数据 050102 资产变更字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assnodict/deleteAssNoDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssNoDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assNoDictJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_id", ids[3]);
			mapVo.put("ass_no", ids[4]);

			listVo.add(mapVo);

		}
		try {

			assNoDictJson = assNoDictService.deleteBatchAssNoDict(listVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assNoDictJson);

	}

	/**
	 * @Description 查询数据 050102 资产变更字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assnodict/queryAssNoDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssNoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assNoDict = assNoDictService.queryAssNoDict(getPage(mapVo));

		return JSONObject.parseObject(assNoDict);

	}

	/**
	 * @Description 导入跳转页面 050102 资产变更字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assnodict/assNoDictImportPage", method = RequestMethod.GET)
	public String assNoDictImportPage(Model mode) throws Exception {

		return "hrp/ass/assnodict/assNoDictImport";

	}

	/**
	 * @Description 下载导入模版 050102 资产变更字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assnodict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "base\\目录", "模版.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050102 资产变更字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assnodict/readAssNoDictFiles", method = RequestMethod.POST)
	public String readAssNoDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssNoDict> list_err = new ArrayList<AssNoDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssNoDict assNoDict = new AssNoDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					assNoDict.setGroup_id(Long.valueOf(temp[0]));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集体ID为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assNoDict.setHos_id(Long.valueOf(temp[1]));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					assNoDict.setCopy_code(temp[2]);

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					assNoDict.setAss_id(Long.valueOf(temp[3]));

					mapVo.put("ass_id", temp[3]);

				} else {

					err_sb.append("资产ID为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assNoDict.setAss_no(Long.valueOf(temp[4]));

					mapVo.put("ass_no", temp[4]);

				} else {

					err_sb.append("资产NO为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assNoDict.setAss_code(temp[5]);

					mapVo.put("ass_code", temp[5]);

				} else {

					err_sb.append("资产编码为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assNoDict.setAss_name(temp[6]);

					mapVo.put("ass_name", temp[6]);

				} else {

					err_sb.append("资产名称为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assNoDict.setAss_type_id(Long.valueOf(temp[7]));

					mapVo.put("ass_type_code", temp[7]);

				} else {

					err_sb.append("类别编码为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assNoDict.setAcc_type_code(temp[8]);

					mapVo.put("acc_type_code", temp[8]);

				} else {

					err_sb.append("财务分类编码为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assNoDict.setAss_unit(temp[9]);

					mapVo.put("ass_unit", temp[9]);

				} else {

					err_sb.append("单位为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assNoDict.setIs_measure(Integer.valueOf(temp[10]));

					mapVo.put("is_measure", temp[10]);

				} else {

					err_sb.append("是否计量为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assNoDict.setIs_depre(Integer.valueOf(temp[11]));

					mapVo.put("is_depre", temp[11]);

				} else {

					err_sb.append("是否折旧为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assNoDict.setAss_depre_code(temp[12]);

					mapVo.put("ass_depre_code", temp[12]);

				} else {

					err_sb.append("折旧方法编码为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					assNoDict.setDepre_years(Integer.valueOf(temp[13]));

					mapVo.put("depre_years", temp[13]);

				} else {

					err_sb.append("折旧年限为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					assNoDict.setIs_stop(Integer.valueOf(temp[14]));

					mapVo.put("is_stop", temp[14]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					assNoDict.setAss_spec(temp[15]);

					mapVo.put("ass_spec", temp[15]);

				} else {

					err_sb.append("规格为空  ");

				}

				if (StringTool.isNotBlank(temp[16])) {

					assNoDict.setAss_model(temp[16]);

					mapVo.put("ass_model", temp[16]);

				} else {

					err_sb.append("型号为空  ");

				}

				if (StringTool.isNotBlank(temp[17])) {

					assNoDict.setFac_id(temp[17]);

					mapVo.put("fac_id", temp[17]);

				} else {

					err_sb.append("生产厂商ID为空  ");

				}

				if (StringTool.isNotBlank(temp[18])) {

					assNoDict.setFac_no(temp[18]);

					mapVo.put("fac_no", temp[18]);

				} else {

					err_sb.append("生产厂商NO为空  ");

				}

				if (StringTool.isNotBlank(temp[19])) {

					assNoDict.setVen_id(temp[19]);

					mapVo.put("ven_id", temp[19]);

				} else {

					err_sb.append("主要供应商ID为空  ");

				}

				if (StringTool.isNotBlank(temp[20])) {

					assNoDict.setVen_no(temp[20]);

					mapVo.put("ven_no", temp[20]);

				} else {

					err_sb.append("主要供应商NO为空  ");

				}

				if (StringTool.isNotBlank(temp[21])) {

					assNoDict.setUsage_code(temp[21]);

					mapVo.put("usage_code", temp[21]);

				} else {

					err_sb.append("资产用途为空  ");

				}

				if (StringTool.isNotBlank(temp[22])) {

					assNoDict.setGb_code(temp[22]);

					mapVo.put("gb_code", temp[22]);

				} else {

					err_sb.append("国标码为空  ");

				}

				if (StringTool.isNotBlank(temp[23])) {

					assNoDict.setSpell_code(temp[23]);

					mapVo.put("spell_code", temp[23]);

				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(temp[24])) {

					assNoDict.setWbx_code(temp[24]);

					mapVo.put("wbx_code", temp[24]);

				} else {

					err_sb.append("五笔码为空  ");

				}

				AssNoDict data_exc_extis = assNoDictService.queryAssNoDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assNoDict.setError_type(err_sb.toString());

					list_err.add(assNoDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

					try {

						String dataJson = assNoDictService.addAssNoDict(mapVo);

					} catch (Exception e) {

						return "{\"error\":\"" + e.getMessage() + " \"}";

					}
				}

			}

		} catch (DataAccessException e) {

			AssNoDict data_exc = new AssNoDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050102 资产变更字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/cost/assnodict/addBatchAssNoDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssNoDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssNoDict> list_err = new ArrayList<AssNoDict>();

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

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AssNoDict assNoDict = new AssNoDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("group_id"))) {

					assNoDict.setGroup_id(Long.valueOf((String) jsonObj.get("group_id")));

					mapVo.put("group_id", SessionManager.getGroupId());

				} else {

					err_sb.append("集体ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("hos_id"))) {

					assNoDict.setHos_id(Long.valueOf((String) jsonObj.get("hos_id")));

					mapVo.put("hos_id", SessionManager.getHosId());

				} else {

					err_sb.append("医院ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("copy_code"))) {

					assNoDict.setCopy_code((String) jsonObj.get("copy_code"));

					mapVo.put("copy_code", SessionManager.getCopyCode());

				} else {

					err_sb.append("账套编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_id"))) {

					assNoDict.setAss_id(Long.valueOf((String) jsonObj.get("ass_id")));

					mapVo.put("ass_id", jsonObj.get("ass_id"));

				} else {

					err_sb.append("资产ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_no"))) {

					assNoDict.setAss_no(Long.valueOf((String) jsonObj.get("ass_no")));

					mapVo.put("ass_no", jsonObj.get("ass_no"));

				} else {

					err_sb.append("资产NO为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_code"))) {

					assNoDict.setAss_code((String) jsonObj.get("ass_code"));

					mapVo.put("ass_code", jsonObj.get("ass_code"));

				} else {

					err_sb.append("资产编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_name"))) {

					assNoDict.setAss_name((String) jsonObj.get("ass_name"));

					mapVo.put("ass_name", jsonObj.get("ass_name"));

				} else {

					err_sb.append("资产名称为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_type_code"))) {

					assNoDict.setAss_type_id((Long) jsonObj.get("ass_type_code"));

					mapVo.put("ass_type_code", jsonObj.get("ass_type_code"));

				} else {

					err_sb.append("类别编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("acc_type_code"))) {

					assNoDict.setAcc_type_code((String) jsonObj.get("acc_type_code"));

					mapVo.put("acc_type_code", jsonObj.get("acc_type_code"));

				} else {

					err_sb.append("财务分类编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_unit"))) {

					assNoDict.setAss_unit((String) jsonObj.get("ass_unit"));

					mapVo.put("ass_unit", jsonObj.get("ass_unit"));

				} else {

					err_sb.append("单位为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("is_measure"))) {

					assNoDict.setIs_measure(Integer.valueOf((String) jsonObj.get("is_measure")));

					mapVo.put("is_measure", jsonObj.get("is_measure"));

				} else {

					err_sb.append("是否计量为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("is_depre"))) {

					assNoDict.setIs_depre(Integer.valueOf((String) jsonObj.get("is_depre")));

					mapVo.put("is_depre", jsonObj.get("is_depre"));

				} else {

					err_sb.append("是否折旧为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_depre_code"))) {

					assNoDict.setAss_depre_code((String) jsonObj.get("ass_depre_code"));

					mapVo.put("ass_depre_code", jsonObj.get("ass_depre_code"));

				} else {

					err_sb.append("折旧方法编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("depre_years"))) {

					assNoDict.setDepre_years(Integer.valueOf((String) jsonObj.get("depre_years")));

					mapVo.put("depre_years", jsonObj.get("depre_years"));

				} else {

					err_sb.append("折旧年限为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assNoDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_spec"))) {

					assNoDict.setAss_spec((String) jsonObj.get("ass_spec"));

					mapVo.put("ass_spec", jsonObj.get("ass_spec"));

				} else {

					err_sb.append("规格为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_model"))) {

					assNoDict.setAss_model((String) jsonObj.get("ass_model"));

					mapVo.put("ass_model", jsonObj.get("ass_model"));

				} else {

					err_sb.append("型号为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("fac_id"))) {

					assNoDict.setFac_id((String) jsonObj.get("fac_id"));

					mapVo.put("fac_id", jsonObj.get("fac_id"));

				} else {

					err_sb.append("生产厂商ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("fac_no"))) {

					assNoDict.setFac_no((String) jsonObj.get("fac_no"));

					mapVo.put("fac_no", jsonObj.get("fac_no"));

				} else {

					err_sb.append("生产厂商NO为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {

					assNoDict.setVen_id((String) jsonObj.get("ven_id"));

					mapVo.put("ven_id", jsonObj.get("ven_id"));

				} else {

					err_sb.append("主要供应商ID为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {

					assNoDict.setVen_no((String) jsonObj.get("ven_no"));

					mapVo.put("ven_no", jsonObj.get("ven_no"));

				} else {

					err_sb.append("主要供应商NO为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("usage_code"))) {

					assNoDict.setUsage_code((String) jsonObj.get("usage_code"));

					mapVo.put("usage_code", jsonObj.get("usage_code"));

				} else {

					err_sb.append("资产用途为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("gb_code"))) {

					assNoDict.setGb_code((String) jsonObj.get("gb_code"));

					mapVo.put("gb_code", jsonObj.get("gb_code"));

				} else {

					err_sb.append("国标码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {

					assNoDict.setSpell_code((String) jsonObj.get("spell_code"));

					mapVo.put("spell_code", jsonObj.get("spell_code"));

				} else {

					err_sb.append("拼音码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {

					assNoDict.setWbx_code((String) jsonObj.get("wbx_code"));

					mapVo.put("wbx_code", jsonObj.get("wbx_code"));

				} else {

					err_sb.append("五笔码为空  ");

				}

				AssNoDict data_exc_extis = assNoDictService.queryAssNoDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assNoDict.setError_type(err_sb.toString());

					list_err.add(assNoDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

					try {

						String dataJson = assNoDictService.addAssNoDict(mapVo);

					} catch (Exception e) {

						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}

				}

			}

		} catch (DataAccessException e) {

			AssNoDict data_exc = new AssNoDict();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

}
