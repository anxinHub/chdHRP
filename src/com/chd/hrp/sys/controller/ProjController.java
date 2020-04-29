/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccProjAttr;
import com.chd.hrp.acc.service.AccProjAttrService;
import com.chd.hrp.sys.entity.Proj;
import com.chd.hrp.sys.entity.ProjType;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.ProjDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.ProjServiceImpl;
import com.chd.hrp.sys.serviceImpl.ProjTypeServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class ProjController extends BaseController{
	private static Logger logger = Logger.getLogger(ProjController.class);
	
	
	@Resource(name = "projService")
	private final ProjServiceImpl projService = null;
	
	@Resource(name = "projDictService")
	private final ProjDictServiceImpl projDictService = null;
   
	@Resource(name = "projTypeService")
	private final ProjTypeServiceImpl projTypeService = null;
	
	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	@Resource(name = "accProjAttrService")
	private final AccProjAttrService accProjAttrService = null;
	
	
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/proj/projMainPage", method = RequestMethod.GET)
	public String projMainPage(Model mode) throws Exception {

		return "hrp/sys/proj/projMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/proj/projAddPage", method = RequestMethod.GET)
	public String projAddPage(Model mode) throws Exception {

		return "hrp/sys/proj/projAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/proj/addProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String projJson = projService.addProj(mapVo);
		return JSONObject.parseObject(projJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/proj/queryProj", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		if(mapVo.get("group_id")==null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id")==null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code")==null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String proj = projService.queryProj(getPage(mapVo));

		return JSONObject.parseObject(proj);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/proj/deleteProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("proj_id", id.split("@")[2]);
            mapVo.put("proj_code", id.split("@")[3]);
            listVo.add(mapVo);
        }
		String projJson = projService.deleteBatchProj(listVo);
	   return JSONObject.parseObject(projJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/proj/projUpdatePage", method = RequestMethod.GET)
	
	public String projUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		AccProjAttr accProjAttr = accProjAttrService.queryProjByCode(mapVo);
		if(accProjAttr != null){
			mode.addAttribute("proj_id", accProjAttr.getProj_id());
			mode.addAttribute("group_id", accProjAttr.getGroup_id());
			mode.addAttribute("hos_id", accProjAttr.getHos_id());
			mode.addAttribute("proj_code", accProjAttr.getProj_code());
			mode.addAttribute("type_code", accProjAttr.getType_code());
			mode.addAttribute("type_name", accProjAttr.getType_name());
			mode.addAttribute("proj_name", accProjAttr.getProj_name());
			mode.addAttribute("proj_simple", accProjAttr.getProj_simple());
			mode.addAttribute("is_stop", accProjAttr.getIs_stop());
			mode.addAttribute("is_disable", accProjAttr.getIs_disable());
			mode.addAttribute("level_code", accProjAttr.getLevel_code());
			mode.addAttribute("use_code", accProjAttr.getUse_code());
			mode.addAttribute("con_emp_id", accProjAttr.getCon_emp_id());
			mode.addAttribute("level_name", accProjAttr.getLevel_name());
			mode.addAttribute("use_name", accProjAttr.getUse_name());
			mode.addAttribute("con_emp_name", accProjAttr.getCon_emp_name());
			mode.addAttribute("con_phone", accProjAttr.getCon_phone());
			mode.addAttribute("acc_emp_id", accProjAttr.getAcc_emp_id());
			mode.addAttribute("acc_emp_name", accProjAttr.getAcc_emp_name());
			mode.addAttribute("acc_phone", accProjAttr.getAcc_phone());
			mode.addAttribute("app_emp_id", accProjAttr.getApp_emp_id());
			mode.addAttribute("app_emp_name", accProjAttr.getApp_emp_name());
			mode.addAttribute("dept_id", accProjAttr.getDept_id());
			mode.addAttribute("dept_name", accProjAttr.getDept_name());
			mode.addAttribute("app_date", DateUtil.dateToString(accProjAttr.getApp_date(), "yyyy-MM-dd"));
			mode.addAttribute("app_phone", accProjAttr.getApp_phone());
			mode.addAttribute("email", accProjAttr.getEmail());
			mode.addAttribute("note", accProjAttr.getNote());
			mode.addAttribute("sort_code", accProjAttr.getSort_code());
		}
		return "hrp/sys/proj/projUpdate";
		
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/proj/updateProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projJson = projService.updateProj(mapVo);
		
		return JSONObject.parseObject(projJson);
	}
	
	@RequestMapping(value = "/hrp/sys/proj/projImportPage", method = RequestMethod.GET)
	public String deptImportPage(Model mode) throws Exception {

		return "hrp/sys/proj/projImport";
	}

	@RequestMapping(value = "hrp/sys/proj/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sys\\医院信息", "项目维护.xls");
		return null;
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/proj/readProjFiles", method = RequestMethod.POST)
	public String readProjFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		Map<String, Object> entityMap = new HashMap<String, Object>();

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("proj_code", "HOS_PROJ");
		entityMap.put("mod_code", "00");

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String s_view = Strings.removeFirst(rules_v);

		entityMap.put("copy_code", SessionManager.getCopyCode());
		List<Proj> list_err = new ArrayList<Proj>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				Proj proj = new Proj();

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

				if (StringTool.isNotBlank(temp[0])) {
					Object proj_code = temp[0].length();
					if (proj_code != rules_level_length.get(1)) {
						err_sb.append("编码不符合要求,请重新添加.编码规则长度：" + rules_level_length.get(1));
					}
					proj.setProj_code(temp[0]);
					mapVo.put("proj_code", temp[0]);

				} else {

					err_sb.append("项目编码为空  ");
				}

				if (StringTool.isNotBlank(temp[1])) {

					proj.setType_code(temp[1]);
					proj.setType_name(temp[2]);
					mapVo.put("type_code", temp[1]);

				} else {

					proj.setType_code("");
					proj.setType_name("");
					mapVo.put("type_code", "");
				}

				if (StringTool.isNotBlank(temp[3])) {

					proj.setProj_name(temp[3]);
					mapVo.put("proj_name", temp[3]);

				} else {

					err_sb.append("项目名称为空  ");
				}

				if (StringTool.isNotBlank(temp[4])) {

					proj.setProj_simple(temp[4]);
					mapVo.put("proj_simple", temp[4]);

				} else {

					err_sb.append("项目简称为空  ");
				}

				mapVo.put("sort_code", "系统生成");

				if (StringTool.isNotBlank(temp[5])) {

					proj.setIs_stop(Integer.valueOf(temp[5]));
					mapVo.put("is_stop", temp[5]);

				} else {

					err_sb.append("是否停用为空  ");
				}

				if (temp.length - 1 >= 6) {

					if (StringTool.isNotBlank(temp[6])) {

						proj.setNote(temp[6]);
						mapVo.put("note", temp[6]);

					}
				} else {

					proj.setNote("");
					mapVo.put("note", "");
				}

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));

				byCodeMap.put("proj_code", mapVo.get("proj_code"));

				Proj data_exc_extis = projService.queryProjByCode(byCodeMap);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");
				}
				byCodeMap.put("type_code", mapVo.get("type_code"));

				ProjType projtype = projTypeService.queryProjTypeByCode(byCodeMap);

				if (projtype == null) {

					err_sb.append("类别编码不存在  ");

				}
				if (err_sb.toString().length() > 0) {

					proj.setError_type(err_sb.toString());

					list_err.add(proj);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("proj_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("proj_name").toString()));
					String dataJson = projService.addProj(mapVo);
				}
			}

		} catch (DataAccessException e) {

			Proj data_exc = new Proj();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}
	
	//导入保存
	@RequestMapping(value = "/hrp/sys/proj/addImportProj", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addImportProj(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		List<Proj> list_err = new ArrayList<Proj>();
		
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
			
			Proj Proj = new Proj();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
					if (StringTool.isNotBlank(jsonObj.get("proj_code"))) {
						
					Proj.setProj_code((String)jsonObj.get("proj_code"));
		    		mapVo.put("proj_code", jsonObj.get("proj_code"));
		    		} else {
						
						err_sb.append("项目编码为空  ");
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
						
					Proj.setType_code((String)jsonObj.get("type_code"));
		    		mapVo.put("type_code", jsonObj.get("type_code"));
		    		} else {
						
						err_sb.append("类型编码为空  ");
					}
					
					if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
						
					Proj.setType_name((String)jsonObj.get("type_name"));
		    		mapVo.put("type_name", jsonObj.get("type_name"));
		    		} else {
						
						err_sb.append("类型名称为空  ");
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_name"))) {
						
					Proj.setProj_name((String)jsonObj.get("proj_name"));
		    		mapVo.put("proj_name", jsonObj.get("proj_name"));
		    		} else {
						
						err_sb.append("项目名称为空  ");
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_simple"))) {
						
					Proj.setProj_simple((String)jsonObj.get("proj_simple"));
		    		mapVo.put("proj_simple", jsonObj.get("proj_simple"));
		    		} else {
						
						err_sb.append("项目简称为空  ");
					}
					
//					if (StringTool.isNotBlank(jsonObj.get("sort_code"))) {
//						
//					Proj.setSort_code(Long.valueOf((String)jsonObj.get("sort_code")));
//		    		mapVo.put("sort_code", jsonObj.get("sort_code"));
//		    		} else {
//						
//						err_sb.append("排序号为空  ");
//					}
					
					if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
						
					Proj.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
		    		mapVo.put("is_stop", jsonObj.get("is_stop"));
		    		} else {
						
						err_sb.append("是否停用为空  ");
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					Proj.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
					}
					
					
				Proj data_exc_extis = projService.queryProjByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					Proj.setError_type(err_sb.toString());
					
					list_err.add(Proj);
					
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("proj_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("proj_name").toString()));
					String dataJson = projService.addProj(mapVo);
				}
			}
			
		} catch (DataAccessException e) {
			
			Proj data_exc = new Proj();
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
		
	}
		

	@RequestMapping(value = "/hrp/sys/proj/projChangePage", method = RequestMethod.GET)
	public String projChangePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		  Proj proj = new Proj();
			proj = projService.queryProjByCode(mapVo);
			mode.addAttribute("group_id", proj.getGroup_id());
			mode.addAttribute("hos_id", proj.getHos_id());
			mode.addAttribute("proj_id", proj.getProj_id());
			mode.addAttribute("proj_code", proj.getProj_code());
			mode.addAttribute("type_code", proj.getType_code());
			mode.addAttribute("type_name", proj.getType_name());
			mode.addAttribute("proj_name", proj.getProj_name());
			mode.addAttribute("proj_simple", proj.getProj_simple());
			mode.addAttribute("sort_code", proj.getSort_code());
			mode.addAttribute("spell_code", proj.getSpell_code());
			mode.addAttribute("wbx_code", proj.getWbx_code());
			mode.addAttribute("is_stop", proj.getIs_stop());
			mode.addAttribute("note", proj.getNote());
		return "hrp/sys/proj/projChange";

	}
	
	@RequestMapping(value = "/hrp/sys/proj/addProjDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String dictType = mapVo.get("dictType").toString();
		if(dictType.equals("0")){
			projService.updateProjCode(mapVo);
		}else{
			projService.updateProjName(mapVo);
		}
		String projJson = projDictService.addProjDict(mapVo);

		return JSONObject.parseObject(projJson);
		
	}
	
}

