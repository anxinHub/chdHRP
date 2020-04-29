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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostFassetCostRela;
import com.chd.hrp.cost.entity.CostFassetTypeArrt;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.service.CostFassetCostRelaService;
import com.chd.hrp.cost.service.CostFassetTypeArrtService;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.chd.hrp.cost.service.CostParaService;

/**
 * @Title. @Description. 固定资产分类与成本项目的对应关系
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class CostFassetCostRelaController extends BaseController {

	private static Logger logger = Logger.getLogger(CostFassetCostRelaController.class);

	@Resource(name = "costFassetCostRelaService")
	private final CostFassetCostRelaService costFassetCostRelaService = null;
	
	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoService costItemDictNoService = null;
	
	@Resource(name = "costFassetTypeArrtService")
	private final CostFassetTypeArrtService costFassetTypeArrtService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
	

	/**
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costfassetcostrela/costFassetCostRelaMainPage", method = RequestMethod.GET)
	public String costFassetCostRelaMainPage(Model mode) throws Exception {

		return "hrp/cost/costfassetcostrela/costFassetCostRelaMain";

	}

	/**
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/costFassetCostRelaAddPage", method = RequestMethod.GET)
	public String costFassetCostRelaAddPage(Model mode) throws Exception {

		return "hrp/cost/costfassetcostrela/costFassetCostRelaAdd";

	}

	/**
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/addCostFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostFassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costFassetCostRelaJson = costFassetCostRelaService.addCostFassetCostRela(mapVo);

		return JSONObject.parseObject(costFassetCostRelaJson);

	}

	/**
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/queryCostFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostFassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String para_value = MyConfig.getSysPara("03001");
		mapVo.put("is_flag", para_value);
		
		String costFassetCostRela = costFassetCostRelaService.queryCostFassetCostRela(getPage(mapVo));

		return JSONObject.parseObject(costFassetCostRela);

	}

	/**
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/deleteCostFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostFassetCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("id", ids[3]);

			listVo.add(mapVo);
		}
		String costFassetCostRelaJson = costFassetCostRelaService.deleteBatchCostFassetCostRela(listVo);
		return JSONObject.parseObject(costFassetCostRelaJson);

	}

	/**
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/costFassetCostRelaUpdatePage", method = RequestMethod.GET)
	public String costFassetCostRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String para_value = MyConfig.getSysPara("03001");
		
		mapVo.put("is_flag", para_value);
		
		CostFassetCostRela costFassetCostRela = new CostFassetCostRela();
		costFassetCostRela = costFassetCostRelaService.queryCostFassetCostRelaByCode(mapVo);
		mode.addAttribute("id", costFassetCostRela.getId());

		mode.addAttribute("group_id", costFassetCostRela.getGroup_id());

		mode.addAttribute("hos_id", costFassetCostRela.getHos_id());

		mode.addAttribute("copy_code", costFassetCostRela.getCopy_code());

		mode.addAttribute("cost_item_code", costFassetCostRela.getCost_item_code());

		mode.addAttribute("asset_type_code", costFassetCostRela.getAsset_type_code());

		mode.addAttribute("cost_item_name", costFassetCostRela.getCost_item_name());

		mode.addAttribute("asset_type_name", costFassetCostRela.getAsset_type_name());

		return "hrp/cost/costfassetcostrela/costFassetCostRelaUpdate";
	}

	/**
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costfassetcostrela/updateCostFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostFassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costFassetCostRelaJson = costFassetCostRelaService.updateCostFassetCostRela(mapVo);

		return JSONObject.parseObject(costFassetCostRelaJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/costFassetCostRelaImportPage", method = RequestMethod.GET)
	public String costFassetCostRelaImportPage(Model mode) throws Exception {

		return "hrp/cost/costfassetcostrela/costFassetCostRelaImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costfassetcostrela/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "固定资产分类与成本项目的对应关系.xls");

		return null;
	}

	/**
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/readCostFassetCostRelaFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostFassetCostRela> list_err = new ArrayList<CostFassetCostRela>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostFassetCostRela costFassetCostRela = new CostFassetCostRela();

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

					mapVo.put("asset_type_code", temp[0]);
					
				} else {
					
					err_sb.append("资产分类编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[1])) {

					costFassetCostRela.setAsset_type_name(temp[1]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[2])) {

					mapVo.put("cost_item_code", temp[2]);
					
				} else {
					
					err_sb.append("成本项目编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {

					costFassetCostRela.setCost_item_name(temp[3]);
					
				}

				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostFassetTypeArrt cfta = costFassetTypeArrtService.queryCostFassetTypeArrtByCode(byCodeMap);

				if(cfta != null ){
					
					mapVo.put("asset_type_code", cfta.getAsset_type_code());
					
					costFassetCostRela.setAsset_type_code((String)mapVo.get("asset_type_code"));
					
				}else{
					
					costFassetCostRela.setAsset_type_code((String)mapVo.get("asset_type_code"));
					
					err_sb.append("资产分类不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));
				
				byCodeMap.put("is_stop", "0");

				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_code", cidn.getCost_item_code());
					
					costFassetCostRela.setCost_item_code((String)mapVo.get("cost_item_code"));
					
				}else{
					
					costFassetCostRela.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					err_sb.append("成本项目不存在 ");
					
				}
				
				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostFassetCostRela data_exc_extis = costFassetCostRelaService.queryCostFassetCostRelaByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("对应关系已经存在！ ");
					
				}
				
				if (err_sb.toString().length() > 0) {

					costFassetCostRela.setError_type(err_sb.toString());

					list_err.add(costFassetCostRela);
					
				} else {
					
					mapVo.put("id", "");

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostFassetCostRela data_exc = new CostFassetCostRela();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costFassetCostRelaService.addBatchCostFassetCostRela(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/addBatchCostFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostFassetCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostFassetCostRela> list_err = new ArrayList<CostFassetCostRela>();

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

				mapVo.put("id", "");

				mapVo.put("asset_type_code", jsonObj.get("asset_type_code"));
				
				mapVo.put("asset_type_name", jsonObj.get("asset_type_name"));

				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));

				mapVo.put("cost_item_name", jsonObj.get("cost_item_name"));
				
				///////////////////////////////////////////////////////////////////////////////
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostFassetTypeArrt cfta = costFassetTypeArrtService.queryCostFassetTypeArrtByCode(byCodeMap);
				
				if(cfta != null ){
				
					mapVo.put("asset_type_code", cfta.getAsset_type_code());	
				
				}else{
				
					err_sb.append("材料分类编码 不存在 ");
				
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));
				
				byCodeMap.put("is_stop", "0");
				
				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);
				
				if(cidn != null ){
				
					mapVo.put("cost_item_code", cidn.getCost_item_code());
					
				}else{
				
					err_sb.append("成本项目 不存在 ");
				
				}

				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostFassetCostRela data_exc_extis = costFassetCostRelaService.queryCostFassetCostRelaByCode(byCodeMap);

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}

				CostFassetCostRela costFassetCostRela = new CostFassetCostRela();

				if (err_sb.toString().length() > 0) {

					costFassetCostRela.setAsset_type_code(mapVo.get("asset_type_code").toString());
					
					costFassetCostRela.setAsset_type_name(mapVo.get("asset_type_name").toString());

					costFassetCostRela.setCost_item_code(mapVo.get("cost_item_code").toString());
					
					costFassetCostRela.setCost_item_name(mapVo.get("cost_item_name").toString());

					costFassetCostRela.setError_type(err_sb.toString());

					list_err.add(costFassetCostRela);
					
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costFassetCostRelaService.addBatchCostFassetCostRela(list_batch);

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
	 * 2016/10/26 lxj
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */
	// 继承页面
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/costFassetCostRelaExtendPage", method = RequestMethod.GET)
	public String costFassetCostRelaExtendPage(Model mode) throws Exception {

		return "hrp/cost/costfassetcostrela/costFassetCostRelaExtend";

	}
	
	/**
	 * 2016/10/26 lxj
	 * 固定资产分类与成本项目的对应关系<BR>
	 * 继承
	 */
	@RequestMapping(value = "/hrp/cost/costfassetcostrela/extendCostFassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendCostFassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costFassetCostRelaJson = costFassetCostRelaService.extendCostFassetCostRela(mapVo);

		return JSONObject.parseObject(costFassetCostRelaJson);

	}
}
