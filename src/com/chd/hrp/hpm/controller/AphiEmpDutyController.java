package com.chd.hrp.hpm.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiEmpDuty;
import com.chd.hrp.hpm.service.AphiEmpDutyService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiEmpDutyController extends BaseController {
	private static Logger logger = Logger.getLogger(AphiEmpDutyController.class);


	@Resource(name = "aphiEmpDutyService")
	private final AphiEmpDutyService aphiEmpDutyService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmempduty/hpmEmpDutyMainPage", method = RequestMethod.GET)
	public String empDutyMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempduty/hpmEmpDutyMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmempduty/hpmEmpDutyAddPage", method = RequestMethod.GET)
	public String empDutyAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempduty/hpmEmpDutyAdd";

	}



	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmempduty/addHpmEmpDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmEmpDuty(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			}
			
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			 
			}
			
			if(mapVo.get("copy_code") == null){
				
	      mapVo.put("copy_code", SessionManager.getCopyCode());
	    
			}
			if(mapVo.get("acct_year") == null){
				
	      mapVo.put("acct_year", SessionManager.getAcctYear());
	   
			}
	
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("duty_name").toString()));
		
	    mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("duty_name").toString()));
			
		String prmEmpDuty = aphiEmpDutyService.addPrmEmpDuty(mapVo);

		return JSONObject.parseObject(prmEmpDuty);

	}

	/**
	 * @Description 删除数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempduty/deleteHpmEmpDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHpmDept(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			
			mapVo.put("hos_id", ids[1]);
			
			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("duty_code", ids[3]);


			listVo.add(mapVo);

		}

		String empDutyJson = aphiEmpDutyService.deleteBatchPrmEmpDuty(listVo);

		return JSONObject.parseObject(empDutyJson);

	}
	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmempduty/queryHpmEmpDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmEmpDuty(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		 if(mapVo.get("group_id") == null){
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
				}
				
				if(mapVo.get("hos_id") == null){
					
				mapVo.put("hos_id", SessionManager.getHosId());
				
				}
				
				if(mapVo.get("copy_code") == null){
					
		    mapVo.put("copy_code", SessionManager.getCopyCode());
		        
				}
				if(mapVo.get("acct_year") == null){
					
		    mapVo.put("acct_year", SessionManager.getAcctYear());
		        
				}
		String empDuty = aphiEmpDutyService.queryEmpDuty(getPage(mapVo));

		return JSONObject.parseObject(empDuty);

	}

	/**
	 * @Description 下载导入模版 8803 职务字典表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmempduty/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\基础设置", "职务字典模板.xls");

		return null;
	}

	/**
	 * @Description 导入跳转页面 8803 职务字典表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempduty/empDutyImportPage", method = RequestMethod.GET)
	public String empDutyImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempduty/hpmEmpDutyImport";

	}

	/**
	 * @Description 导入数据 8803 职务字典表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmempduty/readHpmEmpDutyFiles", method = RequestMethod.POST)
	public String readHpmEmpDutyFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<AphiEmpDuty> list_err = new ArrayList<AphiEmpDuty>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

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

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AphiEmpDuty prmEmpDuty = new AphiEmpDuty();

				String temp[] = list.get(i);// 行

				if (StringTool.isNotBlank(temp[0])) {

					prmEmpDuty.setDuty_code(temp[0]);

					mapVo.put("duty_code", temp[0]);

				} else {

					err_sb.append("职务编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					prmEmpDuty.setDuty_name(temp[1]);

					mapVo.put("duty_name", temp[1]);

				} else {

					err_sb.append("职务名称为空  ");

				}
 
				mapVo.put("duty_note", temp[2]);//备注
				
				mapVo.put("is_stop", temp[3]);//是否停用

				AphiEmpDuty data_exc_extis = aphiEmpDutyService.queryPrmEmpDutyByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					prmEmpDuty.setError_type(err_sb.toString());

					list_err.add(prmEmpDuty);

				} else {
					
					mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("duty_name").toString()));
					
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("duty_name").toString()));

					String dataJson = aphiEmpDutyService.addPrmEmpDuty(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AphiEmpDuty data_exc = new AphiEmpDuty();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}
	
	
	//页面跳转-系统平台职务
	@RequestMapping(value = "/hrp/hpm/hpmempduty/hpmSysEmpDutyMainPage", method = RequestMethod.GET)
	public String hpmSysEmpDutyMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmempduty/hpmSysEmpDutyMain";

	}
	
	// 查询-系统平台职务
	@RequestMapping(value = "/hrp/hpm/hpmempduty/queryHpmSysEmpDuty", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmSysEmpDuty(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
			
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
			
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		String empDuty = aphiEmpDutyService.queryHpmSysEmpDuty(getPage(mapVo));

		return JSONObject.parseObject(empDuty);
	}
	
}
