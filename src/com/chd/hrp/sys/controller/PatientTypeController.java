/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
import java.util.*;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.sys.entity.PatientType;
import com.chd.hrp.sys.serviceImpl.PatientTypeServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class PatientTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(PatientTypeController.class);
	
	
	@Resource(name = "patientTypeService")
	private final PatientTypeServiceImpl patientTypeService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/patienttype/patientTypeMainPage", method = RequestMethod.GET)
	public String patientTypeMainPage(Model mode) throws Exception {

		return "hrp/sys/patienttype/patientTypeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/patienttype/patientTypeAddPage", method = RequestMethod.GET)
	public String patientTypeAddPage(Model mode) throws Exception {

		return "hrp/sys/patienttype/patientTypeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/patienttype/addPatientType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addPatientType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String patientTypeJson = patientTypeService.addPatientType(mapVo);

		return JSONObject.parseObject(patientTypeJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/patienttype/queryPatientType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryPatientType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String patientType = patientTypeService.queryPatientType(getPage(mapVo));

		return JSONObject.parseObject(patientType);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/patienttype/deletePatientType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePatientType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("patient_code", id.split("@")[2]);
            mapVo.put("patient_id", id.split("@")[3]);
            listVo.add(mapVo);
        }
		String patientTypeJson = patientTypeService.deleteBatchPatientType(listVo);
	   return JSONObject.parseObject(patientTypeJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/patienttype/patientTypeUpdatePage", method = RequestMethod.GET)
	
	public String patientTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        PatientType patientType = new PatientType();
		patientType = patientTypeService.queryPatientTypeByCode(mapVo);
		mode.addAttribute("group_id", patientType.getGroup_id());
		mode.addAttribute("hos_id", patientType.getHos_id());
		mode.addAttribute("patient_id", patientType.getPatient_id());
		mode.addAttribute("patient_code", patientType.getPatient_code());
		mode.addAttribute("patient_name", patientType.getPatient_name());
		mode.addAttribute("spell_code", patientType.getSpell_code());
		mode.addAttribute("wbx_code", patientType.getWbx_code());
		mode.addAttribute("is_stop", patientType.getIs_stop());
		mode.addAttribute("note", patientType.getNote());
		
		return "hrp/sys/patienttype/patientTypeUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/patienttype/updatePatientType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePatientType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String patientTypeJson = patientTypeService.updatePatientType(mapVo);
		
		return JSONObject.parseObject(patientTypeJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/patienttype/importPatientType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importPatientType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String patientTypeJson = patientTypeService.importPatientType(mapVo);
		
		return JSONObject.parseObject(patientTypeJson);
	}

}

