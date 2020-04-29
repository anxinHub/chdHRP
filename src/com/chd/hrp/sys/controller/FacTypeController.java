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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.sys.entity.FacType;
import com.chd.hrp.sys.entity.SupType;
import com.chd.hrp.sys.serviceImpl.FacTypeServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class FacTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(FacTypeController.class);
	
	
	@Resource(name = "facTypeService")
	private final FacTypeServiceImpl facTypeService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/factype/facTypeMainPage", method = RequestMethod.GET)
	public String facTypeMainPage(Model mode) throws Exception {

		return "hrp/sys/factype/facTypeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/factype/facTypeAddPage", method = RequestMethod.GET)
	public String facTypeAddPage(Model mode) throws Exception {

		return "hrp/sys/factype/facTypeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/factype/addFacType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addFacType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String facTypeJson = facTypeService.addFacType(mapVo);

		return JSONObject.parseObject(facTypeJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/factype/queryFacType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryFacType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String facType = facTypeService.queryFacType(getPage(mapVo));

		return JSONObject.parseObject(facType);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/factype/deleteFacType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFacType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("type_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String facTypeJson = facTypeService.deleteBatchFacType(listVo);
	   return JSONObject.parseObject(facTypeJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/factype/facTypeUpdatePage", method = RequestMethod.GET)
	
	public String facTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        FacType facType = new FacType();
		facType = facTypeService.queryFacTypeByCode(mapVo);
		mode.addAttribute("group_id", facType.getGroup_id());
		mode.addAttribute("hos_id", facType.getHos_id());
		mode.addAttribute("type_code", facType.getType_code());
		mode.addAttribute("type_name", facType.getType_name());
		mode.addAttribute("spell_code", facType.getSpell_code());
		mode.addAttribute("wbx_code", facType.getWbx_code());
		mode.addAttribute("is_stop", facType.getIs_stop());
		mode.addAttribute("note", facType.getNote());
		
		return "hrp/sys/factype/facTypeUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/factype/updateFacType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateFacType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String facTypeJson = facTypeService.updateFacType(mapVo);
		
		return JSONObject.parseObject(facTypeJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/factype/importFacType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importFacType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String facTypeJson = facTypeService.importFacType(mapVo);
		
		return JSONObject.parseObject(facTypeJson);
	}
	
	
	@RequestMapping(value = "/hrp/sys/factype/facTypeImportPage", method = RequestMethod.GET)
	public String facTypeImportPage(Model mode) throws Exception {

		return "hrp/sys/factype/facTypeImport";

	}

	@RequestMapping(value = "hrp/sys/factype/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sys\\医院信息", "生产厂商类别.xls");

		return null;
	}

	/**
	* 生产厂商分类
	* 导入
	*/
	@RequestMapping(value = "/hrp/sys/factype/importReadFacTypeFiles", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importReadFacTypeFiles(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			return JSON.parseObject(facTypeService.importReadFacTypeFiles(mapVo));
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	@RequestMapping(value = "/hrp/sys/factype/addImportFacTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImportFacTypeDict(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<FacType> list_err = new ArrayList<FacType>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				StringBuffer err_sb = new StringBuffer();

				FacType facType = new FacType();

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(jsonObj.get("type_code"))) {

					mapVo.put("type_code", jsonObj.get("type_code").toString());

					facType.setType_code(jsonObj.get("type_code").toString());

				} else {

					err_sb.append("生产厂商分类编码为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("type_name"))) {

					mapVo.put("type_name", jsonObj.get("type_name").toString());

					facType.setType_name(jsonObj.get("type_name").toString());

				} else {

					err_sb.append("生产厂商分类名称为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					mapVo.put("is_stop",
							Integer.parseInt(jsonObj.get("is_stop").toString()));

					facType.setIs_stop(Integer.parseInt(jsonObj.get("is_stop")
							.toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					mapVo.put("note", jsonObj.get("note").toString());

					facType.setNote(jsonObj.get("note").toString());

				} else {

					mapVo.put("note", "");

					facType.setNote("");
				}

				FacType eFacType = facTypeService.queryFacTypeByCode(mapVo);

				if (eFacType != null) {

					err_sb.append("生产厂商分类编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					facType.setError_type(err_sb.toString());

					list_err.add(facType);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));

					facTypeService.addFacType(mapVo);
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

