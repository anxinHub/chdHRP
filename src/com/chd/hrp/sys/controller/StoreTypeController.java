/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.io.IOException;
import java.util.*;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.sys.entity.FacType;
import com.chd.hrp.sys.entity.StoreType;
import com.chd.hrp.sys.serviceImpl.StoreTypeServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class StoreTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(StoreTypeController.class);
	
	
	@Resource(name = "storeTypeService")
	private final StoreTypeServiceImpl storeTypeService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/storetype/storeTypeMainPage", method = RequestMethod.GET)
	public String storeTypeMainPage(Model mode) throws Exception {

		return "hrp/sys/storetype/storeTypeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/storetype/storeTypeAddPage", method = RequestMethod.GET)
	public String storeTypeAddPage(Model mode) throws Exception {

		return "hrp/sys/storetype/storeTypeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/storetype/addStoreType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String storeTypeJson = storeTypeService.addStoreType(mapVo);

		return JSONObject.parseObject(storeTypeJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/storetype/queryStoreType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String storeType = storeTypeService.queryStoreType(getPage(mapVo));

		return JSONObject.parseObject(storeType);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/storetype/deleteStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStoreType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("type_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String storeTypeJson = storeTypeService.deleteBatchStoreType(listVo);
	   return JSONObject.parseObject(storeTypeJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/storetype/storeTypeUpdatePage", method = RequestMethod.GET)
	
	public String storeTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        StoreType storeType = new StoreType();
		storeType = storeTypeService.queryStoreTypeByCode(mapVo);
		mode.addAttribute("group_id", storeType.getGroup_id());
		mode.addAttribute("hos_id", storeType.getHos_id());
		mode.addAttribute("type_code", storeType.getType_code());
		mode.addAttribute("type_name", storeType.getType_name());
		mode.addAttribute("spell_code", storeType.getSpell_code());
		mode.addAttribute("wbx_code", storeType.getWbx_code());
		mode.addAttribute("is_stop", storeType.getIs_stop());
		mode.addAttribute("note", storeType.getNote());
		
		return "hrp/sys/storetype/storeTypeUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/storetype/updateStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String storeTypeJson = storeTypeService.updateStoreType(mapVo);
		
		return JSONObject.parseObject(storeTypeJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/storetype/importStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String storeTypeJson = storeTypeService.importStoreType(mapVo);
		
		return JSONObject.parseObject(storeTypeJson);
	}

	
	@RequestMapping(value = "/hrp/sys/storetype/storeTypeImportPage", method = RequestMethod.GET)
	public String facTypeImportPage(Model mode) throws Exception {

		return "hrp/sys/storetype/storeTypeImport";

	}

	@RequestMapping(value = "hrp/sys/storetype/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sys\\医院信息", "仓库分类.xls");

		return null;
	}
	
	/**
	 * 仓库类别导入<BR>
	 * 导入
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "/hrp/sys/storetype/readStoreTypeFiles", method = RequestMethod.POST)
	public String readStoreTypeFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws Exception {

		List<StoreType> list_err = new ArrayList<StoreType>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				StoreType  storeType = new StoreType();

				String temp[] = list.get(i);// 行

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(temp[0])) {

					mapVo.put("type_code", temp[0]);

					storeType.setType_code(temp[0]);

				} else {

					err_sb.append("仓库分类编码为空");
				}

				if (StringTool.isNotBlank(temp[1])) {

					mapVo.put("type_name", temp[1]);

					storeType.setType_name(temp[1]);

				} else {

					err_sb.append("仓库分类名称为空");
				}

				if (StringTool.isNotBlank(temp[2])) {

					mapVo.put("is_stop", temp[2]);

					storeType.setIs_stop(Integer.parseInt(temp[2].toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (temp.length - 1 >= 3) {

					if (StringTool.isNotBlank(temp[3])) {

						mapVo.put("note", temp[3]);

						storeType.setNote(temp[3]);

					}

				} else {

					mapVo.put("note", "");

					storeType.setNote("");
				}

				StoreType eStoreType = storeTypeService.queryStoreTypeByCode(mapVo);

				if (eStoreType != null) {

					err_sb.append("仓库分类编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					storeType.setError_type(err_sb.toString());

					list_err.add(storeType);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));

					storeTypeService.addStoreType(mapVo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			StoreType data_exc = new StoreType();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	@RequestMapping(value = "/hrp/sys/storetype/addImportStoreTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImportFacTypeDict(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<StoreType> list_err = new ArrayList<StoreType>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				StringBuffer err_sb = new StringBuffer();

				StoreType storeType = new StoreType();

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(jsonObj.get("type_code"))) {

					mapVo.put("type_code", jsonObj.get("type_code").toString());

					storeType.setType_code(jsonObj.get("type_code").toString());

				} else {

					err_sb.append("仓库分类编码为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("type_name"))) {

					mapVo.put("type_name", jsonObj.get("type_name").toString());

					storeType.setType_name(jsonObj.get("type_name").toString());

				} else {

					err_sb.append("仓库分类名称为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					mapVo.put("is_stop",Integer.parseInt(jsonObj.get("is_stop").toString()));

					storeType.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					mapVo.put("note", jsonObj.get("note").toString());

					storeType.setNote(jsonObj.get("note").toString());

				} else {

					mapVo.put("note", "");

					storeType.setNote("");
				}

				StoreType eStoreType = storeTypeService.queryStoreTypeByCode(mapVo);

				if (eStoreType != null) {

					err_sb.append("仓库分类编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					storeType.setError_type(err_sb.toString());

					list_err.add(storeType);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));

					storeTypeService.addStoreType(mapVo);
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

