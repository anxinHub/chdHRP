/** 
 * @Description:
 * @Copyright: Copyright (c) 2017-6-5 下午13:30:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.controller.business.fixedassets;

import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.hrp.budg.service.business.fixedassets.BudgAssetNowService;

/**
 * 
 * @Description: 
 * 现有固定资产折旧预算
 * @Table: BUDG_ASSET_NOW
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgAssetNowController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgAssetNowController.class);

	// 引入Service服务
	@Resource(name = "budgAssetNowService")
	private final BudgAssetNowService budgAssetNowService = null;

	/**
	 * @Description 
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/fixedassets/assetnow/budgAssetNowMainPage", method = RequestMethod.GET)
	public String budgAssetNowMainPage(Model mode) throws Exception {
		return "hrp/budg/business/fixedassets/assetnow/budgAssetNowMain";
	}

	/**
	 * @Description 
	 * 删除数据 现有固定资产折旧预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/fixedassets/assetnow/deleteBudgAssetNow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAssetNow(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("budg_year", ids[3]);
			mapVo.put("naturs_code", ids[4]);
			mapVo.put("ass_card_no", ids[5]);
			mapVo.put("month", ids[6]);

			listVo.add(mapVo);

		}

		String budgAssetNowJson = budgAssetNowService.deleteBatch(listVo);

		return JSONObject.parseObject(budgAssetNowJson);

	}

	/**
	 * @Description 
	 * 查询数据 现有资产折旧预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/fixedassets/assetnow/queryBudgAssetNow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAssetNow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}

		String budgAssetNowJson = budgAssetNowService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAssetNowJson);

	}
	
	/**
	 * @Description 
	 * 计算 现有资产折旧预算
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/fixedassets/assetnow/collectBudgAssetNow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> collectBudgAssetNow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		//计算之前，先删除所选预算年度的数据，再插入
		//判断某资产卡片预算年月是否提折旧
		//1)是否折旧？若否，结束；若是，继续；
		//2)若使用状态为6待处置或已处置，则不计提两个月的折旧，结束；否则继续；
		//3)若 【 累计折旧月份+（预算年月-上次折旧年月）】>12*计提年数?若是，结束；若否，计算月折旧额
		//6)计算月折旧额
		//若折旧方法=01平均年限法，月折旧额=（原值-残值-累计折旧）/(12*计提年数-累计折旧月份）；否则结束。
		String budgAssetNowJson = budgAssetNowService.collectBudgAssetNow(mapVo);
		return JSONObject.parseObject(budgAssetNowJson);
	}
	

	/**
	 * @Description 
	 * 下载导入模版 现有资产折旧预算
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/business/fixedassets/assetnow/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\business\\fixedassets", "现有资产折旧预算.xls");

		return null;
	}

	/**
	 * @Description 
	 * 导入跳转页面 现有资产折旧预算
	 * 
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/fixedassets/assetnow/budgAssetNowImportPage", method = RequestMethod.GET)
	public String budgAssetNowImportPage(Model mode) throws Exception {

		return "hrp/budg/business/fixedassets/assetnow/budgAssetNowImport";

	}

	/**
	 * @Description 导入数据 现有资产折旧预算
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/fixedassets/assetnow/readBudgAssetNowFiles", method = RequestMethod.POST)
	public String readBudgAssetNowFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		//TODO
		/*List<BudgAssetNow> list_err = new ArrayList<BudgAssetNow>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

		String[] monthList = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };*/

		
		return null;

	}

	/**
	 * @Description 
	 * 批量添加数据 现有资产折旧预算
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/fixedassets/assetnow/addBatchBudgAssetNow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchBudgAssetNow(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		//TODO
		/*List<BudgAssetNow> list_err = new ArrayList<BudgAssetNow>();

		JSONArray json = JSONArray.parseArray(paramVo);*/

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

		return null;

	}

}
