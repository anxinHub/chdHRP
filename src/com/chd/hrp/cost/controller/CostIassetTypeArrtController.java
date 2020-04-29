/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostIassetTypeArrt;
import com.chd.hrp.cost.service.CostIassetTypeArrtService;

/**
 * @Title. @Description. 成本_无形资产分类字典
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class CostIassetTypeArrtController extends BaseController {

	private static Logger logger = Logger.getLogger(CostIassetTypeArrtController.class);

	@Resource(name = "costIassetTypeArrtService")
	private final CostIassetTypeArrtService costIassetTypeArrtService = null;

	/**
	 * 成本_无形资产分类字典<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costiassettypearrt/costIassetTypeArrtMainPage", method = RequestMethod.GET)
	public String costIassetTypeArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costiassettypearrt/costIassetTypeArrtMain";

	}

	/**
	 * 成本_无形资产分类字典<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costiassettypearrt/costIassetTypeArrtAddPage", method = RequestMethod.GET)
	public String costIassetTypeArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costiassettypearrt/costIassetTypeArrtAdd";

	}

	/**
	 * 成本_无形资产分类字典<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costiassettypearrt/addCostIassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostIassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_type_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_type_name").toString()));
		String costIassetTypeArrtJson = costIassetTypeArrtService.addCostIassetTypeArrt(mapVo);

		return JSONObject.parseObject(costIassetTypeArrtJson);

	}

	/**
	 * 成本_无形资产分类字典<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costiassettypearrt/queryCostIassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostIassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costIassetTypeArrt = costIassetTypeArrtService.queryCostIassetTypeArrt(getPage(mapVo));

		return JSONObject.parseObject(costIassetTypeArrt);

	}

	/**
	 * 成本_无形资产分类字典<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costiassettypearrt/deleteCostIassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostIassetTypeArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("asset_type_code", ids[3]);

			listVo.add(mapVo);
		}
		String costIassetTypeArrtJson = costIassetTypeArrtService.deleteBatchCostIassetTypeArrt(listVo);
		return JSONObject.parseObject(costIassetTypeArrtJson);

	}

	/**
	 * 成本_无形资产分类字典<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costiassettypearrt/costIassetTypeArrtUpdatePage", method = RequestMethod.GET)
	public String costIassetTypeArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostIassetTypeArrt costIassetTypeArrt = new CostIassetTypeArrt();
		costIassetTypeArrt = costIassetTypeArrtService.queryCostIassetTypeArrtByCode(mapVo);
		mode.addAttribute("group_id", costIassetTypeArrt.getGroup_id());

		mode.addAttribute("hos_id", costIassetTypeArrt.getHos_id());

		mode.addAttribute("copy_code", costIassetTypeArrt.getCopy_code());

		mode.addAttribute("asset_type_code", costIassetTypeArrt.getAsset_type_code());

		mode.addAttribute("asset_type_name", costIassetTypeArrt.getAsset_type_name());

		mode.addAttribute("supper_code", costIassetTypeArrt.getSupper_code());

		mode.addAttribute("is_last", costIassetTypeArrt.getIs_last());

		mode.addAttribute("spell_code", costIassetTypeArrt.getSpell_code());

		mode.addAttribute("wbx_code", costIassetTypeArrt.getWbx_code());

		return "hrp/cost/costiassettypearrt/costIassetTypeArrtUpdate";
	}

	/**
	 * 成本_无形资产分类字典<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costiassettypearrt/updateCostIassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostIassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_type_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_type_name").toString()));

		String costIassetTypeArrtJson = costIassetTypeArrtService.updateCostIassetTypeArrt(mapVo);

		return JSONObject.parseObject(costIassetTypeArrtJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costiassettypearrt/costIassetTypeArrtImportPage", method = RequestMethod.GET)
	public String costIassetTypeArrtImportPage(Model mode) throws Exception {

		return "hrp/cost/costiassettypearrt/costIassetTypeArrtImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costiassettypearrt/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "无形资产分类.xls");

		return null;
	}

	/**
	 * 成本_无形资产分类字典<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costiassettypearrt/readCostIassetTypeArrtFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostIassetTypeArrt> list_err = new ArrayList<CostIassetTypeArrt>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostIassetTypeArrt costIassetTypeArrt = new CostIassetTypeArrt();

				String temp[] = list.get(i);// 行

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

				if (StringUtils.isNotEmpty(temp[0])) {

					costIassetTypeArrt.setAsset_type_code(temp[0]);

					mapVo.put("asset_type_code", temp[0]);
					
				} else {
					
					err_sb.append("资产分类编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[1])) {

					costIassetTypeArrt.setAsset_type_name(temp[1]);

					mapVo.put("asset_type_name", temp[1]);
					
				} else {
					
					err_sb.append("资产分类名称为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[2])) {

					costIassetTypeArrt.setSupper_code(temp[2]);

					mapVo.put("supper_code", temp[2]);
					
				} else {
					
					err_sb.append("上级编码码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {

					costIassetTypeArrt.setIs_last(Integer.valueOf(temp[3]));

					mapVo.put("is_last", temp[3]);
					
				} else {
					
					err_sb.append("是否末级为空  ");
					
				}
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostIassetTypeArrt data_exc_extis = costIassetTypeArrtService.queryCostIassetTypeArrtByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				
				if (err_sb.toString().length() > 0) {

					costIassetTypeArrt.setError_type(err_sb.toString());

					list_err.add(costIassetTypeArrt);
					
				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_type_name").toString()));
					
					mapVo.put("asset_type_code", "");

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostIassetTypeArrt data_exc = new CostIassetTypeArrt();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costIassetTypeArrtService.addBatchCostIassetTypeArrt(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 成本_无形资产分类字典<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costiassettypearrt/addBatchCostIassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostIassetTypeArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostIassetTypeArrt> list_err = new ArrayList<CostIassetTypeArrt>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {
			while (it.hasNext()) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				StringBuffer err_sb = new StringBuffer();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {

					mapVo.put("copy_code", SessionManager.getCopyCode());
				}

				mapVo.put("asset_type_code", jsonObj.get("asset_type_code"));

				mapVo.put("asset_type_name", jsonObj.get("asset_type_name"));

				mapVo.put("supper_code", jsonObj.get("supper_code"));

				mapVo.put("is_last", jsonObj.get("is_last"));

				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_type_name").toString()));

				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_type_name").toString()));
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));

				CostIassetTypeArrt data_exc_extis = costIassetTypeArrtService.queryCostIassetTypeArrtByCode(byCodeMap);

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}

				CostIassetTypeArrt costIassetTypeArrt = new CostIassetTypeArrt();

				if (err_sb.toString().length() > 0) {

					costIassetTypeArrt.setAsset_type_code(mapVo.get("asset_type_code").toString());

					costIassetTypeArrt.setAsset_type_name(mapVo.get("asset_type_name").toString());

					costIassetTypeArrt.setSupper_code(mapVo.get("supper_code").toString());

					costIassetTypeArrt.setIs_last(Integer.valueOf(mapVo.get("is_last").toString()));

					costIassetTypeArrt.setError_type(err_sb.toString());

					list_err.add(costIassetTypeArrt);
					
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costIassetTypeArrtService.addBatchCostIassetTypeArrt(list_batch);

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
	
	/**
	 * 成本_无形资产分类字典<BR>
	 * 同步
	 */
	@RequestMapping(value = "/hrp/cost/costiassettypearrt/syncCostIassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostIassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costIassetTypeArrtJson = costIassetTypeArrtService.syncCostIassetTypeArrt(mapVo);

		return JSONObject.parseObject(costIassetTypeArrtJson);

	}
	
}
