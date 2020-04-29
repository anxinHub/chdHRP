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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.sys.entity.Duty;
import com.chd.hrp.sys.entity.School;
import com.chd.hrp.sys.serviceImpl.DutyServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class DutyController extends BaseController{
	private static Logger logger = Logger.getLogger(DutyController.class);
	
	
	@Resource(name = "dutyService")
	private final DutyServiceImpl dutyService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/duty/dutyMainPage", method = RequestMethod.GET)
	public String dutyMainPage(Model mode) throws Exception {

		return "hrp/sys/duty/dutyMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/duty/dutyAddPage", method = RequestMethod.GET)
	public String dutyAddPage(Model mode) throws Exception {

		return "hrp/sys/duty/dutyAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/duty/addDuty", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String dutyJson = dutyService.addDuty(mapVo);

		return JSONObject.parseObject(dutyJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/duty/queryDuty", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String duty = dutyService.queryDuty(getPage(mapVo));

		return JSONObject.parseObject(duty);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/duty/deleteDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDuty(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("duty_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String dutyJson = dutyService.deleteBatchDuty(listVo);
	   return JSONObject.parseObject(dutyJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/duty/dutyUpdatePage", method = RequestMethod.GET)
	
	public String dutyUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Duty duty = new Duty();
		duty = dutyService.queryDutyByCode(mapVo);
		mode.addAttribute("group_id", duty.getGroup_id());
		mode.addAttribute("hos_id", duty.getHos_id());
		mode.addAttribute("duty_code", duty.getDuty_code());
		mode.addAttribute("duty_name", duty.getDuty_name());
		mode.addAttribute("spell_code", duty.getSpell_code());
		mode.addAttribute("wbx_code", duty.getWbx_code());
		mode.addAttribute("is_stop", duty.getIs_stop());
		mode.addAttribute("note", duty.getNote());
		
		return "hrp/sys/duty/dutyUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/duty/updateDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String dutyJson = dutyService.updateDuty(mapVo);
		
		return JSONObject.parseObject(dutyJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/duty/importDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String dutyJson = dutyService.importDuty(mapVo);
		
		return JSONObject.parseObject(dutyJson);
	}
	
	// 导入页面
	@RequestMapping(value = "/hrp/sys/duty/sysDutyImportPage", method = RequestMethod.GET)
	public String sysDutyImportPage(Model mode) throws Exception {

		return "hrp/sys/duty/dutyImport";

	}
	
	
	// 下载导入模版
	@RequestMapping(value = "hrp/sys/duty/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "sys\\医院信息", "职工职务.xls");
		return null;
	}

	/**
	 * 导入职工职务<BR>
	 * 
	 */
	@RequestMapping(value = "/hrp/sys/duty/readSysDutyFiles", method = RequestMethod.POST)
	public String readSysDutyFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		
		List<Map<String, Object>> addList = new ArrayList<Map<String,Object>>();
		
		List<Duty> list_err = new ArrayList<Duty>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Duty duty = new Duty();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {
					
					mapVo.put("group_id", SessionManager.getGroupId());
					
				}
				if (mapVo.get("hos_id") == null) {
					
					mapVo.put("hos_id", SessionManager.getHosId());
					
				}

				if (ExcelReader.validExceLColunm(temp,0)) {
					
					mapVo.put("duty_code", temp[0]);
					
					duty.setDuty_code(temp[0]);
					
				} else {
					err_sb.append("职工职务编码为空");
				}

				if (ExcelReader.validExceLColunm(temp,1)) {
					
					mapVo.put("duty_name", temp[1]);
					
					duty.setDuty_name(temp[1]);
					
				} else {

					err_sb.append("职工职务编码为空");
				}
				
	             if (ExcelReader.validExceLColunm(temp,2)) {
					
					mapVo.put("is_stop", temp[2]);
					
					duty.setIs_stop(Integer.parseInt(temp[2].toString()));
					
				} else {

					err_sb.append("是否停用为空");
				}
	             
	               if (ExcelReader.validExceLColunm(temp,3)) {
						
						mapVo.put("note", temp[3]);
						
						duty.setNote(temp[3]);
						
					} else {

                        mapVo.put("note", null);
						
						duty.setNote(null);
					}
		             
				 
				Duty data_exc_extis = dutyService.queryDutyByCode(mapVo);
				    
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					duty.setError_type(err_sb.toString());
					
					list_err.add(duty);
					
				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("duty_name").toString()));
					
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("duty_name").toString()));
					
					addList.add(mapVo);
	
				}		
			}
			
			if(addList.size() > 0 ){
				
				dutyService.addBatchDuty(addList);
			}
			
		}
		
		catch (DataAccessException e) {
			
			e.printStackTrace();
			
			Duty data_exc = new Duty();
			
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
	@RequestMapping(value = "/hrp/sys/duty/addBatchSysDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchSysDuty(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> addList = new ArrayList<Map<String,Object>>();
		
		List<Duty> list_err = new ArrayList<Duty>();
		
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
				
				Duty duty = new Duty();
				
				if (StringTool.isNotBlank(jsonObj.get("duty_code"))) {

					duty.setDuty_code(jsonObj.get("duty_code").toString());
					
					mapVo.put("duty_code", jsonObj.get("duty_code").toString());
					
				} else {

					err_sb.append("职务编码为空");

				}
				
				if (StringTool.isNotBlank(jsonObj.get("duty_name"))) {

					duty.setDuty_name(jsonObj.get("duty_name").toString());
					
					mapVo.put("duty_name", jsonObj.get("duty_name").toString());
					
				} else {

					err_sb.append("职务名称为空");

				}
				
				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					duty.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
					
					mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));
					
				} else {

					err_sb.append("是否停用为空");

				}
				
				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					duty.setNote(jsonObj.get("note").toString());
					
					mapVo.put("note", jsonObj.get("note").toString());
					
				} else {

	                duty.setNote(null);
					
					mapVo.put("note",null);

				}
				
				 
				Duty data_exc_extis = dutyService.queryDutyByCode(mapVo);
				    
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				
				if (err_sb.toString().length() > 0) {
					
					duty.setError_type(err_sb.toString());
					
					list_err.add(duty);
					
				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("duty_name").toString()));
					
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("duty_name").toString()));
					
					addList.add(mapVo);
				}
			}
			
			if(addList.size() > 0 ){
				
				dutyService.addBatchDuty(addList);
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

