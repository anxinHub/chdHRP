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
import com.chd.hrp.cost.entity.CostDeptParaDict;
import com.chd.hrp.cost.service.CostDeptParaDictService;

/**
 * @Title. @Description. 分摊参数
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class CostDeptParaDictController extends BaseController {

	private static Logger logger = Logger.getLogger(CostDeptParaDictController.class);

	@Resource(name = "costDeptParaDictService")
	private final CostDeptParaDictService costDeptParaDictService = null;

	/**
	 * 分摊参数<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costdeptparadict/costDeptParaDictMainPage", method = RequestMethod.GET)
	public String costDeptParaDictMainPage(Model mode) throws Exception {

		return "hrp/cost/costdeptparadict/costDeptParaDictMain";

	}

	/**
	 * 分摊参数<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costdeptparadict/costDeptParaDictAddPage", method = RequestMethod.GET)
	public String costDeptParaDictAddPage(Model mode) throws Exception {

		return "hrp/cost/costdeptparadict/costDeptParaDictAdd";

	}

	/**
	 * 分摊参数<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costdeptparadict/addCostDeptParaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostDeptParaDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));
		String costDeptParaDictJson = costDeptParaDictService.addCostDeptParaDict(mapVo);

		return JSONObject.parseObject(costDeptParaDictJson);

	}

	/**
	 * 分摊参数<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costdeptparadict/queryCostDeptParaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptParaDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costDeptParaDict = costDeptParaDictService.queryCostDeptParaDict(getPage(mapVo));

		return JSONObject.parseObject(costDeptParaDict);

	}

	/**
	 * 分摊参数<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costdeptparadict/deleteCostDeptParaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostDeptParaDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("para_code", ids[3]);
			
			mapVo.put("is_sys", ids[4]);

			listVo.add(mapVo);
		}
		String costDeptParaDictJson = costDeptParaDictService.deleteBatchCostDeptParaDict(listVo);
		return JSONObject.parseObject(costDeptParaDictJson);

	}

	/**
	 * 分摊参数<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costdeptparadict/costDeptParaDictUpdatePage", method = RequestMethod.GET)
	public String costDeptParaDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostDeptParaDict costDeptParaDict = new CostDeptParaDict();
		costDeptParaDict = costDeptParaDictService.queryCostDeptParaDictByCode(mapVo);
		mode.addAttribute("group_id", costDeptParaDict.getGroup_id());

		mode.addAttribute("hos_id", costDeptParaDict.getHos_id());

		mode.addAttribute("copy_code", costDeptParaDict.getCopy_code());

		mode.addAttribute("para_code", costDeptParaDict.getPara_code());

		mode.addAttribute("para_name", costDeptParaDict.getPara_name());

		mode.addAttribute("remark", costDeptParaDict.getRemark());
		
		mode.addAttribute("is_sys", costDeptParaDict.getIs_sys());

		mode.addAttribute("spell_code", costDeptParaDict.getSpell_code());

		mode.addAttribute("wbx_code", costDeptParaDict.getWbx_code());

		return "hrp/cost/costdeptparadict/costDeptParaDictUpdate";
	}

	/**
	 * 分摊参数<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costdeptparadict/updateCostDeptParaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostDeptParaDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));

		String costDeptParaDictJson = costDeptParaDictService.updateCostDeptParaDict(mapVo);

		return JSONObject.parseObject(costDeptParaDictJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costdeptparadict/costDeptParaDictImportPage", method = RequestMethod.GET)
	public String costDeptParaDictImportPage(Model mode) throws Exception {

		return "hrp/cost/costdeptparadict/costDeptParaDictImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costdeptparadict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "分摊参数.xls");

		return null;
	}

	/**
	 * 分摊参数<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costdeptparadict/readCostDeptParaDictFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostDeptParaDict> list_err = new ArrayList<CostDeptParaDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostDeptParaDict costDeptParaDict = new CostDeptParaDict();

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

					costDeptParaDict.setPara_code(temp[0]);

					mapVo.put("para_code", temp[0]);
					
				} else {
					
					err_sb.append("分摊参数编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[1])) {

					costDeptParaDict.setPara_name(temp[1]);

					mapVo.put("para_name", temp[1]);
					
				} else {
					
					err_sb.append("分摊参数名称为空  ");
					
				}
				
				if (temp[2]!=null && StringUtils.isNotEmpty(temp[2])) {
					
					costDeptParaDict.setRemark(temp[2]);

					mapVo.put("remark", temp[2]);
					
				} 
				
				Map<String,Object> byCodeMap = new HashMap<String,Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("para_code", mapVo.get("para_code"));
				
				byCodeMap.put("is_sys", 0);
				
				CostDeptParaDict data_exc_extis = costDeptParaDictService.queryCostDeptParaDictByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				
				if (err_sb.toString().length() > 0) {

					costDeptParaDict.setError_type(err_sb.toString());

					list_err.add(costDeptParaDict);
					
				} else {
					mapVo.put("is_sys", 0);

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostDeptParaDict data_exc = new CostDeptParaDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costDeptParaDictService.addBatchCostDeptParaDict(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 分摊参数<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costdeptparadict/addBatchCostDeptParaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostDeptParaDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostDeptParaDict> list_err = new ArrayList<CostDeptParaDict>();

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

				mapVo.put("para_code", jsonObj.get("para_code"));

				mapVo.put("para_name", jsonObj.get("para_name"));

				mapVo.put("remark", jsonObj.get("remark"));
				
				mapVo.put("is_sys", 0);

				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("para_name").toString()));

				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("para_name").toString()));

				Map<String,Object> byCodeMap = new HashMap<String,Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("para_code", mapVo.get("para_code"));
				
				byCodeMap.put("is_sys", 0);
				
				CostDeptParaDict data_exc_extis = costDeptParaDictService.queryCostDeptParaDictByCode(byCodeMap);

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}

				CostDeptParaDict costDeptParaDict = new CostDeptParaDict();

				if (err_sb.toString().length() > 0) {
					
					costDeptParaDict.setPara_code(mapVo.get("para_code").toString());

					costDeptParaDict.setPara_name(mapVo.get("para_name").toString());

					costDeptParaDict.setRemark(mapVo.get("remark").toString());

					costDeptParaDict.setError_type(err_sb.toString());

					list_err.add(costDeptParaDict);
					
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costDeptParaDictService.addBatchCostDeptParaDict(list_batch);

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
}
