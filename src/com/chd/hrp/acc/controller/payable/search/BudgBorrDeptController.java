/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.acc.controller.payable.search;

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
import com.chd.hrp.acc.service.payable.base.BudgBorrDeptService;
import com.chd.hrp.budg.entity.BudgBorrDept;

/**
 * 
 * @Description: 借款（科室）
 * @Table: BUDG_BORR_DEPT
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class BudgBorrDeptController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgBorrDeptController.class);

	// 引入Service服务
	@Resource(name = "budgBorrDeptService")
	private final BudgBorrDeptService budgBorrDeptService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/payable/search/borrdept/budgBorrDeptMainPage", method = RequestMethod.GET)
	public String budgBorrDeptMainPage(Model mode) throws Exception {

		return "hrp/acc/payable/search/borrdept/main";

	}

	/**
	 * @Description 查询数据 借款（科室）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/payable/search/borrdept/queryBudgBorrDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgBorrDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String budgBorrDept = budgBorrDeptService.query(getPage(mapVo));

		return JSONObject.parseObject(budgBorrDept);

	}

}
