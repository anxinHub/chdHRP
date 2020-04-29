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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.service.AphiDeptService;

/**
 * 
 * @Description: 8801 科室字典表
 * @Table: Prm_DEPT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AphiDeptController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptController.class);

	// 引入Service服务
	@Resource(name = "aphiDeptService")
	private final AphiDeptService aphiDeptService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdept/hpmDeptMainPage", method = RequestMethod.GET)
	public String hpmDeptMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdept/hpmDeptMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdept/hpmDeptAddPage", method = RequestMethod.GET)
	public String hpmDeptAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdept/hpmDeptAdd";

	}

	/**
	 * @Description 添加数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdept/addHpmDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHpmDept(
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

		String PrmDeptJson = aphiDeptService.addPrmDept(mapVo);

		return JSONObject.parseObject(PrmDeptJson);

	}

	/**
	 * @Description 更新跳转页面 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdept/hpmDeptUpdatePage", method = RequestMethod.GET)
	public String hpmDeptUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		AphiDept aphiDept = new AphiDept();

		aphiDept = aphiDeptService.queryPrmDeptByCode_DeptKind_DeptNature(mapVo);

		mode.addAttribute("group_id", aphiDept.getGroup_id());
		
		mode.addAttribute("hos_id", aphiDept.getHos_id());
		
		mode.addAttribute("copy_code", aphiDept.getCopy_code());
		
		mode.addAttribute("dept_id", aphiDept.getDept_id());
		
		mode.addAttribute("dept_code", aphiDept.getDept_code());
		
		mode.addAttribute("dept_name", aphiDept.getDept_name());
		
		mode.addAttribute("dept_note", aphiDept.getDept_note());
		
		mode.addAttribute("dept_kind_code", aphiDept.getDept_kind_code());
		
		mode.addAttribute("dept_kind_name", aphiDept.getDept_kind_name());
		
		mode.addAttribute("nature_code", aphiDept.getNature_code());
		
		mode.addAttribute("nature_name", aphiDept.getNature_name());
		
		mode.addAttribute("spell_code", aphiDept.getSpell_code());
		
		mode.addAttribute("wbx_code", aphiDept.getWbx_code());
		
		mode.addAttribute("is_avg", aphiDept.getIs_avg());
		
		mode.addAttribute("is_stop", aphiDept.getIs_stop());

		return "hrp/hpm/hpmdept/hpmDeptUpdate";
	}
 
	/**
	 * @Description 更新数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdept/updateHpmDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHpmDept(
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


		String PrmDeptJson = aphiDeptService.updatePrmDept(mapVo);

		return JSONObject.parseObject(PrmDeptJson);
	}

	/**
	 * @Description 删除数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdept/deleteHpmDept", method = RequestMethod.POST)
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
			
			mapVo.put("dept_id", ids[3]);
			
			mapVo.put("is_stop", "1");

			listVo.add(mapVo);

		}

		String PrmDeptJson = aphiDeptService.deleteBatchPrmDept(listVo);

		return JSONObject.parseObject(PrmDeptJson);

	}

	/**
	 * @Description 查询数据 8801 科室字典表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdept/queryHpmDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		String PrmDept = aphiDeptService.queryPrmDept(getPage(mapVo));

		return JSONObject.parseObject(PrmDept);

	}

	/**
	 * @Description 查询结果集8801 科室字典表<BR>
	 *              全部 该查询结果关联科室分类字典表、科室性质字典表
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdept/queryHpmDept_DeptKind_DeptNature", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHpmDept_DeptKind_DeptNature(
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
		String PrmDept = aphiDeptService.queryPrmDept_DeptKind_DeptNature(getPage(mapVo));

		return JSONObject.parseObject(PrmDept);

	}

	/**
	 * @Description 导入跳转页面 8801 科室字典表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/hpmdept/hpmDeptImportPage", method = RequestMethod.GET)
	public String hpmDeptImportPage(Model mode) throws Exception {
		return "hrp/hpm/hpmdept/hpmDeptImport";
	}

	//导入跳转
	@RequestMapping(value = "/hrp/hpm/hpmdept/importAphiDept", method = RequestMethod.POST)
	@ResponseBody
	public String importAphiDept(@RequestParam Map<String, Object> mapVo){
		
		String deptJson = null ;
		try {
			
			deptJson = aphiDeptService.importAphiDept(mapVo);
			
		} catch (Exception e) {
			
			deptJson = e.getMessage();
		}
		return deptJson;
	}
	
	/**
	 * @Description 下载导入模版 8801 科室字典表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/hpm/hpmdept/downTemplateHpmDept", method = RequestMethod.GET)
	public String downTemplateHpmDept(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hpm\\downTemplate", "科室维护.xlsx");

		return null;
	}
	
}
