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
import com.chd.hrp.cost.entity.CostMateArrt;
import com.chd.hrp.cost.entity.CostMateTypeArrt;
import com.chd.hrp.cost.service.CostMateArrtService;
import com.chd.hrp.cost.service.CostMateTypeArrtService;

/**
 * @Title. @Description. 成本_材料分类字典
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class CostMateTypeArrtController extends BaseController {

	private static Logger logger = Logger.getLogger(CostMateTypeArrtController.class);

	@Resource(name = "costMateTypeArrtService")
	private final CostMateTypeArrtService costMateTypeArrtService = null;
	
	@Resource(name = "costMateArrtService")
	private final CostMateArrtService costMateArrtService = null ;

	/**
	 * 成本_材料分类字典<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costmatetypearrt/costMateTypeArrtMainPage", method = RequestMethod.GET)
	public String costMateTypeArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costmatetypearrt/costMateTypeArrtMain";

	}

	/**
	 * 成本_材料分类字典<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/costMateTypeArrtAddPage", method = RequestMethod.GET)
	public String costMateTypeArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costmatetypearrt/costMateTypeArrtAdd";

	}

	/**
	 * 成本_材料分类字典<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/addCostMateTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostMateTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_type_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_type_name").toString()));

		String costMateTypeArrtJson = costMateTypeArrtService.addCostMateTypeArrt(mapVo);

		return JSONObject.parseObject(costMateTypeArrtJson);

	}

	/**
	 * 成本_材料分类字典<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/queryCostMateTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostMateTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costMateTypeArrt = costMateTypeArrtService.queryCostMateTypeArrt(getPage(mapVo));

		return JSONObject.parseObject(costMateTypeArrt);

	}

	/**
	 * 成本_材料分类字典<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/deleteCostMateTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostMateTypeArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("mate_type_code", ids[3]);
			
			listVo.add(mapVo);
		}
		
		String costMateTypeArrtJson = costMateTypeArrtService.deleteBatchCostMateTypeArrt(listVo);
		return JSONObject.parseObject(costMateTypeArrtJson);

	}

	/**
	 * 成本_材料分类字典<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/costMateTypeArrtUpdatePage", method = RequestMethod.GET)
	public String costMateTypeArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostMateTypeArrt costMateTypeArrt = new CostMateTypeArrt();
		costMateTypeArrt = costMateTypeArrtService.queryCostMateTypeArrtByCode(mapVo);
		mode.addAttribute("group_id", costMateTypeArrt.getGroup_id());
		mode.addAttribute("hos_id", costMateTypeArrt.getHos_id());
		mode.addAttribute("copy_code", costMateTypeArrt.getCopy_code());
		mode.addAttribute("mate_type_code", costMateTypeArrt.getMate_type_code());
		mode.addAttribute("mate_type_name", costMateTypeArrt.getMate_type_name());
		mode.addAttribute("supper_code", costMateTypeArrt.getSupper_code());
		mode.addAttribute("is_last", costMateTypeArrt.getIs_last());
		mode.addAttribute("spell_code", costMateTypeArrt.getSpell_code());
		mode.addAttribute("wbx_code", costMateTypeArrt.getWbx_code());
		return "hrp/cost/costmatetypearrt/costMateTypeArrtUpdate";
	}

	/**
	 * 成本_材料分类字典<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costmatetypearrt/updateCostMateTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostMateTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_type_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_type_name").toString()));

		String costMateTypeArrtJson = costMateTypeArrtService.updateCostMateTypeArrt(mapVo);

		return JSONObject.parseObject(costMateTypeArrtJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/costMateTypeArrtImportPage", method = RequestMethod.GET)
	public String costMateTypeArrtImportPage(Model mode) throws Exception {

		return "hrp/cost/costmatetypearrt/costMateTypeArrtImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costmatetypearrt/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "cost\\基础设置", "材料分类.xls");
		return null;
	}

	/**
	 * 成本_材料分类字典<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/readCostMateTypeArrtFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<CostMateTypeArrt> list_err = new ArrayList<CostMateTypeArrt>();

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostMateTypeArrt costMateTypeArrt = new CostMateTypeArrt();

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

					costMateTypeArrt.setMate_type_code(temp[0]);

					mapVo.put("mate_type_code", temp[0]);

				} else {

					err_sb.append("材料分类编码为空  ");

				}

				if (StringUtils.isNotEmpty(temp[1])) {

					costMateTypeArrt.setMate_type_name(temp[1]);

					mapVo.put("mate_type_name", temp[1]);

				} else {

					err_sb.append("材料分类名称为空  ");

				}

				if (StringUtils.isNotEmpty(temp[2])) {

					costMateTypeArrt.setSupper_code(temp[2]);

					mapVo.put("supper_code", temp[2]);

				} else {

					err_sb.append("上级编码为空  ");

				}
				if (StringUtils.isNotEmpty(temp[3])) {

					costMateTypeArrt.setIs_last(Integer.valueOf(temp[3]));

					mapVo.put("is_last", temp[3]);

				} else {

					err_sb.append("是否末级为空  ");

				}
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("mate_type_code", mapVo.get("mate_type_code"));

				CostMateTypeArrt data_exc_extis = costMateTypeArrtService.queryCostMateTypeArrtByCode(byCodeMap);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}

				if (err_sb.toString().length() > 0) {

					costMateTypeArrt.setError_type(err_sb.toString());

					list_err.add(costMateTypeArrt);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_type_name").toString()));

					mapVo.put("mate_type_code", "");

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostMateTypeArrt data_exc = new CostMateTypeArrt();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		if (list_batch.size() > 0) {

			costMateTypeArrtService.addBatchCostMateTypeArrt(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
	}

	/**
	 * 成本_材料分类字典<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/addBatchCostMateTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostMateTypeArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		// List<Map<String, Object>> listVo = new ArrayList<Map<String,
		// Object>>();

		List<CostMateTypeArrt> list_err = new ArrayList<CostMateTypeArrt>();

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {
			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

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

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				mapVo.put("mate_type_code", jsonObj.get("mate_type_code"));

				mapVo.put("mate_type_name", jsonObj.get("mate_type_name"));

				mapVo.put("supper_code", jsonObj.get("supper_code"));

				mapVo.put("is_last", jsonObj.get("is_last"));

				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_type_name").toString()));

				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_type_name").toString()));
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("mate_type_code", mapVo.get("mate_type_code"));

				CostMateTypeArrt data_exc_extis = costMateTypeArrtService.queryCostMateTypeArrtByCode(byCodeMap);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}

				CostMateTypeArrt costMateTypeArrt = new CostMateTypeArrt();

				if (err_sb.toString().length() > 0) {

					costMateTypeArrt.setMate_type_code(mapVo.get("mate_type_code").toString());

					costMateTypeArrt.setMate_type_name(mapVo.get("mate_type_name").toString());

					costMateTypeArrt.setSupper_code(mapVo.get("supper_code").toString());

					costMateTypeArrt.setIs_last(Integer.getInteger(mapVo.get("is_last").toString()));

					costMateTypeArrt.setError_type(err_sb.toString());

					list_err.add(costMateTypeArrt);

				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costMateTypeArrtService.addBatchCostMateTypeArrt(list_batch);

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
	 * 成本_材料分类字典<BR>
	 * 同步 物流管理系统物资分类到成本管理系统材料分类
	 */
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/syncCostMateTypeArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostMateTypeArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costMateTypeArrtJson = costMateTypeArrtService.syncCostMateTypeArrt(mapVo);

		return JSONObject.parseObject(costMateTypeArrtJson);

	}
	/**
	 * 2016/10/31 lxj
	 * 成本_材料分类字典<BR>
	 * 同步 物流管理系统物资分类到成本管理系统材料分类
	 */
	@RequestMapping(value = "/hrp/cost/costmatetypearrt/syncCostMateTypeArrtNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostMateTypeArrtNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costMateTypeArrtJson = costMateTypeArrtService.syncCostMateTypeArrtNew(mapVo);

		return JSONObject.parseObject(costMateTypeArrtJson);

	}
}
