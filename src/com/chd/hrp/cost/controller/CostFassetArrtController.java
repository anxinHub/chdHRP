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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostFassetArrt;
import com.chd.hrp.cost.entity.CostFassetTypeArrt;
import com.chd.hrp.cost.service.CostFassetArrtService;
import com.chd.hrp.cost.service.CostFassetTypeArrtService;

/**
 * @Title. @Description. 成本_固定资产字典
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostFassetArrtController extends BaseController {

	private static Logger logger = Logger.getLogger(CostFassetArrtController.class);

	@Resource(name = "costFassetArrtService")
	private final CostFassetArrtService costFassetArrtService = null;

	@Resource(name = "costFassetTypeArrtService")
	private final CostFassetTypeArrtService costFassetTypeArrtService = null;

	/**
	 * 成本_固定资产字典<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costfassetarrt/costFassetArrtMainPage", method = RequestMethod.GET)
	public String costFassetArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costfassetarrt/costFassetArrtMain";

	}

	/**
	 * 成本_固定资产字典<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costfassetarrt/costFassetArrtAddPage", method = RequestMethod.GET)
	public String costFassetArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costfassetarrt/costFassetArrtAdd";

	}

	/**
	 * 成本_固定资产字典<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costfassetarrt/addCostFassetArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostFassetArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_name").toString()));
		String costFassetArrtJson = costFassetArrtService.addCostFassetArrt(mapVo);

		return JSONObject.parseObject(costFassetArrtJson);

	}

	/**
	 * 成本_固定资产字典<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costfassetarrt/queryCostFassetArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostFassetArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costFassetArrt = costFassetArrtService.queryCostFassetArrt(getPage(mapVo));

		return JSONObject.parseObject(costFassetArrt);

	}

	/**
	 * 成本_固定资产字典<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costfassetarrt/deleteCostFassetArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostFassetArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("asset_id", ids[3]);

			listVo.add(mapVo);
		}
		String costFassetArrtJson = costFassetArrtService.deleteBatchCostFassetArrt(listVo);
		return JSONObject.parseObject(costFassetArrtJson);

	}

	/**
	 * 成本_固定资产字典<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costfassetarrt/costFassetArrtUpdatePage", method = RequestMethod.GET)
	public String costFassetArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostFassetArrt costFassetArrt = new CostFassetArrt();
		costFassetArrt = costFassetArrtService.queryCostFassetArrtByCode(mapVo);
		mode.addAttribute("group_id", costFassetArrt.getGroup_id());

		mode.addAttribute("hos_id", costFassetArrt.getHos_id());

		mode.addAttribute("copy_code", costFassetArrt.getCopy_code());

		mode.addAttribute("asset_type_id", costFassetArrt.getAsset_type_id());

		mode.addAttribute("asset_id", costFassetArrt.getAsset_id());

		mode.addAttribute("asset_code", costFassetArrt.getAsset_code());

		mode.addAttribute("asset_name", costFassetArrt.getAsset_name());

		mode.addAttribute("prim_value", costFassetArrt.getPrim_value());

		java.text.DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("start_date", format.format(costFassetArrt.getStart_date()));

		if(costFassetArrt.getEnd_date()==null){
			mode.addAttribute("end_date", costFassetArrt.getEnd_date());
		}else{
			mode.addAttribute("end_date", format.format(costFassetArrt.getEnd_date()));
		}

		mode.addAttribute("dep_year", costFassetArrt.getDep_year());

		mode.addAttribute("spell_code", costFassetArrt.getSpell_code());

		mode.addAttribute("wbx_code", costFassetArrt.getWbx_code());

		mode.addAttribute("asset_type_code", costFassetArrt.getAsset_type_code());

		mode.addAttribute("asset_type_name", costFassetArrt.getAsset_type_name());

		return "hrp/cost/costfassetarrt/costFassetArrtUpdate";
	}

	/**
	 * 成本_固定资产字典<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costfassetarrt/updateCostFassetArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostFassetArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_name").toString()));

		String costFassetArrtJson = costFassetArrtService.updateCostFassetArrt(mapVo);

		return JSONObject.parseObject(costFassetArrtJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costfassetarrt/costFassetArrtImportPage", method = RequestMethod.GET)
	public String costFassetArrtImportPage(Model mode) throws Exception {

		return "hrp/cost/costfassetarrt/costFassetArrtImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costfassetarrt/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "固定资产.xls");

		return null;
	}

	/**
	 * 成本_固定资产字典<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costfassetarrt/readCostFassetArrtFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostFassetArrt> list_err = new ArrayList<CostFassetArrt>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostFassetArrt costFassetArrt = new CostFassetArrt();

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

					costFassetArrt.setAsset_code(temp[0]);

					mapVo.put("asset_code", temp[0]);

				} else {

					err_sb.append("资产编码为空  ");

				}

				if (StringUtils.isNotEmpty(temp[1])) {

					costFassetArrt.setAsset_name(temp[1]);

					mapVo.put("asset_name", temp[1]);

				} else {

					err_sb.append("资产名称为空  ");

				}

				if (StringUtils.isNotEmpty(temp[2])) {

					// costMateArrt.setMate_type_id(Long.valueOf(temp[2]));

					mapVo.put("asset_type_code", temp[2]);

				} else {

					err_sb.append("资产分类编码为空  ");

				}

				if (StringUtils.isNotEmpty(temp[3])) {

					costFassetArrt.setAsset_type_name(temp[3]);

				}

				if (StringUtils.isNotEmpty(temp[4])) {

					costFassetArrt.setPrim_value(Double.valueOf(temp[4]));

					mapVo.put("prim_value", temp[4]);

				} else {

					err_sb.append("资产原值为空  ");

				}

				if (StringUtils.isNotEmpty(temp[5])) {

					costFassetArrt.setStart_date(DateUtil.stringToDate(temp[5],"yyyy-MM-dd"));

					mapVo.put("start_date", temp[5]);

				} else {

					err_sb.append("启用年月为空  ");

				}

				if (StringUtils.isNotEmpty(temp[6])) {

					costFassetArrt.setEnd_date(DateUtil.stringToDate(temp[6],"yyyy-MM-dd"));

					mapVo.put("end_date", temp[6]);

				} else {

					err_sb.append("结束年月为空  ");

				}

				if (StringUtils.isNotEmpty(temp[7])) {

					costFassetArrt.setDep_year(Integer.valueOf(temp[7]));

					mapVo.put("dep_year", temp[7]);
				} else {

					err_sb.append("折旧年限为空  ");

				}

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));

				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));

				CostFassetTypeArrt cfta = costFassetTypeArrtService.queryCostFassetTypeArrtByCode(byCodeMap);

				if (cfta != null) {

					mapVo.put("asset_type_id", cfta.getAsset_type_id());

					costFassetArrt.setAsset_type_code((String) mapVo.get("asset_type_code"));

				} else {

					costFassetArrt.setAsset_type_code((String) mapVo.get("asset_type_code"));

					err_sb.append("资产分类编码不存在 ");

				}

				byCodeMap.put("asset_code", mapVo.get("asset_code"));

				CostFassetArrt data_exc_extis = costFassetArrtService.queryCostFassetArrtByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}

				if (err_sb.toString().length() > 0) {

					costFassetArrt.setError_type(err_sb.toString());

					list_err.add(costFassetArrt);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_name").toString()));

					mapVo.put("asset_id", "");

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostFassetArrt data_exc = new CostFassetArrt();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costFassetArrtService.addBatchCostFassetArrt(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 成本_固定资产字典<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costfassetarrt/addBatchCostFassetArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostFassetArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostFassetArrt> list_err = new ArrayList<CostFassetArrt>();

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

				mapVo.put("asset_id", "");
				
				mapVo.put("asset_type_code", jsonObj.get("asset_type_code"));
				
				mapVo.put("asset_type_name", jsonObj.get("asset_type_name"));

				mapVo.put("asset_code", jsonObj.get("asset_code"));

				mapVo.put("asset_name", jsonObj.get("asset_name"));

				mapVo.put("prim_value", jsonObj.get("prim_value"));

				mapVo.put("start_date", jsonObj.get("start_date"));

				mapVo.put("end_date", jsonObj.get("end_date"));

				mapVo.put("dep_year", jsonObj.get("dep_year"));

				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_name").toString()));

				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_name").toString()));

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));

				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));

				CostFassetTypeArrt cfta = costFassetTypeArrtService.queryCostFassetTypeArrtByCode(byCodeMap);

				if (cfta != null) {

					mapVo.put("asset_type_id", cfta.getAsset_type_id());

				} else {

					err_sb.append("资产分类编码不存在 ");

				}

				byCodeMap.put("asset_code", mapVo.get("asset_code"));

				CostFassetArrt data_exc_extis = costFassetArrtService.queryCostFassetArrtByCode(byCodeMap);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}

				CostFassetArrt costFassetArrt = new CostFassetArrt();

				if (err_sb.toString().length() > 0) {
					
					costFassetArrt.setAsset_type_code(mapVo.get("asset_type_code").toString());
					
					costFassetArrt.setAsset_type_name(mapVo.get("asset_type_name").toString());

					costFassetArrt.setAsset_code(mapVo.get("asset_code").toString());

					costFassetArrt.setAsset_name(mapVo.get("asset_name").toString());

					costFassetArrt.setPrim_value(Double.valueOf(mapVo.get("prim_value").toString()));

					costFassetArrt.setStart_date(DateUtil.stringToDate(mapVo.get("start_date").toString(),"yyyy-MM-dd"));

					costFassetArrt.setEnd_date(DateUtil.stringToDate(mapVo.get("end_date").toString(),"yyyy-MM-dd"));

					costFassetArrt.setDep_year(Integer.valueOf(mapVo.get("dep_year").toString()));

					costFassetArrt.setError_type(err_sb.toString());

					list_err.add(costFassetArrt);

				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costFassetArrtService.addBatchCostFassetArrt(list_batch);

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
	 * 成本_固定资产字典<BR>
	 * 同步
	 */
	@RequestMapping(value = "/hrp/cost/costfassetarrt/syncCostFassetArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostFassetArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costFassetArrtJson = costFassetArrtService.syncCostFassetArrt(mapVo);

		return JSONObject.parseObject(costFassetArrtJson);

	}
	/**
	 * 成本_固定资产字典<BR>
	 * 同步
	 */
	@RequestMapping(value = "/hrp/cost/costfassetarrt/syncCostFassetArrtNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostFassetArrtNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costFassetArrtJson = costFassetArrtService.syncCostFassetArrtNew(mapVo);

		return JSONObject.parseObject(costFassetArrtJson);

	}
	
}
