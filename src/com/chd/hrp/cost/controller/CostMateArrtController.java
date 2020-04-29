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
 * @Title. @Description. 成本_材料信息字典
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostMateArrtController extends BaseController {

	private static Logger logger = Logger.getLogger(CostMateArrtController.class);

	@Resource(name = "costMateArrtService")
	private final CostMateArrtService costMateArrtService = null;
	
	@Resource(name = "costMateTypeArrtService")
	private final CostMateTypeArrtService costMateTypeArrtService = null;
	

	/**
	 * 成本_材料信息字典<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costmatearrt/costMateArrtMainPage", method = RequestMethod.GET)
	public String costMateArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costmatearrt/costMateArrtMain";

	}

	/**
	 * 成本_材料信息字典<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costmatearrt/costMateArrtAddPage", method = RequestMethod.GET)
	public String costMateArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costmatearrt/costMateArrtAdd";

	}

	/**
	 * 成本_材料信息字典<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costmatearrt/addCostMateArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostMateArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_name").toString()));
		String costMateArrtJson = costMateArrtService.addCostMateArrt(mapVo);

		return JSONObject.parseObject(costMateArrtJson);

	}

	/**
	 * 成本_材料信息字典<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costmatearrt/queryCostMateArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostMateArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costMateArrt = costMateArrtService.queryCostMateArrt(getPage(mapVo));

		return JSONObject.parseObject(costMateArrt);

	}

	/**
	 * 成本_材料信息字典<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costmatearrt/deleteCostMateArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostMateArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("mate_id", ids[3]);

			listVo.add(mapVo);
		}
		String costMateArrtJson = costMateArrtService.deleteBatchCostMateArrt(listVo);
		return JSONObject.parseObject(costMateArrtJson);

	}

	/**
	 * 成本_材料信息字典<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costmatearrt/costMateArrtUpdatePage", method = RequestMethod.GET)
	public String costMateArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		CostMateArrt costMateArrt = new CostMateArrt();

		costMateArrt = costMateArrtService.queryCostMateArrtByCode(mapVo);

		mode.addAttribute("group_id", costMateArrt.getGroup_id());

		mode.addAttribute("hos_id", costMateArrt.getHos_id());

		mode.addAttribute("copy_code", costMateArrt.getCopy_code());

		mode.addAttribute("mate_type_id", costMateArrt.getMate_type_id());

		mode.addAttribute("mate_id", costMateArrt.getMate_id());

		mode.addAttribute("mate_code", costMateArrt.getMate_code());

		mode.addAttribute("mate_name", costMateArrt.getMate_name());

		mode.addAttribute("mate_mode", costMateArrt.getMate_mode());

		mode.addAttribute("meas_code", costMateArrt.getMeas_code());

		mode.addAttribute("price", costMateArrt.getPrice());

		mode.addAttribute("spell_code", costMateArrt.getSpell_code());

		mode.addAttribute("wbx_code", costMateArrt.getWbx_code());

		mode.addAttribute("mate_type_code", costMateArrt.getMate_type_code());

		mode.addAttribute("mate_type_name", costMateArrt.getMate_type_name());

		return "hrp/cost/costmatearrt/costMateArrtUpdate";
	}

	/**
	 * 成本_材料信息字典<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costmatearrt/updateCostMateArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostMateArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_name").toString()));

		String costMateArrtJson = costMateArrtService.updateCostMateArrt(mapVo);

		return JSONObject.parseObject(costMateArrtJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costmatearrt/costMateArrtImportPage", method = RequestMethod.GET)
	public String costMateArrtImportPage(Model mode) throws Exception {

		return "hrp/cost/costmatearrt/costMateArrtImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costmatearrt/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "材料信息.xls");

		return null;
	}

	/**
	 * 成本_材料信息字典<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costmatearrt/readCostMateArrtFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostMateArrt> list_err = new ArrayList<CostMateArrt>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostMateArrt costMateArrt = new CostMateArrt();

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

					costMateArrt.setMate_code(temp[0]);

					mapVo.put("mate_code", temp[0]);
					
				} else {
					
					err_sb.append("材料编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[1])) {

					costMateArrt.setMate_name(temp[1]);

					mapVo.put("mate_name", temp[1]);
					
				} else {
					
					err_sb.append("材料名称为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[2])) {

					//costMateArrt.setMate_type_id(Long.valueOf(temp[2]));

					mapVo.put("mate_type_code", temp[2]);
					
				} else {
					
					err_sb.append("材料分类编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {

					costMateArrt.setMate_type_name(temp[3]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[4])) {

					costMateArrt.setMate_mode(temp[4]);

					mapVo.put("mate_mode", temp[4]);
					
				} else {
					
					err_sb.append("型号为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[5])) {

					costMateArrt.setMeas_code(temp[5]);

					mapVo.put("meas_code", temp[5]);
					
				} else {
					
					err_sb.append("单位为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[6])) {

					costMateArrt.setPrice(Double.valueOf(temp[6]));

					mapVo.put("price", temp[6]);
					
				} else {
					
					err_sb.append("单价为空  ");
					
				}


				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("mate_type_code", mapVo.get("mate_type_code"));
				
				CostMateTypeArrt cmta = costMateTypeArrtService.queryCostMateTypeArrtByCode(byCodeMap);

				if(cmta != null ){
					
					mapVo.put("mate_type_id", cmta.getMate_type_id());
					
					costMateArrt.setMate_type_code((String)mapVo.get("mate_type_code"));
					
				}else{
					
					costMateArrt.setMate_type_code((String)mapVo.get("mate_type_code"));
					
					err_sb.append("材料分类编码不存在 ");
					
				}
				

				byCodeMap.put("mate_code", mapVo.get("mate_code"));
				
				CostMateArrt data_exc_extis = costMateArrtService.queryCostMateArrtByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				
				if (err_sb.toString().length() > 0) {

					costMateArrt.setError_type(err_sb.toString());

					list_err.add(costMateArrt);
					
				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_name").toString()));

					mapVo.put("mate_id", "");
					
					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostMateArrt data_exc = new CostMateArrt();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costMateArrtService.addBatchCostMateArrt(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 成本_材料信息字典<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costmatearrt/addBatchCostMateArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostMateArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostMateArrt> list_err = new ArrayList<CostMateArrt>();

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
				
				mapVo.put("mate_type_code", jsonObj.get("mate_type_code"));
				
				mapVo.put("mate_type_name", jsonObj.get("mate_type_name"));

				mapVo.put("mate_id", "");

				mapVo.put("mate_code", jsonObj.get("mate_code"));

				mapVo.put("mate_name", jsonObj.get("mate_name"));

				mapVo.put("mate_mode", jsonObj.get("mate_mode"));

				mapVo.put("meas_code", jsonObj.get("meas_code"));

				mapVo.put("price", jsonObj.get("price"));

				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_name").toString()));

				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_name").toString()));

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));

				byCodeMap.put("mate_type_code", mapVo.get("mate_type_code"));
				
				CostMateTypeArrt cmta = costMateTypeArrtService.queryCostMateTypeArrtByCode(byCodeMap);

				if(cmta != null ){
					
					mapVo.put("mate_type_id", cmta.getMate_type_id());
					
				}else{
					
					err_sb.append("材料分类编码 不存在 ");
					
				}
				
				byCodeMap.put("mate_code", mapVo.get("mate_code"));

				CostMateArrt data_exc_extis = costMateArrtService.queryCostMateArrtByCode(byCodeMap);

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}

				CostMateArrt costMateArrt = new CostMateArrt();

				if (err_sb.toString().length() > 0) {
					
					costMateArrt.setMate_type_code(mapVo.get("mate_type_code").toString());
					
					costMateArrt.setMate_type_name(mapVo.get("mate_type_name").toString());

					costMateArrt.setMate_code(mapVo.get("mate_code").toString());

					costMateArrt.setMate_name(mapVo.get("mate_name").toString());

					costMateArrt.setMate_mode(mapVo.get("mate_mode").toString());

					costMateArrt.setMeas_code(mapVo.get("meas_code").toString());

					costMateArrt.setPrice(Double.valueOf(mapVo.get("price").toString()));

					costMateArrt.setError_type(err_sb.toString());

					list_err.add(costMateArrt);
					
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costMateArrtService.addBatchCostMateArrt(list_batch);

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
	 * 成本_材料信息字典<BR>
	 * 同步 物流管理系统物资分类到成本管理系统材料信息
	 */
	@RequestMapping(value = "/hrp/cost/costmatearrt/syncCostMateArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostMateArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costMateArrtJson = costMateArrtService.syncCostMateArrt(mapVo);

		return JSONObject.parseObject(costMateArrtJson);

	}
	/**
	 * 2016/10/31 lxj
	 * 成本_材料信息字典<BR>
	 * 同步 物流管理系统物资分类到成本管理系统材料信息
	 */
	@RequestMapping(value = "/hrp/cost/costmatearrt/syncCostMateArrtNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostMateArrtNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costMateArrtJson = costMateArrtService.syncCostMateArrtNew(mapVo);

		return JSONObject.parseObject(costMateArrtJson);

	}
}
