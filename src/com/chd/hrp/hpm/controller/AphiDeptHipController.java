/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.service.AphiDeptHipService;

/**
 * 
 * @Description: 8801 科室字典表
 * @Table: Prm_DEPT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiDeptHipController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptHipController.class);

	// 引入Service服务
	@Resource(name = "aphiDeptHipService")
	private final AphiDeptHipService aphiDeptHipService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdepthip/hpmDeptHipMainPage", method = RequestMethod.GET)
	public String hpmDeptHipMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdepthip/hpmDeptHipMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdepthip/hpmDeptHipAddPage", method = RequestMethod.GET)
	public String hpmDeptHipAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdepthip/hpmDeptHipAdd";

	}

	/**
	 * @Description 添加数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdepthip/addAphiDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAphiDeptHip(
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

		String aphiDeptHipJson = aphiDeptHipService.addAphiDeptHip(mapVo);

		return JSONObject.parseObject(aphiDeptHipJson);

	}

	/**
	 * @Description 更新跳转页面 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdepthip/hpmDeptHipUpdatePage", method = RequestMethod.GET)
	public String hpmDeptHipUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

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
		
		AphiDeptHip aphiDeptHip = aphiDeptHipService.queryAphiDeptHipByCode(mapVo);
		
		mode.addAttribute("group_id", aphiDeptHip.getGroup_id());
		mode.addAttribute("hos_id", aphiDeptHip.getHos_id());
		mode.addAttribute("copy_code", aphiDeptHip.getCopy_code());
		mode.addAttribute("dept_id", aphiDeptHip.getDept_id());
		mode.addAttribute("dept_code", aphiDeptHip.getDept_code());
		mode.addAttribute("dept_name", aphiDeptHip.getDept_name());
		mode.addAttribute("dept_note", aphiDeptHip.getDept_note());
		mode.addAttribute("is_avg", aphiDeptHip.getIs_avg());
		mode.addAttribute("is_stop", aphiDeptHip.getIs_stop());


		return "hrp/hpm/hpmdepthip/hpmDeptHipUpdate";
	}
 
	/**
	 * @Description 更新数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdepthip/updateAphiDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAphiDeptHip(
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

		mapVo.put("note", "更新");

		mapVo.put("user_code", SessionManager.getUserCode());

		mapVo.put("create_date", new Date());

		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("dept_name").toString()));

		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("dept_name").toString()));


		String aphiDeptHipJson = aphiDeptHipService.updateAphiDeptHip(mapVo);

		return JSONObject.parseObject(aphiDeptHipJson);
	}

	/**
	 * @Description 删除数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdepthip/deleteAphiDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAphiDeptHip(
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
			
			mapVo.put("dept_id", ids[3]);
			
			mapVo.put("is_stop", "1");

			listVo.add(mapVo);

		}

		String aphiDeptHipJson = aphiDeptHipService.deleteBatchAphiDeptHip(listVo);

		return JSONObject.parseObject(aphiDeptHipJson);

	}

	/**
	 * @Description 查询数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdepthip/queryAphiDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAphiDeptHip(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		String aphiDeptHipJson = aphiDeptHipService.queryAphiDeptHip(getPage(mapVo));

		return JSONObject.parseObject(aphiDeptHipJson);

	}


	/**
	 * @Description 导入跳转页面 8801 科室字典表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdepthip/hpmDeptHipImportPage", method = RequestMethod.GET)
	public String hpmDeptImportPage(Model mode) throws Exception {
		return "hrp/hpm/hpmdepthip/hpmDeptHipImport";
	}

	//导入跳转
	@RequestMapping(value = "/hrp/hpm/hpmdepthip/importAphiDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public String importAphiDeptHip(@RequestParam Map<String, Object> mapVo){
		
		String deptHipJson = null ;
		try {
			
			deptHipJson = aphiDeptHipService.importAphiDeptHip(mapVo);
			
		} catch (Exception e) {
			
			deptHipJson = e.getMessage();
		}
		return deptHipJson;
	}
	
	/**
	 * @Description 下载导入模版 8801 科室字典表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmdepthip/downTemplateHpmDeptHip", method = RequestMethod.GET)
	public String downTemplateHpmDeptHip(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\downTemplate", "其它平台科室维护.xlsx");

		return null;
	}
	
}
