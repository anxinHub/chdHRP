package com.chd.hrp.med.controller.info.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedLocationType;
import com.chd.hrp.med.serviceImpl.info.basic.MedLocationTypeServiceImpl;

/**
 * 
 * @Description: 08401 货位分类字典
 * @Table: MED_LOCATION_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedLocationTypeController extends BaseController {

	private static Logger logger = Logger
			.getLogger(MedLocationTypeController.class);

	// 引入Service服务
	@Resource(name = "medLocationTypeService")
	private final MedLocationTypeServiceImpl medLocationTypeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/medLocationTypeMainPage", method = RequestMethod.GET)
	public String medLocationTypeMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/location/type/medLocationTypeMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/medLocationTypeAddPage", method = RequestMethod.GET)
	public String medLocationTypeAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/location/type/medLocationTypeAdd";

	}

	/**
	 * @Description 添加数据 08401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/addMedLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedLocationType(
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

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get(
				"location_type_name").toString()));
		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("location_type_name").toString()));

		// System.out.println("********** spell_code:"+mapVo.get("spell_code"));
		// System.out.println("********** wbx_code:"+mapVo.get("wbx_code"));

		String medLocationTypeJson = medLocationTypeService.add(mapVo);

		return JSONObject.parseObject(medLocationTypeJson);

	}

	/**
	 * @Description 更新跳转页面 08401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/medLocationTypeUpdatePage", method = RequestMethod.GET)
	public String medLocationTypeUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		MedLocationType medLocationType = new MedLocationType();

		medLocationType = medLocationTypeService.queryByCode(mapVo);

		mode.addAttribute("location_type_id",
				medLocationType.getLocation_type_id());
		mode.addAttribute("location_type_code",
				medLocationType.getLocation_type_code());
		mode.addAttribute("location_type_name",
				medLocationType.getLocation_type_name());
		// mode.addAttribute("spell_code", medLocationType.getSpell_code());
		// mode.addAttribute("wbx_code", medLocationType.getWbx_code());
		mode.addAttribute("is_stop", medLocationType.getIs_stop());
		mode.addAttribute("note", medLocationType.getNote());

		return "hrp/med/info/basic/location/type/medLocationTypeUpdate";
	}

	/**
	 * @Description 更新数据 08401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/updateMedLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedLocationType(
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

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get(
				"location_type_name").toString()));

		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("location_type_name").toString()));

		String medLocationTypeJson = medLocationTypeService.update(mapVo);

		return JSONObject.parseObject(medLocationTypeJson);
	}

	/**
	 * @Description 更新数据 08401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/addOrUpdateMedLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedLocationType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String medLocationTypeJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class,
				mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}

			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}

			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}

			detailVo.put(
					"spell_code",
					StringTool.toPinyinShouZiMu(detailVo.get(
							"location_type_name").toString()));

			detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get(
					"location_type_name").toString()));

			medLocationTypeJson = medLocationTypeService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(medLocationTypeJson);
	}

	/**
	 * @Description 删除数据 08401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/deleteMedLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedLocationType(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("location_type_id", ids[3]);

			listVo.add(mapVo);

		}

		String medLocationTypeJson = medLocationTypeService.deleteBatch(listVo);

		return JSONObject.parseObject(medLocationTypeJson);

	}

	/**
	 * @Description 查询数据 08401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/queryMedLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedLocationType(
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

		String medLocationType = medLocationTypeService.query(mapVo);

		return JSONObject.parseObject(medLocationType);

	}

	/**
	 * @Description 导入跳转页面 08401 货位分类字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/medLocationTypeImportPage", method = RequestMethod.GET)
	public String medLocationTypeImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/location/type/medLocationTypeImport";

	}

	/**
	 * @Description 下载导入模版 08401 货位分类字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate","货位分类.xls");

		return null;
	}

	/**
	 * @Description 导入数据 08401 货位分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */

	@RequestMapping(value = "/hrp/med/info/basic/location/type/readMedLocationTypeFiles", method = RequestMethod.POST)
	public String readMedLocationTypeFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		Map<String, Object> is_Stop_Map = new HashMap<String, Object>();
		
		is_Stop_Map.put("0", "0");
		
		is_Stop_Map.put("1", "1");
		
		is_Stop_Map.put("否", "0");
		
		is_Stop_Map.put("是", "1");
		
		List<Map<String, Object>> addlistMaps = new ArrayList<Map<String,Object>>();
		
		List<MedLocationType> list_err = new ArrayList<MedLocationType>();
		
		Map<String, Map<String,Object>> exitesMap = new HashMap<String, Map<String,Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();

				MedLocationType medLocationType = new MedLocationType();

				String temp[] = list.get(i);
				
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (ExcelReader.validExceLColunm(temp, 0)) {

					medLocationType.setLocation_type_code(temp[0]);
					
					mapVo.put("location_type_code", temp[0]);

				} else{

					err_sb.append("货位分类编码为空!");

				}
				
				if(exitesMap.get(temp[0]) != null){

					err_sb.append("货位分类编码已存在!");

				}

				if (ExcelReader.validExceLColunm(temp, 1)) {

					medLocationType.setLocation_type_name(temp[1]);
					
					mapVo.put("location_type_name", temp[1]);

				} else {

					err_sb.append("货位分类名称为空!");

				}


				if (ExcelReader.validExceLColunm(temp, 2)) {

					if(is_Stop_Map.containsKey(temp[2])){
						
						medLocationType.setIs_stop(Integer.parseInt(is_Stop_Map.get(temp[2]).toString()));
						
						mapVo.put("is_stop", Integer.parseInt(is_Stop_Map.get(temp[2]).toString()));
						
					}else {
						
						err_sb.append("是否停用输入错误  ");
					}

				} else {

					err_sb.append("是否停用为空  ");

				}

				if (ExcelReader.validExceLColunm(temp, 3)) {

					medLocationType.setNote(temp[3]);
					
					mapVo.put("note", temp[3]);

				} else {

                    medLocationType.setNote(null);
					
					mapVo.put("note", null);

				}

				if (err_sb.toString().length() > 0) {

					medLocationType.setError_type(err_sb.toString());

					list_err.add(medLocationType);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("location_type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("location_type_name").toString()));

					exitesMap.put(temp[0], mapVo);
					
					addlistMaps.add(mapVo);

				}

			}

			if(addlistMaps.size() > 0){
				
				medLocationTypeService.addBatch(addlistMaps);
				
			}
			
		} catch (DataAccessException e) {

			MedLocationType data_exc = new MedLocationType();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 08401 货位分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/info/basic/location/type/addBatchMedLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedLocationType(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		
		List<Map<String, Object>> listMaps = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> is_Stop_Map = new HashMap<String, Object>();
		
		is_Stop_Map.put("0", "0");
		
		is_Stop_Map.put("1", "1");
		
		is_Stop_Map.put("否", "0");
		
		is_Stop_Map.put("是", "1");

		List<MedLocationType> list_err = new ArrayList<MedLocationType>();

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

				MedLocationType medLocationType = new MedLocationType();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("location_type_code"))) {

					medLocationType.setLocation_type_code(jsonObj.get("location_type_code").toString());
					
					mapVo.put("location_type_code",jsonObj.get("location_type_code").toString());
					
				} else {

					err_sb.append("货位分类编码为空  ");

				}

				
				if (StringTool.isNotBlank(jsonObj.get("location_type_name"))) {

					medLocationType.setLocation_type_name(jsonObj.get("location_type_name").toString());
					
					mapVo.put("location_type_name",jsonObj.get("location_type_name").toString());
					
				} else {

					err_sb.append("货位分类名称为空  ");

				}


				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

	                   if(is_Stop_Map.containsKey(jsonObj.get("is_stop"))){
						
						medLocationType.setIs_stop(Integer.parseInt(is_Stop_Map.get(jsonObj.get("is_stop")).toString()));
						
						mapVo.put("is_stop", Integer.parseInt(is_Stop_Map.get(jsonObj.get("is_stop")).toString()));
						
					}else {
						
						 err_sb.append("是否停用输入错误  ");
					}

					
				} else {

					err_sb.append("是否停用为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					medLocationType.setNote(jsonObj.get("note").toString());
					
					mapVo.put("note", jsonObj.get("note").toString());
					
				} else {

					medLocationType.setNote(null);
					
					mapVo.put("note", null);

				}

				if (err_sb.toString().length() > 0) {

					medLocationType.setError_type(err_sb.toString());

					list_err.add(medLocationType);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("location_type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("location_type_name").toString()));
					
					listMaps.add(mapVo);

				}

			}
			
			
			if(listMaps.size() > 0){
				
				medLocationTypeService.addBatch(listMaps);
				
			}


		} catch (DataAccessException e) {

			MedLocationType data_exc = new MedLocationType();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

}