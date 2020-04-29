/** 
 * @Description:
 * @Copyright: Copyright (c) 2017-6-5 下午13:30:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.controller.business.intangible;

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
import com.chd.hrp.budg.service.business.fixedassets.BudgAssetService;

/**
 * 
 * @Description: 
 * 固定资产折旧预算编制
 * @Table: BUDG_ASSET
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgAssetIntangbleController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgAssetIntangbleController.class);

	// 引入Service服务
	@Resource(name = "budgAssetIntangibleService")
	private final BudgAssetService budgAssetIntangibleService = null;

	/**
	 * @Description 
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/intangible/budgAsset/budgAssetIntangibleMainPage", method = RequestMethod.GET)
	public String budgAssetIntangibleMainPage(Model mode) throws Exception {
		return "hrp/budg/business/intangible/assset/budgAssetIntangibleMain";
	}

	/**
	 * @Description 
	 * 删除数据 固定资产折旧预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/intangible/budgAsset/deleteBudgAssetIntangible", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAssetIntangible(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("asset_nature", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("hos_id", ids[2]);
			mapVo.put("copy_code", ids[3]);
			mapVo.put("budg_year", ids[4]);
			mapVo.put("month", ids[5]);
			mapVo.put("dept_id", ids[6]);
			mapVo.put("ass_type_id", ids[7]);
			mapVo.put("source_id", ids[8]);

			listVo.add(mapVo);

		}

		String budgAssetJson = budgAssetIntangibleService.deleteBatch(listVo);

		return JSONObject.parseObject(budgAssetJson);

	}

	/**
	 * @Description 
	 * 查询数据 固定资产折旧预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/intangible/budgAsset/queryBudgAssetIntangible", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAssetIntangible(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String budgAssetJson = budgAssetIntangibleService.query(getPage(mapVo));

		return JSONObject.parseObject(budgAssetJson);

	}
	
	/**
	 * @Description 
	 * 计算 固定资产折旧预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/intangible/budgAsset/collectBudgAssetIntangible", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> collectBudgAssetIntangible(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String budgAssetJson = budgAssetIntangibleService.collectBudgAsset(mapVo);
		return JSONObject.parseObject(budgAssetJson);
	}
	

	/**
	 * @Description 
	 * 下载导入模版 固定资产折旧预算编制
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/business/intangible/budgAsset/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\business\\intangible", "现有资产折旧预算.xls");

		return null;
	}

	/**
	 * @Description 
	 * 导入跳转页面 固定资产折旧预算编制
	 * 
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/intangible/budgAsset/budgAssetImportPage", method = RequestMethod.GET)
	public String budgAssetImportPage(Model mode) throws Exception {

		return "hrp/budg/business/intangible/asset/budgAssetImport";

	}

	/**
	 * @Description 导入数据 固定资产折旧预算编制
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/intangible/budgAsset/readBudgAssetFiles", method = RequestMethod.POST)
	public String readBudgAssetFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {
		//TODO
		//List<BudgAsset> list_err = new ArrayList<BudgAsset>();

		//List<String[]> list = UploadUtil.readFile(plupload, request, response);

		//List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

		//String[] monthList = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

		
		return null;

	}

	/**
	 * @Description 
	 * 批量添加数据固定资产折旧预算编制
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/intangible/budgAsset/addBatchBudgAsset", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchBudgAsset(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		//TODO
		/*List<BudgAsset> list_err = new ArrayList<BudgAsset>();

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
