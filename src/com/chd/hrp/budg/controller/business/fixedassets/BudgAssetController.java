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
public class BudgAssetController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgAssetController.class);

	// 引入Service服务
	@Resource(name = "budgAssetService")
	private final BudgAssetService budgAssetService = null;

	/**
	 * @Description 
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/fixedassets/budgAsset/budgAssetMainPage", method = RequestMethod.GET)
	public String budgAssetMainPage(Model mode) throws Exception {
		return "hrp/budg/business/fixedassets/asset/budgAssetMain";
	}

	/**
	 * @Description 
	 * 删除数据 固定资产折旧预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/fixedassets/budgAsset/deleteBudgAsset", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAsset(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

		String budgAssetJson = budgAssetService.deleteBatch(listVo);

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
	@RequestMapping(value = "/hrp/budg/business/fixedassets/budgAsset/queryBudgAsset", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAsset(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String budgAssetJson = budgAssetService.query(getPage(mapVo));

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
	@RequestMapping(value = "/hrp/budg/business/fixedassets/budgAsset/collectBudgAsset", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> collectBudgAsset(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String budgAssetJson = budgAssetService.collectBudgAsset(mapVo);
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
	@RequestMapping(value = "hrp/budg/business/fixedassets/budgAsset/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\business\\fixedassets", "现有资产折旧预算.xls");

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
	@RequestMapping(value = "/hrp/budg/business/fixedassets/budgAsset/budgAssetImportPage", method = RequestMethod.GET)
	public String budgAssetImportPage(Model mode) throws Exception {

		return "hrp/budg/business/fixedassets/asset/budgAssetImport";

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
	@RequestMapping(value = "/hrp/budg/business/fixedassets/budgAsset/readBudgAssetFiles", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/budg/business/fixedassets/budgAsset/addBatchBudgAsset", method = RequestMethod.POST)
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
