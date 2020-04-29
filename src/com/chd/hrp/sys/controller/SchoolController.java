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

import org.apache.commons.lang.StringUtils;
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
import com.chd.hrp.sys.entity.School;
import com.chd.hrp.sys.serviceImpl.SchoolServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class SchoolController extends BaseController{
	private static Logger logger = Logger.getLogger(SchoolController.class);
	
	
	@Resource(name = "schoolService")
	private final SchoolServiceImpl schoolService = null;
   
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/school/schoolMainPage", method = RequestMethod.GET)
	public String schoolMainPage(Model mode) throws Exception {

		return "hrp/sys/school/schoolMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/school/schoolAddPage", method = RequestMethod.GET)
	public String schoolAddPage(Model mode) throws Exception {

		return "hrp/sys/school/schoolAdd";

	}
	
	@RequestMapping(value = "/hrp/sys/school/schoolChangePage", method = RequestMethod.GET)
	public String schoolChangePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        School School = new School();
        
		School = schoolService.querySchoolByCode(mapVo);
		
		mode.addAttribute("group_id", School.getGroup_id());
		
		mode.addAttribute("hos_id", School.getHos_id());
		
		mode.addAttribute("School_code", School.getSchool_code());
		
		mode.addAttribute("School_name", School.getSchool_name());
		
		mode.addAttribute("spell_code", School.getSpell_code());
		
		mode.addAttribute("wbx_code", School.getWbx_code());
		
		mode.addAttribute("is_stop", School.getIs_stop());
		
		mode.addAttribute("note", School.getNote());
		
		return "hrp/sys/school/schoolChange";
	}

	// 保存
	@RequestMapping(value = "/hrp/sys/school/addSchool", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addSchool(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("school_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("school_name").toString()));
		
		String SchoolJson = schoolService.addSchool(mapVo);

		return JSONObject.parseObject(SchoolJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/school/querySchool", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> querySchool(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		String School = schoolService.querySchool(getPage(mapVo));

		return JSONObject.parseObject(School);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/school/deleteSchool", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSchool(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("group_id", id.split("@")[0]);
			
			mapVo.put("hos_id", id.split("@")[1]);
			
			mapVo.put("school_code", id.split("@")[2]);
			
			listVo.add(mapVo);
        }
		
		String school = schoolService.deleteBatchSchool(listVo);
		   
		return JSONObject.parseObject(school);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/school/schoolUpdatePage", method = RequestMethod.GET)
	
	public String schoolUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        School School = new School();
        
        if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
        School = schoolService.querySchoolByCode(mapVo);
		
		mode.addAttribute("group_id", School.getGroup_id());
		
		mode.addAttribute("hos_id", School.getHos_id());
		
		mode.addAttribute("school_code", School.getSchool_code());
		
		mode.addAttribute("school_name", School.getSchool_name());
		
		mode.addAttribute("spell_code", School.getSpell_code());
		
		mode.addAttribute("wbx_code", School.getWbx_code());
		
		mode.addAttribute("is_stop", School.getIs_stop());
		
		mode.addAttribute("note", School.getNote());
		
		return "hrp/sys/school/schoolUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/school/updateSchool", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSchool(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("school_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("school_name").toString()));
		
		
		String SchoolJson = schoolService.updateSchool(mapVo);
		
		return JSONObject.parseObject(SchoolJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/school/importSchool", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importSchool(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		String SchoolJson = schoolService.importSchool(mapVo);
		
		return JSONObject.parseObject(SchoolJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/sys/school/sysSchoolImportPage", method = RequestMethod.GET)
	public String costItemDictImportPage(Model mode) throws Exception {

		return "hrp/sys/school/schoolImport";

	}
	
	
	// 下载导入模版
	@RequestMapping(value = "hrp/sys/school/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "sys\\医院信息", "职工学历.xls");
		return null;
	}
	
	
	/**
	 * 导入职工学历<BR>
	 * 
	 */
	@RequestMapping(value = "/hrp/sys/school/readSysSchoolFiles", method = RequestMethod.POST)
	public String readSysSchoolFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {
		List<School> list_err = new ArrayList<School>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
				School school = new School();
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (StringUtils.isNotEmpty(temp[0])) {
					mapVo.put("school_code", temp[0]);
					school.setSchool_code(temp[0].toString());
				} else {
					err_sb.append("职工学历编码为空");
				}
				if (StringUtils.isNotEmpty(temp[1])) {
					mapVo.put("school_name", temp[1]);
					school.setSchool_name(temp[1].toString());
				} else {
					err_sb.append("职工学历名称为空");
				}
				if (StringUtils.isNotEmpty(temp[3])) {
					mapVo.put("is_stop", temp[3]);
					school.setIs_stop(temp[3].toString());
				} else {
					err_sb.append("是否停用");
				}
				if (StringUtils.isNotEmpty(temp[2])) {
					mapVo.put("note", temp[2]);
					school.setNote(temp[2].toString());
				}
				
				School data_exc_extis = schoolService.querySchoolByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					school.setError_type(err_sb.toString());
					list_err.add(school);
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("school_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("school_name").toString()));
					schoolService.addSchool(mapVo);
				}
			}
		} catch (DataAccessException e) {
			School data_exc = new School();
			data_exc.setError_type("导入系统出错");
			list_err.add(data_exc);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
	}
	
	
	/**
	 * 
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/sys/school/addBatchSysSchool", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchSysSchool(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<School> list_err = new ArrayList<School>();
		
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
				
				mapVo.put("school_code", jsonObj.get("school_code"));
				
				mapVo.put("school_name", jsonObj.get("school_name"));
			
				mapVo.put("is_stop", jsonObj.get("is_stop"));
				
				mapVo.put("note", jsonObj.get("note"));
				
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("school_name").toString()));
				
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("school_name").toString()));
				
				School data_exc_extis = schoolService.querySchoolByCode(mapVo);
			    
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				
				School school = new School();
				
				if (err_sb.toString().length() > 0) {
					
					school.setSchool_code(mapVo.get("school_code").toString());
					
					school.setSchool_name(mapVo.get("school_name").toString());
					
					school.setIs_stop(mapVo.get("is_stop").toString());
					
					school.setNote(mapVo.get("note").toString());
					
					school.setError_type(err_sb.toString());
					
					list_err.add(school);
					
				} else {
					
					schoolService.addSchool(mapVo);
				}
			}
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
}

