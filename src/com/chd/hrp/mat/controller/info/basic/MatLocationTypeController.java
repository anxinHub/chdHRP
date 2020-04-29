package com.chd.hrp.mat.controller.info.basic;

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
import com.chd.hrp.mat.entity.MatLocationType;
import com.chd.hrp.mat.serviceImpl.info.basic.MatLocationTypeServiceImpl;

/**
 * 
 * @Description: 04401 货位分类字典
 * @Table: MAT_LOCATION_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatLocationTypeController extends BaseController {

	private static Logger logger = Logger
			.getLogger(MatLocationTypeController.class);

	// 引入Service服务
	@Resource(name = "matLocationTypeService")
	private final MatLocationTypeServiceImpl matLocationTypeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/matLocationTypeMainPage", method = RequestMethod.GET)
	public String matLocationTypeMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/location/type/matLocationTypeMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/matLocationTypeAddPage", method = RequestMethod.GET)
	public String matLocationTypeAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/location/type/matLocationTypeAdd";

	}

	/**
	 * @Description 添加数据 04401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/addMatLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatLocationType(
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

		String matLocationTypeJson = matLocationTypeService.add(mapVo);

		return JSONObject.parseObject(matLocationTypeJson);

	}

	/**
	 * @Description 更新跳转页面 04401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/matLocationTypeUpdatePage", method = RequestMethod.GET)
	public String matLocationTypeUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());

		MatLocationType matLocationType = new MatLocationType();

		matLocationType = matLocationTypeService.queryByCode(mapVo);

		mode.addAttribute("location_type_id",
				matLocationType.getLocation_type_id());
		mode.addAttribute("location_type_code",
				matLocationType.getLocation_type_code());
		mode.addAttribute("location_type_name",
				matLocationType.getLocation_type_name());
		// mode.addAttribute("spell_code", matLocationType.getSpell_code());
		// mode.addAttribute("wbx_code", matLocationType.getWbx_code());
		mode.addAttribute("is_stop", matLocationType.getIs_stop());
		mode.addAttribute("note", matLocationType.getNote());

		return "hrp/mat/info/basic/location/type/matLocationTypeUpdate";
	}

	/**
	 * @Description 更新数据 04401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/updateMatLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatLocationType(
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

		String matLocationTypeJson = matLocationTypeService.update(mapVo);

		return JSONObject.parseObject(matLocationTypeJson);
	}

	/**
	 * @Description 更新数据 04401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/addOrUpdateMatLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMatLocationType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String matLocationTypeJson = "";

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

			matLocationTypeJson = matLocationTypeService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(matLocationTypeJson);
	}

	/**
	 * @Description 删除数据 04401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/deleteMatLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatLocationType(
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

		String matLocationTypeJson = matLocationTypeService.deleteBatch(listVo);

		return JSONObject.parseObject(matLocationTypeJson);

	}

	/**
	 * @Description 查询数据 04401 货位分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/queryMatLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatLocationType(
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

		String matLocationType = matLocationTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(matLocationType);

	}

	/**
	 * @Description 导入跳转页面 04401 货位分类字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/matLocationTypeImportPage", method = RequestMethod.GET)
	public String matLocationTypeImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/location/type/matLocationTypeImport";

	}

	/**
	 * @Description 下载导入模版 04401 货位分类字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate","货位分类.xls");

		return null;
	}

	/**
	 * @Description 导入数据 04401 货位分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */

	@RequestMapping(value = "/hrp/mat/info/basic/location/type/readMatLocationTypeFiles", method = RequestMethod.POST)
	public String readMatLocationTypeFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		Map<String, Object> is_Stop_Map = new HashMap<String, Object>();
		
		is_Stop_Map.put("0", "0");
		
		is_Stop_Map.put("1", "1");
		
		is_Stop_Map.put("否", "0");
		
		is_Stop_Map.put("是", "1");
		
		List<Map<String, Object>> addlistMaps = new ArrayList<Map<String,Object>>();
		
		List<MatLocationType> list_err = new ArrayList<MatLocationType>();
		
		Map<String, Map<String,Object>> exitesMap = new HashMap<String, Map<String,Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();

				MatLocationType matLocationType = new MatLocationType();

				String temp[] = list.get(i);
				
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (ExcelReader.validExceLColunm(temp, 0)) {

					matLocationType.setLocation_type_code(temp[0]);
					
					mapVo.put("location_type_code", temp[0]);

				} else{

					err_sb.append("货位分类编码为空!");

				}
				
				if(exitesMap.get(temp[0]) != null){

					err_sb.append("货位分类编码已存在!");

				}

				if (ExcelReader.validExceLColunm(temp, 1)) {

					matLocationType.setLocation_type_name(temp[1]);
					
					mapVo.put("location_type_name", temp[1]);

				} else {

					err_sb.append("货位分类名称为空!");

				}


				if (ExcelReader.validExceLColunm(temp, 2)) {

					if(is_Stop_Map.containsKey(temp[2])){
						
						matLocationType.setIs_stop(Integer.parseInt(is_Stop_Map.get(temp[2]).toString()));
						
						mapVo.put("is_stop", Integer.parseInt(is_Stop_Map.get(temp[2]).toString()));
						
					}else {
						
						err_sb.append("是否停用输入错误  ");
					}

				} else {

					err_sb.append("是否停用为空  ");

				}

				if (ExcelReader.validExceLColunm(temp, 3)) {

					matLocationType.setNote(temp[3]);
					
					mapVo.put("note", temp[3]);

				} else {

                    matLocationType.setNote(null);
					
					mapVo.put("note", null);

				}

				if (err_sb.toString().length() > 0) {

					matLocationType.setError_type(err_sb.toString());

					list_err.add(matLocationType);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("location_type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("location_type_name").toString()));

					exitesMap.put(temp[0], mapVo);
					
					addlistMaps.add(mapVo);

				}

			}

			if(addlistMaps.size() > 0){
				
				matLocationTypeService.addBatch(addlistMaps);
				
			}
			
		} catch (DataAccessException e) {

			MatLocationType data_exc = new MatLocationType();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 04401 货位分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/location/type/addBatchMatLocationType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMatLocationType(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		
		List<Map<String, Object>> listMaps = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> is_Stop_Map = new HashMap<String, Object>();
		
		is_Stop_Map.put("0", "0");
		
		is_Stop_Map.put("1", "1");
		
		is_Stop_Map.put("否", "0");
		
		is_Stop_Map.put("是", "1");

		List<MatLocationType> list_err = new ArrayList<MatLocationType>();

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

				MatLocationType matLocationType = new MatLocationType();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("location_type_code"))) {

					matLocationType.setLocation_type_code(jsonObj.get("location_type_code").toString());
					
					mapVo.put("location_type_code",jsonObj.get("location_type_code").toString());
					
				} else {

					err_sb.append("货位分类编码为空  ");

				}

				
				if (StringTool.isNotBlank(jsonObj.get("location_type_name"))) {

					matLocationType.setLocation_type_name(jsonObj.get("location_type_name").toString());
					
					mapVo.put("location_type_name",jsonObj.get("location_type_name").toString());
					
				} else {

					err_sb.append("货位分类名称为空  ");

				}


				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

	                   if(is_Stop_Map.containsKey(jsonObj.get("is_stop"))){
						
						matLocationType.setIs_stop(Integer.parseInt(is_Stop_Map.get(jsonObj.get("is_stop")).toString()));
						
						mapVo.put("is_stop", Integer.parseInt(is_Stop_Map.get(jsonObj.get("is_stop")).toString()));
						
					}else {
						
						 err_sb.append("是否停用输入错误  ");
					}

					
				} else {

					err_sb.append("是否停用为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					matLocationType.setNote(jsonObj.get("note").toString());
					
					mapVo.put("note", jsonObj.get("note").toString());
					
				} else {

					matLocationType.setNote(null);
					
					mapVo.put("note", null);

				}

				if (err_sb.toString().length() > 0) {

					matLocationType.setError_type(err_sb.toString());

					list_err.add(matLocationType);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("location_type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("location_type_name").toString()));
					
					listMaps.add(mapVo);

				}

			}
			
			
			if(listMaps.size() > 0){
				
				matLocationTypeService.addBatch(listMaps);
				
			}


		} catch (DataAccessException e) {

			MatLocationType data_exc = new MatLocationType();

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