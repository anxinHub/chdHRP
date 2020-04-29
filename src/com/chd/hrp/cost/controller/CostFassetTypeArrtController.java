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
import com.chd.hrp.cost.entity.CostFassetTypeArrt;
import com.chd.hrp.cost.service.CostFassetTypeArrtService;

/**
 * @Title. @Description. 成本_固定资产分类字典
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class CostFassetTypeArrtController extends BaseController {

	private static Logger logger = Logger.getLogger(CostFassetTypeArrtController.class);

	@Resource(name = "costFassetTypeArrtService")
	private final CostFassetTypeArrtService costFassetTypeArrtService = null;

	/**
	 * 成本_固定资产分类字典<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costfassettypearrt/costFassetTypeArrtMainPage", method = RequestMethod.GET)
	public String costFassetTypeArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costfassettypearrt/costFassetTypeArrtMain";

	}

	/**
	 * 成本_固定资产分类字典<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/costFassetTypeArrtAddPage", method = RequestMethod.GET)
	public String costFassetTypeArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costfassettypearrt/costFassetTypeArrtAdd";

	}

	/**
	 * 成本_固定资产分类字典<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/addCostFassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostFassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String costFassetTypeArrtJson = costFassetTypeArrtService.addCostFassetTypeArrt(mapVo);

		return JSONObject.parseObject(costFassetTypeArrtJson);

	}

	/**
	 * 成本_固定资产分类字典<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/queryCostFassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostFassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costFassetTypeArrt = costFassetTypeArrtService.queryCostFassetTypeArrt(getPage(mapVo));

		return JSONObject.parseObject(costFassetTypeArrt);

	}

	/**
	 * 成本_固定资产分类字典<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/deleteCostFassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostFassetTypeArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		String costFassetTypeArrtJson = costFassetTypeArrtService.deleteBatchCostFassetTypeArrt(listVo);
		return JSONObject.parseObject(costFassetTypeArrtJson);

	}

	/**
	 * 成本_固定资产分类字典<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/costFassetTypeArrtUpdatePage", method = RequestMethod.GET)
	public String costFassetTypeArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostFassetTypeArrt costFassetTypeArrt = new CostFassetTypeArrt();
		costFassetTypeArrt = costFassetTypeArrtService.queryCostFassetTypeArrtByCode(mapVo);
		mode.addAttribute("group_id", costFassetTypeArrt.getGroup_id());

		mode.addAttribute("hos_id", costFassetTypeArrt.getHos_id());

		mode.addAttribute("copy_code", costFassetTypeArrt.getCopy_code());

		mode.addAttribute("asset_type_code", costFassetTypeArrt.getAsset_type_code());

		mode.addAttribute("asset_type_name", costFassetTypeArrt.getAsset_type_name());

		mode.addAttribute("supper_code", costFassetTypeArrt.getSupper_code());

		mode.addAttribute("is_last", costFassetTypeArrt.getIs_last());

		mode.addAttribute("spell_code", costFassetTypeArrt.getSpell_code());

		mode.addAttribute("wbx_code", costFassetTypeArrt.getWbx_code());

		return "hrp/cost/costfassettypearrt/costFassetTypeArrtUpdate";
	}

	/**
	 * 成本_固定资产分类字典<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costfassettypearrt/updateCostFassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostFassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

		String costFassetTypeArrtJson = costFassetTypeArrtService.updateCostFassetTypeArrt(mapVo);

		return JSONObject.parseObject(costFassetTypeArrtJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/costFassetTypeArrtImportPage", method = RequestMethod.GET)
	public String costFassetTypeArrtImportPage(Model mode) throws Exception {

		return "hrp/cost/costfassettypearrt/costFassetTypeArrtImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costfassettypearrt/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "固定资产分类.xls");

		return null;
	}

	/**
	 * 成本_固定资产分类字典<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/readCostFassetTypeArrtFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostFassetTypeArrt> list_err = new ArrayList<CostFassetTypeArrt>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostFassetTypeArrt costFassetTypeArrt = new CostFassetTypeArrt();

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

					costFassetTypeArrt.setAsset_type_code(temp[0]);

					mapVo.put("asset_type_code", temp[0]);
					
				} else {
					
					err_sb.append("资产分类编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[1])) {

					costFassetTypeArrt.setAsset_type_name(temp[1]);

					mapVo.put("asset_type_name", temp[1]);
					
				} else {
					
					err_sb.append("资产分类名称为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[2])) {

					costFassetTypeArrt.setSupper_code(temp[2]);

					mapVo.put("supper_code", temp[2]);
					
				} else {
					
					err_sb.append("上级编码码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {

					costFassetTypeArrt.setIs_last(Integer.valueOf(temp[3]));

					mapVo.put("is_last", temp[3]);
					
				} else {
					
					err_sb.append("是否末级为空  ");
					
				}
				
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostFassetTypeArrt data_exc_extis = costFassetTypeArrtService.queryCostFassetTypeArrtByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				
				if (err_sb.toString().length() > 0) {

					costFassetTypeArrt.setError_type(err_sb.toString());

					list_err.add(costFassetTypeArrt);
					
				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_type_name").toString()));

					mapVo.put("asset_type_code", "");
					
					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostFassetTypeArrt data_exc = new CostFassetTypeArrt();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costFassetTypeArrtService.addBatchCostFassetTypeArrt(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 成本_固定资产分类字典<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/addBatchCostFassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostFassetTypeArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostFassetTypeArrt> list_err = new ArrayList<CostFassetTypeArrt>();

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

				CostFassetTypeArrt data_exc_extis = costFassetTypeArrtService.queryCostFassetTypeArrtByCode(byCodeMap);

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}

				CostFassetTypeArrt costFassetTypeArrt = new CostFassetTypeArrt();

				if (err_sb.toString().length() > 0) {

					costFassetTypeArrt.setAsset_type_code(mapVo.get("asset_type_code").toString());

					costFassetTypeArrt.setAsset_type_name(mapVo.get("asset_type_name").toString());

					costFassetTypeArrt.setSupper_code(mapVo.get("supper_code").toString());

					costFassetTypeArrt.setIs_last(Integer.valueOf(mapVo.get("is_last").toString()));

					costFassetTypeArrt.setError_type(err_sb.toString());

					list_err.add(costFassetTypeArrt);
					
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costFassetTypeArrtService.addBatchCostFassetTypeArrt(list_batch);

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
	 * 2016/10/31 lxj
	 * 成本_固定资产分类字典<BR>
	 * 同步
	 */
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/syncCostFassetTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostFassetTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costFassetTypeArrtJson = costFassetTypeArrtService.syncCostFassetTypeArrt(mapVo);

		return JSONObject.parseObject(costFassetTypeArrtJson);

	}
	/**
	 * 2016/10/31 lxj
	 * 成本_固定资产分类字典<BR>
	 * 同步
	 */
	@RequestMapping(value = "/hrp/cost/costfassettypearrt/syncCostFassetTypeArrtFNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostFassetTypeArrtNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costFassetTypeArrtJson = costFassetTypeArrtService.syncCostFassetTypeArrtFNew(mapVo);

		return JSONObject.parseObject(costFassetTypeArrtJson);

	}
}
