/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.controller.medicalmanagement;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
import com.chd.hrp.hr.service.medicalmanagement.HrDrugPermService;

/**
 * 
 * @Description:
 * 
 * @Table: HR_DRUG_PERM 药品权限管理
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement")
@SuppressWarnings("unused")
public class HrDrugPermController extends BaseController {

	private static Logger logger = Logger.getLogger(HrDrugPermController.class);

	// 引入Service服务
	@Resource(name = "hrDrugPermService")
	private final HrDrugPermService hrDrugPermService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrDrugPermMainPage", method = RequestMethod.GET)
	public String hrDrugPermMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/hrdrugperm/hrDrugPermMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrDrugPermAddPage", method = RequestMethod.GET)
	public String hrDrugPermAddPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/hrdrugperm/hrDrugPermAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrDrugPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrDrugPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		try {
			String hrDrugPermJson = hrDrugPermService.addHrDrugPerm(mapVo);

			return JSONObject.parseObject(hrDrugPermJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrDrugPerm", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrDrugPerm(@RequestParam String paramVo, Model mode) throws Exception {

		String str = "";
		boolean falg = true;
		List<HrDrugPerm> listVo = JSONArray.parseArray(paramVo, HrDrugPerm.class);

		try {

			return hrDrugPermService.deletehrDrugPerm(listVo);

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrDrugPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrDrugPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String hrDrugPerm = hrDrugPermService.queryHrDrugPerm(getPage(mapVo));

		return JSONObject.parseObject(hrDrugPerm);

	}

	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmHrDrugPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmDrugPerm(@RequestParam String paramVo, Model mode) {

		String msg = "";
		List<HrDrugPerm> listVo = JSONArray.parseArray(paramVo, HrDrugPerm.class);
		try {
			msg = hrDrugPermService.confirmDrugPerm(listVo);
		} catch (Exception e) {
			msg = "{\"error\":\"" + e.getMessage() + "\",\"state\":\"false\"}";
		}
		return JSONObject.parseObject(msg);

	}

	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmHrDrugPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmDrugPerm(@RequestParam String paramVo, Model mode) throws Exception {

		String msg = "";
		List<HrDrugPerm> listVo = JSONArray.parseArray(paramVo, HrDrugPerm.class);
		try {
			msg = hrDrugPermService.reConfirmDrugPerm(listVo);
		} catch (Exception e) {
			msg = "{\"error\":\"" + e.getMessage() + "\",\"state\":\"false\"}";
		}
		return JSONObject.parseObject(msg);

	}

	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importDrugPerm", method = RequestMethod.POST)
	@ResponseBody
	public String importDrugPerm(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request)
			throws Exception {
		String reJson = hrDrugPermService.importDrugPerm(mapVo);
		return reJson;
	}

}