/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

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
import com.chd.hrp.prm.entity.PrmDeptHip;
import com.chd.hrp.prm.service.PrmDeptHipService;

/**
 * 
 * @Description: 8801 科室字典表
 * @Table: Prm_DEPT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmDeptHipController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmDeptHipController.class);

	// 引入Service服务
	@Resource(name = "prmDeptHipService")
	private final PrmDeptHipService prmDeptHipService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepthip/prmDeptHipMainPage", method = RequestMethod.GET)
	public String prmDeptHipMainPage(Model mode) throws Exception {

		return "hrp/prm/prmdepthip/prmDeptHipMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepthip/prmdepthipAddPage", method = RequestMethod.GET)
	public String prmdepthipAddPage(Model mode) throws Exception {

		return "hrp/prm/prmdepthip/prmDeptHipAdd";

	}

	/**
	 * @Description 添加数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepthip/addPrmDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmDeptHip(
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

		String PrmDeptHipJson = prmDeptHipService.addPrmDeptHip(mapVo);

		return JSONObject.parseObject(PrmDeptHipJson);

	}

	/**
	 * @Description 更新跳转页面 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepthip/prmDeptHipUpdatePage", method = RequestMethod.GET)
	public String prmDeptHipUpdatePage(@RequestParam Map<String, Object> mapVo,
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
		
		PrmDeptHip PrmDeptHip = prmDeptHipService.queryPrmDeptHipByCode(mapVo);
		
		mode.addAttribute("group_id", PrmDeptHip.getGroup_id());
		mode.addAttribute("hos_id", PrmDeptHip.getHos_id());
		mode.addAttribute("copy_code", PrmDeptHip.getCopy_code());
		mode.addAttribute("dept_id", PrmDeptHip.getDept_id());
		mode.addAttribute("dept_code", PrmDeptHip.getDept_code());
		mode.addAttribute("dept_name", PrmDeptHip.getDept_name());
		mode.addAttribute("dept_note", PrmDeptHip.getDept_note());
		mode.addAttribute("is_avg", PrmDeptHip.getIs_avg());
		mode.addAttribute("is_stop", PrmDeptHip.getIs_stop());


		return "hrp/prm/prmdepthip/prmDeptHipUpdate";
	}
 
	/**
	 * @Description 更新数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepthip/updatePrmDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmDeptHip(
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


		String PrmDeptHipJson = prmDeptHipService.updatePrmDeptHip(mapVo);

		return JSONObject.parseObject(PrmDeptHipJson);
	}

	/**
	 * @Description 删除数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepthip/deletePrmDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmDeptHip(
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

		String PrmDeptHipJson = prmDeptHipService.deleteBatchPrmDeptHip(listVo);

		return JSONObject.parseObject(PrmDeptHipJson);

	}

	/**
	 * @Description 查询数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepthip/queryPrmDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmDeptHip(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		String PrmDeptHipJson = prmDeptHipService.queryPrmDeptHip(getPage(mapVo));

		return JSONObject.parseObject(PrmDeptHipJson);

	}


	/**
	 * @Description 导入跳转页面 8801 科室字典表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmdepthip/prmDeptHipImportPage", method = RequestMethod.GET)
	public String prmDeptHipImportPage(Model mode) throws Exception {
		return "hrp/prm/prmdepthip/prmDeptHipImport";
	}

	//导入跳转
	@RequestMapping(value = "/hrp/prm/prmdepthip/importPrmDeptHip", method = RequestMethod.POST)
	@ResponseBody
	public String importPrmDeptHip(@RequestParam Map<String, Object> mapVo){
		
		String deptHipJson = null ;
		try {
			
			deptHipJson = prmDeptHipService.importPrmDeptHip(mapVo);
			
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
	@RequestMapping(value = "hrp/prm/prmdepthip/downTemplateprmdepthip", method = RequestMethod.GET)
	public String downTemplateprmdepthip(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "prm\\基础设置", "其它平台科室维护.xlsx");

		return null;
	}
	
}
